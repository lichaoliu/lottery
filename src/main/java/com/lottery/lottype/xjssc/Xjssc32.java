package com.lottery.lottype.xjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Xjssc32 extends XjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		StringBuilder prize = new StringBuilder();
		int winB = Integer.parseInt(wincode.split(",")[2]);
		String prizeB = "";
		if(winB>=0&&winB<=1) {
			prizeB = "1";
		}else if(winB>=2&&winB<=3){
			prizeB = "2";
		}else if(winB>=4&&winB<=5){
			prizeB = "3";
		}else if(winB>=6&&winB<=7){
			prizeB = "4";
		}else if(winB>=8&&winB<=9){
			prizeB = "5";
		}
		String win = prizeB+","+wincode.substring(6);
		for(String code:betcode.split("-")[1].split("\\^")) {
			if((!code.substring(0, 1).equals(prizeB))&&(code.substring(2).equals(wincode.substring(6)))) {
				prize.append(LotteryDrawPrizeAwarder.XJSSC_QJ2.value).append(",");
			}else if(code.equals(win)) {
				prize.append(LotteryDrawPrizeAwarder.XJSSC_QJ1.value).append(",");
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
		if(!betcode.matches(DAN_QJ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
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
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("101432-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("101432-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}
}
