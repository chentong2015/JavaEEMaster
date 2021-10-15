package netty.basic;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyServer {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(8);
        try {
            // 1. 创建服务端的启动对象
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 2. 使用builder开发模式(链式)来配置参数
            bootstrap.group(bossGroup, workerGroup)         // 设置两个线程组
                    .channel(NioServerSocketChannel.class)  // 设置服务器通道的实现类型
                    // TODO: 初始化服务端队列的大小，服务端顺序处理客户端的连接请求
                    //       同一个时间只能处理一个客户端连接，多个请求连接将会被放到队列中等待被处理
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    .childHandler(new ChannelInitializer<>() {
                        // ChannelInitializer抽象类只有一个方法需要被实现
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            channel.pipeline().addLast(new NettyServerHandler());
                        }
                    });// 对SocketChannel设置处理器
            System.out.println("Netty Server started.");
            // 3. 绑定一个服务端端口，生成一个异步对象
            //    使用isDone()等方法来判断异步事件的执行，sync()方法等待异步操作执行完毕
            ChannelFuture future = bootstrap.bind(9000).sync();
            // 4. 给ChannelFuture注册监听器，监听关心的事件
            // future.addListener((ChannelFutureListener) channelFuture -> {
            //     if (channelFuture.isSuccess()) {
            //         System.out.println("Listen to 9000, Success");
            //     } else {
            //         System.out.println("Listen to 9000 failed");
            //     }
            // });
            // 关闭通道
            // future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
        }
    }
}
