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
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq11001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq12001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq13001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq13003;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq13004;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq14001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq14004;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq14005;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq14006;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq14011;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15005;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15006;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15010;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15016;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15020;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq15026;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16006;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16007;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16015;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16020;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16022;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16035;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16042;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16050;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq16057;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq17001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq17007;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq17008;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq17021;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq17035;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq17120;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18008;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18009;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18028;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18056;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18070;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq18247;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq22001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq23001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq23003;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq23004;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq24001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq24004;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq24005;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq24006;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq24011;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25005;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25006;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25010;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25016;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25020;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq25026;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26006;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26007;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26015;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26020;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26022;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26035;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26042;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26050;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq26057;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq27001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq27007;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq27008;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq27021;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq27035;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq27120;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28001;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28008;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28009;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28028;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28056;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28070;
import com.lottery.lottype.jc.jczq.spfwrq.Jczqspfwrq28247;

@Component("3010")
public class Jczqspfwrq extends AbstractJingcaiLot{
	
	private Logger logger = LoggerFactory.getLogger(Jczqspfwrq.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("301011001", new Jczqspfwrq11001());
		map.put("301012001", new Jczqspfwrq12001());
		map.put("301013001", new Jczqspfwrq13001());
		map.put("301013003", new Jczqspfwrq13003());
		map.put("301013004", new Jczqspfwrq13004());
		map.put("301014001", new Jczqspfwrq14001());
		map.put("301014004", new Jczqspfwrq14004());
		map.put("301014005", new Jczqspfwrq14005());
		map.put("301014006", new Jczqspfwrq14006());
		map.put("301014011", new Jczqspfwrq14011());
		
		map.put("301015001", new Jczqspfwrq15001());
		map.put("301015005", new Jczqspfwrq15005());
		map.put("301015006", new Jczqspfwrq15006());
		map.put("301015010", new Jczqspfwrq15010());
		map.put("301015016", new Jczqspfwrq15016());
		map.put("301015020", new Jczqspfwrq15020());
		map.put("301015026", new Jczqspfwrq15026());
		
		map.put("301016001", new Jczqspfwrq16001());
		map.put("301016006", new Jczqspfwrq16006());
		map.put("301016007", new Jczqspfwrq16007());
		map.put("301016015", new Jczqspfwrq16015());
		map.put("301016020", new Jczqspfwrq16020());
		map.put("301016022", new Jczqspfwrq16022());
		map.put("301016035", new Jczqspfwrq16035());
		map.put("301016042", new Jczqspfwrq16042());
		map.put("301016050", new Jczqspfwrq16050());
		map.put("301016057", new Jczqspfwrq16057());
		
		map.put("301017001", new Jczqspfwrq17001());
		map.put("301017007", new Jczqspfwrq17007());
		map.put("301017008", new Jczqspfwrq17008());
		map.put("301017021", new Jczqspfwrq17021());
		map.put("301017035", new Jczqspfwrq17035());
		map.put("301017120", new Jczqspfwrq17120());
		
		map.put("301018001", new Jczqspfwrq18001());
		map.put("301018008", new Jczqspfwrq18008());
		map.put("301018009", new Jczqspfwrq18009());
		map.put("301018028", new Jczqspfwrq18028());
		map.put("301018056", new Jczqspfwrq18056());
		map.put("301018070", new Jczqspfwrq18070());
		map.put("301018247", new Jczqspfwrq18247());
		
		
		map.put("301022001", new Jczqspfwrq22001());
		map.put("301023001", new Jczqspfwrq23001());
		map.put("301023003", new Jczqspfwrq23003());
		map.put("301023004", new Jczqspfwrq23004());
		map.put("301024001", new Jczqspfwrq24001());
		map.put("301024004", new Jczqspfwrq24004());
		map.put("301024005", new Jczqspfwrq24005());
		map.put("301024006", new Jczqspfwrq24006());
		map.put("301024011", new Jczqspfwrq24011());
		
		map.put("301025001", new Jczqspfwrq25001());
		map.put("301025005", new Jczqspfwrq25005());
		map.put("301025006", new Jczqspfwrq25006());
		map.put("301025010", new Jczqspfwrq25010());
		map.put("301025016", new Jczqspfwrq25016());
		map.put("301025020", new Jczqspfwrq25020());
		map.put("301025026", new Jczqspfwrq25026());
		
		map.put("301026001", new Jczqspfwrq26001());
		map.put("301026006", new Jczqspfwrq26006());
		map.put("301026007", new Jczqspfwrq26007());
		map.put("301026015", new Jczqspfwrq26015());
		map.put("301026020", new Jczqspfwrq26020());
		map.put("301026022", new Jczqspfwrq26022());
		map.put("301026035", new Jczqspfwrq26035());
		map.put("301026042", new Jczqspfwrq26042());
		map.put("301026050", new Jczqspfwrq26050());
		map.put("301026057", new Jczqspfwrq26057());
		
		map.put("301027001", new Jczqspfwrq27001());
		map.put("301027007", new Jczqspfwrq27007());
		map.put("301027008", new Jczqspfwrq27008());
		map.put("301027021", new Jczqspfwrq27021());
		map.put("301027035", new Jczqspfwrq27035());
		map.put("301027120", new Jczqspfwrq27120());
		
		map.put("301028001", new Jczqspfwrq28001());
		map.put("301028008", new Jczqspfwrq28008());
		map.put("301028009", new Jczqspfwrq28009());
		map.put("301028028", new Jczqspfwrq28028());
		map.put("301028056", new Jczqspfwrq28056());
		map.put("301028070", new Jczqspfwrq28070());
		map.put("301028247", new Jczqspfwrq28247());
		
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
		return LotteryType.JCZQ_SPF_WRQ;
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
		
		if(zhu==ke) {
			result_str = "1";
		}else if(zhu>ke) {
			result_str = "3";
		}else {
			result_str = "0";
		}
		
		result.setResult_dynamic(result_str);
		result.setResult_static(result_str);
		
//		result.setPrize_dynamic(race.getPrizeSpfWrq());
		
		return result;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		return null;
	}

}
