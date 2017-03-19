package com.bj.day4.itcast2.utils;

import java.io.*;

/**
 * Created by Administrator on 2017/3/19.
 */
public class ByteObjectCoverter {

    public static Object byteToObject(byte[] bytes){
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        ObjectInputStream objectInputStream = null;
        Object o = null;
        try {
            objectInputStream = new ObjectInputStream(byteArrayInputStream);
             o = objectInputStream.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if(byteArrayInputStream!=null){
                try {
                    byteArrayInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(objectInputStream!=null){
                try {
                    objectInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return o;
    }

    public byte[] objectToByte(Object obj){
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ObjectOutputStream objectOutputStream = null;
        byte[] bytes = null;
        try {
            objectOutputStream= new ObjectOutputStream(byteArrayOutputStream);
            objectOutputStream.writeObject(obj);
            bytes = byteArrayOutputStream.toByteArray();

        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(objectOutputStream!=null){
                try {
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(byteArrayOutputStream!=null){
                try {
                    byteArrayOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes;
    }
}
