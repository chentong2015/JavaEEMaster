package tomcat;

// Tomcat 9.0版本(及之前): 支持Java EE, 实现javax.servlet接口
// Tomcat 10.0版本(及之后): 支持JakartaEE，实现jakarta.servlet.Servlet接口
// TODO: Spring (and Spring Boot) currently only support JavaEE and not JakartaEE.
public class BaseTomcat {

    // Tomcat默认端口号8080 http://localhost:8080/
    // 启动后定位位置 apache-tomcat-10.0.4\webapps\

    // Tomcat类加载器架构 <深入理解Java虚拟机 P335>
    // Tomcat详解和源码  https://www.bilibili.com/video/BV1Rf4y1y7xE?p=61
    // Tomcat处理文件上传 https://juejin.cn/post/6955841741349978143
}
