package com.xgd.boss.core.vo;

import com.xgd.boss.core.utils.JsonUtil;

/**
 * 数据中心请求
 * @author chenkai
 *
 */
public class DataCenterReq extends BaseDataCenter {

	@Override
	public String toString() {
		return JsonUtil.toJson(this);
	}
	
}
