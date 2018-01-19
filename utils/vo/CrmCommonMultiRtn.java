package com.xgd.boss.core.vo;

import java.io.Serializable;
import java.util.Collections;
import java.util.List;

/**
 * 请求crm通用返回结构实体-返回结果为集合
 */
public class CrmCommonMultiRtn<T> extends CrmCommonRtn implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 页码 */
    private Integer pageIndex;

    /** 页容量 */
    private Integer pageSize;

    /** 数据重量 */
    private Integer totalCount;

    /**
     * 返回结果
     */
    private List<T> data = Collections.emptyList();

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public Integer getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(Integer pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CrmCommonMultiRtn{");
        sb.append("data=").append(data);
        sb.append(", super=").append(super.toString());
        sb.append('}');
        return sb.toString();
    }
}
