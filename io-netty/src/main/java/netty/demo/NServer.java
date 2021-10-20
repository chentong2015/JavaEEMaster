package netty.demo;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class NServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 16)
                    .childHandler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            ChannelPipeline channelPipeline = channel.pipeline();
                            // channelPipeline.addLast("decoder", new StringDecoder());
                            // channelPipeline.addLast("encoder", new StringEncoder());

                            ClassLoader classLoader = this.getClass().getClassLoader();
                            ClassResolver classResolver = ClassResolvers.weakCachingConcurrentResolver(classLoader);
                            channelPipeline.addLast("decoder", new ObjectDecoder(classResolver));
                            channelPipeline.addLast("encoder", new ObjectEncoder());
                            channelPipeline.addLast(new NServerHandler());
                        }
                    });
            System.out.println("Netty Server started.");
            bootstrap.bind(8080).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
