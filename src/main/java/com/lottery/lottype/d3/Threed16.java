package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

public class Threed16 extends ThreedX{
	
	private Threed06 t06 = new Threed06();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		wincode = transferWincode(wincode);
		betcode = betcode.replace("^", "");
		
		String[] bais = betcode.split("-")[1].split("\\|")[0].split(",");
		String[] shis = betcode.split("-")[1].split("\\|")[1].split(",");
		String[] ges = betcode.split("-")[1].split("\\|")[2].split(",");
		String[] wincodes = wincode.split(",");
		
		StringBuilder sb = new StringBuilder();
		
		for(String bai:bais) {
			for(String shi:shis) {
				for(String ge:ges) {
					if(bai.equals(wincodes[0])&&shi.equals(wincodes[1])&&ge.equals(wincodes[2])) {
						sb.append(LotteryDrawPrizeAwarder.D3_3T1.value).append(",");
					}else if((bai.equals(wincodes[0])&&shi.equals(wincodes[1]))
							||(shi.equals(wincodes[1])&&ge.equals(wincodes[2]))
							||(bai.equals(wincodes[0])&&ge.equals(wincodes[2]))) {
						sb.append(LotteryDrawPrizeAwarder.D3_3T2.value).append(",");
					}
				}
			}
		}
		
		check2delete(sb);
		return sb.toString();
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(d3_16)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String bai = betcode.split("-")[1].split("\\|")[0];
		String shi = betcode.split("-")[1].split("\\|")[1];
		String ge = betcode.split("-")[1].split("\\|")[2];
		
		if(!checkDuplicate(bai)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(shi)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(!checkDuplicate(ge)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int zhushu = bai.split(",").length*shi.split(",").length*ge.split(",").length;
		
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
			s.setAmt(t06.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		
		String[] bais =  betcode.split("-")[1].replace("^", "").split("\\|")[0].split(",");
		String[] shis =  betcode.split("-")[1].replace("^", "").split("\\|")[1].split(",");
		String[] ges =  betcode.split("-")[1].replace("^", "").split("\\|")[2].split(",");
		
		List<String> codes = new ArrayList<String>(); 
		
		for(String bai:bais) {
			for(String shi:shis) {
				for(String ge:ges) {
					codes.add(bai+","+shi+","+ge);
				}
			}
		}

		StringBuilder sb = new StringBuilder();
		
		for(int i=0;i<codes.size();i++) {
			sb.append(codes.get(i)).append("^");
			if(sb.toString().split("\\^").length==5) {
				SplitedLot slot = new SplitedLot(lotterytype);
				slot.setBetcode("100206-"+sb.toString());
				slot.setLotMulti(lotmulti);
				slot.setAmt(t06.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
				list.add(slot);
				sb.delete(0, sb.length());
			}
		}
		
		if(sb.length()>0) {
			SplitedLot slot = new SplitedLot(lotterytype);
			slot.setBetcode("100206-"+sb.toString());
			slot.setLotMulti(lotmulti);
			slot.setAmt(t06.getSingleBetAmount(slot.getBetcode(), new BigDecimal(lotmulti), 200));
			list.add(slot);
		}
		
		return list;
	}

}
