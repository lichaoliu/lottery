package com.lottery.ticket.vender.impl.huay;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class HuayLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	protected static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
	/** 彩期转换*/
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换*/
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	
	public static Map<Integer, String> playTypeMapJc = new HashMap<Integer, String>();
	protected static Map<String,String> numberMap=new HashMap<String, String>();
	public static Map<String,String> tonumberMap=new HashMap<String, String>();
	protected static Map<Integer, String> playTypeMapJcMix = new HashMap<Integer, String>();
	public static Map<String, Integer> toplayTypeMapJcMix = new HashMap<String, Integer>();
	/** 票内容转换 */
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
	
	/** 内容转换 */
	protected static Map<String,String> bqcMap = new HashMap<String,String>();
	protected static Map<String,String> jqsMap = new HashMap<String,String>();
	protected static Map<String,String> bfMap = new HashMap<String,String>();
	static{
		
		
		//竞彩篮球胜负差转换
		numberMap.put("16", "12");
		numberMap.put("15", "11");
		numberMap.put("14", "10");
		numberMap.put("13", "9");
		numberMap.put("12", "8");
		numberMap.put("11", "7");
		numberMap.put("06", "6");
		numberMap.put("05", "5");
		numberMap.put("04", "4");
		numberMap.put("03", "3");
		numberMap.put("02", "2");
		numberMap.put("01", "1");
		
		
		tonumberMap.put("12", "16");
		tonumberMap.put("11", "15");
		tonumberMap.put("10", "14");
		tonumberMap.put("9", "13");
		tonumberMap.put("8", "12");
		tonumberMap.put("7", "11");
		tonumberMap.put("6", "06");
		tonumberMap.put("5", "05");
		tonumberMap.put("4", "04");
		tonumberMap.put("3", "03");
		tonumberMap.put("2", "02");
		tonumberMap.put("1", "01");
		
		
		playTypeMapJcMix.put(LotteryType.JCZQ_SPF_WRQ.getValue(), "209");
		playTypeMapJcMix.put(LotteryType.JCZQ_RQ_SPF.getValue(), "210");
		playTypeMapJcMix.put(LotteryType.JCZQ_BF.getValue(), "211");
		playTypeMapJcMix.put(LotteryType.JCZQ_BQC.getValue(), "213");
		playTypeMapJcMix.put(LotteryType.JCZQ_JQS.getValue(), "212");
		playTypeMapJcMix.put(LotteryType.JCLQ_RFSF.getValue(), "214");
		playTypeMapJcMix.put(LotteryType.JCLQ_DXF.getValue(), "215");
		playTypeMapJcMix.put(LotteryType.JCLQ_SF.getValue(), "216");
		playTypeMapJcMix.put(LotteryType.JCLQ_SFC.getValue(), "217");
		
		
		toplayTypeMapJcMix.put("209",LotteryType.JCZQ_SPF_WRQ.getValue());
		toplayTypeMapJcMix.put("210",LotteryType.JCZQ_RQ_SPF.getValue());
		toplayTypeMapJcMix.put("211",LotteryType.JCZQ_BF.getValue());
		toplayTypeMapJcMix.put("213",LotteryType.JCZQ_BQC.getValue());
		toplayTypeMapJcMix.put("212",LotteryType.JCZQ_JQS.getValue());
		toplayTypeMapJcMix.put("214",LotteryType.JCLQ_RFSF.getValue());
		toplayTypeMapJcMix.put("215",LotteryType.JCLQ_DXF.getValue());
		toplayTypeMapJcMix.put("216",LotteryType.JCLQ_SF.getValue());
		toplayTypeMapJcMix.put("217",LotteryType.JCLQ_SFC.getValue());
		
		bqcMap.put("33", "1");
		bqcMap.put("31", "2");
		bqcMap.put("30", "3");
		bqcMap.put("13", "4");
		bqcMap.put("11", "5");
		bqcMap.put("10", "6");
		bqcMap.put("03", "7");
		bqcMap.put("01", "8");
		bqcMap.put("00", "9");
		
		jqsMap.put("0", "1");
		jqsMap.put("1", "2");
		jqsMap.put("2", "3");
		jqsMap.put("3", "4");
		jqsMap.put("4", "5");
		jqsMap.put("5", "6");
		jqsMap.put("6", "7");
		jqsMap.put("7", "8");
		jqsMap.put("8", "9");
		
		bfMap.put("10", "2");
		bfMap.put("20", "3");
		bfMap.put("21", "4");
		bfMap.put("30", "5");
		bfMap.put("31", "6");
		bfMap.put("32", "7");
		bfMap.put("40", "8");
		bfMap.put("41", "9");
		bfMap.put("42", "10");
		bfMap.put("00", "12");
		bfMap.put("11", "13");
		bfMap.put("22", "14");
		bfMap.put("33", "15");
		bfMap.put("01", "17");
		bfMap.put("02", "18");
		bfMap.put("12", "19");
		bfMap.put("03", "20");
		bfMap.put("13", "21");
		bfMap.put("24", "25");
		bfMap.put("23", "22");
		bfMap.put("04", "23");
		bfMap.put("14", "24");
		bfMap.put("90", "1");
		bfMap.put("99", "11");
		bfMap.put("09", "16");
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
		//大乐透的期号转换
		IPhaseConverter cjdlt=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase.substring(2);
			}
		};
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_SFC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.JX_11X5, cjdlt);
		phaseConverterMap.put(LotteryType.SD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.CQSSC, cjdlt);
		phaseConverterMap.put(LotteryType.GXK3, defaultPhaseConverter);
		
		//彩种转换
		lotteryTypeMap.put(LotteryType.SSQ, "118");
		lotteryTypeMap.put(LotteryType.F3D, "116");
		lotteryTypeMap.put(LotteryType.QLC, "117");
		lotteryTypeMap.put(LotteryType.PL3, "100");
		lotteryTypeMap.put(LotteryType.PL5, "102");
		lotteryTypeMap.put(LotteryType.QXC, "103");
		lotteryTypeMap.put(LotteryType.CJDLT, "106");
		lotteryTypeMap.put(LotteryType.JX_11X5, "113");
		lotteryTypeMap.put(LotteryType.SD_11X5, "112");
		lotteryTypeMap.put(LotteryType.CQSSC, "121");
		lotteryTypeMap.put(LotteryType.GXK3, "128");
		
		lotteryTypeMap.put(LotteryType.ZC_SFC, "108");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "109");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "111");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "110");
		
		lotteryTypeMap.put(LotteryType.DC_SPF, "200");
		lotteryTypeMap.put(LotteryType.DC_ZJQ, "202");
		lotteryTypeMap.put(LotteryType.DC_BQC, "204");
		lotteryTypeMap.put(LotteryType.DC_SXDS, "201");
		lotteryTypeMap.put(LotteryType.DC_BF, "203");
