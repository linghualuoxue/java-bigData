package cn.itcast.rpc.simple.server;

import cn.itcast.rpc.client.HelloServer;
import cn.itcast.rpc.client.Person;
import cn.itcast.rpc.server.RpcService;

/**
 * Created by user on 2017/3/28.
 */
@RpcService(HelloServer.class)
public class HelloServerImpl implements HelloServer {
    public String hello(String name) {
        System.out.printf("hello 方法被调用!");
        return "hello :"+name;
    }

    public String hello(Person person) {
        System.out.printf("hello 对象参数方法被调用!");
        return "hello:"+person.getFirstName()+";lastName:"+person.getLastName();
    }
}
