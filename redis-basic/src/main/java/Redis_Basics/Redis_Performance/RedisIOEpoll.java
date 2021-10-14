package Redis_Basics.Redis_Performance;

// Linux IO多路复用核心原理:
// https://blog.csdn.net/XueyinGuo/article/details/113096163
public class RedisIOEpoll {

    // IO多路复用解决的问题
    // int iResult = recv(socket_fd, buffer, 1024);
    // 1. 一个server只能连接一个socket套接字
    // 2. 永久阻塞IO，只有一个client socket能够连接成功

    // Redis C语言源码的实现 ae_epoll.c ==> NIO的多路复用
    // Redis在启动时会调用的函数
    // static int aeApiCreate(aeEventLoop *eventLoop) {
    //    ...
    //    调用Linux内核函数，使用C语言创建了epoll的实例，里面放置数据
    //    这里设置了1024个channel，连接数目
    //    state->epfd = epoll_create(1024);
    //    ...
    // }
    //
    // static int aeApiAddEvent(aeEventLoop *eventLoop, int fd, int mask) {
    //   ...
    //   监听Channel注册过来的事件
    //   if(epoll_ctl(state->epfd,op,fd,&ee) == -1) return -1;
    //   ...
    // }
    //
    // static int aeApiPoll(aeEventLoop *eventLoop, struct timeval *tvp) {
    //   ...
    //   retval = epoll_wait(...); 事件的监听或者阻塞
    //   ...
    // }
}
