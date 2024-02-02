package dubbo.demo;

public class MyDubboFramework {

    // 手写Dubbo的基本模块
    // 1. Consumer模块: 通过接口名称从注册中心获取服务，然后调用服务
    // 2. Registry模块: 保存服务配置信息
    //    2.1 本地注册: 服务名: 服务的实现
    //    2.2 远端注册: 服务名: List<URL>
    // 3. Provider模块:
    //    1.1 提供API，实现API，暴露(启动内嵌的tomcat, nettyServer)
    //    1.2 注册中心注册服务，本地注册服务
    // 4. RcpProtocol模块：
    //    4.1 基于Tomcat的HttpProtocol => 适用于长请求
    //    4.2 基于Netty的DubboProtocol => 使用于并发量比较高的短请求
}
