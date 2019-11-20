package com.winterchen.util;

/**
 * @author: huangxutao
 * @date: 2019-06-14
 * @description: 缓存常量
 */
public interface CacheConstant {

	/**
	 * 字典信息缓存
	 */
    public static final String DICT_CACHE = "dictCache";

	/**
	 * 权限信息缓存
	 */
    public static final String PERMISSION_CACHE = "permission";

	/**
	 * 登录用户规则缓存
	 */
    public static final String LOGIN_USER_RULES_CACHE = "loginUser_cacheRules";

	/**
	 * 部门信息缓存
	 */
	public static final String DOMAIN_INFO_CACHE = "domain_cache";


	/**
	 * 部门id信息缓存
	 */
	public static final String DEPART_IDMODEL_CACHE = "departCache_idmodel";

	String CACHE_KEY = "JOYZONE:CACHE_KEY:";

	String CACHE_KEY_CODE = CACHE_KEY + "CODE";

	String EASEMOB_GRANT_TYPE = "grant_type";

	String EASEMOB_CLIENT_ID = "client_id";

	String EASEMOB_CLIENT_SECRET = "client_secret";

	String EASEMOB_TOKEN = "easemob_token";

	String EASEMOB_HEADER_AUTH = "Authorization";

	Integer PARAM_GROUP_MAXUSERS = 400;

	Integer CACHE_CODE_EXPIRES = 5 * 60 * 1000;

}
