package com.lottery.common.util.lottery;

import com.lottery.common.Time;
import com.lottery.common.TimeRegion;
import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreTimeUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
/**
 * @author fengqinyun
 */
public class JczqUtil {
	protected static final Logger logger = LoggerFactory.getLogger(JczqUtil.class);

	/**
	 * 根据比赛时间得到官方销售截止时间
	 * 竞彩足球截止时间：周一、二、三、四、五 09:00～24:00  周六至周日 09:00～次日01:00
	 * @param matchDate
	 * @return Date
	 */
	
    static public final String DAYSINWEEK = "日一二三四五六";

    /**
     * 欧洲杯期间的停售时间处理
     * 规则：在欧洲杯比赛日(2016)6月3日-7月11日期间（除6月24、29、30日,7月5日,6日外），竞彩销售时间延长至次日03：00停售
     * 由于这里的入参是比赛时间，所以都拿官方比赛日次日做判断
     * @param matchDate
     * @return
     */
    private static Date europeCup2016StartDate = CoreDateUtils.parseLongDate("2016-06-11 00:00:00");
    private static Date europeCup2016EndDate = CoreDateUtils.parseLongDate("2016-07-11 09:00:00");
    private static Map<String, Boolean> europeCup2016NoMatchDateMap = new HashMap<String, Boolean>();
    //2016年欧洲杯无比赛周末为24:00,只有7月10日的比赛需要修改
    private static Map<String, Boolean> europeCup2016NoMatchWeekendDateMap = new HashMap<String, Boolean>();
    static {
    	europeCup2016NoMatchDateMap.put("2016-06-24", true);
        europeCup2016NoMatchDateMap.put("2016-06-25", true);
    	europeCup2016NoMatchDateMap.put("2016-06-29", true);
    	europeCup2016NoMatchDateMap.put("2016-06-30", true);
    	europeCup2016NoMatchDateMap.put("2016-07-05", true);
    	europeCup2016NoMatchDateMap.put("2016-07-06", true);
        europeCup2016NoMatchDateMap.put("2016-07-09", true);
        europeCup2016NoMatchWeekendDateMap.put("2016-07-10", true);
    }



    public static boolean isDuringEuropeCup(Date date) {
    	if (date == null) {
    		return false;
    	}
    	// 不在欧洲杯期间，不做处理
    	if (date.before(europeCup2016StartDate) || date.after(europeCup2016EndDate)) {
    		return false;
    	}
    	
    	String matchDateStr = CoreDateUtils.formatDate(date);
    	if (europeCup2016NoMatchDateMap.containsKey(matchDateStr) && europeCup2016NoMatchDateMap.get(matchDateStr)) {
    		// 没有比赛的日期，不做特殊处理
    		return false;
    	}
    	
    	return true;
    }
    public static boolean isDuringEuropeCupNoMatchWeekeenDay(Date date){

        if (date == null) {
            return false;
        }

        // 不在欧洲杯期间，不做处理
        if (date.before(europeCup2016StartDate) || date.after(europeCup2016EndDate)) {
            return false;
        }

        String matchDateStr = CoreDateUtils.formatDate(date);

        if (europeCup2016NoMatchWeekendDateMap.containsKey(matchDateStr) && europeCup2016NoMatchWeekendDateMap.get(matchDateStr)) {
            // 不是欧洲杯无比赛周末,欧洲杯无比赛周末为24:00
            return true;
        }

        return false;
    }

    public static Date getOfficialEndSaleTimeByMatchDateDuringEuropeCup(Date matchDate) {
    	if (matchDate == null) {
			return null;
		}

    	if (!isDuringEuropeCup(matchDate)) {
    		// 不在欧洲杯期间，不做处理
    		return null;
    	}

        if (isDuringEuropeCupNoMatchWeekeenDay(matchDate)) {
            //欧洲杯无比赛周末,卖到24:00
            Calendar cd = Calendar.getInstance();
            cd.setTime(matchDate);

            int hourOfDay = cd.get(Calendar.HOUR_OF_DAY);
            int minute = cd.get(Calendar.MINUTE);

            Calendar endSaleCalendar = Calendar.getInstance();
            endSaleCalendar.setTime(matchDate);

            endSaleCalendar.set(Calendar.MILLISECOND, 0);

            // 欧洲杯无比赛周末,设置为24:00
            if (hourOfDay < 9 || (hourOfDay == 9 && minute == 0)) {
                endSaleCalendar.set(Calendar.HOUR_OF_DAY, 0);
                endSaleCalendar.set(Calendar.MINUTE, 0);
                endSaleCalendar.set(Calendar.SECOND, 0);
            }

            return endSaleCalendar.getTime();
        }




    	Calendar cd = Calendar.getInstance();
    	cd.setTime(matchDate);
    	
		int hourOfDay = cd.get(Calendar.HOUR_OF_DAY);
		int minute = cd.get(Calendar.MINUTE);

		Calendar endSaleCalendar = Calendar.getInstance();
		endSaleCalendar.setTime(matchDate);

		endSaleCalendar.set(Calendar.MILLISECOND, 0);

		// 不管周几都卖到03:00，03:00-09:00都设置成03:00
		if ((hourOfDay > 3 && hourOfDay < 9) || (hourOfDay == 9 && minute == 0) || (hourOfDay == 3 && minute > 0)) {
			endSaleCalendar.set(Calendar.HOUR_OF_DAY, 3);
			endSaleCalendar.set(Calendar.MINUTE, 0);
			endSaleCalendar.set(Calendar.SECOND, 0);
		}
		return endSaleCalendar.getTime();
    }

