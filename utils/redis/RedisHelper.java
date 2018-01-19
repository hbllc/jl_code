package com.xgd.boss.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Component
public class RedisHelper extends AbstractRedisHelper{
	
	@Autowired(required=false)
	@Qualifier("defaultRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;

	@Override
	protected RedisTemplate<String, String> getTemplate() {
		return this.redisTemplate;
	}
	
}
