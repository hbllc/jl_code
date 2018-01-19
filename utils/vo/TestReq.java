package com.xgd.boss.core.vo;

import java.util.Arrays;

public class TestReq {
	public static void main(String[] args) {
		DataCenterReq req = new DataCenterReq();
		
		req.setTableName("XDATA.T_ALL_FIELD_TRADE_BILL");
		
		req.addSort("F_L_TRADE_TIME", "desc");
		req.addSort("F_PK", "desc");
		
		DataCenterParam reqParam = new DataCenterParam("AND");
		reqParam.put("F_A_ACCOUNT_ID", "201201703241356477");
		
		reqParam.put("F_MERCH_ACCID", Arrays.asList(new String[]{"10004669","10004671","10004716"}));
		
		//消费类型
		DataCenterParam tradeTypes = new DataCenterParam("OR");
		//普通消费
		DataCenterParam tradeTypeCommon = new DataCenterParam("OR");
		tradeTypeCommon.put("F_L_TRADE_TYPE", "0000000001");
		tradeTypeCommon.put("F_L_TRADE_TYPE", "01");
		//alipay
		DataCenterParam alipay = new DataCenterParam("OR");
		DataCenterParam alipay1 = new DataCenterParam("AND");
		alipay1.put("F_L_TRADE_TYPE", "01");
		alipay1.put("F100", "alipay");
		DataCenterParam alipay2 = new DataCenterParam("AND");
		alipay2.put("F_L_TRADE_TYPE", "3100000002");
		alipay.add(alipay1);
		alipay.add(alipay2);
		
		tradeTypes.add(tradeTypeCommon);
		tradeTypes.add(alipay);
		
		reqParam.add(tradeTypes);
		
		reqParam.put("F_C_SETT_STATUS", "1");
		reqParam.putWithNegation("F_L_CARD_TYPE", "3");
		
		req.setParamsMap(reqParam);
		System.out.print(req.toString());
	}
}
