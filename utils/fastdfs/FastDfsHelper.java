package com.core.fastdfs;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.util.List;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * FastDfs辅助类
 * @author huangweiqi
 * 2015-2-5
 */
public class FastDfsHelper {
	
	private static final Logger logger = LogManager.getLogger(FastDfsHelper.class);

	@SuppressWarnings("rawtypes")
	public static boolean isListEmpty(List list) {
		return (list == null) || (list.size() <= 0);
	}

	@SuppressWarnings("rawtypes")
	public static boolean isMapEmpty(Map map) {
		return (map == null) || (map.size() <= 0);
	}

	public static String compressBeginEndTime(String beginTime, String endTime) {
		return beginTime + "~" + endTime;
	}

	/**
	 * MD5
	 * @param str
	 * @return
	 */
	public static String MD5(String str) {
		MessageDigest md5 = null;
		try {
			md5 = MessageDigest.getInstance("MD5");
		} catch (Exception e) {
			logger.catching(e);
			return "";
		}

		char[] charArray = str.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++) {
			byteArray[i] = (byte) charArray[i];
		}
		byte[] md5Bytes = md5.digest(byteArray);

		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++) {
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16) {
				hexValue.append("0");
			}
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();
	}

	/**
	 * 获取TimeStamp
	 * @return
	 */
	public static String getTimeStamp() {
		return "" + (System.currentTimeMillis() / 1000);
	}

	/**
	 * 复制文件
	 * @param sourceFile
	 * @param targetFile
	 * @throws IOException
	 */
	public static void copyFile(File sourceFile, File targetFile) throws IOException {
		BufferedInputStream inBuff = null;
		BufferedOutputStream outBuff = null;
		try {
			// 新建文件输入流并对它进行缓冲
			inBuff = new BufferedInputStream(new FileInputStream(sourceFile));

			// 新建文件输出流并对它进行缓冲
			outBuff = new BufferedOutputStream(new FileOutputStream(targetFile));

			// 缓冲数组
			byte[] b = new byte[1024 * 10];
			int len;
			while ((len = inBuff.read(b)) != -1) {
				outBuff.write(b, 0, len);
			}
			// 刷新此缓冲的输出流
			outBuff.flush();
		} finally {
			// 关闭流
			if (inBuff != null) {
				inBuff.close();
			}
			if (outBuff != null) {
				outBuff.close();
			}
		}
	}
	
}
