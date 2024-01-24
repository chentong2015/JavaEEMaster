package round_robin;

import model.RequestId;
import model.ServerIP;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 依次轮询
// 0. 基本算法
// 1. 根据Server的权重生成冗余的集合，在冗余集合中进行轮询
// 2. 根据轮询的index位置确定所在的区间范围，然后index++
// 3. 平滑加权轮询
public class WeightRoundRobin {

    private static String getWeightServer() {
        int requestId = RequestId.getRequestId();
        int index = requestId % ServerIP.WEIGHT_SUM;
        for (Map.Entry<String, Integer> entry : ServerIP.WEIGHT_SERVERS.entrySet()) {
            if (index < entry.getValue())
                return entry.getKey();
            index -= entry.getValue();
        }
        return "";
    }

    // TODO: 平滑加权轮询 ==> 数学论证(如何将高权重在一轮过程中平坦给低权重的服务器)
    // 优化：对于权重配置过大的server，则会一直处理请求，权重小的服务器没有机会处理请求
    private static int sumWeight = 0;
    private static List<Integer> weights = new ArrayList<>();
    private static List<Integer> currentWeights = new ArrayList<>();
    private static Map<Integer, String> mapServers = new HashMap<>(); // 以空间换时间

    public static void main(String[] args) {
        loadAllServerWeights();
        System.out.println(getWeightServer2());
        System.out.println(getWeightServer2());
    }

    // 只加载一次数据
    private static void loadAllServerWeights() {
        int index = 0;
        for (Map.Entry<String, Integer> entry : ServerIP.WEIGHT_SERVERS.entrySet()) {
            sumWeight += entry.getValue();
            weights.add(entry.getValue());
            currentWeights.add(0);
            mapServers.put(index, entry.getKey());
            index++;
        }
    }

    // 根据一轮中最大值来确定Server, 标记index位置
    private static String getWeightServer2() {
        int maxWeight = 0;
        int maxWeightIndex = 0;
        for (int index = 0; index < weights.size(); index++) {
            int serverWeight = weights.get(index);
            int newCurrentWeight = currentWeights.get(index) + serverWeight;
            currentWeights.set(index, newCurrentWeight);
            // 在循环的过程中同时确定最大值
            if (newCurrentWeight > maxWeight) {
                maxWeight = newCurrentWeight;
                maxWeightIndex = index;
            }
        }
        // 只改变最大权重位置的值, 分担权重
        int newMaxWeightValue = currentWeights.get(maxWeightIndex) - sumWeight;
        currentWeights.set(maxWeightIndex, newMaxWeightValue);
        return mapServers.get(maxWeightIndex);
    }

    // TODO: 平滑加权轮询 ==> 面向对象的一种实现, 封装成指定的Class对象
    // 使用server IP作为key唯一
    private static Map<String, Weight> weightMap = new HashMap<>();

    public static String getWeightServer3() {
        int totalWeight = 7; // 这里需要通过map来进行统计
        if (weightMap.isEmpty()) {
            ServerIP.WEIGHT_SERVERS.forEach((ip, weight) -> {
                weightMap.put(ip, new Weight(ip, weight, 0));
            });
        }
        Weight maxCurrentWeight = null;
        for (Weight weight : weightMap.values()) {
            weight.setCurrentWeight(weight.getCurrentWeight() + weight.getWeight());
            if (maxCurrentWeight == null) {
                maxCurrentWeight = weight;
            } else if (maxCurrentWeight.getCurrentWeight() < weight.getCurrentWeight()) {
                maxCurrentWeight = weight;
            }
        }
        // 对找的拥有最多currentWeight权重的那个对象，刷新值
        maxCurrentWeight.setCurrentWeight(maxCurrentWeight.getCurrentWeight() - totalWeight);
        return maxCurrentWeight.getIp();
    }
}
