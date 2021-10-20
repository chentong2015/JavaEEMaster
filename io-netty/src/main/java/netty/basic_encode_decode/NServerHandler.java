package netty.basic_encode_decode;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NServerHandler extends ChannelInboundHandlerAdapter {

    // 这里在读取的时候，自动从流中解码成Object对象，然后强转成指定类型的对象
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        DemoClass demoClass = (DemoClass) msg;
        System.out.println(demoClass.getName());
        demoClass.setName("New name");
        ctx.writeAndFlush(demoClass);
    }
}
