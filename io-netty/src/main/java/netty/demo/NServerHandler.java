package netty.demo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        DemoClass demoClass = (DemoClass) msg;
        System.out.println(demoClass.getName());
        demoClass.setName("New chentong");
        ctx.writeAndFlush(demoClass);
    }

    // @Override
    // protected void channelRead(ChannelHandlerContext ctx, String s) throws Exception {
    //     System.out.println("get client " + s);
    //     ctx.writeAndFlush("n server handler ");
    // }
}
