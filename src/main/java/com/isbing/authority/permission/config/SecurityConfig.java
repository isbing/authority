package com.isbing.authority.permission.config;

import com.isbing.authority.permission.service.MenuService;
import com.isbing.authority.permission.service.UserService;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

import javax.annotation.Resource;

/**
 * 对操作的一些配置
 * Created by song bing
 * Created time 2019/4/18 11:41
 */
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

	@Resource
	private UserService userService;

	@Resource
	private StringRedisTemplate stringRedisTemplate;

	@Resource
	private MenuService menuService;

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable();//csrf禁用
		http.headers().frameOptions().sameOrigin();
		http.authorizeRequests()
				.requestMatchers(CorsUtils::isPreFlightRequest).permitAll()
				.antMatchers("/", "/signin.html", "/index.html", "/noPerm.html").permitAll()//登录相关
				//.antMatchers("/menu/**").permitAll()
				//.anyRequest().authenticated()//默认都能访问
				//.antMatchers("/","/hello").permitAll()//定义/请求不需要验证
				.anyRequest().authenticated()//其余的所有请求都需要验证
				.and()
				.formLogin()
				.loginPage("/login")
				.usernameParameter("username")
				.passwordParameter("password")
				.permitAll()
				.and()
				.logout()
				.logoutUrl("/logout")
				.deleteCookies("token")
				.logoutSuccessUrl("/")
				.invalidateHttpSession(true)
				.permitAll()
				.and()
				.rememberMe()
				.and()
				.sessionManagement()
				.maximumSessions(1)
				.expiredUrl("/login?expired");
		http.addFilterBefore(authenticationFilter(), UsernamePasswordAuthenticationFilter.class);

	}

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		//设置验证的具体地方
		AuthenticationProvider authenticationProvider = new AuthenticationProviderCustom(menuService, userService,
				stringRedisTemplate);
		auth.authenticationProvider(authenticationProvider);
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring().antMatchers("/global/**");
	}

	/**
	 * 自定义UsernamePasswordAuthenticationFilter
	 *
	 * @return
	 * @throws Exception
	 */
	@Bean
	public AuthenticationFilterConfig authenticationFilter() throws Exception {
		//创建
		AuthenticationFilterConfig usernamePasswordAuthenticationFilterConfig = new AuthenticationFilterConfig();
		usernamePasswordAuthenticationFilterConfig.setAuthenticationManager(authenticationManagerBean());
		//设置登录失败跳转的地方
		usernamePasswordAuthenticationFilterConfig
				.setAuthenticationFailureHandler(new MyAuthenticationFailureHandler());
		//设置登录成功跳转的地方
		usernamePasswordAuthenticationFilterConfig.setAuthenticationSuccessHandler(
				new MyAuthenticationSuccessHandler(userService, menuService, stringRedisTemplate));
		return usernamePasswordAuthenticationFilterConfig;
	}

}
