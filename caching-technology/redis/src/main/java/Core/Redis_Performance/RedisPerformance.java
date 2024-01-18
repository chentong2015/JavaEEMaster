package Core.Redis_Performance;

// Redis读写都是单线程，性能为何如此高 ?
// 1. 纯内存(核心原因)，基于内存操作要比使用磁盘高效得多
// 2. 网络IO(设计优势)
// 3. key-value高效数据存储结构(查询效率约O(1)), hash具有良好的分布性
// 4. C语言实现，语言更接近操作系统，执行速度相对会更快
// 5. 单线程(模型)避免多线程的频繁上下文切换问题，预防多线程可能产生的竞争问题
public class RedisPerformance {

    // 网络IO优势
    // 1. get建立连接, 监听TCP端口6379
    //    connectionSocket = accept($listenSocket); 建立连接，涉及IO
    //    read(connectSocket);  从客户端读(内存数据的执行)
    //    write(connectSocket); 返回给客户端
    // 2. I/O多路复用: 每建立的一个连接都会对系统开销有影响
    //    TODO: 针对"网络IO"的"多个TCP连接"，"复用一个线程"来处理多个连接请求
    //    IO的模型(底层操作系统函数，逐渐升级的一个过程):
    //       select: 遍历
    //       poll  : 遍历
    //       epoll : Redis选择的模型，本质上其实是一个"事件驱动，通知"
    //    每个请求来时，会注册套接字到epoll中(放到队列中)，然后通知，返回数据给客户端
    // 3. 非阻塞IO: 相当于对请求做一个"报道"，之后去做自己的事情
    //    轮询查看是否数据准备好
    //    或者接收通知(事件机制)，epoll的逻辑
}
