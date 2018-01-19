package com.xgd.boss.core.utils;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.support.AbstractBeanFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/**
 * 
 * @author huangweiqi
 * 2015-3-18
 */
@Lazy(false)
@Component
public class SpringPropertiesUtil {
	
	private static final Log logger = LogFactory.getLog(SpringPropertiesUtil.class);

	@Resource
	private AbstractBeanFactory _beanFactory;
	private static AbstractBeanFactory beanFactory;
	
	@PostConstruct
	public void init() {
		beanFactory = _beanFactory;
	}
	
	/**
	 * 获取properties的值
	 * @param propName 属性名称
	 * @return
	 */
	public static String getPropertiesValue(String propName) {
		if (propName == null || propName.trim().length() == 0) {
			logger.warn("getPropertiesValue->propName is null or empty");
			return "";
		}
		String value = "";
		try {
			value = beanFactory.resolveEmbeddedValue(String.format("${%s}", propName.trim()));
		} catch (Exception e) {
			logger.error(e);
		}
		return value;
	}

	public static AbstractBeanFactory getBeanFactory() {
		return beanFactory;
	}

	public static void setBeanFactory(AbstractBeanFactory beanFactory) {
		SpringPropertiesUtil.beanFactory = beanFactory;
	}
	
	

}
