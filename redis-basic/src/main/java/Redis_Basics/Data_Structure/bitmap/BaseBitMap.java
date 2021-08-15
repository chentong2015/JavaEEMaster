package Redis_Basics.Data_Structure.bitmap;

// 面试思考题:
// 系统有亿级的活跃用户，如何实现日志统计 ?
// 为了增强用户粘性，需要上线一个连续打卡发放积分的功能，如何实现连续打开用户统计 ?
public class BaseBitMap {

    // 解法方案：基于Redis bitmap数据结构(底层是字符串)来实现
    // String
    // value:  0 1 0 1 0 1 0 0   0  1  0  1  0  1  1  1   ==> 登录状态
    // offset: 1 2 3 4 5 6 7 8   9  10 11 12 13 14 15 16  ==> userId最大能达到(2^32-1), 约42亿的数据量 ==> 约512M的内存空间
    //                                                    ==> userId不是连续的也可以存储

    // 日活统计: 只有两种情况，使用bit位来表示
    // setbit key offset 1       找到指定的用户位置，设置登录状态  ==> 位运算O(1)
    // setbit login_11_11 5 1    设置key为指定的天
    // getbit login_11_11 5      直接根据offset来快速判断

    // bitcount key [start end]  字节的索引范围index[0, end]，不写则全部统计
    // bitcount login_11_11      统计指定key序列中的bit位1的数量  ==> 位运算: 数学算法


}
