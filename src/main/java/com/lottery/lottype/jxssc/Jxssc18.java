package com.lottery.lottype.jxssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Jxssc18 extends JxsscX{
	
	Jxssc08 jx08 = new Jxssc08();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] win = wincode.split(",");
		StringBuilder prize = new StringBuilder();
		
		String[] onecodes = betcode.split("-")[1].replace("^", "").split(",");
		for(int i=0;i<=4;i++) {
			if(onecodes[i].contains(win[i])) {
				prize.append(LotteryDrawPrizeAwarder.JXSSC_1R.value).append(",");
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
		
		if(!betcode.matches(FU_1R)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		String code = betcode.split("\\-")[1].replace("^", "");
		
		int isNumber = 0;
		int zhushu = 0;
		for(String onecode:code.split(";")) {
			if(onecode.matches("^[0-9]([,][0-9]){1,9}$")) {
				isNumber = isNumber + 1;
				zhushu = zhushu + onecode.replace(",", "").length();
				if(isBetcodeDuplication(onecode)) {
					throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
				}
			}
		}
		if(isNumber==0||zhushu<=1) {
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
			if(!SplitedLot.isToBeSplitFC(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 50,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(jx08.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}
		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		StringBuilder sb = new StringBuilder();
		String[] split = betcode.replace("^", "").split("-")[1].split(";");
		for(int i=0;i<split.length;i++) {
			if(split[i].equals("~")) {
				continue;
			}
			for(String code:split[i].split(",")) {
				sb.append(getSingleCode(code, i)).append("^");
				if(sb.toString().split("\\^").length==5) {
					SplitedLot slot = new SplitedLot(lotterytype);
					slot.setBetcode("101108-"+sb.toString());
					slot.setLotMulti(lotmulti);
					slot.setAmt(jx08.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
					list.add(slot);
					sb.delete(0, sb.length());
				}
			}
		}
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("101108-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(jx08.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		return list;
	}
	
	
	private String getSingleCode(String code,int point) {
		StringBuilder singlecode = new StringBuilder();
		
		for(int i=0;i<=4;i++) {
			if(i==point) {
				singlecode.append(code).append(",");
			}else {
				singlecode.append("~").append(",");
			}
		}
		singlecode = singlecode.deleteCharAt(singlecode.length()-1);
		
		return singlecode.toString();
	}

}
