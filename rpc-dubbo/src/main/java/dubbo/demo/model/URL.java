package dubbo.demo.model;

import java.io.Serializable;

// schema://hostname:port
// 由于这里的URL信息会被持久化到本地，需要写入IO文件流，因此需要做对象的序列化
public class URL implements Serializable {

    private String hostname;
    private int port;

    public URL(String hostname, int port) {
        this.hostname = hostname;
        this.port = port;
    }

    public String getHostname() {
        return hostname;
    }

    public void setHostname(String hostname) {
        this.hostname = hostname;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }
}
