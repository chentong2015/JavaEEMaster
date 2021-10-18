package dubbo.demo.protocol.dubbo;

import dubbo.demo.model.Invocation;
import dubbo.demo.protocol.base.InvocationHelper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Invocation invocation = (Invocation) msg;
        Object result = InvocationHelper.getInvocationResult(invocation);
        System.out.println("Netty server gets invocation result: " + result);
        // 将结果写回到客户端
        ctx.writeAndFlush("Netty server" + result);
    }
}
