package zookeeper;

// Zookeeper:
// 1. 分布式服务框架，Apache Hadoop子项目
// 2. 解决分布式应用中的一些数据管理问题：统一命名服务，集群管理，应用配置等
// 3. 性能没有Redis高
// https://zookeeper.apache.org/doc/current/index.html
public class BaseZookeeper {

    // TODO: Zookeeper是什么?
    // 1. 一个数据库 ==> 增删改查
    // 2. 一个拥有文件系统(Path路径结点)的数据库 ==> 统一命名服务，定义资源(保证名称不冲突)
    // 3. 一个解决数据一致性问题的分布式数据库    ==> 在一个集群中，数据自动同步
    // 4. 具有分布和订阅工具                  ==> watch机制

    // zookeeper机制:
    // 1. zookeeper启动时会自动同步数据，会发生领导者选择
    // 2. 所有的写请求都会交给leader来处理
    // 3. 必须要集群中大多数都处理了请求，请求才是有效的
}
