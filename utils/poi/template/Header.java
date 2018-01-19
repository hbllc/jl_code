package com.xgd.boss.core.poi.template;

import java.util.HashMap;
import java.util.Map;

import com.xgd.boss.core.utils.CommonUtil;

public class Header {
	
	public Header(String key,Object...dvs) {
		this.key = key;
		if(dvs!=null){
			for(int i=0;i<dvs.length;i++){
				if(i+1<dvs.length){
					addDefaultValue((String)dvs[i], dvs[i+1]);
				}
				i++;
			}
		}
	}
	
	public Header(String key,Header parent,Object...dvs) {
		this.parent = parent;
		this.key = key;
		//获取所有父的默认值
		recycParentDefaultValues(defaultValues);
		if(dvs!=null){
			for(int i=0;i<dvs.length;i=i+2){
				if(i+1<dvs.length){
					addDefaultValue((String)dvs[i], dvs[i+1]);
				}
			}
		}
	}
	
	/**列默认值**/
	Map<String,Object> defaultValues = new HashMap<String, Object>(); 
	Header parent ;
	String key;
	String colname;
	String rowIndex;	
			
	public String getRowIndex() {
		return rowIndex;
	}

	public void setRowIndex(String rowIndex) {
		this.rowIndex = rowIndex;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	public String getExcelColKey(){
		return toExcelColKey(key);
	}

	public String getColname() {
		return colname;
	}

	public void setColname(String colname) {
		this.colname = colname;
	}

	public Map<String, Object> getDefaultValues() {
		return defaultValues;
	}
	public void setDefaultValues(Map<String, Object> defaultValues) {
		this.defaultValues = defaultValues;
	}
	public void addDefaultValue(String key,Object value){
		if(defaultValues==null){
			defaultValues = new HashMap<String, Object>();
		}
		defaultValues.put(key, value);
	}
	
	
	public Header getParent() {
		return parent;
	}
	public void setParent(Header parent) {
		this.parent = parent;
	}
	
	public void recycParentDefaultValues(Map<String,Object> collect){
		if(parent!=null){
			if(CommonUtil.isNotEmpty(parent.defaultValues)){
				if(collect==null){
					collect = new HashMap<String, Object>();
				}
				collect.putAll(parent.defaultValues);
			}
			parent.recycParentDefaultValues(collect);
		}
	}
	
	public static String toExcelColKey(String str){
		return str.replaceAll("\\d+", "");
	}
}
