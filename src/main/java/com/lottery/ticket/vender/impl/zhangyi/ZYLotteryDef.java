package com.lottery.ticket.vender.impl.zhangyi;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class ZYLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	protected static Map<Integer, String> toLotteryTypeMap = new HashMap<Integer, String>();
	/** 彩种转换 */
	public static Map<String,Integer> toLotteryMap = new HashMap<String,Integer>();
	/** 彩种转换 */
	public static Map<Integer, String> playCodeMap = new HashMap<Integer, String>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	
	protected static Map<String,String> numberMap=new HashMap<String, String>();
	public static Map<String,String> tonumberMap=new HashMap<String, String>();
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
		IPhaseConverter jcPhaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "20000";
			}
		};
		phaseConverterMap.put(LotteryType.SD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.GD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SD_KLPK3, defaultPhaseConverter);
		
		phaseConverterMap.put(LotteryType.JCLQ_SF, jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_RFSF,jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_SFC,jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_DXF, jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCLQ_HHGG,jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_RQ_SPF, jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_BF, jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_JQS,jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_BQC, jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_SPF_WRQ,jcPhaseConverter);
		phaseConverterMap.put(LotteryType.JCZQ_HHGG,jcPhaseConverter);
		
		
		//彩种转换
		
		lotteryTypeMap.put(LotteryType.SD_11X5, "112"); 
		lotteryTypeMap.put(LotteryType.GD_11X5, "121"); 
		lotteryTypeMap.put(LotteryType.SSQ, "118"); 
		lotteryTypeMap.put(LotteryType.F3D, "116"); 
		lotteryTypeMap.put(LotteryType.QLC, "117"); 
		lotteryTypeMap.put(LotteryType.PL3, "100"); 
		lotteryTypeMap.put(LotteryType.PL5, "102"); 
		lotteryTypeMap.put(LotteryType.QXC, "103"); 
		lotteryTypeMap.put(LotteryType.QLC, "117"); 
		lotteryTypeMap.put(LotteryType.CJDLT, "106"); 
		lotteryTypeMap.put(LotteryType.SD_KLPK3, "301"); 
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF, "216");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF, "214");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC, "217");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF, "215");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG, "218");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "210");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "211");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "212");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "213");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ, "209");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG, "208");
		
		toLotteryTypeMap.put(LotteryType.JCLQ_SF.getValue(), "216");
		toLotteryTypeMap.put(LotteryType.JCLQ_RFSF.getValue(), "214");
		toLotteryTypeMap.put(LotteryType.JCLQ_SFC.getValue(), "217");
		toLotteryTypeMap.put(LotteryType.JCLQ_DXF.getValue(), "215");
		toLotteryTypeMap.put(LotteryType.JCLQ_HHGG.getValue(), "218");
		toLotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF.getValue(), "210");
		toLotteryTypeMap.put(LotteryType.JCZQ_BF.getValue(), "211");
		toLotteryTypeMap.put(LotteryType.JCZQ_JQS.getValue(), "212");
		toLotteryTypeMap.put(LotteryType.JCZQ_BQC.getValue(), "213");
		toLotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ.getValue(), "209");
		toLotteryTypeMap.put(LotteryType.JCZQ_HHGG.getValue(), "208");
		
		toLotteryMap.put("216",LotteryType.JCLQ_SF.getValue());
		toLotteryMap.put("214",LotteryType.JCLQ_RFSF.getValue());
		toLotteryMap.put("217",LotteryType.JCLQ_SFC.getValue());
		toLotteryMap.put("215",LotteryType.JCLQ_DXF.getValue());
		toLotteryMap.put("218",LotteryType.JCLQ_HHGG.getValue());
		toLotteryMap.put("210",LotteryType.JCZQ_RQ_SPF.getValue());
		toLotteryMap.put("211",LotteryType.JCZQ_BF.getValue());
		toLotteryMap.put("212",LotteryType.JCZQ_JQS.getValue());
		toLotteryMap.put("213",LotteryType.JCZQ_BQC.getValue());
		toLotteryMap.put("209",LotteryType.JCZQ_SPF_WRQ.getValue());
		toLotteryMap.put("208",LotteryType.JCZQ_HHGG.getValue());
		
		
		//玩法转换
		//双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "0");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "1");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "2");
		
		// 3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "0");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "3");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "3");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "1");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "5");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "4");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "2");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "6");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "14");
		
		playTypeMap.put(PlayType.qlc_dan.getValue(), "0");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "1");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "2");
		
			
		
		
		
		
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "0");
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "3");
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "3");
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "1");
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "5");
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "4");
		
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "2");
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "6");
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "6");
		
		playTypeMap.put(PlayType.p5_dan.getValue(), "0");
		playTypeMap.put(PlayType.p5_fu.getValue(), "1");
		
		playTypeMap.put(PlayType.qxc_dan.getValue(), "0");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "1");
		
		playTypeMap.put(PlayType.dlt_dan.getValue(), "0");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "1");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "2");
		
		//山东11x5
		playTypeMap.put(PlayType.sd11x5_sr2.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sr3.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sr4.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sr5.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sr6.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sr7.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sr8.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sq1.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sq2.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sq3.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sz2.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sz3.getValue(), "0");
		
		playTypeMap.put(PlayType.sd11x5_mr2.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mr3.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mr4.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mr5.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mr6.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mr7.getValue(), "1");
//		playTypeMap.put(PlayType.sd11c5_mr8.getValue(), "1");
//		playTypeMap.put(PlayType.sd11c5_mq1.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mq2.getValue(), "3");
		playTypeMap.put(PlayType.sd11x5_mq3.getValue(), "3");
		playTypeMap.put(PlayType.sd11x5_mz2.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_mz3.getValue(), "1");
		
		playTypeMap.put(PlayType.sd11x5_dr2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dr3.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dr4.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dr5.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dr6.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dr7.getValue(), "2");
