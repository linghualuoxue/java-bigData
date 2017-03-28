package cn.itcast.rpc.client;

/**
 * Created by user on 2017/3/28.
 */
public interface HelloServer {
    public String hello(String name);
    public String hello(Person person);
}
