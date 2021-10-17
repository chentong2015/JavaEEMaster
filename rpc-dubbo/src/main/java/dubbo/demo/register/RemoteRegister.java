package dubbo.demo.register;

import dubbo.demo.model.URL;

import java.io.*;
import java.util.*;

// 注册到远端的注册中心
// TODO: 注意一台机器两个进程问题: 一个进程注册的信息没有办法共享给另一个进程使用
// 1. 本地方式: 使用文件共享，将信息持久化到磁盘，然后读取
// 2. 远端方式: Dubbo使用分布式中间件(redis, zk)来解决"注册中心"存储问题
public class RemoteRegister {

    private static Map<String, List<URL>> map = new HashMap<>();

    // 将URL添加到接口名称对应的list中
    public static void register(String interfaceName, URL url) {
        if (map.containsKey(interfaceName)) {
            map.get(interfaceName).add(url);
        } else {
            List<URL> list = new ArrayList<>();
            list.add(url);
            map.put(interfaceName, list);
        }
        saveFile(); // 持久化到本地文件中
    }

    // 模拟从远端注册中心拿到服务列表
    // 模拟使用"随机"负载均衡策略，从列表中取出一个URL来调用服务
    public static URL getRandomURL(String interfaceName) {
        List<URL> list = getFile().get(interfaceName);
        Random random = new Random();
        int index = random.nextInt(list.size());
        return list.get(index);
    }

    // 这里默认写入的路径是D盘
    public static void saveFile() {
        try {
            FileOutputStream outputStream = new FileOutputStream("/RemoteRegister.txt");
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(map);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Map<String, List<URL>> getFile() {
        try {
            FileInputStream fileInputStream = new FileInputStream("/RemoteRegister.txt");
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
