package cn.itcast.rpc.sample.client;

import cn.itcast.rpc.common.RpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by user on 2017/3/28.
 */
public class RpcClient extends SimpleChannelInboundHandler<RpcResponse> {
    private final static Logger logger = LoggerFactory.getLogger(RpcClient.class);

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, RpcResponse rpcResponse) throws Exception {

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        super.exceptionCaught(ctx, cause);
    }
}
