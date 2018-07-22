package com.lottery.ticket.vender.impl.huancai;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;

import com.lottery.ticket.IVenderTicketConverter;

public class HuancaiLotteryDef {
	/** 彩种转换 */
	protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
	/** 彩种转换 */
	public static Map<Integer,String> toLotteryTypeMap = new HashMap<Integer,String>();
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
		phaseConverterMap.put(LotteryType.JSK3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.GXK3, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.SD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.GD_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.XJ_11X5, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.CQSSC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.XJSSC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.CQKL10, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.GDKL10, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.AHK3, defaultPhaseConverter);
		//彩种转换
		
		lotteryTypeMap.put(LotteryType.JSK3, "013");
		lotteryTypeMap.put(LotteryType.GXK3, "014");
		lotteryTypeMap.put(LotteryType.SD_11X5, "115");
		lotteryTypeMap.put(LotteryType.GD_11X5, "116");
		lotteryTypeMap.put(LotteryType.XJ_11X5, "117");
		lotteryTypeMap.put(LotteryType.CQSSC, "113"); 
		lotteryTypeMap.put(LotteryType.XJSSC, "114"); 
		lotteryTypeMap.put(LotteryType.CQKL10, "123"); 
		lotteryTypeMap.put(LotteryType.GDKL10, "122"); 
		lotteryTypeMap.put(LotteryType.AHK3, "003"); 
		
		//玩法转换
		//江苏快三
		playTypeMap.put(PlayType.jiangsu_ertong_dan.getValue(), "601");
		playTypeMap.put(PlayType.jiangsu_ertong_fu.getValue(), "501");
		playTypeMap.put(PlayType.jiangsu_ertong_zuhe.getValue(), "502");
		playTypeMap.put(PlayType.jiangsu_erbutong_dan.getValue(), "701");
//		playTypeMap.put(PlayType.jiangsu_erbutong_zuhe.getValue(), "702");
//		playTypeMap.put(PlayType.jiangsu_erbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.jiangsu_santong_dan.getValue(), "301");
		playTypeMap.put(PlayType.jiangsu_santong_tongxuan.getValue(), "201");
		playTypeMap.put(PlayType.jiangsu_sanbutong_dan.getValue(), "801");
//		playTypeMap.put(PlayType.jiangsu_sanbutong_zuhe.getValue(), "01");
//		playTypeMap.put(PlayType.jiangsu_sanbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.jiangsu_sanlian_tongxuan.getValue(), "401");
		playTypeMap.put(PlayType.jiangsu_hezhi.getValue(), "101");
		
		
		//广西快三
		playTypeMap.put(PlayType.gxk3_ertong_dan.getValue(), "601");
		playTypeMap.put(PlayType.gxk3_ertong_fu.getValue(), "501");
		playTypeMap.put(PlayType.gxk3_ertong_zuhe.getValue(), "502");
		playTypeMap.put(PlayType.gxk3_erbutong_dan.getValue(), "701");
//		playTypeMap.put(PlayType.gxk3_erbutong_zuhe.getValue(), "702");
//		playTypeMap.put(PlayType.gxk3_erbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.gxk3_santong_dan.getValue(), "301");
		playTypeMap.put(PlayType.gxk3_santong_tongxuan.getValue(), "201");
		playTypeMap.put(PlayType.gxk3_sanbutong_dan.getValue(), "801");
//		playTypeMap.put(PlayType.gxk3_sanbutong_zuhe.getValue(), "01");
//		playTypeMap.put(PlayType.gxk3_sanbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.gxk3_sanlian_tongxuan.getValue(), "401");
		playTypeMap.put(PlayType.gxk3_hezhi.getValue(), "101");	
		
		
		//安徽快三
		playTypeMap.put(PlayType.anhui_ertong_dan.getValue(), "601");
		playTypeMap.put(PlayType.anhui_ertong_fu.getValue(), "501");
		playTypeMap.put(PlayType.anhui_ertong_zuhe.getValue(), "502");
		playTypeMap.put(PlayType.anhui_erbutong_dan.getValue(), "701");
//		playTypeMap.put(PlayType.anhui_erbutong_zuhe.getValue(), "702");
//		playTypeMap.put(PlayType.anhui_erbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.anhui_santong_dan.getValue(), "301");
		playTypeMap.put(PlayType.anhui_santong_tongxuan.getValue(), "201");
		playTypeMap.put(PlayType.anhui_sanbutong_dan.getValue(), "801");
//		playTypeMap.put(PlayType.anhui_sanbutong_zuhe.getValue(), "01");
//		playTypeMap.put(PlayType.anhui_sanbutong_dt.getValue(), "01");
		playTypeMap.put(PlayType.anhui_sanlian_tongxuan.getValue(), "401");
		playTypeMap.put(PlayType.anhui_hezhi.getValue(), "101");
		
		
		playTypeMap.put(PlayType.gd11x5_sr2.getValue(),"201");
		playTypeMap.put(PlayType.gd11x5_sr3.getValue(),"301");
		playTypeMap.put(PlayType.gd11x5_sr4.getValue(),"401");
		playTypeMap.put(PlayType.gd11x5_sr5.getValue(),"501");
		playTypeMap.put(PlayType.gd11x5_sr6.getValue(),"601");
		playTypeMap.put(PlayType.gd11x5_sr7.getValue(),"701");
		playTypeMap.put(PlayType.gd11x5_sr8.getValue(),"801");
		playTypeMap.put(PlayType.gd11x5_sq1.getValue(),"101");
	    playTypeMap.put(PlayType.gd11x5_sq2.getValue(),"901");
		playTypeMap.put(PlayType.gd11x5_sq3.getValue(),"111");
		playTypeMap.put(PlayType.gd11x5_sz2.getValue(),"903");
		playTypeMap.put(PlayType.gd11x5_sz3.getValue(),"113");
		
