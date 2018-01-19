package com.xgd.boss.core.poi.sxssf;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * SXSSF导出数据可选参数
 * @author huangweiqi
 * 2015-2-3
 */
public class SxssfExportDataOption implements Serializable {

	private static final long serialVersionUID = -7424372858509270656L;

	/**
	 * 每次抓取数据的数量
	 */
	private int fetchSize = 1000;
	/**
	 * 第一个sheet的名称，默认是sheet1
	 */
	private String firstSheetName = "sheet1";
	
	/**
	 * 是否具有样式，默认：没有
	 */
	private Boolean hasStyle = false;
	
	/**
	 * 是否让单元格的内容始终保持可见
	 */
	private Boolean styleIsWrapText = false;
	
	/**
	 * 列转换为数字
	 */
	private List<Integer> toNumColum = new ArrayList<Integer>();
	
	/**
	 * 数字转为金额模式，且为千分位样式展示
	 */
	private List<Integer> micrometerAmountColumn = new ArrayList<Integer>();
	
	/**
	 * 多个sheet时，不同表头list
	 */
	private Map<Integer,List<String>> headerMap;
	
	public Map<Integer, List<String>> getHeaderMap() {
		return headerMap;
	}
	public void setHeaderMap(Map<Integer, List<String>> headerMap) {
		this.headerMap = headerMap;
	}
	
	public List<Integer> getMicrometerAmountColumn() {
		return micrometerAmountColumn;
	}
	/**
	 * 数字转为金额模式，且为千分位样式展示
	 */
	public void setMicrometerAmountColumn(List<Integer> micrometerAmountColumn) {
		this.micrometerAmountColumn = micrometerAmountColumn;
	}

	public List<Integer> getToNumColum() {
		return toNumColum;
	}

	public void setToNumColum(List<Integer> toNumColum) {
		this.toNumColum = toNumColum;
	}
	
	public int getFetchSize() {
		return fetchSize;
	}

	public void setFetchSize(int fetchSize) {
		this.fetchSize = fetchSize;
	}

	public String getFirstSheetName() {
		return firstSheetName;
	}

	public void setFirstSheetName(String firstSheetName) {
		this.firstSheetName = firstSheetName;
	}

	public Boolean getHasStyle() {
		return hasStyle;
	}

	public void setHasStyle(Boolean hasStyle) {
		this.hasStyle = hasStyle;
	}

	public Boolean getStyleIsWrapText() {
		return styleIsWrapText;
	}

	public void setStyleIsWrapText(Boolean styleIsWrapText) {
		this.styleIsWrapText = styleIsWrapText;
	}
	
}
