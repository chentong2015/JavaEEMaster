package zookeeper.consistency;

// TODO: 如果一个集群需要去执行一个外部请求，如何实现数据一致性问题 ?
// 1. 需要一个leader: 选举机制
// 2. 过半机制      : 大多数机制
// 3. 2阶段提交(2PC): 预提交 -> 收到ack回复 -> 提交
public class LeaderSelection {

    // TODO: 领导者选举机制
    // 1. 投票: 投自己 -> 沟通比较 -> PK改票
    // 2. 选票
    // 3. 投票箱
    // 4. 过半机制(zookeeper)

    // TODO: 什么时候发生领导者选举 ?
    // 1. 集群启动
    //    zookeeper PK: 数据越新则越强，比较Server的事务日志zxid
    //    如果zxid相同或者没有，则PK无效，则使用serverId来比较，越大越强(越后面)
    // 2. leader挂掉
    //    使用同样的PK机制进行分析
    // 3. follower挂掉导致leader没有超过半数的follower了
    //    让整个集群不能提供服务
}
