package tomcat.policy;

// cmd启动方式：apache-tomcat-8.5.65\bin\startup.bat -security
// 打开安全机制，通过conf/catalina.policy文件添加权限
public class TomcatPolicy {

    //  System.exit(1); 在doGet()方法测试调用该方法
    //
    //  public static void exit(int status) {
    //     Runtime.getRuntime().exit(status);
    //  }
    //  public void exit(int status) {
    //      SecurityManager security = System.getSecurityManager();
    //      if (security != null) {
    //          security.checkExit(status);
    //      }
    //      Shutdown.exit(status);
    //  }
    //
    //  验证当前程序是否由退出VM的权限"exitVM.", 没有则会报错，而不是执行exit(1)退出
    //  public void checkExit(int status) {
    //      checkPermission(new RuntimePermission("exitVM."+status));
    //  }

    // 默认是没有该权限的，需要在文件中添加指定的权限名称
    // permission java.lang.RuntimePermission "exitVM.1";
}
