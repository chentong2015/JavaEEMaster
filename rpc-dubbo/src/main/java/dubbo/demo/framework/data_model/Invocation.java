package dubbo.demo.framework.data_model;

import java.io.Serializable;

// "服务消费者"发送到"服务提供者"的数据模型
// TODO: 必须实现Serializable，使得该类型的实例能够被序列化，经过网络传输
public class Invocation implements Serializable {

    private String interfaceName;
    private String methodName;
    private Class[] paramTypes; // 参数类型列表
    private Object[] paramValues; // 参数值列表

    public Invocation() {
    }

    public Invocation(String interfaceName) {
        this(interfaceName, null, null, null);
    }

    public Invocation(String interfaceName, String methodName, Class[] paramTypes, Object[] params) {
        this.interfaceName = interfaceName;
        this.methodName = methodName;
        this.paramTypes = paramTypes;
        this.paramValues = params;
    }

    public String getInterfaceName() {
        return interfaceName;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Class[] getParamTypes() {
        return paramTypes;
    }

    public void setParamTypes(Class[] paramTypes) {
        this.paramTypes = paramTypes;
    }

    public Object[] getParamValues() {
        return paramValues;
    }

    public void setParamValues(Object[] params) {
        this.paramValues = params;
    }
}
