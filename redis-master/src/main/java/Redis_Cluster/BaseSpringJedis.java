package Redis_Cluster;

import redis.clients.jedis.*;

import java.util.HashSet;
import java.util.Set;

// Jedis & Lettuce: Redis Java Client, Open-source Java libraries for Redis
// 使用开源类库Jedis操作搭建的Redis集群
public class BaseSpringJedis {

    public static void main(String[] args) {
        testSingleRedisServerWithJedisPool();
    }

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
