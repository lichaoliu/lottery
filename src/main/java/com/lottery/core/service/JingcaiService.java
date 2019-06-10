package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.TicketResultStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.JclqRaceDAO;
import com.lottery.core.dao.JczqRaceDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.TicketDAO;
import com.lottery.core.dao.print.PrintTicketDao;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.draw.prize.impl.RecoveryPrizingLotteryOrder;
import com.lottery.lottype.AbstractJingcaiLot;
import com.lottery.lottype.JingcaiPrizeResult;
import com.lottery.lottype.OrderCalResult;

/**
 * 竞彩对阵缓存、赛果审核、对阵状态
 * 
 * @author liu
 * 
 */
@Service
public class JingcaiService {

	private final Logger logger = LoggerFactory.getLogger(getClass());



	@Autowired
	private JczqRaceDao jczqRaceDao;

	@Autowired
	private JclqRaceDAO jclqRaceDao;

	@Autowired
	private LotteryOrderDAO orderDao;

	@Autowired
	private TicketDAO ticketDao;

	@Autowired
	private PrizeService prizeService;

	@Autowired
	private PrintTicketDao printTicketDao;

	@Autowired
	private SharedJedisPoolManager shareJedisPoolManager;
	@Autowired
	private Map<String, AbstractJingcaiLot> map;

	@Transactional
	public JczqRace getJczqRace(String matchid) {

		return jczqRaceDao.find(matchid);
	}

	@Transactional
	public JclqRace getJclqRace(String matchid) {

		return jclqRaceDao.find(matchid);
	}

	@Transactional
	public void updateZqResultToOpen(String maxid) {
		String minid = jczqRaceDao.getMinCloseAndResultMatchid();

		if (StringUtil.isEmpty(minid)) {
			return;
		}

		List<JczqRace> races = jczqRaceDao.getRaces(minid, maxid);

		for (JczqRace race : races) {
			if (race.getPrizeStatus() == PrizeStatus.result_set.value) {
				race.setPrizeStatus(PrizeStatus.draw.value);
				jczqRaceDao.merge(race);
			}
		}
	}

	@Transactional
	public void updateLqResultToOpen(String maxid) {
		String minid = jclqRaceDao.getMinCloseAndResultMatchid();
		if (StringUtil.isEmpty(minid)) {
			return;
		}

		List<JclqRace> races = jclqRaceDao.getRaces(minid, maxid);
		for (JclqRace race : races) {
			if (race.getPrizeStatus() == PrizeStatus.result_set.value) {
				race.setPrizeStatus(PrizeStatus.draw.value);
				jclqRaceDao.merge(race);
			}
		}
	}
	
	
	/**
	 * 校验竞彩赔率
	 * @param type
	 * @param betcode
	 * @param odd
	 * @return
	 */
	public boolean validateJingcaiOdd(int lotteryType,String betcode,String odd) {
		AbstractJingcaiLot lot = map.get(String.valueOf(lotteryType));
		return lot.validateJingcaiOdd(LotteryType.getLotteryType(lotteryType), betcode, odd);
	}
	
	
	@Transactional
	public void calcuteFailJingcaiTicket(String id) {
		Ticket ticket = ticketDao.find(id);
		
		try {
			if(ticket!=null&&ticket.getStatus()==TicketStatus.CANCELLED.value&&LotteryType.getJcValue().contains(ticket.getLotteryType())) {
				AbstractJingcaiLot lot = map.get(String.valueOf(ticket.getLotteryType()));
				JingcaiPrizeResult result = lot.calculateJingcaiPrizeResult(ticket.getOrderId(), ticket.getContent(), ticket.getPeilv(), new BigDecimal(ticket.getMultiple()));
				if (result == null) {
					logger.error("竞彩算奖orderid={} ticket={}未全部开奖", new Object[] { ticket.getOrderId(), ticket.getId() });
					return;
				}
				updateTicketPrize(result, ticket);
			}
		}catch(Exception e) {
			logger.error("竞彩失败票算奖出错id="+id,e);
		}
		
	}
	
	

