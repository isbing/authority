package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/20 14:19
 */
@Controller
@RequestMapping("/menu")
public class MenuController {
	@Resource
	private MenuService menuService;



}
