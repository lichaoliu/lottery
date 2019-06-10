/**
 * 
 */
package com.lottery.ticket.sender.worker.thread;

import com.lottery.core.domain.ticket.TicketBatch;

/**
 * @author fengqinyun
 *
 */
public interface ITicketSendDispatcher {

    /**
     * 发送一个送票批次
     * @param ticketBatch 批次对象
     * @throws Exception
     */
	public void dispatch(TicketBatch ticketBatch) throws Exception;
}
