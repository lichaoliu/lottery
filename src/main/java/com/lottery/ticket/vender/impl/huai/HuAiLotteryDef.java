package com.lottery.ticket.vender.impl.huai;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

public class HuAiLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<Integer, String> playCodeMap = new HashMap<Integer, String>();
	/** 彩种转换 */
	protected static Map<Integer,String> toLotteryTypeMap = new HashMap<Integer,String>();
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
				return "20"+phase;
			}
		};
		IPhaseConverter ygPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "201603012801";
			}
		};
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_SFC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.GD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.JC_GUANJUN, ygPhaseConverter);
		phaseConverterMap.put(LotteryType.JC_GUANYAJUN, ygPhaseConverter);
		
		lotteryTypeMap.put(LotteryType.SSQ, "1001");
		lotteryTypeMap.put(LotteryType.F3D, "1003");
		lotteryTypeMap.put(LotteryType.QLC, "1002");
		lotteryTypeMap.put(LotteryType.CJDLT, "1101");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "1108");
		lotteryTypeMap.put(LotteryType.ZC_SFC, "1105");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "1107");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "1106");
		lotteryTypeMap.put(LotteryType.PL5, "1104");
		lotteryTypeMap.put(LotteryType.PL3, "1103");
		lotteryTypeMap.put(LotteryType.QXC, "1102");
		lotteryTypeMap.put(LotteryType.SD_11X5, "2001");
		lotteryTypeMap.put(LotteryType.GD_11X5, "2004");
		lotteryTypeMap.put(LotteryType.JC_GUANJUN, "5001");
		lotteryTypeMap.put(LotteryType.JC_GUANYAJUN, "5002");
		
		toLotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF.getValue(), "4002");
		toLotteryTypeMap.put(LotteryType.JCZQ_BF.getValue(),"4004");
		toLotteryTypeMap.put(LotteryType.JCZQ_JQS.getValue(),"4005");
		toLotteryTypeMap.put(LotteryType.JCZQ_BQC.getValue(),"4003"); 
		toLotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ.getValue(),"4001"); 
		toLotteryTypeMap.put(LotteryType.JCZQ_HHGG.getValue(),"4006");
		
		toLotteryTypeMap.put(LotteryType.JCLQ_SF.getValue(),"4101");
		toLotteryTypeMap.put(LotteryType.JCLQ_RFSF.getValue(),"4102");
		toLotteryTypeMap.put(LotteryType.JCLQ_SFC.getValue(),"4103");
		toLotteryTypeMap.put(LotteryType.JCLQ_DXF.getValue(),"4104");
		toLotteryTypeMap.put(LotteryType.JCLQ_HHGG.getValue(),"4105");
		
		toLotteryTypeMap.put(LotteryType.JC_GUANJUN.getValue(),"5001");
		toLotteryTypeMap.put(LotteryType.JC_GUANYAJUN.getValue(),"5002");
		
		 //子类玩法转换
		 playCodeMap.put(PlayType.dlt_dan.getValue(), "00");
		 playCodeMap.put(PlayType.dlt_fu.getValue(), "00");
		 playCodeMap.put(PlayType.dlt_dantuo.getValue(), "00");
		 playCodeMap.put(PlayType.p5_dan.getValue(), "00");
		 playCodeMap.put(PlayType.p5_fu.getValue(), "00");
		 playCodeMap.put(PlayType.qxc_dan.getValue(), "00");
		 playCodeMap.put(PlayType.qxc_fu.getValue(), "00");
		 
		 playCodeMap.put(PlayType.zc_rjc_dan.getValue(), "00");
		 playCodeMap.put(PlayType.zc_rjc_fu.getValue(), "00");
		 playCodeMap.put(PlayType.zc_rjc_dt.getValue(), "00");
		 playCodeMap.put(PlayType.zc_sfc_dan.getValue(), "00");
		 playCodeMap.put(PlayType.zc_sfc_fu.getValue(), "00");
		 playCodeMap.put(PlayType.zc_jqc_dan.getValue(), "00");
		 playCodeMap.put(PlayType.zc_jqc_fu.getValue(), "00");
		 playCodeMap.put(PlayType.zc_bqc_dan.getValue(), "00");
		 playCodeMap.put(PlayType.zc_bqc_fu.getValue(), "00");
		 
		 playCodeMap.put(PlayType.p3_zhi_dan.getValue(), "01");
		 playCodeMap.put(PlayType.p3_z3_dan.getValue(), "03");
		 playCodeMap.put(PlayType.p3_z6_dan.getValue(), "04");
		 playCodeMap.put(PlayType.p3_zhi_fs.getValue(), "01");
		 playCodeMap.put(PlayType.p3_z3_fs.getValue(), "03");
		 playCodeMap.put(PlayType.p3_z6_fs.getValue(), "04");
		 playCodeMap.put(PlayType.p3_zhi_dt.getValue(), "01");
		 playCodeMap.put(PlayType.p3_z3_dt.getValue(), "02");
		 playCodeMap.put(PlayType.p3_z6_dt.getValue(), "02");
		 
		 
		 
		 playCodeMap.put(PlayType.sd11x5_sr2.getValue(),"02");
		 playCodeMap.put(PlayType.sd11x5_sr3.getValue(),"03");
		 playCodeMap.put(PlayType.sd11x5_sr4.getValue(),"04");
		 playCodeMap.put(PlayType.sd11x5_sr5.getValue(),"05");
		 playCodeMap.put(PlayType.sd11x5_sr6.getValue(),"06");
		 playCodeMap.put(PlayType.sd11x5_sr7.getValue(),"07");
		 playCodeMap.put(PlayType.sd11x5_sr8.getValue(),"08");
		 playCodeMap.put(PlayType.sd11x5_sq1.getValue(),"01");
		 playCodeMap.put(PlayType.sd11x5_sq2.getValue(),"09");
		 playCodeMap.put(PlayType.sd11x5_sq3.getValue(),"10");
		 playCodeMap.put(PlayType.sd11x5_sz2.getValue(),"11");
		 playCodeMap.put(PlayType.sd11x5_sz3.getValue(),"12");
			
		 playCodeMap.put(PlayType.sd11x5_mr2.getValue(),"02");
		 playCodeMap.put(PlayType.sd11x5_mr3.getValue(),"03");
		 playCodeMap.put(PlayType.sd11x5_mr4.getValue(),"04");
		 playCodeMap.put(PlayType.sd11x5_mr5.getValue(),"05");
		 playCodeMap.put(PlayType.sd11x5_mr6.getValue(),"06");
		 playCodeMap.put(PlayType.sd11x5_mr7.getValue(),"07");
		 playCodeMap.put(PlayType.sd11x5_mr8.getValue(),"08");
		 playCodeMap.put(PlayType.sd11x5_mq1.getValue(),"01");
		 playCodeMap.put(PlayType.sd11x5_mq2.getValue(),"09");
		 playCodeMap.put(PlayType.sd11x5_mq3.getValue(),"10");
		 playCodeMap.put(PlayType.sd11x5_mz2.getValue(),"11");
		 playCodeMap.put(PlayType.sd11x5_mz3.getValue(),"12");
			
		 playCodeMap.put(PlayType.sd11x5_dr2.getValue(),"02");
	     playCodeMap.put(PlayType.sd11x5_dr3.getValue(),"03");
	     playCodeMap.put(PlayType.sd11x5_dr4.getValue(),"04");
	     playCodeMap.put(PlayType.sd11x5_dr5.getValue(),"05");
	     playCodeMap.put(PlayType.sd11x5_dr6.getValue(),"06");
	     playCodeMap.put(PlayType.sd11x5_dr7.getValue(),"07");
	     playCodeMap.put(PlayType.sd11x5_dr8.getValue(),"08");
	     playCodeMap.put(PlayType.sd11x5_dz2.getValue(),"11");
	     playCodeMap.put(PlayType.sd11x5_dz3.getValue(),"12");
		 
		 
		 
	     playCodeMap.put(PlayType.gd11x5_sr2.getValue(),"02");
	     playCodeMap.put(PlayType.gd11x5_sr3.getValue(),"03");
	     playCodeMap.put(PlayType.gd11x5_sr4.getValue(),"04");
	     playCodeMap.put(PlayType.gd11x5_sr5.getValue(),"05");
	 	playCodeMap.put(PlayType.gd11x5_sr6.getValue(),"06");
	 	playCodeMap.put(PlayType.gd11x5_sr7.getValue(),"07");
	 	playCodeMap.put(PlayType.gd11x5_sr8.getValue(),"08");
	 	playCodeMap.put(PlayType.gd11x5_sq1.getValue(),"01");
	 	playCodeMap.put(PlayType.gd11x5_sq2.getValue(),"09");
	 	playCodeMap.put(PlayType.gd11x5_sq3.getValue(),"10");
	 	playCodeMap.put(PlayType.gd11x5_sz2.getValue(),"11");
	 	playCodeMap.put(PlayType.gd11x5_sz3.getValue(),"12");
	 	
	 	playCodeMap.put(PlayType.gd11x5_mr2.getValue(),"02");
	 	playCodeMap.put(PlayType.gd11x5_mr3.getValue(),"03");
	 	playCodeMap.put(PlayType.gd11x5_mr4.getValue(),"04");
	 	playCodeMap.put(PlayType.gd11x5_mr5.getValue(),"05");
	 	playCodeMap.put(PlayType.gd11x5_mr6.getValue(),"06");
	 	playCodeMap.put(PlayType.gd11x5_mr7.getValue(),"07");
	 	playCodeMap.put(PlayType.gd11x5_mr8.getValue(),"08");
	 	playCodeMap.put(PlayType.gd11x5_mq1.getValue(),"01");
	 	playCodeMap.put(PlayType.gd11x5_mq2.getValue(),"09");
	 	playCodeMap.put(PlayType.gd11x5_mq3.getValue(),"10");
	 	playCodeMap.put(PlayType.gd11x5_mz2.getValue(),"11");
	 	playCodeMap.put(PlayType.gd11x5_mz3.getValue(),"12");
	 	
	 	playCodeMap.put(PlayType.gd11x5_dr2.getValue(),"02");
	 	playCodeMap.put(PlayType.gd11x5_dr3.getValue(),"03");
	 	playCodeMap.put(PlayType.gd11x5_dr4.getValue(),"04");
	 	playCodeMap.put(PlayType.gd11x5_dr5.getValue(),"05");
	 	playCodeMap.put(PlayType.gd11x5_dr6.getValue(),"06");
	 	playCodeMap.put(PlayType.gd11x5_dr7.getValue(),"07");
	 	playCodeMap.put(PlayType.gd11x5_dr8.getValue(),"08");
	 	playCodeMap.put(PlayType.gd11x5_dz2.getValue(),"11");
	 	playCodeMap.put(PlayType.gd11x5_dz3.getValue(),"12");
	 	
		playCodeMap.put(PlayType.jcguanjun_01.getValue(),"01");
		playCodeMap.put(PlayType.jcguanyajun_01.getValue(),"01");
		 
		//玩法转换
		 playTypeMap.put(PlayType.dlt_dan.getValue(), "01");
		 playTypeMap.put(PlayType.dlt_fu.getValue(), "02");
		 playTypeMap.put(PlayType.dlt_dantuo.getValue(), "03");
		 
		 playTypeMap.put(PlayType.p5_dan.getValue(), "01");
		 playTypeMap.put(PlayType.p5_fu.getValue(), "02");
		 playTypeMap.put(PlayType.qxc_dan.getValue(), "01");
		 playTypeMap.put(PlayType.qxc_fu.getValue(), "02");
		 
		 playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "01");
		 playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "02");
		 playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "04");
		 playTypeMap.put(PlayType.p3_z3_dan.getValue(), "01");
		 playTypeMap.put(PlayType.p3_z6_dan.getValue(), "01");
		 
		 playTypeMap.put(PlayType.p3_z3_fs.getValue(), "02");
		 playTypeMap.put(PlayType.p3_z6_fs.getValue(), "02");
		 playTypeMap.put(PlayType.p3_z3_dt.getValue(), "04");
		 playTypeMap.put(PlayType.p3_z6_dt.getValue(), "04");
		 
		 
		
		 playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "01");
		 playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "02");
		 playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "01");
		 playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "02");
		 playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "01");
		 playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "02");
		 playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "01");
		 playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "02");
		 
		 
		 
		 playTypeMap.put(PlayType.sd11x5_sr2.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sr3.getValue(),"01");
	     playTypeMap.put(PlayType.sd11x5_sr4.getValue(),"01");
	     playTypeMap.put(PlayType.sd11x5_sr5.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sr6.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sr7.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sr8.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sq1.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sq2.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sq3.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sz2.getValue(),"01");
		 playTypeMap.put(PlayType.sd11x5_sz3.getValue(),"01");
			
		 playTypeMap.put(PlayType.sd11x5_mr2.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mr3.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mr4.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mr5.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mr6.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mr7.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mr8.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mq1.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mq2.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mq3.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mz2.getValue(),"02");
		 playTypeMap.put(PlayType.sd11x5_mz3.getValue(),"02");
			
	     playTypeMap.put(PlayType.sd11x5_dr2.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dr3.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dr4.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dr5.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dr6.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dr7.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dr8.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dz2.getValue(),"03");
		 playTypeMap.put(PlayType.sd11x5_dz3.getValue(),"03");
		 
		 
		 playTypeMap.put(PlayType.gd11x5_sr2.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sr3.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sr4.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sr5.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sr6.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sr7.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sr8.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sq1.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sq2.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sq3.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sz2.getValue(),"01");
		 playTypeMap.put(PlayType.gd11x5_sz3.getValue(),"01");
			
		 playTypeMap.put(PlayType.gd11x5_mr2.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mr3.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mr4.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mr5.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mr6.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mr7.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mr8.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mq1.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mq2.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mq3.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mz2.getValue(),"02");
		 playTypeMap.put(PlayType.gd11x5_mz3.getValue(),"02");
			
		 playTypeMap.put(PlayType.gd11x5_dr2.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dr3.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dr4.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dr5.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dr6.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dr7.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dr8.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dz2.getValue(),"03");
		 playTypeMap.put(PlayType.gd11x5_dz3.getValue(),"03");
			
		 
		 
		//竞彩串法
		 playTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "01");
		 playTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "02");
		 playTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "03");
         playTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "06");
         playTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "11");
         playTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "18");
         playTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "28");
         playTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "34");
         playTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "04");
         playTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "05");
         playTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "07");
         playTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "08");
         playTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "09");
         playTypeMap.put(PlayType.jczq_spf_4_11.getValue(), "10");
         playTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "12");
         playTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "13");
     	 playTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "14");
     	 playTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "15");
     	 playTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "16");
     	 playTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "17");
     	 playTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "19");
     	 playTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "20");
     	 playTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "21");
     	 playTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "22");
     	 playTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "23");
     	 playTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "24");
     	 playTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "25");
     	 playTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "26");
     	 playTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "27");
     	 playTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "29");
     	 playTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "30");
     	 playTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "31");
     	 playTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "32");
     	 playTypeMap.put(PlayType.jczq_spf_7_120.getValue(), "33");
     	 playTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "35");
     	 playTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "36");
     	 playTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "37");
     	 playTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "38");
     	 playTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "39");
     	 playTypeMap.put(PlayType.jczq_spf_8_247.getValue(), "40");
     	 
     	 playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(), "01");
     	 playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(), "02");
     	 playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(), "03");
     	 playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(), "06");
     	 playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(), "11");
     	 playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(), "18");
     	 playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(), "28");
     	 playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(), "34");
     	 playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(), "04");
     	 playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(), "05");
     	 playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(), "07");
     	 playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(), "08");
     	 playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(), "09");
     	 playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(), "10");
     	 playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(), "12");
     	 playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(), "13");
     	 playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(), "14");
     	 playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(), "15");
     	 playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(), "16");
     	 playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(), "17");
    	 playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(), "19");
    	 playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(), "20");
    	 playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(), "21");
    	 playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(), "22");
    	 playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(), "23");
    	 playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(), "24");
    	 playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(), "25");
    	 playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(), "26");
    	 playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(), "27");
    	 
    	 playTypeMap.put(PlayType.jczq_bf_1_1.getValue(), "01");
    	 playTypeMap.put(PlayType.jczq_bf_2_1.getValue(), "02");
    	 playTypeMap.put(PlayType.jczq_bf_3_1.getValue(), "03");
    	 playTypeMap.put(PlayType.jczq_bf_4_1.getValue(), "06");
    	 playTypeMap.put(PlayType.jczq_bf_5_1.getValue(), "11");
    	 playTypeMap.put(PlayType.jczq_bf_6_1.getValue(), "18");
    	 playTypeMap.put(PlayType.jczq_bf_7_1.getValue(), "28");
    	 playTypeMap.put(PlayType.jczq_bf_8_1.getValue(), "34");
    	 playTypeMap.put(PlayType.jczq_bf_3_3.getValue(), "04");
    	 playTypeMap.put(PlayType.jczq_bf_3_4.getValue(), "05");
    	 playTypeMap.put(PlayType.jczq_bf_4_4.getValue(), "07");
    	 playTypeMap.put(PlayType.jczq_bf_4_5.getValue(), "08");
    	 playTypeMap.put(PlayType.jczq_bf_4_6.getValue(), "09");
    	 playTypeMap.put(PlayType.jczq_bf_4_11.getValue(), "10");
    	 
    	 
    	 playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "01");
    	 playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "02");
    	 playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "03");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "06");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "11");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "18");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "28");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "34");
    	 playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "04");
    	 playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "05");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "07");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "08");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "09");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "10");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "12");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "13");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "14");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "15");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "16");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "17");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "19");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "20");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "21");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "22");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "23");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "24");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "25");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "26");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "27");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "29");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "30");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "31");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "32");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "33");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "35");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "36");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "37");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "38");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "39");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "40");
    	 
    	 playTypeMap.put(PlayType.jczq_mix_2_1.getValue(), "02");
    	 playTypeMap.put(PlayType.jczq_mix_3_1.getValue(), "03");
    	 playTypeMap.put(PlayType.jczq_mix_4_1.getValue(), "06");
    	 playTypeMap.put(PlayType.jczq_mix_5_1.getValue(), "11");
    	 playTypeMap.put(PlayType.jczq_mix_6_1.getValue(), "18");
    	 playTypeMap.put(PlayType.jczq_mix_7_1.getValue(), "28");
    	 playTypeMap.put(PlayType.jczq_mix_8_1.getValue(), "34");
    	 playTypeMap.put(PlayType.jczq_mix_3_3.getValue(), "04");
    	 playTypeMap.put(PlayType.jczq_mix_3_4.getValue(), "05");
    	 playTypeMap.put(PlayType.jczq_mix_4_4.getValue(), "07");
    	 playTypeMap.put(PlayType.jczq_mix_4_5.getValue(), "08");
    	 playTypeMap.put(PlayType.jczq_mix_4_6.getValue(), "09");
    	 playTypeMap.put(PlayType.jczq_mix_4_11.getValue(), "10");
    	 playTypeMap.put(PlayType.jczq_mix_5_5.getValue(), "12");
    	 playTypeMap.put(PlayType.jczq_mix_5_6.getValue(), "13");
    	 playTypeMap.put(PlayType.jczq_mix_5_10.getValue(), "14");
    	 playTypeMap.put(PlayType.jczq_mix_5_16.getValue(), "15");
    	 playTypeMap.put(PlayType.jczq_mix_5_20.getValue(), "16");
    	 playTypeMap.put(PlayType.jczq_mix_5_26.getValue(), "17");
    	 playTypeMap.put(PlayType.jczq_mix_6_6.getValue(), "19");
    	 playTypeMap.put(PlayType.jczq_mix_6_7.getValue(), "20");
    	 playTypeMap.put(PlayType.jczq_mix_6_15.getValue(), "21");
    	 playTypeMap.put(PlayType.jczq_mix_6_20.getValue(), "22");
    	 playTypeMap.put(PlayType.jczq_mix_6_22.getValue(), "23");
    	 playTypeMap.put(PlayType.jczq_mix_6_35.getValue(), "24");
    	 playTypeMap.put(PlayType.jczq_mix_6_42.getValue(), "25");
    	 playTypeMap.put(PlayType.jczq_mix_6_50.getValue(), "26");
    	 playTypeMap.put(PlayType.jczq_mix_6_57.getValue(), "27");
    	 playTypeMap.put(PlayType.jczq_mix_7_7.getValue(), "29");
    	 playTypeMap.put(PlayType.jczq_mix_7_8.getValue(), "30");
    	 playTypeMap.put(PlayType.jczq_mix_7_21.getValue(), "31");
    	 playTypeMap.put(PlayType.jczq_mix_7_35.getValue(), "32");
    	 playTypeMap.put(PlayType.jczq_mix_7_120.getValue(), "33");
    	 playTypeMap.put(PlayType.jczq_mix_8_8.getValue(), "35");
    	 playTypeMap.put(PlayType.jczq_mix_8_9.getValue(), "36");
    	 playTypeMap.put(PlayType.jczq_mix_8_28.getValue(), "37");
    	 playTypeMap.put(PlayType.jczq_mix_8_56.getValue(), "38");
    	 playTypeMap.put(PlayType.jczq_mix_8_70.getValue(), "39");
    	 playTypeMap.put(PlayType.jczq_mix_8_247.getValue(), "40");
    	 
    	 
    	 playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(), "01");
    	 playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(), "02");
    	 playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(), "03");
    	 playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(), "06");
    	 playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(), "11");
    	 playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(), "18");
    	 playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(), "28");
    	 playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(), "34");
    	 playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(), "04");
    	 playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(), "05");
    	 playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(), "07");
    	 playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(), "08");
    	 playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(), "09");
    	 playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(), "10");
		
		  	 
    	playTypeMap.put(PlayType.jclq_sf_1_1.getValue(),"01");
    	playTypeMap.put(PlayType.jclq_sf_2_1.getValue(),"02");
    	playTypeMap.put(PlayType.jclq_sf_3_1.getValue(),"03");
 		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(),"06");
 		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(),"11");
 		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(),"18");
 		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(),"28");
 		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(),"34");
 		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(),"04");
 		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(),"05");
 		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(),"07");
 		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(),"08");
 		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(),"09");
 		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(),"10");
 		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(),"12");
 		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(),"13");
 		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(),"14");
 		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(),"15");
 		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(),"16");
 		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(),"17");
 		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(),"19");
 		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(),"20");
 		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(),"21");
 		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(),"22");
 		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(),"23");
 		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(),"24");
 		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(),"25");
 		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(),"26");
 		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(),"27");
 		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(),"29");
 		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(),"30");
 		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(),"31");
 		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(),"32");
 		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(),"33");
 		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(),"35");
 		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(),"36");
 		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(),"37");
 		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(),"38");
 		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(),"39");
 		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(),"40");
 		
 		
 		
 		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"01");
 		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"03");
 		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"06");
 		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"11");
 		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"18");
 		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"28");
 		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"34");
 		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"04");
 		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"05");
 		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"07");
 		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"08");
 		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"09");
 		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"10");
 		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"12");
 		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"13");
 		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"14");
 		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"15");
 		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"16");
 		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"17");
 		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"19");
 		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"20");
 		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"21");
 		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"22");
 		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"23");
 		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"24");
 		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"25");
 		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"26");
 		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"27");
 		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"29");
 		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"30");
 		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"31");
 		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"32");
 		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"33");
 		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"35");
 		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"36");
 		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"37");
 		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"38");
 		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"39");
 		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"40");
 		
 		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"01");
 		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"03");
 		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"06");
 		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"11");
 		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"18");
 		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"28");
 		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"34");
 		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"04");
 		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"05");
 		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"07");
 		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"08");
 		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"09");
 		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"10");
 		
 		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(),"01");
 		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(),"03");
 		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(),"06");
 		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(),"11");
 		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(),"18");
 		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(),"28");
 		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(),"34");
 		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(),"04");
 		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(),"05");
 		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(),"07");
 		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(),"08");
 		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(),"09");
 		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(),"10");
 		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(),"12");
 		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(),"13");
 		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(),"14");
 		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(),"15");
 		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(),"16");
 		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(),"17");
 		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(),"19");
 		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(),"20");
 		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(),"21");
 		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(),"22");
 		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(),"23");
 		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(),"24");
 		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(),"25");
 		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(),"26");
 		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(),"27");
 		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(),"29");
 		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(),"30");
 		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(),"31");
 		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(),"32");
 		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(),"33");
 		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(),"35");
 		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(),"36");
 		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(),"37");
 		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(),"38");
 		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(),"39");
 		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(),"40");
 		
 		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"02");
 		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"03");
 		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"06");
 		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"11");
 		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"18");
 		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"28");
 		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"34");
 		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"04");
 		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"05");
 		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"07");
 		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"08");
 		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"09");
 		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"10");
 		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"12");
 		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"13");
 		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"14");
 		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"15");
 		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"16");
 		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"17");
 		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"19");
 		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"20");
 		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"21");
 		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"22");
 		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"23");
 		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"24");
 		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"25");
 		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"26");
 		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"27");
 		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"29");
 		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"30");
 		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"31");
 		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"32");
 		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"33");
 		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"35");
 		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"36");
 		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"37");
 		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"38");
 		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"39");
 		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"40");
 		
 		playTypeMap.put(PlayType.jcguanjun_01.getValue(),"01");
 		playTypeMap.put(PlayType.jcguanyajun_01.getValue(),"01");
 		
    	   IVenderTicketConverter dlt = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String content=ticket.getContent().split("\\-")[1].replace("|","#").replace("^", "");
					return content;
				}};
				
			IVenderTicketConverter dltdt = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String content=ticket.getContent().split("\\-")[1].replace("#","@").replace("|", "#").replace("^", "");
					return content;
				}};
    	 
			IVenderTicketConverter p5ds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer stringBuffer=new StringBuffer();
					String []contents=ticket.getContent().split("\\-")[1].split("\\^");
					int j=0;
					for(String content:contents){
						String []cons=content.split("\\,");
						int i=0;
						for(String con:cons){
							stringBuffer.append(Integer.parseInt(con));
							if(i!=cons.length-1){
								stringBuffer.append(",");
							}
							i++;
						}
						if(j!=contents.length-1){
							stringBuffer.append("~");
						}
						j++;
					}
					return stringBuffer.toString();
			   }};	
			  IVenderTicketConverter p5fs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer stringBuffer=new StringBuffer();
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					int j=0;
					for(String content:contents){
						String []cons=content.split("\\,");
						int i=0;
						for(String con:cons){
							stringBuffer.append(Integer.parseInt(con));
							if(i!=cons.length-1){
								stringBuffer.append(" ");
							}
							i++;
						}
						if(j!=contents.length-1){
							stringBuffer.append(",");
						}
						j++;
					}
					return stringBuffer.toString();
			       
			  }};	
			   IVenderTicketConverter zc = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String []contents=ticket.getContent().split("\\-")[1].split("\\^");
						StringBuffer stringBuffer=new StringBuffer();
						int i=0;
						for(String content:contents){
							stringBuffer.append(content.replace("~","*"));
							if(i!=contents.length-1){
								stringBuffer.append("~");
							}
							i++;
						}
						return stringBuffer.toString();
					}};	
				
					
			IVenderTicketConverter zcfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String content=ticket.getContent().split("\\-")[1].replace("~","*").replace("^", "");
					String []contents=content.split("\\,");
					StringBuffer stringBuffer=new StringBuffer();
					int f=0;
					for(String con:contents){
						if(con.length()>1){
							int j=0;
							for(int i=0;i<con.length();i++){
									stringBuffer.append(con.substring(j,j+1));
									if(j<con.length()-1){
										stringBuffer.append(" ");
									}
									j++;
							}
							
						}else{
							stringBuffer.append(con);
						}
						if(f<contents.length-1){
							stringBuffer.append(",");
						}
						f++;
					}
					return stringBuffer.toString();
				}};		
			
				
			IVenderTicketConverter jc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					StringBuffer stringBuffer=new StringBuffer();
					String con="";
					int i=0;
					for(String content:contents){
						con=content.substring(0, 8);
						stringBuffer.append(con+DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",con))+content.substring(8).replace("(",":[").replace(")", "]"));
						if(i!=contents.length-1){
							stringBuffer.append("/");
						}
						i++;
					}
					return stringBuffer.toString();
				}};
				
				IVenderTicketConverter jclq = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
						StringBuffer stringBuffer=new StringBuffer();
						String con="";
						int i=0;
						for(String content:contents){
							con=content.substring(0, 8);
							stringBuffer.append(con+DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",con))+content.substring(8).split("\\(")[0]+":["+content.substring(8).split("\\(")[1].replace("0", "2").replace("3", "1").replace(")", "]"));
							if(i!=contents.length-1){
								stringBuffer.append("/");
							}
							i++;
						}
						return stringBuffer.toString();
					}};
					
				
				
				IVenderTicketConverter jcmix = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
						StringBuffer stringBuffer=new StringBuffer();
						String con="";
						int i=0;
						String playType="";
						for(String content:contents){
							playType= content.split("\\*")[1].split("\\(")[0];
							con=content.substring(0,8);
							if(Integer.parseInt(playType)==LotteryType.JCLQ_RFSF.getValue()||Integer.parseInt(playType)==LotteryType.JCLQ_SF.getValue()){
								stringBuffer.append(toLotteryTypeMap.get(Integer.parseInt(playType))).append("=").append(con+DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",con))+content.substring(8,11)+":["+content.split("\\*")[1].split("\\(")[1].replace("0", "2").replace("3","1").replace("(",":[").replace(")", "]"));
							}else{
								stringBuffer.append(toLotteryTypeMap.get(Integer.parseInt(playType))).append("=").append(con+DateUtil.getWeekOfDate(DateUtil.parse("yyyyMMdd",con))+content.substring(8,11)+":["+content.split("\\*")[1].split("\\(")[1].replace("(",":[").replace(")", "]"));
								
							}
							if(i!=contents.length-1){
								stringBuffer.append("/");
							}
							i++;
						}
						return stringBuffer.toString();
					}};
					
				  IVenderTicketConverter sd11x5ds = new IVenderTicketConverter() {
							@Override
							public String convert(Ticket ticket) {
								StringBuffer stringBuffer=new StringBuffer();
								String []contents=ticket.getContent().split("\\-")[1].split("\\^");
								int i=0;
								for(String content:contents){
									stringBuffer.append(content);
									if(i!=contents.length-1){
										stringBuffer.append("~");
									}
									i++;
								}
							    return stringBuffer.toString();
				 }};		
					
				 IVenderTicketConverter sd11x5fs = new IVenderTicketConverter() {
						@Override
						public String convert(Ticket ticket) {
							String content=ticket.getContent().split("\\-")[1].replace("^","");
						    return content;
			     }};
			     IVenderTicketConverter sd11x5dwfs = new IVenderTicketConverter() {
						@Override
						public String convert(Ticket ticket) {
							String content=ticket.getContent().split("\\-")[1].replace("^","").replace(",", " ").replace(";", ",");
						    return content;
			     }};
				 IVenderTicketConverter sd11x5dt = new IVenderTicketConverter() {
						@Override
						public String convert(Ticket ticket) {
							String content=ticket.getContent().split("\\-")[1].replace("^","").replace("#", "@");
						    return content;
				}};	
				
				IVenderTicketConverter jcyg = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String content=ticket.getContent().split("\\-")[1].replace("^", "");
						return content;
					}};
					
					
			 playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, dlt);
			 playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, dlt);
			 playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dltdt);
			 playTypeToAdvanceConverterMap.put(PlayType.p5_dan, p5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.p5_fu, p5fs);
			 
			 playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, p5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, p5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, dlt);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, dlt);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, dlt);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, p5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, p5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, p5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, p5ds);
			 
			 playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, p5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, p5fs);
			 
			 playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zc);
			 playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zcfs);
			 playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zc);
			 playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zcfs);
			 
			 playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zc);
			 playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zcfs);
			 playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zc);
			 playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zcfs);		
			 
			 
			 
			 
			 
			 
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1,sd11x5ds);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq2,sd11x5ds);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq3,sd11x5ds);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz2,sd11x5ds);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz3,sd11x5ds);
				
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr2,sd11x5fs);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr3,sd11x5fs);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr4,sd11x5fs);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr5,sd11x5fs);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr6,sd11x5fs);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr7,sd11x5fs);
	         playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr8,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq1,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq2,sd11x5dwfs);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq3,sd11x5dwfs);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz2,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz3,sd11x5fs);
				
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr2,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr3,sd11x5dt);
		     playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr4,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr5,sd11x5dt);
		     playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr6,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr7,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr8,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz2,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz3,sd11x5dt);
			 
			 
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr2,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr3,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr4,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr5,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr6,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr7,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr8,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq1,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq2,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq3,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz2,sd11x5ds);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz3,sd11x5ds);
				
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr2,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr3,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr4,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr5,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr6,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr7,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr8,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq1,sd11x5fs);
		     playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq2,sd11x5dwfs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq3,sd11x5dwfs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz2,sd11x5fs);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz3,sd11x5fs);
				
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr2,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr3,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr4,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr5,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr6,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr7,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr8,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz2,sd11x5dt);
			 playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz3,sd11x5dt);
			 
			 
			 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jc);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jc);
	     	 
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jc);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jc);
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11, jc);
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jc);
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jc);
	    	 

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
	  		
	  		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jclq);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jclq);
	 			    		
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jclq);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jclq);
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11,jc);
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jc);
	 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jc);
	 		 
	 		 
	 		 
	 		 
	 		 
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70,jcmix);
	 		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247,jcmix);
	 		
	 		playTypeToAdvanceConverterMap.put(PlayType.jcguanjun_01,jcyg);
	 		playTypeToAdvanceConverterMap.put(PlayType.jcguanyajun_01,jcyg);
			
	}
	     
	
	public static String getJcLotteryNo(int playType,int lotteryType){
		LotteryType lottType=LotteryType.get(lotteryType);
		if(LotteryType.getJczq().contains(lottType)){
			if(String.valueOf(playType).startsWith("3006")){
				if(playType==PlayType.jczq_spf_1_1.value)
					return "4012";
				else
					return "4002";
			}
			if(String.valueOf(playType).startsWith("3010")){
				if(playType==PlayType.jczq_spfwrq_1_1.value)
					return "4011";
				else
					return "4001";
			}
			
			if(String.valueOf(playType).startsWith("3009")){
				if(playType==PlayType.jczq_bqc_1_1.value)
					return "4013";
				else
					return "4003";
			}
			
			if(String.valueOf(playType).startsWith("3008")){
				if(playType==PlayType.jczq_jqs_1_1.value)
					return "4015";
				else
					return "4005";
			}
			if(String.valueOf(playType).startsWith("3007")){
		     	if(playType==PlayType.jczq_bf_1_1.value)
					return "4014";
				else
					return "4004";
		    }
			if(String.valueOf(playType).startsWith("3011")){
				return "4006";
	       }   
		}else if(LotteryType.getJclq().contains(lottType)){
			if(String.valueOf(playType).startsWith("3001")){
				if(playType==PlayType.jclq_sf_1_1.value)
					return "4111";
				else
					return "4101";
			}
			if(String.valueOf(playType).startsWith("3002")){
				if(playType==PlayType.jclq_rfsf_1_1.value)
					return "4112";
				else
					return "4102";
			}
			
			if(String.valueOf(playType).startsWith("3003")){
				if(playType==PlayType.jclq_sfc_1_1.value)
					return "4113";
				else
					return "4103";
			}
			
			if(String.valueOf(playType).startsWith("3004")){
				if(playType==PlayType.jclq_dxf_1_1.value)
					return "4114";
				else
					return "4104";
			}
			
			if(String.valueOf(playType).startsWith("3005")){
				return "4105";
	       }  
		}else{
			return lotteryTypeMap.get(lottType);
		}
		return null;
	}
	
	/**
	 * 得到子玩法
	 * @param ticket
	 */
	public static String getPlayType(Ticket ticket){
		String playType=null;
		LotteryType lottType=LotteryType.get(ticket.getLotteryType());
		if(LotteryType.getJclq().contains(lottType)||LotteryType.getJczq().contains(lottType)){
			    playType="02";
			if(String.valueOf(ticket.getPlayType()).endsWith("1001")){
				playType="01";
			}
		}else{
			if (ticket.getAddition() == 1) {// 大乐透追加
				playType="01";
			}else{
				playType=HuAiLotteryDef.playCodeMap.get(ticket.getPlayType());
			}
			
		}
		return playType;
	}


}
