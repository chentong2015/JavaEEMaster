package netty.basic_encode_decode;

import java.io.Serializable;

// 由于该类型的对象需要被写入IO网络流传输，因此必须能够序列化
public class DemoClass implements Serializable {

    private String name;

    public DemoClass(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
