package Redis_Communication.socket_server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class MockSocketServer {

    // 模拟Socket Server服务端
    // 可以通过SSH和Telnet构建通讯: localhost, 6379
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(6379);
        Socket socket = serverSocket.accept();
        System.out.println("Client connect ...");
        InputStream inputStream = socket.getInputStream();
        InputStreamReader inputReader = new InputStreamReader(inputStream);
        BufferedReader receivedStream = new BufferedReader(inputReader);
        System.out.println(receivedStream.readLine());
    }
}
