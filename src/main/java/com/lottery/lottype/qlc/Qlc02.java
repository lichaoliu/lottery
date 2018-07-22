package com.lottery.lottype.qlc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Qlc02 extends QlcX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.replace("^", "").split("-")[1];
		
		List<List<String>> betcodes = MathUtils.getCodeCollection(Arrays.asList(betcode.split(",")), 7);
		
		StringBuilder sb = new StringBuilder();
		
		for(List<String> list:betcodes) {
			String prize = oneBetPrize(wincode, list);
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
		if(!betcode.matches(qlc_02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.replace("^", "");
		if(!checkDuplicate(betcode.split("-")[1])){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = MathUtils.combine(betcode.split("-")[1].split(",").length, 7);
		
		if(zhushu>10000) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		return zhushu*beishu.longValue()*200;
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
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
			s.setBetcode(getSortCode02(s.getBetcode()));
		}
		return list;
	}

}
