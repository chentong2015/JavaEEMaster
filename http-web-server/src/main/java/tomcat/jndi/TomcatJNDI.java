package tomcat.jndi;

// 配置JNDI服务，设置DataSource数据库连接信息
// https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
public class TomcatJNDI {

    // 配置Server连接和使用的数据资源, 通过资源名称使用DataSource
    // /conf/context.xml
    // <Resource name="jdbc/nameDataSource"
    //   auth="Container"
    //   type="javax.sql.DataSource"
    //   // The maximum number of active instances that can be allocated from the pool at the same time
    //   maxTotal="100"
    //   // The maximum number of connection that can sit idle in this pool 池中可以闲置的最大连接数
    //   maxIdle="30"
    //   // The time that pool will wait when there is no available connections
    //   maxWaitMillis="10000"
    //   username="javauser"
    //   password="javadude"
    //   driverClassName="com.mysql.jdbc.Driver"
    //   url="jdbc:mysql://localhost:3306/javatest"/>
}
