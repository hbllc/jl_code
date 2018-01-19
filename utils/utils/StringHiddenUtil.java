package com.xgd.boss.core.utils;


import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @author liangxiong
 * 2015-03-06
 * 字符串隐藏字符格式化工具类
 */
public class StringHiddenUtil {
	
	/**
	 * 银行卡隐藏
	 * @param bankNos
	 * @return
	 */
	public static String formatBankAccount(String bankAccount){
		String temp = "";
		if(StringUtil.isNotEmpty(bankAccount)){
			temp = bankAccount.trim();
		}
		if(StringUtil.isEmpty(bankAccount) || bankAccount.trim().length()<8){
			return bankAccount;
		}
		else if(StringUtil.isEmpty(bankAccount) || bankAccount.trim().length()<10){
			temp = StringUtils.substring(temp, 0, 4) + "******" + StringUtils.substring(temp, temp.length()-4, temp.length());
		}
		else{
			temp = StringUtils.substring(temp, 0, 6) + "******" + StringUtils.substring(temp, temp.length()-4, temp.length());
		}
		return temp;
	}
	
	/**
	 * 手机号码隐藏
	 * @param mobilePhone
	 * @return
	 */
	public static String formatMobilePhone(String mobilePhone){
		if(StringUtil.isEmpty(mobilePhone) || mobilePhone.length()!=11){
			return mobilePhone;
		}
		String temp = mobilePhone;
		temp = temp.substring(0,3) + "****" + temp.substring(7,11);
		
		return temp;
	}
	
	/**
	 * 邮件地址隐藏
	 * @param email
	 * @return
	 */
	public static String formatEmail(String email){
		if(StringUtil.isEmpty(email) || email.indexOf("@")==-1){
			return email;
		}
		String temp = email;
		String tempArr[] = temp.split("@");
		if(tempArr[0].length()>2){
			temp = temp.substring(0, tempArr[0].length()/2);
			for(int i = 0 ; i < tempArr[0].length()/2 ; i ++){
				temp += "*";
			}
			temp += "@" + tempArr[1];
		}else{
			temp = tempArr[0] + "**" +  "@" + tempArr[1];
		}
		return temp;
	}
	
	/**
	 * 营业执照号隐藏
	 * @param email
	 * @return
	 */
	public static String formatLicenseCode(String licenseCode){
		if(StringUtil.isEmpty(licenseCode)){
			return licenseCode;
		}
		String temp = licenseCode.trim();
		if(temp.length()<4){
			return temp;
		}
		else if(temp.length()<7){
			temp = StringUtils.substring(temp, 0, 4) + "***";
		}else{
			temp = StringUtils.substring(temp, 0, 4) + "***" + StringUtils.substring(temp, temp.length()-4, temp.length());
		}
		return temp;
	}
	
	public static String formatName(String name){
		if(StringUtil.isEmpty(name)){
			return name;
		}
		if(name.length()>2){
			return name.substring(0,1)+"*"+name.substring(name.length()-1,name.length());
			//return name.replace(name.substring(1,name.length()-1), "*");
		}else{
			return name.replace(name.substring(1), "*");
		}
	}
	
	public static void main(String[] args) {
		StringHiddenUtil shiddenUtil = new StringHiddenUtil();
		System.out.println(shiddenUtil.formatBankAccount("123456780"));
		System.out.println(shiddenUtil.formatBankAccount("6222000000123400"));
		
		System.out.println(shiddenUtil.formatMobilePhone("13878051997"));
		
		System.out.println(shiddenUtil.formatEmail("yfdsfdsfsh@qq.com"));
	}
	
}
