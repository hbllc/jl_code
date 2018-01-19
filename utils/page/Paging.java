package com.xgd.boss.core.page;

/**
 * 请求实体-是否需要分页参数
 * @author chengshaojin
 * @since 2017-11-27
 */
public interface Paging {

	void setPageIndex(String pageIndex);
	
	void setPageSize(String pageSize);
}
