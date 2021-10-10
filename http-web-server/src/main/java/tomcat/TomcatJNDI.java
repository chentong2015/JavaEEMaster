package tomcat;

// Tomcat配置JNDI服务，设置DataSource数据库连接信息
// https://tomcat.apache.org/tomcat-8.0-doc/jndi-datasource-examples-howto.html
public class TomcatJNDI {

    // /conf/context.xml 配置Server连接和使用的数据资源, 通过资源名称使用DataSource
    // <Resource name="jdbc/nameDataSource"
    //   auth="Container"
    //   type="javax.sql.DataSource"
    //   maxTotal="100"         ==> The maximum number of active instances that can be allocated from the pool at the same time
    //   maxIdle="30"           ==> The maximum number of connection that can sit idle in this pool 池中可以闲置的最大连接数
    //   maxWaitMillis="10000"  ==> The time that pool will wait when there is not available connections
    //   username="javauser"
    //   password="javadude"
    //   driverClassName="com.mysql.jdbc.Driver"
    //   url="jdbc:mysql://localhost:3306/javatest"/>
}
