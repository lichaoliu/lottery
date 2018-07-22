package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Threed15 extends ThreedX{
	
	private Threed05 td05 = new Threed05();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		betcode = betcode.replace("^", "");
		
		String[] splitcode = betcode.split("-")[1].split("\\|");

		String[] wincodes = wincode.split(",");
		
		int hit = 0;
		for (int i=0;i<splitcode.length;i++) {
			if(splitcode[i].contains(wincodes[i])) {
				hit = hit + 1;
			}
		}
		
		String prize = "";
		if(hit==2) {
			prize = LotteryDrawPrizeAwarder.D3_2D.value;
		}else if(hit==3) {
			prize = LotteryDrawPrizeAwarder.D3_2D.value+","
					+LotteryDrawPrizeAwarder.D3_2D.value+","
					+LotteryDrawPrizeAwarder.D3_2D.value;
		}
		
		return prize;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_15)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		
		String[] splitcodes = betcode.split("-")[1].split("\\|");
		for(String bet:splitcodes) {
			if(!checkDuplicate(bet)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			if(bet.contains("~")&&(!bet.equals("~"))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		
		String regex = "^(0[0-9])([,](0[0-9])){0,9}$";
		int bai_size = splitcodes[0].matches(regex)?splitcodes[0].split(",").length:0;
		int shi_size = splitcodes[1].matches(regex)?splitcodes[1].split(",").length:0;
		int ge_size = splitcodes[2].matches(regex)?splitcodes[2].split(",").length:0;
		
		int zhushu = bai_size*shi_size+bai_size*ge_size+shi_size*ge_size;
		
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
			s.setAmt(td05.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String bai =  betcode.split("-")[1].replace("^", "").split("\\|")[0];
		String shi =  betcode.split("-")[1].replace("^", "").split("\\|")[1];
		String ge =  betcode.split("-")[1].replace("^", "").split("\\|")[2];
		
		List<String> codes = new ArrayList<String>(); 
		
		if((!bai.contains("~"))&&(!shi.contains("~"))) {
			for(String codebai:bai.split(",")) {
				for(String codeshi:shi.split(",")) {
					codes.add(codebai+","+codeshi+",~");
				}
			}
		}
		
		if((!bai.contains("~"))&&(!ge.contains("~"))) {
			for(String codebai:bai.split(",")) {
				for(String codege:ge.split(",")) {
					codes.add(codebai+",~,"+codege);
				}
			}
		}
		
		if((!shi.contains("~"))&&(!ge.contains("~"))) {
			for(String codeshi:shi.split(",")) {
				for(String codege:ge.split(",")) {
					codes.add("~,"+codeshi+","+codege);
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<codes.size();i++) {
			sb.append(codes.get(i)).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("100205-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(td05.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100205-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(td05.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
