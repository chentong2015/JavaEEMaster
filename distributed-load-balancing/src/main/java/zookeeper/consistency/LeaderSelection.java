package zookeeper.consistency;

import java.util.Set;

// TODO: 领导者选举机制
// 1. 投票: 投自己 -> 沟通比较 -> PK改票
// 2. 投票箱[选票, 选票...]
// 3. 过半机制
public class LeaderSelection {

    // 什么时候发生领导者选举?
    // 1. 集群启动
    //    PK投票标准: 数据越新则越强，比较Server的事务日志zxid
    //    若PK无效(zxid相同或者没有): 使用serverId来比较，越大越强(越后面)
    // 2. leader挂掉
    //    使用同样的PK机制进行分析
    // 3. follower挂掉导致leader没有超过半数的follower追随自己
    //    让整个集群不能提供服务

    // 过半机制: 可以设置等到集群中所有的server都启动完成之后，再选举leader
    // QuorumMaj.java
    private long half = 10;

    public boolean containsQuorum(Set<Long> set) {
        // 如果超过一半，则达到了标准
        // half = n/2, n为参与者，不包括observer
        return (set.size() > half);
    }

    // TODO: 什么是"脑裂"问题，zookeeper是如何解决的 ? ==> 使得集群中要么没有leader，要么只有一个leader !!
    // 一个集群中被分成了不只一个(网络挂掉)，根据发生领导者选举的条件(follower没有超过半数)
    // 则会造成没有小集群中重新发生领导者选举，使得每个小集群都有一个leader，相当于出现多个"大脑"
    // 原本一个完整的集群，不再是一个整体，对外处理请求，但是没有办法同步数据，保证数据一致性 !!
    //
    // zookeeper同时控制成为leader的条件是获得的票数必须严格过半(set.size() > half)
    // 即使出现分裂:
    // 1. 如果分裂出来的server比较少，则不会触发领导者选举，整个集群还是只有一个leader
    // 2. 如果分裂是平均划分，无论那个子集群中都不可能有server能够拿到超过半数的票，
    //    因此没有leader，不能对外提供服务

    // TODO: zookeeper为什么推荐要是奇数个server ?
    //    5台可以挂掉2台
    //    6台最多可以挂掉2台
    // 正常而言，启动两台(偶数结点)也可以构建集群，对外提供服务
    // 增加多余的server，能够提高"读请求"的性能(follower结点直接返回)，提高集群的吞吐量
    // 但是会减低写性能
    // 1. 由于follower会参与领导者选举，导致选举的过程增加
    // 2. 在处理"写请求"的时候，需要follower发送ack，这会导致时间增加

    // Observer观察者结点：
    // 不参与领导者选举，提高"读请求"的性能
    // 只会影响一点点"写请求"性能: 在leader做第二次提交时，会告诉observer同步数据，直接发送数包
    // inform(p); // 没有预提交，没有ack，只需要一步提交 !!

}
