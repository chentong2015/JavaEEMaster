package com.seata.template.transactional;

import com.alibaba.fastjson.JSONObject;
import com.seata.template.client.NettyClient;
import com.seata.template.model.MyTransaction;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class GlobalTransactionManager {

    private static NettyClient nettyClient;
    private static ThreadLocal<MyTransaction> currentTransaction = new ThreadLocal<>();
    private static ThreadLocal<String> currentGroupId = new ThreadLocal<>();
    public static Map<String, MyTransaction> myTransactionMap = new HashMap<>();

    @Autowired
    public void setNettyClient(NettyClient nettyClient) {
        GlobalTransactionManager.nettyClient = nettyClient;
    }

    // 1. 创建事务组，并返回GroupId
    public static String createTransactionGroup() {
        if (currentGroupId.get() != null) {
            return currentGroupId.get();
        } else {
            String groupId = UUID.randomUUID().toString();
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("groupId", groupId);
            jsonObject.put("command", "create");
            nettyClient.send(jsonObject);
            currentGroupId.set(groupId);
            return groupId;
        }
    }

    // 2. 创建分支事务
    public static MyTransaction createMyTransaction(String groupId) {
        String transactionId = UUID.randomUUID().toString();
        MyTransaction myTransaction = new MyTransaction(groupId, transactionId);
        myTransactionMap.put(groupId, myTransaction);
        currentTransaction.set(myTransaction);
        return myTransaction;
    }

    // 3. 注册分支事务
    public static MyTransaction registerMyTransaction(MyTransaction myTransaction) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("command", "register");
        jsonObject.put("groupId", myTransaction.getGroupId());
        jsonObject.put("transactionId", myTransaction.getTransactionId());
        jsonObject.put("transactionType", myTransaction.getTransactionType());
        nettyClient.send(jsonObject);
        return myTransaction;
    }

    // 4. 提交全局的分布式事务
    public static void submitGlobalTransaction(String groupId) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("groupId", groupId);
        jsonObject.put("command", "commit");
        nettyClient.send(jsonObject);
    }

    public static MyTransaction getMyTransaction(String groupId) {
        return myTransactionMap.get(groupId);
    }
}
