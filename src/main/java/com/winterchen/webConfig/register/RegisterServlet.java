package com.winterchen.webConfig.register;

/**
 * Created by zy on 2019/10/3.
 */
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
* ClassName:XbqServlet
* 通过 代码注册Servlet
* @author   zy
* @version
* @since    JDK 1.8
*/
public class RegisterServlet extends HttpServlet{

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(RegisterServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("--register--servlet doGet");
        this.doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("--register--servlet doPost");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Hello World</title>");
        out.println("</head>");
        out.println("<body><center>");
        out.println("<h3>我是通过代码注册Servlet</h3>");
        out.println("</center></body>");
        out.println("</html>");
    }
}
