package com.lottery.common.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {

	private static Date date_1000 = null;

	static {
		try {
			date_1000 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
					.parse("1000-01-01 00:00:00");
		} catch (ParseException e) {
		}
	}

	public static Date get1000Date() {
		return date_1000;
	}

	public static Date parse(String timeStr) {
		return parse("yyyy-MM-dd HH:mm:ss", timeStr);
	}

	public static Date parse(String pattern, String timeStr) {
		if (StringUtil.isEmpty(pattern) || StringUtil.isEmpty(timeStr)) {
			return null;
		}
		try {
			return new SimpleDateFormat(pattern).parse(timeStr);
		} catch (ParseException e) {
		}
		return null;
	}

	public static String format(Date date) {
		return new SimpleDateFormat("yyyy-MM-dd").format(date);
	}

	public static String format(String pattern, Date date) {
		if (null == date) {
			return null;
		}
		return new SimpleDateFormat(pattern).format(date);
	}

	/**
	 * 取得下一个更新统计缓存时间，即明日0点
	 * 
	 * @return
	 */
	public static Date nextTaskTime() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.add(Calendar.DATE, 1);
		return calendar.getTime();
	}

	/**
	 * 取得当前月份
	 * 
	 * @return
	 */
	public static Date getCurrentMonth() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		return calendar.getTime();
	}

	/**
	 * 取得当日0点
	 * 
	 * @return
	 */
	public static Date getCurrentDay() {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();
	}

	public static int getDayWeek(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(Calendar.DAY_OF_WEEK);
	}

	public static Date getPhaseTime(Date daytime, String hourtime) {
		return parse("yyyy-MM-dd hh:mm", DateUtil.format("yyyy-MM-dd", daytime)
				+ " " + hourtime);
	}

	/**
	 * 将数字按长度转换为字符串，不够长度前面补0
	 * 
	 * @param num
	 * @param lentgh
	 * @return
	 */
	public static String fillZero(int num, int lentgh) {

		String numStr = String.valueOf(num);
		if (lentgh == numStr.length()) {
			return numStr;
		}
		int len = lentgh - numStr.length();
		for (int i = 0; i < len; i++) {

			numStr = "0" + numStr;
		}
		return numStr;
	}

	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 */
	public static String getWeekOfDate(Date date) {
		
		String[] weekDaysCode = { "7", "1", "2", "3", "4", "5", "6" };
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		int intWeek = calendar.get(Calendar.DAY_OF_WEEK)-1;
		return weekDaysCode[intWeek];
	}
	
	
	/**
	 * 根据日期获得星期
	 * 
	 * @param date
	 * @return
	 * @throws ParseException 
	 */
	public static String getWeekOfDate(String date) {
		try {
			SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(format.parse(date));
			int intWeek = calendar.get(Calendar.DAY_OF_WEEK);
			String weekchn = "";
			switch (intWeek) {
			case Calendar.MONDAY:
				weekchn = "周一";
				break;
			case Calendar.TUESDAY:
				weekchn = "周二";
				break;
			case Calendar.WEDNESDAY:
				weekchn = "周三";
				break;
			case Calendar.THURSDAY:
				weekchn = "周四";
				break;
			case Calendar.FRIDAY:
				weekchn = "周五";
				break;
			case Calendar.SATURDAY:
				weekchn = "周六";
				break;
			case Calendar.SUNDAY:
				weekchn = "周日";
				break;
			default:
				break;
			}
			return weekchn;
		} catch (Exception e) {
			// TODO: handle exception
		}
		return null;
	}
	
	
	public static Date addMinites(Date date,int minutes) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }
	
	public static Date addDay(Date date,int day) {
    	Calendar calendar = Calendar.getInstance();
    	calendar.setTime(date);
    	calendar.add(Calendar.DAY_OF_YEAR, day);
        return calendar.getTime();
    }
}
