package Spring_Data_Redis;

import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;

public class SpringLettuceConnection {

    // 必须添加Lettuce依赖
    public static RedisConnectionFactory getLettuceConnectionFactory() {
        RedisStandaloneConfiguration config = new RedisStandaloneConfiguration("8.209.74.47", 6379);
        return new LettuceConnectionFactory(config);
    }

    public static RedisConnection getLettuceConnection() {
        return getLettuceConnectionFactory().getConnection();
    }
}
