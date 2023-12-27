package com.rpc.server.request;

import com.rpc.client.model.RpcInfo;

import java.io.ObjectInputStream;
import java.lang.reflect.Method;
import java.net.Socket;

public class RequestHandler implements Runnable {

    // 处理请求时需要从Socket中获取到请求的信息
    private Socket socket;

    public RequestHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(this.socket.getInputStream());
            RpcInfo rpcInfo = (RpcInfo) objectInputStream.readObject();
            invokeMethod(rpcInfo);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
    }

    // 通过反射获取到要调用的服务的信息，调用方法
    private void invokeMethod(RpcInfo rpcInfo) throws Exception {
        String classFullPath = rpcInfo.getPackageName() + "." + rpcInfo.getClazzName();
        Class cls = Class.forName(classFullPath);
        Class[] parameterTypes = new Class[rpcInfo.getArgs().length];
        for (int index = 0; index < rpcInfo.getArgs().length; index++) {
            parameterTypes[index] = rpcInfo.getArgs()[index].getClass();
        }

        Method method = cls.getMethod(rpcInfo.getMethodName(), parameterTypes);
        method.invoke(cls.newInstance(), rpcInfo.getArgs());
    }
}
