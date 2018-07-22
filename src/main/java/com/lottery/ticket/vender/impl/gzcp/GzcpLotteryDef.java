package com.lottery.ticket.vender.impl.gzcp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.DateUtil;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class GzcpLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	protected static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
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
				return phase;
			}
		};
		phaseConverterMap.put(LotteryType.CJDLT, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_SFC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, defaultPhaseConverter);
		
		//彩种转换
		
		lotteryTypeMap.put(LotteryType.SSQ, "SSQ");
		lotteryTypeMap.put(LotteryType.F3D, "3D");
		lotteryTypeMap.put(LotteryType.QLC, "QLC");
		lotteryTypeMap.put(LotteryType.PL3, "D3");
		lotteryTypeMap.put(LotteryType.PL5, "D5");
		lotteryTypeMap.put(LotteryType.QXC, "D7");
		lotteryTypeMap.put(LotteryType.CJDLT, "T001");
		
		lotteryTypeMap.put(LotteryType.ZC_SFC, "D14");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "D9");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "C4");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "C12");
		
		lotteryTypeMap.put(LotteryType.JCLQ_SF,"BSK001");  
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"BSK002");  
		lotteryTypeMap.put(LotteryType.JCLQ_SFC,"BSK003");  
		lotteryTypeMap.put(LotteryType.JCLQ_DXF,"BSK004");  
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG,"BSK005");  
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"FT006");  
		lotteryTypeMap.put(LotteryType.JCZQ_BF,"FT002"); 
		lotteryTypeMap.put(LotteryType.JCZQ_JQS,"FT003"); 
		lotteryTypeMap.put(LotteryType.JCZQ_BQC,"FT004"); 
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"FT001"); 
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG,"FT005"); 
		
		toLotteryTypeMap.put("SSQ", LotteryType.SSQ);
		toLotteryTypeMap.put("3D", LotteryType.F3D);
		toLotteryTypeMap.put("QLC", LotteryType.QLC);
		toLotteryTypeMap.put("D3", LotteryType.PL3);
		toLotteryTypeMap.put("D5", LotteryType.PL5);
		toLotteryTypeMap.put("D7", LotteryType.QXC);
		toLotteryTypeMap.put("T001", LotteryType.CJDLT);
		
		toLotteryTypeMap.put("D14", LotteryType.ZC_SFC);
		toLotteryTypeMap.put("D9", LotteryType.ZC_RJC);
		toLotteryTypeMap.put("C4", LotteryType.ZC_JQC);
		toLotteryTypeMap.put("C12", LotteryType.ZC_BQC);
		
		toLotteryTypeMap.put("BSK001", LotteryType.JCLQ_SF);
		toLotteryTypeMap.put("BSK002", LotteryType.JCLQ_RFSF);
		toLotteryTypeMap.put("BSK003", LotteryType.JCLQ_SFC);
		toLotteryTypeMap.put("BSK004", LotteryType.JCLQ_DXF);
		toLotteryTypeMap.put("BSK005", LotteryType.JCLQ_HHGG);
		toLotteryTypeMap.put("FT006", LotteryType.JCZQ_RQ_SPF);
		toLotteryTypeMap.put("FT002", LotteryType.JCZQ_BF);
		toLotteryTypeMap.put("FT003", LotteryType.JCZQ_JQS);
		toLotteryTypeMap.put("FT004", LotteryType.JCZQ_BQC);
		toLotteryTypeMap.put("FT001", LotteryType.JCZQ_SPF_WRQ);
		toLotteryTypeMap.put("FT005", LotteryType.JCZQ_HHGG);
		
		
		
		
		
		
		//玩法转换
		// 双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "0");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "1");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "2");
		// 3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "0");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "1");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "2");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "3");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "4");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "5");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "9");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "10");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "11");
		// 七星彩
		playTypeMap.put(PlayType.qxc_dan.getValue(), "0");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "1");
		// 七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "0");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "1");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "2");
		// 大乐透
		playTypeMap.put(PlayType.dlt_dan.getValue(), "0");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "1");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "2");
		// 排三
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "0");// 直选
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "1");// 直选复式
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "3");// 组选3
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "3");// 组选6
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "5");// 组三复试
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "4");// 组六复式
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "6");// 组三和值
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "6");// 组六和值
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "2");// 直选和值
		// 排五
		playTypeMap.put(PlayType.p5_dan.getValue(), "0");
		playTypeMap.put(PlayType.p5_fu.getValue(), "1");
		
		
		
		
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
     	 playTypeMap.put(PlayType.jczq_spf_8_247.getValue(), "538");
     	 
     	 playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(), "500");
     	 playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(), "502");
     	 playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(), "503");
     	 playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(), "504");
     	 playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(), "505");
     	 playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(), "506");
     	 playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(), "507");
     	 playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(), "508");
     	 playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(), "526");
     	 playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(), "527");
     	 playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(), "539");
     	 playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(), "540");
     	 playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(), "528");
     	 playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(), "529");
     	 playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(), "544");
     	 playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(), "545");
     	 playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(), "530");
     	 playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(), "541");
     	 playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(), "531");
     	 playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(), "532");
    	 playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(), "549");
    	 playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(), "550");
    	 playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(), "533");
    	 playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(), "542");
    	 playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(), "546");
    	 playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(), "534");
    	 playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(), "543");
    	 playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(), "535");
    	 playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(), "536");
    	 
    	 playTypeMap.put(PlayType.jczq_bf_1_1.getValue(), "500");
    	 playTypeMap.put(PlayType.jczq_bf_2_1.getValue(), "502");
    	 playTypeMap.put(PlayType.jczq_bf_3_1.getValue(), "503");
    	 playTypeMap.put(PlayType.jczq_bf_4_1.getValue(), "504");
    	 playTypeMap.put(PlayType.jczq_bf_5_1.getValue(), "505");
    	 playTypeMap.put(PlayType.jczq_bf_6_1.getValue(), "506");
    	 playTypeMap.put(PlayType.jczq_bf_7_1.getValue(), "507");
    	 playTypeMap.put(PlayType.jczq_bf_8_1.getValue(), "508");
    	 playTypeMap.put(PlayType.jczq_bf_3_3.getValue(), "526");
    	 playTypeMap.put(PlayType.jczq_bf_3_4.getValue(), "527");
    	 playTypeMap.put(PlayType.jczq_bf_4_4.getValue(), "539");
    	 playTypeMap.put(PlayType.jczq_bf_4_5.getValue(), "540");
    	 playTypeMap.put(PlayType.jczq_bf_4_6.getValue(), "528");
    	 playTypeMap.put(PlayType.jczq_bf_4_11.getValue(), "529");
    	 
    	 
    	 playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "500");
    	 playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "502");
    	 playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "503");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "504");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "505");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "506");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "507");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "508");
    	 playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "526");
    	 playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "527");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "539");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "540");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "528");
    	 playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "529");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "544");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "545");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "530");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "541");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "531");
    	 playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "532");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "549");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "550");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "533");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "542");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "546");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "534");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "543");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "535");
    	 playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "536");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "553");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "554");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "551");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "547");
    	 playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "537");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "556");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "557");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "555");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "552");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "548");
    	 playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "538");
    	 
    	 playTypeMap.put(PlayType.jczq_mix_2_1.getValue(), "502");
    	 playTypeMap.put(PlayType.jczq_mix_3_1.getValue(), "503");
    	 playTypeMap.put(PlayType.jczq_mix_4_1.getValue(), "504");
    	 playTypeMap.put(PlayType.jczq_mix_5_1.getValue(), "505");
    	 playTypeMap.put(PlayType.jczq_mix_6_1.getValue(), "506");
    	 playTypeMap.put(PlayType.jczq_mix_7_1.getValue(), "507");
    	 playTypeMap.put(PlayType.jczq_mix_8_1.getValue(), "508");
    	 playTypeMap.put(PlayType.jczq_mix_3_3.getValue(), "526");
    	 playTypeMap.put(PlayType.jczq_mix_3_4.getValue(), "527");
    	 playTypeMap.put(PlayType.jczq_mix_4_4.getValue(), "539");
    	 playTypeMap.put(PlayType.jczq_mix_4_5.getValue(), "540");
    	 playTypeMap.put(PlayType.jczq_mix_4_6.getValue(), "528");
    	 playTypeMap.put(PlayType.jczq_mix_4_11.getValue(), "529");
    	 playTypeMap.put(PlayType.jczq_mix_5_5.getValue(), "544");
    	 playTypeMap.put(PlayType.jczq_mix_5_6.getValue(), "545");
    	 playTypeMap.put(PlayType.jczq_mix_5_10.getValue(), "530");
    	 playTypeMap.put(PlayType.jczq_mix_5_16.getValue(), "541");
    	 playTypeMap.put(PlayType.jczq_mix_5_20.getValue(), "531");
    	 playTypeMap.put(PlayType.jczq_mix_5_26.getValue(), "532");
    	 playTypeMap.put(PlayType.jczq_mix_6_6.getValue(), "549");
    	 playTypeMap.put(PlayType.jczq_mix_6_7.getValue(), "550");
    	 playTypeMap.put(PlayType.jczq_mix_6_15.getValue(), "533");
    	 playTypeMap.put(PlayType.jczq_mix_6_20.getValue(), "542");
    	 playTypeMap.put(PlayType.jczq_mix_6_22.getValue(), "546");
    	 playTypeMap.put(PlayType.jczq_mix_6_35.getValue(), "534");
    	 playTypeMap.put(PlayType.jczq_mix_6_42.getValue(), "543");
    	 playTypeMap.put(PlayType.jczq_mix_6_50.getValue(), "535");
    	 playTypeMap.put(PlayType.jczq_mix_6_57.getValue(), "536");
    	 playTypeMap.put(PlayType.jczq_mix_7_7.getValue(), "553");
    	 playTypeMap.put(PlayType.jczq_mix_7_8.getValue(), "554");
    	 playTypeMap.put(PlayType.jczq_mix_7_21.getValue(), "551");
    	 playTypeMap.put(PlayType.jczq_mix_7_35.getValue(), "547");
    	 playTypeMap.put(PlayType.jczq_mix_7_120.getValue(), "537");
    	 playTypeMap.put(PlayType.jczq_mix_8_8.getValue(), "556");
    	 playTypeMap.put(PlayType.jczq_mix_8_9.getValue(), "557");
    	 playTypeMap.put(PlayType.jczq_mix_8_28.getValue(), "555");
    	 playTypeMap.put(PlayType.jczq_mix_8_56.getValue(), "552");
    	 playTypeMap.put(PlayType.jczq_mix_8_70.getValue(), "548");
    	 playTypeMap.put(PlayType.jczq_mix_8_247.getValue(), "538");
    	 
    	 
    	 playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(), "500");
    	 playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(), "502");
    	 playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(), "503");
    	 playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(), "504");
    	 playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(), "505");
    	 playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(), "506");
    	 playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(), "507");
    	 playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(), "508");
    	 playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(), "526");
    	 playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(), "527");
    	 playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(), "539");
    	 playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(), "540");
    	 playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(), "528");
    	 playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(), "529");
		
		
		
    	 
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
	
 		
 		 //双色球
		IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
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
		
		 //双色球复试
		IVenderTicketConverter ssqfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String content=contentStr.replace(",", "");
		        return content;
		}};
		
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
					
				}
				ticketContent.append("^");
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
					}
					ticketContent.append("^");
				}
				return ticketContent.toString();
			}
		};
		// 排列三 3d 排列五 七星彩 单式
		IVenderTicketConverter d3_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace(",", "");
				return contentStr;
			}
		};
		
		// 排列三 3直选和值
				IVenderTicketConverter p3_hz = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String contentStr = ticket.getContent().split("-")[1].replace(",", "");
						return "**"+contentStr;
					}
				};
		
		// 大乐透
		IVenderTicketConverter dlt_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace(",", "");
				return contentStr;
			}
		};
 		
		// 大乐透
				IVenderTicketConverter dlt_fs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer ticketContent = new StringBuffer();
						String contentStr = ticket.getContent().split("-")[1].replace("^", "");
							String[] content = contentStr.replace(",","").split("\\|");
							for (int i = 0; i < content.length; i++) {
								ticketContent.append("*");
								ticketContent.append(content[i]);
								if(i!=content.length-1){
									ticketContent.append("|");
								}
								
							}
			           return ticketContent.append("^").toString();
					}
				};
				
				// 大乐透
				IVenderTicketConverter dlt_dt = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String contentStr = ticket.getContent().split("-")[1];
						StringBuffer strBuffer=new StringBuffer();
						String []contents=contentStr.split("\\|");
						int i=0;
						for(String content:contents){
							if(content.contains("#")){
								strBuffer.append(content.replace("#","*").replace(",", ""));
							}else{
								strBuffer.append("*").append(content.replace(",", ""));
							}
							if(i!=contents.length-1){
								strBuffer.append("|");
							}
							i++;
							
						}
						return strBuffer.append("^").toString();
					}
				};
				
					
		// 排列三 3d 排列五 七星彩 单式
		IVenderTicketConverter p3_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						if(i!=0){
							ticketContent.append("*").append(Integer.parseInt(content[i]));
						}else{
							ticketContent.append(Integer.parseInt(content[i]));
						}
					}
					ticketContent.append("^");
				}
				return ticketContent.toString();
			}
		};
		
		// 排列三
		IVenderTicketConverter p3_zxfs = new IVenderTicketConverter() {
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
					if (j != oneCode.length - 1) {
						ticketContent.append("*");
					}
				}
				return ticketContent.append("^").toString();
			}
		};
		
		
		
		// 排列三
				IVenderTicketConverter p3_zsfs1 = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer ticketContent = new StringBuffer();
						String contentStr = ticket.getContent().split("-")[1];
						String[] oneCode = contentStr.replace("^", "").split("\\|");
						ticketContent.append("**");
						for (int j = 0; j < oneCode.length; j++) {
							String[] content = oneCode[j].split(",");
							for (int i = 0; i < content.length; i++) {
								ticketContent.append(Integer.parseInt(content[i]));
							}
							
						}
						return ticketContent.append("^").toString();
					}
				};
		// 排列三
		IVenderTicketConverter p3_zshz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer("**");
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] content = contentStr.split(",");
				for (int i = 0; i < content.length; i++) {
					ticketContent.append(content[i]);
				}
				return ticketContent.append("^").toString();
			}
		};
		
		// 排列五  单式
		IVenderTicketConverter p5_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
						if(i<content.length-1){
							ticketContent.append("*");
						}
					}
						ticketContent.append("^");
				}
				return ticketContent.toString();
			}
		};
		// 排列五  复式
		IVenderTicketConverter p5_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.replace("^","").split("\\|");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if (j != oneCode.length - 1) {
						ticketContent.append("*");
					}
				}
				return ticketContent.append("^").toString();
			}
		};
		
		//七星彩  复式
				IVenderTicketConverter qxc_fs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuffer ticketContent = new StringBuffer();
						String contentStr = ticket.getContent().split("-")[1];
						String[] oneCode = contentStr.replace("^","").split("\\|");
						for (int j = 0; j < oneCode.length; j++) {
							String[] content = oneCode[j].split(",");
							for (int i = 0; i < content.length; i++) {
								ticketContent.append(Integer.parseInt(content[i]));
							}
							if (j != oneCode.length - 1) {
								ticketContent.append("*");
							}
						}
						return ticketContent.append("^").toString();
					}
				};
		
			 //足彩胜负彩单式、半全场单式、四场进球单式
			IVenderTicketConverter zucai = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String contentStr = ticket.getContent().split("-")[1];
					StringBuilder strBuilder=new StringBuilder();
					String []strs=contentStr.split("\\^");
					
					boolean fushi = false;
					for(String s:strs){
						String content=s.replace(",", "*").replace("~", "4");
						strBuilder.append(content).append("^");
						if(!fushi) {
							fushi = isZucaiFushi(s);
						}
						
					}
