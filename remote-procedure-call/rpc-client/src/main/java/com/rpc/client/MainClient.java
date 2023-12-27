package com.rpc.client;

import com.rpc.client.model.RpcInfo;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class MainClient {

    // 客户端通过Server端暴露的Socket接口来建立通讯，执行指定API的调用
    public static void main(String[] args) throws IOException {
        Socket socket = new Socket("127.0.0.1", 1234);

        // 实战项目中需要通过"服务发现"来判断要调用的服务
        RpcInfo rpcInfo = new RpcInfo();
        rpcInfo.setPackageName("com.rpc.server.service");
        rpcInfo.setClazzName("ServerDao");
        rpcInfo.setMethodName("print");
        rpcInfo.setArgs(new Object[]{"tong"});

        // 需要向Server端的进程发送封装的对象流
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        objectOutputStream.writeObject(rpcInfo);
        System.out.println("Send RPC request done!");
    }
}
