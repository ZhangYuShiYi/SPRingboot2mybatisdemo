package com.winterchen.webConfig.register;

/**
 * Created by zy on 2019/10/3.
 */
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
* ClassName:JoeListener
* @author   zy
* @version
* @since    JDK 1.8
*/
public class RegisterListener implements ServletContextListener{

    private static Logger logger = LoggerFactory.getLogger(RegisterListener.class);

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        logger.info("--register-监听器-ServletContext 初始化");
        logger.info(sce.getServletContext().getServerInfo());
}

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        logger.info("--register-监听器-ServletContext 销毁");
    }
}
