package Redis_Basics.Redis_Memory_Cache;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RReadWriteLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

public class RedisCacheProblems {

    // Redis缓存问题
    // 1. 存储无底洞
    // 2. 缓存穿透
    // 3. 缓存雪崩
    // 4. 缓存失效
    // 5. 热点key倾斜
    // 6. 热点key重建

    // 7. 缓存和数据库双写不一致(在数据库持久化的同时，导致和缓存中的数据不一致)
    //    Thread 1 -> update DB stock=10  -----> DB卡顿  ------> update Cache stock=10 并发情况写数据库操作不是原子操作 !!
    //    Thread 2 -> update DB stock=6 -> update Cache stock=6
    //
    //    Thread 1 -> update DB stock=10 -> Delete Cache
    //    Thread 2 -----------------------------> update DB stock=6  -> Delete Cache
    //    Thread 2 ------> check cache ---> search DB stock=10  -----> 卡顿  ----->  update Cache stock=10
    //
    //    错误方案：延迟双删(间隔之后删除再次cache缓存)，
    //            内存队列(针对不同的商品的操作进行串行化执行)，
    //            加分布式锁(将并发改成串行执行，性能不行)
    //    解法方案：
    //    1. 分布式读写锁：读锁(不互斥) + 写锁(互斥)  ==> "读多写少"业务场景
    //    2. 设置缓存超时时间 expire key 30s       ==> "读多写多"业务场景，不强制要求"数据库"和"缓存"中的值一致
    //    3. 不用缓存, 避免大量更新缓存的操作         ==> "读多写多"业务场景，要求"数据库"和"缓存"严格一致
    //    4. TODO: 使用Canal中间件来优化第3种解决方案 / 使用支持高并发的数据库, 分布式数据库TiDB

    @Autowired
    Redisson redisson;
    private String lockKey = "keyProduct101";
    private RReadWriteLock readWriteLock = redisson.getReadWriteLock(lockKey);
    StringRedisTemplate stringRedisTemplate = new StringRedisTemplate();

    // 背后实现逻辑: set key -> mode(read), value 对于添加的锁设置一个读写的模式
    @RequestMapping("/getStock")
    public String getStock(@RequestParam("clientId") Long clientId) throws InterruptedException {
        RLock readLock = readWriteLock.readLock();
        readLock.lock();
        String stock = stringRedisTemplate.opsForValue().get("stock");
        if (StringUtil.isEmpty(stock)) {
            System.out.println("Search database 10");
            Thread.sleep(5000);
            stringRedisTemplate.opsForValue().set("stock", "10");
        }
        readLock.unlock();
        return "end";
    }

    @RequestMapping("/updateStock")
    public String updateStock(@RequestParam("clientId") Long clientId) throws InterruptedException {
        RLock writeLock = readWriteLock.writeLock();
        writeLock.lock();
        stringRedisTemplate.delete("stock");   // 写: 更改Redis缓存
        Thread.sleep(5000);
        writeLock.unlock();
        return "end";
    }
}
