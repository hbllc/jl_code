package com.xgd.boss.core.vo;


import java.io.Serializable;

/**
 * 
 * @author chenkai 
 */
public class IceTradeflowVo implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1463212038803875672L;

	//private static final Logger logger = LogFactory.getLog(IceTradeflowVo.class);
	
	/**
	 * 交易时间
	 */
	private String startflTradeTime;
	private String endflTradeTime;
	/**
	 * 交易类型
	 */
	private String flTradeType;
	/**
	 * 交易状态
	 */
	private String ltlSttus;
	
	/**
	 * 终端号
	 */
	private String flTermNo;
	
	/**
	 * 结算状态
	 */
	private String settle_status;
	
	/**
	 * 商户号
	 */
	private String flMerchNo;
	
	/**
	 * 流水号
	 */
	private String flVouchNo;
	
	/**
	 * 商户名称
	 */
	private String merchantName;
	
	/**
	 * 直属代理
	 */
	private String[] AGTACCID;
	

	public String getStartflTradeTime() {
		return startflTradeTime;
	}

	public void setStartflTradeTime(String startflTradeTime) {
		this.startflTradeTime = startflTradeTime;
	}

	public String getEndflTradeTime() {
		return endflTradeTime;
	}

	public void setEndflTradeTime(String endflTradeTime) {
		this.endflTradeTime = endflTradeTime;
	}

	public String getFlTradeType() {
		return flTradeType;
	}

	public void setFlTradeType(String flTradeType) {
		this.flTradeType = flTradeType;
	}

	public String getLtlSttus() {
		return ltlSttus;
	}

	public void setLtlSttus(String ltlSttus) {
		this.ltlSttus = ltlSttus;
	}

	public String getFlTermNo() {
		return flTermNo;
	}

	public void setFlTermNo(String flTermNo) {
		this.flTermNo = flTermNo;
	}

	public String getFlMerchNo() {
		return flMerchNo;
	}

	public void setFlMerchNo(String flMerchNo) {
		this.flMerchNo = flMerchNo;
	}

	public String getFlVouchNo() {
		return flVouchNo;
	}

	public void setFlVouchNo(String flVouchNo) {
		this.flVouchNo = flVouchNo;
	}

	public String getMerchantName() {
		return merchantName;
	}

	public void setMerchantName(String merchantName) {
		this.merchantName = merchantName;
	}

	public String getSettle_status() {
		return settle_status;
	}

	public void setSettle_status(String settle_status) {
		this.settle_status = settle_status;
	}

	public String[] getAGTACCID() {
		return AGTACCID;
	}

	public void setAGTACCID(String[] aGTACCID) {
		AGTACCID = aGTACCID;
	}
	
}
