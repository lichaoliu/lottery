package com.lottery.ticket.vender.impl.zch;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;
import com.lottery.ticket.vender.AbstractVenderConverter;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class ZchConverter extends AbstractVenderConverter {
	
	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return ZchLotteryDef.lotteryTypeMap;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		return ZchLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return ZchLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return ZchLotteryDef.ticketContentConverterMap;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(ZchLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= ZchLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}
	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		return null;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return ZchLotteryDef.phaseReverseConverterMap;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return ZchLotteryDef.toLotteryTypeMap;
	}


	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_ZCH, this);
	}
}
