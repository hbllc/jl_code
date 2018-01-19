package com.xgd.boss.core.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
/**
 * 日期操作类
 * @author chenqiguo
 *
 */
public class DateUtil {
	
	public static final String YYYY_MM_DD = "yyyy-MM-dd";
	public static final String YYYY_MM_DD_HH_MM_SS  = "yyyy-MM-dd HH:mm:ss";
	public static final String HH_MM_SS = "HH:mm:ss";
	public static final String TRADE_DATE = "MM-dd";
	public static final String TRADE_TIME = "HH:mm";
	public static final SimpleDateFormat FORMAT_YYYY_MM_DD = new SimpleDateFormat("yyyy-MM-dd");
	
	// 日期格式常量
    /**
     * 日期格式：yyyy-MM-dd
     */
    public static final String DATE_FORMAT_WITHOUT_TIME = "yyyy-MM-dd";

    /**
     * 日期格式：yyyy-M-d
     */
    public static final String DATE_FORMAT_WITHOUT_TIME_SINGLE = "yyyy-M-d";

    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss
     */
    public static final String DATE_FORMAT_WITH_TIME = "yyyy-MM-dd HH:mm:ss";
    
    /**
     * 日期格式：yyyy-MM-dd HH:mm:ss.S
     */
    public static final String DATE_FORMAT_WITH_TIME_SINGLE_MINI = "yyyy-MM-dd HH:mm:ss.S";

    /**
     * 日期格式：yyyy-M-d H:m:s
     */
    public static final String DATE_FORMAT_WITH_TIME_SINGLE = "yyyy-M-d H:m:s";
    
    /**
     * 日期格式：HH:mm:ss
     */
    public static final String DATE_FORMAT_ONLY_TIME = "HH:mm:ss";

    /**
     * 日期格式：yyyyMMddHHmmssSSS
     */
    public static final String DATE_FORMAT_NO = "yyyyMMddHHmmssSSS";

    /**
     * 日期格式：yyMMddHHmmssSSS
     */
    public static final String DATE_FORMAT_SAMPLE_NO = "yyMMddHHmmssSSS";

    /**
     * 日期格式：yyyyMMddHHmmss
     */
    public static final String DATE_FORMAT_SHORT_NO = "yyyyMMddHHmmss";
    
    /**
     * 日期格式：yyyy-MM
     */
    public static final String MONTH_FORMAT = "yyyy-MM";
    
    /**
     * 日期格式：yyyyMM
     */
    public static final String MONTH_FORMAT_NO = "yyyyMM";
    
    /**
     * 日期格式：yyyyMMdd
     */
    public static final String DAY_FORMAT_NO = "yyyyMMdd";
    
    /**
     * GMT格式:yyyy-MM-dd'T'HH:mm:ss.SSSZ
     */
    public static final String GMT_FORMAT_NO = "yyyy-MM-dd HH:mm:ss 'GMT'";
    
    /**
     * 日期格式：yyyy-MM-dd_HH:mm:ss
     */
    public static final String DATE_FORMAT_WITH_TIME_UNDERLINE = "yyyy-MM-dd_HH:mm:ss";
    
    
    /**周期类型，天*/
    public static final String PERIOD_TYPE_DAY = "d";
    /**周期类型，周*/
    public static final String PERIOD_TYPE_WEEK = "w";
    /**周期类型，月*/
    public static final String PERIOD_TYPE_MONTH = "m";
    /**周期类型，年*/
    public static final String PERIOD_TYPE_YEAR = "y";
    /**周期类型，所有*/
    public static final String PERIOD_TYPE_ALL = "a";
    
    /**周期索引，所有*/
    public static final String PERIOD_INDEX_ALL = "all";
    
    /**最开始的日期*/
    public static final String FINAL_START_TIME = "1970-01-01 00:00:00";
    
    /**最后日期*/
    public static final String FINAL_END_TIME = "9999-12-31 23:59:59";
    
    /**一季度*/
    public static final int QUARTER_FIRST = 0;
    /**二季度*/
    public static final int QUARTER_TWO = 1;
    /**三季度*/
    public static final int QUARTER_THREE = 2;
    /**四季度*/
    public static final int QUARTER_FOUR = 3;
    /**未知季度*/
    public static final int QUARTER_UNKNOW = -1;
    public static final int[][] QUARTER_ARRAY = {{0,1,2},{3,4,5},{6,7,8},{9,10,11}};
    
    public final static int DAY_TIME = 24*60*60*1000;
    public final static SimpleDateFormat DAY = new SimpleDateFormat("yyyy-MM-dd");

