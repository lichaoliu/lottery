package com.lottery.lottype.p3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Pls23 extends PlsX{
	
	
	private Pls03 pls03 = new Pls03();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		
		if(isZuliu(wincode)) {
			if(betcode.split("-")[1].contains(getSumWincode(wincode))) {
				return LotteryDrawPrizeAwarder.P3_3.value;
			}
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(p3_23)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.replace("^", "");
		if(!checkDuplicate(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = 0;
		for(String code:betcode.split("-")[1].split(",")) {
			zhushu = zhushu + group6_P3[Integer.parseInt(code)-3];
		}
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
			s.setAmt(pls03.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.replace("^", "");
		String[] betcodes = betcode.split("-")[1].split(",");
		
		for(String code:betcodes) {
			String singleBetcodes = getGroup6DanshiBetcode(Integer.parseInt(code));
			for(String single:singleBetcodes.split(";")) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode(single);
				slot.setLotMulti(lotmulti);
				slot.setAmt(pls03.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
			}
		}
		return list;
	}

}
