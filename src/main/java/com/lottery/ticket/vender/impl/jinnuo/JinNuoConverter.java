package com.lottery.ticket.vender.impl.jinnuo;
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
public class JinNuoConverter extends AbstractVenderConverter {
	
	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return JinNuoLotteryDef.lotteryTypeMap;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		return JinNuoLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return JinNuoLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return JinNuoLotteryDef.ticketContentConverterMap;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(JinNuoLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= JinNuoLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return JinNuoLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return null;
	}
	@Override
	public  String venderSpConvert(Ticket tickt, String sp) {
		    int lotteryType=tickt.getLotteryType();
			String []contents=tickt.getContent().split("-")[1].split("\\|");
			StringBuilder stringBuilder=new StringBuilder();
			if(lotteryType==LotteryType.JCZQ_RQ_SPF.getValue()||lotteryType==LotteryType.JCZQ_SPF_WRQ.getValue()
			||lotteryType==LotteryType.JCZQ_JQS.getValue()||lotteryType==LotteryType.JCZQ_BQC.getValue()){
				for(int i=0;i<contents.length;i++){
					stringBuilder.append(contents[i].split("\\(")[0]).append("(");
					stringBuilder.append(sp.split("\\//")[i].split("\\,")[1].replace("_", "").replace(":","_").replace("/",",")).append(")");
					if(i<contents.length-1){
						stringBuilder.append("|");
					}
				}
			}else if(lotteryType==LotteryType.JCZQ_BF.getValue()){
				for(int i=0;i<contents.length;i++){
					stringBuilder.append(contents[i].split("\\(")[0]).append("(");
					String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
					int j=0;
					for(String con:cons){
						String []sps=con.split("\\:");
						String c=(sps[0]+sps[1]);
						stringBuilder.append(c.replace("43", "90").replace("44", "99").replace("34", "09")).append("_").append(sps[2].replace("/",","));
						if(j!=cons.length-1){
							stringBuilder.append(",");
						}
						j++;
					}
					stringBuilder.append(")");
					if(i<contents.length-1){
						stringBuilder.append("|");
					}
				}
			}else if(lotteryType==LotteryType.JCZQ_HHGG.getValue()){
				for(int i=0;i<contents.length;i++){
					int lottery=Integer.parseInt(contents[i].split("\\*")[1].split("\\(")[0]);
					if(lottery==LotteryType.JCZQ_RQ_SPF.getValue()||lottery==LotteryType.JCZQ_SPF_WRQ.getValue()
					||lottery==LotteryType.JCZQ_JQS.getValue()||lottery==LotteryType.JCZQ_BQC.getValue()){
						stringBuilder.append(contents[i].split("\\(")[0]);
						stringBuilder.append("(");
						stringBuilder.append(sp.split("\\//")[i].split("\\,")[1].replace("_", "").replace("R", "").replace("B", "")
						.replace(":","_").replace("/",",")).append(")");
					}else if(lottery==LotteryType.JCZQ_BF.getValue()){
							stringBuilder.append(contents[i].split("\\(")[0]).append("(");
							String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
							int j=0;
							for(String con:cons){
								String []sps=con.split("\\:");
								String c=(sps[0]+sps[1]);
								stringBuilder.append(c.replace("43", "90").replace("44", "99").replace("34", "09")).append("_").append(sps[2].replace("/",","));
								if(j!=cons.length-1){
									stringBuilder.append(",");
								}
								j++;
							}
							stringBuilder.append(")");
							if(i<contents.length-1){
								stringBuilder.append(",");
							}
					}
					
					if(i<contents.length-1){
						stringBuilder.append("|");
					}
				}
			}else if(lotteryType==LotteryType.JCLQ_SF.getValue()){
					for(int i=0;i<contents.length;i++){
						stringBuilder.append(contents[i].split("\\(")[0]).append("(");
						String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
						int j=0;
						for(String con:cons){
							stringBuilder.append(con.split("\\:")[0].replace("1", "3")).append("_").append(con.split("\\:")[1]);
							if(j!=cons.length-1){
								stringBuilder.append(",");
							}
							j++;
						}
						stringBuilder.append(")");
						if(i<contents.length-1){
							stringBuilder.append("|");
						}
					}
			}else if(lotteryType==LotteryType.JCLQ_RFSF.getValue()){
				for(int i=0;i<contents.length;i++){
					stringBuilder.append(contents[i].split("\\(")[0]);
					String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
					int j=0;
					stringBuilder.append("(").append(sp.split("\\//")[i].split("\\,")[1].split("\\/")[0].split("\\(")[1].split("\\)")[0]).append(":");
					for(String con:cons){
						stringBuilder.append(con.split("\\(")[0].replace("1","3")).append("_").append(con.split("\\:")[1]);
						if(j!=cons.length-1){
							stringBuilder.append(",");
						}
						j++;
					}
					stringBuilder.append(")");
					if(i<contents.length-1){
						stringBuilder.append("|");
					}
				}
			}else if(lotteryType==LotteryType.JCLQ_SFC.getValue()){
				for(int i=0;i<contents.length;i++){
					stringBuilder.append(contents[i].split("\\(")[0]).append("(");
					String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
					int j=0;
					for(String con:cons){
						String []sps=con.split("\\:");
						String c=(sps[0]);
						stringBuilder.append(c.replace("01", "11").replace("02", "12").replace("03", "13").replace("04", "14")
						.replace("05", "15").replace("06", "16").replace("56", "06").replace("55", "05").replace("54", "04").replace("53", "03").replace("52", "02").replace("51", "01")).append("_").append(sps[1]);
						if(j!=cons.length-1){
							stringBuilder.append(",");
						}
						j++;
					}
					stringBuilder.append(")");
					if(i<contents.length-1){
						stringBuilder.append("|");
					}
				}
			}else if(lotteryType==LotteryType.JCLQ_DXF.getValue()){
					for(int i=0;i<contents.length;i++){
						stringBuilder.append(contents[i].split("\\(")[0]);
						String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
						int j=0;
						stringBuilder.append("(").append(sp.split("\\//")[i].split("\\,")[1].split("\\/")[0].split("\\(")[1].split("\\)")[0]).append(":");
						for(String con:cons){
							stringBuilder.append(con.split("\\(")[0]).append("_").append(con.split("\\:")[1]);
							if(j!=cons.length-1){
								stringBuilder.append(",");
							}
							j++;
						}
						stringBuilder.append(")");
						if(i<contents.length-1){
							stringBuilder.append("|");
						}
					}
			}else if(lotteryType==LotteryType.JCLQ_HHGG.getValue()){
				for(int i=0;i<contents.length;i++){
					int lottery=Integer.parseInt(contents[i].split("\\*")[1].split("\\(")[0]);
					if(lottery==LotteryType.JCLQ_SF.getValue()){
						stringBuilder.append(contents[i].split("\\(")[0]).append("(");
						String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
						int j=0;
						for(String con:cons){
							stringBuilder.append(con.split("\\:")[0].replace("1", "3")).append("_").append(con.split("\\:")[1]);
							if(j!=cons.length-1){
								stringBuilder.append(",");
							}
							j++;
						}
						stringBuilder.append(")");
					}else if(lottery==LotteryType.JCLQ_RFSF.getValue()){
						stringBuilder.append(contents[i].split("\\(")[0]).append("(");
						String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
						int j=0;
						stringBuilder.append(sp.split("\\//")[i].split("\\,")[1].split("\\/")[0].split("\\(")[1].split("\\)")[0]).append(":");
						for(String con:cons){
							String []sps=con.split("\\:");
							String c=(sps[0].split("\\(")[0]);
							stringBuilder.append(c.replace("1D", "3").replace("0D", "0")).append("_").append(sps[1].replace("/",","));
							if(j!=cons.length-1){
								stringBuilder.append(",");
							}
							j++;
						}
						stringBuilder.append(")");
				    }else if(lottery==LotteryType.JCLQ_SFC .getValue()){
				    	stringBuilder.append(contents[i].split("\\(")[0]).append("(");
				    	String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
						int j=0;
						for(String con:cons){
							String []sps=con.split("\\:");
							stringBuilder.append(sps[0].replace("01", "11").replace("02", "12").replace("03", "13").replace("04", "14")
									.replace("05", "15").replace("06", "16").replace("56", "06").replace("55", "05").replace("54", "04").replace("53", "03").replace("52", "02").replace("51", "01")).append("_").append(sps[1]);
							if(j!=cons.length-1){
								stringBuilder.append(",");
							}
							j++;
						}
						stringBuilder.append(")");
				    }else if(lottery==LotteryType.JCLQ_DXF.getValue()){
				    	stringBuilder.append(contents[i].split("\\(")[0]).append("(");
						String []cons=sp.split("\\//")[i].split("\\,")[1].split("\\/");
						int j=0;
						stringBuilder.append(sp.split("\\//")[i].split("\\,")[1].split("\\/")[0].split("\\(")[1].split("\\)")[0]).append(":");
						for(String con:cons){
							stringBuilder.append(con.split("\\(")[0].replace("2B", "2").replace("1B", "1")).append("_").append(con.split("\\:")[1]);
							if(j!=cons.length-1){
								stringBuilder.append(",");
							}
							j++;
						}
						stringBuilder.append(")");
				    }
					if(i<contents.length-1){
						stringBuilder.append("|");
					}
				}
				
			}
			return stringBuilder.toString();
	}

	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_JINRUO, this);
	}
	
}
