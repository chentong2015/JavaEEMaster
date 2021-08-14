package Redis_Basics.Data_Structure;

// Redis数据结构的底层实现
// key-value存储，内存中一张巨大的hash表，依赖于hash function，查询的效率很高 接近O(1)

// 存储基于一张全局的hash表: hash(key)%table_size
//    全局hash表
//       0
//       1   -> entry<key, value>
//       2   -> entry<key, value> -> entry<key, value> 冲突的时候，挂链表
//       3
//       4
//       5
//       6
// Redis底层渐进式rehash以及动态扩容机制，减少hash冲突的可能性
// 对于不同的value值，还对应着特定的存储结构
public class RedisDataStructureImpl {

    // 底层的数据存储结构
    // 1. 简单的动态字符串
    // 2. hash表          O(1)
    // 3. ZipList压缩列表  O(n): 把指针去掉后的双向链表，类似于数组，添加相对的偏移值
    // 4. 双向链表         O(n)
    // 5. 整数数组         O(n)
    // 6. SkipList跳表    O(log(n)): 按照分值从小到大排序

    // TODO: 跳表: 把有序链表修改成支持"二分查找"的结构，以内存空间换时间 !!
    // 将zSet有序集合中的score取出来排序，从小到大构成链表: 查询慢，插入或者删除元素比较快
    // 基于链表的优化，构建索引层(提一层作为冗余元素，占更多的内存空间)
    // 1         ->           25        ->             100
    // 1 ->      12    ->     25        -> 50    ->    100
    // 1 -> 6 -> 12  -> 18 -> 25  -> 32 -> 50 -> 80 -> 100
}
