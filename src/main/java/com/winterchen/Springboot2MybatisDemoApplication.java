package com.winterchen;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication(exclude = { DataSourceAutoConfiguration.class,
		org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration.class})
@MapperScan({"com.winterchen.dao","com.winterchen.easemob.bussiness.mapper"})  //开启mybatis
@EnableSwagger2  //开启swagger
@EnableCaching//启动类启动时会去启动缓存启动器 @Cacheable  @CacheEvict
@ServletComponentScan //Servlet、Filter、Listener 可以直接通过 @WebServlet(urlPatterns = "/test/*")、@WebFilter、@WebListener 注解自动注册
@EnableScheduling  //开启定时任务
public class Springboot2MybatisDemoApplication /*extends SpringBootServletInitializer*/ {

	/*@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		// 注意这里要指向原先用main方法执行的Application启动类  打war包时：需继承并重写该方法
		return builder.sources(Springboot2MybatisDemoApplication.class);
	}*/

	public static void main(String[] args) {
		SpringApplication.run(Springboot2MybatisDemoApplication.class, args);
	}

}
