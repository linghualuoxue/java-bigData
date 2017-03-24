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

    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
         if(in.readableBytes() < 4){
             return;
         }
         in.markReaderIndex();//标记当前readIndex的位置
         int dataLength = in.readInt();
        if(dataLength<0){
            ctx.close();
        }
        if(in.readableBytes() < dataLength){
            in.resetReaderIndex();
            return;
        }
        byte[] bytes = new byte[dataLength];
        in.readBytes(bytes);
        Object obj = SerializetionUtils.covertToObject(bytes,genercClass);
        out.add(obj);
    }
}