		playTypeMap.put(PlayType.gd11x5_mr2.getValue(),"201");
		playTypeMap.put(PlayType.gd11x5_mr3.getValue(),"301");
		playTypeMap.put(PlayType.gd11x5_mr4.getValue(),"401");
		playTypeMap.put(PlayType.gd11x5_mr5.getValue(),"501");
		playTypeMap.put(PlayType.gd11x5_mr6.getValue(),"601");
		playTypeMap.put(PlayType.gd11x5_mr7.getValue(),"701");
//		playTypeMap.put(PlayType.gd11c5_mr8.getValue(),"");
		playTypeMap.put(PlayType.gd11x5_mq1.getValue(),"102");
		playTypeMap.put(PlayType.gd11x5_mq2.getValue(),"902");
		playTypeMap.put(PlayType.gd11x5_mq3.getValue(),"112");
		playTypeMap.put(PlayType.gd11x5_mz2.getValue(),"903");
		playTypeMap.put(PlayType.gd11x5_mz3.getValue(),"113");
		
		playTypeMap.put(PlayType.gd11x5_dr2.getValue(),"202");
		playTypeMap.put(PlayType.gd11x5_dr3.getValue(),"302");
		playTypeMap.put(PlayType.gd11x5_dr4.getValue(),"402");
		playTypeMap.put(PlayType.gd11x5_dr5.getValue(),"502");
		playTypeMap.put(PlayType.gd11x5_dr6.getValue(),"602");
		playTypeMap.put(PlayType.gd11x5_dr7.getValue(),"702");
//		playTypeMap.put(PlayType.gd11c5_dr8.getValue(),"802");
		playTypeMap.put(PlayType.gd11x5_dz2.getValue(),"904");
		playTypeMap.put(PlayType.gd11x5_dz3.getValue(),"114");
		
		

		playTypeMap.put(PlayType.sd11x5_sr2.getValue(),"201");
		playTypeMap.put(PlayType.sd11x5_sr3.getValue(),"301");
		playTypeMap.put(PlayType.sd11x5_sr4.getValue(),"401");
		playTypeMap.put(PlayType.sd11x5_sr5.getValue(),"501");
		playTypeMap.put(PlayType.sd11x5_sr6.getValue(),"601");
		playTypeMap.put(PlayType.sd11x5_sr7.getValue(),"701");
		playTypeMap.put(PlayType.sd11x5_sr8.getValue(),"801");
		playTypeMap.put(PlayType.sd11x5_sq1.getValue(),"101");
	    playTypeMap.put(PlayType.sd11x5_sq2.getValue(),"901");
		playTypeMap.put(PlayType.sd11x5_sq3.getValue(),"111");
		playTypeMap.put(PlayType.sd11x5_sz2.getValue(),"903");
		playTypeMap.put(PlayType.sd11x5_sz3.getValue(),"113");
		
		playTypeMap.put(PlayType.sd11x5_mr2.getValue(),"201");
		playTypeMap.put(PlayType.sd11x5_mr3.getValue(),"301");
		playTypeMap.put(PlayType.sd11x5_mr4.getValue(),"401");
		playTypeMap.put(PlayType.sd11x5_mr5.getValue(),"501");
		playTypeMap.put(PlayType.sd11x5_mr6.getValue(),"601");
		playTypeMap.put(PlayType.sd11x5_mr7.getValue(),"701");
//		playTypeMap.put(PlayType.gd11c5_mr8.getValue(),"");
		playTypeMap.put(PlayType.sd11x5_mq1.getValue(),"102");
		playTypeMap.put(PlayType.sd11x5_mq2.getValue(),"902");
		playTypeMap.put(PlayType.sd11x5_mq3.getValue(),"112");
		playTypeMap.put(PlayType.sd11x5_mz2.getValue(),"903");
		playTypeMap.put(PlayType.sd11x5_mz3.getValue(),"113");
		
