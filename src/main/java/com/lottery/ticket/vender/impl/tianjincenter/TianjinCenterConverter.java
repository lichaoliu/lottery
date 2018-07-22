package com.lottery.ticket.vender.impl.tianjincenter;
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
public class TianjinCenterConverter extends AbstractVenderConverter {
	
	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return TianjinCenterLotteryDef.lotteryTypeMap;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		return TianjinCenterLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return TianjinCenterLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return TianjinCenterLotteryDef.ticketContentConverterMap;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(TianjinCenterLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= TianjinCenterLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return TianjinCenterLotteryDef.phaseReverseConverterMap;
	}
	@Override
	 protected String venderSpConvert(Ticket tickt, String venderSp) {
		return null;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return TianjinCenterLotteryDef.toLotteryTypeMap;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_TJFC_CENTER, this);
	}
}
