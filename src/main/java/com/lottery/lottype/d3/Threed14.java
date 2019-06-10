package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Threed14 extends ThreedX{
	
	private Threed04 td04 = new Threed04();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		betcode = betcode.replace("^", "");
		
		String bai = betcode.split("-")[1].split("\\|")[0];
		String shi = betcode.split("-")[1].split("\\|")[1];
		String ge = betcode.split("-")[1].split("\\|")[2];
		String[] wincodes = wincode.split(",");
		
		
		StringBuilder prize = new StringBuilder();
		if(bai.contains(wincodes[0])) {
			prize.append(LotteryDrawPrizeAwarder.D3_1D.value).append(",");
		}
		if(shi.contains(wincodes[1])) {
			prize.append(LotteryDrawPrizeAwarder.D3_1D.value).append(",");
		}
		if(ge.contains(wincodes[2])) {
			prize.append(LotteryDrawPrizeAwarder.D3_1D.value).append(",");
		}
		check2delete(prize);
		return prize.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_14)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		
		for(String bet:betcode.split("-")[1].split("\\|")) {
			if(!checkDuplicate(bet)) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			if(bet.contains("~")&&(!bet.equals("~"))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		int zhushu = 0;
		for(String bet:betcode.split("-")[1].split("\\|")) {
			if(bet.matches("^(0[0-9])([,](0[0-9])){0,9}$")) {
				zhushu = zhushu + bet.split(",").length;
			}
		}
		
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
			s.setAmt(td04.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String bai =  betcode.split("-")[1].replace("^", "").split("\\|")[0];
		String shi =  betcode.split("-")[1].replace("^", "").split("\\|")[1];
		String ge =  betcode.split("-")[1].replace("^", "").split("\\|")[2];
		
		List<String> codes = new ArrayList<String>(); 
		
		if(!bai.contains("~")) {
			for(String code:bai.split(",")) {
				codes.add(code+",~,~");
			}
		}
		if(!shi.contains("~")) {
			for(String code:shi.split(",")) {
				codes.add("~,"+code+",~");
			}
		}
		if(!ge.contains("~")) {
			for(String code:ge.split(",")) {
				codes.add("~,~,"+code);
			}
		}

		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<codes.size();i++) {
			sb.append(codes.get(i)).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("100204-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(td04.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100204-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(td04.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
