package com.winterchen.easemob.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="easemob")
public class EasemobConfig {
	
	private String host;

	private String orgName;
	
	private String appName;
	
	private String grantType;
	
	private String clientId;
	
	private String clientSecret;
	
	public String getHost() {
		return host;
	}

	public void setHost(String host) {
		this.host = host;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getAppName() {
		return appName;
	}

	public void setAppName(String appName) {
		this.appName = appName;
	}

	public String getGrantType() {
		return grantType;
	}

	public void setGrantType(String grantType) {
		this.grantType = grantType;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}
	
	/**
	 * 获取token rest url
	 * @return
	 */
	public String getTokenUrl() {
		return this.host + this.orgName + "/" + this.appName + "/token";
	}
	
	/**
	 * 用户操作rest url
	 * @return
	 */
	public String getOpeUsersUrl() {
		return this.host + this.orgName + "/" + this.appName + "/users";
	}
	
	/**
	 * 发送消息rest url
	 * @return
	 */
	public String getSendMsgUrl() {
		return this.host + this.orgName + "/" + this.appName + "/messages";
	}
	
	/**
	 * 聊天群组rest url
	 * @return
	 */
	public String getChatGrpUrl() {
		return this.host + this.orgName + "/" + this.appName + "/chatgroups";
	}
	
	public String getPwdUrl() {
		return this.host + this.orgName + "/" + this.appName + "/users/{userName}/password";
	}
	
	/**
	 * 加入聊天群
	 * @return
	 */
	public String getJoinGroupUrl() {
		return this.host + this.orgName + "/" + this.appName + "/chatgroups/{groupId}/users/{userName}";
	}
	
	public String getRemoveMemberFromGroupUrl() {
		return this.host + this.orgName + "/" + this.appName + "/chatgroups/{groupId}/users/{userName}";
	}
	
	/**
	 * 删除群组
	 * @return
	 */
	public String getDeleteGroupUrl() {
		return this.host + this.orgName + "/" + this.appName + "/chatgroups/{groupId}";
	}
	
}