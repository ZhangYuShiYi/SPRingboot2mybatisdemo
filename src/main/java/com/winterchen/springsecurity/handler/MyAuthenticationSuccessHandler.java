package com.winterchen.springsecurity.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by zy on 2020/2/10.
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private RequestCache requestCache = new HttpSessionRequestCache();
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        // 因为deleteCookies时候指定的path是这样的。所以这里我们的cookie也要加这个path，
        // 否则会删除失败
        String cookiePath = request.getContextPath() + "/";
        Cookie cookie = new Cookie("testCookie", "cookie");
        cookie.setMaxAge(60);
        cookie.setPath(cookiePath);
        Cookie cookie2 = new Cookie("testCookie2", "cookie2");
        cookie2.setMaxAge(60);
        cookie2.setPath(cookiePath);
        response.addCookie(cookie);
        response.addCookie(cookie2);
        SavedRequest savedRequest = requestCache.getRequest(request, response);
        // 当直接进入的登陆页面，跳转到hello.html。
        if (savedRequest == null) {
            redirectStrategy.sendRedirect(request, response, "/hello");
            return;
        }

        String targetUrl = savedRequest.getRedirectUrl();
        // 当访问其他路径被拦截到登陆页面，跳转到当时的页面。
        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

}
