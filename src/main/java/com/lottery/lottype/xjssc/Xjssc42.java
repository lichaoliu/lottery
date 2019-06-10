package com.lottery.lottype.xjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Xjssc42 extends XjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		StringBuilder prize = new StringBuilder();
		int winB = Integer.parseInt(wincode.split(",")[2]);
		String[] wincodes = wincode.split(",");
		String prizeB = "";
		if(winB>=0&&winB<=1) {
			prizeB = "1";
		}else if(winB>=2&&winB<=3){
			prizeB = "2";
		}else if(winB>=4&&winB<=5){
			prizeB = "3";
		}else if(winB>=6&&winB<=7){
			prizeB = "4";
		}else if(winB>=8&&winB<=9){
			prizeB = "5";
		}
		String code[] = betcode.replace("^", "").split("\\-")[1].split(";");
		
		for(String code0:code[0].split(",")) {
			for(String code1:code[1].split(",")) {
				for(String code2:code[2].split(",")) {
					
					if(code1.equals(wincodes[3])&&code2.equals(wincodes[4])) {
						if(code0.equals(prizeB)) {
							prize.append(LotteryDrawPrizeAwarder.XJSSC_QJ1.value).append(",");
						}else {
							prize.append(LotteryDrawPrizeAwarder.XJSSC_QJ2.value).append(",");
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
		if(!betcode.matches(FU_QJ2)) {
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
		long amt = getSingleBetAmount(betcode,new BigDecimal(lotmulti),oneAmount);
		if(!SplitedLot.isToBeSplitFC(lotmulti,amt)) {
			list.add(new SplitedLot(betcode,lotmulti,amt,lotterytype));
		}else {
			int amtSingle = (int) (amt / lotmulti);
			int permissionLotmulti = 2000000 / amtSingle;
			if(permissionLotmulti > 50) {
				permissionLotmulti = 50;
			}
			list.addAll(SplitedLot.splitToPermissionMulti(betcode, lotmulti, permissionLotmulti,lotterytype));
			for(SplitedLot s:list) {
				s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
			}
		}
		return list;
	}

}
