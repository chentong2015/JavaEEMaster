package zookeeper.cap;

// TODO: CAP理论只能满足CP或者AP
// Consistency: 一致性(强一致性)
// Availability: 可用性
// Partition Tolerance: 分区容错性
public class BaseCAP {

    // 数据一致性
    // 1. 强一致性: 数据严格一致，保证同步
    //    由于同步需要时间，因此有两种解决办法：
    //    > 提高同步的速度
    //    > 不能及时响应请求，必须等到同步完成之后 ==> 使用分布式锁，阻塞请求，影响了"可用性" !!
    // 2. 弱一致性: 数据不一定一致
    // 3. 最终一致性: 数据最终一致

    // 1. 取决于集群需要保证是什么一致性
    // 2. Zookeeper做到的是最终一致性

    // 分区容错性
    // 将单体架构拆分成"分布式架构", 将原来系统中的模块拆分出来成独立系统
    // 如何保证新的系统在不同部署(网络沟通)下构成整体, 提高容错性 ?
}
