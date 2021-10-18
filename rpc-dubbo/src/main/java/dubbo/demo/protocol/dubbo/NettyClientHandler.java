package dubbo.demo.protocol.dubbo;

import dubbo.demo.model.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> {

    private Invocation invocation;

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {

    }
}
