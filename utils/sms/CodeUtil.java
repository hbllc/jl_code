package com.xgd.boss.core.sms;

import java.util.Random;

public class CodeUtil {
	static char[] codeSequence = { 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
			'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W',
			'X', 'Y', 'Z', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };
	
	/**
	 * generate random code 
	 * @param type  :sring,number
	 * @param length:string size
	 * @return
	 */
	public static String getRandomCode(CodeType type,int length){
		Random random = new Random(); 
		StringBuffer randomCode = new StringBuffer(); 
		int red = 0, green = 0, blue = 0; 
		//随机产生codeCount数字的验证码。
		for (int i = 0; i < length; i++) {
			//得到随机产生的验证码数字。
			String strRand = "0";
			if(type==CodeType.NUMBER){
				int ii = random.nextInt(10);
				strRand = String.valueOf(codeSequence[ii+26]);
			}else if(type==CodeType.STRING){
				strRand = String.valueOf(codeSequence[random.nextInt(36)]);
			}
			randomCode.append(strRand); 
		}
		return randomCode.toString();
	}
	
	public static String fillFrontZero(String value,int length){
		if(value==null)
			value="";
		while(value.length()<length)
			value = ("0"+value);
		return value;
	}
	public static void main(String[] args) {
		System.out.println(CodeUtil.getRandomCode(CodeType.STRING, 8));
		System.out.println(CodeUtil.getRandomCode(CodeType.NUMBER, 8));
		System.out.println(CodeUtil.getRandomCode(CodeType.NUMBER, 8));
		System.out.println(CodeUtil.getRandomCode(CodeType.STRING, 8));
		System.out.println(fillFrontZero("123",1));
	}
}
