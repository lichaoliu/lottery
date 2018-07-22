 package com.lottery.ticket.vender.impl.sjz;

import java.util.HashMap;
import java.util.Map;














import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class SJZLotteryDef {
	 /** 彩种转换 */
    protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
    /** 彩种转换 */
    public static Map<LotteryType,String> toLotteryTypeMap = new HashMap<LotteryType,String>();
    /** 彩期转换*/
    protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
    /** 玩法转换*/
    public static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
    /** 玩法转换*/
    public static Map<Integer, String> playMap = new HashMap<Integer, String>();
    /** 票内容转换 */
    protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
    static {	
    	
    	/**
		 * 彩期转换
		 * */
	
		//默认的期号转换
		IPhaseConverter phaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		
		phaseConverterMap.put(LotteryType.ZC_SFC, phaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, phaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, phaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, phaseConverter);
		
		//彩种转换
		
		lotteryTypeMap.put(LotteryType.ZC_SFC,"102");
		lotteryTypeMap.put(LotteryType.ZC_RJC,"103");
		lotteryTypeMap.put(LotteryType.ZC_JQC,"106");
		lotteryTypeMap.put(LotteryType.ZC_BQC,"107");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF,"505");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"506");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC,"507");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF,"508");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG,"510");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"511");
		lotteryTypeMap.put(LotteryType.JCZQ_BF,"502");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS,"503");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC,"504");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"501");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG,"509");


		//投注方式转换 
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(),"01");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(),"05");
		
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(),"01");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(),"05");
		playTypeMap.put(PlayType.zc_rjc_dt.getValue(),"03");
		
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(),"01");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(),"05");
		
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(),"01");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(),"05");
		
		//玩法
		playMap.put(PlayType.zc_sfc_dan.getValue(),"01");
		playMap.put(PlayType.zc_sfc_fu.getValue(),"01");
		
		playMap.put(PlayType.zc_rjc_dan.getValue(),"01");
		playMap.put(PlayType.zc_rjc_fu.getValue(),"01");
		playMap.put(PlayType.zc_rjc_dt.getValue(),"01");
		
		playMap.put(PlayType.zc_jqc_dan.getValue(),"01");
		playMap.put(PlayType.zc_jqc_fu.getValue(),"01");
		
		playMap.put(PlayType.zc_bqc_dan.getValue(),"01");
		playMap.put(PlayType.zc_bqc_fu.getValue(),"01");
		
		
        playMap.put(PlayType.jczq_spf_1_1.getValue(),"单场");
	    playMap.put(PlayType.jczq_spf_1_1.getValue(), "1");
 		playMap.put(PlayType.jczq_spf_2_1.getValue(), "2");
 		playMap.put(PlayType.jczq_spf_3_1.getValue(), "3");
 		playMap.put(PlayType.jczq_spf_4_1.getValue(), "6");
 		playMap.put(PlayType.jczq_spf_5_1.getValue(), "11");
 		playMap.put(PlayType.jczq_spf_6_1.getValue(), "18");
	     playMap.put(PlayType.jczq_spf_7_1.getValue(), "28");
	     playMap.put(PlayType.jczq_spf_8_1.getValue(), "35");
	     playMap.put(PlayType.jczq_spf_3_3.getValue(), "4");
	     playMap.put(PlayType.jczq_spf_3_4.getValue(), "5");
	     playMap.put(PlayType.jczq_spf_4_4.getValue(), "7");
	     playMap.put(PlayType.jczq_spf_4_5.getValue(), "8");
	     playMap.put(PlayType.jczq_spf_4_6.getValue(), "9");
	     playMap.put(PlayType.jczq_spf_4_11.getValue(), "10");
	     playMap.put(PlayType.jczq_spf_5_5.getValue(), "12");
	     playMap.put(PlayType.jczq_spf_5_6.getValue(), "13");               
	     playMap.put(PlayType.jczq_spf_5_10.getValue(), "14");
	   	 playMap.put(PlayType.jczq_spf_5_16.getValue(), "15");
	   	 playMap.put(PlayType.jczq_spf_5_20.getValue(), "16");
	   	 playMap.put(PlayType.jczq_spf_5_26.getValue(), "17");
	   	 playMap.put(PlayType.jczq_spf_6_6.getValue(), "19");
	   	 playMap.put(PlayType.jczq_spf_6_7.getValue(), "20");
	   	 playMap.put(PlayType.jczq_spf_6_15.getValue(), "21");
	   	 playMap.put(PlayType.jczq_spf_6_20.getValue(), "22");
	   	 playMap.put(PlayType.jczq_spf_6_22.getValue(), "23");
	   	 playMap.put(PlayType.jczq_spf_6_35.getValue(), "24");
	   	 playMap.put(PlayType.jczq_spf_6_42.getValue(), "25");
	   	 playMap.put(PlayType.jczq_spf_6_50.getValue(), "26");
	   	 playMap.put(PlayType.jczq_spf_6_57.getValue(), "27");
	   	 playMap.put(PlayType.jczq_spf_7_7.getValue(), "29");
	   	 playMap.put(PlayType.jczq_spf_7_8.getValue(), "30");
	   	 playMap.put(PlayType.jczq_spf_7_21.getValue(), "31");
	   	 playMap.put(PlayType.jczq_spf_7_35.getValue(), "32");
	   	 playMap.put(PlayType.jczq_spf_7_120.getValue(), "33");
	   	 playMap.put(PlayType.jczq_spf_8_8.getValue(), "36");
	   	 playMap.put(PlayType.jczq_spf_8_9.getValue(), "37");
	   	 playMap.put(PlayType.jczq_spf_8_28.getValue(), "38");
	   	 playMap.put(PlayType.jczq_spf_8_56.getValue(), "39");
	   	 playMap.put(PlayType.jczq_spf_8_70.getValue(), "40");
	   	 playMap.put(PlayType.jczq_spf_8_247.getValue(), "41");
	   	 
	   	 playMap.put(PlayType.jczq_jqs_1_1.getValue(), "1");
	   	 playMap.put(PlayType.jczq_jqs_2_1.getValue(), "2");
	   	 playMap.put(PlayType.jczq_jqs_3_1.getValue(), "3");
	   	 playMap.put(PlayType.jczq_jqs_4_1.getValue(), "6");
	   	 playMap.put(PlayType.jczq_jqs_5_1.getValue(), "11");
	   	 playMap.put(PlayType.jczq_jqs_6_1.getValue(), "18");
	   	 playMap.put(PlayType.jczq_jqs_7_1.getValue(), "28");
	   	 playMap.put(PlayType.jczq_jqs_8_1.getValue(), "35");
	   	 playMap.put(PlayType.jczq_jqs_3_3.getValue(), "4");
	   	 playMap.put(PlayType.jczq_jqs_3_4.getValue(), "5");
	   	 playMap.put(PlayType.jczq_jqs_4_4.getValue(), "7");
	   	 playMap.put(PlayType.jczq_jqs_4_5.getValue(), "8");
	   	 playMap.put(PlayType.jczq_jqs_4_6.getValue(), "9");
	   	 playMap.put(PlayType.jczq_jqs_4_11.getValue(), "10");
	   	 playMap.put(PlayType.jczq_jqs_5_5.getValue(), "12");
	   	 playMap.put(PlayType.jczq_jqs_5_6.getValue(), "13");
	   	 playMap.put(PlayType.jczq_jqs_5_10.getValue(), "14");
	   	 playMap.put(PlayType.jczq_jqs_5_16.getValue(), "15");
	   	 playMap.put(PlayType.jczq_jqs_5_20.getValue(), "16");
	   	 playMap.put(PlayType.jczq_jqs_5_26.getValue(), "17");
	   	 playMap.put(PlayType.jczq_jqs_6_6.getValue(), "19");
	     playMap.put(PlayType.jczq_jqs_6_7.getValue(), "20");
		 playMap.put(PlayType.jczq_jqs_6_15.getValue(), "21");
		 playMap.put(PlayType.jczq_jqs_6_20.getValue(), "22");
		 playMap.put(PlayType.jczq_jqs_6_22.getValue(), "23");
		 playMap.put(PlayType.jczq_jqs_6_35.getValue(), "24");
		 playMap.put(PlayType.jczq_jqs_6_42.getValue(), "25");
		 playMap.put(PlayType.jczq_jqs_6_50.getValue(), "26");
		 playMap.put(PlayType.jczq_jqs_6_57.getValue(), "27");
		 
		 playMap.put(PlayType.jczq_bf_1_1.getValue(), "1");
		 playMap.put(PlayType.jczq_bf_2_1.getValue(), "2");
		 playMap.put(PlayType.jczq_bf_3_1.getValue(), "3");
		 playMap.put(PlayType.jczq_bf_4_1.getValue(), "6");
		 playMap.put(PlayType.jczq_bf_5_1.getValue(), "11");
		 playMap.put(PlayType.jczq_bf_6_1.getValue(), "18");
		 playMap.put(PlayType.jczq_bf_7_1.getValue(), "28");
		 playMap.put(PlayType.jczq_bf_8_1.getValue(), "35");
		 playMap.put(PlayType.jczq_bf_3_3.getValue(), "4");
		 playMap.put(PlayType.jczq_bf_3_4.getValue(), "5");
		 playMap.put(PlayType.jczq_bf_4_4.getValue(), "7");
		 playMap.put(PlayType.jczq_bf_4_5.getValue(), "8");
		 playMap.put(PlayType.jczq_bf_4_6.getValue(), "9");
		 playMap.put(PlayType.jczq_bf_4_11.getValue(), "10");
		 
		 playMap.put(PlayType.jczq_bqc_1_1.getValue(), "1");
	 	 playMap.put(PlayType.jczq_bqc_2_1.getValue(), "2");
	 	 playMap.put(PlayType.jczq_bqc_3_1.getValue(), "3");
	 	 playMap.put(PlayType.jczq_bqc_4_1.getValue(), "6");
	 	 playMap.put(PlayType.jczq_bqc_5_1.getValue(), "11");
	 	 playMap.put(PlayType.jczq_bqc_6_1.getValue(), "18");
	 	 playMap.put(PlayType.jczq_bqc_7_1.getValue(), "28");
	 	 playMap.put(PlayType.jczq_bqc_8_1.getValue(), "35");
	 	 playMap.put(PlayType.jczq_bqc_3_3.getValue(), "4");
	 	 playMap.put(PlayType.jczq_bqc_3_4.getValue(), "5");
	 	 playMap.put(PlayType.jczq_bqc_4_4.getValue(), "7");
	 	 playMap.put(PlayType.jczq_bqc_4_5.getValue(), "8");
	 	 playMap.put(PlayType.jczq_bqc_4_6.getValue(), "9");
	 	 playMap.put(PlayType.jczq_bqc_4_11.getValue(), "10");
	 	 
		 playMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "1");
		 playMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "2");
		 playMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "3");
		 playMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "6");
		 playMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "11");
		 playMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "18");
		 playMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "28");
		 playMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "35");
		 playMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "4");
		 playMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "5");
		 playMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "7");
		 playMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "8");
		 playMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "9");
		 playMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "10");
		 playMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "12");
		 playMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "13");
		 playMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "14");
		 playMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "15");
		 playMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "16");
		 playMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "17");
		 playMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "19");
		 playMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "20");
		 playMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "21");
		 playMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "22");
		 playMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "23");
		 playMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "24");
		 playMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "25");
		 playMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "26");
		 playMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "27");
		 playMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "29");
		 playMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "30");
		 playMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "31");
		 playMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "32");
		 playMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "33");
		 playMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "36");
		 playMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "37");
		 playMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "38");
		 playMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "39");
		 playMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "40");
		 playMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "41");
		 
		 playMap.put(PlayType.jczq_mix_2_1.getValue(), "2");
		 playMap.put(PlayType.jczq_mix_3_1.getValue(), "3");
		 playMap.put(PlayType.jczq_mix_4_1.getValue(), "6");
		 playMap.put(PlayType.jczq_mix_5_1.getValue(), "11");
		 playMap.put(PlayType.jczq_mix_6_1.getValue(), "18");
		 playMap.put(PlayType.jczq_mix_7_1.getValue(), "28");
		 playMap.put(PlayType.jczq_mix_8_1.getValue(), "35");
		 playMap.put(PlayType.jczq_mix_3_3.getValue(), "4");
		 playMap.put(PlayType.jczq_mix_3_4.getValue(), "5");
		 playMap.put(PlayType.jczq_mix_4_4.getValue(), "7");
		 playMap.put(PlayType.jczq_mix_4_5.getValue(), "8");
		 playMap.put(PlayType.jczq_mix_4_6.getValue(), "9");
		 playMap.put(PlayType.jczq_mix_4_11.getValue(), "10");
		 playMap.put(PlayType.jczq_mix_5_5.getValue(), "12");
		 playMap.put(PlayType.jczq_mix_5_6.getValue(), "13");
		 playMap.put(PlayType.jczq_mix_5_10.getValue(), "14");
		 playMap.put(PlayType.jczq_mix_5_16.getValue(), "15");
		 playMap.put(PlayType.jczq_mix_5_20.getValue(), "16");
		 playMap.put(PlayType.jczq_mix_5_26.getValue(), "17");
		 playMap.put(PlayType.jczq_mix_6_6.getValue(), "19");
		 playMap.put(PlayType.jczq_mix_6_7.getValue(), "20");
		 playMap.put(PlayType.jczq_mix_6_15.getValue(), "21");
		 playMap.put(PlayType.jczq_mix_6_20.getValue(), "22");
		 playMap.put(PlayType.jczq_mix_6_22.getValue(), "23");
		 playMap.put(PlayType.jczq_mix_6_35.getValue(), "24");
		 playMap.put(PlayType.jczq_mix_6_42.getValue(), "25");
		 playMap.put(PlayType.jczq_mix_6_50.getValue(), "26");
		 playMap.put(PlayType.jczq_mix_6_57.getValue(), "27");
		 playMap.put(PlayType.jczq_mix_7_7.getValue(), "29");
		 playMap.put(PlayType.jczq_mix_7_8.getValue(), "30");
		 playMap.put(PlayType.jczq_mix_7_21.getValue(), "31");
		 playMap.put(PlayType.jczq_mix_7_35.getValue(), "32");
		 playMap.put(PlayType.jczq_mix_7_120.getValue(), "33");
		 playMap.put(PlayType.jczq_mix_8_8.getValue(), "36");
		 playMap.put(PlayType.jczq_mix_8_9.getValue(), "37");
		 playMap.put(PlayType.jczq_mix_8_28.getValue(), "38");
		 playMap.put(PlayType.jczq_mix_8_56.getValue(), "39");
		 playMap.put(PlayType.jczq_mix_8_70.getValue(), "40");
		 playMap.put(PlayType.jczq_mix_8_247.getValue(), "41");
	  	 
  	    
		 playMap.put(PlayType.jclq_sf_1_1.getValue(),"1");
		 playMap.put(PlayType.jclq_sf_2_1.getValue(),"2");
		 playMap.put(PlayType.jclq_sf_3_1.getValue(),"3");
		 playMap.put(PlayType.jclq_sf_4_1.getValue(),"6");
		 playMap.put(PlayType.jclq_sf_5_1.getValue(),"11");
		 playMap.put(PlayType.jclq_sf_6_1.getValue(),"18");
		 playMap.put(PlayType.jclq_sf_7_1.getValue(),"28");
		 playMap.put(PlayType.jclq_sf_8_1.getValue(),"35");
	 		playMap.put(PlayType.jclq_sf_3_3.getValue(),"4");
	 		playMap.put(PlayType.jclq_sf_3_4.getValue(),"5");
	 		playMap.put(PlayType.jclq_sf_4_4.getValue(),"7");
	 		playMap.put(PlayType.jclq_sf_4_5.getValue(),"8");
	 		playMap.put(PlayType.jclq_sf_4_6.getValue(),"9");
	 		playMap.put(PlayType.jclq_sf_4_11.getValue(),"10");
	 		playMap.put(PlayType.jclq_sf_5_5.getValue(),"12");
	 		playMap.put(PlayType.jclq_sf_5_6.getValue(),"13");
	 		playMap.put(PlayType.jclq_sf_5_10.getValue(),"14");
	 		playMap.put(PlayType.jclq_sf_5_16.getValue(),"15");
	 		playMap.put(PlayType.jclq_sf_5_20.getValue(),"16");
	 		playMap.put(PlayType.jclq_sf_5_26.getValue(),"17");
	 		playMap.put(PlayType.jclq_sf_6_6.getValue(),"19");
	 		playMap.put(PlayType.jclq_sf_6_7.getValue(),"20");
	 		playMap.put(PlayType.jclq_sf_6_15.getValue(),"21");
	 		playMap.put(PlayType.jclq_sf_6_20.getValue(),"22");
	 		playMap.put(PlayType.jclq_sf_6_22.getValue(),"23");
	 		playMap.put(PlayType.jclq_sf_6_35.getValue(),"24");
	 		playMap.put(PlayType.jclq_sf_6_42.getValue(),"25");
	 		playMap.put(PlayType.jclq_sf_6_50.getValue(),"26");
	 		playMap.put(PlayType.jclq_sf_6_57.getValue(),"27");
	 		playMap.put(PlayType.jclq_sf_7_7.getValue(),"29");
	 		playMap.put(PlayType.jclq_sf_7_8.getValue(),"30");
	 		playMap.put(PlayType.jclq_sf_7_21.getValue(),"31");
	 		playMap.put(PlayType.jclq_sf_7_35.getValue(),"32");
	 		playMap.put(PlayType.jclq_sf_7_120.getValue(),"33");
	 		playMap.put(PlayType.jclq_sf_8_8.getValue(),"36");
	 		playMap.put(PlayType.jclq_sf_8_9.getValue(),"37");
	 		playMap.put(PlayType.jclq_sf_8_28.getValue(),"38");
	 		playMap.put(PlayType.jclq_sf_8_56.getValue(),"39");
	 		playMap.put(PlayType.jclq_sf_8_70.getValue(),"40");
	 		playMap.put(PlayType.jclq_sf_8_247.getValue(),"41");	
	 		
	 		
	 		
	 		playMap.put(PlayType.jclq_rfsf_1_1.getValue(),"1");
	 		playMap.put(PlayType.jclq_rfsf_2_1.getValue(),"2");
	 		playMap.put(PlayType.jclq_rfsf_3_1.getValue(),"3");
	 		playMap.put(PlayType.jclq_rfsf_4_1.getValue(),"6");
	 		playMap.put(PlayType.jclq_rfsf_5_1.getValue(),"11");
	 		playMap.put(PlayType.jclq_rfsf_6_1.getValue(),"18");
	 		playMap.put(PlayType.jclq_rfsf_7_1.getValue(),"28");
	 		playMap.put(PlayType.jclq_rfsf_8_1.getValue(),"35");
	 		playMap.put(PlayType.jclq_rfsf_3_3.getValue(),"4");
	 		playMap.put(PlayType.jclq_rfsf_3_4.getValue(),"5");
	 		playMap.put(PlayType.jclq_rfsf_4_4.getValue(),"7");
	 		playMap.put(PlayType.jclq_rfsf_4_5.getValue(),"8");
	 		playMap.put(PlayType.jclq_rfsf_4_6.getValue(),"9");
	 		playMap.put(PlayType.jclq_rfsf_4_11.getValue(),"10");
	 		playMap.put(PlayType.jclq_rfsf_5_5.getValue(),"12");
	 		playMap.put(PlayType.jclq_rfsf_5_6.getValue(),"13");
	 		playMap.put(PlayType.jclq_rfsf_5_10.getValue(),"14");
	 		playMap.put(PlayType.jclq_rfsf_5_16.getValue(),"15");
	 		playMap.put(PlayType.jclq_rfsf_5_20.getValue(),"16");
	 		playMap.put(PlayType.jclq_rfsf_5_26.getValue(),"17");
	 		playMap.put(PlayType.jclq_rfsf_6_6.getValue(),"19");
	 		playMap.put(PlayType.jclq_rfsf_6_7.getValue(),"20");
	 		playMap.put(PlayType.jclq_rfsf_6_15.getValue(),"21");
	 		playMap.put(PlayType.jclq_rfsf_6_20.getValue(),"22");
	 		playMap.put(PlayType.jclq_rfsf_6_22.getValue(),"23");
	 		playMap.put(PlayType.jclq_rfsf_6_35.getValue(),"24");
	 		playMap.put(PlayType.jclq_rfsf_6_42.getValue(),"25");
	 		playMap.put(PlayType.jclq_rfsf_6_50.getValue(),"26");
	 		playMap.put(PlayType.jclq_rfsf_6_57.getValue(),"27");
	 		playMap.put(PlayType.jclq_rfsf_7_7.getValue(),"29");
	 		playMap.put(PlayType.jclq_rfsf_7_8.getValue(),"30");
	 		playMap.put(PlayType.jclq_rfsf_7_21.getValue(),"31");
	 		playMap.put(PlayType.jclq_rfsf_7_35.getValue(),"32");
	 		playMap.put(PlayType.jclq_rfsf_7_120.getValue(),"33");
	 		playMap.put(PlayType.jclq_rfsf_8_8.getValue(),"36");
	 		playMap.put(PlayType.jclq_rfsf_8_9.getValue(),"37");
	 		playMap.put(PlayType.jclq_rfsf_8_28.getValue(),"38");
	 		playMap.put(PlayType.jclq_rfsf_8_56.getValue(),"39");
	 		playMap.put(PlayType.jclq_rfsf_8_70.getValue(),"40");
	 		playMap.put(PlayType.jclq_rfsf_8_247.getValue(),"41");
		 
		 
		 
		playMap.put(PlayType.jclq_sfc_1_1.getValue(),"1");
		playMap.put(PlayType.jclq_sfc_2_1.getValue(),"2");
		playMap.put(PlayType.jclq_sfc_3_1.getValue(),"3");
		playMap.put(PlayType.jclq_sfc_4_1.getValue(),"6");
		playMap.put(PlayType.jclq_sfc_5_1.getValue(),"11");
		playMap.put(PlayType.jclq_sfc_6_1.getValue(),"18");
		playMap.put(PlayType.jclq_sfc_7_1.getValue(),"28");
		playMap.put(PlayType.jclq_sfc_8_1.getValue(),"35");
		playMap.put(PlayType.jclq_sfc_3_3.getValue(),"4");
		playMap.put(PlayType.jclq_sfc_3_4.getValue(),"5");
		playMap.put(PlayType.jclq_sfc_4_4.getValue(),"7");
		playMap.put(PlayType.jclq_sfc_4_5.getValue(),"8");
		playMap.put(PlayType.jclq_sfc_4_6.getValue(),"9");
		playMap.put(PlayType.jclq_sfc_4_11.getValue(),"10");
		
		playMap.put(PlayType.jclq_dxf_1_1.getValue(),"1");
		playMap.put(PlayType.jclq_dxf_2_1.getValue(),"2");
		playMap.put(PlayType.jclq_dxf_3_1.getValue(),"3");
		playMap.put(PlayType.jclq_dxf_4_1.getValue(),"6");
		playMap.put(PlayType.jclq_dxf_5_1.getValue(),"11");
		playMap.put(PlayType.jclq_dxf_6_1.getValue(),"18");
		playMap.put(PlayType.jclq_dxf_7_1.getValue(),"28");
		playMap.put(PlayType.jclq_dxf_8_1.getValue(),"35");
		playMap.put(PlayType.jclq_dxf_3_3.getValue(),"4");
		playMap.put(PlayType.jclq_dxf_3_4.getValue(),"5");
		playMap.put(PlayType.jclq_dxf_4_4.getValue(),"7");
		playMap.put(PlayType.jclq_dxf_4_5.getValue(),"8");
		playMap.put(PlayType.jclq_dxf_4_6.getValue(),"9");
		playMap.put(PlayType.jclq_dxf_4_11.getValue(),"10");
		
		playMap.put(PlayType.jclq_dxf_5_5.getValue(),"12");
		playMap.put(PlayType.jclq_dxf_5_6.getValue(),"13");
		playMap.put(PlayType.jclq_dxf_5_10.getValue(),"14");
		playMap.put(PlayType.jclq_dxf_5_16.getValue(),"15");
		playMap.put(PlayType.jclq_dxf_5_20.getValue(),"16");
		playMap.put(PlayType.jclq_dxf_5_26.getValue(),"17");
		
		playMap.put(PlayType.jclq_dxf_6_6.getValue(),"19");
		playMap.put(PlayType.jclq_dxf_6_7.getValue(),"20");
		playMap.put(PlayType.jclq_dxf_6_15.getValue(),"21");
		playMap.put(PlayType.jclq_dxf_6_20.getValue(),"22");
		playMap.put(PlayType.jclq_dxf_6_22.getValue(),"23");
		playMap.put(PlayType.jclq_dxf_6_35.getValue(),"24");
		playMap.put(PlayType.jclq_dxf_6_42.getValue(),"25");
		playMap.put(PlayType.jclq_dxf_6_50.getValue(),"26");
		playMap.put(PlayType.jclq_dxf_6_57.getValue(),"27");
		
		playMap.put(PlayType.jclq_dxf_7_7.getValue(),"29");
		playMap.put(PlayType.jclq_dxf_7_8.getValue(),"30");
		playMap.put(PlayType.jclq_dxf_7_21.getValue(),"31");
		playMap.put(PlayType.jclq_dxf_7_35.getValue(),"32");
		playMap.put(PlayType.jclq_dxf_7_120.getValue(),"33");
		
		playMap.put(PlayType.jclq_dxf_8_8.getValue(),"36");
		playMap.put(PlayType.jclq_dxf_8_9.getValue(),"37");
		playMap.put(PlayType.jclq_dxf_8_28.getValue(),"38");
		playMap.put(PlayType.jclq_dxf_8_56.getValue(),"39");
		playMap.put(PlayType.jclq_dxf_8_70.getValue(),"40");
		playMap.put(PlayType.jclq_dxf_8_247.getValue(),"41");
		
		playMap.put(PlayType.jclq_mix_2_1.getValue(),"2");
		playMap.put(PlayType.jclq_mix_3_1.getValue(),"3");
		playMap.put(PlayType.jclq_mix_4_1.getValue(),"6");
		playMap.put(PlayType.jclq_mix_5_1.getValue(),"11");
		playMap.put(PlayType.jclq_mix_6_1.getValue(),"18");
		playMap.put(PlayType.jclq_mix_7_1.getValue(),"28");
		playMap.put(PlayType.jclq_mix_8_1.getValue(),"35");
		playMap.put(PlayType.jclq_mix_3_3.getValue(),"4");
		playMap.put(PlayType.jclq_mix_3_4.getValue(),"5");
		playMap.put(PlayType.jclq_mix_4_4.getValue(),"7");
		playMap.put(PlayType.jclq_mix_4_5.getValue(),"8");
		playMap.put(PlayType.jclq_mix_4_6.getValue(),"9");
		playMap.put(PlayType.jclq_mix_4_11.getValue(),"10");
		playMap.put(PlayType.jclq_mix_5_5.getValue(),"12");
		playMap.put(PlayType.jclq_mix_5_6.getValue(),"13");
		playMap.put(PlayType.jclq_mix_5_10.getValue(),"14");
		playMap.put(PlayType.jclq_mix_5_16.getValue(),"15");
		playMap.put(PlayType.jclq_mix_5_20.getValue(),"16");
		playMap.put(PlayType.jclq_mix_5_26.getValue(),"17");
		playMap.put(PlayType.jclq_mix_6_6.getValue(),"19");
		playMap.put(PlayType.jclq_mix_6_7.getValue(),"20");
		playMap.put(PlayType.jclq_mix_6_15.getValue(),"21");
		playMap.put(PlayType.jclq_mix_6_20.getValue(),"22");
		playMap.put(PlayType.jclq_mix_6_22.getValue(),"23");
		playMap.put(PlayType.jclq_mix_6_35.getValue(),"24");
		playMap.put(PlayType.jclq_mix_6_42.getValue(),"25");
		playMap.put(PlayType.jclq_mix_6_50.getValue(),"26");
		playMap.put(PlayType.jclq_mix_6_57.getValue(),"27");
		playMap.put(PlayType.jclq_mix_7_7.getValue(),"29");
		playMap.put(PlayType.jclq_mix_7_8.getValue(),"30");
		playMap.put(PlayType.jclq_mix_7_21.getValue(),"31");
		playMap.put(PlayType.jclq_mix_7_35.getValue(),"32");
		playMap.put(PlayType.jclq_mix_7_120.getValue(),"33");
		playMap.put(PlayType.jclq_mix_8_8.getValue(),"36");
		playMap.put(PlayType.jclq_mix_8_9.getValue(),"37");
		playMap.put(PlayType.jclq_mix_8_28.getValue(),"38");
		playMap.put(PlayType.jclq_mix_8_56.getValue(),"39");
		playMap.put(PlayType.jclq_mix_8_70.getValue(),"40");
		playMap.put(PlayType.jclq_mix_8_247.getValue(),"41");
		
				
    }
			
			
			
    static {			
    	IVenderTicketConverter zqds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].replace("~","9").split("\\^");
		    	int j=0;
		    	for(String con:cons){
		    		strBuilder.append(con);
		    		if(j!=cons.length-1){
		    			strBuilder.append(";");
		    		}
		    		j++;
		    	}
		        return strBuilder.toString();
		}};
    	
    	IVenderTicketConverter zqfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].replace("~","9").replace("^", "").split("\\,");
		    	int j=0;
		    	for(String con:cons){
		    		for(int i=0;i<=con.length()-1;i++){
		    			strBuilder.append(con.substring(i,i+1));
		    			if(i!=con.length()-1){
		    				strBuilder.append(",");
		    			}
		    		}
		    		if(j!=cons.length-1){
		    			strBuilder.append("|");
		    		}
		    		j++;
		    	}
		    
		        return strBuilder.toString();
		}};
    	
    	
	
		
		IVenderTicketConverter jczqrqspf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String con = ticket.getContent().split("-")[1].replace("^", "");
				String  []contents=con.split("\\|");
				int i=0;
				for(String s:contents){
					strBuilder.append(s.split("\\(")[0].substring(0,s.split("\\(")[0].length()-3)).append("-").
					append(s.split("\\(")[0].substring(s.split("\\(")[0].length()-3)).append("=");
					strBuilder.append(s.split("\\(")[1].split("\\)")[0]);
					if(i!=contents.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		
		
		IVenderTicketConverter jczqbf= new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer stringBuffer=new StringBuffer();
		    	String []cons = ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				int i=0;
		    	for(String con:cons){
		    		stringBuffer.append(con.split("\\(")[0].substring(0,con.split("\\(")[0].length()-3)).append("-").
					append(con.split("\\(")[0].substring(con.split("\\(")[0].length()-3)).append("=");
		        	String []contents=con.split("\\(")[1].replace("90","43").replace("99", "44").replace("09","34").replace(")", "").split("\\,");
		        	int j=0;
		        	for(String content:contents){
		        		stringBuffer.append(content.substring(0,1)).append(":").append(content.substring(1,2));
		        		if(j!=contents.length-1){
		        			stringBuffer.append(",");
		        		}
		        		j++;
		        	}
		        	if(i!=cons.length-1){
		        		stringBuffer.append(";");
					}
					i++;
		    	}
		        return stringBuffer.toString();
		}};
		
		IVenderTicketConverter jczqbqc= new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer stringBuffer=new StringBuffer();
		    	String []cons = ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				int i=0;
		    	for(String con:cons){
		    		stringBuffer.append(con.split("\\(")[0].substring(0,con.split("\\(")[0].length()-3)).append("-").
					append(con.split("\\(")[0].substring(con.split("\\(")[0].length()-3)).append("=");
		        	String []contents=con.split("\\(")[1].replace(")", "").split("\\,");
		        	int j=0;
		        	for(String content:contents){
		        		stringBuffer.append(content.substring(0,1)).append("-").append(content.substring(1,2));
		        		if(j!=contents.length-1){
		        			stringBuffer.append(",");
		        		}
		        		j++;
		        	}
		        	if(i!=cons.length-1){
		        		stringBuffer.append(";");
					}
					i++;
		    	}
		        return stringBuffer.toString();
		}};
		
		IVenderTicketConverter jczqmix = new IVenderTicketConverter() {
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
		
	
		
		//足彩
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zqfs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zqfs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dt, zqfs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zqfs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zqfs);
		
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
	    	                                                                       
	    	                                                                       
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120_dan,jclqsf);  
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9_dan,jclqsf);    
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70_dan,jclqsf);   
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247_dan,jclqsf);  
    	                                                                      
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
    	                                                                       
    	                                                                       
    	                                                                       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120_dan,jclqsf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9_dan,jclqsf);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70_dan,jclqsf); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247_dan,jclqsf);                                                   
         
         

    	 
        playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jclqsfc); 
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jclqsfc);  
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jclqsfc);



     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11_dan,jclqsfc); 
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70_dan,jclqsfc);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247_dan,jclqsfc);  


    	 

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
      	                                                                    
      	                                                                    
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6_dan,jclqsfc); 
      	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11_dan,jclqsfc);
    

    	
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_1_1,jclqmix);
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


    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11_dan,jclqmix);   
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70_dan,jclqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247_dan,jclqmix); 

    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,jczqrqspf);    
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jczqrqspf);


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11_dan,jczqrqspf); 
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120_dan,jczqrqspf);  
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247_dan,jczqrqspf);



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11,jczqbf);  


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6_dan,jczqbf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11_dan,jczqbf);



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jczqrqspf);   
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jczqrqspf);



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11_dan,jczqrqspf);   
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57_dan,jczqrqspf);



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jczqbqc);  


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6_dan,jczqbqc);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11_dan,jczqbqc);  


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jczqrqspf);     
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jczqrqspf);


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70_dan,jczqrqspf);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247_dan,jczqrqspf);


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_1_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,jczqmix); 
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,jczqmix);


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120_dan,jczqmix); 
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247_dan,jczqmix);  
    	
    	
		
    }
    
    public static void main(String[] args) {
    	String contentStr="300712001-20170303001(21,31,41,50,52,33,99,13,25,09)|20170303002(21,31,90,99,25,09)^";
    	StringBuffer stringBuffer=new StringBuffer();
    	String []cons = contentStr.split("-")[1].replace("^", "").split("\\|");
		int i=0;
    	for(String con:cons){
    		stringBuffer.append(con.split("\\(")[0].substring(0,con.split("\\(")[0].length()-3)).append("-").
			append(con.split("\\(")[0].substring(con.split("\\(")[0].length()-3)).append("=");
        	String []contents=con.split("\\(")[1].replace("90","43").replace("99", "44").replace("09","34").replace(")", "").split("\\,");
        	int j=0;
        	for(String content:contents){
        		stringBuffer.append(content.substring(0,1)).append(":").append(content.substring(1,2));
        		if(j!=contents.length-1){
        			stringBuffer.append(",");
        		}
        		j++;
        	}
        	if(i!=cons.length-1){
        		stringBuffer.append(";");
			}
			i++;
    	}
		System.out.println(stringBuffer.toString());
		
		
	}

   
    
}
