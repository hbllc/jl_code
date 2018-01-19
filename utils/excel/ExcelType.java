package com.xgd.boss.core.excel;

import org.apache.commons.lang3.StringUtils;


/**
 * 
* @Description: excel类型枚举
* @author tangchunmiao
* @date 2016年6月23日 上午9:15:53
 */
public enum ExcelType {
	//xlsx结尾,如2007版ecxel
	EXCEL_XLSX(".xlsx"),
	//xls结尾,如2003版ecxel
	EXCEL_XLS(".xls")
	;
	
	ExcelType(String key) {
		this.key = key;
	}
	
	private String key;
	
	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}
	
	//文件名后缀
	public static boolean endsKey(String key){
		if (StringUtils.isBlank(key)){
			return false;
		}
		for(ExcelType r: ExcelType.values()){
			if(key.endsWith(r.getKey())){
				return true;
			}
		}
		return false;
	}
}

