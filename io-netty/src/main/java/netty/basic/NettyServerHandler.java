package netty.basic;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;

// 自定义Handler处理器，需要继承规定类型HandlerAdapter
// ChannelInboundHandlerAdapter   继承该类型传输的是Object类型的数据，需要指定特定的编码和解码器
// SimpleChannelInboundHandler<T> 继承该类型所传输的数据是String(封装)，需要配置String编码和解码器

// TODO: 在同一个进程中被多个client端连接，这个ServerHandler需要被设置成"可共享"
@ChannelHandler.Sharable
public class NettyServerHandler extends ChannelInboundHandlerAdapter {

    // 当客户端连接服务器完成时，触发下面方法，在服务端回调
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        System.out.println("Connection OK");
    }

    // 读取客户端发送的数据，数据封装到msg直接使用(源码封装时使用的缓冲数据结构是ByteBuf)
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        // Channel channel = ctx.channel(); 拿到建立连接的客户端Channel
        // ChannelPipeline pipeline = ctx.pipeline(); 双向链表，出站和入站
        // 强制转换成封装的数据的类型
        ByteBuf byteBuf = (ByteBuf) msg;
        System.out.println("Get message from client: " + byteBuf.toString(CharsetUtil.UTF_8));
        // 回写信息回客户端
        ctx.writeAndFlush(msg);
    }
}
