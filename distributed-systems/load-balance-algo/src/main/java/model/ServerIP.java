package model;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerIP {

    // 服务器集群
    public static final List<String> SERVERS = Arrays.asList(
            "192.168.0.1",
            "192.168.0.2",
            "192.168.0.3",
            "192.168.0.4",
            "192.168.0.5",
            "192.168.0.6",
            "192.168.0.7",
            "192.168.0.8",
            "192.168.0.9",
            "192.168.0.10"
    );

    // 设置权重: 性能高的机器需要处理更多的请求, 总的权重需要统计出来
    public static final int WEIGHT_SUM = 50;
    public static final Map<String, Integer> WEIGHT_SERVERS = new LinkedHashMap<>();

    static {
        WEIGHT_SERVERS.put("192.168.0.1", 5);
        WEIGHT_SERVERS.put("192.168.0.2", 1);
        WEIGHT_SERVERS.put("192.168.0.3", 1);

        // WEIGHT_SERVERS.put("192.168.0.1", 1);
        // WEIGHT_SERVERS.put("192.168.0.2", 8);
        // WEIGHT_SERVERS.put("192.168.0.3", 3);
        // WEIGHT_SERVERS.put("192.168.0.4", 6);
        // WEIGHT_SERVERS.put("192.168.0.5", 5);
        // WEIGHT_SERVERS.put("192.168.0.6", 5);
        // WEIGHT_SERVERS.put("192.168.0.7", 4);
        // WEIGHT_SERVERS.put("192.168.0.8", 7);
        // WEIGHT_SERVERS.put("192.168.0.9", 2);
        // WEIGHT_SERVERS.put("192.168.0.10", 9);
    }
}
