package zookeeper;

// Zookeeper 分布式框架: Redis类似, 树形结构的存储, 但性能没有Redis高
// Zookeeper 两大核心
// 1. 集群的Leader选举机制
// 2. 写数据ZAB协议，半数以上的结点写完成则表示写成功

// Zookeeper搭建集群架构(主从架构)来实现主从结点失效的问题
// 1. 在请求主结点并获取锁的时候，主结点会将key"同步"到其余大部份的结点之后，才返回获得锁成功
// 2. 如果主结点挂掉，则Zookeeper会保证使用新的有同步过的结点来作为新的主结点，保证(分布式)锁的有效性
public class BaseZookeeper {

    // Zookeeper详解 https://www.bilibili.com/video/BV1Rf4y1y7xE?p=79

    // https://zookeeper.apache.org/
    // TODO: https://zookeeper.apache.org/doc/current/index.html
}
