package algo.random;

import model.ServerIP;

import java.util.Map;
import java.util.Random;

// 随机算法
// 0. 基本算法
// 1. 将所有server按照权重冗余相等的份数，在冗余集合中随机一个数 ==> 按照权重进行复制，内存效率低
// 2. 将权重转换成x坐标轴长度，值越大则坐标区间范围越大，随机值落到区间的概率越大 ==> 和Server排序无关
public class WeightRandom {

    // 以多大的概率选择指定的server
    // 优化: 先判断是否配置的权重都是一样的，则不需要在加权重
    private static String getWeightServer() {
        Random random = new Random();
        int index = random.nextInt(ServerIP.WEIGHT_SUM);
        for (Map.Entry<String, Integer> entry : ServerIP.WEIGHT_SERVERS.entrySet()) {
            if (index < entry.getValue())
                return entry.getKey();
            index -= entry.getValue(); // 减去之前计算过的区间
        }
        return "";
    }
}
