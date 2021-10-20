package dubbo.demo.framework.spi;

import dubbo.demo.framework.protocol.Protocol;

import java.util.Iterator;
import java.util.ServiceLoader;

public class ProtocolSpi {

    public static Protocol getProtocol() {
        ServiceLoader<Protocol> serviceLoader = ServiceLoader.load(Protocol.class);
        Iterator<Protocol> iterator = serviceLoader.iterator();
        return iterator.next();
    }
}
