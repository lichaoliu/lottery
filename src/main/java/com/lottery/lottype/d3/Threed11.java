package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Threed11 extends ThreedX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		betcode = betcode.replace("^", "");
		
		String bai = betcode.split("-")[1].split("\\|")[0];
		String shi = betcode.split("-")[1].split("\\|")[1];
		String ge = betcode.split("-")[1].split("\\|")[2];
		String[] wincodes = wincode.split(",");
		
		if(bai.contains(wincodes[0])&&shi.contains(wincodes[1])&&ge.contains(wincodes[2])) {
			return LotteryDrawPrizeAwarder.D3_1.value;
		}
		return "";
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_11)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String bai = betcode.split("-")[1].split("\\|")[0];
		String shi = betcode.split("-")[1].split("\\|")[1];
		String ge = betcode.split("-")[1].split("\\|")[2];
		
		if(!checkDuplicate(bai)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(shi)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(ge)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = bai.split(",").length*shi.split(",").length*ge.split(",").length;
		
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
			s.setBetcode(getSortCod11(s.getBetcode()));
		}
		return list;
	}

}
