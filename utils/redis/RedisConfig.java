package com.xgd.boss.core.redis;

import java.util.Collection;
import java.util.HashSet;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class RedisConfig {
	@Value("${redis.hosts}")
	private String hosts;
	@Value("${redis.spos.hosts:}")
	private String sposHosts;
	@Value("${redis.timeout:300000}")
	private int timeout;
	@Value("${redis.maxRedirections:6}")
	private int maxRedirections;
	@Value("${redis.pool.maxTotal:1000}")
	private int maxTotal;
	@Value("${redis.pool.maxIdle:100}")
	private int maxIdle;
	@Value("${redis.pool.maxWait:1000}")
	private long maxWait;
	@Value("${redis.pool.testOnBorrow:true}")
	private boolean testOnBorrow;
	@Value("${redis.pool.testOnReturn:true}")
	private boolean testOnReturn;
	public String getHosts() {
		return hosts;
	}
	public void setHosts(String hosts) {
		this.hosts = hosts;
	}
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	public int getMaxRedirections() {
		return maxRedirections;
	}
	public void setMaxRedirections(int maxRedirections) {
		this.maxRedirections = maxRedirections;
	}
	public int getMaxTotal() {
		return maxTotal;
	}
	public void setMaxTotal(int maxTotal) {
		this.maxTotal = maxTotal;
	}
	public int getMaxIdle() {
		return maxIdle;
	}
	public void setMaxIdle(int maxIdle) {
		this.maxIdle = maxIdle;
	}
	public long getMaxWait() {
		return maxWait;
	}
	public void setMaxWait(long maxWait) {
		this.maxWait = maxWait;
	}
	public boolean isTestOnBorrow() {
		return testOnBorrow;
	}
	public void setTestOnBorrow(boolean testOnBorrow) {
		this.testOnBorrow = testOnBorrow;
	}
	public boolean isTestOnReturn() {
		return testOnReturn;
	}
	public void setTestOnReturn(boolean testOnReturn) {
		this.testOnReturn = testOnReturn;
	}
	
	public String getSposHosts() {
		return sposHosts;
	}
	public void setSposHosts(String sposHosts) {
		this.sposHosts = sposHosts;
	}
	
	public Collection<String> getSposHostList(){
		String[] ss = sposHosts.trim().split("\\s*,\\s*");
		Collection<String> list = new HashSet<String>();
		for(String s :ss)
			list.add(s);
		return list;
	}
	
	public Collection<String> getHostList(){
		String[] ss = hosts.trim().split("\\s*,\\s*");
		Collection<String> list = new HashSet<String>();
		for(String s :ss)
			list.add(s);
		return list;
	}
	
}
