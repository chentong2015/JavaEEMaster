package Core.Data_Structure.base;

public class Redis2Hash {

    // Hash的结构:
    // key -> field1 value
    //        field2 value
    //        field2 value
    //        ...

    /*
     * Hash常用操作
     * hset key field value        存储hash表一个key所对应的键值
     * hsernx key field value      存储一个不存储的key的键值
     * hmset key field vlaue [field value] 批量在key中存储多个键值对
     * hget key field              获取hash表的一个key所对应的field的值
     * hmget key field [field]     批量获取
     * hdel key field [field]      删除key所对应的field键值
     * hlen key                    返回key对应的field的数量
     * hgetall key                 返回key对应的所有键值
     * hincrby key field increment 增加hash key中field键值数量
     * hdecrby key field decrement 减少hash key中field键值的数量
     */

    // Hash应用场景
    // 1. 对象缓存: 将数据库中user_table表的数据存储到缓存中
    //    将表名称作为key值，主键结合字段名称作为field，值作为键值 !!
    //    hmset user {userid}:name chentong {userid}:balance 100
    //    hmset user 1:name chentong 1:balance 100
    //    hmget user 1:name 1:balance

    // 2. 电商购物车:
    // TODO: 购物车中的数据可以不存储到数据库，频繁加减商品，减少数据库的压力
    //       redis缓存中不能存储太大的信息，
    //       具体的商品内容，使用商品id通过"Ajax异步/前后端分离"从后端获取
    // 业务场景的实现，购物车操作: 用户id为key，商品id为field，商品数量为value
    //    1) hset cart:1001 10088 1    添加商品
    //    2) hincrby cart:1001 10088 1 增加数量(减少数量)
    //       hget cart:1001 10088      获取指定的商品数量
    //    3) hlen cart:1001            获取指定用户购买的商品类型的数量
    //    4) hdel cart:1001 10088      删除指定商品
    //    5) hgetall cart:1001         获取所有的商品

    // 优点
    //   1) 数据的整理，同类整合(不推荐存储数量过大的内容, 比如千万级以上的用户, 不建议全部存到一个user下面)
    //   2) 比String消耗CPU和内存更小
    //   3) 比String更节省空间

    // 缺点
    //   1) 过期功能不能使用在field上，只能使用在key上面
    //   2) Redis集群(数据分片)架构不适合大规模使用，分不同的结点可能造成数据存储的不均衡，数据倾斜
    //      应该将user_key进行分端拆分 < 10000，避免集中存储
}
