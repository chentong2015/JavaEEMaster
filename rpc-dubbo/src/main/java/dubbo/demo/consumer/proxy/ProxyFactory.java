package dubbo.demo.consumer.proxy;

import java.lang.reflect.Proxy;

public class ProxyFactory {

    // 使用JDK实现的动态代理：对某一个接口生成一个代理类
    public static <T> T getProxy(Class interfaceClass) {
        MyInvocationHandler invocationHandler = new MyInvocationHandler(interfaceClass);
        Object result = Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, invocationHandler);
        return (T) result;
    }
}
