package com.xgd.boss.core.thrift.req;

import java.io.Serializable;
import java.util.List;

import com.jlpay.commons.command.CommandRequest;
import com.xgd.boss.core.utils.UUIDUtil;

/**
 * RPC请求公共参数
 * @author chengshaojin
 * @since 2017-11-27
 */
public class CommonThriftReq implements Serializable, CommandRequest {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1455588241088360436L;
	
	// 接口标志
	private String service_id;
	
	// 请求应用ID
	private String app_id;
	
	private String logid = UUIDUtil.getUUID();
	
	protected String command_id;
	
	// 以下是公共参数
	/**
	 * 客户ID，取登录返回的客户ID
	 */
	private String s_cust_id;
	
	/**
	 * 用户ID，取登录返回的用户ID
	 */
	private String s_user_id;
	
	/**
	 * 用户名，取登录返回的用户名
	 */
	private String s_user_name;
	
	/**
	 * 商户号，取登录返回的商户号列表，List格式
	 */
	private List<String> s_merch_nos;
	
	/**
	 * 平台代码，商户对账平台送mboss
	 */
	private String s_plat_code;

	/**
	 * 公共参数-商户对账平台平台代码 mboss
	 */
	public static final String PLATFORM_CODE_MBOSS = "mboss";

	/**
	 * 公共参数-商户对账平台平台代码 mgroup
	 */
	public static final String PLATFORM_CODE_MGROUP = "mgroup";

	public String getLogid() {
		return logid;
	}

	public void setLogid(String logid) {
		this.logid = logid;
	}
	
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getApp_id() {
		return app_id;
	}
	public void setApp_id(String app_id) {
		this.app_id = app_id;
	}

	@Override
	public String getCommandId() {
		return this.command_id;
	}

	@Override
	public void validate() {

	}

	public void setCommand_id(String command_id) {
		this.command_id = command_id;
	}
	public String getCommand_id() {
		return this.command_id;
	}

	public String getS_cust_id() {
		return s_cust_id;
	}

	public void setS_cust_id(String s_cust_id) {
		this.s_cust_id = s_cust_id;
	}

	public String getS_user_id() {
		return s_user_id;
	}

	public void setS_user_id(String s_user_id) {
		this.s_user_id = s_user_id;
	}

	public String getS_user_name() {
		return s_user_name;
	}

	public void setS_user_name(String s_user_name) {
		this.s_user_name = s_user_name;
	}

	public List<String> getS_merch_nos() {
		return s_merch_nos;
	}

	public void setS_merch_nos(List<String> s_merch_nos) {
		this.s_merch_nos = s_merch_nos;
	}

	public String getS_plat_code() {
		return s_plat_code;
	}

	public void setS_plat_code(String s_plat_code) {
		this.s_plat_code = s_plat_code;
	}

	@Override
	public String toString() {
		return "CommonThriftReq [service_id=" + service_id + ", app_id="
				+ app_id + ", logid=" + logid + ", command_id=" + command_id
				+ ", s_cust_id=" + s_cust_id + ", s_user_id=" + s_user_id
				+ ", s_user_name=" + s_user_name + ", s_merch_nos="
				+ s_merch_nos + ", s_plat_code=" + s_plat_code + "]";
	}
}
