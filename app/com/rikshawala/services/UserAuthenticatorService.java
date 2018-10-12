package com.rikshawala.services;

import com.rikshawala.models.AutoSession;

public interface UserAuthenticatorService {

	boolean isSessionValid(String accessToken, boolean isPublicApi);

	AutoSession getAutoSession(String accessToken);

}