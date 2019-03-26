package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.entity.PageBean;
import com.isbing.authority.permission.entity.Permission;
import com.isbing.authority.permission.service.PermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by song bing
 * Created time 2019/3/26 16:02
 */
@Controller
@RequestMapping("/permission")
public class PermissionController {

	@Resource
	private PermissionService permissionService;

	@GetMapping("getAll")
	@ResponseBody
	public PageBean getAll(){
		return permissionService.getAll();
	}

	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody Permission permission){
		return permissionService.insert(permission);
	}

	@GetMapping("getById")
	@ResponseBody
	public Permission getById(@RequestParam(value = "id") int id){
		return permissionService.getById(id);
	}

	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody Permission permission){
		permissionService.update(permission);
	}
}
