package com.lottery.core.handler;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.TimeUnit;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.cache.redis.RedisLock;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.OrderPrizeType;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.TicketResultStatus;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyType;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.LotteryCaseLotBuyService;
import com.lottery.core.service.LotteryCaseLotService;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.PrizeService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.UserAchievementService;
import com.lottery.core.service.account.UserAccountDetailService;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.core.service.merchant.MerchantOrderService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.service.topic.TopicMessageSendService;
import com.lottery.draw.ILotteryDrawTask;
import com.lottery.draw.prize.executor.ILotteryDrawExecutor;
import com.lottery.draw.prize.executor.impl.CommonLotteryDcDrawWorker;
import com.lottery.draw.prize.executor.impl.CommonLotteryDrawWorker;
import com.lottery.draw.prize.executor.impl.CommonLotteryGuanjunDrawWorker;
import com.lottery.draw.prize.executor.impl.CommonLotteryJcDrawWorker;
import com.lottery.lottype.OrderCalResult;
import com.lottery.retrymodel.ApiRetryTaskExecutor;
import com.lottery.retrymodel.prize.LotteryDrawCallback;
import com.lottery.retrymodel.prize.OrderDrawCheckCallback;
import com.lottery.retrymodel.prize.PrizeEncashEndCheckerCallback;
import com.lottery.retrymodel.prize.PrizeEndCheckerCallback;

@Service
public class PrizeHandler {

	private final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private PrizeService prizeService;
    @Autowired
	protected  LotteryChaseBuyService chaseBuyService;

	@Autowired
	protected TicketService ticketService;
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	@Autowired
	protected ILotteryDrawExecutor lotteryDrawExecutor;

	@Autowired
	private CommonLotteryJcDrawWorker commonLotteryJcDrawWorker;

	@Autowired
	private CommonLotteryDrawWorker commonLotteryDrawWorker;
	
	@Autowired
	private CommonLotteryGuanjunDrawWorker commonLotteryGuanjunDrawWorker;
	
	@Autowired
	private UserAccountDetailService userAccountDetailService;

	@Autowired
	private CommonLotteryDcDrawWorker commonLotteryDcDrawWorker;
	@Autowired
	protected UserAccountService userAccountService;
	@Autowired
	protected LotteryCaseLotService caseLotService;
	@Autowired
	protected LotteryCaseLotBuyService caseLotBuyService;
	@Autowired
	protected UserAchievementService userAchievementService;
	@Resource(name = "apiRetryTaskExecutor")
	protected ApiRetryTaskExecutor apiReryTaskExecutor;
	@Autowired
	protected SharedJedisPoolManager shareJedisPoolManager;

    @Autowired
	protected TopicMessageSendService topicMessageSendService;
	
    @Autowired
    protected MerchantOrderService merchantOrderService;
	@Autowired
	protected  LotteryPhaseService lotteryPhaseService;
    @Autowired
	protected QueueMessageSendService queueMessageSendService;


