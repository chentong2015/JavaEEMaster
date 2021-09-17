package Redis_Basics.Redis_Cache.Bloom_Filter;

// 使用redis内存，支撑大于40亿的位数组，支持分布式
// TODO: 根据Redis底层数据值的存储来实现，需要位数组
// > set tong abc
// > get tong
// "abc"                --> 实际底层对应的二进制存储：1100001 1100010 1100011
// > setbit tong 6 1    --> 通过bitmap位运算修改bit位的值，从而改变字符串的值
// > setbit tong 7 0
// > get tong
// "bbc"
// > setbit tong 100 0  --> 改变指定bit位的值，存储非连续来进行扩容
public class MyDistributedBloomFilter {

}
