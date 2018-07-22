package com.lottery.lottype.dlt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Dlt02 extends DltX{

	 

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(dlt_02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String qian = betcode.split("-")[1].split("\\|")[0];
		String hou = betcode.split("-")[1].split("\\|")[1];
		if(!checkDuplicate(qian)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(hou)){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		long zhushu = MathUtils.combine(qian.split(",").length, 5)*MathUtils.combine(hou.split(",").length, 2);
		
		if(zhushu==1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return zhushu*oneAmount*beishu.longValue();
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
				int amtSingle = (int) (splitedLot.getAmt()/oneAmount*200 / splitedLot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), oneAmount));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti,int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		betcode = betcode.replace("^", "");
		
		String qian = betcode.split("-")[1].split("\\|")[0];
		String hou = betcode.split("-")[1].split("\\|")[1];
		
		long zhushuQian = MathUtils.combine(qian.split(",").length, 5);
		long zhushuHou = MathUtils.combine(hou.split(",").length, 2);
		
		if(zhushuQian*zhushuHou<=10000) {
			SplitedLot lot = new SplitedLot(betcode+"^", lotmulti, getSingleBetAmount(betcode+"^", new BigDecimal(lotmulti), oneAmount),lotterytype);
			list.add(lot);
		}else {
			List<List<String>> selectCodes = MathUtils.getCodeCollection(Arrays.asList(hou.split(",")), 2);
			for(List<String> codes:selectCodes) {
				String realcode = "200102-"+qian+"|"+codes.get(0)+","+codes.get(1)+"^";
				SplitedLot lot = new SplitedLot(realcode, lotmulti, getSingleBetAmount(realcode, new BigDecimal(lotmulti), oneAmount),lotterytype);
				list.add(lot);
			}
		}
		return list;
	}

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		betcode = betcode.replace("^", "");
		boolean addition = oneAmount==300?true:false;
		StringBuilder sb = new StringBuilder();
		String betcodeQ = betcode.split("-")[1].split("\\|")[0];
		String betcodeH = betcode.split("-")[1].split("\\|")[1];
		
		List<List<String>> qians = MathUtils.getCodeCollection(Arrays.asList(betcodeQ.split(",")), 5);
		List<List<String>> hous = MathUtils.getCodeCollection(Arrays.asList(betcodeH.split(",")), 2);
		
		for(List<String> qian:qians) {
			for(List<String> hou:hous) {
				String prize = getPrize(qian, hou, wincode.split("\\|")[0].split(","), wincode.split("\\|")[1].split(","), addition);
				if(!StringUtil.isEmpty(prize)) {
					sb.append(prize).append(",");
				}
			}
		}
		check2delete(sb);
		return sb.toString();
	}

}
