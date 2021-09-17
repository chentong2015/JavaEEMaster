package Redis_Master.Redis_Concurrency;

import Spring_Data_Redis.SpringJedisConnection;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class RedisDistributedLock {

    // 应用部署场景
    // 1. 一个tomcat单体中部署应用
    // 2. 多个tomcat分布式集群架构部署, 高可用
    //             前端用户访问负载均衡设备
    //        http://192.168.0.60/deduct_stock
    //                nginx 负载均衡(设备)        ===> 分发请求到不同的部署上面
    //         tomcat1            tomcat2       ===> Web引用部署在多个Web Container中
    //      localhost:8080     localhost:8090   ===> 不同的部署会有不同的进程，不同的JVM
    //                    Redis
    //             Redis服务端单线程处理程序
    //               按照先后优先依次处理

    // 在Spring层面封装了Redis的使用模板: StringRedisTemplate针对(Key->String)类型的数据结构进行存储
    String key = "lockKey";
    StringRedisTemplate stringRedisTemplate = SpringJedisConnection.getJedisStringTemplate();

    public String testDistributedLock() {
        // TODO: Java(JDK)内置锁不能跨越进程, 在分布式场景下锁会失效
        // synchronized (this) { }
        // TODO: 使用SETNX来实现基本的分布式锁
        boolean isGetLocked = stringRedisTemplate.opsForValue().setIfAbsent(key, "myValue"); // jedis.setnx(key, value);
        if (!isGetLocked) return "Error"; // 没拿到锁，则从后端返回，后端业务繁忙给出提示
        int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock")); // jedis.get("stock");
        if (stock > 0) {
            stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock - 1)); // jedis.set(key, value);
        }
        stringRedisTemplate.delete(key); // 删除掉设置的key，以便于其的请求拿到锁
        return "Success";
    }

    // 分布式锁的优化
    // 1. 在主体业务抛出异常的情况下，保证拿到锁的线程必须能够释放锁
    // 2. 在主体业务执行的过程，如果服务宕机或者挂掉，如果保存将获得的锁释放掉，能够执行finally的程序 ===> 设置key的超时时间，确保释放
    //    其他的tomcat继续发送请求，则无法有效的获得锁
    // 3. 如果在设置超时时间完成之前，服务宕机或者挂掉，依然无法保证锁的释放                       ==> 在获取锁和设置锁所执行的操作不是原子的，无法保证不被打断
    public String testDistributedLockPlus() {
        // boolean isGetLocked = stringRedisTemplate.opsForValue().setIfAbsent(key, "myValue");
        // stringRedisTemplate.expire(key, 10, TimeUnit.SECONDS);

        // 将两个操作合并到一个原子操作中
        boolean isGetLocked = stringRedisTemplate.opsForValue().setIfAbsent(key, "myValue", 10, TimeUnit.SECONDS);
        if (!isGetLocked) return "Error";
        try {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock - 1));
            }
        } finally {
            stringRedisTemplate.delete(key);
        }
        return "Success";
    }

    // TODO: 分布式锁的高并发场景: 如何避免"锁永久失效"的问题，根本无法保证执行的顺序和逻辑  ===> 使用Redisson
    // 1. 如何设置有效的超时时间，保证同一时间只有一个线程在执行同步的业务代码块(而不是可能又多个线程都在获取锁之后执行中)
    //    不能将过期时间设置过长，否则在某种场景下需要等待锁释放的时间过长，影响体验
    // 2. 请求线程    A    B     C   场景分析: 由于设置了锁的过期事件，在业务的执行时间和锁的过期时间不一致时
    //    执行时间  15S    8S   5S           在A请求的业务执行没有结束时，锁被释放，这时B线程能够获得锁，开始执行
    //             10S   go.                在A请求的业务完全结束时，执行finally语句，把B获得的线程锁给释放掉，然后让C请求来获得锁
    //             5S    5S   go.           接着等B请求执行完，执行finally语句，又释放掉别的线程获得的锁....
    //                   3S   3S
    //    解决办法：必须保证释放的锁是自己获取的锁，通过锁的值来确定
    public String testDistributedLockPlus2() {
        String threadIdValue = UUID.randomUUID().toString();
        boolean isGetLocked = stringRedisTemplate.opsForValue().setIfAbsent(key, threadIdValue, 10, TimeUnit.SECONDS);
        if (!isGetLocked) return "Error";
        try {
            int stock = Integer.parseInt(stringRedisTemplate.opsForValue().get("stock"));
            if (stock > 0) {
                stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock - 1));
            }
        } finally {
            // TODO: 检测并且只释放自己获取的锁，这里的执行必须是原子执行 !!
            if (stringRedisTemplate.opsForValue().get(key).equals(threadIdValue)) {
                // 如果系统在这里卡顿，刚好超过10s，由于之前设置的超时时间，导致锁释放了
                // 下面的操作会失效:
                // 通过key来删除，有可能会删除到被的请求刚获取的锁...
                stringRedisTemplate.delete(key);
            }
        }
        return "Success";
    }
}
