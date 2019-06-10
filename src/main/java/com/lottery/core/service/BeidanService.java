package com.lottery.core.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.draw.prize.impl.RecoveryPrizingLotteryOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lottery.common.PageBean;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.LotteryOrderLineContains;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.PrizeStatus;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.contains.lottery.TicketResultStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.DcRaceDAO;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.TicketDAO;
import com.lottery.core.domain.DcRace;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.lottype.AbstractDanchangLot;
import com.lottery.lottype.DanChangPrizeResult;
import com.lottery.lottype.OrderCalResult;

@Service
public class BeidanService {

	private final Logger logger=LoggerFactory.getLogger(getClass().getName());
	

	
	@Autowired
	private DcRaceDAO dcRaceDao;
	
	@Autowired
	private LotteryOrderDAO orderDao;
	
	@Autowired
	private TicketDAO ticketDao;
	
	@Autowired
	private PrizeService prizeService;
	
	@Autowired
	private Map<String,AbstractDanchangLot> map;
	
	
	
	
	
	
	
	@Transactional
	public void updateDanchangResultToOpen(String phase,String maxid,LotteryType lotteryType) {
		
		DcType type = DcType.normal;
		
		if(lotteryType.equals(LotteryType.DC_SFGG)) {
			type = DcType.sfgg;
		}
		
		int minid = dcRaceDao.getMinCloseAndResultMatchid(phase,type);
		if(minid==0) {
			return;
		}
		
		List<DcRace> races = dcRaceDao.getRaces(phase,minid, Integer.parseInt(maxid),type);
		for(DcRace race:races) {
			if(race.getPrizeStatus()==PrizeStatus.result_set.value) {
				race.setPrizeStatus(PrizeStatus.draw.value);
				dcRaceDao.merge(race);
			}
		}
	}
	
	
	

	
	

	
	

	
	
	
	
	/**
	 * 根据订单查为算奖的票
	 * 某张票不能算奖，继续算其他票，但不能更新订单金额及派奖
	 * 所有票全部都算奖结束，更新订单金额，派奖
	 * @param orderid
	 */
	@Transactional
	public OrderCalResult calcuteBeidanOrder(String orderid) {
		logger.error("北单开始算奖orderid={}",orderid);
		int start = 1;
		int max = 100;
		PageBean<Ticket> page = new PageBean<Ticket>(start, max);
		List<Ticket> tickets = ticketDao.getByorderId(orderid,page);
		List<String> prizeinfos = new ArrayList<String>();
		while (null!=tickets&&tickets.size()!=0) {
			DcType type = DcType.normal;
			for(Ticket ticket:tickets) {
				if(ticket.getTicketResultStatus()!=TicketResultStatus.not_draw.value) {
					logger.error("竞彩算奖orderid={},ticket={}已经算奖,本次退出",orderid,ticket.getId());
					continue;
				}
				AbstractDanchangLot lot = map.get(String.valueOf(ticket.getLotteryType()));
				if(ticket.getLotteryType()==LotteryType.DC_SFGG.getValue()) {
					type = DcType.sfgg;
				}
				DanChangPrizeResult result = lot.calculateDanChangPrizeResult(orderid, ticket.getContent(), ticket.getPhase(), new BigDecimal(ticket.getMultiple()),type);
				if(result==null) {
					logger.error("ticket={}未全部开奖",ticket.getId());
					continue;
				}
				if(StringUtil.isEmpty(result.getPrizedetail())) {
					prizeinfos.add(result.getPrizedetail());
				}
				
				updateTicketPrize(result, ticket);
			}
			start = start + 1;
			page.setPageIndex(start);
			tickets = ticketDao.getByorderId(orderid,page);
		}
		
		OrderCalResult calResult = null;
		
		//查询没有算奖的票的个数
		long notcal = ticketDao.getCountNotCalByOrderId(orderid);

		if(notcal==0) {
			logger.error("北单算奖订单{}未算奖票个数为0,开始更新订单算奖金额状态",orderid);
			LotteryOrder order = orderDao.find(orderid);
			
			AbstractDanchangLot lot = map.get(String.valueOf(order.getLotteryType()));
			BigDecimal preprizeamt = ticketDao.getSumPretaxPrizeByOrderIdAndStatus(orderid, TicketStatus.PRINT_SUCCESS);
			BigDecimal aftertaxPrizeamt = ticketDao.getSumPosttaxPrizeByOrderIdAndStatus(orderid, TicketStatus.PRINT_SUCCESS);
			long winBig = ticketDao.getCountWinBigTicket(orderid, TicketStatus.PRINT_SUCCESS);
			
			DanChangPrizeResult result = new DanChangPrizeResult();
			result.setAfterTaxPrize(aftertaxPrizeamt);
			result.setPrize(preprizeamt);
			result.setBig(winBig>0?true:false);
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
			
			if(result.getAfterTaxPrize().compareTo(BigDecimal.ZERO)==1) {
				//派奖
				if (result.isBig() == false && result.getAfterTaxPrize().compareTo(BigDecimal.ZERO)==1) {
					calResult.setIsencash(true);
				}
			}
		}else{
			logger.error("北单算奖订单{}未算奖票个数为{}",orderid,notcal);
			recoveryPrizingLoteryOrder(orderid);

		}
		return calResult;
		
	}
	
	
	/**
	 * 更新票中奖状态
	 * @param result
	 * @param ticket
	 */
	private void updateTicketPrize(DanChangPrizeResult result,Ticket ticket) {
		if(result.getAfterTaxPrize().compareTo(BigDecimal.ZERO)==0) {
			onNotPrize(ticket);
		}else if(result.getAfterTaxPrize().compareTo(BigDecimal.ZERO)==1) {
			onPrize(ticket, result);
		}
	}
	
	
	/**
	 * 更新票状态为未中奖
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
	 * @param ticket
	 * @param result
	 */
	private void onPrize(Ticket ticket, DanChangPrizeResult result) {
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
	 * @param result
	 * @param order
	 */
	private void onOrderPrize(DanChangPrizeResult result,LotteryOrder order) {
		logger.error("北单开始更新订单中奖金额orderid={},isbig={},prizeamt={}",new Object[]{order.getId(),result.isBig(),result.getAfterTaxPrize()});
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
		} else if (result.getPrize().compareTo(BigDecimal.ZERO)==1) {
			order.setOrderResultStatus(OrderResultStatus.win_already.value);
		} else {
			order.setOrderResultStatus(OrderResultStatus.not_win.value);
		}

		order.setDrawTime(new Date());
		orderDao.merge(order);
		prizeService.merchantChaseCaselot(order);

	}
	
	
	
