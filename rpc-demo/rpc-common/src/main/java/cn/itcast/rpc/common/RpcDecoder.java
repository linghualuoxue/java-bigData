package cn.itcast.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by user on 2017/3/23.
 */
public class RpcDecoder extends ByteToMessageDecoder{

    private Class<?> genercClass;

    public RpcDecoder(Class<RpcRequest> rpcRequestClass) {
        this.genercClass = rpcRequestClass;
    }

    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {

    }
}
