package com.lottery.lottype.gxkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Gxkl10MHYT extends Gxkl10X{
	
	private Gxkl10SHYT shyt = new Gxkl10SHYT();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		wincode = wincode.split(",")[4];
		betcode = betcode.split("\\-")[1];
		StringBuilder sb = new StringBuilder();
		for(String code:betcode.split(",")) {
			if(code.equals(wincode)) {
				sb.append(LotteryDrawPrizeAwarder.GXKL10_HYT.value).append(",");
			}
		}
		if (sb.length() > 0) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(MHYT)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		
		if(isBetcodeDuplication(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = MathUtils.combine(betcode.split("-")[1].split(",").length, 1);
		
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
			if(s.getBetcode().startsWith(String.valueOf(PlayType.gxkll0_shyt.value))) {
				s.setAmt(shyt.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
			
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		if(betcode.split("\\-")[1].replace("^", "").split(",").length<=10) {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode(betcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(betcode, new BigDecimal(lotmulti), 200));
			list.add(lot);
		}else {
			betcode = betcode.replace("^", "");
			String[] betcodes = betcode.split("-")[1].split(",");
			StringBuilder realcode = new StringBuilder();
			for(String bet:betcodes) {
				realcode.append(bet).append(",");
				if(realcode.toString().split(",").length==10) {
					realcode = realcode.deleteCharAt(realcode.length()-1);
					SplitedLot lot = new SplitedLot(lotterytype);
					lot.setBetcode("110516-"+realcode.toString()+"^");
					lot.setLotMulti(lotmulti);
					lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
					list.add(lot);
					realcode.delete(0, realcode.length());
				}
			}
			if(realcode.length()>0) {
				realcode = realcode.deleteCharAt(realcode.length()-1);
				
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setLotMulti(lotmulti);
				
				if(realcode.toString().split(",").length==1) {
					lot.setBetcode("110506-"+realcode.toString()+"^");
					lot.setAmt(shyt.getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				}else {
					lot.setBetcode("110516-"+realcode.toString()+"^");
					lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				}
				list.add(lot);
				realcode.delete(0, realcode.length());
			}
		}
		return list;
	}

}
