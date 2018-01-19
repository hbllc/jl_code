package com.xgd.boss.core.vo;

import java.util.List;
/**
 * 
 * @author chenkai 
 */
public class RiskdataQueryResp<T> {
	
	private List<String> column ;

	/**
	 * 结果集
	 */
	private List<T> data ;

	/**
	 * 返回行数
	 */
	private int returnRows;

	/**
	 * 请求响应时间
	 */
	private String time;
	
	/**
	 * 总条数
	 */
	private int totalRows;
	

	public int getTotalRows() {
		return totalRows;
	}
	public void setTotalRows(int totalRows) {
		this.totalRows = totalRows;
	}
	public void setColumn(List<String> column){
	this.column = column;
	}
	public List<String> getColumn(){
	return this.column;
	}
	public void setData(List<T> data){
	this.data = data;
	}
	public List<T> getData(){
	return this.data;
	}
	public void setReturnRows(int returnRows){
	this.returnRows = returnRows;
	}
	public int getReturnRows(){
	return this.returnRows;
	}
	public void setTime(String time){
	this.time = time;
	}
	public String getTime(){
	return this.time;
	}

}
