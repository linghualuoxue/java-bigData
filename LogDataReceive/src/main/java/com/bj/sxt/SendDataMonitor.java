package com.bj.sxt;

import com.sun.deploy.net.HttpRequest;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Created by user on 2017/1/4.
 */
public class SendDataMonitor {

    private static final Logger log = Logger.getGlobal();
    private BlockingQueue<String> queue = new LinkedBlockingDeque<String>();
    private static SendDataMonitor monitor;

    private SendDataMonitor() {
    }

    private static SendDataMonitor getSendDataMonitor(){
        if(monitor==null){
            synchronized (SendDataMonitor.class){
                if (monitor==null){
                    monitor = new SendDataMonitor();
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            SendDataMonitor.monitor.run();
                        }
                    }).start();
                }
            }
        }
        return monitor;
    }

    private void run() {
        try {
            while(true){
                String url = this.queue.take();
                HttpRequestUtil.sendData(url);
            }
        } catch (InterruptedException e) {
            log.log(Level.WARNING,"发送数据异常");
        }
    }


    public static void sendDataUrl(String url) throws InterruptedException {
        getSendDataMonitor().queue.put(url);
    }

    private static class HttpRequestUtil {

      public static void sendData(String url){
          HttpURLConnection con = null;
          BufferedReader reader = null;
          try {
              URL obj = new URL(url);
              con =  (HttpURLConnection)obj.openConnection();
              con.setConnectTimeout(5000);
              con.setReadTimeout(5000);
              con.setRequestMethod("get");
              System.out.println("发送数据："+url);
              reader = new BufferedReader(new InputStreamReader(con.getInputStream()));
          } catch (IOException e) {
              log.log(Level.WARNING,"url连接异常");
          } finally {
              try {
                  if(reader!=null)reader.close();
              } catch (IOException e) {
                  log.log(Level.WARNING,"关闭读异常");
              }
              if(con!=null)con.disconnect();
          }
      }
    }
}
