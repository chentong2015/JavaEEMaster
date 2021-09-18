package Redis_Basics.Redis_Performance;

// Linux IO多路复用核心原理:
// https://blog.csdn.net/XueyinGuo/article/details/113096163
public class RedisIOEpoll {

    // IO多路复用解决的问题
    // int iResult = recv(socket_fd, buffer, 1024);
    // 1. 一个server只能连接一个socket套接字
    // 2. 永久阻塞IO，只有一个client socket能够连接成功
}
