package Redis_Master.Redis_Concurrency;

import java.util.concurrent.ConcurrentHashMap;

// 秒杀系统: 高并发，大流量场景
// 性能优化: 缓存预减库存，内存售完标记，消息队列异步处理
public class DemoSpikeSystem {

    // Spring MVC Controller
    // @PostConstruct
    public void init() {
        // 将商品数据先加载到缓存中
        // stringRedisTemplate.opsForValue().set("stock", String.valueOf(stock));
    }

    // TODO: 使用Redis构建一级缓存
    // @PostMapping("/{productId}")
    public String secondKill(Long productId) {
        // stringRedisTemplate.opsForValue().decrement() 原子操作，线程安全(单线程模型)
        // if(stock < 0) {
        //    TODO: 对于拿到负数的，必须还原，否则后面由于异常恢复的stock卖不出去(导致少买商品)
        //    stringRedisTemplate.opsForValue().increment() 原子操作
        //    return; 直接在缓存中判断，是否售完
        // }
        secondKillService(productId);
        // TODO: 后面的业务逻辑不一定成功，导致上面缓存数据和数据库中数据不一致
        // stringRedisTemplate.opsForValue().increment() 在后面抛出异常的位置进行数据恢复
        return "success";
    }


    // TODO: 直接在JVM级别构建二级缓存，直接走内存，避免JVM <-> Redis造成的网络开销
    private boolean isSoldOut = false; // JVM级别的标记，内存对象
    private static ConcurrentHashMap<Long, Boolean> productSoldOutMap = new ConcurrentHashMap<>(); // 所有商品是否售完的标记

    // 该方法被回调执行，可以删除之前设置的标记，清除JVM级别的缓存
    public static void removeProductSoldOutFlag(Long productId) {
        if (productSoldOutMap.get(productId))
            productSoldOutMap.remove(productId);
    }

    public void secondKillPlus() {
        // 如果已经卖完，则直接返回，避免操作Redis
        if (!isSoldOut) {
            // stringRedisTemplate.opsForValue().decrement() 原子操作，线程安全(单线程模型)
            // if(stock < 0) {
            //    isSoldOut = true;
            //    stringRedisTemplate.opsForValue().increment();
            //    设置zookeeper的标记，同时设置客户端监听效果
            //    return;
            // }
        }

        // 在异常的情况下:
        // TODO: JVM级别的缓存无法解决分布式锁的问题 !! 必须要在集群中同步
        // 如果后面没有成功操作，必须还原标记
        isSoldOut = false;
        // TODO: 通过Zookeeper实时同步集群JVM缓存，分布式集群中任何一个结点都能更新缓存标记
        // if (zooKeeper.exists(zkProductPath, true) == null) {
        //      修改zooKeeper中path下结点值，通知所有监听的客户端
        //      zooKeeper.create(zkProductPath, "false".getBytes(), ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        // }
        return;
    }


    // Service Layer
    public void secondKillService(Long productId) {
        // 1. 查询商品，判断库存
        // productService.getProductId(productId);

        // 2. 创建秒杀订单
        // Order order = new Order();
        // order.setProductId(order);
        // saveOrder(order);

        // 3. 减少库存，判断更新库存成功
        // productService.deductProductStock(productId);
    }
}
