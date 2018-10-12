package com.rikshawala.services;

import java.util.List;

import com.google.inject.ImplementedBy;
import com.rikshawala.dtos.request.AutoDetailsDTO;
import com.rikshawala.dtos.request.AutoFeedsRequestDTO;
import com.rikshawala.dtos.request.AutoRegisterResponseDTO;
import com.rikshawala.dtos.request.AutoSignUpRequestDTO;
import com.rikshawala.exceptions.MyException;

@ImplementedBy(AutowalaServiceImpl.class)
public interface AutowalaService {

	AutoRegisterResponseDTO signUpAutowala(AutoSignUpRequestDTO payload) throws MyException;

	AutoRegisterResponseDTO signInAutowala(AutoSignUpRequestDTO payload) throws MyException;

	void updateAutoStatus(AutoDetailsDTO payload) throws MyException;

	void updateAutoLocation(AutoDetailsDTO payload) throws MyException;

	void updateAutoFareType(AutoDetailsDTO payload) throws MyException;

	void updateAutoDetails(AutoDetailsDTO payload) throws MyException;

	AutoRegisterResponseDTO getAutoDetails() throws MyException;

	List<AutoDetailsDTO> autoFeeds(AutoFeedsRequestDTO payload) throws MyException;

}
