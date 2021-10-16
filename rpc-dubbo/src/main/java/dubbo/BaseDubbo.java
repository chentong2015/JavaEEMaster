package dubbo;

// Dubbo特点:
// 1. 基于代理的远程调用能力，服务以接口为粒度
// 2. 负载均衡策略: 使用注册中心，服务自动注册与发现
// 3. TODO: dubbo://协议基于netty来实现的
// 4. 可视化的服务治理与运维: 监控
public class BaseDubbo {

    // 手写Dubbo的基本模块:
    // 1. Provider模块:
    //    提供API，实现API，暴露(启动tomcat, nettyServer)
    //    注册中心注册服务，本地注册服务
    // 2. Consumer模块: 通过接口名称从注册中心获取服务，然后调用服务
    // 3. Registry模块: 保存服务配置信息(服务名: List<URL>)
    // 4. RcpProtocol模块：基于Tomcat的HttpProtocol，基于Netty的DubboProtocol ==> 可扩展协议
    // 5. Framework模块: 框架的实现

    // https://dubbo.apache.org/zh/
    // 分布式框架 https://github.com/apache/dubbo
    // Dubbo实现原理与源码解析系列 https://www.iocoder.cn/Dubbo/good-collection/?side
    // Dubbo的底层实现原理和机制？
    // Dubbo的服务请求失败怎么处理？
}
