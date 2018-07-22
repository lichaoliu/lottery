 package com.lottery.ticket.vender.impl.shcp;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

public class SHCPLotteryDef {
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
    /** 票内容转换 */
    protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();
    static {	
    	
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
		
		//默认的期号转换
		IPhaseConverter phaseConverter=new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "20"+phase;
			}
		};
		
		
		phaseConverterMap.put(LotteryType.ZC_SFC, phaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, phaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, phaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, phaseConverter);
		phaseConverterMap.put(LotteryType.CJDLT, phaseConverter);
		phaseConverterMap.put(LotteryType.PL5, phaseConverter);
		phaseConverterMap.put(LotteryType.PL3, phaseConverter);
		phaseConverterMap.put(LotteryType.QXC, phaseConverter);
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.JX_11X5, phaseConverter);
		phaseConverterMap.put(LotteryType.SD_11X5, phaseConverter);
		phaseConverterMap.put(LotteryType.GD_11X5, phaseConverter);
		phaseConverterMap.put(LotteryType.HUBEI_11X5, defaultPhaseConverter);

		//彩种转换
		lotteryTypeMap.put(LotteryType.SSQ, "01");
		lotteryTypeMap.put(LotteryType.QLC, "07");
		lotteryTypeMap.put(LotteryType.F3D, "03");
		lotteryTypeMap.put(LotteryType.CJDLT, "50");
		lotteryTypeMap.put(LotteryType.PL3, "53");
		lotteryTypeMap.put(LotteryType.PL5, "52");
		lotteryTypeMap.put(LotteryType.QXC, "51");
		lotteryTypeMap.put(LotteryType.JX_11X5, "54");
		lotteryTypeMap.put(LotteryType.GD_11X5, "55");
		lotteryTypeMap.put(LotteryType.HUBEI_11X5, "58");
		lotteryTypeMap.put(LotteryType.SD_11X5, "56");
		lotteryTypeMap.put(LotteryType.ZC_SFC, "80");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "81");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "82");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "83");
		
		
	
		lotteryTypeMap.put(LotteryType.JCLQ_SF,"31");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF,"31");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC,"31"); 
		lotteryTypeMap.put(LotteryType.JCLQ_DXF,"31");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG,"31");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF,"30");
		lotteryTypeMap.put(LotteryType.JCZQ_BF,"30");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS,"30");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC,"30");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ,"30");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG,"30");
		
		lotteryTypeMap.put(LotteryType.DC_SPF,"32");
		lotteryTypeMap.put(LotteryType.DC_ZJQ,"32");
		lotteryTypeMap.put(LotteryType.DC_BQC,"32");
		lotteryTypeMap.put(LotteryType.DC_SXDS,"32");
		lotteryTypeMap.put(LotteryType.DC_BF,"32");
		lotteryTypeMap.put(LotteryType.DC_SFGG,"33");
		
		
		toLotteryTypeMap.put("3010", "SPF");
		toLotteryTypeMap.put("3007", "CBF");
		toLotteryTypeMap.put("3009", "BQC");
		toLotteryTypeMap.put("3008", "JQS");
		toLotteryTypeMap.put("3006", "RSPF");
		
		toLotteryTypeMap.put("3001", "SF");
		toLotteryTypeMap.put("3002", "RFSF");
		toLotteryTypeMap.put("3003", "SFC");
		toLotteryTypeMap.put("3004", "DXF");
		
		toLotteryTypeMap.put("5001", "SPF");
		toLotteryTypeMap.put("5002", "JQS");
		toLotteryTypeMap.put("5003", "BQC");
		toLotteryTypeMap.put("5004", "SXP");
		toLotteryTypeMap.put("5005", "CBF");
		toLotteryTypeMap.put("5006", "SF");
		
		//玩法转换
		playTypeMap.put(PlayType.ssq_dan.getValue(), "1");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "2");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "5");
		
		playTypeMap.put(PlayType.qlc_dan.getValue(), "1");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "2");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "5");
		
		playTypeMap.put(PlayType.dlt_dan.getValue(), "1");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "2");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "5");
		
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "1");
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "1");
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "1");
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "2");
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "3");
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "3");
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "4");
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "4");
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "4");
		
		playTypeMap.put(PlayType.p5_dan.getValue(), "1");
		playTypeMap.put(PlayType.p5_fu.getValue(), "2");
		
		playTypeMap.put(PlayType.qxc_dan.getValue(), "1");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "2");

		// 3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "1");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "1");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "1");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "2");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "3");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "3");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "4");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "4");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "4");
		
		
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "2");
		
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "2");
//		playTypeMap.put(PlayType.zc_rjc_dt.getValue(), "3");
		
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "1");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "2");
	    playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "1");
	    playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "2");
		
		
		playTypeMap.put(PlayType.jx11x5_sr2.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sr3.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sr4.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sr5.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sr6.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sr7.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sr8.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sq1.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sq2.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sq3.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sz2.getValue(), "1");
		playTypeMap.put(PlayType.jx11x5_sz3.getValue(), "1");
		
		playTypeMap.put(PlayType.jx11x5_mr2.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mr3.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mr4.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mr5.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mr6.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mr7.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mr8.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mq1.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mq2.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mq3.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mz2.getValue(), "2");
		playTypeMap.put(PlayType.jx11x5_mz3.getValue(), "2");
		
		playTypeMap.put(PlayType.jx11x5_dr2.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dr3.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dr4.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dr5.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dr6.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dr7.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dr8.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dz2.getValue(), "5");
		playTypeMap.put(PlayType.jx11x5_dz3.getValue(), "5");

		playTypeMap.put(PlayType.hubei11x5_sr2.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sr3.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sr4.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sr5.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sr6.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sr7.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sr8.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sq1.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sq2.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sq3.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sz2.getValue(), "1");
		playTypeMap.put(PlayType.hubei11x5_sz3.getValue(), "1");

		playTypeMap.put(PlayType.hubei11x5_mr2.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mr3.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mr4.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mr5.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mr6.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mr7.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mr8.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mq1.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mq2.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mq3.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mz2.getValue(), "2");
		playTypeMap.put(PlayType.hubei11x5_mz3.getValue(), "2");

		playTypeMap.put(PlayType.hubei11x5_dr2.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dr3.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dr4.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dr5.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dr6.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dr7.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dr8.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dz2.getValue(), "5");
		playTypeMap.put(PlayType.hubei11x5_dz3.getValue(), "5");

		playTypeMap.put(PlayType.sd11x5_sr2.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sr3.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sr4.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sr5.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sr6.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sr7.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sr8.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sq1.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sq2.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sq3.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sz2.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sz3.getValue(), "1");
		
		playTypeMap.put(PlayType.sd11x5_mr2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr3.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr4.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr5.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr6.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr7.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mr8.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mq1.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mq2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mq3.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mz2.getValue(), "2");
		playTypeMap.put(PlayType.sd11x5_mz3.getValue(), "2");
		
		playTypeMap.put(PlayType.sd11x5_dr2.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr3.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr4.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr5.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr6.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr7.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dr8.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dz2.getValue(), "5");
		playTypeMap.put(PlayType.sd11x5_dz3.getValue(), "5");

		playTypeMap.put(PlayType.sd11x5_sl3.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sl4.getValue(), "1");
		playTypeMap.put(PlayType.sd11x5_sl5.getValue(), "1");
		
		//广东11x5
		playTypeMap.put(PlayType.gd11x5_sr2.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sr3.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sr4.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sr5.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sr6.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sr7.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sr8.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sq1.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sq2.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sq3.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sz2.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sz3.getValue(), "1");
		
		playTypeMap.put(PlayType.gd11x5_mr2.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mr3.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mr4.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mr5.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mr6.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mr7.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mr8.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mq1.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mq2.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mq3.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mz2.getValue(), "2");
		playTypeMap.put(PlayType.gd11x5_mz3.getValue(), "2");
		
		playTypeMap.put(PlayType.gd11x5_dr2.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dr3.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dr4.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dr5.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dr6.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dr7.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dr8.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dz2.getValue(), "5");
		playTypeMap.put(PlayType.gd11x5_dz3.getValue(), "5");

		playTypeMap.put(PlayType.gd11x5_sl3.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sl4.getValue(), "1");
		playTypeMap.put(PlayType.gd11x5_sl5.getValue(), "1");
		

		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(), "SF");
		
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"RFSF");
		
		
		
	
		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"SFC");
		
		
    
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
		
		
		

		playTypeMap.put(PlayType.jclq_mix_1_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"LQHH");
	

		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"RSPF");
		
		
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
		
		
		playTypeMap.put(PlayType.jczq_mix_1_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"ZQHH");
	
		
		
		playMap.put(PlayType.d3_zhi_dan.getValue(), "1");
		playMap.put(PlayType.d3_z3_dan.getValue(), "2");
		playMap.put(PlayType.d3_z6_dan.getValue(), "3");
		playMap.put(PlayType.d3_zhi_fs.getValue(), "1");
		playMap.put(PlayType.d3_z3_fs.getValue(), "2");
		playMap.put(PlayType.d3_z6_fs.getValue(), "3");
		playMap.put(PlayType.d3_zhi_hz.getValue(), "1");
		playMap.put(PlayType.d3_z3_hz.getValue(), "2");
		playMap.put(PlayType.d3_z6_hz.getValue(), "3");
		
		playMap.put(PlayType.p3_zhi_dan.getValue(), "1");
		playMap.put(PlayType.p3_z3_dan.getValue(), "2");
		playMap.put(PlayType.p3_z6_dan.getValue(), "3");
		
		playMap.put(PlayType.p3_zhi_fs.getValue(), "1");
		playMap.put(PlayType.p3_z3_fs.getValue(), "2");
		playMap.put(PlayType.p3_z6_fs.getValue(), "3");
		
		playMap.put(PlayType.p3_zhi_dt.getValue(), "1");
		playMap.put(PlayType.p3_z3_dt.getValue(), "2");
		playMap.put(PlayType.p3_z6_dt.getValue(), "3");
		
		playMap.put(PlayType.p5_dan.getValue(), "1");
		playMap.put(PlayType.p5_fu.getValue(), "1");
		
		playMap.put(PlayType.qxc_dan.getValue(), "1");
		playMap.put(PlayType.qxc_fu.getValue(), "1");
		
		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(), "SF");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(), "SF");
		
		
		
		
		
		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(),"RFSF");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(),"RFSF");
		
		
		
	
		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(),"SFC");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(),"SFC");
		
		
    
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
		
		
		

		playTypeMap.put(PlayType.jclq_mix_1_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(),"LQHH");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(),"LQHH");
	

		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(),"RSPF");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(),"RSPF");
		
		
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
		
		
		playTypeMap.put(PlayType.jczq_mix_1_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(),"ZQHH");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(),"ZQHH");
	
		
		
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
		
			
			
			
			playMap.put(PlayType.dc_bf_1_1.getValue(),"1*1");
			playMap.put(PlayType.dc_bf_2_1.getValue(),"2*1");
			playMap.put(PlayType.dc_bf_2_3.getValue(),"2*3");
			playMap.put(PlayType.dc_bf_3_1.getValue(),"3*1");
			playMap.put(PlayType.dc_bf_3_4.getValue(),"3*4");
			playMap.put(PlayType.dc_bf_3_7.getValue(),"3*7");
			
			
			
			playMap.put(PlayType.dc_bqc_1_1.getValue(),"1*1");
			playMap.put(PlayType.dc_bqc_2_1.getValue(),"2*1");
			playMap.put(PlayType.dc_bqc_2_3.getValue(),"2*3");
			playMap.put(PlayType.dc_bqc_3_1.getValue(),"3*1");
			playMap.put(PlayType.dc_bqc_3_4.getValue(),"3*4");
			playMap.put(PlayType.dc_bqc_3_7.getValue(),"3*7");
			playMap.put(PlayType.dc_bqc_4_1.getValue(),"4*1");
			playMap.put(PlayType.dc_bqc_4_5.getValue(),"4*5");
			playMap.put(PlayType.dc_bqc_4_11.getValue(),"4*11");
			playMap.put(PlayType.dc_bqc_4_15.getValue(),"4*15");
			playMap.put(PlayType.dc_bqc_5_1.getValue(),"5*1");
			playMap.put(PlayType.dc_bqc_5_6.getValue(),"5*6");
			playMap.put(PlayType.dc_bqc_5_16.getValue(),"5*16");
			playMap.put(PlayType.dc_bqc_5_26.getValue(),"5*26");
			playMap.put(PlayType.dc_bqc_5_31.getValue(),"5*31");
			playMap.put(PlayType.dc_bqc_6_1.getValue(),"6*1");
			playMap.put(PlayType.dc_bqc_6_7.getValue(),"6*7");
			playMap.put(PlayType.dc_bqc_6_22.getValue(),"6*22");
			playMap.put(PlayType.dc_bqc_6_42.getValue(),"6*42");
			playMap.put(PlayType.dc_bqc_6_57.getValue(),"6*57");
		    playMap.put(PlayType.dc_bqc_6_63.getValue(),"6*63");
			
			
			
			
			
			
			playMap.put(PlayType.dc_spf_1_1.getValue(),"1*1");
			playMap.put(PlayType.dc_spf_2_1.getValue(),"2*1");
			playMap.put(PlayType.dc_spf_2_3.getValue(),"2*3");
			playMap.put(PlayType.dc_spf_3_1.getValue(),"3*1");
			playMap.put(PlayType.dc_spf_3_4.getValue(),"3*4");
			playMap.put(PlayType.dc_spf_3_7.getValue(),"3*7");
			playMap.put(PlayType.dc_spf_4_1.getValue(),"4*1");
			playMap.put(PlayType.dc_spf_4_5.getValue(),"4*5");
			playMap.put(PlayType.dc_spf_4_11.getValue(),"4*11");
			playMap.put(PlayType.dc_spf_4_15.getValue(),"4*15");
			playMap.put(PlayType.dc_spf_5_1.getValue(),"5*1");
			playMap.put(PlayType.dc_spf_5_6.getValue(),"5*6");
			playMap.put(PlayType.dc_spf_5_16.getValue(),"5*16");
			playMap.put(PlayType.dc_spf_5_26.getValue(),"5*26");
			playMap.put(PlayType.dc_spf_5_31.getValue(),"5*31");
			playMap.put(PlayType.dc_spf_6_1.getValue(),"6*1");
			playMap.put(PlayType.dc_spf_6_7.getValue(),"6*7");
			playMap.put(PlayType.dc_spf_6_22.getValue(),"6*22");
			playMap.put(PlayType.dc_spf_6_42.getValue(),"6*42");
			playMap.put(PlayType.dc_spf_6_57.getValue(),"6*57");
			playMap.put(PlayType.dc_spf_6_63.getValue(),"6*63");
			playMap.put(PlayType.dc_spf_7_1.getValue(),"7*1");
			playMap.put(PlayType.dc_spf_8_1.getValue(),"8*1");
			playMap.put(PlayType.dc_spf_9_1.getValue(),"9*1");
			playMap.put(PlayType.dc_spf_10_1.getValue(),"10*1");
			playMap.put(PlayType.dc_spf_11_1.getValue(),"11*1");
			playMap.put(PlayType.dc_spf_12_1.getValue(),"12*1");
			playMap.put(PlayType.dc_spf_13_1.getValue(),"13*1");
			playMap.put(PlayType.dc_spf_14_1.getValue(),"14*1");
			playMap.put(PlayType.dc_spf_15_1.getValue(),"15*1");
			
			
			
			
			
			
			playMap.put(PlayType.dc_sxds_1_1.getValue(),"1*1");
			playMap.put(PlayType.dc_sxds_2_1.getValue(),"2*1");
			playMap.put(PlayType.dc_sxds_2_3.getValue(),"2*3");
			playMap.put(PlayType.dc_sxds_3_1.getValue(),"3*1");
			playMap.put(PlayType.dc_sxds_3_4.getValue(),"3*4");
			playMap.put(PlayType.dc_sxds_3_7.getValue(),"3*7");
			playMap.put(PlayType.dc_sxds_4_1.getValue(),"4*1");
			playMap.put(PlayType.dc_sxds_4_5.getValue(),"4*5");
			playMap.put(PlayType.dc_sxds_4_11.getValue(),"4*11");
			playMap.put(PlayType.dc_sxds_4_15.getValue(),"4*15");
			playMap.put(PlayType.dc_sxds_5_1.getValue(),"5*1");
			playMap.put(PlayType.dc_sxds_5_6.getValue(),"5*6");
			playMap.put(PlayType.dc_sxds_5_16.getValue(),"5*16");
			playMap.put(PlayType.dc_sxds_5_26.getValue(),"5*26");
			playMap.put(PlayType.dc_sxds_5_31.getValue(),"5*31");
			playMap.put(PlayType.dc_sxds_6_1.getValue(),"6*1");
			playMap.put(PlayType.dc_sxds_6_7.getValue(),"6*7");
			playMap.put(PlayType.dc_sxds_6_22.getValue(),"6*22");
			playMap.put(PlayType.dc_sxds_6_42.getValue(),"6*42");
			playMap.put(PlayType.dc_sxds_6_57.getValue(),"6*57");
			playMap.put(PlayType.dc_sxds_6_63.getValue(),"6*63");
			
			
			
			
			
			
			
			playMap.put(PlayType.dc_zjq_1_1.getValue(),"1*1");
			playMap.put(PlayType.dc_zjq_2_1.getValue(),"2*1");
			playMap.put(PlayType.dc_zjq_2_3.getValue(),"2*3");
			playMap.put(PlayType.dc_zjq_3_1.getValue(),"3*1");
			playMap.put(PlayType.dc_zjq_3_4.getValue(),"3*4");
			playMap.put(PlayType.dc_zjq_3_7.getValue(),"3*7");
			playMap.put(PlayType.dc_zjq_4_1.getValue(),"4*1");
			playMap.put(PlayType.dc_zjq_4_5.getValue(),"4*5");
			playMap.put(PlayType.dc_zjq_4_11.getValue(),"4*11");
			playMap.put(PlayType.dc_zjq_4_15.getValue(),"4*15");
			playMap.put(PlayType.dc_zjq_5_1.getValue(),"5*1");
			playMap.put(PlayType.dc_zjq_5_6.getValue(),"5*6");
			playMap.put(PlayType.dc_zjq_5_16.getValue(),"5*16");
			playMap.put(PlayType.dc_zjq_5_26.getValue(),"5*26");
			playMap.put(PlayType.dc_zjq_5_31.getValue(),"5*31");
			playMap.put(PlayType.dc_zjq_6_1.getValue(),"6*1");
			playMap.put(PlayType.dc_zjq_6_7.getValue(),"6*7");
			playMap.put(PlayType.dc_zjq_6_22.getValue(),"6*22");
			playMap.put(PlayType.dc_zjq_6_42.getValue(),"6*42");
			playMap.put(PlayType.dc_zjq_6_57.getValue(),"6*57");
			playMap.put(PlayType.dc_zjq_6_63.getValue(),"6*63");
			
			
			
			
			playMap.put(PlayType.dc_sfgg_3_1.getValue(),"3*1");
			playMap.put(PlayType.dc_sfgg_4_1.getValue(),"4*1");
			playMap.put(PlayType.dc_sfgg_4_5.getValue(),"4*5");
			playMap.put(PlayType.dc_sfgg_5_1.getValue(),"5*1");
			playMap.put(PlayType.dc_sfgg_5_6.getValue(),"5*6");
			playMap.put(PlayType.dc_sfgg_5_16.getValue(),"5*16");
			playMap.put(PlayType.dc_sfgg_6_1.getValue(),"6*1");
			playMap.put(PlayType.dc_sfgg_6_7.getValue(),"6*7");
			playMap.put(PlayType.dc_sfgg_6_22.getValue(),"6*22");
			playMap.put(PlayType.dc_sfgg_6_42.getValue(),"6*42");
			playMap.put(PlayType.dc_sfgg_7_1.getValue(),"7*1");
			playMap.put(PlayType.dc_sfgg_8_1.getValue(),"8*1");
			playMap.put(PlayType.dc_sfgg_9_1.getValue(),"9*1");
			playMap.put(PlayType.dc_sfgg_10_1.getValue(),"10*1");
			playMap.put(PlayType.dc_sfgg_11_1.getValue(),"11*1");
			playMap.put(PlayType.dc_sfgg_12_1.getValue(),"12*1");
			playMap.put(PlayType.dc_sfgg_13_1.getValue(),"13*1");
			playMap.put(PlayType.dc_sfgg_14_1.getValue(),"14*1");
			playMap.put(PlayType.dc_sfgg_15_1.getValue(),"15*1");
			
			
			

			playMap.put(PlayType.jx11x5_sr2.getValue(), "2");
			playMap.put(PlayType.jx11x5_sr3.getValue(), "3");
			playMap.put(PlayType.jx11x5_sr4.getValue(), "4");
			playMap.put(PlayType.jx11x5_sr5.getValue(), "5");
			playMap.put(PlayType.jx11x5_sr6.getValue(), "6");
			playMap.put(PlayType.jx11x5_sr7.getValue(), "7");
			playMap.put(PlayType.jx11x5_sr8.getValue(), "8");
			playMap.put(PlayType.jx11x5_sq1.getValue(), "1");
			playMap.put(PlayType.jx11x5_sq2.getValue(), "9");
			playMap.put(PlayType.jx11x5_sq3.getValue(), "10");
			playMap.put(PlayType.jx11x5_sz2.getValue(), "11");
			playMap.put(PlayType.jx11x5_sz3.getValue(), "12");
			
			playMap.put(PlayType.jx11x5_mr2.getValue(), "2");
			playMap.put(PlayType.jx11x5_mr3.getValue(), "3");
			playMap.put(PlayType.jx11x5_mr4.getValue(), "4");
			playMap.put(PlayType.jx11x5_mr5.getValue(), "5");
			playMap.put(PlayType.jx11x5_mr6.getValue(), "6");
			playMap.put(PlayType.jx11x5_mr7.getValue(), "7");
			playMap.put(PlayType.jx11x5_mr8.getValue(), "8");
			playMap.put(PlayType.jx11x5_mq1.getValue(), "1");
			playMap.put(PlayType.jx11x5_mq2.getValue(), "9");
			playMap.put(PlayType.jx11x5_mq3.getValue(), "10");
			playMap.put(PlayType.jx11x5_mz2.getValue(), "11");
			playMap.put(PlayType.jx11x5_mz3.getValue(), "12");
			
			playMap.put(PlayType.jx11x5_dr2.getValue(), "2");
			playMap.put(PlayType.jx11x5_dr3.getValue(), "3");
			playMap.put(PlayType.jx11x5_dr4.getValue(), "4");
			playMap.put(PlayType.jx11x5_dr5.getValue(), "5");
			playMap.put(PlayType.jx11x5_dr6.getValue(), "6");
			playMap.put(PlayType.jx11x5_dr7.getValue(), "7");
			playMap.put(PlayType.jx11x5_dr8.getValue(), "8");
			playMap.put(PlayType.jx11x5_dz2.getValue(), "11");
			playMap.put(PlayType.jx11x5_dz3.getValue(), "12");

			playMap.put(PlayType.hubei11x5_sr2.getValue(), "2");
			playMap.put(PlayType.hubei11x5_sr3.getValue(), "3");
			playMap.put(PlayType.hubei11x5_sr4.getValue(), "4");
			playMap.put(PlayType.hubei11x5_sr5.getValue(), "5");
			playMap.put(PlayType.hubei11x5_sr6.getValue(), "6");
			playMap.put(PlayType.hubei11x5_sr7.getValue(), "7");
			playMap.put(PlayType.hubei11x5_sr8.getValue(), "8");
			playMap.put(PlayType.hubei11x5_sq1.getValue(), "1");
			playMap.put(PlayType.hubei11x5_sq2.getValue(), "9");
			playMap.put(PlayType.hubei11x5_sq3.getValue(), "10");
			playMap.put(PlayType.hubei11x5_sz2.getValue(), "11");
			playMap.put(PlayType.hubei11x5_sz3.getValue(), "12");

			playMap.put(PlayType.hubei11x5_mr2.getValue(), "2");
			playMap.put(PlayType.hubei11x5_mr3.getValue(), "3");
			playMap.put(PlayType.hubei11x5_mr4.getValue(), "4");
			playMap.put(PlayType.hubei11x5_mr5.getValue(), "5");
			playMap.put(PlayType.hubei11x5_mr6.getValue(), "6");
			playMap.put(PlayType.hubei11x5_mr7.getValue(), "7");
			playMap.put(PlayType.hubei11x5_mr8.getValue(), "8");
			playMap.put(PlayType.hubei11x5_mq1.getValue(), "1");
			playMap.put(PlayType.hubei11x5_mq2.getValue(), "9");
			playMap.put(PlayType.hubei11x5_mq3.getValue(), "10");
			playMap.put(PlayType.hubei11x5_mz2.getValue(), "11");
			playMap.put(PlayType.hubei11x5_mz3.getValue(), "12");

			playMap.put(PlayType.hubei11x5_dr2.getValue(), "2");
			playMap.put(PlayType.hubei11x5_dr3.getValue(), "3");
			playMap.put(PlayType.hubei11x5_dr4.getValue(), "4");
			playMap.put(PlayType.hubei11x5_dr5.getValue(), "5");
			playMap.put(PlayType.hubei11x5_dr6.getValue(), "6");
			playMap.put(PlayType.hubei11x5_dr7.getValue(), "7");
			playMap.put(PlayType.hubei11x5_dr8.getValue(), "8");
			playMap.put(PlayType.hubei11x5_dz2.getValue(), "11");
			playMap.put(PlayType.hubei11x5_dz3.getValue(), "12");
			
			playMap.put(PlayType.sd11x5_sr2.getValue(), "2");
			playMap.put(PlayType.sd11x5_sr3.getValue(), "3");
			playMap.put(PlayType.sd11x5_sr4.getValue(), "4");
			playMap.put(PlayType.sd11x5_sr5.getValue(), "5");
			playMap.put(PlayType.sd11x5_sr6.getValue(), "6");
			playMap.put(PlayType.sd11x5_sr7.getValue(), "7");
			playMap.put(PlayType.sd11x5_sr8.getValue(), "8");
			playMap.put(PlayType.sd11x5_sq1.getValue(), "1");
			playMap.put(PlayType.sd11x5_sq2.getValue(), "9");
			playMap.put(PlayType.sd11x5_sq3.getValue(), "10");
			playMap.put(PlayType.sd11x5_sz2.getValue(), "11");
			playMap.put(PlayType.sd11x5_sz3.getValue(), "12");
			
			playMap.put(PlayType.sd11x5_mr2.getValue(), "2");
			playMap.put(PlayType.sd11x5_mr3.getValue(), "3");
			playMap.put(PlayType.sd11x5_mr4.getValue(), "4");
			playMap.put(PlayType.sd11x5_mr5.getValue(), "5");
			playMap.put(PlayType.sd11x5_mr6.getValue(), "6");
			playMap.put(PlayType.sd11x5_mr7.getValue(), "7");
			playMap.put(PlayType.sd11x5_mr8.getValue(), "8");
			playMap.put(PlayType.sd11x5_mq1.getValue(), "1");
			playMap.put(PlayType.sd11x5_mq2.getValue(), "9");
			playMap.put(PlayType.sd11x5_mq3.getValue(), "10");
			playMap.put(PlayType.sd11x5_mz2.getValue(), "11");
			playMap.put(PlayType.sd11x5_mz3.getValue(), "12");
			
			playMap.put(PlayType.sd11x5_dr2.getValue(), "2");
			playMap.put(PlayType.sd11x5_dr3.getValue(), "3");
			playMap.put(PlayType.sd11x5_dr4.getValue(), "4");
			playMap.put(PlayType.sd11x5_dr5.getValue(), "5");
			playMap.put(PlayType.sd11x5_dr6.getValue(), "6");
			playMap.put(PlayType.sd11x5_dr7.getValue(), "7");
			playMap.put(PlayType.sd11x5_dr8.getValue(), "8");
			playMap.put(PlayType.sd11x5_dz2.getValue(), "11");
			playMap.put(PlayType.sd11x5_dz3.getValue(), "12");

			playMap.put(PlayType.sd11x5_sl3.getValue(), "13");
			playMap.put(PlayType.sd11x5_sl4.getValue(), "14");
			playMap.put(PlayType.sd11x5_sl5.getValue(), "15");
            //广东11x5
			playMap.put(PlayType.gd11x5_sr2.getValue(), "2");
			playMap.put(PlayType.gd11x5_sr3.getValue(), "3");
			playMap.put(PlayType.gd11x5_sr4.getValue(), "4");
			playMap.put(PlayType.gd11x5_sr5.getValue(), "5");
			playMap.put(PlayType.gd11x5_sr6.getValue(), "6");
			playMap.put(PlayType.gd11x5_sr7.getValue(), "7");
			playMap.put(PlayType.gd11x5_sr8.getValue(), "8");
			playMap.put(PlayType.gd11x5_sq1.getValue(), "1");
			playMap.put(PlayType.gd11x5_sq2.getValue(), "9");
			playMap.put(PlayType.gd11x5_sq3.getValue(), "10");
			playMap.put(PlayType.gd11x5_sz2.getValue(), "11");
			playMap.put(PlayType.gd11x5_sz3.getValue(), "12");
			
			playMap.put(PlayType.gd11x5_mr2.getValue(), "2");
			playMap.put(PlayType.gd11x5_mr3.getValue(), "3");
			playMap.put(PlayType.gd11x5_mr4.getValue(), "4");
			playMap.put(PlayType.gd11x5_mr5.getValue(), "5");
			playMap.put(PlayType.gd11x5_mr6.getValue(), "6");
			playMap.put(PlayType.gd11x5_mr7.getValue(), "7");
			playMap.put(PlayType.gd11x5_mr8.getValue(), "8");
			playMap.put(PlayType.gd11x5_mq1.getValue(), "1");
			playMap.put(PlayType.gd11x5_mq2.getValue(), "9");
			playMap.put(PlayType.gd11x5_mq3.getValue(), "10");
			playMap.put(PlayType.gd11x5_mz2.getValue(), "11");
			playMap.put(PlayType.gd11x5_mz3.getValue(), "12");
			
			playMap.put(PlayType.gd11x5_dr2.getValue(), "2");
			playMap.put(PlayType.gd11x5_dr3.getValue(), "3");
			playMap.put(PlayType.gd11x5_dr4.getValue(), "4");
			playMap.put(PlayType.gd11x5_dr5.getValue(), "5");
			playMap.put(PlayType.gd11x5_dr6.getValue(), "6");
			playMap.put(PlayType.gd11x5_dr7.getValue(), "7");
			playMap.put(PlayType.gd11x5_dr8.getValue(), "8");
			playMap.put(PlayType.gd11x5_dz2.getValue(), "11");
			playMap.put(PlayType.gd11x5_dz3.getValue(), "12");

			playMap.put(PlayType.gd11x5_sl3.getValue(), "13");
			playMap.put(PlayType.gd11x5_sl4.getValue(), "14");
			playMap.put(PlayType.gd11x5_sl5.getValue(), "15");
    }
			
			
			
    static {			
		
		IVenderTicketConverter ssq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
					strBuilder.append(con.replace("~", "#"));
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
				strBuilder.append(":1:").append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
		IVenderTicketConverter ssqds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
					strBuilder.append(con.replace("~", "#"));
					strBuilder.append(":1:").append(playTypeMap.get(ticket.getPlayType()));
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter dlt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
					strBuilder.append(con.replace("~", "#"));
					if(ticket.getAddition()==1){
						strBuilder.append(":2:");
					}else{
						strBuilder.append(":1:");
					}
					strBuilder.append(playTypeMap.get(ticket.getPlayType()));
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
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].replace("^","").split("\\|");
				
				if(cons[0].contains("#")){
					strBuilder.append(cons[0].replace("#", "$")).append("|");
				}else{
					strBuilder.append(cons[0]).append("$").append("|");
				}
				if(cons[1].contains("#")){
					strBuilder.append(cons[1].replace("#", "$"));
				}else{
					strBuilder.append("$").append(cons[1]);
				}
				
				if(ticket.getAddition()==1){
					strBuilder.append(":2:");
				}else{
					strBuilder.append(":1:");
				}
				strBuilder.append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
		
		
		IVenderTicketConverter ssqdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String cons = ticket.getContent().split("-")[1].replace("^","").replace("#", "$");
				strBuilder.append(cons);
				strBuilder.append(":1:").append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter p3zxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
					String []ss=con.split("\\,");
					int j=0;
					for(String s:ss){
						strBuilder.append(Integer.parseInt(s));
						if(j!=ss.length-1){
							strBuilder.append(",");
						}
						j++;
					}
					strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
		        return strBuilder.toString();
			}};
			
			IVenderTicketConverter p3zxfs = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					String []cons = ticket.getContent().split("-")[1].split("\\^");
					int i=0;
					for(String con:cons){
						String []ss=con.split("\\,");
						for(String s:ss){
							strBuilder.append(Integer.parseInt(s));
						}
						if(i!=cons.length-1){
							strBuilder.append(",");
						}
						i++;
					}
					strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
			        return strBuilder.toString();
				}};
			
				IVenderTicketConverter p3zuxfs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						String []cons = ticket.getContent().split("-")[1].split("\\^");
						int i=0;
						for(String con:cons){
							String []ss=con.split("\\,");
							for(String s:ss){
								strBuilder.append(Integer.parseInt(s));
								if(i!=ss.length-1){
									strBuilder.append(",");
								}
								i++;
							}
						}
						strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
				        return strBuilder.toString();
				}};
				
				IVenderTicketConverter qxcfs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						String []cons = ticket.getContent().split("-")[1].replace("^", "").split("\\|");
						int i=0;
						for(String con:cons){
							String []ss=con.split("\\,");
							for(String s:ss){
								strBuilder.append(Integer.parseInt(s));
							}
							if(i!=cons.length-1){
								strBuilder.append(",");
							}
							i++;
						}
						strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
				        return strBuilder.toString();
					}};
				
		IVenderTicketConverter rjcfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String cons = ticket.getContent().split("-")[1].replace("^","").replace("~", "#");
				strBuilder.append(cons);
				strBuilder.append(":1:").append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
	
		
		IVenderTicketConverter sdds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].split("\\^");
				int i=0;
				for(String con:cons){
					String []ss=con.split("\\,");
					int j=0;
					for(String s:ss){
						strBuilder.append(Integer.parseInt(s));
						if(j!=ss.length-1){
							strBuilder.append(",");
						}
						j++;
					}
					if(i!=cons.length-1){
						strBuilder.append(";");
					}
					i++;
				}
				strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter sdfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].replace("^", "").split("\\|");
				int i=0;
				for(String con:cons){
					String []ss=con.split("\\,");
					for(String s:ss){
						strBuilder.append(Integer.parseInt(s));
					}
					if(i!=cons.length-1){
						strBuilder.append(",");
					}
					i++;
				}
				strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter sdzxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				String []cons = ticket.getContent().split("-")[1].replace("^", "").split("\\,");
				int i=0;
				for(String con:cons){
					String []ss=con.split("\\,");
					for(String s:ss){
						strBuilder.append(Integer.parseInt(s));
					}
					if(i!=cons.length-1){
						strBuilder.append(",");
					}
					i++;
				}
				strBuilder.append(":").append(playMap.get(ticket.getPlayType())).append(":").append(playTypeMap.get(ticket.getPlayType()));
		        return strBuilder.toString();
		}};
		
		
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
					stringBuffer.append(con.split("\\=")[0].split("\\*")[0].substring(2)).append(">").append(toLotteryTypeMap.get(con.split("\\=")[0].split("\\*")[1]));
					stringBuffer.append("=");
					String playTypeStr=con.split("\\=")[0].split("\\*")[1];
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						if("3007".equals(playTypeStr)){
							stringBuffer.append(s.substring(0,1)).append(":").append(s.substring(1,2));
						}else if("3009".equals(playTypeStr)){
							stringBuffer.append(s.substring(0,1)).append("-").append(s.substring(1,2));
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
						stringBuffer.append(s.substring(0,1)).append(":").append(s.substring(1,2));
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
					stringBuffer.append(con.split("\\=")[0].substring(2)).append("=").append(con.split("\\=")[1].replace("1","3").replace("2","0"));
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
					stringBuffer.append(con.split("\\=")[0].split("\\*")[0].substring(2)).append(">").append(toLotteryTypeMap.get(con.split("\\=")[0].split("\\*")[1]));
					stringBuffer.append("=");
					String playTypeStr=con.split("\\=")[0].split("\\*")[1];
					String []ss=con.split("\\=")[1].split("\\/");
					int j=0;
					for(String s:ss){
						if("3003".equals(playTypeStr)){
							stringBuffer.append(s.substring(0,2));
						}else if("3004".equals(playTypeStr)){
							stringBuffer.append(s.replace("1","3").replace("2","0"));
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
						stringBuffer.append(s.substring(0,1)).append("-").append(s.substring(1,2));
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
		
		IVenderTicketConverter dcbf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
				String  []contents=con.split("\\,");
				strBuilder.append("CBF").append("|");
				int i=0;
				for(String s:contents){
					String []cons=s.split("\\=")[1].split("\\/");
					strBuilder.append(Integer.parseInt(s.split("\\=")[0])).append("=");
					int j=0;
					for(String co:cons){
						strBuilder.append(co.substring(0,1)).append(":").append(co.substring(1,2));
						if(j!=cons.length-1){
							strBuilder.append("/");
						}
						j++;
					}
						
					if(i!=contents.length-1){
						strBuilder.append(",");
					}
					i++;
					}
				strBuilder.append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		IVenderTicketConverter dcsxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
				String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
				String  []contents=con.split("\\,");
				strBuilder.append("SXP").append("|");
				int i=0;
				for(String s:contents){
					strBuilder.append(Integer.parseInt(s.split("\\=")[0])).append("=");
					strBuilder.append(s.split("\\=")[1].replace("1","0").replace("2", "1").replace("3", "2").replace("4", "3"));
					if(i!=contents.length-1){
						strBuilder.append(",");
					}
					i++;
					}
				strBuilder.append("|").append(playMap.get(playType));
		        return strBuilder.toString();
		}};
		
		IVenderTicketConverter dcjqs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuilder strBuilder=new StringBuilder();
				int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
	         	String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
	        	String  []contents=con.split("\\,");
		        strBuilder.append("JQS").append("|");
		        int j=0;
		        for(String s:contents){
			      strBuilder.append(Integer.parseInt(s.split("\\=")[0])).append("=");
				  strBuilder.append(s.split("\\=")[1]);
				  if(j!=contents.length-1){
					 strBuilder.append(",");
				  }
				  j++;
		        }
		       strBuilder.append("|").append(playMap.get(playType));
	           return strBuilder.toString();
			}};
			
			IVenderTicketConverter dcsf = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
		         	String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
		        	String  []contents=con.split("\\,");
			        strBuilder.append("SF").append("|");
			        int j=0;
			        for(String s:contents){
				      strBuilder.append(Integer.parseInt(s.split("\\=")[0])).append("=");
					  strBuilder.append(s.split("\\=")[1]);
					  if(j!=contents.length-1){
						 strBuilder.append(",");
					  }
					  j++;
			        }
			       strBuilder.append("|").append(playMap.get(playType));
		           return strBuilder.toString();
				}};
				
				IVenderTicketConverter dcspf = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
			         	String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
			        	String  []contents=con.split("\\,");
				        strBuilder.append("SPF").append("|");
				        int j=0;
				        for(String s:contents){
					      strBuilder.append(Integer.parseInt(s.split("\\=")[0])).append("=");
						  strBuilder.append(s.split("\\=")[1]);
						  if(j!=contents.length-1){
							 strBuilder.append(",");
						  }
						  j++;
				        }
				       strBuilder.append("|").append(playMap.get(playType));
			           return strBuilder.toString();
					}};
			IVenderTicketConverter dcbqc = new IVenderTicketConverter() {
				@Override
				public String convert(Ticket ticket) {
					StringBuilder strBuilder=new StringBuilder();
					int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
		         	String con = ticket.getContent().split("-")[1].replace("^", "").replace("(","=").replace(")","").replace(",","/").replace("|",",");;
		         	String  []contents=con.split("\\,");
		    		strBuilder.append("BQC").append("|");
		    		int i=0;
		    		for(String s:contents){
		    			String []cons=s.split("\\=")[1].split("\\/");
		    			strBuilder.append(Integer.parseInt(s.split("\\=")[0])).append("=");
		    			int j=0;
		    			for(String co:cons){
		    				strBuilder.append(co.substring(0,1)).append("-").append(co.substring(1,2));
		    				if(j!=cons.length-1){
		    					strBuilder.append("/");
		    				}
		    				j++;
		    			}
		    				
		    			if(i!=contents.length-1){
		    				strBuilder.append(",");
		    			}
		    			i++;
		    			}
		    		strBuilder.append("|").append(playMap.get(playType));
		           return strBuilder.toString();
				}};	
			
				IVenderTicketConverter jx11x5 = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
						String []cons = ticket.getContent().split("-")[1].split("\\^");
						int i=0;
						for(String con:cons){
							strBuilder.append(con).append(":").append(playMap.get(playType)).append(":").append(playTypeMap.get(ticket.getPlayType()));
							if(i!=cons.length-1){
								strBuilder.append(";");
							}
							i++;
						}
						return strBuilder.toString();
				}};
				
				IVenderTicketConverter qrzx = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
						String []cons = ticket.getContent().split("-")[1].split("\\^");
						int i=0;
						for(String con:cons){
							strBuilder.append(con.replace(",","|")).append(":").append(playMap.get(playType)).append(":").append(playTypeMap.get(ticket.getPlayType()));
							if(i!=cons.length-1){
								strBuilder.append(";");
							}
							i++;
						}
						return strBuilder.toString();
				}};
				IVenderTicketConverter qrfs = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
						String cons = ticket.getContent().split("-")[1].replace("^","").replace(";","|");
						strBuilder.append(cons).append(":").append(playMap.get(playType)).append(":").append(playTypeMap.get(ticket.getPlayType()));
						return strBuilder.toString();
				}};
				IVenderTicketConverter jx11x5dtrx = new IVenderTicketConverter() {
					@Override
					public String convert(Ticket ticket) {
						StringBuilder strBuilder=new StringBuilder();
						int playType=Integer.parseInt(ticket.getContent().split("-")[0]);
						String cons = ticket.getContent().split("-")[1].replace("^","").replace("#", "$");
						strBuilder.append(cons).append(":").append(playMap.get(playType)).append(":").append(playTypeMap.get(ticket.getPlayType()));
						return strBuilder.toString();
				}};
				
				
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssqds);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssqdt);
		
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, ssqds);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, ssq);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, ssqdt);
		
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, dlt);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, dlt);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, dltdt);
		
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, p3zxds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, p3zxds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, p3zxds);
		
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, qxcfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, p3zuxfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, p3zuxfs);
		
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, p3zuxfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, p3zuxfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, p3zuxfs);
		
		playTypeToAdvanceConverterMap.put(PlayType.p5_dan, p3zxds);
		playTypeToAdvanceConverterMap.put(PlayType.p5_fu, qxcfs);
		
		playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, p3zxds);
		playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, qxcfs);
		
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, sdds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, sdds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, sdds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, sdfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, sdzxfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, sdzxfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, sdzxfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, sdzxfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, sdzxfs);
		
		
		//足彩
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, ssqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, ssq);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, ssqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, rjcfs);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, ssqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, ssq);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, ssqds);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, ssq);
		
		
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


    	
    	
    	        playTypeToAdvanceConverterMap.put(PlayType.dc_bf_1_1,dcbf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bf_2_1,dcbf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bf_2_3,dcbf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_1,dcbf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_4,dcbf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bf_3_7,dcbf);
    				
    				
    				
    				
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_1_1,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_2_1,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_2_3,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_1,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_4,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_3_7,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_1,dcbqc);

    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_5,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_11,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_4_15,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_1,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_6,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_16,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_26,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_5_31,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_1,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_7,dcbqc);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_22,dcbqc);
    		    playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_42,dcbqc);
    		    playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_57,dcbqc);
    		    playTypeToAdvanceConverterMap.put(PlayType.dc_bqc_6_63,dcbqc);
    				
    		    				
    				
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_1_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_2_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_2_3,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_4,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_3_7,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_5,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_11,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_4_15,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_6,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_16,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_26,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_5_31,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_7,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_22,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_42,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_57,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_6_63,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_7_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_8_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_9_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_10_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_11_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_12_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_13_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_14_1,dcspf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_spf_15_1,dcspf);
    				
    				
    				
    				
    				
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_1_1,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_2_1,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_2_3,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_1,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_4,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_3_7,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_1,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_5,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_11,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_4_15,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_1,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_6,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_16,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_26,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_5_31,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_1,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_7,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_22,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_42,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_57,dcsxds);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sxds_6_63,dcsxds);
    				
    				
    				
    				
    				
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_1_1,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_2_1,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_2_3,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_1,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_4,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_3_7,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_1,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_5,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_11,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_4_15,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_1,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_6,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_16,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_26,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_5_31,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_1,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_7,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_22,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_42,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_57,dcjqs);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_zjq_6_63,dcjqs);
    				
    				
    				
    				
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_3_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_4_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_4_5,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_6,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_5_16,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_7,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_22,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_6_42,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_7_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_8_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_9_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_10_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_11_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_12_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_13_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_14_1,dcsf);
    			playTypeToAdvanceConverterMap.put(PlayType.dc_sfgg_15_1,dcsf);
    			
    			
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sq2,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sq3,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_sz3,jx11x5);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mq2,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mq3,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_mz3,jx11x5);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr3,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr4,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr5,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr6,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr7,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dr8,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dz2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.jx11x5_dz3,jx11x5dtrx);

    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sq2,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sq3,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_sz3,jx11x5);

    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mq2,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mq3,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_mz3,jx11x5);

    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr3,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr4,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr5,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr6,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr7,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dr8,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dz2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.hubei11x5_dz3,jx11x5dtrx);

    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq2,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq3,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz3,jx11x5);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq2,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq3,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz3,jx11x5);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr3,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr4,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr5,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr6,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr7,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr8,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz3,jx11x5dtrx);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl5,jx11x5);
    			//广东11x5
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq2,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq3,qrzx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz3,jx11x5);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr5,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr6,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr7,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr8,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq1,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq2,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq3,qrfs);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz2,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz3,jx11x5);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr3,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr4,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr5,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr6,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr7,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr8,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz2,jx11x5dtrx);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz3,jx11x5dtrx);
    			
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sl3,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sl4,jx11x5);
    			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sl5,jx11x5);
    			
    }

   
}
