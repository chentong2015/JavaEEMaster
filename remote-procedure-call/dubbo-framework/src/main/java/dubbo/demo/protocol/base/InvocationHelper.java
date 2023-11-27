package dubbo.demo.protocol.base;

import dubbo.demo.framework.data_model.Invocation;
import dubbo.demo.register.LocalRegister;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class InvocationHelper {

    // TODO: 将网络流中的对象进行反序列化成指定类型的对象
    public static Invocation parseInvocationObjectFormHttpServlet(HttpServletRequest req) {
        try {
            InputStream inputStream = req.getInputStream();
            ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
            return (Invocation) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }
        return new Invocation("");
    }

    // 根据收到的数据执行指定的服务
    public static String getInvocationResult(Invocation invocation) {
        try {
            Class classImpl = LocalRegister.get(invocation.getInterfaceName());
            // 通过反射从指定的类型中拿到指定的方法
            Method method = classImpl.getMethod(invocation.getMethodName(), invocation.getParamTypes());
            // 需要根据构造器的参数类型来找到指定的构造器，然后创建对象
            Constructor constructor = classImpl.getConstructor();
            Object instance = constructor.newInstance();
            // 传入实例和参数，需要严格的判断方法执行后的结果和值
            return (String) method.invoke(instance, invocation.getParamValues());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }
}
