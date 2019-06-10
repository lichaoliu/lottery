package com.lottery.draw.prize.executor;

import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.core.cache.model.LottypeConfigCacheModel;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.handler.PrizeHandler;
import com.lottery.core.service.*;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.service.topic.TopicMessageSendService;
import com.lottery.draw.ILotteryDrawTask;
import com.lottery.lottype.LotManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.concurrent.CountDownLatch;



/**
 * @author fengqinyun
 *
 */
public abstract class AbstractLotteryDrawExecutor implements ILotteryDrawExecutor{
	protected final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	
	
	@Autowired
	protected LottypeConfigCacheModel lottypeconfigCache;

	@Autowired
	protected LotManager lotmanager;
	
	@Autowired
	protected LotteryOrderService lotteryOrderService;
	
	@Autowired
	protected PrizeService prizeService;
	@Autowired
	protected JingcaiService jingcaiService;
	@Autowired
	protected BeidanService beidanService;
	@Autowired
	protected LotteryPhaseService lotteryPhaseService;
	@Autowired
    protected QueueMessageSendService queueMessageSendService;
	@Autowired
	protected JingcaiGuanjunService jingcaiGuanjunService;

	
	@Autowired
	protected TicketService ticketService;
	@Autowired
    protected PrizeHandler prizeHandler;
	
	@Autowired
	protected TopicMessageSendService topicMessageSendService;



	@Autowired
	protected SystemService systemService;

	@Autowired
	protected SharedJedisPoolManager shareJedisPoolManager;
	private int drawThreadCount=5;
	
	private int queryPageSize=15;
	
	private int queryThreadCount=5;
	
	private int avgOrderQueryHour=1;
	
	
	@Override
	public void execute(ILotteryDrawTask lotteryDrawTask) throws Exception {
		logger.info("校验开奖任务");
		if (!validateDrawTask(lotteryDrawTask)) {
			logger.error("校验开奖任务未通过:{}", lotteryDrawTask);
			return;
		}
		
		if(!beforeDrawHandle(lotteryDrawTask)){
            logger.error("开奖操作准备错误",lotteryDrawTask);
            return;
        }

		logger.info("分配订单到开奖线程");
		try {
			dispatchOrderToThread(lotteryDrawTask);
		} catch(Exception e){
			logger.error("分配订单到开奖线程异常",e);
			return;
		}		

		logger.info("开奖完成后续处理");
		checkOrder(lotteryDrawTask);
		recycleOrder(lotteryDrawTask);
		afterDrawHandle(lotteryDrawTask);



	}

	
	/**
	 * 订单回收
	 * */
	abstract protected void recycleOrder(ILotteryDrawTask lotteryDrawTask);
	
	protected void checkOrder(ILotteryDrawTask lotteryDrawTask) throws Exception{
		
	}
	@Override
	public void destroyChildrenThreads() throws Exception {


	}
	
	protected abstract List<LotteryOrder> findOrderListForRecycle(ILotteryDrawTask lotteryDrawTask);
	




	/**
	 * 开奖完成后续处理
	 * @param lotteryDrawTask
	 */
	abstract protected void afterDrawHandle(ILotteryDrawTask lotteryDrawTask) throws Exception;

	/**
	 * 分配订单到开奖线程
	 * @param lotteryDrawTask
	 */
	abstract protected void dispatchOrderToThread(ILotteryDrawTask lotteryDrawTask) throws Exception;

	/**
	 * 初始化开奖订单总数
	 * @param lotteryDrawTask
	 */
	abstract protected Integer initDrawOrderTotal(ILotteryDrawTask lotteryDrawTask);

	/**
	 * 启动执行开奖线程
	 * @param latch
	 * @param lotteryDrawTask
	 */
	abstract protected void startDrawThread(CountDownLatch latch, ILotteryDrawTask lotteryDrawTask);

	/**
	 * 开奖前准备
	 * @param lotteryDrawTask
	 */
	abstract protected boolean beforeDrawHandle(ILotteryDrawTask lotteryDrawTask) throws Exception;


	
	/**
	 * 校验开奖任务
	 * @param lotteryDrawTask
	 * @return
	 */
	abstract protected boolean validateDrawTask(ILotteryDrawTask lotteryDrawTask);

	public int getDrawThreadCount() {
		return drawThreadCount;
	}

	public void setDrawThreadCount(int drawThreadCount) {
		this.drawThreadCount = drawThreadCount;
	}

	public int getQueryPageSize() {
		return queryPageSize;
	}

	public void setQueryPageSize(int queryPageSize) {
		this.queryPageSize = queryPageSize;
	}

	public int getQueryThreadCount() {
		return queryThreadCount;
	}

	public void setQueryThreadCount(int queryThreadCount) {
		this.queryThreadCount = queryThreadCount;
	}

	public int getAvgOrderQueryHour() {
		return avgOrderQueryHour;
	}

	public void setAvgOrderQueryHour(int avgOrderQueryHour) {
		this.avgOrderQueryHour = avgOrderQueryHour;
	}
	
	
	
	
}