	public void validateIsDcMatchOutTime(LotteryType type,String phase,String betcode,int prizeOptimize) {
		Set<String> matches = getAllMatches(betcode,prizeOptimize);
		DcType dctype = DcType.normal;
		if(type.getValue()==LotteryType.DC_SFGG.getValue()) {
			dctype = DcType.sfgg;
		}
		for(String match:matches) {
			DcRace race=null;
			try{
				 race = dcRaceDao.getDcRace(match, phase, dctype);
			}catch(Exception e){
				
			}
			if(race==null) {
				throw new LotteryException(ErrorCode.match_not_exist, ErrorCode.match_not_exist.memo);
			}else if(race.getStatus()!=RaceStatus.OPEN.value) {
				throw new LotteryException(ErrorCode.match_not_open, ErrorCode.match_not_open.memo);
			}else if(new Date().after(race.getEndSaleTime())) {
				throw new LotteryException(ErrorCode.match_out_time, ErrorCode.match_out_time.memo);
			}
		}
	}
	
	
	private Set<String> getAllMatches(String betcode,int prizeOptimize) {
		betcode = betcode.replace("#", "|");
		Set<String> matches = new HashSet<String>();
		
		for(String bet:betcode.split("!")) {
			String code = bet.split("\\-")[1].replace("^", "");
			if(prizeOptimize==YesNoStatus.yes.value) {
				code = code.split(LotteryOrderLineContains.PRIZE_OPTIMIZE_SPLITLINE)[0];
			}
			
			for(String onebet:code.split("\\|")) {
				matches.add(onebet.substring(0, 3));
			}
		}
		return matches;
	}
	
	
	
