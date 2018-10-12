package com.rikshawala.daos;

import com.rikshawala.dtos.request.AutoSignUpRequestDTO;
import com.rikshawala.models.AutoSession;
import com.rikshawala.models.Autowala;

public interface AutoSessionDAO {

	AutoSession findByToken(String accessToken);

	String getAutoIdByContext();

	String getAutoSessionByContext();

	AutoSession findByDeviceToken(String deviceToken);

	AutoSession create(Autowala autowala, AutoSignUpRequestDTO payload);

}
