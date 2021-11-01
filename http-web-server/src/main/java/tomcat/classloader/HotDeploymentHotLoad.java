package tomcat.classloader;

// Tomcat热部署和热加载: 在不重启Tomcat的情况下，使得应用的最新代码(变动)生效
// 1. 热部署表示重新部署运用，它执行的主体是Host主机
//    监听的是部署的整个文件中的内容是否发生了变化
//
// 2. 热加载表示重新加载class，它执行的主体是Context，表示应用
//    监听的是部署文件下的/classes目录下的class文件和/lib下的jar包的变化
//

// TODO: 如何实现的 ?
// 热部署和热加载都需要监听相应的文件和文件夹是否发生了变化，都是由tomcat的后台线程触发的
public class HotDeploymentHotLoad {

}
