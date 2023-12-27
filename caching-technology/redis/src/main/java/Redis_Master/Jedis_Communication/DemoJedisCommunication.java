package Redis_Master.Jedis_Communication;

import redis.clients.jedis.Jedis;

import java.io.IOException;

// Redis Java Client: Jedis & Lettuce
// TODO: 底层是如何实现Jedis <-> Redis之间的通讯, 自定义MyJedis的通讯实现
// 1. 应用层     操作层API         ===> 提供用户调用的公开接口
// 2. 消息处理层  Message Protocol ===> 提供Redis Server通讯的特殊协议 RESP(Redis Serialization Protocol)
// 3. 传输层     TCP/UDP          ===> 网络传输层使用的可靠协议
// 4. 比特流传输
public class DemoJedisCommunication {

    public static void main(String[] args) {
        Jedis jedis = new Jedis("8.209.74.47", 6379);
        jedis.set("key1", "my key");
        System.out.println(jedis.get("key1"));
        System.out.println(jedis.get("name"));
        jedis.close();
    }

    // 测试自定义实现的Redis Client客户端
    public static void testMyJedis() throws IOException {
        String host = "127.0.0.1";
        short port = 6379;
        JedisApplicationLayer myJedis = new JedisApplicationLayer(host, port);
        myJedis.set("name", "new name");
        System.out.println(myJedis.get("name"));
    }
}
