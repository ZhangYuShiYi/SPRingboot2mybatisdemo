/**
 * Created by Jellyleo on 2019年12月16日
 * Copyright © 2019 jellyleo.com 
 * All rights reserved. 
 */
package com.winterchen.activiti.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 功能描述:测试控制器
 *
 * @author Jelly
 * @created 2019年11月19日
 * @version 1.0.0
 */
@Controller
public class TestController extends BaseController {

	/**
	 * 
	 * 功能描述:测试
	 *
	 * @param request
	 * @param response
	 * @see [相关类/方法](可选)
	 * @since [产品/模块版本](可选)
	 */
	@RequestMapping(value = "/test")
	@ResponseBody
	public String test(HttpServletRequest request, HttpServletResponse response) {

		return "success";
	}
}
