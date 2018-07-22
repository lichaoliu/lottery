package com.lottery.ticket.vender.impl.anrui;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderOddsConverter;
import com.lottery.ticket.IVenderTicketConverter;
import com.lottery.ticket.vender.AbstractVenderConverter;
import org.springframework.stereotype.Service;

import java.util.Map;
@Service
public class AnruiConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return AnruiLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return AnruiLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return AnruiLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return AnruiLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(AnruiLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= AnruiLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}
	
	public  String convertodd(Ticket ticket,String odd) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderOddsConverter converter=null;
		if(AnruiLotteryDef.oddConverterMap.containsKey(playType)){
			converter= AnruiLotteryDef.oddConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convertOdds(ticket, odd);
		}
		return null;
	}
	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		return null;
	}
	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return AnruiLotteryDef.toLotteryTypeMap;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_ARUI, this);
	}

}
