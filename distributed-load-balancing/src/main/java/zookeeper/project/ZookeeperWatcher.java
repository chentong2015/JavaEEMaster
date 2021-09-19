package zookeeper.project;

import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;

public class ZookeeperWatcher implements Watcher {

    private ZooKeeper zookeeper;

    @Override
    public void process(WatchedEvent watchedEvent) {
        if (watchedEvent.getType() == Event.EventType.None && watchedEvent.getPath() == null) {
            System.out.println("connection ok");
            if (zookeeper == null) {
                // zookeeper = (ZooKeeper) applicationContext.getBean(ZooKeeper.class)
            }
            try {
                if (zookeeper.exists("product_sold_out_flag_id", false) == null) {
                    zookeeper.create("product_sold_out_flag_id", "".getBytes(),
                            ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
                }
            } catch (KeeperException | InterruptedException exception) {
                exception.printStackTrace();
            }
        } else if (watchedEvent.getType() == Event.EventType.NodeDataChanged) { // 监听结点数据变化
            try {
                String path = watchedEvent.getPath();
                String flagSoldOut = new String(zookeeper.getData(path, true, new Stat()));
                System.out.println("Zookeeper数据结点变动");
                if (flagSoldOut.equals("false")) {
                    String productId = "product_sold_out_flag_id";
                    // 调用应用方法，清除当前JVM中指定product的缓存标记
                }
            } catch (KeeperException | InterruptedException exception) {
                exception.printStackTrace();
            }
        }
    }
}
