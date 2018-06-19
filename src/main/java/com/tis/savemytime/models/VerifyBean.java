package com.tis.savemytime.models;

import java.io.Serializable;

public class VerifyBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2948625278942600213L;
	
	private String userId;
	private String accessToken;
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getAccessToken() {
		return accessToken;
	}
	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}
	
}
