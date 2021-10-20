package dubbo.demo.framework.factory;

import dubbo.demo.framework.protocol.DubboProtocol;
import dubbo.demo.framework.protocol.HttpProtocol;
import dubbo.demo.framework.protocol.Protocol;

public class ProtocolFactory {

    // 使用JVM标准参数(-DprotocolName="xxx")来配置使用那种协议
    // 1. 只需要唯一修改来切换
    // 2. 如果新添加协议，则需要修改工厂类
    public static Protocol getProtocol() {
        String protocolName = "dubbo"; // System.getProperty("protocolName");
        if (protocolName == null || protocolName.equals("") || protocolName.equals("http")) {
            return new HttpProtocol();
        }
        return new DubboProtocol();
    }
}
