package Redis_Basics.Redis_Concurrency;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

// http://redisson.org
// Redis Java Client 客户端解决分布式锁的框架
// 1. Distributed Java Objects
// 2. Distributed Java Locks and synchronizers
// 3. Distributed Java Services
public class RedissonFramework {

    // TODO: 在分布式锁场景下，在主从架构(哨兵架构)中如何解决锁的同步性 ? (面试题)
    //       Redisson  ->  Redis(Master)  ->  Redis(Slave)
    //                                    ->  Redis(Slave)
    // 如果redisson在主redis中加了一把锁(设置一个key)，则从结点一般需要将key"同步"过去
    // 如果key刚好设置到redis主结点，然后redis主结点挂了
    // 1. 将从结点重新切换成主结点，新的结点没有key
    // 2. 新来的线程如果访问新的结点，发现没有锁，则会继续执行

    // 在SpringBoot项目中依赖注入(容器)，初始化Redisson
    @Bean
    public Redisson redisson() {
        Config config = new Config();
        config.useSingleServer().setAddress("redis://localhost:6379").setDatabase(0);
        // 使用集群redis
        // config.useClusterServers()
        //         .addNodeAddress("redis://19.168.0.61:8001")
        //         .addNodeAddress("redis://19.168.0.61:8002");
        return (Redisson) Redisson.create(config);
    }

    // 直接从依赖注入的容器中取出注入的实例
    @Autowired
    Redisson redisson;
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();

    public String testDistributedLockPlus2() {
        String key = "lockKey";
        RLock redissonLock = redisson.getLock(key); // 生成一个Redisson锁对象
        try {
            // TODO: 多个线程只有一个线程能够获得锁，其他的线程会原地等待(自旋)，在锁被释放之后进行争抢
            // 核心是通过分线程+Timer来实现，Lua脚本源代码(原子代码块)
            // 底层的实现 jedis.setnx(key, threadIdValue, 30, TimeUnit.SECONDS) 默认锁30秒
            redissonLock.lock();
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock - 1));
            }
        } finally {
            redissonLock.unlock();
        }
        return "Success";
    }
}
