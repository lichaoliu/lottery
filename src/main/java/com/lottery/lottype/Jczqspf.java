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
import com.lottery.lottype.jc.jczq.spf.Jczqspf11001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf12001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf13001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf13003;
import com.lottery.lottype.jc.jczq.spf.Jczqspf13004;
import com.lottery.lottype.jc.jczq.spf.Jczqspf14001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf14004;
import com.lottery.lottype.jc.jczq.spf.Jczqspf14005;
import com.lottery.lottype.jc.jczq.spf.Jczqspf14006;
import com.lottery.lottype.jc.jczq.spf.Jczqspf14011;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15005;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15006;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15010;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15016;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15020;
import com.lottery.lottype.jc.jczq.spf.Jczqspf15026;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16006;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16007;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16015;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16020;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16022;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16035;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16042;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16050;
import com.lottery.lottype.jc.jczq.spf.Jczqspf16057;
import com.lottery.lottype.jc.jczq.spf.Jczqspf17001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf17007;
import com.lottery.lottype.jc.jczq.spf.Jczqspf17008;
import com.lottery.lottype.jc.jczq.spf.Jczqspf17021;
import com.lottery.lottype.jc.jczq.spf.Jczqspf17035;
import com.lottery.lottype.jc.jczq.spf.Jczqspf17120;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18008;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18009;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18028;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18056;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18070;
import com.lottery.lottype.jc.jczq.spf.Jczqspf18247;
import com.lottery.lottype.jc.jczq.spf.Jczqspf22001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf23001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf23003;
import com.lottery.lottype.jc.jczq.spf.Jczqspf23004;
import com.lottery.lottype.jc.jczq.spf.Jczqspf24001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf24004;
import com.lottery.lottype.jc.jczq.spf.Jczqspf24005;
import com.lottery.lottype.jc.jczq.spf.Jczqspf24006;
import com.lottery.lottype.jc.jczq.spf.Jczqspf24011;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25005;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25006;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25010;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25016;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25020;
import com.lottery.lottype.jc.jczq.spf.Jczqspf25026;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26006;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26007;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26015;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26020;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26022;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26035;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26042;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26050;
import com.lottery.lottype.jc.jczq.spf.Jczqspf26057;
import com.lottery.lottype.jc.jczq.spf.Jczqspf27001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf27007;
import com.lottery.lottype.jc.jczq.spf.Jczqspf27008;
import com.lottery.lottype.jc.jczq.spf.Jczqspf27021;
import com.lottery.lottype.jc.jczq.spf.Jczqspf27035;
import com.lottery.lottype.jc.jczq.spf.Jczqspf27120;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28001;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28008;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28009;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28028;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28056;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28070;
import com.lottery.lottype.jc.jczq.spf.Jczqspf28247;

@Component("3006")
public class Jczqspf extends AbstractJingcaiLot {
	
	private Logger logger = LoggerFactory.getLogger(Jczqspf.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}

	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300611001", new Jczqspf11001());
		map.put("300612001", new Jczqspf12001());
		map.put("300613001", new Jczqspf13001());
		map.put("300613003", new Jczqspf13003());
		map.put("300613004", new Jczqspf13004());
		map.put("300614001", new Jczqspf14001());
		map.put("300614004", new Jczqspf14004());
		map.put("300614005", new Jczqspf14005());
		map.put("300614006", new Jczqspf14006());
		map.put("300614011", new Jczqspf14011());
		
		map.put("300615001", new Jczqspf15001());
		map.put("300615005", new Jczqspf15005());
		map.put("300615006", new Jczqspf15006());
		map.put("300615010", new Jczqspf15010());
		map.put("300615016", new Jczqspf15016());
		map.put("300615020", new Jczqspf15020());
		map.put("300615026", new Jczqspf15026());
		
		map.put("300616001", new Jczqspf16001());
		map.put("300616006", new Jczqspf16006());
		map.put("300616007", new Jczqspf16007());
		map.put("300616015", new Jczqspf16015());
		map.put("300616020", new Jczqspf16020());
		map.put("300616022", new Jczqspf16022());
		map.put("300616035", new Jczqspf16035());
		map.put("300616042", new Jczqspf16042());
		map.put("300616050", new Jczqspf16050());
		map.put("300616057", new Jczqspf16057());
		
		map.put("300617001", new Jczqspf17001());
		map.put("300617007", new Jczqspf17007());
		map.put("300617008", new Jczqspf17008());
		map.put("300617021", new Jczqspf17021());
		map.put("300617035", new Jczqspf17035());
		map.put("300617120", new Jczqspf17120());
		
		map.put("300618001", new Jczqspf18001());
		map.put("300618008", new Jczqspf18008());
		map.put("300618009", new Jczqspf18009());
		map.put("300618028", new Jczqspf18028());
		map.put("300618056", new Jczqspf18056());
		map.put("300618070", new Jczqspf18070());
		map.put("300618247", new Jczqspf18247());
		
		
		
		map.put("300622001", new Jczqspf22001());
		map.put("300623001", new Jczqspf23001());
		map.put("300623003", new Jczqspf23003());
		map.put("300623004", new Jczqspf23004());
		map.put("300624001", new Jczqspf24001());
		map.put("300624004", new Jczqspf24004());
		map.put("300624005", new Jczqspf24005());
		map.put("300624006", new Jczqspf24006());
		map.put("300624011", new Jczqspf24011());
		
		map.put("300625001", new Jczqspf25001());
		map.put("300625005", new Jczqspf25005());
		map.put("300625006", new Jczqspf25006());
		map.put("300625010", new Jczqspf25010());
		map.put("300625016", new Jczqspf25016());
		map.put("300625020", new Jczqspf25020());
		map.put("300625026", new Jczqspf25026());
		
		map.put("300626001", new Jczqspf26001());
		map.put("300626006", new Jczqspf26006());
		map.put("300626007", new Jczqspf26007());
		map.put("300626015", new Jczqspf26015());
		map.put("300626020", new Jczqspf26020());
		map.put("300626022", new Jczqspf26022());
		map.put("300626035", new Jczqspf26035());
		map.put("300626042", new Jczqspf26042());
		map.put("300626050", new Jczqspf26050());
		map.put("300626057", new Jczqspf26057());
		
		map.put("300627001", new Jczqspf27001());
		map.put("300627007", new Jczqspf27007());
		map.put("300627008", new Jczqspf27008());
		map.put("300627021", new Jczqspf27021());
		map.put("300627035", new Jczqspf27035());
		map.put("300627120", new Jczqspf27120());
		
		map.put("300628001", new Jczqspf28001());
		map.put("300628008", new Jczqspf28008());
		map.put("300628009", new Jczqspf28009());
		map.put("300628028", new Jczqspf28028());
		map.put("300628056", new Jczqspf28056());
		map.put("300628070", new Jczqspf28070());
		map.put("300628247", new Jczqspf28247());
		
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
		return LotteryType.JCZQ_RQ_SPF;
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
		int handicap = Integer.parseInt(race.getHandicap().replace("+", ""));
		
		int handicapZhu = zhu + handicap;
		
		String result_str = "";
		
		if(handicapZhu==ke) {
			result_str = "1";
		}else if(handicapZhu>ke) {
			result_str = "3";
		}else {
			result_str = "0";
		}
		
		result.setResult_dynamic(result_str);
		result.setResult_static(result_str);
		
//		result.setPrize_dynamic(race.getPrizeSpf());
		
		return result;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		return null;
	}

}
