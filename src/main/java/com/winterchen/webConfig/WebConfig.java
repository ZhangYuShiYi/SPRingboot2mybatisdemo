package com.winterchen.webConfig;

/**
 * Created by zy on 2019/10/3.
 */
import java.util.ArrayList;
import java.util.EventListener;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.winterchen.webConfig.register.RegisterFilter;
import com.winterchen.webConfig.register.RegisterListener;
import com.winterchen.webConfig.register.RegisterServlet;
/**
 * ClassName:WebConfig
 * 通过 @bean 注入 servlet、filter、listener
*/

@Configuration
public class WebConfig {
/**
   * servletRegistrationBean:(使用代码注册Servlet（不需要@ServletComponentScan注解）).
   * @author zy
   * @return
*/

private static Logger logger = LoggerFactory.getLogger(WebConfig.class);

    /**
     * getDemoFilter:(使用代码注册过滤器).
     * @author zy
     * @return
     */
    /*@Bean
    public FilterRegistrationBean getDemoFilter(){
        logger.info("--webconfig--通过@bean注入filter");
        RegisterFilter demoFilter = new RegisterFilter();
        FilterRegistrationBean registrationBean=new FilterRegistrationBean();
        registrationBean.setFilter(demoFilter);
        List<String> urlPatterns=new ArrayList<String>();
        urlPatterns.add("/user*//*");                            //拦截路径，可以添加多个
        registrationBean.setUrlPatterns(urlPatterns);
        registrationBean.setOrder(1);
        return registrationBean;
    }*/

    /**
     * getDemoListener:(使用代码 引用 监听器).
     * @author zy
     * @return
     */
    /*@Bean
    public ServletListenerRegistrationBean<EventListener> getDemoListener(){
        logger.info("--webconfig--通过@bean注入listener");
        ServletListenerRegistrationBean<EventListener> registrationBean = new ServletListenerRegistrationBean<>();
        registrationBean.setListener(new RegisterListener());
        registrationBean.setOrder(1);
        return registrationBean;
    }*/

    /**
     * servletRegistrationBean:(使用代码注冊servlet).
     * @author zy
     * @return
     */
    /*@Bean
    public ServletRegistrationBean servletRegistrationBean(){
        logger.info("--webconfig--通过@bean注入servlet");
        ServletRegistrationBean registrationBean = new ServletRegistrationBean();
        registrationBean.setServlet(new RegisterServlet());
        List<String> urlMappings = new ArrayList<String>();
        // 访问，可以添加多个
        urlMappings.add("/register/servlet");
        registrationBean.setUrlMappings(urlMappings);
        registrationBean.setLoadOnStartup(1);
        return registrationBean;
    }*/


}
