package load_balance;

// 用户请求直接发送到负载均衡器
public class BaseLoadBalancer {

    // 负载均衡器能够处理的traffic流量
    // 1. HTTP
    // 2. HTTPS
    // 3. TCP
    // 4. UDP

    // 1. NGINX — An HTTP load balancer with SSL termination support.
    // 2. HAProxy — A TCP load balancer.
    // 3. mod_athena — Apache-based HTTP load balancer.
    // 4. Varnish — A reverse proxy-based load balancer.
    // 5. Balance — Open-source TCP load balancer.
    // 6. LVS — Linux virtual server offering layer 4 load balancing.
}
