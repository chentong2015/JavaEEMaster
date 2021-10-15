package io_network.aio.server;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerConnectionHandler implements CompletionHandler<AsynchronousSocketChannel, AsynchronousServerSocketChannel> {

    // ByteBuffer is non-thread safe 不能多线程同时使用
    private ByteBuffer readByteBuffer = ByteBuffer.allocate(100);

    @Override
    public void completed(AsynchronousSocketChannel asyncSocketChannel, AsynchronousServerSocketChannel asyncServerSocketChannel) {
        System.out.println("Deal thread of [Server ConnectCompleteHandler]");
        // TODO: 当前请求建立连接完成之后，将会收到下一个请求去建立连接
        asyncServerSocketChannel.accept(asyncServerSocketChannel, new ServerConnectionHandler());
        ServerReadHandler readHandler = new ServerReadHandler(asyncSocketChannel);
        asyncSocketChannel.read(readByteBuffer, readByteBuffer, readHandler);
    }

    @Override
    public void failed(Throwable exc, AsynchronousServerSocketChannel attachment) {
        System.out.println("Server connection error!");
    }
}
