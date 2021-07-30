import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

// Redis is a cache, message broker, and richly-featured key-value store. Redis是一个缓存，消息代理和功能丰富的键值存储
// Redis的本质: key-value存储，内存中一张巨大的hash表，依赖于hash function，查询的效率很高 接近O(1)
// redis数据结构的底层实现
// redis如何实现高可用
// redis的性能为何如此高

// Redis集群搭建及原理   https://juejin.cn/post/6971243764765425677
// Redis与MySQL双写一致性如何保证？ https://juejin.cn/post/6964531365643550751
// Redis Cluster 原理实践篇 https://xie.infoq.cn/article/b272c96e7346ccbb402109ff2
public class BaseRedis {

    /**
     * TODO: 如何在Spring中使用Redis https://github.com/spring-projects/spring-data-redis
     * Spring Data Redis: part of Spring Data, offers low-level and high-level abstractions
     * 1. RedisTemplate: 高级抽象，用于执行Redis operations, 异常转换和序列化支持
     * 2. "Lettuce" and "Jedis": two popular open-source Java libraries for Redis.
     */
    // Inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // Inject the template as ListOperations
    // @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;
    // listOps.leftPush(String userId, URL url.toExternalForm());

    /**
     * redis数据结构有哪些？
     * Redis缓存穿透，缓存雪崩？
     * 如何使用Redis来实现分布式锁？
     * Redis的并发竞争问题如何解决？
     * Redis持久化的几种方式，优缺点是什么，怎么实现的？
     * Redis的缓存失效策略？
     * Redis集群，高可用，原理？
     * Redis缓存分片？
     * Redis的数据淘汰策略？
     * redis队列应用场景？
     * 分布式使用场景（储存session）？
     */
}
