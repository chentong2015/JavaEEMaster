package dubbo.demo.protocol.dubbo;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class NettyServerHandler extends SimpleChannelInboundHandler<String> {

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("Connected");
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        System.out.println("get : " + s);
    }

    // @Override
    // public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    // ByteBuf buf = (ByteBuf) msg;
    // byte[] req = new byte[buf.readableBytes()];
    // buf.readBytes(req);

    //  ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buf.array());
    //  ObjectInputStream deserializeStream = new ObjectInputStream(byteArrayInputStream);
    //  Invocation invocation = (Invocation) deserializeStream.readObject();

    // String str = (String) msg;
    // System.out.println("get: " + str);


    //Invocation invocation = (Invocation) msg;
    //System.out.println("get: " + invocation.getInterfaceName());

    //Object result = InvocationHelper.getInvocationResult(invocation);
    //System.out.println("Netty server gets invocation: " + result);
    //// 将结果写回到客户端
    //ctx.writeAndFlush("Netty server" + result);
    // }

}
