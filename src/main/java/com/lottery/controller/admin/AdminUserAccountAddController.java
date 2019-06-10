package com.lottery.controller.admin;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.lottery.common.AdminPage;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.util.JsonUtil;
import com.lottery.common.util.StringUtil;
import com.lottery.controller.admin.dto.ConsumeTotal;
import com.lottery.core.dao.UserAccountAddDAO;
import com.lottery.core.domain.UserInfo;
import com.lottery.core.domain.account.UserAccountAdd;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryCaseLotBuyService;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.UserInfoService;
import com.lottery.core.service.account.UserAccountDetailService;

@Controller
@RequestMapping("/adminUserAccountAdd")
public class AdminUserAccountAddController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	@Autowired
	private UserAccountAddDAO userAccountAddDAO;
	@Autowired
	private LotteryOrderService lotteryOrderService;
	@Autowired
	private UserAccountDetailService userAccountDetailService;
	@Autowired
	private LotteryCaseLotBuyService lotteryCaseLotBuyService;
	
	@RequestMapping("/list")
	public @ResponseBody AdminPage<UserAccountAdd> list(@RequestParam(value = "condition", required = false) String condition,
			@RequestParam(value = "startLine", required = false, defaultValue = "0") int startLine,
			@RequestParam(value = "endLine", required = false, defaultValue = "30") int endLine) {
		AdminPage<UserAccountAdd> page = new AdminPage<UserAccountAdd>(startLine, endLine, " order by createTime desc");
		Map<String, Object> conditionMap = JsonUtil.transferJson2Map(condition);
		userAccountAddDAO.findPageByCondition(conditionMap, page);
		return page;
	}
	
	@Autowired
	private UserInfoService userInfoService;
	@RequestMapping("/consumeTotal")
	public @ResponseBody List<ConsumeTotal> consumeTotal(@RequestParam(value = "userno", required = true) String userno,
			@RequestParam(value = "userName", required = true) String userName,
			@RequestParam(value = "lotteryType", required = false,defaultValue="0") int lotteryType,
			@RequestParam(value = "startTime", required = true) String startTime,
			@RequestParam(value = "endTime", required = true) String endTime) {
		List<ConsumeTotal> list = new ArrayList<ConsumeTotal>();
		try {
			UserInfo user = null;
			if(StringUtil.isNotEmpt(userName)){
				user = userInfoService.getByUserName(userName);
				if(user == null){
					return list;
				}else {
					userno = user.getUserno();
				}
			}
			
			if(lotteryType!=0){
				ConsumeTotal consumeTotal = getConsume(userno, lotteryType, startTime, endTime);
				list.add(consumeTotal);
			}else{
				ConsumeTotal hjc = new ConsumeTotal();
				hjc.setUserno("合计");
				hjc.setLotteryType(0);
				for(LotteryType lottType : LotteryType.get()){
					ConsumeTotal con = getConsume(userno, lottType.value, startTime, endTime);
					if(con.getTotalCount() > 0){
						list.add(con);
						
						hjc.setTotalCount(hjc.getTotalCount()+ con.getTotalCount());
						hjc.setTotalbetAmount(hjc.getTotalbetAmount().add(con.getTotalbetAmount()));
						hjc.setTotalprizeAmount(hjc.getTotalprizeAmount().add(con.getTotalprizeAmount()));
						hjc.setOrderCount(hjc.getOrderCount() + con.getOrderCount());
						hjc.setOrderbetAmount(hjc.getOrderbetAmount().add(con.getOrderbetAmount()));
						hjc.setOrderprizeAmount(hjc.getOrderprizeAmount().add(con.getOrderprizeAmount()));
						hjc.setCaselotCount(hjc.getCaselotCount() + con.getCaselotCount());
						hjc.setCaselotBetAmount(hjc.getCaselotBetAmount().add(con.getCaselotBetAmount()));
						hjc.setCaselotPrizeAmount(hjc.getCaselotPrizeAmount().add(con.getCaselotPrizeAmount()));
						hjc.setHalfbetCount(hjc.getHalfbetCount() +  con.getHalfbetCount());
						hjc.setHalfbetAmount(hjc.getHalfbetAmount().add(con.getHalfbetAmount()));
						hjc.setHalfprizeAmount(hjc.getHalfprizeAmount().add(con.getHalfprizeAmount()));
					}
				}
				
				
				list.add(hjc);
			}
		} catch (Exception e) {
			logger.error("根据期号统计出错", e);
		}
		return list;
	}

	private ConsumeTotal getConsume(String userno, int lotteryType, String startTime, String endTime) {
		ConsumeTotal consumeTotal=new ConsumeTotal();
		consumeTotal.setUserno(userno);
		consumeTotal.setLotteryType(lotteryType);
		
		Integer count = 0;
		BigDecimal betAmount = BigDecimal.ZERO;
		BigDecimal prizeAmount = BigDecimal.ZERO;
		//订单部分成功
		List<LotteryOrder> orderList=lotteryOrderService.getByStatus(userno, lotteryType, startTime, endTime, OrderStatus.HALF_PRINTED.value);
		for(LotteryOrder lotteryOrder:orderList){
			UserAccountDetail userAccountDetail=userAccountDetailService.getByPayIdAndType(lotteryOrder.getId(), AccountType.bet, AccountDetailType.half_deduct);
			if(userAccountDetail != null){
				betAmount = betAmount.add(userAccountDetail.getAmt());
			}
			BigDecimal prize = lotteryOrder.getSmallPosttaxPrize();
			if(prize != null){
				prizeAmount = prizeAmount.add(prize);
			}
			count++;
		}
		consumeTotal.setHalfbetCount(count);
		consumeTotal.setHalfbetAmount(betAmount);
		consumeTotal.setHalfprizeAmount(prizeAmount);
		
		
		//订单成功
		Object[] bet = lotteryOrderService.getOrderUserBet(userno, OrderStatus.PRINTED.value, lotteryType, startTime, endTime);
		if(bet[0] != null){
			consumeTotal.setOrderCount((Long)bet[0]);
		}
		if(bet[1] != null){
			consumeTotal.setOrderbetAmount((BigDecimal)bet[1]);
		}
		if(bet[2] != null){
			consumeTotal.setOrderprizeAmount((BigDecimal)bet[2]);
		}
		
		//合买
		Object[] caseLot = lotteryCaseLotBuyService.getCaselotUserbet(userno, CaseLotBuyState.success.value, lotteryType, startTime, endTime);
		if(caseLot[0] != null){
			consumeTotal.setCaselotCount((Long)caseLot[0]);
		}
		if(caseLot[1] != null){
			consumeTotal.setCaselotBetAmount((BigDecimal)caseLot[1]);
		}
		if(caseLot[2] != null){
			consumeTotal.setCaselotPrizeAmount((BigDecimal)caseLot[2]);
		}
		
		consumeTotal.setTotalCount(consumeTotal.getHalfbetCount() + consumeTotal.getOrderCount() + consumeTotal.getCaselotCount());
		consumeTotal.setTotalbetAmount(consumeTotal.getHalfbetAmount().add(consumeTotal.getOrderbetAmount()).add(consumeTotal.getCaselotBetAmount()));
		consumeTotal.setTotalprizeAmount(consumeTotal.getHalfprizeAmount().add(consumeTotal.getOrderprizeAmount()).add(consumeTotal.getCaselotPrizeAmount()));
		return consumeTotal;
	}
	
}
