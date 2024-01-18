package Core.Data_Structure.base;

public class Redis1String {

    /*
     * String常见指令操作:
     * set key value
     * get key
     * del key
     * mset key value [key value] 批量存储字符串键值对
     * mget key [key..]   批量获取字符串键值对
     * expire key seconds 设置一个key过期时间
     * setnx key value    存入一个不存在的字符串键值对(set if not exist) 当且仅当可以不存在, 将key值设置成value
     * incr key           原子增加
     * decr key
     * incrby key increment
     * decrby key decrement
     *
     * type key            返回key对应的(5种基本)大类型
     * object encoding key 判断编码类型 ==> int(如果value能转换类型成功则使用int来存储), embstr, raw(大于44字节)
     */

    // String 应用场景
    // 1. 单值缓存
    //    set key value
    //    get key

    // 2. 对象缓存: 如何将数据库表中一行的数据对象存储到redis中
    //    set user:1 value(json数据格式)                 ==> 将user id为1的数据通过json存储            ===> 简单，但性能不好，不容易修改
    //    mset user:1:name chentong user1:balance 1888 ==> 批量设置值，组合user+id+field成可以键值存储  ===> 热点数据过多容易淘汰 !!
    //    mget user:1name user:1:balance
    //     1) "chentong"
    //     2) "1888"

    // 3. 分布式锁 复杂度O(1)
    //    setnx product:10001 true          返回1表示获取锁成功  ===> 多个线程，那个设置成功，则表示拿到锁
    //    setnx product:10001 true          返回0表示获取锁失败
    //    ...   执行业务操作
    //    del   product:10001               执行完业务后释放锁
    //    set   product:10001 true ex 10 nx 防止程序意外终止导致死锁

    // 4. 计数器
    //    incr article:readcount:{文章id}    微信文章阅读量的计数器, 当有用户打开文章时，则在后台执行这条命令
    //    get article:readcount:{文章id}

    // 5. TODO: Java Web集群session共享
    //    Spring session(底层通过redis实现session共享和存储), 注意查查看源码的实现

    // 6. 分布式id生成器(系统全局序列号)
    //    increby orderid 1000  原子计数器, 批量生成序列号提升性能
}
