package dubbo.master;

import com.alibaba.dubbo.common.extension.ExtensionLoader;
import dubbo.master.model.Car;

// TODO: Dubbo SPI机制 ==> 源码的核心
// 1. 和Java SPI机制完全不同，性能更加高效
// 2. 具有IOC依赖注入机制: 如果使用的实现类依赖了其他的类，则会自动注入
// 3. 具有AOP功能: 能够对实现类完成切面功能
public class BaseDubboSPI {

    // dubbo通过SPI机制获取的协议:
    // dubbo://  http://  rmi://  webservice://  redis:// ...
    // 注册中心:
    // Zookeeper Redis Multicast Simple

    public static void main(String[] args) {
        ExtensionLoader<Car> extensionLoader = ExtensionLoader.getExtensionLoader(Car.class);
        // 通过名称从配置的实现类型中获取
        Car car = extensionLoader.getExtension("black");
        car.getColor();
    }
}
