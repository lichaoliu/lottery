package com.lottery.ticket.vender.impl.fcby;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class FcbyLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	/** 票内容转换 */
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
	
	static{
		/**
		 * 彩期转换
		 * */
		//默认的期号转换
		IPhaseConverter defaultPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		//彩种转换
		lotteryTypeMap.put(LotteryType.CJDLT, "T01001");
		lotteryTypeMap.put(LotteryType.PL3, "T01002");
		lotteryTypeMap.put(LotteryType.PL5, "T01011");
		lotteryTypeMap.put(LotteryType.QXC, "T01009");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "J000013");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "J000002");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "J000003");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "J000004");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ, "J000001");
		
		
		toLotteryTypeMap.put("T01001", LotteryType.CJDLT);
		toLotteryTypeMap.put("T01002", LotteryType.PL3);
		toLotteryTypeMap.put("T01011", LotteryType.PL5);
		toLotteryTypeMap.put("T01009", LotteryType.QXC);
		toLotteryTypeMap.put("J000013", LotteryType.JCZQ_RQ_SPF);
		toLotteryTypeMap.put("J00002", LotteryType.JCZQ_BF);
		toLotteryTypeMap.put("J00003", LotteryType.JCZQ_JQS);
		toLotteryTypeMap.put("J00004", LotteryType.JCZQ_BQC);
		toLotteryTypeMap.put("J00001", LotteryType.JCZQ_SPF_WRQ);
		
		
		//玩法转换
		//七星彩
		playTypeMap.put(PlayType.qxc_dan.getValue(), "");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "");
		//大乐透
		playTypeMap.put(PlayType.dlt_dan.getValue(), "");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "");
		//排三排五
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "1");//直选
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "1");//直选复式
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "6");//组选3
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "6");//组选6
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "Z3");//组三复试
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "Z6");//组六复式
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "S3");//组三和值
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "S6");//组六和值
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "S1");//直选和值
		
		
		
			//大乐投单式玩法
			IVenderTicketConverter dlt_dan=new IVenderTicketConverter() {
				@Override
					public String convert(Ticket ticket) {
						String []strs=ticket.getContent().split("-");
					    String lastContent="";
						String []content1=strs[1].split("\\^");
						for(int i=0;i<content1.length;i++){
							if(i!=content1.length-1){
								lastContent+=content1[i].replace("|","-").replace(","," ")+";";
				    		}else{
				    			lastContent+=content1[i].replace("|","-").replace(","," ");
				    		}
						}
					    return lastContent;
					}
			};
			//胆拖
			IVenderTicketConverter dlt_dantuo=new IVenderTicketConverter() {
				@Override
					public String convert(Ticket ticket) {
						String []strs=ticket.getContent().split("-");
						String []content1=strs[1].split("\\^");
						String lastContent="";
						for(int i=0;i<content1.length;i++){
							if(i!=content1.length-1){
								lastContent+=content1[i].replace("|","-").replace(","," ").replace("#", "$")+";";
				    		}else{
				    			lastContent+=content1[i].replace("|","-").replace(","," ").replace("#", "$");
				    		}
							
						}
						return lastContent;
				}
			};
			//排三单式玩法
			IVenderTicketConverter pl3_dan=new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String content=ticket.getContent();
					String []strs=content.split("-");
					PlayType playType=PlayType.get(ticket.getPlayType());
				 
					String play=FcbyLotteryDef.playTypeMap.get(playType.getValue());
			    	String []content1=strs[1].split("\\^");
			    	String lastContent="";
			    	for(int i=0;i<content1.length;i++){
			    		String []strs1=content1[i].split(",");
			    		String amtContent="";
			    		for(int j=0;j<strs1.length;j++){
			    			Arrays.sort(strs1);
			    			if(j!=strs1.length-1){
			    				amtContent+=Integer.parseInt(strs1[j])+",";
			    			}else{
			    				amtContent+=Integer.parseInt(strs1[j]);
			    			}
			    		}
			    		if(i!=content1.length-1){
			    			lastContent+=play+"|"+amtContent+";";
			    		}else{
			    			lastContent+=play+"|"+amtContent;
			    		}
			    	}
					return lastContent;
				}
			};
			//排三直选复式玩法
			IVenderTicketConverter p3_zhi_fs=new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String content=ticket.getContent();
					PlayType playType=PlayType.get(ticket.getPlayType());
					String play=FcbyLotteryDef.playTypeMap.get(playType.getValue());
					String []strs=content.split("-");
			    	String []content1=strs[1].replace("^", "").split("\\|");
			    	String lastContent="";
			    	for(int i=0;i<content1.length;i++){
			    		String []strs1=content1[i].split(",");
			    		String amtContent="";
			    		for(int j=0;j<strs1.length;j++){
			    			    Arrays.sort(strs1);
			    				amtContent+=Integer.parseInt(strs1[j]);
			    		}
			    		if(i!=content1.length-1){
			    			lastContent+=amtContent+",";
			    		}else{
			    			lastContent+=amtContent;
			    		}
			    	}
					lastContent=play+"|"+lastContent;
					return lastContent;
				}
			};
			//排五单式
			IVenderTicketConverter p5_dan=new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String lastContent="";
					String content=ticket.getContent();
					String []strs=content.split("-");
			    	String []content1=strs[1].split("\\^");
			    	for(int i=0;i<content1.length;i++){
			    		String amtContent="";
			    		String []strs1=content1[i].split(",");
			    		for(int j=0;j<strs1.length;j++){
			    			Arrays.sort(strs1);
			    			if(j!=strs1.length-1){
			    				amtContent+=Integer.parseInt(strs1[j])+",";
			    			}else{
			    				amtContent+=Integer.parseInt(strs1[j]);
			    			}
			    		}
			    		if(i!=content1.length-1){
			    			lastContent+=amtContent+";";
			    		}else{
			    			lastContent+=amtContent;
			    		}
			    	}
			    	return lastContent;
				}
			};
			
			//排五复式
			IVenderTicketConverter p5_fs=new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String []strs=ticket.getContent().split("-");
				    String lastContent="";
			    	String []content1=strs[1].replace("^", "").split("\\|");
			    	for(int i=0;i<content1.length;i++){
			    		String amtContent="";
			    		String []strs1=content1[i].split(",");
			    		for(int j=0;j<strs1.length;j++){
			    			    Arrays.sort(strs1);
			    				amtContent+=Integer.parseInt(strs1[j]);
			    		}
			    		if(i!=content1.length-1){
			    			lastContent+=amtContent+",";
			    		}else{
			    			lastContent+=amtContent;
			    		}
			    	}
			    	return lastContent;
				}
			};
		
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, dlt_dan);
			//排三
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, p3_zhi_fs);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, pl3_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, pl3_dan);
			//排五
			playTypeToAdvanceConverterMap.put(PlayType.p5_dan, p5_dan);
			playTypeToAdvanceConverterMap.put(PlayType.p5_fu, p5_fs);
		    //七星彩
			playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, p5_dan);
			playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, p5_fs);
			//大乐透
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, dlt_dan);
			playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, dlt_dan);
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dlt_dantuo);
			
			
	
		
		
		
	}
}
