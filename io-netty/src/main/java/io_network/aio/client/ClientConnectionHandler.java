package io_network.aio.client;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.util.Scanner;

public class ClientConnectionHandler implements CompletionHandler<Void, Object> {

    private AsynchronousSocketChannel asyncSocketChannel;

    public ClientConnectionHandler(AsynchronousSocketChannel asyncSocketChannel) {
        this.asyncSocketChannel = asyncSocketChannel;
    }

    // 连接完成之后，将数据发送到Server端(调用WriteHandler)
    @Override
    public void completed(Void result, Object attachment) {
        System.out.println("Client connection OK");
        Scanner scanner = new Scanner(System.in);
        ByteBuffer writeByteBuffer;
        while (scanner.hasNextLine()) {
            String request = scanner.nextLine();
            if (request.equals("exit")) break;
            byte[] requestBytes = getRequestBytes(request);
            if (requestBytes.length > 0) {
                writeByteBuffer = ByteBuffer.allocate(requestBytes.length);
                writeByteBuffer.put(requestBytes);
                writeByteBuffer.flip();
                asyncSocketChannel.write(writeByteBuffer, asyncSocketChannel, new ClientWriteHandler());
            }
        }
    }

    private byte[] getRequestBytes(String request) {
        byte[] requestBytes = new byte[0];
        try {
            requestBytes = request.getBytes("utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return requestBytes;
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        System.out.println("Client connection error!");
    }
}
