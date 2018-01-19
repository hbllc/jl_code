package com.xgd.boss.core.thrift.resp;

/**
 * Thrift调用单个结果返回实体
 *
 * @param <T> 结果类型
 * @author chengshaojin
 * @since 2017-11-27
 */
public class SingleThriftResp<T> extends CommonThriftResp {

    /**
     *
     */
    private static final long serialVersionUID = -7207984028819096117L;

    private T data;


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "SingleThriftResp{" + "data=" + data + "super=" + super.toString() + '}';
    }
}
