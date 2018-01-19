package com.xgd.boss.core.poi.sxssf;

import java.util.List;


/**
 * SXSSF导出数据回调
 * @author huangweiqi
 * 2015-2-2
 */
public interface SxssfExportDataCallback {
	
	/**
	 * 是否还有下一个sheet
	 * @param dataRowNum 数据行指针，从1开始
	 * @return
	 */
	public boolean hasNextSheet(int dataRowNum);
	
	/**
	 * 是否还有下一次Sheet数据查询
	 * @param rownum 行指针
	 * @param dataRowNum 数据行指针，从1开始
	 * @return
	 */
	public boolean hasNextSheetDataQuery(int rownum, int dataRowNum);

	/**
	 * 查询单个sheet的数据
	 * @param rownum 行指针
	 * @param dataRowNum 数据行指针，从1开始
	 * @return
	 */
	public List<List<String>> querySheetData(int rownum, int dataRowNum);
	
	/**
	 * 在主体数据之前追加数据
	 * @return
	 */
	public List<List<String>> queryExtPreSheetData();
	
	/**
	 * 在主体数据之后追加数据
	 * @return
	 */
	public List<List<String>> queryExtSuffixSheetData();
	
}
