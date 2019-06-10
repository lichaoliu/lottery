package com.lottery.lottype.jxssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Jxssc23 extends JxsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] wins = wincode.split(",");
		if(wins[3].equals(wins[4])) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		
		betcode = betcode.replace("^", "").split("-")[1];
		if(betcode.contains(wins[3])&&betcode.contains(wins[4])) {
			return LotteryDrawPrizeAwarder.JXSSC_2Z.value;
		}
		
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(OTHER_2Z)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", ""))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int total = betcode.split("\\-")[1].replace("^", "").split(",").length;
		return MathUtils.combine(total, 2)*200*beishu.longValue();
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
		betcode.replace("^", "").split("-")[1].split(",");
		
		List<List<String>> codeCollection = MathUtils.getCodeCollection(Arrays.asList(betcode.replace("^", "").split("-")[1].split(",")), 2);
		
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		for(List<String> code:codeCollection) {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode("101123-"+code.get(0)+","+code.get(1)+"^");
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(lot);
		}
		return list;
	}

}
