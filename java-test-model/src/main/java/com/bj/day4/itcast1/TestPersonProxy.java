package com.bj.day4.itcast1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by user on 2017/3/16.
 */
public class TestPersonProxy {
    public static void main(String[] args) {
        final PersonImpl person = new PersonImpl();
        PersonInterface pf = (PersonInterface)Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                System.out.printf("调用对象方法之前....");
                if(method.getName().equals("doSomeThing")){
                    System.out.println("doSomeThing方法被调用");
                }else{
                    method.invoke(person,args);
                    System.out.printf("proxy is leaving");
                }
                return null;
            }
        });

        pf.saySomeThing();
    }
}