    public static Date getOfficialEndSaleTimeByMatchDateDuringSpringFestival(Date matchDate) {
        if (matchDate == null) {
            return null;
        }

        if (!CoreDateUtils.isDuringSpringFestival(matchDate)) {
            // 不在春节期间，不做处理
            return null;
        }

        // 全部设置成春节开始时间
        return CoreDateUtils.springFestivalStartDate;
    }
    
    /**
     * 传入一个比赛时间，得到竞彩官方规则针对这个比赛时间的截止销售时间
     * @param matchDate
     * @return
     */
    public static Date getOfficialEndSaleTimeByMatchDate(Date matchDate) {
    	if (matchDate == null) {
			return null;
		}


    	// 如果在欧洲杯期间，做一下预处理
    	Date endSaleDate = getOfficialEndSaleTimeByMatchDateDuringEuropeCup(matchDate);
    	if (endSaleDate != null) {
    		return endSaleDate;
    	}


        // 如果是春节期间的比赛，统一截止时间
//        Date endSaleDate = getOfficialEndSaleTimeByMatchDateDuringSpringFestival(matchDate);
//        if (endSaleDate != null) {
//            return endSaleDate;
//        }

    	
    	Calendar cd = Calendar.getInstance();
    	cd.setTime(matchDate);
    	
    	int matchWeekday = cd.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = cd.get(Calendar.HOUR_OF_DAY);
		int minute = cd.get(Calendar.MINUTE);

		Calendar endSaleCalendar = Calendar.getInstance();
		endSaleCalendar.setTime(matchDate);

		endSaleCalendar.set(Calendar.MILLISECOND, 0);

        // 如果是世界杯期间的比赛
        if (CoreDateUtils.isDuringWorldCup(matchDate)) {
            TimeRegion timeRegion = jczqWorldCupSaleClosedTimeRegionMap.get(CoreDateUtils.formatDate(matchDate));
            if (timeRegion != null) {
                // 按照停售时间段进行处理, 如果在停售时间段内以左边界为赛程停止销售时间
                if (CoreTimeUtils.isDuringPeriod(matchDate, timeRegion)) {
                    // 默认只取第一个时间段处理
                    Time time = timeRegion.getRegionList().get(0).getLeft();
                    endSaleCalendar.set(Calendar.HOUR_OF_DAY, time.getHour());
                    endSaleCalendar.set(Calendar.MINUTE, time.getMinute());
                    endSaleCalendar.set(Calendar.SECOND, 0);
                }

                return endSaleCalendar.getTime();
            }
        }
		
		if (matchWeekday == Calendar.MONDAY) {
			if (hourOfDay >= 1 && (hourOfDay < 9 || (hourOfDay == 9 && minute == 0))) {
				endSaleCalendar.set(Calendar.HOUR_OF_DAY, 1);
				endSaleCalendar.set(Calendar.MINUTE, 0);
				endSaleCalendar.set(Calendar.SECOND, 0);
			}
		} else if (matchWeekday == Calendar.TUESDAY || matchWeekday == Calendar.FRIDAY || matchWeekday == Calendar.WEDNESDAY || matchWeekday == Calendar.THURSDAY) {
			if (hourOfDay < 9 || (hourOfDay == 9 && minute == 0)) {
				endSaleCalendar.set(Calendar.HOUR_OF_DAY, 0);
				endSaleCalendar.set(Calendar.MINUTE, 0);
				endSaleCalendar.set(Calendar.SECOND, 0);
			}
		} else if (matchWeekday == Calendar.SATURDAY) {
			if (hourOfDay < 9 || (hourOfDay == 9 && minute == 0)) {
				endSaleCalendar.set(Calendar.HOUR_OF_DAY, 0);
				endSaleCalendar.set(Calendar.MINUTE, 0);
				endSaleCalendar.set(Calendar.SECOND, 0);
			}
		} else {
			if (hourOfDay >= 1 && (hourOfDay < 9 || (hourOfDay == 9 && minute == 0))) {
				endSaleCalendar.set(Calendar.HOUR_OF_DAY, 1);
				endSaleCalendar.set(Calendar.MINUTE, 0);
				endSaleCalendar.set(Calendar.SECOND, 0);
			}
		}
		return endSaleCalendar.getTime();
    }
    
