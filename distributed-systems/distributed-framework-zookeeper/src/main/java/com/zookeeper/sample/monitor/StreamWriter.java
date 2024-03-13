package com.zookeeper.sample.monitor;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

// 异步执行Stream流的写出
public class StreamWriter extends Thread {

    private OutputStream os;
    private InputStream is;

    public StreamWriter(InputStream is, OutputStream os) {
        this.is = is;
        this.os = os;
        start();
    }

    public void run() {
        byte b[] = new byte[80];
        int rc;
        try {
            while ((rc = is.read(b)) > 0) {
                os.write(b, 0, rc);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
