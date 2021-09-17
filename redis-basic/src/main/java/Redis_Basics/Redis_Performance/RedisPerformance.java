package Redis_Basics.Redis_Performance;

// TODO: Redis读写都是单线程，但性能为何如此高 ? Redis 6.0多线(执行用户请求的还是单线程) ?
// 1. 底层是基于高效的数据存储结构来实现的
// 2. 多路复用 ?? https://mikechen.cc/779.html
//    IO多路复用select/poll/epoll介绍  https://www.bilibili.com/video/BV1qJ411w7du
//    ae_epoll.c 单线程，多路复用的实现
//    底层操作系统函数/模型：
//    aeApiCreate() { epoll_create(); ... }
//    aeApiPoll() { epoll_wait(); ... }
//    aeApiDelEvent() { epoll_ctl(); ... }

public class RedisPerformance {
}
