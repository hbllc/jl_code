package com.xgd.boss.core.vo;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class DataCenterParam {
	
	public DataCenterParam(){
	}
	
	public DataCenterParam(String opr){
		this.OPR = opr;
	}
	
	private String OPR;
	private LinkedList<Object> DATA = new LinkedList<Object>();
	
	public String getOPR() {
		return OPR;
	}
	public void setOPR(String oPR) {
		OPR = oPR;
	}
	public LinkedList<Object> getDATA() {
		return DATA;
	}
	public void setDATA(LinkedList<Object> dATA) {
		DATA = dATA;
	}
	
	public void add(Object obj){
		this.DATA.add(obj);
	}
	
	public void put(String key,String value){
		Map<String,String> temp = new HashMap<String,String>();
		temp.put(key, value);
		this.DATA.add(temp);
	}
	
	public void put(String key,List<String> values){
		Map<String,Object> temp = new HashMap<String,Object>();
		temp.put(key, values);
		this.DATA.add(temp);
	}
	
	public void putWithNegation(String key,String value){
		Map<String,Object> temp = new HashMap<String,Object>();
		Map<String,Object> negation = new HashMap<String,Object>();
		negation.put("value", value);
		negation.put("negation", true);
		temp.put(key, negation);
		this.DATA.add(temp);
	}
	
}
