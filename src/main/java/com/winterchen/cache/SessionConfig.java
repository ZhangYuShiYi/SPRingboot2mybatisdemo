package com.winterchen.cache;

import org.springframework.context.annotation.Bean;
/*import org.springframework.session.web.http.DefaultCookieSerializer;*/
import org.springframework.stereotype.Component;

/**
 * Created by zy on 2019/10/18.
 */

@Component
public class SessionConfig {

    /** cooikes 设置 */
    /*@Bean
    public CookieHttpSessionStrategy cookieHttpSessionStrategy(){
        CookieHttpSessionStrategy strategy=new CookieHttpSessionStrategy();
        DefaultCookieSerializer cookieSerializer=new DefaultCookieSerializer();
        cookieSerializer.setCookieName("SESSIONID");//cookies名称
        cookieSerializer.setCookieMaxAge(1800);//过期时间(秒)
        strategy.setCookieSerializer(cookieSerializer);
        return strategy;
    }*/

}
