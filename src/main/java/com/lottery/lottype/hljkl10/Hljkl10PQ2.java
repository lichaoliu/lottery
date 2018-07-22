package com.lottery.lottype.hljkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Hljkl10PQ2 extends Hljkl10X {

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		StringBuilder sb = new StringBuilder("");
		String[] qian = toCodeArray(betcode.split("\\-")[0]);
		List<String> hou = Arrays.asList(toCodeArray(betcode.split("\\-")[1]));
		String[] wincodes = wincode.split(",");
		for(String code:qian) {
			for(int i=0,j=wincodes.length;i<j-1;i++) {
				if(code.equals(wincodes[i])) {
					if(hou.contains(wincodes[i+1])) {
						sb.append(LotteryDrawPrizeAwarder.HLJK10_Q2.value).append(",");
					}
				}
			}
		}
		
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
		
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		if(!betcode.matches(PQ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(betcode.replace("-", "").replace(",", "").length()<6) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[0])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		
		String[] qians = toCodeArray(betcode.split("\\-")[0]);
		String[] hous = toCodeArray(betcode.split("\\-")[1]);
		
		
		
		int total = 0;
		for(String qian:qians) {
			for(String hou:hous) {
				if(!qian.equals(hou)) {
					total = total + 1;
				}
			}
		}
		if(total<2) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
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
		String[] qians = toCodeArray(betcode.split("\\-")[0]);
		String[] hous = toCodeArray(betcode.split("\\-")[1]);
		for(String qian:qians) {
			for(String hou:hous) {
				if(!qian.equals(hou)) {
					SplitedLot s = new SplitedLot(lotterytype);
					s.setBetcode("100816-"+qian+","+hou+"^");
					s.setLotMulti(lotmulti);
					s.setAmt(200*lotmulti);
					list.add(s);
				}
			}
		}
		return list;
	}

}
