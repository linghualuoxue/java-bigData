package com.bj.day4.itcast2.utils;

import com.bj.day4.itcast2.Person;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by Administrator on 2017/3/20.
 */
public class EchoClientHandler extends SimpleChannelInboundHandler<ByteBuf>{

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Person person = new Person();
        person.setAge(18);
        person.setName("angelababy");
        person.setSex("girl");
        ctx.write(person);
    }

    //从服务器接受数据
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
        System.out.printf("client conf服务器读取数据");
        byte[] bytes = new byte[msg.readableBytes()];
        msg.readBytes(bytes);
        String body = new String(bytes,"UTF-8");
        System.out.printf("服务器端数据为:"+body);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        System.out.printf("client exceptionCaught...");
        ctx.close();
    }
}
