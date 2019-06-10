package com.lottery.ticket.vender.impl.yinxi;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class YinxiLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
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
		IPhaseConverter zcltPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "20"+phase;
			}
		};
		//足彩彩期
		
		
		phaseConverterMap.put(LotteryType.ZC_SFC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, zcltPhaseConverter);
		//彩种转换
		lotteryTypeMap.put(LotteryType.ZC_SFC, "401");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "401");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "402");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "403");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF, "702");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF, "702");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC, "702");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF, "702");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG, "702");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "701");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "701");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "701");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "701");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ, "701");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG, "701");
		
		toLotteryTypeMap.put("300", LotteryType.ZC_SFC);
		toLotteryTypeMap.put("303", LotteryType.ZC_RJC);
		toLotteryTypeMap.put("302", LotteryType.ZC_JQC);
		toLotteryTypeMap.put("301", LotteryType.ZC_BQC);
		
		
		
		//玩法转换
		//足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "40101");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "40102");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "40111");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "40112");
//		playTypeMap.put(PlayType.zc_rjc_dt.getValue(), "03");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(),"40201");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "40202");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(),"40301");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "40302");
		
		//竞彩
		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(),"70106");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(),"70106");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "70106");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"70106");
		
		
		playTypeMap.put(PlayType.jczq_bf_1_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_2_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_3_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_4_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_5_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_6_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_7_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_8_1.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_3_3.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_3_4.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_4_4.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_4_5.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_4_6.getValue(),"70102");
		playTypeMap.put(PlayType.jczq_bf_4_11.getValue(),"70102");
		
		
		playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(),"70103");
		playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(),"70103");
		
		
		playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(),"70104");
		playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(),"70104");
		
		
		playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"70101");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"70101");
		
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"70199");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"70199");
		
		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(),"70202");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(),"70202");
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"70201");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"70201");
		
		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"70203");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"70203");
		
		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"70204");
		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"70204");
		
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"70299");
	    playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"70299");
	    playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"70299");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"70299");
		
		
		
		
		
		//传统足彩
		IVenderTicketConverter zc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String ret = ticket.getContent().split("-")[1].replace("^", "");
				ret=ret.replace(",","|").replace("~","*");
				 return ret;
				
		}};
		//竞彩胜平负
		IVenderTicketConverter jcspf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String content=ticket.getContent().replace("(", "→[").replace(")", "]").replace("|","/");
				return content;
		}};
		
		//竞彩比分
		IVenderTicketConverter jcbf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				int i=0;
				for(String str:strs){
					int j=0;
					String []number=str.split("\\(");
					strBuffer.append(number[0]).append("→[");
					String []numbers=number[1].split("\\)")[0].split("\\,");
					for(String num:numbers){
						strBuffer.append(num.substring(0,1)).append(":").append(num.substring(1));
						if(j!=numbers.length-1){
							strBuffer.append(",");
						 }
						j++;
					}
					strBuffer.append("]");
					if(i!=strs.length-1){
						strBuffer.append("/");
					}
					i++;
				}
				String content=strBuffer.toString().replace(")", "]");
				return content;
		}};
		//竞彩半全场
		IVenderTicketConverter jcbqc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				int i=0;
				for(String str:strs){
					int j=0;
					String []number=str.split("\\(");
					strBuffer.append(number[0]).append("→[");
					String []numbers=number[1].split("\\)")[0].split("\\,");
					for(String num:numbers){
						strBuffer.append(num.substring(0,1)).append("-").append(num.substring(1));
						if(j!=numbers.length-1){
							strBuffer.append(",");
						 }
						j++;
					}
					strBuffer.append("]");
					if(i!=strs.length-1){
						strBuffer.append("/");
					}
					i++;
				}
				String content=strBuffer.toString().replace(")", "]");
				return content;
		}};
		//竞彩篮球
		IVenderTicketConverter jclq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String content=ticket.getContent().replace("(", "→[").replace(")", "]").replace("|","/");
				return content;
		}};
		
		IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String content="";
				int i=0;
				StringBuilder stringBuilder=new StringBuilder();
				String []contents=ticket.getContent().split("\\|");//2302(3,0)|2303(3,0)
				for(String num:contents){
					stringBuilder.append(num.split("\\(")[0]).append("→[");
					stringBuilder.append(num.split("\\(")[1].replace("3", "1").replace("0", "2"));
					if(i!=contents.length-1){
						stringBuilder.append("/");
					}
					i++;
				}
				content=stringBuilder.toString().replace(")", "]");
				return content;
		}};
		
		IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String numstr=ticket.getContent();
				String []nums=numstr.split("\\|");
				StringBuilder stringBuilder=new StringBuilder();
				int j=0;
				for(String num:nums){
					String lotteryType=num.split("\\{")[1].split("\\}")[0];
					if("70102".equals(lotteryType)||"70104".equals(lotteryType)||"70202".equals(lotteryType)||"70201".equals(lotteryType)){
					stringBuilder.append(num.split("\\(")[0]).append("[");
					String numbers[]=num.split("\\(")[1].split("\\)")[0].split("\\,");
					int i=0;
					for(String number:numbers){
						   if("70102".equals(lotteryType)){
						       stringBuilder.append(number.substring(0,1)).append(":").append(number.substring(1));
						   }else if("70104".equals(lotteryType)){
							   stringBuilder.append(number.substring(0,1)).append("-").append(number.substring(1));
						   }else if("70202".equals(lotteryType)||"70201".equals(lotteryType)){
							   stringBuilder.append(number.replace("3","1").replace("0", "2"));
						   }
						   if(i!=numbers.length-1){
							   stringBuilder.append(",");
						   }
						   i++;
					   }
					   stringBuilder.append("]");	
					   if(j!=nums.length-1){
						   stringBuilder.append("/");
					   }
					}else{
						stringBuilder.append(num);
						if(j!=nums.length-1){
						   stringBuilder.append("/");
						}
					}
					j++;
				}
				numstr=stringBuilder.toString().replace("*","→").replace("(", "[").replace(")","]").replace("{","(").replace("}", ")");
				return numstr;
		}};
		
        
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jcspf);
         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jcspf);
     	 
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jcspf);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jcspf);
    	 
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
    	 
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jcspf);
    	 
    	 
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
 			    		
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,jclq);
 			    		
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jclq);
 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jclq);
 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jclq);
 		 
 		 
 		 
 		 
 		 
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
 		 
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,jclqmix);
 		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,jclqmix);
 		 
 		 
 		 //老足彩
 		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dt,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan,zc);
 		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu,zc);
 		
	}
	
	
}
