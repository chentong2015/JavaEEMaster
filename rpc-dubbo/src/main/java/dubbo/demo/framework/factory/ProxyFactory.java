package dubbo.demo.framework.factory;

import dubbo.demo.framework.data_model.Invocation;
import dubbo.demo.framework.data_model.URL;
import dubbo.demo.framework.protocol.Protocol;
import dubbo.demo.register.RemoteRegister;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

// 创建基于不同协议的代理对象(及InvocationHandler)
public class ProxyFactory {

    // 使用JDK实现的动态代理：对某一个接口生成一个代理类
    public static <T> T getProxy(Class interfaceClass) {
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(),
                new Class[]{interfaceClass}, getInvocationHandler(interfaceClass));
    }

    // 使用匿名类型，直接在方法体中实现接口，返回匿名对象的引用
    private static InvocationHandler getInvocationHandler(Class interfaceClass) {
        return (proxy, method, args) -> {
            Invocation invocation = new Invocation();
            invocation.setInterfaceName(interfaceClass.getName());
            invocation.setMethodName(method.getName()); // 拿到被调用方法的名称
            invocation.setParamTypes(method.getParameterTypes()); // 被调用方法的参数列表
            invocation.setParamValues(args); // 方法所传递的实际参数

            // TODO: 使用的服务提供者的地址不能写死，需要从注册中心动态取出来(做负载均衡)
            URL remoteAddress = RemoteRegister.getRandomURL(interfaceClass.getName());
            // TODO: 使用工厂模式，解除对具体类型的强依赖(耦合)，基于协议来动态扩展
            Protocol protocol = ProtocolFactory.getProtocol();
            return protocol.sendInvocation(remoteAddress, invocation);
        };
    }
}
