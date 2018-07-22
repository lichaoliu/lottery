package com.lottery.lottype.plw;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Plw02 extends PlwX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		wincode = transferWincode(wincode);
		betcode = betcode.replace("^", "");
		
		String w = betcode.split("-")[1].split("\\|")[0];
		String q = betcode.split("-")[1].split("\\|")[1];
		String b = betcode.split("-")[1].split("\\|")[2];
		String s = betcode.split("-")[1].split("\\|")[3];
		String g = betcode.split("-")[1].split("\\|")[4];
		String[] wincodes = wincode.split(",");
		
		if(w.contains(wincodes[0])&&q.contains(wincodes[1])&&b.contains(wincodes[2])&&s.contains(wincodes[3])&&g.contains(wincodes[4])) {
			return LotteryDrawPrizeAwarder.P5_1.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(p5_02)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String wan = betcode.split("-")[1].split("\\|")[0];
		String qian = betcode.split("-")[1].split("\\|")[1];
		String bai = betcode.split("-")[1].split("\\|")[2];
		String shi = betcode.split("-")[1].split("\\|")[3];
		String ge = betcode.split("-")[1].split("\\|")[4];
		
		if(!checkDuplicate(wan)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(qian)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(bai)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(shi)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(ge)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = wan.split(",").length*qian.split(",").length*bai.split(",").length*shi.split(",").length*ge.split(",").length;
		if(zhushu==1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
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
				long amt = getSingleBetAmount(splitedLot.getBetcode(), new BigDecimal(lotmulti), 200);
				int amtSingle = (int) (amt / lotmulti);
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti > 99) {
					permissionLotmulti = 99;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		if(getSingleBetAmount(betcode, BigDecimal.ONE, 200)<=2000000) {
			SplitedLot lot = new SplitedLot(lotterytype);
			lot.setBetcode(betcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(betcode, new BigDecimal(lotmulti), 200));
			list.add(lot);
		}else {
			String[] betcodes = betcode.split("-")[1].replace("^", "").split("\\|");
			
			for(String code:betcodes[4].split(",")) {
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setBetcode("200302-"+betcodes[0]+"|"+betcodes[1]+"|"+betcodes[2]+"|"+betcodes[3]+"|"+code+"^");
				lot.setLotMulti(lotmulti);
				lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(lot);
			}
		}
		return list;
	}
	


}
