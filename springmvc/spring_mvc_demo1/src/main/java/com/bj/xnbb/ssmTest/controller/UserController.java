package com.bj.xnbb.ssmTest.controller;

import com.bj.xnbb.ssmTest.domain.User;
import com.bj.xnbb.ssmTest.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;

@RequestMapping("/user")
@Controller
public class UserController {

	@Resource
	private UserService userService;

	@RequestMapping("/getUserById")
	public ModelAndView getUserById(@RequestParam(value="id")String id){
		String view = "user";
		ModelAndView mv = new ModelAndView(view);
		User user = userService.getUserById(Integer.parseInt(id));
		mv.addObject("user", user);
		return mv;
	}

	@RequestMapping("/saveUser")
	public String saveUser(User user){
		userService.save(user);
		return "user";
	}

	@RequestMapping("/getUserById2")
	public String getUserById2(String id,ModelAndView mv){
		mv.addObject("user",userService.getUserById(Integer.parseInt(id)));
		return "user";
	}
	
	
	
	
	
	
	
}
