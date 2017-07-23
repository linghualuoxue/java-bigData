package com.bj.xnbb.controller;

import javax.annotation.Resource;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.bj.xnbb.domain.User;
import com.bj.xnbb.server.UserService;

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
	
	
	
	
	
	
	
}
