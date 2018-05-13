package com.bj.sxt.demo1;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;


/**
 * Created by hasee on 2017/7/12.
 */
public class Server {
    public static void main(String[] args)throws Exception {
        //boss线程组，用于接收client端连接
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        //worker线程组，用于处理实际业务操作
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        //创建辅助类，对server进行一系列的配置
        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.group(bossGroup,workerGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel sc) throws Exception {
                        sc.pipeline().addLast(new ServerHandler());
                    }
                });
        ChannelFuture f = bootstrap.bind(8687).sync();
        f.channel().closeFuture().sync();
        bossGroup.shutdownGracefully();
        workerGroup.shutdownGracefully();

    }
}
