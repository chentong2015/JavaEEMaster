package distributed_transactions.seata;

// TCC: 分布式事务框架, 对业务有侵入的(需要手动修改代码，自定义提交事务)
// Seata: 分布式事务框架
// 1. 支持TCC, 无侵入(不影响业务逻辑)
// 2. 网络通讯底层基于Netty来实现
public class BaseSeata {

    // TC: 全局事务协调者 DefaultCoordinator
    // TM: 全局事务管理者 @GlobalTransactional
    // RM: 资源管理器 DataSourceProxy, ConnectionProxy
    // XID: TransactionPropagationFilter

}
