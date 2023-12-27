package Redis_Basics.Data_Structure.base;

public class Redis4Set {

    // Set的结构:
    // key  ->  {member, member...}  一个键值对应一个数据集，其中包含批量的数据，无序Set并且不包含重复数据

    /*
     * Set常用操作
     * sadd key member [member ...]  如果key对应的集合中不存在，则添加，反之不添加
     * srem key member [member ...]  从key对应的集合中移除指定的元素
     * smembers key                  获取key集合中所有的元素
     * scard key                     获取key集合中元素的个数
     * sismember key member          判断members是否在key的集和中
     * srandmemeber key [count]      从key集合中"随机"取出count个元素，元素不从key中删除
     * spop key [count]              从key集合中"随机"取出count个元素，元素从key中删除
     *
     * Set运算操作
     * sinter key [key...]                  交集运算
     * sinterstore destination key [key...] 交集之后，store存储结果到destination集合中
     * sunion key [key...]                  并集运算
     * sunionstrore desination key [key...] 并集之后，store存储结果到destination集合中
     * sdiff key [key...]                   差集运算(以第一个key为准，后面的求并集，计算第一个key不出现在并集中的元素) !!
     * sdiffstore destination key [key...]  差集之后，store存储结果到destination集合中
     */

    // Set应用场景
    // 1. 微信抽奖小程序
    //    1) sadd key {userid}        点击参与抽奖，添加到集合中
    //    2) smemebers key            查看参与抽奖的所有用户
    //    3) srandmemeber key [count] "随机"抽取指定数量的中奖者人数
    //       spop key [count]         抽过奖的人之后不再参与抽奖

    // 2. 微信微博点赞，收藏，标签
    //    1) sadd like:{消息id} {用户id}      点赞之后(后端的执行命令)  ===> 不保证添加的member的顺序，使用list来保证顺序 !!
    //    2) srem like:{消息id} {用户id}      取消点赞               ===> TODO: 使用list列表如何保证随机删除
    //    3) sismember like:{消息id} {用户id} 查看是否点过赞
    //    4) smemebers like:{消息id} {用户id} 获取点赞的列表
    //    5) scard like:{消息id}             获取点赞的用户数

    // 3. 微信微博的关注模型
    //    ASet ->  {chen, chen1, tong}        A关注的人
    //    tongSet ->  {B}
    //    BSet ->  {tong, victor, test}       B关注的人
    //    sinter ASet BSet -> {tong}          A,B共同关注的人
    //    sismember tongSet B                 A关注的人也关注了B     ==> 对每一个关注的人都做判断，需要优化/单独部署 !!
    //    sdiff BSet ASet  -> {victor, test}  差集，A可能认识的人(从B集合中排除A中关注过的人)，根据关注的关注来构建联系，推演算法

    // 4. 电商页面的商品快速筛选Filter
    //    sadd brand:huawei P30
    //    sadd brand:iphone iphone11
    //    sadd os:android P30 iphone11
    //    sadd cpu:brand:intel P30 iphone11
    //    sinter os:android cpu:brand:intel ram:8G -> {P030, iphone11}
}
