/**
 * 
 */
package com.lottery.draw.prize.thread;

import java.util.concurrent.CountDownLatch;

import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.draw.prize.executor.ILotteryDrawWorker;

/**
 * @author fengqinyun
 *
 */
public class CommonDrawRunnable extends AbstractDrawRunnable {
	
	public CommonDrawRunnable() {
	
	}
	
	public CommonDrawRunnable(CountDownLatch latch,String wincode) {
		super(latch,wincode);
	}

	private ILotteryDrawWorker lotteryDrawWorker;
	
	@Override
	protected boolean handleOrder(LotteryOrder order,String wincode) throws Exception {
		try {
			lotteryDrawWorker.draw(order,wincode);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		return true;
	}

	public void setLotteryDrawWorker(ILotteryDrawWorker lotteryDrawWorker) {
		this.lotteryDrawWorker = lotteryDrawWorker;
	}


}
