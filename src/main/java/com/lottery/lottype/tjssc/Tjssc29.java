package com.lottery.lottype.tjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Tjssc29 extends TjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String winb = String.valueOf(Integer.parseInt(wincode.split(",")[2])/2+1);
		String wins = wincode.split(",")[3];
		String wing = wincode.split(",")[4];
		
		betcode = betcode.replace("^", "").split("-")[1];
		
		if(betcode.split(";")[0].contains(winb)&&betcode.split(";")[1].contains(wins)&&betcode.split(";")[1].contains(wing)) {
			return LotteryDrawPrizeAwarder.TJSSC_QJ.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(OTHER_QJ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		long zhushu = 1L;
		
		for(String code:betcode.split("\\-")[1].replace("^", "").split(";")) {
			zhushu = zhushu * code.split(",").length;
			if(isBetcodeDuplication(code)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		return zhushu*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transformsingle(betcode,lotmulti);
		
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
	
	
	
	
	private List<SplitedLot> transformsingle(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].replace("^", "").split(";");
		
		for(String code0:betcodes[0].split(",")) {
			for(String code1:betcodes[1].split(",")) {
				for(String code2:betcodes[2].split(",")) {
					SplitedLot lot = new SplitedLot(lotterytype);
					lot.setBetcode("101629-"+code0+";"+code1+";"+code2+"^");
					lot.setLotMulti(lotmulti);
					lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
					list.add(lot);
				}
			}
		}
		return list;
	}


}
