/*
 * 文件名：NullUtil.java
 * 版权：Copyright 2016 版权所有 深圳市嘉联支付有限公司
 * 描述：该类主要是判断非空
 * 修改人：wuyiming
 * 修改时间：2016-6-6
 * 跟踪单号: <跟踪单号>
 * 修改单号: <修改单号>
 * 修改内容：<修改内容>
 */
package com.xgd.boss.core.utils;

import java.util.Collection;
import java.util.Map;

/**
 * 判断非空类
 * @author wuyiming
 * @version [版本号,YYY-MM-DD]
 * @see [相关类/方法]
 * @since [产品/模块版本]
 */
public final class NullUtil {

	/**
	 * 判断该对象是否为空判断是否为null
	 * 或""为集合长度为0返回true,否则返回false
	 * 目前支持Object、String、Map、Collection类型判断
	 * @param obj
	 * @return boolean
	 */
	@SuppressWarnings("rawtypes")
	public static boolean isNull(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String) {
			return "".equals(((String) obj).trim()) ? true : false;
		} else if (obj instanceof Collection) {
			Collection c = (Collection) obj;
			return c.size() == 0 ? true : false;
		} else if (obj instanceof Object[]) {
			Object[] objs = (Object[]) obj;
			return objs.length == 0 ? true : false;
		} else if (obj instanceof Map) {
			Map map = (Map) obj;
			return map.size() == 0 ? true : false;
		} 
		
		return false;
	}
	
}
