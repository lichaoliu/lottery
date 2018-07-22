package com.lottery.lottype.nxk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

//二不同号复选(组合)
//100521-01,02,03,04^
public class Nxk321 extends Nxk3X{

	
	Nxk320 k20 = new Nxk320();
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		if(!isErBuTong(wincode)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		betcode = betcode.split("-")[1].replace("^", "");
		String[] wins = wincode.split(",");
		//两种情况，二同和三不同
		if(isErTong(wincode)) {
			if(betcode.contains(wins[0])&&betcode.contains(wins[1])&&betcode.contains(wins[2])) {
				return LotteryDrawPrizeAwarder.NXK3_EBT_DAN.value;
			}
		}
		
		if(isSanBuTong(wincode)) {
			if (betcode.contains(wins[0]) && betcode.contains(wins[1])
					&& betcode.contains(wins[2])) {
				return LotteryDrawPrizeAwarder.NXK3_EBT_DAN.value 
						+ ","+LotteryDrawPrizeAwarder.NXK3_EBT_DAN.value
						+ ","+LotteryDrawPrizeAwarder.NXK3_EBT_DAN.value;
			} else if ((betcode.contains(wins[0]) && betcode.contains(wins[1]))
					|| (betcode.contains(wins[1]) && betcode.contains(wins[2]))
					|| (betcode.contains(wins[0]) && betcode.contains(wins[2]))) {
				return LotteryDrawPrizeAwarder.NXK3_EBT_DAN.value;
			}
		}
		
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(k321)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.split("-")[1].replace("^", "");
		
		if(isDuplicate(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return MathUtils.combine(betcode.split(",").length, 2)*beishu.longValue()*200;
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
		
		List<String> betcodes = Arrays.asList(betcode.split("-")[1].replace("^", "").split(","));
		
		List<List<String>> codeCollection = MathUtils.getCodeCollection(betcodes, 2);
		
		
		
		for(List<String> codes:codeCollection) {
			String sortcode = Integer.parseInt(codes.get(0))>Integer.parseInt(codes.get(1))?codes.get(1)+","+codes.get(0):codes.get(0)+","+codes.get(1);
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100520-"+sortcode+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(k20.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
//		StringBuilder sb = new StringBuilder();
//		for(List<String> codes:codeCollection) {
//			String sortcode = Integer.parseInt(codes.get(0))>Integer.parseInt(codes.get(1))?codes.get(1)+","+codes.get(0):codes.get(0)+","+codes.get(1);
//			sb.append(sortcode).append("^");
//			if(sb.toString().split("\\^").length==5) {
//				SplitedLot slot = new SplitedLot();
//				slot.setBetcode("100520-"+sb.toString());
//				slot.setLotMulti(lotmulti);
//				slot.setAmt(k20.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//				list.add(slot);
//				sb.delete(0, sb.length());
//			}
//		}
//		if(sb.length()>0) {
//			SplitedLot slot = new SplitedLot();
//			slot.setBetcode("100520-"+sb.toString());
//			slot.setLotMulti(lotmulti);
//			slot.setAmt(k20.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//			list.add(slot);
//		}
		
		return list;
	}

}
