package com.xgd.boss.core.thrift.req;

import com.xgd.boss.core.page.Paging;

/**
 * RPC分页请求公共参数
 *
 * @author chengshaojin
 * @since 2017-11-29
 */
public class CommonThriftListReq extends CommonThriftReq implements Paging {

    /**
     *
     */
    private static final long serialVersionUID = 4255771987544234540L;

    /**
     * 每页大小
     */
    private String pageSize;

    /**
     * 页码
     */
    private String pageIndex;

    /**
     * 兼容另一种分页方式-偏移量
     */
    private String offset;

    /**
     * 兼容另一种分页方式-查询数量
     */
    private String limit;

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

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }
}