//		playTypeMap.put(PlayType.sd11c5_dr8.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_dz2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_dz3.getValue(), "2");
		
		playTypeMap.put(PlayType.sd11x5_sl3.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sl4.getValue(), "0");
		playTypeMap.put(PlayType.sd11x5_sl5.getValue(), "0");
		
		
		//广东11x5
		playTypeMap.put(PlayType.gd11x5_sr2.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sr3.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sr4.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sr5.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sr6.getValue(), "0");
	    playTypeMap.put(PlayType.gd11x5_sr7.getValue(), "0");
	    playTypeMap.put(PlayType.gd11x5_sr8.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sq1.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sq2.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sq3.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sz2.getValue(), "0");
		playTypeMap.put(PlayType.gd11x5_sz3.getValue(), "0");
		                         
		playTypeMap.put(PlayType.gd11x5_mr2.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mr3.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mr4.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mr5.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mr6.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mr7.getValue(), "1");
      //playTypeMap.put(PlayType.gd11c5_mr8.getValue(), "1");
      //playTypeMap.put(PlayType.gd11c5_mq1.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mq2.getValue(), "3");
		playTypeMap.put(PlayType.gd11x5_mq3.getValue(), "3");
		playTypeMap.put(PlayType.gd11x5_mz2.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_mz3.getValue(), "1");
		                         
		playTypeMap.put(PlayType.gd11x5_dr2.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_dr3.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_dr4.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_dr5.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_dr6.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_dr7.getValue(), "2");
  //	playTypeMap.put(PlayType.gd11c5_dr8.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_dz2.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_dz3.getValue(), "2");		
		
		
		playTypeMap.put(PlayType.SD_KLPK3_SR1.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SR2.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SR3.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SR4.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SR5.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SR6.getValue(), "0");
		
		playTypeMap.put(PlayType.SD_KLPK3_MR1.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MR2.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MR3.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MR4.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MR5.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MR6.getValue(), "1");
		
		playTypeMap.put(PlayType.SD_KLPK3_DR2.getValue(), "2");
		playTypeMap.put(PlayType.SD_KLPK3_DR3.getValue(), "2");
		playTypeMap.put(PlayType.SD_KLPK3_DR4.getValue(), "2");
		playTypeMap.put(PlayType.SD_KLPK3_DR5.getValue(), "2");
		playTypeMap.put(PlayType.SD_KLPK3_DR6.getValue(), "2");
		
		playTypeMap.put(PlayType.SD_KLPK3_STH.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_STHS.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SSZ.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SBZ.getValue(), "0");
		playTypeMap.put(PlayType.SD_KLPK3_SDZ.getValue(), "0");
		
		playTypeMap.put(PlayType.SD_KLPK3_MTH.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MTHS.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MSZ.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MBZ.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MDZ.getValue(), "1");
		playTypeMap.put(PlayType.SD_KLPK3_MBX.getValue(), "0");
		
 		
		//竞彩
		playCodeMap.put(PlayType.jczq_spf_1_1.getValue(), "101");
		playCodeMap.put(PlayType.jczq_spf_2_1.getValue(), "102");
		playCodeMap.put(PlayType.jczq_spf_3_1.getValue(), "103");
		playCodeMap.put(PlayType.jczq_spf_4_1.getValue(), "104");
		playCodeMap.put(PlayType.jczq_spf_5_1.getValue(), "105");
		playCodeMap.put(PlayType.jczq_spf_6_1.getValue(), "106");
		playCodeMap.put(PlayType.jczq_spf_7_1.getValue(), "107");
		playCodeMap.put(PlayType.jczq_spf_8_1.getValue(), "108");
		playCodeMap.put(PlayType.jczq_spf_3_3.getValue(), "603");
		playCodeMap.put(PlayType.jczq_spf_3_4.getValue(), "118");
		playCodeMap.put(PlayType.jczq_spf_4_4.getValue(), "604");
		playCodeMap.put(PlayType.jczq_spf_4_5.getValue(), "120");
		playCodeMap.put(PlayType.jczq_spf_4_6.getValue(), "605");
		playCodeMap.put(PlayType.jczq_spf_4_11.getValue(),"121");
		playCodeMap.put(PlayType.jczq_spf_5_5.getValue(), "606");
		playCodeMap.put(PlayType.jczq_spf_5_6.getValue(), "123");
		playCodeMap.put(PlayType.jczq_spf_5_10.getValue(), "607");
		playCodeMap.put(PlayType.jczq_spf_5_16.getValue(), "124");
		playCodeMap.put(PlayType.jczq_spf_5_20.getValue(), "608");
		playCodeMap.put(PlayType.jczq_spf_5_26.getValue(), "125");
		playCodeMap.put(PlayType.jczq_spf_6_6.getValue(), "609");
		playCodeMap.put(PlayType.jczq_spf_6_7.getValue(), "127");
		playCodeMap.put(PlayType.jczq_spf_6_15.getValue(), "610");
		playCodeMap.put(PlayType.jczq_spf_6_20.getValue(), "611");
		playCodeMap.put(PlayType.jczq_spf_6_22.getValue(), "128");
		playCodeMap.put(PlayType.jczq_spf_6_35.getValue(), "612");
		playCodeMap.put(PlayType.jczq_spf_6_42.getValue(), "129");
		playCodeMap.put(PlayType.jczq_spf_6_50.getValue(), "613");
		playCodeMap.put(PlayType.jczq_spf_6_57.getValue(), "602");
		playCodeMap.put(PlayType.jczq_spf_7_7.getValue(), "702");
		playCodeMap.put(PlayType.jczq_spf_7_8.getValue(), "703");
		playCodeMap.put(PlayType.jczq_spf_7_21.getValue(), "704");
		playCodeMap.put(PlayType.jczq_spf_7_35.getValue(), "705");
		playCodeMap.put(PlayType.jczq_spf_7_120.getValue(),"706");
		playCodeMap.put(PlayType.jczq_spf_8_8.getValue(), "802");
		playCodeMap.put(PlayType.jczq_spf_8_9.getValue(), "803");
		playCodeMap.put(PlayType.jczq_spf_8_28.getValue(), "804");
		playCodeMap.put(PlayType.jczq_spf_8_56.getValue(), "805");
		playCodeMap.put(PlayType.jczq_spf_8_70.getValue(), "806");
		playCodeMap.put(PlayType.jczq_spf_8_247.getValue(),"807");
		
		
		playCodeMap.put(PlayType.jczq_bf_1_1.getValue(),"101");
		playCodeMap.put(PlayType.jczq_bf_2_1.getValue(),"102");
		playCodeMap.put(PlayType.jczq_bf_3_1.getValue(),"103");
		playCodeMap.put(PlayType.jczq_bf_4_1.getValue(),"104");
		playCodeMap.put(PlayType.jczq_bf_5_1.getValue(),"105");
		playCodeMap.put(PlayType.jczq_bf_6_1.getValue(),"106");
		playCodeMap.put(PlayType.jczq_bf_7_1.getValue(),"107");
		playCodeMap.put(PlayType.jczq_bf_8_1.getValue(),"108");
		playCodeMap.put(PlayType.jczq_bf_3_3.getValue(),"603");
		playCodeMap.put(PlayType.jczq_bf_3_4.getValue(),"118");
		playCodeMap.put(PlayType.jczq_bf_4_4.getValue(),"604");
		playCodeMap.put(PlayType.jczq_bf_4_5.getValue(),"120");
		playCodeMap.put(PlayType.jczq_bf_4_6.getValue(),"605");
		playCodeMap.put(PlayType.jczq_bf_4_11.getValue(),"121");
		
		
		playCodeMap.put(PlayType.jczq_jqs_1_1.getValue(),"101");
		playCodeMap.put(PlayType.jczq_jqs_2_1.getValue(),"102");
		playCodeMap.put(PlayType.jczq_jqs_3_1.getValue(),"103");
		playCodeMap.put(PlayType.jczq_jqs_4_1.getValue(),"104");
		playCodeMap.put(PlayType.jczq_jqs_5_1.getValue(),"105");
		playCodeMap.put(PlayType.jczq_jqs_6_1.getValue(),"106");
		playCodeMap.put(PlayType.jczq_jqs_7_1.getValue(),"107");
		playCodeMap.put(PlayType.jczq_jqs_8_1.getValue(),"108");
		playCodeMap.put(PlayType.jczq_jqs_3_3.getValue(),"603");
		playCodeMap.put(PlayType.jczq_jqs_3_4.getValue(),"118");
		playCodeMap.put(PlayType.jczq_jqs_4_4.getValue(),"604");
		playCodeMap.put(PlayType.jczq_jqs_4_5.getValue(),"120");
		playCodeMap.put(PlayType.jczq_jqs_4_6.getValue(),"605");
		playCodeMap.put(PlayType.jczq_jqs_4_11.getValue(),"121");
		playCodeMap.put(PlayType.jczq_jqs_5_5.getValue(),"606");
		playCodeMap.put(PlayType.jczq_jqs_5_6.getValue(),"123");
		playCodeMap.put(PlayType.jczq_jqs_5_10.getValue(),"607");
		playCodeMap.put(PlayType.jczq_jqs_5_16.getValue(),"124");
		playCodeMap.put(PlayType.jczq_jqs_5_20.getValue(),"608");
		playCodeMap.put(PlayType.jczq_jqs_5_26.getValue(),"125");
		playCodeMap.put(PlayType.jczq_jqs_6_6.getValue(),"609");
		playCodeMap.put(PlayType.jczq_jqs_6_7.getValue(),"127");
		playCodeMap.put(PlayType.jczq_jqs_6_15.getValue(),"610");
		playCodeMap.put(PlayType.jczq_jqs_6_20.getValue(),"611");
		playCodeMap.put(PlayType.jczq_jqs_6_22.getValue(),"128");
		playCodeMap.put(PlayType.jczq_jqs_6_35.getValue(),"612");
		playCodeMap.put(PlayType.jczq_jqs_6_42.getValue(),"129");
		playCodeMap.put(PlayType.jczq_jqs_6_50.getValue(),"613");
		playCodeMap.put(PlayType.jczq_jqs_6_57.getValue(),"602");
		
		
		playCodeMap.put(PlayType.jczq_bqc_1_1.getValue(),"101");
		playCodeMap.put(PlayType.jczq_bqc_2_1.getValue(),"102");
		playCodeMap.put(PlayType.jczq_bqc_3_1.getValue(),"103");
		playCodeMap.put(PlayType.jczq_bqc_4_1.getValue(),"104");
		playCodeMap.put(PlayType.jczq_bqc_5_1.getValue(),"105");
		playCodeMap.put(PlayType.jczq_bqc_6_1.getValue(),"106");
		playCodeMap.put(PlayType.jczq_bqc_7_1.getValue(),"107");
		playCodeMap.put(PlayType.jczq_bqc_8_1.getValue(),"108");
		playCodeMap.put(PlayType.jczq_bqc_3_3.getValue(),"603");
		playCodeMap.put(PlayType.jczq_bqc_3_4.getValue(),"118");
		playCodeMap.put(PlayType.jczq_bqc_4_4.getValue(),"604");
		playCodeMap.put(PlayType.jczq_bqc_4_5.getValue(),"120");
		playCodeMap.put(PlayType.jczq_bqc_4_6.getValue(),"605");
		playCodeMap.put(PlayType.jczq_bqc_4_11.getValue(),"121");
		
		
		playCodeMap.put(PlayType.jczq_spfwrq_1_1.getValue(),"101");
		playCodeMap.put(PlayType.jczq_spfwrq_2_1.getValue(),"102");
		playCodeMap.put(PlayType.jczq_spfwrq_3_1.getValue(),"103");
		playCodeMap.put(PlayType.jczq_spfwrq_4_1.getValue(),"104");
		playCodeMap.put(PlayType.jczq_spfwrq_5_1.getValue(),"105");
		playCodeMap.put(PlayType.jczq_spfwrq_6_1.getValue(),"106");
		playCodeMap.put(PlayType.jczq_spfwrq_7_1.getValue(),"107");
		playCodeMap.put(PlayType.jczq_spfwrq_8_1.getValue(),"108");
		playCodeMap.put(PlayType.jczq_spfwrq_3_3.getValue(),"603");
		playCodeMap.put(PlayType.jczq_spfwrq_3_4.getValue(),"118");
		playCodeMap.put(PlayType.jczq_spfwrq_4_4.getValue(),"604");
		playCodeMap.put(PlayType.jczq_spfwrq_4_5.getValue(),"120");
		playCodeMap.put(PlayType.jczq_spfwrq_4_6.getValue(),"605");
		playCodeMap.put(PlayType.jczq_spfwrq_4_11.getValue(),"121");
		playCodeMap.put(PlayType.jczq_spfwrq_5_5.getValue(),"606");
		playCodeMap.put(PlayType.jczq_spfwrq_5_6.getValue(),"123");
		playCodeMap.put(PlayType.jczq_spfwrq_5_10.getValue(),"607");
		playCodeMap.put(PlayType.jczq_spfwrq_5_16.getValue(),"124");
		playCodeMap.put(PlayType.jczq_spfwrq_5_20.getValue(),"608");
		playCodeMap.put(PlayType.jczq_spfwrq_5_26.getValue(),"125");
		playCodeMap.put(PlayType.jczq_spfwrq_6_6.getValue(),"609");
		playCodeMap.put(PlayType.jczq_spfwrq_6_7.getValue(),"127");
		playCodeMap.put(PlayType.jczq_spfwrq_6_15.getValue(),"610");
		playCodeMap.put(PlayType.jczq_spfwrq_6_20.getValue(),"611");
		playCodeMap.put(PlayType.jczq_spfwrq_6_22.getValue(),"128");
		playCodeMap.put(PlayType.jczq_spfwrq_6_35.getValue(),"612");
		playCodeMap.put(PlayType.jczq_spfwrq_6_42.getValue(),"129");
		playCodeMap.put(PlayType.jczq_spfwrq_6_50.getValue(),"613");
		playCodeMap.put(PlayType.jczq_spfwrq_6_57.getValue(),"602");
		playCodeMap.put(PlayType.jczq_spfwrq_7_7.getValue(),"702");
		playCodeMap.put(PlayType.jczq_spfwrq_7_8.getValue(),"703");
		playCodeMap.put(PlayType.jczq_spfwrq_7_21.getValue(),"704");
		playCodeMap.put(PlayType.jczq_spfwrq_7_35.getValue(),"705");
		playCodeMap.put(PlayType.jczq_spfwrq_7_120.getValue(),"706");
		playCodeMap.put(PlayType.jczq_spfwrq_8_8.getValue(),"802");
		playCodeMap.put(PlayType.jczq_spfwrq_8_9.getValue(),"803");
		playCodeMap.put(PlayType.jczq_spfwrq_8_28.getValue(),"804");
		playCodeMap.put(PlayType.jczq_spfwrq_8_56.getValue(),"805");
		playCodeMap.put(PlayType.jczq_spfwrq_8_70.getValue(),"806");
		playCodeMap.put(PlayType.jczq_spfwrq_8_247.getValue(),"807");
		
		playCodeMap.put(PlayType.jczq_mix_2_1.getValue(),"102");
		playCodeMap.put(PlayType.jczq_mix_3_1.getValue(),"103");
		playCodeMap.put(PlayType.jczq_mix_4_1.getValue(),"104");
		playCodeMap.put(PlayType.jczq_mix_5_1.getValue(),"105");
		playCodeMap.put(PlayType.jczq_mix_6_1.getValue(),"106");
		playCodeMap.put(PlayType.jczq_mix_7_1.getValue(),"107");
		playCodeMap.put(PlayType.jczq_mix_8_1.getValue(),"108");
		playCodeMap.put(PlayType.jczq_mix_3_3.getValue(),"603");
		playCodeMap.put(PlayType.jczq_mix_3_4.getValue(),"118");
		playCodeMap.put(PlayType.jczq_mix_4_4.getValue(),"604");
		playCodeMap.put(PlayType.jczq_mix_4_5.getValue(),"120");
		playCodeMap.put(PlayType.jczq_mix_4_6.getValue(),"605");
		playCodeMap.put(PlayType.jczq_mix_4_11.getValue(),"121");
		playCodeMap.put(PlayType.jczq_mix_5_5.getValue(),"606");
		playCodeMap.put(PlayType.jczq_mix_5_6.getValue(),"123");
		playCodeMap.put(PlayType.jczq_mix_5_10.getValue(),"607");
		playCodeMap.put(PlayType.jczq_mix_5_16.getValue(),"124");
		playCodeMap.put(PlayType.jczq_mix_5_20.getValue(),"608");
		playCodeMap.put(PlayType.jczq_mix_5_26.getValue(),"125");
		playCodeMap.put(PlayType.jczq_mix_6_6.getValue(),"609");
		playCodeMap.put(PlayType.jczq_mix_6_7.getValue(),"127");
		playCodeMap.put(PlayType.jczq_mix_6_15.getValue(),"610");
		playCodeMap.put(PlayType.jczq_mix_6_20.getValue(),"611");
		playCodeMap.put(PlayType.jczq_mix_6_22.getValue(),"128");
		playCodeMap.put(PlayType.jczq_mix_6_35.getValue(),"612");
		playCodeMap.put(PlayType.jczq_mix_6_42.getValue(),"129");
		playCodeMap.put(PlayType.jczq_mix_6_50.getValue(),"613");
		playCodeMap.put(PlayType.jczq_mix_6_57.getValue(),"602");
		playCodeMap.put(PlayType.jczq_mix_7_7.getValue(),"702");
		playCodeMap.put(PlayType.jczq_mix_7_8.getValue(),"703");
		playCodeMap.put(PlayType.jczq_mix_7_21.getValue(),"704");
		playCodeMap.put(PlayType.jczq_mix_7_35.getValue(),"705");
		playCodeMap.put(PlayType.jczq_mix_7_120.getValue(),"706");
		playCodeMap.put(PlayType.jczq_mix_8_8.getValue(),"802");
		playCodeMap.put(PlayType.jczq_mix_8_9.getValue(),"803");
		playCodeMap.put(PlayType.jczq_mix_8_28.getValue(),"804");
		playCodeMap.put(PlayType.jczq_mix_8_56.getValue(),"805");
		playCodeMap.put(PlayType.jczq_mix_8_70.getValue(),"806");
		playCodeMap.put(PlayType.jczq_mix_8_247.getValue(),"807");
		
		playCodeMap.put(PlayType.jclq_sf_1_1.getValue(),"02");
		playCodeMap.put(PlayType.jclq_sf_2_1.getValue(),"03");
		playCodeMap.put(PlayType.jclq_sf_3_1.getValue(),"04");
		playCodeMap.put(PlayType.jclq_sf_4_1.getValue(),"05");
		playCodeMap.put(PlayType.jclq_sf_5_1.getValue(),"06");
		playCodeMap.put(PlayType.jclq_sf_6_1.getValue(),"07");
		playCodeMap.put(PlayType.jclq_sf_7_1.getValue(),"08");
		playCodeMap.put(PlayType.jclq_sf_8_1.getValue(),"09");
		playCodeMap.put(PlayType.jclq_sf_3_3.getValue(),"41");
		playCodeMap.put(PlayType.jclq_sf_3_4.getValue(),"42");
		playCodeMap.put(PlayType.jclq_sf_4_4.getValue(),"51");
		playCodeMap.put(PlayType.jclq_sf_4_5.getValue(),"52");
		playCodeMap.put(PlayType.jclq_sf_4_6.getValue(),"53");
		playCodeMap.put(PlayType.jclq_sf_4_11.getValue(),"54");
		playCodeMap.put(PlayType.jclq_sf_5_5.getValue(),"61");
		playCodeMap.put(PlayType.jclq_sf_5_6.getValue(),"62");
		playCodeMap.put(PlayType.jclq_sf_5_10.getValue(),"63");
		playCodeMap.put(PlayType.jclq_sf_5_16.getValue(),"64");
		playCodeMap.put(PlayType.jclq_sf_5_20.getValue(),"65");
		playCodeMap.put(PlayType.jclq_sf_5_26.getValue(),"66");
		playCodeMap.put(PlayType.jclq_sf_6_6.getValue(),"71");
		playCodeMap.put(PlayType.jclq_sf_6_7.getValue(),"72");
		playCodeMap.put(PlayType.jclq_sf_6_15.getValue(),"73");
		playCodeMap.put(PlayType.jclq_sf_6_20.getValue(),"74");
		playCodeMap.put(PlayType.jclq_sf_6_22.getValue(),"75");
		playCodeMap.put(PlayType.jclq_sf_6_35.getValue(),"76");
		playCodeMap.put(PlayType.jclq_sf_6_42.getValue(),"77");
		playCodeMap.put(PlayType.jclq_sf_6_50.getValue(),"78");
		playCodeMap.put(PlayType.jclq_sf_6_57.getValue(),"79");
		playCodeMap.put(PlayType.jclq_sf_7_7.getValue(),"81");
		playCodeMap.put(PlayType.jclq_sf_7_8.getValue(),"82");
		playCodeMap.put(PlayType.jclq_sf_7_21.getValue(),"83");
		playCodeMap.put(PlayType.jclq_sf_7_35.getValue(),"84");
		playCodeMap.put(PlayType.jclq_sf_7_120.getValue(),"85");
		playCodeMap.put(PlayType.jclq_sf_8_8.getValue(),"91");
		playCodeMap.put(PlayType.jclq_sf_8_9.getValue(),"92");
		playCodeMap.put(PlayType.jclq_sf_8_28.getValue(),"93");
		playCodeMap.put(PlayType.jclq_sf_8_56.getValue(),"94");
		playCodeMap.put(PlayType.jclq_sf_8_70.getValue(),"95");
		playCodeMap.put(PlayType.jclq_sf_8_247.getValue(),"96");
		
		
		
		playCodeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"02");
		playCodeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"03");
		playCodeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"04");
		playCodeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"05");
		playCodeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"06");
		playCodeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"07");
		playCodeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"08");
		playCodeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"09");
		playCodeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"41");
		playCodeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"42");
		playCodeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"51");
		playCodeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"52");
		playCodeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"53");
		playCodeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"54");
		playCodeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"61");
		playCodeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"62");
		playCodeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"63");
		playCodeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"64");
		playCodeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"65");
		playCodeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"66");
		playCodeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"71");
		playCodeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"72");
		playCodeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"73");
		playCodeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"74");
		playCodeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"75");
		playCodeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"76");
		playCodeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"77");
		playCodeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"78");
		playCodeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"79");
		playCodeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"81");
		playCodeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"82");
		playCodeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"83");
		playCodeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"84");
		playCodeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"85");
		playCodeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"91");
		playCodeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"92");
		playCodeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"93");
		playCodeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"94");
		playCodeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"95");
		playCodeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"96");
		
		playCodeMap.put(PlayType.jclq_sfc_1_1.getValue(),"02");
		playCodeMap.put(PlayType.jclq_sfc_2_1.getValue(),"03");
		playCodeMap.put(PlayType.jclq_sfc_3_1.getValue(),"04");
		playCodeMap.put(PlayType.jclq_sfc_4_1.getValue(),"05");
		playCodeMap.put(PlayType.jclq_sfc_5_1.getValue(),"06");
		playCodeMap.put(PlayType.jclq_sfc_6_1.getValue(),"07");
		playCodeMap.put(PlayType.jclq_sfc_7_1.getValue(),"08");
		playCodeMap.put(PlayType.jclq_sfc_8_1.getValue(),"09");
		playCodeMap.put(PlayType.jclq_sfc_3_3.getValue(),"41");
		playCodeMap.put(PlayType.jclq_sfc_3_4.getValue(),"42");
		playCodeMap.put(PlayType.jclq_sfc_4_4.getValue(),"51");
		playCodeMap.put(PlayType.jclq_sfc_4_5.getValue(),"52");
		playCodeMap.put(PlayType.jclq_sfc_4_6.getValue(),"53");
		playCodeMap.put(PlayType.jclq_sfc_4_11.getValue(),"54");
		
		playCodeMap.put(PlayType.jclq_dxf_1_1.getValue(),"02");
		playCodeMap.put(PlayType.jclq_dxf_2_1.getValue(),"03");
		playCodeMap.put(PlayType.jclq_dxf_3_1.getValue(),"04");
		playCodeMap.put(PlayType.jclq_dxf_4_1.getValue(),"05");
		playCodeMap.put(PlayType.jclq_dxf_5_1.getValue(),"06");
		playCodeMap.put(PlayType.jclq_dxf_6_1.getValue(),"07");
		playCodeMap.put(PlayType.jclq_dxf_7_1.getValue(),"08");
		playCodeMap.put(PlayType.jclq_dxf_8_1.getValue(),"09");
		playCodeMap.put(PlayType.jclq_dxf_3_3.getValue(),"41");
		playCodeMap.put(PlayType.jclq_dxf_3_4.getValue(),"42");
		playCodeMap.put(PlayType.jclq_dxf_4_4.getValue(),"51");
		playCodeMap.put(PlayType.jclq_dxf_4_5.getValue(),"52");
		playCodeMap.put(PlayType.jclq_dxf_4_6.getValue(),"53");
		playCodeMap.put(PlayType.jclq_dxf_4_11.getValue(),"54");
		playCodeMap.put(PlayType.jclq_dxf_5_5.getValue(),"61");
		playCodeMap.put(PlayType.jclq_dxf_5_6.getValue(),"62");
		playCodeMap.put(PlayType.jclq_dxf_5_10.getValue(),"63");
		playCodeMap.put(PlayType.jclq_dxf_5_16.getValue(),"64");
		playCodeMap.put(PlayType.jclq_dxf_5_20.getValue(),"65");
		playCodeMap.put(PlayType.jclq_dxf_5_26.getValue(),"66");
		playCodeMap.put(PlayType.jclq_dxf_6_6.getValue(),"71");
		playCodeMap.put(PlayType.jclq_dxf_6_7.getValue(),"72");
		playCodeMap.put(PlayType.jclq_dxf_6_15.getValue(),"73");
		playCodeMap.put(PlayType.jclq_dxf_6_20.getValue(),"74");
		playCodeMap.put(PlayType.jclq_dxf_6_22.getValue(),"75");
		playCodeMap.put(PlayType.jclq_dxf_6_35.getValue(),"76");
		playCodeMap.put(PlayType.jclq_dxf_6_42.getValue(),"77");
		playCodeMap.put(PlayType.jclq_dxf_6_50.getValue(),"78");
		playCodeMap.put(PlayType.jclq_dxf_6_57.getValue(),"79");
		playCodeMap.put(PlayType.jclq_dxf_7_7.getValue(),"81");
		playCodeMap.put(PlayType.jclq_dxf_7_8.getValue(),"82");
		playCodeMap.put(PlayType.jclq_dxf_7_21.getValue(),"83");
		playCodeMap.put(PlayType.jclq_dxf_7_35.getValue(),"84");
		playCodeMap.put(PlayType.jclq_dxf_7_120.getValue(),"85");
		playCodeMap.put(PlayType.jclq_dxf_8_8.getValue(),"91");
		playCodeMap.put(PlayType.jclq_dxf_8_9.getValue(),"92");
		playCodeMap.put(PlayType.jclq_dxf_8_28.getValue(),"93");
		playCodeMap.put(PlayType.jclq_dxf_8_56.getValue(),"94");
		playCodeMap.put(PlayType.jclq_dxf_8_70.getValue(),"95");
		playCodeMap.put(PlayType.jclq_dxf_8_247.getValue(),"96");
		
		playCodeMap.put(PlayType.jclq_mix_2_1.getValue(),"03");
		playCodeMap.put(PlayType.jclq_mix_3_1.getValue(),"04");
		playCodeMap.put(PlayType.jclq_mix_4_1.getValue(),"05");
		playCodeMap.put(PlayType.jclq_mix_5_1.getValue(),"06");
		playCodeMap.put(PlayType.jclq_mix_6_1.getValue(),"07");
		playCodeMap.put(PlayType.jclq_mix_7_1.getValue(),"08");
		playCodeMap.put(PlayType.jclq_mix_8_1.getValue(),"09");
		playCodeMap.put(PlayType.jclq_mix_3_3.getValue(),"41");
		playCodeMap.put(PlayType.jclq_mix_3_4.getValue(),"42");
		playCodeMap.put(PlayType.jclq_mix_4_4.getValue(),"51");
		playCodeMap.put(PlayType.jclq_mix_4_5.getValue(),"52");
		playCodeMap.put(PlayType.jclq_mix_4_6.getValue(),"53");
		playCodeMap.put(PlayType.jclq_mix_4_11.getValue(),"54");
		playCodeMap.put(PlayType.jclq_mix_5_5.getValue(),"61");
		playCodeMap.put(PlayType.jclq_mix_5_6.getValue(),"62");
		playCodeMap.put(PlayType.jclq_mix_5_10.getValue(),"63");
		playCodeMap.put(PlayType.jclq_mix_5_16.getValue(),"64");
		playCodeMap.put(PlayType.jclq_mix_5_20.getValue(),"65");
		playCodeMap.put(PlayType.jclq_mix_5_26.getValue(),"66");
		playCodeMap.put(PlayType.jclq_mix_6_6.getValue(),"71");
		playCodeMap.put(PlayType.jclq_mix_6_7.getValue(),"72");
		playCodeMap.put(PlayType.jclq_mix_6_15.getValue(),"73");
		playCodeMap.put(PlayType.jclq_mix_6_20.getValue(),"74");
		playCodeMap.put(PlayType.jclq_mix_6_22.getValue(),"75");
		playCodeMap.put(PlayType.jclq_mix_6_35.getValue(),"76");
		playCodeMap.put(PlayType.jclq_mix_6_42.getValue(),"77");
	    playCodeMap.put(PlayType.jclq_mix_6_50.getValue(),"78");
		playCodeMap.put(PlayType.jclq_mix_6_57.getValue(),"79");
		playCodeMap.put(PlayType.jclq_mix_7_7.getValue(),"81");
		playCodeMap.put(PlayType.jclq_mix_7_8.getValue(),"82");
		playCodeMap.put(PlayType.jclq_mix_7_21.getValue(),"83");
		playCodeMap.put(PlayType.jclq_mix_7_35.getValue(),"84");
		playCodeMap.put(PlayType.jclq_mix_7_120.getValue(),"85");
		playCodeMap.put(PlayType.jclq_mix_8_8.getValue(),"91");
		playCodeMap.put(PlayType.jclq_mix_8_9.getValue(),"92");
		playCodeMap.put(PlayType.jclq_mix_8_28.getValue(),"93");
		playCodeMap.put(PlayType.jclq_mix_8_56.getValue(),"94");
		playCodeMap.put(PlayType.jclq_mix_8_70.getValue(),"95");
		playCodeMap.put(PlayType.jclq_mix_8_247.getValue(),"96");
		
		
		playCodeMap.put(PlayType.ssq_dan.getValue(), "0");
 		playCodeMap.put(PlayType.ssq_fs.getValue(), "0");
 		playCodeMap.put(PlayType.ssq_dt.getValue(), "0");
 		
 		playCodeMap.put(PlayType.qlc_dan.getValue(), "0");
 		playCodeMap.put(PlayType.qlc_fs.getValue(), "0");
 		playCodeMap.put(PlayType.qlc_dt.getValue(), "0");
 		
 		playCodeMap.put(PlayType.dlt_dan.getValue(), "0");
 		playCodeMap.put(PlayType.dlt_fu.getValue(), "0");
 		playCodeMap.put(PlayType.dlt_dantuo.getValue(), "0");
 		
 		
 		//3D
 		playCodeMap.put(PlayType.d3_zhi_dan.getValue(), "0");
 		playCodeMap.put(PlayType.d3_z3_dan.getValue(), "0");
 		playCodeMap.put(PlayType.d3_z6_dan.getValue(), "0");
 		playCodeMap.put(PlayType.d3_zhi_fs.getValue(), "0");
 		playCodeMap.put(PlayType.d3_z3_fs.getValue(), "0");
 		playCodeMap.put(PlayType.d3_z6_fs.getValue(), "0");
 		playCodeMap.put(PlayType.d3_zhi_hz.getValue(), "0");
 		playCodeMap.put(PlayType.d3_z3_hz.getValue(), "0");
 		playCodeMap.put(PlayType.d3_z6_hz.getValue(), "0");
 		
 		playCodeMap.put(PlayType.p3_zhi_dan.getValue(), "0");
 		playCodeMap.put(PlayType.p3_z3_dan.getValue(), "0");
		playCodeMap.put(PlayType.p3_z6_dan.getValue(), "0");
		playCodeMap.put(PlayType.p3_zhi_fs.getValue(), "0");
		playCodeMap.put(PlayType.p3_z3_fs.getValue(), "0");
		playCodeMap.put(PlayType.p3_z6_fs.getValue(), "0");
		
		playCodeMap.put(PlayType.p3_zhi_dt.getValue(), "0");
		playCodeMap.put(PlayType.p3_z3_dt.getValue(), "0");
		playCodeMap.put(PlayType.p3_z6_dt.getValue(), "0");
		
		playCodeMap.put(PlayType.p5_dan.getValue(), "0");
		playCodeMap.put(PlayType.p5_fu.getValue(), "0");
		
		playCodeMap.put(PlayType.qxc_dan.getValue(), "0");
		playCodeMap.put(PlayType.qxc_fu.getValue(), "0");
		
 		
 		//山东11x5
		playCodeMap.put(PlayType.sd11x5_sr2.getValue(), "2");
 		playCodeMap.put(PlayType.sd11x5_sr3.getValue(), "3");
 		playCodeMap.put(PlayType.sd11x5_sr4.getValue(), "4");
 		playCodeMap.put(PlayType.sd11x5_sr5.getValue(), "5");
		playCodeMap.put(PlayType.sd11x5_sr6.getValue(), "6");
		playCodeMap.put(PlayType.sd11x5_sr7.getValue(), "7");
		playCodeMap.put(PlayType.sd11x5_sr8.getValue(), "8");
		playCodeMap.put(PlayType.sd11x5_sq1.getValue(), "1");
		playCodeMap.put(PlayType.sd11x5_sq2.getValue(), "9");
		playCodeMap.put(PlayType.sd11x5_sq3.getValue(), "10");
		playCodeMap.put(PlayType.sd11x5_sz2.getValue(), "11");
		playCodeMap.put(PlayType.sd11x5_sz3.getValue(), "12");
		
		playCodeMap.put(PlayType.sd11x5_mr2.getValue(), "2");
		playCodeMap.put(PlayType.sd11x5_mr3.getValue(), "3");
		playCodeMap.put(PlayType.sd11x5_mr4.getValue(), "4");
		playCodeMap.put(PlayType.sd11x5_mr5.getValue(), "5");
		playCodeMap.put(PlayType.sd11x5_mr6.getValue(), "6");
		playCodeMap.put(PlayType.sd11x5_mr7.getValue(), "7");
