public class BaseDistributed {

    // 负载均衡算法
    // 1.轮循
    // 2.加权轮循: 跟配置高、负载低的机器分配更高的权重; 而性能低、负载高的机器，配置较低的权重，让其处理较少的请求
    // 3.随机   : 系统随机函数，根据后台服务器列表的大小值来随机选取其中一台进行访问
    // 4.最少连接: 记录每个服务器正在处理的请求数，把新的请求分发到最少连接的服务器上
    // 5.源IP地址散列: 根据服务消费者请求客户端的IP地址，通过哈希函数计算得到一个哈希值，
    //               将此哈希值和服务器列表的大小进行取模运算，得到的结果便是要访问的服务器地址的序号

    /**
     * 理解分布式, 负载均衡(DNS负载均衡、IP负载均衡、"反向代理"负载均衡), 连接池
     *
     * 开发高并发应用
     * cap了解吗，分别指什么？ http://www.ruanyifeng.com/blog/2018/07/cap.html
     * 强一致性和弱一致性有什么方法来实现的？
     * 负载均衡怎么实现？为什么这么做？
     *
     * 什么是CAP定理？
     *
     * 说说CAP理论和BASE理论？
     *
     * 什么是最终一致性？最终一致性实现方式？
     *
     * 什么是一致性Hash？
     *
     * 讲讲分布式事务？
     *
     * 如何实现分布式锁？
     *
     * 如何实现分布式 Session?
     *
     * 如何保证消息的一致性?
     *
     * 负载均衡的理解？
     *
     * 正向代理和反向代理？
     *
     * CDN实现原理？
     *
     * 怎么提升系统的QPS和吞吐？
     *
     * Dubbo的底层实现原理和机制？  ===> Dubbo是SOA服务治理方案的核心框架
     *
     * 描述一个服务从发布到被消费的详细过程？
     *
     * 分布式系统怎么做服务治理？
     *
     * 消息中间件如何解决消息丢失问题？
     *
     * Dubbo的服务请求失败怎么处理？
     *
     * 对分布式事务的理解？
     *
     * 如何实现负载均衡,有哪些算法可以实现?
     *
     * Zookeeper的用途,选举的原理是什么?
     *
     * 讲讲数据的垂直拆分水平拆分？
     */
}
