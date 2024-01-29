package sample.monitor;

import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

// 监听ZK节点数据的执行器
// A simple example program to use DataMonitor to start and stop executables based on a znode.
// - Watch specified znode and save the data that corresponds to znode in filesystem
// - Start specified program with the specified arguments when the znode exists
// - Kill the program if the znode goes away
public class DataMonitorListenerImpl implements Watcher, Runnable, DataMonitorListener {

    private DataMonitor dataMonitor;
    private ZooKeeper zooKeeper;
    private String filename;
    private String exec[];
    private Process child;

    public DataMonitorListenerImpl(String hostPort, String znode, String filename, String exec[]) throws IOException {
        this.filename = filename;
        this.exec = exec;
        zooKeeper = new ZooKeeper(hostPort, 3000, this);
        dataMonitor = new DataMonitor(zooKeeper, znode, null, this);
    }

    public void process(WatchedEvent event) {
        dataMonitor.process(event);
    }

    public void run() {
        try {
            synchronized (this) {
                while (!dataMonitor.isDead()) {
                    wait();
                }
            }
        } catch (InterruptedException e) {
        }
    }

    public void closing(int rc) {
        synchronized (this) {
            notifyAll();
        }
    }

    public void exists(byte[] data) {
        if (data == null) {
            if (child != null) {
                System.out.println("Killing process");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                }
            }
            child = null;
        } else {
            if (child != null) {
                System.out.println("Stopping child");
                child.destroy();
                try {
                    child.waitFor();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 如果判断node节点的数据非空，则会写入指定的文件中
            try {
                FileOutputStream fos = new FileOutputStream(filename);
                fos.write(data);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                System.out.println("Starting child");
                child = Runtime.getRuntime().exec(exec);
                new StreamWriter(child.getInputStream(), System.out);
                new StreamWriter(child.getErrorStream(), System.err);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
