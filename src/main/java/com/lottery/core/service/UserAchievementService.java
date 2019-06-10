package com.lottery.core.service;

import java.math.BigDecimal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.UserAchievementDao;
import com.lottery.core.dao.UserAchievementDetailDao;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.UserAchievement;
import com.lottery.core.domain.UserAchievementDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.lottype.Lot;
import com.lottery.lottype.LotManager;
import com.lottery.lottype.PrizeResult;

@Service
public class UserAchievementService {
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private UserAchievementDao userAchievementDao;
	@Autowired
	private UserAchievementDetailDao userAchievementDetailDao;
	@Autowired
	private LotManager lotManager;
	@Autowired
	private LotteryOrderDAO lotteryOrderDAO;
	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;
	@Autowired
	private IdGeneratorDao idGeneratorDao;

	/**
	 * 计算成功的合买战绩
	 * @param caseLot 持久化的caselot对象
	 */
	@Transactional
	public void calculateSuccessCaseLot(LotteryCaseLot caseLot) {
		if (caseLot == null || caseLot.getState() == null || StringUtil.isEmpty(caseLot.getOrderid())) {
			return;
		}
		BigDecimal amt = new BigDecimal(caseLot.getTotalAmt());
		BigDecimal winBigAmt = new BigDecimal(caseLot.getWinBigAmt());
		BigDecimal winPreAmt = new BigDecimal(caseLot.getWinPreAmt());
		if (winPreAmt.compareTo(BigDecimal.ZERO) <= 0) {
			logger.info("奖金小于0，不计算战绩caselotid:" + caseLot.getId());
			return;
		}
		
		UserAchievement userAchievement = findIfNotExistCreate(caseLot.getStarter(), caseLot.getLotteryType());
		userAchievement = userAchievementDao.findWithLock(userAchievement.getId(), true);
		calculateAchievement(amt, winPreAmt, winBigAmt, true, caseLot.getId(), userAchievement);
	}

	/**
	 * 计算未成功的合买战绩
	 * @param caseLot 持久化的caselot对象
	 */
	@Transactional
	public void calculateCancelCaseLot(LotteryCaseLot caseLot, String wincode) {
		if (caseLot == null || caseLot.getState() == null || StringUtil.isEmpty(caseLot.getOrderid())
				|| caseLot.getLotteryType() == null || StringUtil.isEmpty(caseLot.getStarter())) {
			logger.error("计算流单战绩参数错误" + caseLot);
			return;
		}
		BigDecimal amt = new BigDecimal(caseLot.getTotalAmt());
		BigDecimal buyAmt = new BigDecimal(caseLot.getBuyAmtByStarter() + caseLot.getBuyAmtByFollower());
		BigDecimal multiply = buyAmt.divide(amt, 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal(100));
		if (multiply.compareTo(new BigDecimal(50)) < 0) {
			logger.info("认购比例小于50%，不计算战绩caselotid:" + caseLot.getId());
			return;
		}
		Lot lot = lotManager.getLot(caseLot.getLotteryType().toString());
		LotteryOrder order = lotteryOrderDAO.find(caseLot.getOrderid());
		PrizeResult prizeResult = lot.calcuteOrderpirzeamt(order.getContent(), order.getMultiple(), order.getAmt().intValue(), 200, wincode, order.getPhase());
		if (prizeResult == null || prizeResult.getPreTaxAmt().intValue() == 0) {
			logger.info("合买流单caselotid:" + caseLot.getId() + "未中奖,不计算战绩");
			return;
		}
		logger.info("流单caselotid:{}奖金为{}", new Object[] {caseLot.getId(), prizeResult.toString() });
		//caseLot.setWinDetail(prizeResult.getPrizeLevelInfo());
		caseLot.setWinBigAmt(prizeResult.getAfterTaxAmt().longValue());
		caseLot.setWinPreAmt(prizeResult.getPreTaxAmt().longValue());
		lotteryCaseLotDao.merge(caseLot);
		UserAchievement userAchievement = findIfNotExistCreate(caseLot.getStarter(), caseLot.getLotteryType());
		userAchievement = userAchievementDao.findWithLock(userAchievement.getId(), true);
		calculateAchievement(new BigDecimal(caseLot.getTotalAmt()), prizeResult.getPreTaxAmt(), 
				prizeResult.getAfterTaxAmt(), false, caseLot.getId(), userAchievement);
	}

	@Transactional
	public UserAchievement findIfNotExistCreate(String userno, Integer lottype) {
		UserAchievement userAchievement = userAchievementDao.findByUsernoAndLottype(userno, lottype);
		if(userAchievement == null){
			userAchievement = userAchievementDao.createUserachievement(userno, lottype, idGeneratorDao.getUserachievementId());
		}
		return userAchievement;
	}
	
	
	/**
	 * 计算战绩
	 * @param amt 认购金额
	 * @param preTaxAmount 税前奖金
	 * @param afterTaxAmount 税后奖金
	 * @param flag true：有效战绩，false：无效战绩
	 * @param caselotId 合买方案编号
	 */
	@Transactional
	private void calculateAchievement(BigDecimal amt, BigDecimal preTaxAmount, BigDecimal afterTaxAmount, boolean flag,
			String caselotId, UserAchievement userachievement) {
		logger.info("计算战绩方法开始caselotId:{},userno:{},lotteryType:{},amt:{},preTaxAmount:{},afterTaxAmount:{},flag:{}",
				caselotId, userachievement.getUserno(), userachievement.getLotteryType().toString(), amt,
						preTaxAmount, afterTaxAmount, flag);
		Lot lot = lotManager.getLot(userachievement.getLotteryType().toString());
		BigDecimal achievement = lot.computeAchievement(amt, preTaxAmount, afterTaxAmount);
		logger.info(caselotId + "战绩值：" + achievement.longValue());
		if (achievement.compareTo(BigDecimal.ZERO) > 0) {
			if (flag) {
				userachievement.setEffectiveAchievement(userachievement.getEffectiveAchievement().add(achievement));
			} else {
				userachievement.setIneffectiveAchievement(userachievement.getIneffectiveAchievement().add(achievement));
			}
			userAchievementDao.merge(userachievement);
			UserAchievementDetail userachievementDetail = userAchievementDetailDao.createUserAchievementDetail(
					idGeneratorDao.getUserachievementdetailId(), userachievement.getUserno(), userachievement.getLotteryType(), achievement, flag?1:0, caselotId);
			logger.info("增加战绩记录userachievementDetailid:" + userachievementDetail.getId());
		}
	}
}
