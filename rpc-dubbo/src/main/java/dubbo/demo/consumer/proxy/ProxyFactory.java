package dubbo.demo.consumer.proxy;

import dubbo.demo.protocol.base.ProtocolClient;
import dubbo.demo.protocol.dubbo.NettyClient;
import dubbo.demo.protocol.http.HttpClient;

import java.lang.reflect.Proxy;

// 创建基于不同协议的代理对象(及InvocationHandler) ==> 需要基于协议来扩展 !!
public class ProxyFactory {

    // 使用JDK实现的动态代理：对某一个接口生成一个代理类
    public static <T> T getProxyWithHttpProtocol(Class interfaceClass) {
        HttpClient httpClient = new HttpClient();
        return createNewProxy(interfaceClass, httpClient);
    }

    public static <T> T getProxyWithDubboProtocol(Class interfaceClass) {
        NettyClient nettyClient = new NettyClient();
        return createNewProxy(interfaceClass, nettyClient);
    }

    private static <T> T createNewProxy(Class interfaceClass, ProtocolClient client) {
        MyInvocationHandler invocationHandler = new MyInvocationHandler(interfaceClass, client);
        Object result = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, invocationHandler);
        return (T) result;
    }
}
