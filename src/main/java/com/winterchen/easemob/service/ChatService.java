package com.winterchen.easemob.service;

import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.winterchen.easemob.util.JacksonUtil;
import com.winterchen.easemob.util.PublicUtil;
import com.winterchen.easemob.util.RestTemplateUtil;
import com.winterchen.util.CacheConstant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import java.util.Map;


@Service
public class ChatService extends BaseChatService{

	private Logger LOGGER = LoggerFactory.getLogger(ChatService.class);
	
	
	
	/**
	 * 创建聊天群
	 * @param ownerId
	 * @param groupName
	 */
	public String createTeamGroup(Long ownerId, String groupName,String desc) {
		String createGroupUrl = easemob.getChatGrpUrl();
		Map<String,Object> map = Maps.newHashMap();
		map.put("groupname", groupName);
		map.put("desc", desc);
		map.put("public", true);
		map.put("maxusers", CacheConstant.PARAM_GROUP_MAXUSERS);
		map.put("approval", false);
		map.put("owner", ownerId+"");
		String jsonStr = JacksonUtil.deserializer(map);
		Map<String,String> headers = getAuthHeaders();
		try {
			String result = RestTemplateUtil.sendJson(createGroupUrl, jsonStr, headers, HttpMethod.POST);
			if(PublicUtil.isNotEmpty(result)) {
				LOGGER.info(String.format("==== %s create group %s success...", ownerId, groupName));
				JsonObject jsonObj = new JsonParser().parse(result).getAsJsonObject();
				JsonElement ele = jsonObj.get("data");
				JsonObject groupObj = ele.getAsJsonObject();
				return groupObj.get("groupid").getAsString();
			}
		} catch (Exception e) {
			LOGGER.error(String.format("==== %s create group %s failed...", ownerId, groupName), e);
		}
		return null;
	}
	
	@Async
	public void joinTeamGroup(String groupId, Long userId) {
		String joinGroupUrl = easemob.getJoinGroupUrl();
		joinGroupUrl = joinGroupUrl.replace("{groupId}", groupId);
		joinGroupUrl = joinGroupUrl.replace("{userName}", userId+"");
		Map<String,String> headers = getAuthHeaders();
		try {
			String result = RestTemplateUtil.sendJson(joinGroupUrl, null, headers, HttpMethod.POST);
			if(PublicUtil.isEmpty(result)) {
				LOGGER.error(String.format("User %d join group %d failed...", userId, groupId));
			}
		}catch(Exception e) {
			//
		}
	}
	
	public void cancelTeamGroup(String groupId, Long userId) {
		String cancelGroupUrl = easemob.getRemoveMemberFromGroupUrl();
		cancelGroupUrl = cancelGroupUrl.replace("{groupId}",groupId);
		cancelGroupUrl = cancelGroupUrl.replace("{userName}",userId+"");
		Map<String,String> headers = getAuthHeaders();
		try {
			RestTemplateUtil.sendJson(cancelGroupUrl, null, headers, HttpMethod.DELETE);
		}catch(Exception e) {
			LOGGER.error(String.format("Cancel user userId %d failed from d%", groupId, userId), e);
		}
	}
	
	public void deleteTeamGroup(String groupId) {
		String delGroupUrl = easemob.getDeleteGroupUrl();
		delGroupUrl = delGroupUrl.replace("{groupId}", groupId);
		Map<String,String> headers = getAuthHeaders();
		try {
			RestTemplateUtil.sendJson(delGroupUrl, null, headers, HttpMethod.DELETE);
		}catch(Exception e) {
			LOGGER.error(String.format("Delete group %s failed", groupId), e);
		}
	}
}