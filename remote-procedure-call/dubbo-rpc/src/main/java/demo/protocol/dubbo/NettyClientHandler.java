package dubbo.demo.protocol.dubbo;

import dubbo.demo.framework.data_model.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.Callable;

public class NettyClientHandler extends ChannelInboundHandlerAdapter implements Callable<String> {

    private ChannelHandlerContext ctxServer;
    private volatile String response;

    public void sendInvocation(Invocation invocation) {
        // 直接向服务端Channel发动数据
        ctxServer.writeAndFlush(invocation);
        System.out.println("Send ok");
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        this.ctxServer = ctx;
        System.out.println("Client connected!");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Client received message");
        response = (String) msg;
    }

    @Override
    public String call() throws Exception {
        // 自旋等待，直到有返回结果
        for (; ; ) {
            if (response != null) return response;
        }
    }
}
