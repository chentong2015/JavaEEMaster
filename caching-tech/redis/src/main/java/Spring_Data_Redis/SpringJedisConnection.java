package Spring_Data_Redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

// Spring Data Redis: 集成了Lettuce和Jedis
// https://github.com/spring-projects/spring-data-redis
public class SpringJedisConnection {

    public static void main(String[] args) {
        // RedisConnection提供redis不同数据结构的操作
        RedisConnection connection = getJedisConnection();
        connection.stringCommands().set("chentong".getBytes(), "20210".getBytes());
        byte[] value = connection.stringCommands().get("chentong".getBytes());
        System.out.println(value);
        connection.close();

        // 继承自RedisTemplate<String, String>，提供一系列操作接口
        RedisTemplate<String, String> redisTemplate = getJedisStringTemplate();
        StringRedisTemplate stringRedisTemplate = getJedisStringTemplate();
        stringRedisTemplate.opsForValue().set("ctong", "20221", 30, TimeUnit.MINUTES);
        System.out.println(stringRedisTemplate.opsForValue().get("chentong"));
        System.out.println(stringRedisTemplate.opsForValue().get("ctong"));
    }

    public static RedisConnection getJedisConnection() {
        return getRedisConnectionFactory().getConnection();
    }

    public static RedisConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("8.209.74.47", 6379);
        return new JedisConnectionFactory(config);
    }

    public static StringRedisTemplate getJedisStringTemplate() {
        RedisConnectionFactory factory = getRedisConnectionFactory();
        return new StringRedisTemplate(factory);
    }
}
