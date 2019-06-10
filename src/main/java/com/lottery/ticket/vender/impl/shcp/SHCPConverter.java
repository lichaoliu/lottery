package com.lottery.ticket.vender.impl.shcp;

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
public class SHCPConverter extends AbstractVenderConverter {

	


	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return SHCPLotteryDef.lotteryTypeMap;
	}

	@Override
	public Map<Integer, String> getPlayTypeMap() {
		return SHCPLotteryDef.playTypeMap;
	}

	
	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return findLotteryType(lotteryId);
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return SHCPLotteryDef.phaseConverterMap;
	}


	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		return SHCPLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		return null;
	}

	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(SHCPLotteryDef.playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= SHCPLotteryDef.playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		StringBuffer stringBuffer=new StringBuffer();
		if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_SPF_WRQ.value||tickt.getLotteryType().intValue()==LotteryType.JCZQ_BQC.value
		||tickt.getLotteryType().intValue()==LotteryType.JCZQ_JQS.value||tickt.getLotteryType().intValue()==LotteryType.JCZQ_BQC.value
		||tickt.getLotteryType().intValue()==LotteryType.JCLQ_SF.value){
			String conStr=venderSp.replace("7+","7").replace("(","").replace(":", "").replace(")","").replace("胜", "3").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",").replace("元","");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.replace("=","(")).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_RQ_SPF.value){
			String conStr=venderSp.replace("元","").replace("胜", "3").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\(")[0]).append("(").append(con.split("\\=")[1]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_HHGG.value){
			String conStr=venderSp.replace("7+","7").replace("(","").replace(":", "").replace(")","").replace("胜", "3").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",").replace("元","");
			String []cons=conStr.split("\\|");
			int i=0;
			String []contents=tickt.getContent().split("\\-")[1].split("\\|");
			for(String con:cons){
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				stringBuffer.append(con.split("\\=")[1].split("\\(")[0]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_RFSF.value
				||tickt.getLotteryType().intValue()==LotteryType.JCLQ_DXF.value){
			String conStr=venderSp.replace("元","").replace("胜", "3").replace("小","2").replace("大","1").replace("主","").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\(")[0]).append("(").append(con.split("\\(")[1].split("\\)")[0].replace(",","")).append(":").append(con.split("\\=")[1]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCZQ_BF.value){
			String conStr=venderSp.replace("元","").replace("胜", "3").replace(":","").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(").append(con.split("\\=")[1].replace("(","").replace(")","")).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_SFC.value){
			String conStr=venderSp.replace("元","").replace("主1-5","01").replace("主6-10","02").replace("主11-15","03").replace("主16-20","04").replace("主21-25","05").replace("主26+","06").replace("胜", "3").replace("(","").replace(")", "").replace("客1-5","11").replace("客6-10","12").replace("客11-15","13").replace("客16-20","14").replace("客21-25","15").replace("客26+","16").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(").append(con.split("\\=")[1]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(tickt.getLotteryType().intValue()==LotteryType.JCLQ_HHGG.value){
			String conStr=venderSp.replace("元","").replace("平", "1").replace("负", "0").replace("客26+","16").replace(",","|").replace("主26+","06").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			String []contents=tickt.getContent().split("\\-")[1].split("\\|");
			for(String con:cons){
				String playType=contents[i].split("\\*")[1].split("\\(")[0];
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				if("3003".equals(playType)){
					con=con.replace("(","").replace(")", "").replace("主1-5","01").replace("主6-10","02").replace("主11-15","03").replace("主16-20","04").replace("主21-25","05").replace("主26+","06").replace("客1-5","11").replace("客6-10","12").replace("客11-15","13").replace("客16-20","14").replace("客21-25","15").replace("客26+","16").replace("胜", "3");
					stringBuffer.append(con.split("\\=")[1]).append(")");
				}else if("3004".equals(playType)||"3002".equals(playType)){
					con=con.replace("小","2").replace("大","1");
					stringBuffer.append(con.split("\\(")[1].split("\\)")[0].replace(",","").replace("主", "")).append(":").append(con.split("\\=")[1].replace("胜", "3")).append(")");
				}else{
					stringBuffer.append(con.split("\\=")[1].replace("胜", "3").replace("=","(")).append(")");
				}
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
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
		venderConverterBinder.put(TerminalType.T_SHCP, this);
	}
	
	public static void main(String[] args) {//12001-20160720016(3,1)|20160720017(3,1)^
		String venderSp="160719001=负@2.200元,160719002=胜@1.410元,160719003(主-1)=平@3.800元 负@1.550元,160719004(主-1)=负@4.600元,160719005=负@1.590元";
		int lotteryType=3010;
		String content="301012001-20160720016(3,1)|20160720017(3,1)^";
		StringBuffer stringBuffer=new StringBuffer();
		if(lotteryType==LotteryType.JCZQ_SPF_WRQ.value||lotteryType==LotteryType.JCZQ_BQC.value
		||lotteryType==LotteryType.JCZQ_JQS.value||lotteryType==LotteryType.JCZQ_BQC.value
		||lotteryType==LotteryType.JCLQ_SF.value){
			String conStr=venderSp.replace("7+","7").replace("(","").replace(":", "").replace(")","").replace("胜", "3").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",").replace("元","");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.replace("=","(")).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCZQ_RQ_SPF.value){
			String conStr=venderSp.replace("元","").replace("胜", "3").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\(")[0]).append("(").append(con.split("\\=")[1]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCZQ_HHGG.value){
			String conStr=venderSp.replace("7+","7").replace("(","").replace(":", "").replace(")","").replace("胜", "3").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",").replace("元","");
			String []cons=conStr.split("\\|");
			int i=0;
			String []contents=content.split("\\-")[1].split("\\|");
			for(String con:cons){
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				stringBuffer.append(con.split("\\=")[1].split("\\(")[0]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_RFSF.value
				||lotteryType==LotteryType.JCLQ_DXF.value){
			String conStr=venderSp.replace("元","").replace("胜", "3").replace("小","2").replace("大","1").replace("主","").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\(")[0]).append("(").append(con.split("\\(")[1].split("\\)")[0].replace(",","")).append(":").append(con.split("\\=")[1]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCZQ_BF.value){
			String conStr=venderSp.replace("元","").replace("胜", "3").replace(":","").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(").append(con.split("\\=")[1].replace("(","").replace(")","")).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_SFC.value){
			String conStr=venderSp.replace("元","").replace("主1-5","01").replace("主6-10","02").replace("主11-15","03").replace("主16-20","04").replace("主21-25","05").replace("主26+","06").replace("胜", "3").replace("(","").replace(")", "").replace("客1-5","11").replace("客6-10","12").replace("客11-15","13").replace("客16-20","14").replace("客21-25","15").replace("客26+","16").replace("平", "1").replace("负", "0").replace(",","|").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			for(String con:cons){
				stringBuffer.append("20").append(con.split("\\=")[0]).append("(").append(con.split("\\=")[1]).append(")");
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}else if(lotteryType==LotteryType.JCLQ_HHGG.value){
			String conStr=venderSp.replace("元","").replace("平", "1").replace("负", "0").replace("客26+","16").replace(",","|").replace("主26+","06").replace("@", "_").replace("+",",");
			String []cons=conStr.split("\\|");
			int i=0;
			String []contents=content.split("\\-")[1].split("\\|");
			for(String con:cons){
				String playType=contents[i].split("\\*")[1].split("\\(")[0];
				stringBuffer.append(contents[i].split("\\(")[0]).append("(");
				if("3003".equals(playType)){
					con=con.replace("(","").replace(")", "").replace("主1-5","01").replace("主6-10","02").replace("主11-15","03").replace("主16-20","04").replace("主21-25","05").replace("主26+","06").replace("客1-5","11").replace("客6-10","12").replace("客11-15","13").replace("客16-20","14").replace("客21-25","15").replace("客26+","16").replace("胜", "3");
					stringBuffer.append(con.split("\\=")[1]).append(")");
				}else if("3004".equals(playType)||"3002".equals(playType)){
					con=con.replace("小","2").replace("大","1");
					stringBuffer.append(con.split("\\(")[1].split("\\)")[0].replace(",","").replace("主", "")).append(":").append(con.split("\\=")[1].replace("胜", "3")).append(")");
				}else{
					stringBuffer.append(con.split("\\=")[1].replace("胜", "3").replace("=","(")).append(")");
				}
				if(i!=cons.length-1){
					stringBuffer.append("|");
				}
				i++;
			}
		}

	}
	
}
