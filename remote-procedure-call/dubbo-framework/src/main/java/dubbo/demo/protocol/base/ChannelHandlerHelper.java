package dubbo.demo.protocol.base;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class ChannelHandlerHelper {

    // TODO: 根据传输的数据类型自定义设置Object编码和解码器
    // 在解析时需要保证类加载器的一致
    public static ChannelHandler getChannelHandler(ChannelHandler handler) {
        return new ChannelInitializer<>() {
            @Override
            protected void initChannel(Channel channel) {
                ChannelPipeline channelPipeline = channel.pipeline();
                ClassLoader classLoader = this.getClass().getClassLoader();
                ClassResolver classResolver = ClassResolvers.weakCachingConcurrentResolver(classLoader);
                channelPipeline.addLast("decoder", new ObjectDecoder(classResolver));
                channelPipeline.addLast("encoder", new ObjectEncoder());
                channelPipeline.addLast("handler", handler);
            }
        };
    }
}
