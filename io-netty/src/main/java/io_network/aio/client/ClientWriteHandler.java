package io_network.aio.client;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ClientWriteHandler implements CompletionHandler<Integer, AsynchronousSocketChannel> {

    // 向客户端发送完数据之后，读取回复信息(调用ReadHandler)
    @Override
    public void completed(Integer result, AsynchronousSocketChannel asyncSocketChannel) {
        System.out.println("Deal thread of [Client WriteCompleteHandler]");
        if (result == -1) {
            System.out.println("Send request to server error!");
        } else {
            ByteBuffer readByteBuffer = ByteBuffer.allocate(100);
            asyncSocketChannel.read(readByteBuffer, readByteBuffer, new ClientReadHandler());
        }
    }

    @Override
    public void failed(Throwable exc, AsynchronousSocketChannel asyncSocketChannel) {
        System.out.println("Write message error!");
        exc.printStackTrace();
    }
}
