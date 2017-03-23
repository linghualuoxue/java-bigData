package cn.itcast.rpc.server;

import cn.itcast.rpc.common.RpcRequest;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

/**
 * Created by user on 2017/3/23.
 */
public class RpcHandler extends SimpleChannelInboundHandler<RpcRequest> {
    public RpcHandler(Map<String, Object> handlerMap) {
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcRequest rpcRequest) throws Exception {

    }
}
