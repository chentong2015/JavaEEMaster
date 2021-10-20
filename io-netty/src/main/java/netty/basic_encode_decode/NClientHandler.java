package netty.basic_encode_decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        DemoClass demoClass = (DemoClass) msg;
        System.out.println("Receive from server: " + demoClass.getName());
    }
}
