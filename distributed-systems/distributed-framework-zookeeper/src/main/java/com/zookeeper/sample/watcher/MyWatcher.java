package com.zookeeper.sample.watcher;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

public class MyWatcher implements Watcher {

    private ZooKeeper zookeeper;

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.None && watchedEvent.getPath() == null) {
            System.out.println("connection ok");
            if (zookeeper == null) {
                // zookeeper = (ZooKeeper) applicationContext.getBean(ZooKeeper.class)
            }
            try {
                if (zookeeper.exists("flag_id", false) == null) {
                    zookeeper.create("flag_id", "".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                }
            } catch (KeeperException | InterruptedException exception) {
                exception.printStackTrace();
            }
        } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) {
            // 监听Event事件，发现结点数据变化
            try {
                String path = watchedEvent.getPath();
                String flagSoldOut = new String(zookeeper.getData(path, true, new Stat()));
                System.out.println("Zookeeper node changed");
                if (flagSoldOut.equals("false")) {
                    String productId = "flag_id";
                    // 调用应用方法，清除当前JVM中指定product的缓存标记
                }
            } catch (KeeperException | InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