//		playCodeMap.put(PlayType.sd11c5_mr8.getValue(), "8");
//		playCodeMap.put(PlayType.sd11c5_mq1.getValue(), "1");
		playCodeMap.put(PlayType.sd11x5_mq2.getValue(), "9");
		playCodeMap.put(PlayType.sd11x5_mq3.getValue(), "10");
		playCodeMap.put(PlayType.sd11x5_mz2.getValue(), "11");
		playCodeMap.put(PlayType.sd11x5_mz3.getValue(), "12");
		
		playCodeMap.put(PlayType.sd11x5_dr2.getValue(), "2");
		playCodeMap.put(PlayType.sd11x5_dr3.getValue(), "3");
		playCodeMap.put(PlayType.sd11x5_dr4.getValue(), "4");
		playCodeMap.put(PlayType.sd11x5_dr5.getValue(), "5");
		playCodeMap.put(PlayType.sd11x5_dr6.getValue(), "6");
		playCodeMap.put(PlayType.sd11x5_dr7.getValue(), "7");
//		playCodeMap.put(PlayType.sd11c5_dr8.getValue(), "8");
		playCodeMap.put(PlayType.sd11x5_dz2.getValue(), "11");
		playCodeMap.put(PlayType.sd11x5_dz3.getValue(), "12");
		
		playCodeMap.put(PlayType.sd11x5_sl3.getValue(), "14");
		playCodeMap.put(PlayType.sd11x5_sl4.getValue(), "15");
		playCodeMap.put(PlayType.sd11x5_sl5.getValue(), "16");
		
		
		//广东11x5
		playCodeMap.put(PlayType.gd11x5_sr2.getValue(), "2"); 
		playCodeMap.put(PlayType.gd11x5_sr3.getValue(), "3"); 
		playCodeMap.put(PlayType.gd11x5_sr4.getValue(), "4"); 
		playCodeMap.put(PlayType.gd11x5_sr5.getValue(), "5"); 
		playCodeMap.put(PlayType.gd11x5_sr6.getValue(), "6"); 
		playCodeMap.put(PlayType.gd11x5_sr7.getValue(), "7"); 
	    playCodeMap.put(PlayType.gd11x5_sr8.getValue(), "8"); 
	    playCodeMap.put(PlayType.gd11x5_sq1.getValue(), "1"); 
		playCodeMap.put(PlayType.gd11x5_sq2.getValue(), "9"); 
		playCodeMap.put(PlayType.gd11x5_sq3.getValue(), "10");
		playCodeMap.put(PlayType.gd11x5_sz2.getValue(), "11");
		playCodeMap.put(PlayType.gd11x5_sz3.getValue(), "12");
		                                                      
		playCodeMap.put(PlayType.gd11x5_mr2.getValue(), "2"); 
		playCodeMap.put(PlayType.gd11x5_mr3.getValue(), "3"); 
		playCodeMap.put(PlayType.gd11x5_mr4.getValue(), "4"); 
		playCodeMap.put(PlayType.gd11x5_mr5.getValue(), "5"); 
		playCodeMap.put(PlayType.gd11x5_mr6.getValue(), "6"); 
		playCodeMap.put(PlayType.gd11x5_mr7.getValue(), "7"); 
