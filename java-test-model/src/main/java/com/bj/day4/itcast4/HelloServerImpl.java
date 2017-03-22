package com.bj.day4.itcast4;

import com.bj.day4.itcast4.RpcServer;

/**
 * Created by Administrator on 2017/3/21.
 */
@RpcServer("helloServer")
public class HelloServerImpl implements HelloServer {
    @Override
    public void hello(String name) {
        System.out.printf("my name is:"+name);
    }
}
