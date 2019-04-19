package com.isbing.authority.permission.util;

import com.isbing.authority.permission.entity.CurrentUser;
import com.isbing.authority.permission.entity.Menu;
import com.isbing.authority.permission.entity.User;
import com.isbing.authority.permission.service.MenuService;
import com.isbing.authority.permission.service.UserService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by song bing
 * Created time 2019/4/18 15:25
 */
public class UserUtil {

	public static Map getUser(CurrentUser currentUser, UserService userService, MenuService menuService) {
		String username = currentUser.getUsername();
		User user = userService.getByUsername(username);//后台客服,管理员
		Map<String, Object> map = new LinkedHashMap<>();
		map.put("user", currentUser);//用户信息全部信息
		// 设置后台客服的 菜单 角色
		user.setMenus(currentUser.getMenus());
		user.setRole(currentUser.getRole());
		map.put("staff", user);//后台管理员
		map.put("userName", user.getNickName());
		map.put("menuUrl", getStaffMenu(currentUser.getMenus(), menuService));//后台管理菜单
		return map;
	}

	//设置后台菜单
	private static Map getStaffMenu(Set<Menu> menusSet, MenuService menuService) {
		Map<String, Map> mapMenu = new LinkedHashMap<>();
		Iterator<Menu> iter = menusSet.iterator();
		while (iter.hasNext()) {
			Map map = new LinkedHashMap();
			Menu menu = iter.next();
			int parentId = menu.getParentId();
			Menu parentMenu = menuService.getById(parentId);
			String parentMenuName = parentMenu.getUrl();
			if (mapMenu.get(parentMenuName) != null) {
				map = mapMenu.get(parentMenuName);
			}
			map.put(menu.getName(), menu.getUrl());
			mapMenu.put(parentMenuName, map);
		}
		return mapMenu;
	}

	/**
	 * 重新加载用户权限
	 * @param request
	 * @param user
	 */
	public static void loadUserRole(HttpServletRequest request, User user) {
		CurrentUser currentUser = new CurrentUser(user);
		UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(currentUser,
				user.getPassword(), currentUser.getAuthorities());
		//设置authentication中details
		authentication.setDetails(new WebAuthenticationDetails(request));
		//存放authentication到SecurityContextHolder
		SecurityContextHolder.getContext().setAuthentication(authentication);
		HttpSession session = request.getSession(true);
		//在session中存放security context,方便同一个session中控制用户的其他操作
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
	}
}
