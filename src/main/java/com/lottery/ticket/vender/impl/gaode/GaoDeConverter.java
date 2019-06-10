package com.lottery.ticket.vender.impl.gaode;

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
public class GaoDeConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return GaoDeLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return GaoDeLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return GaoDeLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return GaoDeLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(GaoDeLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= GaoDeLotteryDef.playTypeToAdvanceConverterMap.get(playType);
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
		venderConverterBinder.put(TerminalType.T_GAODE, this);
	}
	
	
}
