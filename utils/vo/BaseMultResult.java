package com.xgd.boss.core.vo;

import java.util.List;

import com.xgd.boss.core.utils.StringUtil;

/**
 * @author chenkai
 * 多个返回结果
 * @param <T>
 */
public class BaseMultResult<T> {
	
	protected String rtnMsg;
	protected String rtnCode;
	protected String datasource;
	protected List<T> data;
	protected String totalPage;//总页数
	protected String pageSize; //每页数据数量
	protected String pageIndex; //请求第几页数据
	protected String totalCount;
	
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
	public String getDatasource() {
		return datasource;
	}
	public void setDatasource(String datasource) {
		this.datasource = datasource;
	}
	public List<T> getData() {
		return data;
	}
	public void setData(List<T> data) {
		this.data = data;
	}
	public String getTotalPage() {
		return totalPage;
	}
	public void setTotalPage(String totalPage) {
		this.totalPage = totalPage;
	}
	public String getPageSize() {
		return pageSize;
	}
	public void setPageSize(String pageSize) {
		this.pageSize = pageSize;
	}
	public String getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(String pageIndex) {
		this.pageIndex = pageIndex;
	}
	public boolean isSuccess(){
		if(StringUtil.equalsIgnoreCase("0", rtnCode)){
			return true;
		}else{
			return false;
		}
	}
	
	public String getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(String totalCount) {
		this.totalCount = totalCount;
	}
	public void setSuccess(){
		this.rtnCode = "0";
	}

}
