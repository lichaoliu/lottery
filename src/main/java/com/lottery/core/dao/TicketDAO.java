package com.lottery.core.dao;

import com.lottery.common.PageBean;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.ticket.Ticket;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Set;

public interface TicketDAO extends IGenericDAO<Ticket, String>{
	/**
	 * 根据订单号查询票
	 * @param orderId 订单号
	 * */
	List<Ticket> getByorderId(String orderId);
	
	
	
	/**
	 * 根据订单号,票状态查询票
	 * @param orderId 订单号
	 * @param ticketStatus 票状态
	 * */
	List<Ticket> getByorderIdAndStatus(String orderId,Integer ticketStatus);
	/**
	 * 根据订单号查询票
	 * @param orderId 订单号
	 * @param page 分页
	 * 
	 * */
	public List<Ticket> getByorderId(String orderId,PageBean<Ticket> page);
	
	/**
	 * 根据订单号查询票,不考虑状态
	 * @param orderId 订单号
	 * @param page 分页
	 * 
	 * */
	public List<Ticket> getByorderIdIgnoreStatus(String orderId,PageBean<Ticket> page);
	
	
	/**
	 * 修改订单已 算奖状态
	 * @param orderid 订单号
	 * */
	public int updateTicketsEncash(String orderid);
	/**
	 * 重新算奖，修改票状态
	 * @param orderId 订单号
	 * */
	public int updateTicketPrize(String orderId);
	

	
	public Long getCountNotCalByOrderId(String orderId);
	
	
	public List<Ticket> findWinBigTicket(String orderId);
	/**
	 * 根据订单号，订单状态查询票的总金额
	 * @param orderId 订单号
	 * @param ticketStatus 票状态
	 * */
	public BigDecimal getSumAmountByOrderIdAndStatus(String orderId,int ticketStatus);
	
	
	public BigDecimal getSumPosttaxPrizeByOrderIdAndStatus(String orderId,TicketStatus status);
	
	public BigDecimal getSumPretaxPrizeByOrderIdAndStatus(String orderId,TicketStatus status);
	
	public Long getCountWinBigTicket(String orderid,TicketStatus status);
	
	/**
	 * 根据订单号修改票的状态
	 * @param orderId 订单号
	 * @param fromTicketStatus 原来状态
	 * @param toTicketStatus 需要修改的状态
	 * */
	public int updateTicketStatusByOrderId(String orderId,int fromTicketStatus,int toTicketStatus);
	/**根据批次号状态查询票
	 * @param batchTicketId 批次号
	 * @param ticketStatus 票状态
	 * */
	
	public List<Ticket> getByBatchIdAndStatus(String batchTicketId, int  ticketStatus,PageBean<Ticket> page);
	/**
	 * 根据批次查询票
	 * @param batchId 批次号
	 * */
	public List<Ticket> getByBatchId(String batchId);
    /**
     * 查询出票中的票
     * @param terminalId 终端id
     * @param lotteryTypeList 彩种集合
     * @param max 最大多少条
     * @param date 时间偏移量
     * */
	public List<Ticket> getUnCheckTicket(Long terminalId,List<LotteryType> lotteryTypeList,int max,Date  date);
	
	/***
	 * 根据订单号,中奖状态查询票
	 * @param orderId 订单号
	 * @param  ticketResultStatus 票中奖状态
	 * */
	public List<Ticket> getPrizeList(List<Integer> ticketResultStatus,String orderId);
	/***
	 * 根据订单号,中奖状态查询票数量
	 * @param orderId 订单号
	 * @param  ticketResultStatus 票开奖状态   集合
	 * */
	public Long getByResultStatus(List<Integer> ticketResultStatus,String orderId);
	/***
	 * 根据订单号,中奖状态查询票数量
	 * @param orderId 订单号
	 * @param  ticketResultStatus 票开奖状态
	 * */
	public Long getByResultStatus(Integer ticketResultStatus,String orderId);
	/**
	 * 根据彩种,期号,开奖状态查询
	 * @param ticketResultStatus 开奖状态
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param max 最大条数
	 * */
	public List<Ticket> getUnPrize(int ticketResultStatus,Integer lotteryType,String phase,int max);
	
	public int updateTicketStatus(Ticket ticket);
	/**
	 * 修改成功送票信息
	 * */
	public int updateTicketPrintingInfo(Ticket ticket);
	/**
	 * 修改出票成功信息
	 * @param isSync 是否同步出票
	 * **/
	public int updateTicketSuccessInfo(Ticket ticket,boolean isSync);

	/**
	 * 修改出票失败的票
	 * */
	public int updateTicketStatusAndFailureInfo(Ticket ticket);
	/**
	 * 查询需要转换终端的票
	 * @param terminalId 票所在终端
	 * @param lotteryType 彩种
	 * @param statusList 状态
	 * @param minute 距离期间时间
	 * */
	public List<Ticket> getChangeTerminal(Long terminalId,Integer lotteryType,List<Integer> statusList,Integer minute,PageBean<Ticket> pageBean);
	/**
	 * 修改票的状态
	 * @param status 状态
	 * @param id 票号
	 * */
	public int updateTicketStatus(int status,String id);
	/**
	 *查询需要分票的票
	 *@param lotteryType 彩种
	 *@param phase 期号
	 *@param max 最大条数
	 * */
	public List<Ticket> findToAllot(int lotteryType, String phase, int max);
	
