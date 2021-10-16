package load_balance;

public class AlgoLoadBalance {

    // 根据什么来负载均衡
    // DNS负载均衡、IP负载均衡、"反向代理"负载均衡

    // 负载均衡算法 => 可以在客户端实现
    // 1.轮循
    // 2.加权轮循: 跟配置高、负载低的机器分配更高的权重; 而性能低、负载高的机器，配置较低的权重，让其处理较少的请求
    // 3.随机   : 系统随机函数，根据后台服务器列表的大小值来随机选取其中一台进行访问
    // 4.最少连接: 记录每个服务器正在处理的请求数，把新的请求分发到最少连接的服务器上
    // 5.源IP地址散列:
    //   根据服务消费者请求客户端的IP地址，通过哈希函数计算得到一个哈希值，
    //   将此哈希值和服务器列表的大小进行取模运算，得到的结果便是要访问的服务器地址的序号
}
