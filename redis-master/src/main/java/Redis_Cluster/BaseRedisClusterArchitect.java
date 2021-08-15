package Redis_Cluster;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

// TODO: Redis自身和高并发是不一致的(将并发转成串行执行的代码)，虽然单体的性能不错，但是如何最多限度的提升性能 ? (面试题)
// Redis分布式集群方案比较: 部署在多个服务器
// Web --->  Redis Master 主服务
//           Redis Slave  从服务(可能需要人工运维干预)

// TODO: 在分布式锁场景下，在主从架构(哨兵架构)中如何解决锁的同步性 ? (面试题)  ==> 如果主从失效，能够容忍 & 使用Zookeeper分布式结构
//       Redisson  ->  Redis(Master)  ->  Redis(Slave) / Redis(Slave)
// 如果redisson在主redis中加了一把锁(设置一个key)，则从结点一般需要将key"同步/异步复制"过去
// 如果key刚好设置到redis主结点，然后redis主结点挂了
// 1. 将从结点重新切换成主结点，新的结点没有key
// 2. 新来的线程如果访问新的结点，发现没有锁，则会继续执行
public class BaseRedisClusterArchitect {

    // 1. Guard哨兵模式(中小型企业)
    //    client
    //     哨兵   --监控-->  master
    //        监控     监控      监控
    //          slave  ...    slave 从服务器，同步数据，顶替master
    //    哨兵监控服务的作用: 故障检测，自动故障转移，避免人工干预
    //    如果哨兵挂掉：可以做多个，如果一个挂了，前端的负载均衡可以找另一个哨兵
    //    并发缺点：对外提供的写只有一台，在挂掉的时候切换时间内"访问瞬断"的情况

    // TODO: 伪分布式/伪集群，在一台机器上搭建3主3从的Redis实例，模拟分布式场景
    // 2. 高可用集群模式(相对的)
    //           client         client
    //                JedisCluster
    //          master           master          master         至少要3个master结点(选举必须是单数)，最多集群支持1000台...
    //      slave  slave     slave  slave     slave   salve     多个集群的数据是分开
    // 备注: 1. 在小集群中"瞬断切换"的时候，也是相对的高可用
    //      2. 对集群做水平扩展或者减小一个集群，比较麻烦
    //
    // 数据操作验证/集群内部会重定向到不同的master机器上执行(类似于轮询的负载均衡的算法)
    // 127.0.0.1:8001> set name chentong
    // -> Redirected to slot [5798] located at 127.0.0.1:8002
    //    OK
    // 根据key来定位存储位置, 每个master结点存储的数据是分开存储的，数据分片，不重复
    // 集群之间有通讯原理

    // 测试单体Redis Server服务的操作
    private static void testSingleRedisServer() {
        Jedis jedis = new Jedis("8.209.74.47", 6379);
        // jedis.auth("password");  使用配置文件中自定义的authentication password进行身份认证
        // jedis.auth("root", "requirepass");
        jedis.set("key1", "my key");
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    // 测试使用Jedis连接池访问单体Redis服务
    private static void testSingleRedisServerWithJedisPool() {
        JedisPool pool = new JedisPool("8.209.74.47", 6379);
        Jedis jedis = pool.getResource();
        System.out.println(jedis.get("name"));
        jedis.close();
        pool.close();
    }

    // 测试Redis Cluster集群
    private static void testRedisCluster() {
        Set<HostAndPort> jedisClusterNodes = new HashSet<>();
        jedisClusterNodes.add(new HostAndPort("8.209.74.47", 8001));
        jedisClusterNodes.add(new HostAndPort("8.209.74.47", 8002));
        jedisClusterNodes.add(new HostAndPort("8.209.74.47", 8003));
        jedisClusterNodes.add(new HostAndPort("8.209.74.47", 8004));
        jedisClusterNodes.add(new HostAndPort("8.209.74.47", 8005));
        jedisClusterNodes.add(new HostAndPort("8.209.74.47", 8006));
        // Jedis连接池的基本配置
        JedisPoolConfig config = new JedisPoolConfig();
        config.setMaxTotal(100);
        config.setMaxIdle(10);        // 设置最大空闲
        config.setTestOnBorrow(true); // 设置借用测试

        JedisCluster jedisCluster = new JedisCluster(jedisClusterNodes, 5000, 10, config);
        jedisCluster.set("firstname", "chen");
        jedisCluster.set("age", "27");
        System.out.println(jedisCluster.get("firstname"));
        System.out.println(jedisCluster.get("age"));
        jedisCluster.close();
    }
}
