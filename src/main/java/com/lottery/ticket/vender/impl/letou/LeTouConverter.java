package com.lottery.ticket.vender.impl.letou;

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
public class LeTouConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return LeTouLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return LeTouLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return LeTouLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return LeTouLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(LeTouLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= LeTouLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		StringBuffer stringBuffer=new StringBuffer();
		String []strs=venderSp.split("\n");
		if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_SPF_WRQ.value
		||tickt.getLotteryType().intValue()==LotteryType.JCZQ_RQ_SPF.value
		||tickt.getLotteryType().intValue()==LotteryType.JCZQ_BQC.value
		||tickt.getLotteryType().intValue()==LotteryType.JCZQ_JQS.value
		||tickt.getLotteryType().intValue()==LotteryType.JCZQ_BF.value
		||tickt.getLotteryType().intValue()==LotteryType.JCLQ_SF.value
		||tickt.getLotteryType().intValue()==LotteryType.JCLQ_SFC.value){
			String []cons=tickt.getContent().split("\\-")[1].replace("^", "").split("\\|");
			int j=0;
			for(String con:cons){
				stringBuffer.append(con.split("\\(")[0]).append("(");
				String []cs=con.split("\\(")[1].replace(")","").split("\\,");
				int i=0;
				String []nums=strs[6+(j*3)].replace("+)", "").split("\\+");
				for(String c:cs){
					stringBuffer.append(c).append("_").append(nums[i].split("@")[1].replace("元",""));
					if(i!=cs.length-1){
						stringBuffer.append(",");
					}
					i++;
				}
				stringBuffer.append(")");
				if(j!=cons.length-1){
					stringBuffer.append("|");
				}
				j++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_HHGG.value){
			String []cons=tickt.getContent().split("\\-")[1].replace("^", "").split("\\|");
			int j=0;
			for(String con:cons){
				stringBuffer.append(con.split("\\(")[0]).append("(");
				String []cs=con.split("\\(")[1].replace(")","").split("\\,");
				int i=0;
				String sp=null;
				String []nums=null;
				for(String c:cs){
					if(cs.length>1){
						nums=strs[6+(j*3)].replace("+)", "").split("\\+");
						sp=nums[i];
					}else{
						sp=strs[6+(j*3)];
					}
					stringBuffer.append(c).append("_").append(sp.split("@")[1].replace("元",""));
					if(i!=cs.length-1){
						stringBuffer.append(",");
					}
					i++;
				}
				stringBuffer.append(")");
				if(j!=cons.length-1){
					stringBuffer.append("|");
				}
				j++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_RFSF.value
				||tickt.getLotteryType().intValue()==LotteryType.JCLQ_DXF.value){
			String []cons=tickt.getContent().split("\\-")[1].replace("^", "").split("\\|");
			int j=0;
			for(String con:cons){
				stringBuffer.append(con.split("\\(")[0]).append("(");
				stringBuffer.append(strs[4+(j*3)].replace("+)", "").split("\\:")[1].replace("主","")).append(":");
				String []cs=con.split("\\(")[1].replace(")","").split("\\,");
				int i=0;
				String []nums=strs[6+(j*3)].split("\\+");
				for(String c:cs){
					stringBuffer.append(c).append("_").append(nums[i].split("@")[1].replace("元",""));
					if(i!=cs.length-1){
						stringBuffer.append(",");
					}
					i++;
				}
				stringBuffer.append(")");
				if(j!=cons.length-1){
					stringBuffer.append("|");
				}
				j++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_HHGG.value){
			String []cons=tickt.getContent().split("\\-")[1].replace("^", "").split("\\|");
			int j=0;
			for(String con:cons){
				String lotterType=con.split("\\*")[1].split("\\(")[0];
				stringBuffer.append(con.split("\\(")[0]).append("(");
				if("3002".equals(lotterType)||"3004".equals(lotterType)){
					stringBuffer.append(strs[4+(j*3)].replace("+)", "").split("\\:")[1].replace("主","").replace("客","")).append(":");
				}
				String []cs=con.split("\\(")[1].replace(")","").split("\\,");
				int i=0;
				String []nums=null;
				String sp=null;
				for(String c:cs){
					if(cs.length>1){
						nums=strs[6+(j*3)].replace("+)", "").split("\\+");
						sp=nums[i];
					}else{
						sp=strs[6+(j*3)];
					}
					stringBuffer.append(c).append("_").append(sp.split("@")[1].replace("元",""));
					if(i!=cs.length-1){
						stringBuffer.append(",");
					}
					i++;
				}
				stringBuffer.append(")");
				if(j!=cons.length-1){
					stringBuffer.append("|");
				}
				j++;
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
		venderConverterBinder.put(TerminalType.T_LETOU, this);
	}
	




	
}
