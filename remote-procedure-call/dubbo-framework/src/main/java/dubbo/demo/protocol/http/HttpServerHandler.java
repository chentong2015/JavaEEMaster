package dubbo.demo.protocol.http;

import dubbo.demo.framework.data_model.Invocation;
import dubbo.demo.protocol.base.InvocationHelper;
import org.apache.commons.io.IOUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.Charset;

public class HttpServerHandler {

    // 处理请求(执行服务)，返回结果
    public void handleRequest(HttpServletRequest req, HttpServletResponse resp) {
        try {
            Invocation invocation = InvocationHelper.parseInvocationObjectFormHttpServlet(req);
            String result = InvocationHelper.getInvocationResult(invocation);
            // 将执行服务后的结果返回
            IOUtils.write(result, resp.getOutputStream(), Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