	/**
	 * 根据订单,票状态查询总数
	 * 
	 * @param orderId
	 *            订单号
	 * @param ticketStatus
	 *            票状态
	 * */
	public Long getCountByOrderIdAndStatus(String orderId, int ticketStatus);
	/**
	 * 根据订单查询票总数
	 * @param orderId 订单号
	 * */
	public Long getCountByOrderId(String orderId);
	/**
	 * 根据终端id,外部票号查询票 
	 * @param terminalId 终端id
	 * @param externalId 外部票号
	  * */
	public List<Ticket> getByTerminalIdAndExternalId(Long terminalId,String externalId);
	/**
	 * 根据状态,截止时间 查询
	 * @param statusList 票状态
	 * @param beginTime 开始时间
	 * @param endTime 结束时间
	 * 
	 * */
	public List<Ticket> getByStatusAndTerminateTime(List<Integer> statusList,Date beginTime,Date endTime,PageBean<Ticket> page);
	/**
	 * 修改票派奖状态
	 * @param orderid 订单号
	 * @param rewarStatus 派奖状态 
	 * */
	public int updateTicketRewardStatus(int rewarStatus,String orderid);

    /**
     * 根据票状态查询
     * @param  ticketStatus 票状态
     * @param max 最大条数
     * */
    public List<Ticket> getByStatus(Integer ticketStatus,int max);
    /**
     * 根据彩种,票状态查询
     * @param  lotteryType 彩种
     * @param  ticketStatus 票状态
     * @param max 最大条数
     * */
    public List<Ticket> getByLotteryTypeAndStatus(Integer lotteryType,int ticketStatus,int max);
	/**
	 *查找未检票的票
	 * @param  lotteryType 彩种
	 * @param terminalId 终端id
	 * **/
	public List<Ticket> getUnCheckTicket(int lotteryType,Long terminalId,Date date,int max);
	
	
	public int updateOdd(String ticketid,String odd) ;

	/**
	 * 根据终端id,彩种,状态 查询
	 * @param  terminalId 终端id
	 * @param  lotteryType 彩种
	 * @param status 票状态
	 * @param max 最大条数
	 * */
	public List<Ticket> getByTerminalIdLotteryTypeAndStatus(Long terminalId,int lotteryType,int status,int max);
	/**
	 * 根据订单号修票状态
	 * @param  orderId 订单号
	 * @param  status 出票状态
	 * @param  ticketResultStatus 中奖状态
	 * */
	public  int updateStatusByOrderId(String orderId,int status,int ticketResultStatus);


	/**
	 * 根据订单号修票中奖状态
	 * @param  orderId 订单号
	 * @param  ticketResultStatus 中奖状态
	 * */
	public  int updateTicketResultStatusByOrderId(String orderId,int ticketResultStatus);
	/**
	 * 根据终端号,票状态查询
	 * @param terminalId 终端号
	 * @param status 票状态
	 * @param max 最大条数
	 * */
	public List<Ticket> getByTerminalIdAndStatus(Long terminalId,int status,int  max);

    /***
	 * 修改票是否兑奖
	 * @param  ticketId 票号
	 * @param isExchange 是否中奖
	 * */
	public int updateExchange(String ticketId,int isExchange);
	
	/**
	 * 根据终端号,票状态 查询票号
	 * @param terminalId 终端号
	 * @param status 票状态
	 * */
	public List<Object[]> getIdsByTerminalIdAndStatus(Long terminalId, int status, int max);
	public Long countByTerminalIdAndStatus(Long terminalId, int status);
	public Long countByTerminalIdAndStatus(Long terminalId, int status, Date deadline);
	public List<Object[]> countsByTerminalIdAndStatus(Set<Long> terminalIds, int status);

	/**
	 * 查找渠道未兑奖票
	 * @param lotteryType 彩种
	 * @param phase 期号
	 * @param ticketResultStatus 中奖状态
	 * @param agencyExchange 兑奖状态
	 * @param terminalId 终端号
	 * @param startTime  开始时间
	 * @param endTime 结束时间
	 * */
	public List<Ticket> getUnVenderExcechange(Integer lotteryType,String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId,Date startTime,Date endTime,PageBean<Ticket> page);



	/**
	 * 根据创建时间,结束时间,票状态查询
	 * @param createTime 创建时间
	 * @param endTime 结束时间
	 * @param terminalId 终端号
	 * @param status 状态
	 * */
	public List<Ticket> getByCreateTimeAndEndTimeAndStatus(Date createTime,Date endTime,Long terminalId,Integer status,PageBean<Ticket> page);

	public List<Object[]> getByLotteryPhaseAndStatusForMonitor(Integer lotteryType, Date deadline, List<Integer> status);
	public List<Object[]> getByLotteryPhaseAndStatus(Integer lotteryType, String phase, Integer status, Integer max);
	public Long getCountByLotteryPhaseAndStatus(Integer lotteryType, String phase, Integer status);




}
