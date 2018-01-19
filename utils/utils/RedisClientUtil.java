package com.xgd.boss.core.utils;

import java.io.Serializable;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * Redis全新工具类
 * @author huangweiqi
 * 2015-9-7
 */
public class RedisClientUtil {
	
	private static final Logger logger = LogManager.getLogger(RedisClientUtil.class);

	/**默认最大过期时间*/
	public static final int DEFAULT_MAX_TIMEOUT = 1209600;
	
	/**redis 模板map*/
	private static final Map<String, RedisTemplate<String, Serializable>> redisTemplateMap = new ConcurrentHashMap<>();
	
	private RedisClientUtil() {}
	
	/**
	 *  获取redis 模板
	 * @param servId redis服务id，详见CacheConst类
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public static RedisTemplate<String, Serializable> getRedisTemplate() {
		RedisTemplate<String, Serializable> redisTemplate = redisTemplateMap.get("defaultServId");
		if (redisTemplate != null) {
			return redisTemplate;
		}
		
		RedisTemplate<String, Serializable> redisTemplateTmp = (RedisTemplate<String, Serializable>) SpringContext.getBeanById("defaultRedisTemplate");
		redisTemplateMap.put("defaultServId", redisTemplateTmp);
		return redisTemplateTmp;
	}
	
	/**
	 * set操作
	 * @param key key
	 * @param value value
	 * @param servId redis服务id，详见CacheConst类
	 */
	public static void set(String key, String value) {
		set(key, value, DEFAULT_MAX_TIMEOUT);
	}
	
	/**
	 * set操作
	 * @param key key
	 * @param value value
	 * @param expireTime 过期时间(秒)
	 * @param servId redis服务id，详见CacheConst类
	 */
	public static void set(final String key, String value, final int expireTime) {
		if (key == null || key.length() == 0) {
			logger.info("key is null or empty");
			return;
		}
		
		if (value == null) {
			value = "";
		}
		
		final String finalValue = value;
		
		final RedisTemplate<String, Serializable> redisTemplate = getRedisTemplate();
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
				byte[] valueBytes = redisTemplate.getStringSerializer().serialize(finalValue);
				connection.set(keyBytes, valueBytes);
				
				if (expireTime > 0) {
					connection.expire(keyBytes, expireTime);
					
				} else {
					connection.expire(keyBytes, DEFAULT_MAX_TIMEOUT);
					
				} 
				
				return null;
			}
		});
	}
	
	/**
	 * get操作
	 * @param key key
	 * @param servId redis服务id，详见CacheConst类
	 * @return
	 */
	public static String get(final String key) {
		if (key == null || key.length() == 0) {
			logger.info("key is null or empty");
			return null;
		}
		final RedisTemplate<String, Serializable> redisTemplate = getRedisTemplate();
		return redisTemplate.execute(new RedisCallback<String>() {

			@Override
			public String doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
				byte[] valueBytes = connection.get(keyBytes);
				String value = redisTemplate.getStringSerializer().deserialize(valueBytes);
				return value;
			}
		});
	}
	
	/**
	 * del操作（批量）
	 * @param keys key数组
	 * @param servId redis服务id，详见CacheConst类
	 */
	public static void del(final String[] keys) {
		if (keys == null || keys.length == 0) {
			logger.info("keys is null or empty");
			return;
		}
		
		final RedisTemplate<String, Serializable> redisTemplate = getRedisTemplate();
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				for (String key : keys) {
					if (key == null || key.length() == 0) {
						logger.info("key is null or empty");
						continue;
					}
					
					byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
					connection.del(keyBytes);
				}
				
				return null;
			}
			
		});
		
	}
	
	/**
	 * del操作
	 * @param key key
	 * @param servId redis服务id，详见CacheConst类
	 */
	public static void del(final String key) {
		del(new String[] {key});
	}
	
	/**
	 * keys操作
	 * @param pattern 模式
	 * @param servId redis服务id，详见CacheConst类
	 * @return 返回key集合
	 */
	public static Set<String> keys(final String pattern) {
		if (pattern == null || pattern.length() == 0) {
			logger.info("pattern is null or empty");
			return Collections.emptySet();
		}
		
		final RedisTemplate<String, Serializable> redisTemplate = getRedisTemplate();
		return redisTemplate.execute(new RedisCallback<Set<String>>() {

			@Override
			public Set<String> doInRedis(RedisConnection connection) throws DataAccessException {
				byte[] patternBytes = redisTemplate.getStringSerializer().serialize(pattern);
				Set<byte[]> keyBytesSet = connection.keys(patternBytes);
				if (keyBytesSet == null) {
					return Collections.emptySet();
				}
				
				Set<String> keySet = new LinkedHashSet<>();
				
				for (byte[] keyBytes : keyBytesSet) {
					String key = redisTemplate.getStringSerializer().deserialize(keyBytes);
					keySet.add(key);
				}
				
				return keySet;
			}
			
		});
	}
	
	
	/**
	 * expire操作
	 * @param key key
	 * @param expireTime 过期时间(秒)
	 * @param servId redis服务id，详见CacheConst类
	 */
	public static void resetExpire(final String key, final int expireTime) {
		resetExpire(new String[] {key}, expireTime);
	}
	
	/**
	 * expire操作(批量)
	 * @param keys key数组
	 * @param expireTime
	 * @param servId
	 */
	public static void resetExpire(final String[] keys, final int expireTime) {
		if (keys == null || keys.length == 0) {
			logger.info("keys is null or empty");
			return;
		}
		
		final RedisTemplate<String, Serializable> redisTemplate = getRedisTemplate();
		redisTemplate.execute(new RedisCallback<Object>() {

			@Override
			public Object doInRedis(RedisConnection connection) throws DataAccessException {
				for (String key : keys) {
					if (key == null || key.length() == 0) {
						logger.info("key is null or empty");
						continue;
					}
					
					byte[] keyBytes = redisTemplate.getStringSerializer().serialize(key);
					
					if (expireTime > 0) {
						connection.expire(keyBytes, expireTime);
						
					} else {
						connection.expire(keyBytes, DEFAULT_MAX_TIMEOUT);
						
					} 
					
				}
				
				return null;
			}
			
		});
	}
	
}
