package ga.jiangnan.nettychat;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @Author JN
 * @Date 2020/6/17 21:27
 * @Version 1.0
 * @Description
 **/
public class WebSocketNettyServer {
    public static void main(String[] args) {
        //创建两个线程
        NioEventLoopGroup mainGroup = new NioEventLoopGroup();
        NioEventLoopGroup subGroup = new NioEventLoopGroup();

        //创建启动对象
        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            //初始化服务启动对象
            serverBootstrap
                    .group(mainGroup,subGroup)
                    //指定通道类型
                    .channel(NioServerSocketChannel.class)
                    //指定通道初始化器加载当Channel收到事件后处理业务
                    .childHandler(new WebSocketChannelInitializer());

            ChannelFuture future = serverBootstrap.bind(9000).sync();
            //等待服务器关闭
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            //关闭服务器
            mainGroup.shutdownGracefully();
            subGroup.shutdownGracefully();
        }
    }
}
