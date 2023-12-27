package Redis_Master.Jedis_Communication;

import Redis_Master.Jedis_Communication.model.MyRedisCommand;

import java.io.IOException;

public class JedisApplicationLayer {

    private MyJedisConnection connectionLayer;

    public JedisApplicationLayer(String host, short port) {
        connectionLayer = new MyJedisConnection(host, port);
    }

    public String set(String key, String value) throws IOException {
        connectionLayer.sendCommand(MyRedisCommand.SET, key, value);
        return "OK";
    }

    public String get(String key) throws IOException {
        connectionLayer.sendCommand(MyRedisCommand.GET, key);
        return connectionLayer.getReply();
    }
}
