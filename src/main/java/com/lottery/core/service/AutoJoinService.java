package com.lottery.core.service;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.caselot.AutoJoinDetailState;
import com.lottery.common.contains.lottery.caselot.AutoJoinState;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyType;
import com.lottery.common.contains.lottery.caselot.CaseLotState;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.dao.AutoJoinDao;
import com.lottery.core.dao.AutoJoinDetailDao;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.dao.UserAccountDAO;
import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.AutoJoinDetail;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.account.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@Service
public class AutoJoinService {
	private Logger logger = LoggerFactory.getLogger(AutoJoinService.class);
	@Autowired
	private AutoJoinDao autoJoinDao;
	@Autowired
	private AutoJoinDetailDao autoJoinDetailDao;
	@Autowired
	private LotteryCaseLotDao caseLotDao;
	@Autowired
	private LotteryCaseLotService caseLotService;
	@Autowired
	private UserAccountDAO userAccountDAO;

	/**
	 * @param autoJoin 自动跟单对象
	 * @param caselotid  合买id
	 */
	@Transactional
	public AutoJoinDetail doAutoJoin(AutoJoin autoJoin, String caselotid) {
		if (autoJoin.getTimes() != null && autoJoin.getTimes() != 0) {
			if (autoJoin.getJoinTimes() > autoJoin.getTimes()) {
				logger.info("超过投注次数,设置为无效跟单autoJoinId:{},userno:{}", autoJoin.getId(), autoJoin.getUserno());
				autoJoin.setStatus(AutoJoinState.canncel.value);
				autoJoin.setUpdateTime(new Date());
				merge(autoJoin);
				return null;
			}
		}
	
		LotteryCaseLot caseLot = caseLotDao.findWithLock(caselotid, true);
		if ((caseLot.getState().intValue() != CaseLotState.processing.value) && (caseLot.getState().intValue() != CaseLotState.alreadyBet.value)) {
			logger.info("自动跟单满员caselotid:{}", caselotid);
			AutoJoinDetail ad = new AutoJoinDetail();
			ad.setStatus(AutoJoinDetailState.caselotFull.state);
			return ad;
		}
		
		BigDecimal joinAmt = autoJoin.getJoinAmt();
		if (joinAmt.compareTo(new BigDecimal(caseLot.getMinAmt())) < 0) {
			String memo = "投注金额" + joinAmt.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP)	+ "元小于最低认购金额" + caseLot.getMinAmt() / 100 + "元";
			return autoJoinDetailDao.createAutoJoinDetail(autoJoin, caselotid, joinAmt, AutoJoinDetailState.lessMinAmt, memo, null, caseLot.getLotteryType());
		}
		
