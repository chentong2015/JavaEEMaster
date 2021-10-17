package tomcat.config;

import org.apache.catalina.*;
import org.apache.catalina.connector.Connector;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.core.StandardEngine;
import org.apache.catalina.core.StandardHost;
import org.apache.catalina.startup.Tomcat;

public class TomcatStarter {

    private static String hostname = "localhost";
    private static int port = 8081;

    // 使用纯程序启动Tomcat，替代XML配置结构
    public static void startTomcat() {
        Tomcat tomcat = new Tomcat();
        tomcat.getHost().setAutoDeploy(false);

        // getServer(): 创建一个Standard Server，然后创建Service并添加到Server中
        Server server = tomcat.getServer();
        Service service = server.findService("Tomcat");
        Connector connector = new Connector();
        connector.setPort(port);
        service.addConnector(connector);

        String contextPath = "";
        Context context = new StandardContext();
        context.setPath(contextPath);
        // TODO: 这里不能使用基于Xml配置的Listener
        // context.addLifecycleListener(new Tomcat.DefaultWebXmlListener());
        context.addLifecycleListener(new Tomcat.FixContextListener());

        Host host = new StandardHost();
        // 这里必须设置host名称
        host.setName(hostname);
        host.addChild(context);

        Engine engine = new StandardEngine();
        engine.setDefaultHost(hostname);
        engine.addChild(host);
        service.setContainer(engine);

        // 这里需要添加Servlet用于分发请求 ==> Spring Web使用DispatcherServlet
        // tomcat.addServlet(contextPath, "dispatcher", new MyDispatcherServlet());
        // 配置映射关系，所有请求需要走的Servlet
        context.addServletMappingDecoded("/*", "dispatcher");

        try {
            tomcat.start();
            System.out.println("tomcat started at " + hostname + ":" + port);
            tomcat.getServer().await();
        } catch (LifecycleException ex) {
            ex.printStackTrace();
        }
    }
}
