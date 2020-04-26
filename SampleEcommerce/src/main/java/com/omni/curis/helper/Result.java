package com.omni.curis.helper;

import java.io.Serializable;

public class Result implements Serializable{
	
	public int status;
	public String message;
	public String email;
	public String jwstoken;
	
	
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getJwstoken() {
		return jwstoken;
	}
	public void setJwstoken(String jwstoken) {
		this.jwstoken = jwstoken;
	}
	
	
	
	

}
