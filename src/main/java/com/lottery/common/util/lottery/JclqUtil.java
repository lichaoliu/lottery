package com.lottery.common.util.lottery;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.TimeRegion;
import com.lottery.common.contains.LotteryConstant;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreTimeUtils;
/**
 * @author fengqinyun
 */
public class JclqUtil {
	protected static final Logger logger = LoggerFactory.getLogger(JclqUtil.class);

	/**
	 * 根据比赛时间得到官方销售截止时间
	 * 竞彩篮球截止时间：周一、二、五 09:00～24:00 周三、四 07:30～24:00 周六至周日 09:00～次日01:00
	 * @param matchDate
	 * @return Date
	 */
	
    static public final String DAYSINWEEK = "日一二三四五六";

    public static Date getOfficialEndSaleTimeByMatchDateDuringSpringFestival(Date matchDate) {
        if (matchDate == null) {
            return null;
        }

        if (!CoreDateUtils.isDuringSpringFestival(matchDate)) {
            // 不在春节期间，不做处理
            return null;
        }

        // 全部设置成春节休假开始时间
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

        // 如果是春节期间的比赛，统一截止时间
        Date endSaleDate = getOfficialEndSaleTimeByMatchDateDuringSpringFestival(matchDate);
        if (endSaleDate != null) {
            return endSaleDate;
        }
        
        //欧洲杯期间，统一判断
        Date europeCupTime=JczqUtil.getOfficialEndSaleTimeByMatchDateDuringEuropeCup(matchDate);
        if(europeCupTime!=null){
        	return europeCupTime;
        }
    	
    	Calendar cd = Calendar.getInstance();
    	cd.setTime(matchDate);
    	
    	int matchWeekday = cd.get(Calendar.DAY_OF_WEEK);
		int hourOfDay = cd.get(Calendar.HOUR_OF_DAY);
		int minute = cd.get(Calendar.MINUTE);

		Calendar endSaleCalendar = Calendar.getInstance();
		endSaleCalendar.setTime(matchDate);
		
		endSaleCalendar.set(Calendar.MILLISECOND, 0);

        // 如果是世界杯期间的比赛, 篮彩取消周三周四早场, 跟竞彩足球采用一样的截止时间
        if (CoreDateUtils.isDuringWorldCup(matchDate)) {
            return JczqUtil.getOfficialEndSaleTimeByMatchDate(matchDate);
        }

		
		if (matchWeekday == Calendar.MONDAY) {
			if (hourOfDay >= 1 && (hourOfDay < 9 || (hourOfDay == 9 && minute == 0))) {
				endSaleCalendar.set(Calendar.HOUR_OF_DAY, 1);
				endSaleCalendar.set(Calendar.MINUTE, 0);
				endSaleCalendar.set(Calendar.SECOND, 0);
			}
		} else if (matchWeekday == Calendar.TUESDAY || matchWeekday == Calendar.FRIDAY) {
			if (hourOfDay < 9 || (hourOfDay == 9 && minute == 0)) {
				endSaleCalendar.set(Calendar.HOUR_OF_DAY, 0);
				endSaleCalendar.set(Calendar.MINUTE, 0);
				endSaleCalendar.set(Calendar.SECOND, 0);
			}
		} else if (matchWeekday == Calendar.WEDNESDAY || matchWeekday == Calendar.THURSDAY) {
			Calendar cdBegin = Calendar.getInstance();
			cdBegin.setTimeInMillis(matchDate.getTime());
			cdBegin.set(Calendar.HOUR_OF_DAY, 7);
			cdBegin.set(Calendar.MINUTE, 30);
			cdBegin.set(Calendar.SECOND, 0);
			cdBegin.set(Calendar.MILLISECOND, 0);
			
			if (cd.getTimeInMillis() <= cdBegin.getTimeInMillis()) {
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
	 * @param endSaleForward
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
	//根据竞彩篮球的官方赛程发布时间和比赛场次编号生成我们系统的赛程场次编号
	public static String getMatchNum(String officialDate, String officialNum) {
		return (officialDate.length() > 8 ? officialDate.substring(0, 8) : officialDate) + LotteryConstant.JCLQ_MATCH_NUM_CODE_DEFAULT + (officialNum.length() > 3 ? officialNum.substring(officialNum.length() - 3) : officialNum);
	}

    /**
     * 根据标准年月日+编号的场次号生成我们系统的赛程场次编号
     * @param officialMatchNum
     * @return
     */
    public static String getMatchNum(String officialMatchNum) {
        return officialMatchNum.substring(0, officialMatchNum.length() - 3) + LotteryConstant.JCLQ_MATCH_NUM_CODE_DEFAULT + officialMatchNum.substring(officialMatchNum.length()-3);
    }
    
    //根据我们系统的赛程场次编号生成官方赛程场次编号
	public static String getOfficialNum(String matchNum){
        Date matchDate = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        //我们系统场次编码的前8位为官方发布比赛时间的日期
        try {
			matchDate = sdf.parse(matchNum.substring(0, 8));
		} catch (ParseException e) {
			e.printStackTrace();
		}
        if(matchDate==null){
        	return null;
        }
		Calendar cal = Calendar.getInstance();
		cal.setTime(matchDate);
		String dayofweek =String.valueOf(DAYSINWEEK.charAt(cal.get(Calendar.DAY_OF_WEEK)-1));
		String matchNoString = matchNum.substring(matchNum.length()-3, matchNum.length());
		return "周" + dayofweek + matchNoString;
	}

    private static Map<Integer, TimeRegion> jclqSaleClosedTimeRegionMap = new HashMap<Integer, TimeRegion>();

    static {
        jclqSaleClosedTimeRegionMap.put(Calendar.MONDAY, TimeRegion.parse("1:00|9:00"));
        jclqSaleClosedTimeRegionMap.put(Calendar.TUESDAY, TimeRegion.parse("0:00|9:00"));
        jclqSaleClosedTimeRegionMap.put(Calendar.WEDNESDAY, TimeRegion.parse("0:00|7:30"));
        jclqSaleClosedTimeRegionMap.put(Calendar.THURSDAY, TimeRegion.parse("0:00|7:30"));
        jclqSaleClosedTimeRegionMap.put(Calendar.FRIDAY, TimeRegion.parse("0:00|9:00"));
        jclqSaleClosedTimeRegionMap.put(Calendar.SATURDAY, TimeRegion.parse("0:00|9:00"));
        jclqSaleClosedTimeRegionMap.put(Calendar.SUNDAY, TimeRegion.parse("1:00|9:00"));
    }

    public static boolean isDuringSaleClosedPeriod() {
        return isDuringSaleClosedPeriod(0);
    }

    public static TimeRegion getSaleClosedPeriod(Calendar calendar, Map<Integer, TimeRegion> saleClosedTimeRegionMap) {
        int weekday = calendar.get(Calendar.DAY_OF_WEEK);
        if (saleClosedTimeRegionMap != null && saleClosedTimeRegionMap.containsKey(weekday)) {
            return saleClosedTimeRegionMap.get(weekday);
        }

        // 如果是世界杯期间, 停售期跟竞彩足球一致
        TimeRegion timeRegion = null;
        if (CoreDateUtils.isDuringWorldCup(calendar.getTime())) {
            timeRegion = JczqUtil.getSaleClosedPeriod(calendar, saleClosedTimeRegionMap);
        }
        if (timeRegion != null) {
            return timeRegion;
        }

        return jclqSaleClosedTimeRegionMap.get(weekday);
    }

    /**
     * 是否处于竞彩篮球停售期
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
     * 是否处于竞彩篮球停售期
     * @param forwardTimeMillis 提前毫秒数
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
     * 是否处于竞彩篮球停售期
     * @param forwardTimeMillis 提前毫秒数
     * @return ture=停售, false=开售
     */
    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillis) {
        return isDuringSaleClosedPeriod(forwardTimeMillis, null);
    }
}
