package com.seata.template.client;

import com.alibaba.fastjson.JSONObject;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class NettyClient {

    private static NettyClientHandler clientHandler;

    public static void start(String hostname, int port) {
        clientHandler = new NettyClientHandler();
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<Channel>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline channelPipeline = channel.pipeline();
                            // 添加编码和解码器
                            channelPipeline.addLast("decoder", new StringDecoder());
                            channelPipeline.addLast("encoder", new StringEncoder());
                            channelPipeline.addLast("handler", clientHandler);
                        }
                    });
            // 和服务端建立起连接，客户端会开启一个端口号(用于通讯)
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8000).sync();
            System.out.println("Connected server");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void send(JSONObject jsonObject) {
        try {
            clientHandler.call(jsonObject);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
