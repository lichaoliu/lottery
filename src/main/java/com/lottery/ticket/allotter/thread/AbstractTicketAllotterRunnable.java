/**
 * 
 */
package com.lottery.ticket.allotter.thread;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.ticket.allotter.ITicketAllotWorker;

/**
 * @author fengqinyun
 *
 */
public abstract class AbstractTicketAllotterRunnable extends AbstractThreadRunnable  {
	
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private long interval = 10000l;		//每次处理间隔
	@Resource(name="executorBinder")
	private Map<LotteryType, ITicketAllotWorker> executorBinder;
	
	private List<LotteryType> lotteryList;
	
	@Override
	protected void executeRun() {
		
		if (executorBinder == null) {
			logger.error("未绑定分票器");
			return;
		}
		
		if (executorBinder.size() == 0) {
			logger.error("未绑定任何彩种的分票器");
			return;
		}
		logger.info("准备开始执行分票线程");
		this.execute();
	}
	
	abstract public void execute();

	public ITicketAllotWorker getAllotExecutor(LotteryType lotteryType) {
		ITicketAllotWorker worker=this.executorBinder.get(lotteryType);
		if(worker==null){
			worker=this.executorBinder.get(LotteryType.ALL);
		}
		return worker;
	}
	
	public List<LotteryType> getLotteryList() {
		return lotteryList;
	}

	public void setLotteryList(List<LotteryType> lotteryList) {
		this.lotteryList = lotteryList;
	}

	

	public long getInterval() {
		return interval;
	}

	public void setInterval(long interval) {
		this.interval = interval;
	}
}
