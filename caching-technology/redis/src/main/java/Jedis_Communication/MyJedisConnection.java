package Jedis_Communication;

import Jedis_Communication.model.MyRedisCommand;

import java.io.*;
import java.net.Socket;

// 使用Socket(底层)构建网络连接和通讯: host + 16位的port端口
public class MyJedisConnection {

    private Socket socket;
    private String host;
    private short port;

    // 通讯的比特流数据: 传出和接收
    private OutputStream outputStream; // Send stream
    private InputStream inputStream;   // Get back stream

    public MyJedisConnection(String host, short port) {
        this.host = host;
        this.port = port;
    }

    // 只有在有指令操作的时候才构建Connection ===> 性能不好 !!
    // TODO: 使用连接池或者"复用"来解决connection的问题 ??
    public MyJedisConnection sendCommand(MyRedisCommand command, String... input)
            throws IOException {
        connectRedisServer();
        JedisMessageProtocolLayer.serialize(command, outputStream, input);
        return this;
    }

    public String getReply() throws IOException {
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader receivedStream = new BufferedReader(inputReader);
        return receivedStream.readLine();
    }

    private void connectRedisServer() throws IOException {
        socket = new Socket(host, port);
        outputStream = socket.getOutputStream();
        inputStream = socket.getInputStream();
    }
}
