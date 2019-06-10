package com.lottery.core.terminal.impl;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.cache.TerminalFailureCache;
import com.lottery.core.service.TicketServiceCache;
import com.lottery.core.terminal.ITerminalSelectorFilter;

/**
 * 根据票的失败记录来排除可用终端
 */
public class TicketFailureCacheTerminalSelectorFilter implements ITerminalSelectorFilter {

    private String ticketId;

    private TicketServiceCache ticketServiceCache;

    public TicketFailureCacheTerminalSelectorFilter(String ticketId) {
        this.ticketId = ticketId;
    }

    @Override
    public boolean filter(TerminalFailureCache terminalFailureCache, LotteryType lotteryType, String phase, PlayType playType) {
        if (this.getTicketServiceCache().hasTicketFailureTerminalIdCache(this.ticketId, terminalFailureCache.getTerminalId())) {
            // 过滤有失败记录的终端id
            return true;
        }
        return false;
    }

	public String getTicketId() {
		return ticketId;
	}

	public void setTicketId(String ticketId) {
		this.ticketId = ticketId;
	}

	public TicketServiceCache getTicketServiceCache() {
		return ticketServiceCache;
	}

	public void setTicketServiceCache(TicketServiceCache ticketServiceCache) {
		this.ticketServiceCache = ticketServiceCache;
	}

	

    
}
