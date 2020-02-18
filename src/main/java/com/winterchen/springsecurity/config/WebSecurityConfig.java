package com.winterchen.springsecurity.config;

import com.winterchen.springsecurity.handler.MyAuthenticationSuccessHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

/**
 * Created by zy on 2020/2/10.
 */
/*@Configuration
@EnableWebSecurity*/
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    protected void configure(HttpSecurity http) throws Exception {
        /*http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll()
                .and()
                .logout()
                .permitAll();*/
        http
                .authorizeRequests()
                .antMatchers("/", "/index").permitAll()
                .antMatchers("/static/**", "/common/**","/login/**","/user/**","/schedule/**").permitAll()
                .antMatchers("/admin/**").hasRole("ADMIN")
                .antMatchers("/user/**").access("hasRole('USER') and hasRole('DBA')")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .successHandler(new MyAuthenticationSuccessHandler())
                .permitAll()
                .and()
                .logout()
//                .logoutUrl("/my/logout")
//                .logoutSuccessUrl("/my/index")
//                .logoutSuccessHandler(null)
                .invalidateHttpSession(true)
//                .addLogoutHandler(null)
                .deleteCookies("testCookie", "testCookie2")
                .permitAll();
    }

    @Bean
    @Override
    public UserDetailsService userDetailsService() {
        /*UserDetails user =
                User.withDefaultPasswordEncoder()
                        .username("user")
                        .password("password")
                        .roles("USER")
                        .build();
        return new InMemoryUserDetailsManager(user);*/
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withDefaultPasswordEncoder().username("user")
                .password("user").roles("USER").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("admin")
                .password("admin").roles("ADMIN").build());
        manager.createUser(User.withDefaultPasswordEncoder().username("dba")
                .password("dba").roles("DBA","USER").build());
        return manager;   //创建了三个用户，
    }

}
