package com.isbing.authority.permission.config;

import com.isbing.authority.common.entity.CommonResponse;
import com.isbing.authority.common.util.JsonUtil;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登录失败的处理
 * Created by song bing
 * Created time 2019/4/18 15:37
 */
public class MyAuthenticationFailureHandler implements AuthenticationFailureHandler {

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException {
		response.setHeader("Content-type", "application/json; charset=utf-8");
		CommonResponse result = new CommonResponse();
		result.setCode(1001);
		result.setMessage(exception.getMessage());
		response.getWriter().write(JsonUtil.toJson(result));
	}
}
