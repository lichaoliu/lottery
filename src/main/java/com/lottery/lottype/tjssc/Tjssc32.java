package com.lottery.lottype.tjssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Tjssc32 extends TjsscX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String win0 = wincode.split(",")[0];
		String win1 = wincode.split(",")[1];
		String win2 = wincode.split(",")[2];
		String win3 = wincode.split(",")[3];
		String win4 = wincode.split(",")[4];
		
		String[] codes = betcode.split("\\-")[1].replace("^", "").split(";");
		StringBuilder sb = new StringBuilder();
		
		int hit = 0;
		
		if(codes[0].contains(win0)) {
			hit = hit + 1;
		}
		if(codes[1].contains(win1)) {
			hit = hit + 1;
		}
		if(codes[2].contains(win2)) {
			hit = hit + 1;
		}
		if(codes[3].contains(win3)) {
			hit = hit + 1;
		}
		if(codes[4].contains(win4)) {
			hit = hit + 1;
		}
		
		long total = 0;
		if(hit>=2) {
			total = MathUtils.combine(hit, 2);
			for(int i=0;i<total;i++) {
				sb.append(LotteryDrawPrizeAwarder.TJSSC_2R.value).append(",");
			}
		}
		
		if(sb.toString().endsWith(",")) {
			sb = sb.deleteCharAt(sb.length()-1);
		}
		
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(OTHER_R2)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		String[] codes = betcode.split("\\-")[1].replace("^", "").split(";");
		
		int isNumber = 0;
		List<Integer> list = new ArrayList<Integer>();
		for(String code:codes) {
			if(code.length()>1&&code.contains("~")) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			
			if(code.matches("[0-9]([,][0-9]){0,9}")) {
				isNumber = isNumber + 1;
				list.add(code.split(",").length);
				if(isBetcodeDuplication(code)) {
					throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
				}
			}
		}
		if(isNumber<2) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		int zhushu = 0;
		for(int i=0;i<list.size()-1;i++) {
			for(int j=i+1;j<list.size();j++) {
				zhushu = zhushu + list.get(i)*list.get(j);
			}
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
			for(SplitedLot s:list) {
				s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
			}
		}
		return list;
	}
}
