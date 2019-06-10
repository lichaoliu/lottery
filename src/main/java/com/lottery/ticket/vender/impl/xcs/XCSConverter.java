package com.lottery.ticket.vender.impl.xcs;

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
public class XCSConverter extends AbstractVenderConverter{

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(XCSLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= XCSLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	public Map<LotteryType, String> getLotteryTypeMap() {
		return XCSLotteryDef.lotteryTypeToMap;
	}

	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		
		return XCSLotteryDef.playTypeMap;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		// TODO Auto-generated method stub
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
		venderConverterBinder.put(TerminalType.T_XCS, this);
	}

}
