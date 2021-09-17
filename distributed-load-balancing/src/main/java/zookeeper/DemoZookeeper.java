package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;

// Zookeeper和Redis类似: (key路径值，value存标记)
// 当key对应的value值发生变化，所有"监听"zookeeper的应用都将受到通知，执行相应的回调逻辑操作

// 在服务端启动
// > ps -ef | grep zookeeper
// > bin/zkCli.sh 进入控制台操作
// > ls /
// > get /key_flag
// > set /key_flag false
public class DemoZookeeper {

    // TODO: 分布式场景，JVM级别的多实例要同步缓存，考虑使用Zookeeper (或者消息中间件)
    public void testZookeeper(Long productId) throws IOException, KeeperException, InterruptedException {
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
