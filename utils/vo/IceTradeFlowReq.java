package com.xgd.boss.core.vo;


import java.io.Serializable;

/**
 * @author liukun：2016年6月16日 上午11:06:59
 * @Desc Solr + hbase ICE查询接口vo
 */
public class IceTradeFlowReq implements Cloneable, Serializable {
	/**
	 * 表名
	 */
	private String tableName;
	/**
	 * 起始行
	 */
	private int start;
	/**
	 * 查询行数
	 */
	private int rows;
	/**
	 * 请求转换器
	 */
	private String reqParser = "commonReqParser";
	/**
	 * 返回结果转换器
	 */
	private String respParser = "jsonRespParser";
	
	/**
	 * 交易流水查询条件
	 */
	private IceTradeflowVo paramsMap;

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public int getStart() {
		return start;
	}

	public void setStart(int start) {
		this.start = start;
	}

	public int getRows() {
		return rows;
	}

	public void setRows(int rows) {
		this.rows = rows;
	}

	public String getReqParser() {
		return reqParser;
	}

	public void setReqParser(String reqParser) {
		this.reqParser = reqParser;
	}

	public String getRespParser() {
		return respParser;
	}

	public void setRespParser(String respParser) {
		this.respParser = respParser;
	}

	public IceTradeflowVo getIceTradeflowVo() {
		return paramsMap;
	}

	public void setIceTradeflowVo(IceTradeflowVo iceTradeflowVo) {
		this.paramsMap = iceTradeflowVo;
	}
	
	
	
	

}