		playTypeMap.put(PlayType.sd11x5_dr2.getValue(),"202");
		playTypeMap.put(PlayType.sd11x5_dr3.getValue(),"302");
		playTypeMap.put(PlayType.sd11x5_dr4.getValue(),"402");
		playTypeMap.put(PlayType.sd11x5_dr5.getValue(),"502");
		playTypeMap.put(PlayType.sd11x5_dr6.getValue(),"602");
		playTypeMap.put(PlayType.sd11x5_dr7.getValue(),"702");
//		playTypeMap.put(PlayType.sd11c5_dr8.getValue(),"802");
		playTypeMap.put(PlayType.sd11x5_dz2.getValue(),"904");
		playTypeMap.put(PlayType.sd11x5_dz3.getValue(),"114");
		
		playTypeMap.put(PlayType.sd11x5_sl3.getValue(),"121");
		playTypeMap.put(PlayType.sd11x5_sl4.getValue(),"122");
		playTypeMap.put(PlayType.sd11x5_sl5.getValue(),"123");
		
		
		
		playTypeMap.put(PlayType.xj11x5_sr2.getValue(),"201");
		playTypeMap.put(PlayType.xj11x5_sr3.getValue(),"301");
		playTypeMap.put(PlayType.xj11x5_sr4.getValue(),"401");
		playTypeMap.put(PlayType.xj11x5_sr5.getValue(),"501");
		playTypeMap.put(PlayType.xj11x5_sr6.getValue(),"601");
		playTypeMap.put(PlayType.xj11x5_sr7.getValue(),"701");
		playTypeMap.put(PlayType.xj11x5_sr8.getValue(),"801");
		playTypeMap.put(PlayType.xj11x5_sq1.getValue(),"101");
	    playTypeMap.put(PlayType.xj11x5_sq2.getValue(),"901");
		playTypeMap.put(PlayType.xj11x5_sq3.getValue(),"111");
		playTypeMap.put(PlayType.xj11x5_sz2.getValue(),"903");
		playTypeMap.put(PlayType.xj11x5_sz3.getValue(),"113");
		
		playTypeMap.put(PlayType.xj11x5_mr2.getValue(),"201");
		playTypeMap.put(PlayType.xj11x5_mr3.getValue(),"301");
		playTypeMap.put(PlayType.xj11x5_mr4.getValue(),"401");
		playTypeMap.put(PlayType.xj11x5_mr5.getValue(),"501");
		playTypeMap.put(PlayType.xj11x5_mr6.getValue(),"601");
		playTypeMap.put(PlayType.xj11x5_mr7.getValue(),"701");
//		playTypeMap.put(PlayType.gd11c5_mr8.getValue(),"");
		playTypeMap.put(PlayType.xj11x5_mq1.getValue(),"102");
		playTypeMap.put(PlayType.xj11x5_mq2.getValue(),"902");
		playTypeMap.put(PlayType.xj11x5_mq3.getValue(),"112");
		playTypeMap.put(PlayType.xj11x5_mz2.getValue(),"903");
		playTypeMap.put(PlayType.xj11x5_mz3.getValue(),"113");
		
		playTypeMap.put(PlayType.xj11x5_dr2.getValue(),"202");
		playTypeMap.put(PlayType.xj11x5_dr3.getValue(),"302");
		playTypeMap.put(PlayType.xj11x5_dr4.getValue(),"402");
		playTypeMap.put(PlayType.xj11x5_dr5.getValue(),"502");
		playTypeMap.put(PlayType.xj11x5_dr6.getValue(),"602");
		playTypeMap.put(PlayType.xj11x5_dr7.getValue(),"702");
//		playTypeMap.put(PlayType.sd11c5_dr8.getValue(),"802");
		playTypeMap.put(PlayType.xj11x5_dz2.getValue(),"904");
		playTypeMap.put(PlayType.xj11x5_dz3.getValue(),"114");
		
	
		
		playTypeMap.put(PlayType.cqssc_dan_1d.getValue(),"103");
		playTypeMap.put(PlayType.cqssc_dan_2d.getValue(),"201");
		playTypeMap.put(PlayType.cqssc_dan_3d.getValue(),"301");
		playTypeMap.put(PlayType.cqssc_dan_5d.getValue(),"501");
		playTypeMap.put(PlayType.cqssc_dan_z3.getValue(),"401");
		playTypeMap.put(PlayType.cqssc_dan_z6.getValue(),"403");
		
		playTypeMap.put(PlayType.cqssc_fu_1d.getValue(),"104");
		playTypeMap.put(PlayType.cqssc_fu_2d.getValue(),"202");
		playTypeMap.put(PlayType.cqssc_fu_3d.getValue(),"302");
		playTypeMap.put(PlayType.cqssc_fu_5d.getValue(),"502");
		
