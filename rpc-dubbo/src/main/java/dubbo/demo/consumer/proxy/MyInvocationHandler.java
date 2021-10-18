package dubbo.demo.consumer.proxy;

import dubbo.demo.model.Invocation;
import dubbo.demo.model.URL;
import dubbo.demo.protocol.base.ProtocolClient;
import dubbo.demo.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Class interfaceClass;
    private ProtocolClient client;

    public MyInvocationHandler(Class interfaceClass, ProtocolClient client) {
        this.interfaceClass = interfaceClass;
        this.client = client;
    }

    // 1. Invocation对象的数据动态的根据调用方法的来获取
    // 2. TODO: 使用的服务提供者的地址不能写死，需要从注册中心动态取出来(做负载均衡)
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation();
        invocation.setInterfaceName(interfaceClass.getName());
        invocation.setMethodName(method.getName()); // 拿到被调用方法的名称
        invocation.setParamTypes(method.getParameterTypes()); // 被调用方法的参数列表
        invocation.setParamValues(args); // 方法所传递的实际参数
        URL remoteAddress = RemoteRegister.getRandomURL(interfaceClass.getName());
        return client.send(remoteAddress.getHostname(), remoteAddress.getPort(), invocation);
    }
}
