package ga.jiangnan.nettychat;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @Author JN
 * @Date 2020/6/17 21:31
 * @Version 1.0
 * @Description
 *      通道初始化器，加载通道处理器
 **/
public class WebSocketChannelInitializer extends ChannelInitializer<SocketChannel> {

    //初始化通道
    protected void initChannel(SocketChannel socketChannel) throws Exception {

        //获取管道，添加
        ChannelPipeline pipeline = socketChannel.pipeline();

        //支持http
        //添加http编解码
        pipeline.addLast(new HttpServerCodec());
        //添加大数据支持
        pipeline.addLast(new ChunkedWriteHandler());
        //添加聚合器，将HttpMessage聚合成为FullHttpRequest/Response
        pipeline.addLast(new HttpObjectAggregator(1024*64));

        //接收请求路由
        pipeline.addLast(new WebSocketServerProtocolHandler("/ws"));

        pipeline.addLast(new ChatHandler());

    }
}
