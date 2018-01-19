package com.xgd.boss.core.utils;

import java.util.UUID;

/**
 * UUID生成工具类
 * @author Neo
 *
 */
public class UUIDUtil {

	/**
	 * 获取UUID
	 * @return
	 */
	public static String getUUID() {
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	public static void main(String[] args) {
		for (int i = 0; i < 20; i++) {
			System.out.println(getUUID());
		}
	}
	
}
