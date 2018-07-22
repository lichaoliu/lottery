 package com.lottery.ticket.vender.impl.letou;

 import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

public class LeTouLotteryDef {
	 /** 彩种转换 */
    protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
    /** 彩种转换 */
    public static Map<LotteryType,String> toLotteryTypeMap = new HashMap<LotteryType,String>();
    /** 彩期转换*/
    protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
    /** 玩法转换*/
    public static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
    /** 玩法转换*/
    public static Map<Integer, String> toplayTypeMap = new HashMap<Integer, String>();
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
				return "20"+phase;
			}
		};
		
		
		phaseConverterMap.put(LotteryType.CJDLT, phaseConverter);
		phaseConverterMap.put(LotteryType.PL3, phaseConverter);
		phaseConverterMap.put(LotteryType.PL5, phaseConverter);
		phaseConverterMap.put(LotteryType.QXC, phaseConverter);

		//彩种转换 otherlotteryType
		lotteryTypeMap.put(LotteryType.CJDLT, "200");
        lotteryTypeMap.put(LotteryType.PL3, "203");
        lotteryTypeMap.put(LotteryType.PL5, "205");
        lotteryTypeMap.put(LotteryType.QXC, "207");
		lotteryTypeMap.put(LotteryType.JCLQ_SF,"301");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"301");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC,"301"); 
		lotteryTypeMap.put(LotteryType.JCLQ_DXF,"301");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG,"301");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"201");
		lotteryTypeMap.put(LotteryType.JCZQ_BF,"201");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS,"201");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC,"201");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"201");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG,"201");


		//对方otherPlayType
		toLotteryTypeMap.put(LotteryType.JCLQ_SF,"01");
		toLotteryTypeMap.put(LotteryType.JCLQ_RFSF,"02");
		toLotteryTypeMap.put(LotteryType.JCLQ_SFC,"03"); 
		toLotteryTypeMap.put(LotteryType.JCLQ_DXF,"04");
		toLotteryTypeMap.put(LotteryType.JCLQ_HHGG,"10");

		toLotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"01");
		toLotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"02");
		toLotteryTypeMap.put(LotteryType.JCZQ_BF,"04");
		toLotteryTypeMap.put(LotteryType.JCZQ_JQS,"03");
		toLotteryTypeMap.put(LotteryType.JCZQ_BQC,"05");
		toLotteryTypeMap.put(LotteryType.JCZQ_HHGG,"10");

        //对方otherPlayType,数字彩按玩法对应
        toplayTypeMap.put(PlayType.p5_dan.getValue(), "10");
        toplayTypeMap.put(PlayType.p5_fu.getValue(), "10");

        toplayTypeMap.put(PlayType.qxc_dan.getValue(), "10");
        toplayTypeMap.put(PlayType.qxc_fu.getValue(), "10");

        toplayTypeMap.put(PlayType.p3_zhi_dan.getValue(), "01");
        toplayTypeMap.put(PlayType.p3_zhi_fs.getValue(), "01");
        toplayTypeMap.put(PlayType.p3_zhi_dt.getValue(), "01");
        toplayTypeMap.put(PlayType.p3_z3_dan.getValue(), "02");
        toplayTypeMap.put(PlayType.p3_z3_fs.getValue(), "02");
        toplayTypeMap.put(PlayType.p3_z6_dan.getValue(), "03");
        toplayTypeMap.put(PlayType.p3_z6_fs.getValue(), "03");
        toplayTypeMap.put(PlayType.p3_z3_dt.getValue(), "04");
        toplayTypeMap.put(PlayType.p3_z6_dt.getValue(), "04");

		
		//玩法转换 对应otherbetType
		playTypeMap.put(PlayType.dlt_dan.getValue(), "10");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "20");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "30");

        playTypeMap.put(PlayType.p5_dan.getValue(), "10");
        playTypeMap.put(PlayType.p5_fu.getValue(), "20");

        playTypeMap.put(PlayType.qxc_dan.getValue(), "10");
        playTypeMap.put(PlayType.qxc_fu.getValue(), "20");

        playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "01");
        playTypeMap.put(PlayType.p3_z3_dan.getValue(), "01");
        playTypeMap.put(PlayType.p3_z6_dan.getValue(), "01");
        playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "02");
        playTypeMap.put(PlayType.p3_z3_fs.getValue(), "02");
        playTypeMap.put(PlayType.p3_z6_fs.getValue(), "02");
        playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "04");
        playTypeMap.put(PlayType.p3_z3_dt.getValue(), "01");
        playTypeMap.put(PlayType.p3_z6_dt.getValue(), "01");

	
		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(), "02");
		
		
		playTypeMap.put(PlayType.jclq_sf_2_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_1_dan.getValue(), "02");                                                                                              
		playTypeMap.put(PlayType.jclq_sf_6_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_3_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_4_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_4_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_5_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_6_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_11_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_5_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_6_dan.getValue(),"02");             
		playTypeMap.put(PlayType.jclq_sf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_120_dan.getValue(),"02");             
		playTypeMap.put(PlayType.jclq_sf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_247_dan.getValue(),"02");             
		
		
		
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
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1_dan.getValue(),"01");
		playTypeMap.put(PlayType.jclq_rfsf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_11_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_6_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_6_7_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_120_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_247_dan.getValue(),"02");           
	
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
		
		
		playTypeMap.put(PlayType.jclq_sfc_1_1_dan.getValue(),"01");
		playTypeMap.put(PlayType.jclq_sfc_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_11_dan.getValue(),"02");
    
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
		
		
		
		playTypeMap.put(PlayType.jclq_dxf_1_1_dan.getValue(),"01");
		playTypeMap.put(PlayType.jclq_dxf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_3_1_dan.getValue(),"02");
	    playTypeMap.put(PlayType.jclq_dxf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_247_dan.getValue(),"02");

		playTypeMap.put(PlayType.jclq_mix_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"02");
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
	
		playTypeMap.put(PlayType.jclq_mix_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_247_dan.getValue(),"02");

		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_spf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_247_dan.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_bf_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_bf_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_11.getValue(),"02");
		
		
		playTypeMap.put(PlayType.jczq_bf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_11_dan.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(),"02");
	
		playTypeMap.put(PlayType.jczq_jqs_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_57_dan.getValue(),"02");
	
		playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(),"02");
		
		
		playTypeMap.put(PlayType.jczq_bqc_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_11_dan.getValue(),"02");

		playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_spfwrq_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247_dan.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_mix_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"02");
	
		playTypeMap.put(PlayType.jczq_mix_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_247_dan.getValue(),"02");
		
		
		
		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(), "02");
		
		
		playTypeMap.put(PlayType.jclq_sf_2_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_6_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_7_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_8_1_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_3_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_3_4_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_4_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_5_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_6_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_4_11_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_5_dan.getValue(), "02");
		playTypeMap.put(PlayType.jclq_sf_5_6_dan.getValue(),"02");             
		playTypeMap.put(PlayType.jclq_sf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_7_120_dan.getValue(),"02");             
		playTypeMap.put(PlayType.jclq_sf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sf_8_247_dan.getValue(),"02");             
		
		
		
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
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1_dan.getValue(),"01");
		playTypeMap.put(PlayType.jclq_rfsf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_4_11_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_6_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_6_7_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_7_120_dan.getValue(),"02");           
		playTypeMap.put(PlayType.jclq_rfsf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_rfsf_8_247_dan.getValue(),"02");           
	
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
		
		
		playTypeMap.put(PlayType.jclq_sfc_1_1_dan.getValue(),"01");
		playTypeMap.put(PlayType.jclq_sfc_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_sfc_4_11_dan.getValue(),"02");
    
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
		
		
		
		playTypeMap.put(PlayType.jclq_dxf_1_1_dan.getValue(),"01");
		playTypeMap.put(PlayType.jclq_dxf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_3_1_dan.getValue(),"02");
	  playTypeMap.put(PlayType.jclq_dxf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_dxf_8_247_dan.getValue(),"02");

		playTypeMap.put(PlayType.jclq_mix_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"02");
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
	
		playTypeMap.put(PlayType.jclq_mix_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jclq_mix_8_247_dan.getValue(),"02");

		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_spf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spf_8_247_dan.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_bf_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_bf_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_11.getValue(),"02");
		
		
		playTypeMap.put(PlayType.jczq_bf_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bf_4_11_dan.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(),"02");
	
		playTypeMap.put(PlayType.jczq_jqs_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_jqs_6_57_dan.getValue(),"02");
	
		playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(),"02");
		
		
		playTypeMap.put(PlayType.jczq_bqc_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_bqc_4_11_dan.getValue(),"02");

		playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_spfwrq_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247_dan.getValue(),"02");
		
		playTypeMap.put(PlayType.jczq_mix_1_1.getValue(),"01");
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"02");
	
		playTypeMap.put(PlayType.jczq_mix_2_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_1_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_3_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_3_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_4_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_4_11_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_5_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_10_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_16_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_5_26_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_6_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_15_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_20_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_22_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_42_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_50_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_6_57_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_7_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_21_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_35_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_7_120_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_8_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_9_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_28_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_56_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_70_dan.getValue(),"02");
		playTypeMap.put(PlayType.jczq_mix_8_247_dan.getValue(),"02");
				
		
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
			
			
    }
			
			
			
    static {			
		
		IVenderTicketConverter dlt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
					strBuilder.append(con);
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter dltdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String cons = ticket.getContent().split("-")[1].replace("^","");
		        return cons;
		}};

		//直选单式
		IVenderTicketConverter pl3zxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
				    String[] attr = con.split(",");
				    int j = 0;
                    for (String s : attr) {
                        strBuilder.append(Integer.parseInt(s));
                        if(j!=attr.length-1){
                            strBuilder.append("|");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};

		//直选单式
		IVenderTicketConverter pl3zxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
				    String[] groups = con.split("\\|");
				    int j = 0;
                    for (String group : groups) {
                        String[] attr = group.split(",");
                        int k = 0;
                        for (String s : attr) {
                            strBuilder.append(Integer.parseInt(s));
                            if(k!=attr.length-1){
                                strBuilder.append(",");
                            }
                            k++;
                        }
                        if(j!=groups.length-1){
                            strBuilder.append("|");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};

		IVenderTicketConverter pl3z3dsz6dshz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
                    String[] attr = con.split(",");
                    int j = 0;
                    for (String s : attr) {
                        strBuilder.append(Integer.parseInt(s));
                        if(j!=attr.length-1){
                            strBuilder.append(",");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};

		//单式
		IVenderTicketConverter pl5ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
                    String[] attr = con.split(",");
                    int j = 0;
                    for (String s : attr) {
                        strBuilder.append(Integer.parseInt(s));
                        if(j!=attr.length-1){
                            strBuilder.append("|");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};

		IVenderTicketConverter pl5fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
                    String[] groups = con.split("\\|");
                    int j = 0;
                    for (String group : groups) {
                        String[] attr = group.split(",");
                        int k = 0;
                        for (String s : attr) {
                            strBuilder.append(Integer.parseInt(s));
                            if(k!=attr.length-1){
                                strBuilder.append(",");
                            }
                            k++;
                        }
                        if(j!=groups.length-1){
                            strBuilder.append("|");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};

		//单式
		IVenderTicketConverter qxcds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
                    String[] attr = con.split(",");
                    int j = 0;
                    for (String s : attr) {
                        strBuilder.append(Integer.parseInt(s));
                        if(j!=attr.length-1){
                            strBuilder.append("|");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};

		IVenderTicketConverter qxcfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
                    String[] groups = con.split("\\|");
                    int j = 0;
                    for (String group : groups) {
                        String[] attr = group.split(",");
                        int k = 0;
                        for (String s : attr) {
                            strBuilder.append(Integer.parseInt(s));
                            if(k!=attr.length-1){
                                strBuilder.append(",");
                            }
                            k++;
                        }
                        if(j!=groups.length-1){
                            strBuilder.append("|");
                        }
                        j++;
                    }
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};


	
		
		IVenderTicketConverter jczq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String con = ticket.getContent().split("-")[1].replace("^", "").replace("(",":").replace(")","").replace("|",";");
				strBuilder.append(con);
				strBuilder.append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		IVenderTicketConverter jczqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {

				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String []cons= ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				StringBuffer stringBuffer=new StringBuffer();
				int i=0;
				for(String con:cons){
					stringBuffer.append(con.split("\\*")[0]).append(":").append(toLotteryTypeMap.get(LotteryType.get(Integer.parseInt(con.split("\\*")[1].split("\\(")[0]))))
					.append(":").append(con.split("\\*")[1].split("\\(")[1].replace(")", ""));
					if(i!=cons.length-1){
						stringBuffer.append(";");
					}
					i++;
		        }
				stringBuffer.append("|").append(playMap.get(playType));
				 return stringBuffer.toString();
		}};
		
	
		
	    playTypeToAdvanceConverterMap.put(PlayType.dlt_dan,dlt);
	    playTypeToAdvanceConverterMap.put(PlayType.dlt_fu,dlt);
	    playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo,dltdt);

        playTypeToAdvanceConverterMap.put(PlayType.p5_dan, pl5ds);
        playTypeToAdvanceConverterMap.put(PlayType.p5_fu, pl5fs);


        playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, pl3zxds);
        playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, pl3z3dsz6dshz);
        playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, pl3z3dsz6dshz);
        playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, pl3zxfs);
        playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, pl3z3dsz6dshz);
        playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, pl3z3dsz6dshz);
        playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, pl3z3dsz6dshz);

        playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, qxcds);
        playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, qxcfs);
		
        playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jczq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jczq);        
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jczq);        
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jczq);        
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jczq);        
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jczq);        
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jczq);       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jczq);      
    	                                                                       
    	                                                                       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9_dan,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70_dan,jczq);   
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247_dan,jczq);  
    	                                                                      
    	                                                                      
    	                                                                      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jczq);    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jczq);      
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jczq);    
    	                                                                       
    	                                                                       
    	                                                                       
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120_dan,jczq);
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9_dan,jczq);  
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247_dan,jczq);


    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,jczq);     
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,jczq);    
    	                                                                    
    	                                                                    
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6_dan,jczq); 
    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11_dan,jczq);
  

    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jczq); 
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jczq);  
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jczq);



    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11_dan,jczq); 
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247_dan,jczq);  


    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_1_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,jczqmix);   
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,jczqmix);  
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,jczqmix); 


    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11_dan,jczqmix);   
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70_dan,jczqmix);
    	playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247_dan,jczqmix); 

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



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11,jczq);  


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11_dan,jczq);



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



    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jczq);  


    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6_dan,jczq);
    	playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11_dan,jczq);  


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
    	
    	
		
    }

   
    
}
