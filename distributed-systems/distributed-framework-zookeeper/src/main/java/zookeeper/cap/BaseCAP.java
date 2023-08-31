package zookeeper.cap;

// TODO: CAP理论只能满足CP或者AP  ==> Zookeeper保证的是CP，本身不保证A
// Consistency: 一致性(强一致性)
// Availability: 可用性
// Partition Tolerance: 分区容错性

// BASE理论:
// Basically Available
// Soft state
// Eventually consistent
// 强一致性(Strong consistency)无法得到保障时(分区容错和可用性满足系统)
// 每个应用都可以根据自身的业务特点, 采用适当的方式来达到最终一致性(Eventual consistency)
public class BaseCAP {

    // 数据一致性
    // 1. 强一致性: 数据严格一致，保证同步，但是同步需要时间
    //    1.1 提高同步的速度
    //    1.2 不能及时响应请求，必须等到同步完成之后 ==> 使用分布式锁阻塞请求，影响"可用性"
    // 2. 弱一致性: 数据不一定一致
    // 3. 最终一致性: 需要等待一定的时间，达到数据最终一致

    // TODO: 通过"ZAB协议(定义)"来保证数据一致性问题 !!
    // 1. 需要leader: 选举机制
    // 2. 过半机制   : 大多数机制
    // 3. 两阶段提交 : 2PC(Two-phase commit)
    // 4. 数据同步

    // 分区容错性
    // 将单体架构拆分成"分布式架构", 将原来系统中的模块拆分出来成独立系统
    // 如何保证新的系统在不同部署(网络沟通)下构成整体, 提高容错性 ?
}
