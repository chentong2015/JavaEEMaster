package com.zookeeper.sample;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;
import com.zookeeper.sample.watcher.MyWatcher;

// TODO. 分布式场景下，同步多个JVM实例级别的缓存
// 当key对应的value值发生变化，所有"监听"ZK的应用都将受到通知，执行相应的回调逻辑操作
public class DemoZookeeperWatcher {

    public void SyncJvmInstanceCaches(Long productId) throws Exception {
        String zookeeperAddress = "192.168.0.60:2181";
        ZooKeeper zooKeeper = new ZooKeeper(zookeeperAddress, 6000, new MyWatcher());
        String zkProductPath = "flag_" + productId;

        // 在zooKeeper指定的path创建结点，设置值
        if (zooKeeper.exists(zkProductPath, true) == null) {
            zooKeeper.create(zkProductPath, "true".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

        // 客户端监听zookeeper指定的path结点，一旦服务端有变动，客户端通过Watcher来回调代码(保证并发的一致性)
        // 监听事件的类型是结点数据的变化 Event.EventType.NodeDataChanged
        zooKeeper.exists(zkProductPath, true);
    }
}
