package com.lottery.lottype.cqssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Cqssc15 extends CqsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		betcode = betcode.replace("^", "").split("-")[1];
		String[] wins = wincode.split(",");
		
		if(betcode.split(";")[0].contains(wins[0])
				&&betcode.split(";")[1].contains(wins[1])
				&&betcode.split(";")[2].contains(wins[2])
				&&betcode.split(";")[3].contains(wins[3])
				&&betcode.split(";")[4].contains(wins[4])) {
			return LotteryDrawPrizeAwarder.CQSSC_5D.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(FU_5D)) {
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
				long amt = getSingleBetAmount(splitedLot.getBetcode(), new BigDecimal(lotmulti), 200);
				int amtSingle = (int) (amt / lotmulti);
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti > 50) {
					permissionLotmulti = 50;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		if(getSingleBetAmount(betcode, BigDecimal.ONE, 200)<=20000) {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode(betcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(betcode, new BigDecimal(lotmulti), 200));
			list.add(lot);
		}else {
			String[] betcodes = betcode.split("-")[1].replace("^", "").split(";");
			
			for(String code:betcodes[4].split(",")) {
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setBetcode("100715-"+betcodes[0]+";"+betcodes[1]+";"+betcodes[2]+";"+betcodes[3]+";"+code+"^");
				lot.setLotMulti(lotmulti);
				lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(lot);
			}
		}
		return list;
	}

}
