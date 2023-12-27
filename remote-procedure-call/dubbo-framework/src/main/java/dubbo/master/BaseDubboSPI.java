package dubbo.master;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.ExtensionLoader;
import dubbo.master.api.Car;
import dubbo.master.api.Driver;

import java.util.HashMap;
import java.util.Map;

// TODO: Dubbo SPI机制 ==> 源码的核心
// 1. 和Java SPI机制完全不同，性能更加高效
// 2. 具有IOC依赖注入机制: 如果使用的实现类依赖了其他的类，则会自动注入
// 3. 具有AOP功能: 能够对实现类完成切面功能

// 通过SPI机制获取协议: dubbo://  http://  rmi://  webservice://  redis:// ...
// 注册中心: Zookeeper Redis Multicast Simple
public class BaseDubboSPI {

    public static void main(String[] args) {
        // testsDubboSpiIoc();
        System.out.println("test");
    }

    private static void testDubboSpiAop() {
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        // 从配置实现类型中获取指定名称的类型
        Car car = extensionLoader.getExtension("black");
        car.getColor(null);
    }

    private static void testsDubboSpiIoc() {
        ExtensionLoader<Driver> extensionLoader = ExtensionLoader.getExtensionLoader(Driver.class);
        Driver driver = extensionLoader.getExtension("trucker");
        // 配置URL总线: 通过url找到(要注入的)具体实现类型(名称)
        Map<String, String> map = new HashMap<>();
        map.put("carType", "black");
        URL url = new URL("", "", 8000, map);
        driver.driveCar(url);
    }

    // 从以下的三个指定路径中获取信息
    // String SERVICES_DIRECTORY = "META-INF/services/";
    // String DUBBO_DIRECTORY = "META-INF/dubbo/";
    // String DUBBO_INTERNAL_DIRECTORY = "META-INF/dubbo/internal/";

    // 从配置文件中获取的信息存储在map中, 多个名称可以对应同一个实现类型
    // private Map<String, Class<?>> getExtensionClasses() {
    //     Map<String, Class<?>> classes = (Map)this.cachedClasses.get();
    //     if (classes == null) {
    //         synchronized(this.cachedClasses) {
    //             classes = (Map)this.cachedClasses.get();
    //             if (classes == null) {
    //                 classes = this.loadExtensionClasses();
    //                 this.cachedClasses.set(classes);
    //             }
    //         }
    //     }
    //     return classes;
    // }

    // 1. 将name对应的名称转成class，验证必须时接口的实现类
    //   if (line.length() > 0) {
    //      this.loadClass(extensionClasses, resourceURL, Class.forName(line, true, classLoader), name);
    //   }
    // 2. 判断是否是代理类(只能有一个) @Adaptive
    // 3. 判断是否是包装类(可以有多个) WrapperClass
}
