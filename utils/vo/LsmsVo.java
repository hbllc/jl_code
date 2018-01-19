package com.xgd.boss.core.vo;

import java.util.Date;

import com.xgd.boss.core.utils.DateUtil;

public class LsmsVo {
	
	private String mobile;
	private String msg;
	private String msgtype;
	private String sender;
	private String send_time;
	
	public LsmsVo(){
		this.msgtype = "1";
		this.sender = "lboss";
		this.send_time = DateUtil.formatDateWithTime(new Date());
	}
	
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getMsgtype() {
		return msgtype;
	}
	public void setMsgtype(String msgtype) {
		this.msgtype = msgtype;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getSend_time() {
		return send_time;
	}
	public void setSend_time(String send_time) {
		this.send_time = send_time;
	}

}
