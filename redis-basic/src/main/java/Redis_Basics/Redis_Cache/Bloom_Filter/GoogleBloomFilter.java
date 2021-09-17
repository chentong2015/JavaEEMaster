package Redis_Basics.Redis_Cache.Bloom_Filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

// Google版布隆过滤器:
// TODO: 单机版本，使用JVM内存，不支撑分布式 !!
// 1. 底层使用bit数组实现(只能存放0或1，默认为0)，占用的空间很小
// 2. BloomFilter是具有容错概率的集合，集合中数据无法取出

// BloomFilter的实现原理:
// 1. 首先对插入的key进行hash运算(多个hash函数)，结果 % 数组长度 = index位置
// 2. 判断是否存在也使用同样的hash运算，然后判断位置结果是否都为1

// 容错率来源: 如果3个位置都为1，并不能说明所查询的key一定在，可能是通过别的key计算出来组成的3个1
// 容错率大小: 数组的长度和hash函数的个数同时决定了，容错率越小则开销越大
// 无法取数据: 由于没有保存原始的数据，所有无法从其中取出
public class GoogleBloomFilter {

    // expectedInsertions: 预计插入的数据
    // fpp: 容错率
    private BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), 10000, 0.1);

    // 根据定义的参数，源码中使用两个算法计算出需要的位数组长度和hash函数个数
    // TODO: 定义的位数组长度位int类型的最大值(2^31-1)，约20多亿
    // long numBits = optimalNumOfBits(expectedInsertions, fpp);
    // int numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);

    public void testBloomFilter() {
        int size = 10000;
        for (int index = 1; index <= size; index++) {
            bloomFilter.put(index);
        }
        List<Integer> valuesContain = new ArrayList<>(1000);
        for (int index = size + 1000; index < size + 2000; index++) {
            // 如果将不在bloomFilter的数据判断为包含在其中，则视为误判
            // 误判的概率接近于容错率，可以自定义
            if (bloomFilter.mightContain(index)) {
                valuesContain.add(index);
            }
        }
        System.out.println("误判的数量: " + valuesContain.size());
    }
}
