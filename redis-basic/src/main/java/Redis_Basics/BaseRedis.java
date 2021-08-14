package Redis_Basics;

public class BaseRedis {

    // Redis服务端模型：单线程模型
    // Redis缓存：功能强大 + 性能(单机的QPS每秒处理几万请求) ===> 压力测试出请求量
    // 1. 不适合存储大量的信息, 只暂存关键的信息
    // 2. 操作是微秒级别的, 对于某些业务场景下，性能明显优于MySQL数据库
    // 3. 重构数据存储的方式，和业务逻辑的轻松实现(不同场景使用不同数据类型来实现)

    // 早期新浪微博后端使用redis实现，使用集群架构(单个结点挂了不影响)，配置Redis的容量为T级别
    // BAT后台的分布式缓存/中间件，基本使用自研的方式来实现，保证源码的可控性，容量扩展
    // WeChat这种级别的应用，不会使用Redis，难以修改，处理问题

    /**
     *
     *  // Redis is a cache, message broker, and richly-featured key-value store.
     *  // Redis是一个缓存，消息代理和功能丰富的键值存储
     *  // Redis的本质: key-value存储，内存中一张巨大的hash表，依赖于hash function，查询的效率很高 接近O(1)
     *  // redis数据结构的底层实现
     *  // redis如何实现高可用
     *  // redis的性能为何如此高 ?          https://mikechen.cc/779.html
     *
     *  // Redis集群搭建及原理              https://juejin.cn/post/6971243764765425677
     *  // Redis与MySQL双写一致性如何保证？  https://juejin.cn/post/6964531365643550751
     *  // Redis Cluster 原理实践篇        https://xie.infoq.cn/article/b272c96e7346ccbb402109ff2
     *  // 内存节省到极致的数据结构ziplist   https://juejin.cn/post/6992003200899350541
     *
     * redis数据结构有哪些？
     * Redis缓存穿透，缓存雪崩？
     * 如何使用Redis来实现分布式锁？
     * Redis的并发竞争问题如何解决？
     * Redis持久化的几种方式，优缺点是什么，怎么实现的？
     * Redis的缓存失效策略？
     * Redis集群，高可用，原理？
     * Redis缓存分片？
     * Redis的数据淘汰策略？
     * redis队列应用场景？
     * 分布式使用场景（储存session）？ Redis分布式/集群中Session共享的问题  https://www.cnblogs.com/lenve/p/10971384.html
     * Redis的数据结构是什么？线程模型说一下？
     * 讲讲Redis的数据淘汰机制？
     * 说说Redis的数据一致性问题？
     * Redis的分布式怎么做
     * redis的io多路复用
     */
}
