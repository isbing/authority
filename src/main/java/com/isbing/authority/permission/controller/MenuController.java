package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.entity.Menu;
import com.isbing.authority.permission.entity.PageBean;
import com.isbing.authority.permission.service.MenuService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

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

	/**
	 * 菜单管理  这样的 不用分页
	 * 找所有菜单
	 * @return
	 */
	@RequestMapping("getAll")
	@ResponseBody
	public PageBean getAll(){
		return menuService.getAll();
	}


	/**
	 * 找所有的一级菜单
	 * @return
	 */
	@RequestMapping("getFirstAll")
	@ResponseBody
	public PageBean getFirstAll(){
		return menuService.getFirstAll();
	}
}
