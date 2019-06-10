package com.lottery.core.service;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.BetType;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.OrderResultStatus;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.util.BetweenDate;
import com.lottery.common.util.CoreDateUtils;
import com.lottery.common.util.SpecialDateUtils;
import com.lottery.controller.admin.dto.TicketMonitor;
import com.lottery.core.dao.LotteryCaseLotDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.merchant.MerchantOrderDAO;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.ticket.LotteryOrder;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.*;

@Service
public class  LotteryOrderService {
	
	@Autowired
	private LotteryOrderDAO lotteryOrderDao;


	@Autowired
	private LotteryCaseLotDao lotteryCaseLotDao;

    @Autowired
	private MerchantOrderDAO merchantOrderDAO;


	@Transactional
	public LotteryOrder get(String orderId) {
		return lotteryOrderDao.find(orderId);

	}

	@Transactional
	public LotteryOrder getWithLock(String orderId) {
		return lotteryOrderDao.findWithLock(orderId, true);

	}

	
	@Transactional
	public boolean update(LotteryOrder lotteryOrder) {
		lotteryOrderDao.merge(lotteryOrder);
		return true;
	}

	@Transactional
	public List<LotteryOrder> get(String userno, String startTime, String endTime, PageBean<LotteryOrder> page, String type, String lotteryType) {

		List<Integer> orderStatusList = new ArrayList<Integer>();
		List<Integer> orderResultStatus = new ArrayList<Integer>();
		if (Integer.parseInt(type) == 1) { // 未中奖
			orderStatusList.add(OrderStatus.PRINTED.getValue());
			orderStatusList.add(OrderStatus.HALF_PRINTED.getValue());
			orderResultStatus.add(OrderResultStatus.not_win.getValue());
		} else if (Integer.parseInt(type) == 2) { // 已中奖
			orderStatusList.add(OrderStatus.PRINTED.getValue());
			orderStatusList.add(OrderStatus.HALF_PRINTED.getValue());
			orderResultStatus.add(OrderResultStatus.win_big.getValue());
			orderResultStatus.add(OrderResultStatus.win_already.getValue());
		} else if (Integer.parseInt(type) == 3) { // 未开奖
			orderStatusList.add(OrderStatus.PRINT_WAITING.getValue());
			orderStatusList.add(OrderStatus.PRINTING.getValue());
			orderStatusList.add(OrderStatus.PRINTED.getValue());
			orderStatusList.add(OrderStatus.NOT_SPLIT.getValue());
			orderResultStatus.add(OrderResultStatus.not_open.getValue());
		}
		return lotteryOrderDao.getOrder(userno, startTime, endTime, page, type, lotteryType, orderStatusList, orderResultStatus);

	}
	
	
	
	@Transactional
	public List<LotteryOrder> get(String userno, String startTime, String endTime, PageBean<LotteryOrder> page, String type, List<Integer> lotteryType) {

		List<Integer> orderStatusList = new ArrayList<Integer>();
		List<Integer> orderResultStatus = new ArrayList<Integer>();
		if (Integer.parseInt(type) == 1) { // 未中奖
			orderStatusList.add(OrderStatus.PRINTED.getValue());
			orderStatusList.add(OrderStatus.HALF_PRINTED.getValue());
			orderResultStatus.add(OrderResultStatus.not_win.getValue());
		} else if (Integer.parseInt(type) == 2) { // 已中奖
			orderStatusList.add(OrderStatus.PRINTED.getValue());
			orderStatusList.add(OrderStatus.HALF_PRINTED.getValue());
			orderResultStatus.add(OrderResultStatus.win_big.getValue());
			orderResultStatus.add(OrderResultStatus.win_already.getValue());
		} else if (Integer.parseInt(type) == 3) { // 未开奖
			orderStatusList.add(OrderStatus.PRINT_WAITING.getValue());
			orderStatusList.add(OrderStatus.PRINTING.getValue());
			orderStatusList.add(OrderStatus.PRINTED.getValue());
			orderStatusList.add(OrderStatus.NOT_SPLIT.getValue());
			orderResultStatus.add(OrderResultStatus.not_open.getValue());
		} else if (Integer.parseInt(type) == 4) { // 撤单
			orderStatusList.add(OrderStatus.CANCELLED.getValue());
			orderStatusList.add(OrderStatus.UNPAY_OBSOLETE.getValue());
		}
		return lotteryOrderDao.getOrder(userno, startTime, endTime, page, type, lotteryType, orderStatusList, orderResultStatus);

	}

