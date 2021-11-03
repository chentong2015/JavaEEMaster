package tomcat.classloader;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;

// TODO: Tomcat中所有的应用都有一个对应的类加载器，相互隔离
//       可以设置是否使用双亲委派来加载 if (delegateLoad) {}
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

    // 自定的类加载器 WebAppClassLoaderBase extends URLClassLoader
    // public Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
    //     synchronized (getClassLoadingLockInternal(name)) {
    //         if (log.isDebugEnabled()) {
    //           log.debug("loadClass(" + name + ", " + resolve + ")");
    //         }
    //         Class<?> clazz = null;
    //
    //         // Log access to stopped classloader
    //         if (!started) {
    //             try {
    //                 throw new IllegalStateException();
    //             } catch (IllegalStateException e) {
    //                 log.info(sm.getString("webappClassLoader.stopped", name), e);
    //             }
    //         }
    //
    //         // (0) Check our previously loaded local class cache
    //         // 先检查该类是否已经被WebApp类加载器加载
    //         // 提供了缓存机制
    //         clazz = findLoadedClass0(name);
    //         if (clazz != null) {
    //             if (log.isDebugEnabled()) {
    //               log.debug("  Returning class from cache");
    //             }
    //             if (resolve) {
    //               resolveClass(clazz);
    //             }
    //             return (clazz);
    //         }
    //
    //         // (0.1) Check our previously loaded class cache
    //         // 该方法调用findLoadedClass0本地方法，本地方法会检查JVM缓存中是否加载过此类型
    //         clazz = findLoadedClass(name);
    //         if (clazz != null) {
    //             if (log.isDebugEnabled()) {
    //               log.debug("  Returning class from cache");
    //             }
    //             if (resolve) {
    //               resolveClass(clazz);
    //             }
    //             return (clazz);
    //         }
    //
    //         // (0.2) Try loading the class with the system class loader, to prevent
    //         //       the webapp from overriding J2SE classes
    //         // 尝试通过系统类加载器(AppClassLoader)加载类，放置webapp重写JDK中的类
    //         // 比如，webapp想加载一个java.lang.String的类型是不被允许的，必须在这里预防
    //         try {
    //             // TODO: j2seClassLoader设置的系统类型加载器
    //             clazz = j2seClassLoader.loadClass(name);
    //             if (clazz != null) {
    //                 if (resolve) {
    //                   resolveClass(clazz);
    //                 }
    //                 return (clazz);
    //             }
    //         } catch (ClassNotFoundException e) {
    //             // Ignore
    //         }
    //
    //         // (0.5) Permission to access this class when using a SecurityManager
    //         if (securityManager != null) {
    //             int i = name.lastIndexOf('.');
    //             if (i >= 0) {
    //                 try {
    //                     securityManager.checkPackageAccess(name.substring(0,i));
    //                 } catch (SecurityException se) {
    //                     String error = "Security Violation, attempt to use " +
    //                         "Restricted Class: " + name;
    //                     if (name.endsWith("BeanInfo")) {
    //                         // BZ 57906: suppress logging for calls from
    //                         // java.beans.Introspector.findExplicitBeanInfo()
    //                         log.debug(error, se);
    //                     } else {
    //                         log.info(error, se);
    //                     }
    //                     throw new ClassNotFoundException(error, se);
    //                 }
    //             }
    //         }
    //
    //         boolean delegateLoad = delegate || filter(name);
    //
    //         // (1) Delegate to our parent if requested
    //         // TODO: 是否委派给父类去加载, 可以使用双亲委派 !!
    //         if (delegateLoad) {
    //             if (log.isDebugEnabled()) {
    //               log.debug("  Delegating to parent classloader1 " + parent);
    //             }
    //             try {
    //                 clazz = Class.forName(name, false, parent);
    //                 if (clazz != null) {
    //                     if (log.isDebugEnabled()) {
    //                       log.debug("  Loading class from parent");
    //                     }
    //                     if (resolve) {
    //                       resolveClass(clazz);
    //                     }
    //                     return (clazz);
    //                 }
    //             } catch (ClassNotFoundException e) {
    //                 // Ignore
    //             }
    //         }
    //
    //         // (2) Search local repositories
    //         // 从webapp应用内部进行加载
    //         if (log.isDebugEnabled()) {
    //           log.debug("  Searching local repositories");
    //         }
    //         try {
    //             clazz = findClass(name);
    //             if (clazz != null) {
    //                 if (log.isDebugEnabled()) {
    //                   log.debug("  Loading class from local repository");
    //                 }
    //                 if (resolve) {
    //                   resolveClass(clazz);
    //                 }
    //                 return (clazz);
    //             }
    //         } catch (ClassNotFoundException e) {
    //             // Ignore
    //         }
    //
    //         // (3) Delegate to parent unconditionally
    //         // 如果webapp应用内部没有加载到类，那么无条件的委托给父类进行加载
    //         if (!delegateLoad) {
    //             if (log.isDebugEnabled()) {
    //               log.debug("  Delegating to parent classloader at end: " + parent);
    //             }
    //             try {
    //                 clazz = Class.forName(name, false, parent);
    //                 if (clazz != null) {
    //                     if (log.isDebugEnabled()) {
    //                       log.debug("  Loading class from parent");
    //                     }
    //                     if (resolve) {
    //                       resolveClass(clazz);
    //                     }
    //                     return (clazz);
    //                 }
    //             } catch (ClassNotFoundException e) {
    //                 // Ignore
    //             }
    //         }
    //     }
    //     throw new ClassNotFoundException(name);
    // }
}
