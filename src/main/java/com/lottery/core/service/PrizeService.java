package com.lottery.core.service;

import com.lottery.common.PageBean;
import com.lottery.common.contains.*;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorDao;
import com.lottery.core.cache.model.LottypeConfigCacheModel;
import com.lottery.core.dao.*;
import com.lottery.core.dao.merchant.MerchantOrderDAO;
import com.lottery.core.dao.print.PrintTicketDao;
import com.lottery.core.domain.*;
import com.lottery.core.domain.account.UserAccount;
import com.lottery.core.domain.account.UserAccountDetail;
import com.lottery.core.domain.merchant.MerchantOrder;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;

import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.service.topic.TopicMessageSendService;
import com.lottery.draw.IVenderPhaseDrawWorker;
import com.lottery.draw.LotteryDraw;
import com.lottery.lottype.Lot;
import com.lottery.lottype.LotManager;
import com.lottery.lottype.OrderCalResult;
import com.lottery.lottype.PrizeResult;
import com.lottery.lottype.jc.JcResultUtil;
import com.lottery.retrymodel.ApiRetryTaskExecutor;
import com.lottery.retrymodel.prize.GetVenderPhaseDrawResultRetryCallback;
import com.lottery.scheduler.fetcher.sp.domain.MatchSpDomain;
import com.lottery.scheduler.fetcher.sp.impl.JclqMatchSpService;
import com.lottery.scheduler.fetcher.sp.impl.JczqMatchSpService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class PrizeService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());



	@Autowired
	private LotteryPhaseDAO phasedao;
	@Autowired
	private IdGeneratorDao idDao;
	@Autowired
	private LotteryOrderDAO lotteryorderDao;
	@Autowired
	private PrizeErrorLogDAO prizeErrorDao;
	@Autowired
	private TicketDAO ticketDao;
	@Autowired
	private LotManager lotmanager;
	@Autowired
	private UserAccountDetailDAO userAccountDetailDao;
	@Autowired
	private UserAccountDAO userAccountDao;

	@Autowired
	private LottypeConfigCacheModel lottypeconfigCache;
	@Autowired
	private QueueMessageSendService queueMessageSendService;
	@Resource(name = "apiRetryTaskExecutor")
	protected ApiRetryTaskExecutor apiReryTaskExecutor;

	@Autowired
	private TopicMessageSendService topicMessageSendService;

	@Resource(name = "venderPhaseDrawWorkerMap")
	private Map<TerminalType, IVenderPhaseDrawWorker> venderPhaseDrawWorkerMap;


	@Autowired
	private JcGuanyajunRaceDao jcGuanyajunRaceDao;


    @Autowired
	private MerchantOrderDAO merchantOrderDAO;
	@Autowired
	private LotteryChaseBuyDAO lotteryChaseBuyDAO;

	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;

    @Autowired
	private PrintTicketDao printTicketDao;


	@Transactional
	public void savePrize(String lotterytype, String phase, String wincode) throws Exception {
		logger.error("进行保存开奖lotterytype={} phase={} wincode={}", new Object[] { lotterytype, phase, wincode });

		phasedao.saveWincode(Integer.parseInt(lotterytype), phase, wincode);
		logger.error("保存开奖期号成功lotterytype={} phase={}", new Object[] { lotterytype, phase });
	}

	@Transactional
	public void savePrizeInfo(String lotterytype, String phase, String info, String poolAmount, String addPoolAmount, String saleAmount) throws Exception {

		logger.error("进行保存开奖详情lotterytype={} phase={} info={} poolAmount={} addPoolAmount={} saleAmount={}", new Object[] { lotterytype, phase, info, poolAmount, addPoolAmount, saleAmount });

		if (StringUtil.isEmpty(info) || StringUtil.isEmpty(poolAmount) || StringUtil.isEmpty(saleAmount)) {
			logger.error("进行保存开奖详情为空lotterytype={} phase={} info={} saleAmount={} ", new Object[] { lotterytype, phase, info, saleAmount });
			return;
		}

		int i = phasedao.saveWininfo(Integer.parseInt(lotterytype), phase, null, info, poolAmount, addPoolAmount, saleAmount, null);

		if (i == 0) {
			logger.error("进行保存开奖详情期号不存在lotterytype={} phase={}", new Object[] { lotterytype, phase });
			return;
		}

		logger.error("保存开奖详情成功lotterytype={} phase={}", new Object[] { lotterytype, phase });
	}

	@Transactional
	public void zcSavePrize(String lotterytype, String phase, String wincode) throws Exception {
		logger.error("进行保存开奖lotterytype={} phase={} wincode={}", new Object[] { lotterytype, phase, wincode });
		LotteryPhase currentphase = phasedao.getByTypeAndPhase(Integer.parseInt(lotterytype), phase);
		if (currentphase == null) {
			logger.error("进行保存开奖期号不存在lotterytype={} phase={}", new Object[] { lotterytype, phase });
			return;
		}
		if (!(currentphase.getPhaseStatus() == PhaseStatus.close.value || currentphase.getPhaseStatus() == PhaseStatus.prize_ing.value)) {
			logger.error("期状态不为关闭或者开奖中lotterytype={} phase={}", new Object[] { lotterytype, phase });
			return;
		}
		phasedao.saveZcWincode(Integer.parseInt(lotterytype), phase, wincode);
		logger.error("保存开奖成功lotterytype={} phase={} wincode={}", new Object[] { lotterytype, phase, wincode });

	}


	@Transactional
	public boolean updatePhaseWinStatus(int lotterytype, String phase, String wincode) throws Exception {
		if (StringUtil.isEmpty(wincode)) {
			logger.error("进行开奖开奖号码错误lotterytype={} phase={} wincode", new Object[] { lotterytype, phase, wincode });
			throw new LotteryException(ErrorCode.phase_wincode_regex_error, "开奖号码格式错误");
		}
		LotteryPhase currentphase = phasedao.getByTypeAndPhase(lotterytype, phase);
		if (currentphase == null) {
			logger.error("进行开奖期号不存在lotterytype={} phase={}", new Object[] { lotterytype, phase });
			throw new LotteryException(ErrorCode.no_exits, "期不存在");
		}

		if (LotteryType.getZc().contains(LotteryType.getLotteryType(lotterytype))) {
			if (!(currentphase.getPhaseStatus() == PhaseStatus.close.value || currentphase.getPhaseStatus() == PhaseStatus.prize_ing.value)) {
				logger.error("开奖彩种为足彩且状态不是关闭和开奖中,退出开奖lotterytype={} phase={} status={}", new Object[] { lotterytype, phase, currentphase.getPhaseStatus() });
				throw new LotteryException(ErrorCode.phase_status_error, "期状态不正确");
			}
			if (!lotmanager.getLot(String.valueOf(lotterytype)).validatePrizeDetail(currentphase.getPrizeDetail())) {
				logger.error("开奖彩种为足彩,奖级信息格式错误,不能算奖lotterytype={} phase={},prizedetail=", new Object[] { lotterytype, phase,currentphase.getPrizeDetail() });
				throw new LotteryException(ErrorCode.phase_info_error, "奖级信息不正确,prizedetail="+currentphase.getPrizeDetail());
			}
		} else {
			if (!(currentphase.getPhaseStatus() == PhaseStatus.close.value || currentphase.getPhaseStatus() == PhaseStatus.result_set.value)) {
				logger.error("彩期已经开奖lotterytype={} phase={} status={}", new Object[] { lotterytype, phase, currentphase.getPhaseStatus() });
				throw new LotteryException(ErrorCode.phase_already_prize, "期已经开奖");
			}
		}
		phasedao.onPrize(lotterytype, phase, wincode);
		return true;
	}

	



	// 发送要派奖的订单
	public void sendEncashOrder(String orderid, int lotterytype) {

		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("orderid", orderid);

			if (LotteryType.getGaoPin().contains(LotteryType.getLotteryType(lotterytype))) {
				queueMessageSendService.sendMessage(QueueName.encashorderGaopin, headers);
			} else {
				queueMessageSendService.sendMessage(QueueName.encashorderDapan, headers);
			}
		} catch (Exception e) {
			logger.error("发送派奖订单出错orderid={}",orderid, e);
		}
	}
	
	
	private BigDecimal getOneMoney(Ticket ticket) {
		return ticket.getAddition() == YesNoStatus.yes.getValue() ? new BigDecimal(300) : new BigDecimal(200);
	}
	
	
	/**
	 * 计算大奖奖金,同时更新出票商和合买金额
	 * @param orderid
	 */
	@Transactional
	public void prizeWinBigLotteryOrder(String orderid) {
		LotteryOrder order = lotteryorderDao.find(orderid);
		if (order == null) {
			logger.error("order不存在,orderid={}", orderid);
			return;
		}

		if (!(order.getOrderStatus() == OrderStatus.PRINTED.getValue() || order.getOrderStatus() == OrderStatus.HALF_PRINTED.getValue())) {
			logger.error("order状态不是出票成功或部分出票成功,orderid={}", orderid);
			return;
		}
		
		if(!LotteryType.getDaPan().contains(LotteryType.getLotteryType(order.getLotteryType()))) {
			logger.error("不是大盘彩种,不提供手工大奖计算奖金lotterytype={} orderid={}",new Object[]{order.getLotteryType(),orderid});
			return;
		}
		
		LotteryPhase phase = null;
		try {
			phase = phasedao.getByTypeAndPhase(order.getLotteryType(), order.getPhase());
		} catch (Exception e) {
		}
		
		if(phase==null||StringUtil.isEmpty(phase.getPrizeDetail())) {
			logger.error("期或者期的开奖详情信息不存在,lotterytype={} phase={}", new Object[]{order.getLotteryType(),order.getPhase()});
			return;
		}
		
		List<Ticket> tickets = ticketDao.findWinBigTicket(orderid);
		
		Lot lot = lotmanager.getLot(String.valueOf(order.getLotteryType()));
		
		for(Ticket ticket:tickets) {
			PrizeResult prizeResul = lot.caculatePrizeAmt(ticket.getPrizeDetail(), lot.getAwardLevelsByPrizeInfo(order.getLotteryType(), order.getPhase(), phase.getPrizeDetail()), getOneMoney(ticket));
			updateTicketPrize(ticket, prizeResul);
		}
		
		BigDecimal posttaxPrize = ticketDao.getSumPosttaxPrizeByOrderIdAndStatus(orderid, TicketStatus.PRINT_SUCCESS);
		BigDecimal pretaxPrize = ticketDao.getSumPretaxPrizeByOrderIdAndStatus(orderid, TicketStatus.PRINT_SUCCESS);
		
		updateOrderPrize(order, posttaxPrize, pretaxPrize);
	}
	
	
	private void updateTicketPrize(Ticket ticket,PrizeResult prizeResult) {
		Map<String,Object> contentMap = new HashMap<String,Object>();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("id", ticket.getId());
		
		contentMap.put("pretaxPrize", prizeResult.getPreTaxAmt());
		contentMap.put("posttaxPrize", prizeResult.getAfterTaxAmt());
		contentMap.put("totalPrize", prizeResult.getAfterTaxAmt());
		ticketDao.update(contentMap, whereMap);
	}
	
	private void updateOrderPrize(LotteryOrder order,BigDecimal posttaxPrize,BigDecimal pretaxPrize) {
		
		final boolean isBig = order.getOrderResultStatus()==OrderResultStatus.win_big.value;
		order.setSmallPosttaxPrize(posttaxPrize);
		order.setPretaxPrize(pretaxPrize);
		order.setTotalPrize(posttaxPrize);
		if (isBig) {
			order.setBigPosttaxPrize(posttaxPrize);
		} else {
			order.setBigPosttaxPrize(BigDecimal.ZERO);
		}
		order.setDrawTime(new Date());
		lotteryorderDao.updateOrderPrize(order);
		//修改合买，追号，出票商投注中奖状态
        merchantChaseCaselot(order);
	}

	// 算奖过程
	@Transactional
	public OrderCalResult prizeLotteryOrder(int lotterytype, String orderid, String wincode) {

		LottypeConfig config = null;
		try {
			config = lottypeconfigCache.get(LotteryType.getSingleValue(lotterytype));
		} catch (Exception e) {
			logger.error("获取lottypeconfig异常 lotterytype={}", lotterytype);
			return null;
		}

		// LotteryOrder order = lotteryorderDao.findWithLock(orderid,true);
		LotteryOrder order = lotteryorderDao.find(orderid);

		if (order == null) {
			logger.error("order不存在,orderid={}", orderid);
			return null;
		}

		if (!(order.getOrderStatus() == OrderStatus.PRINTED.getValue() || order.getOrderStatus() == OrderStatus.HALF_PRINTED.getValue())) {
			logger.error("order状态不是出票成功或部分出票成功,orderid={}", orderid);
			return null;
		}

		// List<Ticket> tickets = ticketDao.getByorderId(orderid);

		Lot lot = lotmanager.getLot(String.valueOf(lotterytype));

		BigDecimal littlePrizeamt = BigDecimal.ZERO;
		BigDecimal bigPrizeamt = BigDecimal.ZERO;
		BigDecimal preprizeamt = BigDecimal.ZERO;

		List<String> prizeinfos = new ArrayList<String>();

		// 是否是大奖
		boolean isbig = false;
		boolean isBigNullPrize = false;

		int start = 1;
		int max = 100;

		PageBean<Ticket> page = new PageBean<Ticket>(start, max);

		List<Ticket> tickets = ticketDao.getByorderId(orderid, page);

		while (null != tickets && tickets.size() != 0) {
			for (Ticket ticket : tickets) {
				try {
					PrizeResult result = caculateTicket(wincode, ticket);
					if (result == null) {
						continue;
					}
					if (!StringUtil.isEmpty(result.getPrizeLevelInfo())) {
						prizeinfos.add(result.getPrizeLevelInfo());
					}

					if (result.isBig()) {
						isbig = true;
						if (result.getPreTaxAmt().compareTo(BigDecimal.ZERO) == 0) {
							isBigNullPrize = true;
						}
					}
					littlePrizeamt = littlePrizeamt.add(result.getAfterTaxAmt());
					bigPrizeamt = bigPrizeamt.add(result.getAfterTaxAmt());
					preprizeamt = preprizeamt.add(result.getPreTaxAmt());
				} catch (Exception e) {
					logger.error("订单id={} wincode={} 算奖出错",new Object[]{orderid,wincode});
					logger.error("算奖出错", e);
					throw new LotteryException("算奖出错",e);
				}
			}
			start = start + 1;
			page.setPageIndex(start);
			tickets = ticketDao.getByorderId(orderid, page);
		}

		if(isbig==false) {
			if(preprizeamt.compareTo(new BigDecimal(1000000))>=0) {
				isbig = true;
			}
		}
		updateLotteryOrderPrize(order, littlePrizeamt, bigPrizeamt, preprizeamt, isbig, lot.getPrizeInfo(prizeinfos), wincode, isBigNullPrize);



		OrderCalResult calresult = new OrderCalResult();
		calresult.setOrderid(order.getId());
		calresult.setIsbig(isbig);
		calresult.setLotterytype(lotterytype);
		calresult.setPretaxprize(order.getPretaxPrize());
		calresult.setAftertaxprize(order.getTotalPrize());
		calresult.setBettype(order.getBetType());

		if (isbig == false && littlePrizeamt.longValue() > 0 && config != null && config.getAutoencash() == YesNoStatus.yes.value) {
			calresult.setIsencash(true);
		} else {
			calresult.setIsencash(false);
		}
		return calresult;
	}

	// 更新订单奖金状态
	private void updateLotteryOrderPrize(LotteryOrder order, BigDecimal littlePrizeamt, BigDecimal bigPrizeamt, BigDecimal preprizeamt, boolean isbig, String prizeinfos, String wincode, boolean isBigNullPrize) {


		order.setWincode(wincode);
		order.setPrizeDetail(prizeinfos);
		order.setSmallPosttaxPrize(bigPrizeamt);
		order.setPretaxPrize(preprizeamt);
		order.setTotalPrize(bigPrizeamt);

		if (isbig) {
			order.setBigPosttaxPrize(bigPrizeamt);
		} else {
			order.setBigPosttaxPrize(BigDecimal.ZERO);
		}

		if (isBigNullPrize) {
			order.setSmallPosttaxPrize(BigDecimal.ZERO);
			order.setPretaxPrize(BigDecimal.ZERO);
			order.setBigPosttaxPrize(BigDecimal.ZERO);
		}

		if (isbig) {
			order.setOrderResultStatus(OrderResultStatus.win_big.value);
		} else if (littlePrizeamt.longValue() > 0) {
			order.setOrderResultStatus(OrderResultStatus.win_already.value);
		} else {
			order.setOrderResultStatus(OrderResultStatus.not_win.value);
		}
		
		order.setDrawTime(new Date());
		lotteryorderDao.updateOrderPrize(order);
		//修改合买，追号，出票商投注中奖状态
        merchantChaseCaselot(order);


	}
	@Transactional
	public   void merchantChaseCaselot(LotteryOrder lotteryOrder){
		if (lotteryOrder.getBetType()==BetType.bet_merchant.value){
			if (lotteryOrder.getOrderResultStatus()==OrderResultStatus.win_already.value||lotteryOrder.getOrderResultStatus()==OrderResultStatus.win_big.value){
				merchantOrderDAO.updateMerchantOrderResultStatus(lotteryOrder.getId(), OrderResultStatus.win_already.value, lotteryOrder.getSmallPosttaxPrize());
			}else{
				merchantOrderDAO.updateMerchantOrderResultStatus(lotteryOrder.getId(), OrderResultStatus.not_win.value, BigDecimal.ZERO);
			}
		}
		if (lotteryOrder.getBetType()==BetType.chase.value&&lotteryOrder.getChaseId()!=null){
			LotteryChaseBuy lotteryChaseBuy=null;
			try{
				 lotteryChaseBuy=lotteryChaseBuyDAO.getByChaseIdAndOrderId(lotteryOrder.getChaseId(),lotteryOrder.getId());
			}catch (Exception e){

			}
			if (lotteryChaseBuy!=null){
				if((lotteryOrder.getOrderResultStatus()==OrderResultStatus.win_already.value||lotteryOrder.getOrderResultStatus()==OrderResultStatus.win_big.value)&&lotteryOrder.getSmallPosttaxPrize()!=null){
					lotteryChaseBuy.setPrize(lotteryOrder.getSmallPosttaxPrize());
					lotteryChaseBuy.setOrderResultStatus(OrderResultStatus.win_already.value);
				}else{
					lotteryChaseBuy.setPrize(BigDecimal.ZERO);
					lotteryChaseBuy.setOrderResultStatus(OrderResultStatus.not_win.value);
				}
				lotteryChaseBuyDAO.update(lotteryChaseBuy);
			}
		}
		if (lotteryOrder.getBetType()==BetType.hemai.value&&lotteryOrder.getHemaiId()!=null){
            LotteryCaseLot lotteryCaseLot=lotteryCaseLotDao.find(lotteryOrder.getHemaiId());
			if (lotteryCaseLot!=null){
				lotteryCaseLot.setOrderResultStatus(lotteryOrder.getOrderResultStatus());
				if (lotteryOrder.getSmallPosttaxPrize()!=null){
					lotteryCaseLot.setWinBigAmt(lotteryOrder.getSmallPosttaxPrize().longValue());
				}
				if (lotteryOrder.getPretaxPrize()!=null){
					lotteryCaseLot.setWinPreAmt(lotteryOrder.getPretaxPrize().longValue());
				}
				lotteryCaseLotDao.update(lotteryCaseLot);
			}
		}

	}


	// 计算票中奖
	@Transactional
	public PrizeResult caculateTicket(String wincode, Ticket ticket) {
		PrizeResult result = null;
		Lot lot = lotmanager.getLot(String.valueOf(ticket.getLotteryType()));

		int oneAmount = ticket.getAddition().intValue() == YesNoStatus.yes.value ? 300 : 200;
		String prizeinfo = lot.getPrizeLevelInfo(ticket.getContent(), wincode, new BigDecimal(ticket.getMultiple().intValue()), oneAmount);
		if (StringUtil.isEmpty(prizeinfo)) {
			onNotPrize(ticket);
		} else {
			result = lot.caculatePrizeAmt(prizeinfo, lot.getAwardLevels(ticket.getLotteryType(), ticket.getPhase()), ticket.getAddition() == YesNoStatus.yes.getValue() ? new BigDecimal(300) : new BigDecimal(200));
			result.setPrizeLevelInfo(prizeinfo);

			onPrize(ticket, result);
		}
		return result;
	}

	// 更新票状态为未中奖
	private void onNotPrize(Ticket ticket) {
		
		Map<String,Object> contentMap = new HashMap<String,Object>();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		
		contentMap.put("ticketResultStatus", TicketResultStatus.not_win.value);
		contentMap.put("pretaxPrize", BigDecimal.ZERO);
		contentMap.put("posttaxPrize", BigDecimal.ZERO);
		contentMap.put("prizeDetail", StringUtil.nullValue);
		
		whereMap.put("id", ticket.getId());
		
		ticketDao.update(contentMap, whereMap);

       printTicketDao.updatePrizeInfo(ticket.getId(),TicketResultStatus.not_win.value,BigDecimal.ZERO,BigDecimal.ZERO,YesNoStatus.no.getValue());
       


	}

	// 更新票状态为中奖

	private void onPrize(Ticket ticket, PrizeResult result) {
		int ticketResultStatus=TicketResultStatus.win_little.value;
		Map<String,Object> contentMap = new HashMap<String,Object>();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		whereMap.put("id", ticket.getId());
		
		if (result.isBig()) {
			ticketResultStatus=TicketResultStatus.win_big.value;
		}
		contentMap.put("ticketResultStatus",ticketResultStatus );
		contentMap.put("pretaxPrize", result.getPreTaxAmt());
		contentMap.put("posttaxPrize", result.getAfterTaxAmt());
		contentMap.put("totalPrize", result.getAfterTaxAmt());
		contentMap.put("prizeDetail", result.getPrizeLevelInfo());
		ticketDao.update(contentMap, whereMap);
		//更新票机
		printTicketDao.updatePrizeInfo(ticket.getId(),ticketResultStatus,result.getPreTaxAmt(),result.getAfterTaxAmt(),YesNoStatus.no.getValue());
	}

	

	/**
	 * 从各出票商抓取
	 * */
	public LotteryDraw getLotteryDraw(Integer lotteryType, String phase) {


		AtomicBoolean hasFetched = new AtomicBoolean(false);

		List<Future<Object>> futureList = new ArrayList<Future<Object>>();
		List<GetVenderPhaseDrawResultRetryCallback> callbackList=new ArrayList<GetVenderPhaseDrawResultRetryCallback>();
		for (IVenderPhaseDrawWorker work : venderPhaseDrawWorkerMap.values()) {
			GetVenderPhaseDrawResultRetryCallback callback = new GetVenderPhaseDrawResultRetryCallback();

			callback.setLotteryType(lotteryType);
			callback.setPhase(phase);
			callback.setVenderPhaseDrawWorker(work);
			callback.setHasFetched(hasFetched);
			callbackList.add(callback);
			//futureList.add(apiReryTaskExecutor.invokeAsync(callback));
			try {
				LotteryDraw lotteryDraw=(LotteryDraw) apiReryTaskExecutor.invokeAsyncWithResult(callback,0l);
				if(lotteryDraw!=null){
					return lotteryDraw;
				}
			} catch (Exception e) {
				logger.error("抓取开奖出错",e);
			}
		}
		
	
//
//		for (int i=0;i<callbackList.size();i++){
//			Future<Object> future = futureList.get(i);
//			if (!future.isDone()) {
//				// 未执行完的直接忽略
//				continue;
//			}
//			try{
//				Object futureGetObject = future.get();
//				if (futureGetObject == null) {
//					continue;
//				}
//
//				LotteryDraw lotteryDraw = (LotteryDraw)futureGetObject;
//				return lotteryDraw;
//			}catch (Exception e){
//				logger.error("抓取开奖结果出错",e);
//
//			}
//
//		}

		return null;
	}

	/**
	 * 从各出票商抓取
	 * */
	public List<LotteryDraw> getLotteryDrawList(Integer lotteryType, String phase) {
		List<LotteryDraw> list = new ArrayList<LotteryDraw>();
		if (venderPhaseDrawWorkerMap != null) {
			for (IVenderPhaseDrawWorker work : venderPhaseDrawWorkerMap.values()) {
				LotteryDraw draw = work.getPhaseDraw(lotteryType, phase);
				if (draw != null) {
					list.add(draw);
				}
			}
		}
		return list;
	}


