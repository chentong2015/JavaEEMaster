package io_network.aio.server;

import java.nio.channels.CompletionHandler;

public class ServerWriteHandler implements CompletionHandler<Integer, Object> {

    @Override
    public void completed(Integer result, Object attachment) {
        System.out.println("Deal thread of [Server WriteCompleteHandler]");
        if (result == -1)
            System.out.println("Send response to client error!");
        else
            System.out.println("The response has been sent back to client successfully!");
    }

    @Override
    public void failed(Throwable exc, Object attachment) {
        System.out.println("Write message error!");
        exc.printStackTrace();
    }
}
