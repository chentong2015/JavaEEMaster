package dubbo.demo.provider;

import dubbo.demo.protocol.http.HttpServer;

public class Provider {

    public static void main(String[] args) {
        // 1. 本地注册

        // 2. 远程注册中心注册

        // 3. 启动Tomcat
        HttpServer httpServer = new HttpServer();
        httpServer.start("localhost", 8000);
    }
}
