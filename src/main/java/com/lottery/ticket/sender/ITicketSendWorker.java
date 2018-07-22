/**
 * 
 */
package com.lottery.ticket.sender;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.ticket.TicketBatch;

/**
 * @author fengqinyun
 *
 */
public interface ITicketSendWorker {

	/**
	 * 按批次送票
	 * @param ticketBatch 批次
	 * @return
	 */
	public void execute(TicketBatch ticketBatch, LotteryType lotteryType) throws Exception;
}
