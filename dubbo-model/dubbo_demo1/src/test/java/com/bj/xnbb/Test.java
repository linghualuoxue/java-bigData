package com.bj.xnbb;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hasee on 2017/8/3.
 */
public class Test {

    public static void main(String[] args)throws Exception {
        ClassPathXmlApplicationContext applicationContext =  new ClassPathXmlApplicationContext(new String[]{"sample-provider.xml"});
        applicationContext.start();
        System.in.read(); //保证服务一直开启
    }

}
