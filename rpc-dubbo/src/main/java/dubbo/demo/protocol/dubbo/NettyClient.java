package dubbo.demo.protocol.dubbo;

import dubbo.demo.model.Invocation;
import dubbo.demo.protocol.base.ProtocolClient;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient implements ProtocolClient {

    public NettyClientHandler clientHandler;

    private static ExecutorService executorService = Executors.newCachedThreadPool();

    @Override
    public String send(String hostname, int port, Invocation invocation) {
        if (clientHandler == null) {
            start(hostname, port); // 先建立好连接
        }
        clientHandler.setInvocation(invocation);
        // try {
        //     return (String) executorService.submit(clientHandler).get();
        // } catch (InterruptedException e) {
        //     e.printStackTrace();
        // }
        return null;
    }

    public void start(String hostname, int port) {
        clientHandler = new NettyClientHandler();
        Bootstrap bootstrap = new Bootstrap();
        EventLoopGroup group = new NioEventLoopGroup();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<>() {
                    @Override
                    protected void initChannel(Channel channel) {
                        ChannelPipeline channelPipeline = channel.pipeline();
                        ClassLoader classLoader = this.getClass().getClassLoader();
                        ClassResolver classResolver = ClassResolvers.weakCachingConcurrentResolver(classLoader);
                        channelPipeline.addLast("decoder", new ObjectDecoder(classResolver));
                        channelPipeline.addLast("encoder", new ObjectEncoder());
                        channelPipeline.addLast("handler", clientHandler);
                    }
                });
        try {
            bootstrap.connect(hostname, port).sync();
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
