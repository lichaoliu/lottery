package com.lottery.lottype.jxssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

//三星和值
public class Jxssc22 extends JxsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] wins = wincode.split(",");
		int sum = Integer.parseInt(wins[2])+Integer.parseInt(wins[3])+Integer.parseInt(wins[4]);
		
		for(String code:betcode.replace("^", "").split("-")[1].split(",")) {
			if(code.equals(String.valueOf(sum))) {
				return LotteryDrawPrizeAwarder.JXSSC_H3.value;
			}
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(OTHER_3H)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(isBetcodeDuplication(betcode.split("\\-")[1].replace("^", ""))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = 0;
		
		for(String code:betcode.split("\\-")[1].replace("^", "").split(",")) {
			zhushu = zhushu + OTHER_3H_ZHUSHU[Integer.parseInt(code)];
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
		for(String code:betcode.replace("^", "").split("-")[1].split(",")) {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode("101122-"+code+"^");
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(lot);
		}
		return list;
	}
}
