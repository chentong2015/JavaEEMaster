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

// Tomcat是一个Servlet容器  ==>  默认端口号8080 http://localhost:8080/
//   Servlet(服务端应用程序) = server+applet(java开发的应用程序，运行在浏览器端，客户端)
//   Servlet是一个规范: 自定义实现了HttpServlet的类型(最终实现的接口Servlet Interface)
//   Servlet是一个小程序，wrapper -> 应用上下文Context -> 部署在host主机 -> 使用Engine来管理主机

// TODO: Wrapper的理解
// public class MyServlet extends HttpServlet implements SingleThreadModel {}
//   一般自定义实现了HttpServlet类型的类，在部署的时候，所有的请求都是公用同一个Servlet
//   如果实现了SingleThreadModel接口，则表示每一个请求的线程都独立的使用一个Servlet实例
//   使用Wrapper来包装所有的Servlet实例
public class TomcatBasic extends HttpServlet {

    // Tomcat部署应用的四种方式：
    // 1. 将项目build成war包，放置到tomcat/webapps/下 ==> 解压出来之后和第二种部署方式相同
    // 2. 将项目build成war exploded包，将文件夹放置到tomcat/webapps/下
    // 3. 通过在tomcat配置文件server.xml中添加主机结点来实现
    //    <Host name="" appBase="webs">
    //       <Context path="" docBase="" />
    //    </Host>
    // 4. 在apache-tomcat-9.0.41\conf\Catalina\localhost目录下添加文件 ServletDemoProject.xml
    //    文件中配置<Context path="/ServletDemoProject" docBase="指定build出来的文件全路径" />

    // TODO: War包和jar包的区别 ==> 本质上都存储了共同的信息(编译后的class文件和web.xml)
    // jar一般表示的是依赖的包，在tomcat启动时需要做出额外的判断
    // 使用war包的形式部署，方便tomcat识别和处理(当成是web引用)
    // Tomcat规范:
    //   ProjectName
    //     -- WEB-INF
    //        -- classes
    //        web.xml

    // Tomcat处理请求(分发请求)逻辑:
    // Tomcat > ApplicationFilterChain.java > 直接调用自定义servlet.service()
    // service()方法是HttpServlet所实现的方法  > 确定接收请求的类型 > 调用doGet()还是doPost()
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
