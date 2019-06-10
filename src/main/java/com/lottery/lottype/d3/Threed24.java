package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Threed24 extends ThreedX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		
		String sumwin = getSumWincode(wincode);
		
		StringBuilder prize = new StringBuilder();
		
		for(String bet:betcode.split("\\-")[1].replace("^", "").split(",")) {
			if(bet.equals(sumwin)) {
				if(sumwin.equals("00")||sumwin.equals("27")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS0.value);
				}else if(sumwin.equals("01")||sumwin.equals("26")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS1.value);
				}else if(sumwin.equals("02")||sumwin.equals("25")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS2.value);
				}else if(sumwin.equals("03")||sumwin.equals("24")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS3.value);
				}else if(sumwin.equals("04")||sumwin.equals("23")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS4.value);
				}else if(sumwin.equals("05")||sumwin.equals("22")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS5.value);
				}else if(sumwin.equals("06")||sumwin.equals("21")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS6.value);
				}else if(sumwin.equals("07")||sumwin.equals("20")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS7.value);
				}else if(sumwin.equals("08")||sumwin.equals("19")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS8.value);
				}else if(sumwin.equals("09")||sumwin.equals("18")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS9.value);
				}else if(sumwin.equals("10")||sumwin.equals("17")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS10.value);
				}else if(sumwin.equals("11")||sumwin.equals("16")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS11.value);
				}else if(sumwin.equals("12")||sumwin.equals("15")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS12.value);
				}else if(sumwin.equals("13")||sumwin.equals("14")) {
					prize.append(LotteryDrawPrizeAwarder.D3_HS13.value);
				}
			}
		}
		check2delete(prize);
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_24)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.replace("^", "");
		
		if(!checkDuplicate(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = betcode.split("-")[1].split(",").length;
		return zhushu*beishu.longValue()*200;
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
		betcode = betcode.replace("^", "");
		String[] betcodes = betcode.split("-")[1].split(",");
		
		for(String code:betcodes) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100224-"+code+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		return list;
	}

}
