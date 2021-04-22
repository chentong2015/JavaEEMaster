package main;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * Spring Data Redis: part of Spring Data, offers low-level and high-level abstractions
 * 1. RedisTemplate: 高级抽象，用于执行Redis operations, 异常转换和序列化支持
 * 2. "Lettuce" and "Jedis": two popular open-source Java libraries for Redis.
 */
public class BaseRedis {

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
}
