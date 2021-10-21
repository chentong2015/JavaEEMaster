package load_balance.algo;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ServerIP {

    // 服务器集群
    public static final List<String> servers = Arrays.asList(
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

    // 设置权重: 性能高的机器需要处理更多的请求
    public static final Map<String, Integer> weightServers = new LinkedHashMap<>();

    static {
        weightServers.put("192.168.0.1", 1);
        weightServers.put("192.168.0.2", 8);
        weightServers.put("192.168.0.3", 3);
        weightServers.put("192.168.0.4", 6);
        weightServers.put("192.168.0.5", 5);
        weightServers.put("192.168.0.6", 5);
        weightServers.put("192.168.0.7", 4);
        weightServers.put("192.168.0.8", 7);
        weightServers.put("192.168.0.9", 2);
        weightServers.put("192.168.0.10", 9);
    }
}
