package com.bj.day4.itcast4;

import com.bj.day4.itcast4.RpcServer;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * Created by Administrator on 2017/3/21.
 */
@Component
public class MyServer1 implements ApplicationContextAware {
    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("spring2.xml");
    }

    @Override
    public void setApplicationContext(ApplicationContext ctx) throws BeansException {
        Map<String, Object> beansWithAnnotation = ctx.getBeansWithAnnotation(RpcServer.class);
        for (Object o : beansWithAnnotation.values()) {
            String value = o.getClass().getAnnotation(RpcServer.class).value();
            System.out.printf("annotation value is:"+value);
            Method hello = null;
            try {
                hello = o.getClass().getMethod("hello", String.class);
                hello.invoke(o, "bb");
            } catch (Exception e) {
                e.printStackTrace();
            }

        }
    }
}
