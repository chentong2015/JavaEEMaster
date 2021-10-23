package distributed_transactions.seata.template.aspect;

import distributed_transactions.seata.template.annotation.GlobalTransaction;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

@Aspect
@Component
public class GlobalTransactionAspect implements Ordered {

    // 针对注解的一个切面
    @Around("@annotation(distributed_transactions.seata.template.annotation.GlobalTransaction)")
    public void invoke(ProceedingJoinPoint point) throws Throwable {
        // Before
        // 拿到切面方法上面标注的注解，确定分布式事务的开启
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        GlobalTransaction globalTransaction = method.getAnnotation(GlobalTransaction.class);
        if (globalTransaction.isFirstServer()) {
            // 需要全局事务的管理者TM
        }

        // 执行Spring的切面逻辑
        point.proceed();

        // After
    }

    // 定义切面的优先级: 高于Spring的@Transactional注解的优先级
    @Override
    public int getOrder() {
        return 10000;
    }
}
