package com.rpc.client.model;

import java.io.Serializable;

// RPC服务端传递的Info对象需要和客户端路径一致
public class RpcInfo implements Serializable {

    private String packageName;
    private String clazzName;
    private String methodName;
    private Object[] args;

    public RpcInfo() {
    }

    public RpcInfo(String packageName, String clazzName, String methodName, Object[] args) {
        this.packageName = packageName;
        this.clazzName = clazzName;
        this.methodName = methodName;
        this.args = args;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getClazzName() {
        return clazzName;
    }

    public void setClazzName(String clazzName) {
        this.clazzName = clazzName;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
