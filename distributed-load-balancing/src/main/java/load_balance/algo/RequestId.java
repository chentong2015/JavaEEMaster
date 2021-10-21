package load_balance.algo;

// Nginx, dubbo > RequestId
// 客户端请求时逐增生成的id数字, 00001,,,
public class RequestId {

    private static int id = 0;

    // 模拟请求id的逐增
    public static int getRequestId() {
        return id++;
    }
}
