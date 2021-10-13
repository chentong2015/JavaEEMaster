package apache;

// Apache Http Server for windows
// http://eost.u-strasbg.fr/manual/fr/platform/windows.html

// TODO: HTTP协议端口80
// bin/httpd.exe     应用程序启动
// conf/httpd.conf   配置文件路径, 必须修改到安装的root路径
//    Define SRVROOT "D:/Logiciel/WempServer/Apache24"
//    ServerRoot "${SRVROOT}"
// htdocs/index.html 默认主页面
public class ApacheHttpServer {

    // TODO: ApacheBench
    // bin/ab.exe 网站性能压力测试指令
    // ab命令会创建很多并发访问线程，模拟多个访问者同时对某一URL地址进行访问
    // D:\Logiciel\WempServer\Apache24\bin>ab -help
    //    Usage: ab [options] [http://]hostname[:port]/path
    //    Options are:
    //        -n requests     Number of requests to perform
    //        -c concurrency  Number of multiple requests to make at a time
    //        -t timelimit    Seconds to max. to spend on benchmarking, This implies -n 50000
}
