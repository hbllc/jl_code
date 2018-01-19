package com.xgd.boss.core.codec;

import java.security.MessageDigest;

/**
 * 加密工具
 * @author chenkai
 *
 */
public class SHA2 {

	/**
	 * @param source
	 * @param sha2Type
	 * @return
	 * @throws Exception
	 */
	private static String getSHA2(byte[] source, String sha2Type)
			throws Exception {
		String strResult = null;
		MessageDigest messageDigest = MessageDigest.getInstance(sha2Type);

		messageDigest.update(source);

		byte[] byteBuffer = messageDigest.digest();

		StringBuffer strHexString = new StringBuffer();
		for (int i = 0; i < byteBuffer.length; i++) {
			String hex = Integer.toHexString(0xFF & byteBuffer[i]);
			if (hex.length() == 1) {
				strHexString.append('0');
			}
			strHexString.append(hex);
		}
		strResult = strHexString.toString();
		return strResult;
	}

	/**
	 * @param inputStr
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static final String getSHA512ofStr(String inputStr,
			String charsetName) throws Exception {
		return getSHA2(inputStr.getBytes(charsetName), "SHA-512");
	}

	/**
	 * @param inputStr
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static final String getSHA256ofStr(String inputStr,
			String charsetName) throws Exception {
		return getSHA2(inputStr.getBytes(charsetName), "SHA-256");
	}

	public static void main(String[] args) throws Exception {
		String toEncryptStr = "";
		System.out.println(getSHA512ofStr(toEncryptStr, "UTF-8"));
		System.out.println(getSHA256ofStr(toEncryptStr, "UTF-8"));
	}
}
