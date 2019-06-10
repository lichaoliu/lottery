package com.lottery.lottype.tjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Tjssc27 extends TjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String winw = wincode.split(",")[0];
		String winq = wincode.split(",")[1];
		String winb = wincode.split(",")[2];
		String wins = wincode.split(",")[3];
		String wing = wincode.split(",")[4];
		
		betcode = betcode.replace("^", "").split("-")[1];
		
		String[] betsw = betcode.split(";")[0].split(",");
		String[] betsq = betcode.split(";")[1].split(",");
		String[] betsb = betcode.split(";")[2].split(",");
		String[] betss = betcode.split(";")[3].split(",");
		String[] betsg = betcode.split(";")[4].split(",");
		
		StringBuilder prize = new StringBuilder();
		for(String betw:betsw) {
			for(String betq:betsq) {
				for(String betb:betsb) {
					for(String bets:betss) {
						for(String betg:betsg) {
							if (betw.toString().equals(winw) && betq.toString().equals(winq) && betb.toString().equals(winb)
									&& bets.toString().equals(wins) && betg.toString().equals(wing)) {
								prize.append(LotteryDrawPrizeAwarder.TJSSC_5T5.value).append(",")
									.append(LotteryDrawPrizeAwarder.TJSSC_5T2.value).append(",")
									.append(LotteryDrawPrizeAwarder.TJSSC_5T2.value).append(",")
									.append(LotteryDrawPrizeAwarder.TJSSC_5T3.value).append(",")
									.append(LotteryDrawPrizeAwarder.TJSSC_5T3.value);
								continue;
							}
							
							if (betw.toString().equals(winw) && betq.toString().equals(winq) && bets.toString().equals(wins) && betg.toString().equals(wing)) {
								prize.append(LotteryDrawPrizeAwarder.TJSSC_5T2.value);
								continue;
							}
							
							if (betw.toString().equals(winw) && betq.toString().equals(winq)) {
								prize.append(LotteryDrawPrizeAwarder.TJSSC_5T2.value).append(",");
							}
							if (betg.toString().equals(wing) && bets.toString().equals(wins)) {
								prize.append(LotteryDrawPrizeAwarder.TJSSC_5T2.value).append(",");
							}
							if (betb.toString().equals(winb) && betq.toString().equals(winq) && betw.toString().equals(winw)) {
								prize.append(LotteryDrawPrizeAwarder.TJSSC_5T3.value).append(",");
							}
							if (betb.toString().equals(winb) && bets.toString().equals(wins) && betg.toString().equals(wing)) {
								prize.append(LotteryDrawPrizeAwarder.TJSSC_5T3.value).append(",");
							}
						}
					}
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
		if(!betcode.matches(OTHER_5T)) {
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
	
	
	
	/**
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
				lot.setBetcode("100727-"+betcodes[0]+";"+betcodes[1]+";"+betcodes[2]+";"+betcodes[3]+";"+code+"^");
				lot.setLotMulti(lotmulti);
				lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(lot);
			}
		}
		return list;
	}*/
	
	
	
	private List<SplitedLot> transformsingle(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].replace("^", "").split(";");
		
		for(String code0:betcodes[0].split(",")) {
			for(String code1:betcodes[1].split(",")) {
				for(String code2:betcodes[2].split(",")) {
					for(String code3:betcodes[3].split(",")) {
						for(String code4:betcodes[4].split(",")) {
							SplitedLot lot = new SplitedLot(lotterytype);
							lot.setBetcode("101627-"+code0+";"+code1+";"+code2+";"+code3+";"+code4+"^");
							lot.setLotMulti(lotmulti);
							lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
							list.add(lot);
						}
					}
				}
			}
		}
		return list;
	}


}
