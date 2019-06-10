package com.lottery.ticket.vender.impl.ruiying;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class RuiYingLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	protected static Map<String,String> toLotteryTypeMap = new HashMap<String,String>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	public static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	   /** 玩法转换*/
    public static Map<Integer, String> playMap = new HashMap<Integer, String>();
	/** 玩法编码转换*/
	public static Map<Integer, String> playCodeMap = new HashMap<Integer, String>();
	public static Map<String, Integer> playTypeMapJc = new HashMap<String, Integer>();
	/** 票内容转换 */
	protected static Map<PlayType, ITicketContentConverter> ticketContentConverterMap = new HashMap<PlayType, ITicketContentConverter>();
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();


	static {
		// 彩种转换设置
		lotteryTypeMap.put(LotteryType.PL5, "110035");
		lotteryTypeMap.put(LotteryType.QXC, "110022");
		lotteryTypeMap.put(LotteryType.PL3, "110033");
		lotteryTypeMap.put(LotteryType.CJDLT, "120029");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "130042");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "130042");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "130042");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "130042");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ, "130042");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG, "130042");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF, "130043"); 
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF, "130043");
	    lotteryTypeMap.put(LotteryType.JCLQ_SFC, "130043");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF, "130043"); 
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG, "130043");
		/**
		 * 彩期转换
		 * FIXME 暂不对彩期进行转换
		 */
		IPhaseConverter phaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "20" + phase;
			}
		};
		
		IPhaseConverter jcphaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "0";
			}
		};

		phaseConverterMap.put(LotteryType.CJDLT, phaseConverter);
		phaseConverterMap.put(LotteryType.PL5, phaseConverter);
		phaseConverterMap.put(LotteryType.QXC, phaseConverter);
		phaseConverterMap.put(LotteryType.PL3, phaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_RQ_SPF, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_BF, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_JQS, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_BQC, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_SPF_WRQ, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_HHGG, jcphaseConverter);
		
		phaseConverterMap.put(LotteryType.JCLQ_SF, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_RFSF, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_SFC, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_DXF, jcphaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_HHGG, jcphaseConverter);
		
		toLotteryTypeMap.put("3010", "SPF");
		toLotteryTypeMap.put("3007", "CBF");
		toLotteryTypeMap.put("3009", "BQC");
		toLotteryTypeMap.put("3008", "JQS");
		toLotteryTypeMap.put("3006", "RSP");
		
		toLotteryTypeMap.put("3001", "SFC");
		toLotteryTypeMap.put("3002", "RSF");
		toLotteryTypeMap.put("3003", "SFD");
		toLotteryTypeMap.put("3004", "DXF");
		
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "Z1");
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "Z3");
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "Z6");
		
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "Z1");
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "F3");
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "F6");
		
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "S1");
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "S3");
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "S6");
		
		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(), "SFC");
		
		
		playTypeMap.put(PlayType.jclq_sf_2_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_3_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_6_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_7_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_8_1_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_3_3_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_3_4_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_4_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_5_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_6_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_4_11_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_5_dan.getValue(), "SFC");
		playTypeMap.put(PlayType.jclq_sf_5_6_dan.getValue(),"SFC");             
		playTypeMap.put(PlayType.jclq_sf_5_10_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_5_16_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_5_20_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_5_26_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_6_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_7_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_15_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_20_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_22_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_35_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_42_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_50_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_6_57_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_7_7_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_7_8_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_7_21_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_7_35_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_7_120_dan.getValue(),"SFC");             
		playTypeMap.put(PlayType.jclq_sf_8_8_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_8_9_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_8_28_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_8_56_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_8_70_dan.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sf_8_247_dan.getValue(),"SFC");             
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"RSF");
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_2_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_1_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_3_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_4_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_4_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_5_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_6_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_11_dan.getValue(),"RSF");           
		playTypeMap.put(PlayType.jclq_rfsf_5_5_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_6_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_10_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_16_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_20_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_26_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_6_dan.getValue(),"RSF");           
		playTypeMap.put(PlayType.jclq_rfsf_6_7_dan.getValue(),"RSF");           
		playTypeMap.put(PlayType.jclq_rfsf_6_15_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_20_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_22_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_35_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_42_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_50_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_57_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_7_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_8_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_21_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_35_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_120_dan.getValue(),"RSF");           
		playTypeMap.put(PlayType.jclq_rfsf_8_8_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_9_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_28_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_56_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_70_dan.getValue(),"RSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_247_dan.getValue(),"RSF");           
	
		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"SFD");
		
		
		playTypeMap.put(PlayType.jclq_sfc_1_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_2_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_3_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_5_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_6_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_7_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_8_1_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_3_3_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_3_4_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_4_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_5_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_6_dan.getValue(),"SFD");
		playTypeMap.put(PlayType.jclq_sfc_4_11_dan.getValue(),"SFD");
    
		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"DXF");
		
		
		
		playTypeMap.put(PlayType.jclq_dxf_1_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_2_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_3_1_dan.getValue(),"DXF");
	    playTypeMap.put(PlayType.jclq_dxf_4_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_1_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_3_3_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_3_4_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_4_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_5_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_6_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_4_11_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_5_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_6_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_10_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_16_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_20_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_5_26_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_6_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_7_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_15_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_20_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_22_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_35_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_42_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_50_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_6_57_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_7_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_8_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_21_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_35_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_7_120_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_8_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_9_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_28_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_56_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_70_dan.getValue(),"DXF");
		playTypeMap.put(PlayType.jclq_dxf_8_247_dan.getValue(),"DXF");

		playTypeMap.put(PlayType.jclq_mix_1_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"LHH");
	
		playTypeMap.put(PlayType.jclq_mix_2_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_3_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_1_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_3_3_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_3_4_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_4_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_5_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_6_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_4_11_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_5_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_6_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_10_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_16_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_20_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_5_26_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_6_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_7_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_15_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_20_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_22_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_35_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_42_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_50_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_6_57_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_7_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_8_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_21_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_35_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_7_120_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_8_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_9_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_28_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_56_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_70_dan.getValue(),"LHH");
		playTypeMap.put(PlayType.jclq_mix_8_247_dan.getValue(),"LHH");

		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"RSP");
		
		playTypeMap.put(PlayType.jczq_spf_2_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_3_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_1_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_3_3_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_3_4_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_4_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_5_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_6_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_4_11_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_5_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_6_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_10_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_16_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_20_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_5_26_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_6_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_7_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_15_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_20_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_22_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_35_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_42_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_50_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_6_57_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_7_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_8_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_21_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_35_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_7_120_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_8_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_9_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_28_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_56_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_70_dan.getValue(),"RSP");
		playTypeMap.put(PlayType.jczq_spf_8_247_dan.getValue(),"RSP");
		
		playTypeMap.put(PlayType.jczq_bf_1_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_2_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_3_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_5_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_6_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_7_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_8_1.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_3_3.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_3_4.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_4.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_5.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_6.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_11.getValue(),"CBF");
		
		
		playTypeMap.put(PlayType.jczq_bf_2_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_3_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_5_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_6_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_7_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_8_1_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_3_3_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_3_4_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_4_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_5_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_6_dan.getValue(),"CBF");
		playTypeMap.put(PlayType.jczq_bf_4_11_dan.getValue(),"CBF");
		
		playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(),"JQS");
	
		playTypeMap.put(PlayType.jczq_jqs_2_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_3_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_7_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_8_1_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_3_3_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_3_4_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_4_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_5_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_6_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_4_11_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_5_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_6_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_10_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_16_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_20_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_5_26_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_6_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_7_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_15_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_20_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_22_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_35_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_42_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_50_dan.getValue(),"JQS");
		playTypeMap.put(PlayType.jczq_jqs_6_57_dan.getValue(),"JQS");
	
		playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(),"BQC");
		
		
		playTypeMap.put(PlayType.jczq_bqc_2_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_3_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_5_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_6_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_7_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_8_1_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_3_3_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_3_4_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_4_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_5_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_6_dan.getValue(),"BQC");
		playTypeMap.put(PlayType.jczq_bqc_4_11_dan.getValue(),"BQC");

		playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"SPF");
		
		playTypeMap.put(PlayType.jczq_spfwrq_2_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70_dan.getValue(),"SPF");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247_dan.getValue(),"SPF");
		
		playTypeMap.put(PlayType.jczq_mix_1_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"ZHH");
	
		playTypeMap.put(PlayType.jczq_mix_2_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_3_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_1_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_3_3_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_3_4_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_4_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_5_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_6_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_4_11_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_5_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_6_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_10_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_16_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_20_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_5_26_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_6_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_7_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_15_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_20_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_22_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_35_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_42_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_50_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_6_57_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_7_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_8_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_21_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_35_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_7_120_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_8_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_9_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_28_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_56_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_70_dan.getValue(),"ZHH");
		playTypeMap.put(PlayType.jczq_mix_8_247_dan.getValue(),"ZHH");
		
		
		//竞彩串法
		playMap.put(PlayType.jczq_spf_1_1.getValue(),"1*1");
		playMap.put(PlayType.jczq_spf_2_1.getValue(),"2*1");
		playMap.put(PlayType.jczq_spf_3_1.getValue(),"3*1");
		playMap.put(PlayType.jczq_spf_4_1.getValue(),"4*1");
		playMap.put(PlayType.jczq_spf_5_1.getValue(),"5*1");
		playMap.put(PlayType.jczq_spf_6_1.getValue(),"6*1");
		playMap.put(PlayType.jczq_spf_7_1.getValue(),"7*1");
		playMap.put(PlayType.jczq_spf_8_1.getValue(),"8*1");
		playMap.put(PlayType.jczq_spf_3_3.getValue(),"3*3");
		playMap.put(PlayType.jczq_spf_3_4.getValue(),"3*4");
		playMap.put(PlayType.jczq_spf_4_4.getValue(),"4*4");
		playMap.put(PlayType.jczq_spf_4_5.getValue(),"4*5");
		playMap.put(PlayType.jczq_spf_4_6.getValue(),"4*6");
		playMap.put(PlayType.jczq_spf_4_11.getValue(),"4*11");
		playMap.put(PlayType.jczq_spf_5_5.getValue(),"5*5"); 
		playMap.put(PlayType.jczq_spf_5_6.getValue(),"5*6"); 
		playMap.put(PlayType.jczq_spf_5_10.getValue(),"5*10");  
		playMap.put(PlayType.jczq_spf_5_16.getValue(),"5*16");
		playMap.put(PlayType.jczq_spf_5_20.getValue(),"5*20");
		playMap.put(PlayType.jczq_spf_5_26.getValue(),"5*26");
		playMap.put(PlayType.jczq_spf_6_6.getValue(),"6*6");
		playMap.put(PlayType.jczq_spf_6_7.getValue(),"6*7");
		playMap.put(PlayType.jczq_spf_6_15.getValue(),"6*15");
		playMap.put(PlayType.jczq_spf_6_20.getValue(),"6*20");
		playMap.put(PlayType.jczq_spf_6_22.getValue(),"6*22");
		playMap.put(PlayType.jczq_spf_6_35.getValue(),"6*35");
		playMap.put(PlayType.jczq_spf_6_42.getValue(),"6*42");
		playMap.put(PlayType.jczq_spf_6_50.getValue(),"6*50");
		playMap.put(PlayType.jczq_spf_6_57.getValue(),"6*57");
		playMap.put(PlayType.jczq_spf_7_7.getValue(),"7*7");
		playMap.put(PlayType.jczq_spf_7_8.getValue(),"7*8");
		playMap.put(PlayType.jczq_spf_7_21.getValue(),"7*21");
		playMap.put(PlayType.jczq_spf_7_35.getValue(),"7*35");
		playMap.put(PlayType.jczq_spf_7_120.getValue(),"7*120");  
		playMap.put(PlayType.jczq_spf_8_8.getValue(),"8*8");
		playMap.put(PlayType.jczq_spf_8_9.getValue(),"8*9");
		playMap.put(PlayType.jczq_spf_8_28.getValue(),"8*28");
		playMap.put(PlayType.jczq_spf_8_56.getValue(),"8*56");
		playMap.put(PlayType.jczq_spf_8_70.getValue(),"8*70");
		playMap.put(PlayType.jczq_spf_8_247.getValue(),"8*247"); 				 
		
		
		playMap.put(PlayType.jczq_spf_2_1_dan.getValue(),"2*1");
		playMap.put(PlayType.jczq_spf_3_1_dan.getValue(),"3*1");
		playMap.put(PlayType.jczq_spf_4_1_dan.getValue(),"4*1");
		playMap.put(PlayType.jczq_spf_5_1_dan.getValue(),"5*1");
		playMap.put(PlayType.jczq_spf_6_1_dan.getValue(),"6*1");
		playMap.put(PlayType.jczq_spf_7_1_dan.getValue(),"7*1");
		playMap.put(PlayType.jczq_spf_8_1_dan.getValue(),"8*1");
		playMap.put(PlayType.jczq_spf_3_3_dan.getValue(),"3*3");
		playMap.put(PlayType.jczq_spf_3_4_dan.getValue(),"3*4");
		playMap.put(PlayType.jczq_spf_4_4_dan.getValue(),"4*4");
		playMap.put(PlayType.jczq_spf_4_5_dan.getValue(),"4*5");
		playMap.put(PlayType.jczq_spf_4_6_dan.getValue(),"4*6");
		playMap.put(PlayType.jczq_spf_4_11_dan.getValue(),"4*11");
		playMap.put(PlayType.jczq_spf_5_5_dan.getValue(),"5*5");
		playMap.put(PlayType.jczq_spf_5_6_dan.getValue(),"5*6");
		playMap.put(PlayType.jczq_spf_5_10_dan.getValue(),"5*10");
		playMap.put(PlayType.jczq_spf_5_16_dan.getValue(),"5*16");
		playMap.put(PlayType.jczq_spf_5_20_dan.getValue(),"5*20");
		playMap.put(PlayType.jczq_spf_5_26_dan.getValue(),"5*26");
		playMap.put(PlayType.jczq_spf_6_6_dan.getValue(),"6*6");
		playMap.put(PlayType.jczq_spf_6_7_dan.getValue(),"6*7");
		playMap.put(PlayType.jczq_spf_6_15_dan.getValue(),"6*15");
		playMap.put(PlayType.jczq_spf_6_20_dan.getValue(),"6*20");
		playMap.put(PlayType.jczq_spf_6_22_dan.getValue(),"6*22");
		playMap.put(PlayType.jczq_spf_6_35_dan.getValue(),"6*35");
		playMap.put(PlayType.jczq_spf_6_42_dan.getValue(),"6*42");
		playMap.put(PlayType.jczq_spf_6_50_dan.getValue(),"6*50");
		playMap.put(PlayType.jczq_spf_6_57_dan.getValue(),"6*57");
		playMap.put(PlayType.jczq_spf_7_7_dan.getValue(),"7*7");
		playMap.put(PlayType.jczq_spf_7_8_dan.getValue(),"7*8");
		playMap.put(PlayType.jczq_spf_7_21_dan.getValue(),"7*21");
		playMap.put(PlayType.jczq_spf_7_35_dan.getValue(),"7*35");
		playMap.put(PlayType.jczq_spf_7_120_dan.getValue(),"7*120");
		playMap.put(PlayType.jczq_spf_8_8_dan.getValue(),"8*8");
		playMap.put(PlayType.jczq_spf_8_9_dan.getValue(),"8*9");
		playMap.put(PlayType.jczq_spf_8_28_dan.getValue(),"8*28");
		playMap.put(PlayType.jczq_spf_8_56_dan.getValue(),"8*56");
		playMap.put(PlayType.jczq_spf_8_70_dan.getValue(),"8*70");
		playMap.put(PlayType.jczq_spf_8_247_dan.getValue(),"8*247");
		
		playMap.put(PlayType.jczq_bf_1_1.getValue(),"1*1");
		playMap.put(PlayType.jczq_bf_2_1.getValue(),"2*1");
		playMap.put(PlayType.jczq_bf_3_1.getValue(),"3*1");
		playMap.put(PlayType.jczq_bf_4_1.getValue(),"4*1");
		playMap.put(PlayType.jczq_bf_5_1.getValue(),"5*1");
		playMap.put(PlayType.jczq_bf_6_1.getValue(),"6*1");
		playMap.put(PlayType.jczq_bf_7_1.getValue(),"7*1");
		playMap.put(PlayType.jczq_bf_8_1.getValue(),"8*1");
		playMap.put(PlayType.jczq_bf_3_3.getValue(),"3*3");
		playMap.put(PlayType.jczq_bf_3_4.getValue(),"3*4");
		playMap.put(PlayType.jczq_bf_4_4.getValue(),"4*4");
		playMap.put(PlayType.jczq_bf_4_5.getValue(),"4*5");
		playMap.put(PlayType.jczq_bf_4_6.getValue(),"4*6");
		playMap.put(PlayType.jczq_bf_4_11.getValue(),"4*11");
		
		
		playMap.put(PlayType.jczq_bf_2_1_dan.getValue(),"2*1");
		playMap.put(PlayType.jczq_bf_3_1_dan.getValue(),"3*1");
		playMap.put(PlayType.jczq_bf_4_1_dan.getValue(),"4*1");
		playMap.put(PlayType.jczq_bf_5_1_dan.getValue(),"5*1");
		playMap.put(PlayType.jczq_bf_6_1_dan.getValue(),"6*1");
		playMap.put(PlayType.jczq_bf_7_1_dan.getValue(),"7*1");
		playMap.put(PlayType.jczq_bf_8_1_dan.getValue(),"8*1");
		playMap.put(PlayType.jczq_bf_3_3_dan.getValue(),"3*3");
		playMap.put(PlayType.jczq_bf_3_4_dan.getValue(),"3*4");
		playMap.put(PlayType.jczq_bf_4_4_dan.getValue(),"4*4");
		playMap.put(PlayType.jczq_bf_4_5_dan.getValue(),"4*5");
		playMap.put(PlayType.jczq_bf_4_6_dan.getValue(),"4*6");
		playMap.put(PlayType.jczq_bf_4_11_dan.getValue(),"4*11");
		
		playMap.put(PlayType.jczq_jqs_1_1.getValue(),"1*1");
		playMap.put(PlayType.jczq_jqs_2_1.getValue(),"2*1");
		playMap.put(PlayType.jczq_jqs_3_1.getValue(),"3*1");
		playMap.put(PlayType.jczq_jqs_4_1.getValue(),"4*1");
		playMap.put(PlayType.jczq_jqs_5_1.getValue(),"5*1");
		playMap.put(PlayType.jczq_jqs_6_1.getValue(),"6*1");
		playMap.put(PlayType.jczq_jqs_7_1.getValue(),"7*1");
		playMap.put(PlayType.jczq_jqs_8_1.getValue(),"8*1");
		playMap.put(PlayType.jczq_jqs_3_3.getValue(),"3*3");
		playMap.put(PlayType.jczq_jqs_3_4.getValue(),"3*4");
		playMap.put(PlayType.jczq_jqs_4_4.getValue(),"4*4");
		playMap.put(PlayType.jczq_jqs_4_5.getValue(),"4*5");
		playMap.put(PlayType.jczq_jqs_4_6.getValue(),"4*6");
		playMap.put(PlayType.jczq_jqs_4_11.getValue(),"4*11");
		playMap.put(PlayType.jczq_jqs_5_5.getValue(),"5*5");
		playMap.put(PlayType.jczq_jqs_5_6.getValue(),"5*6");
		playMap.put(PlayType.jczq_jqs_5_10.getValue(),"5*10");
		playMap.put(PlayType.jczq_jqs_5_16.getValue(),"5*16");
		playMap.put(PlayType.jczq_jqs_5_20.getValue(),"5*20");
		playMap.put(PlayType.jczq_jqs_5_26.getValue(),"5*26");
		playMap.put(PlayType.jczq_jqs_6_6.getValue(),"6*6");
		playMap.put(PlayType.jczq_jqs_6_7.getValue(),"6*7");
		playMap.put(PlayType.jczq_jqs_6_15.getValue(),"6*15");
		playMap.put(PlayType.jczq_jqs_6_20.getValue(),"6*20");
		playMap.put(PlayType.jczq_jqs_6_22.getValue(),"6*22");
		playMap.put(PlayType.jczq_jqs_6_35.getValue(),"6*35");
		playMap.put(PlayType.jczq_jqs_6_42.getValue(),"6*42");
		playMap.put(PlayType.jczq_jqs_6_50.getValue(),"6*50");
		playMap.put(PlayType.jczq_jqs_6_57.getValue(),"6*57");
	
		playMap.put(PlayType.jczq_jqs_2_1_dan.getValue(),"2*1");
		playMap.put(PlayType.jczq_jqs_3_1_dan.getValue(),"3*1");
		playMap.put(PlayType.jczq_jqs_4_1_dan.getValue(),"4*1");
		playMap.put(PlayType.jczq_jqs_5_1_dan.getValue(),"5*1");
		playMap.put(PlayType.jczq_jqs_6_1_dan.getValue(),"6*1");
		playMap.put(PlayType.jczq_jqs_7_1_dan.getValue(),"7*1");
		playMap.put(PlayType.jczq_jqs_8_1_dan.getValue(),"8*1");
		playMap.put(PlayType.jczq_jqs_3_3_dan.getValue(),"3*3");
		playMap.put(PlayType.jczq_jqs_3_4_dan.getValue(),"3*4");
		playMap.put(PlayType.jczq_jqs_4_4_dan.getValue(),"4*4");
		playMap.put(PlayType.jczq_jqs_4_5_dan.getValue(),"4*5");
		playMap.put(PlayType.jczq_jqs_4_6_dan.getValue(),"4*6");
		playMap.put(PlayType.jczq_jqs_4_11_dan.getValue(),"4*11");
		playMap.put(PlayType.jczq_jqs_5_5_dan.getValue(),"5*5");
		playMap.put(PlayType.jczq_jqs_5_6_dan.getValue(),"5*6");
		playMap.put(PlayType.jczq_jqs_5_10_dan.getValue(),"5*10");
		playMap.put(PlayType.jczq_jqs_5_16_dan.getValue(),"5*16");
		playMap.put(PlayType.jczq_jqs_5_20_dan.getValue(),"5*20");
		playMap.put(PlayType.jczq_jqs_5_26_dan.getValue(),"5*26");
		playMap.put(PlayType.jczq_jqs_6_6_dan.getValue(),"6*6");
		playMap.put(PlayType.jczq_jqs_6_7_dan.getValue(),"6*7");
		playMap.put(PlayType.jczq_jqs_6_15_dan.getValue(),"6*15");
		playMap.put(PlayType.jczq_jqs_6_20_dan.getValue(),"6*20");
		playMap.put(PlayType.jczq_jqs_6_22_dan.getValue(),"6*22");
		playMap.put(PlayType.jczq_jqs_6_35_dan.getValue(),"6*35");
		playMap.put(PlayType.jczq_jqs_6_42_dan.getValue(),"6*42");
		playMap.put(PlayType.jczq_jqs_6_50_dan.getValue(),"6*50");
		playMap.put(PlayType.jczq_jqs_6_57_dan.getValue(),"6*57");
	
		playMap.put(PlayType.jczq_bqc_1_1.getValue(),"1*1");
		playMap.put(PlayType.jczq_bqc_2_1.getValue(),"2*1");
		playMap.put(PlayType.jczq_bqc_3_1.getValue(),"3*1");
		playMap.put(PlayType.jczq_bqc_4_1.getValue(),"4*1");
		playMap.put(PlayType.jczq_bqc_5_1.getValue(),"5*1");
		playMap.put(PlayType.jczq_bqc_6_1.getValue(),"6*1");
		playMap.put(PlayType.jczq_bqc_7_1.getValue(),"7*1");
		playMap.put(PlayType.jczq_bqc_8_1.getValue(),"8*1");
		playMap.put(PlayType.jczq_bqc_3_3.getValue(),"3*3");
		playMap.put(PlayType.jczq_bqc_3_4.getValue(),"3*4");
		playMap.put(PlayType.jczq_bqc_4_4.getValue(),"4*4");
		playMap.put(PlayType.jczq_bqc_4_5.getValue(),"4*5");
		playMap.put(PlayType.jczq_bqc_4_6.getValue(),"4*6");
		playMap.put(PlayType.jczq_bqc_4_11.getValue(),"4*11");
		
		
		playMap.put(PlayType.jczq_bqc_2_1_dan.getValue(),"2*1");
		playMap.put(PlayType.jczq_bqc_3_1_dan.getValue(),"3*1");
		playMap.put(PlayType.jczq_bqc_4_1_dan.getValue(),"4*1");
		playMap.put(PlayType.jczq_bqc_5_1_dan.getValue(),"5*1");
		playMap.put(PlayType.jczq_bqc_6_1_dan.getValue(),"6*1");
		playMap.put(PlayType.jczq_bqc_7_1_dan.getValue(),"7*1");
		playMap.put(PlayType.jczq_bqc_8_1_dan.getValue(),"8*1");
		playMap.put(PlayType.jczq_bqc_3_3_dan.getValue(),"3*3");
		playMap.put(PlayType.jczq_bqc_3_4_dan.getValue(),"3*4");
		playMap.put(PlayType.jczq_bqc_4_4_dan.getValue(),"4*4");
		playMap.put(PlayType.jczq_bqc_4_5_dan.getValue(),"4*5");
		playMap.put(PlayType.jczq_bqc_4_6_dan.getValue(),"4*6");
		playMap.put(PlayType.jczq_bqc_4_11_dan.getValue(),"4*11");

		playMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"1*1");
		playMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"2*1");
		playMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"3*1");
		playMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"4*1");
		playMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"5*1");
		playMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"6*1");
		playMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"7*1");
		playMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"8*1");
		playMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"3*3");
		playMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"3*4");
		playMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"4*4");
		playMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"4*5");
		playMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"4*6");
		playMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"4*11");
		playMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"5*5");
		playMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"5*6");
		playMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"5*10");
		playMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"5*16");
		playMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"5*20");
		playMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"5*26");
		playMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"6*6");
		playMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"6*7");
		playMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"6*15");
		playMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"6*20");
		playMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"6*22");
		playMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"6*35");
		playMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"6*42");
		playMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"6*50");
		playMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"6*57");
		playMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"7*7");
		playMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"7*8");
		playMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"7*21");
		playMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"7*35");
		playMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"7*120");
		playMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"8*8");
		playMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"8*9");
		playMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"8*28");
		playMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"8*56");
		playMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"8*70");
		playMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"8*247");
		
		playMap.put(PlayType.jczq_spfwrq_2_1_dan.getValue(),"2*1");
		playMap.put(PlayType.jczq_spfwrq_3_1_dan.getValue(),"3*1");
		playMap.put(PlayType.jczq_spfwrq_4_1_dan.getValue(),"4*1");
		playMap.put(PlayType.jczq_spfwrq_5_1_dan.getValue(),"5*1");
		playMap.put(PlayType.jczq_spfwrq_6_1_dan.getValue(),"6*1");
		playMap.put(PlayType.jczq_spfwrq_7_1_dan.getValue(),"7*1");
		playMap.put(PlayType.jczq_spfwrq_8_1_dan.getValue(),"8*1");
		playMap.put(PlayType.jczq_spfwrq_3_3_dan.getValue(),"3*3");
		playMap.put(PlayType.jczq_spfwrq_3_4_dan.getValue(),"3*4");
		playMap.put(PlayType.jczq_spfwrq_4_4_dan.getValue(),"4*4");
		playMap.put(PlayType.jczq_spfwrq_4_5_dan.getValue(),"4*5");
		playMap.put(PlayType.jczq_spfwrq_4_6_dan.getValue(),"4*6");
		playMap.put(PlayType.jczq_spfwrq_4_11_dan.getValue(),"4*11");
		playMap.put(PlayType.jczq_spfwrq_5_5_dan.getValue(),"5*5");
		playMap.put(PlayType.jczq_spfwrq_5_6_dan.getValue(),"5*6");
		playMap.put(PlayType.jczq_spfwrq_5_10_dan.getValue(),"5*10");
		playMap.put(PlayType.jczq_spfwrq_5_16_dan.getValue(),"5*16");
		playMap.put(PlayType.jczq_spfwrq_5_20_dan.getValue(),"5*20");
		playMap.put(PlayType.jczq_spfwrq_5_26_dan.getValue(),"5*26");
		playMap.put(PlayType.jczq_spfwrq_6_6_dan.getValue(),"6*6");
		playMap.put(PlayType.jczq_spfwrq_6_7_dan.getValue(),"6*7");
		playMap.put(PlayType.jczq_spfwrq_6_15_dan.getValue(),"6*15");
		playMap.put(PlayType.jczq_spfwrq_6_20_dan.getValue(),"6*20");
		playMap.put(PlayType.jczq_spfwrq_6_22_dan.getValue(),"6*22");
		playMap.put(PlayType.jczq_spfwrq_6_35_dan.getValue(),"6*35");
		playMap.put(PlayType.jczq_spfwrq_6_42_dan.getValue(),"6*42");
		playMap.put(PlayType.jczq_spfwrq_6_50_dan.getValue(),"6*50");
		playMap.put(PlayType.jczq_spfwrq_6_57_dan.getValue(),"6*57");
		playMap.put(PlayType.jczq_spfwrq_7_7_dan.getValue(),"7*7");
		playMap.put(PlayType.jczq_spfwrq_7_8_dan.getValue(),"7*8");
		playMap.put(PlayType.jczq_spfwrq_7_21_dan.getValue(),"7*21");
		playMap.put(PlayType.jczq_spfwrq_7_35_dan.getValue(),"7*35");
		playMap.put(PlayType.jczq_spfwrq_7_120_dan.getValue(),"7*120");
		playMap.put(PlayType.jczq_spfwrq_8_8_dan.getValue(),"8*8");
		playMap.put(PlayType.jczq_spfwrq_8_9_dan.getValue(),"8*9");
		playMap.put(PlayType.jczq_spfwrq_8_28_dan.getValue(),"8*28");
		playMap.put(PlayType.jczq_spfwrq_8_56_dan.getValue(),"8*56");
		playMap.put(PlayType.jczq_spfwrq_8_70_dan.getValue(),"8*70");
		playMap.put(PlayType.jczq_spfwrq_8_247_dan.getValue(),"8*247");
		
		playMap.put(PlayType.jczq_mix_1_1.getValue(),"1*1");
		playMap.put(PlayType.jczq_mix_2_1.getValue(),"2*1");
		playMap.put(PlayType.jczq_mix_3_1.getValue(),"3*1");
		playMap.put(PlayType.jczq_mix_4_1.getValue(),"4*1");
		playMap.put(PlayType.jczq_mix_5_1.getValue(),"5*1");
		playMap.put(PlayType.jczq_mix_6_1.getValue(),"6*1");
		playMap.put(PlayType.jczq_mix_7_1.getValue(),"7*1");
		playMap.put(PlayType.jczq_mix_8_1.getValue(),"8*1");
		playMap.put(PlayType.jczq_mix_3_3.getValue(),"3*3");
		playMap.put(PlayType.jczq_mix_3_4.getValue(),"3*4");
		playMap.put(PlayType.jczq_mix_4_4.getValue(),"4*4");
		playMap.put(PlayType.jczq_mix_4_5.getValue(),"4*5");
		playMap.put(PlayType.jczq_mix_4_6.getValue(),"4*6");
		playMap.put(PlayType.jczq_mix_4_11.getValue(),"4*11");
		playMap.put(PlayType.jczq_mix_5_5.getValue(),"5*5");
		playMap.put(PlayType.jczq_mix_5_6.getValue(),"5*6");
		playMap.put(PlayType.jczq_mix_5_10.getValue(),"5*10");
		playMap.put(PlayType.jczq_mix_5_16.getValue(),"5*16");
		playMap.put(PlayType.jczq_mix_5_20.getValue(),"5*20");
		playMap.put(PlayType.jczq_mix_5_26.getValue(),"5*26");
		playMap.put(PlayType.jczq_mix_6_6.getValue(),"6*6");
		playMap.put(PlayType.jczq_mix_6_7.getValue(),"6*7");
		playMap.put(PlayType.jczq_mix_6_15.getValue(),"6*15");
		playMap.put(PlayType.jczq_mix_6_20.getValue(),"6*20");
		playMap.put(PlayType.jczq_mix_6_22.getValue(),"6*22");
		playMap.put(PlayType.jczq_mix_6_35.getValue(),"6*35");
		playMap.put(PlayType.jczq_mix_6_42.getValue(),"6*42");
		playMap.put(PlayType.jczq_mix_6_50.getValue(),"6*50");
		playMap.put(PlayType.jczq_mix_6_57.getValue(),"6*57");
		playMap.put(PlayType.jczq_mix_7_7.getValue(),"7*7");
		playMap.put(PlayType.jczq_mix_7_8.getValue(),"7*8");
		playMap.put(PlayType.jczq_mix_7_21.getValue(),"7*21");
		playMap.put(PlayType.jczq_mix_7_35.getValue(),"7*35");
		playMap.put(PlayType.jczq_mix_7_120.getValue(),"7*120");
		playMap.put(PlayType.jczq_mix_8_8.getValue(),"8*8");
		playMap.put(PlayType.jczq_mix_8_9.getValue(),"8*9");
		playMap.put(PlayType.jczq_mix_8_28.getValue(),"8*28");
		playMap.put(PlayType.jczq_mix_8_56.getValue(),"8*56");
		playMap.put(PlayType.jczq_mix_8_70.getValue(),"8*70");
		playMap.put(PlayType.jczq_mix_8_247.getValue(),"8*247");
	
		playMap.put(PlayType.jczq_mix_2_1_dan.getValue(),"2*1");
		playMap.put(PlayType.jczq_mix_3_1_dan.getValue(),"3*1");
		playMap.put(PlayType.jczq_mix_4_1_dan.getValue(),"4*1");
		playMap.put(PlayType.jczq_mix_5_1_dan.getValue(),"5*1");
		playMap.put(PlayType.jczq_mix_6_1_dan.getValue(),"6*1");
		playMap.put(PlayType.jczq_mix_7_1_dan.getValue(),"7*1");
		playMap.put(PlayType.jczq_mix_8_1_dan.getValue(),"8*1");
		playMap.put(PlayType.jczq_mix_3_3_dan.getValue(),"3*3");
		playMap.put(PlayType.jczq_mix_3_4_dan.getValue(),"3*4");
		playMap.put(PlayType.jczq_mix_4_4_dan.getValue(),"4*4");
		playMap.put(PlayType.jczq_mix_4_5_dan.getValue(),"4*5");
		playMap.put(PlayType.jczq_mix_4_6_dan.getValue(),"4*6");
		playMap.put(PlayType.jczq_mix_4_11_dan.getValue(),"4*11");
		playMap.put(PlayType.jczq_mix_5_5_dan.getValue(),"5*5");
		playMap.put(PlayType.jczq_mix_5_6_dan.getValue(),"5*6");
		playMap.put(PlayType.jczq_mix_5_10_dan.getValue(),"5*10");
		playMap.put(PlayType.jczq_mix_5_16_dan.getValue(),"5*16");
		playMap.put(PlayType.jczq_mix_5_20_dan.getValue(),"5*20");
		playMap.put(PlayType.jczq_mix_5_26_dan.getValue(),"5*26");
		playMap.put(PlayType.jczq_mix_6_6_dan.getValue(),"6*6");
		playMap.put(PlayType.jczq_mix_6_7_dan.getValue(),"6*7");
		playMap.put(PlayType.jczq_mix_6_15_dan.getValue(),"6*15");
		playMap.put(PlayType.jczq_mix_6_20_dan.getValue(),"6*20");
		playMap.put(PlayType.jczq_mix_6_22_dan.getValue(),"6*22");
		playMap.put(PlayType.jczq_mix_6_35_dan.getValue(),"6*35");
		playMap.put(PlayType.jczq_mix_6_42_dan.getValue(),"6*42");
		playMap.put(PlayType.jczq_mix_6_50_dan.getValue(),"6*50");
		playMap.put(PlayType.jczq_mix_6_57_dan.getValue(),"6*57");
		playMap.put(PlayType.jczq_mix_7_7_dan.getValue(),"7*7");
		playMap.put(PlayType.jczq_mix_7_8_dan.getValue(),"7*8");
		playMap.put(PlayType.jczq_mix_7_21_dan.getValue(),"7*21");
		playMap.put(PlayType.jczq_mix_7_35_dan.getValue(),"7*35");
		playMap.put(PlayType.jczq_mix_7_120_dan.getValue(),"7*120");
		playMap.put(PlayType.jczq_mix_8_8_dan.getValue(),"8*8");
		playMap.put(PlayType.jczq_mix_8_9_dan.getValue(),"8*9");
		playMap.put(PlayType.jczq_mix_8_28_dan.getValue(),"8*28");
		playMap.put(PlayType.jczq_mix_8_56_dan.getValue(),"8*56");
		playMap.put(PlayType.jczq_mix_8_70_dan.getValue(),"8*70");
		playMap.put(PlayType.jczq_mix_8_247_dan.getValue(),"8*247");
		
		
		  playMap.put(PlayType.jclq_sf_1_1.getValue(), "1*1");
			playMap.put(PlayType.jclq_sf_2_1.getValue(), "2*1");
			playMap.put(PlayType.jclq_sf_3_1.getValue(), "3*1");
			playMap.put(PlayType.jclq_sf_4_1.getValue(), "4*1");
			playMap.put(PlayType.jclq_sf_5_1.getValue(), "5*1");
			playMap.put(PlayType.jclq_sf_6_1.getValue(), "6*1");
			playMap.put(PlayType.jclq_sf_7_1.getValue(), "7*1");
			playMap.put(PlayType.jclq_sf_8_1.getValue(), "8*1");
			playMap.put(PlayType.jclq_sf_3_3.getValue(), "3*3");
			playMap.put(PlayType.jclq_sf_3_4.getValue(), "3*4");
			playMap.put(PlayType.jclq_sf_4_4.getValue(), "4*4");
			playMap.put(PlayType.jclq_sf_4_5.getValue(), "4*5");
			playMap.put(PlayType.jclq_sf_4_6.getValue(), "4*6");
			playMap.put(PlayType.jclq_sf_4_11.getValue(), "4*11");
			playMap.put(PlayType.jclq_sf_5_5.getValue(), "5*5");
			playMap.put(PlayType.jclq_sf_5_6.getValue(), "5*6");
			playMap.put(PlayType.jclq_sf_5_10.getValue(), "5*10");
			playMap.put(PlayType.jclq_sf_5_16.getValue(), "5*16");
			playMap.put(PlayType.jclq_sf_5_20.getValue(), "5*20");
			playMap.put(PlayType.jclq_sf_5_26.getValue(), "5*26");
			playMap.put(PlayType.jclq_sf_6_6.getValue(), "6*6");
			playMap.put(PlayType.jclq_sf_6_7.getValue(), "6*7");
			playMap.put(PlayType.jclq_sf_6_15.getValue(), "6*15");
			playMap.put(PlayType.jclq_sf_6_20.getValue(), "6*20");
			playMap.put(PlayType.jclq_sf_6_22.getValue(), "6*22");
			playMap.put(PlayType.jclq_sf_6_35.getValue(), "6*35");
			playMap.put(PlayType.jclq_sf_6_42.getValue(), "6*42");
			playMap.put(PlayType.jclq_sf_6_50.getValue(), "6*50");
			playMap.put(PlayType.jclq_sf_6_57.getValue(), "6*57");
			playMap.put(PlayType.jclq_sf_7_7.getValue(), "7*7");
			playMap.put(PlayType.jclq_sf_7_8.getValue(), "7*8");
			playMap.put(PlayType.jclq_sf_7_21.getValue(), "7*21");
			playMap.put(PlayType.jclq_sf_7_35.getValue(), "7*35");
			playMap.put(PlayType.jclq_sf_7_120.getValue(), "7*120");
			playMap.put(PlayType.jclq_sf_8_8.getValue(), "8*8");
			playMap.put(PlayType.jclq_sf_8_9.getValue(), "8*9");
			playMap.put(PlayType.jclq_sf_8_28.getValue(), "8*28");
			playMap.put(PlayType.jclq_sf_8_56.getValue(), "8*56");
			playMap.put(PlayType.jclq_sf_8_70.getValue(), "8*70");
			playMap.put(PlayType.jclq_sf_8_247.getValue(), "8*247");
			
			
			playMap.put(PlayType.jclq_sf_2_1_dan.getValue(), "2*1");
			playMap.put(PlayType.jclq_sf_3_1_dan.getValue(), "3*1");
			playMap.put(PlayType.jclq_sf_4_1_dan.getValue(), "4*1");
			playMap.put(PlayType.jclq_sf_5_1_dan.getValue(), "5*1");
			playMap.put(PlayType.jclq_sf_6_1_dan.getValue(), "6*1");
			playMap.put(PlayType.jclq_sf_7_1_dan.getValue(), "7*1");
			playMap.put(PlayType.jclq_sf_8_1_dan.getValue(), "8*1");
			playMap.put(PlayType.jclq_sf_3_3_dan.getValue(), "3*3");
			playMap.put(PlayType.jclq_sf_3_4_dan.getValue(), "3*4");
			playMap.put(PlayType.jclq_sf_4_4_dan.getValue(), "4*4");
			playMap.put(PlayType.jclq_sf_4_5_dan.getValue(), "4*5");
			playMap.put(PlayType.jclq_sf_4_6_dan.getValue(), "4*6");
			playMap.put(PlayType.jclq_sf_4_11_dan.getValue(), "4*11");
			playMap.put(PlayType.jclq_sf_5_5_dan.getValue(), "5*5");
			playMap.put(PlayType.jclq_sf_5_6_dan.getValue(),"5*6");             
			playMap.put(PlayType.jclq_sf_5_10_dan.getValue(),"5*10");
			playMap.put(PlayType.jclq_sf_5_16_dan.getValue(),"5*16");
			playMap.put(PlayType.jclq_sf_5_20_dan.getValue(),"5*20");
			playMap.put(PlayType.jclq_sf_5_26_dan.getValue(),"5*26");
			playMap.put(PlayType.jclq_sf_6_6_dan.getValue(),"6*6");
			playMap.put(PlayType.jclq_sf_6_7_dan.getValue(),"6*7");
			playMap.put(PlayType.jclq_sf_6_15_dan.getValue(),"6*15");
			playMap.put(PlayType.jclq_sf_6_20_dan.getValue(),"6*20");
			playMap.put(PlayType.jclq_sf_6_22_dan.getValue(),"6*22");
			playMap.put(PlayType.jclq_sf_6_35_dan.getValue(),"6*35");
			playMap.put(PlayType.jclq_sf_6_42_dan.getValue(),"6*42");
			playMap.put(PlayType.jclq_sf_6_50_dan.getValue(),"6*50");
			playMap.put(PlayType.jclq_sf_6_57_dan.getValue(),"6*57");
			playMap.put(PlayType.jclq_sf_7_7_dan.getValue(),"7*7");
			playMap.put(PlayType.jclq_sf_7_8_dan.getValue(),"7*8");
			playMap.put(PlayType.jclq_sf_7_21_dan.getValue(),"7*21");
			playMap.put(PlayType.jclq_sf_7_35_dan.getValue(),"7*35");
			playMap.put(PlayType.jclq_sf_7_120_dan.getValue(),"7*120");             
			playMap.put(PlayType.jclq_sf_8_8_dan.getValue(),"8*8");
			playMap.put(PlayType.jclq_sf_8_9_dan.getValue(),"8*9");
			playMap.put(PlayType.jclq_sf_8_28_dan.getValue(),"8*28");
			playMap.put(PlayType.jclq_sf_8_56_dan.getValue(),"8*56");
			playMap.put(PlayType.jclq_sf_8_70_dan.getValue(),"8*70");
			playMap.put(PlayType.jclq_sf_8_247_dan.getValue(),"8*247");             
			
			
			
			playMap.put(PlayType.jclq_rfsf_1_1.getValue(),"1*1");
			playMap.put(PlayType.jclq_rfsf_2_1.getValue(),"2*1");
			playMap.put(PlayType.jclq_rfsf_3_1.getValue(),"3*1");
			playMap.put(PlayType.jclq_rfsf_4_1.getValue(),"4*1");
			playMap.put(PlayType.jclq_rfsf_5_1.getValue(),"5*1");
			playMap.put(PlayType.jclq_rfsf_6_1.getValue(),"6*1");
			playMap.put(PlayType.jclq_rfsf_7_1.getValue(),"7*1");
			playMap.put(PlayType.jclq_rfsf_8_1.getValue(),"8*1");
			playMap.put(PlayType.jclq_rfsf_3_3.getValue(),"3*3");
			playMap.put(PlayType.jclq_rfsf_3_4.getValue(),"3*4");
			playMap.put(PlayType.jclq_rfsf_4_4.getValue(),"4*4");
			playMap.put(PlayType.jclq_rfsf_4_5.getValue(),"4*5");
			playMap.put(PlayType.jclq_rfsf_4_6.getValue(),"4*6");
			playMap.put(PlayType.jclq_rfsf_4_11.getValue(),"4*11");
			playMap.put(PlayType.jclq_rfsf_5_5.getValue(),"5*5");
			playMap.put(PlayType.jclq_rfsf_5_6.getValue(),"5*6");
			playMap.put(PlayType.jclq_rfsf_5_10.getValue(),"5*10");
			playMap.put(PlayType.jclq_rfsf_5_16.getValue(),"5*16");
			playMap.put(PlayType.jclq_rfsf_5_20.getValue(),"5*20");
			playMap.put(PlayType.jclq_rfsf_5_26.getValue(),"5*26");
			playMap.put(PlayType.jclq_rfsf_6_6.getValue(),"6*6");
			playMap.put(PlayType.jclq_rfsf_6_7.getValue(),"6*7");
			playMap.put(PlayType.jclq_rfsf_6_15.getValue(),"6*15");
			playMap.put(PlayType.jclq_rfsf_6_20.getValue(),"6*20");
			playMap.put(PlayType.jclq_rfsf_6_22.getValue(),"6*22");
			playMap.put(PlayType.jclq_rfsf_6_35.getValue(),"6*35");
			playMap.put(PlayType.jclq_rfsf_6_42.getValue(),"6*42");
			playMap.put(PlayType.jclq_rfsf_6_50.getValue(),"6*50");
			playMap.put(PlayType.jclq_rfsf_6_57.getValue(),"6*57");
			playMap.put(PlayType.jclq_rfsf_7_7.getValue(),"7*7");
			playMap.put(PlayType.jclq_rfsf_7_8.getValue(),"7*8");
			playMap.put(PlayType.jclq_rfsf_7_21.getValue(),"7*21");
			playMap.put(PlayType.jclq_rfsf_7_35.getValue(),"7*35");
			playMap.put(PlayType.jclq_rfsf_7_120.getValue(),"7*120");
			playMap.put(PlayType.jclq_rfsf_8_8.getValue(),"8*8");
			playMap.put(PlayType.jclq_rfsf_8_9.getValue(),"8*9");
			playMap.put(PlayType.jclq_rfsf_8_28.getValue(),"8*28");
			playMap.put(PlayType.jclq_rfsf_8_56.getValue(),"8*56");
			playMap.put(PlayType.jclq_rfsf_8_70.getValue(),"8*70");
			playMap.put(PlayType.jclq_rfsf_8_247.getValue(),"8*247");
			
			
			
			playMap.put(PlayType.jclq_rfsf_1_1_dan.getValue(),"1*1");
			playMap.put(PlayType.jclq_rfsf_2_1_dan.getValue(),"2*1");
			playMap.put(PlayType.jclq_rfsf_3_1_dan.getValue(),"3*1");
			playMap.put(PlayType.jclq_rfsf_4_1_dan.getValue(),"4*1");
			playMap.put(PlayType.jclq_rfsf_5_1_dan.getValue(),"5*1");
			playMap.put(PlayType.jclq_rfsf_6_1_dan.getValue(),"6*1");
			playMap.put(PlayType.jclq_rfsf_7_1_dan.getValue(),"7*1");
			playMap.put(PlayType.jclq_rfsf_8_1_dan.getValue(),"8*1");
			playMap.put(PlayType.jclq_rfsf_3_3_dan.getValue(),"3*3");
			playMap.put(PlayType.jclq_rfsf_3_4_dan.getValue(),"3*4");
			playMap.put(PlayType.jclq_rfsf_4_4_dan.getValue(),"4*4");
			playMap.put(PlayType.jclq_rfsf_4_5_dan.getValue(),"4*5");
			playMap.put(PlayType.jclq_rfsf_4_6_dan.getValue(),"4*6");
			playMap.put(PlayType.jclq_rfsf_4_11_dan.getValue(),"4*11");           
			playMap.put(PlayType.jclq_rfsf_5_5_dan.getValue(),"5*5");
			playMap.put(PlayType.jclq_rfsf_5_6_dan.getValue(),"5*6");
			playMap.put(PlayType.jclq_rfsf_5_10_dan.getValue(),"5*10");
			playMap.put(PlayType.jclq_rfsf_5_16_dan.getValue(),"5*16");
			playMap.put(PlayType.jclq_rfsf_5_20_dan.getValue(),"5*20");
			playMap.put(PlayType.jclq_rfsf_5_26_dan.getValue(),"5*26");
			playMap.put(PlayType.jclq_rfsf_6_6_dan.getValue(),"6*6");           
			playMap.put(PlayType.jclq_rfsf_6_7_dan.getValue(),"6*7");           
			playMap.put(PlayType.jclq_rfsf_6_15_dan.getValue(),"6*15");
			playMap.put(PlayType.jclq_rfsf_6_20_dan.getValue(),"6*20");
			playMap.put(PlayType.jclq_rfsf_6_22_dan.getValue(),"6*22");
			playMap.put(PlayType.jclq_rfsf_6_35_dan.getValue(),"6*35");
			playMap.put(PlayType.jclq_rfsf_6_42_dan.getValue(),"6*42");
			playMap.put(PlayType.jclq_rfsf_6_50_dan.getValue(),"6*50");
			playMap.put(PlayType.jclq_rfsf_6_57_dan.getValue(),"6*57");
			playMap.put(PlayType.jclq_rfsf_7_7_dan.getValue(),"7*7");
			playMap.put(PlayType.jclq_rfsf_7_8_dan.getValue(),"7*8");
			playMap.put(PlayType.jclq_rfsf_7_21_dan.getValue(),"7*21");
			playMap.put(PlayType.jclq_rfsf_7_35_dan.getValue(),"7*35");
			playMap.put(PlayType.jclq_rfsf_7_120_dan.getValue(),"7*120");           
			playMap.put(PlayType.jclq_rfsf_8_8_dan.getValue(),"8*8");
			playMap.put(PlayType.jclq_rfsf_8_9_dan.getValue(),"8*9");
			playMap.put(PlayType.jclq_rfsf_8_28_dan.getValue(),"8*28");
			playMap.put(PlayType.jclq_rfsf_8_56_dan.getValue(),"8*56");
			playMap.put(PlayType.jclq_rfsf_8_70_dan.getValue(),"8*70");
			playMap.put(PlayType.jclq_rfsf_8_247_dan.getValue(),"8*247");           
		
			playMap.put(PlayType.jclq_sfc_1_1.getValue(),"1*1");
			playMap.put(PlayType.jclq_sfc_2_1.getValue(),"2*1");
			playMap.put(PlayType.jclq_sfc_3_1.getValue(),"3*1");
			playMap.put(PlayType.jclq_sfc_4_1.getValue(),"4*1");
			playMap.put(PlayType.jclq_sfc_5_1.getValue(),"5*1");
			playMap.put(PlayType.jclq_sfc_6_1.getValue(),"6*1");
			playMap.put(PlayType.jclq_sfc_7_1.getValue(),"7*1");
			playMap.put(PlayType.jclq_sfc_8_1.getValue(),"8*1");
			playMap.put(PlayType.jclq_sfc_3_3.getValue(),"3*3");
			playMap.put(PlayType.jclq_sfc_3_4.getValue(),"3*4");
			playMap.put(PlayType.jclq_sfc_4_4.getValue(),"4*4");
			playMap.put(PlayType.jclq_sfc_4_5.getValue(),"4*5");
			playMap.put(PlayType.jclq_sfc_4_6.getValue(),"4*6");
			playMap.put(PlayType.jclq_sfc_4_11.getValue(),"4*11");
			
			
			playMap.put(PlayType.jclq_sfc_1_1_dan.getValue(),"1*1");
			playMap.put(PlayType.jclq_sfc_2_1_dan.getValue(),"2*1");
			playMap.put(PlayType.jclq_sfc_3_1_dan.getValue(),"3*1");
			playMap.put(PlayType.jclq_sfc_4_1_dan.getValue(),"4*1");
			playMap.put(PlayType.jclq_sfc_5_1_dan.getValue(),"5*1");
			playMap.put(PlayType.jclq_sfc_6_1_dan.getValue(),"6*1");
			playMap.put(PlayType.jclq_sfc_7_1_dan.getValue(),"7*1");
			playMap.put(PlayType.jclq_sfc_8_1_dan.getValue(),"8*1");
			playMap.put(PlayType.jclq_sfc_3_3_dan.getValue(),"3*3");
			playMap.put(PlayType.jclq_sfc_3_4_dan.getValue(),"3*4");
			playMap.put(PlayType.jclq_sfc_4_4_dan.getValue(),"4*4");
			playMap.put(PlayType.jclq_sfc_4_5_dan.getValue(),"4*5");
			playMap.put(PlayType.jclq_sfc_4_6_dan.getValue(),"4*6");
			playMap.put(PlayType.jclq_sfc_4_11_dan.getValue(),"4*11");
	    
			playMap.put(PlayType.jclq_dxf_1_1.getValue(),"1*1");
			playMap.put(PlayType.jclq_dxf_2_1.getValue(),"2*1");
			playMap.put(PlayType.jclq_dxf_3_1.getValue(),"3*1");
			playMap.put(PlayType.jclq_dxf_4_1.getValue(),"4*1");
			playMap.put(PlayType.jclq_dxf_5_1.getValue(),"5*1");
			playMap.put(PlayType.jclq_dxf_6_1.getValue(),"6*1");
			playMap.put(PlayType.jclq_dxf_7_1.getValue(),"7*1");
			playMap.put(PlayType.jclq_dxf_8_1.getValue(),"8*1");
			playMap.put(PlayType.jclq_dxf_3_3.getValue(),"3*3");
			playMap.put(PlayType.jclq_dxf_3_4.getValue(),"3*4");
			playMap.put(PlayType.jclq_dxf_4_4.getValue(),"4*4");
			playMap.put(PlayType.jclq_dxf_4_5.getValue(),"4*5");
			playMap.put(PlayType.jclq_dxf_4_6.getValue(),"4*6");
			playMap.put(PlayType.jclq_dxf_4_11.getValue(),"4*11");
			playMap.put(PlayType.jclq_dxf_5_5.getValue(),"5*5");
			playMap.put(PlayType.jclq_dxf_5_6.getValue(),"5*6");
			playMap.put(PlayType.jclq_dxf_5_10.getValue(),"5*10");
			playMap.put(PlayType.jclq_dxf_5_16.getValue(),"5*16");
			playMap.put(PlayType.jclq_dxf_5_20.getValue(),"5*20");
			playMap.put(PlayType.jclq_dxf_5_26.getValue(),"5*26");
			playMap.put(PlayType.jclq_dxf_6_6.getValue(),"6*6");
			playMap.put(PlayType.jclq_dxf_6_7.getValue(),"6*7");
			playMap.put(PlayType.jclq_dxf_6_15.getValue(),"6*15");
			playMap.put(PlayType.jclq_dxf_6_20.getValue(),"6*20");
			playMap.put(PlayType.jclq_dxf_6_22.getValue(),"6*22");
			playMap.put(PlayType.jclq_dxf_6_35.getValue(),"6*35");
			playMap.put(PlayType.jclq_dxf_6_42.getValue(),"6*42");
			playMap.put(PlayType.jclq_dxf_6_50.getValue(),"6*50");
			playMap.put(PlayType.jclq_dxf_6_57.getValue(),"6*57");
			playMap.put(PlayType.jclq_dxf_7_7.getValue(),"7*7");
			playMap.put(PlayType.jclq_dxf_7_8.getValue(),"7*8");
			playMap.put(PlayType.jclq_dxf_7_21.getValue(),"7*21");
			playMap.put(PlayType.jclq_dxf_7_35.getValue(),"7*35");
			playMap.put(PlayType.jclq_dxf_7_120.getValue(),"7*120");
			playMap.put(PlayType.jclq_dxf_8_8.getValue(),"8*8");
			playMap.put(PlayType.jclq_dxf_8_9.getValue(),"8*9");
			playMap.put(PlayType.jclq_dxf_8_28.getValue(),"8*28");
			playMap.put(PlayType.jclq_dxf_8_56.getValue(),"8*56");
			playMap.put(PlayType.jclq_dxf_8_70.getValue(),"8*70");
			playMap.put(PlayType.jclq_dxf_8_247.getValue(),"8*247");
			
			
			
			playMap.put(PlayType.jclq_dxf_1_1_dan.getValue(),"1*1");
			playMap.put(PlayType.jclq_dxf_2_1_dan.getValue(),"2*1");
			playMap.put(PlayType.jclq_dxf_3_1_dan.getValue(),"3*1");
		     playMap.put(PlayType.jclq_dxf_4_1_dan.getValue(),"4*1");
			playMap.put(PlayType.jclq_dxf_5_1_dan.getValue(),"5*1");
			playMap.put(PlayType.jclq_dxf_6_1_dan.getValue(),"6*1");
			playMap.put(PlayType.jclq_dxf_7_1_dan.getValue(),"7*1");
			playMap.put(PlayType.jclq_dxf_8_1_dan.getValue(),"8*1");
			playMap.put(PlayType.jclq_dxf_3_3_dan.getValue(),"3*3");
			playMap.put(PlayType.jclq_dxf_3_4_dan.getValue(),"3*4");
			playMap.put(PlayType.jclq_dxf_4_4_dan.getValue(),"4*4");
			playMap.put(PlayType.jclq_dxf_4_5_dan.getValue(),"4*5");
			playMap.put(PlayType.jclq_dxf_4_6_dan.getValue(),"4*6");
			playMap.put(PlayType.jclq_dxf_4_11_dan.getValue(),"4*11");
			playMap.put(PlayType.jclq_dxf_5_5_dan.getValue(),"5*5");
			playMap.put(PlayType.jclq_dxf_5_6_dan.getValue(),"5*6");
			playMap.put(PlayType.jclq_dxf_5_10_dan.getValue(),"5*10");
			playMap.put(PlayType.jclq_dxf_5_16_dan.getValue(),"5*16");
			playMap.put(PlayType.jclq_dxf_5_20_dan.getValue(),"5*20");
			playMap.put(PlayType.jclq_dxf_5_26_dan.getValue(),"5*26");
			playMap.put(PlayType.jclq_dxf_6_6_dan.getValue(),"6*6");
			playMap.put(PlayType.jclq_dxf_6_7_dan.getValue(),"6*7");
			playMap.put(PlayType.jclq_dxf_6_15_dan.getValue(),"6*15");
			playMap.put(PlayType.jclq_dxf_6_20_dan.getValue(),"6*20");
			playMap.put(PlayType.jclq_dxf_6_22_dan.getValue(),"6*22");
			playMap.put(PlayType.jclq_dxf_6_35_dan.getValue(),"6*35");
			playMap.put(PlayType.jclq_dxf_6_42_dan.getValue(),"6*42");
			playMap.put(PlayType.jclq_dxf_6_50_dan.getValue(),"6*50");
			playMap.put(PlayType.jclq_dxf_6_57_dan.getValue(),"6*57");
			playMap.put(PlayType.jclq_dxf_7_7_dan.getValue(),"7*7");
			playMap.put(PlayType.jclq_dxf_7_8_dan.getValue(),"7*8");
			playMap.put(PlayType.jclq_dxf_7_21_dan.getValue(),"7*21");
			playMap.put(PlayType.jclq_dxf_7_35_dan.getValue(),"7*35");
			playMap.put(PlayType.jclq_dxf_7_120_dan.getValue(),"7*120");
			playMap.put(PlayType.jclq_dxf_8_8_dan.getValue(),"8*8");
			playMap.put(PlayType.jclq_dxf_8_9_dan.getValue(),"8*9");
			playMap.put(PlayType.jclq_dxf_8_28_dan.getValue(),"8*28");
			playMap.put(PlayType.jclq_dxf_8_56_dan.getValue(),"8*56");
			playMap.put(PlayType.jclq_dxf_8_70_dan.getValue(),"8*70");
			playMap.put(PlayType.jclq_dxf_8_247_dan.getValue(),"8*247");

			playMap.put(PlayType.jclq_mix_1_1.getValue(),"1*1");
			playMap.put(PlayType.jclq_mix_2_1.getValue(),"2*1");
			playMap.put(PlayType.jclq_mix_3_1.getValue(),"3*1");
			playMap.put(PlayType.jclq_mix_4_1.getValue(),"4*1");
			playMap.put(PlayType.jclq_mix_5_1.getValue(),"5*1");
			playMap.put(PlayType.jclq_mix_6_1.getValue(),"6*1");
			playMap.put(PlayType.jclq_mix_7_1.getValue(),"7*1");
			playMap.put(PlayType.jclq_mix_8_1.getValue(),"8*1");
			playMap.put(PlayType.jclq_mix_3_3.getValue(),"3*3");
			playMap.put(PlayType.jclq_mix_3_4.getValue(),"3*4");
			playMap.put(PlayType.jclq_mix_4_4.getValue(),"4*4");
			playMap.put(PlayType.jclq_mix_4_5.getValue(),"4*5");
			playMap.put(PlayType.jclq_mix_4_6.getValue(),"4*6");
			playMap.put(PlayType.jclq_mix_4_11.getValue(),"4*11");
			playMap.put(PlayType.jclq_mix_5_5.getValue(),"5*5");
			playMap.put(PlayType.jclq_mix_5_6.getValue(),"5*6");
			playMap.put(PlayType.jclq_mix_5_10.getValue(),"5*10");
			playMap.put(PlayType.jclq_mix_5_16.getValue(),"5*16");
			playMap.put(PlayType.jclq_mix_5_20.getValue(),"5*20");
			playMap.put(PlayType.jclq_mix_5_26.getValue(),"5*26");
			playMap.put(PlayType.jclq_mix_6_6.getValue(),"6*6");
			playMap.put(PlayType.jclq_mix_6_7.getValue(),"6*7");
			playMap.put(PlayType.jclq_mix_6_15.getValue(),"6*15");
			playMap.put(PlayType.jclq_mix_6_20.getValue(),"6*20");
			playMap.put(PlayType.jclq_mix_6_22.getValue(),"6*22");
			playMap.put(PlayType.jclq_mix_6_35.getValue(),"6*35");
			playMap.put(PlayType.jclq_mix_6_42.getValue(),"6*42");
			playMap.put(PlayType.jclq_mix_6_50.getValue(),"6*50");
			playMap.put(PlayType.jclq_mix_6_57.getValue(),"6*57");
			playMap.put(PlayType.jclq_mix_7_7.getValue(),"7*7");
			playMap.put(PlayType.jclq_mix_7_8.getValue(),"7*8");
			playMap.put(PlayType.jclq_mix_7_21.getValue(),"7*21");
			playMap.put(PlayType.jclq_mix_7_35.getValue(),"7*35");
			playMap.put(PlayType.jclq_mix_7_120.getValue(),"7*120");
			playMap.put(PlayType.jclq_mix_8_8.getValue(),"8*8");
			playMap.put(PlayType.jclq_mix_8_9.getValue(),"8*9");
			playMap.put(PlayType.jclq_mix_8_28.getValue(),"8*28");
			playMap.put(PlayType.jclq_mix_8_56.getValue(),"8*56");
			playMap.put(PlayType.jclq_mix_8_70.getValue(),"8*70");
			playMap.put(PlayType.jclq_mix_8_247.getValue(),"8*247");
		
			playMap.put(PlayType.jclq_mix_2_1_dan.getValue(),"2*1");
			playMap.put(PlayType.jclq_mix_3_1_dan.getValue(),"3*1");
			playMap.put(PlayType.jclq_mix_4_1_dan.getValue(),"4*1");
			playMap.put(PlayType.jclq_mix_5_1_dan.getValue(),"5*1");
			playMap.put(PlayType.jclq_mix_6_1_dan.getValue(),"6*1");
			playMap.put(PlayType.jclq_mix_7_1_dan.getValue(),"7*1");
			playMap.put(PlayType.jclq_mix_8_1_dan.getValue(),"8*1");
			playMap.put(PlayType.jclq_mix_3_3_dan.getValue(),"3*3");
			playMap.put(PlayType.jclq_mix_3_4_dan.getValue(),"3*4");
			playMap.put(PlayType.jclq_mix_4_4_dan.getValue(),"4*4");
			playMap.put(PlayType.jclq_mix_4_5_dan.getValue(),"4*5");
			playMap.put(PlayType.jclq_mix_4_6_dan.getValue(),"4*6");
			playMap.put(PlayType.jclq_mix_4_11_dan.getValue(),"4*11");
			playMap.put(PlayType.jclq_mix_5_5_dan.getValue(),"5*5");
			playMap.put(PlayType.jclq_mix_5_6_dan.getValue(),"5*6");
			playMap.put(PlayType.jclq_mix_5_10_dan.getValue(),"5*10");
			playMap.put(PlayType.jclq_mix_5_16_dan.getValue(),"5*16");
			playMap.put(PlayType.jclq_mix_5_20_dan.getValue(),"5*20");
			playMap.put(PlayType.jclq_mix_5_26_dan.getValue(),"5*26");
			playMap.put(PlayType.jclq_mix_6_6_dan.getValue(),"6*6");
			playMap.put(PlayType.jclq_mix_6_7_dan.getValue(),"6*7");
			playMap.put(PlayType.jclq_mix_6_15_dan.getValue(),"6*15");
			playMap.put(PlayType.jclq_mix_6_20_dan.getValue(),"6*20");
			playMap.put(PlayType.jclq_mix_6_22_dan.getValue(),"6*22");
			playMap.put(PlayType.jclq_mix_6_35_dan.getValue(),"6*35");
			playMap.put(PlayType.jclq_mix_6_42_dan.getValue(),"6*42");
			playMap.put(PlayType.jclq_mix_6_50_dan.getValue(),"6*50");
			playMap.put(PlayType.jclq_mix_6_57_dan.getValue(),"6*57");
			playMap.put(PlayType.jclq_mix_7_7_dan.getValue(),"7*7");
			playMap.put(PlayType.jclq_mix_7_8_dan.getValue(),"7*8");
			playMap.put(PlayType.jclq_mix_7_21_dan.getValue(),"7*21");
			playMap.put(PlayType.jclq_mix_7_35_dan.getValue(),"7*35");
			playMap.put(PlayType.jclq_mix_7_120_dan.getValue(),"7*120");
			playMap.put(PlayType.jclq_mix_8_8_dan.getValue(),"8*8");
			playMap.put(PlayType.jclq_mix_8_9_dan.getValue(),"8*9");
			playMap.put(PlayType.jclq_mix_8_28_dan.getValue(),"8*28");
			playMap.put(PlayType.jclq_mix_8_56_dan.getValue(),"8*56");
			playMap.put(PlayType.jclq_mix_8_70_dan.getValue(),"8*70");
			playMap.put(PlayType.jclq_mix_8_247_dan.getValue(),"8*247");
			
		
		
		/**
		 * 投注票内容转换
		 * FIXME 暂时只支持双色球,后期需要添加其它彩种
		 */
		// 双色球/复试
		IVenderTicketConverter ssq_ds_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace(",", " ")
						.replace("|", "-").replace("^", "");
				return contentStr;
		}};

		// 双色球胆拖
		IVenderTicketConverter ssqdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace(",", " ")
						.replace("|", "-").replace("^", "").replace("#", "$");
				return contentStr;
		}};
		
		// 大乐透单式/复式
		IVenderTicketConverter dlt_hz_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1]
						.replace(",", " ").replace("|", "-").replace("^", "");
				return contentStr;
			}
		};

		// 大乐透胆拖
		IVenderTicketConverter dlt_dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1]
						.replace(",", " ").replace("|", "-")
						.replace("^", "").replace("#", "$");
				return contentStr;
			}
		};
		IVenderTicketConverter d3_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if(j!=oneCode.length-1){
						ticketContent.append("\r\n");
					}
				}
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter p3_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				StringBuffer ticketContentStr = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if(j!=oneCode.length-1){
						ticketContent.append("\r\n");
					}
				}
				return ticketContentStr.append(playTypeMap.get(ticket.getPlayType())).append("|").append(ticketContent.toString()).toString();
			}
		};
		
		
		IVenderTicketConverter d3_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.replace("^", "").split("\\|");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if(j!=oneCode.length-1){
						ticketContent.append(",");
					}
				}
				return ticketContent.toString();
			  }
			};
		
		
		IVenderTicketConverter jczq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
				String  []contents=con.split("\\,");
				strBuilder.append(playTypeMap.get(playType)).append("|");
				int i=0;
				for(String s:contents){
					strBuilder.append(s.substring(2));
					if(i!=contents.length-1){
						strBuilder.append(",");
					}
					i++;
				}
				strBuilder.append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter lczq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].substring(2)).append("=");
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						stringBuffer.append(s.replace("3","1").replace("0", "2"));
						if(j!=ss.length-1){
							stringBuffer.append("/");
						}
						j++;
					}
					
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		        
		        return strBuilder.toString();
		}};
		
		
		IVenderTicketConverter lczqsfc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].substring(2)).append("=");
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						stringBuffer.append(s);
						if(j!=ss.length-1){
							stringBuffer.append("/");
						}
						j++;
					}
					
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		        
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter jczqbf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].substring(2)).append("=");
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						stringBuffer.append(s.substring(0,1)).append(s.substring(1,2));
						if(j!=ss.length-1){
							stringBuffer.append("/");
						}
						j++;
					}
					
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		IVenderTicketConverter jczqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].split("\\*")[0].substring(2)).append("-").append(toLotteryTypeMap.get(con.split("\\=")[0].split("\\*")[1]));
					stringBuffer.append("=");
					String playTypeStr=con.split("\\=")[0].split("\\*")[1];
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						if("3007".equals(playTypeStr)){
							stringBuffer.append(s.substring(0,1)).append(s.substring(1,2));
						}else if("3009".equals(playTypeStr)){
							stringBuffer.append(s);
						}else{
							stringBuffer.append(s);
						}
						if(j!=ss.length-1){
							stringBuffer.append("/");
						}
						j++;
					}
					
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		       
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter jczqbqc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].substring(2)).append("=");
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						stringBuffer.append(s);
						if(j!=ss.length-1){
							stringBuffer.append("/");
						}
						j++;
					}
					
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].split("\\*")[0].substring(2)).append("-").append(toLotteryTypeMap.get(con.split("\\=")[0].split("\\*")[1]));
					stringBuffer.append("=");
					String playTypeStr=con.split("\\=")[0].split("\\*")[1];
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						if("3003".equals(playTypeStr)){
							stringBuffer.append(s);
						}else if("3004".equals(playTypeStr)){
							stringBuffer.append(s);
						}else if("3001".equals(playTypeStr)||"3002".equals(playTypeStr)){
							stringBuffer.append(s.replace("3","1").replace("0","2"));
						}else{
							stringBuffer.append(s);
						}
						if(j!=ss.length-1){
							stringBuffer.append("/");
						}
						j++;
					}
					
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		         return strBuilder.toString();
		}};
		
		IVenderTicketConverter dxf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String content = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");
				String []cons=content.split("\\,");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\=")[0].substring(2)).append("=").append(con.split("\\=")[1]);
					if(i!=cons.length-1){
						stringBuffer.append(",");
					}
					i++;
		        }
				strBuilder.append(playTypeMap.get(playType)).append("|").append(stringBuffer.toString()).append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		// 大乐透胆拖
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, dlt_hz_fs);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, dlt_hz_fs);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dlt_dt);
		
		// 双色球
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds_fs);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq_ds_fs);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssqdt);

		//七星彩
		playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, dlt_hz_fs);
		playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, dlt_hz_fs);
		
		


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,jczq);    
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jczq);


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11_dan,jczq); 
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120_dan,jczq);  
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247_dan,jczq);



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



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jczq);   
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jczq);



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11_dan,jczq);   
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57_dan,jczq);



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


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jczq);     
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jczq);


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247_dan,jczq);


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
    	
    	
    	
    	
    	
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,lczq);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,lczq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,lczq);        
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,lczq);        
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,lczq);        
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,lczq);        
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,lczq);        
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,lczq);       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,lczq);      
     	                                                                       
     	                                                                       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9_dan,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70_dan,lczq);   
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247_dan,lczq);  
     	                                                                      
     	                                                                      
     	                                                                      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,lczq);    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,lczq);      
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,lczq);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,lczq);    
     	                                                                       
     	                                                                       
     	                                                                       
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120_dan,lczq);
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9_dan,lczq);  
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70_dan,lczq); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247_dan,lczq);


     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,lczqsfc);     
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,lczqsfc);    
     	                                                                    
     	                                                                    
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6_dan,lczqsfc); 
     	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11_dan,lczqsfc);
   

     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,dxf); 
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,dxf);  
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,dxf);



     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11_dan,dxf); 
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70_dan,dxf);
     	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247_dan,dxf);  


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

     	playTypeToAdvanceConverterMap.put(PlayType.qxc_dan,d3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.qxc_fu,d3_fs); 
     	playTypeToAdvanceConverterMap.put(PlayType.p5_dan,d3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p5_fu,d3_fs); 
     	
     	playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan,p3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan,p3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan,p3_ds); 
    	playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs,d3_fs); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs,p3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs,p3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt,p3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt,p3_ds); 
     	playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt,p3_ds); 
	}
	
}
