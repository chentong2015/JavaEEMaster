package dubbo.demo.provider;

import dubbo.demo.framework.data_model.URL;
import dubbo.demo.framework.factory.ProtocolFactory;
import dubbo.demo.framework.protocol.Protocol;
import dubbo.demo.register.LocalRegister;
import dubbo.demo.register.RemoteRegister;

public class ServiceProvider {

    public static void main(String[] args) {
        // 1. 本地注册 {服务名: 实现类}
        LocalRegister.register(MyService.class.getName(), MyServiceImpl.class);

        // 2. 远程注册中心注册 {服务名: List<URL>}
        URL url = new URL("localhost", 8080); // 这里使用本机地址
        RemoteRegister.register(MyService.class.getName(), url);

        // 3. 启动不同协议下的Server服务提供端, 通过工厂模式动态配置
        Protocol protocol = ProtocolFactory.getProtocol();
        protocol.startServer(url);
    }
}
