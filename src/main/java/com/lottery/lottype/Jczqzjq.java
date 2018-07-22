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
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq11001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq12001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq13001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq13003;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq13004;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq14001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq14004;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq14005;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq14006;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq14011;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15005;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15006;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15010;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15016;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15020;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq15026;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16006;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16007;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16015;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16020;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16022;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16035;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16042;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16050;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq16057;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq22001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq23001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq23003;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq23004;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq24001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq24004;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq24005;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq24006;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq24011;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25005;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25006;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25010;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25016;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25020;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq25026;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26001;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26006;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26007;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26015;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26020;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26022;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26035;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26042;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26050;
import com.lottery.lottype.jc.jczq.zjq.Jczqzjq26057;

@Component("3008")
public class Jczqzjq extends AbstractJingcaiLot {
	
	private Logger logger = LoggerFactory.getLogger(Jczqzjq.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300811001", new Jczqzjq11001());
		map.put("300812001", new Jczqzjq12001());
		map.put("300813001", new Jczqzjq13001());
		map.put("300813003", new Jczqzjq13003());
		map.put("300813004", new Jczqzjq13004());
		map.put("300814001", new Jczqzjq14001());
		map.put("300814004", new Jczqzjq14004());
		map.put("300814005", new Jczqzjq14005());
		map.put("300814006", new Jczqzjq14006());
		map.put("300814011", new Jczqzjq14011());
		
		map.put("300815001", new Jczqzjq15001());
		map.put("300815005", new Jczqzjq15005());
		map.put("300815006", new Jczqzjq15006());
		map.put("300815010", new Jczqzjq15010());
		map.put("300815016", new Jczqzjq15016());
		map.put("300815020", new Jczqzjq15020());
		map.put("300815026", new Jczqzjq15026());
		
		map.put("300816001", new Jczqzjq16001());
		map.put("300816006", new Jczqzjq16006());
		map.put("300816007", new Jczqzjq16007());
		map.put("300816015", new Jczqzjq16015());
		map.put("300816020", new Jczqzjq16020());
		map.put("300816022", new Jczqzjq16022());
		map.put("300816035", new Jczqzjq16035());
		map.put("300816042", new Jczqzjq16042());
		map.put("300816050", new Jczqzjq16050());
		map.put("300816057", new Jczqzjq16057());
		
		
		map.put("300822001", new Jczqzjq22001());
		map.put("300823001", new Jczqzjq23001());
		map.put("300823003", new Jczqzjq23003());
		map.put("300823004", new Jczqzjq23004());
		map.put("300824001", new Jczqzjq24001());
		map.put("300824004", new Jczqzjq24004());
		map.put("300824005", new Jczqzjq24005());
		map.put("300824006", new Jczqzjq24006());
		map.put("300824011", new Jczqzjq24011());
		
		map.put("300825001", new Jczqzjq25001());
		map.put("300825005", new Jczqzjq25005());
		map.put("300825006", new Jczqzjq25006());
		map.put("300825010", new Jczqzjq25010());
		map.put("300825016", new Jczqzjq25016());
		map.put("300825020", new Jczqzjq25020());
		map.put("300825026", new Jczqzjq25026());
		
		map.put("300826001", new Jczqzjq26001());
		map.put("300826006", new Jczqzjq26006());
		map.put("300826007", new Jczqzjq26007());
		map.put("300826015", new Jczqzjq26015());
		map.put("300826020", new Jczqzjq26020());
		map.put("300826022", new Jczqzjq26022());
		map.put("300826035", new Jczqzjq26035());
		map.put("300826042", new Jczqzjq26042());
		map.put("300826050", new Jczqzjq26050());
		map.put("300826057", new Jczqzjq26057());
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
				logger.error("split amt_lotmulti err:betcode={} lotmulti={} amt={}",new Object[]{slot.getBetcode(),slot.getLotMulti(),slot.getAmt()});
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
			totalamt = totalamt + slot.getAmt();
		}
		
		if(totalamt!=amt) {
			logger.error("split amt_equal totalAmt={} amt={}",new Object[]{totalamt,amt});
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
		return LotteryType.JCZQ_JQS;
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
		int sum = zhu + ke;
		if (sum >= 0 && sum <= 6) {
			result_str =  String.valueOf(sum);
		}else {
			result_str =  "7";
		}
		result.setResult_dynamic(result_str);
		result.setResult_static(result_str);
		
//		result.setPrize_dynamic(race.getPrizeJqs());
		return result;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		return null;
	}


}
