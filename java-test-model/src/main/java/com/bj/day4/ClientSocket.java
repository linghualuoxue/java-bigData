package com.bj.day4;

import java.io.*;
import java.net.Socket;

/**
 * Created by user on 2017/3/16.
 */
public class ClientSocket {
    public static void main(String[] args) {
        BufferedReader reader = null;
        PrintWriter printWriter = null;
        Socket socket = null;
        try {
                       socket = new Socket("192.168.253.1",8899);
            printWriter = new PrintWriter(new BufferedOutputStream(socket.getOutputStream()));
            printWriter.write("com.bj.day4.IBussessImpl:getPrice:yifu");
            printWriter.flush();
            printWriter.close();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String s = reader.readLine();
            System.out.printf("client result:"+s);

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
