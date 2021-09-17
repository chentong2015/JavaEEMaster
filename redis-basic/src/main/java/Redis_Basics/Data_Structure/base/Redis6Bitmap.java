package Redis_Basics.Data_Structure.base;

// 面试思考题:
// 系统有亿级的活跃用户，如何实现日志统计 ?
// 为了增强用户粘性，需要上线一个连续打卡发放积分的功能，如何实现连续打开用户统计 ?
public class Redis6Bitmap {

    // Bitmap数据结构：底层字符串，string类型的扩展
    // TODO: 底层是一个"位数组"的存储形式，string类型的value最多能够保存512M !!
    // value:  0 1 0 1 0 1 0 0   0  1  0  1  0  1  1  1   ==> 最多位数(2^32)约42亿数据量，约512M内存空间
    // offset: 1 2 3 4 5 6 7 8   9  10 11 12 13 14 15 16

    // TODO: setbit & bitcount 底层都是位运算来实现的
    // setbit key offset 1       key指的是对key所对应的那一组值进行操作 !!
    //                           找到指定用户位置，设置登录状态 ==> 位运算: 时间复杂度 O(1)
    // setbit login_11_11 5 1    设置key为指定的日期天
    // getbit login_11_11 5      直接根据offset来快速判断

    // bitcount key [start end]  [0, end]字节的索引范围index，不是bit偏移量的索引，默认统计全部范围
    // bitcount login_11_11      统计指定key序列中bit位1数量  ==> 位运算: 求一个序列中1的个数(数学算法)

    // 测试案列 1：基本登录的统计
    // > setbit login1111 0 1
    // > setbit login1111 1 1
    // > setbit login1111 2 1
    // > type login1111
    //   string 底层(封装)的数据类型
    // > strlen login1111
    //   1 表示一个字节的长度
    // > setbit login1111 8 1  可以存储不是连续的userId
    // > strlen login1111
    //   2 自动扩容成2个字节的长度
    // > bitcount login1111 0 1
    //   5 一共有5个用户登录

    // 测试案列 2：如何统计连续登录(两个key对应的位置值都是1)
    // key1:  0 1 0 1 0 1 0 0   1  1  1  1  0  1  1  1
    // key2:  0 1 1 1 0 1 1 0   0  1  0  1  1  1  0  1
    //        求按位与运算，再统计bit位为1的个数
    // >bitop and login1110-and-1111 login1110 login1111 提供存储的结果的key

    // 测试案列 3：周活，月活
    // key1:  0 1 0 1 0 1 0 0   1  1  1  1  0  1  1  1
    // key2:  0 1 1 1 0 1 1 0   0  1  0  1  1  1  0  1
    // key3....
    //        求按位或运算，再统计bit为1的个数
    // >bitop or login1110-or-1111 login1110 login1111

    // TODO: 当用户量不够大的时候，不建议使用bitmap > 适用场景: 数据是连续的，或者数据量非常大
    // >setbit anykey 420000000 0
    // 分配内存耗时比较久，分配非常大的字节数量
}
