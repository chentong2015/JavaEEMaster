package Redis_Basics.Redis_Cache.Bloom_Filter;

import Spring_Data_Redis.SpringJedisConnection;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.List;

import static java.util.Objects.hash;

// 自定义分布式布隆过滤器的实现：
// TODO: 根据Redis底层数据值的存储(位数组)来实现，支撑位组2^32，约42亿
// > set tong abc
// > get tong
//   "abc"              --> 实际底层对应的二进制存储：1100001 1100010 1100011
// > setbit tong 6 1    --> 通过bitmap位运算修改bit位的值，从而改变字符串的值
// > setbit tong 7 0
// > get tong
//   "bbc"
// > setbit tong 100 0  --> 改变指定bit位的值，存储非连续来进行扩容
public class DistributedBloomFilter {

    private long numBits;
    private int numHashFunctions;

    // 使用两个算法计算出需要创建的位数组的长度和hash函数的个数
    public void createMyBloomFilter(int expectedInsertions, double fpp) {
        numBits = optimalNumOfBits(expectedInsertions, fpp);
        numHashFunctions = optimalNumOfHashFunctions(expectedInsertions, numBits);
    }

    private long optimalNumOfBits(long n, double p) {
        if (p == 0.0D) p = 4.9E-324D;
        return (long) ((double) (-n) * Math.log(p) / (Math.log(2.0D) * Math.log(2.0D)));
    }

    private int optimalNumOfHashFunctions(long n, long m) {
        return Math.max(1, (int) Math.round((double) m / (double) n * Math.log(2.0D)));
    }

    private StringRedisTemplate redisTemplate = SpringJedisConnection.getJedisStringTemplate();

    public void put(int id) {
        long[] indexs = getIndexs(String.valueOf(id));
        redisTemplate.executePipelined((RedisCallback<Object>) redisConnection -> {
            redisConnection.openPipeline();
            // 将所有算出来的index位置(offset)做好标记，bit值设置成1
            for (long index : indexs) {
                redisConnection.setBit("bf:chentong".getBytes(), index, true);
            }
            redisConnection.close();
            return null;
        });
    }

    // 计算key值经过不同的hash函数计算之后，对应在bit位数组上的index位置
    private long[] getIndexs(String key) {
        long[] indexs = new long[numHashFunctions];
        long hash1 = hash(key);
        long hash2 = hash1 >>> 16;
        for (int index = 0; index < numHashFunctions; index++) {
            // 使用index来模拟使用多次不同的hash函数
            long combinedHash = hash1 + index * hash2;
            if (combinedHash < 0) {
                combinedHash = ~combinedHash;
            }
            indexs[index] = combinedHash % numBits;
        }
        return indexs;
    }

    // 如果key计算出来的index位置有一个为false，则表示key不在过滤器中
    public boolean isExist(int id) {
        long[] indexs = getIndexs(String.valueOf(id));
        List<Object> results = redisTemplate.executePipelined(new RedisCallback<Object>() {
            @Override
            public Object doInRedis(RedisConnection redisConnection) throws DataAccessException {
                redisConnection.openPipeline();
                for (long index : indexs) {
                    redisConnection.getBit("bf:chentong".getBytes(), index);
                }
                redisConnection.close();
                return null;
            }
        });
        return !results.contains(false);
    }
}

