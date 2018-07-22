package com.lottery.core.handler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import javax.annotation.Resource;

import com.lottery.common.contains.lottery.*;
import com.lottery.core.service.account.UserAccountService;
import com.lottery.retrymodel.prize.error.OrderFailureCompensateCallback;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.QueueName;
import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.caselot.CaseLotBuyState;
import com.lottery.common.contains.lottery.caselot.CaseLotDisplayState;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.IdGeneratorService;
import com.lottery.core.domain.LotteryCaseLot;
import com.lottery.core.domain.LotteryCaseLotBuy;
import com.lottery.core.domain.terminal.Terminal;
import com.lottery.core.domain.terminal.TerminalConfig;
import com.lottery.core.domain.ticket.LotteryOrder;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.domain.ticket.TicketAllotLog;
import com.lottery.core.domain.ticket.TicketBatch;

import com.lottery.core.service.LotteryCaseLotBuyService;
import com.lottery.core.service.LotteryCaseLotService;
import com.lottery.core.service.LotteryOrderService;
import com.lottery.core.service.LotteryPhaseService;
import com.lottery.core.service.OrderSplitService;
import com.lottery.core.service.PrizeService;
import com.lottery.core.service.TerminalConfigService;
import com.lottery.core.service.TerminalService;
import com.lottery.core.service.TicketAllotLogService;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.service.TicketService;
import com.lottery.core.service.merchant.MerchantOrderService;
import com.lottery.core.service.queue.QueueMessageSendService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.retrymodel.ApiRetryTaskExecutor;
import com.lottery.retrymodel.order.OrderStatusBatchProcessCallback;
import com.lottery.retrymodel.prize.error.OrderRefundRetryCallback;
import com.lottery.retrymodel.ticket.TicketChangeTerminalIdCallback;
import com.lottery.retrymodel.ticket.TicketCheckCallback;
import com.lottery.retrymodel.ticket.VenderTicketExchangeCallback;
import com.lottery.retrymodel.ticket.VenderTicketPrizeCheckCallback;
import com.lottery.ticket.allotter.ITicketAllotWorker;
import com.lottery.ticket.vender.exchange.IVenderTicketExchange;
import com.lottery.ticket.vender.prizecheck.IVenderTicketPrizeCheck;

@Service
public class PrizeErrorHandler {
    protected final Logger logger = LoggerFactory.getLogger(getClass().getName());
    @Autowired
    LotteryOrderService lotteryOrderService;
    @Autowired
    private QueueMessageSendService queueMessageSendService;
    @Autowired
    private PrizeService prizeService;
  
    @Autowired
    private TerminalConfigService terminalConfigService;
    @Resource(name = "apiRetryTaskExecutor")
    protected ApiRetryTaskExecutor apiReryTaskExecutor;
    @Autowired
    private LotteryCaseLotService lotteryCaseLotService;
    @Autowired
    private TerminalService terminalService;
    @Autowired
    private TicketService ticketService;
    @Autowired
    private IdGeneratorService idGeneratorService;
    @Autowired
    private TicketBatchService ticetBatchService;
    @Autowired
    private TicketAllotLogService ticketAllotLogService;
    @Autowired
    protected ITerminalSelector terminalSelector;
    @Autowired
    protected VenderConfigHandler venderConfigService;

    @Autowired
    protected LotteryCaseLotBuyService caseLotBuyService;
    @Autowired
    protected LotteryPhaseService lotteryPhaseService;
    @Autowired
    protected MerchantOrderService merchantOrderService;
    @Autowired
    protected  OrderSplitService orderSplitService;

    @Resource(name = "executorBinder")
    protected Map<LotteryType, ITicketAllotWorker> executorBinder;

    @Autowired
    private UserAccountService userAccountService;



    public void prizeRecovery(String orderId) {
        LotteryOrder lotteryOrder = lotteryOrderService.get(orderId);
        prizeRecovery(lotteryOrder);
    }