	/**
	 * 根据订单查为算奖的票 某张票不能算奖，继续算其他票，但不能更新订单金额及派奖 所有票全部都算奖结束，更新订单金额，派奖
	 * 
	 * @param orderid
	 */
	@Transactional
	public OrderCalResult calcuteJingcaiOrder(String orderid) {

		int start = 1;
		int max = 100;
		PageBean<Ticket> page = new PageBean<Ticket>(start, max);
		List<Ticket> tickets = ticketDao.getByorderId(orderid, page);
		List<String> prizeinfos = new ArrayList<String>();
		while (null != tickets && tickets.size() != 0) {
			for (Ticket ticket : tickets) {
				if (ticket.getTicketResultStatus() != TicketResultStatus.not_draw.value) {
					logger.error("竞彩算奖orderid={} ticket={}已经算奖", new Object[] { orderid, ticket.getId() });
					continue;
				}
				AbstractJingcaiLot lot = map.get(String.valueOf(ticket.getLotteryType()));
				JingcaiPrizeResult result = lot.calculateJingcaiPrizeResult(orderid, ticket.getContent(), ticket.getPeilv(), new BigDecimal(ticket.getMultiple()));
				if (result == null) {
					logger.error("竞彩算奖orderid={} ticket={}未全部开奖", new Object[] { orderid, ticket.getId() });
					continue;
				}
				if (StringUtil.isEmpty(result.getPrizedetail())) {
					prizeinfos.add(result.getPrizedetail());
				}
				updateTicketPrize(result, ticket);
			}
			start = start + 1;
			page.setPageIndex(start);
			tickets = ticketDao.getByorderId(orderid, page);
		}

		OrderCalResult calResult = null;
		// 查询没有算奖的票的个数
		long notcal = ticketDao.getCountNotCalByOrderId(orderid);

		if (notcal == 0) {

			//logger.error("竞彩算奖订单{}未算奖票个数为0,开始更新订单算奖金额状态", orderid);
			LotteryOrder order = orderDao.find(orderid);

			AbstractJingcaiLot lot = map.get(String.valueOf(order.getLotteryType()));
			BigDecimal preprizeamt = ticketDao.getSumPretaxPrizeByOrderIdAndStatus(orderid, TicketStatus.PRINT_SUCCESS);
			BigDecimal aftertaxPrizeamt = ticketDao.getSumPosttaxPrizeByOrderIdAndStatus(orderid, TicketStatus.PRINT_SUCCESS);
			long winBig = ticketDao.getCountWinBigTicket(orderid, TicketStatus.PRINT_SUCCESS);

			JingcaiPrizeResult result = new JingcaiPrizeResult();
			result.setAfterTaxPrize(aftertaxPrizeamt);
			result.setPrize(preprizeamt);
			result.setBig(winBig > 0 ? true : false);
			result.setPrizedetail(lot.getPrizeInfo(prizeinfos));
			onOrderPrize(result, order);

			calResult = new OrderCalResult();
			calResult.setOrderid(orderid);
			calResult.setIsbig(result.isBig());
			calResult.setLotterytype(order.getLotteryType());
			calResult.setAftertaxprize(order.getTotalPrize());
			calResult.setPretaxprize(order.getPretaxPrize());
			calResult.setBettype(order.getBetType());
			calResult.setIsencash(false);

			if (result.getAfterTaxPrize().compareTo(BigDecimal.ZERO) == 1) {
				// 派奖
				if (result.isBig() == false && result.getAfterTaxPrize().compareTo(BigDecimal.ZERO) == 1) {
					calResult.setIsencash(true);
				}
			}
		}else{
			logger.error("竞彩算奖订单{}未算奖票个数为{}", new Object[] { orderid, notcal });
			recoveryPrizingLoteryOrder(orderid);
		}
		return calResult;

	}


	private void recoveryPrizingLoteryOrder(String orderId){
		RecoveryPrizingLotteryOrder recoveryPrizingLotteryOrder=new RecoveryPrizingLotteryOrder(shareJedisPoolManager);
		recoveryPrizingLotteryOrder.pull(orderId);
	}

	/**
	 * 更新票中奖状态
	 * 
	 * @param result
	 * @param ticket
	 */
	private void updateTicketPrize(JingcaiPrizeResult result, Ticket ticket) {
		if (result.getAfterTaxPrize().compareTo(BigDecimal.ZERO) == 0) {
			onNotPrize(ticket);
		} else if (result.getAfterTaxPrize().compareTo(BigDecimal.ZERO) == 1) {
			onPrize(ticket, result);
		}
	}

	/**
	 * 更新票状态为未中奖
	 * 
	 * @param ticket
	 */
	private void onNotPrize(Ticket ticket) {
		ticket.setTicketResultStatus(TicketResultStatus.not_win.value);
		ticket.setPretaxPrize(BigDecimal.ZERO);
		ticket.setPosttaxPrize(BigDecimal.ZERO);
		ticket.setPrizeDetail(StringUtil.nullValue);
		ticketDao.merge(ticket);
		printTicketDao.updatePrizeInfo(ticket.getId(),TicketResultStatus.not_win.value,BigDecimal.ZERO,BigDecimal.ZERO, YesNoStatus.no.getValue());
	}

