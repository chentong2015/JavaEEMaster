package tomcat.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

// Tomcat使用自定义的ClassLoader
// URLClassLoader
public class UrlClassLoaderTest {

    // URLClassLoader加载类的时候也具有安全机制
    // public final Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    //     // First check if we have permission to access the package. This
    //     // should go away once we've added support for exported packages.
    //     SecurityManager sm = System.getSecurityManager();
    //     if (sm != null) {
    //         int i = name.lastIndexOf('.');
    //         if (i != -1) {
    //             sm.checkPackageAccess(name.substring(0, i));
    //         }
    //     }
    //     return super.loadClass(name, resolve);
    // }
    private void testUrlClassLoader() {
        try {
            URL url = new URL("../dubbo-rpc-api.jar");
            URL[] urls = new URL[]{url};
            URLClassLoader urlClassLoader = new URLClassLoader(urls);

            Class clazz = urlClassLoader.loadClass("org.apache.dubbo.rpc.filter");
            Class clazz1 = urlClassLoader.loadClass("com.tomcat.Test1");

            System.out.println(clazz.getClassLoader().toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
