package hash;

import java.util.SortedMap;

// 一致性hash负载均衡算法
public class HashConsistency {

    public static String getServer(String clientInfo) {
        HashCircle hashCircle = new HashCircle();
        int hash = hashCircle.getHashcode(clientInfo);

        // 在红黑树的子树中找寻满足条件的Server
        SortedMap<Integer, String> subMap = hashCircle.getTailMap(hash);
        Integer key = subMap.firstKey();

        // 如果算出的hash值过大，没有符合条件的node，则取哈希环中最小node构成一个封闭环状
        if (key == null) {
            int firstKey = hashCircle.getFirstKey();
            return hashCircle.getNodeValueByKey(firstKey);
        }
        return hashCircle.getNodeValueByKey(key);
    }
}
