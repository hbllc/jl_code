package com.xgd.boss.core.vo;

/**
 * 
 * @author chenkai 
 */
public class DCResp<T> {
	
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
