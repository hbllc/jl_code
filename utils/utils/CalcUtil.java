package com.xgd.boss.core.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 计算工具类
 * @author yanfuhua
 * 2014-11-13
 */
public class CalcUtil {
	/**
	 * 小数点后2位
	 */
	public static final String FORMAT_TWO = "0.00";

	/**
	 * 默认精度
	 */
	public static final int DEFAULT_SCALE = 6;
	
	/**
	 * 不允许实例化
	 */
	private CalcUtil() {}
	
	/**
	 * 四舍五入，以默认精度处理
	 * @param number 待处理的数字
	 * @return
	 */
	public static double roundUp(double number) {
		return roundUp(number, DEFAULT_SCALE);
	}
	
	/**
	 * 四舍五入
	 * @param number 待处理的数字
	 * @param scale 精度，精确到小数点后sacle位
	 * @return
	 */
	public static double roundUp(double number, int scale) {
		return new BigDecimal(number + "")
				.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 四舍五入，以默认精度处理
	 * @param number 待处理的数字
	 * @return
	 */
	public static double roundUp(BigDecimal number) {
		return roundUp(number, DEFAULT_SCALE);
	}
	
	/**
	 * 四舍五入
	 * @param number 待处理的数字
	 * @param scale 精度，精确到小数点后sacle位
	 * @return
	 */
	public static double roundUp(BigDecimal number, int scale) {
		return number.setScale(scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	public static double roundUp(String strMoney, int scale) {
		BigDecimal doubleMoney = new BigDecimal(strMoney);
		return roundUp(doubleMoney, scale);
	}
	
	/**
	 * 四舍五入
	 * @param number 待处理的数字
	 * @param scale 精度，精确到小数点后sacle位
	 * @return BigDecimal
	 */
	public static BigDecimal roundUpWithoutSN(BigDecimal number, int scale) {
		return number.setScale(scale, BigDecimal.ROUND_HALF_UP);
	}
	
	/**
	 * 相加,num1 + num2
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @return
	 */
	public static double add(double num1, double num2) {
		return add(num1, num2, DEFAULT_SCALE);
	}
	
	/**
	 * 相加,num1 + num2
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @param scale 精度
	 * @return
	 */
	public static double add(double num1, double num2, int scale) {
		BigDecimal number1 = new BigDecimal(num1 + "");
		BigDecimal number2 = new BigDecimal(num2 + "");
		return roundUp(number1.add(number2), scale);
	}
	
	/**
	 * 数组求和
	 * @param nums 数字数组
	 * @return
	 */
	public static double add(double[] nums) {
		return add(nums, DEFAULT_SCALE);
	}
	
	/**
	 * 数组求和
	 * @param nums 数字数组
	 * @param scale 精度
	 * @return
	 */
	public static double add(double[] nums, int scale) {
		if (nums == null || nums.length == 0) {
			return 0.0;
		}
		double total = 0.0;
		for (int i = 0, len = nums.length; i < len; i++) {
			total = add(total, nums[i], scale);
		}
		return total;
	}
	
	/**
	 * 相减,num1 - num2
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @return
	 */
	public static double sub(double num1, double num2) {
		return sub(num1, num2, DEFAULT_SCALE);
	}
	
	/**
	 * 相减,num1 - num2
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @param sacle 精度
	 * @return
	 */
	public static double sub(double num1, double num2, int sacle) {
		BigDecimal number1 = new BigDecimal(num1 + "");
		BigDecimal number2 = new BigDecimal(num2 + "");
		return roundUp(number1.subtract(number2), sacle);
	}
	
	/**
	 * 相乘,num1 * num2
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @return
	 */
	public static double mul(double num1, double num2) {
		return mul(num1, num2, DEFAULT_SCALE);
	}
	
	/**
	 * 相乘,num1 * num2
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @param scale 精度
	 * @return
	 */
	public static double mul(double num1, double num2, int scale) {
		BigDecimal number1 = new BigDecimal(num1 + "");
		BigDecimal number2 = new BigDecimal(num2 + "");
		return roundUp(number1.multiply(number2), scale);
	}
	
	/**
	 * 数组连乘
	 * @param nums 数字数组
	 * @return
	 */
	public static double mul(double[] nums) {
		return mul(nums, DEFAULT_SCALE);
	}
	
	/**
	 * 数组连乘
	 * @param nums 数字数组
	 * @param scale 精度
	 * @return
	 */
	public static double mul(double[] nums, int scale) {		
		if (nums == null || nums.length == 0) {
			return 0.0;
		}
		double total = 1.0;
		for (int i = 0, len = nums.length; i < len; i++) {
			total = mul(total, nums[i], scale);
		}
		return total;
	}
	
	/**
	 * 相除,num1 / num2，保留三位小数
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @return
	 */
	public static double div(double num1, double num2) {
		return div(num1, num2, DEFAULT_SCALE);
	}
	
	/**
	 * 相除,num1 / num2，保留三位小数
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @param scale 精度
	 * @return
	 */
	public static double div(double num1, double num2, int scale) {
		BigDecimal number1 = new BigDecimal(num1 + "");
		BigDecimal number2 = new BigDecimal(num2 + "");
		return number1.divide(number2, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}
	
	/**
	 * 比较两个Double数字
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @return 相等则返回true
	 */
	public static boolean compareDouble(Double num1, Double num2) {
		return compareDouble(num1, num2, DEFAULT_SCALE);
	}
	
	/**
	 * 比较两个Double数字
	 * @param num1 第一个数字
	 * @param num2 第二个数字
	 * @param scale 精度
	 * @return 相等则返回true
	 */
	public static boolean compareDouble(Double num1, Double num2, int scale) {
		if (num1 == null && num2 == null) {
			return true;
		} else if (num1 != null && num2 == null) {
			return false;
		} else if (num1 == null && num2 != null) {
			return false;
		}
		
		return sub(num1, num2, scale) == 0;
	}
	
	public static double penny2Yuan(long pennyLong){
		return roundUp(new BigDecimal(pennyLong).divide(new BigDecimal(100)),2);
	}
	
	public static double penny2Yuan(double pennyMoney){
		return roundUp(new BigDecimal(pennyMoney).divide(new BigDecimal(100)),2);
	}
	
	public static double penny2Yuan(BigDecimal pennyMoney){
		return roundUp(pennyMoney.divide(new BigDecimal(100)),2);
	}
	
	public static String penny2YuanWithoutSN(BigDecimal pennyMoney){
		return roundUpWithoutSN(pennyMoney.divide(new BigDecimal(100)),2).toString();
	}
	
	public static Integer yuan2Penny(Double yuanMoney){
		if(yuanMoney==null) return null;
		return new Double(yuanMoney*100).intValue();
	}
	
	
	public static String format(String format,Object num){
		if(num==null) return null;
		DecimalFormat df = new DecimalFormat(format);
		return df.format(num);
	}
	
	public static Double format2Double(String format,Object num){
		if(num==null) return null;
		DecimalFormat df = new DecimalFormat(format);
		return Double.parseDouble(df.format(num));
	}
	
	public static void main(String[] args) {
		double num1 = 10.97;
		double num2 = 30.03;
		System.out.println(String.format("%s + %s = %s", num1, num2, add(num1, num2)));
		System.out.println(String.format("%s - %s = %s", num1, num2, sub(num1, num2)));
		System.out.println(String.format("%s * %s = %s", num1, num2, mul(num1, num2)));
		System.out.println(String.format("0.1 * 0.2 = %s", 0.1, 0.2, mul(0.1, 0.2)));
		System.out.println(String.format("%s / %s = %s", num1, num2, div(num1, num2)));
		
		double[] nums = new double[] {0.1, 0.2, 0.3, 0.4, 0.55};
		System.out.println(String.format("total:%s", add(nums)));
		System.out.println(String.format("mul total:%s", mul(nums)));
	}
}
