package dubbo.demo.provider;

import dubbo.demo.framework.URL;
import dubbo.demo.protocol.http.HttpServer;
import dubbo.demo.provider.local.LocalRegister;
import dubbo.demo.provider.remote.RemoteRegister;
import dubbo.demo.provider.services.MyService;
import dubbo.demo.provider.services.MyServiceImpl;

public class Provider {

    public static void main(String[] args) {
        // 1. 本地注册 {服务名: 实现类}
        LocalRegister.register(MyService.class.getName(), MyServiceImpl.class);

        // 2. 远程注册中心注册 {服务名: List<URL>}
        URL url = new URL("localhost", 8080); // 这里使用本机地址
        RemoteRegister.register(MyService.class.getName(), url);

        // 3. 启动Tomcat
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8080);
    }
}