	public void encashProcess(String orderId, boolean isBigEncash) {

		LotteryOrder order = lotteryOrderService.get(orderId);
		if (null == order) {
			logger.error("派奖订单不存在,退出操作orderid={}", orderId);
			return;
		}

		if(order.getSmallPosttaxPrize()==null||order.getSmallPosttaxPrize().intValue()==0){
			logger.error("订单:{},奖金不存在或为0",order);
			return;
		}

		if (!(order.getOrderStatus() == OrderStatus.PRINTED.getValue() || order.getOrderStatus() == OrderStatus.HALF_PRINTED.getValue())) {
			logger.error("order状态不是出票成功或部分出票成功,orderid={}", orderId);
			return;
		}

		if ((!isBigEncash) && order.getOrderResultStatus() == OrderResultStatus.win_big.value) {
			logger.error("订单中大奖且大奖不自动派奖,退出自动派奖orderid={}", orderId);
			return;
		}
		if (order.getIsExchanged() == YesNoStatus.yes.value) {
			logger.error("订单派奖状态为已派奖,退出派奖orderid={}", order.getId());
			return;
		}



		if (order.getBetType() == BetType.hemai.getValue()) {

			RedisLock lock = new RedisLock(this.shareJedisPoolManager, String.format("order_caselot_prize_encase_%s", orderId), 180);

			boolean hasLocked = false;
			try {
				// 等待获取锁30秒
				hasLocked = lock.tryLock(30000l, TimeUnit.MILLISECONDS);
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
			LotteryCaseLot caselot = caseLotService.updateCaseLotWin(order.getHemaiId(), order.getSmallPosttaxPrize(), order.getPretaxPrize());
			if (caselot != null) {
				try {

					lotteryOrderService.updateOrderRewardStatus(YesNoStatus.yes.value, orderId);
					ticketService.updateTicketsEncash(orderId);
					caseLotPrizeProcess(caselot);
				} catch (Exception e) {
					logger.error("caselotid={},lotteryorderid={},error={}",order.getHemaiId(),orderId,e.getMessage());
					logger.error("合买订单派奖出错", e);

				}
				try {
					userAchievementService.calculateSuccessCaseLot(caselot);
				} catch (Exception e) {
					logger.error("合买订单({})计算战绩出错", orderId, e);
				}
			}

			if (hasLocked) {
				// 如果获取到锁, 执行完处理后解锁
				lock.unlock();
			}
			return;
		}





		try {
			UserAccountDetail userAccountDetail=userAccountDetailService.getByPayIdAndType(orderId,AccountType.drawprize,AccountDetailType.add);
			if (userAccountDetail==null){
				userAccountService.drawPrize(order.getUserno(),orderId, orderId, order.getLotteryType(), order.getPhase(), order.getSmallPosttaxPrize(), order.getAgencyNo(), OrderPrizeType.orderPrize.value);

			}
			lotteryOrderService.updateOrderRewardStatus(YesNoStatus.yes.value, orderId);
			ticketService.updateTicketsEncash(orderId);
			if(order.getBetType()==BetType.bet_merchant.value){
				merchantOrderService.updateIsExchanged(orderId, YesNoStatus.yes.value);
			}
		
		} catch (Exception e) {
			logger.error("普通订单({})派奖出错", orderId, e);
		}
	}



	public void encashTicket(String ticketId){
		Ticket ticket=ticketService.getTicket(ticketId);
		if (ticket!=null){
			LotteryOrder lotteryOrder=lotteryOrderService.get(ticket.getOrderId());
			if (lotteryOrder==null){
				return;
			}
			if(lotteryOrder.getBetType()==BetType.bet_merchant.value||lotteryOrder.getBetType()==BetType.hemai.value||!LotteryType.getJcValue().contains(ticket.getLotteryType())){
				logger.error("票:{}不符合算奖条件");
				return;
			}

			try {
				UserAccountDetail userAccountDetail=userAccountDetailService.getByPayIdAndType(ticketId,AccountType.drawprize,AccountDetailType.add);
				if (userAccountDetail==null){
					userAccountService.drawPrize(ticket.getUserno(),ticketId, ticket.getOrderId(), ticket.getLotteryType(), ticket.getPhase(), ticket.getTotalPrize(), lotteryOrder.getAgencyNo(), OrderPrizeType.tikcetPrize.value);

				}


				ticketService.updateExchange(ticketId, YesNoStatus.yes.value);
				Long ticketTotal=ticketService.getByResultStatus(TicketResultStatus.not_draw.value,ticket.getOrderId());
				if (ticketTotal==null||ticketTotal.intValue()==0){
					lotteryOrderService.updateOrderRewardStatus(YesNoStatus.yes.value,ticket.getOrderId());
                    userAccountDetailService.updateOrderPrizeAmount(lotteryOrder.getId(),lotteryOrder.getSmallPosttaxPrize());
				}


			} catch (Exception e) {
				logger.error("票({})派奖出错", ticket, e);
			}

		}
	}

	/***
	 * 合买奖金，佣金计算
	 * */
	protected void caseLotPrizeProcess(LotteryCaseLot caselot) {
		String caselotId = caselot.getId();
		BigDecimal dispatchMoneyForStarter = new BigDecimal(caselot.getWinBigAmt()).multiply(new BigDecimal(caselot.getCommisionRatio())).divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
		// 分配的奖金
		BigDecimal dispatchable = null;
		if (caselot.getWinBigAmt() > caselot.getTotalAmt()) {// 中奖金额>方案购买金额
			if ((caselot.getWinBigAmt() - caselot.getTotalAmt()) >= dispatchMoneyForStarter.longValue()) {
				dispatchable = new BigDecimal(caselot.getWinBigAmt()).multiply(new BigDecimal(100).add(new BigDecimal(caselot.getCommisionRatio()).negate())).divide(new BigDecimal(100), 0, BigDecimal.ROUND_HALF_UP);
			} else {
				dispatchMoneyForStarter = new BigDecimal(caselot.getWinBigAmt() - caselot.getTotalAmt());
				dispatchable = new BigDecimal(caselot.getTotalAmt());
			}
		} else {
			dispatchable = new BigDecimal(caselot.getWinBigAmt());
			dispatchMoneyForStarter = BigDecimal.ZERO;
		}
		// 按参与的比例派发奖金
		List<LotteryCaseLotBuy> caselotBuys = caseLotBuyService.findCaseLotBuysByCaselotIdAndState(caselotId, CaseLotBuyState.success.value());
		for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
			String caselotbuyid = caseLotBuy.getId();
			String userno = caseLotBuy.getUserno();
			if (caseLotBuy.getIsExchanged() == YesNoStatus.yes.value||caseLotBuy.getFlag()!=CaseLotBuyState.success.value) {
				// 如果该次购买已经派奖，则跳过
				continue;
			}
			BigDecimal moneyToDispatch = BigDecimal.ZERO;
			if (caseLotBuy.getNum() != null) {
				moneyToDispatch = dispatchable.multiply(caseLotBuy.getNum()).divide(new BigDecimal(caselot.getTotalAmt()), 0, BigDecimal.ROUND_HALF_UP);
			}
			if (moneyToDispatch.compareTo(BigDecimal.ZERO) > 0) {
				userAccountService.hemaiEncash(userno, caselotbuyid, caselotId, AccountType.drawprize, AccountDetailType.add, moneyToDispatch, moneyToDispatch, "合买返奖", caseLotBuy.getLotteryType(), caseLotBuy.getPhase());
				caseLotBuy.setIsExchanged(YesNoStatus.yes.value);
				caseLotBuy.setPrizeAmt(moneyToDispatch);
				caseLotBuyService.update(caseLotBuy);
			}
		}
		// 派发合买佣金,逻辑同上
		if (dispatchMoneyForStarter.compareTo(BigDecimal.ZERO) > 0) {
			List<LotteryCaseLotBuy> starterCaseLotBuyList = caseLotBuyService.findCaseLotBuysByCaselotIdAndBuyType(caselotId, CaseLotBuyType.starter.value);
			if (starterCaseLotBuyList != null && starterCaseLotBuyList.size() > 0) {
				LotteryCaseLotBuy startCaseLotBuy = starterCaseLotBuyList.get(0);
				String caselotbuyid = startCaseLotBuy.getId();
				if (startCaseLotBuy.getCommisionPrizeAmt().compareTo(BigDecimal.ZERO) > 0) {
					logger.info("合买佣金已派发caseLotBuyId:" + startCaseLotBuy.getId());
				} else {
					userAccountService.hemaiEncash(startCaseLotBuy.getUserno(), caselotbuyid, caselotId, AccountType.hemai, AccountDetailType.hemai_yongjin, dispatchMoneyForStarter, dispatchMoneyForStarter, "合买佣金", startCaseLotBuy.getLotteryType(), startCaseLotBuy.getPhase());
					startCaseLotBuy.setCommisionPrizeAmt(dispatchMoneyForStarter);
					caseLotBuyService.update(startCaseLotBuy);

				}
			}
		}
	}

