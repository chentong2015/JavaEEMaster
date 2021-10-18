package dubbo.demo.protocol.base;

import dubbo.demo.model.Invocation;

public interface ProtocolClient {
    
    String send(String hostname, int port, Invocation invocation);
}
