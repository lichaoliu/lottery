/**
 * 
 */
package com.lottery.draw.prize.executor;

import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.lottype.OrderCalResult;

/**
 * 订单开奖器
 * @author fengqinyun
 *
 */
public interface ILotteryDrawWorker {
	/**
	 * 对单个订单执行开奖
	 * @param order
	 * @throws LotteryDrawWorkerException
	 */
	public OrderCalResult draw(LotteryOrder order,String wincode) throws Exception;


}
