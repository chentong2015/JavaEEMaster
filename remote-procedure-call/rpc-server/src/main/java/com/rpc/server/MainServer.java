package com.rpc.server;

import com.rpc.server.request.RequestHandler;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MainServer {

    // RPC服务端对外暴露socket接口，接收另外进程来的请求
    // 使用异步的方式来处理请求，避免RPC服务端在接收请求时被阻塞
    public static void main(String[] args) throws IOException {
       try(ServerSocket serverSocket = new ServerSocket(1234)){
           Executor executor = Executors.newFixedThreadPool(10);
           while (true) {
               System.out.println("wait connection");
               Socket socket = serverSocket.accept();
               System.out.println("connection ok");
               // 执行通讯成功的socket
               executor.execute(new RequestHandler(socket));
           }
       }
    }
}
