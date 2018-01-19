package com.xgd.boss.core.utils;

import org.springframework.util.DigestUtils;

public class Md5Util {

	public static String encode(String password) {
		if (null == password ){
			return password;
		}
		return DigestUtils.md5DigestAsHex(password.getBytes());
	}
}