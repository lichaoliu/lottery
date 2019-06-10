package com.lottery.core.handler;

import com.lottery.common.cache.redis.RedisLock;
import com.lottery.common.cache.redis.SharedJedisPoolManager;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TopicName;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.*;
import com.lottery.core.cache.model.MerchantCacheModel;
import com.lottery.core.domain.LotteryChaseBuy;
import com.lottery.core.domain.merchant.Merchant;
import com.lottery.core.domain.merchant.MerchantNotice;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.service.LotteryCaseLotBuyService;
import com.lottery.core.service.LotteryChaseBuyService;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.merchant.MerchantNoticeService;
import com.lottery.core.service.merchant.MerchantOrderService;
import com.lottery.core.service.queue.QueueMessageSendService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service
public class OrderCheckerHandler {
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	@Autowired
	private TicketService ticketService;
	@Autowired
	private LotteryOrderService lotteryOrderService;
	@Autowired
	private QueueMessageSendService queueMessageSendService;
	@Autowired
	protected SharedJedisPoolManager shareJedisPoolManager;
	@Autowired
	protected MerchantOrderService merchantOrderService;
	@Autowired
	protected LotteryChaseBuyService lotteryChaseBuyService;
    @Autowired
	protected LotteryCaseLotBuyService lotteryCaseLotBuyService;

	@Autowired
	private MerchantCacheModel merchantCacheModel;

    @Value("${virtual.on:false}")
	private Boolean virtualOn;