	/**
	 * 注意：返回的是不减去提前量的时间，提前量只用来做判断
	 * @param matchDate
	 * @param endSaleForward（提前销售截止时间，毫秒）
	 * @return
	 */
	public static Date getEndSaleTimeByMatchDate(Date matchDate, long endSaleForward) {
		if (matchDate == null) {
			return null;
		}
		
		// 不带提前量得到本场比赛在官方的截止时间
		Date officialEndSaleTime = getOfficialEndSaleTimeByMatchDate(matchDate);
		
		// 加上提前量
		Calendar cd = Calendar.getInstance();
		cd.setTimeInMillis(officialEndSaleTime.getTime() - endSaleForward);
		
		// 带提前量以后的比赛时间在官方的截止时间
		Date endSaleTime = getOfficialEndSaleTimeByMatchDate(cd.getTime());
		
		if (endSaleTime.before(officialEndSaleTime)) {
			// 如果带上提前量后的官方截止时间发生了变化,说明应该使用第二次计算的截止时间
			return endSaleTime;
		}
		
		return officialEndSaleTime;
	}
	
	//根据竞彩足球的官方赛程发布时间和比赛场次编号生成我们系统的赛程场次编号
	public static String getMatchNum(String officialDate,String officialNum){
		return (officialDate.length() > 8 ? officialDate.substring(0, 8) : officialDate) + LotteryConstant.JCZQ_MATCH_NUM_CODE_DEFAULT + (officialNum.length() > 3 ? officialNum.substring(officialNum.length() - 3) : officialNum);
	}

    /**
     * 根据标准年月日+编号的场次号生成我们系统的赛程场次编号
     * @param officialMatchNum
     * @return
     */
    public static String getMatchNum(String officialMatchNum) {
        return officialMatchNum.substring(0, officialMatchNum.length() - 3) + LotteryConstant.JCZQ_MATCH_NUM_CODE_DEFAULT + officialMatchNum.substring(officialMatchNum.length()-3);
    }
    
