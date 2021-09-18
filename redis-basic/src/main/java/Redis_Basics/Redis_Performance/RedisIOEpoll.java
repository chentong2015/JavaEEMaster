package Redis_Basics.Redis_Performance;

// Linux IO多路复用核心原理: https://blog.csdn.net/XueyinGuo/article/details/113096163
// aeApiCreate() { epoll_create(); ... }
// aeApiPoll() { epoll_wait(); ... }
// aeApiDelEvent() { epoll_ctl(); ... }
public class RedisIOEpoll {
    
}
