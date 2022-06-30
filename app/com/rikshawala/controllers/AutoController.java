package com.rikshawala.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletionStage;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.JsonNode;
import com.rikshawala.actions.jsonrequestvalidation.ValidateJson;
import com.rikshawala.actions.userauthentication.AuthenticateUser;
import com.rikshawala.dtos.request.AutoDetailsDTO;
import com.rikshawala.dtos.request.AutoFeedsRequestDTO;
import com.rikshawala.dtos.request.AutoRegisterResponseDTO;
import com.rikshawala.dtos.request.AutoSignInRequestDTO;
import com.rikshawala.dtos.request.AutoSignUpRequestDTO;
import com.rikshawala.exceptions.MyException;
import com.rikshawala.services.AutowalaService;
import com.rikshawala.utils.CustomObjectMapper;
import com.rikshawala.utils.MyConstants.ApiFailureMessages;

import play.mvc.BodyParser;
import play.mvc.Result;

public class AutoController extends BaseController {

	private CustomObjectMapper customObjectMapper;
	private AutowalaService autoService;

	int x;

	@Inject
	public AutoController(CustomObjectMapper customObjectMapper, AutowalaService autoService) {
		this.customObjectMapper = customObjectMapper;
		this.autoService = autoService;
	}

	@BodyParser.Of(BodyParser.Json.class)
	@ValidateJson(AutoSignUpRequestDTO.class)
	public CompletionStage<Result> signUp() {
		JsonNode inputData = request().body().asJson();
		AutoSignUpRequestDTO payload = new AutoSignUpRequestDTO();

		AutoRegisterResponseDTO response = new AutoRegisterResponseDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoSignUpRequestDTO.class);
			response = autoService.signUpAutowala(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise(response);
	}

	@BodyParser.Of(BodyParser.Json.class)
	@ValidateJson(AutoSignInRequestDTO.class)
	public CompletionStage<Result> signIn() {
		JsonNode inputData = request().body().asJson();
		AutoSignUpRequestDTO payload = new AutoSignUpRequestDTO();

		AutoRegisterResponseDTO response = new AutoRegisterResponseDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoSignUpRequestDTO.class);
			response = autoService.signInAutowala(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise(response);
	}

	@BodyParser.Of(BodyParser.Json.class)
	@AuthenticateUser()
	public CompletionStage<Result> updateAutoStatus() {
		JsonNode inputData = request().body().asJson();

		AutoDetailsDTO payload = new AutoDetailsDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoDetailsDTO.class);

			if (payload.status == null) {
				throw new MyException(ApiFailureMessages.FIELD_MISSING);
			}
			autoService.updateAutoStatus(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise();
	}

	@BodyParser.Of(BodyParser.Json.class)
	@AuthenticateUser()
	public CompletionStage<Result> updateAutoLocation() {
		JsonNode inputData = request().body().asJson();

		AutoDetailsDTO payload = new AutoDetailsDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoDetailsDTO.class);
			if (payload.latitude == null || payload.longitude == null) {
				throw new MyException(ApiFailureMessages.FIELD_MISSING);
			}
			autoService.updateAutoLocation(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise();
	}

	@BodyParser.Of(BodyParser.Json.class)
	@AuthenticateUser()
	public CompletionStage<Result> updateAutoFareType() {
		JsonNode inputData = request().body().asJson();

		AutoDetailsDTO payload = new AutoDetailsDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoDetailsDTO.class);
			if (payload.fareType == null) {
				throw new MyException(ApiFailureMessages.FIELD_MISSING);
			}
			autoService.updateAutoFareType(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise();
	}

	@BodyParser.Of(BodyParser.Json.class)
	@AuthenticateUser()
	public CompletionStage<Result> updateAutoDetails() {
		JsonNode inputData = request().body().asJson();

		AutoDetailsDTO payload = new AutoDetailsDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoDetailsDTO.class);
			autoService.updateAutoDetails(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise();
	}

	@AuthenticateUser()
	public CompletionStage<Result> getAutoDetails() {

		AutoRegisterResponseDTO response = new AutoRegisterResponseDTO();
		try {
			response = autoService.getAutoDetails();
		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise(response);
	}

	@BodyParser.Of(BodyParser.Json.class)
	public CompletionStage<Result> autoFeeds() {
		JsonNode inputData = request().body().asJson();

		List<AutoDetailsDTO> autoResponseList = new ArrayList<AutoDetailsDTO>();

		AutoFeedsRequestDTO payload = new AutoFeedsRequestDTO();
		try {
			payload = customObjectMapper.getInstance().convertValue(inputData, AutoFeedsRequestDTO.class);
			autoResponseList = autoService.autoFeeds(payload);

		} catch (Exception e) {
			return failureResponsePromise(e);
		}

		return successResponsePromise(autoResponseList);
	}
}
