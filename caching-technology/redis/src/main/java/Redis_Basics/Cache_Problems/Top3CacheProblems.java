package Redis_Basics.Cache_Problems;

import Redis_Basics.Cache_Problems.Bloom_Filter.DistributedBloomFilter;
import Redis_Basics.Cache_Problems.model.Order;
import Redis_Basics.Cache_Problems.service.OrderService;
import Spring_Data_Redis.SpringJedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Top3CacheProblems {

    StringRedisTemplate redisTemplate = SpringJedisConnection.getJedisStringTemplate();

    // 错误代码演示:
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

    // TODO: 缓存空对象的实现: 当查询不存在的数据时，在缓存中添加空对象做判断
    // 1. 空的对象会占用redis的内存空间
    // 2. 一旦超过过期时间，redis会将其清除，之后必须再次查询数据库
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

    // TODO: 布隆过滤器: 具有容错概率，一次误判则查询一次数据库，可以挡掉大部分的请求
    // 1. 占用的空间非常小，最多占用Redis 512M的内存
    // 2. 比较难以维护，数据库中的添加，则必须同步添加到布隆过滤器中 !!
    // 3. 布隆过滤器不能删除数据，别的数据同样受到影响，导致数据不一致
    private DistributedBloomFilter bloomFilter;

    // 在启动时将需要查询的表格中的id字段添加到BloomFilter中
    public void init() {
        List<Order> orders = OrderService.selectAllOrders();
        bloomFilter = new DistributedBloomFilter();
        for (Order order : orders) {
            bloomFilter.put(order.getId());
        }
    }

    // 直接先查询布隆过滤器中是否存在，解决缓存穿透
    public String findOrder3(int id) {
        if (!bloomFilter.isExist(id)) {
            return "can not find in the bloom filter";
        }
        // ...
        return "find nothing";
    }

    // TODO: 分布式锁
    // 测试使用JVM级别的锁代替分布式锁
    private Lock distributedLock = new ReentrantLock();

    public String findOrder4(int id) {
        if (!bloomFilter.isExist(id)) {
            return "can not find in the bloom filter";
        }
        Object cacheOrder = redisTemplate.opsForValue().get(String.valueOf(id));
        if (cacheOrder != null) {
            return "find order in cache";
        }
        // 添加一把分布式锁: 互斥锁，只有一个线程能够拿到，其他的线程阻塞直到拿到锁
        distributedLock.lock();
        try {
            // 双重检测:
            // 拿到锁之后，需要再次查询缓存，缓存可能被别的线程所修改，避免直接查询数据库
            cacheOrder = redisTemplate.opsForValue().get(String.valueOf(id));
            if (cacheOrder != null) {
                return "find order in cache";
            }
            Order order = OrderService.selectOrderById(id);
            if (order != null) {
                redisTemplate.opsForValue().set(String.valueOf(id), order.getName(), 10, TimeUnit.MINUTES);
                return "get order from db";
            }
        } finally {
            distributedLock.unlock();  // 最后必须要确保能够释放掉锁
        }
        return "find nothing";
    }
}

