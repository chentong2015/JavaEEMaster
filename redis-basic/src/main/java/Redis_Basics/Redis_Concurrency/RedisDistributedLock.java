package Redis_Basics.Redis_Concurrency;

import org.springframework.data.redis.core.StringRedisTemplate;

// Redis分布式锁场景
// 1. 互联网秒杀
// 2. 抢优惠券
// 3. 接口幂等性校验
public class RedisDistributedLock {

    // 1. 单体上部署Web App, 在一个tomcat中部署应用

    // 2. 分布式集群架构部署
    //           前端的用户访问的是负载均衡的设备
    //        http://192.168.0.60/deduct_stock
    //                nginx 负载均衡(设备)        ===> 分发请求到不同的部署上面
    //         tomcat1            tomcat2       ===> Web引用部署在多个Web Container中
    //      localhost:8080     localhost:8090   ===> 不同的部署会有不同的进程，不同的JVM  ==> TODO: Java(JDK)内置锁不能跨越进程
    //                    Redis

    // 在Spring层面封装了Redis的使用模板: StringRedisTemplate针对(Key->String)类型的数据结构进行存储
    public void testRedisTemplate() {
        // 这里添加的锁在高并发的场景下失效 !!
        synchronized (this) {
            StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();
            // jedis.get("stock");
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                int newStock = stock - 1;
                // jedis.set(key, value);
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(newStock));
            }
        }
    }
}
