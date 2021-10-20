package dubbo.master.model;

import com.alibaba.dubbo.common.extension.SPI;

// 必须在接口上标注注解
@SPI
public interface Car {

    void getColor();
}
