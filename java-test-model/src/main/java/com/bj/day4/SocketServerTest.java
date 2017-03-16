package com.bj.day4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by user on 2017/3/16.
 */
public class SocketServerTest {
    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
        try {
            ServerSocket server = new ServerSocket();
            server.bind(new InetSocketAddress("192.168.253.1",8899));
            while(true) {
                Socket socket = server.accept();
                System.out.printf("有客户端连接....");
               // pool.execute(new TaskServer(socket));
                new Thread(new TaskServer(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
