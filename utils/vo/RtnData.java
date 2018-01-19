package com.xgd.boss.core.vo;

/**
 * 返回统一信息所用
 */
public class RtnData<T> {

	/** 返回码-0:成功，其他为异常 */
	private int rtnCode;

	/** 返回提示信息，成功则不返回任何信息 */
	private String rtnMsg;

	/** 返回数据，list */
	private T data;

	public String getRtnMsg() {
		return rtnMsg;
	}

	public void setRtnMsg(String rtnMsg) {
		this.rtnMsg = rtnMsg;
	}

	public int getRtnCode() {
		return rtnCode;
	}

	public void setRtnCode(int rtnCode) {
		this.rtnCode = rtnCode;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
}
