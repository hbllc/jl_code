package com.xgd.boss.core.thrift.resp;

import com.jlpay.commons.command.CommandResponse;
import com.xgd.boss.core.mybatis.PageQuery;
import com.xgd.boss.core.page.PageResult;
import com.xgd.boss.core.utils.StringUtil;

import java.io.Serializable;
import java.util.List;

/**
 * Thrift调用多结果返回实体
 *
 * @param <T> 结果类型
 * @author chengshaojin
 */
public class MultiThriftResp<T> extends PageResult<T> implements Serializable, CommandResponse {
	/**
	 * 
	 */
	private static final long serialVersionUID = -2683328391485070969L;

	private List<T> result;
	private String ret_code;
	private String ret_msg;
	private String totalNum;
	
	public MultiThriftResp(){
		
	}
	
	
	public MultiThriftResp(List<T> data, long totalCount, int pageIndex,
						   int pageSize) {
		super(data, totalCount, pageIndex, pageSize);
	}


    public MultiThriftResp(List<T> data, PageQuery pageQuery) {
        super(data, pageQuery.getTotalCount(), pageQuery.getPageIndex(), pageQuery.getPageSize());
    }

    public void fillInPageResult(PageQuery pg) {
        this.setData(result);
        this.setTotalCount(StringUtil.toInt(totalNum));
        if (null != pg) {
            this.setPageIndex(pg.getPageIndex());
            this.setPageSize(pg.getPageSize());
        }
    }

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }

    public String getRet_code() {
        return ret_code;
    }

    public void setRet_code(String ret_code) {
        this.ret_code = ret_code;
    }

    public String getRet_msg() {
        return ret_msg;
    }

    public void setRet_msg(String ret_msg) {
        this.ret_msg = ret_msg;
    }

    public String getTotalNum() {
        return totalNum;
    }

    public void setTotalNum(String totalNum) {
        this.totalNum = totalNum;
    }

    public boolean isSuccess() {
        return CommonThriftResp.SUCCESS_RET_CODE.equals(this.getRet_code());
    }

    @Override
    public void setRetCode(String retCode) {
        this.ret_code = retCode;
    }

    @Override
    public void setRetMsg(String retMsg) {
        this.ret_msg = retMsg;
    }
}
