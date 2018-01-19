package com.xgd.boss.core.thrift.resp;

import java.util.List;

/**
 * Thrift调用多结果返回实体,兼容row
 *
 * @author chengshaojin
 * @since 2017/12/26
 */
public class MultiThriftRespRow<T> extends CommonThriftResp {

    /**
     * 查询总数
     */
    private String limit;

    /**
     * 偏移量
     */
    private String offset;

    /**
     * 数据总数
     */
    private String total;

    /**
     * 返回数据
     */
    private List<T> rows;

    public String getLimit() {
        return limit;
    }

    public void setLimit(String limit) {
        this.limit = limit;
    }

    public String getOffset() {
        return offset;
    }

    public void setOffset(String offset) {
        this.offset = offset;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }
}
