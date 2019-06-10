package com.lottery.ticket.vender.impl.xmac;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.core.domain.ticket.Ticket;
import com.lottery.ticket.IPhaseConverter;
import com.lottery.ticket.IVenderTicketConverter;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * @author wyliuxiaoyan
 *
 */
public class XmacLotteryDef {
    /** 彩种转换 */
    protected static Map<LotteryType, String> lotteryTypeMap = new HashMap<LotteryType, String>();
    /** 彩种转换 */
    protected static Map<String,LotteryType> toLotteryTypeMap = new HashMap<String,LotteryType>();
    /** 彩期转换*/
    protected static Map<LotteryType, IPhaseConverter> phaseConverterMap = new HashMap<LotteryType, IPhaseConverter>();
    /** 玩法转换*/
    protected static Map<Integer, String> playTypeMap = new HashMap<Integer, String>();
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
		
		phaseConverterMap.put(LotteryType.SSQ, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.QLC, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.F3D, defaultPhaseConverter);
		phaseConverterMap.put(LotteryType.JXSSC, defaultPhaseConverter);
		//彩种转换
		lotteryTypeMap.put(LotteryType.SSQ, "B001");
		lotteryTypeMap.put(LotteryType.F3D, "D3");
		lotteryTypeMap.put(LotteryType.QLC, "QL730");
		lotteryTypeMap.put(LotteryType.JXSSC, "DT5");
		// 双色球
		playTypeMap.put(PlayType.ssq_dan.getValue(), "012");
		playTypeMap.put(PlayType.ssq_dt.getValue(), "017");		
		// 七乐彩
		playTypeMap.put(PlayType.qlc_dan.getValue(), "022");
		playTypeMap.put(PlayType.qlc_fs.getValue(), "023");
		playTypeMap.put(PlayType.qlc_dt.getValue(), "027");		
		
		playTypeMap.put(PlayType.d3_zhi_dan.getValue(), "0301");
		playTypeMap.put(PlayType.d3_z3_dan.getValue(), "0303");
		playTypeMap.put(PlayType.d3_z6_dan.getValue(), "0306");
		playTypeMap.put(PlayType.d3_zhi_fs.getValue(), "0330");
		playTypeMap.put(PlayType.d3_z3_fs.getValue(), "0343");
		playTypeMap.put(PlayType.d3_z6_fs.getValue(), "0346");
		playTypeMap.put(PlayType.d3_zhi_hz.getValue(), "0321");
		playTypeMap.put(PlayType.d3_z3_hz.getValue(), "0323");
		playTypeMap.put(PlayType.d3_z6_hz.getValue(), "0326");
		
		
		playTypeMap.put(PlayType.jxssc_dan_1d.getValue(),"0701");
		playTypeMap.put(PlayType.jxssc_dan_2d.getValue(),"0702");
		playTypeMap.put(PlayType.jxssc_dan_3d.getValue(),"0703");
		playTypeMap.put(PlayType.jxssc_dan_4d.getValue(),"0704");
		playTypeMap.put(PlayType.jxssc_dan_5d.getValue(),"0705");
		playTypeMap.put(PlayType.jxssc_dan_z3.getValue(),"0752");
		playTypeMap.put(PlayType.jxssc_dan_z6.getValue(),"0753");
		playTypeMap.put(PlayType.jxssc_dan_r1.getValue(),"07e1");
		playTypeMap.put(PlayType.jxssc_dan_r2.getValue(),"07e2");
		
		playTypeMap.put(PlayType.jxssc_fu_1d.getValue(),"0731");
		playTypeMap.put(PlayType.jxssc_fu_2d.getValue(),"0732");
		playTypeMap.put(PlayType.jxssc_fu_3d.getValue(),"0733");
		playTypeMap.put(PlayType.jxssc_fu_4d.getValue(),"0734");
		playTypeMap.put(PlayType.jxssc_fu_5d.getValue(),"0735");
		playTypeMap.put(PlayType.jxssc_fu_r1.getValue(),"07e1");
		playTypeMap.put(PlayType.jxssc_fu_r2.getValue(),"07e2");
//		
//		playTypeMap.put(PlayType.jxssc_other_2h(101121,"时时彩二星直选和值",LotteryType.JXSSC),
//		playTypeMap.put(PlayType.jxssc_other_3h(101122,"时时彩三星直选和值",LotteryType.JXSSC),
		playTypeMap.put(PlayType.jxssc_other_z3.getValue(),"0772");
		playTypeMap.put(PlayType.jxssc_other_z6.getValue(),"0773");
		playTypeMap.put(PlayType.jxssc_other_dd.getValue(),"0741");
		playTypeMap.put(PlayType.jxssc_other_5t.getValue(),"0761");
		
		
		
		
		 //双色球
		IVenderTicketConverter ssq_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				StringBuilder strBuilder=new StringBuilder();
				String []strs=contentStr.split("\\^");
				int i=0;
				for(String s:strs){
					String content=s.replace(",", "").replace("|","*");
					strBuilder.append(content);
					if(i!=strs.length-1){
						strBuilder.append("**");
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
				String content=contentStr.replace(",", "").replace("^","").replace("|","*");
		        return content;
		}};
		
