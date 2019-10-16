package com.winterchen.webConfig.register;

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
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* ClassName:JoeFilter
* 自定义 Serlvlet 的过滤器
* @author   zy
* @version
* @since    JDK 1.8
*/
public class RegisterFilter implements Filter {

    private static Logger logger = LoggerFactory.getLogger(RegisterFilter.class);

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        logger.info("--register--初始化JoeFilter!");
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest req = (HttpServletRequest)request;
        logger.info(req.getRequestURL() + "---register---> doFilter ");
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        logger.info("--register--销毁JoeFilter!");
    }

}