	/**
	 * 算奖入口
	 * */

	public void drawExcetor(int lotteryType, String phase, String wincode, Long lastMatchNum) {

		if (LotteryType.getJclqValue().contains(lotteryType)) {
			for (Integer lottype : LotteryType.getJclqValue()) {
				draw(lottype,phase,wincode,lastMatchNum);
			}
		} else if (LotteryType.getJczqValue().contains(lotteryType)) {
			for (Integer lottype : LotteryType.getJczqValue()) {
				draw(lottype,phase,wincode,lastMatchNum);
			}
		} else if (LotteryType.getDcValue().contains(lotteryType)) {
			if(lotteryType==LotteryType.DC_SFGG.value) {
				draw(lotteryType,phase,wincode,lastMatchNum);
			}else {
				for (Integer lottype : LotteryType.getDcValue()) {
					if(lottype!=LotteryType.DC_SFGG.value) {
						draw(lottype,phase,wincode,lastMatchNum);
					}
				}
			}
			
		} else {
			draw(lotteryType,phase,wincode,lastMatchNum);
		}


	}


	public void draw(int lotterytype, String phase, String wincode, Long lastMatchNum) {

		LotteryDrawCallback lotteryDrawCallback = new LotteryDrawCallback();
		lotteryDrawCallback.setLotteryDrawExecutor(lotteryDrawExecutor);
		lotteryDrawCallback.setWincode(wincode);
		lotteryDrawCallback.setPhase(phase);
		lotteryDrawCallback.setLastMatchNum(lastMatchNum);
		lotteryDrawCallback.setName("彩种:" + lotterytype + "算奖线程");
		lotteryDrawCallback.setRetry(0);
		lotteryDrawCallback.setLotteryType(lotterytype);
		apiReryTaskExecutor.invokeAsync(lotteryDrawCallback);

	}

