package com.lottery.ticket.phase.execute;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PhaseEventType;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.message.IWarningTool;
import com.lottery.ticket.phase.thread.IPhaseEventExecutor;
import com.lottery.ticket.phase.thread.IPhaseHandler;
import com.lottery.ticket.phase.thread.IScheduledTask;

import java.util.ArrayList;
import java.util.List;

/**
 * @author fengqinyun
 *
 */
public abstract class AbstractPhaseEventExecutor extends AbstractThreadRunnable implements IPhaseEventExecutor {

	protected LotteryPhaseService phaseService;
	private IWarningTool warningTool;

	private Object taskLock = new Object();
	private List<IScheduledTask> tasks;

	protected IPhaseHandler phaseHandler;
	private IPhaseEventExecutor nextEventExecuter; // 级联事件处理


	public AbstractPhaseEventExecutor() {
		tasks = new ArrayList<IScheduledTask>();
	}

	protected QueueMessageSendService queueMessageSendService;
	@Override
	protected void executeRun() {
		logger.info("准备启动彩期事件处理线程");

		while (running) {
			while (true) {
				IScheduledTask task = null;
				synchronized (taskLock) {
					if (tasks.size() > 0) {
						task = tasks.remove(0);
					}
				}
				if (task == null) {
					break;
				}
				// 打印调试信息
				//logger.error(task.getTaskInfo());

				// 执行任务
				boolean rc = false;
				try {
					rc = execute(task);
				} catch (Exception e) {
					logger.error("{},执行任务出错",task.getTaskInfo());
					logger.error(e.getMessage(), e);
				}
				if (!rc) {
					logger.error("任务处理失败,任务内容:{}",task.getTaskInfo());
					continue;
				}

				if (nextEventExecuter != null) {
					IScheduledTask nextEventTask = generateScheduledTaskForNextEvent(task);
					if (nextEventTask.isDuplicate()) {
						// 如果任务已重复，不做级联传递
						logger.error("任务已重复,不做级联传递, {}", nextEventTask.getTaskInfo());
						continue;
					}

					nextEventExecuter.addScheduledTask(nextEventTask);
					nextEventExecuter.executeNotify();
				}

			}

			synchronized (this) {
				try {
					this.wait();
				} catch (InterruptedException e) {
					logger.error(e.getMessage(), e);
				}
			}
		}

		logger.info("事件执行线程结束");
	}

	/**
	 * 实际的各事件执行体
	 */
	abstract protected boolean execute(IScheduledTask itask);

	/**
	 * 提供给级联事件处理的任务，默认直接传递原有任务
	 * 
	 * @param currentTask
	 * @return
	 */
	protected IScheduledTask generateScheduledTaskForNextEvent(IScheduledTask currentTask) {
		return currentTask;
	}

	@Override
	public void addScheduledTask(IScheduledTask task) {
		synchronized (taskLock) {
			tasks.add(task);
		}
	}

	@Override
	public boolean removeScheduledTask(LotteryType lotteryType, PhaseEventType phaseEventType) {

		synchronized (taskLock) {
			if (tasks == null || tasks.size() <= 0) {
				return false;
			}
			List<IScheduledTask> delList = new ArrayList<IScheduledTask>();
			for (IScheduledTask task : tasks) {
				if (task.getLotteryType() == lotteryType && phaseEventType == task.getEventType()) {
					delList.add(task);
				}
			}
			if (delList.size() <= 0) {
				return false;
			}
			tasks.removeAll(delList);
			return true;
		}
	}

	public IPhaseEventExecutor getNextEventExecuter() {
		return nextEventExecuter;
	}

	public void setNextEventExecuter(IPhaseEventExecutor nextEventExecuter) {
		this.nextEventExecuter = nextEventExecuter;
	}

	public IWarningTool getWarningTool() {
		return warningTool;
	}

	public void setWarningTool(IWarningTool warningTool) {
		this.warningTool = warningTool;
	}

	public LotteryPhaseService getPhaseService() {
		return phaseService;
	}

	public void setPhaseService(LotteryPhaseService phaseService) {
		this.phaseService = phaseService;
	}

	public IPhaseHandler getPhaseHandler() {
		return phaseHandler;
	}

	public void setPhaseHandler(IPhaseHandler phaseHandler) {
		this.phaseHandler = phaseHandler;
	}

	public QueueMessageSendService getQueueMessageSendService() {
		return queueMessageSendService;
	}

	public void setQueueMessageSendService(QueueMessageSendService queueMessageSendService) {
		this.queueMessageSendService = queueMessageSendService;
	}
	

}
