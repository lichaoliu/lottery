package com.lottery.lottype.jc.guanjun;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.LotPlayType;
import com.lottery.lottype.SplitedLot;

public class JcGuanJun01 implements LotPlayType{
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return false;
	}

	//302101-01,02,03^
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		return betcode.split("\\-")[1].split(",").length*beishu.longValue()*oneAmount;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode, lotmulti);
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 99,LotteryType.JC_GUANJUN.value));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}
		return list;
		
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] betcodes = betcode.split("-")[1].replace("^", "").split(",");
		
		for(String code:betcodes) {
			SplitedLot slot = new SplitedLot(LotteryType.JC_GUANJUN.value);
			slot.setBetcode("301201-"+code+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		return list;
	}

}