//		lotteryTypeMap.put(LotteryType.DC_SF, "203");
		
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
		
		toLotteryTypeMap.put("118", LotteryType.SSQ);
		toLotteryTypeMap.put("116", LotteryType.F3D);
		toLotteryTypeMap.put("117", LotteryType.QLC);
		toLotteryTypeMap.put("100", LotteryType.PL3);
		toLotteryTypeMap.put("102", LotteryType.PL5);
		toLotteryTypeMap.put("103", LotteryType.QXC);
		toLotteryTypeMap.put("106", LotteryType.CJDLT);
		toLotteryTypeMap.put("122", LotteryType.GXK3);
		
		toLotteryTypeMap.put("108", LotteryType.ZC_SFC);
		toLotteryTypeMap.put("109", LotteryType.ZC_RJC);
		toLotteryTypeMap.put("111", LotteryType.ZC_JQC);
		toLotteryTypeMap.put("110", LotteryType.ZC_BQC);
		
		toLotteryTypeMap.put("200", LotteryType.DC_SPF);
		toLotteryTypeMap.put("202", LotteryType.DC_ZJQ);
		toLotteryTypeMap.put("204", LotteryType.DC_BQC);
		toLotteryTypeMap.put("201", LotteryType.DC_SXDS);
		toLotteryTypeMap.put("203", LotteryType.DC_BF);
		toLotteryTypeMap.put("113", LotteryType.JX_11X5);
		toLotteryTypeMap.put("112", LotteryType.SD_11X5);
		
		//北单
		lotteryTypeMap.put(LotteryType.DC_SPF, "200");
		lotteryTypeMap.put(LotteryType.DC_ZJQ, "202");
		lotteryTypeMap.put(LotteryType.DC_BQC, "204");
		lotteryTypeMap.put(LotteryType.DC_SXDS, "201");
		lotteryTypeMap.put(LotteryType.DC_BF, "203");
		//lotteryTypeMap.put(LotteryType.DC_SF, "");
		
		//玩法转换
		//双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "0");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "1");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "2");
		//七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "0");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "1");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "2");
		//3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "0");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "3");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "3");
		
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "1");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "5");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "4");
		
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "2");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "6");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "14");
		//七星彩
		playTypeMap.put(PlayType.qxc_dan.getValue(), "0");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "1");
		//大乐透
		playTypeMap.put(PlayType.dlt_dan.getValue(), "0");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "1");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "2");
		//排三
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "0");//直选
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "1");//直选复式
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "3");//组选3
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "3");//组选6
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "5");//组三复试
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "4");//组六复式
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "9");//组三和值
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "10");//组六和值
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "2");//直选和值
		//排五
		playTypeMap.put(PlayType.p5_dan.getValue(), "0");
		playTypeMap.put(PlayType.p5_fu.getValue(), "1");
		//重庆时时彩
		playTypeMap.put(PlayType.cqssc_dan_1d.getValue(), "0");
		playTypeMap.put(PlayType.cqssc_dan_2d.getValue(), "0");
		playTypeMap.put(PlayType.cqssc_dan_3d.getValue(), "0");
		playTypeMap.put(PlayType.cqssc_dan_5d.getValue(), "0");
		playTypeMap.put(PlayType.cqssc_dan_z3.getValue(), "2");
		playTypeMap.put(PlayType.cqssc_dan_z6.getValue(), "4");
		
		playTypeMap.put(PlayType.cqssc_fu_1d.getValue(), "1");
		playTypeMap.put(PlayType.cqssc_fu_2d.getValue(), "1");
		playTypeMap.put(PlayType.cqssc_fu_3d.getValue(), "1");
		playTypeMap.put(PlayType.cqssc_fu_5d.getValue(), "1");
		
//		playTypeMap.put(PlayType.cqssc_other_2h.getValue(), "1");
//		playTypeMap.put(PlayType.cqssc_other_3h.getValue(), "1");
		playTypeMap.put(PlayType.cqssc_other_2z.getValue(), "2");//单式支持
		playTypeMap.put(PlayType.cqssc_other_z3.getValue(), "3");
		playTypeMap.put(PlayType.cqssc_other_z6.getValue(), "5");
		playTypeMap.put(PlayType.cqssc_other_dd.getValue(), "0");
		playTypeMap.put(PlayType.cqssc_other_5t.getValue(), "2");
		
		
		//广西快三
		playTypeMap.put(PlayType.gxk3_ertong_dan.getValue(), "0");
		playTypeMap.put(PlayType.gxk3_ertong_fu.getValue(), "1");
//		playTypeMap.put(PlayType.gxk3_ertong_zuhe.getValue(), "502");
		playTypeMap.put(PlayType.gxk3_erbutong_dan.getValue(), "0");
//		playTypeMap.put(PlayType.gxk3_erbutong_zuhe.getValue(), "702");
//		playTypeMap.put(PlayType.gxk3_erbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.gxk3_santong_dan.getValue(), "1");
		playTypeMap.put(PlayType.gxk3_santong_tongxuan.getValue(), "0");
		playTypeMap.put(PlayType.gxk3_sanbutong_dan.getValue(), "0");
