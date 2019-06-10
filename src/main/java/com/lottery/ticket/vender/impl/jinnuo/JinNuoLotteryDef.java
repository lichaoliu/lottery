package com.lottery.ticket.vender.impl.jinnuo;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class JinNuoLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<Integer, String> toLotteryTypeMap = new HashMap<Integer, String>();
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
		 * 
		*/
		IPhaseConverter jrPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		//足彩彩期
		phaseConverterMap.put(LotteryType.ZC_SFC, jrPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, jrPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, jrPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, jrPhaseConverter);
		
		//彩种转换
		lotteryTypeMap.put(LotteryType.ZC_SFC, "102");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "103");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "106");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "107");
		
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "511");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "502");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "503");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "504");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ, "501");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG, "509");
		lotteryTypeMap.put(LotteryType.JCLQ_SF, "505");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF, "506");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC, "507");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF, "508");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG, "510");
		
		
		//玩法转换
		//足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "05");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "05");
		playTypeMap.put(PlayType.zc_rjc_dt.getValue(), "03");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "05");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "05");
		
		
		//竞彩串法
		 playTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "01");
		 playTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_4_11.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "02");               
	     playTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_7_120.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_spf_8_247.getValue(), "02");
	   	 
	   	 playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(), "01");
	   	 playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(), "02");
	   	 playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(), "02");
	     playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(), "02");
		 
		 playTypeMap.put(PlayType.jczq_bf_1_1.getValue(), "01");
		 playTypeMap.put(PlayType.jczq_bf_2_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_3_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_4_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_5_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_6_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_7_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_8_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_3_3.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_3_4.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_4_4.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_4_5.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_4_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_bf_4_11.getValue(), "02");
		 
		 playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(), "01");
	 	 playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(), "02");
	 	 playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(), "02");
	 	 
		 playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "01");
		 playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "02");
		 
		 playTypeMap.put(PlayType.jczq_mix_2_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_3_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_4_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_7_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_3_3.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_3_4.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_4_4.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_4_5.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_4_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_4_11.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_5.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_10.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_16.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_20.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_5_26.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_6.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_7.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_15.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_20.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_22.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_35.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_42.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_50.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_6_57.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_7_7.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_7_8.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_7_21.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_7_35.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_7_120.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_8.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_9.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_28.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_56.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_70.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_mix_8_247.getValue(), "02");
	  	 
   	 
		    playTypeMap.put(PlayType.jclq_sf_1_1.getValue(),"01");
	    	playTypeMap.put(PlayType.jclq_sf_2_1.getValue(),"02");
	    	playTypeMap.put(PlayType.jclq_sf_3_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(),"02");
	 		
	 		
	 		
	 		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"01");
	 		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"02");
	 		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"02"); 
		 
		 

		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"01");
 		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"02");
 		
 		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"01");
 		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"02");
 		
 		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"02");
 		
 		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"02");
 		
 		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"02");
 		
 		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"02");
 		
 		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"01");
 		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"02");
 		
     	 
     	
		
		
		////
 		toLotteryTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "1");
 		toLotteryTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "2");
 		toLotteryTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "3");
 		toLotteryTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "6");
 		toLotteryTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "11");
 		toLotteryTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "18");
	     toLotteryTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "28");
	     toLotteryTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "35");
	     toLotteryTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "4");
	     toLotteryTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "5");
	     toLotteryTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "7");
	     toLotteryTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "8");
	     toLotteryTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "9");
	     toLotteryTypeMap.put(PlayType.jczq_spf_4_11.getValue(), "10");
	     toLotteryTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "12");
	     toLotteryTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "13");               
	     toLotteryTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "14");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "15");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "16");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "17");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "19");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "20");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "21");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "22");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "23");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "24");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "25");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "26");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "27");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "29");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "30");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "31");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "32");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_7_120.getValue(), "33");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "36");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "37");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "38");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "39");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "40");
	   	 toLotteryTypeMap.put(PlayType.jczq_spf_8_247.getValue(), "41");
	   	 
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_1_1.getValue(), "1");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_2_1.getValue(), "2");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_3_1.getValue(), "3");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_4_1.getValue(), "6");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_1.getValue(), "11");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_6_1.getValue(), "18");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_7_1.getValue(), "28");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_8_1.getValue(), "35");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_3_3.getValue(), "4");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_3_4.getValue(), "5");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_4_4.getValue(), "7");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_4_5.getValue(), "8");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_4_6.getValue(), "9");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_4_11.getValue(), "10");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_5.getValue(), "12");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_6.getValue(), "13");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_10.getValue(), "14");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_16.getValue(), "15");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_20.getValue(), "16");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_5_26.getValue(), "17");
	   	 toLotteryTypeMap.put(PlayType.jczq_jqs_6_6.getValue(), "19");
	     toLotteryTypeMap.put(PlayType.jczq_jqs_6_7.getValue(), "20");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_15.getValue(), "21");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_20.getValue(), "22");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_22.getValue(), "23");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_35.getValue(), "24");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_42.getValue(), "25");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_50.getValue(), "26");
		 toLotteryTypeMap.put(PlayType.jczq_jqs_6_57.getValue(), "27");
		 
		 toLotteryTypeMap.put(PlayType.jczq_bf_1_1.getValue(), "1");
		 toLotteryTypeMap.put(PlayType.jczq_bf_2_1.getValue(), "2");
		 toLotteryTypeMap.put(PlayType.jczq_bf_3_1.getValue(), "3");
		 toLotteryTypeMap.put(PlayType.jczq_bf_4_1.getValue(), "6");
		 toLotteryTypeMap.put(PlayType.jczq_bf_5_1.getValue(), "11");
		 toLotteryTypeMap.put(PlayType.jczq_bf_6_1.getValue(), "18");
		 toLotteryTypeMap.put(PlayType.jczq_bf_7_1.getValue(), "28");
		 toLotteryTypeMap.put(PlayType.jczq_bf_8_1.getValue(), "35");
		 toLotteryTypeMap.put(PlayType.jczq_bf_3_3.getValue(), "4");
		 toLotteryTypeMap.put(PlayType.jczq_bf_3_4.getValue(), "5");
		 toLotteryTypeMap.put(PlayType.jczq_bf_4_4.getValue(), "7");
		 toLotteryTypeMap.put(PlayType.jczq_bf_4_5.getValue(), "8");
		 toLotteryTypeMap.put(PlayType.jczq_bf_4_6.getValue(), "9");
		 toLotteryTypeMap.put(PlayType.jczq_bf_4_11.getValue(), "10");
		 
		 toLotteryTypeMap.put(PlayType.jczq_bqc_1_1.getValue(), "1");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_2_1.getValue(), "2");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_3_1.getValue(), "3");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_4_1.getValue(), "6");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_5_1.getValue(), "11");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_6_1.getValue(), "18");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_7_1.getValue(), "28");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_8_1.getValue(), "35");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_3_3.getValue(), "4");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_3_4.getValue(), "5");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_4_4.getValue(), "7");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_4_5.getValue(), "8");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_4_6.getValue(), "9");
	 	 toLotteryTypeMap.put(PlayType.jczq_bqc_4_11.getValue(), "10");
	 	 
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "1");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "2");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "3");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "6");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "11");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "18");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "28");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "35");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "4");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "5");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "7");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "8");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "9");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "10");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "12");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "13");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "14");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "15");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "16");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "17");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "19");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "20");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "21");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "22");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "23");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "24");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "25");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "26");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "27");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "29");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "30");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "31");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "32");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "33");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "36");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "37");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "38");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "39");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "40");
		 toLotteryTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "41");
		 
		 toLotteryTypeMap.put(PlayType.jczq_mix_2_1.getValue(), "2");
		 toLotteryTypeMap.put(PlayType.jczq_mix_3_1.getValue(), "3");
		 toLotteryTypeMap.put(PlayType.jczq_mix_4_1.getValue(), "6");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_1.getValue(), "11");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_1.getValue(), "18");
		 toLotteryTypeMap.put(PlayType.jczq_mix_7_1.getValue(), "28");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_1.getValue(), "35");
		 toLotteryTypeMap.put(PlayType.jczq_mix_3_3.getValue(), "4");
		 toLotteryTypeMap.put(PlayType.jczq_mix_3_4.getValue(), "5");
		 toLotteryTypeMap.put(PlayType.jczq_mix_4_4.getValue(), "7");
		 toLotteryTypeMap.put(PlayType.jczq_mix_4_5.getValue(), "8");
		 toLotteryTypeMap.put(PlayType.jczq_mix_4_6.getValue(), "9");
		 toLotteryTypeMap.put(PlayType.jczq_mix_4_11.getValue(), "10");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_5.getValue(), "12");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_6.getValue(), "13");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_10.getValue(), "14");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_16.getValue(), "15");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_20.getValue(), "16");
		 toLotteryTypeMap.put(PlayType.jczq_mix_5_26.getValue(), "17");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_6.getValue(), "19");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_7.getValue(), "20");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_15.getValue(), "21");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_20.getValue(), "22");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_22.getValue(), "23");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_35.getValue(), "24");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_42.getValue(), "25");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_50.getValue(), "26");
		 toLotteryTypeMap.put(PlayType.jczq_mix_6_57.getValue(), "27");
		 toLotteryTypeMap.put(PlayType.jczq_mix_7_7.getValue(), "29");
		 toLotteryTypeMap.put(PlayType.jczq_mix_7_8.getValue(), "30");
		 toLotteryTypeMap.put(PlayType.jczq_mix_7_21.getValue(), "31");
		 toLotteryTypeMap.put(PlayType.jczq_mix_7_35.getValue(), "32");
		 toLotteryTypeMap.put(PlayType.jczq_mix_7_120.getValue(), "33");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_8.getValue(), "36");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_9.getValue(), "37");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_28.getValue(), "38");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_56.getValue(), "39");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_70.getValue(), "40");
		 toLotteryTypeMap.put(PlayType.jczq_mix_8_247.getValue(), "41");
	  	 
  	    
		 toLotteryTypeMap.put(PlayType.jclq_sf_1_1.getValue(),"1");
		 toLotteryTypeMap.put(PlayType.jclq_sf_2_1.getValue(),"2");
		 toLotteryTypeMap.put(PlayType.jclq_sf_3_1.getValue(),"3");
		 toLotteryTypeMap.put(PlayType.jclq_sf_4_1.getValue(),"6");
		 toLotteryTypeMap.put(PlayType.jclq_sf_5_1.getValue(),"11");
		 toLotteryTypeMap.put(PlayType.jclq_sf_6_1.getValue(),"18");
		 toLotteryTypeMap.put(PlayType.jclq_sf_7_1.getValue(),"28");
		 toLotteryTypeMap.put(PlayType.jclq_sf_8_1.getValue(),"35");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_3_3.getValue(),"4");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_3_4.getValue(),"5");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_4_4.getValue(),"7");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_4_5.getValue(),"8");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_4_6.getValue(),"9");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_4_11.getValue(),"10");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_5_5.getValue(),"12");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_5_6.getValue(),"13");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_5_10.getValue(),"14");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_5_16.getValue(),"15");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_5_20.getValue(),"16");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_5_26.getValue(),"17");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_6.getValue(),"19");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_7.getValue(),"20");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_15.getValue(),"21");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_20.getValue(),"22");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_22.getValue(),"23");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_35.getValue(),"24");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_42.getValue(),"25");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_50.getValue(),"26");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_6_57.getValue(),"27");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_7_7.getValue(),"29");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_7_8.getValue(),"30");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_7_21.getValue(),"31");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_7_35.getValue(),"32");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_7_120.getValue(),"33");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_8_8.getValue(),"36");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_8_9.getValue(),"37");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_8_28.getValue(),"38");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_8_56.getValue(),"39");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_8_70.getValue(),"40");
	 		toLotteryTypeMap.put(PlayType.jclq_sf_8_247.getValue(),"41");	
	 		
	 		
	 		
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"1");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"2");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"3");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"6");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"11");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"18");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"28");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"35");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"4");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"5");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"7");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"8");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"9");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"10");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"12");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"13");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"14");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"15");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"16");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"17");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"19");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"20");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"21");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"22");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"23");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"24");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"25");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"26");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"27");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"29");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"30");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"31");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"32");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"33");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"36");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"37");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"38");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"39");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"40");
	 		toLotteryTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"41");
		 
		 
		 
		toLotteryTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"1");
		toLotteryTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"2");
		toLotteryTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"3");
		toLotteryTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"6");
		toLotteryTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"11");
		toLotteryTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"18");
		toLotteryTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"28");
		toLotteryTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"35");
		toLotteryTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"4");
		toLotteryTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"5");
		toLotteryTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"7");
		toLotteryTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"8");
		toLotteryTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"9");
		toLotteryTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"10");
		
		toLotteryTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"1");
		toLotteryTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"2");
		toLotteryTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"3");
		toLotteryTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"6");
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"11");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"18");
		toLotteryTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"28");
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"35");
		toLotteryTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"4");
		toLotteryTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"5");
		toLotteryTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"7");
		toLotteryTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"8");
		toLotteryTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"9");
		toLotteryTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"10");
		
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"12");
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"13");
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"14");
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"15");
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"16");
		toLotteryTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"17");
		
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"19");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"20");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"21");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"22");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"23");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"24");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"25");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"26");
		toLotteryTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"27");
		
		toLotteryTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"29");
		toLotteryTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"30");
		toLotteryTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"31");
		toLotteryTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"32");
		toLotteryTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"33");
		
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"36");
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"37");
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"38");
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"39");
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"40");
		toLotteryTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"41");
		
		toLotteryTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"2");
		toLotteryTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"3");
		toLotteryTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"6");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"11");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"18");
		toLotteryTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"28");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"35");
		toLotteryTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"4");
		toLotteryTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"5");
		toLotteryTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"7");
		toLotteryTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"8");
		toLotteryTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"9");
		toLotteryTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"10");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"12");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"13");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"14");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"15");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"16");
		toLotteryTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"17");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"19");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"20");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"21");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"22");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"23");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"24");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"25");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"26");
		toLotteryTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"27");
		toLotteryTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"29");
		toLotteryTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"30");
		toLotteryTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"31");
		toLotteryTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"32");
		toLotteryTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"33");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"36");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"37");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"38");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"39");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"40");
		toLotteryTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"41");
		
		
		
		
		
		
		//传统足彩
		IVenderTicketConverter zc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String []contentStr = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String content:contentStr){
					ticketContent.append(content.replace("~","9"));
					if(i!=contentStr.length-1){
						ticketContent.append(";");
					}
					i++;
				}
				 return ticketContent.toString();
				
		}};
		IVenderTicketConverter zcfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\,");
				int j=0;
				for (String oneCode:oneCodes) {
					for(int i=0;i<oneCode.length();i++){
						String content = oneCode.substring(i,i+1);
						ticketContent.append(content.replace("~","9"));
						if(i!=oneCode.length()-1){
							ticketContent.append(",");
						}
					}
					if(j!=oneCodes.length-1){
						ticketContent.append("|");
					}
					j++;
				}
				 return ticketContent.toString();
				
		}};
		
		
		
		IVenderTicketConverter jcsfc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.subSequence(8,11))
					.append("=").append(oneCode.split("\\(")[1].split("\\)")[0].replace("01","51").replace("02","52")
					.replace("03","53").replace("04","54").replace("05","55").replace("06","56").replace("11","01")
					.replace("12","02").replace("13","03").replace("14","04").replace("15","05").replace("16","06"));
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
		
		IVenderTicketConverter jcbf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.substring(8,11)).append("=");
					String []cons=oneCode.split("\\(")[1].split("\\)")[0].replace("99", "44").replace("09", "34").replace("90", "43").split("\\,");
					int i=0;
					for(String con:cons){
						ticketContent.append(con.substring(0,1)).append(":").append(con.substring(1));
						if(i!=cons.length-1){
							ticketContent.append(",");
						}
						i++;
					}
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
 		 
		IVenderTicketConverter jcbqc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.substring(8,11)).append("=");
					String []cons=oneCode.split("\\(")[1].split("\\)")[0].split("\\,");
					int i=0;
					for(String con:cons){
						ticketContent.append(con.substring(0,1)).append("-").append(con.substring(1));
						if(i!=cons.length-1){
							ticketContent.append(",");
						}
						i++;
					}
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
		
		
		IVenderTicketConverter jcmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					String code=oneCode.split("\\*")[1].split("\\(")[0];
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.substring(8,11)).append("=");
					if("3010".equals(code)){
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0]);
					}else if("3006".equals(code)){
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("1","1R").replace("3","3R").replace("0","0R"));
					}else if("3008".equals(code)){//jqs
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("0","0B").replace("1","1B").replace("2","2B").replace("3","3B")
					   .replace("4","4B").replace("5","5B").replace("6","6B").replace("7","7B"));
					}else if("3007".equals(code)){//bf
						String []cons=oneCode.split("\\(")[1].split("\\)")[0].replace("99", "44").replace("09", "34").replace("90", "43").split("\\,");
						int i=0;
						for(String con:cons){
							ticketContent.append(con.substring(0,1)).append(":").append(con.substring(1));
							if(i!=cons.length-1){
								ticketContent.append(",");
							}
							i++;
						}
					}else if("3009".equals(code)){//bqc
						String []cons=oneCode.split("\\(")[1].split("\\)")[0].split("\\,");
						int i=0;
						for(String con:cons){
							ticketContent.append(con.substring(0,1)).append("-").append(con.substring(1));
							if(i!=cons.length-1){
								ticketContent.append(",");
							}
							i++;
						}
					}
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
		
		
		IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.subSequence(8,11))
					.append("=").append(oneCode.split("\\(")[1].split("\\)")[0].replace("3","1"));
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
		
		IVenderTicketConverter jclqsfc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.subSequence(8,11))
					.append("=").append(oneCode.split("\\(")[1].split("\\)")[0].replace("01","51").replace("02","52")
							.replace("03","53").replace("04","54").replace("05","55").replace("06","56").replace("11","01")
							.replace("12","02").replace("13","03").replace("14","04").replace("15","05").replace("16","06"));
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
		
		IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCodes = contentStr.split("\\|");
				int j=0;
				for (String oneCode:oneCodes) {
					String code=oneCode.split("\\*")[1].split("\\(")[0];
					ticketContent.append(oneCode.substring(0,8)).append("-").append(oneCode.substring(8,11)).append("=");
					if("3001".equals(code)){//sf
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("3","1"));
					}else if("3002".equals(code)){//rfsf
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("3","1D").replace("0","0D"));
					}else if("3004".equals(code)){//dxf
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("1","1B").replace("2","2B"));
					}else if("3003".equals(code)){//sfc
						ticketContent.append(oneCode.split("\\(")[1].split("\\)")[0].replace("01","51").replace("02","52")
						.replace("03","53").replace("04","54").replace("05","55").replace("06","56").replace("11","01")
						.replace("12","02").replace("13","03").replace("14","04").replace("15","05").replace("16","06"));
					}
					if(j!=oneCodes.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				 return ticketContent.toString();
		}};
		
		
 		 //老足彩
 		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu,zcfs);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu,zcfs);
// 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dt,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu,zcfs);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu,zcfs);
 		
 		
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jcsfc);
        playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jcsfc);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jcsfc);
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jcsfc);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jcsfc);
       	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jcsfc);
   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jcbf);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11,jcbf);
   	 
   	 
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jcsfc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jcsfc);
   	 
   	
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jcbqc);
   	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jcbqc);
   	 

 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,jcmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,jcmix);
 		
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
			    		
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,jclqsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,jclqsfc);
			    		
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jcsfc);
	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jcsfc);
		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jcsfc);
		 
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,jclqmix);
		
	}
	
	

}
