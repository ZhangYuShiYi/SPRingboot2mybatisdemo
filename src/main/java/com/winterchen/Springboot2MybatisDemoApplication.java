package com.winterchen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class })  //禁用数据源的自动配置类DataSourceAutoConfiguration
@MapperScan("com.winterchen.dao")  //开启mybatis
@EnableSwagger2  //开启swagger
@EnableCaching//启动类启动时会去启动缓存启动器 @Cacheable  @CacheEvict
@ServletComponentScan //Servlet、Filter、Listener 可以直接通过 @WebServlet(urlPatterns = "/test/*")、@WebFilter、@WebListener 注解自动注册
@EnableScheduling  //开启定时任务
public class Springboot2MybatisDemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(Springboot2MybatisDemoApplication.class, args);
	}

}
