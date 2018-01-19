package com.xgd.boss.core.poi.sxssf;

import java.io.Serializable;

import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

/**
 * SXSSF数据导出上下文
 * @author huangweiqi
 * 2015-2-3
 */
public class SxssfExportDataContext implements Serializable {

	private static final long serialVersionUID = 2276394361683297376L;
	
	/**
	 * 可选参数
	 */
	private SxssfExportDataOption option;
	/**
	 * excel对象
	 */
	private SXSSFWorkbook workbook;
	/**
	 * 总记录数
	 */
	private Long totalCount;
	/**
	 * 当前sheet的名称
	 */
	private String currentSheetName;
	
	/**
	 * 获取当前活动的sheet
	 * @return
	 */
	public SXSSFSheet getActiveSheet() {
		return (SXSSFSheet) workbook.getSheetAt(workbook.getActiveSheetIndex());
	}
	
	/**
	 * 获取当前活动sheet的索引号
	 * @return
	 */
	public int getActiveSheetIndex() {
		return workbook.getActiveSheetIndex();
	}
	
	public SxssfExportDataOption getOption() {
		return option;
	}
	public void setOption(SxssfExportDataOption option) {
		this.option = option;
	}
	public SXSSFWorkbook getWorkbook() {
		return workbook;
	}
	public void setWorkbook(SXSSFWorkbook workbook) {
		this.workbook = workbook;
	}
	
	public Long getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(Long totalCount) {
		this.totalCount = totalCount;
	}

	public String getCurrentSheetName() {
		return currentSheetName;
	}
	public void setCurrentSheetName(String currentSheetName) {
		this.currentSheetName = currentSheetName;
	}
	
}
