package com.lottery.ticket.allotter.worker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.core.terminal.ITerminalSelectorFilter;
/**
 * 按照玩法分票
 * */
public class PlayTypeSupportedTicketAllotterWorker extends CommonTicketAllotterWorker {

    @Override
    protected List<Ticket> preAllotTicketList(List<Ticket> ticketList, int countInBatch, LotteryType lotteryType, String phaseNo) throws Exception {
        Set<PlayType> allPlayTypeSet = new HashSet<PlayType>();
        Set<PlayType> specifiedPlayTypeSet = new HashSet<PlayType>();
        for (Ticket ticket : ticketList) {
            PlayType playType = PlayType.get(ticket.getPlayType());
            if (allPlayTypeSet.contains(playType)) {
                continue;
            }
            allPlayTypeSet.add(playType);
            if (this.terminalSelector.hasSpecifyTerminalConfigForPlayType(playType)) {
                specifiedPlayTypeSet.add(playType);
            }
        }

        // 如果没有需要特殊处理的玩法，直接返回原集合
        if (specifiedPlayTypeSet.isEmpty()) {
            return ticketList;
        }

        // 开始处理需要按玩法分票的内容
        // 首先分离集合，生成要返回的结果集和要处理的结果集
        Map<PlayType, List<Ticket>> preAllotTicketListMap = new HashMap<PlayType, List<Ticket>>();
        List<Ticket> returnTicketList = new ArrayList<Ticket>();
        for (Ticket ticket : ticketList) {
            PlayType playType = PlayType.get(ticket.getPlayType());
            // 分离存储
            if (specifiedPlayTypeSet.contains(playType)) {
                List<Ticket> preAllotTicketList = preAllotTicketListMap.get(playType);
                if (preAllotTicketList == null) {
                    preAllotTicketList = new ArrayList<Ticket>();
                    preAllotTicketListMap.put(playType, preAllotTicketList);
                }
                preAllotTicketList.add(ticket);
            } else {
                returnTicketList.add(ticket);
            }
        }

        // 遍历要处理的结果集，按玩法进行终端选择
        for (PlayType playType : preAllotTicketListMap.keySet()) {
            this.executeWithPlayType(preAllotTicketListMap.get(playType), countInBatch, lotteryType, phaseNo, playType);
        }

        // 将未处理的结果集返回
        return returnTicketList;
    }
    @Override
    protected Long executeAllot(Ticket ticket, ITerminalSelectorFilter filter) throws Exception {
    	PlayType playType=PlayType.get(ticket.getPlayType());
    	LotteryType lotteryType=LotteryType.get(ticket.getLotteryType());
        if (!this.terminalSelector.hasSpecifyTerminalConfigForPlayType(playType)) {
            // 不需要特殊处理
            return super.executeAllot(ticket, filter);
        }

        this.clearTicketInfo(ticket);

        List<Ticket> ticketList = new ArrayList<Ticket>();
        ticketList.add(ticket);
        return this.executeAllot(ticketList, lotteryType, ticket.getPhase(), playType, filter);
    }

}
