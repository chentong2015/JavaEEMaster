package distributed_session;

// 分布式Session:
// 场景: 在分布式Server集群架构场景中，不同Server所保存的Session没有全局共享
//      导致在客户端请求，经过负载均衡算法之后，找到的Server没有Session信息，因此无法完成相关操作
// 方案1: 一致性hash负载均衡算法
public class BaseDistributedSession {

}
