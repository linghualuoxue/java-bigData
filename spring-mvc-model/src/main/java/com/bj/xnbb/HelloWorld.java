package com.bj.xnbb;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Administrator on 2017/7/19.
 */
@Controller
public class HelloWorld {

    @RequestMapping("/hello")
    public String hello(){
        System.out.println("到了！");
        return "success";
    }

}
