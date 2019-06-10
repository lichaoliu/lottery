package com.lottery.lottype.ahk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

//二不同号胆拖
//100822-01#02,03,04,05,06^
public class Ahk322 extends Ahk3X{
	
	Ahk320 k20 = new Ahk320();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		if(!isErBuTong(wincode)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		betcode = betcode.split("-")[1].replace("^", "");
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		
		//如果胆码不在开奖号码里，不中奖
		if(!wincode.contains(dan)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		
		//找出开奖号码除去胆码的注码
		String[] wincodeLeft = splitCodeString(wincode.replace(",", "").replace(dan, ""));
		if(isErTong(wincode)) {
			for(String code:wincodeLeft) {
				if(!tuo.contains(code)) {
					return LotteryDrawPrizeAwarder.NOT_WIN.value;
				}
			}
			return LotteryDrawPrizeAwarder.AHK3_EBT_DAN.value;
		}else if(isSanBuTong(wincode)) {
			int total = 0;
			if(tuo.contains(wincodeLeft[0])) {
				total = total + 1;
			}
			if(tuo.contains(wincodeLeft[1])) {
				total = total + 1;
			}
			if(total==0) {
				return LotteryDrawPrizeAwarder.NOT_WIN.value;
			}else if(total==1) {
				return LotteryDrawPrizeAwarder.AHK3_EBT_DAN.value;
			}else if(total==2) {
				return LotteryDrawPrizeAwarder.AHK3_EBT_DAN.value+","+LotteryDrawPrizeAwarder.AHK3_EBT_DAN.value;
			}
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(k322)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.split("-")[1].replace("^", "");
		
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		
		if(isDuplicate(dan+","+tuo)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		return tuo.split(",").length*beishu.longValue()*200;
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
			s.setAmt(k20.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		betcode = betcode.split("-")[1].replace("^", "");
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		
		for(String code:tuo.split(",")) {
			String sortcode = Integer.parseInt(dan)>Integer.parseInt(code)?code+","+dan:dan+","+code;
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100820-"+sortcode+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(k20.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
//		StringBuilder sb = new StringBuilder();
//		for(String code:tuo.split(",")) {
//			String sortcode = Integer.parseInt(dan)>Integer.parseInt(code)?code+","+dan:dan+","+code;
//			sb.append(sortcode).append("^");
//			if(sb.toString().split("\\^").length==5) {
//				SplitedLot slot = new SplitedLot();
//				slot.setBetcode("100820-"+sb.toString());
//				slot.setLotMulti(lotmulti);
//				slot.setAmt(k20.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//				list.add(slot);
//				sb.delete(0, sb.length());
//			}
//		}
//		if(sb.length()>0) {
//			SplitedLot slot = new SplitedLot();
//			slot.setBetcode("100820-"+sb.toString());
//			slot.setLotMulti(lotmulti);
//			slot.setAmt(k20.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//			list.add(slot);
//		}
		
		return list;
	}

}
