package com.bj.day4.itcast1;

import com.bj.day4.itcast2.utils.ByteBufToBytes;
import com.bj.day4.itcast2.utils.ByteObjectCoverter;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * Created by user on 2017/3/20.
 */
public class PersonDeconder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf byteBuf, List<Object> list) throws Exception {
        ByteBufToBytes byteBufToBytes = new ByteBufToBytes();
        byte[] bytes = byteBufToBytes.read(byteBuf);
        Object o = ByteObjectCoverter.byteToObject(bytes);
        list.add(o);
    }
}
