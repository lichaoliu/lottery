package com.lottery.ticket.vender.impl.guoxin;

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
public class GuoxinConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return GuoxinLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return GuoxinLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return GuoxinLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return GuoxinLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(GuoxinLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= GuoxinLotteryDef.playTypeToAdvanceConverterMap.get(playType);
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
	public Map<String, LotteryType> getReverseLotteryTypeMap() {
		return null;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_GX, this);
	}

}
