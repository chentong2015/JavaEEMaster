package zookeeper;

import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.ZooDefs;
import org.apache.zookeeper.ZooKeeper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

// 1. 分布式服务框架，Apache Hadoop子项目
// 2. 解决分布式应用中的一些数据管理问题：统一命名服务，集群管理，应用配置等
// 3. 性能没有Redis高
// https://zookeeper.apache.org/doc/current/index.html
public class BaseZookeeper {

    // TODO: Zookeeper是什么?
    // 1. 具有文件系统(Path路径结点)的数据库 ==> 统一命名服务，定义资源(保证名称不冲突)
    // 2. 能解决数据一致性问题的分布式数据库  ==> 集群中数据自动同步
    // 3. 具有分布和订阅工具               ==> watch机制

    // zookeeper机制:
    // 1. zookeeper启动时会自动同步数据，会发生领导者选择
    // 2. 所有的写请求都会交给leader来处理
    // 3. 必须要集群中大多数都处理了请求，请求才是有效的

    // zookeeper处理请求的机制:
    // 集群中接收请求的类型
    //   1. 非事务性请求: get, exs
    //   2. 事务性请求: create, set, del
    //      生成事务日志: 事务id:zxid(自增的id)
    //      (对于刚搭建好的集群，启动没有id，运行过一段时间之后便有)
    // Zookeeper处理请求的过程
    //   1. 持久化事务日志，处理文件，可用顺序添加，速度更慢  ==> 可以通过事务日志来恢复数据
    //   2. 更新内存，DataTree，数结构修改速度更慢

    // Test Zookeeper cluster connection
    public static void main(String[] args) throws IOException, KeeperException, InterruptedException {
        // Connection follower server
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
