package com.lottery.lottype.ssq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

//100101-02,06,08,11,12,16|02^02,06,08,11,12,16|02^02,06,08,11,12,16|02^02,06,08,11,12,16|02^
public class Ssq01 extends SsqX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		String winRed = wincode.split("\\|")[0];
		String winBlue = wincode.split("\\|")[1];
		StringBuilder sb = new StringBuilder();
		
		for(String code:betcode.split("-")[1].split("\\^")) {
			String prizeStr = caculatePrize(code.split("\\|")[0], code.split("\\|")[1], winRed, winBlue);
			if(!StringUtil.isEmpty(prizeStr)) {
				sb.append(prizeStr).append(",");
			}
		}
		check2delete(sb);
		
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(ssq_01)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		for(String code:betcode.split("-")[1].split("\\^")) {
			if(!checkDuplicate(code.split("\\|")[1])){
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
//		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
//		
//		for(SplitedLot splitedLot:zhumaList) {
//			if(!SplitedLot.isToBeSplitFC(splitedLot.getLotMulti(),splitedLot.getAmt())) {
//				list.add(splitedLot);
//			}else {
//				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 50,lotterytype));
//			}
//		}
		String[] betcodes = betcode.split("-")[1].split("\\^");
		for(String onebet:betcodes) {
			String finalbetcode = "100101-"+onebet+"^";
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
		
		
		for(SplitedLot s:list) {
			s.setAmt(s.getBetcode().split("\\^").length*s.getLotMulti()*200);
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
				slot.setBetcode("100101-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(5*lotmulti*200);
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100101-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(slot.getBetcode().split("\\^").length*lotmulti*200);
			list.add(slot);
		}
		
		return list;
	}

}