		playTypeMap.put(PlayType.cqssc_other_2h.getValue(),"205");
		playTypeMap.put(PlayType.cqssc_other_3h.getValue(),"303");
		playTypeMap.put(PlayType.cqssc_other_2z.getValue(),"204");
		playTypeMap.put(PlayType.cqssc_other_z3.getValue(),"402");
		playTypeMap.put(PlayType.cqssc_other_z6.getValue(),"404");
		playTypeMap.put(PlayType.cqssc_other_dd.getValue(),"101");
		playTypeMap.put(PlayType.cqssc_other_5t.getValue(),"503");
		
		//新疆时时彩
		playTypeMap.put(PlayType.xjssc_dan_1d.getValue(),"102");
		playTypeMap.put(PlayType.xjssc_dan_2d.getValue(),"201");
		playTypeMap.put(PlayType.xjssc_dan_3d.getValue(),"301");
		playTypeMap.put(PlayType.xjssc_dan_4d.getValue(),"401");
		playTypeMap.put(PlayType.xjssc_dan_5d.getValue(),"501");
		playTypeMap.put(PlayType.xjssc_dan_z3.getValue(),"302");
		playTypeMap.put(PlayType.xjssc_dan_z6.getValue(),"304");
		playTypeMap.put(PlayType.xjssc_dan_2r.getValue(),"203");
		playTypeMap.put(PlayType.xjssc_dan_3r.getValue(),"305");

		playTypeMap.put(PlayType.xjssc_fu_1d.getValue(),"102");
		playTypeMap.put(PlayType.xjssc_fu_2d.getValue(),"201");
		playTypeMap.put(PlayType.xjssc_fu_3d.getValue(),"301");
		playTypeMap.put(PlayType.xjssc_fu_4d.getValue(),"401");
		playTypeMap.put(PlayType.xjssc_fu_5d.getValue(),"501");
		playTypeMap.put(PlayType.xjssc_fu_2r.getValue(),"203");
		playTypeMap.put(PlayType.xjssc_fu_3r.getValue(),"305");

		playTypeMap.put(PlayType.xjssc_other_2z.getValue(),"202");
		playTypeMap.put(PlayType.xjssc_other_z3.getValue(),"303");
		playTypeMap.put(PlayType.xjssc_other_z6.getValue(),"304");
		playTypeMap.put(PlayType.xjssc_other_dd.getValue(),"101");
		playTypeMap.put(PlayType.xjssc_other_5t.getValue(),"502");

		playTypeMap.put(PlayType.xjssc_dan_qw2d.getValue(),"204");
		playTypeMap.put(PlayType.xjssc_dan_qj2d.getValue(),"205");

		playTypeMap.put(PlayType.xjssc_fu_qw2d.getValue(),"204");
		playTypeMap.put(PlayType.xjssc_fu_qj2d.getValue(),"205");
		
		
		//重庆快十
		playTypeMap.put(PlayType.cqkl10_sh0.getValue(),"102");
		playTypeMap.put(PlayType.cqkl10_ss1.getValue(),"101");
		playTypeMap.put(PlayType.cqkl10_sr2.getValue(),"201");
		playTypeMap.put(PlayType.cqkl10_sr3.getValue(),"301");
		playTypeMap.put(PlayType.cqkl10_sr4.getValue(),"401");
		playTypeMap.put(PlayType.cqkl10_sr5.getValue(),"501");
		playTypeMap.put(PlayType.cqkl10_sq2.getValue(),"205");
	    playTypeMap.put(PlayType.cqkl10_sq3.getValue(),"305");
		playTypeMap.put(PlayType.cqkl10_sz2.getValue(),"203");
		playTypeMap.put(PlayType.cqkl10_sz3.getValue(),"303");
		
		playTypeMap.put(PlayType.cqkl10_ms1.getValue(),"101");
		playTypeMap.put(PlayType.cqkl10_mr2.getValue(),"201");
		playTypeMap.put(PlayType.cqkl10_mr3.getValue(),"301");
		playTypeMap.put(PlayType.cqkl10_mr4.getValue(),"401");
		playTypeMap.put(PlayType.cqkl10_mr5.getValue(),"501");
		playTypeMap.put(PlayType.cqkl10_mz2.getValue(),"203");
		playTypeMap.put(PlayType.cqkl10_mz3.getValue(),"303");
		
		playTypeMap.put(PlayType.cqkl10_dr2.getValue(),"202");
		playTypeMap.put(PlayType.cqkl10_dr3.getValue(),"302");
		playTypeMap.put(PlayType.cqkl10_dr4.getValue(),"402");
		playTypeMap.put(PlayType.cqkl10_dr5.getValue(),"502");
//		playTypeMap.put(PlayType.cqkl10_dq2.getValue(),"503");
//		playTypeMap.put(PlayType.cqkl10_dq3.getValue(),"503");
		playTypeMap.put(PlayType.cqkl10_dz2.getValue(),"204");
		playTypeMap.put(PlayType.cqkl10_dz3.getValue(),"304");
		
