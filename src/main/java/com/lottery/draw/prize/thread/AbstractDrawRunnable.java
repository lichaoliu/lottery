package com.lottery.draw.prize.thread;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.draw.prize.IDrawRunnable;

public abstract class AbstractDrawRunnable extends AbstractThreadRunnable implements IDrawRunnable {



	private CountDownLatch latch;

	private int orderUpdatePageSize = 100;
	
	private String wincode;

	private List<LotteryOrder> orderList = new ArrayList<LotteryOrder>();
	private Object orderLock = new Object();
	/**
	 * 已处理成功的方案计数
	 */
	private long successOrderCount = 0;
	private Object successOrderCountLock = new Object();
	/**
	 * 已处理失败的方案计数
	 */
	private long failedOrderCount = 0;
	private Object failedOrderCountLock = new Object();
	/**
	 * 未处理的方案计数
	 */
	private long waitingOrderCount = 0;
	private Object waitingOrderCountLock = new Object();

	/**
	 * 订单是否分配完毕
	 */
	private boolean dispatchCompleted = false;
	private Object dispatchCompletedLock = new Object();

	public AbstractDrawRunnable() {

	}

	public AbstractDrawRunnable(CountDownLatch latch) {
		this.latch = latch;
	}
	
	public AbstractDrawRunnable(CountDownLatch latch,String wincode) {
		this.latch = latch;
		this.wincode = wincode;
	}

	@Override
	protected void executeRun() {
		logger.info("执行开奖子线程：{}", Thread.currentThread().getName());

		boolean completed = false; // 当前子线程是否已处理完成所有任务
		int totalOrderCount = 0;
		while (running) {
			int i = 0;
			while (true) {
				if (!running) {
					break;
				}

				LotteryOrder order = null;

				completed = isDispatchCompleted(); // 先置成是否已分派任务完成的标记

				synchronized (orderLock) {
					if (orderList.size() > 0) {
						logger.info("获取一个待处理订单");
						order = orderList.remove(0);
						totalOrderCount++;
					}
				}

				if (order != null) { // 如果还有订单,说明还未完成处理,此时无论任务是否已分派完成都应该标记成未处理完成
					completed = false;
				} else {
					break;
				}

				boolean result = false;
				try {
					result = handleOrder(order,getWincode());
				} catch (Exception e) {
					logger.error("订单({})开奖处理失败", order.getId());
					logger.error(e.getMessage(), e);
				}

				if (result) {
					logger.info("订单({})开奖处理成功", order.getId());
					synchronized (successOrderCountLock) {
						successOrderCount++;
					}
				} else {
					logger.error("订单({})开奖处理失败", order.getId());
					synchronized (failedOrderCountLock) {
						failedOrderCount++;
					}
				}
				synchronized (waitingOrderCountLock) {
					waitingOrderCount--;
				}

				if (i >= orderUpdatePageSize) {
					break;
				}

				i++;
			}

			if (!running) {
				break;
			}

			if (completed) {
				logger.error("共处理了{}个订单", totalOrderCount);
				logger.info("开奖子线程任务处理完成，线程销毁");
				latch.countDown();
				break;
			} else {
				synchronized (this) {
					try {
						this.wait(100);
					} catch (InterruptedException e) {
						logger.error(e.getMessage(), e);
					}
				}
			}

		}
		logger.error("运行标志running=false,开奖子线程[{}]退出", Thread.currentThread().getName());
	}

	/**
	 * 执行订单开奖
	 * 
	 * @param order
	 * @return
	 * @throws Exception
	 */
	abstract protected boolean handleOrder(LotteryOrder order,String wincode) throws Exception;

	@Override
	public void addOrder(LotteryOrder order) {
		synchronized (orderLock) {
			this.orderList.add(order);
		}
		synchronized (waitingOrderCountLock) {
			waitingOrderCount++;
		}
	}

	@Override
	public void addOrderList(List<LotteryOrder> orderList) {
		synchronized (orderLock) {
			this.orderList.addAll(orderList);
		}
		synchronized (waitingOrderCountLock) {
			waitingOrderCount += orderList.size();
		}
	}

	@Override
	public long getFailedOrderCount() {
		synchronized (failedOrderCountLock) {
			return failedOrderCount;
		}
	}

	@Override
	public long getSuccessOrderCount() {
		synchronized (successOrderCountLock) {
			return successOrderCount;
		}
	}

	@Override
	public long getWaitingOrderCount() {
		synchronized (waitingOrderCountLock) {
			return waitingOrderCount;
		}
	}

	@Override
	public void setDispatchCompleted(boolean dispatchCompleted) {
		synchronized (dispatchCompletedLock) {
			this.dispatchCompleted = dispatchCompleted;
		}
	}

	protected boolean isDispatchCompleted() {
		synchronized (dispatchCompletedLock) {
			return dispatchCompleted;
		}
	}

	public int getOrderUpdatePageSize() {
		return orderUpdatePageSize;
	}

	public void setOrderUpdatePageSize(int orderUpdatePageSize) {
		this.orderUpdatePageSize = orderUpdatePageSize;
	}
	
	public String getWincode() {
		return wincode;
	}

}
