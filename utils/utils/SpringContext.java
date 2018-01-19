package com.xgd.boss.core.utils;

import java.util.Collection;
import java.util.Map;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Service;

@Service
@Order(value=Ordered.HIGHEST_PRECEDENCE)
public class SpringContext implements ApplicationContextAware{
	
	private static ApplicationContext context;


	public static <T>T getBean(Class<T> clazz){
		return context.getBean(clazz);
	}
	
	public static <T>Collection<T> getBeans(Class<T> clazz){
		return context.getBeansOfType(clazz).values();
	}

	public static <T>Map<String, T> getBeansOfType(Class<T> clazz){
		return context.getBeansOfType(clazz);
	}
	
    public static Object getBeanById(String id){
        return context.getBean(id);
    }
	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		context = applicationContext;
	}
}
