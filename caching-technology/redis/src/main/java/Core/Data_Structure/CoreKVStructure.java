package Core.Data_Structure;

// TODO: Redis K-V底层(C语言)实现原理 --> 基于数组和链表来实现
// 1. 内存中一张巨大的hash表，依赖于hash function，查询的效率很高，接近O(1)
// 2. hash运算: hash(key)%table_size
//             hash(key) -> 自然数 % framework = index 将字符串转换成整型值
// 3. hash冲突: 渐进式rehash以及动态扩容机制，减少hash冲突

// 计算数组存放的位置，hash运算，散列避免hash冲突
// 1. 任意相同的输入，一定有相同的冲突
// 2. 不同的输入，有可能有想用的输出
//    hash(k1) % 4 = 0
//    hash(k2) % 4 = 1
//    hash(k3) % 4 = 1
// 全局hash表
//    arr[0] => key: 都是String类型 --> 对应到C语言SDS类型
//    arr[0] -> (k1, v1, next->null) 数组存指针，指向具体的键值对元素
//    arr[1] -> (k3, v3, next-> k2),(k2, v2, next->null)  如果key一样则先删除再设置，反之挂链表
//    arr[2]    如果这里挂的链表过长，可能会rehash
//    arr[3] => 对于不同的value值, 对应着特定的存储结构
public class CoreKVStructure {

    // TODO: Redis所有key都是string类型, 对应到C语言的实现SDS: Simple Dynamic String --> "Raw"
    // 1. 二进制安全的数据结构
    // 2. 内存的预分配操作，避免频繁的内存分配
    //      int free: 0              还剩余多少空间，以进行数据的"动态扩容"
    //       int len: 9              根据长度来读取指定的字符串长度
    //    char buf[]: "chen\0tong"   原始C语言是通过"\0"来确定字符串的结尾 !!
    //
    //       int free: 0       int free: 9
    //        int len: 6       int len:  9
    //     int addLen: 3
    //     char buf[]: "chentong123"
    //    (len+addLen)*2=18    成倍的扩容长度，当数据达到1M时，每次最多只增加1M，并且库扩容之后不会再缩小 !!
    // 3. 兼容C语言的函数库: 对自动串结尾添加"\0"的处理

    // C语言源码理解
    // struct sdshdr {   3.2版本之前的设计，头部所占的字节过多，导致浪费
    //    int len;
    //    int free;
    //    char buf[];
    // }
    // struct __attribute__ sdshdr5 {}  之后版本的优化
    // struct __attribute__ sdshdr8 {}  uint8_t len; 只使用一个字节存储len长度
    // struct __attribute__ sdshdr16 {} 从上面往下扩容
    // struct __attribute__ sdshdr32 {}
    // struct __attribute__ sdshdr64 {}

    // TODO: Redis的value里面又套了一层k-v性数据
    //       不同的value数据类型对应实际编码的对象, 底层encoding进行编码优化
    // 1. 简单的动态字符串
    // 2. hash表          O(1)
    // 3. ZipList压缩列表  O(n): 把指针去掉后的双向链表，类似于数组，添加相对的偏移值
    // 4. 双向链表         O(n)
    // 5. 整数数组         O(n)
    // 6. SkipList跳表    O(log(n)): 基于有序链表的优化结构，支持二分查找的结构

    // TODO: Redis默认支持16个DB数据库
    // > select redisDB
    //   typedef struct redisDb {
    //      dict *dict;                 /* The keyspace for this DB */ 所有的"键值对"存储的位置
    //      dict *expires;              /* Timeout of keys with a timeout set */
    //      dict *blocking_keys;        /* Keys with clients waiting for data (BLPOP)*/ 阻塞队列
    //      dict *ready_keys;           /* Blocked keys that received a PUSH */
    //      dict *watched_keys;         /* WATCHED keys for MULTI/EXEC CAS */ 事务
    //      int id;                     /* Database ID */  db的编号
    //      long long avg_ttl;          /* Average TTL, just for stats */
    //      unsigned long expires_cursor; /* Cursor of the active expire cycle. */
    //      list *defrag_later;         /* List of key names to attempt to defrag one by one, gradually. */
    //  } redisDb;

    // 数据库所使用的dict字典数据结构: map结构的实现
    // struct dict {
    //     dictType *type;
    //     dictEntry **ht_table[2];
    //     dictht ht[2]; 渐进式的扩容，避免转移数据时的卡顿
    //     unsigned long ht_used[2];
    //     long rehashidx; /* rehashing not in progress if rehashidx == -1 */
    //     int16_t pauserehash; /* If >0 rehashing is paused (<0 indicates coding error) */
    //     signed char ht_size_exp[2]; /* exponent of size. (size = 1<<exp) */
    // };

    // typeof struct dictht {
    //   dictEntry **table;
    //   unsigned long size; // hashtable容量
    //   unsigned long sizemask;
    //   unsigned long used; // 使用元素的个数
    // }dictht;

    // typedef struct dictType {
    //    uint64_t (*hashFunction)(const void *key);
    //    void *(*keyDup)(dict *d, const void *key);
    //    void *(*valDup)(dict *d, const void *obj);
    //    int (*keyCompare)(dict *d, const void *key1, const void *key2);
    //    void (*keyDestructor)(dict *d, void *key);
    //    void (*valDestructor)(dict *d, void *obj);
    //    int (*expandAllowed)(size_t moreMem, double usedRatio);
    //    size_t (*dictEntryMetadataBytes)(dict *d);
    // } dictType;

    // 使用dictEntry封装键值对<key, v, next指针>
    // typedef struct dictEntry {
    //    void *key;         这里的key指向string对象(所有的key都是string)
    //    union {
    //        void *val;     具体存储数据的redisObject ==> 对应real object
    //        uint64_t u64;
    //        int64_t s64;
    //        double d;
    //    } v;
    //    struct dictEntry *next; 构建链表的指针next
    //    void *metadata[];
    // } dictEntry;
}
