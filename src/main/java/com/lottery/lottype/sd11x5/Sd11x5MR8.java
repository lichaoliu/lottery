package com.lottery.lottype.sd11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Sd11x5MR8 extends Sd11x5X{
	
	private Sd11x5SR8 sr8 = new Sd11x5SR8();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return caculatePrizeMS(betcode, wincode, 5, LotteryDrawPrizeAwarder.SD11X5_R8.value,8);
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(MR8)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		
		if(isBetcodeDuplication(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = MathUtils.combine(betcode.split("-")[1].split(",").length, 8);
		
		if(zhushu<=1) {
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
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(sr8.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.replace("^", "").split("\\-")[1];
		String[] codes = betcode.split(",");
		
		List<List<String>> collections = MathUtils.getCodeCollection(Arrays.asList(codes), 8);
		
		for(List<String> collection:collections) {
			Collections.sort(collection);
			SplitedLot slot = new SplitedLot(lotterytype);
			StringBuilder sb = new StringBuilder("200708-");
			for(String onecode:collection) {
				sb.append(onecode).append(",");
			}
			sb.deleteCharAt(sb.length()-1);
			sb.append("^");
			slot.setBetcode(sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(200*lotmulti);
			list.add(slot);
		}
		return list;
	}

}
