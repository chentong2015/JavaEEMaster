package netty.basic_encode_decode;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(ChannelHandlerHelper.getChannelHandler(new NClientHandler()));
            ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
            System.out.println("Client started.");
            testSendObject(future);
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }

    // 测试将Object对象发送到Netty Server
    private static void testSendObject(ChannelFuture future) {
        Channel clientChannel = future.channel();
        DemoClass demoClass = new DemoClass("basic name");
        clientChannel.writeAndFlush(demoClass);
        System.out.println("Send OK");
        // clientChannel.close();
    }
}
