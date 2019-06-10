package com.lottery.lottype.hljkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Hljkl10PQ3 extends Hljkl10X {

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);

		List<String> first = Arrays
				.asList(toCodeArray(betcode.split("\\-")[0]));
		List<String> second = Arrays
				.asList(toCodeArray(betcode.split("\\-")[1]));
		List<String> third = Arrays
				.asList(toCodeArray(betcode.split("\\-")[2]));

		if (first.contains(wincode.substring(0, 2))
				&& second.contains(wincode.substring(3, 5))
				&& third.contains(wincode.substring(6, 8))) {
			return LotteryDrawPrizeAwarder.HLJK10_Q3.value;
		}

		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		if(!betcode.matches(PQ3)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(betcode.replace("-", "").replace(",", "").length()<8) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode.split("\\-")[0])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode.split("\\-")[2])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		String[] firsts = toCodeArray(betcode.split("\\-")[0]);
		String[] seconds = toCodeArray(betcode.split("\\-")[1]);
		String[] thirds = toCodeArray(betcode.split("\\-")[2]);
		
		int total = 0;
		for(String first:firsts) {
			for(String second:seconds) {
				if(first.equals(second)) {
					continue;
				}
				for(String third:thirds) {
					if(second.equals(third)||first.equals(third)) {
						continue;
					}
					total = total + 1;
				}
			}
		}

		return total * beishu.longValue() * 200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		long amt = getSingleBetAmount(betcode,new BigDecimal(lotmulti),oneAmount);
		if(!SplitedLot.isToBeSplit99(lotmulti,amt)) {
			list.add(new SplitedLot(betcode,lotmulti,amt,lotterytype));
		}else {
			int amtSingle = (int) (amt / lotmulti);
			int permissionLotmulti = 2000000 / amtSingle;
			if(permissionLotmulti > 99) {
				permissionLotmulti = 99;
			}
			list.addAll(SplitedLot.splitToPermissionMulti(betcode, lotmulti, permissionLotmulti,lotterytype));
			for(SplitedLot s:list) {
				s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
				s.setBetcode(s.getBetcode());
			}
		}
		return list;
	}
	
	
	protected List<SplitedLot> transform(String betcode, int lotmulti) {
		betcode = betcode.substring(7,betcode.length()-1);
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		String[] firsts = toCodeArray(betcode.split("\\-")[0]);
		String[] seconds = toCodeArray(betcode.split("\\-")[1]);
		String[] thirds = toCodeArray(betcode.split("\\-")[2]);
		for(String first:firsts) {
			for(String second:seconds) {
				if(first.equals(second)) {
					continue;
				}
				for(String third:thirds) {
					if(second.equals(third)||first.equals(third)) {
						continue;
					}
					SplitedLot s = new SplitedLot(lotterytype);
					s.setBetcode("100817-"+first+","+second+","+third+"^");
					s.setLotMulti(lotmulti);
					s.setAmt(200*lotmulti);
					list.add(s);
				}
			}
		}
		return list;
	}


}
