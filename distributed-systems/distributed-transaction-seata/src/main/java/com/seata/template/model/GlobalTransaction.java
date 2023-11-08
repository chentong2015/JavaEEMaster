package com.seata.template.model;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

// 添加注解，表示开启分布式事务
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface GlobalTransaction {

    // 注明是否是第一个事务，作为事务开启点
    boolean isFirstServer() default false;
}
