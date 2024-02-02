package dubbo.demo.framework.protocol;

import dubbo.demo.framework.data_model.Invocation;
import dubbo.demo.framework.data_model.URL;

// 所有协议的一个抽象接口，包含两个基本的操作
public interface Protocol {

    // 启动这个协议所对应的Server
    void startServer(URL url);

    // 客户端使用指定的协议调用远端指定的服务(通过Invocation封装信息)
    String sendInvocation(URL url, Invocation invocation);
}
