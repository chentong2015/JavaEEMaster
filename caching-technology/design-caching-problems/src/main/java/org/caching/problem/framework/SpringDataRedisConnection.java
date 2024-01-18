package org.caching.problem.framework;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

public class SpringDataRedisConnection {

    public static RedisConnection getJedisConnection() {
        return getRedisConnectionFactory().getConnection();
    }

    public static StringRedisTemplate getJedisStringTemplate() {
        RedisConnectionFactory factory = getRedisConnectionFactory();
        return new StringRedisTemplate(factory);
    }

    public static RedisConnectionFactory getRedisConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("8.209.74.47", 6379);
        return new JedisConnectionFactory(config);
    }


}
