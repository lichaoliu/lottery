package com.lottery.ticket.vender.impl.gaode;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

public class GaoDeLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<LotteryType, String> playCodeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<String,Integer> toLotteryTypeMap = new HashMap<String,Integer>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	public static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
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
		phaseConverterMap.put(LotteryType.DC_SFGG, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.DC_BF, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.DC_SXDS, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.DC_BQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.DC_ZJQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.DC_SPF, defaultPhaseConverter);
		
		
		lotteryTypeMap.put(LotteryType.DC_SFGG, "100");
		lotteryTypeMap.put(LotteryType.DC_BF, "100");
		lotteryTypeMap.put(LotteryType.DC_SXDS, "100");
		lotteryTypeMap.put(LotteryType.DC_BQC, "100");
		lotteryTypeMap.put(LotteryType.DC_ZJQ, "100");
		lotteryTypeMap.put(LotteryType.DC_SPF, "100");
		
		playCodeMap.put(LotteryType.DC_SFGG, "7");
		playCodeMap.put(LotteryType.DC_BF, "4");
		playCodeMap.put(LotteryType.DC_SXDS, "3");
		playCodeMap.put(LotteryType.DC_BQC, "5");
		playCodeMap.put(LotteryType.DC_ZJQ, "2");
		playCodeMap.put(LotteryType.DC_SPF, "1");
		
		toLotteryTypeMap.put("76",LotteryType.DC_SFGG.getValue());
		toLotteryTypeMap.put("47",LotteryType.DC_BF.getValue());
		toLotteryTypeMap.put("46",LotteryType.DC_SXDS.getValue());
		toLotteryTypeMap.put("48",LotteryType.DC_BQC.getValue()); 
		toLotteryTypeMap.put("45",LotteryType.DC_ZJQ.getValue()); 
		toLotteryTypeMap.put("44",LotteryType.DC_SPF.getValue());
		
		playTypeMap.put(PlayType.dc_bf_1_1.getValue(),"单关");
		playTypeMap.put(PlayType.dc_bf_2_1.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_bf_2_3.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_bf_3_1.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_bf_3_4.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_bf_3_7.getValue(),"3串7");
		
		playTypeMap.put(PlayType.dc_bf_2_1_dan.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_bf_2_3_dan.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_bf_3_1_dan.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_bf_3_4_dan.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_bf_3_7_dan.getValue(),"3串7");
		
		
		playTypeMap.put(PlayType.dc_bqc_1_1.getValue(),"单关");
		playTypeMap.put(PlayType.dc_bqc_2_1.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_bqc_2_3.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_bqc_3_1.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_bqc_3_4.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_bqc_3_7.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_bqc_4_1.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_bqc_4_5.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_bqc_4_11.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_bqc_4_15.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_bqc_5_1.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_bqc_5_6.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_bqc_5_16.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_bqc_5_26.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_bqc_5_31.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_bqc_6_1.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_bqc_6_7.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_bqc_6_22.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_bqc_6_42.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_bqc_6_57.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_bqc_6_63.getValue(),"6串63");
		
		
		
		playTypeMap.put(PlayType.dc_bqc_2_1_dan.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_bqc_2_3_dan.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_bqc_3_1_dan.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_bqc_3_4_dan.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_bqc_3_7_dan.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_bqc_4_1_dan.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_bqc_4_5_dan.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_bqc_4_11_dan.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_bqc_4_15_dan.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_bqc_5_1_dan.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_bqc_5_6_dan.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_bqc_5_16_dan.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_bqc_5_26_dan.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_bqc_5_31_dan.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_bqc_6_1_dan.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_bqc_6_7_dan.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_bqc_6_22_dan.getValue(),"5串22");
		playTypeMap.put(PlayType.dc_bqc_6_42_dan.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_bqc_6_57_dan.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_bqc_6_63_dan.getValue(),"6串63");
		
		
		
		playTypeMap.put(PlayType.dc_spf_1_1.getValue(),"单关");
		playTypeMap.put(PlayType.dc_spf_2_1.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_spf_2_3.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_spf_3_1.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_spf_3_4.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_spf_3_7.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_spf_4_1.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_spf_4_5.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_spf_4_11.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_spf_4_15.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_spf_5_1.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_spf_5_6.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_spf_5_16.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_spf_5_26.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_spf_5_31.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_spf_6_1.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_spf_6_7.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_spf_6_22.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_spf_6_42.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_spf_6_57.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_spf_6_63.getValue(),"6串63");
		playTypeMap.put(PlayType.dc_spf_7_1.getValue(),"7串1");
		playTypeMap.put(PlayType.dc_spf_8_1.getValue(),"8串1");
		playTypeMap.put(PlayType.dc_spf_9_1.getValue(),"9串1");
		playTypeMap.put(PlayType.dc_spf_10_1.getValue(),"10串1");
		playTypeMap.put(PlayType.dc_spf_11_1.getValue(),"11串1");
		playTypeMap.put(PlayType.dc_spf_12_1.getValue(),"12串1");
		playTypeMap.put(PlayType.dc_spf_13_1.getValue(),"13串1");
		playTypeMap.put(PlayType.dc_spf_14_1.getValue(),"14串1");
		playTypeMap.put(PlayType.dc_spf_15_1.getValue(),"15串1");
		
		playTypeMap.put(PlayType.dc_spf_1_1_dan.getValue(),"单关");
		playTypeMap.put(PlayType.dc_spf_2_1_dan.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_spf_2_3_dan.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_spf_3_1_dan.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_spf_3_4_dan.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_spf_3_7_dan.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_spf_4_1_dan.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_spf_4_5_dan.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_spf_4_11_dan.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_spf_4_15_dan.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_spf_5_1_dan.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_spf_5_6_dan.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_spf_5_16_dan.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_spf_5_26_dan.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_spf_5_31_dan.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_spf_6_1_dan.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_spf_6_7_dan.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_spf_6_22_dan.getValue(),"6串33");
		playTypeMap.put(PlayType.dc_spf_6_42_dan.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_spf_6_57_dan.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_spf_6_63_dan.getValue(),"6串63");
		playTypeMap.put(PlayType.dc_spf_7_1_dan.getValue(),"7串1");
		playTypeMap.put(PlayType.dc_spf_8_1_dan.getValue(),"8串1");
		playTypeMap.put(PlayType.dc_spf_9_1_dan.getValue(),"9串1");
		playTypeMap.put(PlayType.dc_spf_10_1_dan.getValue(),"10串1");
		playTypeMap.put(PlayType.dc_spf_11_1_dan.getValue(),"11串1");
		playTypeMap.put(PlayType.dc_spf_12_1_dan.getValue(),"12串1");
		playTypeMap.put(PlayType.dc_spf_13_1_dan.getValue(),"13串1");
		playTypeMap.put(PlayType.dc_spf_14_1_dan.getValue(),"14串1");
		playTypeMap.put(PlayType.dc_spf_15_1_dan.getValue(),"15串1");
		
		playTypeMap.put(PlayType.dc_sxds_1_1.getValue(),"单关");
		playTypeMap.put(PlayType.dc_sxds_2_1.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_sxds_2_3.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_sxds_3_1.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_sxds_3_4.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_sxds_3_7.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_sxds_4_1.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_sxds_4_5.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_sxds_4_11.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_sxds_4_15.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_sxds_5_1.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_sxds_5_6.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_sxds_5_16.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_sxds_5_26.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_sxds_5_31.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_sxds_6_1.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_sxds_6_7.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_sxds_6_22.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_sxds_6_42.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_sxds_6_57.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_sxds_6_63.getValue(),"6串63");
		
		playTypeMap.put(PlayType.dc_sxds_1_1_dan.getValue(),"单关");
		playTypeMap.put(PlayType.dc_sxds_2_1_dan.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_sxds_2_3_dan.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_sxds_3_1_dan.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_sxds_3_4_dan.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_sxds_3_7_dan.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_sxds_4_1_dan.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_sxds_4_5_dan.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_sxds_4_11_dan.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_sxds_4_15_dan.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_sxds_5_1_dan.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_sxds_5_6_dan.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_sxds_5_16_dan.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_sxds_5_26_dan.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_sxds_5_31_dan.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_sxds_6_1_dan.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_sxds_6_7_dan.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_sxds_6_22_dan.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_sxds_6_42_dan.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_sxds_6_57_dan.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_sxds_6_63_dan.getValue(),"6串63");
	
		playTypeMap.put(PlayType.dc_zjq_1_1.getValue(),"单关");
		playTypeMap.put(PlayType.dc_zjq_2_1.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_zjq_2_3.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_zjq_3_1.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_zjq_3_4.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_zjq_3_7.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_zjq_4_1.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_zjq_4_5.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_zjq_4_11.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_zjq_4_15.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_zjq_5_1.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_zjq_5_6.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_zjq_5_16.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_zjq_5_26.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_zjq_5_31.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_zjq_6_1.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_zjq_6_7.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_zjq_6_22.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_zjq_6_42.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_zjq_6_57.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_zjq_6_63.getValue(),"6串63");
		
		
		playTypeMap.put(PlayType.dc_zjq_1_1_dan.getValue(),"单关");
		playTypeMap.put(PlayType.dc_zjq_2_1_dan.getValue(),"2串1");
		playTypeMap.put(PlayType.dc_zjq_2_3_dan.getValue(),"2串3");
		playTypeMap.put(PlayType.dc_zjq_3_1_dan.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_zjq_3_4_dan.getValue(),"3串4");
		playTypeMap.put(PlayType.dc_zjq_3_7_dan.getValue(),"3串7");
		playTypeMap.put(PlayType.dc_zjq_4_1_dan.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_zjq_4_5_dan.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_zjq_4_11_dan.getValue(),"4串11");
		playTypeMap.put(PlayType.dc_zjq_4_15_dan.getValue(),"4串15");
		playTypeMap.put(PlayType.dc_zjq_5_1_dan.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_zjq_5_6_dan.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_zjq_5_16_dan.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_zjq_5_26_dan.getValue(),"5串26");
		playTypeMap.put(PlayType.dc_zjq_5_31_dan.getValue(),"5串31");
		playTypeMap.put(PlayType.dc_zjq_6_1_dan.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_zjq_6_7_dan.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_zjq_6_22_dan.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_zjq_6_42_dan.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_zjq_6_57_dan.getValue(),"6串57");
		playTypeMap.put(PlayType.dc_zjq_6_63_dan.getValue(),"6串63");
		
		
		playTypeMap.put(PlayType.dc_sfgg_3_1.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_sfgg_4_1.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_sfgg_4_5.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_sfgg_5_1.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_sfgg_5_6.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_sfgg_5_16.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_sfgg_6_1.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_sfgg_6_7.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_sfgg_6_22.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_sfgg_6_42.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_sfgg_7_1.getValue(),"7串1");
		playTypeMap.put(PlayType.dc_sfgg_8_1.getValue(),"8串1");
		playTypeMap.put(PlayType.dc_sfgg_9_1.getValue(),"9串1");
		playTypeMap.put(PlayType.dc_sfgg_10_1.getValue(),"10串1");
		playTypeMap.put(PlayType.dc_sfgg_11_1.getValue(),"11串1");
		playTypeMap.put(PlayType.dc_sfgg_12_1.getValue(),"12串1");
		playTypeMap.put(PlayType.dc_sfgg_13_1.getValue(),"13串1");
		playTypeMap.put(PlayType.dc_sfgg_14_1.getValue(),"14串1");
		playTypeMap.put(PlayType.dc_sfgg_15_1.getValue(),"15串1");
		
		
		playTypeMap.put(PlayType.dc_sfgg_3_1_dan.getValue(),"3串1");
		playTypeMap.put(PlayType.dc_sfgg_4_1_dan.getValue(),"4串1");
		playTypeMap.put(PlayType.dc_sfgg_4_5_dan.getValue(),"4串5");
		playTypeMap.put(PlayType.dc_sfgg_5_1_dan.getValue(),"5串1");
		playTypeMap.put(PlayType.dc_sfgg_5_6_dan.getValue(),"5串6");
		playTypeMap.put(PlayType.dc_sfgg_5_16_dan.getValue(),"5串16");
		playTypeMap.put(PlayType.dc_sfgg_6_1_dan.getValue(),"6串1");
		playTypeMap.put(PlayType.dc_sfgg_6_7_dan.getValue(),"6串7");
		playTypeMap.put(PlayType.dc_sfgg_6_22_dan.getValue(),"6串22");
		playTypeMap.put(PlayType.dc_sfgg_6_42_dan.getValue(),"6串42");
		playTypeMap.put(PlayType.dc_sfgg_7_1_dan.getValue(),"7串1");
		playTypeMap.put(PlayType.dc_sfgg_8_1_dan.getValue(),"8串1");
		playTypeMap.put(PlayType.dc_sfgg_9_1_dan.getValue(),"9串1");
		playTypeMap.put(PlayType.dc_sfgg_10_1_dan.getValue(),"10串1");
		playTypeMap.put(PlayType.dc_sfgg_11_1_dan.getValue(),"11串1");
		playTypeMap.put(PlayType.dc_sfgg_12_1_dan.getValue(),"12串1");
		playTypeMap.put(PlayType.dc_sfgg_13_1_dan.getValue(),"13串1");
		playTypeMap.put(PlayType.dc_sfgg_14_1_dan.getValue(),"14串1");
		playTypeMap.put(PlayType.dc_sfgg_15_1_dan.getValue(),"15串1");
		
		
		// 单场胜平负
		IVenderTicketConverter dcspf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strStr=ticket.getContent().split("\\-")[1].replace("^","");
				String []contents=strStr.split("\\|");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String content:contents){
			        String []cons=content.split("\\(")[1].split("\\)")[0].split("\\,");
			        stringBuffer.append(Integer.parseInt(content.split("\\(")[0])).append(":");
			        for(String con:cons){
			        	stringBuffer.append(con);
			        }
			        if(i!=contents.length-1){
						stringBuffer.append("/");
					}
					i++;
				}
				return stringBuffer.toString();
			}
		};
		
		// 单场比分
		IVenderTicketConverter dcbf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strStr=ticket.getContent().split("\\-")[1].replace("^","");
				String []contents=strStr.split("\\|");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String content:contents){
			        String []cons=content.split("\\(")[1].split("\\)")[0].split("\\,");
			        stringBuffer.append(Integer.parseInt(content.split("\\(")[0])).append(":");
			        for(String con:cons){
			        	stringBuffer.append(con);
			        }
			        if(i!=contents.length-1){
						stringBuffer.append("/");
					}
					i++;
				}
				return stringBuffer.toString();
			}
		};
		
		// 单场半全场
		IVenderTicketConverter dcbqc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strStr=ticket.getContent().split("\\-")[1].replace("^","");
				String []contents=strStr.split("\\|");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String content:contents){
			        String []cons=content.split("\\(")[1].split("\\)")[0].split("\\,");
			        stringBuffer.append(Integer.parseInt(content.split("\\(")[0])).append(":");
			        for(String con:cons){
			        	stringBuffer.append(con);
			        }
			        if(i!=contents.length-1){
						stringBuffer.append("/");
					}
					i++;
				}
				return stringBuffer.toString();
			}
		};
		// 单场大小单双
		IVenderTicketConverter dcdxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strStr=ticket.getContent().split("\\-")[1].replace("^","");
				String []contents=strStr.split("\\|");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String content:contents){
					stringBuffer.append(Integer.parseInt(content.split("\\(")[0])).append(":");
					String []cons=content.split("\\(")[1].split("\\)")[0].split("\\,");
					String f=null;
					for(String con:cons){
						if("2".equals(con)){
							f="4";
						}else if("4".equals(con)){
							f="2";
						}else if("1".equals(con)){
							f="3";
						}else if("3".equals(con)){
							f="1";
						}
						stringBuffer.append(f);
						
					}
					if(i!=contents.length-1){
						stringBuffer.append("/");
					}
					i++;
				}
				return stringBuffer.toString();
			}
		};
		// 单场总进球
		IVenderTicketConverter dczjq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strStr=ticket.getContent().split("\\-")[1].replace("^","");
				String []contents=strStr.split("\\|");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String content:contents){
			        String []cons=content.split("\\(")[1].split("\\)")[0].split("\\,");
			        stringBuffer.append(Integer.parseInt(content.split("\\(")[0])).append(":");
			        for(String con:cons){
			        	stringBuffer.append(con);
			        }
			        if(i!=contents.length-1){
						stringBuffer.append("/");
					}
					i++;
				}
				return stringBuffer.toString();
			}
		};


		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_1_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_2_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_2_3,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_4,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_7,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_5,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_11,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_15,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_6,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_16,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_26,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_31,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_7,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_22,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_42,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_57,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_63,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_7_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_8_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_9_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_10_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_11_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_12_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_13_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_14_1,dcspf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_15_1,dcspf);

		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_1_1,dcbf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_2_1,dcbf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_2_3,dcbf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_1,dcbf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_4,dcbf);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_7,dcbf);
		
		
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_1_1,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_2_1,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_2_3,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_1,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_4,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_7,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_1,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_5,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_11,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_15,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_1,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_6,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_16,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_26,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_31,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_1,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_7,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_22,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_42,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_57,dcbqc);
		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_63,dcbqc);
		
	
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_1_1,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_2_1,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_2_3,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_1,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_4,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_7,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_1,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_5,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_11,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_15,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_1,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_6,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_16,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_26,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_31,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_1,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_7,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_22,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_42,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_57,dcdxds);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_63,dcdxds);
		
		
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_1_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_2_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_2_3,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_4,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_7,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_5,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_11,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_15,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_6,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_16,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_26,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_31,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_7,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_22,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_42,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_57,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_63,dczjq);

		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_3_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_4_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_4_5,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_6,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_16,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_7,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_22,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_42,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_7_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_8_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_9_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_10_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_11_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_12_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_13_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_14_1,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_15_1,dczjq);
		
		
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_3_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_4_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_4_5_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_6_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_16_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_7_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_22_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_42_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_7_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_8_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_9_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_10_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_11_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_12_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_13_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_14_1_dan,dczjq);
		playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_15_1_dan,dczjq);
		
		
	}
	     
	
  
}
