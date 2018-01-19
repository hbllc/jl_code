package com.xgd.boss.core.redis;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisClusterConfiguration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericToStringSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds=3600)
public class RedisFactory {
	private static final Log logger = LogFactory.getLog(RedisFactory.class);
	
	@Autowired
	private RedisConfig redisConfig;
	
	private RedisTemplate<String, String> rt;

	@Bean(name="defaultRedisFactory")
	public RedisConnectionFactory jedisConnectionFactory() {
		JedisPoolConfig jedisconfig = initJedisConfig();
	    JedisConnectionFactory factory = null;

    	if(redisConfig.getHostList().size()>1)
    		factory = new JedisConnectionFactory(new RedisClusterConfiguration(redisConfig.getHostList()),jedisconfig);
    	else{
    		factory = new JedisConnectionFactory(jedisconfig);
			Collection<HostAndPort> hosts = this.redisHosts(redisConfig.getHosts());
			HostAndPort hp = hosts.iterator().next();
			factory.setHostName(hp.getHost());
			factory.setPort(hp.getPort());
			factory.setUsePool(true);
    	}

	    return factory;
	}

	private JedisPoolConfig initJedisConfig() {
		JedisPoolConfig jedisconfig = new JedisPoolConfig();
	    jedisconfig.setMaxTotal(redisConfig.getMaxTotal());
	    jedisconfig.setMaxIdle(redisConfig.getMaxIdle());
	    jedisconfig.setMaxWaitMillis(redisConfig.getMaxWait());
	    jedisconfig.setTestOnBorrow(redisConfig.isTestOnBorrow());
	    jedisconfig.setTestOnReturn(redisConfig.isTestOnReturn());
		return jedisconfig;
	}
	
	private RedisConnectionFactory sposJedisFactory() {
		if(StringUtils.isBlank(redisConfig.getSposHosts()))
			return null;
		JedisPoolConfig jedisconfig = initJedisConfig();
	    JedisConnectionFactory factory = null;

    	if(redisConfig.getSposHostList().size()>1)
    		factory = new JedisConnectionFactory(new RedisClusterConfiguration(redisConfig.getSposHostList()),jedisconfig);
    	else{
    		factory = new JedisConnectionFactory(jedisconfig);
			Collection<HostAndPort> hosts = this.redisHosts(redisConfig.getSposHosts());
			HostAndPort hp = hosts.iterator().next();
			factory.setHostName(hp.getHost());
			factory.setPort(hp.getPort());
			factory.setUsePool(true);
    	}
    	factory.afterPropertiesSet();
	    return factory;
	}	
	
	@Bean(name="defaultRedisTemplate",autowire=Autowire.BY_TYPE)
	public RedisTemplate<String, String> redisTemplate(){
		rt = new RedisTemplate<>();
		RedisSerializer<String> serializer = new StringRedisSerializer();
		//GenericToStringSerializer stringSerializer = new GenericToStringSerializer();
		rt.setKeySerializer(serializer);
		rt.setValueSerializer(serializer);
		rt.setHashKeySerializer(serializer);
		rt.setHashValueSerializer(serializer);
		return rt;
	}
	
//	@Bean(name="sposRedisTemplate")
	public RedisTemplate<String, String> sposRedisTemplate(){
		if(redisConfig.getHosts().equals(redisConfig.getSposHosts())){
			return rt;
		}
		RedisTemplate<String, String> rt = new RedisTemplate<>();
		rt.setConnectionFactory(this.sposJedisFactory());
		if(rt.getConnectionFactory()==null) 
			return null;
		RedisSerializer<String> serializer = new StringRedisSerializer();
		rt.setKeySerializer(serializer);
		rt.setValueSerializer(serializer);
		rt.setHashKeySerializer(serializer);
		rt.setHashValueSerializer(serializer);
		return rt;
	}
	
	private Set<HostAndPort> redisHosts(String hosts) {
		if (StringUtils.isEmpty(hosts)) return null;
		Set<HostAndPort> hs = new HashSet<HostAndPort>();
		String[] ss = hosts.trim().split("\\s*,\\s*");
		for (String s: ss) {
			String ip = s;
			int port = 6379;
			int i = ip.indexOf(":");
			if (i>0) {
				ip = s.substring(0,i);
				port = Integer.parseInt(s.substring(i+1));
			}
			hs.add(new HostAndPort(ip,port));
		}
		return hs;
	}
	
}
