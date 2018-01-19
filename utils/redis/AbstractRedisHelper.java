package com.xgd.boss.core.redis;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.springframework.data.redis.core.RedisTemplate;

public abstract class AbstractRedisHelper {
	
	protected abstract RedisTemplate<String, String> getTemplate();
	
	
	 /** 
     * 删除匹配的key
     * 如以my为前缀的则参数为"my*" 
     * @param pattern
     */  
	public void batchDelKeys(String pattern) {
		// 列出所有匹配的key
		Set<String> keySet = getTemplate().keys(pattern);
		if (keySet == null || keySet.size() <= 0) {
			return;
		}
		for (String key : keySet) {
			getTemplate().delete(key);
		}
	} 
	
	public void multi(){
		getTemplate().multi();
	}
	
	public void exec(){
		getTemplate().exec();
	}
	
	public void discard(){
		getTemplate().discard();
	}

	public Set<String> keys(String keys) {
		return getTemplate().keys(keys);
	}

	/** 设置redis对应的key和超时时间 */
	public boolean expire(String key, int seconds) {
		return getTemplate().expire(key, seconds, TimeUnit.SECONDS);
	}

	public boolean persist(String key) {
		return getTemplate().persist(key);
	}

	public void set(final String key, final String value) {
		getTemplate().opsForValue().set(key,value);
	}

	public boolean setWithTimeOut(String key, String value, int seconds) {
		this.set(key, value);
		return this.expire(key, seconds);
	}

	public Long lPush(String key, String... value) {
		return getTemplate().opsForList().leftPushAll(key,value);
	}
	
	public Long lPushAll(String key, Collection<String> values) {
		return getTemplate().opsForList().leftPushAll(key, values);
	}

	public List<String> getLists(String key) {
		return getTemplate().opsForList().range(key,0, -1);
	}

	public Long delLists(String key, String value) {
		return getTemplate().opsForList().remove(key,0, value);
	}

	public void setList(String key, long index, String value) {
		getTemplate().opsForList().set(key,index, value);
	}

	public void del(String keys) {
		getTemplate().delete(keys);
	}

	public String get(String key) {
		return getTemplate().opsForValue().get(key);
	}

	public Long sadd(String key, String... members) {
		return getTemplate().opsForSet().add(key,members);
	}

	public Long srem(String key, String... members) {
		return getTemplate().opsForSet().remove(key,members);
	}

	public Set<String> smembers(String key) {
		return getTemplate().opsForSet().members(key);
	}
	
	public void renameKey(String oldKey,String newKey) {
		getTemplate().rename(oldKey, newKey);
	}

	public void hset(String key, String field, String value) {
		getTemplate().opsForHash().put(key,field, value);
	}

	public String hget(String key, String field) {
		return (String) getTemplate().opsForHash().get(key,field);
	}

	public void hmset(String key, Map<String, String> hash) {
		getTemplate().opsForHash().putAll(key,hash);
	}

	public List<String> hmget(String key, String... field) {
		Collection<Object> list = new ArrayList<>();
		for (String f : field)
			list.add(f);
		List<Object> r = getTemplate().opsForHash().multiGet(key,list);
		List<String> l = new ArrayList<>();
		for (Object o : r)
			l.add((String) o);
		return l;
	}

	public Map<String, String> hgetall(String key) {
		Map<Object, Object> map = getTemplate().opsForHash().entries(key);
		Map<String, String> smap = new HashMap<>();
		for (Object k : map.keySet()) {
			smap.put(key.toString(), map.get(k).toString());
		}
		return smap;
	}

	public Long hdel(String key, String... field) {
		return getTemplate().opsForHash().delete(key,field);
	}

	public Long incr(String key) {
		return getTemplate().opsForValue().increment(key, 1);
	}

	public boolean setnx(String key, String value) {
		return getTemplate().opsForValue().setIfAbsent(key,value);
	}
	
	public void convertAndSend(String channel, Object message) {
		getTemplate().convertAndSend(channel, message);
	}
}
