package com.lottery.core.dao;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.dao.IGenericDAO;
import com.lottery.core.domain.ticket.TicketBatch;

import java.util.List;

public interface TicketBatchDAO extends IGenericDAO<TicketBatch, String> {

	public int updateStatusAndSendTime(TicketBatch ticketBatch);

	public int updateStatusAndTerminalId(TicketBatch ticketBatch);
	
	/**
	 * 修改批次状态
	 * 
	 * */
	public int updateTicketBatchStatus(TicketBatch ticketBatch);

	/**
	 * 查询需要送票的批次
	 * 
	 * @param lotteryType
	 *            彩种
	 * @param excludeTerminalIdList
	 *            暂停的终端
	 * @param max
	 *            最大条数
	 * */
	public List<TicketBatch> findForSend(LotteryType lotteryType, List<Long> excludeTerminalIdList, int max);
	
	/**
	 * 查找处于进入送票队列等待状态的批次供回收处理
	 * @param lotteryType
	 * @return
	 */
	public List<TicketBatch> findForSendRecycle(int lotteryType);


	/**
	 * 查询需要送票的批次
	 *
	 * @param lotteryType
	 *            彩种
	 * @param terminalId
	 *            暂停的终端
	 * @param max
	 *            最大条数
	 * */
	public List<TicketBatch> findForSend(LotteryType lotteryType, Long terminalId, int max);

	/**
	 * 修改批次状态
	 * @param id  批次id
	 * @param ticketBatchStatus  批次状态
	 * */
	public int updateStatus(String id,int ticketBatchStatus);
	

}
