package Redis_Basics.Redis_Performance;

// https://www.bilibili.com/video/BV1qJ411w7du
// ae_epoll.c单线程，多路复用的实现
// aeApiCreate() { epoll_create(); ... }
// aeApiPoll() { epoll_wait(); ... }
// aeApiDelEvent() { epoll_ctl(); ... }
public class RedisIOEpoll {
}
