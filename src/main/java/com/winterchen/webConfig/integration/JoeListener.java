package com.winterchen.webConfig.integration;

/**
 * Created by zy on 2019/10/3.
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* ClassName:JoeListener
* @author   zy
* @version
* @since    JDK 1.8
*/
@WebListener
public class JoeListener implements ServletContextListener{

    private static Logger logger = LoggerFactory.getLogger(JoeListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("--Joe-监听器-ServletContext 初始化");
        logger.info(sce.getServletContext().getServerInfo());
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("--Joe-监听器-ServletContext 销毁");
    }
}
