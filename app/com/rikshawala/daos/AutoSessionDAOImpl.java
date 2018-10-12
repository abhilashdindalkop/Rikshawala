package com.rikshawala.daos;

import java.util.Date;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.query.Query;

import com.rikshawala.dtos.request.AutoSignUpRequestDTO;
import com.rikshawala.models.AutoSession;
import com.rikshawala.models.Autowala;
import com.rikshawala.utils.MongoConnection;
import com.rikshawala.utils.MyConstants.ContextConstants;
import com.rikshawala.utils.MyConstants.OsType;

import play.Logger;
import play.mvc.Http;

public class AutoSessionDAOImpl implements AutoSessionDAO {

	private static Datastore ds = MongoConnection.getDS();

	@Override
	public AutoSession create(Autowala autowala, AutoSignUpRequestDTO payload) {

		if (payload.deviceId != null) {
			/* Delete session for duplicate device id */
			Query<AutoSession> query = ds.createQuery(AutoSession.class);
			query.field("deviceId").equal(payload.deviceId);
			ds.findAndDelete(query);
		}

		if (payload.deviceToken != null) {
			/* Delete session for duplicate device token */
			Query<AutoSession> query = ds.find(AutoSession.class);
			query.field("deviceToken").equal(payload.deviceToken);
			ds.findAndDelete(query);
		}

		if (payload.deviceTypeId != OsType.ANDROID && payload.deviceTypeId != OsType.IOS
				&& payload.deviceTypeId != OsType.BROWSER) {
			payload.deviceTypeId = OsType.UNKNOWN;
		}

		AutoSession autoSession = new AutoSession();

		String token = UUID.randomUUID().toString();
		autoSession.setToken(token);
		autoSession.setDeviceToken(payload.deviceToken);
		autoSession.setAutoId(autowala.getAutoId());
		autoSession.setDeviceType(payload.deviceTypeId);

		autoSession.setUpdatedTime(new Date());
		autoSession.setCreatedTime(new Date());

		if (payload.deviceTypeId == OsType.BROWSER || payload.deviceTypeId == OsType.UNKNOWN) {
			autoSession.setDeviceId(token);
		} else {
			/*
			 * registering device with push notification server (apns, gcm)
			 */
			if (payload.deviceToken != null) {
				try {
					// autoSession.setEndPointArn(PushNotificationSender.registerDevice(payload.deviceToken,
					// deviceTypeId));
				} catch (Exception e) {
					Logger.info(e.getMessage());
				}
			}
			autoSession.setDeviceId(payload.deviceId);
		}

		ds.save(autoSession);
		return autoSession;
	}

	@Override
	public AutoSession findByDeviceToken(String deviceToken) {
		return ds.find(AutoSession.class).filter("deviceToken", deviceToken).get();
	}

	@Override
	public AutoSession findByToken(String accessToken) {
		return ds.find(AutoSession.class).filter("token", accessToken).get();
	}

	@Override
	public String getAutoIdByContext() {
		String autoId = (String) Http.Context.current().args.get(ContextConstants.AUTO_ID);
		return autoId;
	}

	@Override
	public String getAutoSessionByContext() {
		String autoSession = (String) Http.Context.current().args.get(ContextConstants.AUTO_SESSION);
		return autoSession;
	}

}
