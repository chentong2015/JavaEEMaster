package netty.chat;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.Scanner;

public class ChatClient {

    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(group)
                    .channel(NioSocketChannel.class)
                    .handler(new ChannelInitializer<>() {
                        @Override
                        protected void initChannel(Channel channel) {
                            ChannelPipeline channelPipeline = channel.pipeline();
                            // 添加编码和解码器
                            channelPipeline.addLast("decoder", new StringDecoder());
                            channelPipeline.addLast("encoder", new StringEncoder());
                            channelPipeline.addLast(new ChatClientHandler());
                        }
                    });
            ChannelFuture future = bootstrap.connect("127.0.0.1", 8000).sync();
            System.out.println("Connected server");
            simulateChatting(future.channel());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // TODO: 如果要退出聊天群，需要将原本建立的连接Channel关闭
    private static void simulateChatting(Channel clientChannel) {
        Scanner scanner = new Scanner(System.in);
        try {
            while (scanner.hasNextLine()) {
                String message = scanner.nextLine();
                if (message.equals("exit")) {
                    System.out.println("Exit the chat OK");
                    return;
                }
                clientChannel.writeAndFlush(message); // 将输入信息一并发送给服务端(群发)
            }
        } finally {
            clientChannel.close();
        }
    }
}
