package com.lottery.lottype.gxkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.MathUtils;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;
import com.lottery.lottype.SplitedLot;

public abstract class Gxkl10X implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}

	protected int lotterytype = LotteryType.GXKL10.value;
	
	protected static String SHY1 = "(110501)[-]((0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHY2 = "(110502)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHY3 = "(110503)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHY4 = "(110504)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHY5 = "(110505)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHYT = "(110506)[-]((0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHYTX3 = "(110507)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHYTX4 = "(110508)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	protected static String SHYTX5 = "(110509)[-]((0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[,](0[1-9]|1[0-9]|2[0-1])[\\^])+";
	
	protected static String MHY1 = "(110511)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){1,20}[\\^]";
	protected static String MHY2 = "(110512)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){2,20}[\\^]";
	protected static String MHY3 = "(110513)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){3,20}[\\^]";
	protected static String MHY4 = "(110514)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){4,20}[\\^]";
	protected static String MHY5 = "(110515)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){5,17}[\\^]";
	protected static String MHYT = "(110516)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){1,20}[\\^]";
	protected static String MHYTX3 = "(110517)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){3,20}[\\^]";
	protected static String MHYTX4 = "(110518)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){4,20}[\\^]";
	protected static String MHYTX5 = "(110519)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){5,17}[\\^]";
	
	protected static String DHY2 = "(110522)[-](0[1-9]|1[0-9]|2[0-1])[#](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){1,19}[\\^]";
	protected static String DHY3 = "(110523)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){0,1}[#](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){1,19}[\\^]";
	protected static String DHY4 = "(110524)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){0,2}[#](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){1,19}[\\^]";
	protected static String DHY5 = "(110525)[-](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){0,3}[#](0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){1,19}[\\^]";
	
	protected static String SHYBX3b4 = "(110531)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){3}[\\^])+";
	protected static String SHYBX3b5 = "(110532)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){4}[\\^])+";
	protected static String SHYBX3b6 = "(110533)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){5}[\\^])+";
	
	protected static String SHYBX4b5 = "(110541)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){4}[\\^])+";
	protected static String SHYBX4b6 = "(110542)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){5}[\\^])+";
	protected static String SHYBX4b7 = "(110543)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){6}[\\^])+";
	protected static String SHYBX4b8 = "(110544)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){7}[\\^])+";
	protected static String SHYBX4b9 = "(110545)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){8}[\\^])+";
	protected static String SHYBX4b10 = "(110546)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){9}[\\^])+";
	
	protected static String SHYBX5b6 = "(110551)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){5}[\\^])+";
	protected static String SHYBX5b7 = "(110552)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){6}[\\^])+";
	protected static String SHYBX5b8 = "(110553)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){7}[\\^])+";
	protected static String SHYBX5b9 = "(110554)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){8}[\\^])+";
	protected static String SHYBX5b10 = "(110555)[-]((0[1-9]|1[0-9]|2[0-1])([,](0[1-9]|1[0-9]|2[0-1])){9}[\\^])+";
	
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
	 * 计算code中命中多少个wincode
	 * 
	 * @param code
	 * @param wincode
	 * @return
	 */
	protected int totalHits(String code, String wincode) {
		String[] codeArray = code.split(",");
		List<String> wincodeArray = Arrays.asList(wincode.split(","));
		int total = 0;

		for (String codeOne : codeArray) {
			if (wincodeArray.contains(codeOne)) {
				total = total + 1;
			}
		}
		return total;
	}
	
	
	/**
	 * 任选复式算奖
	 * 
	 * @param betcode
	 * @param wincode
	 *            任选一传入第一个开奖
	 * @param hit
	 * @param prize
	 *            中奖串
	 * @return
	 */
	protected String caculatePrizeMR(String betcode, String wincode, int hit,
			String prize) {
		StringBuilder sb = new StringBuilder("");
		int totalHits = totalHits(betcode, wincode);

		if (totalHits >= hit) {
			long total = MathUtils.combine(totalHits, hit);

			for (long i = 1; i <= total; i++) {
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
	protected String caculatePrizeDR(String betcode,String wincode,int hit,String prize) {
		StringBuilder sb = new StringBuilder("");
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		int totalDan = totalHits(dan, wincode);
		int totalTuo = totalHits(tuo, wincode);
		long total = 0;
		if(totalDan==dan.split(",").length&&totalTuo>=hit-totalDan) {
			total = MathUtils.combine(totalTuo, hit-totalDan);
		}
		
		for(long i=1;i<=total;i++) {
			sb.append(prize).append(",");
		}
		
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}
}
