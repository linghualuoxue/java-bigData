package com.bj.sxt.demo2;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * Created by hasee on 2017/7/16.
 */
public class Client {

    public static void main(String[] args)throws Exception {
           EventLoopGroup worker =  new NioEventLoopGroup();
           Bootstrap bs = new Bootstrap();

           bs.group(worker)
                   .channel(NioSocketChannel.class)
                   .handler(new ChannelInitializer<SocketChannel>() {
                       protected void initChannel(SocketChannel socketChannel) throws Exception {
                         socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingDecoder());
                         socketChannel.pipeline().addLast(MarshallingCodeCFactory.buildMarshallingEncoder());
                         socketChannel.pipeline().addLast(new ClientHandler());
                       }
                   });
        ChannelFuture future = bs.connect("localhost",8586).sync();

    }
}
