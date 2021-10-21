package load_balance.algo.hash;

// 一致性hash负载均衡算法
public class ConsistentHash {

    // 场景1:
    // 使用n台缓存服务器时，一种常用的负载均衡方式是，对资源o的请求使用hash(o)= o mod n来映射到某一台缓存服务器
    // 当增加或减少缓存服务器时，可能会改变所有资源对应的hash值，也就是所有的缓存都失效了
    //
    // 1. 在增加和减少Server时，所有的服务器能够分担存储缓存资源
    // 2. 将每个对象映射到圆环边上的一个点，系统再将可用的节点机器映射到圆环的不同位置
    //    用一致哈希算法计算得到对象对应圆环边上位置，沿着圆环边上查找直到遇到某个节点机器(对象保存位置)
    //    当增删结点机器时，机器周边的对象做重新转移和适配到相应的机器上，不对其他机器区间造成影响

    // 场景2:
    // 可以根据客户端的请求ip，算出hashcode，请求到指定的server上面
    // 避免在不同的Server上Session不一致的问题(没有共享)，一个客户端只对应一台Server来处理
    //
    // client ip --> hash function --> hashcode --> server ip
    // 1. 提前定义映射关系: {hashcode:server_ip} 由于可能算处理的hashcode太多，因此无法提前配置
    // 2. 哈希环: 规定一定范围内hash值对应唯一server ip
    //           环上存储的都是hashcode，数字顺时针从小到大

}
