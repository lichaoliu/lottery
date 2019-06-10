package com.lottery.lottype.zc.jinqiu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Zucaijinqiu01 extends ZucaijinqiuX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] wincodes = wincode.split(",");
		StringBuilder prize = new StringBuilder();
		for(String code:betcode.split("-")[1].split("\\^")) {
			String[] betcodes = code.split(",");
			int correct = 0;
			for(int i = 0;i<betcodes.length;i++) {
				if(wincodes[i].equals("*")) {
					correct = correct + 1;
				}else {
					if(betcodes[i].contains(wincodes[i])) {
						correct = correct + 1;
					}
				}
			}
			
			if(correct==8) {
				prize.append("1").append(",");
			}
		}
		if(prize.toString().equals(",")) {
			prize = prize.deleteCharAt(prize.length()-1);
		}
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(regex_jqc01)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = betcode.split("\\-")[1].split("\\^").length;
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
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		List<String> betcodelist = new ArrayList<String>();
		
		for(String code:betcode.split("\\!")) {
			for(String codeinner:code.split("\\-")[1].split("\\^")) {
				betcodelist.add(codeinner);
			}
		}
		StringBuilder sb = new StringBuilder();
		
		for(String innercode:betcodelist) {
			sb.append(innercode).append("^");
			if(sb.toString().split("\\^").length==3) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("400301-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("400301-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
