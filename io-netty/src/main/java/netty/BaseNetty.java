package netty;

// Netty
// 1. 底层使用NIO通讯，在NIO上面做了大量的封装
// 2. 客户端服务端IO通讯的框架(程序)，中间件
// 3. 性能非常高效，可以集群
// https://netty.io/wiki/user-guide-for-4.x.html
public class BaseNetty {

    // 链路有效监测:
    // 1. 心跳检测机制
    // 2. 心跳机制
    // 3. 重连机制
    // 内存保护机制
    // 1. 链路总数控制
    // 2. 单个缓冲区的上限控制
    // 3. 缓冲区内存释放
    // 4. 消息发送队列长度上限控制

    // Netty主从Reactor高并发线程模型
    // Netty线程模型，什么是零拷贝？
    // 实现了时间轮 HashedWheelTimer.java
    // https://github.com/netty/netty/blob/4.1/common/src/main/java/io/netty/util/HashedWheelTimer.java
}
