package com.lottery.lottype.hljkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.util.MathUtils;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;
import com.lottery.lottype.SplitedLot;

public abstract class Hljkl10X implements LotPlayType {
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return betcode.equals(limitedCodes.getContent());
	}

	protected int lotterytype = LotteryType.HLJKL10.value;
	
	protected static String SS1 = "0[1-9]|1[0-8]";
	protected static String SH1 = "(19)|(20)";
	protected static String SR2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1}";
	protected static String SR3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){2}";
	protected static String SR4 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){3}";
	protected static String SR5 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){4}";
	protected static String SQ2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1}";
	protected static String SQ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){2}";
	protected static String SZ2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1}";
	protected static String SZ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){2}";

	protected static String MS1 = "(0[1-9]|1[0-8])([,](0[1-9]|1[0-8])){1,17}";
	protected static String MR2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){2,19}";
	protected static String MR3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){3,19}";
	protected static String MR4 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){4,19}";
	protected static String MR5 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){5,19}";
	protected static String MQ2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){2,19}";
	protected static String MQ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){3,19}";
	protected static String MZ2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){2,19}";
	protected static String MZ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){3,19}";
	
	
	protected static String DR2 = "(0[1-9]|1[0-9]|20){1}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1,18}";
	protected static String DR3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,1}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1,18}";
	protected static String DR4 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,2}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1,18}";
	protected static String DR5 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,3}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1,18}";
	protected static String DZ2 = "(0[1-9]|1[0-9]|20){1}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1,18}";
	protected static String DZ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,1}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){1,18}";
	protected static String DQ2 = "(0[1-9]|1[0-9]|20){1}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,18}";
	protected static String DQ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,1}[#](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,18}";
	
	protected static String PQ2 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,19}[-](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,19}";
	protected static String PQ3 = "(0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,19}[-](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,19}[-](0[1-9]|1[0-9]|20)([,](0[1-9]|1[0-9]|20)){0,19}";

	/**
	 * 注码每两位拆分一次
	 * 
	 * @param code
	 * @return
	 */
	protected String[] toCodeArray(String code) {
		return code.split(",");
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

	/**
	 * 判断betcode是否有重复注码
	 * 
	 * @param betcode
	 * @return
	 */
	protected boolean isBetcodeDuplication(String betcode) {
		return isArrayDuplication(toCodeArray(betcode));
	}

	/**
	 * 验证注码是否合法(注码中不带-)：符合正则，不重复
	 * 
	 * @param betcode
	 * @param regex
	 * @return
	 */
	protected boolean validateBetcode(String betcode, String regex) {
		return betcode.matches(regex) && (!isBetcodeDuplication(betcode));
	}
	
	
	/**
	 * 验证注码是否合法(注码中带-)：符合正则，不重复
	 * @param betcode
	 * @param regex
	 * @return
	 */
	protected boolean validateBetcodeContainsLine(String betcode,String regex) {
		return betcode.matches(regex)&&(!isBetcodeDuplication(betcode.replace("#", ",")));
	}

	/**
	 * 计算code中命中多少个wincode
	 * 
	 * @param code
	 * @param wincode
	 * @return
	 */
	protected int totalHits(String code, String wincode) {
		String[] codeArray = toCodeArray(code);
		List<String> wincodeArray = Arrays.asList(toCodeArray(wincode));
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
	
	
	private int[] toIntArray(String code) {
		String[] codesStr = toCodeArray(code);
		int[] codes = new int[codesStr.length];
		for(int i=1;i<codesStr.length;i++) {
			codes[i] = Integer.parseInt(codesStr[i]);
		}
		return codes;
	}
	
	
	protected List<int[]> getCombineList(String code,int select) {
		int[] codeArray = toIntArray(code);
		List<int[]> combineList = new ArrayList<int[]>();
		MathUtils.combine(codeArray, codeArray.length, select, new int[select], select, combineList);
		return combineList;
	}
	
	
	protected List<SplitedLot> transformSingle5(String betcode,int lotmulti,PlayType type) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode(type.getValue()+"-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode(type.getValue()+"-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}
	
	
	
	private String getSortBetcode(String betcode) {
		String[] codes = betcode.split(",");
		Arrays.sort(codes);
		StringBuilder sortcode = new StringBuilder();
		for(String code:codes) {
			sortcode.append(code).append(",");
		}
		if(sortcode.toString().endsWith(",")) {
			sortcode = sortcode.deleteCharAt(sortcode.length()-1);
		}
		return sortcode.toString();
	}
	
	
	public String getRSortCode(String betcode) {
		String type = betcode.split("\\-")[0];
		String realcode = betcode.split("\\-")[1];
		StringBuilder sortcode = new StringBuilder(type+"-");
		for(String code:realcode.split("\\^")) {
			sortcode.append(getSortBetcode(code)).append("^");
		}
		return sortcode.toString();
	}
	
	
	public String getMSortCode(String betcode) {
		return getRSortCode(betcode);
	}
	
	
	public String getDSortCode(String betcode) {
		String type = betcode.split("\\-")[0];
		String realcode = betcode.split("\\-")[1].replace("^", "");
		StringBuilder sortcode = new StringBuilder(type+"-");
		sortcode.append(getSortBetcode(realcode.split("\\#")[0])).append("#").append(getSortBetcode(realcode.split("\\#")[1])).append("^");
		return sortcode.toString();
	}

}
