package com.xgd.boss.core.vo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 大数据查询util
 * 
 * @author yanfuhua
 *
 */
public class XdataQueryUtil {
	
	protected static Map<String,Object> addParam(String key, Object value, boolean isLike){
		Map<String, Object> map = new HashMap<>();
		if(isLike){
			value = "*"+value+"*";
		}
		map.put(key, value);
		return map;
	}
	
	protected static XdataParamObj and(XdataParamObj paramObj, String key, Object value, boolean isLike) {
		paramObj.setOPR(XdataParamObj.OPR_AND);
		paramObj.addDATA(addParam(key, value, isLike));
		return paramObj;
	}
	
	protected static XdataParamObj or(XdataParamObj paramObj, String key, Object value, boolean isLike) {
		paramObj.setOPR(XdataParamObj.OPR_OR);
		paramObj.addDATA(addParam(key, value, isLike));
		return paramObj;
	}
	
	/**
	 * and单个查询条件
	 * @param key
	 * @param value
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj and(String key, Object value, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		return and(paramObj, key, value, isLike);
	}
	
	/**
	 * and多个查询条件
	 * @param keys
	 * @param values
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj and(List<String> keys, List<Object> values, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		for (int i = 0; i < keys.size(); i++) {
			and(paramObj, keys.get(i), values.get(i), isLike);
		}
		return paramObj;
	}
	
	/**
	 * and多个查询条件
	 * @param keys
	 * @param values
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj and(String[] keys, Object[] values, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		for (int i = 0; i < values.length; i++) {
			and(paramObj, keys[i], values[i], isLike);
		}
		return paramObj;
	}
	
	/**
	 * and多个查询条件
	 * @param keys
	 * @param values
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj and(List<Object[]> keyValues, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		for (int i = 0; i < keyValues.size(); i++) {
			and(paramObj, (String) keyValues.get(i)[0], keyValues.get(i)[1], isLike);
		}
		return paramObj;
	}
	
	/**
	 * and多个XdataParamObj
	 * @param paramObjs
	 * @return
	 */
	public static XdataParamObj and(List<XdataParamObj> paramObjs) {
		XdataParamObj paramObj = new XdataParamObj();
		paramObj.setOPR(XdataParamObj.OPR_AND);
		for (XdataParamObj childParamObj : paramObjs) {
			paramObj.addDATA(childParamObj);
		}
		return paramObj;
	}
	
	/**
	 * 合集paramobj
	 * @param paramObj1
	 * @param paramObj2
	 * @return
	 */
	public static XdataParamObj and(XdataParamObj paramObj1, XdataParamObj paramObj2) {
		if(paramObj1==null && paramObj2==null) return null;
		if(paramObj1==null) return paramObj2;
		if(paramObj2==null) return paramObj1;
		
		XdataParamObj paramObj = new XdataParamObj();
		paramObj.setOPR(XdataParamObj.OPR_AND);
		paramObj.addDATA(paramObj1);
		paramObj.addDATA(paramObj2);
		return paramObj;
	}
	
	/**
	 * or两个条件组合
	 * @param paramObj1
	 * @param paramObj2
	 * @return
	 */
	public static XdataParamObj or(XdataParamObj paramObj1, XdataParamObj paramObj2) {
		if(paramObj1==null && paramObj2==null) return null;
		if(paramObj1==null) return paramObj2;
		if(paramObj2==null) return paramObj1;
		
		XdataParamObj paramObj = new XdataParamObj();
		paramObj.setOPR(XdataParamObj.OPR_OR);
		paramObj.addDATA(paramObj1);
		paramObj.addDATA(paramObj2);
		return paramObj;
	}
	
	/**
	 * or一个查询条件
	 * @param key
	 * @param value
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj or(String key, Object value, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		return or(paramObj, key, value, isLike);
	}
	
	/**
	 * or一组查询条件,每个条件值一样
	 * @param keys
	 * @param value
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj or(String[] keys, Object value, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		for (String key : keys) {
			or(paramObj, key, value, isLike);
		}
		return paramObj;
	}
	
	/**
	 * or一组查询条件,每个条件值不一样
	 * @param keyValues
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj or(List<Object[]> keyValues, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		for (Object[] kv: keyValues) {
			or(paramObj, (String) kv[0], kv[1], isLike);
		}
		return paramObj;
	}
	
	/**
	 * or一组查询条件,每个条件值不一样
	 * @param keys
	 * @param values
	 * @param isLike
	 * @return
	 */
	public static XdataParamObj or(String[] keys, Object[] values, boolean isLike) {
		XdataParamObj paramObj = new XdataParamObj();
		for (int i = 0; i < keys.length; i++) {
			if(values[i]!=null){
				or(paramObj, keys[i], values[i], isLike);
			}
		}
		return paramObj;
	}
	
}