	public static Date getDateByStr(String str) throws ParseException{
		Date date;
		DateFormat fmt = new SimpleDateFormat(YYYY_MM_DD);
		date=fmt.parse(str);
		return date;
		
	}
	public static String getStatDate() {
		DateFormat fmt = new SimpleDateFormat(YYYY_MM_DD);
		
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.DAY_OF_YEAR, -1);
		
		String stat_day = fmt.format(cal.getTime());
		return stat_day;
	}
	
	public static String getNowDate() {
		DateFormat fmt = new SimpleDateFormat(YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		String stat_day = fmt.format(cal.getTime());
		return stat_day;
	}
	
	public static String getStatDate(Calendar calendar) {
		DateFormat fmt = new SimpleDateFormat(YYYY_MM_DD);
		
		String stat_day = fmt.format(calendar.getTime());
		return stat_day;
	}
	
	
	public static Calendar getCalendar(String dateStr, String pattern) {
		DateFormat fmt = new SimpleDateFormat(pattern);
		Date date = null;
		Calendar calendar = null;
		try {
			date = fmt.parse(dateStr);
			calendar = Calendar.getInstance();
			calendar.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return calendar;
	}
	
	/**
	 * long型时间转换成String格式
	 * @param dateTime 需要转换的时间
	 * @param pattern 需要转换时间的格式
	 * @return
	 */
	public static String getFormatDateString(long dateTime, String pattern) {
		DateFormat fmt = new SimpleDateFormat(pattern);
		Date date = new Date(dateTime);
		return fmt.format(date);
	}
	
	/**
	 * long型时间转换成String格式
	 * @param dateTime 需要转换的时间
	 *    默认转换成"yyyy-MM-dd HH:mm:ss"格式
	 * @return
	 */
	public static String getFormatDateString(long dateTime) {
		DateFormat fmt = new SimpleDateFormat(YYYY_MM_DD_HH_MM_SS);
		Date date = new Date(dateTime);
		return fmt.format(date);
	}
	
	/**
	 * 获取long型时间
	 * @param dateStr 需要转换的时间
	 * @param pattern 需要转换时间的格式
	 * @return
	 */
	public static long getDateLong(String dateStr, String pattern) {
		DateFormat fmt = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = fmt.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date.getTime();
	}
	
	/**
	 * YYYY_MM_DD_HH_MM_SS时间转换成long型时间
	 * @param dateStr 需要转换的时间
	 * @param pattern 需要转换时间的格式
	 * @return
	 */
	public static long getDateLong(String dateStr) {
		return getDateLong(dateStr, YYYY_MM_DD_HH_MM_SS);
	}
	
	public static long getHourMillis(int hour, int min, int seconds, int millis) {
		
		return hour*60*60*1000 + min*60*1000 + seconds*1000 + millis;
	}
	
	public static long getHourMillis(Calendar calendar) {
		int hour = calendar.get(Calendar.HOUR_OF_DAY);
		int min = calendar.get(Calendar.MINUTE);
		int seconds = calendar.get(Calendar.SECOND);
		int millis = calendar.get(Calendar.MILLISECOND);
		return getHourMillis(hour, min, seconds, millis);
	}
	
	/**
	 * 根据毫秒数获取日期时间HH-mm-ss
	 * 使用时请注意一天的最大毫秒数
	 * @param millis
	 * @return
	 */
	public static String getStr4HourByMil(long millis) {
		String bResult = "";
		int iRemainder = 0;
		int iHour  = (int)millis/(60*60*1000);
		iRemainder = (int)millis%(60*60*1000);
		int iMinute = (int)iRemainder/(60*1000);
		iRemainder = (int)millis%(60*1000);
		int iSecond = (int)iRemainder/(1000);
		bResult = addZero(iHour)+":"+addZero(iMinute)+":"+addZero(iSecond);
		return bResult;
	}
	
	private static String addZero(int i){
		i = Math.abs(i);
		return i<10?"0"+i:i+"";
	}
	
	/**
	 * 获取上一个周日 yyyyMMdd
	 */
	public static String getPreSunday(String statDay) {
		return getPreSunday(statDay, FORMAT_YYYY_MM_DD);
	}
	
	/**
	 * 获取上一个周日 yyyyMMdd
	 */
	public static String getPreSunday(String statDay, SimpleDateFormat dateFormat) {
		try {
			Date date = dateFormat.parse(statDay);
			Calendar calendar = Calendar.getInstance();	
			calendar.setTime(date);		
			
			int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
			int md;
			if(dayOfWeek==Calendar.SUNDAY) {
				md = -7;
			} else {
				md = 1 - dayOfWeek;
			}
			calendar.add(Calendar.DAY_OF_MONTH, md);
			
			Date d = calendar.getTime();
			return dateFormat.format(d);
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
		
	}
	
	/**
	 * 获取weeks周之前的的周一
	 */
	public static String getPreWeeksMonday(String statDay, int weeks) {
		return getPreWeeksMonday(statDay, weeks, FORMAT_YYYY_MM_DD);
	}
	
	/**
	 * 获取weeks周之前的的周一
	 */
	public static String getPreWeeksMonday(String statDay, int weeks, SimpleDateFormat dateFormat) {
		try {
			String preSunday = getPreSunday(statDay);
			Date date = dateFormat.parse(preSunday);
			
			Calendar calendar = Calendar.getInstance();	
			calendar.setTime(date);		// statDay时间
			
			calendar.add(Calendar.DAY_OF_MONTH, 1-7*weeks);
			Date d = calendar.getTime();
			return dateFormat.format(d);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 获取当天的开始的时间long值.
	 */
	public static long getStartLong(String yyyy_mm_dd) {
		Date d = null;
		try {
			d = FORMAT_YYYY_MM_DD.parse(yyyy_mm_dd);			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return d.getTime();
	}
	
	/**
	 * 获取当天的结束的时间long值.
	 */
	public static long getEndLong(String yyyy_mm_dd) {
		Calendar cal = Calendar.getInstance();	
		try {
			Date d = FORMAT_YYYY_MM_DD.parse(yyyy_mm_dd);			
			cal.setTime(d);	
			
			cal.add(Calendar.DAY_OF_YEAR, 1);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return cal.getTimeInMillis()-1;
	}
	
	
	public static String afterNDay(int n){   
		Calendar c=Calendar.getInstance();   
		DateFormat df=new SimpleDateFormat("yyyy-MM-dd");   
		c.setTime(new Date());   
		c.add(Calendar.DATE,n);   
		Date d2=c.getTime();   
		String s=df.format(d2);   
		return s;   
		}  
	
	/**
	 * 获取2个日期的天数间隔
	 * @return
	 */
	public static long getDaysBetween(Date startDate, Date endDate){
		if(startDate==null || endDate==null) 
			return 0L;
		long startDay = startDate.getTime()/(24*60*60*1000);
		long endDay = endDate.getTime()/(24*60*60*1000);
		return endDay-startDay;
	}
	
	/**
	 * 年月日格式化时间
	 * @param date
	 * @return
	 */
	public static String formatDateWithoutTime(Date date){
		return FORMAT_YYYY_MM_DD.format(date);
	}
	
	/**
	 * 根据Date来获取当天日期。
	 * 返回Date日期。
	 * @throws ParseException 
	 * 
	 */
	public static Date getDate(Date date){
		Date newDate = null;
		try{
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String dates = dateFormat.format(date);
			newDate = dateFormat.parse(dates);
		}catch(Exception e){
			e.printStackTrace();
		}
		return newDate;
	}
	
	/**
	 * @param date
	 * @param month
	 * @return
	 */
	public static Date addMonth(Date date,int month){
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, month);
		return cal.getTime();
	}
	
	public static void main(String[] args) {
		System.out.println(FORMAT_YYYY_MM_DD.format(addMonth(new Date(),12)));
	}
	
	  /**
     * 格式化日期
     * @param date 日期对象
     * @param pattern 格式化日期模式
     * @return 返回格式化后的日期字符串
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date, pattern);
    }
    /**
     * 转换成Date类型(指定Pattern)
     * @param source 待转换的字符串
     * @param pattern 限定日期模式
     * @return java.util.Date对象，转换失败则返回null
     */
    public static Date parseDate(String source, String pattern) {
        return parseDate(source, new String[] { pattern });
    }

    /**
     * 转换成Date类型（指定Pattern数组）
     * @param source 待转换的字符串
     * @param patterns 限定日期模式数组
     * @return java.util.Date对象，转换失败则返回null
     */
    public static Date parseDate(String source, String[] patterns) {
    	if (source == null || source.length() == 0) {
			return null;
		}
        Date date = null;
        try {
            date = DateUtils.parseDate(source, patterns);
        } catch (ParseException e) {
        	e.printStackTrace();
        }
        return date;
    }
    
    /**
     * 用这种格式：yyyy-MM-dd HH:mm:ss，格式化日期
     * @param date 日期对象
     * @return 返回格式化后的日期字符串
     */
    public static String formatDateWithTime(Date date) {
        if (date == null) {
            return "";
        }
        return DateFormatUtils.format(date, DATE_FORMAT_WITH_TIME);
    }
    
    /**
     * 计算两个时间相差多少分
     * @param before
     * @param now
     * @return
     */
    public static long calTimeWidthMinite(Date before,Date now){
    	long diff = now.getTime() - before.getTime();
    	return diff / (60*1000);
    }

}