		 //双色球胆拖
		IVenderTicketConverter ssqdt = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = ticket.getContent().split("-")[1];
				String content=contentStr.replace(",", "").replace("^","").replace("|","*").replace("#","*");
		        return content;
		}};
		
		// 3d  单式
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
						ticketContent.append("**");
					}
				}
				return ticketContent.toString();
			}
		};	
	      //  3d 和值
		IVenderTicketConverter d3_hz = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				String contentStr = Integer.parseInt(ticket.getContent().split("-")[1].replace(",", "").replace("^",""))+"";
				return contentStr;
			}
		};		
					
	//  3D复试
		IVenderTicketConverter d3_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] contents = contentStr.split(",");
				for (int i = 0; i < contents.length; i++) {
					ticketContent.append(Integer.parseInt(contents[i]));
				}
				return ticketContent.toString();
			}
		};						
				
		//  3D复试
		IVenderTicketConverter d3_fszx = new IVenderTicketConverter() {
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
		
		
		
		
		//时时彩
		IVenderTicketConverter jxssc_ds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append("0").append(content[i]);
					}
					if(j!=oneCode.length-1){
						ticketContent.append("**");
					}
				}
				return ticketContent.toString();
			}
		};	
		
		
		//时时彩
		IVenderTicketConverter jxssc_zx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append("0").append(content[i]);
					}
					if(j!=oneCode.length-1){
						ticketContent.append("**");
					}
				}
				return ticketContent.toString();
			}
		};
		
		//时时彩
		IVenderTicketConverter jxssc_fs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] content = contentStr.split(",");
				for (int i = 0; i < content.length; i++) {
					ticketContent.append("0").append(content[i]);
				}
				return ticketContent.toString();
			}
		};	
		
		//时时彩
		IVenderTicketConverter jxssc_xfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^", "");
				String[] oneCode = contentStr.split("\\;");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append("0").append(content[i]);
					}
					if(j!=oneCode.length-1){
						ticketContent.append("*");
					}
				}
				return ticketContent.toString();
			}
		};			
		
		//时时彩
		IVenderTicketConverter jxssc_dxdx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","").replace(";", "");
				String[] content = contentStr.split(",");
				for (int i = 0; i < content.length; i++) {
					ticketContent.append(content[i].replace("11", "00")
					.replace("12", "01").replace("21", "02").replace("22", "03").replace("55", "04")
					.replace("54", "05").replace("45", "06").replace("44", "07").replace("15", "08")
					.replace("14", "09").replace("51", "10").replace("41", "11").replace("25", "12")
					.replace("24", "13").replace("52", "14").replace("42", "15"));
				}
					
				return ticketContent.toString();
			}
		};	
		
		//时时彩
		IVenderTicketConverter jxssc_tx = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1].replace("^","");
				String[] oneCode = contentStr.split("\\;");
				for (int j = 0; j < oneCode.length; j++) {
					String[] content = oneCode[j].split(",");
					for (int i = 0; i < content.length; i++) {
						ticketContent.append("0").append(content[i]);
					}
					if(j!=oneCode.length-1){
						ticketContent.append("|");
					}
				}
				return ticketContent.toString();
			}
		};
		
		//时时彩
		IVenderTicketConverter jxssc_rxfs = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String []oneCode = ticket.getContent().split("-")[1].split("\\^");
				for (int i = 0; i < oneCode.length; i++) {
					String[] content = oneCode[i].split(",");
					if(!"~".equals(content[4])){
						ticketContent.append("01").append(content[4]);
					}
					if(!"~".equals(content[3])){
						ticketContent.append("02").append(content[3]);
					}
					if(!"~".equals(content[2])){
						ticketContent.append("04").append(content[2]);
					}
					if(!"~".equals(content[1])){
						ticketContent.append("08").append(content[1]);
					}
					if(!"~".equals(content[0])){
						ticketContent.append("16").append(content[0]);
					}
					if(i!=oneCode.length-1){
						ticketContent.append("**");
					}
				  } 
				return ticketContent.toString();
			}
		};
		//时时彩
		IVenderTicketConverter jxssc_rxds = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int i = 0; i < oneCode.length; i++) {
					String[] content = oneCode[i].split(",");
					if(!"~".equals(content[4])){
						ticketContent.append("01").append("0").append(content[4]);
					}
					if(!"~".equals(content[3])){
						ticketContent.append("02").append("0").append(content[3]);
					}
					if(!"~".equals(content[2])){
						ticketContent.append("04").append("0").append(content[2]);
					}
					if(!"~".equals(content[1])){
						ticketContent.append("08").append("0").append(content[1]);
					}
					if(!"~".equals(content[0])){
						ticketContent.append("16").append("0").append(content[0]);
					}
					if(i!=oneCode.length-1){
						ticketContent.append("**");
					}
				  } 
				return ticketContent.toString();
			}
		};
		IVenderTicketConverter jxssc_rxer = new IVenderTicketConverter() {
			@Override
			public String convert(Ticket ticket) {
				StringBuffer ticketContent = new StringBuffer();
				String contentStr = ticket.getContent().split("-")[1];
				String[] oneCode = contentStr.split("\\^");
				for (int i = 0; i < oneCode.length; i++) {
					String[] content = oneCode[i].split(",");
					if(!"~".equals(content[3])&&!"~".equals(content[4])){
						ticketContent.append("03").append("0").append(content[3]).append("0").append(content[4]);
					}
					if(!"~".equals(content[2])&&!"~".equals(content[4])){
						ticketContent.append("05").append("0").append(content[2]).append("0").append(content[4]);
					}
					if(!"~".equals(content[2])&&!"~".equals(content[3])){
						ticketContent.append("06").append("0").append(content[2]).append("0").append(content[3]);
					}
					if(!"~".equals(content[1])&&!"~".equals(content[4])){
						ticketContent.append("09").append("0").append(content[1]).append("0").append(content[4]);
					}
					if(!"~".equals(content[1])&&!"~".equals(content[3])){
						ticketContent.append("10").append("0").append(content[1]).append("0").append(content[3]);
					}
					if(!"~".equals(content[1])&&!"~".equals(content[2])){
						ticketContent.append("12").append("0").append(content[1]).append("0").append(content[2]);
					}
					if(!"~".equals(content[0])&&!"~".equals(content[4])){
						ticketContent.append("17").append("0").append(content[0]).append("0").append(content[4]);
					}
					if(!"~".equals(content[0])&&!"~".equals(content[3])){
						ticketContent.append("18").append("0").append(content[0]).append("0").append(content[3]);
					}
					if(!"~".equals(content[0])&&!"~".equals(content[2])){
						ticketContent.append("20").append("0").append(content[0]).append("0").append(content[2]);
					}
					if(!"~".equals(content[0])&&!"~".equals(content[1])){
						ticketContent.append("24").append("0").append(content[0]).append("0").append(content[1]);
					}
					if(i!=oneCode.length-1){
						ticketContent.append("**");
					}
				  } 
				return ticketContent.toString();
			}
		};
		
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dan, ssq_ds);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_fs, ssqfs);
		playTypeToAdvanceConverterMap.put(PlayType.ssq_dt, ssqdt);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dan, ssq_ds);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_fs, ssqfs);
		playTypeToAdvanceConverterMap.put(PlayType.qlc_dt, ssqdt);
		
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_dan, d3_ds);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_fs, d3_fszx);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_fs, d3_fs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_fs, d3_fs);
		playTypeToAdvanceConverterMap.put(PlayType.d3_zhi_hz, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z3_hz, d3_hz);
		playTypeToAdvanceConverterMap.put(PlayType.d3_z6_hz, d3_hz);
		
		
		
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_1d,jxssc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_2d,jxssc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_3d,jxssc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_4d,jxssc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_5d,jxssc_ds);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_z3,jxssc_zx);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_z6,jxssc_zx);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_r1,jxssc_rxds);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_dan_r2,jxssc_rxer);
		
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_1d,jxssc_xfs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_2d,jxssc_xfs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_3d,jxssc_xfs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_4d,jxssc_xfs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_5d,jxssc_xfs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_r1,jxssc_rxfs);
//		playTypeToAdvanceConverterMap.put(PlayType.jxssc_fu_r2,jxssc_rxer);
//		
//		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_2h(101121,"时时彩二星直选和值",LotteryType.JXSSC),
//		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_3h(101122,"时时彩三星直选和值",LotteryType.JXSSC),
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_2z,jxssc_fs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_z3,jxssc_fs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_z6,jxssc_fs);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_dd,jxssc_dxdx);
		playTypeToAdvanceConverterMap.put(PlayType.jxssc_other_5t,jxssc_tx);
		
		
    }
    
    
}