		playTypeMap.put(PlayType.cqkl10_pq2.getValue(),"205");
		playTypeMap.put(PlayType.cqkl10_pq3.getValue(),"305");
		
		//广东 快乐十分
		playTypeMap.put(PlayType.gdkl10_sh0.getValue(),"102");
		playTypeMap.put(PlayType.gdkl10_ss1.getValue(),"101");
		playTypeMap.put(PlayType.gdkl10_sr2.getValue(),"201");
		playTypeMap.put(PlayType.gdkl10_sr3.getValue(),"301");
		playTypeMap.put(PlayType.gdkl10_sr4.getValue(),"401");
		playTypeMap.put(PlayType.gdkl10_sr5.getValue(),"501");
		playTypeMap.put(PlayType.gdkl10_sq2.getValue(),"205");
	    playTypeMap.put(PlayType.gdkl10_sq3.getValue(),"305");
		playTypeMap.put(PlayType.gdkl10_sz2.getValue(),"203");
		playTypeMap.put(PlayType.gdkl10_sz3.getValue(),"303");
		
		playTypeMap.put(PlayType.gdkl10_ms1.getValue(),"101");
		playTypeMap.put(PlayType.gdkl10_mr2.getValue(),"201");
		playTypeMap.put(PlayType.gdkl10_mr3.getValue(),"301");
		playTypeMap.put(PlayType.gdkl10_mr4.getValue(),"401");
		playTypeMap.put(PlayType.gdkl10_mr5.getValue(),"501");
		playTypeMap.put(PlayType.gdkl10_mz2.getValue(),"203");
		playTypeMap.put(PlayType.gdkl10_mz3.getValue(),"303");
		
		playTypeMap.put(PlayType.gdkl10_dr2.getValue(),"202");
		playTypeMap.put(PlayType.gdkl10_dr3.getValue(),"302");
		playTypeMap.put(PlayType.gdkl10_dr4.getValue(),"402");
		playTypeMap.put(PlayType.gdkl10_dr5.getValue(),"502");
//		playTypeMap.put(PlayType.cqkl10_dq2.getValue(),"503");
//		playTypeMap.put(PlayType.cqkl10_dq3.getValue(),"503");
		playTypeMap.put(PlayType.gdkl10_dz2.getValue(),"204");
		playTypeMap.put(PlayType.gdkl10_dz3.getValue(),"304");
		
