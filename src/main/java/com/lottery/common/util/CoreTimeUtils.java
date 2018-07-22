package com.lottery.common.util;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.Time;
import com.lottery.common.TimeRegion;

public class CoreTimeUtils {
	private static final Logger logger = LoggerFactory.getLogger(CoreTimeUtils.class.getName());
	
	/**
	 * 默认判断：大于等于左边界，小于右边界
	 * @param date
	 * @param timeRegion
	 * @return
	 */
	public static boolean isDuringPeriod(Date date, TimeRegion timeRegion) {
		return isDuringPeriod(date, timeRegion, 0, 0);
	}

    /**
     * 判断指定时间是否在时间段之内
     * @param date 时间
     * @param timeRegion 时间范围
     * @param forwardTimeMillisLeft 时间范围左边界提前量
     * @param forwardTimeMillisRight 时间范围右边界提前量
     * @return 指定时间是否在时间段之内
     */
    public static boolean isDuringPeriod(Date date, TimeRegion timeRegion, long forwardTimeMillisLeft, long forwardTimeMillisRight) {
        if (timeRegion == null) {
            logger.error("时间范围为空，不做判断");
            return false;
        }
        List<PairValue<Time, Time>> regionList = timeRegion.getRegionList();
        if (regionList == null || regionList.isEmpty()) {
            logger.error("时间段为空，不做判断");
            return false;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        Calendar regionLeftCalendar = Calendar.getInstance();
        regionLeftCalendar.setTime(date);

        Calendar regionRightCalendar = Calendar.getInstance();
        regionRightCalendar.setTime(date);

        for (PairValue<Time, Time> pairValue : regionList) {
            Time timeLeft = pairValue.getLeft();
            Time timeRight = pairValue.getRight();
            if (timeLeft == null || timeRight == null) {
                logger.error("不是有效的范围，跳过");
                continue;
            }
            // 设置左右边界
            regionLeftCalendar.set(Calendar.HOUR_OF_DAY, timeLeft.getHour());
            regionLeftCalendar.set(Calendar.MINUTE, timeLeft.getMinute());
            regionLeftCalendar.set(Calendar.SECOND, timeLeft.getSecond());
            regionLeftCalendar.set(Calendar.MILLISECOND, timeLeft.getMillisecond());

            // 设置左边界提前量
            regionLeftCalendar.add(Calendar.MILLISECOND, -1 * (int)forwardTimeMillisLeft);

            regionRightCalendar.set(Calendar.HOUR_OF_DAY, timeRight.getHour());
            regionRightCalendar.set(Calendar.MINUTE, timeRight.getMinute());
            regionRightCalendar.set(Calendar.SECOND, timeRight.getSecond());
            regionRightCalendar.set(Calendar.MILLISECOND, timeRight.getMillisecond());

            // 设置右边界提前量
            regionRightCalendar.add(Calendar.MILLISECOND, -1 * (int)forwardTimeMillisRight);

            // 大于等于左边界，小于右边界
            if (calendar.getTimeInMillis() >= regionLeftCalendar.getTimeInMillis() && calendar.getTimeInMillis() < regionRightCalendar.getTimeInMillis()) {
                return true;
            }
        }
        return false;
    }

    public static boolean isCurrentDuringPeriod(TimeRegion timeRegion, long forwardTimeMillisLeft, long forwardTimeMillisRight) {
        return isDuringPeriod(new Date(), timeRegion, forwardTimeMillisLeft, forwardTimeMillisRight);
    }

	public static boolean isCurrentDuringPeriod(TimeRegion timeRegion) {
		return isDuringPeriod(new Date(), timeRegion);
	}
	
	   /**
     * 碎片化切分一段时间
     * @param beginTime 开始时间
     * @param endTime 结束时间
     * @param fragmentUnitMinutes 切分的碎片单位, 单位: 分钟
     * @param pieceOfUnit 将一个碎片单位切分成多少份
     * @return
     */
    public static List<PairValue<Date,Date>> fragmentTime(Date beginTime, Date endTime, int fragmentUnitMinutes, int pieceOfUnit) {
        List<PairValue<Date, Date>> fragmentList = new ArrayList<PairValue<Date, Date>>();

        Date fragmentBeginTime =  beginTime;
        while (true) {
            Calendar fragmentBeginCalendar = Calendar.getInstance();
            fragmentBeginCalendar.setTime(fragmentBeginTime);

            Calendar fragmentEndCalendar = Calendar.getInstance();
            fragmentEndCalendar.setTime(fragmentBeginTime);
            fragmentEndCalendar.add(Calendar.MINUTE, fragmentUnitMinutes);
            if (fragmentEndCalendar.getTime().after(endTime)) {
                fragmentEndCalendar.setTime(endTime);
            }

            int offsetSeconds = (int)Math.ceil(((double)(fragmentEndCalendar.getTimeInMillis() - fragmentBeginCalendar.getTimeInMillis()) / 1000 / pieceOfUnit));

            Date fragmentPieceBeginTime = fragmentBeginCalendar.getTime();
            while (true) {
                Calendar fragmentPieceBeginCalendar = Calendar.getInstance();
                fragmentPieceBeginCalendar.setTime(fragmentPieceBeginTime);

                Calendar fragmentPieceEndCalendar = Calendar.getInstance();
                fragmentPieceEndCalendar.setTime(fragmentPieceBeginTime);
                fragmentPieceEndCalendar.add(Calendar.SECOND, offsetSeconds);
                if (fragmentPieceEndCalendar.getTime().after(fragmentEndCalendar.getTime())) {
                    fragmentPieceEndCalendar.setTime(fragmentEndCalendar.getTime());
                }

                fragmentList.add(new PairValue<Date, Date>(fragmentPieceBeginCalendar.getTime(), fragmentPieceEndCalendar.getTime()));

                fragmentPieceEndCalendar.add(Calendar.SECOND, 1);
                if (fragmentPieceEndCalendar.getTime().after(fragmentEndCalendar.getTime())) {
                    break;
                }
                fragmentPieceBeginTime = fragmentPieceEndCalendar.getTime();
            }

            // 结束时间加1秒作为下次循环的开始时间
            fragmentEndCalendar.add(Calendar.SECOND, 1);
            if (fragmentEndCalendar.getTime().after(endTime)) {
                break;
            }

            fragmentBeginTime = fragmentEndCalendar.getTime();
        }

        return fragmentList;
    }
}
