package com.lottery.lottype.sdklpk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;
import com.lottery.lottype.SplitedLot;

public abstract class Sdpk3X implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}

	protected int lotterytype = LotteryType.SD_KLPK3.value;
	
	protected static String SR1 = "(200901)[-](((0[1-9])|(1[0-3]))[\\^])+";
	protected static String SR2 = "(200902)[-](((0[1-9])|(1[0-3]))[,]((0[1-9])|(1[0-3]))[\\^])+";
	protected static String SR3 = "(200903)[-](((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){2}[\\^])+";
	protected static String SR4 = "(200904)[-](((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){3}[\\^])+";
	protected static String SR5 = "(200905)[-](((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){4}[\\^])+";
	protected static String SR6 = "(200906)[-](((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){5}[\\^])+";
	
	protected static String STH = "(200931)[-]((0[1-4])[\\^])+";
	protected static String SHS = "(200932)[-]((0[1-4])[\\^])+";
	protected static String SSZ = "(200933)[-](((0[1-9])|(1[0-2]))[\\^])+";
	protected static String SBZ = "(200934)[-](((0[1-9])|(1[0-3]))[\\^])+";
	protected static String SDZ = "(200935)[-](((0[1-9])|(1[0-3]))[\\^])+";
	
	
	protected static String MR1 = "(200911)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,12}[\\^]";
	protected static String MR2 = "(200912)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){2,12}[\\^]";
	protected static String MR3 = "(200913)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){3,12}[\\^]";
	protected static String MR4 = "(200914)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){4,12}[\\^]";
	protected static String MR5 = "(200915)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){5,12}[\\^]";
	protected static String MR6 = "(200916)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){6,12}[\\^]";
	
	protected static String MTH = "(200941)[-](0[1-4])([,](0[1-4])){1,3}[\\^]";
	protected static String MHS = "(200942)[-](0[1-4])([,](0[1-4])){1,3}[\\^]";
	protected static String MSZ = "(200943)[-]((0[1-9])|(1[0-2]))([,]((0[1-9])|(1[0-2]))){1,11}[\\^]";
	protected static String MBZ = "(200944)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,12}[\\^]";
	protected static String MDZ = "(200945)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,12}[\\^]";
	protected static String MBX = "(200946)[-]((0[7-9])|(1[0-1]))([,]((0[7-9])|(1[0-1]))){0,4}[\\^]";
	
	
	
	
	protected static String DR2 = "(200922)[-]((0[1-9])|(1[0-3]))[#]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,11}[\\^]";
	protected static String DR3 = "(200923)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){0,1}[#]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,11}[\\^]";
	protected static String DR4 = "(200924)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){0,2}[#]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,11}[\\^]";
	protected static String DR5 = "(200925)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){0,3}[#]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,11}[\\^]";
	protected static String DR6 = "(200926)[-]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){0,4}[#]((0[1-9])|(1[0-3]))([,]((0[1-9])|(1[0-3]))){1,11}[\\^]";
	
	
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
	
	
	protected List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].replace("^", "").split(",");
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
	
	
	protected List<SplitedLot> transformM(String betcode,int lotmulti,String splay,String mplay) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		
		if(betcodes.length==1) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode(splay+"-"+betcodes[0]+"^");
			slot.setLotMulti(lotmulti);
			list.add(slot);
		}else {
			SplitedLot slot = new SplitedLot(lotterytype);
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<betcodes.length;i++) {
				sb.append(betcodes[i]).append(",");
			}
			sb = sb.deleteCharAt(sb.length()-1);
			slot.setBetcode(mplay+"-"+sb.toString()+"^");
			slot.setLotMulti(lotmulti);
			list.add(slot);
		}
		
		return list;
	}
	
	
	protected boolean isBaozi(String wincode) {
		String[] wins = new String[]{wincode.split(",")[0].substring(1),wincode.split(",")[1].substring(1),wincode.split(",")[2].substring(1)};
		if(wins[0].equals(wins[1])&&wins[1].equals(wins[2])) {
			return true;
		}
		return false;
	}
	
	protected boolean isDuizi(String wincode) {
		String[] wins = new String[]{wincode.split(",")[0].substring(1),wincode.split(",")[1].substring(1),wincode.split(",")[2].substring(1)};
		Arrays.sort(wins);
		if(wins[0].equals(wins[1])&&(!wins[1].equals(wins[2]))) {
			return true;
		}else if (wins[1].equals(wins[2])&&(!wins[1].equals(wins[0]))) {
			return true;
		}
		return false;
	}
	
	
	/**
	 * 是否连号，三个号码
	 * @param wins
	 * @return
	 */
	protected boolean isShunzi(String wincode) {
		String[] wins = new String[]{wincode.split(",")[0].substring(1),wincode.split(",")[1].substring(1),wincode.split(",")[2].substring(1)};
		int[] winInts = new int[wins.length];
		for(int i=0;i<wins.length;i++) {
			winInts[i] = Integer.parseInt(wins[i]);
		}
		Arrays.sort(winInts);
		if(winInts[0]+1==winInts[1]&&winInts[1]+1==winInts[2]) {
			return true;
		}else if(winInts[0]==1&&winInts[1]==12&&winInts[2]==13) {
			return true;
		}
		return false;
	}
	
	
	protected boolean isTonghua(String[] wins) {
		if(wins[0].equals(wins[1])&&wins[1].equals(wins[2])) {
			return true;
		}
		return false;
	}

}