	@Autowired
	private MerchantNoticeService merchantNoticeService;
	public void check(String orderId) {
		// 开始检票处理
		// 加锁以保证同时只处理一个订单
		// 锁的生命期60秒


		RedisLock lock = new RedisLock(this.shareJedisPoolManager, String.format("lottery_order_check_%s", orderId), 60);

		boolean hasLocked = false;

		try {
			// 等待获取锁30秒
			hasLocked = lock.tryLock(30000l, TimeUnit.MILLISECONDS);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		try {
			LotteryOrder lotteryOrder = lotteryOrderService.get(orderId);
			if (lotteryOrder == null) {
				logger.error("订单orderId={}不存在", orderId);
				return;
			}
			int status = lotteryOrder.getOrderStatus();
			if (status == OrderStatus.PRINTED.getValue()) {
				logger.info("订单[{}]已出票", orderId);
				updateMerchantOrder(lotteryOrder,status);
				return;
			}
			if (status != OrderStatus.PRINTING.getValue()) {
				logger.error("订单[{}]的状态为{},不做检票处理", new Object[] { orderId, OrderStatus.get(status) });
				return;
			}

			Long countL = ticketService.getCountByOrderId(orderId);
			Long countSucessL = ticketService.getCountByOrderIdAndStatus(orderId, TicketStatus.PRINT_SUCCESS.getValue());
			Long countFailL = ticketService.getCountByOrderIdAndStatus(orderId, TicketStatus.PRINT_FAILURE.getValue());
			Long countPrintL = ticketService.getCountByOrderIdAndStatus(orderId, TicketStatus.PRINTING.getValue());
			Long countCancel = ticketService.getCountByOrderIdAndStatus(orderId, TicketStatus.CANCELLED.getValue());
			int count = countL.intValue();
			int countSucess = countSucessL.intValue();
			int countFail = countFailL.intValue() + countCancel.intValue();
			int countPrint = countPrintL.intValue();
			if (countPrint > 0) {
				return;
			}
			if (count == 0) {
				logger.error("订单orderId={}下没有票，直接跳过", lotteryOrder.getId());
				sendJMS(QueueName.betFailuerUnfreeze.getValue(), lotteryOrder);//没有票,直接退款
				lotteryOrderService.updateOrderResultStatus(orderId, OrderResultStatus.unable_to_draw.value,null);
				updateMerchantOrder(lotteryOrder, OrderStatus.PRINTED_FAILED.value);
			} else {
				if (count == countSucess) {

					int flag = lotteryOrderService.updateOrderStatusPrintTime(orderId, OrderStatus.PRINTED.value, new Date());
					if (flag == 0) {
						return;
					}
					updateMerchantOrder(lotteryOrder, OrderStatus.PRINTED.value);
					String queueName = QueueName.betSuccessDeduct.getValue();
					sendJMS(queueName, lotteryOrder);

                    if (lotteryOrder.getBetType() == BetType.bet.getValue()
                            || lotteryOrder.getBetType() == BetType.chase.getValue()) {
                        //目前只针对普通投注和追号投注发送增加积分的订单队列
                        if (virtualOn != null && virtualOn == true) {
                            //如果该lottery配套有virtualLottery
                            sendVirtualJMS(QueueName.virtualJifen.getValue(), lotteryOrder);
                        }
                    }

                    return;
				}
				if (count == countFail) {
					logger.error("订单orderId={}的票送票失败，将进入解冻流程", lotteryOrder.getId());
					int orderStatus = OrderStatus.PRINTED_FAILED.getValue();

					if (countCancel.intValue() > 0) {
						orderStatus = OrderStatus.CANCELLED.getValue();
					}

					int flag = lotteryOrderService.updateOrderStatusAndFailCount(orderId,orderStatus,countFail);
					if (flag == 0) {
						return;
					}

					String queueName = QueueName.betFailuerUnfreeze.getValue();
					if (lotteryOrder.getBetType() == BetType.hemai.getValue()) {
						queueName = QueueName.hemaiFailuerUnfreeze.getValue();
					}

					sendJMS(queueName, lotteryOrder);
					updateMerchantOrder(lotteryOrder, orderStatus);
					lotteryOrderService.updateOrderResultStatus(orderId, OrderResultStatus.unable_to_draw.value,null);
					return;
				}
				if (countSucess > 0 && countFail > 0) {
					logger.error("订单orderId={}的票部分成功，订单总数:{}成功数量{},失败数量:{},将进入解冻流程", new Object[] { orderId, count, countSucess, countFail });
					int orderStatus = OrderStatus.HALF_PRINTED.getValue();
					updateMerchantOrder(lotteryOrder, orderStatus);
					int flag = lotteryOrderService.updateOrderStatusAndFailCount(orderId,orderStatus,countFail);
					if (flag == 0) {
						return;
					}
					String queueName = QueueName.betFailuerUnfreeze.getValue();
					if (lotteryOrder.getBetType() == BetType.hemai.getValue()) {
						queueName = QueueName.hemaiFailuerUnfreeze.getValue();
					}
					sendJMS(queueName, lotteryOrder);
				}
			}
		} catch (Exception e) {
			logger.error("订单OrderId={}检票出现异常", orderId);
			logger.error(e.getMessage(),e);

		} finally {
			if (hasLocked) {
				// 如果获取到锁, 执行完处理后解锁
				lock.unlock();
			}
		}


	}

	protected void sendJMS(String url, LotteryOrder lotteryOrder) {
		String orderId = null;
		try {
			orderId = lotteryOrder.getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderId", orderId);
			queueMessageSendService.sendMessage(url, map);
			Map<String, Object> orderMap = new HashMap<String, Object>();
			orderMap.put("orderId", orderId);
			queueMessageSendService.sendMessage(TopicName.orderresult.value, orderMap);

		} catch (Exception e) {
			logger.error("队列url={}发送内容orderId={}失败",  url, orderId , e);
			logger.error(e.getMessage(),e);
		}
	}

	protected void sendVirtualJMS(String url, LotteryOrder lotteryOrder) {
		String orderId = null;
		try {
			orderId = lotteryOrder.getId();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("orderid", orderId);
			map.put("userno", lotteryOrder.getUserno());
			map.put("amount", lotteryOrder.getAmount().intValue());
			map.put("lotteryType", lotteryOrder.getLotteryType());
			queueMessageSendService.sendMessage(url, map);
		} catch (Exception e) {
			logger.error("队列url={}发送内容orderId={}失败",  url, orderId , e);
			logger.error(e.getMessage(),e);
		}
	}

	/**
	 * b2b，追号处理
	 * */
	protected void updateMerchantOrder(LotteryOrder lotteryOrder, int orderStatus) {
		try{
			if (lotteryOrder.getBetType() == BetType.bet_merchant.value) {
				if (orderStatus == OrderStatus.PRINTED.value) {
					merchantOrderService.updateMerchantOrderStatusAndPrintTime(lotteryOrder.getId(), orderStatus, new Date());
					merchantnotice(lotteryOrder,orderStatus);
				} else {
					if(orderStatus==OrderStatus.HALF_PRINTED.value){
						orderStatus=OrderStatus.PRINTED_FAILED.value;
					}
					merchantOrderService.updateMerchantOrderStatus(lotteryOrder.getId(), orderStatus);
					merchantnotice(lotteryOrder,orderStatus);
				}
			}
			if (lotteryOrder.getBetType() == BetType.chase.value&&lotteryOrder.getChaseId()!=null){
				LotteryChaseBuy lotteryChaseBuy = lotteryChaseBuyService.getByChaseIdAndOrderId(lotteryOrder.getChaseId(), lotteryOrder.getId());
				if (lotteryChaseBuy != null) {
					lotteryChaseBuy.setOrderStatus(orderStatus);
					lotteryChaseBuyService.update(lotteryChaseBuy);
				}
			}

		}catch (Exception e){
			logger.error("出票状态修改出错", e);
		}

	}


	private void merchantnotice(LotteryOrder lotteryOrder, int orderStatus){
		try{

			Merchant merchant=merchantCacheModel.get(lotteryOrder.getUserno());
			if(merchant==null||merchant.getMerchantCode()==null){
				return;
			}
			if(merchant.getIsNotice()== YesNoStatus.yes.value&& StringUtils.isNoneBlank(merchant.getNoticeUrl())){
                MerchantNotice merchantNotice=merchantNoticeService.get(lotteryOrder.getId());
				if(merchantNotice!=null){
					return;
				}
				 merchantNotice=new MerchantNotice();
				merchantNotice.setOrderid(lotteryOrder.getId());
				merchantNotice.setLotteryType(lotteryOrder.getLotteryType());
				merchantNotice.setPhase(lotteryOrder.getPhase());
				merchantNotice.setOrderStatus(orderStatus);
				merchantNotice.setOrderResultStatus(lotteryOrder.getOrderResultStatus());
				merchantNotice.setMerchantCode(lotteryOrder.getUserno());
				merchantNotice.setMerchantNo(lotteryOrder.getAgentId());
                merchantNotice.setTotalPrize(BigDecimal.ZERO);
				merchantNotice.setPrintTime(new Date());
				merchantNotice.setOrderStatusNotice(NoticeStatus.no_notice.value);
				merchantNotice.setPrizeStatusNotice(NoticeStatus.no_notice.value);
				merchantNotice.setBatchId("0");
				merchantNoticeService.save(merchantNotice);
			}

		}catch (Exception e){
			logger.error("保存通知出错",e);
		}
	}

}
