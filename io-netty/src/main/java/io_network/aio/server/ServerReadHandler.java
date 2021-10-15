package io_network.aio.server;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

public class ServerReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    private AsynchronousSocketChannel asyncSocketChannel;

    public ServerReadHandler(AsynchronousSocketChannel asyncSocketChannel) {
        this.asyncSocketChannel = asyncSocketChannel;
    }

    @Override
    public void completed(Integer result, ByteBuffer readByteBuffer) {
        System.out.println("Deal thread of [Server ReadCompleteHandler]");
        if (result == -1) {
            System.out.println("Get request from client error!");
        } else {
            try {
                readClientMessage(readByteBuffer);
                // 回复客户端信息
                asyncSocketChannel.write(getResponseMessage(), null, new ServerWriteHandler());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void readClientMessage(ByteBuffer readByteBuffer) throws UnsupportedEncodingException {
        readByteBuffer.flip();
        byte[] requestBytes = new byte[readByteBuffer.remaining()];
        readByteBuffer.get(requestBytes);
        String message = new String(requestBytes, "utf-8");
        System.out.println(message);
        readByteBuffer.flip();
    }

    private ByteBuffer getResponseMessage() throws UnsupportedEncodingException {
        String response = "Hi, this is server!";
        byte[] responseBytes = response.getBytes("utf-8");
        ByteBuffer responseByteBuffer = ByteBuffer.allocate(responseBytes.length);
        responseByteBuffer.put(responseBytes);
        responseByteBuffer.flip();
        return responseByteBuffer;
    }

    @Override
    public void failed(Throwable exc, ByteBuffer readByteBuffer) {
        System.out.println("Read message error!");
        exc.printStackTrace();
    }
}
