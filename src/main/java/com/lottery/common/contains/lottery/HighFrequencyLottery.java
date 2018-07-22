package com.lottery.common.contains.lottery;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * 高频彩种
 *
 *
 */
public class HighFrequencyLottery {

    private static Set<LotteryType> _lotterySet;

    private static List<LotteryType> _lotteryList;
	
	static {
        Set<LotteryType> lotterySet = new LinkedHashSet<LotteryType>();
        lotterySet.add(LotteryType.NXK3);
		lotterySet.add(LotteryType.TJKL10);
		lotterySet.add(LotteryType.HLJSSC);
		lotterySet.add(LotteryType.CQSSC);
		lotterySet.add(LotteryType.AHK3);
		lotterySet.add(LotteryType.JSK3);
		lotterySet.add(LotteryType.GXK3);
		lotterySet.add(LotteryType.HLJKL10);
		lotterySet.add(LotteryType.GXKL10);
		lotterySet.add(LotteryType.TJSSC);
		lotterySet.add(LotteryType.JX_11X5);
		lotterySet.add(LotteryType.SD_11X5);
		lotterySet.add(LotteryType.SD_KLPK3);
		lotterySet.add(LotteryType.XJ_11X5);
		lotterySet.add(LotteryType.GD_11X5);
		lotterySet.add(LotteryType.CQKL10);
		lotterySet.add(LotteryType.GDKL10);
		lotterySet.add(LotteryType.XJSSC);
		lotterySet.add(LotteryType.HUBEI_11X5);
		lotterySet.add(LotteryType.HB_11X5);
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
