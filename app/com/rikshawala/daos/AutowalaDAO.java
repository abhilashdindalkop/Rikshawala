package com.rikshawala.daos;

import java.util.List;

import org.mongodb.morphia.query.Query;

import com.google.inject.ImplementedBy;
import com.rikshawala.exceptions.MyException;
import com.rikshawala.models.Autowala;

@ImplementedBy(AutowalaDAOImpl.class)
public interface AutowalaDAO {

	Autowala findById(String autoId) throws MyException;

	Autowala add(Autowala newAuto);

	void updateFareType(String autoId, int fareType) throws MyException;

	Autowala findByVehicleNoOrPhoneNo(String vehicleNo, String phoneNo);

	void updateDetails(String autoId, String name, String description, String vehicleNo, String phoneNo)
			throws MyException;

	void updateAutoStatus(String autoId, int status);

	void updateLocation(String autoId, double latitude, double longitude);

	/* Distance in Meters */
	Query<Autowala> findNearbyAutos(double latitude, double longitude, long distance);

	Query<Autowala> filterByFareTypes(Query<Autowala> query, List<Integer> fareTypes);

	List<Autowala> searchByNamePhoneVehicleNo(String searchText);

}
