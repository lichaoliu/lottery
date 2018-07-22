package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.List;

import com.lottery.core.domain.BettingLimitNumber;

/**
 * 玩法
 * @author liuhongxing
 *
 */
public interface LotPlayType {

	/**
	 * 计算奖级
	 * @param betcode
	 * @param wincode
	 * @return
	 */
	String caculatePrizeLevel(String betcode,String wincode,int oneAmount);
	
	/**
	 * 注码验证
	 * @param betcode
	 * @param beishu
	 * @param oneAmount
	 * @return
	 */
	long getSingleBetAmount(String betcode, BigDecimal beishu,int oneAmount);
	
	/**
	 * 拆票
	 * @param betcode
	 * @param lotmulti
	 * @param oneAmount
	 * @return
	 */
	List<SplitedLot> splitByType(String betcode,int lotmulti,int oneAmount);
	
	
	boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes);

	
}
