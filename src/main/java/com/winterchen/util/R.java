package com.winterchen.util;


import org.apache.http.HttpStatus;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 返回数据
 *
 * @author Louis.he
 */
public class R extends HashMap<String, Object> {
	private static final long serialVersionUID = 1L;

	public static final int STATUS_OK = 0; 		//0 成功
	public static final int STATUS_FAIL = 1; 	//1 失败


	
	public R() {
		put("code", R.STATUS_OK);
		put("msg", "success");
	}
	
	public static R error() {
		return error(HttpStatus.SC_INTERNAL_SERVER_ERROR, "未知异常，请联系管理员");
	}
	
	public static R error(String msg) {
		return error(STATUS_FAIL, msg);
	}
	
	public static R error(int code, String msg) {
		R r = new R();
		r.put("code", code);
		r.put("msg", msg);
		return r;
	}

	public static R ok(String msg) {
		R r = new R();
		r.put("code", STATUS_OK);
		r.put("msg", msg);
		return r;
	}
	public static R ok(Object obj) {
		R r = new R();
		r.put("data",obj);
		return r;
	}
	
	public static R ok(Map<String, Object> map) {
		R r = new R();
		r.putAll(map);
		return r;
	}

	public static R pageToData(Long total,Object obj) {
		R r = new R();
		r.put("total",total);
		r.put("data",obj);
		return r;
	}
	
	public static R ok() {
		return new R();
	}

	public R put(String key, Object value) {
		super.put(key, value);
		return this;
	}
	
	private HttpServletResponse getHttpServletResponse() {
        return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
    }
}
