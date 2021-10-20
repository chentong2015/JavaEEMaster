package netty.demo;

import java.io.Serializable;

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
