package com.bj.sxt;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Created by Administrator on 2017/4/18.
 */
public class GetData {
    public static void main(String[] args) {
        File file = new File("C:\\track.log");
        Random random = new Random();
        String[] host={"www.taobao.com"};
        String[] sessionId={"alsdefwoefhsldhfowhf","ahefowheofhwofowe","sdfhwoevwfwpe","swfhowhfowhefhwehf"};
        String[] time={"2014.01.07","2013.02.12","2016.12.12","2017.10.09"};
        StringBuilder stringBuilder = new StringBuilder();
        for (int i=0;i<50;i++){
            stringBuilder.append(host[0]+"\t"+sessionId[random.nextInt(4)]+"\t"+time[random.nextInt(4)]+"\n");
        }
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                System.out.printf("create file fail");
            }
        }
        byte[] bytes = stringBuilder.toString().getBytes();
        FileOutputStream out = null;
        try {
            out = new FileOutputStream(file);
            out.write(bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
