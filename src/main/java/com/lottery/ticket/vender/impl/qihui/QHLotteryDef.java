package com.lottery.ticket.vender.impl.qihui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class QHLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
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
		
		//彩种转换
		lotteryTypeMap.put(LotteryType.ZC_SFC, "D14");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "D9");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "D8");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "D12");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF, "BSK001");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"BSK002");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC, "BSK003");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF, "BSK004");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG, "BSK005");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "FT006");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "FT002");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "FT003");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "FT004");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"FT001");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG, "FT005");
		
		toLotteryTypeMap.put("300", LotteryType.ZC_SFC);
		toLotteryTypeMap.put("303", LotteryType.ZC_RJC);
		toLotteryTypeMap.put("302", LotteryType.ZC_JQC);
		toLotteryTypeMap.put("301", LotteryType.ZC_BQC);
		
		playTypeMapJc.put("jclq01", LotteryType.JCLQ_SF.getValue());
		playTypeMapJc.put("jclq02", LotteryType.JCLQ_RFSF.getValue());
		playTypeMapJc.put("jclq03", LotteryType.JCLQ_SFC.getValue());
		playTypeMapJc.put("jclq04", LotteryType.JCLQ_DXF.getValue());
		
		playTypeMapJc.put("jczq01", LotteryType.JCZQ_RQ_SPF.getValue());
		playTypeMapJc.put("jczq02", LotteryType.JCZQ_JQS.getValue());
		playTypeMapJc.put("jczq03", LotteryType.JCZQ_BQC.getValue());
		playTypeMapJc.put("jczq04", LotteryType.JCZQ_BF.getValue());
		playTypeMapJc.put("jczq05", LotteryType.JCZQ_SPF_WRQ.getValue());
		
		
		
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
		
		//竞彩
		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "500");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "502");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "503");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "504");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "505");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "506");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "507");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "508");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "526");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "527");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "539");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "540");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "528");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(), "529");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "544");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "545");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "530");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "541");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "531");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "532");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "549");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "550");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "533");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "542");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "546");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "534");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "543");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "535");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "536");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "553");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "554");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "551");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "547");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(), "537");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "556");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "557");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "555");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "552");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "548");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"538");
		
		
		playTypeMap.put(PlayType.jczq_bf_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jczq_bf_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jczq_bf_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jczq_bf_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jczq_bf_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jczq_bf_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jczq_bf_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jczq_bf_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jczq_bf_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jczq_bf_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jczq_bf_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jczq_bf_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jczq_bf_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jczq_bf_4_11.getValue(),"529");
		
		
		playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(),"534");
		playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(),"536");
		
		
		playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(),"529");
		
		
		playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"534");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"536");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"553");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"554");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"551");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"547");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"537");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"556");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"557");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"555");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"552");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"548");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"538");
		
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"534");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"536");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"553");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"554");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"551");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"547");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"537");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"556");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"557");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"555");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"552");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"548");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"538");
		
		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(),"534");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(),"536");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(),"553");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(),"554");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(),"551");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(),"547");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(),"537");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(),"556");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(),"557");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(),"555");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(),"552");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(),"548");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(),"538");
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"534");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"536");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"553");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"554");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"551");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"547");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"537");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"556");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"557");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"555");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"552");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"548");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"538");
		
		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"529");
		
		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"500");
		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"534");
		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"536");
		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"553");
		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"554");
		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"551");
		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"547");
		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"537");
		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"556");
		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"557");
		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"555");
		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"552");
		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"548");
		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"538");
		
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"502");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"503");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"504");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"505");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"506");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"507");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"508");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"526");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"527");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"539");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"540");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"528");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"529");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"544");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"545");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"530");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"541");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"531");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"532");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"549");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"550");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"533");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"542");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"546");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"534");
	    playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"543");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"535");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"536");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"553");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"554");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"551");
	    playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"547");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"537");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"556");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"557");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"555");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"552");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"548");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"538");
		
		
		
		//传统足彩
		IVenderTicketConverter zc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					String content = oneCode[j].replace(",","*").replace("~","4");
					ticketContent.append(content);
					ticketContent.append("^");
				}
				 return ticketContent.toString();
				
		}};
		//竞彩胜平负
		IVenderTicketConverter jcspf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				for(String str1:strs){
					String content=str1.split("\\(")[1].split("\\)")[0];
					String time=str1.substring(0, 8);
					Date date=DateUtil.parse("yyyy-MM-dd", time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8));
					String week=DateUtil.getWeekOfDate(date);
					strBuffer.append(time).append("|").append(week).append("|").append(str1.substring(8,11)).append("|").append(content.replace(",","")).append("^");;
					
				}
				return strBuffer.toString();
		}};
		//总进球数
		IVenderTicketConverter jczjq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				for(String str1:strs){
					String content=str1.split("\\(")[1].split("\\)")[0];
					String time=str1.substring(0, 8);
					Date date=DateUtil.parse("yyyy-MM-dd", time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8));
					String week=DateUtil.getWeekOfDate(date);
				    strBuffer.append(time).append("|").append(week).append("|").append(str1.substring(8,11)).append("|").append(content.replace(",","")).append("^");;
					
				}
				return strBuffer.toString();
		}};
		//竞彩篮球
		IVenderTicketConverter jclq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				for(String str1:strs){
					String content=str1.split("\\(")[1].split("\\)")[0];
					String time=str1.substring(0, 8);
					Date date=DateUtil.parse("yyyy-MM-dd", time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8));
					String week=DateUtil.getWeekOfDate(date);
					strBuffer.append(time).append("|").append(week).append("|").append(str1.substring(8,11)).append("|").append(content.replace(",","")).append("^");;
				}
				return strBuffer.toString();
		}};
		
		IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				for(String str1:strs){
					String content=str1.split("\\(")[1].split("\\)")[0];
					String time=str1.substring(0, 8);
					Date date=DateUtil.parse("yyyy-MM-dd", time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8));
					String week=DateUtil.getWeekOfDate(date);
					strBuffer.append(time).append("|").append(week).append("|").append(str1.substring(8,11)).append("|").append(content.replace(",","")).append("^");;
					
				}
				return strBuffer.toString();
			}};
		
		IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				int i=0;
				for(String str1:strs){
					String content=str1.split("\\(")[1].split("\\)")[0];
					String time=str1.substring(0, 8);
					Date date=DateUtil.parse("yyyy-MM-dd", time.substring(0,4)+"-"+time.substring(4,6)+"-"+time.substring(6,8));
					String week=DateUtil.getWeekOfDate(date);
					strBuffer.append(time).append("|").append(week).append("|").append(str1.substring(8,11)).append("|").append(content);
					if(i!=strs.length-1){
						strBuffer.append("^");
					}
				}
				return strBuffer.toString();
				
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
     	 
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jczjq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jczjq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jczjq);
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11, jcspf);
    	 
    	 
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
    	 
    	 
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jcspf);
    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jcspf);
    	 
    	 
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
