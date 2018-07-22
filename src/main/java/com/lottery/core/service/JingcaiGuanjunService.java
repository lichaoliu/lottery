package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.TicketResultStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.JcGuanyajunRaceDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.TicketDAO;
import com.lottery.core.domain.JcGuanYaJunRace;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.lottype.AbstractGuanyajunLot;
import com.lottery.lottype.JingcaiPrizeResult;
import com.lottery.lottype.OrderCalResult;

@Service
public class JingcaiGuanjunService {

	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	private LotteryOrderDAO orderDao;

	@Autowired
	private TicketDAO ticketDao;
	
	@Autowired
	private Map<String, AbstractGuanyajunLot> map;
	
	@Autowired
	private PrizeService prizeService;
	
	@Autowired
	private JcGuanyajunRaceDao jcGuanyajunRaceDao;
	
	
	public String getMinTeamid(String betcode) {
		String[] codes = betcode.split("\\-")[1].replace("^", "").split(",");
		Arrays.sort(codes);
		return codes[0];
	}
	
	public String getMaxTeamid(String betcode) {
		String[] codes = betcode.split("\\-")[1].replace("^", "").split(",");
		Arrays.sort(codes);
		return codes[codes.length-1];
	}
	
	
	public boolean validateJingcaiOdd(int lotteryType,String betcode,String odd) {
		AbstractGuanyajunLot lot = map.get(String.valueOf(lotteryType));
		return lot.validateJcGuanyajunOdd( betcode, odd);
	}
	
	
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
					logger.error("冠亚军算奖orderid={} ticket={}已经算奖", new Object[] { orderid, ticket.getId() });
					continue;
				}
				AbstractGuanyajunLot lot = map.get(String.valueOf(ticket.getLotteryType()));
				JingcaiPrizeResult result = lot.calculateGuanyajunPrizeResult(orderid, ticket.getContent(), ticket.getPeilv(), new BigDecimal(ticket.getMultiple()),
						ticket.getLotteryType(),ticket.getPhase());
				if (result == null) {
					logger.error("冠亚军算奖orderid={} ticket={}未全部开奖", new Object[] { orderid, ticket.getId() });
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

			AbstractGuanyajunLot lot = map.get(String.valueOf(order.getLotteryType()));
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
		}
		return calResult;
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
	}

	/**
	 * 更新票状态为中奖
	 * 
	 * @param ticket
	 * @param result
	 */
	private void onPrize(Ticket ticket, JingcaiPrizeResult result) {
		if (result.isBig()) {
			ticket.setTicketResultStatus(TicketResultStatus.win_big.value);
		} else {
			ticket.setTicketResultStatus(TicketResultStatus.win_little.value);
		}

		ticket.setPretaxPrize(result.getPrize());
		ticket.setPosttaxPrize(result.getAfterTaxPrize());
		ticket.setPrizeDetail(result.getPrizedetail());
		ticketDao.merge(ticket);
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
	
	
	
	@Transactional
	public void updateGuanyajunResultToOpen(int lotteryType,String phase,String maxid) {
		String minid = jcGuanyajunRaceDao.getMinCloseAndResultMatchid(lotteryType, phase);
		if (StringUtil.isEmpty(minid)) {
			return;
		}

		List<JcGuanYaJunRace> races = jcGuanyajunRaceDao.getRaces(lotteryType,phase,minid, maxid);
		for (JcGuanYaJunRace race : races) {
			if (race.getPrizeStatus() == PrizeStatus.result_set.value) {
				race.setPrizeStatus(PrizeStatus.draw.value);
				jcGuanyajunRaceDao.merge(race);
			}
		}
	}
}
