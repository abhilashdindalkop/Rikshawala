package com.rikshawala.dtos.request;

import play.data.validation.Constraints;

public class AutoSignInRequestDTO {

	@Constraints.Required
	@Constraints.MinLength(10)
	@Constraints.MaxLength(10)
	@Constraints.Pattern("^[0-9]+$")
	public String phoneNo;

	@Constraints.Required
	@Constraints.MinLength(5)
	@Constraints.MaxLength(10)
	@Constraints.Pattern("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{1,4}$")
	public String vehicleNo;

	@Constraints.Required
	public String password;

	@Constraints.Required
	public String deviceId;

	@Constraints.Required
	public String deviceToken;

	@Constraints.Required
	public Double latitude;

	@Constraints.Required
	public Double longitude;

}
