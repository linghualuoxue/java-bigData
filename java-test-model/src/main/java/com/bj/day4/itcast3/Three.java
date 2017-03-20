package com.bj.day4.itcast3;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

/**
 * Created by user on 2017/3/20.
 */
@Service
public class Three {
    public Three(){
        System.out.printf("this is three");
    }
    public Three(String name){
        System.out.printf("name:"+name);
    }
}
