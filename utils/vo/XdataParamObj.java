package com.xgd.boss.core.vo;

import java.util.ArrayList;
import java.util.List;
/**
 * 大数据查询参数对象
 * @author yanfuhua
 *
 */
public class XdataParamObj {
	/**
	 * 操作
	 */
	public static String OPR_AND = "AND";
	public static String OPR_OR = "OR";
	
	String OPR;
	List<Object> DATA;
	public String getOPR() {
		return OPR;
	}
	public void setOPR(String oPR) {
		OPR = oPR;
	}
	public List<Object> getDATA() {
		return DATA;
	}
	public void setDATA(List<Object> dATA) {
		DATA = dATA;
	}
	public void addDATA(Object obj){
		if(DATA==null){
			DATA = new ArrayList<>();
		}
		DATA.add(obj);
	}
	
}
