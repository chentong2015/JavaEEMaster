package netty.demo;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NClient {

    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        ChannelPipeline channelPipeline = channel.pipeline();
                        // 确当使用想用的解码器来解码对象
                        ClassLoader classLoader = this.getClass().getClassLoader();
                        ClassResolver classResolver = ClassResolvers.weakCachingConcurrentResolver(classLoader);
                        channelPipeline.addLast("decoder", new ObjectDecoder(classResolver));
                        channelPipeline.addLast("encoder", new ObjectEncoder());

                        // channelPipeline.addLast("decoder", new StringDecoder());
                        // channelPipeline.addLast("encoder", new StringEncoder());
                        channelPipeline.addLast("handler", new NClientHandler());
                    }
                });
        try {
            ChannelFuture future = bootstrap.connect("localhost", 8080).sync();
            Channel clientChannel = future.channel();

            DemoClass demoClass = new DemoClass("chentong");
            clientChannel.writeAndFlush(demoClass);
            System.out.println("Send OK");
            // clientChannel.close();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