    public void prizeRecovery(LotteryOrder lotteryOrder) {
        String caselotid = prizeService.prizeRecovery(lotteryOrder);
        if (StringUtils.isBlank(caselotid)) {
            return;
        }

        try {
            LotteryCaseLot caselot = lotteryCaseLotService.get(caselotid);
            if (caselot == null) {
                logger.error("合买订单{}不存在", caselotid);
                return;
            }
            if (caselot.getIsExchanged() == YesNoStatus.no.value) {
                logger.error("合买订单{}已经是未派奖状态", caselotid);
                return;
            }
            caselot.setWinBigAmt(0l);
            caselot.setWinPreAmt(0l);
            caselot.setDisplayState(CaseLotDisplayState.success.value());
            caselot.setDisplayStateMemo(CaseLotDisplayState.success.memo());
            caselot.setIsExchanged(YesNoStatus.no.value);
            lotteryCaseLotService.update(caselot);
            List<LotteryCaseLotBuy> caseLotBuys = caseLotBuyService.findCaseLotBuysByCaselotIdAndState(caselotid, CaseLotBuyState.success.value());
            for (LotteryCaseLotBuy caseLotBuy : caseLotBuys) {
                lotteryCaseLotService.cancelCaseLotbuyPrize(caseLotBuy);
            }
            List<LotteryCaseLotBuy> caselotBuys = caseLotBuyService.findCaseLotBuysByCaselotIdAndState(caselotid, CaseLotBuyState.success.value());
            for (LotteryCaseLotBuy caseLotBuy : caselotBuys) {
                lotteryCaseLotService.cancelCaseLotBuyCommisionPrize(caseLotBuy);
            }

        } catch (Exception e) {
            logger.error("合买({})重新派奖出错", lotteryOrder.getId(), e);
        }
    }


    /**
     * 根据终端类型,彩种,玩法查找可用终端id
     *
     * @param terminalType 终端类型
     * @param lotteryType  彩种
     * @param playType     玩法
     */
    protected Long getEnableId(int terminalType, int lotteryType, int playType) {
        List<Long> enableList = new ArrayList<Long>();
        try {
            List<TerminalConfig> list = terminalConfigService.getByTerminalTypeAndLotteryType(terminalType, lotteryType, playType);
            if (list != null) {
                for (TerminalConfig terminalConfig : list) {
                    // if (terminalConfig.getIsEnabled() ==
                    // YesNoStatus.yes.getValue() &&
                    // terminalConfig.getIsPaused()==YesNoStatus.no.getValue())
                    // {
                    Long id = terminalConfig.getTerminalId();
                    Terminal terminal = terminalService.get(id);
                    if (terminal != null) {
                        if (terminal.getIsEnabled() == YesNoStatus.yes.getValue() && terminal.getIsPaused() == YesNoStatus.no.getValue()) {
                            enableList.add(id);
                        }
                    }
                    // } //先不判断状态
                }
            } else {
                logger.error("终端:{},彩种:{},玩法:{}没有可用终端", new Object[]{TerminalType.get(terminalType), lotteryType, playType});
            }

        } catch (Exception e) {
            logger.error("获取可以终端出错", e);
        }

        if (enableList.size() > 0) {
            return enableList.get(0);
        }
        return null;
    }

    /**
     * 根据终端号,票号指定终端终端出票
     * @param ticketId     彩种
     * @param terminalId     终端号
     */
    public boolean changeTerminal(String ticketId, Long terminalId) {
        try {
            Ticket ticket = ticketService.getTicket(ticketId);
            if (ticket == null) {
                logger.error("id={}的票为空", ticketId);
                return false;
            }
            if (ticket.getStatus() == TicketStatus.PRINT_SUCCESS.getValue()) {
                logger.error("id={}的票为成功状态", ticketId);
                return false;
            }

            List<Ticket> ticketList = new ArrayList<Ticket>();
            ticketList.add(ticket);

            TicketBatch ticketBatch = ticketallot(ticket.getLotteryType(), ticket.getPhase(), ticket.getTerminateTime(), terminalId, null, ticketList);
            logger.error("票ticketId={},切换终端成功,从批次:{},更换为批次:{}", new Object[]{ticketId, ticket.getBatchId(), ticketBatch.getId()});
            return true;
        } catch (Exception e) {

            logger.error("重新转换终端出错", e);
            return false;
        }

    }