//		playCodeMap.put(PlayType.gd11c5_mr8.getValue(), "8"); 
//		playCodeMap.put(PlayType.gd11c5_mq1.getValue(), "1"); 
		playCodeMap.put(PlayType.gd11x5_mq2.getValue(), "9"); 
		playCodeMap.put(PlayType.gd11x5_mq3.getValue(), "10");
		playCodeMap.put(PlayType.gd11x5_mz2.getValue(), "11");
		playCodeMap.put(PlayType.gd11x5_mz3.getValue(), "12");
		                                                      
		playCodeMap.put(PlayType.gd11x5_dr2.getValue(), "2"); 
		playCodeMap.put(PlayType.gd11x5_dr3.getValue(), "3"); 
		playCodeMap.put(PlayType.gd11x5_dr4.getValue(), "4"); 
		playCodeMap.put(PlayType.gd11x5_dr5.getValue(), "5"); 
		playCodeMap.put(PlayType.gd11x5_dr6.getValue(), "6"); 
		playCodeMap.put(PlayType.gd11x5_dr7.getValue(), "7"); 
//		playCodeMap.put(PlayType.gd11c5_dr8.getValue(), "8"); 
		playCodeMap.put(PlayType.gd11x5_dz2.getValue(), "11");
		playCodeMap.put(PlayType.gd11x5_dz3.getValue(), "12");
	
		
		playCodeMap.put(PlayType.SD_KLPK3_SR1.getValue(), "11");
		playCodeMap.put(PlayType.SD_KLPK3_SR2.getValue(), "12");
		playCodeMap.put(PlayType.SD_KLPK3_SR3.getValue(), "13");
		playCodeMap.put(PlayType.SD_KLPK3_SR4.getValue(), "14");
		playCodeMap.put(PlayType.SD_KLPK3_SR5.getValue(), "15");
		playCodeMap.put(PlayType.SD_KLPK3_SR6.getValue(), "16");
		
		playCodeMap.put(PlayType.SD_KLPK3_MR1.getValue(), "11");
		playCodeMap.put(PlayType.SD_KLPK3_MR2.getValue(), "12");
		playCodeMap.put(PlayType.SD_KLPK3_MR3.getValue(), "13");
		playCodeMap.put(PlayType.SD_KLPK3_MR4.getValue(), "14");
		playCodeMap.put(PlayType.SD_KLPK3_MR5.getValue(), "15");
		playCodeMap.put(PlayType.SD_KLPK3_MR6.getValue(), "16");
		
		playCodeMap.put(PlayType.SD_KLPK3_DR2.getValue(), "12");
		playCodeMap.put(PlayType.SD_KLPK3_DR3.getValue(), "13");
		playCodeMap.put(PlayType.SD_KLPK3_DR4.getValue(), "14");
		playCodeMap.put(PlayType.SD_KLPK3_DR5.getValue(), "15");
		playCodeMap.put(PlayType.SD_KLPK3_DR6.getValue(), "16");
		
		playCodeMap.put(PlayType.SD_KLPK3_STH.getValue(), "2");
		playCodeMap.put(PlayType.SD_KLPK3_STHS.getValue(), "4");
		playCodeMap.put(PlayType.SD_KLPK3_SSZ.getValue(), "6");
		playCodeMap.put(PlayType.SD_KLPK3_SBZ.getValue(), "8");
		playCodeMap.put(PlayType.SD_KLPK3_SDZ.getValue(), "10");
		
		playCodeMap.put(PlayType.SD_KLPK3_MTH.getValue(), "2");
		playCodeMap.put(PlayType.SD_KLPK3_MTHS.getValue(), "4");
		playCodeMap.put(PlayType.SD_KLPK3_MSZ.getValue(), "6");
		playCodeMap.put(PlayType.SD_KLPK3_MBZ.getValue(), "8");
		playCodeMap.put(PlayType.SD_KLPK3_MDZ.getValue(), "10");
