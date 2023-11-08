package algo.hash;

import java.util.SortedMap;

// 一致性hash负载均衡算法
public class HashConsistency {

    // 场景1:
    // 使用n台缓存服务器时，常规的负载均衡是对资源x的请求使用hash(x)=(o mod n)映射到某一台缓存服务器
    // 当增加或减少缓存服务器时，可能会改变所有资源对应的hash值，导致缓存都失效
    // 1. 在增加和减少Server时，所有的服务器能够分担存储缓存资源
    // 2. 将每个对象映射到圆环边上的一个点，系统再将可用的节点机器映射到圆环的不同位置
    //    用一致哈希算法计算得到对象对应圆环边上位置，沿着圆环边上查找直到遇到某个节点机器(对象保存位置)
    //    当增删结点机器时，机器周边的对象做重新转移和适配到相应的机器上，不对其他机器区间造成影响

    // 场景2:
    // 可以根据客户端的请求ip，算出hashcode，请求到指定的server上面
    // 避免在不同的Server上Session不一致的问题(没有共享)，一个客户端只对应一台Server来处理
    // client ip --> hash function --> hashcode --> server ip
    // 1. 提前定义映射关系: {hashcode:server_ip}，由于可能算处理的hashcode太多，因此无法提前配置
    // 2. 哈希环: 环上存储hashcode，数字顺时针从小到大，规定一定范围内hash值对应唯一server ip

    // 场景3: 分布式Session
    // 在分布式Server集群架构场景中，不同Server所保存的Session没有全局共享
    // 导致在客户端请求，经过负载均衡算法之后，找到的Server没有Session信息，因此无法完成相关操作
    
    public static String getServer(String clientInfo) {
        HashCircle hashCircle = new HashCircle();
        int hash = hashCircle.getHashcode(clientInfo);

        // 在红黑树的子树中找寻满足条件的Server
        SortedMap<Integer, String> subMap = hashCircle.getTailMap(hash);
        Integer key = subMap.firstKey();

        // 如果通过客户端信息算出来的hash值过大，没有符合条件的node，则取哈希环中最小的node构成一个封闭环状
        if (key == null) {
            int firstKey = hashCircle.getFirstKey();
            return hashCircle.getNodeValueByKey(firstKey);
        }
        return hashCircle.getNodeValueByKey(key);
    }
}
