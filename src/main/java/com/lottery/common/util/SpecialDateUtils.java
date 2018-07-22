package com.lottery.common.util;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class SpecialDateUtils {
	// >= start && < end
    private static boolean springFestivalCheck = false;
    public static Date springFestivalStartDate = CoreDateUtils.parseDate("2014-01-30");
    public static Date springFestivalEndDate = CoreDateUtils.parseDate("2014-02-06");

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
    public static Date worldCupStartDate = CoreDateUtils.parseDate("2014-06-13");
    public static Date worldCupEndDate = CoreDateUtils.parseDate("2014-07-15");

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
    /**
     * 将开始时间，结束时间，由字符串转换为标准时间
     * @param startTime 开始时间 yyyy-mm-dd
     * @param endTime 结束时间 yyyy-mm-dd
     * @param count ,如果开始时间为空往后数几个月
     * @return map key startDate ,endDate
     * */
    public static BetweenDate getBetweenDate(Integer count,String startTime,String endTime){
    	if(count==null||count==0)
    		count=3;
    	Date startDate=null;
		Date endDate=null;
		BetweenDate between=new BetweenDate();
		if(StringUtils.isBlank(startTime)){
			Calendar cd = Calendar.getInstance();
			cd.add(Calendar.MONTH, -1*count);
			startDate=cd.getTime();
		}else{
			startDate=CoreDateUtils.parseDate(startTime, CoreDateUtils.DATE);
		}
		if(StringUtils.isBlank(endTime)){
			endDate=new Date();
		}else{
			endDate=CoreDateUtils.getTomorrowDay(endTime);
			if((endDate.getTime()-(new Date().getTime()))>0){
				endDate=new Date();
			}
		}
		between.setStartDate(startDate);
		between.setEndDate(endDate);;
		return  between;
    }
    
}
