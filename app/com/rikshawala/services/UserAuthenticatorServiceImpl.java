package com.rikshawala.services;

import javax.inject.Inject;

import com.rikshawala.daos.AutoSessionDAO;
import com.rikshawala.models.AutoSession;

public class UserAuthenticatorServiceImpl implements UserAuthenticatorService {

	private AutoSessionDAO autoSessionDAO;

	@Inject
	public UserAuthenticatorServiceImpl(AutoSessionDAO autoSessionDAO) {
		this.autoSessionDAO = autoSessionDAO;
	}

	@Override
	public boolean isSessionValid(String accessToken, boolean isPublicApi) {
		return (isPublicApi && accessToken == null)
				|| (accessToken != null && autoSessionDAO.findByToken(accessToken) != null);
	}

	@Override
	public AutoSession getAutoSession(String accessToken) {
		return autoSessionDAO.findByToken(accessToken);
	}

}
