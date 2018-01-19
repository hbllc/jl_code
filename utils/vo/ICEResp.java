package com.xgd.boss.core.vo;

/**
 * 
 * @author chenkai 
 */
public class ICEResp<T> {
	
	/**
	 * 返回码
	 */
	private String code;

	/**
	 * 返回结果
	 */
	private RiskdataQueryResp<T> result;
	/**
	 * 错误信息
	 */
	private String message;
	
	public static final String XDATA_QUERY_SUCCESS = "00";// 数据平台查询成功标识码
	
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public void setCode(String code){
	this.code = code;
	}
	public String getCode(){
	return this.code;
	}
	public void setResult(RiskdataQueryResp<T> result){
	this.result = result;
	}
	public RiskdataQueryResp<T> getResult(){
	return this.result;
	}

}
