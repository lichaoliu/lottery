package com.lottery.lottype.ssq;
/**
 * 双色球拆单
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;

public abstract class SsqX implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}
	
	protected int lotterytype = LotteryType.SSQ.value;
	
	protected static String ssq_01 = "(100101)[-]((0[1-9]|[12][0-9]|3[0-3]){1}([,](0[1-9]|[12][0-9]|3[0-3])){5}[|](0[1-9]|1[0-6])[\\^])+";
	
	protected static String ssq_02 = "(100102)[-](0[1-9]|[12][0-9]|3[0-3]){1}([,](0[1-9]|[12][0-9]|3[0-3])){5,15}[|](0[1-9]|1[0-6])([,](0[1-9]|1[0-6])){0,15}[\\^]";
	
	protected static String ssq_03 = "(100103)[-](0[1-9]|[12][0-9]|3[0-3]){1}([,](0[1-9]|[12][0-9]|3[0-3])){0,4}[#](0[1-9]|[12][0-9]|3[0-3]){1}([,](0[1-9]|[12][0-9]|3[0-3])){1,15}[|](0[1-9]|1[0-6])([,](0[1-9]|1[0-6])){0,15}[\\^]";
	/**
	 * 双色球解析级别
	 * 
	 * @param redCode
	 * @param blueCode
	 * @param winRedCode
	 * @param winBlueCode
	 * @return
	 */
	protected String caculatePrize(String redCode, String blueCode,
			String winRedCode, String winBlueCode) {
		// 得到投注红球数组
		String[] codes = redCode.split(",");
		// 得到中奖球数组
		String[] winCodes = winRedCode.split(",");
		// 遍历中红球个数
		int i = 0;
		for (String code : codes) {
			for (String winCode : winCodes) {
				if (code.equals(winCode)) {
					i = i + 1;
				}
			}
		}
		// 判断是否中了蓝球
		boolean blueFlag = false;
		boolean winFlag = true;
		if (blueCode.equals(winBlueCode)) {
			blueFlag = true;
		}
		String awardlevel = "0";
		// 分别判断中蓝球和不中蓝球的奖级
		if (blueFlag) {
			switch (i) {
			case 3:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_5.value;
				break;
			case 4:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_4.value;
				break;
			case 5:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_3.value;
				break;
			case 6:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_1.value;
				break;
			default:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_6.value;
			}
		} else {
			switch (i) {
			case 4:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_5.value;
				break;
			case 5:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_4.value;
				break;
			case 6:
				awardlevel = LotteryDrawPrizeAwarder.SSQ_2.value;
				break;
			default:
				winFlag = false;
			}
		}
		if (winFlag)
			return awardlevel;
		return null;
	}
	
	/**
	 * 分割注码
	 * @param code
	 * @return
	 */
	protected String[] splitBoll(String code) {
		int length = code.length();
		int num = length / 2;
		String[] result = new String[num];
		int index = 0;
		for(int i = 0; i < length; i = i + 2) {
			result[index] = code.substring(i, i + 2);
			index = index + 1;
		}
		return result;
	}
	
	/**
	 * 解析复式注码 注：去掉重复的
	 * @param a
	 * @param m
	 * @return
	 */
	protected List<String> combine(String code, int m) {
		String[] a = splitBoll(code);
		Arrays.sort(a);
		int n = a.length;
		if (m > n) {
			return null;
		}
		List<String> result = new ArrayList<String>();
		int[] bs = new int[n];
		for (int i = 0; i < n; i++) {
			bs[i] = 0;
		}
		// 初始化
		for (int i = 0; i < m; i++) {
			bs[i] = 1;
		}
		boolean flag = true;
		boolean tempFlag = false;
		int pos = 0;
		int sum = 0;
		// 首先找到第一个10组合，然后变成01，同时将左边所有的1移动到数组的最左边
		do {
			sum = 0;
			pos = 0;
			tempFlag = true;
			print(bs, a, m, result);
			for (int i = 0; i < n - 1; i++) {
				if (bs[i] == 1 && bs[i + 1] == 0) {
					bs[i] = 0;
					bs[i + 1] = 1;
					pos = i;
					break;
				}
			}
			// 将左边的1全部移动到数组的最左边
			for (int i = 0; i < pos; i++) {
				if (bs[i] == 1) {
					sum++;
				}
			}
			for (int i = 0; i < pos; i++) {
				if (i < sum) {
					bs[i] = 1;
				} else {
					bs[i] = 0;
				}
			}
			// 检查是否所有的1都移动到了最右边
			for (int i = n - m; i < n; i++) {
				if (bs[i] == 0) {
					tempFlag = false;
					break;
				}
			}
			if (tempFlag == false) {
				flag = true;
			} else {
				flag = false;
			}
		} while (flag);
		if(n != m) {
			print(bs, a, m, result);
		}
		return result;
	}

	private static void print(int[] bs, String[] a, int m, List<String> list) {
		String[] result = new String[m];
		int index = 0;
		for (int i = 0; i < bs.length; i++) {
			if (bs[i] == 1) {
				result[index] = a[i];
				index = index + 1;
			}
		}
		Arrays.sort(result);
		StringBuilder sb = new StringBuilder();
		for(String s : result) {
			sb.append(s);
		}
		boolean flag = false;
		for(String s : list) {
			if(s.equals(sb.toString())) {
				flag = true;
				break;
			}
		}
		if(!flag) {
			list.add(sb.toString());
		}
	}
	
	protected void putAwardlevel(String awardlevel, Map<String, Integer> map) {
		if(!StringUtil.isEmpty(awardlevel)) {
			Integer count = map.get(awardlevel);
			count = null == count ? 0 : count;
			map.put(awardlevel, count + 1);
		}
	}
	
	protected void check2delete(StringBuilder builder) {
		if(!StringUtil.isEmpty(builder.toString())) {
			if(builder.toString().endsWith(",")) {
				builder.deleteCharAt(builder.length() - 1);
			}
		}
	}
	
	

	
	
	public static boolean checkDuplicate(String code) {
		//将字符串拆分为数组
		int[] codes = splitCodeToInt(code);
		//判断是否重复
		
		if(checkDuplicate(codes)==false) {
			return false;
		}

		return true;
	}
	
	/**
	 * 按照逗号分割
	 * @param code
	 * @return
	 */
	public static int[] splitCodeToInt(String code) {
		int[] codes = new int[code.split(",").length];
		String[] codeStrs = code.split(",");
		for(int i=0,j=codeStrs.length;i<j;i++) {
			codes[i] = Integer.parseInt(codeStrs[i]);
		}
		return codes;
	}
	
	/**
	 * 查重复
	 * @param zhuma
	 * @return
	 */
	public static boolean checkDuplicate(int[] zhuma) {
		for(int i = 0,i1=zhuma.length;i < i1; i++) {
			for(int j = i+1; j < i1;j++) {
				if(zhuma[i]==(zhuma[j]))
					return false;
			}
		}
		return true;
	}
	
	/**
	 * 查排序
	 * @param codes
	 * @return
	 */
	public static boolean checkSort(int[] codes) {
		int[] codes2 = codes.clone();
		Arrays.sort(codes2);
		for(int i = 0,j=codes.length;i<j;i++) {
			if(codes[i]!=codes2[i]) {
				return false;
			}
		}
		return true;
	}
	

	
	/**
	 * 按照指定长度切割字符串,逗号分割
	 * @param code 逗号分割
	 * @param select 长度
	 * @return
	 */
	public static List<String> getSelectCodes(String code,int select) {
		List<String> selectCode = new ArrayList<String>();
		if(select>=code.split(",").length) {
			selectCode.add(code);
		}else {
			String[] codes = code.split(",");
			StringBuilder sb = new StringBuilder();
			for(int i=0;i<codes.length;i++) {
				sb.append(codes[i]).append(",");
				if((i+1)%select==0) {
					selectCode.add(sb.deleteCharAt(sb.toString().length()-1).toString());
					sb.delete(0, sb.toString().length());
				}
			}
			
			if(!StringUtil.isEmpty(sb.toString())) {
				selectCode.add(sb.deleteCharAt(sb.toString().length()-1).toString());
			}
		}
		
		return selectCode;
	}
	
	
	public String joinBetcode(List<String> bets) {
		StringBuilder sb = new StringBuilder();
		
		for(String bet:bets) {
			sb.append(bet).append(",");
		}
		
		check2delete(sb);
		return sb.toString();
	}
	
	
	/**
	 * 逗号分割的注码从小到大排序
	 * @param code
	 * @return
	 */
	private String sortCode(String code) {
		String[] codes = code.split(",");
		Arrays.sort(codes);
		StringBuilder realcode = new StringBuilder();
		
		for(String c:codes) {
			realcode.append(c).append(",");
		}
		check2delete(realcode);
		return realcode.toString();
	}
	
	
	//100101-01,02,03,04,05,06|07^01,02,03,04,05,06|07^01,02,03,04,05,06|07^
	protected String getSortCode01(String betcode) {
		String type = betcode.split("\\-")[0];
		StringBuilder realcode = new StringBuilder(type+"-");
		for(String code:betcode.split("\\-")[1].split("\\^")) {
			realcode.append(sortCode(code.split("\\|")[0])).append("|").append(sortCode(code.split("\\|")[1])).append("^");
		}
		return realcode.toString();
	}
	
	
	//100102-01,02,03,04,05,06,07,08,09,10|07,08,09,10^
	protected String getSortCode02(String betcode) {
		StringBuilder realcode = new StringBuilder(betcode.split("\\-")[0]+"-");
		String red = betcode.split("\\-")[1].split("\\|")[0];
		String blue = betcode.split("\\-")[1].split("\\|")[1].replace("^", "");
		realcode.append(sortCode(red)).append("|").append(sortCode(blue)).append("^");
		return realcode.toString();
	}
	
	
	//100103-01#06,07,08,09,10,11,12,13,14,15,16,17,18,19,20,21|01,02,03^
	protected String getSortCode03(String betcode) {
		StringBuilder realcode = new StringBuilder(betcode.split("\\-")[0]+"-");
		String reddan = betcode.split("\\-")[1].split("\\|")[0].split("\\#")[0];
		String redtuo = betcode.split("\\-")[1].split("\\|")[0].split("\\#")[1];
		String blue = betcode.split("\\-")[1].split("\\|")[1].replace("^", "");
		realcode.append(sortCode(reddan)).append("#").append(sortCode(redtuo)).append("|").append(sortCode(blue)).append("^");
		return realcode.toString();
		
	}
	
	
}
