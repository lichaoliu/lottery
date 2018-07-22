package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Threed13 extends ThreedX{
	
	Threed03 d303 = new Threed03();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		betcode = betcode.replace("^", "").split("\\-")[1];
		String[] wincodes = wincode.split(",");
		if(isZuliu(wincode)) {
			if(betcode.contains(wincodes[0])&&betcode.contains(wincodes[1])&&betcode.contains(wincodes[2])) {
				return LotteryDrawPrizeAwarder.D3_3.value;
			}
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_13)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.replace("^", "");
		
		if(!checkDuplicate(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int total = betcode.split("-")[1].split(",").length;
		long zhushu = MathUtils.combine(total, 3);
		return zhushu*beishu.longValue()*200;
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
			if(s.getBetcode().startsWith("100203")) {
				s.setAmt(d303.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
				s.setBetcode(getSortCode03(s.getBetcode()));
			}else {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
				s.setBetcode(getSortCode13(s.getBetcode()));
			}
			
		}

		return list;
	}
	
	
	
	//100213-00,01,02,03,04,05,06,07,08,09^
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		if(betcode.split("-")[1].replace("^", "").split(",").length==10) {
			String[] bets = betcode.split("-")[1].replace("^", "").split(",");
			
			List<List<String>> codeCollections = MathUtils.getCodeCollection(Arrays.asList(bets), 3);
			
			for(List<String> code:codeCollections) {
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setBetcode("100203-"+code.get(0)+","+code.get(1)+","+code.get(2)+"^");
				lot.setLotMulti(lotmulti);
				lot.setAmt(d303.getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(lot);
			}
		}else {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode(betcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(betcode, new BigDecimal(lotmulti), 200));
			list.add(lot);
		}
		
		return list;
	}

}
