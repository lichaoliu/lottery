package com.lottery.ticket.vender.impl.xcs;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;
import org.apache.commons.lang.StringUtils;

import java.util.HashMap;
import java.util.Map;

public class XCSLotteryDef {
	/** 彩种转换 */
	protected static Map<Integer, String> lotteryTypeMap = new HashMap<Integer, String>();
	protected static Map<LotteryType, String> lotteryTypeToMap = new HashMap<LotteryType, String>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	public static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	/** 玩法编码转换*/
	public static Map<Integer, String> playCodeMap = new HashMap<Integer, String>();
	public static Map<String, Integer> playTypeMapJc = new HashMap<String, Integer>();
	/** 票内容转换 */
	protected static Map<PlayType, ITicketContentConverter> ticketContentConverterMap = new HashMap<PlayType, ITicketContentConverter>();
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
	
	static{
		/**
		 * 彩期转换
		 * */
		
		IPhaseConverter qhPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};

		
		
		//足彩彩期
		phaseConverterMap.put(LotteryType.ZC_SFC, qhPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, qhPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, qhPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, qhPhaseConverter);
		
		lotteryTypeToMap.put(LotteryType.CJDLT, "03");
		lotteryTypeToMap.put(LotteryType.PL3, "01");
		lotteryTypeToMap.put(LotteryType.PL5, "0101");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF.getValue(), "62");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF.getValue(), "61");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC.getValue(), "63");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF.getValue(), "64");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF.getValue(), "56");
		lotteryTypeMap.put(LotteryType.JCZQ_BF.getValue(), "52");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS.getValue(), "53");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC.getValue(), "54");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ.getValue(), "51");
		
		lotteryTypeToMap.put(LotteryType.ZC_BQC, "33");
		lotteryTypeToMap.put(LotteryType.ZC_JQC, "34");
		lotteryTypeToMap.put(LotteryType.ZC_RJC, "32");
		lotteryTypeToMap.put(LotteryType.ZC_SFC, "31");
		
		lotteryTypeToMap.put(LotteryType.CJDLT, "03");
		lotteryTypeToMap.put(LotteryType.PL3, "01");
		lotteryTypeToMap.put(LotteryType.PL5, "0101");
		
		lotteryTypeToMap.put(LotteryType.JCZQ_SPF_WRQ, "11");
		lotteryTypeToMap.put(LotteryType.JCZQ_BQC, "12");
		lotteryTypeToMap.put(LotteryType.JCZQ_JQS, "13");
		lotteryTypeToMap.put(LotteryType.JCZQ_BF, "14");
		lotteryTypeToMap.put(LotteryType.JCZQ_RQ_SPF, "16");
		lotteryTypeToMap.put(LotteryType.JCZQ_HHGG, "19");
		
		lotteryTypeToMap.put(LotteryType.JCLQ_SF, "21");
		lotteryTypeToMap.put(LotteryType.JCLQ_RFSF, "22");
		lotteryTypeToMap.put(LotteryType.JCLQ_SFC, "23");
		lotteryTypeToMap.put(LotteryType.JCLQ_DXF, "24");
		lotteryTypeToMap.put(LotteryType.JCLQ_HHGG, "29");
		lotteryTypeToMap.put(LotteryType.SSQ,"15");

		//玩法转换
		//足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_rjc_dt.getValue(), "2");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "1");
		playTypeMap.put(PlayType.ssq_dan.value,"1");
		playTypeMap.put(PlayType.ssq_fs.value,"2");
		playTypeMap.put(PlayType.ssq_dt.value,"3");
		
		//传统足彩
		IVenderTicketConverter zcrj = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				if("01".equals(ticket.getContent().substring(4,6))){
					String[] oneCode = contentStr.split("\\^");
					for (int j=0; j < oneCode.length; j++) {
						String content = oneCode[j].replace(",","").replace("~","*");
						ticketContent.append(content);
						if(j!=oneCode.length-1){
							ticketContent.append("|");
						}
					}
				}else if("02".equals(ticket.getContent().substring(4,6))){
					ticketContent.append(contentStr.replace("^","").replace(",","-").replace("~","*"));
				}
				 return ticketContent.toString();
		}};
		//大乐透
		IVenderTicketConverter dlt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				if("01".equals(ticket.getContent().substring(4,6))){
					String[] oneCode = contentStr.split("\\^");
					for (int j=0; j < oneCode.length; j++) {
						String content = oneCode[j].replace(",","").replace("|","+");
						ticketContent.append(content);
						if(j!=oneCode.length-1){
							ticketContent.append("|");
						}
					}
				}else if("02".equals(ticket.getContent().substring(4,6))){
					String []strs=contentStr.replace("^","").split("\\|");
					ticketContent.append("0").append(strs[0].replace(",", "")).append("|").append("0").append(strs[1].replace(",", ""));
				}
				 return ticketContent.toString();
		}};
		
		
		
		//传统足彩
		IVenderTicketConverter zcbqc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				if("01".equals(ticket.getContent().substring(4,6))){
					String[] oneCode = contentStr.split("\\^");
					for (int j=0; j < oneCode.length; j++) {
						String content = oneCode[j].replace(",","");
						ticketContent.append(content);
						if(j!=oneCode.length-1){
							ticketContent.append("|");
						}
					}
				}else{
					ticketContent.append(contentStr.replace("^","").replace(",","-"));
				}
				 return ticketContent.toString();
				
		}};
		
		
		 //竞彩
		IVenderTicketConverter jcspfgg = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer strBuffer=new StringBuffer();
		        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
		        int i=0;
                for(String str:strs){
			        	strBuffer.append(DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",str.substring(0, 8)))).append("|")
			        	.append(str.split("\\(")[0].substring(8)).append("|").append(str.split("\\(")[1].split("\\)")[0]);
			        	if(i!=strs.length-1){
			        		strBuffer.append("|");
			        	}
			        	i++;
			       }
                  return strBuffer.toString();
			}};
			
			
			//竞彩
			IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
	                	for(String str:strs){
	    		        	strBuffer.append(DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",str.substring(0, 8)))).append("|")
	    		        	.append(str.split("\\(")[0].substring(8)).append("|").append(str.split("\\(")[1].split("\\)")[0].replace("3","1").replace("0","2"));
	    		        	if(i!=strs.length-1){
	    		        		strBuffer.append("|");
	    		        	}
	    		        	i++;
	    		        }
					
	                  return strBuffer.toString();
				}};
			
			
			 //竞彩混合
			IVenderTicketConverter jchh = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
			        int playType=Integer.parseInt(ticket.getContent().split("-")[0].substring(0,3));
			        	 for(String str:strs){
					        	if(str.contains("*")){
					        		int lotteryType=Integer.parseInt(str.split("\\*")[1].split("\\(")[0]);
					        		if(lotteryType==LotteryType.JCLQ_SF.getValue()||lotteryType==LotteryType.JCLQ_RFSF.getValue()){
					        			strBuffer.append(lotteryTypeMap.get(lotteryType)).append("|");
						        		strBuffer.append(DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",str.substring(0, 8)))).append("|")
							        	.append(str.split("\\(")[0].substring(8,11)).append("|").append(str.split("\\(")[1].split("\\)")[0].replace("3","1").replace("0","2"));
                                     }else{
                                    	strBuffer.append(lotteryTypeMap.get(Integer.parseInt(str.split("\\*")[1].split("\\(")[0]))).append("|");
     					        		strBuffer.append(DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",str.substring(0, 8)))).append("|")
     						        	.append(str.split("\\(")[0].substring(8,11)).append("|").append(str.split("\\(")[1].split("\\)")[0]);
                                     }
					        	}else{
					        		if(playType==LotteryType.JCLQ_SF.getValue()||playType==LotteryType.JCLQ_RFSF.getValue()){
					        			strBuffer.append(lotteryTypeMap.get(Integer.parseInt(ticket.getContent().split("-")[0].substring(0,4)))).append("|");
						        		strBuffer.append(DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",str.substring(0, 8)))).append("|")
							        	.append(str.split("\\(")[0].substring(8)).append("|").append(str.split("\\(")[1].split("\\)")[0].replace("3","1").replace("0","2"));
					        		}else{
					        			strBuffer.append(lotteryTypeMap.get(Integer.parseInt(ticket.getContent().split("-")[0].substring(0,4)))).append("|");
						        		strBuffer.append(DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",str.substring(0, 8)))).append("|")
							        	.append(str.split("\\(")[0].substring(8)).append("|").append(str.split("\\(")[1].split("\\)")[0]);
                                     }
					        	}
					        	
					        	if(i!=strs.length-1){
					        		strBuffer.append("|");
					        	}
					        	i++;
					        }
			       
	                  return strBuffer.toString();
				}};

		//双色球
		//单式 01 02 03 04 05 06 01|01 03 05 07 08 16
		IVenderTicketConverter ssq_dan= new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String content=ticket.getContent();
				content= StringUtils.split(content,"-")[1];
				String betcode=content.replace(","," ").replace("|"," ").replace("^","|");
				if (betcode.charAt(betcode.length() - 1) == '|') {
					betcode=betcode.substring(0,betcode.length()-1);
				}
				return betcode;
			}
		};

		//复式01 02 03 04 05 06 07|01
		IVenderTicketConverter ssq_fs= new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String content=ticket.getContent();
				content= StringUtils.split(content,"-")[1];
				return content.replace(","," ").replace("^","");


			}
		};
		//胆拖 01 02|03 04 05 06 07|01
		IVenderTicketConverter ssq_dt= new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String content=ticket.getContent();
				content= StringUtils.split(content,"-")[1];
				return content.replace(","," ").replace("#", "|").replace("^", "");
			}
		};
        //双色球
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan,ssq_dan);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs,ssq_fs);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt,ssq_dt);


 		 
 		 //老足彩
 		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan,zcbqc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu,zcbqc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan,zcrj);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu,zcrj);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan,zcbqc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu,zcbqc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan,zcbqc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu,zcbqc);
 		
 		playTypeToAdvanceConverterMap.put(PlayType.dlt_dan,dlt);
 		playTypeToAdvanceConverterMap.put(PlayType.dlt_fu,dlt);
 		
 		
 		//竞彩
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jcspfgg);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jcspfgg);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jcspfgg);
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jcspfgg);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jcspfgg);
     playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jcspfgg);
     playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jcspfgg);
   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11, jcspfgg);
   	 
   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jcspfgg);
   	 
   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,jchh);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,jchh);
//   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jcspfgg);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jcspfgg);
   	 
   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jclqsf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jclqsf);
			    		
			    		
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jclqsf);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jclqsf);
			    		
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,jcspfgg);
			    		
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jcspfgg);
	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jcspfgg);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jcspfgg);
	 
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,jchh);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,jchh);
	}
	
}
