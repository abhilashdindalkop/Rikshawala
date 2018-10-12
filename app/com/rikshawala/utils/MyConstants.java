package com.rikshawala.utils;

import java.util.Arrays;
import java.util.List;

public class MyConstants {

	public static final int DEFAULT_LIMIT = 10;

	public class ContextConstants {
		public static final String AUTO_ID = "AUTO_ID";
		public static final String AUTO_SESSION = "AUTO_SESSION";
	}

	public class OsType {
		public static final int ANDROID = 1;
		public static final int IOS = 2;
		public static final int BROWSER = 3;
		public static final int UNKNOWN = 4;
	}

	public class ApiRequestHeaders {
		public static final String SESSION_TOKEN_HEADER = "Session-Token";
	}

	public class ApiFailureMessages {
		public static final String INVALID_JSON_REQUEST = "invalid.json.request";
		public static final String INVALID_API_CALL = "invalid.api.call";
		public static final String ACCESS_FORBIDDEN = "access.forbidden";
		public static final String TECHNICAL_ERROR = "technical.error";
		public static final String INVALID_INPUT = "invalid.input";
		public static final String TYPE_MISMATCH = "type.mismatch";
		public static final String MIN_ITEMS_CONSTRAINT = "min.items";
		public static final String MIN_LENGTH_VALIDATION = "min.length.validation";
		public static final String MAX_LENGTH_VALIDATION = "max.length.validation";
		public static final String PATTERN_VALIDATION = "pattern.validation";
		public static final String FIELD_MISSING = "field.missing";
		public static final String INVALID_EMAIL = "invalid.email.format";
		public static final String SESSION_INVALID = "session.invalid";
		public static final String INVALID_PASSWORD = "password.invalid";

		public static final String AUTOWALA_DOESNT_EXIST = "autowala.doesnt.exist";
		public static final String FARE_TYPE_DOESNT_EXIST = "fare.type.doesnt.exist";
		public static final String VEHICLE_OR_PHONE_ALREADY_EXIST = "vehicle.or.phone.already.exist";
		public static final String VEHICLE_NUMBER_ALREADY_EXIST = "vehicle.number.already.exist";
	}

	public class PushNotificationMessages {

	}

	public class AutowalaStatus {
		public static final int AVAILABLE = 1;
		public static final int NOT_AVAILABLE = 2;
	}

	public static final List<Integer> FARE_TYPES = Arrays.asList(FareTypes.METER, FareTypes.NEGOTIATE,
			FareTypes.METER_X_HALF, FareTypes.METER_X_2, FareTypes.METER_ADD);

	public class FareTypes {
		public static final int METER = 1;
		public static final int NEGOTIATE = 2;
		public static final int METER_X_HALF = 3;
		public static final int METER_ADD = 4;
		public static final int METER_X_2 = 5;
	}

}
