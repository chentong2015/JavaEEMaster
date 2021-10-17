package dubbo.demo.consumer.proxy;

import dubbo.demo.consumer.client.HttpClient;
import dubbo.demo.model.Invocation;
import dubbo.demo.model.URL;
import dubbo.demo.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class MyInvocationHandler implements InvocationHandler {

    private Class interfaceClass;

    public MyInvocationHandler(Class interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Invocation invocation = new Invocation();
        invocation.setInterfaceName(interfaceClass.getName());
        invocation.setMethodName(method.getName()); // 拿到被调用方法的名称
        invocation.setParamTypes(method.getParameterTypes()); // 被调用方法的参数列表
        invocation.setParamValues(args); // 方法所传递的实际参数

        HttpClient client = new HttpClient();
        // TODO: 这里的地址不能写死，需要从注册中心动态的取出来，并做负载均衡
        // client.send("localhost", 8080, invocation);
        URL remoteAddress = RemoteRegister.getRandomURL(interfaceClass.getName());
        return client.send(remoteAddress.getHostname(), remoteAddress.getPort(), invocation);
    }
}
