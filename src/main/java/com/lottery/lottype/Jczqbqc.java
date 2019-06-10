package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;
import com.lottery.lottype.jc.JingcaiCanCal;
import com.lottery.lottype.jc.JingcaiCodeItem;
import com.lottery.lottype.jc.JingcaiResult;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc11001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc12001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc13001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc13003;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc13004;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc14001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc14004;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc14005;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc14006;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc14011;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc22001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc23001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc23003;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc23004;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc24001;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc24004;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc24005;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc24006;
import com.lottery.lottype.jc.jczq.bqc.Jczqbqc24011;

@Component("3009")
public class Jczqbqc extends AbstractJingcaiLot {
	
	private Logger logger = LoggerFactory.getLogger(Jczqbqc.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300911001", new Jczqbqc11001());
		map.put("300912001", new Jczqbqc12001());
		map.put("300913001", new Jczqbqc13001());
		map.put("300913003", new Jczqbqc13003());
		map.put("300913004", new Jczqbqc13004());
		map.put("300914001", new Jczqbqc14001());
		map.put("300914004", new Jczqbqc14004());
		map.put("300914005", new Jczqbqc14005());
		map.put("300914006", new Jczqbqc14006());
		map.put("300914011", new Jczqbqc14011());
		
		
		
		map.put("300922001", new Jczqbqc22001());
		map.put("300923001", new Jczqbqc23001());
		map.put("300923003", new Jczqbqc23003());
		map.put("300923004", new Jczqbqc23004());
		map.put("300924001", new Jczqbqc24001());
		map.put("300924004", new Jczqbqc24004());
		map.put("300924005", new Jczqbqc24005());
		map.put("300924006", new Jczqbqc24006());
		map.put("300924011", new Jczqbqc24011());
		
	}

