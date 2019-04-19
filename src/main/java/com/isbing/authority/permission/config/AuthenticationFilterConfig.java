package com.isbing.authority.permission.config;

import com.isbing.authority.common.util.JsonUtil;
import com.isbing.authority.permission.entity.CurrentUser;
import com.isbing.authority.permission.entity.User;
import com.isbing.authority.permission.service.MenuService;
import com.isbing.authority.permission.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Objects;

/**
 * 判断登录操作
 * Created by song bing
 * Created time 2019/4/18 13:00
 */
public class AuthenticationFilterConfig extends UsernamePasswordAuthenticationFilter {
	private static Logger LOGGER = LoggerFactory.getLogger(AuthenticationFilterConfig.class);

	@Resource
	private UserService userService;

	@Resource
	private MenuService menuService;

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws
			AuthenticationException {
		//登录方式
		//		String loginMode = request.getParameter("loginMode");
		// 获取用户名和密码
		String username = obtainUsername(request);
		String password = obtainPassword(request);

		// 校验用户名 密码
		User user = userService.getByUsername(username);
		if (Objects.isNull(user)) {
			throw new BadCredentialsException("账户或密码错误");
		}
		if (!StringUtils.equals(user.getPassword(), password)) {
			throw new BadCredentialsException("账户或密码错误");
		}
		// todo  校验用户的状态

		// 此时 user下有set<Role> role下有set<Permission>
		user = menuService.getMenusByUser(user);
		//创建 Security User
		CurrentUser currentUser = new CurrentUser(user);
		final UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
				currentUser, user.getPassword(), currentUser.getAuthorities());
		// 允许子类设置详细属性
		setDetails(request, authenticationToken);
		return getAuthenticationManager().authenticate(authenticationToken);
	}

	@Override
	protected String obtainUsername(HttpServletRequest request) {
		Object obj = request.getParameter(getUsernameParameter());
		return null == obj ? "" : obj.toString().trim();
	}

	@Override
	protected String obtainPassword(HttpServletRequest request) {
		Object obj = request.getParameter(getPasswordParameter());
		return null == obj ? "" : obj.toString().trim();
	}

}
