package zookeeper.project;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

// TODO: 可以使用Zookeeper集群架构来解决"主从结点失效"的问题
// 1. 在请求主结点并获取锁的时候，主结点会将key"同步"到其余大部份的结点之后，才返回获得锁成功
// 2. 如果主结点挂掉，则Zookeeper会保证使用新的同步过的结点来作为leader，保证(分布式)锁的有效性

// TODO: 分布式场景下，同步多个JVM实例级别的缓存
// 当key对应的value值发生变化，所有"监听"zookeeper的应用都将受到通知，执行相应的回调逻辑操作
public class ZkProjects {

    public void SyncJvmInstanceCaches(Long productId) throws IOException, KeeperException, InterruptedException {
        String zookeeperAddress = "192.168.0.60:2181";
        ZooKeeper zooKeeper = new ZooKeeper(zookeeperAddress, 6000, new ZookeeperWatcher());
        String zkProductPath = "product_sold_out_flag_" + productId;
        // 在zooKeeper指定的path创建结点，设置值
        if (zooKeeper.exists(zkProductPath, true) == null) {
            zooKeeper.create(zkProductPath, "true".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        }

        // 客户端监听zooKeeper指定的path结点，一旦服务端有变动，客户端通过Watcher来回调代码(保证并发的一致性)
        // 监听事件的类型是结点数据的变化 Event.EventType.NodeDataChanged
        zooKeeper.exists(zkProductPath, true);
    }
}
