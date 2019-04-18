package com.isbing.authority.permission.controller;

import com.isbing.authority.permission.entity.Menu;
import com.isbing.authority.permission.entity.PageBean;
import com.isbing.authority.permission.service.MenuService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

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
	 * 找所有菜单的一级菜单
	 * @return
	 */
	@GetMapping("getAllFirstLevel")
	@ResponseBody
	public PageBean getAllFirstLevel(){
		return menuService.getAllFirstLevel();
	}

	/**
	 * 菜单管理  这样的 不用分页
	 * 找所有菜单的二级菜单
	 * @return
	 */
	@GetMapping("getAllSecondLevel")
	@ResponseBody
	public List<Menu> getAllSecondLevel(@RequestParam(value = "parentId",required = false) int parentId){
		return menuService.getAllSecondLevel(parentId);
	}

	/**
	 * 菜单管理  这样的 不用分页
	 * 找所有菜单的二级菜单
	 * @return
	 */
	@GetMapping("getAllSecondLevelNoId")
	@ResponseBody
	public List<Menu> getAllSecondLevelNoId(){
		return menuService.getAllSecondLevelNoId();
	}

	/**
	 * 新增一级菜单
	 * @return
	 */
	@PostMapping("create")
	@ResponseBody
	public Integer create(@RequestBody Menu menu){
		return menuService.create(menu);
	}

	/**
	 * 根据ID查找菜单信息
	 * @param id
	 * @return
	 */
	@GetMapping("getById")
	@ResponseBody
	public Menu getById(@RequestParam(value = "id") Integer id){
		return menuService.getById(id);
	}

	@PreAuthorize("hasAnyRole('MENU')")
	@PutMapping("update")
	@ResponseBody
	public void update(@RequestBody Menu menu){
		menuService.update(menu);
	}
}
