package Redis_Basics.Data_Structure;

// Redis数据结构的底层实现
// key-value存储，内存中一张巨大的hash表，依赖于hash function，查询的效率很高 接近O(1)
// Redis底层渐进式rehash以及动态扩容机制，减少hash冲突的可能性
// 存储基于一张全局的hash表: hash(key)%table_size
//    全局hash表
//       0   -> key: 都是String类型
//       1   -> entry<key, value>
//       2   -> entry<key, value> -> entry<key, value> 冲突的时候，挂链表
//       3   -> 对于不同的value值, 对应着特定的存储结构
//       4
//       5
public class RedisDataStructureImpl {

    // TODO: Redis底层的数据存储结构
    // 1. 简单的动态字符串
    // 2. hash表          O(1)
    // 3. ZipList压缩列表  O(n): 把指针去掉后的双向链表，类似于数组，添加相对的偏移值
    // 4. 双向链表         O(n)
    // 5. 整数数组         O(n)
    // 6. SkipList跳表    O(log(n)): 基于有序链表的优化结构，支持二分查找的结构

    // TODO: Redis C类型的实现SDS: Simple Dynamic String
    // 1. 二进制安全的数据结构
    // 2. 内存的预分配操作，避免频繁的内存分配
    //      int free: 0              还剩余多少空间，以进行数据的"动态扩容"
    //       int len: 9              根据长度来读取指定的字符串长度
    //    char buf[]: "chen\0tong"   原始C语言是通过"\0"来确定字符串的结尾 !!
    //
    //       int free: 0       int free: 9
    //        int len: 6       int len:  9
    //       int addLen: 3
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
}
