package com.lottery.lottype.nxk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

//二同号单选组合
//100512-01,02*03,04^
public class Nxk312 extends Nxk3X{
	
	private Nxk310 k310 = new Nxk310();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		if(!isErTong(wincode)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		
		// 找出二同号
		String ertong = "";
	    String other = "";
	    int[] wincodes = convertToInt(wincode);
		Arrays.sort(wincodes);
		if (wincodes[0] == wincodes[1]) {
			ertong = "" + wincodes[0];
			other = "" + wincodes[2];
		} else {
			ertong = "" + wincodes[2];
			other = "" + wincodes[0];
		}
		betcode = betcode.split("-")[1].replace("^", "");
		if(betcode.split("\\*")[0].contains(ertong)&&betcode.split("\\*")[1].contains(other)) {
			return LotteryDrawPrizeAwarder.NXK3_ET_DAN.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(k312)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.split("-")[1].replace("^", "");
		
		String tong = betcode.split("\\*")[0];
		String butong = betcode.split("\\*")[1];
		
		if(isDuplicate(tong+","+butong)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return tong.split(",").length*butong.split(",").length*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> zhumaList = transform(betcode, lotmulti);
		List<SplitedLot> list = new ArrayList<SplitedLot>();

		for (SplitedLot splitedLot : zhumaList) {

			if (!SplitedLot.isToBeSplitFC(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			} else {
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(),splitedLot.getLotMulti(),50,lotterytype));
			}
		}
		for (SplitedLot s : list) {
			s.setAmt(k310.getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.split("-")[1].replace("^", "");
		
		String[] tongs = betcode.split("\\*")[0].split(",");
		String[] butongs = betcode.split("\\*")[1].split(",");
		StringBuilder realcode = new StringBuilder();
		
		for (String dCodes : tongs) {
			for (String sCodes : butongs) {
				if (Integer.parseInt(dCodes) > Integer.parseInt(sCodes)) {
					realcode.append(sCodes +","+ dCodes +","+ dCodes)
							.append("^");
				} else {
					realcode.append(dCodes +","+ dCodes +","+ sCodes)
							.append("^");
				}
				
				SplitedLot s = new SplitedLot(lotterytype);
				s.setBetcode("100510-"+realcode.toString());
				s.setLotMulti(lotmulti);
				s.setAmt(200 * lotmulti);
				list.add(s);
				realcode.delete(0, realcode.length());
			}
		}

		
//		for (String dCodes : tongs) {
//			for (String sCodes : butongs) {
//				if (Integer.parseInt(dCodes) > Integer.parseInt(sCodes)) {
//					realcode.append(sCodes +","+ dCodes +","+ dCodes)
//							.append("^");
//				} else {
//					realcode.append(dCodes +","+ dCodes +","+ sCodes)
//							.append("^");
//				}
//
//				if (realcode.toString().split("\\^").length == 5) {
//					SplitedLot s = new SplitedLot();
//					s.setBetcode("100510-"+realcode.toString());
//					s.setLotMulti(lotmulti);
//					s.setAmt(200 * 5 * lotmulti);
//					list.add(s);
//					realcode.delete(0, realcode.length());
//				}
//			}
//		}
//		if (realcode.length() > 0) {
//			SplitedLot s = new SplitedLot();
//			s.setBetcode("100510-"+realcode.toString());
//			s.setLotMulti(lotmulti);
//			s.setAmt(200 * (realcode.toString().split("\\^").length) * lotmulti);
//			list.add(s);
//		}
		return list;
	}

}
