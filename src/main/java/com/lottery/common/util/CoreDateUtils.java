package com.lottery.common.util;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
/**
 * 时间工具类
 * @author fengqinyun
 *
 */
public class CoreDateUtils {
	private static final Logger logger = LoggerFactory.getLogger(CoreDateUtils.class.getName());
	
	public static final String DATETIME = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE = "yyyy-MM-dd";
	public static final String DATE_YYYYMMDD="yyyyMMdd";
	public static final String DATE_YYYYMM="yyyyMM"; 
	public static final String DATE_YYYYMMDDHHMMSS="yyyyMMddHHmmss";  
	public static final String DATE_YEAR="yyyy";
	public static String formatDate(Date date) {
		return formatDate(date, DATE);
	}
	
	public static String formatDateTime(Date date) {
		return formatDate(date, DATETIME);
	}

	public static String formatDate(Date date, String pattern) {
        if (date == null) {
            return null;
        }
        return DateFormatUtils.format(date, pattern, Locale.CHINA);
	}
	
	public static String formatDate(String dateStr, String srcPattern, String desPattern) {
		Date date = parseDate(dateStr, srcPattern);
		if (date == null) {
			return null;
		}
		return formatDate(date, desPattern);
	}

	public static Date parseDate(String dateStr) {
		return parseDate(dateStr, DATE);
	}
	
	public static Date parseDateTime(String dateStr) {
		return parseDate(dateStr, DATETIME);
	}
	
	public static Date parseLongDate(String dateStr) {
		return parseDate(dateStr, new String[] {
                DATETIME,
                "yyyy-MM-dd HH:mm:ss.SSS",
        });
	}

	public static Date parseDate(String dateStr, String pattern) {
		return parseDate(StringUtils.trim(dateStr), new String[]{pattern});
	}

	
    public static Date parseDate(String dateStr, String[] patterns) {
        if (dateStr == null) {
            return null;
        }
        try {
            return DateUtils.parseDateStrictly(dateStr, patterns);
        } catch (ParseException e) {
            logger.error("日期转换错误, dateStr={}, pattern={}", dateStr, StringUtils.join(patterns, ","));
            logger.error(e.getMessage(), e);
            return null;
        }
    }

