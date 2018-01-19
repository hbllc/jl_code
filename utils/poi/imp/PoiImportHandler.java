package com.xgd.boss.core.poi.imp;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.commons.logging.Log;

import com.xgd.boss.core.poi.POIExcelUtil;
import com.xgd.boss.core.poi.template.PoiTemplate;
import com.xgd.boss.core.utils.CommonUtil;
import com.xgd.boss.core.utils.JsonUtil;

public class PoiImportHandler<C extends PoiTemplate> {
	
	private static final Log logger = LogFactory.getLog(PoiImportHandler.class);
	
	private Class<C> templateClass;
	private PoiTemplate template;
	public PoiImportHandler(PoiTemplate template) {
		this.template = template;
	}
	
	public PoiImportHandler(Class templateClass) {
		this.templateClass = templateClass;
	}
	
	public void db2excel(String filepath){
		
	}
	
	public List<Map<String, Object>> excel2Map(String filepath){
		return excel2Map(filepath, false);
	}
	
	public List<Map<String, Object>> excel2Map(String filepath,boolean needContainHeader){
		return excel2Map(filepath, 0, needContainHeader);
	}
	
	public List<Map<String, Object>> excel2Map(String filepath,int sheetnum,boolean needContainHeader){
		return excel2Map(filepath, new int[]{sheetnum}, needContainHeader);
	}
	
	public List<Map<String, Object>> excel2Map(String filepath,int[] sheetnums,boolean needContainHeader){
		if(template==null){
			template = createTemplate();
		}
		template.parse();
		Workbook wb = POIExcelUtil.readExcel(filepath);
		List<Map<String, Object>> dataList = new ArrayList<Map<String,Object>>();
		for(int i=0;i<sheetnums.length;i++){
			int sheetnum = sheetnums[i];
			List<Map<String, Object>> evalList = POIExcelUtil.readCell(wb.getSheetAt(sheetnum),
					template,needContainHeader);
			if(evalList!=null){
				dataList.addAll(evalList);
			}
		}		
		return dataList;
	}
	
	public <T> List excel2Entity(String filepath,Class<T> t){
		List<Map<String,Object>> datamapList = excel2Map(filepath);
		if(CommonUtil.isEmpty(datamapList)){
			return null;
		}
		List<T> result = new ArrayList<T>();
		for(Map<String,Object> datamap :datamapList){
			result.add(JsonUtil.mapToObject(datamap, t));
		}
		return result;
	}
	
	public PoiTemplate createTemplate(){
		PoiTemplate template = null;
		try {
			template = templateClass.newInstance();
		} catch (InstantiationException | IllegalAccessException e) {
			logger.error(e);
		}
		return template;
	}

	public PoiTemplate getTemplate() {
		return template;
	}

	public void setTemplate(PoiTemplate template) {
		this.template = template;
	}
	
	
}
