package zookeeper.source_code;

// 集群启动的基本流程：逐个启动server
// 1. 初始化配置(zoo.cfg)
// 2. 根据日志+快照，初始化内存中的DataTree
// 3. 开启对client客户端端口的监听
// 4. 进行领导者选举
// 5. 服务器其他的一些初始化...
public class ZookeeperSourceCode {

    // Server服务器在运行时候的状态: 代表处在不同身份状态, 不同的角色初始化有所区别
    // 1. LOOKING    正在寻找leader的状态
    // 2. OBSERVING  处于在观察的状态
    // 3. FOLLOWING  处于在跟随的状态
    // 4. LEADING    处于在领导的状态

}
