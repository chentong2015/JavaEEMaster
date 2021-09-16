package Redis_Communication;

import Redis_Communication.model.MyRedisCommand;

import java.io.IOException;
import java.io.OutputStream;

// 发送数据需要通讯协议:
// 1. 自定义协议的实现，对数据进行特殊加工
// 2. 必须使用指定的协议才能建立通讯，并操作成功

// TODO: RESP(Redis Serialization Protocol) 使用指定协议和Redis Server通讯
// In RESP, the type of some data depends on the first byte: 协议的规范设计
//    For Simple Strings the first byte of the reply is "+"
//    For Errors the first byte of the reply is "-"
//    For Integers the first byte of the reply is ":"
//    For Bulk Strings the first byte of the reply is "$"   $3 表示字符串的字符数
//    For Arrays the first byte of the reply is "*"         *3 表示数组有3个
// RESP different parts of the protocol are always terminated with "\r\n" (CRLF) 以占行或回车作为部分的结束
public class JedisMessageProtocolLayer {

    // byte[]... 支持附加多个参数的传递，同时适用.set() & .get()方法
    public static void serialize(MyRedisCommand command, OutputStream outputStream, String... input) throws IOException {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('*').append(input.length + 1).append("\\r\\n");
        stringBuffer.append('$').append(command.name().length()).append("\\r\\n");
        stringBuffer.append(command).append("\\r\\n");
        for (String str : input) {
            stringBuffer.append('$').append(str.length()).append("\\r\\n");
            stringBuffer.append(str).append("\\r\\n");
        }
        outputStream.write(stringBuffer.toString().getBytes());
    }
}
