package zookeeper.consistency;

import java.util.Set;

// 领导者选举机制
// 1. 投票: 投自己 -> 沟通比较 -> PK改票
// 2. 选票
// 3. 投票箱
// 4. 过半机制(zookeeper)
public class LeaderSelection {

    // TODO: 什么时候发生领导者选举 ?
    // 1. 集群启动
    //    zookeeper PK: 数据越新则越强，比较Server的事务日志zxid
    //    如果zxid相同或者没有，则PK无效，则使用serverId来比较，越大越强(越后面)
    // 2. leader挂掉
    //    使用同样的PK机制进行分析
    // 3. follower挂掉导致leader没有超过半数的follower
    //    让整个集群不能提供服务

    // TODO: 什么是"脑裂"问题，zookeeper是如何解决的 ?
    // 一个集群中被分成了不只一个，每个小集群都有一个leader，相当于出现了多个"大脑"
    // 原本一个完整的集群，没有办法同步数据，保证数据一致性 !!

    // https://www.bilibili.com/video/BV1Rf4y1y7xE?p=82

    // 过半机制: 可以设置等到集群中所有的server都启动完成之后，再选举leader
    // QuorumMaj.java
    private long half = 10;

    public boolean containsQuorum(Set<Long> set) {
        // 如果超过一半，则达到了标准
        // half = n/2, n为参与者，不包括observer
        // TODO: 这里必须大于，否则会产生"脑裂"问题
        return (set.size() > half);
    }


}
