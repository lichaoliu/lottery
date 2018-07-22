package com.lottery.lottype.xj11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.MathUtils;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;
import com.lottery.lottype.SplitedLot;

public abstract class Xj11x5X implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}

	protected int lotterytype = LotteryType.XJ_11X5.value;
	
	protected static String SR2 = "(200802)[-](((0[1-9])|10|11)[,]((0[1-9])|10|11)[\\^])+";
	protected static String SR3 = "(200803)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){2}[\\^])+";
	protected static String SR4 = "(200804)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){3}[\\^])+";
	protected static String SR5 = "(200805)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){4}[\\^])+";
	protected static String SR6 = "(200806)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){5}[\\^])+";
	protected static String SR7 = "(200807)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){6}[\\^])+";
	protected static String SR8 = "(200808)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){7}[\\^])+";
	
	protected static String SQ1 = "(200831)[-](((0[1-9])|10|11)[\\^])+";
	protected static String SQ2 = "(200832)[-](((0[1-9])|10|11)[,]((0[1-9])|10|11)[\\^])+";
	protected static String SQ3 = "(200833)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){2}[\\^])+";
	
	protected static String SZ2 = "(200834)[-](((0[1-9])|10|11)[,]((0[1-9])|10|11)[\\^])+";
	protected static String SZ3 = "(200835)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){2}[\\^])+";
	
	
	
	protected static String MR2 = "(200812)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){2,10}[\\^]";
	protected static String MR3 = "(200813)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){3,10}[\\^]";
	protected static String MR4 = "(200814)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){4,10}[\\^]";
	protected static String MR5 = "(200815)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){5,10}[\\^]";
	protected static String MR6 = "(200816)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){6,10}[\\^]";
	protected static String MR7 = "(200817)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){7,10}[\\^]";
	protected static String MR8 = "(200818)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){8,10}[\\^]";
	
	protected static String MQ1 = "(200841)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,10}[\\^]";
	protected static String MQ2 = "(200842)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,10}[;]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,10}[\\^]";
	protected static String MQ3 = "(200843)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,10}[;]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,10}[;]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,10}[\\^]";
	
	protected static String MZ2 = "(200844)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){2,10}[\\^]";
	protected static String MZ3 = "(200845)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){3,10}[\\^]";
	
	
	protected static String DR2 = "(200822)[-]((0[1-9])|10|11)[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DR3 = "(200823)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,1}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DR4 = "(200824)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,2}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DR5 = "(200825)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,3}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DR6 = "(200826)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,4}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DR7 = "(200827)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,5}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DR8 = "(200828)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,6}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	
	protected static String DZ2 = "(200854)[-]((0[1-9])|10|11)[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	protected static String DZ3 = "(200855)[-]((0[1-9])|10|11)([,]((0[1-9])|10|11)){0,1}[#]((0[1-9])|10|11)([,]((0[1-9])|10|11)){1,9}[\\^]";
	
	protected static String SL3 = "(200863)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){2}[\\^])+";
	protected static String SL4 = "(200864)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){3}[\\^])+";
	protected static String SL5 = "(200865)[-](((0[1-9])|10|11)([,]((0[1-9])|10|11)){4}[\\^])+";
	
	protected boolean isBetcodeDuplication(String betcode) {
		return isArrayDuplication(betcode.split(","));
	}
	
	/**
	 * 比较array里是否有重复
	 * 
	 * @param array
	 * @return
	 */
	protected boolean isArrayDuplication(String[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			for (int j = i + 1; j < array.length; j++) {
				if (array[i].equals(array[j])) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	protected List<SplitedLot> transformone(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		String playtype = betcode.split("-")[0];
		for(int i=0;i<betcodes.length;i++) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode(playtype+"-"+betcodes[i]+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		return list;
	}
	
	
	protected List<SplitedLot> transformfive(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		String playtype = betcode.split("-")[0];
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode(playtype+"-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode(playtype+"-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}
	
	
	/**
	 * 任选单式奖金计算
	 * @param betcode 注码
	 * @param wincode 任选一传入第一个开奖
	 * @param hit 要中的个数
	 * @param prize 中奖串
	 * @return
	 */
	protected String caculatePrizeRS(String betcode,String wincode,int hit,String prize) {
		betcode = betcode.split("\\-")[1];
		StringBuilder sb = new StringBuilder("");
		for(String code:betcode.split("\\^")) {
			if(hit==totalHits(code,wincode)) {
				sb.append(prize).append(",");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	
	/**
	 * 任选复式算奖
	 * @param betcode
	 * @param wincode 任选一传入第一个开奖
	 * @param hit
	 * @param prize 中奖串
	 * @return
	 */
	protected String caculatePrizeMS(String betcode,String wincode,int hit,String prize,int type) {
		betcode = betcode.split("\\-")[1].replace("^", "");
		StringBuilder sb = new StringBuilder("");
		int totalHits = totalHits(betcode, wincode);
		
		if(totalHits>=hit) {
			long total = 0;
			if(type<=5) {
				total = MathUtils.combine(totalHits, hit);
			}else {
				total = MathUtils.combine(betcode.split(",").length-totalHits, type-5);
			}
			for(long i=1;i<=total;i++) {
				sb.append(prize).append(",");
			}
			
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	
	/**
	 * 任选胆拖算奖
	 * @param betcode
	 * @param wincode 任选一传入第一个开奖
	 * @param hit
	 * @param prize 中奖串
	 * @param type 玩法
	 * @return
	 */
	protected String caculatePrizeDS(String betcode,String wincode,int hit,String prize,int type) {
		betcode = betcode.split("\\-")[1].replace("^", "");
		StringBuilder sb = new StringBuilder("");
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		int danlength = dan.split(",").length;
		int tuolength = tuo.split(",").length;
		int totalDan = totalHits(dan, wincode);
		int totalTuo = totalHits(tuo, wincode);
		long total = 0;
		if(type<=5) {
			if(totalDan==danlength&&totalTuo>=hit-totalDan) {
				total = MathUtils.combine(totalTuo, hit-totalDan);
			}
		}else {
			if((totalDan+totalTuo>=5)&&(danlength+5-totalDan<=type)) {
				if(totalDan==5) {
					total = 1*MathUtils.combine(tuolength,type-danlength);
				}else {
					total = MathUtils.combine(totalTuo, hit-totalDan)*MathUtils.combine(tuolength-(hit-totalDan), type-danlength-(hit-totalDan));
				}
				
			}
		}
		
		for(long i=1;i<=total;i++) {
			sb.append(prize).append(",");
		}
		
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
	
	
	/**
	 * 计算code中命中多少个wincode
	 * @param code
	 * @param wincode
	 * @return
	 */
	protected int totalHits(String code,String wincode) {
		String[] codeArray = code.split(",");
		List<String> wincodeArray = Arrays.asList(wincode.split(","));
		int total = 0;
		
		for(String codeOne:codeArray) {
			if(wincodeArray.contains(codeOne)) {
				total = total + 1;
			}
		}
		return total;
	}
}
