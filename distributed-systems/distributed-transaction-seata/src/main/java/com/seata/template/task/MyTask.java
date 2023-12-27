package com.seata.template.task;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class MyTask {

    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    public void waitTask() {
        try {
            lock.lock();
            condition.await(); // 线程等待
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public void signalTask() {
        lock.lock();
        condition.signal(); // 通知等待的线程
        lock.unlock();
    }
}