//		playTypeMap.put(PlayType.gxk3_sanbutong_zuhe.getValue(), "01");
//		playTypeMap.put(PlayType.gxk3_sanbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.gxk3_sanlian_tongxuan.getValue(), "0");
		playTypeMap.put(PlayType.gxk3_hezhi.getValue(), "0");			
		
		
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
		playTypeMapJc.put(PlayType.jczq_spf_1_1.getValue(), "101");
		playTypeMapJc.put(PlayType.jczq_spf_2_1.getValue(), "102");
		playTypeMapJc.put(PlayType.jczq_spf_3_1.getValue(), "103");
         playTypeMapJc.put(PlayType.jczq_spf_4_1.getValue(), "104");
         playTypeMapJc.put(PlayType.jczq_spf_5_1.getValue(), "105");
         playTypeMapJc.put(PlayType.jczq_spf_6_1.getValue(), "106");
         playTypeMapJc.put(PlayType.jczq_spf_7_1.getValue(), "107");
         playTypeMapJc.put(PlayType.jczq_spf_8_1.getValue(), "108");
         playTypeMapJc.put(PlayType.jczq_spf_3_3.getValue(), "603");
         playTypeMapJc.put(PlayType.jczq_spf_3_4.getValue(), "118");
         playTypeMapJc.put(PlayType.jczq_spf_4_4.getValue(), "604");
         playTypeMapJc.put(PlayType.jczq_spf_4_5.getValue(), "120");
         playTypeMapJc.put(PlayType.jczq_spf_4_6.getValue(), "605");
         playTypeMapJc.put(PlayType.jczq_spf_4_11.getValue(), "121");
         playTypeMapJc.put(PlayType.jczq_spf_5_5.getValue(), "606");
         playTypeMapJc.put(PlayType.jczq_spf_5_6.getValue(), "123");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_10.getValue(), "607");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_16.getValue(), "124");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_20.getValue(), "608");
     	 playTypeMapJc.put(PlayType.jczq_spf_5_26.getValue(), "125");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_6.getValue(), "609");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_7.getValue(), "127");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_15.getValue(), "610");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_20.getValue(), "611");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_22.getValue(), "128");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_35.getValue(), "612");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_42.getValue(), "129");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_50.getValue(), "613");
     	 playTypeMapJc.put(PlayType.jczq_spf_6_57.getValue(), "602");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_7.getValue(), "702");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_8.getValue(), "703");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_21.getValue(), "704");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_35.getValue(), "705");
     	 playTypeMapJc.put(PlayType.jczq_spf_7_120.getValue(), "706");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_8.getValue(), "802");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_9.getValue(), "803");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_28.getValue(), "804");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_56.getValue(), "805");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_70.getValue(), "806");
     	 playTypeMapJc.put(PlayType.jczq_spf_8_247.getValue(), "807");
     	 
     	 playTypeMapJc.put(PlayType.jczq_jqs_1_1.getValue(), "101");
     	 playTypeMapJc.put(PlayType.jczq_jqs_2_1.getValue(), "102");
     	 playTypeMapJc.put(PlayType.jczq_jqs_3_1.getValue(), "103");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_1.getValue(), "104");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_1.getValue(), "105");
     	 playTypeMapJc.put(PlayType.jczq_jqs_6_1.getValue(), "106");
     	 playTypeMapJc.put(PlayType.jczq_jqs_7_1.getValue(), "107");
     	 playTypeMapJc.put(PlayType.jczq_jqs_8_1.getValue(), "108");
     	 playTypeMapJc.put(PlayType.jczq_jqs_3_3.getValue(), "603");
     	 playTypeMapJc.put(PlayType.jczq_jqs_3_4.getValue(), "118");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_4.getValue(), "604");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_5.getValue(), "120");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_6.getValue(), "605");
     	 playTypeMapJc.put(PlayType.jczq_jqs_4_11.getValue(), "121");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_5.getValue(), "606");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_6.getValue(), "123");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_10.getValue(), "607");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_16.getValue(), "124");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_20.getValue(), "608");
     	 playTypeMapJc.put(PlayType.jczq_jqs_5_26.getValue(), "125");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_6.getValue(), "609");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_7.getValue(), "127");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_15.getValue(), "610");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_20.getValue(), "611");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_22.getValue(), "128");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_35.getValue(), "705");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_42.getValue(), "129");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_50.getValue(), "613");
    	 playTypeMapJc.put(PlayType.jczq_jqs_6_57.getValue(), "602");
    	 
    	 playTypeMapJc.put(PlayType.jczq_bf_1_1.getValue(), "101");
    	 playTypeMapJc.put(PlayType.jczq_bf_2_1.getValue(), "102");
    	 playTypeMapJc.put(PlayType.jczq_bf_3_1.getValue(), "103");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_1.getValue(), "104");
    	 playTypeMapJc.put(PlayType.jczq_bf_5_1.getValue(), "105");
    	 playTypeMapJc.put(PlayType.jczq_bf_6_1.getValue(), "106");
    	 playTypeMapJc.put(PlayType.jczq_bf_7_1.getValue(), "107");
    	 playTypeMapJc.put(PlayType.jczq_bf_8_1.getValue(), "108");
    	 playTypeMapJc.put(PlayType.jczq_bf_3_3.getValue(), "603");
    	 playTypeMapJc.put(PlayType.jczq_bf_3_4.getValue(), "118");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_4.getValue(), "604");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_5.getValue(), "120");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_6.getValue(), "605");
    	 playTypeMapJc.put(PlayType.jczq_bf_4_11.getValue(), "121");
    	 
    	 
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_1_1.getValue(), "101");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_2_1.getValue(), "102");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_1.getValue(), "103");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_1.getValue(), "104");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_1.getValue(), "105");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_1.getValue(), "106");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_1.getValue(), "107");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_1.getValue(), "108");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_3.getValue(), "603");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_3_4.getValue(), "118");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_4.getValue(), "604");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_5.getValue(), "120");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_6.getValue(), "605");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_4_11.getValue(), "121");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_5.getValue(), "606");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_6.getValue(), "123");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_10.getValue(), "607");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_16.getValue(), "124");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_20.getValue(), "608");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_5_26.getValue(), "125");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_6.getValue(), "609");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_7.getValue(), "127");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_15.getValue(), "610");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_20.getValue(), "611");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_22.getValue(), "128");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_35.getValue(), "705");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_42.getValue(), "129");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_50.getValue(), "613");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_6_57.getValue(), "602");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_7.getValue(), "702");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_8.getValue(), "703");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_21.getValue(), "704");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_35.getValue(), "705");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_7_120.getValue(), "706");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_8.getValue(), "802");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_9.getValue(), "803");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_28.getValue(), "804");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_56.getValue(), "805");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_70.getValue(), "806");
    	 playTypeMapJc.put(PlayType.jczq_spfwrq_8_247.getValue(), "807");
    	 
    	 playTypeMapJc.put(PlayType.jczq_mix_2_1.getValue(), "102");
    	 playTypeMapJc.put(PlayType.jczq_mix_3_1.getValue(), "103");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_1.getValue(), "104");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_1.getValue(), "105");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_1.getValue(), "106");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_1.getValue(), "107");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_1.getValue(), "108");
    	 playTypeMapJc.put(PlayType.jczq_mix_3_3.getValue(), "603");
    	 playTypeMapJc.put(PlayType.jczq_mix_3_4.getValue(), "118");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_4.getValue(), "604");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_5.getValue(), "120");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_6.getValue(), "605");
    	 playTypeMapJc.put(PlayType.jczq_mix_4_11.getValue(), "121");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_5.getValue(), "606");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_6.getValue(), "123");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_10.getValue(), "607");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_16.getValue(), "124");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_20.getValue(), "608");
    	 playTypeMapJc.put(PlayType.jczq_mix_5_26.getValue(), "125");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_6.getValue(), "609");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_7.getValue(), "127");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_15.getValue(), "610");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_20.getValue(), "611");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_22.getValue(), "128");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_35.getValue(), "705");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_42.getValue(), "129");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_50.getValue(), "613");
    	 playTypeMapJc.put(PlayType.jczq_mix_6_57.getValue(), "602");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_7.getValue(), "702");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_8.getValue(), "703");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_21.getValue(), "704");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_35.getValue(), "705");
    	 playTypeMapJc.put(PlayType.jczq_mix_7_120.getValue(), "706");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_8.getValue(), "802");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_9.getValue(), "803");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_28.getValue(), "804");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_56.getValue(), "805");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_70.getValue(), "806");
    	 playTypeMapJc.put(PlayType.jczq_mix_8_247.getValue(), "807");
    	 
    	 
    	 playTypeMapJc.put(PlayType.jczq_bqc_1_1.getValue(), "101");
    	 playTypeMapJc.put(PlayType.jczq_bqc_2_1.getValue(), "102");
    	 playTypeMapJc.put(PlayType.jczq_bqc_3_1.getValue(), "103");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_1.getValue(), "104");
    	 playTypeMapJc.put(PlayType.jczq_bqc_5_1.getValue(), "105");
    	 playTypeMapJc.put(PlayType.jczq_bqc_6_1.getValue(), "106");
    	 playTypeMapJc.put(PlayType.jczq_bqc_7_1.getValue(), "107");
    	 playTypeMapJc.put(PlayType.jczq_bqc_8_1.getValue(), "108");
    	 playTypeMapJc.put(PlayType.jczq_bqc_3_3.getValue(), "603");
    	 playTypeMapJc.put(PlayType.jczq_bqc_3_4.getValue(), "118");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_4.getValue(), "604");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_5.getValue(), "120");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_6.getValue(), "605");
    	 playTypeMapJc.put(PlayType.jczq_bqc_4_11.getValue(), "121");
		
		
		
    	 
    	playTypeMapJc.put(PlayType.jclq_sf_1_1.getValue(),"02");
    	playTypeMapJc.put(PlayType.jclq_sf_2_1.getValue(),"03");
    	playTypeMapJc.put(PlayType.jclq_sf_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.jclq_sf_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.jclq_sf_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.jclq_sf_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.jclq_sf_7_1.getValue(),"08");
 		playTypeMapJc.put(PlayType.jclq_sf_8_1.getValue(),"09");
 		playTypeMapJc.put(PlayType.jclq_sf_3_3.getValue(),"41");
 		playTypeMapJc.put(PlayType.jclq_sf_3_4.getValue(),"42");
 		playTypeMapJc.put(PlayType.jclq_sf_4_4.getValue(),"51");
 		playTypeMapJc.put(PlayType.jclq_sf_4_5.getValue(),"52");
 		playTypeMapJc.put(PlayType.jclq_sf_4_6.getValue(),"53");
 		playTypeMapJc.put(PlayType.jclq_sf_4_11.getValue(),"54");
 		playTypeMapJc.put(PlayType.jclq_sf_5_5.getValue(),"61");
 		playTypeMapJc.put(PlayType.jclq_sf_5_6.getValue(),"62");
 		playTypeMapJc.put(PlayType.jclq_sf_5_10.getValue(),"63");
 		playTypeMapJc.put(PlayType.jclq_sf_5_16.getValue(),"64");
 		playTypeMapJc.put(PlayType.jclq_sf_5_20.getValue(),"65");
 		playTypeMapJc.put(PlayType.jclq_sf_5_26.getValue(),"66");
 		playTypeMapJc.put(PlayType.jclq_sf_6_6.getValue(),"71");
 		playTypeMapJc.put(PlayType.jclq_sf_6_7.getValue(),"72");
 		playTypeMapJc.put(PlayType.jclq_sf_6_15.getValue(),"73");
 		playTypeMapJc.put(PlayType.jclq_sf_6_20.getValue(),"74");
 		playTypeMapJc.put(PlayType.jclq_sf_6_22.getValue(),"75");
 		playTypeMapJc.put(PlayType.jclq_sf_6_35.getValue(),"76");
 		playTypeMapJc.put(PlayType.jclq_sf_6_42.getValue(),"77");
 		playTypeMapJc.put(PlayType.jclq_sf_6_50.getValue(),"78");
 		playTypeMapJc.put(PlayType.jclq_sf_6_57.getValue(),"79");
 		playTypeMapJc.put(PlayType.jclq_sf_7_7.getValue(),"81");
 		playTypeMapJc.put(PlayType.jclq_sf_7_8.getValue(),"82");
 		playTypeMapJc.put(PlayType.jclq_sf_7_21.getValue(),"83");
 		playTypeMapJc.put(PlayType.jclq_sf_7_35.getValue(),"84");
 		playTypeMapJc.put(PlayType.jclq_sf_7_120.getValue(),"85");
 		playTypeMapJc.put(PlayType.jclq_sf_8_8.getValue(),"91");
 		playTypeMapJc.put(PlayType.jclq_sf_8_9.getValue(),"92");
 		playTypeMapJc.put(PlayType.jclq_sf_8_28.getValue(),"93");
 		playTypeMapJc.put(PlayType.jclq_sf_8_56.getValue(),"94");
 		playTypeMapJc.put(PlayType.jclq_sf_8_70.getValue(),"95");
 		playTypeMapJc.put(PlayType.jclq_sf_8_247.getValue(),"96");
 		
 		
 		
 		playTypeMapJc.put(PlayType.jclq_rfsf_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.jclq_rfsf_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.jclq_rfsf_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_1.getValue(),"08");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_1.getValue(),"09");
 		playTypeMapJc.put(PlayType.jclq_rfsf_3_3.getValue(),"41");
 		playTypeMapJc.put(PlayType.jclq_rfsf_3_4.getValue(),"42");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_4.getValue(),"51");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_5.getValue(),"52");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_6.getValue(),"53");
 		playTypeMapJc.put(PlayType.jclq_rfsf_4_11.getValue(),"54");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_5.getValue(),"61");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_6.getValue(),"62");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_10.getValue(),"63");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_16.getValue(),"64");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_20.getValue(),"65");
 		playTypeMapJc.put(PlayType.jclq_rfsf_5_26.getValue(),"66");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_6.getValue(),"71");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_7.getValue(),"72");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_15.getValue(),"73");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_20.getValue(),"74");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_22.getValue(),"75");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_35.getValue(),"76");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_42.getValue(),"77");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_50.getValue(),"78");
 		playTypeMapJc.put(PlayType.jclq_rfsf_6_57.getValue(),"79");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_7.getValue(),"81");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_8.getValue(),"82");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_21.getValue(),"83");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_35.getValue(),"84");
 		playTypeMapJc.put(PlayType.jclq_rfsf_7_120.getValue(),"85");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_8.getValue(),"91");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_9.getValue(),"92");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_28.getValue(),"93");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_56.getValue(),"94");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_70.getValue(),"95");
 		playTypeMapJc.put(PlayType.jclq_rfsf_8_247.getValue(),"96");
 		
 		playTypeMapJc.put(PlayType.jclq_sfc_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.jclq_sfc_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.jclq_sfc_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.jclq_sfc_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.jclq_sfc_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.jclq_sfc_7_1.getValue(),"08");
 		playTypeMapJc.put(PlayType.jclq_sfc_8_1.getValue(),"09");
 		playTypeMapJc.put(PlayType.jclq_sfc_3_3.getValue(),"41");
 		playTypeMapJc.put(PlayType.jclq_sfc_3_4.getValue(),"42");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_4.getValue(),"51");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_5.getValue(),"52");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_6.getValue(),"53");
 		playTypeMapJc.put(PlayType.jclq_sfc_4_11.getValue(),"54");
 		
 		playTypeMapJc.put(PlayType.jclq_dxf_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.jclq_dxf_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.jclq_dxf_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_1.getValue(),"08");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_1.getValue(),"09");
 		playTypeMapJc.put(PlayType.jclq_dxf_3_3.getValue(),"41");
 		playTypeMapJc.put(PlayType.jclq_dxf_3_4.getValue(),"42");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_4.getValue(),"51");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_5.getValue(),"52");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_6.getValue(),"53");
 		playTypeMapJc.put(PlayType.jclq_dxf_4_11.getValue(),"54");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_5.getValue(),"61");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_6.getValue(),"62");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_10.getValue(),"63");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_16.getValue(),"64");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_20.getValue(),"65");
 		playTypeMapJc.put(PlayType.jclq_dxf_5_26.getValue(),"66");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_6.getValue(),"71");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_7.getValue(),"72");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_15.getValue(),"73");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_20.getValue(),"74");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_22.getValue(),"75");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_35.getValue(),"76");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_42.getValue(),"77");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_50.getValue(),"78");
 		playTypeMapJc.put(PlayType.jclq_dxf_6_57.getValue(),"79");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_7.getValue(),"81");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_8.getValue(),"82");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_21.getValue(),"83");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_35.getValue(),"84");
 		playTypeMapJc.put(PlayType.jclq_dxf_7_120.getValue(),"85");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_8.getValue(),"91");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_9.getValue(),"92");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_28.getValue(),"93");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_56.getValue(),"94");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_70.getValue(),"95");
 		playTypeMapJc.put(PlayType.jclq_dxf_8_247.getValue(),"96");
 		
 		playTypeMapJc.put(PlayType.jclq_mix_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.jclq_mix_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.jclq_mix_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.jclq_mix_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.jclq_mix_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.jclq_mix_7_1.getValue(),"08");
 		playTypeMapJc.put(PlayType.jclq_mix_8_1.getValue(),"09");
 		playTypeMapJc.put(PlayType.jclq_mix_3_3.getValue(),"41");
 		playTypeMapJc.put(PlayType.jclq_mix_3_4.getValue(),"42");
 		playTypeMapJc.put(PlayType.jclq_mix_4_4.getValue(),"51");
 		playTypeMapJc.put(PlayType.jclq_mix_4_5.getValue(),"52");
 		playTypeMapJc.put(PlayType.jclq_mix_4_6.getValue(),"53");
 		playTypeMapJc.put(PlayType.jclq_mix_4_11.getValue(),"54");
 		playTypeMapJc.put(PlayType.jclq_mix_5_5.getValue(),"61");
 		playTypeMapJc.put(PlayType.jclq_mix_5_6.getValue(),"62");
 		playTypeMapJc.put(PlayType.jclq_mix_5_10.getValue(),"63");
 		playTypeMapJc.put(PlayType.jclq_mix_5_16.getValue(),"64");
 		playTypeMapJc.put(PlayType.jclq_mix_5_20.getValue(),"65");
 		playTypeMapJc.put(PlayType.jclq_mix_5_26.getValue(),"66");
 		playTypeMapJc.put(PlayType.jclq_mix_6_6.getValue(),"71");
 		playTypeMapJc.put(PlayType.jclq_mix_6_7.getValue(),"72");
 		playTypeMapJc.put(PlayType.jclq_mix_6_15.getValue(),"73");
 		playTypeMapJc.put(PlayType.jclq_mix_6_20.getValue(),"74");
 		playTypeMapJc.put(PlayType.jclq_mix_6_22.getValue(),"75");
 		playTypeMapJc.put(PlayType.jclq_mix_6_35.getValue(),"76");
 		playTypeMapJc.put(PlayType.jclq_mix_6_42.getValue(),"77");
 		playTypeMapJc.put(PlayType.jclq_mix_6_50.getValue(),"78");
 		playTypeMapJc.put(PlayType.jclq_mix_6_57.getValue(),"79");
 		playTypeMapJc.put(PlayType.jclq_mix_7_7.getValue(),"81");
 		playTypeMapJc.put(PlayType.jclq_mix_7_8.getValue(),"82");
 		playTypeMapJc.put(PlayType.jclq_mix_7_21.getValue(),"83");
 		playTypeMapJc.put(PlayType.jclq_mix_7_35.getValue(),"84");
 		playTypeMapJc.put(PlayType.jclq_mix_7_120.getValue(),"85");
 		playTypeMapJc.put(PlayType.jclq_mix_8_8.getValue(),"91");
 		playTypeMapJc.put(PlayType.jclq_mix_8_9.getValue(),"92");
 		playTypeMapJc.put(PlayType.jclq_mix_8_28.getValue(),"93");
 		playTypeMapJc.put(PlayType.jclq_mix_8_56.getValue(),"94");
 		playTypeMapJc.put(PlayType.jclq_mix_8_70.getValue(),"95");
 		playTypeMapJc.put(PlayType.jclq_mix_8_247.getValue(),"96");
 		
 		playTypeMapJc.put(PlayType.dc_bf_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.dc_bf_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.dc_bf_2_3.getValue(),"17");
 		playTypeMapJc.put(PlayType.dc_bf_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.dc_bf_3_4.getValue(),"18");
 		playTypeMapJc.put(PlayType.dc_bf_3_7.getValue(),"19");
 		
 		playTypeMapJc.put(PlayType.dc_bqc_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.dc_bqc_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.dc_bqc_2_3.getValue(),"17");
 		playTypeMapJc.put(PlayType.dc_bqc_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.dc_bqc_3_4.getValue(),"18");
 		playTypeMapJc.put(PlayType.dc_bqc_3_7.getValue(),"19");
 		playTypeMapJc.put(PlayType.dc_bqc_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.dc_bqc_4_5.getValue(),"20");
 		playTypeMapJc.put(PlayType.dc_bqc_4_11.getValue(),"21");
 		playTypeMapJc.put(PlayType.dc_bqc_4_15.getValue(),"22");
 		playTypeMapJc.put(PlayType.dc_bqc_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.dc_bqc_5_6.getValue(),"23");
 		playTypeMapJc.put(PlayType.dc_bqc_5_16.getValue(),"24");
 		playTypeMapJc.put(PlayType.dc_bqc_5_26.getValue(),"25");
 		playTypeMapJc.put(PlayType.dc_bqc_5_31.getValue(),"26");
 		playTypeMapJc.put(PlayType.dc_bqc_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.dc_bqc_6_7.getValue(),"27");
 		playTypeMapJc.put(PlayType.dc_bqc_6_22.getValue(),"28");
 		playTypeMapJc.put(PlayType.dc_bqc_6_42.getValue(),"29");
 		playTypeMapJc.put(PlayType.dc_bqc_6_57.getValue(),"30");
 		playTypeMapJc.put(PlayType.dc_bqc_6_63.getValue(),"31");
 		
 		
 		playTypeMapJc.put(PlayType.dc_spf_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.dc_spf_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.dc_spf_2_3.getValue(),"17");
 		playTypeMapJc.put(PlayType.dc_spf_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.dc_spf_3_4.getValue(),"18");
 		playTypeMapJc.put(PlayType.dc_spf_3_7.getValue(),"19");
 		playTypeMapJc.put(PlayType.dc_spf_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.dc_spf_4_5.getValue(),"20");
 		playTypeMapJc.put(PlayType.dc_spf_4_11.getValue(),"21");
 		playTypeMapJc.put(PlayType.dc_spf_4_15.getValue(),"22");
 		playTypeMapJc.put(PlayType.dc_spf_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.dc_spf_5_6.getValue(),"23");
 		playTypeMapJc.put(PlayType.dc_spf_5_16.getValue(),"24");
 		playTypeMapJc.put(PlayType.dc_spf_5_26.getValue(),"25");
 		playTypeMapJc.put(PlayType.dc_spf_5_31.getValue(),"26");
 		playTypeMapJc.put(PlayType.dc_spf_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.dc_spf_6_7.getValue(),"27");
 		playTypeMapJc.put(PlayType.dc_spf_6_22.getValue(),"28");
 		playTypeMapJc.put(PlayType.dc_spf_6_42.getValue(),"29");
 		playTypeMapJc.put(PlayType.dc_spf_6_57.getValue(),"30");
 		playTypeMapJc.put(PlayType.dc_spf_6_63.getValue(),"31");
 		playTypeMapJc.put(PlayType.dc_spf_7_1.getValue(),"08");
 		playTypeMapJc.put(PlayType.dc_spf_8_1.getValue(),"09");
 		playTypeMapJc.put(PlayType.dc_spf_9_1.getValue(),"10");
 		playTypeMapJc.put(PlayType.dc_spf_10_1.getValue(),"11");
 		playTypeMapJc.put(PlayType.dc_spf_11_1.getValue(),"12");
 		playTypeMapJc.put(PlayType.dc_spf_12_1.getValue(),"13");
 	    playTypeMapJc.put(PlayType.dc_spf_13_1.getValue(),"14");
 	    playTypeMapJc.put(PlayType.dc_spf_14_1.getValue(),"15");
 	    playTypeMapJc.put(PlayType.dc_spf_15_1.getValue(),"16");
 		
 		
 	    playTypeMapJc.put(PlayType.dc_sxds_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.dc_sxds_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.dc_sxds_2_3.getValue(),"17");
 		playTypeMapJc.put(PlayType.dc_sxds_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.dc_sxds_3_4.getValue(),"18");
 		playTypeMapJc.put(PlayType.dc_sxds_3_7.getValue(),"19");
 		playTypeMapJc.put(PlayType.dc_sxds_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.dc_sxds_4_5.getValue(),"20");
 		playTypeMapJc.put(PlayType.dc_sxds_4_11.getValue(),"21");
 		playTypeMapJc.put(PlayType.dc_sxds_4_15.getValue(),"22");
 		playTypeMapJc.put(PlayType.dc_sxds_5_1.getValue(),"06");
 	    playTypeMapJc.put(PlayType.dc_sxds_5_6.getValue(),"23");
 		playTypeMapJc.put(PlayType.dc_sxds_5_16.getValue(),"24");
 		playTypeMapJc.put(PlayType.dc_sxds_5_26.getValue(),"25");
 		playTypeMapJc.put(PlayType.dc_sxds_5_31.getValue(),"26");
 		playTypeMapJc.put(PlayType.dc_sxds_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.dc_sxds_6_7.getValue(),"27");
 		playTypeMapJc.put(PlayType.dc_sxds_6_22.getValue(),"28");
 		playTypeMapJc.put(PlayType.dc_sxds_6_42.getValue(),"29");
 		playTypeMapJc.put(PlayType.dc_sxds_6_57.getValue(),"30");
 		playTypeMapJc.put(PlayType.dc_sxds_6_63.getValue(),"31");
 		
 		
 		playTypeMapJc.put(PlayType.dc_zjq_1_1.getValue(),"02");
 		playTypeMapJc.put(PlayType.dc_zjq_2_1.getValue(),"03");
 		playTypeMapJc.put(PlayType.dc_zjq_2_3.getValue(),"17");
 		playTypeMapJc.put(PlayType.dc_zjq_3_1.getValue(),"04");
 		playTypeMapJc.put(PlayType.dc_zjq_3_4.getValue(),"18");
 		playTypeMapJc.put(PlayType.dc_zjq_3_7.getValue(),"19");
 		playTypeMapJc.put(PlayType.dc_zjq_4_1.getValue(),"05");
 		playTypeMapJc.put(PlayType.dc_zjq_4_5.getValue(),"20");
 		playTypeMapJc.put(PlayType.dc_zjq_4_11.getValue(),"21");
 		playTypeMapJc.put(PlayType.dc_zjq_4_15.getValue(),"22");
 		playTypeMapJc.put(PlayType.dc_zjq_5_1.getValue(),"06");
 		playTypeMapJc.put(PlayType.dc_zjq_5_6.getValue(),"23");
 		playTypeMapJc.put(PlayType.dc_zjq_5_16.getValue(),"24");
 		playTypeMapJc.put(PlayType.dc_zjq_5_26.getValue(),"25");
 		playTypeMapJc.put(PlayType.dc_zjq_5_31.getValue(),"26");
 		playTypeMapJc.put(PlayType.dc_zjq_6_1.getValue(),"07");
 		playTypeMapJc.put(PlayType.dc_zjq_6_7.getValue(),"27");
 		playTypeMapJc.put(PlayType.dc_zjq_6_22.getValue(),"28");
 		playTypeMapJc.put(PlayType.dc_zjq_6_42.getValue(),"29");
 		playTypeMapJc.put(PlayType.dc_zjq_6_57.getValue(),"30");
 		playTypeMapJc.put(PlayType.dc_zjq_6_63.getValue(),"31");
 		
 		
 		//山东11x5
 		playTypeMapJc.put(PlayType.sd11x5_sr2.getValue(), "2");
 		playTypeMapJc.put(PlayType.sd11x5_sr3.getValue(), "3");
 		playTypeMapJc.put(PlayType.sd11x5_sr4.getValue(), "4");
		playTypeMapJc.put(PlayType.sd11x5_sr5.getValue(), "5");
		playTypeMapJc.put(PlayType.sd11x5_sr6.getValue(), "6");
		playTypeMapJc.put(PlayType.sd11x5_sr7.getValue(), "7");
		playTypeMapJc.put(PlayType.sd11x5_sr8.getValue(), "8");
		playTypeMapJc.put(PlayType.sd11x5_sq1.getValue(), "1");
		playTypeMapJc.put(PlayType.sd11x5_sq2.getValue(), "9");
		playTypeMapJc.put(PlayType.sd11x5_sq3.getValue(), "10");
		playTypeMapJc.put(PlayType.sd11x5_sz2.getValue(), "11");
		playTypeMapJc.put(PlayType.sd11x5_sz3.getValue(), "12");
		
		playTypeMapJc.put(PlayType.sd11x5_mr2.getValue(), "2");
		playTypeMapJc.put(PlayType.sd11x5_mr3.getValue(), "3");
		playTypeMapJc.put(PlayType.sd11x5_mr4.getValue(), "4");
		playTypeMapJc.put(PlayType.sd11x5_mr5.getValue(), "5");
		playTypeMapJc.put(PlayType.sd11x5_mr6.getValue(), "6");
		playTypeMapJc.put(PlayType.sd11x5_mr7.getValue(), "7");
		playTypeMapJc.put(PlayType.sd11x5_mr8.getValue(), "8");
		playTypeMapJc.put(PlayType.sd11x5_mq1.getValue(), "1");
		playTypeMapJc.put(PlayType.sd11x5_mq2.getValue(), "9");
		playTypeMapJc.put(PlayType.sd11x5_mq3.getValue(), "10");
		playTypeMapJc.put(PlayType.sd11x5_mz2.getValue(), "11");
		playTypeMapJc.put(PlayType.sd11x5_mz3.getValue(), "12");
		
		playTypeMapJc.put(PlayType.sd11x5_dr2.getValue(), "2");
		playTypeMapJc.put(PlayType.sd11x5_dr3.getValue(), "3");
		playTypeMapJc.put(PlayType.sd11x5_dr4.getValue(), "4");
		playTypeMapJc.put(PlayType.sd11x5_dr5.getValue(), "5");
		playTypeMapJc.put(PlayType.sd11x5_dr6.getValue(), "6");
		playTypeMapJc.put(PlayType.sd11x5_dr7.getValue(), "7");
		playTypeMapJc.put(PlayType.sd11x5_dr8.getValue(), "8");
		playTypeMapJc.put(PlayType.sd11x5_dz2.getValue(), "11");
		playTypeMapJc.put(PlayType.sd11x5_dz3.getValue(), "12");
		
		playTypeMapJc.put(PlayType.cqssc_dan_1d.getValue(), "1");
		playTypeMapJc.put(PlayType.cqssc_dan_2d.getValue(), "2");
		playTypeMapJc.put(PlayType.cqssc_dan_3d.getValue(), "3");
		playTypeMapJc.put(PlayType.cqssc_dan_5d.getValue(), "5");
		playTypeMapJc.put(PlayType.cqssc_dan_z3.getValue(), "3");
		playTypeMapJc.put(PlayType.cqssc_dan_z6.getValue(), "3");
		
		playTypeMapJc.put(PlayType.cqssc_fu_1d.getValue(), "1");
		playTypeMapJc.put(PlayType.cqssc_fu_2d.getValue(), "2");
		playTypeMapJc.put(PlayType.cqssc_fu_3d.getValue(), "3");
		playTypeMapJc.put(PlayType.cqssc_fu_5d.getValue(), "5");
		
