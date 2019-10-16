package com.winterchen.interceptor;

import com.winterchen.annotation.FileAccessToken;
import com.winterchen.annotation.PassToken;
import com.winterchen.util.RedisUtil;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

@Component
public class FileFolderInterceptor implements HandlerInterceptor{

    @Resource
    RedisUtil redisUtil;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 从 http 请求头中取出 token
        String token = request.getHeader("token");
        // 如果不是映射到方法直接通过
        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        // 检查是否有passToken注释，有则跳过认证
        if (method.isAnnotationPresent(PassToken.class)) {
            PassToken passToken = method.getAnnotation(PassToken.class);
            if (passToken.required()) {
                return true;
            }
        }
        // 检查有没有需要用户权限的注解
        if (method.isAnnotationPresent(FileAccessToken.class)) {
            FileAccessToken accessToken = method.getAnnotation(FileAccessToken.class);
            if (accessToken.required()) {
                // 执行认证
                /*if (token == null) {
                    throw new BusinessException("400", "非法请求");
                }
                Claims claims = JwtUtil.parseJWT(token);
                if(claims == null) {
                    throw new BusinessException("401", "请重新登录");
                }
                if (claims.getExpiration().getTime() < new Date().getTime()) { // Token超时
                    throw new BusinessException("401", "token过期，请重新登录");
                }
                String id = (String) claims.get("id");
                //判断用户基本信息是否更新
                if(redisUtil.hasKey(id)) {
                    Integer value = (Integer) redisUtil.get(id);
                    if(value != 1) {
                        throw new BusinessException("401", "用户信息更新，请重新登录");
                    }
                }else {
                    throw new BusinessException("401", "token过期，请重新登录");
                }
                //查询用户状态
                Integer status = userService.findUserStatusByUserId(id);
                if(status != null) {
                    if(status == 1) {
                        throw new BusinessException("400", "你的账号已经被禁用，请联系管理员");
                    }else if(status == 2) {
                        throw new BusinessException("400", "你的账号已经被删除，请联系管理员");
                    }
                }
                //数据权限校验
                String requestUrl = request.getRequestURI();
                //获取用户角色ID
                List<Map<String, Object>> mapList=  (List<Map<String, Object>>) claims.get("role_names");
                //如果是系统管理员
                Integer role_id = (Integer) mapList.get(0).get("id");
                if(role_id == 1) {
                    return true;
                }
                Integer menuId = menuService.findMenuIdByRoleIdAndUrlPath(role_id, requestUrl);
                if(menuId == null) {
                    throw new BusinessException("403", "权限不足");
                }*/
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {

    }
}
