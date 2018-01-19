package com.xgd.boss.core.vo;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据中心基类
 * @author chenkai
 *
 */
public abstract class BaseDataCenter {
	
	private String tableName;
	private String start;
	private String rows;
	private String reqParser;
	private String respParser;
	private boolean useCache;
	private String service_id;
	private String stats;
	private String statsFields;
	private Map<String,String> sort;
	
	/**
	 * 主体
	 */
	private DataCenterParam paramsMap;
	
	public BaseDataCenter(){
		this.start = "0";
		this.rows = "10";
		this.reqParser = "commonReqParser";
		this.respParser = "jsonRespParser";
		this.useCache = false;
		this.service_id = "xdataquery";
		this.stats = "true";
		this.statsFields = "F_L_TRADE_AMT,F_A_CR_TRADE_AMT,F_A_DR_TRADE_AMT";
		sort = new HashMap<String,String>();
	}
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
	public String getStart() {
		return start;
	}
	public void setStart(String start) {
		this.start = start;
	}
	public String getRows() {
		return rows;
	}
	public void setRows(String rows) {
		this.rows = rows;
	}
	public String getReqParser() {
		return reqParser;
	}
	public void setReqParser(String reqParser) {
		this.reqParser = reqParser;
	}
	public String getRespParser() {
		return respParser;
	}
	public void setRespParser(String respParser) {
		this.respParser = respParser;
	}
	public boolean isUseCache() {
		return useCache;
	}
	public void setUseCache(boolean useCache) {
		this.useCache = useCache;
	}
	public String getService_id() {
		return service_id;
	}
	public void setService_id(String service_id) {
		this.service_id = service_id;
	}
	public String getStats() {
		return stats;
	}
	public void setStats(String stats) {
		this.stats = stats;
	}
	public String getStatsFields() {
		return statsFields;
	}
	public void setStatsFields(String statsFields) {
		this.statsFields = statsFields;
	}
	public Map<String, String> getSort() {
		return sort;
	}
	public void setSort(Map<String, String> sort) {
		this.sort = sort;
	}
	
	public void addSort(String key,String sortType){
		sort.put(key, sortType);
	}

	public DataCenterParam getParamsMap() {
		return paramsMap;
	}

	public void setParamsMap(DataCenterParam paramsMap) {
		this.paramsMap = paramsMap;
	}
	
}