//		playTypeMapJc.put(PlayType.cqssc_other_2h.getValue(), "12");
//		playTypeMapJc.put(PlayType.cqssc_other_3h.getValue(), "12");
		playTypeMapJc.put(PlayType.cqssc_other_2z.getValue(), "2");
		playTypeMapJc.put(PlayType.cqssc_other_z3.getValue(), "3");
		playTypeMapJc.put(PlayType.cqssc_other_z6.getValue(), "3");
		playTypeMapJc.put(PlayType.cqssc_other_dd.getValue(), "8");
		playTypeMapJc.put(PlayType.cqssc_other_5t.getValue(), "5");
		
		//广西快三
		playTypeMapJc.put(PlayType.gxk3_ertong_dan.getValue(), "5");
		playTypeMapJc.put(PlayType.gxk3_ertong_fu.getValue(), "5");
//		playTypeMapJc.put(PlayType.gxk3_ertong_zuhe.getValue(), "5");
		playTypeMapJc.put(PlayType.gxk3_erbutong_dan.getValue(), "6");
//		playTypeMapJc.put(PlayType.gxk3_erbutong_zuhe.getValue(), "5");
//		playTypeMapJc.put(PlayType.gxk3_erbutong_dt.getValue(), "5");
		playTypeMapJc.put(PlayType.gxk3_santong_dan.getValue(), "2");
		playTypeMapJc.put(PlayType.gxk3_santong_tongxuan.getValue(), "2");
		playTypeMapJc.put(PlayType.gxk3_sanbutong_dan.getValue(), "3");
