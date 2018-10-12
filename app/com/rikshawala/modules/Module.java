package com.rikshawala.modules;

import com.google.inject.AbstractModule;
import com.rikshawala.daos.AutoSessionDAO;
import com.rikshawala.daos.AutoSessionDAOImpl;
import com.rikshawala.services.UserAuthenticatorService;
import com.rikshawala.services.UserAuthenticatorServiceImpl;
import com.rikshawala.utils.*;

public class Module extends AbstractModule {
	@Override
	protected void configure() {
		bind(CustomObjectMapper.class).to(CustomObjectMapperImpl.class);
        bind(UserAuthenticatorService.class).to(UserAuthenticatorServiceImpl.class);
        bind(AutoSessionDAO.class).to(AutoSessionDAOImpl.class);
		bind(ErrorHandler.class).asEagerSingleton();
	}
}