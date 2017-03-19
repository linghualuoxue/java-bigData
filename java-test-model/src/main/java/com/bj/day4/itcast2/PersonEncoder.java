package com.bj.day4.itcast2;

import com.bj.day4.itcast2.utils.ByteBufToBytes;
import com.bj.day4.itcast2.utils.ByteObjectCoverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**反序列化
 * Created by Administrator on 2017/3/19.
 */
public class PersonEncoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        ByteBufToBytes read = new ByteBufToBytes();
        byte[] bytes = read.read(in);
        Object o = ByteObjectCoverter.byteToObject(bytes);
        out.add(o);
    }
}