	/**
	 * 更新票状态为中奖
	 * 
	 * @param ticket
	 * @param result
	 */
	private void onPrize(Ticket ticket, JingcaiPrizeResult result) {
		int ticketResultStatus=TicketResultStatus.win_little.value;
		if (result.isBig()) {
			ticketResultStatus=TicketResultStatus.win_big.value;
		}

		ticket.setPretaxPrize(result.getPrize());
		ticket.setPosttaxPrize(result.getAfterTaxPrize());
		ticket.setPrizeDetail(result.getPrizedetail());
		ticket.setTicketResultStatus(ticketResultStatus);
		ticketDao.merge(ticket);
		printTicketDao.updatePrizeInfo(ticket.getId(),ticketResultStatus,result.getPrize(),result.getAfterTaxPrize(),YesNoStatus.no.getValue());
	}

	/**
	 * 更新订单中奖金额和中奖状态
	 * 
	 * @param result
	 * @param order
	 */
	private void onOrderPrize(JingcaiPrizeResult result, LotteryOrder order) {

		order.setSmallPosttaxPrize(result.getAfterTaxPrize());
		order.setTotalPrize(result.getAfterTaxPrize());
		order.setPrizeDetail(result.getPrizedetail());
		if (result.isBig()) {
			order.setBigPosttaxPrize(result.getAfterTaxPrize());
		} else {
			order.setBigPosttaxPrize(BigDecimal.ZERO);
		}
		order.setPretaxPrize(result.getPrize());
		order.setPrizeDetail("");

		if (result.isBig()) {
			order.setOrderResultStatus(OrderResultStatus.win_big.value);
		} else if (result.getPrize().compareTo(BigDecimal.ZERO) == 1) {
			order.setOrderResultStatus(OrderResultStatus.win_already.value);
		} else {
			order.setOrderResultStatus(OrderResultStatus.not_win.value);
		}

		order.setDrawTime(new Date());
		orderDao.merge(order);

		prizeService.merchantChaseCaselot(order);
	}

	/**
	 * 获取时间最小的场次id
	 * 
	 * @param betcode
	 * @param lotterytype
	 * @return
	 */
	public String getMinTeamid(String betcode, LotteryType lotterytype,int prizeOptimize) {
		String allMatch = map.get(betcode.substring(0, 4)).getAllMatches(betcode,prizeOptimize);
		String[] matches = allMatch.split(",");
		Map<Long, List<String>> matchdates = new HashMap<Long, List<String>>();
		List<Long> dateList = new ArrayList<Long>();

		for (String match : matches) {
			Long endtime = 0L;
			if (LotteryType.getJczq().contains(lotterytype)) {
				endtime = jczqRaceDao.find(match).getEndSaleTime().getTime();
			} else if (LotteryType.getJclq().contains(lotterytype)) {
				endtime = jclqRaceDao.find(match).getEndSaleTime().getTime();
			}

			dateList.add(endtime);
			if (matchdates.containsKey(endtime)) {
				matchdates.get(endtime).add(match);
			} else {
				List<String> matchlist = new ArrayList<String>();
				matchlist.add(match);
				matchdates.put(endtime, matchlist);
			}
		}

		Collections.sort(dateList);
		Collections.sort(matchdates.get(dateList.get(0)));
		return matchdates.get(dateList.get(0)).get(0);

	}

	/**
	 * 获取时间最大的场次id
	 * 
	 * @param betcode
	 * @param lotterytype
	 * @return
	 */
	public String getMaxTeamid(String betcode, LotteryType lotterytype,int prizeOptimize) {
		String allMatch = map.get(betcode.substring(0, 4)).getAllMatches(betcode,prizeOptimize);
		String[] matches = allMatch.split(",");
		Map<Long, List<String>> matchdates = new HashMap<Long, List<String>>();
		List<Long> dateList = new ArrayList<Long>();

		for (String match : matches) {
			Long endtime = 0L;
			if (LotteryType.getJczq().contains(lotterytype)) {
				endtime = jczqRaceDao.find(match).getEndSaleTime().getTime();
			} else if (LotteryType.getJclq().contains(lotterytype)) {
				endtime = jclqRaceDao.find(match).getEndSaleTime().getTime();
			}

			dateList.add(endtime);
			if (matchdates.containsKey(endtime)) {
				matchdates.get(endtime).add(match);
			} else {
				List<String> matchlist = new ArrayList<String>();
				matchlist.add(match);
				matchdates.put(endtime, matchlist);
			}
		}

		Collections.sort(dateList);
		Collections.sort(matchdates.get(dateList.get(dateList.size() - 1)));
		int size = matchdates.get(dateList.get(dateList.size() - 1)).size();
		return matchdates.get(dateList.get(dateList.size() - 1)).get(size - 1);
	}

	public Date getDealTeime(String teamid, LotteryType type) {
		Date date = null;
		if (LotteryType.getJclq().contains(type)) {
			date = getJclqRace(teamid).getOfficialDate();
		} else if (LotteryType.getJczq().contains(type)) {
			date = getJczqRace(teamid).getOfficialDate();
		}
		return date;
	}

}
