package zookeeper;

// Zookeeper:
// 1. 分布式服务框架，Apache Hadoop子项目
// 2. 解决分布式应用中的一些数据管理问题：统一命名服务，集群管理，应用配置等
// https://zookeeper.apache.org/doc/current/index.html
public class BaseZookeeper {

    // TODO: Zookeeper是什么?
    // 1. 一个数据库 ==> 增删改查
    // 2. 一个拥有文件系统(Path路径结点)的数据库 ==> 统一命名服务，定义资源(保证名称不冲突)
    // 3. 一个解决数据一致性问题的分布式数据库    ==> 在一个集群中，数据自动同步
    // 4. 具有分布和订阅工具                  ==> watch机制

    // zookeeper机制:
    // 1. zookeeper启动时会自动同步数据
    // 2. 所有的写请求都会交给leader来处理
    // 3. 必须要集群中大多数都处理了请求，请求才是有效的 !!


    //
    //
    //
    //
    //
    // Zookeeper 分布式框架: Redis类似, 树形结构的存储, 但性能没有Redis高
    // Zookeeper 两大核心
    // 1. 集群的Leader选举机制
    // 2. 写数据ZAB协议，半数以上的结点写完成则表示写成功

    // Zookeeper搭建集群架构(主从架构)来实现主从结点失效的问题
    // 1. 在请求主结点并获取锁的时候，主结点会将key"同步"到其余大部份的结点之后，才返回获得锁成功
    // 2. 如果主结点挂掉，则Zookeeper会保证使用新的有同步过的结点来作为新的主结点，保证(分布式)锁的有效性
}