	/**
	 * 开奖操作
	 * */
	public void prizeOpen(int lotterytype, String phase, String wincode)throws Exception{
		this.drawExcetor(lotterytype, phase, wincode, null);
	}
	/**
	 * 检查算奖
	 * */
	public boolean prizeEndCheckReturn(int lotteryType, String phase) throws Exception {
		PrizeEndCheckerCallback prizeendCallback=prizeendCallback(lotteryType,phase);
	return 	(Boolean)apiReryTaskExecutor.invokeAsyncWithResult(prizeendCallback, 0);
	}


	private PrizeEndCheckerCallback prizeendCallback(int lotteryType,String phase){
		PrizeEndCheckerCallback prizeendCallback = new PrizeEndCheckerCallback();
		prizeendCallback.setLotteryPhaseService(lotteryPhaseService);
		prizeendCallback.setLotterytype(lotteryType);
		prizeendCallback.setPhase(phase);
		prizeendCallback.setLotteryOrderService(lotteryOrderService);
		prizeendCallback.setTopicMessageSendService(topicMessageSendService);
		prizeendCallback.setName("检查算奖是否完成");
		prizeendCallback.setRetry(1);
		return prizeendCallback;
	}

	/**
	 * 检查算奖
	 * */
	public void prizeEndCheckNotReturn(int lotteryType, String phase) throws Exception {
		PrizeEndCheckerCallback prizeendCallback=prizeendCallback(lotteryType,phase);
		apiReryTaskExecutor.invokeAsync(prizeendCallback);
	}
    /**
	 * 检查派奖
	 * */
	public  void prizeEncashEndCheck(int lotteryType,String phase){
		PrizeEncashEndCheckerCallback prizeEncashCheckerCallback = new PrizeEncashEndCheckerCallback();
		prizeEncashCheckerCallback.setLotteryPhaseService(lotteryPhaseService);
		prizeEncashCheckerCallback.setLotterytype(lotteryType);
		prizeEncashCheckerCallback.setPhase(phase);
		prizeEncashCheckerCallback.setTopicMessageSendService(topicMessageSendService);
		prizeEncashCheckerCallback.setLotteryOrderService(lotteryOrderService);
		prizeEncashCheckerCallback.setName("检查派奖是否完成");
		prizeEncashCheckerCallback.setRetry(1);
		apiReryTaskExecutor.invokeAsync(prizeEncashCheckerCallback);
	}
    /**
	 * 订单开奖检查
	 * */
	public  void orderDrawCheck(ILotteryDrawTask lotteryDrawTask)throws Exception{
		OrderDrawCheckCallback drawCheckCallback=new OrderDrawCheckCallback();
		drawCheckCallback.setLotteryDrawTask(lotteryDrawTask);
		drawCheckCallback.setLotteryOrderService(lotteryOrderService);
		drawCheckCallback.setQueueMessageSendService(queueMessageSendService);
		drawCheckCallback.setName("检查订单开奖是否完成");
		drawCheckCallback.setRetry(1);
		apiReryTaskExecutor.invokeAsync(drawCheckCallback);
	}



