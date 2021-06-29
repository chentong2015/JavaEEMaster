import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Spring Data Redis: part of Spring Data, offers low-level and high-level abstractions
 * 1. RedisTemplate: 高级抽象，用于执行Redis operations, 异常转换和序列化支持
 * 2. "Lettuce" and "Jedis": two popular open-source Java libraries for Redis.
 */
public class BaseRedis {

    // Redis的本质: key-value存储，内存中一张巨大的hash表，依赖于hash function，查询的效率很高 接近O(1)

    /**
     * redis 入门 ===> https://github.com/spring-projects/spring-data-redis
     * 1. NoSQL数据库
     * 2. Redis is a cache, message broker, and richly-featured key-value store. Redis是一个缓存，消息代理和功能丰富的键值存储
     */

    // inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // inject the template as ListOperations
    // @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

    /**
     *
     public void addLink(String userId, URL url) {
     listOps.leftPush(userId, url.toExternalForm());
     } */

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
