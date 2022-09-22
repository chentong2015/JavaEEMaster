package tomcat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

// Tomcat 7.0版本: 支持BIO, NIO
// Tomcat 8.0版本: 支持NIO
// Tomcat 9.0版本(及之前): 支持Java EE, 实现javax.servlet接口
// Tomcat 10.0版本(及之后): 支持JakartaEE，实现jakarta.servlet.Servlet接口

// Tomcat是一个Servlet容器
//   Servlet(服务端应用程序) = server+applet(java开发的应用程序，运行在浏览器端，客户端)
//   Servlet是一个规范: 自定义实现了HttpServlet的类型(最终实现的接口Servlet Interface)
//   Servlet是一个小程序，wrapper -> 应用上下文Context -> 部署在host主机 -> 使用Engine来管理主机
//   TODO: Wrapper的理解
//   public class MyServlet extends HttpServlet implements SingleThreadModel {}
//   一般自定义实现了HttpServlet类型的类，在部署的时候，所有的请求都是公用同一个Servlet
//   如果实现了SingleThreadModel接口，则表示每一个请求的线程都独立的使用一个Servlet实例
//   使用Wrapper来包装所有的Servlet实例

// Tomcat作为Http Server，默认端口号8080
// Tomcat启动方式: cmd>apache-tomcat-8.5.65\bin\startup.bat
public class TomcatBasic extends HttpServlet {
    
    // TODO: Tomcat处理请求(分发请求)逻辑
    // 直接调用自定义servlet.service()，HttpServlet所实现的方法
    // > 确定接收请求的类型
    // > 调用doGet()还是doPost()
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
