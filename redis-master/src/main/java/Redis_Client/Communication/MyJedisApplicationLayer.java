package Redis_Client.Communication;

import Redis_Client.Communication.connection.MyJedisConnection;
import Redis_Client.Communication.model.MyRedisCommand;

import java.io.IOException;

public class MyJedisApplicationLayer {

    private MyJedisConnection connectionLayer;

    public MyJedisApplicationLayer(String host, short port) {
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
