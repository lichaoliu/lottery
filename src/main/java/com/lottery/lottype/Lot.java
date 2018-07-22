package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.BettingLimitNumber;

public interface Lot {
	
	
	LotPlayType getLotPlayType(String playType);

	
	/**
	 * 注码校验
	 * @param betcode
	 * @param amount
	 * @param beishu
	 * @param oneAmount
	 * @return
	 */
	boolean validate(String betcode,BigDecimal amount,BigDecimal beishu,int oneAmount);
	
	
	/**
	 * 注码校验,同时校验是否限号
	 * @param betcode
	 * @param amount
	 * @param beishu
	 * @param oneAmount
	 * @return
	 */
	boolean validateWithLimited(String betcode,BigDecimal amount,BigDecimal beishu,int oneAmount);
	
	
	
	/**
	 * 判断注码是否被限号
	 * @param betcode
	 * @param limitedCodes
	 * @return
	 */
	boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes);
	
	
	/**
	 * 奖金优化注码校验
	 * @param betcode
	 * @param amount
	 * @param beishu
	 * @param oneAmount
	 * @return
	 */
	boolean validatePrizeOptimize(String betcode,BigDecimal amount,BigDecimal beishu,int oneAmount);
	
	/**
	 * 拆票
	 * @param betcode
	 * @param lotmulti
	 * @param amt
	 * @param oneAmount
	 * @return
	 */
	List<SplitedLot> split(String betcode,int lotmulti,long amt,int oneAmount);
	
	/**
	 * 奖金优化拆票
	 * @param betcode
	 * @param lotmulti
	 * @param amt
	 * @param oneAmount
	 * @return
	 */
	List<SplitedLot> splitPrizeOptimize(String betcode,int lotmulti,long amt,int oneAmount);
	
	/**
	 * 校验期
	 * @param batchcode
	 * @return
	 */
	boolean validatePhase(String phase);
	
	/**
	 * 获取中奖奖等信息
	 * @param betcode
	 * @param wincode
	 * @param lotmulti
	 * @return
	 */
	String getPrizeLevelInfo(String betcode,String wincode,BigDecimal lotmulti,int oneAmount);
	
	
	Map<String,Long> getAwardLevels(int lotterytype,String phase);
	
	
	Map<String,Long> getAwardLevelsByPrizeInfo(int lotterytype,String phase,String prizeInfo);
	
	Map<String,String> getPrizeLevelMapper();
	
	
	boolean isBigPrize(String prizeinfo,BigDecimal preprizeamt,BigDecimal afterprizeamt);
	
	PrizeResult caculatePrizeAmt(String prizeinfo,Map<String, Long> prizeLevelInfo, BigDecimal oneMoney);
	
	
	String getPrizeInfo(List<String> prizeinfos);
	
	PlayType getBetcodePlayType(String betccode);
	
	LotteryType getLotteryType();
	

	
	String getAllPlayType(String betcode);
	

	/**
	 * 计算战绩
	 * @param amt 认购金额
	 * @param preTaxAmount 税前奖金
	 * @param afterTaxAmount 税后奖金
	 * @return	战绩值
	 */
	BigDecimal computeAchievement(BigDecimal amt, BigDecimal preTaxAmount, BigDecimal afterTaxAmount);
	
	
	PrizeResult calcuteOrderpirzeamt(String betcode,int lotmulti,long amt,int oneamount,String wincode,String phase) ;
	
	
	public boolean validatePrizeDetail(String prizedetail);


	String getAllMatches(String betcode, int prizeOptimize);
	
	
	List<String> canUnionTypes();
}
