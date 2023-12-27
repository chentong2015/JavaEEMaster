package dubbo.master.aop;

import com.alibaba.dubbo.common.URL;
import dubbo.master.api.Car;

// AOP切面：对接口的实例进行包装，需要实现指定的接口
public class CarWrapper implements Car {

    private Car car;

    // 必须有一个属性参数的构造器
    // 源码: 将原始实现类型的实例对象赋值给包装类型的属性(通过构造器传递)
    // instance = this.injectExtension(wrapperClass.getConstructor(this.type).newInstance(instance)))
    // 最终会实现嵌套的包装
    public CarWrapper(Car car) {
        this.car = car;
    }

    // TODO: 在调用Car的实现类型的实例的方法时，会通过AOP机制调用到该方法
    @Override
    public void getColor(URL url) {
        System.out.println("Before ...");
        car.getColor(url);
        System.out.println("After ...");
    }
}
