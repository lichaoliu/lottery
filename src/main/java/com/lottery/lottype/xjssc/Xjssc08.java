package com.lottery.lottype.xjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Xjssc08 extends XjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		StringBuilder prize = new StringBuilder();
		String[] wincodes = wincode.split(",");
		for(String code:betcode.split("-")[1].split("\\^")) {
			String[] codes = code.split(",");
			int hit1 = 0;
			int hit2= 0;
			for(int i=0;i<codes.length;i++) {
				if(codes[i].equals(wincodes[i])) {
					hit1 = hit1 + 1;
				}else {
					if(codes[i].equals("~")) {
						continue;
					}
					int codeInt = Integer.parseInt(codes[i]);
					int winInt = Integer.parseInt(wincodes[i]);
					int codeIntPlus = codeInt+1==10?0:codeInt+1;
					int codeIntMinus = codeInt-1==-1?9:codeInt-1;
					
					if(codeIntPlus==winInt||codeIntMinus==winInt) {
						hit2 = hit2 + 1;
					}
				}
			}
			if(hit1==2) {
				prize.append(LotteryDrawPrizeAwarder.XJSSC_R21.value).append(",");
			}
			if(hit1==1&&hit2==1) {
				prize.append(LotteryDrawPrizeAwarder.XJSSC_R22.value).append(",");
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
		if(!betcode.matches(DAN_R2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		for(String code:betcode.split("\\-")[1].split("\\^")) {
			int notnum = 0;
			for(String onecode:code.split(",")) {
				if("~".equals(onecode)) {
					notnum = notnum + 1;
				}
			}
			if(notnum!=3) {
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
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("101408-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("101408-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
