package com.bj.day4.itcast1;

/**
 * Created by user on 2017/3/16.
 */
public class PersonImpl implements PersonInterface{
    @Override
    public void doSomeThing() {
        System.out.printf("person dosomething....");
    }

    @Override
    public void saySomeThing() {
        System.out.printf("person saysonmeting...");
    }
}
