package com.xgd.boss.core.vo;

import com.xgd.boss.core.utils.StringUtil;

/**
 * @author chenkai
 * 单个返回结果处理类
 *
 * @param <T>
 */
public class BaseResult<T> {
	
	protected String rtnMsg;
	protected String rtnCode;
	protected T data;
	
	public String getRtnMsg() {
		return rtnMsg;
	}
	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}
	public String getRtnCode() {
		return rtnCode;
	}
	public void setRtnCode(String rtnCode) {
		this.rtnCode = rtnCode;
	}
	public T getData() {
		return data;
	}
	public void setData(T data) {
		this.data = data;
	}
	
	public boolean isSuccess(){
		if(StringUtil.equalsIgnoreCase("0", rtnCode)){
			return true;
		}else{
			return false;
		}
	}
	
	
}
