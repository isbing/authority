package com.isbing.authority.permission.entity;

import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

import java.util.*;

/**
 * Security User
 * Created by song bing
 * Created time 2019/4/18 14:35
 */
public class CurrentUser extends User {
	private Integer id;
	private Set<Menu> menus = new LinkedHashSet<>();//菜单
	private Set<Role> role = new LinkedHashSet<>();//角色

	public CurrentUser(com.isbing.authority.permission.entity.User user) {
		super(user.getUserName(), user.getPassword(), AuthorityUtils.createAuthorityList(getRoles(user.getRole())));
		this.id = user.getId();
		this.menus = user.getMenus();
		this.role = user.getRole();
	}

	public Integer getId() {
		return this.id;
	}

	public Set<Role> getRole() {
		return this.role;
	}

	public Set<Menu> getMenus() {
		return this.menus;
	}

	@Override
	public String toString() {
		return "CurrentUser{" + "} " + super.toString();
	}

	/**
	 * 得到所有的权限码
	 * @param rolesSet
	 * @return
	 */
	private static String[] getRoles(Set<Role> rolesSet) {
		Iterator<Role> iter = rolesSet.iterator();
		List<String> list = new ArrayList<>();
		while (iter.hasNext()) {
			//角色
			Role role = iter.next();
			list.add(role.getCode());
			//权限
			for (Permission permission : role.getPermission()) {
				list.add(permission.getCode());
			}
		}
		String[] arr = new String[list.size()];
		return list.toArray(arr);
	}

}
