package Redis_Basics.Data_Structure;

// Redis K-V 底层实现原理:
// Redis底层使用C语言实现，基于数组和链表来实现
// 1. 数据结构: map -> dict
// 2. K - V : 数据库 -> 海量的数据存储 
public class RedisKVStructure {
    
    // hash(key) -> 自然数 % model = index 将字符串转换成整型值

    // 计算数组存放的位置，hash运算，散列避免hash冲突
    // 1. 任意相同的输入，一定有相同的冲突
    // 2. 不同的输入，有可能有想用的输出
    //    hash(k1) % 4 = 0
    //    hash(k2) % 4 = 1
    //    hash(k3) % 4 = 1
    //    arr[0] -> (k1, v1, next->null) 数组存指针，指向具体的键值对元素
    //    arr[1] -> (k3, v3, next-> k2),(k2, v2, next->null)  如果key一样则先删除再设置，反之挂链表
    //    arr[2]     TODO: 如果这里挂的链表过长，可能会rehash
    //    arr[3]

    // TODO: Redis默认支持16个DB数据库，可以配置redis.conf
    // > select redisDB
    //   typedef struct redisDb {
    //      dict *dict;                 /* The keyspace for this DB */ 所有的"键值对"存储的位置，字典
    //      dict *expires;              /* Timeout of keys with a timeout set */
    //      dict *blocking_keys;        /* Keys with clients waiting for data (BLPOP)*/ 阻塞队列
    //      dict *ready_keys;           /* Blocked keys that received a PUSH */
    //      dict *watched_keys;         /* WATCHED keys for MULTI/EXEC CAS */ 事务
    //      int id;                     /* Database ID */  db的编号
    //      long long avg_ttl;          /* Average TTL, just for stats */
    //      unsigned long expires_cursor; /* Cursor of the active expire cycle. */
    //      list *defrag_later;         /* List of key names to attempt to defrag one by one, gradually. */
    //  } redisDb;

    //
    // struct dict {
    //     dictType *type;
    //     dictEntry **ht_table[2];  解决渐进式的扩容，避免转移数据时的卡顿
    //     unsigned long ht_used[2];
    //     long rehashidx; /* rehashing not in progress if rehashidx == -1 */
    //     int16_t pauserehash; /* If >0 rehashing is paused (<0 indicates coding error) */
    //     signed char ht_size_exp[2]; /* exponent of size. (size = 1<<exp) */
    // };

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
}