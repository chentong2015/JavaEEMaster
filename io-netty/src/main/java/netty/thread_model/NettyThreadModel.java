package netty.thread_model;

// TODO: Netty线程模型，响应式编程模型<<Scalable IO in Java>>
// 1. 支撑上百万的连接, 强于Redis(10万)
// 2. 两个EventLoopGroup线程池，一组多从
//    EventLoopGroup bossGroup = new NioEventLoopGroup(1); 只处理连接请求
//    EventLoopGroup workerGroup = new NioEventLoopGroup(8); 处理客户端业务，线程数默认cpu核数2倍
public class NettyThreadModel {

    // 1. 如何实现这样一个线程模型 ?
    // 2. Redis底层为什么不使用netty来通讯 ?
}
