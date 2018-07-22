package com.lottery.ticket.vender.impl.xmac;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;
import com.lottery.ticket.vender.AbstractVenderConverter;
import com.lottery.ticket.vender.impl.huay.HuayLotteryDef;

import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * Created by fengqinyun on 15/9/1.
 */
@Service
public class XmacConverter extends AbstractVenderConverter {
    @Override
    protected Map<LotteryType, String> getLotteryTypeMap() {
        return XmacLotteryDef.lotteryTypeMap;
    }

    @Override
    public String convertContent(Ticket ticket) {
    	PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(XmacLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= XmacLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
    }

    @Override
    public LotteryType getLotteryMap(String lotteryId) {
        return XmacLotteryDef.toLotteryTypeMap.get(lotteryId);
    }

    @Override
    protected Map<Integer, String> getPlayTypeMap() {
        return XmacLotteryDef.playTypeMap;
    }

    @Override
    protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
        return XmacLotteryDef.phaseConverterMap;
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
        venderConverterBinder.put(TerminalType.T_XMAC, this);
    }
}
