package com.lottery.lottype.xjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Xjssc18 extends XjsscX{
	
	Xjssc08 ssc08 = new Xjssc08();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] codes = betcode.replace("^", "").split("\\-")[1].split(";");
		List<List<String>> onecodes = new ArrayList<List<String>>();
		
		for(String code:codes) {
			onecodes.add(Arrays.asList(code.split(",")));
		}
		
		List<String> singleCode = new ArrayList<String>();
		for(int i=0;i<onecodes.size()-1;i++) {
			if(onecodes.get(i).contains("~")) {
				continue;
			}
			for(int j=i+1;j<onecodes.size();j++) {
				if(onecodes.get(j).contains("~")) {
					continue;
				}
				for(String code1:onecodes.get(i)) {
					for(String code2:onecodes.get(j)) {
						singleCode.add(buildCode(code1, code2, i, j));
					}
				}
			}
		}
		
		StringBuilder prize = new StringBuilder();
		for(String code:singleCode) {
			String level = ssc08.caculatePrizeLevel(code, wincode, oneAmount);
			if(StringUtil.isNotEmpt(level)) {
				prize.append(level).append(",");
			}
		}
		
		if(prize.toString().endsWith(",")) {
			prize = prize.deleteCharAt(prize.length()-1);
		}
		return prize.toString();
	}
	
	
	private String buildCode(String code1,String code2,int point1,int point2) {
		String[] empty = new String[]{"~","~","~","~","~"};
		empty[point1] = code1;
		empty[point2] = code2;
		return "101408-"+empty[0]+","+empty[1]+","+empty[2]+","+empty[3]+","+empty[4];
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(FU_R2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		int notNumber = 0;
		String[] codes = betcode.replace("^", "").split("\\-")[1].split(";");
		List<Integer> list = new ArrayList<Integer>();
		
		for(String code:codes) {
			if("~".equals(code)) {
				notNumber = notNumber + 1;
			}else {
				if(isBetcodeDuplication(code)) {
					throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
				}
				list.add(code.split(",").length);
			}
		}
		
		if(notNumber>3) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = 0l;
		for(int i=0;i<list.size()-1;i++) {
			for(int j=i+1;j<list.size();j++) {
				zhushu = zhushu + list.get(i)*list.get(j);
			}
		}
		
		if(zhushu==1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
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
