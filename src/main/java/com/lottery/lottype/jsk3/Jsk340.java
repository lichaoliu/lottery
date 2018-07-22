package com.lottery.lottype.jsk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

//三不同单式
//100940-01,02,03^02,03,04^
public class Jsk340 extends Jsk3X{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		if (!isSanBuTong(wincode)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		StringBuilder prize = new StringBuilder();
		betcode = betcode.split("-")[1];
		String[] wins = wincode.split(",");
		
		for(String code:betcode.split("\\^")) {
			if(code.contains(wins[0])&&code.contains(wins[1])&&code.contains(wins[2])) {
				prize.append(LotteryDrawPrizeAwarder.JSK3_SBT.value).append(",");
			}
		}
		check2delete(prize);
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(k340)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.split("-")[1];
		
		for(String code:betcode.split("\\^")) {
			if(!isSanBuTong(code)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		return betcode.split("\\^").length*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplitFC(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 50,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		
		for(String code:betcodes) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100940-"+code+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
//		StringBuilder sb = new StringBuilder();
//		
//		for(int i=0;i<betcodes.length;i++) {
//			sb.append(betcodes[i]).append("^");
//			if(sb.toString().split("\\^").length==5) {
//				SplitedLot slot = new SplitedLot();
//				slot.setBetcode("100940-"+sb.toString());
//				slot.setLotMulti(lotmulti);
//				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//				list.add(slot);
//				sb.delete(0, sb.length());
//			}
//		}
//		
//		if(sb.length()>0) {
//			SplitedLot slot = new SplitedLot();
//			slot.setBetcode("100940-"+sb.toString());
//			slot.setLotMulti(lotmulti);
//			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//			list.add(slot);
//		}
		
		return list;
	}

}
