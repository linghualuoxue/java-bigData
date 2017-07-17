package com.bj.sxt.demo1;
import io.netty.buffer.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;


/**
 * Created by hasee on 2017/7/12.
 */
public class ServerHandler extends ChannelHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         ByteBuf bf =  (ByteBuf)msg;
         byte[] data = new byte[bf.readableBytes()];
         bf.readBytes(data);
         String request = new String(data,"utf-8");
        System.out.println("Server:"+request);


    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
