package com.lottery.ticket.vender.impl.qihui;
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
public class QHConverter extends AbstractVenderConverter {
	
	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return QHLotteryDef.lotteryTypeMap;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		return QHLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return QHLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return QHLotteryDef.ticketContentConverterMap;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(QHLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= QHLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return QHLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return QHLotteryDef.toLotteryTypeMap;
	}
	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		return null;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_QH, this);
	}
}
