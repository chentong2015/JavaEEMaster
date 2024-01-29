package sample;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class ZookeeperSampleDemo {

    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // Connection follower basic
        String zookeeperAddress = "8.209.74.47:2181";
        ZooKeeper zooKeeper = new ZooKeeper(zookeeperAddress, 10000, null);
        // 获取指定的结点
        byte[] data = zooKeeper.getData("/node", false, null);
        String value = new String(data, StandardCharsets.UTF_8); // 反序列化需要有指定的编码格式
        System.out.println(value);

        // 创建指定结点
        String nodePath = "/MyNode";
        if (zooKeeper.exists(nodePath, false) == null) {
            zooKeeper.create(nodePath, "100".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
        }
        System.out.println("Success");
    }
}
