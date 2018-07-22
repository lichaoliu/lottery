package com.lottery.ticket.checker.thread.timeout;

import com.lottery.common.PageBean;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.*;
import com.lottery.common.contains.lottery.coupon.UserCouponStatus;
import com.lottery.common.thread.AbstractThreadRunnable;
import com.lottery.core.domain.LotteryPhase;
import com.lottery.core.domain.coupon.UserCoupon;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.handler.LotteryPhaseHandler;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.coupon.UserCouponService;
import com.lottery.core.service.queue.QueueMessageSendService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;

/**
 * 订单超时回收
 * */
public class TicketTimeOutHandler extends AbstractThreadRunnable {
	private long inivit = 30000;
	@Autowired
	protected LotteryOrderService los;
	@Autowired
	private TicketService ticketService;
	@Autowired
	private QueueMessageSendService queueMessageSendService;
	@Autowired
	protected LotteryPhaseHandler phaseHandler;
	@Autowired
	protected UserCouponService userCouponService;
	@Autowired
	private TicketBatchService ticketBatchService;

	private static List<Integer> statusList = new ArrayList<Integer>();
	private int max = 20;
	static {
		statusList.add(OrderStatus.PRINTING.value);
		statusList.add(OrderStatus.NOT_SPLIT.value);
		statusList.add(OrderStatus.PRINT_WAITING.value);
		
	}

	@Override
	protected void executeRun() {
		while (running) {
			
			for(LotteryType lotteryType:LotteryType.get()){
				Calendar deadlineCalendar = Calendar.getInstance();
				deadlineCalendar.add(Calendar.MILLISECOND, (-1)*(int)getEndTicketTimeOut(lotteryType.value));

				PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
				pageBean.setMaxResult(max);
				pageBean.setTotalFlag(false);
				int page = 1;
		        try{
		    	
					while(true){
						pageBean.setPageIndex(page);
						List<LotteryOrder> orders=null;
						try{
							orders=los.getByOrderStatusAndDealLine(lotteryType.value, statusList, deadlineCalendar.getTime(), pageBean);
						}catch(Exception e){
							logger.error("回收订单出错",e);
							break;
						}
						if (orders != null && orders.size() > 0) {
							cancle(orders);
							if (orders.size() < max) {
								logger.info("读取到的订单列表不足一页，已读取结束");
								break;
							}
						} else {
							logger.info("已读取完所有超时的订单,lotteryType={}",lotteryType);
							break;
						}
						// 准备读取下一页
						page ++;
					}
					
		        }catch(Exception e){
		        	logger.error("彩种:{},回收失败",lotteryType,e);
		        }
		
				
			}
			try {
				synchronized (this) {
					wait(inivit);
				}
			} catch (Exception e) {
				logger.error(e.getMessage(), e);
			}
		}
	}

	protected void cancle(List<LotteryOrder> orderList){
		for(LotteryOrder lotteryOrder:orderList){
			String orderId=lotteryOrder.getId();
			String wincode=null;
		    LotteryPhase lotteryPhase=phaseHandler.get(lotteryOrder.getLotteryType(),lotteryOrder.getPhase());
		    if(lotteryPhase!=null){
		    	wincode=lotteryPhase.getWincode();
		    }
			if(lotteryOrder.getOrderStatus()==OrderStatus.NOT_SPLIT.value){
				los.updateOrderStatus(OrderStatus.CANCELLED.value, orderId);
				logger.error("订单{}未拆票,做撤单处理",orderId);
				
				los.updateOrderResultStatus(orderId, OrderResultStatus.unable_to_draw.value,wincode);
				//sendJMS(QueueName.betSplitQueue.value,orderId);
				sendJMS(QueueName.betFailuerUnfreeze.value,orderId);
			}else if (lotteryOrder.getOrderStatus()==OrderStatus.PRINT_WAITING.value){
				los.updateOrderStatus(OrderStatus.UNPAY_OBSOLETE.value, orderId);
				los.updateOrderResultStatus(orderId, OrderResultStatus.unable_to_draw.value,wincode);
				//sendJMS(QueueName.betSplitQueue.value,orderId);
				//将优惠卷改为未使用
				if(lotteryOrder.getPreferentialAmount().intValue()>0&& StringUtils.isNotBlank(lotteryOrder.getChaseId())){
					UserCoupon userCoupon=userCouponService.getWithLock(lotteryOrder.getChaseId());
					if(userCoupon!=null){
						userCoupon.setUpdateTime(new Date());
						userCoupon.setStatus(UserCouponStatus.unuse.getValue());
						userCouponService.update(userCoupon);
					}
				}
			}else{

				List<Ticket> ticketList=ticketService.getByorderId(orderId);
				if(ticketList!=null){
					for (Ticket ticket : ticketList) {
						if(ticket.getStatus()!=TicketStatus.PRINT_SUCCESS.value||ticket.getStatus()!=TicketStatus.CANCELLED.value){
							ticketService.updateTicketStatus(TicketStatus.CANCELLED.value, ticket.getId());
							ticket.setStatus(TicketStatus.CANCELLED.value);
							ticket.setFailureType(TicketFailureType.PRINT_EXPIRED.value);
							ticket.setFailureMessage("票已过期自动撤单");
							ticketBatchService.updateStatus(ticket.getBatchId(), TicketBatchStatus.SEND_SUCCESS.value);
							logger.error("票({})到截止时间未出票,做撤单处理",ticket.getId());
						}
					}
				}
				sendJMS(QueueName.cancelChercher.value,orderId);
			}
			
		}
	}
	
	protected long  getEndTicketTimeOut(int lotteryType){
		long timeount=phaseHandler.getEndTicketTimeout(lotteryType);
		if(timeount==0){
			timeount=180000;
		}
		return timeount;
	}
	
	
	
	protected void sendJMS(String queueName,String orderId){
		try{
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			queueMessageSendService.sendMessage(queueName, map);
		}catch(Exception e){
			
		}
	}

}
