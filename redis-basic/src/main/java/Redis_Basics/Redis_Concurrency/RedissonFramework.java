package Redis_Basics.Redis_Concurrency;

import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.config.Config;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;

// Redis Java Client 客户端解决分布式锁的框架 github.com/redisson/redisson/wiki/目录
// 1. Distributed Java Objects
// 2. Distributed Java Locks and synchronizers
// 3. Distributed Java Services
public class RedissonFramework {

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

    // TODO: 使用分线程"动态控制"锁的超时时间，给锁延续更正时间
    // TODO: 性能问题: 把并行的逻辑串行化，没有并发的问题     ===> 使用分段锁进行优化，不同的请求选择不同的段位，实现并发执行请求 !!
    public String testDistributedLockPlus2() {
        String key = "lockKey";
        RLock redissonLock = redisson.getLock(key); // 生成一个Redisson锁对象
        try {
            // 多个线程只有一个线程能够获得锁，其他的线程会原地等待(自旋)，在锁被释放之后进行争抢 !!
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
