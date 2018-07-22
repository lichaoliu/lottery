package com.lottery.lottype.qxc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Qxc01 extends QxcX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		wincode = transferWincode(wincode);
		StringBuilder sb = new StringBuilder();
		String[] win = wincode.split(",");
		
		for(String code:betcode.split("-")[1].split("\\^")) {
			String[] one = new String[]{code.split(",")[0]};
			String[] two = new String[]{code.split(",")[1]};
			String[] three = new String[]{code.split(",")[2]};
			String[] four = new String[]{code.split(",")[3]};
			String[] five = new String[]{code.split(",")[4]};
			String[] six = new String[]{code.split(",")[5]};
			String[] seven = new String[]{code.split(",")[6]};
			
			String prize = getPrizeLevel(one, two, three, four, five, six, seven, win, oneAmount);
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
		if(!betcode.matches(qxc_01)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		long zhushu = betcode.split("-")[1].split("\\^").length;
		
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
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
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
				slot.setBetcode("200401-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("200401-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
