package com.bj.day4.itcast1;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by user on 2017/3/16.
 */
public class TestPersonProxy {
    public static void main(String[] args) {
        PersonImpl person = new PersonImpl();
        PersonInterface pf = (PersonInterface)Proxy.newProxyInstance(person.getClass().getClassLoader(), person.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                return null;
            }
        });
    }
}
