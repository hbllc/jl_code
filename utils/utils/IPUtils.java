package com.xgd.boss.core.utils;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * IP工具类
 */
public class IPUtils {
	/**
	 * 获取本机IP地址
	 */
	public static String getLocalIP() {
		String host = null;
		try {
			InetAddress addr = InetAddress.getLocalHost();
			host = addr.getHostAddress().toString();
		} catch (UnknownHostException e) {
			e.printStackTrace();
		}
		return host;
	}
}
