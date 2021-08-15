// Redis缓存高并发架构实战 https://www.bilibili.com/video/BV1PA411V7U3

// Redis 应用场景
// 1. 缓存
//    存储器      硬件介质   随机访问延时
//    L1 cache   SRAM       1ns
//    L2 cache   SRAM       4ns
//    Memory     DRAM       100ns    内存     ==> Redis存储在内存
//    Disk       SSD        150us    固态硬盘  ==> DB数据库
//    Disk       HDD        10ms     机械硬盘
// 2. 分布式计数器: 对String进行频繁的自增和自减
// 3. 分布式ID生成器: 一次请求一个大一点的步长incr 2000
// 4. 海量数据存储: bitmap
// 5. 会话缓存: 统一存储多态服务器的会话信息
// 6. 分布式队列，阻塞队列
// 7. 分布式锁
// 8. 热点数据存储，排行榜
public class RedisMaster {

}
