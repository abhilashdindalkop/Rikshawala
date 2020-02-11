package com.rikshawala.daos;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.rikshawala.exceptions.MyException;
import com.rikshawala.models.Autowala;
import com.rikshawala.utils.MongoConnection;
import com.rikshawala.utils.MyConstants;
import com.rikshawala.utils.MyConstants.ApiFailureMessages;
import com.rikshawala.utils.MyConstants.AutowalaStatus;

public class AutowalaDAOImpl implements AutowalaDAO {

	private static Datastore ds = MongoConnection.getDS();

	@Override
	public Autowala findById(String autoId) throws MyException {
		Autowala autowala = ds.find(Autowala.class).filter("autoId", autoId).get();
		if (autowala == null) {
			throw new MyException(ApiFailureMessages.AUTOWALA_DOESNT_EXIST);
		}
		return autowala;
	}

	@Override
	public Autowala findByVehicleNoOrPhoneNo(String vehicleNo, String phoneNo) {
		Query<Autowala> query = ds.find(Autowala.class);
		query.or(query.criteria("vehicleNo").equal(vehicleNo), query.criteria("phoneNo").equal(phoneNo));
		return query.get();
	}

	@Override
	public Autowala add(Autowala newAuto) {
		newAuto.setStatus(AutowalaStatus.NOT_AVAILABLE);
		newAuto.setAutoId(UUID.randomUUID().toString());
		newAuto.setCreatedTime(new Date());
		newAuto.setUpdatedTime(new Date());
		ds.save(newAuto);
		return newAuto;
	}

	@Override
	public void updateFareType(String autoId, int fareType) throws MyException {

		if (!MyConstants.FARE_TYPES.contains(fareType)) {
			throw new MyException(ApiFailureMessages.FARE_TYPE_DOESNT_EXIST);
		}

		Query<Autowala> autoUpdate = ds.find(Autowala.class).filter("autoId", autoId);
		UpdateOperations<Autowala> ops = ds.createUpdateOperations(Autowala.class);
		ops.set("fareType", fareType);
		ds.update(autoUpdate, ops);
	}

	@Override
	public void updateDetails(String autoId, String name, String description, String vehicleNo, String phoneNo)
			throws MyException {

		Query<Autowala> autoUpdate = ds.find(Autowala.class).filter("autoId", autoId);

		UpdateOperations<Autowala> ops = ds.createUpdateOperations(Autowala.class);

		if (description != null) {
			ops.set("description", description);
		}
		if (name != null) {
			ops.set("name", name);
		}
		if (vehicleNo != null) {
			Autowala existAuto = findByVehicleNoOrPhoneNo(vehicleNo, null);
			if (existAuto != null && existAuto.getAutoId() != autoId) {
				throw new MyException(ApiFailureMessages.VEHICLE_NUMBER_ALREADY_EXIST);
			}
			// TODO check regex for vehicle number
			ops.set("vehicleNo", vehicleNo);
		}

		if (phoneNo != null) {
			Autowala existAuto = findByVehicleNoOrPhoneNo(null, phoneNo);
			if (existAuto != null && existAuto.getAutoId() != autoId) {
				throw new MyException(ApiFailureMessages.VEHICLE_NUMBER_ALREADY_EXIST);
			}
			// TODO check regex for phone number
			ops.set("phoneNo", phoneNo);
		}

		ds.update(autoUpdate, ops);
	}

	@Override
	public void updateAutoStatus(String autoId, int status) {
		Query<Autowala> autoUpdate = ds.find(Autowala.class).filter("autoId", autoId);
		UpdateOperations<Autowala> ops = ds.createUpdateOperations(Autowala.class);
		ops.set("status", status);
		ds.update(autoUpdate, ops);
	}

	@Override
	public void updateLocation(String autoId, double latitude, double longitude) {
		Query<Autowala> autoUpdate = ds.find(Autowala.class).filter("autoId", autoId);
		UpdateOperations<Autowala> ops = ds.createUpdateOperations(Autowala.class);

		List<Double> location = Arrays.asList(longitude, latitude);
		ops.set("location", location);

		ds.update(autoUpdate, ops);
	}

	@Override
	public Query<Autowala> findNearbyAutos(double latitude, double longitude, long distance) {
		double distanceInKM = distance / 1000;
		return ds.find(Autowala.class).field("Location").near(latitude, longitude, distanceInKM / 111.12);
	}

	@Override
	public Query<Autowala> filterByFareTypes(Query<Autowala> query, List<Integer> fareTypes) {
		if (fareTypes.isEmpty()) {
			return query;
		}
		return query.field("fareType").in(fareTypes);

	}

	@Override
	public List<Autowala> searchByNamePhoneVehicleNo(String searchText) {

		Query<Autowala> autoQuery = ds.find(Autowala.class);

		autoQuery.or(autoQuery.criteria("name").containsIgnoreCase(searchText),
				autoQuery.criteria("phoneNo").containsIgnoreCase(searchText),
				autoQuery.criteria("vehicleNo").containsIgnoreCase(searchText));

		return autoQuery.order("name").limit(MyConstants.DEFAULT_LIMIT).asList();
	}

}
