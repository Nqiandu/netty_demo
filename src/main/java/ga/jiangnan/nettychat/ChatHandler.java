package ga.jiangnan.nettychat;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @Author JN
 * @Date 2020/6/17 21:46
 * @Version 1.0
 * @Description
 **/
public class ChatHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {

    //保存所有客户端连接
    private static ChannelGroup channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd hh:MM");

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TextWebSocketFrame textWebSocketFrame) throws Exception {
        //接收到数据后自动调用


        String text = textWebSocketFrame.text();
        System.out.println("接收到消息:"+text);

        for (Channel client : channels){
            //接收到消息发送给客户端
            client.writeAndFlush(new TextWebSocketFrame(dateFormat.format(new Date())+":"+text));
        }

    }


    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        channels.add(ctx.channel());
    }
}
