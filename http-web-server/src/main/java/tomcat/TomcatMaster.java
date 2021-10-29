package tomcat;

// Tomcat作为Http Server，默认端口号8080
// cmd启动方式：apache-tomcat-8.5.65\bin\startup.bat
public class TomcatMaster {

    // 根据Server.xml中<Connector>配置，使用不同的IO模型
    // public class Connector extends LifecycleMBeanBase
    //   if ("HTTP/1.1".equals(protocol)) {
    //       setProtocolHandlerClassName
    //       ("org.apache.coyote.http11.Http11Protocol");
    //   } else if ("AJP/1.3".equals(protocol)) {
    //       setProtocolHandlerClassName
    //       ("org.apache.coyote.ajp.AjpProtocol");
    //   } else if (protocol != null) {
    //       指定配置类名的路径
    //       setProtocolHandlerClassName(protocol);
    //   }

    // Tomcat对HTTP协议的实现类：
    // 1. Http11Protocol 支持BIO模型
    //    public Http11Protocol() {
    //        endpoint = new JIoEndpoint(); 端点, JIO -> BIO
    //        cHandler = new Http11ConnectionHandler(this);
    //        ((JIoEndpoint) endpoint).setHandler(cHandler);
    //        setSoLinger(Constants.DEFAULT_CONNECTION_LINGER);
    //        setSoTimeout(Constants.DEFAULT_CONNECTION_TIMEOUT);
    //        setTcpNoDelay(Constants.DEFAULT_TCP_NO_DELAY);
    //    }
    //
    //    public class JIoEndpoint {
    //        在端点中开启一个线程，使用BIO来取数据
    //        protected class Acceptor extends AbstractEndpoint.Acceptor {
    //           socket = serverSocketFactory.acceptSocket(serverSocket);
    //           processSocket(Socket socket); 处理socket的数据
    //        }
    //    }
    //
    // 2. Http11NioProtocol 支持NIO模型
    //    public class NIoEndpoint {
    //       在端点中开启一个线程，使用NIO来取数据
    //       protected class Acceptor extends AbstractEndpoint.Acceptor {
    //           SocketChannel socket = null;
    //           try {
    //             // Accept the next incoming connection from the server socket
    //             socket = serverSock.accept();
    //       }
    //    }

    // Tomcat通过IO模型(NIO)从Socket中获取数据，然后进行解析  ===> 最终封装成Request !!
    //    protected boolean processSocket(Socket socket) {
    //        // Process the request from this socket
    //        try {
    //            SocketWrapper<Socket> wrapper = new SocketWrapper<Socket>(socket);
    //            wrapper.setKeepAliveLeft(getMaxKeepAliveRequests());
    //            wrapper.setSecure(isSSLEnabled());
    //            // During shutdown, executor may be null - avoid NPE
    //            if (!running) {
    //                return false;
    //            }
    //            TODO: 使用线程池进行处理 ==> 调优线程池的大小
    //            getExecutor().execute(new SocketProcessor(wrapper));
    //        } catch (RejectedExecutionException x) {
    //            log.warn("Socket processing request was rejected for:"+socket,x);
    //            return false;
    //        } catch (Throwable t) {
    //            ExceptionUtils.handleThrowable(t);
    //            // This means we got an OOM or similar creating a thread, or that
    //            // the pool and its queue are full
    //            log.error(sm.getString("endpoint.process.fail"), t);
    //            return false;
    //        }
    //        return true;
    //    }
    //
    // 真正处理数据的类型 AbstractHttp11Processor<S>
    //    public SocketState process(SocketWrapper<S> socketWrapper) {
    //       getInputBuffer().parseRequestLine(keptAlive) 解析请求行
    //       getInputBuffer().parseHeaders() 解析请求头
    //       解析请求体(发送的数据)
    //    }
}
