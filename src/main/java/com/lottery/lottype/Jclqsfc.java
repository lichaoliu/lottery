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
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc11001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc12001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc13001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc13003;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc13004;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc14001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc14004;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc14005;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc14006;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc14011;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc22001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc23001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc23003;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc23004;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc24001;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc24004;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc24005;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc24006;
import com.lottery.lottype.jc.jclq.sfc.Jclqsfc24011;

@Component("3003")
public class Jclqsfc extends AbstractJingcaiLot{

	private Logger logger = LoggerFactory.getLogger(Jclqsfc.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300311001", new Jclqsfc11001());
		map.put("300312001", new Jclqsfc12001());
		map.put("300313001", new Jclqsfc13001());
		map.put("300313003", new Jclqsfc13003());
		map.put("300313004", new Jclqsfc13004());
		map.put("300314001", new Jclqsfc14001());
		map.put("300314004", new Jclqsfc14004());
		map.put("300314005", new Jclqsfc14005());
		map.put("300314006", new Jclqsfc14006());
		map.put("300314011", new Jclqsfc14011());
		
		
		map.put("300322001", new Jclqsfc22001());
		map.put("300323001", new Jclqsfc23001());
		map.put("300323003", new Jclqsfc23003());
		map.put("300323004", new Jclqsfc23004());
		map.put("300324001", new Jclqsfc24001());
		map.put("300324004", new Jclqsfc24004());
		map.put("300324005", new Jclqsfc24005());
		map.put("300324006", new Jclqsfc24006());
		map.put("300324011", new Jclqsfc24011());
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
	public LotteryType getLotteryType() {
		return LotteryType.JCLQ_SFC;
	}

	@Override
	public List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,
			String betcode, String odds) {
		JingcaiCanCal cancal = jclqCanCal(betcode, odds);
		if(cancal.isCanCal()==false) {
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
	protected JingcaiResult getJingcaiResult(JczqRace race, JingcaiCodeItem item) {
		return null;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		boolean cancel = race.getStatus().equals(RaceStatus.CANCEL.value)?true:false;
		
		JingcaiResult result = new JingcaiResult(race.getMatchNum(),cancel);
		
		if(result.isCancel()) {
			return result;
		}
		String finalScore = race.getFinalScore();
		
		double zhu = Double.parseDouble(finalScore.split(":")[0]);
		double ke = Double.parseDouble(finalScore.split(":")[1]);
		
		double minus = Math.abs(zhu-ke);
		
		String result_str = "";
		
		if(zhu>ke) {
			if (minus >= 1 && minus <= 5) {
				result_str = "01";
			} else if (minus >= 6 && minus <= 10) {
				result_str = "02";
			} else if (minus >= 11 && minus <= 15) {
				result_str = "03";
			} else if (minus >= 16 && minus <= 20) {
				result_str = "04";
			} else if (minus >= 21 && minus <= 25) {
				result_str = "05";
			} else {
				result_str = "06";
			}
		}else {
			if (minus >= 1 && minus <= 5) {
				result_str = "11";
			} else if (minus >= 6 && minus <= 10) {
				result_str = "12";
			} else if (minus >= 11 && minus <= 15) {
				result_str = "13";
			} else if (minus >= 16 && minus <= 20) {
				result_str = "14";
			} else if (minus >= 21 && minus <= 25) {
				result_str = "15";
			} else {
				result_str = "16";
			}
		}
		
		result.setResult_dynamic(result_str);
		result.setResult_static(result_str);
		
//		result.setPrize_dynamic(race.getPrizeSfc());
		
		return result;
	}

}
