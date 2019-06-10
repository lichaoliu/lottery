package com.lottery.lottype.qlc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Qlc01 extends QlcX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		StringBuilder sb = new StringBuilder();
		for(String bet:betcode.split("-")[1].split("\\^")) {
			String prize = oneBetPrize(wincode, Arrays.asList(bet.split(",")));
			if(!StringUtil.isEmpty(prize)) {
				sb.append(prize).append(",");
			}
		}
		check2delete(sb);
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(qlc_01)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		for(String code:betcode.split("-")[1].split("\\^")) {
			if(!checkDuplicate(code)){
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		long zhushu = betcode.split("\\^").length*beishu.longValue()*1L;
		return zhushu*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		for(String onebet:betcodes) {
			String finalbetcode = "100301-"+onebet+"^";
			long realamt = lotmulti*200;
			if(SplitedLot.isToBeSplitFC(lotmulti, realamt)) {
				list.addAll(SplitedLot.splitToPermissionMulti(finalbetcode, lotmulti, 50, lotterytype));
			}else {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode(finalbetcode);
				slot.setLotMulti(lotmulti);
				slot.setAmt(realamt);
				list.add(slot);
			}
		}
		
//		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
//		
//		for(SplitedLot splitedLot:zhumaList) {
//			if(!SplitedLot.isToBeSplitFC(splitedLot.getLotMulti(),splitedLot.getAmt())) {
//				list.add(splitedLot);
//			}else {
//				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 50,lotterytype));
//			}
//		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			s.setBetcode(getSortCode01(s.getBetcode()));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("100301-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100301-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
