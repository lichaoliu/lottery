package com.lottery.lottype.ahk3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

//三不同胆拖
//100842-01#02,03,04^
//100842-01,02#03,04,05^
public class Ahk342 extends Ahk3X{

	Ahk340 k40 = new Ahk340();
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		if (!isSanBuTong(wincode)) {
			return LotteryDrawPrizeAwarder.NOT_WIN.value;
		}
		betcode = betcode.split("-")[1].replace("^", "");
		
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		//判断是否胆码全部猜中
		for(String dcode:dan.split(",")) {
			if(!wincode.contains(dcode)) {
				return LotteryDrawPrizeAwarder.NOT_WIN.value;
			}
		}
		//开奖号码中去除胆码
		String[] wins = wincode.split(",");
		for(String dcode:dan.split(",")) {
			if(wins[0].equals(dcode)) {
				wins[0] = "00";
			}else if(wins[1].equals(dcode)) {
				wins[1] = "00";
			}else if(wins[2].equals(dcode)) {
				wins[2] = "00";
			}
		}
		
		//判断开奖号码除去胆码之外的，托码是否全包含
		for(String win:wins) {
			if(!win.equals("00")) {
				if(tuo.contains(win)) {
					continue;
				}else {
					return LotteryDrawPrizeAwarder.NOT_WIN.value;
				}
			}
		}
		return LotteryDrawPrizeAwarder.AHK3_SBT.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(k342)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		betcode = betcode.split("-")[1].replace("^", "");
		
		String dan = betcode.split("\\#")[0];
		String tuo = betcode.split("\\#")[1];
		
		if(isDuplicate(dan+","+tuo)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int danLength = dan.split(",").length;
		int tuoLength = tuo.split(",").length;
		
		long zhushu = MathUtils.combine(tuoLength, 3-danLength);
		if(zhushu<=1) {
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
		
		betcode = betcode.split("-")[1].replace("^", "");
		List<String> dans = Arrays.asList(betcode.split("\\#")[0].split(","));
		List<String> tuos = Arrays.asList(betcode.split("\\#")[1].split(","));
		
		
		
		if(dans.size()==1) {
			List<List<String>> codeCollection = MathUtils.getCodeCollection(tuos, 2);
			
			for(List<String> codes:codeCollection) {
				int[] sortints = new int[]{Integer.parseInt(dans.get(0)),Integer.parseInt(codes.get(0)),Integer.parseInt(codes.get(1))};
				Arrays.sort(sortints);
				String sortcode = sortints[0]+","+sortints[1]+","+sortints[2];
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("100840-"+sortcode+"^");
				slot.setLotMulti(lotmulti);
				slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
			}
//			for(List<String> codes:codeCollection) {
//				int[] sortints = new int[]{Integer.parseInt(dans.get(0)),Integer.parseInt(codes.get(0)),Integer.parseInt(codes.get(1))};
//				Arrays.sort(sortints);
//				String sortcode = "0"+sortints[0]+",0"+sortints[1]+",0"+sortints[2];
//				sb.append(sortcode).append("^");
//				if(sb.toString().split("\\^").length==5) {
//					SplitedLot slot = new SplitedLot();
//					slot.setBetcode("100840-"+sb.toString());
//					slot.setLotMulti(lotmulti);
//					slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//					list.add(slot);
//					sb.delete(0, sb.length());
//				}
//			}
//			if(sb.length()>0) {
//				SplitedLot slot = new SplitedLot();
//				slot.setBetcode("100840-"+sb.toString());
//				slot.setLotMulti(lotmulti);
//				slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//				list.add(slot);
//			}
		}else {
			
			for(String tuo:tuos) {
				int[] sortints = new int[]{Integer.parseInt(dans.get(0)),Integer.parseInt(dans.get(1)),Integer.parseInt(tuo)};
				Arrays.sort(sortints);
				String sortcode = sortints[0]+","+sortints[1]+","+sortints[2];
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("100840-"+sortcode+"^");
				slot.setLotMulti(lotmulti);
				slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
			}
			
//			for(String tuo:tuos) {
//				int[] sortints = new int[]{Integer.parseInt(dans.get(0)),Integer.parseInt(dans.get(1)),Integer.parseInt(tuo)};
//				Arrays.sort(sortints);
//				String sortcode = "0"+sortints[0]+",0"+sortints[1]+",0"+sortints[2];
//				sb.append(sortcode).append("^");
//				if(sb.toString().split("\\^").length==5) {
//					SplitedLot slot = new SplitedLot();
//					slot.setBetcode("100840-"+sb.toString());
//					slot.setLotMulti(lotmulti);
//					slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//					list.add(slot);
//					sb.delete(0, sb.length());
//				}
//			}
//			if(sb.length()>0) {
//				SplitedLot slot = new SplitedLot();
//				slot.setBetcode("100840-"+sb.toString());
//				slot.setLotMulti(lotmulti);
//				slot.setAmt(k40.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
//				list.add(slot);
//			}
		}
		return list;
	}

}