	/**
	 * 获取时间最小的场次id
	 * @param betcode
	 * @param lotterytype
	 * @return
	 */
	public String getMinTeamid(String betcode,LotteryType lotterytype,String phase,int prizeOptimize) {
		String allMatch = map.get(betcode.substring(0, 4)).getAllMatches(betcode,prizeOptimize);
		String[] matches = allMatch.split(",");
		Map<Long,List<String>> matchdates = new  HashMap<Long,List<String>>();
		List<Long> dateList = new ArrayList<Long>();
		
		DcType type = DcType.normal;
		
		if(lotterytype.getValue()==LotteryType.DC_SFGG.getValue()) {
			type = DcType.sfgg;
		}
		
		for(String match:matches) {
			Long endtime=null;
			DcRace dcRace=null;
			try{
				dcRace=dcRaceDao.getDcRace(match, phase, type);
				if(dcRace!=null){
					if(dcRace.getEndSaleTime()!=null){
						endtime=dcRace.getEndSaleTime().getTime();
					}
				}
			}catch(Exception e){
				logger.error("场次号{},期号{},类型:{}出错",new Object[]{match, phase, type});
				logger.error("彩种:{},期号:{},注码:{}获取最小场次出错:{}",new Object[]{lotterytype,phase,betcode,e.getMessage()});
			}
			if(dcRace==null){
				continue;
			}
			if(endtime==null){
				continue;
			}
			dateList.add(endtime);
			if(matchdates.containsKey(endtime)) {
				matchdates.get(endtime).add(match);
			}else {
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
	 * @param betcode
	 * @param lotterytype
	 * @return
	 */
	public String getMaxTeamid(String betcode,LotteryType lotterytype,String phase,int prizeOptimize) {
		String allMatch = map.get(betcode.substring(0, 4)).getAllMatches(betcode,prizeOptimize);
		String[] matches = allMatch.split(",");
		Map<Long,List<String>> matchdates = new  HashMap<Long,List<String>>();
		List<Long> dateList = new ArrayList<Long>();
		
		DcType type = DcType.normal;
		
		if(lotterytype.getValue()==LotteryType.DC_SFGG.getValue()) {
			type = DcType.sfgg;
		}
		
		for(String match:matches) {
			Long endtime=null;
			try{
				DcRace dcRace=dcRaceDao.getDcRace(match, phase, type);
				if(dcRace!=null){
					if(dcRace.getEndSaleTime()!=null){
						endtime=dcRace.getEndSaleTime().getTime();
					}
				}
			}catch(Exception e){
				logger.error("彩种:{},期号:{},注码:{}获取最大场次出错:{}",new Object[]{lotterytype,phase,e.getMessage()});
			}
			
			if(endtime==null){
				continue;
			}
			
			dateList.add(endtime);
			if(matchdates.containsKey(endtime)) {
				matchdates.get(endtime).add(match);
			}else {
				List<String> matchlist = new ArrayList<String>();
				matchlist.add(match);
				matchdates.put(endtime, matchlist);
			}
		}
		
		
		Collections.sort(dateList);
		Collections.sort(matchdates.get(dateList.get(dateList.size()-1)));
		int size = matchdates.get(dateList.get(dateList.size()-1)).size();
		return matchdates.get(dateList.get(dateList.size()-1)).get(size-1);
	}

	@Autowired
	private SharedJedisPoolManager shareJedisPoolManager;
	private void recoveryPrizingLoteryOrder(String orderId){
		RecoveryPrizingLotteryOrder recoveryPrizingLotteryOrder=new RecoveryPrizingLotteryOrder(shareJedisPoolManager);
		recoveryPrizingLotteryOrder.pull(orderId);
	}
	
	public Date getDealTeime(String teamid,LotteryType lotterytype,String phase) {
		
		DcType type = DcType.normal;
		
		if(lotterytype.getValue()==LotteryType.DC_SFGG.getValue()) {
			type = DcType.sfgg;
		}
		try{
			DcRace dcRace=dcRaceDao.getDcRace(teamid, phase, type);
			if(dcRace!=null){
				return dcRace.getEndSaleTime();
			}
		}catch(Exception e){
			logger.error("彩种:{},新期:{},场次:{}不存在 ,错误原因是:{}",new Object[]{teamid,phase,teamid});
		}
		return null;
	}
	
	
	public void testPrize(String flowno,String betcode,String phase,BigDecimal beishu,BigDecimal prize) {
		try {
			AbstractDanchangLot lot = map.get(betcode.substring(0, 4));
			DanChangPrizeResult result = lot.calculateDanChangPrizeResult("", betcode, phase, beishu, DcType.normal);
			if(result.getAfterTaxPrize().intValue()==prize.intValue()) {
				logger.error("flowno={} 计算奖金={} 实际奖金={} 奖金相等",new Object[]{flowno,result.getAfterTaxPrize().intValue(),prize.intValue()});
			}else {
				logger.error("flowno={} 计算奖金={} 实际奖金={} 奖金不相等",new Object[]{flowno,result.getAfterTaxPrize().intValue(),prize.intValue()});
			}
		}catch(Exception e) {
			logger.error("flowno ="+flowno+" 奖金计算异常",e);
		}
		
	}
}
