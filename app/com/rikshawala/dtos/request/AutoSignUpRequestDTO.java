package com.rikshawala.dtos.request;

import play.data.validation.Constraints;

public class AutoSignUpRequestDTO {

	@Constraints.Required
	@Constraints.MinLength(2)
	@Constraints.MaxLength(50)
	public String name;

	@Constraints.Required
	@Constraints.MinLength(10)
	@Constraints.MaxLength(10)
	@Constraints.Pattern("^[0-9]+$")
	public String phoneNo;

	@Constraints.Required
	@Constraints.MinLength(6)
	@Constraints.MaxLength(16)
	public String password;

	@Constraints.Required
	@Constraints.MinLength(5)
	@Constraints.MaxLength(10)
	@Constraints.Pattern("^[A-Z]{2}[ -][0-9]{1,2}(?: [A-Z])?(?: [A-Z]*)? [0-9]{1,4}$")
	public String vehicleNo;

	@Constraints.Required
	public String deviceId;

	@Constraints.Required
	public Integer deviceTypeId;

	public String deviceToken;

	@Constraints.Required
	public Double latitude;

	@Constraints.Required
	public Double longitude;
}
