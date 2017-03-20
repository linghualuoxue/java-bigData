package com.bj.day4.itcast2.sendString.client;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by user on 2017/3/20.
 */
public class ClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.printf("客户端连接服务器...");
        byte[] bytes = "client is connect to server".getBytes();
        ByteBuf buffer = Unpooled.buffer(bytes.length);
        buffer.writeBytes(bytes);//发送
        ctx.writeAndFlush(buffer);//flush
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.close();
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf message) throws Exception {
        System.out.printf("客户端读取数据...");
        byte[] bytes = new byte[message.readableBytes()];
        message.readBytes(bytes);
        System.out.printf("服务端数据:"+new String(bytes,"UTF-8"));
    }
}
