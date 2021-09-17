package Redis_Basics.Redis_Cache.Bloom_Filter;

import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.util.ArrayList;
import java.util.List;

// com.google.guava
// Google版布隆过滤器: 单机版本
public class GoogleBloomFilter {

    // 预计要插入的数据
    private static int size = 10000;

    // fpp: 容错率
    private static BloomFilter<Integer> bloomFilter =
            BloomFilter.create(Funnels.integerFunnel(), size, 0.1);

    public static void main(String[] args) {
        for (int index = 0; index < size; index++) {
            bloomFilter.put(index);
        }
        List<Integer> list = new ArrayList<>(1000);
        for (int index = size + 1000; index < size + 2000; index++) {
            // 如果将不在bloomFilter的数据判断为包含在其中，则视为误判
            if (bloomFilter.mightContain(index)) {
                list.add(index);
            }
        }
        System.out.println("误判的数量: " + list.size());
    }

}
