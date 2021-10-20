package dubbo.demo.protocol.dubbo;

import dubbo.demo.framework.data_model.Invocation;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.Callable;

public class NettyClientHandler extends SimpleChannelInboundHandler<String> implements Callable<String> {

    private Invocation invocation;
    private String serverResponse;

    public void setInvocation(Invocation invocation) {
        this.invocation = invocation;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("Get result back: " + s);
    }


    // @Override
    // public void channelActive(ChannelHandlerContext ctx) throws Exception {
    //     // ctx.writeAndFlush()
    // }
// 
    // @Override
    // protected void channelRead0(ChannelHandlerContext ctx, String s) {
    //     // 收到从服务端返回的信息
    //     serverResponse = s;
    // }

    @Override
    public String call() throws Exception {
        // 自旋，直到有返回结果
        for (; ; ) {
            if (serverResponse != null)
                return serverResponse;
        }
    }
}
