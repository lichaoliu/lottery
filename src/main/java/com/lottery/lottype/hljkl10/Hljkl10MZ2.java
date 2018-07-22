package com.lottery.lottype.hljkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Hljkl10MZ2 extends Hljkl10X {

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		String[] betcodes = toCodeArray(betcode);
		String[] wincodes = wincode.split(",");
		int[] points = new int[betcodes.length];
		for(int i=0,j=points.length;i<j;i++) {
			points[i]=-2;
		}
		
		int total = 0;
		for(int i=0,j=wincodes.length;i<j;i++) {
			for(int a=0,b=betcodes.length;a<b;a++) {
				if(wincodes[i].equals(betcodes[a])) {
					points[a] = i;
					total = total + 1;
					break;
				}
			}
		}
		
		if(total>=2) {
			StringBuilder sb = new StringBuilder("");
			int[] hits = new int[total];
			int cursor = 0;
			for(int point:points) {
				if(point!=-2) {
					hits[cursor] = point;
					cursor = cursor+1;
				}
			}
			
			for(int i=0,j=hits.length-1;i<j;i++) {
				for(int a=i+1,b=hits.length;a<b;a++) {
					if(Math.abs(hits[i]-hits[a])==1) {
						sb.append(LotteryDrawPrizeAwarder.HLJK10_Z2.value).append(",");
					}
				}
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			return sb.toString();
		}
		
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		if(!validateBetcode(betcode, MZ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return MathUtils.combine(betcode.split(",").length, 2)*beishu.longValue()*200;
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
		for(SplitedLot lot:list) {
			lot.setBetcode(getMSortCode(lot.getBetcode()));
		}
		return list;
	}

}
