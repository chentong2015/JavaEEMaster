package com.seata.template.client;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.seata.template.model.MyTransaction;
import com.seata.template.model.TransactionType;
import com.seata.template.transactional.GlobalTransactionManager;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class NettyClientHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext context;

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        context = ctx;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        System.out.println("Receive message: " + msg.toString());
        JSONObject jsonObject = JSON.parseObject((String) msg);
        String groupId = jsonObject.getString("groupId");
        String command = jsonObject.getString("command");

        // TODO: 每一个client接收到整个分布式事务的groupId和要执行的command
        // 拿到GroupId对应的分支事务，根据接收到的Server回复信息，决定最终状态
        MyTransaction transaction = GlobalTransactionManager.getMyTransaction(groupId);
        if (command.equals("commit")) {
            transaction.setTransactionType(TransactionType.commit);
        } else {
            transaction.setTransactionType(TransactionType.rollback);
        }
        // 唤醒等待，让该分支事务执行指定的逻辑
        transaction.getTask().signalTask();
    }

    // 向Server端发送数据
    public synchronized Object call(JSONObject data) {
        context.writeAndFlush(data.toJSONString()).channel().newPromise();
        return null;
    }
}
