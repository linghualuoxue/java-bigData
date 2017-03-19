package com.bj.day4.itcast2.utils;

import io.netty.buffer.ByteBuf;

/**将byteBuf转化为byte
 * Created by Administrator on 2017/3/19.
 */
public class ByteBufToBytes {
    public byte[] read(ByteBuf datas){
        byte[] bytes = new byte[datas.readableBytes()];
        datas.readBytes(bytes);
        return bytes;
    }
}
