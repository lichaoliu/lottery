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
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf11001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf12001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf13001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf13003;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf13004;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf14001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf14004;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf14005;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf14006;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf14011;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15005;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15006;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15010;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15016;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15020;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf15026;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16006;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16007;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16015;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16020;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16022;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16035;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16042;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16050;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf16057;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf17001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf17007;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf17008;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf17021;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf17035;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf17120;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18008;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18009;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18028;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18056;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18070;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf18247;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf22001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf23001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf23003;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf23004;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf24001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf24004;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf24005;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf24006;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf24011;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25005;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25006;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25010;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25016;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25020;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf25026;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26006;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26007;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26015;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26020;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26022;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26035;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26042;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26050;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf26057;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf27001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf27007;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf27008;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf27021;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf27035;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf27120;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28001;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28008;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28009;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28028;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28056;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28070;
import com.lottery.lottype.jc.jclq.rfsf.Jclqrfsf28247;

@Component("3002")
public class Jclqrqsf extends AbstractJingcaiLot{

	private Logger logger = LoggerFactory.getLogger(Jclqrqsf.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300211001", new Jclqrfsf11001());
		map.put("300212001", new Jclqrfsf12001());
		map.put("300213001", new Jclqrfsf13001());
		map.put("300213003", new Jclqrfsf13003());
		map.put("300213004", new Jclqrfsf13004());
		map.put("300214001", new Jclqrfsf14001());
		map.put("300214004", new Jclqrfsf14004());
		map.put("300214005", new Jclqrfsf14005());
		map.put("300214006", new Jclqrfsf14006());
		map.put("300214011", new Jclqrfsf14011());
		
		map.put("300215001", new Jclqrfsf15001());
		map.put("300215005", new Jclqrfsf15005());
		map.put("300215006", new Jclqrfsf15006());
		map.put("300215010", new Jclqrfsf15010());
		map.put("300215016", new Jclqrfsf15016());
		map.put("300215020", new Jclqrfsf15020());
		map.put("300215026", new Jclqrfsf15026());
		
		map.put("300216001", new Jclqrfsf16001());
		map.put("300216006", new Jclqrfsf16006());
		map.put("300216007", new Jclqrfsf16007());
		map.put("300216015", new Jclqrfsf16015());
		map.put("300216020", new Jclqrfsf16020());
		map.put("300216022", new Jclqrfsf16022());
		map.put("300216035", new Jclqrfsf16035());
		map.put("300216042", new Jclqrfsf16042());
		map.put("300216050", new Jclqrfsf16050());
		map.put("300216057", new Jclqrfsf16057());
		
		map.put("300217001", new Jclqrfsf17001());
		map.put("300217007", new Jclqrfsf17007());
		map.put("300217008", new Jclqrfsf17008());
		map.put("300217021", new Jclqrfsf17021());
		map.put("300217035", new Jclqrfsf17035());
		map.put("300217120", new Jclqrfsf17120());
		
		map.put("300218001", new Jclqrfsf18001());
		map.put("300218008", new Jclqrfsf18008());
		map.put("300218009", new Jclqrfsf18009());
		map.put("300218028", new Jclqrfsf18028());
		map.put("300218056", new Jclqrfsf18056());
		map.put("300218070", new Jclqrfsf18070());
		map.put("300218247", new Jclqrfsf18247());
		
		
		map.put("300222001", new Jclqrfsf22001());
		map.put("300223001", new Jclqrfsf23001());
		map.put("300223003", new Jclqrfsf23003());
		map.put("300223004", new Jclqrfsf23004());
		map.put("300224001", new Jclqrfsf24001());
		map.put("300224004", new Jclqrfsf24004());
		map.put("300224005", new Jclqrfsf24005());
		map.put("300224006", new Jclqrfsf24006());
		map.put("300224011", new Jclqrfsf24011());
		
		map.put("300225001", new Jclqrfsf25001());
		map.put("300225005", new Jclqrfsf25005());
		map.put("300225006", new Jclqrfsf25006());
		map.put("300225010", new Jclqrfsf25010());
		map.put("300225016", new Jclqrfsf25016());
		map.put("300225020", new Jclqrfsf25020());
		map.put("300225026", new Jclqrfsf25026());
		
		map.put("300226001", new Jclqrfsf26001());
		map.put("300226006", new Jclqrfsf26006());
		map.put("300226007", new Jclqrfsf26007());
		map.put("300226015", new Jclqrfsf26015());
		map.put("300226020", new Jclqrfsf26020());
		map.put("300226022", new Jclqrfsf26022());
		map.put("300226035", new Jclqrfsf26035());
		map.put("300226042", new Jclqrfsf26042());
		map.put("300226050", new Jclqrfsf26050());
		map.put("300226057", new Jclqrfsf26057());
		
		map.put("300227001", new Jclqrfsf27001());
		map.put("300227007", new Jclqrfsf27007());
		map.put("300227008", new Jclqrfsf27008());
		map.put("300227021", new Jclqrfsf27021());
		map.put("300227035", new Jclqrfsf27035());
		map.put("300227120", new Jclqrfsf27120());
		
		map.put("300228001", new Jclqrfsf28001());
		map.put("300228008", new Jclqrfsf28008());
		map.put("300228009", new Jclqrfsf28009());
		map.put("300228028", new Jclqrfsf28028());
		map.put("300228056", new Jclqrfsf28056());
		map.put("300228070", new Jclqrfsf28070());
		map.put("300228247", new Jclqrfsf28247());
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
		return LotteryType.JCLQ_RFSF;
	}

	@Override
	public List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,
			String betcode, String odds) {
		JingcaiCanCal cancal = jclqCanCal(betcode, odds);
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
					if(danguanType.equals(type)) {
						String prizecode = jingcaiResult.getResult_static();
						if(item.getCodeItem().containsKey(prizecode)) {
							amt = amt.multiply(new BigDecimal(item.getCodeItem().get(prizecode)));
						}else {
							amt = BigDecimal.ZERO;
							break;
						}
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
		
		/**
		//单关
		if(item.getType().equals(danguanType)) {
			double let = Double.parseDouble(race.getDynamicHandicap().replace("+", ""));
			double letzhu = zhu+let;
			if(letzhu>ke) {
				result.setResult_dynamic("3");
			}else {
				result.setResult_dynamic("0");
			}
			
		} else {//过关
			double let = Double.parseDouble(item.getSpecialItem().replace("+", ""));
			double letzhu = zhu+let;
			if(letzhu>ke) {
				result.setResult_static("3");
			}else {
				result.setResult_static("0");
			}
		}
		result.setPrize_dynamic(race.getPrizeRfsf());
		*/
		double let = Double.parseDouble(item.getSpecialItem().replace("+", ""));
		double letzhu = zhu+let;
		if(letzhu>ke) {
			result.setResult_static("3");
		}else {
			result.setResult_static("0");
		}
		return result;
	}
}
