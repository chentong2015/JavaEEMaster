package Redis_Basics.Data_Structure.base;

public class Redis3List {

    /*
     * List常用操作
     * lpush key value [value]     将一个或者多个value插入到key列表的表头(left)
     * rpush key value [value]     将一个或者多个value插入到key列表的表尾(right)
     * lpop                        移除并返回key列表的头元素
     * rpop                        移除并返回key列表的尾元素
     * lrange key start stop       返回指定偏移量区间中的元素
     * blpop key [key...] timeout  从key列表头弹出一个元素，如果没有则阻塞timeout秒，如果timeout=0则一直阻塞
     * brpop key [key...] timeout  从key列表尾弹出一个元素，如果没有则阻塞timeout秒，如果timeout=0则一直阻塞  b->blocking
     *
     *           lpush        rpush
     * key   -->   a   b   c    d
     *           lpop         rpop
     *    正数索引  0   1    2    3
     *    负数索引 -4  -3   -2   -1
     */

    // List应用场景
    // 1. 实现常用数据结构
    //    Stack = lpush + lpop
    //    Queue = lpush + rpop
    //    Blocking Message Queue = lpush + brpop 阻塞队列(阻塞式的，线程需要等待取到数据，监听/消费者)

    // 2. 信息流(News Feed): 底层实现
    //    微博和微信公号，订阅公众号，登录页面刷新的时候，显示被订阅者发布新信息动态
    //    1) 被订阅者1发布动态，消息id为10010   lpush msg:{我的-id} 10010  因为我关注了(作为粉丝)，所以会插入我等待的消息队列中
    //    2) 被订阅者2发布动态，消息id为10011   lpush msg:{我的-id} 10011
    //    3) 我查看最新的动态                  lrange meg{我的-id} 0 5   获取最新更新的动态信息，从list前面开始取，根据时间线
    //    优化实现: 如果一个用户的粉丝过多，则可以将新的动态发布到这个大V的消息队列中，，，
    //    TODO: 使用MQ的订阅和发布 ?
}
