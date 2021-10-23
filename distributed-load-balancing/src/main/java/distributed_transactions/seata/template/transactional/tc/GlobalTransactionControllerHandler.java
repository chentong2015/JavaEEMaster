package distributed_transactions.seata.template.transactional.tc;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.json.JSONObject;

public class GlobalTransactionControllerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive Message");
        JSONObject jsonObject;
    }

    private void sentMessage(String groupId, String command) {
        JSONObject result = new JSONObject();
        result.put("groupId", groupId);
        result.put("command", command);
    }

    private void sendResult(JSONObject result) {
        for (Channel channel : channelGroup) {
            System.out.println("Send data: " + result.toString());
            channel.writeAndFlush(result.toString());
        }
    }
}
