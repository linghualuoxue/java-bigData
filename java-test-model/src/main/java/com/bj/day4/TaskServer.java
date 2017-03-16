package com.bj.day4;

import java.io.*;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * Created by user on 2017/3/16.
 */
public class TaskServer implements Runnable {

    private Socket socket;

    public TaskServer(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        System.out.printf("服务端开始处理任务...");
        InputStream inputStream = null;
        BufferedReader reader = null;
        OutputStream outputStream = null;
        PrintWriter writer = null;
        try {
            inputStream = socket.getInputStream();
            outputStream= socket.getOutputStream();

            reader= new BufferedReader(new InputStreamReader(inputStream));
            String line = reader.readLine();
            String[] split = line.split(":");
            String className = split[0];
            String methodName = split[1];
            String methodParam = split[2];

            Class<?> forName = Class.forName(className);
            System.out.printf("class name:"+forName);
            Method method = forName.getMethod(methodName, String.class);
            System.out.printf("method name:"+method);
            Object result = method.invoke(forName.newInstance(), methodParam);
            System.out.printf("result:"+(int)result);

            writer= new PrintWriter(new BufferedOutputStream(outputStream));
            writer.write((int)result);
            writer.flush();

            reader.close();
            writer.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
}
