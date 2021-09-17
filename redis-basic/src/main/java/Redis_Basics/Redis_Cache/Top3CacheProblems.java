package Redis_Basics.Redis_Cache;

import Redis_Basics.Redis_Cache.model.Order;
import Redis_Basics.Redis_Cache.service.OrderService;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.concurrent.TimeUnit;

// Redis缓存三大问题:
// 1. 缓存穿透: 查询一个根本不存在的数据，数据库中没有，缓存中也不可能有，导致始终会走数据库 !!
//    1.1 缓存空对象: 当查询不存在的数据时，在缓存中添加空对象做判断
//    1.2 布隆过滤器: Google框架单机版本，<com.google.guava>，BloomFilter
// 2. 缓存击穿:
// 3. 缓存雪崩:
public class Top3CacheProblems {

    StringRedisTemplate redisTemplate = new StringRedisTemplate();

    // 错误代码演示：
    public String findOrder(int id) {
        // 1. 查询缓存
        Object cacheOrder = redisTemplate.opsForValue().get(String.valueOf(id));
        if (cacheOrder != null) {
            return "find order in cache";
        }
        // 2. 查询数据库
        Order order = OrderService.selectOrderById(id);
        if (order != null) {
            // 3. 加入缓存
            redisTemplate.opsForValue().set(String.valueOf(id), order.getName());
            return "get order from db";
        }
        return "find nothing";
    }

    // TODO: 缓存空对象的实现
    // 1. 一旦超过过期时间，redis会将其清除，之后又必须查询数据库
    // 2. 空的对象会占用redis的内存空间
    public String findOrder2(int id) {
        // 1. 查询缓存
        Object cacheOrder = redisTemplate.opsForValue().get(String.valueOf(id));
        if (cacheOrder != null) {
            // 判断查询的是否是缓存的空对象, 如果是，则不用再查询数据库
            // if(cacheOrder is null object) {
            //    return "find nothing";
            // }
            return "find order in cache";
        }
        // 2. 查询数据库
        Order order = OrderService.selectOrderById(id);
        if (order != null) {
            // 3. 加入缓存
            redisTemplate.opsForValue().set(String.valueOf(id), order.getName(), 10, TimeUnit.MINUTES);
            return "get order from db";
        } else {
            // 如果在数据库中没有查到，则设置空对象到缓存中
            redisTemplate.opsForValue().set(String.valueOf(id), "null object", 10, TimeUnit.MINUTES);
        }
        return "find nothing";
    }

    // TODO: 布隆过滤器

}
