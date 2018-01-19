package com.xgd.boss.core.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

//@Component
public class SposRedisHelper extends AbstractRedisHelper{
	@Autowired(required=false)
	@Qualifier("sposRedisTemplate")
	private RedisTemplate<String, String> redisTemplate;
	
	@Override
	protected RedisTemplate<String, String> getTemplate() {
		return redisTemplate;
	}

}
