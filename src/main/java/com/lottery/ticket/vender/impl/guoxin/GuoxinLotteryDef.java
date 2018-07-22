package com.lottery.ticket.vender.impl.guoxin;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

public class GuoxinLotteryDef {
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
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		
		//彩种转换
		
		lotteryTypeMap.put(LotteryType.SSQ, "001");
		lotteryTypeMap.put(LotteryType.F3D, "002");
		lotteryTypeMap.put(LotteryType.QLC, "003");
		
		
		
		
		//玩法转换
		//双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "01");
		playTypeMap.put(PlayType.ssq_fs.getValue(), "02");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "03");
		//七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "01");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "02");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "03");
		//3D
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "01");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "01");
		
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "02");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "02");
		
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "04");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "04");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "04");
		
		
		//双色球
		toLotteryTypeMap.put(PlayType.ssq_dan.getValue(), "01");
		toLotteryTypeMap.put(PlayType.ssq_fs.getValue(), "01");
		toLotteryTypeMap.put(PlayType.ssq_dt.getValue(), "01");
		//七乐彩
		toLotteryTypeMap.put(PlayType.qlc_dan.getValue(), "01");
		toLotteryTypeMap.put(PlayType.qlc_fs.getValue(), "01");
		toLotteryTypeMap.put(PlayType.qlc_dt.getValue(), "01");
		//3D
		toLotteryTypeMap.put(PlayType.d3_zhi_dan.getValue(), "01");
		toLotteryTypeMap.put(PlayType.d3_zhi_fs.getValue(), "01");
		toLotteryTypeMap.put(PlayType.d3_zhi_hz.getValue(), "01");
		
		toLotteryTypeMap.put(PlayType.d3_z3_dan.getValue(), "02");
		toLotteryTypeMap.put(PlayType.d3_z3_fs.getValue(), "02");
		toLotteryTypeMap.put(PlayType.d3_z3_hz.getValue(), "02");
		
		toLotteryTypeMap.put(PlayType.d3_z6_dan.getValue(), "03");
		toLotteryTypeMap.put(PlayType.d3_z6_fs.getValue(), "03");
		toLotteryTypeMap.put(PlayType.d3_z6_hz.getValue(), "03");
		
	
		// 七乐彩 单式
		IVenderTicketConverter qlc_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					String content = oneCode[j];
					ticketContent.append(content);
					if(j!=oneCode.length-1){
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};
		// 七乐彩 复式 胆拖
		IVenderTicketConverter qlc_fd = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				return contentStr;
			}
		};

		// 双色球 大乐透 单式
		IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuffer ticketContent = new StringBuffer();
				String[] oneCode = contentStr.split("\\^");
				for (int j=0; j < oneCode.length; j++) {
					String content = oneCode[j];
					ticketContent.append(content);
					if(j!=oneCode.length-1){
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};
		// 双色球 大乐透 复式 
		IVenderTicketConverter ssq_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				return contentStr;
			}
		};
		// 双色球 大乐透 复式 胆拖
		IVenderTicketConverter ssq_fd = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				return contentStr;
			}
		};

		// 3d 单式
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
							ticketContent.append("|");
						}
					}
					if (j != oneCode.length - 1) {
						ticketContent.append(";");
					}
				}
				return ticketContent.toString();
			}
		};

		// 3D 直选复式
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
						if (i != content.length - 1) {
							ticketContent.append(",");
						}
					}
					if (j != oneBet.length - 1) {
						ticketContent.append("|");
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
		
		// 3d 组三单式
		IVenderTicketConverter d3_zsds = new IVenderTicketConverter() {
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
			
			
			playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
			playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssq_fs);
			playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssq_fd);

			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, d3_ds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, d3_zsds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, d3_zsds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, d3_zxfs);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, d3_zsds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, d3_zsds);
			playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, d3_hz);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, d3_hz);
			playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, d3_hz);

			playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, qlc_ds);
			playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, qlc_fd);

			
	 		

			
	}


	
}