	public  void prizeCallbak(int lotteryType,String phase)throws Exception{
       boolean flag=prizeEndCheckReturn(lotteryType,phase);
		if (flag){
			prizeEncashEndCheck(lotteryType, phase);
		}
	}

	public void drawWork(String orderid,String wincode) throws Exception {


		LotteryOrder order = lotteryOrderService.get(orderid);
		if (order == null) {
			logger.error("订单:{}不存在",orderid);
			return;
		}

		if(order.getOrderResultStatus()!=OrderResultStatus.prizing.value){
			logger.error("订单:{},不是开奖中,不能进行算奖",orderid);
		}
//		if (order.getIsExchanged()==YesNoStatus.yes.value){
//			return;
//		}
		RedisLock lock = new RedisLock(this.shareJedisPoolManager, String.format("order_draw_worker_%s", order), 180);

		boolean hasLocked = false;
		try {
			// 等待获取锁30秒
			hasLocked = lock.tryLock(30000l, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}

		try{
			OrderCalResult calResult = null;

			if (LotteryType.getJclq().contains(LotteryType.getLotteryType(order.getLotteryType())) || LotteryType.getJczq().contains(LotteryType.getLotteryType(order.getLotteryType()))) {
				calResult = commonLotteryJcDrawWorker.draw(order, wincode);
			} else if (LotteryType.getDc().contains(LotteryType.getLotteryType(order.getLotteryType()))) {
				calResult = commonLotteryDcDrawWorker.draw(order, wincode);
			} else if(LotteryType.getGuanyajun().contains(LotteryType.getLotteryType(order.getLotteryType()))) {
				calResult = commonLotteryGuanjunDrawWorker.draw(order, wincode);
			} else {
				calResult = commonLotteryDrawWorker.draw(order, wincode);
			}  

			if (calResult == null) {
				return;
			}

			// 发送大奖消息
			prizeService.sendWinBigOrder(calResult.isIsbig(), calResult.getOrderid(), calResult.getLotterytype(), calResult.getPretaxprize());

			// 发送订单奖金
			prizeService.sendOrderPrize(calResult.getOrderid(), calResult.getBettype(), calResult.getAftertaxprize(), calResult.isIsbig(),order.getLotteryType(),order.getPhase(),order.getUserno());
			if (calResult.isIsencash()) {
				prizeService.sendEncashOrder(order.getId(), calResult.getLotterytype());
			}

		}catch (Exception e){

			logger.error("算奖出错:{}",orderid);
			lotteryOrderService.updateOrderResultStatus(orderid,OrderResultStatus.not_open.value,null);//将票开奖状态改为未开奖
			logger.error(e.getMessage(),e);
		}finally {
			if (hasLocked){
				lock.unlock();
			}
		}



	}

}
