package com.lottery.lottype.ssq;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Ssq03 extends SsqX{
	
	Ssq02 ssq02 = new Ssq02();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		String winRed = wincode.split("\\|")[0];
		String winBlue = wincode.split("\\|")[1];
		
		betcode = betcode.replace("^", "");
		String redCodeDan = betcode.split("-")[1].split("\\|")[0].split("#")[0];
		String redCodeTuo = betcode.split("-")[1].split("\\|")[0].split("#")[1];
		
		String blueCode = betcode.split("-")[1].split("\\|")[1];
		
		
		List<List<String>> redBetcodes = MathUtils.getCodeCollection(Arrays.asList(redCodeTuo.split(",")), 6-redCodeDan.split(",").length);
		
		List<String> blueBetcodes = Arrays.asList(blueCode.split(","));
		
		StringBuilder sb = new StringBuilder();
		
		for(List<String> list:redBetcodes) {
			for(String blue:blueBetcodes) {
				String prizeinfo = caculatePrize(redCodeDan+","+joinBetcode(list), blue, winRed, winBlue);
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
		if(!betcode.matches(ssq_03)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String redDan = betcode.split("-")[1].split("\\|")[0].split("#")[0];
		String redTuo = betcode.split("-")[1].split("\\|")[0].split("#")[1];
		String blue = betcode.split("-")[1].split("\\|")[1];
		
		if(!checkDuplicate(redDan)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(redTuo)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(redDan+","+redTuo)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(blue)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		if(redDan.split(",").length+redTuo.split(",").length<6) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long redZhushu = MathUtils.combine(redTuo.split(",").length, 6-redDan.split(",").length);
		long blueZhushu = blue.split(",").length;
		
		if(redZhushu>10000) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		//胆拖注码不能小于等于1
		if(redZhushu*blueZhushu<=1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return redZhushu*blueZhushu*200*beishu.longValue();
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
			if(s.getBetcode().startsWith("100102")) {
				s.setAmt(ssq02.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
				s.setBetcode(getSortCode02(s.getBetcode()));
			}else {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
				s.setBetcode(getSortCode03(s.getBetcode()));
			}
			
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		betcode = betcode.replace("^", "");
		
		String redDan = betcode.split("-")[1].split("\\|")[0].split("#")[0];
		String redTuo = betcode.split("-")[1].split("\\|")[0].split("#")[1];
		String red = betcode.split("-")[1].split("\\|")[0];
		String blue = betcode.split("-")[1].split("\\|")[1];
		
		if(redDan.split(",").length+redTuo.split(",").length==6) {
			String realbetcode = "100102-"+red.replace("#", ",")+"|"+blue+"^";
			SplitedLot lot = new SplitedLot(realbetcode, lotmulti, ssq02.getSingleBetAmount(realbetcode, new BigDecimal(lotmulti), 200),lotterytype);
			list.add(lot);
			return list;
		}
		
		long zhushuRed = MathUtils.combine(redTuo.split(",").length, 6-redDan.split(",").length);
		long zhushuBlue = blue.split(",").length;
		
		if(zhushuRed*zhushuBlue<=10000) {
			SplitedLot lot = new SplitedLot(betcode+"^", lotmulti, getSingleBetAmount(betcode+"^", new BigDecimal(lotmulti), 200),lotterytype);
			list.add(lot);
		}else {
			int selectBlue = (int) (10000/zhushuRed);
			List<String> selectCodes = getSelectCodes(blue, selectBlue);
			for(String select:selectCodes) {
				String realcode = "100103-"+red+"|"+select+"^";
				SplitedLot lot = new SplitedLot(realcode, lotmulti, getSingleBetAmount(realcode, new BigDecimal(lotmulti), 200),lotterytype);
				list.add(lot);
			}
		}
		return list;
	}

}
