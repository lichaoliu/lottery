package com.lottery.core.handler;

import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.caselot.AutoJoinDetailState;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.contains.lottery.caselot.CaseLotState;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.domain.AutoJoin;
import com.lottery.core.domain.AutoJoinDetail;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.*;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.account.UserRebateInstantService;
import org.apache.camel.Header;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class CaseLotHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	@Autowired
	protected LotteryCaseLotBuyService caseLotBuyService;
	@Autowired
	protected UserAccountService userAccountService;
	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;
	@Autowired
	private LotteryCaseLotService lotteryCaseLotService;

	@Autowired
	protected UserRebateInstantService userRebateInstantService;
	@Autowired
	private UserAchievementService userAchievementService;

	@Autowired
	private AutoJoinService autoJoinService;
	

	
	public void onhemaiFailuerUnfreeze(@Header("orderId") String orderId){
		logger.error("收到合买出票失败消息,orderId:{}", orderId);
		LotteryOrder order = lotteryOrderService.get(orderId);
		if (order == null) {
			return;
		}
		String caselotId = order.getHemaiId();
		if(caselotId == null){
			logger.error("订单合买id不存在:{}", caselotId);
			return;
		}
		lotteryCaseLotService.orderFailCancelCaseLot(caselotId);
		List<LotteryCaseLotBuy> caselotBuys = caseLotBuyService.getByCaseLotId(caselotId);
		for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
			try {
				//解冻
				if(caseLotBuy.getFreezeSafeAmt().compareTo(BigDecimal.ZERO) > 0){
					lotteryCaseLotService.unfreeze(caseLotBuy.getFreezeSafeAmt(), caseLotBuy, AccountDetailType.hemai_retUnfreeze);
				}
				
				//返款
				if (caseLotBuy.getNum().compareTo(BigDecimal.ZERO) > 0) {
					userAccountService.hemaiReturnAmt(caseLotBuy.getId(), caseLotBuy.getCaselotId(),
							caseLotBuy.getUserno(), caseLotBuy.getNum(), CaseLotBuyState.orderfailRetract, caseLotBuy.getBuyDrawAmt());
				}
				caseLotBuy.setFlag(CaseLotBuyState.orderfailRetract.value);
				caseLotBuyService.update(caseLotBuy);
			} catch (Exception e) {
				logger.error("出票失败返款失败 caselotbuyid:{}", caseLotBuy.getId());
			}
		}
	}
	
	public void ineffectiveAchievement(@Header("LOTTERYTYPE") Integer lotteryType, @Header("PHASE") String phase, @Header("WINCODE") String wincode) {
		logger.info("开奖统计未成功合买战绩, lotteryType:{}, phase:{}, wincode:{}", new Object[] { lotteryType, phase, wincode });
		List<LotteryCaseLot> list = lotteryCaseLotDao.findByCondition("lotteryType = ? and phase = ? and state = ?", new Object[] { lotteryType, phase, CaseLotState.canceled.value });
		for (LotteryCaseLot caselot : list) {
			logger.info("计算未成功合买caselotid:{}", caselot.getId());
			try {
				userAchievementService.calculateCancelCaseLot(caselot, wincode);
			} catch (Exception e) {
				logger.error("计算战绩异常caselotid:" + caselot.getId(), e);
			}
		}
	}

	/**
	 * 监听合买发单消息
	 */
	public void autoJoinCaselot(String caselotid) {
		logger.info("合买发单自动跟单caselotid:{}", caselotid);
		LotteryCaseLot caseLot = lotteryCaseLotService.get(caselotid);
		if (caseLot == null) {
			logger.info("合买caselotid:{}查询为空", caselotid);
			return;
		}
		if (caseLot.getState() !=CaseLotState.processing.value && caseLot.getState() != CaseLotState.alreadyBet.value) {
			return;
		}
		Long canBetNum = caseLot.getTotalAmt() - caseLot.getBuyAmtByStarter() - caseLot.getBuyAmtByFollower();
		if (canBetNum <= 0) {
			return;
		}
		List<AutoJoin> list = autoJoinService.getByLotteryTypeAndStarter(caseLot.getLotteryType(), caseLot.getStarter());
		logger.info("caselotid:{},定制跟单数量为:{}", new Object[] { caselotid, list.size()});
		for (AutoJoin autoJoin : list) {
			logger.info("开始定制跟单 autoJoinid:{}", autoJoin.getId());
			AutoJoinDetail details = null;
			try {
				details = autoJoinService.doAutoJoin(autoJoin, caselotid);
			}  catch (Exception e) {
				logger.error("跟单异常caselotid", caselotid, e);
			}
			if(details != null){
				if (details.getStatus() == AutoJoinDetailState.caselotFull.state) {
					logger.info("跟单状态为合买已满员，停止跟单caselotid:{}", caselotid);
					break;
				}
			}
		}
	}
}
