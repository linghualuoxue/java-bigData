package com.bj.day4.itcast2;

import com.bj.day4.itcast2.utils.EchoClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * Created by Administrator on 2017/3/19.
 */
public class EchoCliet {
    private final String host;
    private final int port;

    public EchoCliet(String host,int port){
        this.host = host;
        this.port = port;
    }

    public void start(){
        //启动器
        Bootstrap bootstrap = null;
        EventLoopGroup loopGroup = null;
        try {
            bootstrap = new Bootstrap();
            loopGroup = new NioEventLoopGroup();
            bootstrap.group(loopGroup)
                    .channel(NioSocketChannel.class)
                    .remoteAddress(new InetSocketAddress(host,port))
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel sc) throws Exception {
                            sc.pipeline().addLast(new PersonEncoder());//注册编码的handler
                            sc.pipeline().addLast(new EchoClientHandler());//注册处理消息的handler
                        }
                    });
            ChannelFuture f = bootstrap.connect().sync();//调用connect来连接
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }finally {
            if(loopGroup!=null){
                try {
                    loopGroup.shutdownGracefully().sync();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        new EchoCliet("localhost",8888).start();
    }
}
