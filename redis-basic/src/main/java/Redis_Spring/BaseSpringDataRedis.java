package Redis_Spring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * TODO: Spring Data Redis官方文档: https://github.com/spring-projects/spring-data-redis
 * Spring Data Redis  : Spring Data数据的一部分, offers low-level and high-level abstractions
 * 1. RedisTemplate   : 高级抽象，用于执行Redis operations, 异常转换和序列化支持
 * 2. Jedis & Lettuce : Open-source Java libraries for Redis
 */
public class BaseSpringDataRedis {

    // Inject the actual template
    @Autowired
    private RedisTemplate<String, String> template;

    // Inject the template as ListOperations
    // listOps.leftPush(String userId, URL url.toExternalForm());
    // @Resource(name = "redisTemplate")
    private ListOperations<String, String> listOps;

}
