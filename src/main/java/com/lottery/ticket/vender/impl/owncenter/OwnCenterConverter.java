package com.lottery.ticket.vender.impl.owncenter;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.vender.AbstractVenderConverter;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by fengqinyun on 15/5/20.
 */
@Service
public class OwnCenterConverter   extends AbstractVenderConverter {
    @Override
    protected Map<LotteryType, String> getLotteryTypeMap() {
        return null;
    }

    @Override
    public String convertContent(Ticket ticket) {
        return ticket.getContent().split("\\-")[1];
    }

    @Override
    public LotteryType getLotteryMap(String lotteryId) {
        return null;
    }

    @Override
    protected Map<Integer, String> getPlayTypeMap() {
        return null;
    }

    @Override
    protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
        return null;
    }

    @Override
    protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
        return null;
    }

    @Override
    protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
        return null;
    }

    @Override
    protected Map<String, LotteryType> getReverseLotteryTypeMap() {
        return null;
    }

    @Override
    protected String venderSpConvert(Ticket tickt, String venderSp) {
        return null;
    }

    @Override
    protected void init() {
        venderConverterBinder.put(TerminalType.T_OWN_CENTER, this);
    }
}
