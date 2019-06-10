package com.lottery.ticket.phase.thread;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.terminal.ITerminalSelector;

public abstract class AbstractPhaseHandler extends AbstractThreadRunnable implements IPhaseHandler {
	
	

	/**
	 * 全局的彩期守护线程执行体绑定，启动后将自身对象注册到映射
	 */
	@Resource(name="allPhaseHandlerBinder")
	protected Map<LotteryType, IPhaseHandler> allPhaseHandlerBinder;
	@Autowired
	protected ITerminalSelector terminalSelector; 
	private LotteryType lotteryType;
	
	protected volatile boolean reloading = false;
	
	protected volatile boolean taskLoading = false;
	
	protected volatile boolean taskClearing = false;
	
	protected volatile boolean paused = false;	// 是否暂停
	
	private long offsetTime = 0;	// 相对于系统时间的偏移，用于出票商修正，负数为提前，单位：毫秒
	
	private long failoverInterval = 15000l;	//失败重试间隔，15秒
	protected Map<PhaseEventType,IPhaseEventExecutor> eventTypeMap=new ConcurrentHashMap<PhaseEventType,IPhaseEventExecutor>();

	@Autowired
	protected QueueMessageSendService queueMessageSendService;

	@Override
	protected void executeRun() {
		logger.info("准备执行彩期守护线程");
		
		if (lotteryType == null) {
			logger.error("要守护的彩种未设置");
			return;
		}
		
		
		logger.info("准备开始守护({})的彩期", lotteryType.getName());
		// 注册到全局彩期守护绑定
		if (this.allPhaseHandlerBinder != null) {
			synchronized (allPhaseHandlerBinder) {
				allPhaseHandlerBinder.put(lotteryType, this);
			}
		}
		
		logger.info("彩种[{}]的彩期守护线程执行初始化操作", lotteryType.getName());
		
		if(this.isStopSale(lotteryType)){
			logger.error("彩种:{}停止销售",lotteryType);
			return;
		}
		
		this.init();
		if(eventTypeMap==null||eventTypeMap.isEmpty()){
			logger.error("{}线程初始化失败",lotteryType.getName());
			return;
		}
		this.reload();
	
		this.execute();
	}

	 protected boolean isStopSale(LotteryType lotteryType){
		 return false;
	 }
	
	/**
	 * 初始化方法
	 */
	abstract protected void init();
	
	abstract protected void reload();
	abstract protected void execute();

	@Override
	public void executeTaskLoad() {
		synchronized (this) {
			taskLoading = true;
		}
		executeNotify();
	}

	@Override
	public void executeReload() {
		synchronized (this) {
			reloading = true;
		}
		logger.error("彩种[{}]的彩期守护线程接收到重载请求", lotteryType.getName());
		executeNotify();
	}
	

	@Override
	public void executePause() {
        boolean needToNotify = false;
		synchronized (this) {
            if (!paused) {
			    paused = true;
                // 只有原先运行中的线程才需要唤醒，否则会影响其他状态
                needToNotify = true;
            }
		}
		logger.error("彩种[{}]的彩期守护线程接收到暂停请求", lotteryType.getName());
        if (needToNotify) {
            // 恢复后，需要调用唤醒操作让线程执行之前的事件
            executeNotify();
        }
	}

	@Override
	public void executeResume() {
		boolean needToNotify = false;
		synchronized (this) {
			if (paused) {
				paused = false;
				// 只有原先暂停的线程才需要唤醒，否则会影响其他状态
				needToNotify = true;
			}
		}
		logger.error("彩种[{}]的彩期守护线程接收到恢复请求", lotteryType.getName());
		if (needToNotify) {
			// 恢复后，需要调用唤醒操作让线程执行之前的事件
			executeNotify();
		}
	}

	@Override
	public void executeTaskClear() {
		synchronized (this) {
			taskClearing = true;
		}
		executeNotify();
	}

	public long getFailoverInterval() {
		return failoverInterval;
	}

	public void setFailoverInterval(long failoverInterval) {
		this.failoverInterval = failoverInterval;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	
	public long getOffsetTime() {
		return offsetTime;
	}

	public void setOffsetTime(long offsetTime) {
		this.offsetTime = offsetTime;
	}

	
}
