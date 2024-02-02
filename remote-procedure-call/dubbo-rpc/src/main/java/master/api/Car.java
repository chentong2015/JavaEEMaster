package dubbo.master.api;

import com.alibaba.dubbo.common.URL;
import com.alibaba.dubbo.common.extension.Adaptive;
import com.alibaba.dubbo.common.extension.SPI;

// 证明该接口是可以被扩展的
// 可以指定默认的实现类型
@SPI(value = "red")
public interface Car {

    // 通过URL总线中配置的信息来确定使用具体实现的类型
    @Adaptive(value = "carType")
    void getColor(URL url);
}
