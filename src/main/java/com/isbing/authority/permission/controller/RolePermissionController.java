package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.service.RolePermissionService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by song bing
 * Created time 2019/3/28 10:15
 */
@RequestMapping("/rolePermission")
@Controller
public class RolePermissionController {

	@Resource
	private RolePermissionService rolePermissionService;

	/**
	 * 针对 某一个 角色设置的权限。
	 * 需要 将这个角色ID  然后就是 点击到的 权限ID
	 */
	@PostMapping("setRolePermission")
	@ResponseBody
	public void setRolePermission(@RequestParam(value = "roleId") int roleId,
								@RequestParam(value = "permissionIds") String permissionIds){


	}


	@RequestMapping("getByRoleId")
	@ResponseBody
	public List<Integer> getByRoleId(@RequestParam(value = "roleId") int roleId){
		return rolePermissionService.getByRoleId(roleId);
	}


}
