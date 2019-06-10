package com.lottery.common.contains.lottery;

import java.util.*;

/**
 * Created by fengqinyun on 16/7/5.
 * 经常出现限号的彩种
 */
public class LimitedLottery {

    private static Set<LotteryType> _lotterySet;

    private static List<LotteryType> _lotteryList;

    static {
        Set<LotteryType> lotterySet = new LinkedHashSet<LotteryType>();
        lotterySet.addAll(HighFrequencyLottery.getList());//所有的地方高频
        lotterySet.addAll(LotteryType.getGuanyajun());//冠亚军只有一家送票,做限号处理

        _lotterySet = Collections.unmodifiableSet(lotterySet);
        _lotteryList = Collections.unmodifiableList(new ArrayList<LotteryType>(lotterySet));
    }

    public static List<LotteryType> getList(){
        return _lotteryList;
    }

    public static boolean contains(LotteryType lotteryType) {
        return _lotterySet.contains(lotteryType);
    }

    public static boolean contains(int lotteryType){
        return _lotterySet.contains(LotteryType.get(lotteryType));
    }
}
