package com.lottery.retrymodel.prize.error;

import com.lottery.common.PageBean;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.lottery.OrderStatus;
import com.lottery.common.contains.lottery.TicketStatus;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.retrymodel.ApiRetryCallback;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrderRefundRetryCallback extends ApiRetryCallback<Object> {

	private Integer lotteryType;
	private String phase;
	private LotteryOrderService lotteryOrderService;

	private TicketService ticketService;
	private QueueMessageSendService queueMessageSendService;
	private int max = 15;

	@Override
	protected Object execute() throws Exception {
		if (lotteryType == null || phase == null || lotteryOrderService == null||ticketService==null) {
			throw new Exception("参数不全");
		}
		for (LotteryOrder lotteryOrder : getRefund(lotteryType, phase)) {
			String orderId=lotteryOrder.getId();
			if(lotteryOrder.getOrderStatus()==OrderStatus.PRINTED.value){
				sendJms(QueueName.betRefund,orderId);
			}else{
				List<Ticket> ticketList = ticketService.getByorderId(orderId);
				for (Ticket ticket : ticketList) {
					if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
						ticketService.updateTicketStatus(TicketStatus.CANCELLED.value, ticket.getId());
					}
				}
				sendJms(QueueName.betChercher,orderId);
			}
			
		}
		return null;
	}

	protected void sendJms(QueueName queueName,String orderId) {
		try {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			queueMessageSendService.sendMessage(queueName, map);
		} catch (Exception e) {
			logger.error("发送jms消息出错", e);
		}
	}

	protected List<LotteryOrder> getRefund(int lotteryType, String phase) {
		List<LotteryOrder> allOrderList = new ArrayList<LotteryOrder>();
		PageBean<LotteryOrder> pageBean = new PageBean<LotteryOrder>();
		pageBean.setMaxResult(max);
		int page = 1;
		while (true) {
			List<LotteryOrder> orders = null;
			pageBean.setPageIndex(page);
			try {
				orders = lotteryOrderService.getByLotteryTypeAndPhase(lotteryType, phase, pageBean);
			} catch (Exception e) {
				logger.error("取订单出错", e);
				break;
			}
			if (orders != null && orders.size() > 0) {
				allOrderList.addAll(orders);
				if (orders.size() < max) {
					logger.info("读取到的方案列表不足一页，已读取结束");
					break;
				}
			} else {
				break;
			}
			page++;
		}

		return allOrderList;

	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public LotteryOrderService getLotteryOrderService() {
		return lotteryOrderService;
	}

	public void setLotteryOrderService(LotteryOrderService lotteryOrderService) {
		this.lotteryOrderService = lotteryOrderService;
	}

	public QueueMessageSendService getQueueMessageSendService() {
		return queueMessageSendService;
	}

	public void setQueueMessageSendService(QueueMessageSendService queueMessageSendService) {
		this.queueMessageSendService = queueMessageSendService;
	}

	public TicketService getTicketService() {
		return ticketService;
	}

	public void setTicketService(TicketService ticketService) {
		this.ticketService = ticketService;
	}

	
}
