package com.lottery.lottype.gd11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Gd11x5DR8 extends Gd11x5X{
	
	
	private Gd11x5SR8 sr8 = new Gd11x5SR8();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return caculatePrizeDS(betcode, wincode, 5, LotteryDrawPrizeAwarder.GD11X5_R8.value, 8);
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(DR8)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", "").replace("#", ","))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		int dan = betcode.split("\\-")[1].split("\\#")[0].split(",").length;
		int tuo = betcode.split("\\-")[1].split("\\#")[1].split(",").length;
		
		long zhushu = MathUtils.combine(tuo, 8-dan);
		
		return zhushu*200*beishu.longValue();
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
		String dan = betcode.split("\\#")[0];
		String[] tuos = betcode.split("\\#")[1].split(",");
		
		List<List<String>> collections = MathUtils.getCodeCollection(Arrays.asList(tuos), 8-dan.split(",").length);
		
		for(List<String> collection:collections) {
			SplitedLot slot = new SplitedLot(lotterytype);
			
			StringBuilder code = new StringBuilder("200508-"+dan+",");
			for(String c:collection) {
				code.append(c).append(",");
			}
			
			if(code.toString().endsWith(",")) {
				code = code.deleteCharAt(code.length()-1);
			}
			code.append("^");
			
			slot.setBetcode(code.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(200*lotmulti);
			list.add(slot);
		}
		
		return list;
	}

}
