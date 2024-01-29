package sample;

import sample.monitor.DataMonitorListenerImpl;

public class DemoZookeeperMonitor {

    public static void main(String[] args) {
        String hostPort = "127.0.0.1:2181";
        String znode = "/node";
        String filename = "/Users/tongchen/Desktop/file.txt";
        String exec[] = {"pwd"};
        try {
            new DataMonitorListenerImpl(hostPort, znode, filename, exec).run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
