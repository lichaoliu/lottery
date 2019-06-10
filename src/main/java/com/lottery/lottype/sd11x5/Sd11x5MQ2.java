package com.lottery.lottype.sd11x5;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Sd11x5MQ2 extends Sd11x5X{
	
	private Sd11x5SQ2 sq2 = new Sd11x5SQ2();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] betcodes = betcode.split("\\-")[1].replace("^", "").split(";");
		if(betcodes[0].contains(wincode.substring(0, 2))&&betcodes[1].contains(wincode.substring(3, 5))) {
			return LotteryDrawPrizeAwarder.SD11X5_Q2.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(MQ2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.replace("^", "");
		
		for(String bet:betcode.split("-")[1].split(";")) {
			if(isBetcodeDuplication(bet)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		String[] bet1s = betcode.split("-")[1].split(";")[0].split(",");
		String[] bet2s = betcode.split("-")[1].split(";")[1].split(",");
		
		int num = 0;
		
		for(String bet1:bet1s) {
			for(String bet2:bet2s) {
				if(!bet1.equals(bet2)) {
					num = num + 1;
				}
			}
		}
		
		if(num<=1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return num*beishu.longValue()*200;
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
			if(s.getBetcode().startsWith("200742")) {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(sq2.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
		}

		return list;
	}
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		String[] points = betcode.split("\\-")[1].replace("^", "").split(";");
		
		Set<String> bets = new HashSet<String>();
		int total = 0;
		
		for(String point:points) {
			for(String code:point.split(",")) {
				bets.add(code);
				total = total + 1;
			}
		}
		if(bets.size()==total) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setAmt(getSingleBetAmount(betcode, new BigDecimal(lotmulti), 200));
			slot.setBetcode(betcode);
			slot.setLotMulti(lotmulti);
			list.add(slot);
		}else {
			String[] bet1s = points[0].split(",");
			String[] bet2s = points[1].split(",");
			for(String bet1:bet1s) {
				for(String bet2:bet2s) {
					if(!bet1.equals(bet2)) {
						SplitedLot slot = new SplitedLot(lotterytype);
						slot.setBetcode("200732-"+bet1+","+bet2+"^");
						slot.setAmt(200*lotmulti);
						slot.setLotMulti(lotmulti);
						list.add(slot);
					}
				}
			}
		}
		
		return list;
	}

}
