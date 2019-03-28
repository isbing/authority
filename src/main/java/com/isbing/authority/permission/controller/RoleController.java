package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.entity.PageBean;
import com.isbing.authority.permission.entity.Role;
import com.isbing.authority.permission.service.RoleService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/28 9:51
 */
@RequestMapping("/role")
@Controller
public class RoleController {

	@Resource
	private RoleService roleService;

	@GetMapping("getAll")
	@ResponseBody
	public PageBean getAll(){
		return roleService.getAll();
	}

	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody Role role){
		return roleService.create(role);
	}

	@GetMapping("getById")
	@ResponseBody
	public Role getById(@RequestParam(value = "id") int id){
		return roleService.getById(id);
	}

	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody Role role){
		roleService.update(role);
	}
}
