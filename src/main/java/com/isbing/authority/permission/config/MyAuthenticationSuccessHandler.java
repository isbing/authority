package com.isbing.authority.permission.config;

import com.google.common.collect.Maps;
import com.isbing.authority.common.entity.CommonResponse;
import com.isbing.authority.common.util.JsonUtil;
import com.isbing.authority.permission.entity.CurrentUser;
import com.isbing.authority.permission.service.MenuService;
import com.isbing.authority.permission.service.UserService;
import com.isbing.authority.permission.util.UserUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * 登录成功 干的操作
 * Created by song bing
 * Created time 2019/4/18 13:04
 */
public class MyAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
	private static Logger logger = LoggerFactory.getLogger(MyAuthenticationSuccessHandler.class);
	//token变量
	private static final String TOKEN = "token";
	public static final long USER_SESSION_TIME = 2692000;

	private StringRedisTemplate redisTemplate;

	private UserService userService;

	private MenuService menuService;

	public MyAuthenticationSuccessHandler(UserService userService, MenuService menuService,
			StringRedisTemplate redisTemplate) {
		this.userService = userService;
		this.menuService = menuService;
		this.redisTemplate = redisTemplate;
	}

	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {
		response.setHeader("Content-type", "application/json; charset=utf-8");
		// 拿到sessionId
		String token = request.getSession().getId();
		CurrentUser currentUser = (CurrentUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		Map map = UserUtil.getUser(currentUser, userService, menuService);
		map.put(TOKEN, token);

		CommonResponse result = new CommonResponse();
		result.setCode(1000);
		result.setMessage("登录成功");
		result.setData(map);
		logger.info("token={} login success.", token);
		try {
			//设置session,后期会以这种方式替换http session
			final HashOperations hashOperations = redisTemplate.opsForHash();
			String tokenKey = "usession:" + token;
			Map data = Maps.newHashMap();
			final Integer uid = currentUser.getId();
			data.put("id", uid + "");
			//设置用户session过期时间
			data.put("expireTime", (System.currentTimeMillis() + USER_SESSION_TIME * 1000) + "");
			// 存到redis
			hashOperations.putAll(tokenKey, data);
			//设置有效期
			redisTemplate.expire(tokenKey, USER_SESSION_TIME + 1000, TimeUnit.SECONDS);
			//设置用户对应的tokenid
			final String utokenKey = "utoken:" + uid;
			final SetOperations setOperations = redisTemplate.opsForSet();
			final Set<String> members = setOperations.members(utokenKey);
			for (String userToken : members) {
				if (isExpire(userToken)) {//该token已经过期
					//从token集合里面去除
					setOperations.remove(utokenKey, userToken);
				}
			}
			//新的token写入用户session 列表
			setOperations.add(utokenKey, token);
			redisTemplate.expire(utokenKey, USER_SESSION_TIME + 2000, TimeUnit.SECONDS);
			logger.info("tokenKey={} ,data={}", tokenKey, JsonUtil.toJson(data));
		} catch (Exception e) {
			logger.error("set token Exception", e);
		}
		response.getWriter().write(JsonUtil.toJson(result));
	}

	/**
	 * 判断用户登录状态是否过期
	 *
	 * @param token 用户token
	 * @return true：过期 false：没有过期
	 */
	public final boolean isExpire(String token) {
		if (StringUtils.isBlank(token)) {
			return true;
		}
		long subTime = getExpireTime(token) - System.currentTimeMillis();
		//过期时间小于当前时间，说明已经过期
		return subTime < 120000;
	}

	/**
	 * 获取过期时间
	 *
	 * @param token
	 * @return
	 */
	public long getExpireTime(String token) {
		final String expireTimeStr = (String) redisTemplate.opsForHash().get("usession:" + token, "expireTime");
		long loginTime = -1;
		if (StringUtils.isNotBlank(expireTimeStr)) {//id存在
			loginTime = Long.valueOf(expireTimeStr);
		}
		return loginTime;
	}
}
