package com.rikshawala.models;

import java.util.Date;
import java.util.List;

import org.mongodb.morphia.annotations.Id;

public class Autowala {

	@Id
	private String id;

	private String autoId;

	private String name;

	private String phoneNo;

	private String password;

	private String vehicleNo;

	private String description;

	private int fareType;

	private int status;

	private List<Double> location;

	private boolean isAutoVerified = false;

	private boolean isBlocked = false;

	private Date createdTime;

	private Date updatedTime;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getAutoId() {
		return autoId;
	}

	public void setAutoId(String autoId) {
		this.autoId = autoId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPhoneNo() {
		return phoneNo;
	}

	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getVehicleNo() {
		return vehicleNo;
	}

	public void setVehicleNo(String vehicleNo) {
		this.vehicleNo = vehicleNo;
	}

	public boolean isAutoVerified() {
		return isAutoVerified;
	}

	public void setAutoVerified(boolean isAutoVerified) {
		this.isAutoVerified = isAutoVerified;
	}

	public Date getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Date createdTime) {
		this.createdTime = createdTime;
	}

	public boolean isBlocked() {
		return isBlocked;
	}

	public void setBlocked(boolean isBlocked) {
		this.isBlocked = isBlocked;
	}

	public Date getUpdatedTime() {
		return updatedTime;
	}

	public void setUpdatedTime(Date updatedTime) {
		this.updatedTime = updatedTime;
	}

	public int getFareType() {
		return fareType;
	}

	public void setFareType(int fareType) {
		this.fareType = fareType;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public List<Double> getLocation() {
		return location;
	}

	public void setLocation(List<Double> location) {
		this.location = location;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
