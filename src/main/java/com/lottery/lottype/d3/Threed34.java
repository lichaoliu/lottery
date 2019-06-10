package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

//猜大小
public class Threed34 extends ThreedX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		int bai = Integer.parseInt(wincode.split(",")[0]);
		int shi = Integer.parseInt(wincode.split(",")[1]);
		int ge = Integer.parseInt(wincode.split(",")[2]);
		
		StringBuilder prize = new StringBuilder();
		
		for(String bet:betcode.split("\\-")[1].replace("^", "").split(",")) {
			if(bai>0&&shi>0&&ge>0){
				if(bai%2==1&&shi%2==1&&ge%2==1&&bet.equals("05")) {
					prize.append(LotteryDrawPrizeAwarder.D3_JO.value).append(",");
				}else if(bai%2==0&&shi%2==0&&ge%2==0&&bet.equals("04")) {
					prize.append(LotteryDrawPrizeAwarder.D3_JO.value).append(",");
				}
			}
		}
		check2delete(prize);
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_34)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.replace("^", "");
		
		if(!checkDuplicate(betcode.split("-")[1])) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = betcode.split("-")[1].split(",").length;
		
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
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), 50,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}

	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.replace("^", "");
		String[] betcodes = betcode.split("-")[1].split(",");
		
		for(String code:betcodes) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100234-"+code+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		return list;
	}

}
