package com.bj.xnbb;

import com.bj.xnbb.service.SampleService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by hasee on 2017/8/3.
 */
public class Consumer {

    public static void main(String[] args) {
        ClassPathXmlApplicationContext applicationContext = new ClassPathXmlApplicationContext(new String[]{"sample-consumer.xml"});
        applicationContext.start();

        SampleService sampleService =  (SampleService)applicationContext.getBean("sampleService");
        String value = sampleService.sayHello("110", SampleService.Type.LOGIN);
        System.out.println(value);

    }

}
