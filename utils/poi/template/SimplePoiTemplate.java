package com.xgd.boss.core.poi.template;

import java.util.List;

public class SimplePoiTemplate extends PoiTemplate {
	int startNum = 65;//A
	int maxNum = 52;
	protected String[][] simpleHeaders = new String[maxNum][5];//最大到AZ列
	public SimplePoiTemplate() {
		dataRow = 2;
		headerRow = 1;		
		parentHeaders = null;	
		isSignExcelLoc = false;
		headers = initHeader(null);
	}
	
	public SimplePoiTemplate(List<String> selfHeaders){
		this();
		headers = initHeader(selfHeaders);
	}
	
	private String[][] initHeader(List<String> selfHeaders){
		for(int i=startNum;i<startNum+maxNum;i++){
			String colKey = "";
			if(i-startNum>=26){
				colKey = ((char)startNum)+""+((char)(i-26))+"1";
			}else{
				colKey = ((char)i)+"1";
			}
			int headerCol =i-startNum; 
			String headerColKey = ""+headerCol;
			if(selfHeaders!=null && !selfHeaders.isEmpty() && headerCol<selfHeaders.size()){
				headerColKey = selfHeaders.get(headerCol);
			}
			simpleHeaders[headerCol] = new String[]{colKey,""  , headerColKey ,"ROW1",   ""};
		}
		return simpleHeaders;
	}
}
