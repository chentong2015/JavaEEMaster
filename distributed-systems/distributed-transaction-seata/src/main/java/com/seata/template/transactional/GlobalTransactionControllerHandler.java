package com.seata.template.transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GlobalTransactionControllerHandler extends ChannelInboundHandlerAdapter {

    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private static Map<String, List<String>> transactionIdMap = new HashMap<>();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel clientChannel = ctx.channel();
        channelGroup.add(clientChannel);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive Message");
        JSONObject jsonObject = JSON.parseObject((String) msg);
        String command = jsonObject.getString("command");
        String groupId = jsonObject.getString("command");
        String transactionType = jsonObject.getString("transactionType");
        String transactionId = jsonObject.getString("transactionsId");
        if (command.equals("create")) {
            // 1. 开启全局事务, 划分一个组，包含这个事务下的所有transactionId
            transactionIdMap.put(groupId, new ArrayList<>());
        } else if (command.equals("register")) {
            // 2. 注册分支事务
            transactionIdMap.get(groupId).add(transactionId);
            if (transactionType.equals("rollback")) {
                // 3. 判断接收到一个回滚状态
                sentMessage(groupId, "rollback");
            }
        } else if (command.equals("commit")) {
            sentMessage(groupId, "commit");
        }
    }

    // 向这个事务组中所有的分支事务发送command指令
    // 在客户端接收到指令消息之后，执行相应的逻辑
    private void sentMessage(String groupId, String command) {
        JSONObject result = new JSONObject();
        result.put("groupId", groupId);
        result.put("command", command);
        for (Channel channel : channelGroup) {
            System.out.println("Send data: " + result.toString());
            channel.writeAndFlush(result.toString());
        }
    }
}
