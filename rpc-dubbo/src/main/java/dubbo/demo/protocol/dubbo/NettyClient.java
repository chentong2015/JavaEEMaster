package dubbo.demo.protocol.dubbo;

import dubbo.demo.framework.data_model.Invocation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NettyClient {

    public NettyClientHandler clientHandler;
    private static ExecutorService executorService = Executors.newCachedThreadPool();

    public String send(String hostname, int port, Invocation invocation) {
        if (clientHandler == null)
            connectServer(hostname, port, invocation);
        clientHandler.setInvocation(invocation);
        try {
            // 调用Future<T>.get()方法会阻塞当前线程，直到获取服务返回的值
            return executorService.submit(clientHandler).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        return null;
    }

    private void connectServer(String hostname, int port, Invocation invocation) {
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
            ChannelFuture future = bootstrap.connect(hostname, port).sync();
            Channel clientChannel = future.channel();

            //  ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            //  ObjectOutputStream serializeStream = new ObjectOutputStream(byteArrayOutputStream);
            //  serializeStream.writeObject(invocation);
            // clientChannel.writeAndFlush(serializeStream);

            clientChannel.writeAndFlush("invocation");
            System.out.println("Send OK");
        } catch (InterruptedException exception) {
            exception.printStackTrace();
        }
    }
}
