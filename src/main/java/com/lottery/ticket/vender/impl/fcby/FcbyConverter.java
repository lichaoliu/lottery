package com.lottery.ticket.vender.impl.fcby;

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
public class FcbyConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return FcbyLotteryDef.lotteryTypeMap;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		// TODO Auto-generated method stub
		return FcbyLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return FcbyLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return FcbyLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(FcbyLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= FcbyLotteryDef.playTypeToAdvanceConverterMap.get(playType);
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
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return FcbyLotteryDef.toLotteryTypeMap;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_FCBY, this);
	}

}