    /**
     * 批次转换指定终端
     *
     * @param terminalType 终端类型
     * @param batchId      批次号
     */
    public boolean changeTerminal(int terminalType, String batchId) {
        try {
            TicketBatch batch = ticetBatchService.get(batchId);
            if (batch == null) {
                return false;
            }
            int lotteryType = batch.getLotteryType();
            Long id = getEnableId(terminalType, lotteryType, PlayType.mix.value);
            if (id == null) {
                logger.error("批次{}无可用终端", batchId);
                return false;
            }
            List<Ticket> ticketList = ticketService.getByBatchId(batchId);
            List<Ticket> enableList = new ArrayList<Ticket>();
            for (Ticket ticket : ticketList) {
                if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
                    enableList.add(ticket);
                }
            }
            if (enableList.size() == 0)
                return false;
            TicketBatch ticketBatch = ticketallot(lotteryType, batch.getPhase(), batch.getTerminateTime(), id, null, enableList);
            logger.error("切换终端成功,从批次:[{}]更换为批次:[{}]", new Object[]{batchId, ticketBatch.getId()});
            return true;
        } catch (Exception e) {
            logger.error("批次:{}在终端{}重新分票出错", new Object[]{batchId, TerminalType.get(terminalType)});
            logger.error("重新转换终端出错", e);
            return false;
        }

    }


    // 撤单
    public int cancelOrder(String orderId) throws Exception {
        LotteryOrder order = lotteryOrderService.get(orderId);
        if (order == null) {
            return 0;
        }
        int orderStatus = order.getOrderStatus();
        if (orderStatus == OrderStatus.NOT_SPLIT.value) {
            lotteryOrderService.updateOrderStatus(OrderStatus.UNPRINTED_OBSOLETE.value, orderId);
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", orderId);
            queueMessageSendService.sendMessage(QueueName.betFailuerUnfreeze, map);
        } else if (orderStatus == OrderStatus.PRINT_WAITING.value) {
            lotteryOrderService.updateOrderStatus(OrderStatus.UNPAY_OBSOLETE.value, orderId);
        } else {
            List<Ticket> ticketList = ticketService.getByorderId(orderId);
            int total = 0;
            for (Ticket ticket : ticketList) {
                if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
                    ticketService.updateTicketStatus(TicketStatus.CANCELLED.value, ticket.getId());
                    total++;
                }
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("orderId", orderId);
            queueMessageSendService.sendMessage(QueueName.betChercher, map);
            return total;
        }
        return 0;


    }

    public void ordrerRefund(String orderid) {

    }

    /**
     * 用户退款操作
     */
    public void refundOrder(int lotteryType, String phase) {
        OrderRefundRetryCallback refundCallback = new OrderRefundRetryCallback();
        refundCallback.setLotteryType(lotteryType);
        refundCallback.setPhase(phase);
        refundCallback.setLotteryOrderService(lotteryOrderService);
        refundCallback.setQueueMessageSendService(queueMessageSendService);
        refundCallback.setName("退款操作");
        refundCallback.setTicketService(ticketService);

        apiReryTaskExecutor.invokeAsync(refundCallback);
    }

    /**
     * 将票改为成功状态
     *
     * @throws Exception
     */
    public void upateOrderSuccess(String orderId) throws Exception {

        LotteryOrder lotteryOrder = lotteryOrderService.get(orderId);
        if (lotteryOrder == null) {
            return;
        }
        List<Ticket> ticketList = ticketService.getByorderId(orderId);
        for (Ticket ticket : ticketList) {

            if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
                ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
                ticket.setTerminalType(TerminalType.T_ZHONGYING.value);
                ticket.setTerminalId(0l);
                ticket.setFailureMessage("手动设置成功");
                ticket.setPrintTime(new Date());
                LotteryType lotteryType = LotteryType.get(ticket.getLotteryType());
                if (LotteryType.getJclq().contains(lotteryType) || LotteryType.getJczq().contains(lotteryType)) {

                    String odds = prizeService.simulateOdd(ticket.getContent(), lotteryType);
                    ticket.setPeilv(odds);
                }
                ticketService.update(ticket);
            }
        }
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("orderId", orderId);
        lotteryOrderService.updateOrderStatusPrintTime(orderId, OrderStatus.PRINTED.value, new Date());
        if (lotteryOrder.getBetType() == BetType.bet_merchant.value) {
            merchantOrderService.updateMerchantOrderStatusAndPrintTime(orderId, OrderStatus.PRINTED.value, new Date());
        }
        queueMessageSendService.sendMessage(QueueName.betSuccessDeduct, map);

    }

    /**
     * 批量修改票的状态
     */
    public void updateSuccessList(String orders) {
        String[] orderlist = org.apache.commons.lang3.StringUtils.split(orders, ",");
        for (String orderId : orderlist) {
            try {
                upateOrderSuccess(orderId);
            } catch (Exception e) {
                logger.error("手动处理订单:{}失败", orderId, e);
            }
        }
    }

    /**
     * 批量修改票的状态
     *
     * @param tickets 票批量
     * @param useid   (1,成功;2,撤单;3,重新分配;4,重新检票;5,优先送票;6,对方成功;7,订单检票;8,票合并)
     */
	public void updateTicketListStatus(String tickets, int useid)
			throws Exception {
		String[] ticketlist = org.apache.commons.lang3.StringUtils.split(
                tickets, ",");

		if (useid == 8) {
			mergeTicket(ticketlist);
		} else {
			for (String ticketid : ticketlist) {
				Ticket ticket = ticketService.getTicket(ticketid);
				if (ticket != null) {
					if (useid == 1) {
						if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
							ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
							ticket.setTerminalType(TerminalType.T_ZHONGYING.value);
							ticket.setTerminalId(0l);
							ticket.setFailureMessage("手动设置成功");
							LotteryType lotteryType = LotteryType.get(ticket.getLotteryType());
							if (LotteryType.getJclq().contains(lotteryType)|| LotteryType.getJczq().contains(lotteryType)) {

								String odds = prizeService.simulateOdd(ticket.getContent(), lotteryType);
								ticket.setPeilv(odds);
							}

                            if(lotteryType==LotteryType.JC_GUANJUN||lotteryType==LotteryType.JC_GUANYAJUN){

                                String odds = prizeService.simulateGuanyajunOdd(ticket.getContent(), lotteryType,ticket.getPhase());
                                ticket.setPeilv(odds);
                            }
							ticket.setPrintTime(new Date());
                            ticetBatchService.updateStatus(ticket.getBatchId(),TicketBatchStatus.SEND_SUCCESS.value);

							ticketService.update(ticket);
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderId", ticket.getOrderId());
						queueMessageSendService.sendMessage(QueueName.betChercher, map);
					}
					if (useid == 2) {
						if (ticket.getStatus() != TicketStatus.CANCELLED.value) {
							ticket.setStatus(TicketStatus.CANCELLED.value);
							ticket.setFailureType(TicketFailureType.PRINT_FAILURE.value);
							ticket.setFailureMessage("未出票人工撤单");
							ticketService.update(ticket);
						}
						Map<String, Object> map = new HashMap<String, Object>();
						map.put("orderId", ticket.getOrderId());
						queueMessageSendService.sendMessage(QueueName.betChercher, map);
					}
					if (useid == 3) {
						if (ticket.getTerminalId() == null) {
							ticket.setStatus(TicketStatus.UNALLOTTED.value);
							ticketService.update(ticket);
						} else {
							ITicketAllotWorker allotWorker = getAllotExecutor(LotteryType
									.getLotteryType(ticket.getLotteryType()));
							if (allotWorker != null) {
								Long newterminalId = allotWorker
										.executeWithFailureCheck(ticket);
								if (newterminalId == null) {
									ticket.setStatus(TicketStatus.UNALLOTTED.value);
									ticketService.update(ticket);
								}
							} else {
								ticket.setStatus(TicketStatus.UNALLOTTED.value);
								ticketService.update(ticket);
							}
						}
					}


                    if (useid == 4) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("ticketId", ticketid);
                        queueMessageSendService.sendMessage(QueueName.ticketCheck, map);
                    }
                    if (useid == 5) {
                        if (ticket.getBatchId() != null) {
                            Map<String, Object> map = new HashMap<String, Object>();
                            map.put("batchId", ticket.getBatchId());
                            queueMessageSendService.sendMessage(QueueName.ticketSend, map);
                        }
                    }
                    if (useid == 6) {
                        if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
                            ticket.setStatus(TicketStatus.PRINT_SUCCESS.value);
                            ticket.setFailureMessage("对方成功,手动设置");
                            LotteryType lotteryType = LotteryType
                                    .get(ticket.getLotteryType());
                            if (LotteryType.getJclq().contains(lotteryType)|| LotteryType.getJczq().contains(lotteryType)) {

                                String odds = prizeService.simulateOdd(ticket.getContent(), lotteryType);
                                
                                if(StringUtil.isEmpty(odds)) {
                                	throw new LotteryException(ErrorCode.betcode_jingcai_odds_error, "模拟赔率失败");
                                }
                                ticket.setPeilv(odds);
                            }
                            ticket.setPrintTime(new Date());
                            ticketService.update(ticket);
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("orderId", ticket.getOrderId());
                        queueMessageSendService.sendMessage(QueueName.betChercher, map);
                    }
                    if (useid == 7) {
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("orderId", ticket.getOrderId());
                        queueMessageSendService.sendMessage(QueueName.betChercher, map);
                    }
                    if(useid==9){//票限号撤单
                        if (ticket.getStatus() != TicketStatus.CANCELLED.value) {
                            ticket.setStatus(TicketStatus.CANCELLED.value);
                            ticket.setFailureType(TicketFailureType.PRINT_LIMITED.value);
                            ticket.setFailureMessage("限号手动撤单");
                            ticketService.update(ticket);
                        }
                        Map<String, Object> map = new HashMap<String, Object>();
                        map.put("orderId", ticket.getOrderId());
                        queueMessageSendService.sendMessage(QueueName.betChercher, map);
                    }
				}
			}
		}
	}



    public void mergeTicket(String[] ticketlist) {
        List<Ticket> ticketList = new ArrayList<Ticket>();
        for (String tickeId : ticketlist) {
            Ticket ticket = ticketService.getTicket(tickeId);
            if (ticket != null) {
                if (ticket.getAmount().intValue() == 200 && ticket.getMultiple() == 1 && String.valueOf(ticket.getPlayType()).endsWith("01")) {
                    ticketList.add(ticket);
                }
            }
        }
        if (ticketList.size()==0){
            return;
        }
        int batchcount = 5;
        int len = 0;
        int ticketCount = ticketList.size();
        if (ticketCount % batchcount == 0) {
            len = ticketCount / batchcount;
        } else {
            len = ticketCount / batchcount + 1;
        }
        for (int i = 0; i < len; i++) {
            List<Ticket> ticketBatchList = null;
            if (((i * batchcount) + batchcount) < ticketCount) {
                ticketBatchList = ticketList.subList(i * batchcount, i * batchcount +
                        batchcount);
            } else {
                ticketBatchList = ticketList.subList(i * batchcount, ticketCount);
            }

            if (ticketBatchList!=null&&ticketBatchList.size()>0){
                try {
                    orderSplitService.ticketMerge(ticketBatchList);
                }catch (Exception e){
                    logger.error("票合并出错",e);
                }

            }


        }
        }

        /**
         * 票切换终端
         *
         * @param fromId
         *            需要切换的终端
         * @param toId
         *            切到目的终端
         * @param lotteryType
         *            期号
         * */

    public int changeTerminalId(Long fromId, Long toId, int lotteryType, int minute) {
        TicketChangeTerminalIdCallback ticketChangeCallback = new TicketChangeTerminalIdCallback();
        ticketChangeCallback.setFromId(fromId);
        ticketChangeCallback.setLotteryType(lotteryType);
        ticketChangeCallback.setIdGeneratorService(idGeneratorService);
        ticketChangeCallback.setTerminalService(terminalService);
        ticketChangeCallback.setTicketService(ticketService);
        ticketChangeCallback.setToId(toId);
        ticketChangeCallback.setTicketAllotLogService(ticketAllotLogService);
        ticketChangeCallback.setName("批量从terminalId:" + fromId + ",转到terminalId:" + toId);
        ticketChangeCallback.setRetry(1);
        ticketChangeCallback.setMinute(minute);
        apiReryTaskExecutor.invokeAsync(ticketChangeCallback);
        return 0;
    }

    public TicketBatch ticketallot(int lotteryType, String phaseNo, Date deadline, Long terminalId, Integer playType, List<Ticket> ticketList) {
        TicketBatch ticketBatch = new TicketBatch();
        ticketBatch.setCreateTime(new Date());
        ticketBatch.setPhase(phaseNo);
        ticketBatch.setTerminateTime(deadline);
        ticketBatch.setLotteryType(lotteryType);
        ticketBatch.setTerminalId(terminalId);
        ticketBatch.setId(idGeneratorService.getBatchTicketId());
        ticketBatch.setStatus(TicketBatchStatus.SEND_WAITING.value);
        ticketBatch.setPlayType(playType == null ? PlayType.mix.getValue() : playType);
        Terminal terminal = terminalSelector.getTerminal(terminalId);
        if (terminal != null)
            ticketBatch.setTerminalTypeId(terminal.getTerminalType());
        ticketService.saveAllottedTicketsAndBatch(ticketList, ticketBatch);

        for (Ticket ticket : ticketList) {
            TicketAllotLog allotLog = new TicketAllotLog();
            allotLog.setBatchId(ticketBatch.getId());
            allotLog.setCreateTime(new Date());
            allotLog.setTerminalId(ticketBatch.getTerminalId());
            allotLog.setTicketId(ticket.getId());
            ticketAllotLogService.save(allotLog);
        }
        return ticketBatch;
    }

    /**
     * 订单指定终端分票
     *
     * @param orderId    订单号
     * @param terminalId 终端号
     */
    public void changeOrderIdTerminal(String orderId, Long terminalId) {

        LotteryOrder lotteryOrder = lotteryOrderService.get(orderId);
        if (lotteryOrder == null) {
            return;
        }
        List<Ticket> ticketList = ticketService.getByorderId(orderId);
        if (ticketList == null) {
            return;
        }
        List<Ticket> enableList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            if (ticket.getStatus() != TicketStatus.PRINT_SUCCESS.value) {
                enableList.add(ticket);
            }
        }
        if (enableList.size() == 0)
            return;

        this.ticketallot(lotteryOrder.getLotteryType(), lotteryOrder.getPhase(), lotteryOrder.getDeadline(), terminalId, null, enableList);
    }

    /**
     * 指定彩种，往指定终端送票
     *
     * @param lotteryType 彩种
     * @param terminalId  终端
     * @param max         最大条数
     */

    public void lotteryTypeChangeTerminal(Integer lotteryType, Long terminalId, int max) {
        List<Ticket> ticketList = null;
        if (lotteryType == LotteryType.ALL.value) {
            ticketList = ticketService.getByLotteryTypeAndStatus(null, TicketStatus.UNALLOTTED.value, max);
        } else {
            ticketList = ticketService.getByLotteryTypeAndStatus(lotteryType, TicketStatus.UNALLOTTED.value, max);
        }

        if (ticketList == null || ticketList.size() == 0)
            return;

        Ticket ticket = ticketList.get(0);
        ticketallot(ticket.getLotteryType(), ticket.getPhase(), ticket.getTerminateTime(), terminalId, null, ticketList);
    }

    /**
     * 根据订单状态修改订单
     */

    public void orderBatchProcess(int lotteryType, int orderStatus, String queueName) {
        OrderStatusBatchProcessCallback orderStatusBatchProcessCallback = new OrderStatusBatchProcessCallback();
        orderStatusBatchProcessCallback.setLotteryOrderService(lotteryOrderService);
        orderStatusBatchProcessCallback.setOrderStatus(orderStatus);
        orderStatusBatchProcessCallback.setQueueName(queueName);
        orderStatusBatchProcessCallback.setQueueMessageSendService(queueMessageSendService);
        orderStatusBatchProcessCallback.setName("根据订单状态批量修改");
        orderStatusBatchProcessCallback.setRetry(1);
        orderStatusBatchProcessCallback.setTicketService(ticketService);
        orderStatusBatchProcessCallback.setLotteryType(lotteryType);
        apiReryTaskExecutor.invokeAsync(orderStatusBatchProcessCallback);

    }

    /**
     * 票兑奖
     * */
    @Resource(name="venderExchangeMap")
    protected Map<TerminalType,IVenderTicketExchange> venderExchangeMap;


    public  void ticketExchange(Integer lotteryType,String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId,String ids,Date startTime,Date endTime){
        if(StringUtil.isNotEmpt(ids)){
            this.exchangeTicket(lotteryType, phase, ticketResultStatus, agencyExchange, terminalId, ids,startTime,endTime);
        }else{
            if (lotteryType==null){
                this.exchangeTicket(lotteryType, phase, ticketResultStatus, agencyExchange, terminalId, ids,startTime,endTime);
            }else{
                for(int lotterytype:LotteryType.getLotteryTypeList(lotteryType)){
                    this.exchangeTicket(lotterytype, phase, ticketResultStatus, agencyExchange, terminalId, ids,startTime,endTime);
                }
            }

        }
    }

    public void exchangeTicket(Integer lotteryType,String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId,String ids,Date startTime,Date endTime){
        List<String> ticketIdList=null;
        if(StringUtil.isNotEmpt(ids)){
        	ticketIdList = Arrays.asList(org.apache.commons.lang3.StringUtils.split(ids,","));
        }
        VenderTicketExchangeCallback venderExchangeCallback=new VenderTicketExchangeCallback();
        venderExchangeCallback.setLotteryType(lotteryType);
        venderExchangeCallback.setPhase(phase);
        venderExchangeCallback.setTicketIdList(ticketIdList);
        venderExchangeCallback.setTerminalId(terminalId);
        venderExchangeCallback.setTicketResultStatus(ticketResultStatus);
        venderExchangeCallback.setName("票兑奖");
        venderExchangeCallback.setRetry(1);
        venderExchangeCallback.setEndTime(endTime);
        venderExchangeCallback.setStartTime(startTime);
        venderExchangeCallback.setTicketService(ticketService);
        venderExchangeCallback.setVenderConfigHandler(venderConfigService);
        venderExchangeCallback.setVenderTicketExchangeMap(venderExchangeMap);
        venderExchangeCallback.setAgencyExchange(agencyExchange);
        apiReryTaskExecutor.invokeAsync(venderExchangeCallback);
    }

    /**
     * 票奖金核对
     * */
    @Resource(name="venderTicketPrizeCheckMap")
    protected Map<TerminalType,IVenderTicketPrizeCheck> venderTicketPrizeCheckMap;


    public void ticketCheckPrize(Integer lotteryType,String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId,String ids,Date startTime,Date endTime){
        if(StringUtil.isNotEmpt(ids)){
            this.ticketprizeCheck(lotteryType, phase, ticketResultStatus, agencyExchange, terminalId, ids,startTime,endTime);
        }else{
            if(lotteryType==null){
                this.ticketprizeCheck(lotteryType, phase, ticketResultStatus, agencyExchange, terminalId, ids ,startTime,endTime);
            }else {
                for(int lotterytype:LotteryType.getLotteryTypeList(lotteryType)){
                    this.ticketprizeCheck(lotterytype, phase, ticketResultStatus, agencyExchange, terminalId, ids,startTime,endTime);
                }
            }

        }
    }
    public void ticketprizeCheck(Integer lotteryType,String phase,Integer ticketResultStatus,Integer agencyExchange,Long terminalId,String ids,Date startTime,Date endTime){
        List<String> ticketIdList=null;
        if(StringUtil.isNotEmpt(ids)){
            ticketIdList = Arrays.asList(org.apache.commons.lang3.StringUtils.split(ids,","));
        }
        VenderTicketPrizeCheckCallback venderTicketPrizeCheckCallback=new VenderTicketPrizeCheckCallback();
        venderTicketPrizeCheckCallback.setLotteryType(lotteryType);
        venderTicketPrizeCheckCallback.setPhase(phase);
        venderTicketPrizeCheckCallback.setTicketIdList(ticketIdList);
        venderTicketPrizeCheckCallback.setTerminalId(terminalId);
        venderTicketPrizeCheckCallback.setTicketResultStatus(ticketResultStatus);
        venderTicketPrizeCheckCallback.setName("票奖金核对");
        venderTicketPrizeCheckCallback.setRetry(1);
        venderTicketPrizeCheckCallback.setStartTime(startTime);
        venderTicketPrizeCheckCallback.setEndTime(endTime);
        venderTicketPrizeCheckCallback.setTicketService(ticketService);
        venderTicketPrizeCheckCallback.setVenderConfigHandler(venderConfigService);
        venderTicketPrizeCheckCallback.setVenderTicketPrizeCheckMap(venderTicketPrizeCheckMap);
        venderTicketPrizeCheckCallback.setAgencyExchange(agencyExchange);
        apiReryTaskExecutor.invokeAsync(venderTicketPrizeCheckCallback);
    }

    protected ITicketAllotWorker getAllotExecutor(LotteryType lotteryType) {
        ITicketAllotWorker worker = this.executorBinder.get(lotteryType);
        if (worker == null) {
            worker = this.executorBinder.get(LotteryType.ALL);
        }
        return worker;
    }


    public void checkTicketAll(Date createTime,Date endTime,Long terminalId){

        TicketCheckCallback ticketCheckCallback=new TicketCheckCallback();
        ticketCheckCallback.setCreateTime(createTime);
        ticketCheckCallback.setEndTime(endTime);
        ticketCheckCallback.setQueueMessageSendService(queueMessageSendService);
        ticketCheckCallback.setTicketService(ticketService);
        ticketCheckCallback.setTerminalId(terminalId);
        ticketCheckCallback.setName("一键检票");
        ticketCheckCallback.setRetry(1);
        apiReryTaskExecutor.invokeAsync(ticketCheckCallback);
    }



    //出票失败赔付
    public  void betCompensate(int lotteryType,String phase,String[] orders){
        OrderFailureCompensateCallback compensateCallback=new OrderFailureCompensateCallback();
        compensateCallback.setName("撤单赔付算奖");
        compensateCallback.setLotteryOrderService(lotteryOrderService);
        compensateCallback.setLotteryType(lotteryType);
        compensateCallback.setLotteryPhaseService(lotteryPhaseService);
        compensateCallback.setPrizeService(prizeService);
        compensateCallback.setTicketService(ticketService);
        compensateCallback.setUserAccountService(userAccountService);
        compensateCallback.setOrders(orders);
        compensateCallback.setPhase(phase);
        apiReryTaskExecutor.invokeAsync(compensateCallback);
    }


	/*
     * int batchSize=20; int len = 0; int ticketCount=needList.size(); if
	 * (ticketCount % batchSize == 0) { len =ticketCount/batchSize; } else { len
	 * =ticketCount/batchSize + 1; } for (int i = 0; i < len; i++){ List<Ticket>
	 * ticketBatchList=null; if (((i * batchSize) + batchSize) <ticketCount) {
	 * ticketBatchList = needList.subList(i * batchSize, i* batchSize +
	 * batchSize); } else { ticketBatchList = needList.subList(i *
	 * batchSize,ticketCount); }
	 */

}