		playTypeMap.put(PlayType.gdkl10_pq2.getValue(),"205");
		playTypeMap.put(PlayType.gdkl10_pq3.getValue(),"305");
		
		
		// 单式
		IVenderTicketConverter erth_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					String content = oneCode[j];
					String []cons=content.split("\\,");
				    if(cons[0].equals(cons[1])){
					   ticketContent.append(cons[0]).append("#").append(cons[2]);
					}else{
                       ticketContent.append(cons[2]).append("#").append(cons[0]);
					}
					if(j!=oneCode.length-1){
						ticketContent.append(";");
					}
				}
				return "<betCode>"+ticketContent.toString()+"</betCode>";
			}
		};
		IVenderTicketConverter erth_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String []oneCodes = contentStr.split("\\^");
				for(String code:oneCodes){
			    	ticketContent.append("<betCode>");
			    	ticketContent.append(code);
			    	ticketContent.append("</betCode>");
			    }
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter sth_tx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String []oneCodes = contentStr.split("\\^");
				for(String code:oneCodes){
			    	ticketContent.append("<betCode>");
			    	ticketContent.append(code.replace("1","3"));
			    	ticketContent.append("</betCode>");
			    }
				return ticketContent.toString();
			}
		};
	
	
		IVenderTicketConverter tx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String []oneCodes = contentStr.split("\\^");
				for(String code:oneCodes){
			    	ticketContent.append("<betCode>");
			    	ticketContent.append(code.replace("1","3"));
			    	ticketContent.append("</betCode>");
			    }
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter erbt_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				int j=0;
				for (String code:oneCode) {
					ticketContent.append(code);
					if(j!=oneCode.length-1){
						ticketContent.append(";");
					}
					j++;
				}
				return "<betCode>"+ticketContent.toString()+"</betCode>";
			}
		};
		IVenderTicketConverter sth_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					String content = oneCode[j];
					String []cons=content.split("\\,");
					ticketContent.append(cons[0]);
					if(j!=oneCode.length-1){
						ticketContent.append(";");
					}
				}
				return "<betCode>"+ticketContent.toString()+"</betCode>";
			}
		};
		IVenderTicketConverter k3hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^","");
				if(Integer.parseInt(oneCode)<=9){
					oneCode="0"+oneCode;
				}
				return "<betCode>"+oneCode+"</betCode>";
			}
		};

		
		// 单式
		IVenderTicketConverter sdrx_qxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^", "");
				oneCode=oneCode.replace(",","#");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		IVenderTicketConverter qerfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^", "");
				oneCode=oneCode.replace(";","#");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		
		IVenderTicketConverter gddt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^", "");
				oneCode=oneCode.replace("#","@");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		
		IVenderTicketConverter cqssc = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
					String[] oneCode = contentStr.split("\\^");
					
					for (int j=0; j < oneCode.length; j++) {
						 ticketContent.append("<betCode>");
						ticketContent.append(oneCode[j].replace(",","#"));
//						if(j!=oneCode.length-1){
//							ticketContent.append(";");
//						}
						 ticketContent.append("</betCode>");
					}
					
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter cqssc_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j]);
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter cqsrlz_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j].replace(",","#"));
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter cqssc_zxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j]);
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter cqssc_zx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j]);
					 ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter cqssc_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^","").replace(";","#");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		
		IVenderTicketConverter cqssc_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^","");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		IVenderTicketConverter cqssc_dxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer("<betCode>");
				String []oneCode = contentStr.replace("^","").split("\\;");
				if(oneCode[0].equals("2")){
					ticketContent.append(oneCode[0].replace("2","1")).append("#");
				}
				if(oneCode[0].equals("1")){
					ticketContent.append(oneCode[0].replace("1","2")).append("#");
				}
				if(oneCode[0].equals("5")){
					ticketContent.append(oneCode[0].replace("5","3")).append("#");
				}
				if(oneCode[0].equals("4")){
					ticketContent.append(oneCode[0]).append("#");
				}
				if(oneCode[1].equals("2")){
					ticketContent.append(oneCode[1].replace("2","1"));
				}
				if(oneCode[1].equals("1")){
					ticketContent.append(oneCode[1].replace("1","2"));
				}
				if(oneCode[1].equals("5")){
					ticketContent.append(oneCode[1].replace("5","3"));
				}
				if(oneCode[1].equals("4")){
					ticketContent.append(oneCode[1]);
				}
				return ticketContent.append("</betCode>").toString();
			}
		};
		
		IVenderTicketConverter cqklsfdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^", "");
				oneCode=oneCode.replace("#","@");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		
		IVenderTicketConverter cqklsfdw = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1]+"-"+ticket.getContent().split("-")[2];
				String oneCode = contentStr.replace("^", "");
				oneCode=oneCode.replace("-","#");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		IVenderTicketConverter cqklsfqsdw = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1]+"-"+ticket.getContent().split("-")[2]+"-"+ticket.getContent().split("-")[3];
				String oneCode = contentStr.replace("^", "");
				oneCode=oneCode.replace("-","#");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		
		IVenderTicketConverter xjds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j].replace("~","-").replace(",", "#"));
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjexrxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j].replace("~","-").replace(",", "#"));
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				StringBuffer ticketContent = new StringBuffer();
				ticketContent.append("<betCode>").append(contentStr.replace("~","-").replace(";", "#"));
				ticketContent.append("</betCode>");
				
				return ticketContent.toString();
			}
		};
		
		
		IVenderTicketConverter xjexrxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				StringBuffer ticketContent = new StringBuffer();
				ticketContent.append("<betCode>").append(contentStr.replace("~","-").replace(";", "#"));
				ticketContent.append("</betCode>");
				
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjrxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				ticketContent.append("<betCode>").append(contentStr.replace("~","-").replace(",", "#").replace(";", ","));
				ticketContent.append("</betCode>");
				
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter xjerxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j].replace("~","-").replace(";","#"));
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter xjexzx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j]);
					ticketContent.append("</betCode>");
				}
				
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjdxdx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				StringBuffer ticketContent = new StringBuffer();
				String []contents=contentStr.split(";");
				ticketContent.append("<betCode>");
				int j=0;
				for(String content:contents){
					String []cons=content.split("\\,");
					int i=0;
					for(String con:cons){
						int c=Integer.parseInt(con);
						if(c==2){
							ticketContent.append("1");
						}else if(c==1){
							ticketContent.append("2");
						}else if(c==5){
							ticketContent.append("3");
						}else{
							ticketContent.append("4");
						}
						if(i!=cons.length-1){
							ticketContent.append(",");
						}
						i++;
					}
					if(j!=contents.length-1){
						ticketContent.append("#");
					}
					j++;
				}
				
				ticketContent.append("</betCode>");
				
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjwxtx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					 ticketContent.append("<betCode>").append(oneCode[j].replace(";","#"));
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
	
		
		IVenderTicketConverter xjqj = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				
				for(String content:oneCode){
					ticketContent.append("<betCode>");
					String []cons=content.split("\\,");
					int i=0;
					for(String con:cons){
						ticketContent.append(con);
						if(i!=cons.length-1){
							ticketContent.append("#");
						}
						i++;
					}
					ticketContent.append("</betCode>");
				}
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjqw = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for(String content:oneCode){
					ticketContent.append("<betCode>");
					String []cons=content.split("\\,");
					int i=0;
					for(String con:cons){
						int c=Integer.parseInt(con);
						if(i==0){
							if(c==1){
								ticketContent.append("2");
							}else if(c==2){
								ticketContent.append("1");
							}
						}else{
							ticketContent.append(con);
						}
						
						if(i!=cons.length-1){
							ticketContent.append("#");
						}
						i++;
					}
					ticketContent.append("</betCode>");
				}
				
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter xjqwfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {//101442-2,2;4,5;9^
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\;");
				ticketContent.append("<betCode>");
				int j=0;
				for(String content:oneCode){
					String []cons=content.split("\\,");
					int i=0;
					for(String con:cons){
						int c=Integer.parseInt(con);
						if(j==0){
							if(c==1){
								ticketContent.append("2");
							}else if(c==2){
								ticketContent.append("1");
							}
						}else{
							ticketContent.append(con);
						}
						
						if(i!=cons.length-1){
							ticketContent.append(",");
						}
						i++;
					}
					if(j!=oneCode.length-1){
						ticketContent.append("#");
					}
					j++;
				}
				ticketContent.append("</betCode>");	
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter xjqjfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\;");
				ticketContent.append("<betCode>");
				int j=0;
				for(String content:oneCode){
					String []cons=content.split("\\,");
					int i=0;
					for(String con:cons){
						ticketContent.append(con);
						if(i!=cons.length-1){
							ticketContent.append(",");
						}
						i++;
					}
					if(j!=oneCode.length-1){
						ticketContent.append("#");
					}
					j++;
				}
				ticketContent.append("</betCode>");		
				return ticketContent.toString();
			}
		};
		
		IVenderTicketConverter cqssc_tx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String oneCode = contentStr.replace("^","").replace(";","#");
				return "<betCode>"+oneCode+"</betCode>";
			}
		};
		
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_ertong_dan, erth_ds);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_ertong_fu, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_erbutong_dan, erbt_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_erbutong_dt, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_santong_dan, sth_ds);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_santong_tongxuan, sth_tx);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_sanbutong_dan, erbt_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_sanbutong_zuhe, qlc_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_sanbutong_dt, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_sanlian_tongxuan, sth_tx);
			playTypeToAdvanceConverterMap.put(PlayType.jiangsu_hezhi, k3hz);
			
			
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_ertong_dan, erth_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_ertong_fu, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_erbutong_dan, erbt_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.gxk3_erbutong_dt, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_santong_dan, sth_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_santong_tongxuan, sth_tx);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanbutong_dan, erbt_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanbutong_zuhe, qlc_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanbutong_dt, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_sanlian_tongxuan, sth_tx);
			playTypeToAdvanceConverterMap.put(PlayType.gxk3_hezhi, k3hz);
			
			
			playTypeToAdvanceConverterMap.put(PlayType.anhui_ertong_dan, erth_ds);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_ertong_fu, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_erbutong_dan, erbt_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.anhui_erbutong_dt, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_santong_dan, sth_ds);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_santong_tongxuan, tx);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_sanbutong_dan, erbt_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.anhui_sanbutong_zuhe, qlc_ds);
