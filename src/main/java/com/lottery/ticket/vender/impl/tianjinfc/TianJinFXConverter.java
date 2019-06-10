package com.lottery.ticket.vender.impl.tianjinfc;

import java.util.HashMap;
import java.util.Map;


import org.springframework.stereotype.Service;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;
import com.lottery.ticket.vender.AbstractVenderConverter;
@Service
public class TianJinFXConverter extends AbstractVenderConverter {
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
	static{
		IVenderTicketConverter ssq=new IVenderTicketConverter() {
			
			@Override
			public String convert(Ticket ticket) {
				String ticketContent=ticket.getContent();
				int beishu=ticket.getMultiple();
				String contentStr="";
				String beishuStr="";
				String playTypeStr="";
				String playStr="";
				int playType=Integer.parseInt(ticketContent.split("-")[0]);
				if(beishu<=9){
					beishuStr="0"+beishu;
				}else 
					beishuStr=beishu+"";
				playStr=this.ConvertPlayType(ticketContent.split("-")[1]);
				playTypeStr=playStr+beishuStr;
				if(playType==TianJinFXLotteryDef.ssqFuShi){
					playTypeStr=playTypeStr+"*";
				}
				String content[]=ticketContent.split("-")[1].split("\\^");;
				for(String str:content){
						contentStr+=playTypeStr+str.replace(",","").replace("|", "~").replace("#", "*")+"^";
				}	
				return contentStr;
		}

			//红单蓝单00  01,02,03,04,05,06|07^
			//红复蓝单10  01,02,03,04,05,06,08|07^
			//红单蓝复20  01,02,03,04,05,06|07,09^
			//红复蓝复30  01,02,03,04,05,06,09|07,09^
			//红胆蓝单40  08#01,02,03,04,05|07^
			//红胆蓝复50  08#01,02,03,04,05|07,09^  14,15,24,32,33#06,23|05,06^
			public String ConvertPlayType(String content){
				String playType="";
				if(!content.contains("#")){
					String str[]=content.split("\\^")[0].split("\\|");
					if(str[0].length()==17&&str[1].length()==2){
						playType="00";
					}else if(str[0].length()>17&&str[1].length()==2){
						playType="10";
					}else if(str[0].length()==17&&str[1].length()>2){
						playType="20";
					}else if(str[0].length()>17&&str[1].length()>2){
						playType="30";
					}else if(str[0].length()>17&&str[1].length()==2){
						playType="10";
					}
				}else{
					String str[]=content.split("#")[1].replace("^","").split("\\|");
					if(str[1].length()==2){
						playType="40";
					}else{
						playType="50";
					}
				}
				return playType;
			}
	};
	
	IVenderTicketConverter klsf=new IVenderTicketConverter() {
		@Override
		public String convert(Ticket ticket) {
			String ticketContent=ticket.getContent();
			int beishu=ticket.getMultiple();
			String contentStr="";
			int playType=Integer.parseInt(ticketContent.split("-")[0]);
			String beishuStr="";
			String contentLenStr="";
			if(beishu<=9) beishuStr="000"+beishu;
			else if(beishu>9&&beishu<=99){
				beishuStr="00"+beishu;
			}else if(beishu>99&&beishu<=999){
				beishuStr="0"+beishu;
			}else 
				beishuStr=beishu+"";
			String playTypeM=TianJinFXLotteryDef.playType.get(playType);
		    String playTypeStr=playTypeM+beishuStr;
		if(playType<=100419){//单式
			String content[]=ticketContent.split("-")[1].split("\\^");
			int contentLen=content[0].replace(",","").replace("^", "").length()/2;
			if(contentLen<=9){
				contentLenStr="0"+contentLen;
			}else 
				contentLenStr=contentLen+"";
			playTypeStr=playTypeStr+contentLenStr;
			for(String str:content){
					contentStr+=playTypeStr+str.replace(",","").replace("|", "~")+"^";
			}
		 }else if(playType<100432&&playType>=100421){//复式
			String content=ticketContent.split("-")[1].split("^")[0].replace(",","").replace("|", "~");
			int contentLen=ticketContent.split("-")[1].replace(",","").replace("^", "").length()/2;
			if(contentLen<9){
				contentLenStr="0"+contentLen;
			}else{
				contentLenStr=contentLen+"";
			}
			playTypeStr=playTypeStr+TianJinFXLotteryDef.playType.get(playType)+contentLenStr;
			contentStr+=playTypeStr+"*"+content;
		}else if(playType>TianJinFXLotteryDef.KlsfDingWei){//定位
			int contentLen=(ticketContent.split("-")[1].replace(",","")+ticketContent.split("-")[2].replace(",","").replace("^", "")).length()/2;
			if(contentLen<9){
				contentLenStr="0"+contentLen;
			}else{
				contentLenStr=contentLen+"";
			}
			playTypeStr=playTypeStr+TianJinFXLotteryDef.playType.get(playType)+contentLenStr;
			contentStr+=playTypeStr+"*"+ticketContent.split("-")[1].replace(",","")+ticketContent.split("-")[2].replace(",","");
		}else if(playType>=100432&&playType<=100439){//胆拖
			String []dan=ticketContent.split("-")[1].replace(",", "").replace("^", "").split("#");
			int danLen=dan[0].length()/2;
			int tuoLen=dan[1].length()/2;
			String danContentLen="";
			String tuoContentLen="";
			if(danLen<9){
				danContentLen="0"+danLen;
			}else{
				danContentLen=danLen+"";
			}
			if(tuoLen<9){
				tuoContentLen="0"+tuoLen;
			}else{
				tuoContentLen=tuoLen+"";
			}
			contentStr=playTypeStr+TianJinFXLotteryDef.playType.get(playType)+danContentLen+dan[0]+"*"+tuoContentLen+dan[1]+"^";
		}
		return contentStr;
	  }
    };
    
    IVenderTicketConverter sand=new IVenderTicketConverter() {
    	@Override
		public String convert(Ticket ticket) {
			String ticketContent=ticket.getContent();
			int beishu=ticket.getMultiple();
			String contentStr="";
			String beishuStr="";
			int playType=Integer.parseInt(ticketContent.split("-")[0]);
			if(beishu<=9){
				beishuStr="0"+beishu;
			}else 
				beishuStr=beishu+"";
			String playStr=TianJinFXLotteryDef.playTypeMap.get(playType);
			String playTypeStr=playStr+beishuStr;
			String content[]=ticketContent.split("-")[1].split("\\^");;
			for(String str:content){
				contentStr+=playTypeStr+str.replace(",","").replace("|", "").replace("#", "*")+"^";
			}
			return contentStr;
		}
    };
    IVenderTicketConverter qlc=new IVenderTicketConverter() {
    	@Override
		public String convert(Ticket ticket) {
			String ticketContent=ticket.getContent();
			int beishu=ticket.getMultiple();
			String contentStr="";
			String beishuStr="";
			int playType=Integer.parseInt(ticketContent.split("-")[0]);
			if(beishu<=9){
				beishuStr="0"+beishu;
			}else 
				beishuStr=beishu+"";
			String playStr=TianJinFXLotteryDef.playTypeMap.get(playType);
			String playTypeStr=playStr+beishuStr;
			if(playType==TianJinFXLotteryDef.qlcFuShi){
				playTypeStr=playTypeStr+"*";
			}
			String content[]=ticketContent.split("-")[1].split("\\^");;
			for(String str:content){
					contentStr+=playTypeStr+str.replace(",","").replace("|", "~").replace("#", "*")+"^";
			}
			return contentStr;
		}
	};
    
		//双色球
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssq);
		//快乐十分单式
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sh0, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_ss1, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr3, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr4, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sr5, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sq2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sq3, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sq2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_sz3, klsf);
		//快乐十分复式
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_ms1, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr3, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr4, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mr5, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mz2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_mz3, klsf);
		//快乐十分定位
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_pq2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_pq3, klsf);
		//快乐十分胆拖
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr3, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr4, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dr5, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dq2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dq3, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dz2, klsf);
		playTypeToAdvanceConverterMap.put(PlayType.tjkl10_dz3, klsf);
		//3d
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz,sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, sand);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, sand);
		//qlc
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, qlc);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, qlc);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, qlc);
	}
	
	
	
	
	//红单蓝单00  01,02,03,04,05,06|07^
	//红复蓝单10  01,02,03,04,05,06,08|07^
	//红单蓝复20  01,02,03,04,05,06|07,09^
	//红复蓝复30  01,02,03,04,05,06,09|07,09^
	//红胆蓝单40  08#01,02,03,04,05|07^
	//红胆蓝复50  08#01,02,03,04,05|07,09^  14,15,24,32,33#06,23|05,06^
	
	@Override
	protected Map<LotteryType, String> getLotteryTypeMap() {
		
		return TianJinFXLotteryDef.lotteryTypeMap;
	}

	@Override
	protected Map<Integer, String> getPlayTypeMap() {
		// TODO Auto-generated method stub
		return TianJinFXLotteryDef.playTypeMap;
	}

	
	protected Map<Integer, String> getPlayType() {
		return TianJinFXLotteryDef.playType;
	}
	
	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseConverterMap() {
		return TianJinFXLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<LotteryType, IPhaseConverter> getPhaseReverseConverterMap() {
		
		return TianJinFXLotteryDef.phaseConverterMap;
	}

	@Override
	protected Map<PlayType, ITicketContentConverter> getTicketContentConverterMap() {
		// TODO Auto-generated method stub
		return TianJinFXLotteryDef.ticketContentConverterMap;
	}
	//红单蓝单00  01,02,03,04,05,06|07^
	//红复蓝单10  01,02,03,04,05,06,08|07^
	//红单蓝复20  01,02,03,04,05,06|07,09^
	//红复蓝复30  01,02,03,04,05,06,09|07,09^
	//红胆蓝单40  08#01,02,03,04,05|07^
	//红胆蓝复50  08#01,02,03,04,05|07,09^  14,15,24,32,33#06,23|05,06^
	public static String ConvertSsqPlayType(String content){
		String playType="";
		if(!content.contains("#")){
			String str[]=content.split("\\^")[0].split("\\|");
			if(str[0].length()==17&&str[1].length()==2){
				playType="00";
			}else if(str[0].length()>17&&str[1].length()==2){
				playType="10";
			}else if(str[0].length()==17&&str[1].length()>2){
				playType="20";
			}else if(str[0].length()>17&&str[1].length()>2){
				playType="30";
			}else if(str[0].length()>17&&str[1].length()==2){
				playType="10";
			}
		}else{
			String str[]=content.split("#")[1].replace("^","").split("\\|");
			if(str[1].length()==2){
				playType="40";
			}else{
				playType="50";
			}
		}
		return playType;
	}
	@Override
	public String convertContent(Ticket ticket) {
		PlayType playType=PlayType.get(ticket.getPlayType());
		IVenderTicketConverter converter=null;
		if(playTypeToAdvanceConverterMap.containsKey(playType)){
			converter= playTypeToAdvanceConverterMap.get(playType);
		}
		if(converter!=null){
			return converter.convert(ticket);
		}
		return null;
	}

	/**
	 * 玩法转换
	 * @param playType
	 * @return
	 */
	public static String ConvertPlayType(Integer lotteryNo,String content){
		
		Integer playType=Integer.parseInt(content.split("-")[0]);
		String playStr="";
		switch (lotteryNo) {
		case 1:
			playStr=ConvertSsqPlayType(content);
			break;
		case 2:
			playStr=TianJinFXLotteryDef.playTypeMap.get(playType);
			break;
		case 3:
			playStr=TianJinFXLotteryDef.playTypeMap.get(playType);
			break;
		case 6:
			playStr=playType+"";
			break;
		default:
			break;
		}
		return playStr;
	}
	@Override
	protected String venderSpConvert(Ticket tickt, String venderSp) {
		return null;
	}

	@Override
	public LotteryType getLotteryMap(String lotteryId) {
		return null;
	}

	@Override
	protected Map<String, LotteryType> getReverseLotteryTypeMap() {
		return null;
	}
	
	@Override
	protected void init() {
		venderConverterBinder.put(TerminalType.T_TJFC, this);
	}
}
