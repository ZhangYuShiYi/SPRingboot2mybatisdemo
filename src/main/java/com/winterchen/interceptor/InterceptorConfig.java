package com.winterchen.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class InterceptorConfig implements WebMvcConfigurer{

    private static Logger logger = LoggerFactory.getLogger(InterceptorConfig.class);

    @Bean
    public FileFolderInterceptor fileFolderInterceptor() {
        logger.info("--实例化FileFolderInterceptor拦截器--");
        return new FileFolderInterceptor();
    }

    @Bean
    public AuthenticationInterceptor authenticationInterceptor() {
        logger.info("--实例化authenticationInterceptor拦截器--");
        return new AuthenticationInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        logger.info("--将具体拦截器添加到容器中--");
        registry.addInterceptor(fileFolderInterceptor())
                .addPathPatterns("/**");

        registry.addInterceptor(authenticationInterceptor())
                .addPathPatterns("/**");
    }


}
