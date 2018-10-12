package com.rikshawala.dtos.request;

import java.util.List;

public class AutoFeedsRequestDTO {

	public String searchText;

	public Double latitude;

	public Double longitude;

	/* In Meters */
	public Long distance;

	public List<Integer> fareTypes;
}
