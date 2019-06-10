package com.lottery.ticket.sender.worker.thread;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.ticket.TicketBatch;
import com.lottery.retrymodel.ApiRetryCallback;
import com.lottery.ticket.sender.ITicketSendWorker;

/**
 * Created by fengqinyun on 16/5/19.
 */
public class TicketSendRetryCallback extends ApiRetryCallback<Object> {
    private ITicketSendWorker ticketSendWorker;
    private TicketBatch ticketBatch;
    private LotteryType lotteryType;
    @Override
    protected Object execute() throws Exception {
        if (ticketSendWorker==null||ticketBatch==null||lotteryType==null){
            throw new Exception("参数不全");
        }
        ticketSendWorker.execute(ticketBatch,lotteryType);
        return null;
    }

    public ITicketSendWorker getTicketSendWorker() {
        return ticketSendWorker;
    }

    public void setTicketSendWorker(ITicketSendWorker ticketSendWorker) {
        this.ticketSendWorker = ticketSendWorker;
    }

    public TicketBatch getTicketBatch() {
        return ticketBatch;
    }

    public void setTicketBatch(TicketBatch ticketBatch) {
        this.ticketBatch = ticketBatch;
    }

    public LotteryType getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(LotteryType lotteryType) {
        this.lotteryType = lotteryType;
    }
}