//		playCodeMap.put(PlayType.SD_KLPK3_MBX(200946,"扑克包选复式","包选复式",LotteryType.SD_KLPK3),
		
		
		
		        //双色球单式
				IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						String []contentStr=ticket.getContent().split("-")[1].replace(",","").split("\\^");
						int i=0;
						for(String str:contentStr){
							strBuilder.append(str);
							if(i!=contentStr.length-1){
								strBuilder.append(";");
							}
							i++;
						}
						return strBuilder.toString();
					}
				};
				
				//双色球复试
				IVenderTicketConverter ssq_fs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String contentStr=ticket.getContent().split("-")[1].replace("^","").replace(",","");
						return contentStr;
					}
				};
				//双色球胆拖
				IVenderTicketConverter ssq_dt = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String contentStr=ticket.getContent().split("-")[1].replace("^","").replace("#", "*").replace(",","");
						return contentStr;
					}
				};
		
				
				IVenderTicketConverter d3_zxfs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer ticketContent = new StringBuffer();
						String contentStr = ticket.getContent().split("-")[1].replace("^", "");
						String[] oneCode = contentStr.split("\\|");
						for (int j = 0; j < oneCode.length; j++) {
							String[] content = oneCode[j].split(",");
							for (int i = 0; i < content.length; i++) {
								ticketContent.append(Integer.parseInt(content[i]));
							}
							if(j!=oneCode.length-1){
								ticketContent.append("*");
							}
						}
				     return ticketContent.toString();
					}
				};
					
				// 排列三 3d 排列五 七星彩 单式
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
								if(i!=content.length-1){
									ticketContent.append("*");
								}
							}
							if(j!=oneCode.length-1){
								ticketContent.append("^");
							}
						}
						return ticketContent.toString();
					}
				};
				
				
				//  3d 单式
				IVenderTicketConverter d3_zxds = new IVenderTicketConverter() {
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
								ticketContent.append("^");
							}
						}
						return ticketContent.toString();
					}
				};
				// 排列三 3d 排列五 七星彩 单式
				IVenderTicketConverter d3_hz = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String contentStr = ticket.getContent().split("-")[1].replace("^","").replace(",", "");
						return String.valueOf(Integer.parseInt(contentStr));
					}
				};
			
			//山东11x5
			IVenderTicketConverter sd_11x5_fs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace(",","").replace("^","");
					return contentStr;
				}
			};
			//山东11x5
			IVenderTicketConverter sd_11x5_qsfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace(",","").replace(";","*").replace("^","");
					return contentStr;
				}
			};
			
			
			//山东11x5
			IVenderTicketConverter sd_11x5_ds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []contentStr=ticket.getContent().split("-")[1].replace(",","").split("\\^");
					int i=0;
					for(String str:contentStr){
						strBuilder.append(str);
						if(i!=contentStr.length-1){
							strBuilder.append("^");
						}
						i++;
					}
					return strBuilder.toString();
				}
			};
			
			
			
			//山东11x5胆拖
			IVenderTicketConverter sd_11x5_dt = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace(",","").replace("#", "*").replace("^","");
					return contentStr;
				}
			};
			
			
			//竞彩胜平负
			IVenderTicketConverter jcspf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer stringBuffer=new StringBuffer();
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					int i=0;
					for(String content:contents){
						content=content.substring(2,8)+"-"+content.substring(8);
						stringBuffer.append(content);
						if(i!=contents.length-1){
							stringBuffer.append(";");
						}
						i++;
					}  
					return stringBuffer.toString();
			}};
			
			//竞彩胜平负
			IVenderTicketConverter jclqspf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer stringBuffer=new StringBuffer();
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					int i=0;
					for(String content:contents){
						content=content.substring(0,8)+"-"+content.substring(8);
						stringBuffer.append(content);
						if(i!=contents.length-1){
							stringBuffer.append(";");
						}
						i++;
					}  
					return stringBuffer.toString();
			}};
			
			//竞彩篮球
			IVenderTicketConverter jclqsfc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer stringBuffer=new StringBuffer();
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					int i=0;
					for(String content:contents){
						stringBuffer.append(content.substring(0,8)+"-"+content.substring(8,11)).append("(")
						.append(content.split("\\(")[1].replace("12", "8").replace("11", "7").replace("13", "9")
								.replace("16", "12").replace("15", "11").replace("15", "11")
								.replace("10", "6").replace("06", "6").replace("05", "5").replace("04", "4")
								.replace("03", "3").replace("02", "2").replace("01", "1").replace("14", "10"));
						if(i!=contents.length-1){
							stringBuffer.append(";");
						}
						i++;
					}  
					return stringBuffer.toString();
			}};
			
			IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder stringBuilder=new StringBuilder();
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					int i=0;
					for(String content:contents){
						String lotteryType=content.split("\\*")[1].split("\\(")[0];
						stringBuilder.append(toLotteryTypeMap.get(Integer.parseInt(lotteryType))).append("^");
						if(Integer.parseInt(lotteryType)==LotteryType.JCLQ_SFC.getValue()){
							stringBuilder.append(content.split("\\*")[0].substring(0,8)+"-"+content.split("\\*")[0].substring(8,11)).append("(")
							.append(content.split("\\(")[1].replace("12", "8").replace("11", "7").replace("13", "9")
									.replace("16", "12").replace("15", "11").replace("15", "11")
									.replace("10", "6").replace("06", "6").replace("05", "5").replace("04", "4")
									.replace("03", "3").replace("02", "2").replace("01", "1").replace("14", "10"));
						}else{
							stringBuilder.append(content.split("\\*")[0].substring(0,8)+"-"+content.split("\\*")[0].substring(8,11)).append("(").append(content.split("\\(")[1]);
						}
						
						if(i!=contents.length-1){
							stringBuilder.append(";");
						}
						i++;
					}  
					return stringBuilder.toString();
			}};
			
			IVenderTicketConverter jczqmix = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder stringBuilder=new StringBuilder();
					String []contents=ticket.getContent().split("\\-")[1].replace("^", "").split("\\|");
					int i=0;
					for(String content:contents){
						String lotteryType=content.split("\\*")[1].split("\\(")[0];
						stringBuilder.append(toLotteryTypeMap.get(Integer.parseInt(lotteryType))).append("^");
						stringBuilder.append(content.split("\\*")[0].substring(2,8)+"-"+content.split("\\*")[0].substring(8,11)).append("(").append(content.split("\\(")[1]);
						if(i!=contents.length-1){
							stringBuilder.append(";");
						}
						i++;
					}  
					return stringBuilder.toString();
			}};
	        
			
			//大乐透单式
			IVenderTicketConverter dlt_ds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []contentStr=ticket.getContent().split("-")[1].replace(",","").split("\\^");
					int i=0;
					for(String str:contentStr){
						strBuilder.append(str);
						if(i!=contentStr.length-1){
							strBuilder.append("^");
						}
						i++;
					}
					return strBuilder.toString();
				}
			};
			
			//大乐透胆拖
			IVenderTicketConverter dlt_dt = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
				    String	contentStr=ticket.getContent().split("-")[1].replace("^","");
					StringBuffer stringBuffer=new StringBuffer();
					String []contents=contentStr.split("\\|");
					if(contents[0].contains("#")){
						stringBuffer.append(contents[0].replace("#", "*").replace(",",""));
					}else{
						stringBuffer.append("*").append(contents[0].replace("#", "*").replace(",",""));
					}
					stringBuffer.append("|");
					if(contents[1].contains("#")){
						stringBuffer.append(contents[1].replace("#", "*").replace(",",""));
					}else{
						stringBuffer.append("*").append(contents[1].replace("#", "*").replace(",",""));
					}
					return stringBuffer.toString();
				}
			};
			
			//排三单式
			IVenderTicketConverter psds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
				    StringBuffer ticketContent = new StringBuffer();
					String contentStr = ticket.getContent().split("-")[1];
					String[] oneCode = contentStr.split("\\^");
					for (int j = 0; j < oneCode.length; j++) {
						String[] content = oneCode[j].split(",");
						for (int i = 0; i < content.length; i++) {
							ticketContent.append(Integer.parseInt(content[i]));
							if(i!=content.length-1){
								ticketContent.append("*");
							}
						}
						if(j!=oneCode.length-1){
							ticketContent.append("^");
						}
					}
					return ticketContent.toString();
				}
			};
			
			IVenderTicketConverter zsds = new IVenderTicketConverter() {
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
							ticketContent.append("^");
						}
					}
					return ticketContent.toString();
				}
			};
			
			IVenderTicketConverter zxfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
				    StringBuffer ticketContent = new StringBuffer();
					String contentStr = ticket.getContent().split("-")[1].replace("^","");
					String[] oneCode = contentStr.split("\\|");
					for (int j = 0; j < oneCode.length; j++) {
						String[] content = oneCode[j].split(",");
						for (int i = 0; i < content.length; i++) {
							ticketContent.append(Integer.parseInt(content[i]));
						}
						if(j!=oneCode.length-1){
							ticketContent.append("*");
						}
					}
					return ticketContent.toString();
				}
			};
			
			IVenderTicketConverter pshz = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1].replace("^","");
					contentStr=String.valueOf(Integer.parseInt(contentStr));
					return contentStr;
				}
			};
			 //山东快乐扑克单式
			IVenderTicketConverter klpk_ds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer strBuilder=new StringBuffer();
					String []contentStr=ticket.getContent().split("-")[1].replace(",","").split("\\^");
					int i=0;
					for(String str:contentStr){
						strBuilder.append(str);
						if(i!=contentStr.length-1){
							strBuilder.append("^");
						}
						i++;
					}
					return strBuilder.toString();
				}
			};
			
			 //山东快乐扑克单式
			IVenderTicketConverter klpk_pkds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace(",","").replace("^","");
					return contentStr;
				}
			};
			 //山东快乐扑克复试
			IVenderTicketConverter klpk_fs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace("^","").replace(",","");
					return contentStr;
				}
			};
			IVenderTicketConverter klpk_dt = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace("^","").replace(",","").replace("#", "*");
					return contentStr;
				}
			};
			
			
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SR1, klpk_ds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SR2, klpk_ds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SR3, klpk_ds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SR4, klpk_ds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SR5, klpk_ds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SR6, klpk_ds);
			
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MR1, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MR2, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MR3, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MR4, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MR5, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MR6, klpk_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_DR2, klpk_dt);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_DR3, klpk_dt);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_DR4, klpk_dt);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_DR5, klpk_dt);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_DR6, klpk_dt);
			
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_STH, klpk_pkds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_STHS, klpk_pkds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SSZ, klpk_pkds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SBZ, klpk_pkds);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_SDZ, klpk_pkds);
			
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MTH, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MTHS, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MSZ, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MBZ, klpk_fs);
			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MDZ, klpk_fs);
