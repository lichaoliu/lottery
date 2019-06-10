package com.lottery.ticket.vender.impl.anrui;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderOddsConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class AnruiLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<LotteryType, String> wareidMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	protected static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	public static Map<Integer, String> playTypeMapJc = new HashMap<Integer, String>();
	/** 票内容转换 */
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
	/**赔率转换 */
	public static Map<PlayType, IVenderOddsConverter> oddConverterMap = new HashMap<PlayType, IVenderOddsConverter>();

	/**
	 * 周
	 */
	public static Map<String, String> WEEKSTR = new HashMap<String, String>();

	static{
		
			
		WEEKSTR.put("1", "周一");
		WEEKSTR.put("2", "周二");
		WEEKSTR.put("3", "周三");
		WEEKSTR.put("4", "周四");
		WEEKSTR.put("5", "周五");
		WEEKSTR.put("6", "周六");
		WEEKSTR.put("7", "周日");	
		//足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "1");
		
		
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
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_SFC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, defaultPhaseConverter);
		
		//彩种转换
		
		lotteryTypeMap.put(LotteryType.SSQ, "200");
		lotteryTypeMap.put(LotteryType.ZC_SFC, "10");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "11");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "13");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "12");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF,"30");  
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"31");  
		lotteryTypeMap.put(LotteryType.JCLQ_SFC,"32");  
		lotteryTypeMap.put(LotteryType.JCLQ_DXF,"33");  
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG,"35");  
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"26");  
		lotteryTypeMap.put(LotteryType.JCZQ_BF,"21"); 
		lotteryTypeMap.put(LotteryType.JCZQ_JQS,"22"); 
		lotteryTypeMap.put(LotteryType.JCZQ_BQC,"23"); 
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"20"); 
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG,"25"); 
		
		
		toLotteryTypeMap.put("10", LotteryType.ZC_SFC);
		toLotteryTypeMap.put("11", LotteryType.ZC_RJC);
		toLotteryTypeMap.put("13", LotteryType.ZC_JQC);
		toLotteryTypeMap.put("12", LotteryType.ZC_BQC);
		
		
		
		
		wareidMap.put(LotteryType.ZC_SFC, "0");
		wareidMap.put(LotteryType.ZC_RJC, "0");
		wareidMap.put(LotteryType.ZC_JQC, "0");
		wareidMap.put(LotteryType.ZC_BQC, "0");
		
		wareidMap.put(LotteryType.JCLQ_SF,"98");  
		wareidMap.put(LotteryType.JCLQ_RFSF,"99");  
		wareidMap.put(LotteryType.JCLQ_SFC,"100");  
		wareidMap.put(LotteryType.JCLQ_DXF,"101");  
		wareidMap.put(LotteryType.JCLQ_HHGG,"4722");  
		wareidMap.put(LotteryType.JCZQ_RQ_SPF,"6145");  
		wareidMap.put(LotteryType.JCZQ_BF,"95"); 
		wareidMap.put(LotteryType.JCZQ_JQS,"96"); 
		wareidMap.put(LotteryType.JCZQ_BQC,"97"); 
		wareidMap.put(LotteryType.JCZQ_SPF_WRQ,"94"); 
		wareidMap.put(LotteryType.JCZQ_HHGG,"4721"); 
		
		
		//玩法转换
		
		//足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "1");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "0");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "1");
		
		//竞彩串法
		 playTypeMapJc.put(PlayType.jczq_spf_1_1.getValue(), "单关");
		 playTypeMapJc.put(PlayType.jczq_spf_2_1.getValue(), "2X1");
		 playTypeMapJc.put(PlayType.jczq_spf_3_1.getValue(), "3X1");
         playTypeMapJc.put(PlayType.jczq_spf_4_1.getValue(), "4X1");
         playTypeMapJc.put(PlayType.jczq_spf_5_1.getValue(), "5X1");
         playTypeMapJc.put(PlayType.jczq_spf_6_1.getValue(), "6X1");
         playTypeMapJc.put(PlayType.jczq_spf_7_1.getValue(), "7X1");
         playTypeMapJc.put(PlayType.jczq_spf_8_1.getValue(), "8X1");
         playTypeMapJc.put(PlayType.jczq_spf_3_3.getValue(), "3X3");
         playTypeMapJc.put(PlayType.jczq_spf_3_4.getValue(), "3X4");
         playTypeMapJc.put(PlayType.jczq_spf_4_4.getValue(), "4X4");
         playTypeMapJc.put(PlayType.jczq_spf_4_5.getValue(), "4X5");
         playTypeMapJc.put(PlayType.jczq_spf_4_6.getValue(), "4X6");
         playTypeMapJc.put(PlayType.jczq_spf_4_11.getValue(), "4X11");
         playTypeMapJc.put(PlayType.jczq_spf_5_5.getValue(), "5X5");
         playTypeMapJc.put(PlayType.jczq_spf_5_6.getValue(), "5X6");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_10.getValue(), "5X10");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_16.getValue(), "5X16");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_20.getValue(), "5X20");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_26.getValue(), "5X26");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_6.getValue(), "6X6");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_7.getValue(), "6X7");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_15.getValue(), "6X15");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_20.getValue(), "6X20");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_22.getValue(), "6X22");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_35.getValue(), "6X35");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_42.getValue(), "6X42");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_50.getValue(), "6X50");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_57.getValue(), "6X57");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_7.getValue(), "7X7");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_8.getValue(), "7X8");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_21.getValue(), "7X21");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_35.getValue(), "7X35");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_120.getValue(), "7X120");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_8.getValue(), "8X8");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_9.getValue(), "8X9");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_28.getValue(), "8X28");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_56.getValue(), "8X56");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_70.getValue(), "8X70");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_247.getValue(), "8X247");
     	 
     	 playTypeMapJc.put(PlayType.jczq_jqs_1_1.getValue(), "单关");
     	 playTypeMapJc.put(PlayType.jczq_jqs_2_1.getValue(), "2X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_3_1.getValue(), "3X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_1.getValue(), "4X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_1.getValue(), "5X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_6_1.getValue(), "6X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_7_1.getValue(), "7X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_8_1.getValue(), "8X1");
     	 playTypeMapJc.put(PlayType.jczq_jqs_3_3.getValue(), "3X3");
     	 playTypeMapJc.put(PlayType.jczq_jqs_3_4.getValue(), "3X4");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_4.getValue(), "4X4");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_5.getValue(), "4X5");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_6.getValue(), "4X6");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_11.getValue(), "4X11");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_5.getValue(), "5X5");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_6.getValue(), "5X6");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_10.getValue(), "5X10");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_16.getValue(), "5X16");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_20.getValue(), "5X20");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_26.getValue(), "5X26");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_6.getValue(), "6X6");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_7.getValue(), "6X7");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_15.getValue(), "6X15");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_20.getValue(), "6X20");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_22.getValue(), "6X22");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_35.getValue(), "6X35");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_42.getValue(), "6X42");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_50.getValue(), "6X50");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_57.getValue(), "6X57");
    	 
    	 playTypeMapJc.put(PlayType.jczq_bf_1_1.getValue(), "单关");
    	 playTypeMapJc.put(PlayType.jczq_bf_2_1.getValue(), "2X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_3_1.getValue(), "3X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_1.getValue(), "4X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_5_1.getValue(), "5X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_6_1.getValue(), "6X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_7_1.getValue(), "7X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_8_1.getValue(), "8X1");
    	 playTypeMapJc.put(PlayType.jczq_bf_3_3.getValue(), "3X3");
    	 playTypeMapJc.put(PlayType.jczq_bf_3_4.getValue(), "3X4");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_4.getValue(), "4X4");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_5.getValue(), "4X5");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_6.getValue(), "4X6");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_11.getValue(), "4X11");
    	 
    	 
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_1_1.getValue(), "单关");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_2_1.getValue(), "2X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_1.getValue(), "3X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_1.getValue(), "4X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_1.getValue(), "5X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_1.getValue(), "6X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_1.getValue(), "7X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_1.getValue(), "8X1");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_3.getValue(), "3X3");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_4.getValue(), "3X4");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_4.getValue(), "4X4");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_5.getValue(), "4X5");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_6.getValue(), "4X6");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_11.getValue(), "4X11");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_5.getValue(), "5X5");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_6.getValue(), "5X6");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_10.getValue(), "5X10");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_16.getValue(), "5X16");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_20.getValue(), "5X20");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_26.getValue(), "5X26");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_6.getValue(), "6X6");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_7.getValue(), "6X7");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_15.getValue(), "6X15");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_20.getValue(), "6X20");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_22.getValue(), "6X22");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_35.getValue(), "6X35");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_42.getValue(), "6X42");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_50.getValue(), "6X50");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_57.getValue(), "6X57");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_7.getValue(), "7X7");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_8.getValue(), "7X8");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_21.getValue(), "7X21");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_35.getValue(), "7X35");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_120.getValue(), "7X120");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_8.getValue(), "8X8");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_9.getValue(), "8X9");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_28.getValue(), "8X28");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_56.getValue(), "8X56");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_70.getValue(), "8X70");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_247.getValue(), "8X247");
    	 
    	 playTypeMapJc.put(PlayType.jczq_mix_2_1.getValue(), "2X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_3_1.getValue(), "3X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_1.getValue(), "4X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_1.getValue(), "5X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_1.getValue(), "6X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_1.getValue(), "7X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_1.getValue(), "8X1");
    	 playTypeMapJc.put(PlayType.jczq_mix_3_3.getValue(), "3X3");
    	 playTypeMapJc.put(PlayType.jczq_mix_3_4.getValue(), "3X4");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_4.getValue(), "4X4");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_5.getValue(), "4X5");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_6.getValue(), "4X6");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_11.getValue(), "4X11");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_5.getValue(), "5X5");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_6.getValue(), "5X6");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_10.getValue(), "5X10");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_16.getValue(), "5X16");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_20.getValue(), "5X20");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_26.getValue(), "5X26");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_6.getValue(), "6X6");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_7.getValue(), "6X7");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_15.getValue(), "6X15");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_20.getValue(), "6X20");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_22.getValue(), "6X22");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_35.getValue(), "6X35");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_42.getValue(), "6X42");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_50.getValue(), "6X50");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_57.getValue(), "6X57");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_7.getValue(), "7X7");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_8.getValue(), "7X8");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_21.getValue(), "7X21");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_35.getValue(), "7X35");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_120.getValue(), "7X120");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_8.getValue(), "8X8");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_9.getValue(), "8X9");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_28.getValue(), "8X28");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_56.getValue(), "8X56");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_70.getValue(), "8X70");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_247.getValue(), "8X247");
    	 
    	 
    	 playTypeMapJc.put(PlayType.jczq_bqc_1_1.getValue(), "单关");
    	 playTypeMapJc.put(PlayType.jczq_bqc_2_1.getValue(), "2X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_3_1.getValue(), "3X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_1.getValue(), "4X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_5_1.getValue(), "5X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_6_1.getValue(), "6X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_7_1.getValue(), "7X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_8_1.getValue(), "8X1");
    	 playTypeMapJc.put(PlayType.jczq_bqc_3_3.getValue(), "3X3");
    	 playTypeMapJc.put(PlayType.jczq_bqc_3_4.getValue(), "3X4");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_4.getValue(), "4X4");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_5.getValue(), "4X5");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_6.getValue(), "4X6");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_11.getValue(), "4X11");
		
		
		
    	 
    	playTypeMapJc.put(PlayType.jclq_sf_1_1.getValue(),"单关");
    	playTypeMapJc.put(PlayType.jclq_sf_2_1.getValue(),"2X1");
    	playTypeMapJc.put(PlayType.jclq_sf_3_1.getValue(),"3X1");
 		playTypeMapJc.put(PlayType.jclq_sf_4_1.getValue(),"4X1");
 		playTypeMapJc.put(PlayType.jclq_sf_5_1.getValue(),"5X1");
 		playTypeMapJc.put(PlayType.jclq_sf_6_1.getValue(),"6X1");
 		playTypeMapJc.put(PlayType.jclq_sf_7_1.getValue(),"7X1");
 		playTypeMapJc.put(PlayType.jclq_sf_8_1.getValue(),"8X1");
 		playTypeMapJc.put(PlayType.jclq_sf_3_3.getValue(),"3X3");
 		playTypeMapJc.put(PlayType.jclq_sf_3_4.getValue(),"3X4");
 		playTypeMapJc.put(PlayType.jclq_sf_4_4.getValue(),"4X4");
 		playTypeMapJc.put(PlayType.jclq_sf_4_5.getValue(),"4X5");
 		playTypeMapJc.put(PlayType.jclq_sf_4_6.getValue(),"4X6");
 		playTypeMapJc.put(PlayType.jclq_sf_4_11.getValue(),"4X11");
 		playTypeMapJc.put(PlayType.jclq_sf_5_5.getValue(),"5X5");
 		playTypeMapJc.put(PlayType.jclq_sf_5_6.getValue(),"5X6");
 		playTypeMapJc.put(PlayType.jclq_sf_5_10.getValue(),"5X10");
 		playTypeMapJc.put(PlayType.jclq_sf_5_16.getValue(),"5X16");
 		playTypeMapJc.put(PlayType.jclq_sf_5_20.getValue(),"5X20");
 		playTypeMapJc.put(PlayType.jclq_sf_5_26.getValue(),"5X26");
 		playTypeMapJc.put(PlayType.jclq_sf_6_6.getValue(),"6X6");
 		playTypeMapJc.put(PlayType.jclq_sf_6_7.getValue(),"6X7");
 		playTypeMapJc.put(PlayType.jclq_sf_6_15.getValue(),"6X15");
 		playTypeMapJc.put(PlayType.jclq_sf_6_20.getValue(),"6X20");
 		playTypeMapJc.put(PlayType.jclq_sf_6_22.getValue(),"6X22");
 		playTypeMapJc.put(PlayType.jclq_sf_6_35.getValue(),"6X35");
 		playTypeMapJc.put(PlayType.jclq_sf_6_42.getValue(),"6X42");
 		playTypeMapJc.put(PlayType.jclq_sf_6_50.getValue(),"6X50");
 		playTypeMapJc.put(PlayType.jclq_sf_6_57.getValue(),"6X57");
 		playTypeMapJc.put(PlayType.jclq_sf_7_7.getValue(),"7X7");
 		playTypeMapJc.put(PlayType.jclq_sf_7_8.getValue(),"7X8");
 		playTypeMapJc.put(PlayType.jclq_sf_7_21.getValue(),"7X21");
 		playTypeMapJc.put(PlayType.jclq_sf_7_35.getValue(),"7X35");
 		playTypeMapJc.put(PlayType.jclq_sf_7_120.getValue(),"7X120");
 		playTypeMapJc.put(PlayType.jclq_sf_8_8.getValue(),"8X8");
 		playTypeMapJc.put(PlayType.jclq_sf_8_9.getValue(),"8X9");
 		playTypeMapJc.put(PlayType.jclq_sf_8_28.getValue(),"8X28");
 		playTypeMapJc.put(PlayType.jclq_sf_8_56.getValue(),"8X56");
 		playTypeMapJc.put(PlayType.jclq_sf_8_70.getValue(),"8X70");
 		playTypeMapJc.put(PlayType.jclq_sf_8_247.getValue(),"8X247");
 		
 		
 		
 		playTypeMapJc.put(PlayType.jclq_rfsf_1_1.getValue(),"单关");
 		playTypeMapJc.put(PlayType.jclq_rfsf_2_1.getValue(),"2X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_3_1.getValue(),"3X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_1.getValue(),"4X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_1.getValue(),"5X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_1.getValue(),"6X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_1.getValue(),"7X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_1.getValue(),"8X1");
 		playTypeMapJc.put(PlayType.jclq_rfsf_3_3.getValue(),"3X3");
 		playTypeMapJc.put(PlayType.jclq_rfsf_3_4.getValue(),"3X4");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_4.getValue(),"4X4");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_5.getValue(),"4X5");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_6.getValue(),"4X6");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_11.getValue(),"4X11");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_5.getValue(),"5X5");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_6.getValue(),"5X6");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_10.getValue(),"5X10");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_16.getValue(),"5X16");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_20.getValue(),"5X20");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_26.getValue(),"5X26");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_6.getValue(),"6X6");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_7.getValue(),"6X7");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_15.getValue(),"6X15");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_20.getValue(),"6X20");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_22.getValue(),"6X22");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_35.getValue(),"6X35");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_42.getValue(),"6X42");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_50.getValue(),"6X50");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_57.getValue(),"6X57");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_7.getValue(),"7X7");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_8.getValue(),"7X8");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_21.getValue(),"7X21");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_35.getValue(),"7X35");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_120.getValue(),"7X120");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_8.getValue(),"8X8");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_9.getValue(),"8X9");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_28.getValue(),"8X28");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_56.getValue(),"8X56");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_70.getValue(),"8X70");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_247.getValue(),"8X247");
 		
 		playTypeMapJc.put(PlayType.jclq_sfc_1_1.getValue(),"单关");
 		playTypeMapJc.put(PlayType.jclq_sfc_2_1.getValue(),"2X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_3_1.getValue(),"3X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_1.getValue(),"4X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_5_1.getValue(),"5X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_6_1.getValue(),"6X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_7_1.getValue(),"7X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_8_1.getValue(),"8X1");
 		playTypeMapJc.put(PlayType.jclq_sfc_3_3.getValue(),"3X3");
 		playTypeMapJc.put(PlayType.jclq_sfc_3_4.getValue(),"3X4");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_4.getValue(),"4X4");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_5.getValue(),"4X5");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_6.getValue(),"4X6");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_11.getValue(),"4X11");
 		
 		playTypeMapJc.put(PlayType.jclq_dxf_1_1.getValue(),"单关");
 		playTypeMapJc.put(PlayType.jclq_dxf_2_1.getValue(),"2X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_3_1.getValue(),"3X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_1.getValue(),"4X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_1.getValue(),"5X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_1.getValue(),"6X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_1.getValue(),"7X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_1.getValue(),"8X1");
 		playTypeMapJc.put(PlayType.jclq_dxf_3_3.getValue(),"3X3");
 		playTypeMapJc.put(PlayType.jclq_dxf_3_4.getValue(),"3X4");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_4.getValue(),"4X4");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_5.getValue(),"4X5");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_6.getValue(),"4X6");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_11.getValue(),"4X11");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_5.getValue(),"5X5");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_6.getValue(),"5X6");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_10.getValue(),"5X10");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_16.getValue(),"5X16");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_20.getValue(),"5X20");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_26.getValue(),"5X26");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_6.getValue(),"6X6");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_7.getValue(),"6X7");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_15.getValue(),"6X15");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_20.getValue(),"6X20");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_22.getValue(),"6X22");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_35.getValue(),"6X35");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_42.getValue(),"6X42");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_50.getValue(),"6X50");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_57.getValue(),"6X57");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_7.getValue(),"7X7");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_8.getValue(),"7X8");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_21.getValue(),"7X21");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_35.getValue(),"7X35");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_120.getValue(),"7X120");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_8.getValue(),"8X8");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_9.getValue(),"8X9");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_28.getValue(),"8X28");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_56.getValue(),"8X56");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_70.getValue(),"8X70");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_247.getValue(),"8X247");
 		
 		playTypeMapJc.put(PlayType.jclq_mix_2_1.getValue(),"2X1");
 		playTypeMapJc.put(PlayType.jclq_mix_3_1.getValue(),"3X1");
 		playTypeMapJc.put(PlayType.jclq_mix_4_1.getValue(),"4X1");
 		playTypeMapJc.put(PlayType.jclq_mix_5_1.getValue(),"5X1");
 		playTypeMapJc.put(PlayType.jclq_mix_6_1.getValue(),"6X1");
 		playTypeMapJc.put(PlayType.jclq_mix_7_1.getValue(),"7X1");
 		playTypeMapJc.put(PlayType.jclq_mix_8_1.getValue(),"8X1");
 		playTypeMapJc.put(PlayType.jclq_mix_3_3.getValue(),"3X3");
 		playTypeMapJc.put(PlayType.jclq_mix_3_4.getValue(),"3X4");
 		playTypeMapJc.put(PlayType.jclq_mix_4_4.getValue(),"4X4");
 		playTypeMapJc.put(PlayType.jclq_mix_4_5.getValue(),"4X5");
 		playTypeMapJc.put(PlayType.jclq_mix_4_6.getValue(),"4X6");
 		playTypeMapJc.put(PlayType.jclq_mix_4_11.getValue(),"4X11");
 		playTypeMapJc.put(PlayType.jclq_mix_5_5.getValue(),"5X5");
 		playTypeMapJc.put(PlayType.jclq_mix_5_6.getValue(),"5X6");
 		playTypeMapJc.put(PlayType.jclq_mix_5_10.getValue(),"5X10");
 		playTypeMapJc.put(PlayType.jclq_mix_5_16.getValue(),"5X16");
 		playTypeMapJc.put(PlayType.jclq_mix_5_20.getValue(),"5X20");
 		playTypeMapJc.put(PlayType.jclq_mix_5_26.getValue(),"5X26");
 		playTypeMapJc.put(PlayType.jclq_mix_6_6.getValue(),"6X6");
 		playTypeMapJc.put(PlayType.jclq_mix_6_7.getValue(),"6X7");
 		playTypeMapJc.put(PlayType.jclq_mix_6_15.getValue(),"6X15");
 		playTypeMapJc.put(PlayType.jclq_mix_6_20.getValue(),"6X20");
 		playTypeMapJc.put(PlayType.jclq_mix_6_22.getValue(),"6X22");
 		playTypeMapJc.put(PlayType.jclq_mix_6_35.getValue(),"6X35");
 		playTypeMapJc.put(PlayType.jclq_mix_6_42.getValue(),"6X42");
 		playTypeMapJc.put(PlayType.jclq_mix_6_50.getValue(),"6X50");
 		playTypeMapJc.put(PlayType.jclq_mix_6_57.getValue(),"6X57");
 		playTypeMapJc.put(PlayType.jclq_mix_7_7.getValue(),"7X7");
 		playTypeMapJc.put(PlayType.jclq_mix_7_8.getValue(),"7X8");
 		playTypeMapJc.put(PlayType.jclq_mix_7_21.getValue(),"7X21");
 		playTypeMapJc.put(PlayType.jclq_mix_7_35.getValue(),"7X35");
 		playTypeMapJc.put(PlayType.jclq_mix_7_120.getValue(),"7X120");
 		playTypeMapJc.put(PlayType.jclq_mix_8_8.getValue(),"8X8");
 		playTypeMapJc.put(PlayType.jclq_mix_8_9.getValue(),"8X9");
 		playTypeMapJc.put(PlayType.jclq_mix_8_28.getValue(),"8X28");
 		playTypeMapJc.put(PlayType.jclq_mix_8_56.getValue(),"8X56");
 		playTypeMapJc.put(PlayType.jclq_mix_8_70.getValue(),"8X70");
 		playTypeMapJc.put(PlayType.jclq_mix_8_247.getValue(),"8X247");
		
 		 //ssq单式
		IVenderTicketConverter ssqds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.split("\\^");
				int i=0;
				for(String s:strs){
					String content=s.replace(",", "").replace("|", "-");
					strBuilder.append(content);
					if(i!=strs.length-1){
						strBuilder.append("#");
					}
					i++;
				}
				strBuilder.append("$单式");
		        return strBuilder.toString();
		}};
 		
 		
			 //足彩胜负彩单式、半全场单式、四场进球单式
			IVenderTicketConverter zcsfc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
					String []strs=contentStr.split("\\^");
					int i=0;
					for(String s:strs){
						String content=s.replace(",", "-").replace("~", "*");
						strBuilder.append(content);
						if(i!=strs.length-1){
							strBuilder.append("#");
						}
						i++;
					}
					strBuilder.append("$普通");
			        return strBuilder.toString();
			}};
			
			
			 //足彩胜负彩半全场复式
			IVenderTicketConverter zcsfcfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1].replace("^","");
					String []contents=contentStr.replace("~", "*").split(",");
					StringBuilder strBuilder=new StringBuilder();
					int i=0;
					for(String content:contents){
						for(int j=0;j<content.length();j++){
							strBuilder.append(content.substring(j,j+1));
							 if(j<content.length()-1){
			                    	strBuilder.append(",");
							 }
						}
						 if(i<contents.length-1){
			                strBuilder.append("-");
						 }
						i++;
					}
					strBuilder.append("$普通");
			        return strBuilder.toString();
			}};
			
			
			//足彩任9场复式
			IVenderTicketConverter zcrjcfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					String content=contentStr.replace(",", "-").replace("^","")+"$普通";
			        return content;
			}};
			

			 //足彩半全场单式复试
			IVenderTicketConverter zcbqcfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String[] codes = ticket.getContent().split("\\-")[1].replace("^", "").split("\\,");
					StringBuilder convertcode = new StringBuilder();
					
					for(int i=0;i<codes.length;i=i+2) {
						convertcode.append(decare(codes[i],codes[i+1])).append("-");
					}
					convertcode = convertcode.deleteCharAt(convertcode.length()-1).append("$普通");
			        return convertcode.toString();
			}};
			
			 //足彩半全场单式
			IVenderTicketConverter zcbqcds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String contentStr = ticket.getContent().split("\\-")[1];
					String []strs=contentStr.split("\\^");
					int j=0;
					for(String s:strs){
						String []contents=s.split("\\,");
						for(int i=0;i<contents.length;i=i+2) {
							strBuilder.append(decare(contents[i],contents[i+1])).append("-");
						}
						strBuilder = strBuilder.deleteCharAt(strBuilder.length()-1);
						if(j!=strs.length-1){
							strBuilder.append("#");
						}
						j++;
					}
					strBuilder.append("$普通");
			        return strBuilder.toString();
			}};
			
			
			IVenderTicketConverter jcspf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					int i=0;
					for(String str1:strs){
						String content=str1.split("\\(")[1];
						String time=str1.substring(8).split("\\(")[0];
						Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
						String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
						strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","胜").replace("1", "平").replace("0", "负").replace(")","]")).append("~0");
						if(i!=strs.length-1){
							strBuffer.append("/");
						}
						i++;
					}
					strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
					return strBuffer.toString();
				}};
		   
				IVenderTicketConverter jcspfrq = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
						StringBuffer strBuffer=new StringBuffer();
						int i=0;
						for(String str1:strs){
							String content=str1.split("\\(")[1];
							String time=str1.substring(8).split("\\(")[0];
							Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
							String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","让球胜").replace("1", "让球平").replace("0", "让球负").replace(")","]")).append("~0");
							if(i!=strs.length-1){
								strBuffer.append("/");
							}
							i++;
						}
						strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
						return strBuffer.toString();
				}};
				IVenderTicketConverter jczqbf = new IVenderTicketConverter() {
						@Override
						public String convert(Ticket ticket) {
							String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
							StringBuffer strBuffer=new StringBuffer();
							int i=0;
							for(String str1:strs){
								String []contents=str1.split("\\(")[1].split("\\)")[0].split("\\,");
								String time=str1.substring(8).split("\\(")[0];
								Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
								String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
								strBuffer.append(week).append(time).append("~").append("[");
								int j=0;
								for(String content:contents){
									if(content.contains("99")||content.contains("09")||content.contains("90")){
										strBuffer.append(content.replace("99", "平其他").replace("09", "负其他").replace("90", "胜其他"));
									}else{
										strBuffer.append(content.substring(0,1)).append(":").append(content.substring(1));
									}
									if(j!=contents.length-1){
										strBuffer.append(",");
									}
									j++;
								}
								strBuffer.append("]~0");
								if(i!=strs.length-1){
									strBuffer.append("/");
								}
								i++;
							}
							strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
							return strBuffer.toString();
						}};
						
					IVenderTicketConverter jcjqs = new IVenderTicketConverter() {
							@Override
							public String convert(Ticket ticket) {
								String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
								StringBuffer strBuffer=new StringBuffer();
								int i=0;
								for(String str1:strs){
									String content=str1.split("\\(")[1];
									String time=str1.substring(8).split("\\(")[0];
									Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
									String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
									strBuffer.append(week).append(time).append("~").append("[").append(content.replace("7","7+").replace(")","]")).append("~0");
									if(i!=strs.length-1){
										strBuffer.append("/");
									}
									i++;
							   }
							strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
							return strBuffer.toString();
				}};

				IVenderTicketConverter jcbqc = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
						StringBuffer strBuffer=new StringBuffer();
						int i=0;
						for(String str1:strs){
							String content=str1.split("\\(")[1];
							String time=str1.substring(8).split("\\(")[0];
							Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
							String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","胜").replace("1","平").replace("0","负").replace(")","]")).append("~0");
							if(i!=strs.length-1){
								strBuffer.append("/");
							}
							i++;
					   }
					strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
					return strBuffer.toString();
		    }};
		    
		    IVenderTicketConverter jczchh = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					int i=0;
					for(String str1:strs){
						int playType=Integer.parseInt(str1.split("\\*")[1].split("\\(")[0]);
						String time=str1.substring(8,11).split("\\(")[0];
						Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
						String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
						if(LotteryType.JCZQ_RQ_SPF.getValue()==playType){
							String content=str1.split("\\*")[1].split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","让球胜").replace("1", "让球平").replace("0", "让球负").replace(")","]")).append("~0");
						}else if(LotteryType.JCZQ_BF.getValue()==playType){
							String []contents=str1.split("\\(")[1].split("\\)")[0].split("\\,");
							strBuffer.append(week).append(time).append("~").append("[");
							int j=0;
							for(String content:contents){
								if(content.contains("99")||content.contains("09")||content.contains("90")){
									strBuffer.append(content.replace("99", "平其他").replace("09", "负其他").replace("90", "胜其他"));
								}else{
									strBuffer.append(content.substring(0,1)).append(":").append(content.substring(1));
								}
								if(j!=contents.length-1){
									strBuffer.append(",");
								}
								j++;
							}
							strBuffer.append("]~0");
						}else if(LotteryType.JCZQ_JQS.getValue()==playType){
							String content=str1.split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("7","7+").replace(")","]")).append("~0");
						}else if(LotteryType.JCZQ_SPF_WRQ.getValue()==playType){
							String content=str1.split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","胜").replace("1", "平").replace("0", "负").replace(")","]")).append("~0");
							
						}else if(LotteryType.JCZQ_BQC.getValue()==playType){
							String content=str1.split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","胜").replace("1","平").replace("0","负").replace(")","]")).append("~0");
						}
						
						if(i!=strs.length-1){
							strBuffer.append("/");
						}
						i++;
					}
				    strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
				return strBuffer.toString();
	    }};
	    
	    IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
				StringBuffer strBuffer=new StringBuffer();
				int i=0;
				for(String str1:strs){
					String content=str1.split("\\(")[1];
					String time=str1.substring(8).split("\\(")[0];
					Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
					String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
					strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","主胜").replace("0", "主负").replace(")","]")).append("~0");
					if(i!=strs.length-1){
						strBuffer.append("/");
					}
					i++;
				}
				strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
				return strBuffer.toString();
			}};
			IVenderTicketConverter jclqrfsf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					int i=0;
					for(String str1:strs){
						String content=str1.split("\\(")[1];
						String time=str1.substring(8).split("\\(")[0];
						Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
						String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
						strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","让分主胜").replace("0", "让分主负").replace(")","]")).append("~0");
						if(i!=strs.length-1){
							strBuffer.append("/");
						}
						i++;
					}
					strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
					return strBuffer.toString();
			}};
			IVenderTicketConverter jclqsfc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					int i=0;
					for(String str1:strs){
						String []contents=str1.split("\\(")[1].split("\\)")[0].split("\\,");
						String time=str1.substring(8).split("\\(")[0];
						Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
						String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
						strBuffer.append(week).append(time).append("~").append("[");
						int j=0;
						for(String content:contents){
							strBuffer.append(content.replace("05", "主胜21-25").replace("06", "主胜26+")
									.replace("16", "客胜26+").replace("14", "客胜16-20").replace("11", "客胜1-5").replace("15", "客胜21-25").replace("13", "客胜11-15").replace("03", "主胜11-15").replace("12", "客胜6-10").replace("04", "主胜16-20").replace("01", "主胜1-5").replace("02", "主胜6-10"));
							if(j!=contents.length-1){
								strBuffer.append(",");
							}
							j++;
						}
						strBuffer.append("]~0");
						if(i!=strs.length-1){
							strBuffer.append("/");
						}
						i++;
					}
					strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
					return strBuffer.toString();
			}};
			IVenderTicketConverter jclqdxf= new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					int i=0;
					for(String str1:strs){
						String content=str1.split("\\(")[1];
						String time=str1.substring(8).split("\\(")[0];
						Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
						String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
						strBuffer.append(week).append(time).append("~").append("[").append(content.replace("1","大").replace("2", "小").replace(")","]")).append("~0");
						if(i!=strs.length-1){
							strBuffer.append("/");
						}
						i++;
					}
					strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
					return strBuffer.toString();
			}};
			
			
			IVenderTicketConverter jclqhh = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					int i=0;
					for(String str1:strs){
						int playType=Integer.parseInt(str1.split("\\*")[1].split("\\(")[0]);
						String time=str1.substring(8,11).split("\\(")[0];
						Date date=DateUtil.parse("yyyy-MM-dd", str1.substring(0,4)+"-"+str1.substring(4,6)+"-"+str1.substring(6,8));
						String week=WEEKSTR.get(DateUtil.getWeekOfDate(date));
						if(LotteryType.JCLQ_SF.getValue()==playType){
							String content=str1.split("\\*")[1].split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","让分主胜").replace("0", "让分主负").replace(")","]")).append("~0");
						}else if(LotteryType.JCLQ_RFSF.getValue()==playType){
							String content=str1.split("\\*")[1].split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("3","让分主胜").replace("0", "让分主负").replace(")","]")).append("~0");
                        }else if(LotteryType.JCLQ_SFC.getValue()==playType){
                        	String []contents=str1.split("\\(")[1].split("\\)")[0].split("\\,");
    						strBuffer.append(week).append(time).append("~").append("[");
    						int j=0;
    						for(String content:contents){
    							strBuffer.append(content.replace("01", "主胜1-5").replace("02", "主胜6-10")
    									.replace("03", "主胜11-15").replace("05", "主胜21-25")
    									.replace("06", "主胜26+").replace("11", "客胜1-5").replace("12", "客胜6-10")
    									.replace("13", "客胜11-15").replace("16", "客胜26+").replace("14", "客胜16-20").replace("15", "客胜21-25").replace("04", "主胜16-20"));
    							if(j!=contents.length-1){
    								strBuffer.append(",");
    							}
    							j++;
    						}
    						strBuffer.append("]~0");
    					}else if(LotteryType.JCLQ_DXF.getValue()==playType){
							String content=str1.split("\\(")[1];
							strBuffer.append(week).append(time).append("~").append("[").append(content.replace("1","大").replace("2", "小").replace(")","]")).append("~0");
                        }
						if(i!=strs.length-1){
							strBuffer.append("/");
						}
						i++;
					}
				    strBuffer.append("$").append(playTypeMapJc.get(Integer.parseInt(ticket.getContent().split("-")[0])));
				return strBuffer.toString();
	    }};
	    
	    
	    IVenderOddsConverter jczqspfodd = new IVenderOddsConverter(){
			@Override
			public String convertOdds(Ticket ticket, String odds) {
				StringBuffer stringBuffer=new StringBuffer();
				String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
				String []oddstr=odds.split("\\/");
				int i=0;
				for(String content:contents){
					stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("胜","3")
		    				.replace("负", "0").replace("平", "1").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
					if(i!=contents.length-1){
						   stringBuffer.append("|");
					}
				    i++;
				}
				return stringBuffer.toString();
			}
	    };
	    
	    IVenderOddsConverter jczqbqc = new IVenderOddsConverter() {
	    	@Override
	    	public String convertOdds(Ticket ticket,String odds) {
	    		StringBuffer stringBuffer=new StringBuffer();
				String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
				String []oddstr=odds.split("\\/");
				int i=0;
				for(String content:contents){
					stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("胜胜","33")
							.replace("胜平", "31").replace("胜负", "30").replace("平平", "11").replace("平胜", "13").replace("平负", "10").replace("负胜", "03").replace("负平", "01").replace("负负", "00").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
					if(i!=contents.length-1){
						   stringBuffer.append("|");
					}
				    i++;
				}
				return stringBuffer.toString();
	    	   }
			};	
	    
				IVenderOddsConverter jczqbfodd = new IVenderOddsConverter() {
					@Override
					public String convertOdds(Ticket ticket,String odds) {
						StringBuffer stringBuffer=new StringBuffer();
						String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
						String []oddstr=odds.split("\\/");
						int i=0;
						for(String content:contents){
							 stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("胜胜","33")
									 .replace("负负", "00").replace("平其他", "99").replace("负其他", "09").replace("胜其他", "90").replace("负平", "01").replace("负胜", "03").replace("平负", "10").replace("平平", "11").replace("平胜", "13").replace("胜平", "31").replace("胜负", "30").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
                                  if(i!=contents.length-1){
								   stringBuffer.append("|");
							}
						    i++;
						}//[66495:周三001(0):[50@300.0+42@80.00+05@125.0], 66503:周三009(0):[42@35.00+15@2500+05@2500]]
						return stringBuffer.toString();
					}
			      };
					IVenderOddsConverter jczqjqs = new IVenderOddsConverter() {
						@Override
						public String convertOdds(Ticket ticket,String odds) {
							StringBuffer stringBuffer=new StringBuffer();
							String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
							String []oddstr=odds.split("\\/");
							int i=0;
							for(String content:contents){
								stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
						    	if(i!=contents.length-1){
									   stringBuffer.append("|");
								}
							    i++;
							}
							return stringBuffer.toString();
						}
				     };
						IVenderOddsConverter jczqrfsf = new IVenderOddsConverter() {
							@Override
							public String convertOdds(Ticket ticket,String odds) {
								StringBuffer stringBuffer=new StringBuffer();
								String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
								String []oddstr=odds.split("\\/");
								int i=0;
								for(String content:contents){
									stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("让球平","1").replace("让球胜","3").replace("让球负","0").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
						    	    if(i!=contents.length-1){
										   stringBuffer.append("|");
									}
								    i++;
								}
								return stringBuffer.toString();
							}
						 };
						IVenderOddsConverter jclqrfsfodd = new IVenderOddsConverter() {
										@Override
										public String convertOdds(Ticket ticket,String odds) {
											StringBuffer stringBuffer=new StringBuffer();
											String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
											String []oddstr=odds.split("\\/");
											int i=0;
											for(String content:contents){
											stringBuffer.append(content.split("\\(")[0]).append("(").append(oddstr[i].split("\\(")[1].split("\\)")[0]).append(oddstr[i].split("\\(")[1].split("\\)")[1].replace(")","").replace("+", ",").replace("[","").replace("让分主胜","3").replace("让分主负","0").replace("@", "_").replace("[", "(").replace("]", ")"));
											if(i!=contents.length-1){
												   stringBuffer.append("|");
											}
										    i++;
										}
										return stringBuffer.toString();
								} };
								IVenderOddsConverter jclqsfodd = new IVenderOddsConverter() {
									@Override
									public String convertOdds(Ticket ticket,String odds) {
										StringBuffer stringBuffer=new StringBuffer();
										String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
										String []oddstr=odds.split("\\/");
										int i=0;
										for(String content:contents){
											stringBuffer.append(content.split("\\(")[0]).append("(").append(oddstr[i].split("\\:")[2].replace("(","").replace("[","").replace("主胜","3").replace("主负","0").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
										 	  if(i!=contents.length-1){
											   stringBuffer.append("|");
										}
									    i++;
									}
									return stringBuffer.toString();
							} };
							IVenderOddsConverter jclqsfcodd= new IVenderOddsConverter() {
								@Override
								public String convertOdds(Ticket ticket,String odds) {
									StringBuffer stringBuffer=new StringBuffer();
									String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
									String []oddstr=odds.split("\\/");
									int i=0;
									for(String content:contents){
										stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("主胜1-5","01").replace("主胜6-10","02")
												.replace("主胜11-15","03").replace("主胜16-20","04").replace("主胜21-25","05").replace("主胜26","06").replace("客胜1-5","11").replace("客胜6-10","12").replace("客胜11-15","13").replace("客胜16-20","14").replace("客胜21-25","15").replace("客胜26","16")
												.replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
                                        if(i!=contents.length-1){
										   stringBuffer.append("|");
									}
								    i++;
								}
								return stringBuffer.toString();
						} };
						IVenderOddsConverter jclqdxfodd= new IVenderOddsConverter() {
							@Override
							public String convertOdds(Ticket ticket,String odds) {
								StringBuffer stringBuffer=new StringBuffer();
								String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
								String []oddstr=odds.split("\\/");
								int i=0;
								for(String content:contents){
									stringBuffer.append(content.split("\\(")[0]).append("(").append(oddstr[i].split("\\(")[1].split("\\)")[0]).append(oddstr[i].split("\\(")[1].split("\\)")[1].replace(")","").replace("+", ",").replace("[","").replace("大","1").replace("小","2").replace("@", "_").replace("[", "(").replace("]", ")"));
									  if(i!=contents.length-1){
									   stringBuffer.append("|");
								}
							    i++;
							}
							return stringBuffer.toString();
					} };
					
					IVenderOddsConverter jczqhhdd= new IVenderOddsConverter() {
						@Override
						public String convertOdds(Ticket ticket,String odds) {
							StringBuffer stringBuffer=new StringBuffer();
							String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
							String []oddstr=odds.split("\\/");
							int i=0;
							for(String content:contents){
								int playType=Integer.parseInt(content.split("\\(")[0].split("\\*")[1]);
								if(LotteryType.JCZQ_RQ_SPF.getValue()==playType){
									stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("让球平","1").replace("让球胜","3").replace("让球负","0").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
							     }else if(LotteryType.JCZQ_BF.getValue()==playType){
							    	 stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("胜胜","33")
											 .replace("负负", "00").replace("平其他", "99").replace("负其他", "09").replace("胜其他", "90").replace("负平", "01").replace("负胜", "03").replace("平负", "10").replace("平平", "11").replace("平胜", "13").replace("胜平", "31").replace("胜负", "30").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
		                         }else if(LotteryType.JCZQ_JQS.getValue()==playType){
									 stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
								    	
								 }else if(LotteryType.JCZQ_BQC.getValue()==playType){
									 stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("胜胜","33")
												.replace("胜平", "31").replace("胜负", "30").replace("平平", "11").replace("平胜", "13").replace("平负", "10").replace("负胜", "03").replace("负平", "01").replace("负负", "00").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
																				
								 }else if(LotteryType.JCZQ_SPF_WRQ.getValue()==playType){
									 stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("胜","3").replace("平","1").replace("负","0").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
							     } 
								 if(i!=contents.length-1){
								   stringBuffer.append("|");
							     }
						    i++;
						}
						return stringBuffer.toString();
				} };
				
				IVenderOddsConverter jclqhhodd= new IVenderOddsConverter() {
					@Override
					public String convertOdds(Ticket ticket,String odds) {
						StringBuffer stringBuffer=new StringBuffer();
						String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
						String []oddstr=odds.split("\\/");
						int i=0;
						for(String content:contents){
							int playType=Integer.parseInt(content.split("\\(")[0].split("\\*")[1]);
							if(LotteryType.JCLQ_SF.getValue()==playType){
								stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\(")[2].replace("(","").replace("[","").replace("让分主胜","3").replace("让分主负","0").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
							}else if(LotteryType.JCLQ_RFSF.getValue()==playType){
								stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("让分主胜","3").replace("让分主负","0").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
							}else if(LotteryType.JCLQ_SFC.getValue()==playType){
								stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\:")[2].replace("主胜1-5","01").replace("主胜6-10","02")
										.replace("主胜11-15","03").replace("主胜16-20","04").replace("主胜21-25","05").replace("主胜26","06").replace("客胜1-5","11").replace("客胜6-10","12").replace("客胜11-15","13").replace("客胜16-20","14").replace("客胜21-25","15").replace("客胜26","16")
										.replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
                             }else if(LotteryType.JCLQ_DXF.getValue()==playType){
                            	 stringBuffer.append(content.split("\\(")[0]).append(oddstr[i].split("\\(")[2].replace("(","").replace("[","").replace("大","1").replace("小","2").replace("+", ",").replace("@", "_").replace("[", "(").replace("]", ")"));
							 }
							
                            if(i!=contents.length-1){
							   stringBuffer.append("|");
						}
					    i++;
					}
					return stringBuffer.toString();
			} };
			//足彩
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zcsfc);
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zcsfcfs);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zcsfc);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zcsfcfs);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zcsfc);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zcsfcfs);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zcbqcds);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zcbqcfs);
			
			
			 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jcspfrq);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcspfrq);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jcspfrq);
	     	 
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jcjqs);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jcjqs);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jcjqs);
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jczqbf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11, jczqbf);
	    	 
	    	 
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
	    	 

	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70,jczchh);
	  		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247,jczchh);
	  		
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
	 			    		
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jclqrfsf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jclqrfsf);
	 			    		
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
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jclqdxf);
	 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jclqdxf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jclqdxf);
	 		 
	 		 
	 		 
	 		 
	 		 
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,jclqhh);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,jclqhh);
	 		

			playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssqds);
	 		
	 		
	 		
	 		
	 		
	 		
	 		
	 		
	 		 oddConverterMap.put(PlayType.jczq_spf_1_1, jczqrfsf);
	 		 oddConverterMap.put(PlayType.jczq_spf_2_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_3_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_4_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_5_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_6_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_7_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_8_1, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_3_3, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_3_4, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_4_4, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_4_5, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_4_6, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_4_11,jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_5_5, jczqrfsf);
	         oddConverterMap.put(PlayType.jczq_spf_5_6, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_5_10,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_5_16,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_5_20,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_5_26,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_6_6, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_6_7, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_6_15,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_6_20,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_6_22,jczqrfsf);
	        oddConverterMap.put(PlayType.jczq_spf_6_35,jczqrfsf);
	        oddConverterMap.put(PlayType.jczq_spf_6_42,jczqrfsf);
	        oddConverterMap.put(PlayType.jczq_spf_6_50,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_6_57,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_7_7, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_7_8, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_7_21,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_7_35,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_7_120,jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_8_8,  jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_8_9,  jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_8_28, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_8_56, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_8_70, jczqrfsf);
	     	oddConverterMap.put(PlayType.jczq_spf_8_247,jczqrfsf);
	     	 
	     	oddConverterMap.put(PlayType.jczq_jqs_1_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_2_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_3_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_4_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_5_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_6_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_7_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_8_1,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_3_3,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_3_4,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_4_4,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_4_5,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_4_6,jczqjqs);
	     	oddConverterMap.put(PlayType.jczq_jqs_4_11,jczqjqs);
	     	 oddConverterMap.put(PlayType.jczq_jqs_5_5,jczqjqs);
	     	 oddConverterMap.put(PlayType.jczq_jqs_5_6,jczqjqs);
	     	 oddConverterMap.put(PlayType.jczq_jqs_5_10,jczqjqs);
	     	 oddConverterMap.put(PlayType.jczq_jqs_5_16,jczqjqs);
	     	 oddConverterMap.put(PlayType.jczq_jqs_5_20,jczqjqs);
	     	 oddConverterMap.put(PlayType.jczq_jqs_5_26,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_6,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_7,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_15,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_20,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_22,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_35,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_42,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_50,jczqjqs);
	    	 oddConverterMap.put(PlayType.jczq_jqs_6_57,jczqjqs);
	    	 
	    	 oddConverterMap.put(PlayType.jczq_bf_1_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_2_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_3_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_4_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_5_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_6_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_7_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_8_1, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_3_3, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_3_4, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_4_4, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_4_5, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_4_6, jczqbfodd);
	    	 oddConverterMap.put(PlayType.jczq_bf_4_11, jczqbfodd);
	    	 
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_1_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_2_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_3_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_4_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_7_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_1,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_3_3,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_3_4,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_4_4,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_4_5,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_4_6,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_4_11,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_5,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_6,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_10,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_16,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_20,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_5_26,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_6,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_7,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_15,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_20,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_22,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_35,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_42,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_50,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_6_57,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_7_7,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_7_8,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_7_21,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_7_35,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_7_120,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_8,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_9,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_28,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_56,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_70,jczqspfodd);
	    	 oddConverterMap.put(PlayType.jczq_spfwrq_8_247,jczqspfodd);
	    	 
	    	 
	    	 oddConverterMap.put(PlayType.jczq_bqc_1_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_2_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_3_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_4_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_5_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_6_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_7_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_8_1,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_3_3,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_3_4,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_4_4,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_4_5,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_4_6,jczqbqc);
	    	 oddConverterMap.put(PlayType.jczq_bqc_4_11,jczqbqc);
	    	 

	  		
	  		oddConverterMap.put(PlayType.jclq_sf_1_1,jclqsfodd);
	  		oddConverterMap.put(PlayType.jclq_sf_2_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_3_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_4_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_7_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_8_1,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_3_3,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_3_4,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_4_4,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_4_5,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_4_6,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_4_11,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_5,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_6,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_10,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_16,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_20,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_5_26,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_6,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_7,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_15,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_20,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_22,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_35,jclqsfodd);
	    	 oddConverterMap.put(PlayType.jclq_sf_6_42,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_6_50,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_6_57,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_7_7,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_7_8,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_7_21,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_7_35,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_7_120,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_8_8,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_8_9,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_8_28,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_8_56,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_8_70,jclqsfodd);
	 		oddConverterMap.put(PlayType.jclq_sf_8_247,jclqsfodd);
	 			    		
	 			    		
	 		oddConverterMap.put(PlayType.jclq_rfsf_1_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_2_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_3_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_4_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_7_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_1,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_3_3,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_3_4,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_4_4,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_4_5,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_4_6,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_4_11,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_5,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_6,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_10,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_16,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_20,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_5_26,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_6,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_7,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_15,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_20,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_22,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_35,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_42,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_50,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_6_57,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_7_7,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_7_8,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_7_21,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_7_35,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_7_120,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_8,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_9,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_28,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_56,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_70,jclqrfsfodd);
	 		oddConverterMap.put(PlayType.jclq_rfsf_8_247,jclqrfsfodd);
	 			    		
	 		oddConverterMap.put(PlayType.jclq_sfc_1_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_2_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_3_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_4_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_5_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_6_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_7_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_8_1,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_3_3,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_3_4,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_4_4,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_4_5,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_4_6,jclqsfcodd);
	 		oddConverterMap.put(PlayType.jclq_sfc_4_11,jclqsfcodd);
	 			    		
	 		oddConverterMap.put(PlayType.jclq_dxf_1_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_2_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_3_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_4_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_7_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_1,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_3_3,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_3_4,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_4_4,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_4_5,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_4_6,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_4_11,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_5,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_6,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_10,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_16,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_20,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_5_26,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_6,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_7,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_15,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_20,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_22,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_35,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_42,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_50,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_6_57,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_7_7,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_7_8,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_7_21,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_7_35,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_7_120,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_8,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_9,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_28,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_56,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_70,jclqdxfodd);
	 		oddConverterMap.put(PlayType.jclq_dxf_8_247,jclqdxfodd);
	 		 
	 		oddConverterMap.put(PlayType.jczq_mix_2_1,jczqhhdd);
	 		oddConverterMap.put(PlayType.jczq_mix_3_1,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_4_1,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_1,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_1,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_7_1,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_1,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_3_3,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_3_4,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_4_4,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_4_5,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_4_6,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_4_11,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_5,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_6,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_10,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_16,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_20,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_5_26,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_6,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_7,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_15,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_20,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_22,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_35,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_42,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_50,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_6_57,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_7_7,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_7_8,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_7_21,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_7_35,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_7_120,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_8,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_9,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_28,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_56,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_70,jczqhhdd);
	  		oddConverterMap.put(PlayType.jczq_mix_8_247,jczqhhdd);
	 		

	  		oddConverterMap.put(PlayType.jclq_mix_2_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_3_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_4_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_7_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_1,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_3_3,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_3_4,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_4_4,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_4_5,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_4_6,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_4_11,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_5,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_6,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_10,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_16,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_20,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_5_26,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_6,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_7,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_15,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_20,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_22,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_35,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_42,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_50,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_6_57,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_7_7,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_7_8,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_7_21,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_7_35,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_7_120,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_8,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_9,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_28,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_56,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_70,jclqhhodd);
	 		oddConverterMap.put(PlayType.jclq_mix_8_247,jclqhhodd);
	}
	                
	
	public static String decare(String code1,String code2) {
		StringBuilder decarestr = new StringBuilder();
		for(char c1:code1.toCharArray()) {
			for(char c2:code2.toCharArray()) {
				decarestr.append(c1).append(c2).append(",");
			}
		}
		decarestr = decarestr.deleteCharAt(decarestr.length()-1);
		return decarestr.toString();
	}
	
}
