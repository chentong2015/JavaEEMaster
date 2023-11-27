package dubbo.demo.protocol.http;

import dubbo.demo.framework.data_model.Invocation;
import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;

// 自定义模拟HttpClient的实现
public class HttpClient {

    public String send(String hostname, int port, Invocation invocation) {
        try {
            URL url = new URL("http", hostname, port, "/");
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("POST");
            connection.setDoOutput(true);

            // 写到输出流，发送数据到HttpServer(Tomcat)
            OutputStream outputStream = connection.getOutputStream();
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            // TODO: 将对象进行序列化，然后通过网络进行传输
            objectOutputStream.writeObject(invocation);
            objectOutputStream.flush();
            objectOutputStream.close(); // 这里应该被写到finally中

            // 发送请求，并且拿到Server端执行服务后的结果
            InputStream inputStream = connection.getInputStream();
            return IOUtils.toString(inputStream, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
