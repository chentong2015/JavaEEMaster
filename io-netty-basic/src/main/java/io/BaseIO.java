package io;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

// IO通讯程序: Redis, Zookeeper, Netty, 游戏服务器
// Java IO: BIO, NIO, AIO模型
public class BaseIO {

    // >telnet localhost 9000
    // >send "message"

    // 1. BIO(Blocking IO) 阻塞IO >> 不支持高并发
    //    client --> Thread -->
    //    client --> Thread --> Server
    //    client --> Thread -->
    public void testBaseBIO() throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            // 阻塞：直到有客户端来连接
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client connected.");
            handleSocket(clientSocket);
        }
    }

    private void handleSocket(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        // 阻塞：直到拿到客户端发送的消息
        int value = socket.getInputStream().read(bytes);
        if (value != -1) {
            System.out.println("Receive: " + value);
        }
    }

    public void testBaseBIO2(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(9000);
        while (true) {
            Socket clientSocket = serverSocket.accept();
            System.out.printf("Client connected.");
            // TODO: 如果将接受客户端请求的逻辑放到新的线程中，可以避免阻塞主线程
            //       使得主线程可以再次去接受新的客户端连接
            // C10K: connection 10*1000 一万连接数的问题
            // C10M: connection 10*1M   一千万连接的问题
            // TODO: 连接的客户端过多，后天开的线程过多，内存爆炸
            //       如果使用线程池，又受到线程池的线程数量的限制
            new Thread(() -> {
                try {
                    handleSocket(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // 2. NIO(Non block IO, New IO)
    private static List<SocketChannel> channelList = new ArrayList<>();

    // TODO: 不停的循环，可能造成CPU过高
    //       每次都需要遍历list中的SocketChannel，造成不必要的性能浪费
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9000));
        // 设置server端为非阻塞
        serverSocketChannel.configureBlocking(false);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept();
            if (socketChannel != null) { // 确定有连接并且连接成功
                System.out.println("Connection OK");
                // 同样设置成非阻塞
                socketChannel.configureBlocking(false);
                channelList.add(socketChannel);
            }
            Iterator<SocketChannel> iterator = channelList.iterator();
            while (iterator.hasNext()) {
                SocketChannel sc = iterator.next();
                ByteBuffer buffer = ByteBuffer.allocate(128);
                // 在非阻塞模式下不会被阻塞
                int len = sc.read(buffer);
                if (len > 0) { // 确定有接受到来自客户端的数据
                    System.out.println("Receive: " + new String(buffer.array()));
                } else if (len == -1) {
                    iterator.remove();
                }
            }
        }
    }
}
