package com.lottery.lottype.qxc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Qxc02 extends QxcX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		wincode = transferWincode(wincode);
		String[] win = wincode.split(",");
		String code = betcode.split("-")[1].replace("^", "");
		String[] one = code.split("\\|")[0].split(",");
		String[] two = code.split("\\|")[1].split(",");
		String[] three = code.split("\\|")[2].split(",");
		String[] four = code.split("\\|")[3].split(",");
		String[] five = code.split("\\|")[4].split(",");
		String[] six = code.split("\\|")[5].split(",");
		String[] seven = code.split("\\|")[6].split(",");
		
		return getPrizeLevel(one, two, three, four, five, six, seven, win, oneAmount);
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(qxc_02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String s01 = betcode.split("-")[1].split("\\|")[0];
		String s02 = betcode.split("-")[1].split("\\|")[1];
		String s03 = betcode.split("-")[1].split("\\|")[2];
		String s04 = betcode.split("-")[1].split("\\|")[3];
		String s05 = betcode.split("-")[1].split("\\|")[4];
		String s06 = betcode.split("-")[1].split("\\|")[5];
		String s07 = betcode.split("-")[1].split("\\|")[6];
		
		if(!checkDuplicate(s01)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(s02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(s03)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(s04)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(s05)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(s06)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(s07)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = s01.split(",").length*s02.split(",").length
				*s03.split(",").length*s04.split(",").length*s05.split(",").length
				*s06.split(",").length*s07.split(",").length;
		
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
			String[] betlengths = new String[7];
			
			String[] bets = betcode.split("-")[1].replace("^", "").split("\\|");
			
			for(int i=0;i<bets.length;i++) {
				betlengths[i] = (bets[i].split(",").length>=10?String.valueOf(bets[i].split(",").length):"0"+bets[i].split(",").length)+"-"+i;
			}
			
			Arrays.sort(betlengths);
			
			int zhushu = 1;
			List<Integer> notSplit = new ArrayList<Integer>();
			List<Integer> beSplit = new ArrayList<Integer>();
			
			for(int i=betlengths.length-1;i>0;i--) {
				zhushu = zhushu * Integer.parseInt(betlengths[i].split("-")[0]);
				notSplit.add(Integer.parseInt(betlengths[i].split("-")[1]));
				if(zhushu * Integer.parseInt(betlengths[i-1].split("-")[0])>10000) {
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
				for(String c:splitCode.split(",")) {
					if(changed.size()==0) {
						combinations.add(c);
					}else {
						for(String change:changed) {
							combinations.add(change+","+c);
						}
					}
				}
			}
			
			
			for(String combination:combinations) {
				String[] realcode = new String[7];
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
			betcode.append(str).append("|");
		}
		if(betcode.toString().endsWith("|")) {
			betcode.deleteCharAt(betcode.length()-1);
		}
		betcode.append("^");
		return betcode.toString();
	}

}
