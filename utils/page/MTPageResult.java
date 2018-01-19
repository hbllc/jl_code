package com.xgd.boss.core.page;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import com.xgd.boss.core.mybatis.PageQuery;
import com.xgd.boss.core.utils.JsonUtil;

/**
 * 返回分页结果
 * @param <T>
 */
public class MTPageResult<T> extends PageResult<T> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 3753967808003326923L;

	/**
	 * 状态，默认200
	 */
	private String status = "200";
	
	private String message;
	
	/**
	 * 扩展属性
	 */
	private Map<String,String> extendParam;

	public MTPageResult() {
		super();
	}

	/**
	 * data构造函数
	 * @param data 数据集
     */
	public MTPageResult(List<T> data) {
		super(data);
	}

	public MTPageResult(int pageIndex, int pageSize) {
		super(pageIndex, pageSize);
		
	}
	
	public MTPageResult(List<T> data, long totalCount, int pageIndex,
			int pageSize) {
		super(data, totalCount, pageIndex, pageSize);
	}

	public MTPageResult(List<T> data, long totalCount, int pageIndex,
			int pageSize, String status, String message) {
		super(data, totalCount, pageIndex, pageSize);
		this.status = status;
		this.message = message;
		this.extendParam = extendParam;
	}
	
	public MTPageResult(List<T> data, PageQuery pageQuery){
		super(data, pageQuery.getTotalCount(), pageQuery.getPageIndex(), pageQuery.getPageSize());
	}
	
	public MTPageResult(PageResult<T> pageResult) {
		super(pageResult.getData(), pageResult.getTotalCount(), pageResult.getPageIndex(), pageResult.getPageSize());
		this.status = status;
		this.message = message;
		this.extendParam = extendParam;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public Map<String, String> getExtendParam() {
		return extendParam;
	}

	public void setExtendParam(Map<String, String> extendParam) {
		this.extendParam = extendParam;
	}

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
}
