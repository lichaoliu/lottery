package com.lottery.lottype.tjkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class TJkl10DQ2 extends TJkl10X{

	TJkl10SQ2 sq2 = new TJkl10SQ2();
	
	
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		StringBuilder sb = new StringBuilder("");
		List<String> wincodes = Arrays.asList(wincode.split(","));
		String dan = betcode.split("\\#")[0];
		List<String> tuo = Arrays.asList(toCodeArray(betcode.split("\\#")[1]));
		if(!wincodes.contains(dan)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		
		for(int i=0,j=wincodes.size();i<j;i++) {
			if(wincodes.get(i).equals(dan)) {
				if(i==0) {
					if(tuo.contains(wincodes.get(1))) {
						sb.append(LotteryDrawPrizeAwarder.TJK10_Q2.value).append(",");
					}
				}else if(i==7) {
					if(tuo.contains(wincodes.get(6))) {
						sb.append(LotteryDrawPrizeAwarder.TJK10_Q2.value).append(",");
					}
				}else {
					if(tuo.contains(wincodes.get(i-1))) {
						sb.append(LotteryDrawPrizeAwarder.TJK10_Q2.value).append(",");
					}
					if(tuo.contains(wincodes.get(i+1))) {
						sb.append(LotteryDrawPrizeAwarder.TJK10_Q2.value).append(",");
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
		if(!validateBetcodeContainsLine(betcode,DQ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return betcode.split("\\#")[1].replace(",", "").length()*beishu.longValue()*200;
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
			if(s.getBetcode().startsWith("100416")) {
				s.setAmt(sq2.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
		}

		return list;
	}
	
	
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.replace("^", "");
		String bet = betcode.split("-")[1].replace("#", "");
		
		if(bet.length()==4) {
			String realbetcode = "100416-"+bet.substring(0, 2)+","+bet.substring(2, 4)+"^"+bet.substring(2, 4)+","+bet.substring(0, 2)+"^";
			SplitedLot lot = new SplitedLot(realbetcode, lotmulti, sq2.getSingleBetAmount(realbetcode, new BigDecimal(lotmulti), 200),lotterytype);
			list.add(lot);
			return list;
		}
		
		SplitedLot lot = new SplitedLot(betcode+"^", lotmulti, getSingleBetAmount(betcode+"^", new BigDecimal(lotmulti), 200),lotterytype);
		list.add(lot);
		return list;
	}

	
}
