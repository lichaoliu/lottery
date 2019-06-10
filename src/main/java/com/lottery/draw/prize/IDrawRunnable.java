package com.lottery.draw.prize;

import java.util.List;

import com.lottery.common.thread.IThreadRunnable;
import com.lottery.core.domain.ticket.LotteryOrder;

/**
 * 开奖线程接口
 * @author fengqinyun
 *
 */
public interface IDrawRunnable extends IThreadRunnable {
	
	/**
	 * 单个添加需开奖订单
	 * @param order
	 */
	public void addOrder(LotteryOrder order);
	
	/**
	 * 批量添加需开奖订单
	 * @param orderList
	 */
	public void addOrderList(List<LotteryOrder> orderList);
	
	/**
	 * 得到等待开奖订单数量
	 * @return
	 */
	public long getWaitingOrderCount();
	
	/**
	 * 得到成功开奖订单数量
	 * @return
	 */
	public long getSuccessOrderCount();
	
	/**
	 * 得到失败开奖订单数量
	 * @return
	 */
	public long getFailedOrderCount();
	
	/**
	 * 订单是否分配完毕
	 * @param dispatchCompleted
	 * @return
	 */
	public void setDispatchCompleted(boolean dispatchCompleted);
	
	
	/**
	 * 获取开奖号码
	 * @return
	 */
	public String getWincode();

}
