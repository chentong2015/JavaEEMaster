package com.ctong.main;

import com.ctong.main.mapper.BlogMapper;
import com.ctong.main.model.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * iBatis: 2001年发起的开放源代码项目, 一个基于Java的持久层框架   ====> 2010Apache软件基金会退役, 形成现在的MyBatis
 * 1. 主要提供SQL Map和DAO(Data Access Objects)
 * 2. "半自动化"的ORM实现, 需要写SQL, 增加了程序的灵活性(作为ORM的一种补充)
 * 3. iBatis是把"实体类"和"sql语句"之间建立了映射关系 !!
 * .
 * MyBatis: 持久层框架，它支持自定义 SQL、存储过程以及高级映射
 * 1. 取消JDBC代码以及设置参数和获取结果集的代码
 * 2. MyBatis提供的所有特性都可以利用基于XML的映射语言来实现
 */
public class BaseMyBatis {

    /* 等效于XML的配置
      DataSource dataSource = BlogDataSourceFactory.getBlogDataSource();
      TransactionFactory transactionFactory = new JdbcTransactionFactory();
      Environment environment = new Environment("development", transactionFactory, dataSource);
      Configuration configuration = new Configuration(environment);
      configuration.addMapper(BlogMapper.class);  => 基于类路径和BlogMapper.class类名，会加载BlogMapper.xml
      SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);
    */
    public static void main(String[] args) throws IOException {
        // 从XML文件中构建SqlSessionFactory实例
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // SqlSession提供了在数据库执行SQL命令所需的所有方法
        // 通过SqlSession实例来直接执行已映射的SQL语句 !!
        /*
        try (SqlSession session = sqlSessionFactory.openSession()) {
            Blog blog = (Blog) session.selectOne("org.ctong.main.BlogMapper.selectBlog", 1);
            System.out.println(blog.getName());
        } */

        // 通过Java注解来调用方法, 提高编译时的类型安全性
        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(2);
            System.out.println(blog.getName());
        }
    }
}