    //根据我们系统的赛程场次编号生成官方赛程场次编号
	public static String getOfficialNum(String matchNum){
        Date matchDate = CoreDateUtils.parseDate(matchNum.substring(0, 8), "yyyyMMdd");
        if (matchDate == null) {
            logger.error("转换官方场次编号出错, matchNum={}", matchNum);
            return null;
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(matchDate);
		String dayofweek =String.valueOf(DAYSINWEEK.charAt(cal.get(Calendar.DAY_OF_WEEK)-1));
		String matchNoString = matchNum.substring(matchNum.length()-3, matchNum.length());
		return "周" + dayofweek + matchNoString;
	}

    private static Map<Integer, TimeRegion> jczqSaleClosedTimeRegionMap = new HashMap<Integer, TimeRegion>();

    static {
        jczqSaleClosedTimeRegionMap.put(Calendar.MONDAY, TimeRegion.parse("1:00|9:00"));
        jczqSaleClosedTimeRegionMap.put(Calendar.TUESDAY, TimeRegion.parse("0:00|9:00"));
        jczqSaleClosedTimeRegionMap.put(Calendar.WEDNESDAY, TimeRegion.parse("0:00|9:00"));
        jczqSaleClosedTimeRegionMap.put(Calendar.THURSDAY, TimeRegion.parse("0:00|9:00"));
        jczqSaleClosedTimeRegionMap.put(Calendar.FRIDAY, TimeRegion.parse("0:00|9:00"));
        jczqSaleClosedTimeRegionMap.put(Calendar.SATURDAY, TimeRegion.parse("0:00|9:00"));
        jczqSaleClosedTimeRegionMap.put(Calendar.SUNDAY, TimeRegion.parse("1:00|9:00"));
    }

    // 世界杯期间的停售时间覆盖
    private static Map<String, TimeRegion> jczqWorldCupSaleClosedTimeRegionMap = new HashMap<String, TimeRegion>();

    static {


      //  jczqWorldCupSaleClosedTimeRegionMap.put("2016-07-10", TimeRegion.parse("00:00|9:00"));

    }

    public static boolean isDuringSaleClosedPeriod() {
        return isDuringSaleClosedPeriod(0);
    }

    public static TimeRegion getSaleClosedPeriod(Calendar calendar, Map<Integer, TimeRegion> saleClosedTimeRegionMap) {
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (saleClosedTimeRegionMap != null && saleClosedTimeRegionMap.containsKey(weekday)) {
            return saleClosedTimeRegionMap.get(weekday);
        }

        // 如果是世界杯期间
        TimeRegion timeRegion = null;
        if (CoreDateUtils.isDuringWorldCup(calendar.getTime())) {
            String date = CoreDateUtils.formatDate(calendar.getTime());
            if (jczqWorldCupSaleClosedTimeRegionMap.containsKey(date)) {
                timeRegion = jczqWorldCupSaleClosedTimeRegionMap.get(date);
            }
        }
        if (timeRegion != null) {
            return timeRegion;
        }

        return jczqSaleClosedTimeRegionMap.get(weekday);
    }

    /**
     * 是否处于竞彩足球停售期
     * @param forwardTimeMillisBegin 提前毫秒数, 相对于每天最后出票时间点的提前量(变量的Begin意思为停售期的开始区间), 例如1:00停止出票, 提前五分钟则0:55即停止销售
     * @param forwardTimeMillisEnd 提前毫秒数, 相对于每天开始出票时间点的提前量(变量的End意思为停售期的结束区间), 例如9:00开始可以出票, 提前五分钟则8:55即认为开始销售
     * @param saleClosedTimeRegionMap 自定义的时间区间供扩展
     * @return ture=停售, false=开售
     */
    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillisBegin, long forwardTimeMillisEnd, Map<Integer, TimeRegion> saleClosedTimeRegionMap) {
        return isDuringSaleClosedPeriod(null, forwardTimeMillisBegin, forwardTimeMillisEnd, saleClosedTimeRegionMap);
    }

    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillisBegin, long forwardTimeMillisEnd) {
        return isDuringSaleClosedPeriod(forwardTimeMillisBegin, forwardTimeMillisEnd, null);
    }

    /**
     * 是否处于竞彩足球停售期
     * @param forwardTimeMillis 提前毫秒数
     * @param saleClosedTimeRegionMap 自定义的停售区间
     * @return ture=停售, false=开售
     */
    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillis, Map<Integer, TimeRegion> saleClosedTimeRegionMap) {
        return isDuringSaleClosedPeriod(null, forwardTimeMillis, 0, saleClosedTimeRegionMap);
    }

    public static boolean isDuringSaleClosedPeriod(Date checkTime, long forwardTimeMillisBegin, long forwardTimeMillisEnd, Map<Integer, TimeRegion> saleClosedTimeRegionMap) {
        Calendar current = Calendar.getInstance();
        if (checkTime != null) {
            current.setTime(checkTime);
        }

        if (forwardTimeMillisBegin == 0) {
            // 获取当前时间的停售时间范围
            TimeRegion saleClosedTimeRegion = getSaleClosedPeriod(current, saleClosedTimeRegionMap);
            return CoreTimeUtils.isCurrentDuringPeriod(saleClosedTimeRegion, 0, forwardTimeMillisEnd);
        }

        // 先判断当前时间是否处于停售期
        boolean isCurrentSaleClosed = isDuringSaleClosedPeriod(0, saleClosedTimeRegionMap);

        if (isCurrentSaleClosed) {
            return true;
        }

        // 再判断加上提前量之后是否处于停售期
        current.add(Calendar.MILLISECOND, (int)forwardTimeMillisBegin);

        // 获取加上时间提前量之后的周几
        TimeRegion saleClosedTimeRegion = getSaleClosedPeriod(current, saleClosedTimeRegionMap);

        return CoreTimeUtils.isDuringPeriod(current.getTime(), saleClosedTimeRegion, 0, forwardTimeMillisEnd);
    }

    /**
     * 是否处于竞彩足球停售期
     * @param forwardTimeMillis 提前毫秒数
     * @return ture=停售, false=开售
     */
    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillis) {
        return isDuringSaleClosedPeriod(forwardTimeMillis, null);
    }
}
