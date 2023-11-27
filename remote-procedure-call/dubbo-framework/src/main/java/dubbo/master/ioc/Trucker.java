package dubbo.master.ioc;

import com.alibaba.dubbo.common.URL;
import dubbo.master.api.Car;
import dubbo.master.api.Driver;

public class Trucker implements Driver {

    // 在调用Car的方法时，根据URL总线获取指定实现类型，完成属性的IoC注入
    private Car car;

    // IoC必须包含setter方法，传递一个属性参数
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public void driveCar(URL url) {
        car.getColor(url);
    }
}
