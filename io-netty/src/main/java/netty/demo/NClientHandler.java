package netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NClientHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DemoClass demoClass = (DemoClass) msg;
        System.out.println("Back object : " + demoClass.getName());
    }

    // @Override
    // protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
    //     System.out.println("get back: " + s);
    // }
}
