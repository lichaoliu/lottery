/**
 *
 */
package com.lottery.ticket.sender.worker.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.TicketBatchStatus;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.terminal.TerminalTypeSingletonMapping;
import com.lottery.core.domain.ticket.LotteryTicketConfig;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.core.service.TicketBatchService;
import com.lottery.core.terminal.ITerminalSelector;
import com.lottery.log.LotteryLog;

/**
 * @author fengqinyun
 */
public class CommonTicketSenderRunnable extends AbstractTicketSenderRunnable {
    @Autowired
    protected TicketBatchService ticketBatchService;
    @Autowired
    private ITerminalSelector terminalSelector;
    @Resource(name = "singletonTicketSendDispatcher")
    private ITicketSendDispatcher ticketSendDispatcher;


    @Override
    public void execute() {
        while (running) {
            for (LotteryType lotteryType : this.getLotteryList()) {


                if (this.isDuringGlobalSendForbidPeriod(lotteryType)) {
                    LotteryLog.getLotterWarnLog().error("此彩种处于全局停售期，不做送票处理, {}", lotteryType);
                    continue;
                }

                List<Long> allExcludeTerminalIdList = new ArrayList<Long>();

                // 获取此彩种暂停送票的终端ID列表
                List<Long> pausedTerminalConfigList = terminalSelector.getPausedTerminalIdList(lotteryType);

                if (pausedTerminalConfigList != null && pausedTerminalConfigList.size() > 0) {
                    allExcludeTerminalIdList.addAll(pausedTerminalConfigList);
                }


                // 获取当前期待送票的批次
                List<TicketBatch> ticketBatchList = null;
                try {
                    ticketBatchList = ticketBatchService.findForSend(lotteryType, allExcludeTerminalIdList, getSendCount(lotteryType));
                } catch (Exception ex) {
                    logger.error("查询({})要送票的批次出错!", lotteryType.getName(), ex);
                }
                if (ticketBatchList == null || ticketBatchList.isEmpty()) {
                    logger.info("彩种({})没有未送票的批次", lotteryType.getName());
                    continue;
                }


                // 过滤结果
                ticketBatchList = this.filterSendPaused(ticketBatchList);
                for (TicketBatch ticketBatch : ticketBatchList) {
                    String batchId = null;
                    try {
                        batchId = ticketBatch.getId();

                        TerminalType terminalType = getTerminalType(ticketBatch.getTerminalId());
                        if (terminalType == null) {
                            logger.error("该批次({})指定的出票点未找到可用的出票商", batchId);
                            ticketBatch.setStatus(TicketBatchStatus.SEND_FAILURE.value);
                            ticketBatch.setUpdateTime(new Date());
                            ticketBatchService.update(ticketBatch);
                            continue;
                        }
                        ticketSendWorker.execute(ticketBatch, lotteryType);

                    } catch (Exception e) {
                        logger.error("批次{}送票出错", batchId, e);

                    }
                }
            }

            synchronized (this) {
                try {
                    wait(this.getInterval());
                } catch (InterruptedException e) {
                    logger.error("等待出错", e);
                }
            }
        }
    }

    protected boolean isDuringGlobalSendForbidPeriod(LotteryType lotteryType) {
        return terminalSelector.isGlobalSendPausedOrDuringSendForbidPeriod(lotteryType);
    }


    protected List<TicketBatch> filterSendPaused(List<TicketBatch> ticketBatchList) {
        List<TicketBatch> filterResult = new ArrayList<TicketBatch>();
        for (TicketBatch ticketBatch : ticketBatchList) {
            Long terminalId = ticketBatch.getTerminalId();
            LotteryType lotteryType = LotteryType.getLotteryType(ticketBatch.getLotteryType());
            // 先检测当前终端是否在禁止送票时段
            if (terminalSelector.isDuringSendForbidPeriod(terminalId, lotteryType)) {
                LotteryLog.getLotterWarnLog().error("当前终端不允许送票，不做处理, terminalId={}, lotteryType={}", terminalId, lotteryType);
                continue;
            }
            filterResult.add(ticketBatch);
        }
        return filterResult;
    }

    protected TerminalType getTerminalType(Long terminalId) {
        return terminalSelector.getTerminalType(terminalId);
    }

    public TicketBatchService getTicketBatchService() {
        return ticketBatchService;
    }

    public void setTicketBatchService(TicketBatchService ticketBatchService) {
        this.ticketBatchService = ticketBatchService;
    }

    public ITerminalSelector getTerminalSelector() {
        return terminalSelector;
    }

    public void setTerminalSelector(ITerminalSelector terminalSelector) {
        this.terminalSelector = terminalSelector;
    }

    protected List<TicketBatch> filterTerminalSingletonDispatch(List<TicketBatch> ticketBatchList) {
        List<TicketBatch> filterResult = new ArrayList<TicketBatch>();
        for (TicketBatch ticketBatch : ticketBatchList) {
            String batchId = null;
            try {
                batchId = ticketBatch.getId();
                TerminalType terminalType = this.getTerminalSelector().getTerminalType(ticketBatch.getTerminalId());
                if (terminalType == null) {
                    logger.error("该批次({})指定的出票点未找到可用的出票商,terminalid={}", batchId, ticketBatch.getTerminalId());
                    continue;
                }

                if (this.filterTerminalSingletonDispatch(ticketBatch, terminalType)) {
                    continue;
                }

                filterResult.add(ticketBatch);
            } catch (Exception e) {
                logger.error("批次{}送票分发出错", batchId, e);
            }
        }

        return filterResult;
    }

    protected boolean filterTerminalSingletonDispatch(TicketBatch ticketBatch, TerminalType terminalType) throws Exception {
        if (this.getTicketSendDispatcher() != null && TerminalTypeSingletonMapping.isSingleton(terminalType)) {
            // 发送到送票分发器进行处理
            this.getTicketSendDispatcher().dispatch(ticketBatch);
            return true;
        }

        return false;
    }

    public ITicketSendDispatcher getTicketSendDispatcher() {
        return ticketSendDispatcher;
    }

    public void setTicketSendDispatcher(ITicketSendDispatcher ticketSendDispatcher) {
        this.ticketSendDispatcher = ticketSendDispatcher;
    }

    protected int getSendCount(LotteryType lotteryType) {
        LotteryTicketConfig ticketConfig = terminalSelector.getLotteryTicketConfig(LotteryType.getPhaseType(lotteryType));
        if (ticketConfig != null && ticketConfig.getSendCount() != null && ticketConfig.getSendCount() > 0) {
            return ticketConfig.getSendCount();
        }
        return getMaxProcessBatchCount();
    }


}
