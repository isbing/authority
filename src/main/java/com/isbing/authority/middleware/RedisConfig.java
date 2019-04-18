package com.isbing.authority.middleware;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.net.UnknownHostException;

/**
 * Created by song bing
 * Created time 2019/4/18 13:12
 */
@Configuration
public class RedisConfig {

	/**
	 * 注意，名字一定要是redisTemplate
	 * 防止生成RedisTemplate 而不是StringRedisTemplate
	 *
	 * @param redisConnectionFactory
	 * @return
	 * @exception
	 */
	@Bean
	@ConditionalOnMissingBean(value = { StringRedisTemplate.class, RedisTemplate.class })
	public StringRedisTemplate redisTemplate(RedisConnectionFactory redisConnectionFactory) {
		StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(redisConnectionFactory);
		return template;
	}
}
