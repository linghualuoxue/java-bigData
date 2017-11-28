package com.bj.sxt.demo2;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * Created by hasee on 2017/7/16.
 */
public class ServerHandler extends ChannelHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
         Request request =  (Request)msg;

         System.out.println("id:"+request.getId()+";name:"+request.getName()+";requestMessage:"+request.getRequestMessage());

        Request re = new Request();
        re.setId("001");
        re.setName("server<>");
        re.setRequestMessage("服务端的返回信息");

        ctx.writeAndFlush(re);
    }

/*    protected void messageReceived(ChannelHandlerContext ctx, Request request) throws Exception {

        System.out.println("id:"+request.getId()+";name:"+request.getName()+";requestMessage:"+request.getRequestMessage());

        Request re = new Request();
        re.setId("001");
        re.setName("server<>");
        re.setRequestMessage("服务端的返回信息");

        ctx.writeAndFlush(re);   //父类中已经释放，子类不需要再释放

    }*/

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