		Long canBetNum = caseLot.getTotalAmt() - caseLot.getBuyAmtByStarter() - caseLot.getBuyAmtByFollower();//可购买金额
		//设置的跟单金额 大于可购买金额
		if (joinAmt.compareTo(new BigDecimal(canBetNum)) > 0) {
			if (autoJoin.getForceJoin() == 0) {
				String memo = "投注金额" + joinAmt.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP) + "元大于可投注金额" + canBetNum / 100 + "元";
				return autoJoinDetailDao.createAutoJoinDetail(autoJoin, caselotid, joinAmt, AutoJoinDetailState.biggerCanBet, memo, null, caseLot.getLotteryType());
			}
			joinAmt = new BigDecimal(canBetNum);
		}

		UserAccount account = userAccountDAO.findWithLock(autoJoin.getUserno(), true);
		if (account.getBalance().compareTo(joinAmt) < 0) { // 判断用户余额减去冻结金额是否小于投注金额
			autoJoin.setFailNum(autoJoin.getFailNum() + 1);
			merge(autoJoin);
			return autoJoinDetailDao.createAutoJoinDetail(autoJoin, caselotid, joinAmt, AutoJoinDetailState.drawamtNotEnough, "账户余额不足", null, caseLot.getLotteryType());
		}	
	
		
		LotteryCaseLotBuy caseLotBuy = caseLotService.caseLotToBuy(caseLot, autoJoin.getUserno(), joinAmt.longValue(), 0, CaseLotBuyType.follower, true);
		autoJoin.setJoinTimes(autoJoin.getJoinTimes() + 1);
		if (autoJoin.getJoinTimes() >= autoJoin.getTimes()) {
			autoJoin.setStatus(AutoJoinState.canncel.value);
			autoJoin.setUpdateTime(new Date());
		}
		autoJoinDao.merge(autoJoin);
		return autoJoinDetailDao.createAutoJoinDetail(autoJoin, caselotid, joinAmt, AutoJoinDetailState.success, "跟单成功", caseLotBuy.getId(), caseLot.getLotteryType());
	}

	@Autowired
	IdGeneratorDao idGeneratorDao;
	@Transactional
	public AutoJoin createAutoJoin(String userno, Integer lotteryType, String starter, Integer times, BigDecimal joinAmt, Integer forceJoin) {
		if (userno.equals(starter)) {
			throw new LotteryException(ErrorCode.autojoin_cannt_join_self, "不能定制自己");
		}
		if (joinAmt == null || joinAmt.compareTo(BigDecimal.ZERO) <= 0) {
			throw new LotteryException(ErrorCode.autojoin_amt_error,"定制金额有误");
		}
		AutoJoin join = new AutoJoin();
		join.setId(idGeneratorDao.getAutoJoinId());
		join.setUserno(userno);
		join.setLotteryType(lotteryType);
		join.setStarter(starter);
		join.setTimes(times == null ? 0 : times);
		join.setJoinTimes(0);
		join.setJoinAmt(joinAmt);
		join.setForceJoin(forceJoin);
		join.setFailNum(0);
		Date date = new Date();
		join.setCreateTime(date);
		join.setUpdateTime(date);
		join.setStatus(AutoJoinState.available.value);
		autoJoinDao.insert(join);
		return join;
	}

	@Transactional
	public void merge(AutoJoin autoJoin) {
		if (autoJoin.getFailNum() > 3) {// 如果失败次数大于3，则该自动方案设置为无效
			autoJoin.setStatus(AutoJoinState.canncel.value);
			autoJoin.setUpdateTime(new Date());
		}
		autoJoinDao.merge(autoJoin);
	}

	@Transactional
	public AutoJoin cancel(Long id, String userno) {
		AutoJoin autoJoin = autoJoinDao.findWithLock(id, true);
		if (autoJoin == null) {
			throw new LotteryException(ErrorCode.paramter_error, "跟单记录不存在");
		}
		if(StringUtil.isNotEmpt(userno)){
			if(!userno.equals(autoJoin.getUserno())){
				throw new LotteryException(ErrorCode.caseLot_not_starter, "发起人才能更新");
			}
		}
		autoJoin.setStatus(AutoJoinState.canncel.value);
		autoJoin.setUpdateTime(new Date());
		autoJoinDao.merge(autoJoin);
		return autoJoin;
	}

	@Transactional
	public AutoJoin updateAutoJoin(Long id, BigDecimal joinAmt, Integer forceJoin, String userno) {
		AutoJoin autoJoin = autoJoinDao.find(id);
		if (autoJoin == null) {
			throw new LotteryException(ErrorCode.paramter_error, "跟单记录不存在");
		}
		if(!autoJoin.getJoinAmt().equals(joinAmt) && joinAmt.compareTo(BigDecimal.ZERO) > 0){
			autoJoin.setJoinAmt(joinAmt);
		}
		if(StringUtil.isNotEmpt(userno)){
			if(!userno.equals(autoJoin.getUserno())){
				throw new LotteryException(ErrorCode.caseLot_not_starter, "发起人才能更新");
			}
		}
		autoJoin.setForceJoin(forceJoin);
		autoJoin.setUpdateTime(new Date());
		autoJoinDao.merge(autoJoin);
		return autoJoin;
	}
	
	@Transactional
	public List<AutoJoin> getByLotteryTypeAndStarter(int lotteryType,String userno){
		return autoJoinDao.findByLotteryTypeAndStarter(lotteryType, userno);
	}

	@Transactional
	public AutoJoin cancelByStarter(Long id, String starter) {
		AutoJoin autoJoin = autoJoinDao.find(id);
		if (autoJoin == null) {
			throw new LotteryException(ErrorCode.paramter_error, "跟单记录不存在");
		}
		if(!starter.equals(autoJoin.getStarter())){
			throw new LotteryException(ErrorCode.caseLot_not_bystarter, ErrorCode.caseLot_not_bystarter.memo);
		}
		autoJoin.setStatus(AutoJoinState.canncelBystarter.value);
		autoJoin.setUpdateTime(new Date());
		autoJoinDao.merge(autoJoin);
		return autoJoin;
	}
}
