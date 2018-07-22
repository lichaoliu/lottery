package com.lottery.lottype.qxc;

import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class QxcX implements LotPlayType {
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}
	
	protected int lotterytype = LotteryType.QXC.value;

	protected static String qxc_01 = "(200401)[-]((0[0-9]){1}([,](0[0-9])){6}[\\^])+";

	protected static String qxc_02 = "(200402)[-](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[|](0[0-9]){1}([,](0[0-9])){0,9}[\\^]";

	protected void check2delete(StringBuilder builder) {
		if (!StringUtil.isEmpty(builder.toString())) {
			if (builder.toString().endsWith(",")) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
	}

	public boolean checkDuplicate(String code) {
		// 将字符串拆分为数组
		int[] codes = splitCodeToInt(code);
		// 判断是否重复

		if (checkDuplicate(codes) == false) {
			return false;
		}
		return true;
	}

	private int[] splitCodeToInt(String code) {
		int[] codes = new int[code.split(",").length];
		String[] codeStrs = code.split(",");
		for (int i = 0, j = codeStrs.length; i < j; i++) {
			codes[i] = Integer.parseInt(codeStrs[i]);
		}
		return codes;
	}

	/**
	 * 查重复
	 * 
	 * @param zhuma
	 * @return
	 */
	private boolean checkDuplicate(int[] zhuma) {
		for (int i = 0, i1 = zhuma.length; i < i1; i++) {
			for (int j = i + 1; j < i1; j++) {
				if (zhuma[i] == (zhuma[j]))
					return false;
			}
		}
		return true;
	}

	protected String getPrizeLevel(String[] one,
			String[] two, String[] three, String[] four, String[] five,
			String[] six,String[] seven, String[] win, int oneAmount) {
		StringBuilder sb = new StringBuilder();
		
		for (int i = 0; i < one.length; i++) {
			for (int j = 0; j < two.length; j++) {
				for (int k = 0; k < three.length; k++) {
					for (int k2 = 0; k2 < four.length; k2++) {
						for (int l = 0; l < five.length; l++) {
							for (int l2 = 0; l2 < six.length; l2++) {
								for (int m = 0; m < seven.length; m++) {
									String[] betc = { one[i], two[j], three[k],four[k2], five[l], six[l2],seven[m] };
									int count = 0;
									int max = 0;
									for (int n = 0; n < betc.length; n++) {
										if (betc[n].equals(win[n])) {
											count++;
											if (max < count) {
												max = count;
											}
										} else {
											count = 0;
										}
									}
									if(max>=2) {
										
										int prize = 7-max+1;
										
										switch (prize) {
										case 1:
											sb.append(LotteryDrawPrizeAwarder.QXC_1.value).append(",");
											break;
										case 2:
											sb.append(LotteryDrawPrizeAwarder.QXC_2.value).append(",");
											break;
										case 3:
											sb.append(LotteryDrawPrizeAwarder.QXC_3.value).append(",");
											break;
										case 4:
											sb.append(LotteryDrawPrizeAwarder.QXC_4.value).append(",");
											break;
										case 5:
											sb.append(LotteryDrawPrizeAwarder.QXC_5.value).append(",");
											break;
										case 6:
											sb.append(LotteryDrawPrizeAwarder.QXC_6.value).append(",");
											break;
										default:
											break;
										}
									}
								}
							}
						}
					}
				}
			}
		}
		if(sb.toString().endsWith(",")) {
			sb = sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}
	
	
	protected String transferWincode(String wincode) {
		String[] wins = wincode.split(",");
		wincode = "0"+wins[0]+","+"0"+wins[1]+","+"0"+wins[2]+","+"0"+wins[3]+","+"0"+wins[4]+","+"0"+wins[5]+","+"0"+wins[6];
		return wincode;
	}
}