	@Override
	public boolean validate(String betcode, BigDecimal amount,
			BigDecimal beishu, int oneAmount) {
		if(validateBasicBetcode(betcode)==false) {
			return false;
		}
		long totalAmt = 0;
		
		for(String code:betcode.split("!")) {
			LotPlayType type = map.get(code.split("-")[0]);
			if(null==type) {
				logger.error("注码金额校验错误betcode={} beishu={} amount={} oneAmout={},玩法不存在",new Object[]{betcode,beishu.intValue(),amount,oneAmount});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalAmt = totalAmt + type.getSingleBetAmount(code, beishu, oneAmount);
		}
		
		//验证是否注码拆分后，会有一倍大于20000元的注码。
		split(betcode, beishu.intValue(), amount.longValue(), oneAmount);
		
		return amount.longValue()==totalAmt;
	}

	@Override
	public List<SplitedLot> split(String betcode, int lotmulti, long amt,
			int oneAmount) {
		List<SplitedLot> splitedLots = new ArrayList<SplitedLot>();
		for(String code:betcode.split("!")) {
			splitedLots.addAll(map.get(code.split("-")[0]).splitByType(code, lotmulti, oneAmount));
		}
		
		long totalamt = 0L;
		for(SplitedLot slot:splitedLots) {
			if(slot.getAmt()>2000000||slot.getLotMulti()>99) {
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{slot.getBetcode(),String.valueOf(slot.getLotMulti()),String.valueOf(slot.getAmt())});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalamt = totalamt + slot.getAmt();
		}
		
		if(totalamt!=amt) {
			logger.error("split amt_equal totalAmt={} amt={}",new Object[]{String.valueOf(totalamt),String.valueOf(amt)});
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		return splitedLots;
	}

	@Override
	public boolean isBigPrize(String prizeinfo, BigDecimal preprizeamt,
			BigDecimal afterprizeamt) {
		if(preprizeamt.compareTo(new BigDecimal(1000000))>=0) {
			return true;
		}
		return false;
	}

	@Override
	public List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,String betcode, String odds) {
		JingcaiCanCal cancal = jczqCanCal(betcode, odds);
		if(cancal.isCanCal()==false) {
//			lotteryOrderDao.updateOrderLastMatchNum(orderid, cancal.getNotOpenMatchid());
			logger.error("票{}未全部开奖,退出算奖,本次未开奖的场次为{}",new Object[]{orderid,cancal.getNotOpenMatchid()});
			return null;
		}
		
		List<JingcaiPrizeResult> results = new ArrayList<JingcaiPrizeResult>();
		String type = betcode.split("\\-")[0].substring(5);
		
		List<List<JingcaiCodeItem>> itemN1s = splitJingcaiItemToN1(cancal.getCodeItems(), Integer.parseInt(type));
		
		for(List<JingcaiCodeItem> items:itemN1s) {
			int cancelcount = 0;
			BigDecimal cancelzhushu = BigDecimal.ONE;
			BigDecimal amt = BigDecimal.ONE;
			
			for(JingcaiCodeItem item:items) {
				JingcaiResult jingcaiResult = cancal.getResults().get(item.getMatchid());
				if(jingcaiResult.isCancel()) {
					cancelzhushu = cancelzhushu.multiply(new BigDecimal(item.getCodeItem().size()));
					cancelcount = cancelcount + 1;
				}else {
					String prizecode = jingcaiResult.getResult_static();
					if(item.getCodeItem().containsKey(prizecode)) {
						amt = amt.multiply(new BigDecimal(item.getCodeItem().get(prizecode)));
					}else {
						amt = BigDecimal.ZERO;
						break;
					}
				}
			}
			
			if(amt.compareTo(BigDecimal.ZERO)==1) {
				if(cancelcount>0) {
					for(int i=0;i<cancelzhushu.intValue();i++) {
						int typecount = items.size() - cancelcount;
						JingcaiPrizeResult result = new JingcaiPrizeResult();
						result.setType(typecount*1000+1);
						result.setPrize(amt.compareTo(BigDecimal.ONE)==-1?BigDecimal.ONE:amt);
						//乘以200
						result.setPrize(result.getPrize().multiply(new BigDecimal(200)));
						results.add(result);
					}
				}else {
					JingcaiPrizeResult result = new JingcaiPrizeResult();
					result.setType(items.size()*1000+1);
					result.setPrize(amt.compareTo(BigDecimal.ONE)==-1?BigDecimal.ONE:amt);
					//乘以200
					result.setPrize(result.getPrize().multiply(new BigDecimal(200)));
					results.add(result);
				}
			}
		}
		return results;
	}
	
	
	@Override
	public LotteryType getLotteryType() {
		return LotteryType.JCZQ_BQC;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JczqRace race, JingcaiCodeItem item) {
		boolean cancel = race.getStatus().equals(RaceStatus.CANCEL.value)?true:false;
		
		JingcaiResult result = new JingcaiResult(race.getMatchNum(),cancel);
		
		if(result.isCancel()) {
			return result;
		}
		
		String firsthalf = race.getFirstHalf();
		int firsthalf_zhu = Integer.parseInt(firsthalf.split(":")[0]);
		int firsthalf_ke = Integer.parseInt(firsthalf.split(":")[1]);
		
		String finalscore = race.getFinalScore();
		int final_zhu = Integer.parseInt(finalscore.split(":")[0]);
		int final_ke = Integer.parseInt(finalscore.split(":")[1]);
		
		
		String ban = "1";
		if(firsthalf_zhu>firsthalf_ke) {
			ban = "3";
		}else if(firsthalf_zhu<firsthalf_ke) {
			ban = "0";
		}
		
		String quan = "1";
		if(final_zhu>final_ke) {
			quan = "3";
		}else if(final_zhu<final_ke) {
			quan = "0";
		}
		
		result.setResult_dynamic(ban+quan);
		result.setResult_static(ban+quan);
		
//		result.setPrize_dynamic(race.getPrizeBqc());
		return result;
		
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		return null;
	}


}
