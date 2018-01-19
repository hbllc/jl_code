package com.xgd.boss.core.vo;

import java.io.Serializable;
/**
 * 当前登录用户信息
 * @author weiqingding
 *
 */
public class LoginUser implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String customerId;
	
	private String loginNo;
	
	private String loginPhone;
	
	private String[] depts;
	
	private String loginEmail;
	
	private String[] roles;
	
	private String name;
	
	private String[] firms;
	
	public String getCustomerId() {
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public String getLoginNo() {
		return loginNo;
	}

	public void setLoginNo(String loginNo) {
		this.loginNo = loginNo;
	}

	public String getLoginPhone() {
		return loginPhone;
	}

	public void setLoginPhone(String loginPhone) {
		this.loginPhone = loginPhone;
	}

	public String getLoginEmail() {
		return loginEmail;
	}

	public void setLoginEmail(String loginEmail) {
		this.loginEmail = loginEmail;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String[] getDepts() {
		return depts;
	}

	public void setDepts(String[] depts) {
		this.depts = depts;
	}

	public String[] getRoles() {
		return roles;
	}

	public void setRoles(String[] roles) {
		this.roles = roles;
	}

	public String[] getFirms() {
		return firms;
	}

	public void setFirms(String[] firms) {
		this.firms = firms;
	}
	
}
