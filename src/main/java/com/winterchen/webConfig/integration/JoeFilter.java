package com.winterchen.webConfig.integration;

/**
 * Created by zy on 2019/10/3.
 */
import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;

/**
* ClassName:JoeFilter
* 自定义  Filter
* @Order注解表示执行过滤顺序，值越小，越先执行
* @author   zy
* @version
* @since    JDK 1.8
*/
@WebFilter(filterName = "joeFilter", urlPatterns = "/user/all")
public class JoeFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(JoeFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("--joe--初始化JoeFilter!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        logger.info(req.getRequestURL() + "---joe---> doFilter");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("--joe--销毁JoeFilter!");
    }
}
