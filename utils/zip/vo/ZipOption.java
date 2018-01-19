package com.xgd.boss.core.zip.vo;

import java.io.Serializable;

/**
 * 打Zip包选项
 * @author huangweiqi
 * 2015-2-13
 */
public class ZipOption implements Serializable {

	private static final long serialVersionUID = -1109171251299086839L;

	/**
	 * 在压缩包中是否生成顶级目录
	 */
	private Boolean isTopPath = false;

	public Boolean getIsTopPath() {
		return isTopPath;
	}

	public void setIsTopPath(Boolean isTopPath) {
		this.isTopPath = isTopPath;
	}
	
	
	
}
