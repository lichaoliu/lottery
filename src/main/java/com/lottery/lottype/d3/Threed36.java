package com.lottery.lottype.d3;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryDrawPrizeAwarder;
import com.lottery.common.exception.LotteryException;
import com.lottery.lottype.SplitedLot;

//猜三同
public class Threed36 extends ThreedX{

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,int oneAmount) {
		
		int bai = Integer.parseInt(wincode.split(",")[0]);
		int shi = Integer.parseInt(wincode.split(",")[1]);
		int ge = Integer.parseInt(wincode.split(",")[2]);
		
		if((bai-shi==1&&shi-ge==1)||(ge-shi==1&&shi-bai==1)) {
			return LotteryDrawPrizeAwarder.D3_TLJ.value;
		}
		return LotteryDrawPrizeAwarder.NOT_WIN.value;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		
		if(!betcode.equals("100236-00^")) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return beishu.longValue()*200;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		long amt = getSingleBetAmount(betcode,new BigDecimal(lotmulti),oneAmount);
		if(!SplitedLot.isToBeSplitFC(lotmulti,amt)) {
			list.add(new SplitedLot(betcode,lotmulti,amt,lotterytype));
		}else {
			int amtSingle = (int) (amt / lotmulti);
			int permissionLotmulti = 2000000 / amtSingle;
			if(permissionLotmulti > 50) {
				permissionLotmulti = 50;
			}
			list.addAll(SplitedLot.splitToPermissionMulti(betcode, lotmulti, permissionLotmulti,lotterytype));
		}
		for(SplitedLot s:list) {
			s.setAmt(getSingleBetAmount(s.getBetcode(),new BigDecimal(s.getLotMulti()),oneAmount));
		}
		return list;
	}


}
