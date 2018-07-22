package com.lottery.lottype.gdkl10;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Gdkl10DQ3 extends Gdkl10X{
	
	Gdkl10SQ3 sq3 = new Gdkl10SQ3();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		List<String> wincodes = Arrays.asList(wincode.substring(0, 8).split(","));
		List<String> dans = Arrays.asList(toCodeArray(betcode.split("\\#")[0]));
		List<String> tuos = Arrays.asList(toCodeArray(betcode.split("\\#")[1]));
		
		for(String dan:dans) {
			if(!wincodes.contains(dan)) {
				return LotteryDrawPrizeAwarder.NOT_WIN.value;
			}
		}
		
		List<String> notcontains = new ArrayList<String>();
		
		for(String win:wincodes) {
			if(!dans.contains(win)) {
				notcontains.add(win);
			}
		}
		
		for(String notcontain:notcontains) {
			if(!tuos.contains(notcontain)) {
				return LotteryDrawPrizeAwarder.NOT_WIN.value;
			}
		}
		
		return LotteryDrawPrizeAwarder.GDK10_Q3.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		betcode = betcode.substring(7,betcode.length()-1);
		if(!validateBetcodeContainsLine(betcode,DQ3)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		if(betcode.replace("-", "").replace(",", "").length()<6) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		int dan = betcode.split("\\#")[0].split(",").length;
		int tuo = betcode.split("\\#")[1].split(",").length;
		return MathUtils.combine(tuo, 3-dan)*6*beishu.longValue()*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				int amtSingle = (int) (splitedLot.getAmt() / splitedLot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			if(s.getBetcode().startsWith("101317")) {
				s.setAmt(sq3.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
		}

		return list;
	}
	
	
	private List<SplitedLot> transform(String betcode,int lotmulti) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.replace("^", "");
		
		
		if(betcode.split("-")[1].split("#")[0].replace(",", "").length()==4) {
			String s1 = betcode.split("-")[1].split("#")[0].substring(0, 2);
			String s2 = betcode.split("-")[1].split("#")[0].substring(3, 5);
			String[] hous = betcode.split("-")[1].split("#")[1].split(",");
			for(String s3:hous) {
				String realbetcode = "101317-"+s1+","+s2+","+s3+"^"+s1+","+s3+","+s2+"^"+s2+","+s1+","+s3+"^"+s2+","+s3+","+s1+"^"+s3+","+s1+","+s2+"^";
				SplitedLot lot = new SplitedLot(realbetcode, lotmulti, sq3.getSingleBetAmount(realbetcode, new BigDecimal(lotmulti), 200),lotterytype);
				list.add(lot);
				String realbetcode2 = "101317-"+s3+","+s2+","+s1+"^";
				SplitedLot lot2 = new SplitedLot(realbetcode2, lotmulti, sq3.getSingleBetAmount(realbetcode2, new BigDecimal(lotmulti), 200),lotterytype);
				list.add(lot2);
			}
		}else if(betcode.split("-")[1].split("#")[0].replace(",", "").length()==2) {
			String s1 = betcode.split("-")[1].split("#")[0];
			List<List<String>> codes = MathUtils.getCodeCollection(Arrays.asList(betcode.split("-")[1].split("#")[1].split(",")), 2);
			
			for(List<String> code:codes) {
				String s2 = code.get(0);
				String s3 = code.get(1);
				String realbetcode = "101317-"+s1+","+s2+","+s3+"^"+s1+","+s3+","+s2+"^"+s2+","+s1+","+s3+"^"+s2+","+s3+","+s1+"^"+s3+","+s1+","+s2+"^";
				SplitedLot lot = new SplitedLot(realbetcode, lotmulti, sq3.getSingleBetAmount(realbetcode, new BigDecimal(lotmulti), 200),lotterytype);
				list.add(lot);
				String realbetcode2 = "101317-"+s3+","+s2+","+s1+"^";
				SplitedLot lot2 = new SplitedLot(realbetcode2, lotmulti, sq3.getSingleBetAmount(realbetcode2, new BigDecimal(lotmulti), 200),lotterytype);
				list.add(lot2);
			}
			
		}
		return list;
	}
	
	

}
