package com.winterchen.webConfig.integration;

/**
 * Created by zy on 2019/10/3.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* ClassName:XbqServlet
* 通过  @WebServlet 注解 整合Servlet
* @author   zy
* @version
* @since    JDK 1.8
*/
/*@WebServlet(urlPatterns = "/user/add")*/
public class JoeServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(JoeServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("--joe--servlet doGet");
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("--joe--servlet doPost");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        /*PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World</title>");
        out.println("</head>");
        out.println("<body><center>");
        out.println("<h3>我是通过   @WebServlet 注解注册Servlet的</h3>");
        out.println("</center></body>");
        out.println("</html>");*/
    }
}
