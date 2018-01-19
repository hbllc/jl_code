package com.xgd.boss.core.utils;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;

import com.google.gson.ExclusionStrategy;
import com.google.gson.FieldAttributes;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.LongSerializationPolicy;

/**
 * Json工具类
 * @author Neo
 *
 */
public class JsonUtil {

    /**
     * gson对象
     */
    private final static Gson gson = new GsonBuilder().serializeNulls()
            .setDateFormat(DateUtil.YYYY_MM_DD)
            .setLongSerializationPolicy(LongSerializationPolicy.STRING)
            .disableHtmlEscaping().create();

 
    /**
     * 把对象转换成json字符串
     * @param object 待转换的对象
     * @return json格式的字符串
     */
    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    /**
     * 把对象转换成json字符串，可以配置需要忽略的属性
     * @param object 待转换的对象
     * @param ignoreProperties 需要忽略的属性list
     * @return json格式的字符串
     */
    public static String toJsonIgnore(Object object, final List<String> ignoreProperties) {
        if (ignoreProperties == null || ignoreProperties.size() == 0) {
            return toJson(object);
        }

        return new GsonBuilder().serializeNulls()
                .setDateFormat(DateUtil.YYYY_MM_DD)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String propName = null;
                        for (int i = 0, len = ignoreProperties.size(); i < len; i++) {
                            propName = ignoreProperties.get(i);
                            if (f.getName().equals(propName)) {
                                return true;
                            }
                        }
                        return false;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create().toJson(object);
    }

    /**
     * 把对象转换成json字符串，可以配置需要输出的属性
     * @param object 待转换的对象
     * @param includeProperties 需要输出的属性list
     * @return json格式的字符串
     */
    public static String toJsonInclude(Object object, final List<String> includeProperties) {
        if (includeProperties == null || includeProperties.size() == 0) {
            return toJson(object);
        }

        return new GsonBuilder().serializeNulls()
                .setDateFormat(DateUtil.YYYY_MM_DD)
                .setLongSerializationPolicy(LongSerializationPolicy.STRING)
                .disableHtmlEscaping()
                .setExclusionStrategies(new ExclusionStrategy() {

                    @Override
                    public boolean shouldSkipField(FieldAttributes f) {
                        String propName = null;
                        for (int i = 0, len = includeProperties.size(); i < len; i++) {
                            propName = includeProperties.get(i);
                            if (f.getName().equals(propName)) {
                                return false;
                            }
                        }
                        return true;
                    }

                    @Override
                    public boolean shouldSkipClass(Class<?> clazz) {
                        return false;
                    }
                }).create().toJson(object);
    }

    /**
     * json字符串转换成对象
     * @param jsonStr json格式的字符串
     * @param clz 目标对象的Class对象
     * @return 目标对象
     */
    public static <T> T fromJson(String jsonStr, Class<T> clz) {
        return gson.fromJson(jsonStr, clz);
    }
    
    public static <T> List<T> fromJson(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }
    
    public static <T> T fromJsonO(String jsonStr, Type type) {
        return gson.fromJson(jsonStr, type);
    }

	public static <T> T mapToObject(Map map,Class<T> tclass){
		String jsonStr = JsonUtil.toJson(map);
		T instance = JsonUtil.fromJson(jsonStr,tclass);
		return instance;
	}
}
