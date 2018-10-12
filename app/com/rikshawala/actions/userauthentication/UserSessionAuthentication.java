package com.rikshawala.actions.userauthentication;

import com.rikshawala.controllers.BaseController;
import com.rikshawala.exceptions.MyException;
import com.rikshawala.models.AutoSession;
import com.rikshawala.services.UserAuthenticatorService;
import com.rikshawala.utils.MyConstants.*;

import play.Logger;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

import javax.inject.Inject;
import java.util.concurrent.CompletionStage;

public class UserSessionAuthentication extends Action<AuthenticateUser> {

	@Inject
	private BaseController baseController;

	@Inject
	private UserAuthenticatorService userAuthenticatorService;

	@Override
	public CompletionStage<Result> call(Http.Context ctx) {
		try {
			if (isSessionValid(ctx)) {
				// uncomment the following line to set custom attributes in Http
				// Context
				// ctx.args.put("key", "value");
				return delegate.call(ctx);
			}
		} catch (Exception e) {
			Logger.info("Exception while authenticating user " + e.getMessage());
		}

		return baseController.failureResponsePromise(new MyException(ApiFailureMessages.SESSION_INVALID));
	}

	public boolean isSessionValid(Http.Context ctx) {
		boolean isPublicApi = configuration.isPublicApi();
		String accessToken = ctx.request().getHeader(ApiRequestHeaders.SESSION_TOKEN_HEADER);

		if (accessToken != null) {
			AutoSession autoSession = userAuthenticatorService.getAutoSession(accessToken);
			ctx.args.put(ContextConstants.AUTO_ID, autoSession.getAutoId());
			ctx.args.put(ContextConstants.AUTO_SESSION, autoSession.getToken());
		}

		return (isPublicApi && accessToken == null)
				|| (accessToken != null && userAuthenticatorService.getAutoSession(accessToken) != null);
	}

}