//					strBuilder.append("$").append(fushi?"1":"0");
			        return strBuilder.toString();
			}};
			
			
			
			
			
			
			IVenderTicketConverter jc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
					StringBuffer strBuffer=new StringBuffer();
					
					for(String str:strs){
						Date date=DateUtil.parse("yyyy-MM-dd", str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8));
						String week=DateUtil.getWeekOfDate(date);
						strBuffer.append(str.substring(0, 8))
							.append("|")
							.append(week)
							.append("|")
							.append(str.substring(8, 11))
							.append("|")
							.append(str.substring(11).replace("(", "").replace(")", "").replace(",", ""))
							.append("^");
						
					}
					return strBuffer.toString();
				}};
				
				
				IVenderTicketConverter jcmix = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						String strs[]=ticket.getContent().split("-")[1].replace("^","").split("\\|");
						StringBuffer strBuffer=new StringBuffer();
						
						for(String str:strs){
							Date date=DateUtil.parse("yyyy-MM-dd", str.substring(0,4)+"-"+str.substring(4,6)+"-"+str.substring(6,8));
							String week=DateUtil.getWeekOfDate(date);
							strBuffer.append(str.substring(0, 8))
								.append("|")
								.append(week)
								.append("|")
								.append(str.substring(8, 11))
								.append("|")
								.append(lotteryTypeMap.get(LotteryType.getLotteryType(Integer.parseInt(str.substring(12, 16)))))
								.append("|")
								.append(str.substring(16).replace("(", "").replace(")", "").replace(",", ""))
								.append("^");
							
						}
						return strBuffer.toString();
					}};
		   
					playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
					playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssqfs);
					playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssqfs);

					playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, d3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, d3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, d3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, d3_zxfs);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, d3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, d3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, d3_hz);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, d3_hz);
					playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, d3_hz);

					playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, ssq_ds);
					playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, ssq_ds);
					playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, ssqfs);

					playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, d3_hz);
					playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, dlt_fs);
					playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dlt_dt);

					playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, p3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, p3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, p3_ds);
					playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, p3_zxfs);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, p3_zsfs1);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, p3_zsfs1);
					playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, p3_hz);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, p3_zshz);
					playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, p3_zshz);

					playTypeToAdvanceConverterMap.put(PlayType.p5_dan, p5_ds);
					playTypeToAdvanceConverterMap.put(PlayType.p5_fu, p5_fs);

					playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, p5_ds);
					playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, qxc_fs);			
				
			//足彩
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zucai);
			playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zucai);
			
			
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
	  		
	  		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22,jc);
	    	 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247,jc);
	 			    		
	 			    		
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70,jc);
	 		 playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247,jc);
	 			    		
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
	 		

			
	}
	                
	private static boolean isZucaiFushi(String betcode) {
		for(String code:betcode.split(",")) {
			if(code.length()>1) {
				return true;
			}
		}
		return false;
	}
	
	
	

	
}
