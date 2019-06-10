/**
 * 
 */
package com.lottery.ticket.sender.worker.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.ticket.sender.ITicketSendWorker;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengqinyun
 *
 */
public abstract class AbstractTicketSenderRunnable extends AbstractThreadRunnable implements ITicketSenderRunnable {

	private long interval = 1000l; // 每次处理间隔(毫秒)

	private int maxProcessBatchCount = 100; // 每次最大送票批次数

	@Autowired
	protected ITicketSendWorker ticketSendWorker;


	private List<LotteryType> lotteryList;
	private LotteryType lotteryType;

	@Override
	protected void executeRun() {
		if (this.getLotteryList() == null) {
			this.setLotteryList(new ArrayList<LotteryType>());

			if (this.getLotteryType() != null) {
				this.getLotteryList().add(this.getLotteryType());
			}
		}

		if (this.getLotteryList().isEmpty()) {
			logger.error("要送票的彩种未设置");
			return;
		}
		if (ticketSendWorker == null) {
			logger.error("未绑定送票器");
			return;
		}


		this.execute();
	}




	abstract public void execute();

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}



	public List<LotteryType> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(List<LotteryType> lotteryList) {
		this.lotteryList = lotteryList;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public int getMaxProcessBatchCount() {
		return maxProcessBatchCount;
	}

	public void setMaxProcessBatchCount(int maxProcessBatchCount) {
		this.maxProcessBatchCount = maxProcessBatchCount;
	}

	public ITicketSendWorker getTicketSendWorker() {
		return ticketSendWorker;
	}

	public void setTicketSendWorker(ITicketSendWorker ticketSendWorker) {
		this.ticketSendWorker = ticketSendWorker;
	}

	/**
	 * 重新加载
	 * */
	public void reload()throws Exception{

	}

}
