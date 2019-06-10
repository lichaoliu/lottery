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

//三不同组合
//100541-01,02,03,04,05^
public class Nxk341 extends Nxk3X{

	Nxk340 k40 = new Nxk340();
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		if (!isSanBuTong(wincode)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		String[] wins = wincode.split(",");
		betcode = betcode.split("-")[1].replace("^", "");
		
		if(betcode.contains(wins[0])&&betcode.contains(wins[1])&&betcode.contains(wins[2])) {
			return LotteryDrawPrizeAwarder.NXK3_SBT.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(k341)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.split("-")[1].replace("^", "");
		
		if(isDuplicate(betcode)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return MathUtils.combine(betcode.split(",").length, 3)*200*beishu.longValue();
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
			s.setAmt(k40.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		List<String> betcodes = Arrays.asList(betcode.split("-")[1].replace("^", "").split(","));
		
		List<List<String>> codeCollection = MathUtils.getCodeCollection(betcodes, 3);
		
		for(List<String> codes:codeCollection) {
			int[] codeints = new int[]{Integer.parseInt(codes.get(0)),Integer.parseInt(codes.get(1)),Integer.parseInt(codes.get(2))};
			Arrays.sort(codeints);
			String sortcode = codeints[0]+","+codeints[1]+","+codeints[2];
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100540-"+sortcode+"^");
			slot.setLotMulti(lotmulti);
			slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
//		StringBuilder sb = new StringBuilder();
//		for(List<String> codes:codeCollection) {
//			int[] codeints = new int[]{Integer.parseInt(codes.get(0)),Integer.parseInt(codes.get(1)),Integer.parseInt(codes.get(2))};
//			Arrays.sort(codeints);
//			String sortcode = "0"+codeints[0]+",0"+codeints[1]+",0"+codeints[2];
//			sb.append(sortcode).append("^");
//			if(sb.toString().split("\\^").length==5) {
//				SplitedLot slot = new SplitedLot();
//				slot.setBetcode("100540-"+sb.toString());
//				slot.setLotMulti(lotmulti);
//				slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//				list.add(slot);
//				sb.delete(0, sb.length());
//			}
//		}
//		if(sb.length()>0) {
//			SplitedLot slot = new SplitedLot();
//			slot.setBetcode("100540-"+sb.toString());
//			slot.setLotMulti(lotmulti);
//			slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//			list.add(slot);
//		}
		
		return list;
	}

}
