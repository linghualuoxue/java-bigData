package cn.itcast.rpc.simple.server;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by user on 2017/3/28.
 */
public class RpcBootstrap {
    public static void main(String[] args) {
      new ClassPathXmlApplicationContext("spring.xml");
    }
}
