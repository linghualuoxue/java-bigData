package com.bj.xnbb.action;

import com.bj.xnbb.domain.User;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Date;

/**
 * Created by hasee on 2017/7/19.
 */
@RequestMapping("/mvc")
@Controller
public class HelloWorld {

    @RequestMapping("/hello")
    public String hello(@RequestHeader(value="Accept-Language")String accep){
        System.out.println("accept:"+accep);
        return "success";
    }

    @RequestMapping("/testPojo")
    public String testPojo(User user){
        System.out.println("userId:"+user.getId()+";userName:"+user.getName()+";password:"+user.getPassword());

        return "success";
    }

    @RequestMapping("/modelAndView")
    public ModelAndView testModelAndView(){

        String viewName="success";
        ModelAndView mv = new ModelAndView(viewName);
        mv.addObject("date",new Date());
        return mv;
    }
}
