
package com.rikshawala.services;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;

import org.mongodb.morphia.query.Query;

import com.rikshawala.daos.AutoSessionDAO;
import com.rikshawala.daos.AutowalaDAO;
import com.rikshawala.dtos.request.AutoDetailsDTO;
import com.rikshawala.dtos.request.AutoFeedsRequestDTO;
import com.rikshawala.dtos.request.AutoRegisterResponseDTO;
import com.rikshawala.dtos.request.AutoSignUpRequestDTO;
import com.rikshawala.exceptions.MyException;
import com.rikshawala.models.AutoSession;
import com.rikshawala.models.Autowala;
import com.rikshawala.utils.CustomObjectMapper;
import com.rikshawala.utils.PasswordEncryptDecrypt;
import com.rikshawala.utils.MyConstants;
import com.rikshawala.utils.MyConstants.ApiFailureMessages;

public class AutowalaServiceImpl implements AutowalaService {

	AutowalaDAO autowalaDAO;
	AutoSessionDAO autosessionDAO;
	CustomObjectMapper mapper;
	PasswordEncryptDecrypt passwordEncrypt;

	@Inject
	public AutowalaServiceImpl(CustomObjectMapper mapper, AutowalaDAO autowalaDAO, AutoSessionDAO autosessionDAO,
			PasswordEncryptDecrypt passwordEncrypt) {
		this.autowalaDAO = autowalaDAO;
		this.autosessionDAO = autosessionDAO;
		this.mapper = mapper;
		this.passwordEncrypt = passwordEncrypt;
	}

	@Override
	public AutoRegisterResponseDTO signUpAutowala(AutoSignUpRequestDTO payload) throws MyException {

		if (autowalaDAO.findByVehicleNoOrPhoneNo(payload.vehicleNo, payload.phoneNo) != null) {
			throw new MyException(ApiFailureMessages.VEHICLE_OR_PHONE_ALREADY_EXIST);
		}

		/* Create Autowala */
		Autowala newAuto = new Autowala();
		newAuto = mapper.getInstance().convertValue(payload, Autowala.class);

		List<Double> location = Arrays.asList(payload.longitude, payload.latitude);
		newAuto.setLocation(location);

		/* Hash the password */
		newAuto.setPassword(passwordEncrypt.generatePasswordHash(payload.password));
		newAuto = autowalaDAO.add(newAuto);

		/* Create Autowala Session */
		AutoSession autoSession = autosessionDAO.create(newAuto, payload);

		return constructAutowalaResponse(newAuto, autoSession);
	}

	@Override
	public AutoRegisterResponseDTO signInAutowala(AutoSignUpRequestDTO payload) throws MyException {

		/* Find Autowala */
		Autowala auto = autowalaDAO.findByVehicleNoOrPhoneNo(payload.vehicleNo, payload.phoneNo);

		if (auto == null) {
			throw new MyException(ApiFailureMessages.AUTOWALA_DOESNT_EXIST);
		}

		String encryptedPassword = passwordEncrypt.generatePasswordHash(payload.password);
		if (!passwordEncrypt.isPasswordSame(auto.getPassword(), encryptedPassword)) {
			throw new MyException(ApiFailureMessages.INVALID_PASSWORD);
		}

		// TODO update lat lon

		/* Create Autowala Session */
		AutoSession autoSession = autosessionDAO.create(auto, payload);

		return constructAutowalaResponse(auto, autoSession);
	}

	@Override
	public AutoRegisterResponseDTO getAutoDetails() throws MyException {

		/* Find Autowala */
		Autowala auto = autowalaDAO.findById(autosessionDAO.getAutoIdByContext());

		if (auto == null) {
			throw new MyException(ApiFailureMessages.AUTOWALA_DOESNT_EXIST);
		}
		return constructAutowalaResponse(auto, null);
	}

	private AutoRegisterResponseDTO constructAutowalaResponse(Autowala autowala, AutoSession autoSession) {

		AutoRegisterResponseDTO autoResponse = new AutoRegisterResponseDTO();

		autoResponse.autoId = autowala.getAutoId();
		if (autoSession != null) {
			autoResponse.token = autoSession.getToken();
		}
		if (!autowala.getLocation().isEmpty()) {
			autoResponse.longitude = autowala.getLocation().get(0);
			autoResponse.latitude = autowala.getLocation().get(1);
		}
		autoResponse.fareType = autowala.getFareType();
		autoResponse.phoneNo = autowala.getPhoneNo();
		autoResponse.vehicleNo = autowala.getVehicleNo();
		autoResponse.name = autowala.getName();

		return autoResponse;
	}

	@Override
	public void updateAutoStatus(AutoDetailsDTO payload) throws MyException {

		String autoId = autosessionDAO.getAutoIdByContext();
		autowalaDAO.updateAutoStatus(autoId, payload.status);
	}

	@Override
	public void updateAutoLocation(AutoDetailsDTO payload) throws MyException {

		String autoId = autosessionDAO.getAutoIdByContext();
		autowalaDAO.updateLocation(autoId, payload.latitude, payload.longitude);
	}

	@Override
	public void updateAutoFareType(AutoDetailsDTO payload) throws MyException {

		String autoId = autosessionDAO.getAutoIdByContext();
		autowalaDAO.updateFareType(autoId, payload.fareType);
	}

	@Override
	public void updateAutoDetails(AutoDetailsDTO payload) throws MyException {

		String autoId = autosessionDAO.getAutoIdByContext();
		autowalaDAO.updateDetails(autoId, payload.name, payload.description, payload.vehicleNo, payload.phoneNo);

	}

	@Override
	public List<AutoDetailsDTO> autoFeeds(AutoFeedsRequestDTO payload) throws MyException {

		List<Autowala> autoList = new ArrayList<Autowala>();

		if (payload.latitude != null && payload.longitude != null) {

			payload.distance = payload.distance == null ? 2 : payload.distance;
			Query<Autowala> autoQuery = autowalaDAO.findNearbyAutos(payload.latitude, payload.longitude,
					payload.distance);

			if (!payload.fareTypes.isEmpty()) {
				autoQuery = autowalaDAO.filterByFareTypes(autoQuery, payload.fareTypes);
			}
			autoList = autoQuery.limit(MyConstants.DEFAULT_LIMIT).asList();
		} else if (payload.searchText != null) {

			autoList = autowalaDAO.searchByNamePhoneVehicleNo(payload.searchText);

		}

		return constructAutowalaListResponse(autoList);
	}

	private List<AutoDetailsDTO> constructAutowalaListResponse(List<Autowala> autoList) {

		List<AutoDetailsDTO> autoListResponse = new ArrayList<AutoDetailsDTO>();

		for (Autowala auto : autoList) {
			AutoDetailsDTO newAuto = new AutoDetailsDTO();

			newAuto.name = auto.getName();
			newAuto.description = auto.getDescription();
			newAuto.fareType = auto.getFareType();
			newAuto.phoneNo = auto.getPhoneNo();
			newAuto.vehicleNo = auto.getVehicleNo();
			autoListResponse.add(newAuto);
		}

		return autoListResponse;
	}
}
