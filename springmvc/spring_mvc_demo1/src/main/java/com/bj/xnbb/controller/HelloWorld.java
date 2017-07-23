package com.bj.xnbb.controller;

import com.bj.xnbb.domain.User;
import com.bj.xnbb.server.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by hasee on 2017/7/19.
 */
@RequestMapping("/mvc")
@Controller
public class HelloWorld {


    @Resource
    private UserService userService;  //默认按照名称去找，没有的话按照类型匹配

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

    @RequestMapping("/queryUserByid")
    public ModelAndView queryUserByid(@RequestParam(value="id")String id){
        String viewName = "success";
        ModelAndView modelAndView = new ModelAndView(viewName);
        if(StringUtils.isEmpty(id)){
            modelAndView.addObject("user",userService.getUserById(Integer.parseInt(id)));
        }
        return modelAndView;
    }
}
