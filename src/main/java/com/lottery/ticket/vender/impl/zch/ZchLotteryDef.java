package com.lottery.ticket.vender.impl.zch;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.ITicketContentConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class ZchLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<String, LotteryType> toLotteryTypeMap = new HashMap<String, LotteryType>();
	/** 彩期转换 */
	protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();

	protected static Map<LotteryType, IPhaseConverter> phaseReverseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
	/** 玩法转换 */
	protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
	/** 玩法编码转换 */
	public static Map<Integer, String> playCodeMap = new HashMap<Integer, String>();
	public static Map<String, Integer> playTypeMapJc = new HashMap<String, Integer>();
	/** 票内容转换 */
	protected static Map<PlayType, ITicketContentConverter> ticketContentConverterMap = new HashMap<PlayType, ITicketContentConverter>();
	protected static Map<PlayType, IVenderTicketConverter> playTypeToAdvanceConverterMap = new HashMap<PlayType, IVenderTicketConverter>();

	static {
		/**
		 * 彩期转换
		 * */
		// 默认的期号转换
		IPhaseConverter defaultPhaseConverter = new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase.substring(2);
			}
		};
		IPhaseConverter zcltPhaseConverter = new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		// 足彩彩期

		phaseConverterMap.put(LotteryType.CJDLT, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.PL3, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.PL5, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.QXC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_SFC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_RJC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_JQC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.ZC_BQC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.SSQ, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.CQSSC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SD_11X5, zcltPhaseConverter);
		phaseConverterMap.put(LotteryType.AHK3, zcltPhaseConverter);
		// 默认的期号转换
		IPhaseConverter defaultReversePhaseConverter = new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return phase;
			}
		};
		IPhaseConverter zchReversePhaseConverter = new IPhaseConverter() {
			@Override
			public String convert(String phase) {
				return "20" + phase;
			}
		};
		phaseReverseConverterMap.put(LotteryType.AHK3, defaultReversePhaseConverter);

		phaseReverseConverterMap.put(LotteryType.CQSSC, zchReversePhaseConverter);

		phaseReverseConverterMap.put(LotteryType.SD_11X5, defaultReversePhaseConverter);
		// 彩种转换
		lotteryTypeMap.put(LotteryType.SSQ, "001");
		lotteryTypeMap.put(LotteryType.F3D, "002");
		lotteryTypeMap.put(LotteryType.QLC, "004");
		lotteryTypeMap.put(LotteryType.PL3, "108");
		lotteryTypeMap.put(LotteryType.PL5, "109");
		lotteryTypeMap.put(LotteryType.QXC, "110");
		lotteryTypeMap.put(LotteryType.CJDLT, "113");
		lotteryTypeMap.put(LotteryType.CQSSC, "006");
		lotteryTypeMap.put(LotteryType.SD_11X5, "107");
		lotteryTypeMap.put(LotteryType.AHK3, "010");

		lotteryTypeMap.put(LotteryType.ZC_SFC, "300");
		lotteryTypeMap.put(LotteryType.ZC_RJC, "303");
		lotteryTypeMap.put(LotteryType.ZC_JQC, "302");
		lotteryTypeMap.put(LotteryType.ZC_BQC, "301");

		lotteryTypeMap.put(LotteryType.JCLQ_SF, "201");
		lotteryTypeMap.put(LotteryType.JCLQ_RFSF, "201");
		lotteryTypeMap.put(LotteryType.JCLQ_SFC, "201");
		lotteryTypeMap.put(LotteryType.JCLQ_DXF, "201");
		lotteryTypeMap.put(LotteryType.JCLQ_HHGG, "201");
		lotteryTypeMap.put(LotteryType.JCZQ_RQ_SPF, "200");
		lotteryTypeMap.put(LotteryType.JCZQ_BF, "200");
		lotteryTypeMap.put(LotteryType.JCZQ_JQS, "200");
		lotteryTypeMap.put(LotteryType.JCZQ_BQC, "200");
		lotteryTypeMap.put(LotteryType.JCZQ_SPF_WRQ, "200");
		lotteryTypeMap.put(LotteryType.JCZQ_HHGG, "200");

		toLotteryTypeMap.put("300", LotteryType.ZC_SFC);
		toLotteryTypeMap.put("303", LotteryType.ZC_RJC);
		toLotteryTypeMap.put("302", LotteryType.ZC_JQC);
		toLotteryTypeMap.put("301", LotteryType.ZC_BQC);
		toLotteryTypeMap.put("001", LotteryType.SSQ);
		toLotteryTypeMap.put("002", LotteryType.F3D);
		toLotteryTypeMap.put("004", LotteryType.QLC);
		toLotteryTypeMap.put("108", LotteryType.PL3);
		toLotteryTypeMap.put("109", LotteryType.PL5);
		toLotteryTypeMap.put("110", LotteryType.QXC);
		toLotteryTypeMap.put("113", LotteryType.CJDLT);
		toLotteryTypeMap.put("107", LotteryType.SD_11X5);
		toLotteryTypeMap.put("006", LotteryType.CQSSC);
		toLotteryTypeMap.put("010", LotteryType.AHK3);

		playTypeMapJc.put("jclq01", LotteryType.JCLQ_SF.getValue());
		playTypeMapJc.put("jclq02", LotteryType.JCLQ_RFSF.getValue());
		playTypeMapJc.put("jclq03", LotteryType.JCLQ_SFC.getValue());
		playTypeMapJc.put("jclq04", LotteryType.JCLQ_DXF.getValue());

		playTypeMapJc.put("jczq01", LotteryType.JCZQ_RQ_SPF.getValue());
		playTypeMapJc.put("jczq02", LotteryType.JCZQ_JQS.getValue());
		playTypeMapJc.put("jczq03", LotteryType.JCZQ_BQC.getValue());
		playTypeMapJc.put("jczq04", LotteryType.JCZQ_BF.getValue());
		playTypeMapJc.put("jczq05", LotteryType.JCZQ_SPF_WRQ.getValue());

		// 玩法转换
		// 双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "01");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "02");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "03");
		// 3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "04");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "04");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "04");
		// 七星彩
		playTypeMap.put(PlayType.qxc_dan.getValue(), "01");
		playTypeMap.put(PlayType.qxc_fu.getValue(), "02");
		// 七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "01");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "02");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "03");
		// 大乐透
		playTypeMap.put(PlayType.dlt_dan.getValue(), "01");
		playTypeMap.put(PlayType.dlt_fu.getValue(), "02");
		playTypeMap.put(PlayType.dlt_dantuo.getValue(), "03");
		// 排三
		playTypeMap.put(PlayType.p3_zhi_dan.getValue(), "01");// 直选
		playTypeMap.put(PlayType.p3_zhi_fs.getValue(), "02");// 直选复式
		playTypeMap.put(PlayType.p3_z3_dan.getValue(), "01");// 组选3
		playTypeMap.put(PlayType.p3_z6_dan.getValue(), "01");// 组选6
		playTypeMap.put(PlayType.p3_z3_fs.getValue(), "02");// 组三复试
		playTypeMap.put(PlayType.p3_z6_fs.getValue(), "02");// 组六复式
		playTypeMap.put(PlayType.p3_z3_dt.getValue(), "04");// 组三和值
		playTypeMap.put(PlayType.p3_z6_dt.getValue(), "04");// 组六和值
		playTypeMap.put(PlayType.p3_zhi_dt.getValue(), "04");// 直选和值
		// 排五
		playTypeMap.put(PlayType.p5_dan.getValue(), "01");
		playTypeMap.put(PlayType.p5_fu.getValue(), "02");
		// 足彩
		playTypeMap.put(PlayType.zc_sfc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_sfc_fu.getValue(), "02");
		playTypeMap.put(PlayType.zc_rjc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_rjc_fu.getValue(), "02");
		playTypeMap.put(PlayType.zc_rjc_dt.getValue(), "03");
		playTypeMap.put(PlayType.zc_jqc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_jqc_fu.getValue(), "02");
		playTypeMap.put(PlayType.zc_bqc_dan.getValue(), "01");
		playTypeMap.put(PlayType.zc_bqc_fu.getValue(), "02");

		// 重庆时时彩
		playTypeMap.put(PlayType.cqssc_dan_1d.getValue(), "01");
		playTypeMap.put(PlayType.cqssc_dan_2d.getValue(), "01");
		playTypeMap.put(PlayType.cqssc_dan_3d.getValue(), "01");
		playTypeMap.put(PlayType.cqssc_dan_5d.getValue(), "01");
		playTypeMap.put(PlayType.cqssc_dan_z3.getValue(), "01");
		playTypeMap.put(PlayType.cqssc_dan_z6.getValue(), "01");

		playTypeMap.put(PlayType.cqssc_fu_1d.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_fu_2d.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_fu_3d.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_fu_5d.getValue(), "02");
		// playTypeMap.put(PlayType.cqssc_other_2h.getValue(), "02");
		// playTypeMap.put(PlayType.cqssc_other_3h.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_other_2z.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_other_z3.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_other_z6.getValue(), "02");
		playTypeMap.put(PlayType.cqssc_other_dd.getValue(), "01");
		playTypeMap.put(PlayType.cqssc_other_5t.getValue(), "08");

		// 山东11x5
		playTypeMap.put(PlayType.sd11x5_sr2.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sr3.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sr4.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sr5.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sr6.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sr7.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sr8.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sq1.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sq2.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sq3.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sz2.getValue(), "01");
		playTypeMap.put(PlayType.sd11x5_sz3.getValue(), "01");

		playTypeMap.put(PlayType.sd11x5_mr2.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mr3.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mr4.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mr5.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mr6.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mr7.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mr8.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mq1.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mq2.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mq3.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mz2.getValue(), "02");
		playTypeMap.put(PlayType.sd11x5_mz3.getValue(), "02");

		playTypeMap.put(PlayType.sd11x5_dr2.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dr3.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dr4.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dr5.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dr6.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dr7.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dr8.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dz2.getValue(), "03");
		playTypeMap.put(PlayType.sd11x5_dz3.getValue(), "03");

		// 安徽快三
		playTypeMap.put(PlayType.anhui_ertong_dan.getValue(), "01");
		playTypeMap.put(PlayType.anhui_ertong_fu.getValue(), "07");
		// playTypeMap.put(PlayType.anhui_ertong_zuhe.getValue(), "03");
		playTypeMap.put(PlayType.anhui_erbutong_dan.getValue(), "01");
		// playTypeMap.put(PlayType.anhui_erbutong_zuhe.getValue(), "03");
		playTypeMap.put(PlayType.anhui_erbutong_dt.getValue(), "03");
		playTypeMap.put(PlayType.anhui_santong_dan.getValue(), "01");
		playTypeMap.put(PlayType.anhui_santong_tongxuan.getValue(), "08");
		playTypeMap.put(PlayType.anhui_sanbutong_dan.getValue(), "01");
		// playTypeMap.put(PlayType.anhui_sanbutong_zuhe.getValue(), "03");
		// playTypeMap.put(PlayType.anhui_sanbutong_dt.getValue(), "03");
		playTypeMap.put(PlayType.anhui_sanlian_tongxuan.getValue(), "08");
		playTypeMap.put(PlayType.anhui_hezhi.getValue(), "04");

		// 竞彩
		playTypeMap.put(PlayType.jczq_spf_1_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_2_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_3_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_4_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_7_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_1.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_3_3.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_3_4.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_4_4.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_4_5.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_4_6.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_4_11.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_5.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_6.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_10.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_16.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_20.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_5_26.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_6.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_7.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_15.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_20.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_22.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_35.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_42.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_50.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_6_57.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_7_7.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_7_8.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_7_21.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_7_35.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_7_120.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_8.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_9.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_28.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_56.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_70.getValue(), "01");
		playTypeMap.put(PlayType.jczq_spf_8_247.getValue(), "01");

		playTypeMap.put(PlayType.jczq_bf_1_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_2_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_3_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_4_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_5_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_6_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_7_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_8_1.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_3_3.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_3_4.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_4_4.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_4_5.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_4_6.getValue(), "04");
		playTypeMap.put(PlayType.jczq_bf_4_11.getValue(), "04");

		playTypeMap.put(PlayType.jczq_jqs_1_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_2_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_3_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_4_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_7_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_8_1.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_3_3.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_3_4.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_4_4.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_4_5.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_4_6.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_4_11.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_5.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_6.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_10.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_16.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_20.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_5_26.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_6.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_7.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_15.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_20.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_22.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_35.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_42.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_50.getValue(), "02");
		playTypeMap.put(PlayType.jczq_jqs_6_57.getValue(), "02");

		playTypeMap.put(PlayType.jczq_bqc_1_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_2_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_3_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_4_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_5_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_6_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_7_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_8_1.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_3_3.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_3_4.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_4_4.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_4_5.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_4_6.getValue(), "03");
		playTypeMap.put(PlayType.jczq_bqc_4_11.getValue(), "03");

		playTypeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "05");
		playTypeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "05");

		playTypeMap.put(PlayType.jczq_mix_2_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_3_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_4_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_7_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_1.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_3_3.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_3_4.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_4_4.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_4_5.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_4_6.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_4_11.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_5.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_6.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_10.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_16.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_20.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_5_26.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_6.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_7.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_15.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_20.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_22.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_35.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_42.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_50.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_6_57.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_7_7.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_7_8.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_7_21.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_7_35.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_7_120.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_8.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_9.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_28.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_56.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_70.getValue(), "10");
		playTypeMap.put(PlayType.jczq_mix_8_247.getValue(), "10");

		playTypeMap.put(PlayType.jclq_sf_1_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_2_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_3_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_4_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_1.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_3_3.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_3_4.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_4_4.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_4_5.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_4_6.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_4_11.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_5.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_6.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_10.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_16.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_20.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_5_26.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_6.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_7.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_15.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_20.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_22.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_35.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_42.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_50.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_6_57.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_7.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_8.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_21.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_35.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_7_120.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_8.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_9.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_28.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_56.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_70.getValue(), "01");
		playTypeMap.put(PlayType.jclq_sf_8_247.getValue(), "01");

		playTypeMap.put(PlayType.jclq_rfsf_1_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_2_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_3_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_4_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_7_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_1.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_3_3.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_3_4.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_4_4.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_4_5.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_4_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_4_11.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_5.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_10.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_16.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_20.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_5_26.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_6.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_7.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_15.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_20.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_22.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_35.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_42.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_50.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_6_57.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_7_7.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_7_8.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_7_21.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_7_35.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_7_120.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_8.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_9.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_28.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_56.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_70.getValue(), "02");
		playTypeMap.put(PlayType.jclq_rfsf_8_247.getValue(), "02");

		playTypeMap.put(PlayType.jclq_sfc_1_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_2_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_3_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_4_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_5_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_6_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_7_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_8_1.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_3_3.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_3_4.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_4_4.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_4_5.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_4_6.getValue(), "03");
		playTypeMap.put(PlayType.jclq_sfc_4_11.getValue(), "03");

		playTypeMap.put(PlayType.jclq_dxf_1_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_2_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_3_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_4_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_7_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_1.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_3_3.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_3_4.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_4_4.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_4_5.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_4_6.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_4_11.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_5.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_6.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_10.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_16.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_20.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_5_26.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_6.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_7.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_15.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_20.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_22.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_35.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_42.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_50.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_6_57.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_7_7.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_7_8.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_7_21.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_7_35.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_7_120.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_8.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_9.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_28.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_56.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_70.getValue(), "04");
		playTypeMap.put(PlayType.jclq_dxf_8_247.getValue(), "04");

		playTypeMap.put(PlayType.jclq_mix_2_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_3_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_4_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_7_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_1.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_3_3.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_3_4.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_4_4.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_4_5.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_4_6.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_4_11.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_5.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_6.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_10.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_16.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_20.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_5_26.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_6.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_7.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_15.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_20.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_22.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_35.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_42.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_50.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_6_57.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_7_7.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_7_8.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_7_21.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_7_35.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_7_120.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_8.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_9.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_28.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_56.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_70.getValue(), "10");
		playTypeMap.put(PlayType.jclq_mix_8_247.getValue(), "10");

		// 玩法编码转换
		// 双色球
		playCodeMap.put(PlayType.ssq_dan.getValue(), "01");
		playCodeMap.put(PlayType.ssq_fs.getValue(), "01");
		playCodeMap.put(PlayType.ssq_dt.getValue(), "01");
		// 3D
		playCodeMap.put(PlayType.d3_zhi_dan.getValue(), "01");
		playCodeMap.put(PlayType.d3_zhi_fs.getValue(), "01");
		playCodeMap.put(PlayType.d3_zhi_hz.getValue(), "01");
		playCodeMap.put(PlayType.d3_z3_dan.getValue(), "02");
		playCodeMap.put(PlayType.d3_z3_fs.getValue(), "02");
		playCodeMap.put(PlayType.d3_z3_hz.getValue(), "02");
		playCodeMap.put(PlayType.d3_z6_dan.getValue(), "03");
		playCodeMap.put(PlayType.d3_z6_fs.getValue(), "03");
		playCodeMap.put(PlayType.d3_z6_hz.getValue(), "03");
		// 七星彩
		playCodeMap.put(PlayType.qxc_dan.getValue(), "01");
		playCodeMap.put(PlayType.qxc_fu.getValue(), "01");
		// 七乐彩
		playCodeMap.put(PlayType.qlc_dan.getValue(), "01");
		playCodeMap.put(PlayType.qlc_fs.getValue(), "01");
		playCodeMap.put(PlayType.qlc_dt.getValue(), "01");
		// 大乐透
		playCodeMap.put(PlayType.dlt_dan.getValue(), "01");
		playCodeMap.put(PlayType.dlt_fu.getValue(), "01");
		playCodeMap.put(PlayType.dlt_dantuo.getValue(), "01");
		// 排三
		playCodeMap.put(PlayType.p3_zhi_dan.getValue(), "01");// 直选
		playCodeMap.put(PlayType.p3_zhi_fs.getValue(), "01");// 直选复式
		playCodeMap.put(PlayType.p3_zhi_dt.getValue(), "01");// 直选和值
		playCodeMap.put(PlayType.p3_z3_dan.getValue(), "02");// 组选3
		playCodeMap.put(PlayType.p3_z3_fs.getValue(), "03");// 组三复试
		playCodeMap.put(PlayType.p3_z3_dt.getValue(), "02");// 组三和值
		playCodeMap.put(PlayType.p3_z6_dan.getValue(), "02");// 组选6
		playCodeMap.put(PlayType.p3_z6_fs.getValue(), "04");// 组六复式
		playCodeMap.put(PlayType.p3_z6_dt.getValue(), "02");// 组六和值
		// 排五
		playCodeMap.put(PlayType.p5_dan.getValue(), "01");
		playCodeMap.put(PlayType.p5_fu.getValue(), "01");

		// 重庆时时彩
		playCodeMap.put(PlayType.cqssc_dan_1d.getValue(), "01");
		playCodeMap.put(PlayType.cqssc_dan_2d.getValue(), "02");
		playCodeMap.put(PlayType.cqssc_dan_3d.getValue(), "03");
		playCodeMap.put(PlayType.cqssc_dan_5d.getValue(), "05");
		playCodeMap.put(PlayType.cqssc_dan_z3.getValue(), "07");
		playCodeMap.put(PlayType.cqssc_dan_z6.getValue(), "08");

		playCodeMap.put(PlayType.cqssc_fu_1d.getValue(), "01");
		playCodeMap.put(PlayType.cqssc_fu_2d.getValue(), "02");
		playCodeMap.put(PlayType.cqssc_fu_3d.getValue(), "03");
		playCodeMap.put(PlayType.cqssc_fu_5d.getValue(), "05");

		// playTypeMap.put(PlayType.cqssc_other_2h.getValue(), "02");
		// playTypeMap.put(PlayType.cqssc_other_3h.getValue(), "02");
		playCodeMap.put(PlayType.cqssc_other_2z.getValue(), "06");
		playCodeMap.put(PlayType.cqssc_other_z3.getValue(), "07");
		playCodeMap.put(PlayType.cqssc_other_z6.getValue(), "08");
		playCodeMap.put(PlayType.cqssc_other_dd.getValue(), "30");
		playCodeMap.put(PlayType.cqssc_other_5t.getValue(), "05");

		// 山东11x5
		playCodeMap.put(PlayType.sd11x5_sr2.getValue(), "02");
		playCodeMap.put(PlayType.sd11x5_sr3.getValue(), "03");
		playCodeMap.put(PlayType.sd11x5_sr4.getValue(), "04");
		playCodeMap.put(PlayType.sd11x5_sr5.getValue(), "05");
		playCodeMap.put(PlayType.sd11x5_sr6.getValue(), "06");
		playCodeMap.put(PlayType.sd11x5_sr7.getValue(), "07");
		playCodeMap.put(PlayType.sd11x5_sr8.getValue(), "08");
		playCodeMap.put(PlayType.sd11x5_sq1.getValue(), "01");
		playCodeMap.put(PlayType.sd11x5_sq2.getValue(), "10");
		playCodeMap.put(PlayType.sd11x5_sq3.getValue(), "11");
		playCodeMap.put(PlayType.sd11x5_sz2.getValue(), "12");
		playCodeMap.put(PlayType.sd11x5_sz3.getValue(), "13");

		playCodeMap.put(PlayType.sd11x5_mr2.getValue(), "02");
		playCodeMap.put(PlayType.sd11x5_mr3.getValue(), "03");
		playCodeMap.put(PlayType.sd11x5_mr4.getValue(), "04");
		playCodeMap.put(PlayType.sd11x5_mr5.getValue(), "05");
		playCodeMap.put(PlayType.sd11x5_mr6.getValue(), "06");
		playCodeMap.put(PlayType.sd11x5_mr7.getValue(), "07");
		playCodeMap.put(PlayType.sd11x5_mr8.getValue(), "08");
		playCodeMap.put(PlayType.sd11x5_mq1.getValue(), "01");
		playCodeMap.put(PlayType.sd11x5_mq2.getValue(), "10");
		playCodeMap.put(PlayType.sd11x5_mq3.getValue(), "11");
		playCodeMap.put(PlayType.sd11x5_mz2.getValue(), "12");
		playCodeMap.put(PlayType.sd11x5_mz3.getValue(), "13");

		playCodeMap.put(PlayType.sd11x5_dr2.getValue(), "02");
		playCodeMap.put(PlayType.sd11x5_dr3.getValue(), "03");
		playCodeMap.put(PlayType.sd11x5_dr4.getValue(), "04");
		playCodeMap.put(PlayType.sd11x5_dr5.getValue(), "05");
		playCodeMap.put(PlayType.sd11x5_dr6.getValue(), "06");
		playCodeMap.put(PlayType.sd11x5_dr7.getValue(), "07");
		playCodeMap.put(PlayType.sd11x5_dr8.getValue(), "08");
		playCodeMap.put(PlayType.sd11x5_dz2.getValue(), "12");
		playCodeMap.put(PlayType.sd11x5_dz3.getValue(), "13");

		// 安徽快三
		playCodeMap.put(PlayType.anhui_ertong_dan.getValue(), "02");
		playCodeMap.put(PlayType.anhui_ertong_fu.getValue(), "02");
		// playCodeMap.put(PlayType.anhui_ertong_zuhe.getValue(), "03");
		playCodeMap.put(PlayType.anhui_erbutong_dan.getValue(), "04");
		// playCodeMap.put(PlayType.anhui_erbutong_zuhe.getValue(), "03");
		playCodeMap.put(PlayType.anhui_erbutong_dt.getValue(), "04");
		playCodeMap.put(PlayType.anhui_santong_dan.getValue(), "03");
		playCodeMap.put(PlayType.anhui_santong_tongxuan.getValue(), "03");
		playCodeMap.put(PlayType.anhui_sanbutong_dan.getValue(), "05");
		// playCodeMap.put(PlayType.anhui_sanbutong_zuhe.getValue(), "03");
		// playCodeMap.put(PlayType.anhui_sanbutong_dt.getValue(), "03");
		playCodeMap.put(PlayType.anhui_sanlian_tongxuan.getValue(), "06");
		playCodeMap.put(PlayType.anhui_hezhi.getValue(), "01");
		// 足彩
		playCodeMap.put(PlayType.zc_sfc_dan.getValue(), "01");
		playCodeMap.put(PlayType.zc_sfc_fu.getValue(), "01");
		playCodeMap.put(PlayType.zc_rjc_dan.getValue(), "01");
		playCodeMap.put(PlayType.zc_rjc_fu.getValue(), "01");
		playCodeMap.put(PlayType.zc_rjc_dt.getValue(), "01");
		playCodeMap.put(PlayType.zc_sfc_dan.getValue(), "01");
		playCodeMap.put(PlayType.zc_jqc_dan.getValue(), "01");
		playCodeMap.put(PlayType.zc_jqc_fu.getValue(), "01");
		playCodeMap.put(PlayType.zc_bqc_dan.getValue(), "01");
		playCodeMap.put(PlayType.zc_bqc_fu.getValue(), "01");
		// 竞彩
		playCodeMap.put(PlayType.jczq_spf_1_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_2_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_3_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_4_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_7_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_1.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_3_3.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_3_4.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_4_4.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_4_5.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_4_6.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_4_11.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_5.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_6.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_10.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_16.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_20.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_5_26.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_6.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_7.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_15.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_20.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_22.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_35.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_42.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_50.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_6_57.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_7_7.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_7_8.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_7_21.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_7_35.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_7_120.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_8.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_9.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_28.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_56.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_70.getValue(), "01");
		playCodeMap.put(PlayType.jczq_spf_8_247.getValue(), "01");

		playCodeMap.put(PlayType.jczq_bf_1_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_2_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_3_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_4_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_5_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_6_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_7_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_8_1.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_3_3.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_3_4.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_4_4.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_4_5.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_4_6.getValue(), "04");
		playCodeMap.put(PlayType.jczq_bf_4_11.getValue(), "04");

		playCodeMap.put(PlayType.jczq_jqs_1_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_2_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_3_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_4_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_7_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_8_1.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_3_3.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_3_4.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_4_4.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_4_5.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_4_6.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_4_11.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_5.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_6.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_10.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_16.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_20.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_5_26.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_6.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_7.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_15.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_20.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_22.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_35.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_42.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_50.getValue(), "02");
		playCodeMap.put(PlayType.jczq_jqs_6_57.getValue(), "02");

		playCodeMap.put(PlayType.jczq_bqc_1_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_2_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_3_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_4_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_5_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_6_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_7_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_8_1.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_3_3.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_3_4.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_4_4.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_4_5.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_4_6.getValue(), "03");
		playCodeMap.put(PlayType.jczq_bqc_4_11.getValue(), "03");

		playCodeMap.put(PlayType.jczq_spfwrq_1_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_2_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_3_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_4_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_7_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_1.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_3_3.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_3_4.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_4_4.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_4_5.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_4_6.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_4_11.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_5.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_6.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_10.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_16.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_20.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_5_26.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_6.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_7.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_15.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_20.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_22.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_35.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_42.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_50.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_6_57.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_7_7.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_7_8.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_7_21.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_7_35.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_7_120.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_8.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_9.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_28.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_56.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_70.getValue(), "05");
		playCodeMap.put(PlayType.jczq_spfwrq_8_247.getValue(), "05");
		// 篮球

		playCodeMap.put(PlayType.jclq_sf_1_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_2_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_3_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_4_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_7_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_1.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_3_3.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_3_4.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_4_4.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_4_5.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_4_6.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_4_11.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_5.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_6.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_10.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_16.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_20.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_5_26.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_6.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_7.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_15.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_20.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_22.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_35.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_42.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_50.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_6_57.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_7_7.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_7_8.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_7_21.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_7_35.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_7_120.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_8.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_9.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_28.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_56.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_70.getValue(), "01");
		playCodeMap.put(PlayType.jclq_sf_8_247.getValue(), "01");

		playCodeMap.put(PlayType.jclq_rfsf_1_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_2_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_3_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_4_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_7_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_1.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_3_3.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_3_4.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_4_4.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_4_5.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_4_6.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_4_11.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_5.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_6.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_10.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_16.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_20.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_5_26.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_6.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_7.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_15.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_20.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_22.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_35.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_42.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_50.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_6_57.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_7_7.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_7_8.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_7_21.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_7_35.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_7_120.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_8.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_9.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_28.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_56.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_70.getValue(), "02");
		playCodeMap.put(PlayType.jclq_rfsf_8_247.getValue(), "02");

		playCodeMap.put(PlayType.jclq_sfc_1_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_2_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_3_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_4_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_5_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_6_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_7_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_8_1.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_3_3.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_3_4.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_4_4.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_4_5.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_4_6.getValue(), "03");
		playCodeMap.put(PlayType.jclq_sfc_4_11.getValue(), "03");

		playCodeMap.put(PlayType.jclq_dxf_1_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_2_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_3_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_4_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_7_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_1.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_3_3.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_3_4.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_4_4.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_4_5.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_4_6.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_4_11.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_5.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_6.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_10.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_16.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_20.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_5_26.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_6.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_7.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_15.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_20.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_22.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_35.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_42.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_50.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_6_57.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_7_7.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_7_8.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_7_21.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_7_35.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_7_120.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_8.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_9.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_28.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_56.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_70.getValue(), "04");
		playCodeMap.put(PlayType.jclq_dxf_8_247.getValue(), "04");

		playCodeMap.put(PlayType.jczq_mix_2_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_3_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_4_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_7_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_1.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_3_3.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_3_4.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_4_4.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_4_5.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_4_6.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_4_11.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_5.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_6.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_10.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_16.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_20.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_5_26.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_6.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_7.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_15.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_20.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_22.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_35.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_42.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_50.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_6_57.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_7_7.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_7_8.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_7_21.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_7_35.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_7_120.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_8.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_9.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_28.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_56.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_70.getValue(), "10");
		playCodeMap.put(PlayType.jczq_mix_8_247.getValue(), "10");

		playCodeMap.put(PlayType.jclq_mix_2_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_3_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_4_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_7_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_1.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_3_3.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_3_4.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_4_4.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_4_5.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_4_6.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_4_11.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_5.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_6.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_10.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_16.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_20.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_5_26.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_6.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_7.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_15.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_20.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_22.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_35.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_42.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_50.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_6_57.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_7_7.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_7_8.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_7_21.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_7_35.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_7_120.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_8.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_9.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_28.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_56.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_70.getValue(), "10");
		playCodeMap.put(PlayType.jclq_mix_8_247.getValue(), "10");
		// 七乐彩 单式
		IVenderTicketConverter qlc_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", ";");
				return contentStr.substring(0, contentStr.length() - 1);
			}
		};
		// 七乐彩 复式 胆拖
		IVenderTicketConverter qlc_fd = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("#", "@").replace("^", "");
				return contentStr;
			}
		};

		// 双色球 大乐透 单式
		IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("|", "#").replace("^", ";");
				return contentStr.substring(0, contentStr.length() - 1);
			}
		};
		// 双色球 大乐透 复式 胆拖
		IVenderTicketConverter ssq_fd = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("#", "@").replace("|", "#").replace("^", "");
				return contentStr;
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
						if (i != content.length - 1) {
							ticketContent.append(",");
						}
					}
					if (j != oneCode.length - 1) {
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};

		// 3D 排列三 排五 七星彩 直选复式
		IVenderTicketConverter d3_zxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] oneBet = contentStr.split("\\|");
				for (int j = 0; j < oneBet.length; j++) {
					String[] content = oneBet[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if (j != oneBet.length - 1) {
						ticketContent.append(",");
					}
				}
				return ticketContent.toString();
			}
		};

		// 排五 复式
		IVenderTicketConverter pl5 = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] oneBet = contentStr.split("\\|");
				for (int j = 0; j < oneBet.length; j++) {
					String[] content = oneBet[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if (j != oneBet.length - 1) {
						ticketContent.append(",");
					}
				}
				return ticketContent.toString();
			}
		};
		// 七星彩复式
		IVenderTicketConverter qxc_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] oneBet = contentStr.split("\\|");
				for (int j = 0; j < oneBet.length; j++) {
					String[] content = oneBet[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append(Integer.parseInt(content[i]));
					}
					if (j != oneBet.length - 1) {
						ticketContent.append(",");
					}
				}
				return ticketContent.toString();
			}
		};

		// 3D 排列三 组三组六复式
		IVenderTicketConverter d3_slfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] content = contentStr.split(",");
				StringBuffer ticketContent = new StringBuffer();
				for (int i = 0; i < content.length; i++) {
					ticketContent.append(Integer.parseInt(content[i]));
					if (i != content.length - 1) {
						ticketContent.append(",");
					}
				}
				return ticketContent.toString();
			}
		};

		// 3d 排三 和值
		IVenderTicketConverter d3_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String ret = ticket.getContent().split("-")[1].replace("^", "");
				return Integer.parseInt(ret) + "";

			}
		};

		// 传统足彩
		IVenderTicketConverter zc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String content = oneCode[j].replace(",", "*").replace("~", "4");
					ticketContent.append(content);
					if (j != oneCode.length - 1) {
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();

			}
		};
		// 竞彩胜平负
		IVenderTicketConverter jcspf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String chuan = ticket.getContent().split("-")[0].substring(5, 9);
				String strs[] = ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer = new StringBuffer();
				int i = 0;
				for (String str1 : strs) {
					String[] number = str1.split("\\(");
					strBuffer.append(number[0]).append(":");
					strBuffer.append(number[1].split("\\)")[0]);
					if (i != strs.length - 1) {
						strBuffer.append(";");
					}
					i++;
				}
				return strBuffer.append("|").append(chuan.substring(0, 1) + "*" + Integer.parseInt(chuan.substring(1))).toString();
			}
		};
		// 总进球数
		IVenderTicketConverter jczjq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String chuan = ticket.getContent().split("-")[0].substring(5, 9);
				String strs[] = ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer = new StringBuffer();
				int i = 0;
				for (String str1 : strs) {
					int j = 0;
					String[] number = str1.split("\\(");
					strBuffer.append(number[0]).append(":");
					String[] numbers = number[1].split("\\)")[0].split("\\,");
					for (String num : numbers) {
						strBuffer.append(Integer.parseInt(num));
						if (j != numbers.length - 1) {
							strBuffer.append(",");
						}
						j++;
					}
					if (i != strs.length - 1) {
						strBuffer.append(";");
					}
					i++;
				}
				return strBuffer.append("|").append(chuan.substring(0, 1) + "*" + Integer.parseInt(chuan.substring(1))).toString();
			}
		};
		// 竞彩篮球
		IVenderTicketConverter jclq = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String chuan = ticket.getContent().split("-")[0].substring(5, 9);
				String strs[] = ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer = new StringBuffer();
				int i = 0;
				for (String str1 : strs) {
					int j = 0;
					String[] number = str1.split("\\(");
					strBuffer.append(number[0]).append(":");
					String[] numbers = number[1].split("\\)")[0].split("\\,");
					for (String num : numbers) {
						strBuffer.append(num);
						if (j != numbers.length - 1) {
							strBuffer.append(",");
						}
						j++;
					}
					if (i != strs.length - 1) {
						strBuffer.append(";");
					}
					i++;
				}
				return strBuffer.append("|").append(chuan.substring(0, 1) + "*" + Integer.parseInt(chuan.substring(1))).toString();
			}
		};

		IVenderTicketConverter jclqsf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String chuan = ticket.getContent().split("-")[0].substring(5, 9);
				String strs[] = ticket.getContent().split("-")[1].split("\\|");
				StringBuilder strBuffer = new StringBuilder();
				int i = 0;
				for (String str1 : strs) {
					int j = 0;
					String[] number = str1.split("\\(");
					strBuffer.append(number[0]).append(":");
					String[] numbers = number[1].split("\\)")[0].split("\\,");
					for (String num : numbers) {
						if ("3".equals(num)) {
							num = "1";
						} else if ("0".equals(num)) {
							num = "2";
						}
						strBuffer.append(num);
						if (j != numbers.length - 1) {
							strBuffer.append(",");
						}
						j++;
					}
					if (i != strs.length - 1) {
						strBuffer.append(";");
					}
					i++;
				}
				return strBuffer.append("|").append(chuan.substring(0, 1) + "*" + Integer.parseInt(chuan.substring(1))).toString();
			}
		};

		IVenderTicketConverter jclqmix = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String chuan = ticket.getContent().split("-")[0].substring(5, 9);
				String strs[] = ticket.getContent().split("-")[1].split("\\|");
				StringBuffer strBuffer = new StringBuffer();
				int i = 0;
				for (String str1 : strs) {
					int j = 0;
					String[] number = str1.replace("*", ":").split("\\(");
					strBuffer.append(number[0].split("\\:")[0]).append(":").append(playTypeMap.get(Integer.parseInt(number[0].split("\\:")[1] + "11001"))).append(":");
					String[] numbers = number[1].split("\\)")[0].split("\\,");
					for (String num : numbers) {
						if (Integer.parseInt(number[0].split("\\:")[1]) == 3007 || Integer.parseInt(number[0].split("\\:")[1]) == 3009 || Integer.parseInt(number[0].split("\\:")[1]) == 3003) {
							strBuffer.append(num);
						} else if (Integer.parseInt(number[0].split("\\:")[1]) == 3002 || Integer.parseInt(number[0].split("\\:")[1]) == 3001) {
							if ("3".equals(num)) {
								num = "1";
							} else if ("0".equals(num)) {
								num = "2";
							}
							strBuffer.append(num);
						} else {
							strBuffer.append(Integer.parseInt(num));
						}

						if (j != numbers.length - 1) {
							strBuffer.append(",");
						}
						j++;
					}
					if (i != strs.length - 1) {
						strBuffer.append(";");
					}
					i++;
				}
				return strBuffer.append("|").append(chuan.substring(0, 1) + "*" + Integer.parseInt(chuan.substring(1))).toString();
			}
		};

		// 重庆时时彩单
		IVenderTicketConverter cqsscd = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String[] contentStr = ticket.getContent().split("-")[1].split("\\^");
				for (int i = 0; i < contentStr.length; i++) {
					ticketContent.append(contentStr[i]);
					if (i != contentStr.length - 1) {
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};

		// 重庆时时彩复式
		IVenderTicketConverter cqsscf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace(",", "").replace("^", "").replace(";", ",");
				return contentStr;
			}
		};
		// 重庆时时彩包号
		IVenderTicketConverter cqsscb = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				return contentStr;
			}
		};
		// 重庆时时彩大小单双
		IVenderTicketConverter cqsscds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String[] contentStr = ticket.getContent().split("-")[1].replace(";", ",").split("\\^");

				for (int i = 0; i < contentStr.length; i++) {
					ticketContent.append(contentStr[i].replace("2", "9").replace("1", "0").replace("5", "1").replace("4", "2"));
					if (i != contentStr.length - 1) {
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};

		// 重庆时时彩五星通选双
		IVenderTicketConverter cqsstx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace(";", ",").replace("^", "");
				return contentStr;
			}
		};

		// 山东11x5前二单
		IVenderTicketConverter sd11x5d = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String[] contentStr = ticket.getContent().split("-")[1].split("\\^");
				for (int i = 0; i < contentStr.length; i++) {
					ticketContent.append(contentStr[i].replace(",", "#"));
					if (i != contentStr.length - 1) {
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};
		// 山东11x5前二复式
		IVenderTicketConverter sd11x5qf = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "").replace(";", "#");
				return contentStr;
			}
		};

		// 山东11x5任选胆拖
		IVenderTicketConverter sd11x5dt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "").replace("#", "@");
				return contentStr;
			}
		};

		// 安徽快三二同号复选
		IVenderTicketConverter ethfx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				StringBuffer ticketContent = new StringBuffer();
				ticketContent.append(Integer.parseInt(contentStr)).append(",").append(Integer.parseInt(contentStr));
				ticketContent.append("*");
				return ticketContent.toString();
			}
		};

		// 安徽快三二不同胆拖
		IVenderTicketConverter ebtdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] content = contentStr.split("\\#");
				StringBuffer ticketContent = new StringBuffer();
				ticketContent.append(Integer.parseInt(content[0])).append("@");
				String[] contents = content[1].split("\\,");
				for (int i = 0; i < contents.length; i++) {
					ticketContent.append(Integer.parseInt(content[i]));
					if (i != contents.length - 1) {
						ticketContent.append(",");
					}
				}
				return ticketContent.toString();
			}
		};

		// 安徽快三三同号通选
		IVenderTicketConverter sthtx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = "7,7,7";
				return contentStr;
			}
		};
		// 安徽快三三连号通选
		IVenderTicketConverter slhtx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = "7,8,9";
				return contentStr;
			}
		};

		// 安徽快三和值
		IVenderTicketConverter kshz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				contentStr = Integer.parseInt(contentStr) + "";
				return contentStr;
			}
		};

		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq_fd);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssq_fd);

		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, d3_zxfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, d3_slfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, d3_slfs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, d3_hz);

		playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, qlc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, qlc_fd);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, qlc_fd);

		playTypeToAdvanceConverterMap.put(PlayType.dlt_dan, ssq_ds);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_fu, ssq_fd);
		playTypeToAdvanceConverterMap.put(PlayType.dlt_dantuo, ssq_fd);

		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_fs, qxc_fs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_fs, d3_slfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_fs, d3_slfs);
		playTypeToAdvanceConverterMap.put(PlayType.p3_zhi_dt, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z3_dt, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.p3_z6_dt, d3_hz);

		playTypeToAdvanceConverterMap.put(PlayType.p5_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.p5_fu, pl5);

		playTypeToAdvanceConverterMap.put(PlayType.qxc_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.qxc_fu, qxc_fs);

		playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_1d, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_2d, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_3d, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_5d, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_z3, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_z6, cqsscd);

		playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_1d, cqsscf);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_2d, cqsscf);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_3d, cqsscf);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_5d, cqsscf);

		// playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_2h, cqsscf);
		// playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_3h, cqsscf);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_2z, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_z3, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_z6, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_dd, cqsscds);
		playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_5t, cqsstx);

		// 山东11x5
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq2, sd11x5d);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq3, sd11x5d);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz2, cqsscd);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz3, cqsscd);

		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr2, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr3, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr4, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr5, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr6, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr7, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr8, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq1, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq2, sd11x5qf);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq3, sd11x5qf);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz2, cqsscb);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz3, cqsscb);

		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr2, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr3, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr4, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr5, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr6, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr7, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr8, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz2, sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz3, sd11x5dt);

		// 安徽快三
		playTypeToAdvanceConverterMap.put(PlayType.anhui_ertong_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_ertong_fu, ethfx);
		// playTypeToAdvanceConverterMap.put(PlayType.anhui_ertong_zuhe,sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_erbutong_dan, d3_ds);
		// playTypeToAdvanceConverterMap.put(PlayType.anhui_erbutong_zuhe,sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_erbutong_dt, ebtdt);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_santong_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_santong_tongxuan, sthtx);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_sanbutong_dan, d3_ds);
		// playTypeToAdvanceConverterMap.put(PlayType.anhui_sanbutong_zuhe,sd11x5dt);
		// playTypeToAdvanceConverterMap.put(PlayType.anhui_sanbutong_dt,sd11x5dt);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_sanlian_tongxuan, slhtx);
		playTypeToAdvanceConverterMap.put(PlayType.anhui_hezhi, kshz);

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
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_4_11, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_5, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_6, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_10, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_16, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_20, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_5_26, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_6, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_7, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_15, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_20, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_22, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_35, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_42, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_50, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_6_57, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_7, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_8, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_21, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_35, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_7_120, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_8, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_9, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_28, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_56, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_70, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spf_8_247, jcspf);

		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_1_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_2_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_7_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_8_1, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_3, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_3_4, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_4, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_5, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_6, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_4_11, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_5, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_6, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_10, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_16, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_20, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_5_26, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_6, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_7, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_15, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_20, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_22, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_35, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_42, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_50, jczjq);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_jqs_6_57, jczjq);

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

		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_1_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_2_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_3, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_3_4, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_4, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_5, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_6, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_4_11, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_5, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_6, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_10, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_16, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_20, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_5_26, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_6, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_7, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_15, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_20, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_22, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_35, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_42, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_50, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_6_57, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_7, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_8, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_21, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_35, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_7_120, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_8, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_9, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_28, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_56, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_70, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_spfwrq_8_247, jcspf);

		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_1_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_2_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_5_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_6_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_7_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_8_1, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_3, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_3_4, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_4, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_5, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_6, jcspf);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_bqc_4_11, jcspf);

		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_1_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_2_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_3, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_3_4, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_4, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_5, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_6, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_4_11, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_5, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_6, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_10, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_16, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_20, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_5_26, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_6, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_7, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_15, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_20, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_22, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_35, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_42, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_50, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_6_57, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_7, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_8, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_21, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_35, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_7_120, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_8, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_9, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_28, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_56, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_70, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sf_8_247, jclqsf);

		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_1_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_2_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_1, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_3, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_3_4, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_4, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_5, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_6, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_4_11, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_5, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_6, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_10, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_16, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_20, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_5_26, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_6, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_7, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_15, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_20, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_22, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_35, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_42, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_50, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_6_57, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_7, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_8, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_21, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_35, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_7_120, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_8, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_9, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_28, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_56, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_70, jclqsf);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_rfsf_8_247, jclqsf);

		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_1_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_2_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_5_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_6_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_7_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_8_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_3, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_3_4, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_4, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_5, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_6, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_sfc_4_11, jclq);

		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_1_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_2_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_1, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_3, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_3_4, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_4, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_5, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_6, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_4_11, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_5, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_6, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_10, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_16, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_20, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_5_26, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_6, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_7, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_15, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_20, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_22, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_35, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_42, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_50, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_6_57, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_7, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_8, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_21, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_35, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_7_120, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_8, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_9, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_28, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_56, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_70, jclq);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_dxf_8_247, jclq);

		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_2_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_3, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_3_4, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_4, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_5, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_6, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_4_11, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_5, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_6, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_10, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_16, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_20, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_5_26, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_6, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_7, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_15, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_20, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_22, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_35, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_42, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_50, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_6_57, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_7, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_8, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_21, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_35, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_7_120, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_8, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_9, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_28, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_56, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_70, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jclq_mix_8_247, jclqmix);

		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_2_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_1, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_3, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_3_4, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_4, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_5, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_6, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_4_11, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_5, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_6, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_10, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_16, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_20, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_5_26, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_6, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_7, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_15, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_20, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_22, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_35, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_42, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_50, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_6_57, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_7, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_8, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_21, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_35, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_7_120, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_8, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_9, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_28, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_56, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_70, jclqmix);
		playTypeToAdvanceConverterMap.put(PlayType.jczq_mix_8_247, jclqmix);

		// 老足彩
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_dan, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_sfc_fu, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dan, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_fu, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_rjc_dt, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_dan, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_jqc_fu, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_dan, zc);
		playTypeToAdvanceConverterMap.put(PlayType.zc_bqc_fu, zc);

	}

}
