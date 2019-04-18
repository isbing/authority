package com.isbing.authority.permission.config;

import com.isbing.authority.permission.service.MenuService;
import com.isbing.authority.permission.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.transaction.annotation.Transactional;

/**
 * 认证用户信息
 * Created by song bing
 * Created time 2019/4/18 16:56
 */
@Transactional(rollbackFor = Exception.class)
public class AuthenticationProviderCustom implements AuthenticationProvider {
	private static Logger logger = LoggerFactory.getLogger(AuthenticationProviderCustom.class);
	private final StringRedisTemplate redisTemplate;
	private final MenuService menuService;
	private final UserService userService;

	public AuthenticationProviderCustom(MenuService menuService, UserService userService,
			StringRedisTemplate redisTemplate) {
		this.menuService = menuService;
		this.userService = userService;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		return authentication;
	}

	@Override
	public boolean supports(Class<?> authentication) {
		//返回true后才会执行上面的authenticate方法,这步能确保authentication能正确转换类型
		return UsernamePasswordAuthenticationToken.class.equals(authentication);
	}
}