    /***
     * 获得到某个时间点的订单时间
     * @param hour 时间点,如:凌晨4点,hour=4
     * */
    public long getWaitTime(int hour) {
        Calendar cd = Calendar.getInstance();
        cd.set(Calendar.HOUR_OF_DAY, hour);
        cd.set(Calendar.MINUTE, 0);
        cd.set(Calendar.SECOND, 0);
        cd.set(Calendar.MILLISECOND, 0);
        //当前时间已经过了当天删除时间点，等待时间为下一天时间点到当前时间
        if (Calendar.getInstance().get(Calendar.HOUR_OF_DAY) >=hour) {
            cd.add(Calendar.DATE, 1);
        }
        return cd.getTimeInMillis() - System.currentTimeMillis();
    }
    
	
	/**
	 * 两个时间相隔天数 time1-time2
	 * @param time1
	 * @param time2
	 * @return
	 */
	public static long diffDays(Date time1, Date time2){
        if (time1 == null || time2 == null) {
            return 0;
        }
        return (time1.getTime() - time2.getTime()) / 1000 / 60 / 60 / 24;
	}
	public static Date getLastDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DATE, -1);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		return calendar.getTime();
	}
	/**
	 * 获得下一年的YYYY格式
	 * @return
	 */
	public static String getNextYear(){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy");       
	    Calendar lastDate = Calendar.getInstance();   
	    lastDate.add(Calendar.YEAR,1);
		return sdf.format(lastDate.getTime());	
	}
	
	/**
	 * 获得下一年的YYYY格式
	 * @return
	 */
	public static String getNextYear(String year){
		SimpleDateFormat sdf=new SimpleDateFormat("yyyy"); 
		 Calendar calendar=Calendar.getInstance();   //创建一个日历对象
		 Date date ;
		 try {
			date = sdf.parse(year);
			calendar.setTime(date); 
			calendar.add(Calendar.YEAR,1);
		} catch (ParseException e) {

			e.printStackTrace();
		}
		return sdf.format(calendar.getTime());	
	}
	/**
	* 日期转换成字符串
	* @param date 
	* @return str yyyy-MM-dd HH:mm:ss
	*/
	public static String DateToStr(Date date) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(DATETIME);
	   String str = format.format(date);
	   return str;
	} 

	/**
	* 字符串转换成日期
	* @param str 日期  yyyy-MM-dd HH:mm:ss
	* @return date
	*/
	public static Date StrToDate(String str) {
	  
	   SimpleDateFormat format = new SimpleDateFormat(DATETIME);
	   Date date = null;
	   try {
	    date = format.parse(str);
	   } catch (ParseException e) {
	    e.printStackTrace();
	   }
	   return date;
	}
	/**
	 * 得某个时间前多少分钟的时间
	 * @param date 时间
	 * @param minute 多少分钟
	 * @return
	 */
	public static Date getBeforeTime(Date date,int minute){
		Calendar calendar=Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MILLISECOND,-1*minute*1000*60);
	    return calendar.getTime();
	}

	/**
	 * 当前时间前多少分钟的时间
	 * @param minute 多少分钟
	 * @return
	 */
	public static Date getBeforeTime(int minute){
		Calendar calendar=Calendar.getInstance();
		calendar.add(Calendar.MILLISECOND,-1*minute*1000*60);
		return calendar.getTime();
	}
	
	
	/**
	 * 获取上个月第一天  
	 * @return 时间格式 yyyy-mm-dd
	 * */
	public static String getLastMouthFirstDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, -1);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return sdf.format(cal_1.getTime());
	}
	
	/**
	 * 获取上个月最后一天  
	 * @return 时间格式 yyyy-mm-dd
	 * */
	public static String getLastMouthLastDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, 0);// 
		return sdf.format(cal_1.getTime());
	}
	
	
	/**
	 * 获取本月第一天  
	 * @return 时间格式 yyyy-mm-dd
	 * */
	public static String getMouthFirstDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.add(Calendar.MONTH, 0);
		cal_1.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		return sdf.format(cal_1.getTime());
	}
	
	/**
	 * 获取本月最后一天  
	 * @return 时间格式 yyyy-mm-dd
	 * */
	public static String getMouthLastDay() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE);
		Calendar cal_1 = Calendar.getInstance();// 获取当前日期
		cal_1.set(Calendar.DAY_OF_MONTH, cal_1.getActualMaximum(Calendar.DAY_OF_MONTH));// 设置为1号,当前日期既为本月第一天
		return sdf.format(cal_1.getTime());
	}
	
	
	/**
	 * 获取明天的时间
	 * @param dateStr 时间格式 yyyy-mm-dd
	 * */
	public static Date getTomorrowDay(String dateStr){
		Date date=parseDate(dateStr, DATE);
		Calendar time=Calendar.getInstance();
		time.setTime(date);
		time.add(Calendar.DATE, 1);
		return time.getTime();
	}
	
	
	
	
	//================
	
	 public static String getNowDate(String format){
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat(format);
	    	return sdf.format(date);
	    }

	    public static String getNowDateYYYYMMDD(){
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	    	return sdf.format(date);
	    }
	    
	    public static String getNowDateYYYY_MM_DD(){
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	    	return sdf.format(date);
	    }
	    
	    public static String getNowDateYYYY_MM_DD_HH_MM(){
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    	return sdf.format(date);
	    }
	    
	    public static String getNowDateYYYY_MM_DD_HH_MM_SS(){
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	    	return sdf.format(date);
	    }
	    
	    public static String getNowDateYYYYMMDDHHMMSS(){
	    	Date date = new Date();
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	    	return sdf.format(date);
	    }
	    //获得一个星期前的日期
	    public static String getLastWeekDateYYYY_MM_DD(Date date){
	    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   	 
	    	   Calendar calendar = Calendar.getInstance();
	    	   calendar.setTime(date);
	    	   calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) -1);//一星期前
	   	  
	   	       return sdf.format(calendar.getTime());//一星期前
	    }
	    //获得一个月前的日期
	    public static String getLastMonthDateYYYY_MM_DD(Date date){
	 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	   Calendar calendar = Calendar.getInstance();
	 	   calendar.setTime(date);
	 	   calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -1);//一星期前
		  
		   return sdf.format(calendar.getTime());//一个月前
	    }
	    //获得三个月前的日期
	    public static String getLastThreeMonthDateYYYY_MM_DD(Date date){
	 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	   Calendar calendar = Calendar.getInstance();
	 	   calendar.setTime(date);
	 	   calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -3);//三个月前
	 	  
		   return sdf.format(calendar.getTime());//一个月前
	    }
	    //获得一年前的日期
	    public static String getLastYearDateYYYY_MM_DD(Date date){
	 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	 	   Calendar calendar = Calendar.getInstance();
	 	   calendar.setTime(date);
	 	   calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) -1);//一年前
		  
		   return sdf.format(calendar.getTime());
	    }
	    
	    //特殊时间处理
	    // >= start && < end
	    private static boolean springFestivalCheck = false;
	    public static Date springFestivalStartDate = parseDate("2014-01-30");
	    public static Date springFestivalEndDate = parseDate("2014-02-06");

	    public static boolean isDuringSpringFestival() {
	        return isDuringSpringFestival(new Date());
	    }

	    public static boolean isDuringSpringFestival(Date date) {
	        if (!springFestivalCheck) {
	            return false;
	        }
	        if (date == null) {
	            return isDuringSpringFestival();
	        }
	        if (date.before(springFestivalStartDate) || date.after(springFestivalEndDate)) {
	            return false;
	        }

	        return true;
	    }

	    // >= start && < end
	    private static boolean worldCupCheck = true;
	    public static Date worldCupStartDate = parseDate("2016-08-06");
	    public static Date worldCupEndDate = parseDate("2016-10-21");

	    public static boolean isDuringWorldCup() {
	        return isDuringWorldCup(new Date());
	    }

	    public static boolean isDuringWorldCup(Date date) {
	        if (!worldCupCheck) {
	            return false;
	        }
	        if (date == null) {
	            return isDuringWorldCup();
	        }
	        if (date.before(worldCupStartDate) || date.after(worldCupEndDate)) {
	            return false;
	        }

	        return true;
	    }

}
