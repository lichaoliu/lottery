package com.lottery.lottype.jxssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Jxssc08 extends JxsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] win = wincode.split(",");
		StringBuilder prize = new StringBuilder();
		
		for(String code:betcode.split("-")[1].split("\\^")) {
			String[] onecodes = code.split(",");
			for(int i=0;i<=4;i++) {
				if(onecodes[i].contains(win[i])) {
					prize.append(LotteryDrawPrizeAwarder.JXSSC_1R.value).append(",");
				}
			}
		}
		if(prize.toString().endsWith(",")) {
			prize = prize.deleteCharAt(prize.length()-1);
		}
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		
		if(!betcode.matches(DAN_1R)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		String[] codes = betcode.split("\\-")[1].split("\\^");
		int total_zhushu = 0;
		
		for(String code:codes) {
			int isNumber = 0;
			int zhushu = 0;
			for(String onecode:code.split(",")) {
				if(onecode.matches("^[0-9]{1,10}$")) {
					isNumber = isNumber + 1;
					zhushu = zhushu + onecode.length();
					if(isBetcodeDuplication(onecode)) {
						throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
					}
				}
			}
			if(isNumber==0||zhushu>1) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			
			total_zhushu = total_zhushu + zhushu;
		}
		
		return total_zhushu*beishu.longValue()*200;
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
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("101108-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("101108-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