//		playTypeMapJc.put(PlayType.gxk3_sanbutong_zuhe.getValue(), "5");
//		playTypeMapJc.put(PlayType.gxk3_sanbutong_dt.getValue(), "5");
		playTypeMapJc.put(PlayType.gxk3_sanlian_tongxuan.getValue(), "4");
		playTypeMapJc.put(PlayType.gxk3_hezhi.getValue(), "1");
 		
		//双色球、七乐彩 大乐透 单式
		IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr=ticket.getContent().substring(0,ticket.getContent().length()-1).split("-")[1].replace(",","");
				return contentStr;
			}
		};
		//双色球、七乐彩胆拖
		IVenderTicketConverter ssq_dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr=ticket.getContent().substring(0,ticket.getContent().length()-1).split("-")[1].replace(",","").replace("#", "*");
				return contentStr;
			}
		};
		//大乐透胆拖
				IVenderTicketConverter dlt_dt = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						String []contentStrs=ticket.getContent().replace("^","").split("-")[1].split("\\|");
						int i=0;
						for(String str:contentStrs){
							if(!str.contains("#")){
								strBuilder.append("*").append(str.replace(",",""));
							}else{
								strBuilder.append(str.replace(",","").replace("#", "*"));
							}
                            if(i!=contentStrs.length-1){
                            	strBuilder.append("|");
							}
							i++;
						}
						return strBuilder.toString();
					}
				};
		//3D组三六单式、复式
		IVenderTicketConverter sand = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.split("\\^");
				int i=0;
				for(String s:strs){
					String []nums=s.split(",");
					for(String num:nums){
						strBuilder.append(Integer.parseInt(num));
					}
					if(i!=strs.length-1){
						strBuilder.append("^");
					}
					i++;
				}
				return strBuilder.toString();
		}};
		
		//3D直选和值、组三和值、组六和值
		IVenderTicketConverter sandhezhi = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String contentStr = ticket.getContent().split("-")[1];
				String str=contentStr.replace("^", "");
				strBuilder.append(Integer.parseInt(str));
				return strBuilder.toString();
		}};
		
		//3D直选和值、组三和值、组六和值
		IVenderTicketConverter sandzxhz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String contentStr = ticket.getContent().split("-")[1];
				String str=contentStr.replace("^", "");
				if(String.valueOf(Integer.parseInt(str)).length() ==1){
					strBuilder.append("00").append(Integer.parseInt(str));
				}else if(String.valueOf(Integer.parseInt(str)).length() ==2){
					strBuilder.append("0").append(Integer.parseInt(str));
				}else{
					strBuilder.append(Integer.parseInt(str));
				}
				return strBuilder.toString();		
		}};
		//排三、排六直选和值、组三和值、组六和值
		IVenderTicketConverter paisanhezhi = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String contentStr = ticket.getContent().split("-")[1];
				String []strs=contentStr.replace("^", "").split("\\,");
				for(String str:strs){
					strBuilder.append(Integer.parseInt(str));
				}
				return strBuilder.toString();
		}};
		//3d直选单式、直选复式、组选单式
		IVenderTicketConverter sandfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				int i=0;
				String []strs=contentStr.replace("^", "").split("\\|");
				for(String s:strs){
					String []nums=s.split(",");
					for(String num:nums){
						strBuilder.append(Integer.parseInt(num));
					}
					if(i!=strs.length-1){
						strBuilder.append("*");
					}
					i++;
				}
		        return strBuilder.toString();
			}};
			 //排三复式
			IVenderTicketConverter paisan = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
			        String []strs=contentStr.replace("^", "").split("\\|");
			        int i=0;
			        for(String s:strs){
						String []nums=s.split(",");
						for(String num:nums){
							strBuilder.append(Integer.parseInt(num));
						}
						if(i!=strs.length-1){
							strBuilder.append("*");
						}
						i++;
					}
			       return strBuilder.toString();
			}};
	     //七星彩
			IVenderTicketConverter qxc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
			        StringBuilder strBuilder=new StringBuilder();
			        String []strs=contentStr.split("\\^");
			        int j=0;
			        for(String s:strs){
			    	   int i=0;
						String []nums=s.split(",");
						for(String num:nums){
							strBuilder.append(Integer.parseInt(num));
							if(i!=nums.length-1){
								strBuilder.append("*");
							}
							i++;
						}
						if(j!=strs.length-1){
							strBuilder.append("^");
						}
				        j++;
			        }
			          return strBuilder.toString();
			}};
			
			//重庆时时彩
			IVenderTicketConverter ssc_ds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []contentStr=ticket.getContent().split("-")[1].split("\\^");
					int i=0;
					for(String str:contentStr){
						strBuilder.append(str);
						if(i!=str.length()-1){
							strBuilder.append(";");
						}
						i++;
					}
					return strBuilder.toString();
				}
			};
			//重庆时时彩复式
			IVenderTicketConverter ssc_fs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace(",","").replace("^", "");
					return contentStr;
				}
			};
			//重庆时时彩二星单式
			IVenderTicketConverter ssc_exds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []contentStr=ticket.getContent().split("-")[1].replace(",","*").split("\\^");
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
			//重庆时时彩二星复式
			IVenderTicketConverter ssc_exfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace(",","").replace(";","*").replace("^","");
					return contentStr;
				}
			};
			//重庆时时彩大小单双
			IVenderTicketConverter ssc_dx = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1].replace("2", "9").replace("1", "0").replace("5", "1").replace("4", "2").replace(";", "*").replace("^", "");
					return contentStr;
				}
			};
			
			//山东11x5
			IVenderTicketConverter sd_11x5 = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr=ticket.getContent().split("-")[1].replace("^","").replace(";","^");
					return contentStr;
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
							strBuilder.append(";");
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
			
			//广西快三
			IVenderTicketConverter gxksds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []contentStr=ticket.getContent().split("-")[1].replace(",","*").split("\\^");
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
			
			//广西快三
			IVenderTicketConverter gxkrteds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []contentStr=ticket.getContent().split("-")[1].replace(",","*").split("\\^");
					int i=0;
					for(String str:contentStr){
						int first=Integer.parseInt(str.substring(0,1));
						int third=Integer.parseInt(str.substring(2,3));
						if(first<third){
							strBuilder.append(str.substring(2,3)+"*"+str.substring(4,5)+"*"+str.substring(0,1));
						}else{
							strBuilder.append(str);
						}
						if(i!=contentStr.length-1){
							strBuilder.append(";");
						}
						i++;
					}
					return strBuilder.toString();
				}
			};
			
			IVenderTicketConverter gxtx = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					strBuilder.append("1*2*3*4*5*6");
					
					return strBuilder.toString();
				}
			};
			 //足彩胜负彩单式、半全场单式、四场进球单式
			IVenderTicketConverter zcsfc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
					String []strs=contentStr.split("\\^");
					int i=0;
					for(String s:strs){
						String content=s.replace(",", "");
						strBuilder.append(content);
						if(i!=strs.length-1){
							strBuilder.append("^");
						}
						i++;
					}
			        return strBuilder.toString();
			}};
			 //足彩胜负彩半全场复式
			IVenderTicketConverter zcsfcfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					String content=contentStr.replace(",", "*").replace("^","");
			        return content;
			}};
			
			//足彩任9场
			IVenderTicketConverter zcrjcds = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
					String []strs=contentStr.split("\\^");
					int i=0;
					for(String s:strs){
						String content=s.replace(",", "").replace("~", "#");
						strBuilder.append(content);
						if(i!=strs.length-1){
							strBuilder.append("^");
						}
						i++;
					}
			        return strBuilder.toString();
			}};
			//足彩任9场复式
			IVenderTicketConverter zcrjcfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					String content=contentStr.replace(",", "*").replace("~", "#").replace("^","");
			        return content;
			}};
		    //竞彩足球单关
			IVenderTicketConverter jcspf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].split("\\^");
			        int i=0;
			        for(String str:strs){
			        	strBuffer.append(str.substring(2, 8)+"-"+str.substring(8));
			        	if(i!=strs.length-1){
			        		strBuffer.append(";");
			        	}
			        	
			        	i++;
			        }
					return strBuffer.toString();
			}};
             //竞彩足球过关
			IVenderTicketConverter jcspfgg = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
			        for(String str:strs){
			        	strBuffer.append(str.substring(2, 8)+"-"+str.substring(8));
			        	if(i!=strs.length-1){
			        		strBuffer.append(";");
			        	}
			        	
			        	i++;
			        }
	                  return strBuffer.toString();
				}};
				
				
				IVenderTicketConverter jczqmix = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer strBuffer=new StringBuffer();
				        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				        int i=0;
				        for(String str:strs){
				        	String []nums=str.split("\\*");
				        	strBuffer.append(playTypeMapJcMix.get(Integer.parseInt(nums[1].split("\\(")[0]))).append("^");
				        	strBuffer.append(nums[0].substring(2, 8)+"-"+nums[0].substring(8)).append("(");
				        	strBuffer.append(nums[1].split("\\(")[1]);
				        	if(i!=strs.length-1){
				        		strBuffer.append(";");
				        	}
				        	
				        	i++;
				        }
				        return strBuffer.toString();
					}};
				
				//竞彩蓝球单关
				IVenderTicketConverter jclqspf = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer strBuffer=new StringBuffer();
				        String strs[]=ticket.getContent().split("-")[1].split("\\^");
				        int i=0;
				        for(String str:strs){
				        	strBuffer.append(str.substring(0, 8)+"-"+str.substring(8));
				        	if(i!=strs.length-1){
				        		strBuffer.append(";");
				        	}
				        	
				        	i++;
				        }
						return strBuffer.toString();
				}};
	             //竞彩篮球过关
				IVenderTicketConverter jclqspfgg = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer strBuffer=new StringBuffer();
				        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				        int i=0;
				        for(String str:strs){
				        	strBuffer.append(str.substring(0, 8)+"-"+str.substring(8,12));
				        	String []nums=str.split("\\(")[1].split("\\)")[0].split("\\,");
				        	int j=0;
				        	for(String num:nums){
				        		strBuffer.append(Integer.parseInt(num));
				        		if(j!=nums.length-1){
				        			strBuffer.append(",");
				        		}
				        		j++;
				        	}
				        	strBuffer.append(")");
				        	if(i!=strs.length-1){
				        		strBuffer.append(";");
				        	}
				        	
				        	i++;
				        }
		                  return strBuffer.toString();
					}};
					
					
					 //竞彩篮球胜分差
					IVenderTicketConverter jclqsfc = new IVenderTicketConverter() {
						@Override
						public String convert(Ticket ticket) {
							StringBuffer strBuffer=new StringBuffer();
					        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
					        int i=0;
					        for(String str:strs){
					        	strBuffer.append(str.substring(0, 8)+"-"+str.substring(8,12));
					        	String []nums=str.split("\\(")[1].split("\\)")[0].split("\\,");
					        	int j=0;
					        	for(String num:nums){
					        		strBuffer.append(numberMap.get(num));
					        		if(j!=nums.length-1){
					        			strBuffer.append(",");
					        		}
					        		j++;
					        	}
					        	strBuffer.append(")");
					        	if(i!=strs.length-1){
					        		strBuffer.append(";");
					        	}
					        	
					        	i++;
					        }
			                  return strBuffer.toString();
						}};
					//竞彩篮球混合过关
					IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
						@Override
						public String convert(Ticket ticket) {
							StringBuffer strBuffer=new StringBuffer();
					        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
					        int i=0;
					        for(String str:strs){
					        	String []numbers=str.split("\\*");
					        	strBuffer.append(playTypeMapJcMix.get(Integer.parseInt(numbers[1].split("\\(")[0]))).append("^");
					        	strBuffer.append(numbers[0].substring(0, 8)+"-"+numbers[0].substring(8)).append("(");
					        	String []nums=str.split("\\(")[1].split("\\)")[0].split("\\,");
					        	int j=0;
					        	if("217".equals(playTypeMapJcMix.get(Integer.parseInt(numbers[1].split("\\(")[0])))){
					        		for(String num:nums){
						        		strBuffer.append(numberMap.get(num));
						        		if(j!=nums.length-1){
						        			strBuffer.append(",");
						        		}
						        		j++;
						        	}
					        	}else{
					        		for(String num:nums){
						        		strBuffer.append(Integer.parseInt(num));
						        		if(j!=nums.length-1){
						        			strBuffer.append(",");
						        		}
						        		j++;
						        	}
					        	}
					        	strBuffer.append(")");
					        	if(i!=strs.length-1){
					        		strBuffer.append(";");
					        	}
					        	
					        	i++;
					        }
			                  return strBuffer.toString();
						}};
			//单场胜平负
				IVenderTicketConverter dc_spf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {		
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
			        for(String s:strs){
			        	String []nn=s.split("\\(")[1].split("\\)");
			        	String []nums=nn[0].split("\\,");
			        	int j=0;
			        	strBuffer.append(Integer.parseInt(s.split("\\(")[0])).append("(");
			        	for(String num:nums){
			        		strBuffer.append(Integer.parseInt(num));
			        		if(j!=nums.length-1){
			        			strBuffer.append(",");
			        		}
			        		j++;
			        	}
			        	strBuffer.append(")");
			        	if(i!=strs.length-1){
			        		strBuffer.append(";");
			        	}
			        	
			        	i++;
			          }
			        return strBuffer.toString();
				}};
				
				//单场半全场
				IVenderTicketConverter dc_bqc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {		
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
			        for(String s:strs){
			        	String []nn=s.split("\\(");
			        	String []nums=nn[1].split("\\,");
			        	strBuffer.append(Integer.parseInt(nn[0])).append("(");
			        	int j=0;
			        	for(String num:nums){
			        		strBuffer.append(bqcMap.get(num.substring(0,2)));
			        		if(j!=nums.length-1){
			        			strBuffer.append(",");
			        		}
			        		j++;
			        	}
			        	strBuffer.append(")");
			        	
			        	if(i!=strs.length-1){
			        		strBuffer.append(";");
			        	}
			        	
			        	i++;
			          }
			        return strBuffer.toString();
				}};
				
				//单场进球数
				IVenderTicketConverter dc_jqs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {		
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
			        for(String s:strs){
			        	String []nn=s.split("\\(")[1].split("\\)");
			        	String []nums=nn[0].split("\\,");
			        	int j=0;
			        	strBuffer.append(Integer.parseInt(s.split("\\(")[0])).append("(");
			        	for(String num:nums){
			        		strBuffer.append(jqsMap.get(num));
			        		if(j!=nums.length-1){
			        			strBuffer.append(",");
			        		}
			        		j++;
			        	}
			        	strBuffer.append(")");
			        	if(i!=strs.length-1){
			        		strBuffer.append(";");
			        	}
			        	
			        	i++;
			          }
			        return strBuffer.toString();
				}};
				
				//单场比分
				IVenderTicketConverter dc_bf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {		
					StringBuffer strBuffer=new StringBuffer();
			        String strs[]=ticket.getContent().split("-")[1].replace("^", "").split("\\|");
			        int i=0;
			        for(String s:strs){
			        	String []nn=s.split("\\(")[1].split("\\)");
			        	String []nums=nn[0].split("\\,");
			        	int j=0;
			        	strBuffer.append(Integer.parseInt(s.split("\\(")[0])).append("(");
			        	for(String num:nums){
			        		strBuffer.append(bfMap.get(num));
			        		if(j!=nums.length-1){
			        			strBuffer.append(",");
			        		}
			        		j++;
			        	}
			        	strBuffer.append(")");
			        	if(i!=strs.length-1){
			        		strBuffer.append(";");
			        	}
			        	i++;
			          }
			        return strBuffer.toString();
				}};
		    //双色球
			playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssq_dt);
			//七乐彩
			playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, ssq_dt);
			//3D
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan,qxc);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, sand);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, sand);
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, sandfs);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, sand);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, sand);
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz,sandzxhz);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, sandhezhi);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, sandhezhi);
			//七星彩
			playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, qxc);
			playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, sandfs);
			//排五
			playTypeToAdvanceConverterMap.put(PlayType.p5_dan, qxc);
			playTypeToAdvanceConverterMap.put(PlayType.p5_fu, sandfs);
			
			//排三
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, sand);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, sand);
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, qxc);
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, paisan);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, paisanhezhi);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, paisanhezhi);
			playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt,paisanhezhi);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, paisanhezhi);
			playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, paisanhezhi);
			
			//大乐透
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dlt_dt);
			
			//重庆时时彩
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_1d,ssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_2d,ssc_exds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_3d, ssc_exds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_5d, ssc_exds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_z3, ssc_exds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_z6, ssc_exds);
			
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_1d, ssc_fs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_2d, ssc_exfs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_3d, ssc_exfs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_5d, ssc_exfs);
			
