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
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen11001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen12001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen13001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen13003;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen13004;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen14001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen14004;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen14005;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen14006;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen14011;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen22001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen23001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen23003;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen23004;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen24001;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen24004;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen24005;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen24006;
import com.lottery.lottype.jc.jczq.bifen.Jczqbifen24011;

@Component("3007")
public class Jczqbifen extends AbstractJingcaiLot{
	
	private Logger logger = LoggerFactory.getLogger(Jczqbifen.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300711001", new Jczqbifen11001());
		map.put("300712001", new Jczqbifen12001());
		map.put("300713001", new Jczqbifen13001());
		map.put("300713003", new Jczqbifen13003());
		map.put("300713004", new Jczqbifen13004());
		map.put("300714001", new Jczqbifen14001());
		map.put("300714004", new Jczqbifen14004());
		map.put("300714005", new Jczqbifen14005());
		map.put("300714006", new Jczqbifen14006());
		map.put("300714011", new Jczqbifen14011());
		
		
		map.put("300722001", new Jczqbifen22001());
		map.put("300723001", new Jczqbifen23001());
		map.put("300723003", new Jczqbifen23003());
		map.put("300723004", new Jczqbifen23004());
		map.put("300724001", new Jczqbifen24001());
		map.put("300724004", new Jczqbifen24004());
		map.put("300724005", new Jczqbifen24005());
		map.put("300724006", new Jczqbifen24006());
		map.put("300724011", new Jczqbifen24011());
		
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
		return LotteryType.JCZQ_BF;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JczqRace race, JingcaiCodeItem item) {
		boolean cancel = race.getStatus().equals(RaceStatus.CANCEL.value)?true:false;
		
		JingcaiResult result = new JingcaiResult(race.getMatchNum(),cancel);
		
		if(result.isCancel()) {
			return result;
		}
		
		String finalScore = race.getFinalScore();
		
		int zhu = Integer.parseInt(finalScore.split(":")[0]);
		int ke = Integer.parseInt(finalScore.split(":")[1]);
		
		String result_str = "";
		if (zhu == ke) {
			if (zhu + ke <= 6) {
				result_str = zhu + "" + ke;
			} else {
				result_str = "99";
			}
		} else if (zhu > ke) {
			if (zhu >= 1 && zhu <= 5 && ke >= 0 && ke <= 2) {
				result_str = zhu + "" + ke;
			} else {
				result_str = "90";
			}
		} else{
			if (ke >= 1 && ke <= 5 && zhu >= 0 && zhu <= 2) {
				result_str = zhu + "" + ke;
			} else {
				result_str = "09";
			}
		}
		result.setResult_dynamic(result_str);
		result.setResult_static(result_str);
		
		//单关有赔率，实际此值不存在
//		result.setPrize_dynamic(race.getPrizeBf());
		
		return result;
		
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		return null;
	}
}
