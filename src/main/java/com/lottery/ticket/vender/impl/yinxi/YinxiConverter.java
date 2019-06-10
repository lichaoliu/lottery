package com.lottery.ticket.vender.impl.yinxi;
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
public class YinxiConverter extends AbstractVenderConverter {
	
	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return YinxiLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return YinxiLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return YinxiLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return YinxiLotteryDef.ticketContentConverterMap;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(YinxiLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= YinxiLotteryDef.playTypeToAdvanceConverterMap.get(playType);
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
		return YinxiLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return YinxiLotteryDef.toLotteryTypeMap;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_YX, this);
	}
}
