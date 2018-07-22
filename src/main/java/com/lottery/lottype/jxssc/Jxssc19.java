package com.lottery.lottype.jxssc;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Jxssc19 extends JxsscX{
	
	Jxssc09 jx09 = new Jxssc09();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		String[] win = wincode.split(",");
		StringBuilder prize = new StringBuilder();
		int hit = 0;
		String[] onecodes = betcode.split("-")[1].replace("^", "").split(",");
		for(int i=0;i<=4;i++) {
			if(onecodes[i].contains(win[i])) {
				hit = hit + 1;
			}
		}
		
		if(hit>=2) {
			long select = MathUtils.combine(hit, 2);
			for(int i=0;i<select;i++) {
				prize.append(LotteryDrawPrizeAwarder.JXSSC_2R.value).append(",");
			}
		}
		if(prize.toString().endsWith(",")) {
			prize = prize.deleteCharAt(prize.length()-1);
		}
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		
		if(!betcode.matches(FU_2R)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		String code = betcode.split("\\-")[1].replace("^", "");
		
		int isNumber = 0;
		List<Integer> list = new ArrayList<Integer>();
		for(String onecode:code.split(";")) {
			if(onecode.matches("^[0-9]([,][0-9]){1,9}$")) {
				isNumber = isNumber + 1;
				if(isBetcodeDuplication(onecode)) {
					throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
				}
				list.add(onecode.replace(",", "").length());
			}
		}
		if(isNumber<2||getZhushu(list)<=1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		return getZhushu(list)*beishu.longValue()*200;
	}
	
	
	private int getZhushu(List<Integer> list) {
		int zhushu = 0;
		for(int i=0;i<list.size()-1;i++) {
			for(int j=i+1;j<list.size();j++) {
				zhushu = zhushu + list.get(i)*list.get(j);
			}
		}
		return zhushu;
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
			s.setAmt(jx09.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}
		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		List<String> betcodes = new ArrayList<String>();
		String[] split = betcode.replace("^", "").split("-")[1].split(";");
		for(int i=0;i<split.length-1;i++) {
			for(int j=i+1;j<split.length;j++) {
				if(split[i].equals("~")||split[j].equals("~")) {
					continue;
				}
				betcodes.addAll(getSingleCode(split[i], split[j], i, j));
			}
		}
		
		StringBuilder sb = new StringBuilder();
		for(String code:betcodes) {
			sb.append(code).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("101109-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(jx09.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("101109-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(jx09.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		return list;
	}
	
	
	
	private List<String> getSingleCode(String code1,String code2,int point1,int point2) {
		List<String> list = new ArrayList<String>();
		for(String c1:code1.split(",")) {
			for(String c2:code2.split(",")) {
				StringBuilder singlecode = new StringBuilder();
				for(int i=0;i<=4;i++) {
					if(i==point1) {
						singlecode.append(c1).append(",");
					}else if(i==point2) {
						singlecode.append(c2).append(",");
					}else {
						singlecode.append("~").append(",");
					}
				}
				singlecode = singlecode.deleteCharAt(singlecode.length()-1);
				list.add(singlecode.toString());
			}
		}
		return list;
		
	}
	
}
