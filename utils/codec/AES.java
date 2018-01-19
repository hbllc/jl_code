package com.xgd.boss.core.codec;

import java.io.IOException;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

/**
 * AES加密工具
 * @author chenkai
 *
 */
public class AES {
	
	/**
	 * @param byteContent
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] encrypt(byte[] byteContent, byte[] password)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password);
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(1, key);
		byte[] result = cipher.doFinal(byteContent);
		return result;
	}

	/**
	 * @param content
	 * @param password
	 * @return
	 * @throws Exception
	 */
	private static byte[] decrypt(byte[] content, byte[] password)
			throws Exception {
		KeyGenerator kgen = KeyGenerator.getInstance("AES");
		SecureRandom secureRandom = SecureRandom.getInstance("SHA1PRNG");
		secureRandom.setSeed(password);
		kgen.init(128, secureRandom);
		SecretKey secretKey = kgen.generateKey();
		byte[] enCodeFormat = secretKey.getEncoded();
		SecretKeySpec key = new SecretKeySpec(enCodeFormat, "AES");
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(2, key);
		byte[] result = cipher.doFinal(content);
		return result;
	}

	/**
	 * @param data
	 * @param key
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static final String decrypt(String data, String key,
			String charsetName) throws Exception {
		return new String(decrypt(hex2byte(data), key.getBytes(charsetName)),
				charsetName);
	}

	/**
	 * @param data
	 * @param key
	 * @param charsetName
	 * @return
	 * @throws Exception
	 */
	public static final String encrypt(String data, String key,
			String charsetName) throws Exception {
		return byte2hex(encrypt(data.getBytes(charsetName),
				key.getBytes(charsetName)));
	}

	/**
	 * @param b
	 * @return
	 */
	private static String byte2hex(byte[] b) {
		return Base64.encodeBase64String(b);
	}

	/**
	 * @param hex
	 * @return
	 * @throws IOException
	 */
	private static byte[] hex2byte(String hex) throws IOException {
		return Base64.decodeBase64(hex);
	}

	public static void main(String[] args) throws Exception {
		String enStr = encrypt("fuck", "123", "utf-8");
		System.out.println("AES加密后字符串:" + enStr);

		String deStr = decrypt(enStr, "123", "utf-8");
		System.out.println("AES解密后字符串:" + deStr);

		enStr = encrypt("fuck123232", "123456", "utf-8");
		System.out.println("AES加密后字符串:" + enStr);

		deStr = decrypt(enStr, "123456", "utf-8");
		System.out.println("AES解密后字符串:" + deStr);
	}
}
