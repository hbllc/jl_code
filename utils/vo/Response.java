package com.xgd.boss.core.vo;

import java.io.Serializable;
/**
 * 页面响应对象
 * @author weiqingding
 *
 * @param <T>
 */
public class Response<T> implements Serializable {

	private static final long serialVersionUID = 1L;

	private boolean success;
	
	private T data;
	
	private int total;
	
	private int pageCount;
	
	private int pageNo;
	
	private int pageSize;
	
	private String msg;
	public Response(){
		
	}
	public Response(boolean success, T data, String msg) {
		super();
		this.success = success;
		this.data = data;
		this.msg = msg;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public int getPageCount() {
		return pageCount;
	}
	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}
	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	
	public Response<T> getErrorResponse(String errorMessage){
		 this.setSuccess(false);
		 this.setMsg(errorMessage);
		 return this;
	}
}
