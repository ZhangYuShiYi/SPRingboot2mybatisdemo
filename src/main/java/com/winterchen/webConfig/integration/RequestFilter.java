package com.winterchen.webConfig.integration;


import com.auth0.jwt.JWT;
import com.winterchen.service.user.UserService;
import com.winterchen.util.JwtUtil;
import org.apache.shiro.authc.AuthenticationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

/**
 * 
 * @author 调用shiro之前用过滤器实现api接口权限控制，维护一张角色与api接口关系表即可：sys_role_api
 *
 */
@WebFilter
public class RequestFilter implements Filter {

	private Logger logger = LoggerFactory.getLogger(RequestFilter.class);

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		// TODO Auto-generated method stub
		logger.info("--初始化RequestFilter!");
	}

	/*@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		*//*String token = httpServletRequest.getHeader("token");
		String userId = JWT.decode(token).getAudience().get(0);*//*
		//1、获取请求里的参数
        String userId = httpServletRequest.getParameter("userId");
		//2、获取请求里的请求路径
        String requestURI = httpServletRequest.getRequestURI();
        if (null != userId && !userId.equals("") && requestURI.indexOf("user_model/getUserType") == -1
                && requestURI.indexOf("user_model/addUser") == -1
                && requestURI.indexOf("user_model/submitHnUserinfo") == -1) {
            logger.info(requestURI);
            WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
			//3、获取service层
			UserService userService = context.getBean(UserService.class);
            String hnID = null;//userService.getHNID(Long.valueOf(userId));
             if(hnID != null) {
            	 httpServletRequest = replaceMemberIdWithJYId(httpServletRequest, hnID);
             } else {
            	 logger.error("没有找到对应user.id,可能没同步" + userId);
             }
        }
        chain.doFilter(request, response);
	}*/

	//调用shiro之前用过滤器实现api接口权限控制，维护一张角色与api接口关系表即可：sys_role_api
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		//2、获取请求里的请求路径
		String requestURI = httpServletRequest.getRequestURI();
		if(requestURI.indexOf("/user/login") == -1 && requestURI.indexOf("/swagger-ui.html") == -1){
			//1、获取请求里的参数
			String token = httpServletRequest.getHeader("token");
			/*String userId = JWT.decode(token).getAudience().get(0);*/   //第一种方式存进token
			String userName = JwtUtil.getUsername(token);                 //第二种方式存进token
			if (userName == null) {
				throw new AuthenticationException("token非法无效!");
			}
			if (null != userName && !userName.equals("")) {
				logger.info(requestURI);
				WebApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(request.getServletContext());
				//3、获取service层
				UserService userService = context.getBean(UserService.class);
				//4、获取该用户的api接口访问路径的集合，与requestURI相比较
				/*SysUser user = userService.getUserByName(userName);*/
				LinkedList<String> userApiList = userService.getUserApiList(userName);
				boolean flag = userApiList.contains(requestURI);
				if(flag){
				}else {
					logger.error("用户没有api接口权限");
					throw new ServletException("用户没有api接口权限");
				}

				HttpSession session = httpServletRequest.getSession();
				boolean res = (boolean)session.getAttribute("isLogin");
				if(!res){
					logger.error("session中isLogin为false");
					throw new ServletException("用户没有权限");
				}
			}
		}
		chain.doFilter(request, response);
	}

	
	/**
	 * 转换玄乐ID为营养师ID，因为前后台的ID类型不一样，前台传来的是玄乐ID，后台使用的是营养师ID
	 * @param yysId
	 */
	private HttpServletRequest replaceMemberIdWithJYId(HttpServletRequest request, String yysId)
	{
		Map<String, String[]> map = new HashMap<String, String[]>(request.getParameterMap());
		if(map.get("userId") != null)
		{
			map.put("userId", new String[] {yysId});
		}
		/*request = new ParamRequestWrapper(request, map);*/
		return request;
	}

	@Override
	public void destroy() {
		// TODO Auto-generated method stub
		
	}

}
