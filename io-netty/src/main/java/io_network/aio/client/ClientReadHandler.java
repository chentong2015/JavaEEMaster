package io_network.aio.client;

import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.channels.CompletionHandler;

public class ClientReadHandler implements CompletionHandler<Integer, ByteBuffer> {

    @Override
    public void completed(Integer result, ByteBuffer respByteBuffer) {
        System.out.println("Deal thread of [Client ReadCompleteHandler]");
        if (result == -1) {
            System.out.println("Get response from server error!");
        } else {
            try {
                readServerMessage(respByteBuffer);
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    private void readServerMessage(ByteBuffer readByteBuffer) throws UnsupportedEncodingException {
        readByteBuffer.flip();
        byte[] requestBytes = new byte[readByteBuffer.remaining()];
        readByteBuffer.get(requestBytes);
        String response = new String(requestBytes, "utf-8");
        System.out.println("The response sent by server is: " + response);
        readByteBuffer.flip();
    }

    @Override
    public void failed(Throwable exc, ByteBuffer readByteBuffer) {
        System.out.println("Read message error!");
        exc.printStackTrace();
    }
}
