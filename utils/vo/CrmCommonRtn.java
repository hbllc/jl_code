package com.xgd.boss.core.vo;

import java.io.Serializable;

/**
 * 请求crm通用返回结构实体
 */
public class CrmCommonRtn implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/** 成功返回码-0 */
    public static final String SUCCESS_RETURN_CODE_0 = "0";

    /** 成功返回码-0000 */
    public static final String SUCCESS_RETURN_CODE_0000 = "0000";

    /**
     * 返回
     */
    private String rtn;

    /**
     * 返回码
     */
    private String rtnCode;

    /**
     * 返回信息
     */
    private String rtnMsg;

    /**
     * 返回结果数目
     */
    private Integer total;

    public String getRtn() {
        return rtn;
    }

    public void setRtn(String rtn) {
        this.rtn = rtn;
    }

    public String getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(String rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("CrmCommonRtn{");
        sb.append("rtn='").append(rtn).append('\'');
        sb.append(", rtnCode='").append(rtnCode).append('\'');
        sb.append(", rtnMsg='").append(rtnMsg).append('\'');
        sb.append(", total=").append(total);
        sb.append('}');
        return sb.toString();
    }
}
