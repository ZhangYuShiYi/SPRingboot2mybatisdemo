package com.winterchen.easemob.dto;

import org.codehaus.jackson.annotate.JsonProperty;

public class EasemobToken {
	
	@JsonProperty("access_token")
	private String token;
	
	@JsonProperty("expires_in")
	private Integer expiresIn;
	
	private String application;

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Integer getExpiresIn() {
		return expiresIn;
	}

	public void setExpiresIn(Integer expiresIn) {
		this.expiresIn = expiresIn;
	}

	public String getApplication() {
		return application;
	}

	public void setApplication(String application) {
		this.application = application;
	}

}
