package com.potatospy.photoapp.api.gateway.model;

import java.util.Date;



public class ErrorMessageResponse {

	private Date timestamp;
	private String message;
	
	
	
	public ErrorMessageResponse() {}
	
	
	public ErrorMessageResponse(Date timestamp, String message) {
		this.timestamp = timestamp;
		this.message = message;
	}
	
	
	public Date getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	

}

