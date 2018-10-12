package com.rikshawala.utils;

public class MongoException extends Exception{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	public MongoException(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return this.message;
	}
}
