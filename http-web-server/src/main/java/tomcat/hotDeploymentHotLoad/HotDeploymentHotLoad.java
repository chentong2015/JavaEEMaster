package tomcat.hotDeploymentHotLoad;

// Tomcat热部署和热加载: 在不重启Tomcat的情况下，使得应用的最新代码(变动)生效
// TODO: /WEB-INF子文件夹下面的文件内容(修改时间)发生变化，不影响外层整个部署的文件夹变动(修改时间)
// 1. 热部署表示重新部署运用，它执行的主体是Host主机
//    <Host autoDeploy="true"> 热部署默认是打开的
//    监听整个部署的文件夹/ProjectDemo中内容是否发生了变化 ==> 看文件夹的修改时间
//
// 2. 热加载表示重新加载class，它执行的主体是Context，表示应用
//    <Context reloadable="false"> 热加载默认是关闭的
//    监听整个部署文件下的/classes目录下的/classes文件和/lib下jar包的变化 ==> 看文件夹的修改时间
public class HotDeploymentHotLoad {

    // 如果重新部署，则会重新生成Context对象
    // 热加载，如果重新加载，不会生成Context对象
    // StandardContext.java > reload()

}
