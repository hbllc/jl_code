package com.xgd.boss.core.thrift.resp;

import com.jlpay.commons.command.CommandResponse;

import java.io.Serializable;

/**
 * 一般Thrift服务返回结构
 *
 * @author chengshaojin
 * @since 2017/12/25
 */
public class CommonThriftResp implements Serializable, CommandResponse {

    /**
     * 成功返回码
     */
    public static final String SUCCESS_RET_CODE = "00";

    /**
     * 返回码
     */
    private String ret_code;
    /**
     * 返回消息
     */
    private String ret_msg;

    @Override
    public void setRetCode(String s) {
        this.ret_code = s;
    }

    @Override
    public void setRetMsg(String s) {
        this.ret_msg = s;
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

    public boolean isSuccess() {
        return SUCCESS_RET_CODE.equals(this.ret_code);
    }

    @Override
    public String toString() {
        return "CommonThriftResp{" + "ret_code='" + ret_code + '\'' +
                ", ret_msg='" + ret_msg + '\'' +
                '}';
    }
}
