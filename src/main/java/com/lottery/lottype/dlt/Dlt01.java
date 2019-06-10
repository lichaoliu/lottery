package com.lottery.lottype.dlt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Dlt01 extends DltX{

 

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(dlt_01)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		for(String code:betcode.split("-")[1].split("\\^")) {
			if(!checkDuplicate(code.split("\\|")[0])){
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			if(!checkDuplicate(code.split("\\|")[1])){
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		long zhushu = betcode.split("\\^").length*beishu.longValue()*1L;
		return zhushu*oneAmount;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti,oneAmount);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt()/oneAmount*200)) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), oneAmount));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti,int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].split("\\^");
		
		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<betcodes.length;i++) {
			sb.append(betcodes[i]).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("200101-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("200101-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
			list.add(slot);
		}
		
		return list;
	}

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		StringBuilder sb = new StringBuilder();
		
		boolean addition = oneAmount==300?true:false;
		for(String code:betcode.split("-")[1].split("\\^")) {
			List<String> qian = Arrays.asList(code.split("\\|")[0].split(","));
			List<String> hou = Arrays.asList(code.split("\\|")[1].split(","));
			
			String prize = getPrize(qian, hou, wincode.split("\\|")[0].split(","), wincode.split("\\|")[1].split(","), addition);
			
			if(!StringUtil.isEmpty(prize)) {
				sb.append(prize).append(",");
			}
		}
		check2delete(sb);
		return sb.toString();
	}

}
