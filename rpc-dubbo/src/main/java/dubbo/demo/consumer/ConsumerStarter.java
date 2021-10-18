package dubbo.demo.consumer;

import dubbo.demo.consumer.proxy.ProxyFactory;
import dubbo.demo.model.Invocation;
import dubbo.demo.protocol.http.HttpClient;
import dubbo.demo.provider.services.MyService;

public class ConsumerStarter {

    public static void main(String[] args) {
        getServiceWithDubboProxy();
    }

    // 基于Http协议，通过发送指定的"调用协议(信息)"，获取到服务提供端指定服务的结果
    public static void testGetService() {
        Invocation invocation = new Invocation();
        invocation.setInterfaceName(MyService.class.getName());
        invocation.setMethodName("getServiceInfo");
        invocation.setParamTypes(new Class[]{String.class});
        invocation.setParamValues(new Object[]{"consumer infos"});
        HttpClient client = new HttpClient();
        String result = client.send("localhost", 8080, invocation);
        System.out.println(result);
    }

    // 使用动态代理，基于Http通讯协议
    public static void getServiceWithHttpProxy() {
        // 拿到实现了指定接口的代理对象
        MyService myService = ProxyFactory.getProxyWithHttpProtocol(MyService.class);
        // 通过代理对象调用方法，实际会调用到InvocationHandler.invoke()方法
        String result = myService.getServiceInfo("http Proxy");
        System.out.println(result);
    }

    // 使用动态代理，基于Dubbo通讯协议
    public static void getServiceWithDubboProxy() {
        MyService myService = ProxyFactory.getProxyWithDubboProtocol(MyService.class);
        String result = myService.getServiceInfo("dubbo Proxy");
        System.out.println(result);
    }
}
