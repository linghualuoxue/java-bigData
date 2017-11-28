package com.bj.sxt.demo2;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hasee on 2017/7/16.
 */
public class ClientHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        Request re = new Request();
        re.setId("1");
        re.setName("name:");
        re.setRequestMessage("客户端发送消息：");
        ctx.writeAndFlush(re);

        Request request = (Request)msg;
        System.out.println("客户端接收到的数据：id:"+request.getId()+";name:"+request.getName()+";requestMessage:"+request.getRequestMessage());

    }

   /* protected void messageReceived(ChannelHandlerContext channelHandlerContext, Request request) throws Exception {
        System.out.println("客户端接收到的数据：id:"+request.getId()+";name:"+request.getName()+";requestMessage:"+request.getRequestMessage());
    }*/

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
