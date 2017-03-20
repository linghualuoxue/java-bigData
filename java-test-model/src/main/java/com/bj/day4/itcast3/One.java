package com.bj.day4.itcast3;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * Created by user on 2017/3/20.
 */
public class One implements ApplicationContextAware,InitializingBean {

    public One() {
        System.out.printf("One 的构造方法被创建!");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.printf("afterPropertiesSet");
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.printf("setApplicationContext");
    }
}