	@Transactional
	public List<LotteryOrder> getToday(PageBean<LotteryOrder> page) {
		String whereS = " orderStatus in(:orderStatus) and orderResultStatus in (:orderResultStatus) and receiveTime>=:startTime  and receiveTime<=:endTime";
		List<Integer> orderStatusList = new ArrayList<Integer>();
		orderStatusList.add(OrderStatus.PRINTED.getValue());
		orderStatusList.add(OrderStatus.HALF_PRINTED.getValue());
		List<Integer> orderResultStatusList = new ArrayList<Integer>();
		orderResultStatusList.add(OrderResultStatus.win_big.getValue());
		orderResultStatusList.add(OrderResultStatus.win_already.getValue());
		Map<String, Object> map = new HashMap<String, Object>();
	

		map.put("orderStatus", orderStatusList);
		map.put("orderResultStatus", orderResultStatusList);
		map.put("endTime", new Date());
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.HOUR, -72);
		map.put("startTime", calendar.getTime());
		return lotteryOrderDao.findByCondition(50, whereS, map);
	}



	@Transactional
	public List<LotteryOrder> getByOrderStatusAndDealLine(int lotteryType,List<Integer> statusList,Date dealLine,PageBean<LotteryOrder> pageBean){
		return lotteryOrderDao.getByOrderStatusAndDealLine(lotteryType, statusList, dealLine, pageBean);
	}
	/**
	 * 根据彩种，期号查询订单
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            订单
	 * @return list 返回数据
	 * */
	@Transactional
	public List<LotteryOrder> getByLotteryTypeAndPhase(Integer lotteryType, String phase, PageBean<LotteryOrder> page) {
		return lotteryOrderDao.getByLotteryTypeAndPhase(lotteryType, phase, page);
	}

	/***
	 * 根据彩种，期号，场次号,订单 状态，开奖状态查询
	 * @param lotteryType 彩种
	 * @oparm phase 期号
	 * @param matchNum 场次号
	 * @param orderStatusList 订单状态
	 * @param orderResultStatusList 开奖状态
	 * 
	 * */
	@Transactional
	public List<LotteryOrder> getByLotteryPhaseMatchNumAndStatus(Integer lotteryType,String phase,String matchNum,List<Integer> orderStatusList,List<Integer> orderResultStatusList,PageBean<LotteryOrder> page){
		return lotteryOrderDao.getByLotteryPhaseMatchNumAndStatus(lotteryType, phase, matchNum, orderStatusList, orderResultStatusList, page);
	}

	@Transactional
	public void modifyLotteryOrderTime(LotteryOrder lotteryOrder) {
		lotteryOrderDao.update(lotteryOrder);
		if (lotteryOrder.getBetType() == BetType.hemai.getValue() || StringUtils.isNotBlank(lotteryOrder.getHemaiId())) {
			LotteryCaseLot caseLot = lotteryCaseLotDao.find(lotteryOrder.getHemaiId());
			if (caseLot != null) {
				caseLot.setEndTime(lotteryOrder.getDeadline());
				lotteryCaseLotDao.merge(caseLot);
			}
		}
	}


	/**
	 * 根据用户，彩种，订单状态，时间查询消费额
	 * @param userno 用户名
	 * @param orderStatus 订单状态
	 * @param lotteryType 彩种
	 * @param startTime 开始时间 yyyy-mm-dd hh:mm:ss
	 * @param endTime 结束时间 yyyy-mm-dd hh:mm:ss
	 * */
	@Transactional
	public Object[] getOrderUserBet(String userno, int orderStatus, int lotteryType, String startTime, String endTime) {
		return lotteryOrderDao.getOrderUserBet(userno, lotteryType, orderStatus, CoreDateUtils.StrToDate(startTime), CoreDateUtils.StrToDate(endTime));
	}

	/**
	 * 根据用户编号，开始时间，结束时间，查询投注明细
	 * 
	 * @param userno
	 *            用户编号
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * */
	@Transactional
	public List<LotteryOrder> getOrderListByUserno(String userno, String startTime, String endTime, PageBean<LotteryOrder> page) {
		BetweenDate bd = SpecialDateUtils.getBetweenDate(3, startTime, endTime);
		Date startDate = bd.getStartDate();
		Date endDate = bd.getEndDate();
		return lotteryOrderDao.getOrderListByUserno(userno, startDate, endDate, page);
	}
	/**
	 * 查询中奖记录
	 * @param  userno 用户编号
	 * @param  startTime 结束时间
	 * @param  endTime 开始时间
	 * */
	@Transactional
	public List<LotteryOrder> getAllPrize(String userno,String startTime, String endTime, PageBean<LotteryOrder> page ){
		BetweenDate bd = SpecialDateUtils.getBetweenDate(3, startTime, endTime);
		Date startDate = bd.getStartDate();
		Date endDate = bd.getEndDate();
        return  lotteryOrderDao.getAllPrize(userno,startDate,endDate,page);
	}



	/**
	 * 根据用户编号,彩种,开始时间，结束时间，状态查询订单
	 * 
	 * @param userno
	 *            用户名
	 * @param lotteryType
	 *            彩种
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间 yyyy-mm-dd hh:mm:ss
	 * @param status
	 *            状态
	 * */
	@Transactional
	public List<LotteryOrder> getByStatus(String userno, int lotteryType, String startTime, String endTime, int status) {
		Date startDate = CoreDateUtils.StrToDate(startTime);
		Date endDate = CoreDateUtils.StrToDate(endTime);
		return lotteryOrderDao.getByStatus(userno, lotteryType, startDate, endDate, status);
	}



	/**
	 * 根据彩种，期号，订单状态，开奖状态查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param orderStatusList
	 *            订单状态
	 * @param orderResultStatusList
	 *            开奖状态
	 * @param sortType
	 *            排序方式(asc,desc)
	 * @param lastMatchNum
	 *            最后场次(单场,竞彩)
	 * */
	@Transactional
	public List<LotteryOrder> getByStatusAndType(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, String sortType, Long lastMatchNum, PageBean<LotteryOrder> page) {
		return lotteryOrderDao.getByStatusAndType(lotteryType, phase, orderStatusList, orderResultStatusList, sortType, lastMatchNum, page);
	}

	/**
	 * 根据彩种，期号，订单状态，开奖状态查询
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param orderStatusList
	 *            订单状态
	 * @param orderResultStatusList
	 *            开奖状态
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param lastMatchNum
	 *            最后场次(单场,竞彩)
	 * */
	@Transactional
	public List<LotteryOrder> getByStatusAndDate(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, Date beginTime, Date endTime, Long lastMatchNum, PageBean<LotteryOrder> page) {
		return lotteryOrderDao.getByStatusAndDate(lotteryType, phase, orderStatusList, orderResultStatusList, beginTime, endTime, lastMatchNum, page);
	}
	/**
	 * 根据彩种，期号，订单状态，开奖状态查询
	 *
	 * @param lotteryType
	 *            彩种
	 * @param phase
	 *            期号
	 * @param orderStatusList
	 *            订单状态
	 * @param orderResultStatusList
	 *            开奖状态
	 * @param beginTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @param lastMatchNum
	 *            最后场次(单场,竞彩)
	 * */
	@Transactional
	public List<String> getIdByStatusAndDate(int lotteryType, String phase, List<Integer> orderStatusList, List<Integer> orderResultStatusList, Date beginTime, Date endTime, Long lastMatchNum, PageBean<LotteryOrder> page) {
		return lotteryOrderDao.getIdByStatusAndDate( lotteryType,phase, orderStatusList,  orderResultStatusList,  beginTime,  endTime, lastMatchNum,page);

	}

	/**
	 * 修改订单的支付状态
	 * @param payStatus 支付状态
	 * @param id 订单号
	 * */
	@Transactional
	public int updatePayStatus(int payStatus,String id){
		return lotteryOrderDao.updatePayStatus(payStatus, id);
	}
	/**
	 * 修改订单状态
	 * @param status 订单状态
	 * @param id 订单号
	 * */
	@Transactional
	public int updateOrderStatus(int status,String id){
		return lotteryOrderDao.updateOrderStatus(status, id);
	}
	
	/**
	 * 修改订单派奖状态
	 * @param orderid 订单号
	 * @param rewarStatus 派奖状态
	 * */
	@Transactional
	public int updateOrderRewardStatus(int rewarStatus,String orderid){

		return lotteryOrderDao.updateOrderRewardStatus(rewarStatus, orderid);
	}
	@Transactional
	public int updateOrderStatusPrintTime(String orderId,int orderStatus,Date date){
		merchantOrderDAO.updateMerchantOrderStatusAndPrintTime(orderId, orderStatus, date);
		return lotteryOrderDao.updateOrderStatusPrintTime(orderId, orderStatus, date);
	}
    /**
     * 修改票的状态和票总数
     * @param  orderId 票号
     * @param orderStatus 订单状态
     * @param ticketCount 票总数
     * */
    @Transactional
     public int updateOrderStatusAndTicketCount(String orderId,int orderStatus,int ticketCount ){
        return lotteryOrderDao.updateOrderStatusAndTicketCount(orderId, orderStatus, ticketCount);
    }
    /**
     * 修改票的状态,失败票总数
     * @param  orderId 票号
     * @param orderStatus 订单状态
     * @param failCount 失败票数
     * */
    @Transactional
     public int updateOrderStatusAndFailCount(String orderId,int orderStatus,int failCount ){
        return lotteryOrderDao.updateOrderStatusAndFailCount(orderId, orderStatus, failCount);
    }
	/**
	 * 修改订单状态，支付状态
	 * @param  orderId 订单号
	 * @param  orderStatus 订单状态
	 * @param  payStatus 支付状态
	 * */
	@Transactional
	public int updateOrderStatusAndPayStatus(String orderId,Integer orderStatus,Integer payStatus){
		return lotteryOrderDao.updateOrderStatusAndPayStatus(orderId, orderStatus, payStatus);
	}
	/**
	 * 根据订单状态查询
	 * @param  orderStatus 订单状态
	 * @param  pageBean 分页
	 * */
	@Transactional
	public List<LotteryOrder> getByOrderStatus(Integer lotteryType,int orderStatus,PageBean<LotteryOrder> pageBean){
		return lotteryOrderDao.getByOrderStatus(lotteryType,orderStatus, pageBean);
	}
	/**
	 * 修改订单中奖状态
	 * @param orderId 订单号
	 * @param  orderResultstatus 中奖状态
	 * @param wincode 开奖号码（可为空）
	 * */
	@Transactional
	public  void updateOrderResultStatus(String orderId,int orderResultstatus,String wincode){
		lotteryOrderDao.updateOrderResultStatus(orderId, orderResultstatus,wincode);
	}
	/**
	 * 根据彩种,彩期,状态,开奖状态查询
	 * @param lotteryType 彩种
	 *  @param phase 彩期
	 * @param  orderResultStatusList 中奖状态
	 * @param  orderStatusList
	 * */
	@Transactional
	public List<LotteryOrder> getByLotteryPhaseAndStatus(Integer lotteryType,String phase,List<Integer> orderStatusList,List<Integer> orderResultStatusList,int max){
		return  lotteryOrderDao.getByLotteryPhaseAndStatus(lotteryType, phase, orderStatusList, orderResultStatusList, max);
	}
	/**
	 *根据彩种，期号，订单状态，开奖状态查询,场次,时间
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param orderStatusList 订单状态
	 * @param orderResultStatusList 开奖状态
	 * @param beginTime  开始时间
	 * @param endTime 结束时间
	 * @param matchNum 最大场次号(北单，竞彩)
	 * */
	@Transactional
	public List<LotteryOrder> getByLotteryStatusMatchNumAndDate(int lotteryType, String phase,List<Integer> orderStatusList,List<Integer> orderResultStatusList, Date beginTime, Date endTime, String matchNum,PageBean<LotteryOrder> page){
		return  lotteryOrderDao.getByLotteryStatusMatchNumAndDate(lotteryType, phase, orderStatusList, orderResultStatusList, beginTime, endTime, matchNum, page);
	}
	/**
	 * 根据用户编号,订单状态查询
	 * @param userno 用户编号
	 * @param orderStatus  订单状态
	 * */
	@Transactional
	public List<LotteryOrder> getByUsernoAndStatus(String userno,int orderStatus){
		return lotteryOrderDao.getByUsernoAndStatus(userno, orderStatus);
	}
	/***
	 * 检查未派奖的订单个数
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * */
	@Transactional
	public Long countNotPrize(int  lotteryType,String phase){
		return lotteryOrderDao.countNotPrize(lotteryType, phase);
	}
	
	/***
	 * 检查未算奖的订单
	 * @param lotteryType 彩种
	 * @param phase 期号 
	 * */
	@Transactional
	public Long countNotEncash(int  lotteryType,String phase){
		return lotteryOrderDao.countNotEncash(lotteryType, phase);
	}



	/**
	 *
	 * 修改订单开奖号码
	 * @param orderId  订单号
	 * @param wincode 开奖号码
	 * */
	@Transactional
	public void updateWincode(String orderId,String wincode){
		lotteryOrderDao.updateWincode(orderId, wincode);
	}


	/**
	 * 根据状态查询
	 * @param orderStatus  订单状态
	 * @param orderResultStatus  开奖状态
	 * @param max 最大条数
	 * */
	@Transactional
	public List<LotteryOrder> getByStatus(int orderStatus,int orderResultStatus,int max){
		return lotteryOrderDao.getByStatus(orderStatus, orderResultStatus,max);
	}
	/**
	 * 根据 彩种，期号，票状态查询
	 * @param lotteryType  彩种
	 * @param phase 期号
	 *@param status 状态
	 * @param max  最大条数
	 * */

	public List<LotteryOrder> getByTypePhaseAndStatus(int lotteryType,String phase,int status,int max){
		return lotteryOrderDao.getByTypePhaseAndStatus(lotteryType, phase, status, max);
	}
}
