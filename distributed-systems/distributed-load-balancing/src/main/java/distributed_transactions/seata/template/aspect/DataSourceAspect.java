package distributed_transactions.seata.template.aspect;

import distributed_transactions.seata.template.connection.MyConnection;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.sql.Connection;

@Aspect
@Component
public class DataSourceAspect {

    // 对底层DataSource做切面，在Spring向数据库创建请求时，会走该切面的逻辑
    // 对一个接口方法做切面，所有该接口的实现类都会被切到
    @Around("execution(* javax.sql.DataSource.getConnection(..))")
    public Connection invoke(ProceedingJoinPoint point) throws Throwable {
        // TODO: 这里会返回Spring本身的Connection实现类
        Connection connection = (Connection) point.proceed();
        // 使用原始拿到的实现类的对象
        // 返回自定义实现的Connection给Spring，以便拿到对事务的控制权
        return new MyConnection(connection);
    }
}
