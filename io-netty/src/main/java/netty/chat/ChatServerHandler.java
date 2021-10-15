package netty.chat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

public class ChatServerHandler extends SimpleChannelInboundHandler<String> {

    // TODO: 必须使用Static，唯一全局的静态成员(共享连接到Server端的所有Client)
    // GlobalEventExecutor.INSTANCE单例，全局的事件执行器
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    // 处理Channel登录，连接事件
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " connected");
        // TODO: 遍历其中所有的Channel并发送消息，推送到全部的客户端
        channelGroup.writeAndFlush(formatChannelMessage(channel));
        // 将当前的channel也添加到集合中
        channelGroup.add(channel);
    }

    private String formatChannelMessage(Channel channel) {
        SimpleDateFormat dateFormat = new SimpleDateFormat();
        String dateStr = dateFormat.format(new Date());
        return "Client: " + channel.remoteAddress() + " up," + dateStr + "\n";
    }

    // 处理接收(read)客户发送信息的事件
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        Channel channel = ctx.channel();
        // 当Netty Server接收到信息后，会将信息群发给每一个Channel
        channelGroup.forEach(ch -> {
            String msgTitle = (ch == channel) ? "Me: " : "Client: ";
            ch.writeAndFlush(msgTitle + channel.remoteAddress() + " send message: " + msg);
        });
    }

    // 处理Channel离线，处于不活动状态 ==> 客户端调用clientChannel.close();
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush(formatChannelMessage(channel));  // 在客户端显示
        System.out.println(channel.remoteAddress() + " goes away"); // 在服务端显示
        System.out.println("Remaining connections: " + channelGroup.size());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
