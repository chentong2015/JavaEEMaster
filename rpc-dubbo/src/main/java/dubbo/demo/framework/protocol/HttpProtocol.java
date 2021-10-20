package dubbo.demo.framework.protocol;

import dubbo.demo.framework.data_model.Invocation;
import dubbo.demo.framework.data_model.URL;
import dubbo.demo.protocol.http.HttpClient;
import dubbo.demo.protocol.http.HttpServer;

public class HttpProtocol implements Protocol {

    @Override
    public void startServer(URL url) {
        HttpServer server = new HttpServer();
        server.start(url.getHostname(), url.getPort());
    }

    @Override
    public String sendInvocation(URL url, Invocation invocation) {
        HttpClient client = new HttpClient();
        return client.send(url.getHostname(), url.getPort(), invocation);
    }
}
