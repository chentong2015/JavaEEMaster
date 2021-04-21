package com.ctong.main;

import com.ctong.main.datasource.MyDataSourceFactory;
import com.ctong.main.datasource.PropertiesLoader;
import com.ctong.main.mapper.BlogMapper;
import com.ctong.main.model.Blog;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.mapping.Environment;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.apache.ibatis.transaction.TransactionFactory;
import org.apache.ibatis.transaction.jdbc.JdbcTransactionFactory;

import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * iBatis: 2001年发起的开放源代码项目, 一个基于Java的持久层框架       ====> 2010Apache软件基金会退役, 形成现在的MyBatis
 * 1. 主要提供SQL Map和DAO(Data Access Objects)
 * 2. "半自动化"的ORM实现, 需要写SQL, 增加了程序的灵活性(作为ORM的一种补充)
 * 3. iBatis是把"实体类"和"sql语句"之间建立映射关系 !!
 * .
 * MyBatis: 持久层框架，它支持自定义 SQL、存储过程以及高级映射         ====> https://mybatis.org/mybatis-3/zh/getting-started.html
 * 1. 取消JDBC代码以及设置参数和获取结果集的代码
 * 2. MyBatis提供的所有特性都可以利用基于XML映射来实现或者使用Java注解
 * 3. MyBatis的强大在于它的语句映射, 省掉了相同功能的JDBC的很多代码
 */
public class BaseMyBatis {

    public static void main(String[] args) throws IOException {
        testAnnotationBasedSqlSession();
    }

    // 全部使用XML配置，在配置信息更改后无需再编译源代码
    private static void testXmlBasedSqlSession() throws IOException {
        // 默认是资源的路径/resources
        InputStream inputStream = Resources.getResourceAsStream("mybatis-config.xml");
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);

        // SqlSession提供了在数据库执行SQL命令所需的所有方法, 直接执行已映射的SQL语句 !!
        try (SqlSession session = sqlSessionFactory.openSession()) {
            // 使用完整的名称空间来调用指定的方法
            Blog blog = session.selectOne("com.ctong.main.BlogMapper.selectBlog", 1);
            System.out.println(blog.getName());
        }
    }

    // 全部使用Java源码和注解的方式，提高编译时的类型安全性
    private static void testAnnotationBasedSqlSession() throws IOException {
        MyDataSourceFactory dataSourceFactory = new MyDataSourceFactory();
        dataSourceFactory.setProperties(PropertiesLoader.getPropertiesFromResourcePath());
        DataSource dataSource = dataSourceFactory.getDataSource();
        TransactionFactory transactionFactory = new JdbcTransactionFactory();
        Environment environment = new Environment("development", transactionFactory, dataSource);
        Configuration configuration = new Configuration(environment);
        configuration.addMapper(BlogMapper.class);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(configuration);

        try (SqlSession session = sqlSessionFactory.openSession()) {
            BlogMapper mapper = session.getMapper(BlogMapper.class);
            Blog blog = mapper.selectBlog(2);
            System.out.println(blog.getName());
        }
    }

    /**
     * 1. SqlSessionFactoryBuilder: 实例化之后，创建SqlSessionFactory之后则不再需要，最好关闭(保证在方法局部作用域)
     * .
     * 2. SqlSessionFactory: 创建之后，应该在应用的运行期间一直存在, 不应该丢弃或者创建新的实例
     * .                     应用作用域, 使用单例模式或者静态单例模式
     * .
     * 3. SqlSession: 每个线程都有它自己的SqlSession实例, 不是线程安全的, 不能共享
     * .              一般使用try-resource声明, 保证所有数据库资源都能被正确地关闭
     * .              不能将SqlSession实例的引用放在一个类的静态域，或者类型的托管作用域中
     * .              使用Web框架, 应该将SqlSession放在和HTTP请求相似的作用域中, 收到请求时打开, 返回响应后关闭 !
     * .
     * 4. Mapper: 映射器接口的实例是从SqlSession中获得的, 最好放在方法作用域内, 映射器实例并不需要被显式地关闭
     * .          MyBatis 创建结果对象的新实例时, 它都会使用一个对象工厂(ObjectFactory)实例来完成实例化工作 ! 可以自定义
     */
}
