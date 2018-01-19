package com.xgd.boss.core.vo;

import java.nio.charset.Charset;


/**
 * 公共常量
 * @author Neo
 *
 */
public class CommonConstant {

	/**系统编码*/
	public static final String ENCODING = Charset.forName("UTF-8").toString();
	/**ISO8859-1*/
	public static final String ENCODING_ISO8859_1 = Charset.forName("ISO8859-1").toString();
	
	/**系统内容类型，html*/
	public static final String CONTENT_TYPE_HTML = "text/html;charset=" + ENCODING;
	/**系统内容类型，text*/
	public static final String CONTENT_TYPE_TEXT = "text/plain;charset=" + ENCODING;

	/**Spring配置路径*/
	public static final String SPRING_CONFIG_PATH = "classpath*:/conf/**/applicationContext-*.xml";
	
	/**zepto json 头信息*/
	public static final String ZEPTO_JSON_HEADER = "application/json, text/javascript, */*; q=0.01";
	
	//------------------------------------------------
	
	/**调用接口成功返回码定义*/
	public static final String SUCCESS_RET_CODE = "200";
	/**调用接口失败返回码定义*/
	public static final String FAIL_RET_CODE = "1";
	/**调用接口json key值定义*/
	public static final String HTTP_JSON_KEY = "jsonparam";
	
	
	public static void main(String[] args) {
		System.out.println(ENCODING);
	}
}
