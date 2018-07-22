package com.lottery.lottype.zc.sfc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Zucaisfc02 extends ZucaisfcX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		
		String[] bet = betcode.split("-")[1].replace("^", "").split(",");
		String[] win = wincode.split(",");
		int count = 0;
		int prizeCount = 1;
		//未中奖位的长度
		int noPrizeCount = 0;
		for (int i = 0; i < 14; i++) {
			if (!bet[i].contains(win[i])) {
				if (win[i].equals("*")) {
					//*场次 投注次数 中一等奖 二等奖 要乘以*场次次数
					prizeCount *= bet[i].length();
				}else{
					count++;
					//中二等奖 要乘以 未中奖位的长度
					noPrizeCount = bet[i].length();
				}
				if (count == 2) {
					return "";
				}
			}
		}
		if (count == 1) {
			StringBuffer level = new StringBuffer("2");
			for (int i = 1; i < noPrizeCount*prizeCount; i++) {
				level.append(",2");
			}
			return level.toString();
		}else if (count == 0) {
			StringBuffer level = new StringBuffer("1");
			for (int i = 1; i < prizeCount; i++) {
				level.append(",1");
			}
			for (int i = 0; i < bet.length; i++) {
				if (bet[i].length()>1 && (!win[i].equals("*"))) {
					int prize2Count = (bet[i].length()-1)*prizeCount;
					for (int j = 0; j < prize2Count; j++) {
							level.append(",2");
					}
				}
			}
			return level.toString();
		}
		return "";
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(regex_spf02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		String[] bets = betcode.split("-")[1].replace("^", "").split(",");
		
		for(String bet:bets) {
			if(isRepeated(bet.toCharArray())) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		int zhushu = 1;
		for(String bet:bets) {
			zhushu = zhushu * bet.length();
		}
		
		if(zhushu==1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return zhushu*beishu.longValue()*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				int amtSingle = (int) (splitedLot.getAmt() / splitedLot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		long amt = getSingleBetAmount(betcode, BigDecimal.ONE, 200);
		
		if(amt<=2000000) {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode(betcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(betcode, new BigDecimal(lotmulti), 200));
			list.add(lot);
		}else {
			String[] betlengths = new String[14];
			
			String[] bets = betcode.split("-")[1].replace("^", "").split(",");
			
			for(int i=0;i<bets.length;i++) {
				betlengths[i] = bets[i].length()+"-"+i;
			}
			
			Arrays.sort(betlengths);
			
			int zhushu = 1;
			List<Integer> notSplit = new ArrayList<Integer>();
			List<Integer> beSplit = new ArrayList<Integer>();
			
			for(int i=betlengths.length-1;i>0;i--) {
				zhushu = zhushu * Integer.parseInt(betlengths[i].split("-")[0]);
				notSplit.add(Integer.parseInt(betlengths[i].split("-")[1]));
				if(zhushu * Integer.parseInt(betlengths[i-1].split("-")[0])>=10000) {
					for(int j=i-1;j>=0;j--) {
						beSplit.add(Integer.parseInt(betlengths[j].split("-")[1]));
					}
					break;
				}
			}
			
			
			String[] beSplitCode = new String[beSplit.size()];
			for(int i=0;i<beSplit.size();i++) {
				beSplitCode[i] = bets[beSplit.get(i)];
			}
			
			
			
			List<String> combinations = new ArrayList<String>();
			List<String> changed = new ArrayList<String>();
			for(String splitCode:beSplitCode) {
				changed = combinations;
				combinations = new ArrayList<String>();
				for(char c:splitCode.toCharArray()) {
					if(changed.size()==0) {
						combinations.add(String.valueOf(c));
					}else {
						for(String change:changed) {
							combinations.add(change+","+String.valueOf(c));
						}
					}
				}
			}
			
			
			for(String combination:combinations) {
				String[] realcode = new String[14];
				for(int not:notSplit) {
					realcode[not] = bets[not];
				}
				
				for(int i=0;i<beSplit.size();i++) {
					realcode[beSplit.get(i)] = combination.split(",")[i];
				}
				
				
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setBetcode(appendBetcode(realcode));
				lot.setLotMulti(lotmulti);
				lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(lot);
				
				
			}
		}
		return list;
	}
	
	
	private String appendBetcode(String[] strs) {
		StringBuilder betcode = new StringBuilder(lotterytype+"02-");
		for(String str:strs) {
			betcode.append(str).append(",");
		}
		if(betcode.toString().endsWith(",")) {
			betcode.deleteCharAt(betcode.length()-1);
		}
		betcode.append("^");
		return betcode.toString();
	}

}
