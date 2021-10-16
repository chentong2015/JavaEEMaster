package netty;

// Netty: https://netty.io/wiki/user-guide-for-4.x.html
// 1. 底层使用NIO通讯，在NIO上面做了大量封装和性能优化
// 2. 客户端服务端IO通讯的框架(程序)，中间件
// 3. 性能非常高效，可以集群
public class BaseNetty {

    // TODO: Netty线程模型，响应式编程模型<<Scalable IO in Java>>
    // 1. 支撑上百万的连接, 强于Redis(10万)
    // 2. 两个EventLoopGroup线程池，一主多从
    //    EventLoopGroup bossGroup = new NioEventLoopGroup(1); 只处理连接请求
    //    EventLoopGroup workerGroup = new NioEventLoopGroup(8); 处理客户端业务，线程数默认cpu核数2倍
    //    NioEventLoop 使用多个来分散对事件的处理，支撑高并发(多事件)

    // TODO: Netty之于NIO的性能优化
    // 1. 借助NIO多路复用非阻塞
    // 2. 主从Reactor线程模型
    // 3. 无锁串行化设计思想
    // 4. 支持高性能序列化协议
    // 5. 零拷贝机制
    // 6. ByteBuf内存池设计，扩容机制
    // 7. TCP参数配置
    // 8. 并发优化
    // 9. 心跳机制: 保持客户端和服务端的连接，重连

    // 内存保护机制
    // 1. 链路总数控制
    // 2. 单个缓冲区的上限控制
    // 3. 缓冲区内存释放
    // 4. 消息发送队列长度上限控制
}
