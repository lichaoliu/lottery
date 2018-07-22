package com.lottery.lottype.tjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Tjssc26 extends TjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		
		String winten = getDDPrize(Integer.parseInt(wincode.split(",")[3]));
		String winge = getDDPrize(Integer.parseInt(wincode.split(",")[4]));
		StringBuilder prize = new StringBuilder();
		
		String[] bettens = betcode.replace("^", "").split("-")[1].split(";")[0].split(",");
		String[] betges = betcode.replace("^", "").split("-")[1].split(";")[1].split(",");
		
		for(String betten:bettens) {
			for(String betge:betges) {
				if(winten.contains(betten)&&winge.contains(betge)) {
					prize.append(LotteryDrawPrizeAwarder.TJSSC_DD.value).append(",");
				}
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
		if(!betcode.matches(OTHER_DD)) {
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
		String[] qians = betcode.replace("^", "").split("-")[1].split(";")[0].split(",");
		String[] hous = betcode.replace("^", "").split("-")[1].split(";")[1].split(",");
		
		for(String qian:qians) {
			for(String hou:hous) {
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setBetcode("101626-"+qian+";"+hou+"^");
				lot.setLotMulti(lotmulti);
				lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(lot);
			}
		}
		return list;
	}

}