//			playTypeToAdvanceConverterMap.put(PlayType.anhui_sanbutong_dt, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_sanlian_tongxuan, tx);
			playTypeToAdvanceConverterMap.put(PlayType.anhui_hezhi, k3hz);
			

			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr5, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr6, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr7, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sr8, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq1, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq2, sdrx_qxds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sq3, sdrx_qxds);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_sz3, erth_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr5, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr6, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mr7, erth_fs);
//			playTypeToAdvanceConverterMap.put(PlayType.gd11c5_mr8, k3hz);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq1, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq2, qerfs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mq3, qerfs);
		    playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_mz3, erth_fs);
			
		    playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr2, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr3, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr4, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr5, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr6, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dr7, gddt);
//			playTypeToAdvanceConverterMap.put(PlayType.gd11c5_dr8, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz2, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.gd11x5_dz3, gddt);
			
			
			
			//sd11x5
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr5, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr6, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr7, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sr8, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq1, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq2, sdrx_qxds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sq3, sdrx_qxds);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sz3, erth_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr5, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr6, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mr7, erth_fs);
//			playTypeToAdvanceConverterMap.put(PlayType.sd11c5_mr8, k3hz);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq1, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq2, qerfs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mq3, qerfs);
		    playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_mz3, erth_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr2, gddt);
		    playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr3, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr4, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr5, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr6, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dr7, gddt);
