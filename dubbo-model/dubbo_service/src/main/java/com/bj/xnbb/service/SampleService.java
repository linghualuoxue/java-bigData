package com.bj.xnbb.service;

/**
 * Created by hasee on 2017/8/3.
 */
public interface SampleService {

    public enum Type{

        LOGIN,EXIT;
    }

    public String sayHello(String id,Type type);

}
