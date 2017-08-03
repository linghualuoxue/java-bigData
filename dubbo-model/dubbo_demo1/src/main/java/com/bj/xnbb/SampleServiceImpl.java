package com.bj.xnbb;

import com.bj.xnbb.service.SampleService;

/**
 * Created by hasee on 2017/8/3.
 */
public class SampleServiceImpl implements SampleService {

    public String sayHello(String id, Type type) {
        System.out.println("服务端被调用......,id="+id);
        switch (type){
            case LOGIN: return "login";
            case EXIT:return "exit";
            default:return "null";
        }
    }
}
