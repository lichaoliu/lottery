package com.lottery.lottype.ssq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Ssq02 extends SsqX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		String winRed = wincode.split("\\|")[0];
		String winBlue = wincode.split("\\|")[1];
		
		betcode = betcode.replace("^", "");
		String redCode = betcode.split("-")[1].split("\\|")[0];
		String blueCode = betcode.split("-")[1].split("\\|")[1];
		
		List<List<String>> redBetcodes = MathUtils.getCodeCollection(Arrays.asList(redCode.split(",")), 6);
		
		List<String> blueBetcodes = Arrays.asList(blueCode.split(","));
		
		StringBuilder sb = new StringBuilder();
		for(List<String> list:redBetcodes) {
			for(String blue:blueBetcodes) {
				String prizeinfo = caculatePrize(joinBetcode(list), blue, winRed, winBlue);
				if(!StringUtil.isEmpty(prizeinfo)) {
					sb.append(prizeinfo).append(",");
				}
			}
		}
		
		check2delete(sb);
		
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(ssq_02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String red = betcode.split("-")[1].split("\\|")[0];
		String blue = betcode.split("-")[1].split("\\|")[1];
		if(!checkDuplicate(red)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(blue)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = MathUtils.combine(red.split(",").length, 6)*blue.split(",").length;
		
		if(zhushu==1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
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
				int amtSingle = (int) (splitedLot.getAmt() / splitedLot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>50) {
					permissionLotmulti = 50;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			s.setBetcode(getSortCode02(s.getBetcode()));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		betcode = betcode.replace("^", "");
		
		String red = betcode.split("-")[1].split("\\|")[0];
		String blue = betcode.split("-")[1].split("\\|")[1];
		
		long zhushuRed = MathUtils.combine(red.split(",").length, 6);
		long zhushuBlue = blue.split(",").length;
		
		if(zhushuRed*zhushuBlue<=10000) {
			SplitedLot lot = new SplitedLot(betcode+"^", lotmulti, getSingleBetAmount(betcode+"^", new BigDecimal(lotmulti), 200),lotterytype);
			list.add(lot);
		}else {
			int selectBlue = (int) (10000/zhushuRed);
			List<String> selectCodes = getSelectCodes(blue, selectBlue);
			for(String select:selectCodes) {
				String realcode = "100102-"+red+"|"+select+"^";
				SplitedLot lot = new SplitedLot(realcode, lotmulti, getSingleBetAmount(realcode, new BigDecimal(lotmulti), 200),lotterytype);
				list.add(lot);
			}
		}
		return list;
	}

}
