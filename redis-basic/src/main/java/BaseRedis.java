public class BaseRedis {

    // 单线程模型, 底层使用C++实现/C语言实现
    // 分布式的缓存中间件：功能强大 + 性能(单机的QPS每秒处理几万请求)
    // 1. 不适合存储大量的信息，只暂存关键的信息
    // 2. 操作是微秒级别的，对于某些业务场景下，性能明显优于MySQL数据库
    // 3. 重构数据存储的方式，和业务逻辑的轻松实现(不同场景使用不同数据类型来实现)

    // Redis应用场景
    // 1. 缓存
    //    存储器      硬件介质   随机访问延时
    //    L1 cache   SRAM       1ns      CPU的1级缓存
    //    L2 cache   SRAM       4ns
    //    Memory     DRAM       100ns    内存     ==> Redis存储在内存
    //    Disk       SSD        150us    固态硬盘  ==> DB数据库
    //    Disk       HDD        10ms     机械硬盘
    // 2. 分布式计数器: 对String进行频繁的自增和自减
    // 3. 分布式ID生成器: 一次请求一个大一点的步长incr 2000
    // 4. 海量数据存储: bitmap
    // 5. 会话缓存: 统一存储多态服务器的会话信息
    // 6. 分布式队列，阻塞队列
    // 7. 分布式锁: 互联网秒杀, 抢优惠券
    // 8. 热点数据存储，排行榜

    // 早期新浪微博后端使用redis实现，使用集群架构(单个结点挂了不影响)，配置Redis的容量为T级别
    // BAT后台的分布式缓存/中间件，基本使用自研的方式来实现，保证源码的可控性，容量扩展
    // WeChat这种级别的应用，不会使用Redis，难以修改，处理问题
    

    // TODO: Redis读写都是单线程，但性能为何如此高 ? Redis 6.0多线(执行用户请求的还是单线程) ?
    // 1. 底层是基于高效的数据存储结构来实现的
    // 2. 多路复用 ?? https://mikechen.cc/779.html
    //    IO多路复用select/poll/epoll介绍  https://www.bilibili.com/video/BV1qJ411w7du
    //    ae_epoll.c 单线程，多路复用的实现
    //    底层操作系统函数/模型：
    //    aeApiCreate() { epoll_create(); ... }
    //    aeApiPoll() { epoll_wait(); ... }
    //    aeApiDelEvent() { epoll_ctl(); ... }

    /**
     * Redis集群搭建及原理              https://juejin.cn/post/6971243764765425677
     * Redis与MySQL双写一致性如何保证？   https://juejin.cn/post/6964531365643550751
     * Redis Cluster 原理实践篇        https://xie.infoq.cn/article/b272c96e7346ccbb402109ff2
     * 内存节省到极致的数据结构ziplist    https://juejin.cn/post/6992003200899350541
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
     * redis如何实现高可用
     */
}
