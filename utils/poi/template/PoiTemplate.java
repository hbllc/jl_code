package com.xgd.boss.core.poi.template;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xgd.boss.core.utils.CommonUtil;


public class PoiTemplate {
	protected boolean isSignExcelLoc = true;
	public static String KEY_ONLYONE_ROW = "ROWONE";
	public static String KEY_COMMON_ROW = "ROWA";
	public static String[] IGNORE_SYMBOL = null;
	protected int dataRow = 1;	
	protected int headerRow = 3;
	protected char COL_START_CHAR = 'A';
	protected char COL_END_CHAR = 'Z';
	protected String[][] headers = null;
	protected String[][] parentHeaders = null;
	
	protected Map<String, Header> usedExcelColKey2Headers = new HashMap<String, Header>();
	protected Map<String, Header> headerMaps = new HashMap<String, Header>();
	
	public PoiTemplate() {
	}
	
	public void parse(){
		parseHeader(parentHeaders);
		usedExcelColKey2Headers = parseHeader(headers);
	}
	
	public Map<String,Header> parseHeader(String[][] hdArray){
		if(CommonUtil.isEmpty(hdArray)) return null;
		Map<String,Header> excelColKey2Headers = new HashMap<String, Header>();
		Header head = null;		
		int fixcol = 5;
		for(String[] ph : hdArray){
			String key = ph[0];
			String parentKey = ph[1];			
			String colname = ph[2];
			String rowIndex = ph[3];
			String cellFormat = ph[4];
			List<Object> dvs = new ArrayList<Object>();
			for(int i=fixcol;i<15;i++){
				if(i<ph.length-1){
					dvs.add(ph[i]);
					dvs.add(ph[i+1]);
					i++;
				}else{
					break;
				}
			}
			if(CommonUtil.isNull(parentKey)){
				head = new Header(key,dvs.toArray());
			}else{
				head = new Header(key,headerMaps.get(parentKey), dvs.toArray());
			}
			if(!CommonUtil.isNull(colname)){
				head.setColname(colname);
			}
			if(!CommonUtil.isNull(rowIndex)){
				head.setRowIndex(rowIndex);
			}
			excelColKey2Headers.put(head.getExcelColKey(), head);
			headerMaps.put(key, head);
		}
		return excelColKey2Headers;
	}

	public int getDataRow() {
		return dataRow;
	}

	public void setDataRow(int dataRow) {
		this.dataRow = dataRow;
	}

	public int getHeaderRow() {
		return headerRow;
	}

	public void setHeaderRow(int headerRow) {
		this.headerRow = headerRow;
	}

	public Map<String, Header> getUsedExcelColKey2Headers() {
		return usedExcelColKey2Headers;
	}

	public void setUsedExcelColKey2Headers(
			Map<String, Header> usedExcelColKey2Headers) {
		this.usedExcelColKey2Headers = usedExcelColKey2Headers;
	}

	public Map<String, Header> getHeaderMaps() {
		return headerMaps;
	}

	public void setHeaderMaps(Map<String, Header> headerMaps) {
		this.headerMaps = headerMaps;
	}

	public boolean isSignExcelLoc() {
		return isSignExcelLoc;
	}

	public void setSignExcelLoc(boolean isSignExcelLoc) {
		this.isSignExcelLoc = isSignExcelLoc;
	}
	
	
}
