package com.seata.template.model;

import com.seata.template.task.MyTask;

public class MyTransaction {

    private String groupId;
    private String transactionId;
    private TransactionType transactionType;
    // 每个事务都有它自己的一个任务
    private MyTask task = new MyTask();

    public MyTransaction(String groupId, String transactionId) {
        this.groupId = groupId;
        this.transactionId = transactionId;
    }

    public MyTransaction(String groupId, String transactionId, TransactionType transactionType) {
        this.groupId = groupId;
        this.transactionId = transactionId;
        this.transactionType = transactionType;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public void setTransactionType(TransactionType transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionType getTransactionType() {
        return transactionType;
    }

    public MyTask getTask() {
        return task;
    }
}