//			playTypeToAdvanceConverterMap.put(PlayType.sd11c5_dr8, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz2, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_dz3, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.sd11x5_sl5, erth_fs);
			
			
			//xj11x5
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr5, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr6, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr7, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sr8, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sq1, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sq2, sdrx_qxds);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sq3, sdrx_qxds);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sz2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_sz3, erth_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mr2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mr3, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mr4, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mr5, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mr6, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mr7, erth_fs);
//			playTypeToAdvanceConverterMap.put(PlayType.sd11c5_mr8, k3hz);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mq1, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mq2, qerfs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mq3, qerfs);
		    playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mz2, erth_fs);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_mz3, erth_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dr2, gddt);
		    playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dr3, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dr4, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dr5, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dr6, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dr7, gddt);
//			playTypeToAdvanceConverterMap.put(PlayType.sd11c5_dr8, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dz2, gddt);
			playTypeToAdvanceConverterMap.put(PlayType.xj11x5_dz3, gddt);
			
			
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_1d, cqssc);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_2d, cqssc);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_3d, cqssc);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_5d, cqssc);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_z3, cqssc_zxds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_dan_z6, cqssc_zxds);
			
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_1d, cqssc_fs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_2d, cqssc_fs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_3d, cqssc_fs);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_fu_5d, cqssc_fs);
			
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_2h, cqssc_hz);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_3h, cqssc_hz);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_2z, cqssc_zx);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_z3, cqssc_hz);
		    playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_z6, cqssc_hz);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_dd, cqssc_dxds);
			playTypeToAdvanceConverterMap.put(PlayType.cqssc_other_5t, cqssc_tx);
			
			
			
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sh0,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_ss1,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sr2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sr3,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sr4,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sr5,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sq2,cqsrlz_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sq3,cqsrlz_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sz2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_sz3,cqssc_ds);
			
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_ms1,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_mr2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_mr3,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_mr4,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_mr5,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_mz2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_mz3,cqssc_ds);
			
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_dr2,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_dr3,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_dr4,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_dr5,cqklsfdt);
//			playTypeMap.put(PlayType.cqkl10_dq2.getValue(),"503");
//			playTypeMap.put(PlayType.cqkl10_dq3.getValue(),"503");
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_dz2,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_dz3,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_pq2,cqklsfdw);
			playTypeToAdvanceConverterMap.put(PlayType.cqkl10_pq3,cqklsfqsdw);
			
			
			//广东
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sh0,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_ss1,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sr2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sr3,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sr4,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sr5,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sq2,cqsrlz_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sq3,cqsrlz_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sz2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_sz3,cqssc_ds);
			
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_ms1,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_mr2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_mr3,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_mr4,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_mr5,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_mz2,cqssc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_mz3,cqssc_ds);
			
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_dr2,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_dr3,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_dr4,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_dr5,cqklsfdt);
//			playTypeMap.put(PlayType.cqkl10_dq2.getValue(),"503");
//			playTypeMap.put(PlayType.cqkl10_dq3.getValue(),"503");
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_dz2,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_dz3,cqklsfdt);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_pq2,cqklsfdw);
			playTypeToAdvanceConverterMap.put(PlayType.gdkl10_pq3,cqklsfqsdw);
			
			
			//新疆时时彩
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_1d,xjds);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_2d,xjds);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_3d,xjds);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_4d,xjds);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_5d,xjds);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_z3,xjexzx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_z6,xjexzx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_2r,xjexrxds);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_3r,xjexrxds);
			
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_1d,xjfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_2d,xjerxfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_3d,xjerxfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_4d,xjerxfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_5d,xjerxfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_2r,xjexrxfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_3r,xjexrxfs);
			
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_other_2z,xjexzx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_other_z3,xjexzx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_other_z6,xjexzx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_other_dd,xjdxdx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_other_5t,xjwxtx);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_qw2d,xjqw);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_dan_qj2d,xjqj);
			
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_qw2d,xjqwfs);
			playTypeToAdvanceConverterMap.put(PlayType.xjssc_fu_qj2d,xjqjfs);
			
			
			
	}
	
	
	public static String getPlayType(Ticket ticket){
		if(PlayType.gxk3_ertong_fu.getValue()==ticket.getPlayType()||PlayType.jiangsu_ertong_fu.getValue()==ticket.getPlayType()||PlayType.anhui_ertong_fu.getValue()==ticket.getPlayType()){
			if(ticket.getContent().split("\\-")[1].contains(",")){
				return "502";
			}else{
				return "501";
			}
		}else if(PlayType.cqssc_other_2z.getValue()==ticket.getPlayType()){
			if(ticket.getContent().split("\\-")[1].replace("^","").replace(",","").length()>2){
				return "204";
			}else{
				return "203";
			}
		}
		return playTypeMap.get(ticket.getPlayType());
	}

	
}
