package zookeeper.request;

public class ZookeeperRequest {

    // 集群中接收请求的类型
    // 事务性请求: create, set, del -> 生成事务日志: 事务id:zxid(自增的id)
    //           对于刚搭建好的集群，启动没有id，运行过一段时间之后便有
    // 非事务性请求: get, exs

    // TODO: Zookeeper处理请求的过程
    // 1. 持久化事务日志，处理文件，可用顺序添加，速度更慢  ==> 可以通过事务日志来恢复数据
    // 2. 更新内存，DataTree，数结构修改速度更慢
}