//			playTypeMap.put(PlayType.cqssc_other_2h.getValue(), "1");
//			playTypeMap.put(PlayType.cqssc_other_3h.getValue(), "1");
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_2z,ssc_exds);//单式支持
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_z3,ssc_fs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_z6,ssc_fs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_dd,ssc_dx);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_5t,ssc_exfs);
	 		
			//山东11x5
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8,sd_11x5_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1,sd_11x5);
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
			
			//足彩
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zcsfc);
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zcsfcfs);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zcrjcds);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zcrjcfs);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zcsfc);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zcsfcfs);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zcsfc);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zcsfcfs);
			
			
		    //竞彩
			 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_1_1, jcspf);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_2_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_1, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_3, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_3_4, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_4, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_5, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_6, jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11,jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20,jcspfgg);
	         playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8,  jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9,  jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247,jcspfgg);
	     	 
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1,jcspf);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16,jcspfgg);
	     	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57,jcspfgg);
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_1_1, jcspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_2_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_5_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_6_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_7_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_8_1, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_3, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_3_4, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_4, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_5, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_6, jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bf_4_11, jcspfgg);
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1,jcspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247,jcspfgg);
	    	 
	    	 
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
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1,jcspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6,jcspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11,jcspfgg);
	    	 
	    	 
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jclqspf);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jclqspfgg);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jclqspfgg);
	 			    		
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jclqspf);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jclqspfgg);
	 			    		
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
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4,jclqspfgg);
	 	 	 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70,jclqspfgg);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247,jclqspfgg);
	 		 
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
	 		 
	 		 
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_1_1,dc_bf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_1_1,dc_bf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_2_1,dc_bf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_2_3,dc_bf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_1,dc_bf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_4,dc_bf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_7,dc_bf);
	 		
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_1_1,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_2_1,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_2_3,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_1,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_4,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_7,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_1,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_5,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_11,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_15,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_1,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_6,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_16,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_26,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_31,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_1,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_7,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_22,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_42,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_57,dc_bqc);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_63,dc_bqc);
	 		
	 		
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_1_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_2_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_2_3,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_4,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_7,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_5,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_11,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_15,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_6,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_16,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_26,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_31,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_7,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_22,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_42,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_57,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_63,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_7_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_8_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_9_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_10_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_11_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_12_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_13_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_14_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_spf_15_1,dc_spf);
	 		
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_1_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_2_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_2_3,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_4,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_7,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_5,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_11,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_15,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_6,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_16,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_26,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_31,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_1,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_7,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_22,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_42,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_57,dc_spf);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_63,dc_spf);
	 		
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_1_1,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_2_1,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_2_3,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_1,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_4,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_7,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_1,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_5,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_11,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_15,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_1,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_6,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_16,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_26,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_31,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_1,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_7,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_22,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_42,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_57,dc_jqs);
	 		playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_63,dc_jqs);
	 		
	 		
	 		
	 		//广西快三
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_ertong_dan,gxksds);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_ertong_fu,gxksds);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_ertong_zuhe,gxksds);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_erbutong_dan,gxksds);
//	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_erbutong_zuhe,sd_11x5_fs);
//	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_erbutong_dt,sd_11x5_fs);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_santong_dan,gxkrteds);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_santong_tongxuan,gxtx);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanbutong_dan,gxksds);
//	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanbutong_zuhe,sd_11x5_fs);
//	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanbutong_dt,sd_11x5_fs);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanlian_tongxuan,gxtx);
	 		playTypeToAdvanceConverterMap.put(PlayType.gxk3_hezhi,gxksds);
	 		
	 		
	 		
	 		
	 		
	 		
	 		
	}
	
	
	
}
