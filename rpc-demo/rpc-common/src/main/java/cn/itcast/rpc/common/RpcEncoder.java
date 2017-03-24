package cn.itcast.rpc.common;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * Created by user on 2017/3/23.
 */
public class RpcEncoder extends MessageToByteEncoder {

    private Class<?> genericClass;
    public RpcEncoder(Class<RpcResponse> rpcResponseClass) {
        this.genericClass = rpcResponseClass;
    }

    protected void encode(ChannelHandlerContext ctx, Object inob, ByteBuf out) throws Exception {
         if(genericClass.isInstance(inob)){
             byte[] byes = SerializetionUtils.serialize(inob);
             out.writeInt(byes.length);
             out.writeBytes(byes);
         }
    }
}