//			playTypeToAdvanceConverterMap.put(PlayType.SD_KLPK3_MBX, klpk_fs);
			
			
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
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11,jcspf);
	    	 
	    	 
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
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jclqspf);
	 			    		
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jclqspf);
	 			    		
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
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jclqspf);
	 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jclqspf);
	 		 
	 		 
	 		 
	 		 
	 		 
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
			
			
			 //双色球
			playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq_fs);
			playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssq_dt);
			//3D
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, d3_ds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, d3_zxds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, d3_zxds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, d3_zxfs);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, d3_zxds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, d3_zxds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, d3_hz);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, d3_hz);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, d3_hz);
			
			playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, ssq_fs);
			playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, ssq_dt);
			//山东11x5
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq2,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq3,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz2,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz3,sd_11x5_ds);
			
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr2,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr3,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr4,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr5,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr6,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr7,sd_11x5_fs);
//			playTypeMap.put(PlayType.sd11c5_mr8.getValue(), "1");
//			playTypeMap.put(PlayType.sd11c5_mq1.getValue(), "1");
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq2,sd_11x5_qsfs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq3,sd_11x5_qsfs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz2,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz3,sd_11x5_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr2,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr3,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr4,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr5,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr6,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr7,sd_11x5_dt);
//			playTypeMap.put(PlayType.sd11c5_dr8.getValue(), "1");
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz2,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz3,sd_11x5_dt);
			

			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl3,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl4,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl5,sd_11x5_ds);
			
			//广东11x5
		    playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr2,sd_11x5_ds);
		    playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr3,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr4,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr5,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr6,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr7,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr8,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq1,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq2,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq3,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz2,sd_11x5_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz3,sd_11x5_ds);
			                                           
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr2,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr3,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr4,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr5,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr6,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr7,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq2,sd_11x5_qsfs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq3,sd_11x5_qsfs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz2,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz3,sd_11x5_fs);
			                                           
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr2,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr3,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr4,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr5,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr6,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr7,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz2,sd_11x5_dt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz3,sd_11x5_dt);	
			
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dan,dlt_ds);
			playTypeToAdvanceConverterMap.put(PlayType.dlt_fu,dlt_ds);
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo,dlt_dt);
			//p3
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan,psds);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan,zsds);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan,zsds);
			
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs,zxfs);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs,zxfs);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs,zxfs);
			
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt,pshz);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt,pshz);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt,pshz);
			//p5
			playTypeToAdvanceConverterMap.put(PlayType.p5_dan,psds);
			playTypeToAdvanceConverterMap.put(PlayType.p5_fu,zxfs);
			//qxc
			playTypeToAdvanceConverterMap.put(PlayType.qxc_dan,psds);
			playTypeToAdvanceConverterMap.put(PlayType.qxc_fu,zxfs);
			
	}
	
	public static String getChildType(Ticket ticket){
		int content=Integer.parseInt(ticket.getContent().split("\\-")[1].replace("^",""));
		if(content==7){
			return "1";
		}else if(content==8){
			return "3";
		}else if(content==9){
			return "5";
		}else if(content==10){
			return "7";
		}else if(content==11){
			return "9";
		}
		return null;
	}
	
	public static String getContent(Ticket ticket){
		String content=ticket.getContent().split("\\-")[1].replace("^","");
		return content;
	}
	
	
	
}
