package com.isbing.authority.permission.controller;

import com.google.common.collect.Maps;
import com.isbing.authority.common.entity.CommonResponse;
import com.isbing.authority.permission.entity.CurrentUser;
import com.isbing.authority.permission.service.MenuService;
import com.isbing.authority.permission.service.UserService;
import com.isbing.authority.permission.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

/**
 * Created by song bing
 * Created time 2019/4/18 15:53
 */
@Controller
public class RestLoginController {
	//token变量
	private static final String TOKEN = "token";
	private static Logger logger = LoggerFactory.getLogger(RestLoginController.class);

	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService;

	/**
	 *  当输入一个 没有认证的链接。springsecurity认证不通过
	 *  输入 http://127.0.0.1:9090/tt 会返回这个。。然后我们接收到 提示错误信息
	 *  http://127.0.0.1:9090/login
	 * 返回登陆的错误信息
	 *
	 * @param error
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/login")
	public CommonResponse login(HttpServletResponse response, @RequestParam Optional<String> error) {
		Cookie cookie = new Cookie(TOKEN, null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
		//权限拒绝重新登录
		CommonResponse result = new CommonResponse();
		result.setCode(1003);
		result.setMessage("您还未登录,请登录后再操作");
		return result;
	}

	/**
	 * 根据认证信息获取用户信息
	 *
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/getUser", method = RequestMethod.GET)
	public Map getUser(HttpServletRequest request) {
		Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		if (principal instanceof CurrentUser) {
			CurrentUser currentUser = (CurrentUser) principal;
			return UserUtil.getUser(currentUser, userService, menuService);
		} else {
			Map<String, Object> data = Maps.newHashMap();
			data.put("code", 1003);
			return data;
		}
		//		User user = userService.getById(currentUser.getId());
		//		// todo 这里重新加载 权限就没了
		//		// UserUtil.loadUserRole(request, user);
		//		user = menuService.getMenusByUser(user);
		//		currentUser = new CurrentUser(user);
	}

	/**
	 * 默认首页
	 *
	 * @return
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String index() {
		return "/index.html";
	}

}
