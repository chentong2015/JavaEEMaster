package Core.Data_Structure.base;

public class Redis5ZSet {

    // ZSet的结构:
    // TODO: 默认使用ZipList压缩列表, 元素相对较多时, 可以考虑使用跳表
    //       zset-max-ziplist-entries 128 配置元素超过128时，使用SkipList进行编码
    // key  -> 80 steven   正数索引: 0   负数索引: -3
    //         84 chen              1           -2
    //         90 tong              2           -1
    //         ...

    /*
     * ZSet(有序集合)常用操作
     * zadd key score member [[score member] ...]  在有序集合中添加带分值元素
     * zrem key member [member ...]                参从有序集合中删除元素
     * zscore key member                           返回key中指定member的分值
     * zincrby key increment member                在指定分值上添加increment
     * zcard key                                   返回有序集合的元素个数
     * zrange key start stop [WITHSCORES]          正序获取指定范围数据
     * zrevrange key start stop [WITHSCORE]        泛型获取指定范围数据
     *
     * ZSet集合操作
     * zunionstore destkey numberkeys key [key...]  并集
     * zinterstore destkey numberkeys key [key...]  交集
     */

    // ZSet应用场景
    // 1. 排行榜的实现
    //    1) zincrby hotNews:20210810 1 BigNews                  点击新闻，增加一份热度
    //    2) zrevrange hotNews:20210810 0 10 WITHSCORES          展示当日排行前十
    //    3) zunionstore hotNews:20210810-20210817 7             七日搜索榜单计算
    //    4) zrevrange hotNews:20210810-20210817 0 10 WITHSCORES 展示7日排行前10

    // 2. TODO: 分布式的定时任务调度器 ?
}