/**
 * 重新置为未算奖状态
 * */
    @Transactional
	public String prizeRecovery(LotteryOrder lotteryOrder){
		if (lotteryOrder == null) {
			return null;
		}
		String orderId=lotteryOrder.getId();
		if (lotteryOrder.getOrderResultStatus() == OrderResultStatus.not_open.getValue()) {
			logger.error("订单{}处于未开奖状态，不能进行重新算奖业务", orderId);
			return lotteryOrder.getHemaiId();
		}
		try{
			updateOrderStatus(orderId,lotteryOrder);
			if(lotteryOrder.getBetType()==BetType.hemai.value){
				return lotteryOrder.getHemaiId();
			}
			UserAccountDetail useraccountDetail = userAccountDetailDao.getByPayIdAndType(orderId, AccountType.drawprize, AccountDetailType.add);
			if (useraccountDetail == null) {
				return null;
			}
			String userno = lotteryOrder.getUserno();
			if (!useraccountDetail.getUserno().equals(userno)) {
				logger.error("订单userno={}与交易useraccountdeail中的userno={}不同", new Object[] { userno, useraccountDetail.getUserno() });
				return null;
			}
			UserAccount userAccount = userAccountDao.findWithLock(userno, true);
			if (userAccount == null) {
				return null;
			}
			PrizeErrorLog errorLog = new PrizeErrorLog();
			errorLog.setOrderId(orderId);
			errorLog.setTransactionId(useraccountDetail.getId());
			errorLog.setUserno(userno);
			errorLog.setBetType(lotteryOrder.getBetType());
			errorLog.setLotteryType(lotteryOrder.getLotteryType());
			errorLog.setPhase(lotteryOrder.getPhase());
			errorLog.setAmt(useraccountDetail.getAmt());
			errorLog.setBalance(userAccount.getBalance());
			errorLog.setDrawBalance(userAccount.getDrawbalance());
			BigDecimal amount = useraccountDetail.getAmt();
			userAccount.setTotalBalance(userAccount.getTotalBalance().subtract(amount));
			userAccount.setBalance(userAccount.getBalance().subtract(amount));
			userAccount.setDrawbalance(userAccount.getDrawbalance().subtract(amount));
			userAccount.setTotalprizeamt(userAccount.getTotalprizeamt().subtract(amount));
			if (userAccount.getDrawbalance().compareTo(userAccount.getBalance()) > 0) {
				userAccount.setDrawbalance(userAccount.getBalance());
			}
			userAccountDao.merge(userAccount);
			UserAccountDetail newUad = userAccountDetailDao.getByPayIdAndType(orderId, AccountType.drawprize, AccountDetailType.error_add);
			if (newUad != null) {
				newUad.setFreeze(userAccount.getFreeze());
				newUad.setBalance(userAccount.getBalance());
				newUad.setDrawbalance(userAccount.getDrawbalance());
				newUad.setAmt(amount);
				newUad.setCreatetime(new Date());
				userAccountDetailDao.merge(newUad);
			} else {
				UserAccountDetail newUad1 = new UserAccountDetail(idDao.getUserAccountDetailId(), userno, userAccount.getUserName(), orderId, new Date(), amount, AccountType.drawprize, CommonStatus.success, userAccount.getBalance(), AccountDetailType.error_add, userAccount.getDrawbalance(), userAccount.getFreeze(), "开奖错误,扣回奖金", SignStatus.flat);
				newUad1.setLotteryType(lotteryOrder.getLotteryType());
				newUad1.setPhase(lotteryOrder.getPhase());
				newUad1.setGiveAmount(amount);
				newUad1.setNotDrawAmount(amount);
				newUad1.setDrawAmount(amount);
				newUad1.setAgencyNo(lotteryOrder.getAgencyNo());
				newUad1.setOtherid(useraccountDetail.getOtherid());
				userAccountDetailDao.insert(newUad1);
			}

			userAccountDetailDao.remove(useraccountDetail);
			errorLog.setAfterBalance(userAccount.getBalance());
			errorLog.setAfterDrawBalance(userAccount.getDrawbalance());
			errorLog.setCreateTime(new Date());
			prizeErrorDao.insert(errorLog);
			return lotteryOrder.getHemaiId();
		}catch (Exception e){
			logger.error("算奖还原出错", e);
			throw new LotteryException("算奖还原出错");
		}
	}




	@Transactional
	private void updateOrderStatus(String orderId,LotteryOrder lotteryOrder) {
		Map<String,Object> contentMap = new HashMap<String,Object>();
		Map<String,Object> whereMap = new HashMap<String,Object>();
		
		contentMap.put("orderResultStatus", OrderResultStatus.not_open.getValue());
		contentMap.put("bigPosttaxPrize", BigDecimal.ZERO);
		contentMap.put("pretaxPrize", BigDecimal.ZERO);
		contentMap.put("smallPosttaxPrize", BigDecimal.ZERO);
		contentMap.put("prizeDetail", "");
		contentMap.put("wincode", "");
		contentMap.put("totalPrize", BigDecimal.ZERO);
		contentMap.put("isExchanged", YesNoStatus.no.getValue());
		whereMap.put("id", orderId);
		
		lotteryorderDao.update(contentMap, whereMap);
		ticketDao.updateTicketPrize(orderId);

		if (lotteryOrder.getBetType()==BetType.bet_merchant.value){
			MerchantOrder merchantOrder=merchantOrderDAO.find(orderId);
			if (merchantOrder!=null){
				merchantOrder.setTotalPrize(BigDecimal.ZERO);
				merchantOrder.setIsExchanged(YesNoStatus.no.value);
				merchantOrder.setOrderResultStatus(OrderResultStatus.not_open.value);
				merchantOrderDAO.update(merchantOrder);
			}
		}

	}

	public void sendWinBigOrder(boolean isbig, String id, int lotterytype, BigDecimal prizeamt) {
		if (isbig) {
			sendWinBigPrize(id, lotterytype, prizeamt);
		}
	}

	private void sendWinBigPrize(String id, int lotterytype, BigDecimal prizeamt) {

		try {
			Map<String, Object> headers = new HashMap<String, Object>();
			headers.put("id", id);
			headers.put("lotterytype", lotterytype);
			headers.put("prizeamt", prizeamt.toString());
			topicMessageSendService.sendMessage(TopicName.winBigPrize, headers);
		} catch (Exception e) {
			logger.error("发送大奖票消息失败id=" + id, e);
		}

	}




	/**
	 * 发送中奖结果的jms消息
	 * 
	 * @param orderId
	 *            订单号
	 * @param prizeAmount
	 *            中奖金额，未中奖金额为0
	 * */
	public void sendOrderPrize(String orderId, int betType, BigDecimal prizeAmount, boolean isbig,int lotterytype,String phase,String userno) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			map.put("prizeamt", prizeAmount.toString());
			map.put("lotterytype", lotterytype);
			map.put("phase", phase);
			map.put("userno", userno);
			queueMessageSendService.sendMessage(TopicName.orderprize.value, map);
		} catch (Exception e) {
			logger.error("发送中奖消息出错", e);
		}
	}
	
	@Transactional
	public void simulateTicketOdd(String ticketid) {
		Ticket ticket = ticketDao.find(ticketid);
		if(null==ticket||StringUtil.isNotEmpt(ticket.getPeilv())) {
			return;
		}
		if(LotteryType.getJclqValue().contains(ticket.getLotteryType())
				||LotteryType.getJczqValue().contains(ticket.getLotteryType())) {
			String odd = simulateOdd(ticket.getContent(),LotteryType.getLotteryType(ticket.getLotteryType()));
			ticketDao.updateOdd(ticketid, odd);
		}
	}
	
	

	public String simulateOdd(String content, LotteryType type) {
		

		try {
			String[] betcodes = content.split("\\-")[1].replace("^", "").split("\\|");
			StringBuilder odd = new StringBuilder();
			for (String betcode : betcodes) {
				if (betcode.contains("*")) {
					String currenttypestr = betcode.split("\\*")[1].substring(0, 4);
					LotteryType currenttype = LotteryType.getLotteryType(Integer.parseInt(currenttypestr));
					String oneodd = simulateOneMatchOdd(betcode.replace("*" + currenttypestr, ""), currenttype);
					odd.append(oneodd.substring(0, 11)).append("*" + currenttypestr).append(oneodd.substring(11)).append("|");
				} else {
					odd.append(simulateOneMatchOdd(betcode, type)).append("|");
				}
			}

			if (odd.toString().endsWith("|")) {
				odd = odd.deleteCharAt(odd.length() - 1);
			}
			//logger.error("模拟赔率成功content={} odd={}", new Object[] { content, odd.toString() });
			return odd.toString();
		} catch (Exception e) {
			logger.error("模拟赔率出错", e);
		}

		return "";

	}
	
	@Transactional
	public void simulateGuanjunTicketOdd(String ticketid) {
		Ticket ticket = ticketDao.find(ticketid);
		if(null==ticket||StringUtil.isNotEmpt(ticket.getPeilv())) {
			return;
		}
		if(LotteryType.getGuanyajun().contains(LotteryType.getLotteryType(ticket.getLotteryType()))) {
			String odd = simulateGuanyajunOdd(ticket.getContent(),LotteryType.getLotteryType(ticket.getLotteryType()),ticket.getPhase());
			ticketDao.updateOdd(ticketid, odd);
		}
	}
	
	public String simulateGuanyajunOdd(String content,LotteryType lotteryType,String phase) {
		StringBuilder odd = new StringBuilder();
		for(String code:content.split("\\-")[1].replace("^", "").split(",")) {
			JcGuanYaJunRace race = jcGuanyajunRaceDao.getRace(lotteryType.getValue(), phase, code);
			if(race!=null&&StringUtil.isNotEmpt(race.getOdd())) {
				odd.append(code).append("_").append(race.getOdd()).append(",");
			}
		}
		if(odd.toString().endsWith(",")) {
			odd = odd.deleteCharAt(odd.length()-1);
		}
		return odd.toString();
	}

	@Autowired
	private JclqMatchSpService jclqMatchSpService;
	@Autowired
	private JczqMatchSpService jczqMatchSpService;

	
	private String simulateOneMatchOdd(String code, LotteryType type) {
		StringBuilder odd = new StringBuilder();
		//logger.error("开始模拟场次赔率 code={} type={}", new Object[] { code, type });
		try {
			String matchnum = code.substring(0, 11);
			String[] betcodes = code.substring(11).replace("(", "").replace(")", "").split(",");
			odd.append(matchnum);
			odd.append("(");
			if (LotteryType.getJclq().contains(type)) {
				MatchSpDomain sp = jclqMatchSpService.get(matchnum);
				if (type.equals(LotteryType.JCLQ_RFSF) || type.equals(LotteryType.JCLQ_DXF)) {
					odd.append(type.equals(LotteryType.JCLQ_RFSF) ? sp.getLotteryType().get(String.valueOf(LotteryType.JCLQ_RFSF.value)).get(LotteryConstant.JCLQ_RFSF_HANDICAP) : sp.getLotteryType().get(String.valueOf(LotteryType.JCLQ_DXF.value)).get(LotteryConstant.JCLQ_DXF_PRESETSCORE)).append(":");
				}
				for (String betcode : betcodes) {
					odd.append(betcode).append("_").append(sp.getLotteryType().get(String.valueOf(type.value)).get(JcResultUtil.getLotteryInnerResult(type, betcode))).append(",");
				}
			} else {
				MatchSpDomain sp = jczqMatchSpService.get(matchnum);
				for (String betcode : betcodes) {
					odd.append(betcode).append("_").append(sp.getLotteryType().get(String.valueOf(type.value)).get(JcResultUtil.getLotteryInnerResult(type, betcode))).append(",");
				}
			}
			if (odd.toString().endsWith(",")) {
				odd = odd.deleteCharAt(odd.length() - 1);
			}
			odd.append(")");
			//logger.error("模拟场次赔率成功 code={} type={}", new Object[] { code, type });
		} catch (Exception e) {
			logger.error("模拟赔率出错", e);
			throw new LotteryException("模拟赔率出错");
		}
		return odd.toString();
	}

	/***
	 * 出票商或中心进行彩期同步
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param terminalType
	 *            终端类型
	 * */

	public List<LotteryPhase> asyncPhase(int lotteryType, int terminalType) {
		TerminalType terminal = TerminalType.get(terminalType);
		IVenderPhaseDrawWorker worker = venderPhaseDrawWorkerMap.get(terminal);
		if (worker == null) {
			return null;
		}
		return worker.getPhaseList(lotteryType,null);
	}

}
