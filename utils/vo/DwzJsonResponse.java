package com.xgd.boss.core.vo;

import java.util.HashMap;
import java.util.Map;

import com.xgd.boss.core.utils.JsonUtil;

/**
 * 类名：JsonResponse
 * DWZ指定返回json格式json实体
 */
public class DwzJsonResponse {
	//状态码
	public final static String STATUS_CODE_SUCCESS = "200";
	public final static String STATUS_CODE_FAILURE = "300";
	public final static String STATUS_CODE_TIMEOUT = "301";
	public final static String STATUS_CODE_FORBIDDEN = "403";
	//callbackType类型
	public final static String CALLBACK_TYPE_CLOSE_CURRENT = "closeCurrent";
	public final static String CALLBACK_TYPE_FORWARD = "forward";
	//rel属性
	//元素rel
	public final static String ELEMENT_REL="elementCtrl";
	//动作rel
	public final static String ACTCTRL_REL="actCtrl";
	//用户机构关系rel
	public final static String USER2BRANCH_REL = "user2branchinfo";
	//用户机构关系rel
	public final static String PARAMS_REL = "paramsinfo";
	
	//状态码200成功,300异常或者失败//301为超时//403扩展状态
	private String statusCode;
	//提示信息
	private String message;
	//导航标签id
	private String navTabId;
	//dwz框架的rel属性
	private String rel;
	//值为closeCurrent关闭当前tab，forward时才需要forwardUrl(刷新(也可标签内添加callback))
	private String callbackType;
	private String forwardUrl;
	//业务结果数据
	private Object dataValue;
	
	/**
	 * 默认操作成功
	 * @param statusCode
	 * @param message
	 */
	public DwzJsonResponse(){
		statusCode=STATUS_CODE_SUCCESS;
		message="操作成功";
		dataValue = new HashMap<String, Object>();
	}
	
	
	
	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}

	@SuppressWarnings("unchecked")
	public void putData(String key, Object value) {
		((Map<String, Object>)dataValue).put(key, value);
	}
	@SuppressWarnings("unchecked")
	public void putDataAll(Map<String,Object> map) {
		((Map<String, Object>)dataValue).putAll(map);
	}
	
	public Object getDataValue() {
		return dataValue;
	}
	public void setDataValue(Object dataValue) {
		this.dataValue = dataValue;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
	}
	
	
}
