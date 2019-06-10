package com.lottery.lottype.tjkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class TJkl10DZ2 extends TJkl10X {

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		StringBuilder sb = new StringBuilder("");
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		String[] wincodes = toCodeArray(wincode);
		if(totalHits(dan, wincode)==dan.length()/2) {
			int point = -2;
			for(int i=0,j=wincodes.length;i<j;i++) {
				if(wincodes[i].equals(dan)) {
					point = i;
					break;
				}
			}
			if(point==0) {
				if(totalHits(wincodes[1], tuo)==1)
					sb.append(LotteryDrawPrizeAwarder.TJK10_Z2.value).append(",");
			}else if(point==7) {
				if(totalHits(wincodes[6], tuo)==1)
					sb.append(LotteryDrawPrizeAwarder.TJK10_Z2.value).append(",");
			}else if(point>0&&point<7){
				if(totalHits(wincodes[point-1], tuo)==1)
					sb.append(LotteryDrawPrizeAwarder.TJK10_Z2.value).append(",");
				if(totalHits(wincodes[point+1], tuo)==1)
					sb.append(LotteryDrawPrizeAwarder.TJK10_Z2.value).append(",");
			}
			if (sb.length() > 0) {
				sb.deleteCharAt(sb.length() - 1);
			}
			
		}

		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		if(!validateBetcodeContainsLine(betcode,DZ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return MathUtils.combine(betcode.split("\\#")[1].split(",").length,
				2 - betcode.split("\\#")[0].split(",").length)
				* beishu.longValue()
				* 200;
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
			lot.setBetcode(getDSortCode(lot.getBetcode()));
		}
		return list;
	}

}
