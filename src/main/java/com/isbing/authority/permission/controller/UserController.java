package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.entity.PageBean;
import com.isbing.authority.permission.entity.User;
import com.isbing.authority.permission.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/28 17:05
 */
@RequestMapping("user")
@Controller
public class UserController {

	@Resource
	private UserService userService;

	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody User user){
		return userService.create(user);
	}


	@GetMapping("getAll")
	@ResponseBody
	public PageBean getAll(){
		return userService.getAll();
	}
}
