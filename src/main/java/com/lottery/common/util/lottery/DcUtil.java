package com.lottery.common.util.lottery;

import java.util.Calendar;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.Time;
import com.lottery.common.TimeRegion;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.CoreTimeUtils;

/**
 * @author fengqinyun
 */
public class DcUtil {


    private static TimeRegion dcSaleClosedTimeRegion = TimeRegion.parse("5:50|9:30");

    private static TimeRegion worldCupSaleClosedTimeRegion = TimeRegion.parse("9:00|9:30");

    public static boolean isDuringSaleClosedPeriod() {
        return isDuringSaleClosedPeriod(0);
    }

    /**
     * 是否处于北单停售期
     * @param forwardTimeMillis 提前毫秒数
     * @return ture=停售, false=开售
     */
    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillis) {
        if (CoreDateUtils.isDuringWorldCup()) {
            // 世界杯期间的停售时间处理
            return isDuringSaleClosedPeriod(forwardTimeMillis, worldCupSaleClosedTimeRegion);
        }

        return isDuringSaleClosedPeriod(forwardTimeMillis, dcSaleClosedTimeRegion);
    }

    /**
     * 是否处于北单停售期
     * @param forwardTimeMillis 提前毫秒数
     * @return ture=停售, false=开售
     */
    public static boolean isDuringSaleClosedPeriod(long forwardTimeMillis, TimeRegion timeRegion) {
        if (forwardTimeMillis == 0) {
            return CoreTimeUtils.isCurrentDuringPeriod(timeRegion);
        }

        // 先判断当前时间是否处于停售期
        boolean isCurrentSaleClosed = isDuringSaleClosedPeriod();

        if (isCurrentSaleClosed) {
            return true;
        }

        // 再判断加上提前量之后是否处于停售期
        Calendar current = Calendar.getInstance();
        current.add(Calendar.MILLISECOND, (int)forwardTimeMillis);

        return CoreTimeUtils.isDuringPeriod(current.getTime(), timeRegion);
    }

    /**
     * 根据开赛时间和官方停售时段来计算官方停售时间
     * @param matchDate 实际开赛时间
     * @return 官方停售时间
     */
    public static Date getOfficialEndSaleTimeByMatchDate(Date matchDate) {
        if (matchDate == null) {
            return null;
        }

        TimeRegion timeRegion = dcSaleClosedTimeRegion;

        if (CoreDateUtils.isDuringWorldCup(matchDate)) {
            // 如果是世界杯期间的比赛
            timeRegion = worldCupSaleClosedTimeRegion;
        }

        // 如果比赛在停售时段, 以停售时段的左边界为停售时间
        if (CoreTimeUtils.isDuringPeriod(matchDate, timeRegion)) {
            // 默认只取第一个时间段处理
            Time time = timeRegion.getRegionList().get(0).getLeft();
            Calendar endSaleCalendar = Calendar.getInstance();
            endSaleCalendar.setTime(matchDate);
            endSaleCalendar.set(Calendar.HOUR_OF_DAY, time.getHour());
            endSaleCalendar.set(Calendar.MINUTE, time.getMinute());
            endSaleCalendar.set(Calendar.SECOND, 0);
            endSaleCalendar.set(Calendar.MILLISECOND, 0);
            return endSaleCalendar.getTime();
        }



        // 否则以开赛时间为停售时间
        return matchDate;
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


}
