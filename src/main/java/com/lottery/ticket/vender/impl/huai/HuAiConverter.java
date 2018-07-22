package com.lottery.ticket.vender.impl.huai;

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
public class HuAiConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return HuAiLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return HuAiLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return HuAiLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return HuAiLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(HuAiLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= HuAiLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		String content=tickt.getContent();
		String []contents=content.split("\\-")[1].replace("^","").split("\\|");
		String []oddsStr=venderSp.split("\\-");
		String []sps=null;
		String []odds=null;
		if(venderSp.contains("@")){
			sps=venderSp.split("\\@")[0].split("\\-");
			odds=venderSp.split("\\@")[1].split("B");
		}
		StringBuffer stringBuffer=new StringBuffer();
		for(int i=0;i<contents.length;i++){
			if(tickt.getLotteryType()==LotteryType.JCLQ_RFSF.getValue()||tickt.getLotteryType()==LotteryType.JCLQ_DXF.getValue()){
				stringBuffer.append(contents[i].split("\\(")[0]).append("(").append(odds[i]).append(":");
				if(sps[i].contains("A")){
					String []spss=sps[i].split("A");
					
					String []cons=contents[i].split("\\(")[1].split("\\)")[0].split("\\,");
					for(int f=0;f<cons.length;f++){
						stringBuffer.append(cons[f]).append("_").append(spss[f]);
						if(f!=cons.length-1){
							stringBuffer.append(",");
						}
					}
					stringBuffer.append(")");
				}else{
					stringBuffer.append(contents[i].split("\\(")[1].split("\\)")[0]).append("_").append(sps[i]).append(")");
				}
				
			}else if(tickt.getLotteryType()==LotteryType.JCLQ_HHGG.getValue()){
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				if(!"#".equals(odds[i])){
					stringBuffer.append(odds[i]).append(":");
				}
				if(sps[i].contains("A")){
					String []spss=sps[i].split("A");
					
					String []cons=contents[i].split("\\(")[1].split("\\)")[0].split("\\,");
					for(int f=0;f<cons.length;f++){
						stringBuffer.append(cons[f]).append("_").append(spss[f]);
						if(f!=cons.length-1){
							stringBuffer.append(",");
						}
					}
					stringBuffer.append(")");
				}else{
					stringBuffer.append(contents[i].split("\\(")[1].split("\\)")[0]).append("_").append(sps[i]).append(")");
				}
			}else if(tickt.getLotteryType()==LotteryType.JC_GUANJUN.getValue()||tickt.getLotteryType()==LotteryType.JC_GUANYAJUN.getValue()){
				stringBuffer.append(contents[i]).append("_").append(venderSp);
			}else{
				if(contents[i].contains(",")){
					String []cons=contents[i].replace(")","").split("\\,");
					String []ods=oddsStr[i].split("A");
					for(int j=0;j<cons.length;j++){
						stringBuffer.append(cons[j]+"_"+ods[j]);
						if(j<cons.length-1){
							stringBuffer.append(",");
						}else{
							stringBuffer.append(")");
						}
					}
				}else{
					stringBuffer.append(contents[i].split("\\)")[0]+"_"+oddsStr[i]+")");
				}
			}
			if(i!=contents.length-1){
				stringBuffer.append("|");
			}
		}
		return stringBuffer.toString();
	}
	@Override
	public Map<String, LotteryType> getReverseLotteryTypeMap() {
		return null;
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_HUAI, this);
	}
	
	
}
