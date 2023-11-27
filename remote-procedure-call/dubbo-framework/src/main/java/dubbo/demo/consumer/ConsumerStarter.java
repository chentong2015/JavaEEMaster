package dubbo.demo.consumer;

import dubbo.demo.framework.data_model.Invocation;
import dubbo.demo.framework.factory.ProxyFactory;
import dubbo.demo.protocol.http.HttpClient;
import dubbo.demo.provider.MyService;

public class ConsumerStarter {

    public static void main(String[] args) {
        getServiceWithProxy();
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

    // 使用动态代理，需要基于不同的协议配置，调用到远程不同的服务
    public static void getServiceWithProxy() {
        // 拿到实现了指定接口的代理对象
        MyService myService = ProxyFactory.getProxy(MyService.class);
        // 通过代理对象调用方法，实际会调用到InvocationHandler.invoke()方法
        String result = myService.getServiceInfo("message01");
        System.out.println(result);
        System.out.println(myService.getServiceInfo("message02"));
    }
}
