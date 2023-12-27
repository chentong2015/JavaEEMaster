package com.seata.template.aspect;

import com.seata.template.model.GlobalTransaction;
import com.seata.template.model.MyTransaction;
import com.seata.template.model.TransactionType;
import com.seata.template.transactional.GlobalTransactionManager;
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

    // 定义切面的优先级: 高于Spring的@Transactional注解的优先级
    @Override
    public int getOrder() {
        return 10000;
    }

    // 针对注解的一个切面
    @Around("@annotation(template.model.GlobalTransaction)")
    public void invoke(ProceedingJoinPoint point) {
        // 拿到切面方法上面标注的注解，确定分布式事务的开启
        MethodSignature signature = (MethodSignature) point.getSignature();
        Method method = signature.getMethod();
        GlobalTransaction globalTransaction = method.getAnnotation(GlobalTransaction.class);

        String groupId = "";
        if (globalTransaction.isFirstServer()) {
            groupId = GlobalTransactionManager.createTransactionGroup();
        }
        MyTransaction transaction = GlobalTransactionManager.createMyTransaction(groupId);
        // 执行Spring的切面逻辑: 根据抛错来判断TransactionType(提交或者回滚)
        try {
            point.proceed(); // 执行Spring切面
            transaction.setTransactionType(TransactionType.commit);
        } catch (Throwable e) {
            e.printStackTrace();
            transaction.setTransactionType(TransactionType.rollback);
        }
        // 注册到事务的协调者
        GlobalTransactionManager.registerMyTransaction(transaction);
        // 如果上面没有报错，下面全局的提交
        GlobalTransactionManager.submitGlobalTransaction(groupId);
    }
}
