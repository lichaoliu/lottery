package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;
import com.lottery.lottype.jc.JingcaiCanCal;
import com.lottery.lottype.jc.JingcaiCodeItem;
import com.lottery.lottype.jc.JingcaiResult;
import com.lottery.lottype.jc.jclq.mix.Jclqmix11001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix12001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix13001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix13003;
import com.lottery.lottype.jc.jclq.mix.Jclqmix13004;
import com.lottery.lottype.jc.jclq.mix.Jclqmix14001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix14004;
import com.lottery.lottype.jc.jclq.mix.Jclqmix14005;
import com.lottery.lottype.jc.jclq.mix.Jclqmix14006;
import com.lottery.lottype.jc.jclq.mix.Jclqmix14011;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15005;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15006;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15010;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15016;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15020;
import com.lottery.lottype.jc.jclq.mix.Jclqmix15026;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16006;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16007;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16015;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16020;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16022;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16035;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16042;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16050;
import com.lottery.lottype.jc.jclq.mix.Jclqmix16057;
import com.lottery.lottype.jc.jclq.mix.Jclqmix17001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix17007;
import com.lottery.lottype.jc.jclq.mix.Jclqmix17008;
import com.lottery.lottype.jc.jclq.mix.Jclqmix17021;
import com.lottery.lottype.jc.jclq.mix.Jclqmix17035;
import com.lottery.lottype.jc.jclq.mix.Jclqmix17120;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18008;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18009;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18028;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18056;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18070;
import com.lottery.lottype.jc.jclq.mix.Jclqmix18247;
import com.lottery.lottype.jc.jclq.mix.Jclqmix22001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix23001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix23003;
import com.lottery.lottype.jc.jclq.mix.Jclqmix23004;
import com.lottery.lottype.jc.jclq.mix.Jclqmix24001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix24004;
import com.lottery.lottype.jc.jclq.mix.Jclqmix24005;
import com.lottery.lottype.jc.jclq.mix.Jclqmix24006;
import com.lottery.lottype.jc.jclq.mix.Jclqmix24011;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25005;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25006;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25010;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25016;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25020;
import com.lottery.lottype.jc.jclq.mix.Jclqmix25026;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26006;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26007;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26015;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26020;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26022;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26035;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26042;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26050;
import com.lottery.lottype.jc.jclq.mix.Jclqmix26057;
import com.lottery.lottype.jc.jclq.mix.Jclqmix27001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix27007;
import com.lottery.lottype.jc.jclq.mix.Jclqmix27008;
import com.lottery.lottype.jc.jclq.mix.Jclqmix27021;
import com.lottery.lottype.jc.jclq.mix.Jclqmix27035;
import com.lottery.lottype.jc.jclq.mix.Jclqmix27120;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28001;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28008;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28009;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28028;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28056;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28070;
import com.lottery.lottype.jc.jclq.mix.Jclqmix28247;

@Component("3005")
public class JclqMix extends AbstractJingcaiLot{
	
	private Logger logger = LoggerFactory.getLogger(JclqMix.class);
	
	@Autowired
	private Map<String,AbstractJingcaiLot> jingcaimap;
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		
		map.put("300511001", new Jclqmix11001());
		map.put("300512001", new Jclqmix12001());
		map.put("300513001", new Jclqmix13001());
		map.put("300513003", new Jclqmix13003());
		map.put("300513004", new Jclqmix13004());
		map.put("300514001", new Jclqmix14001());
		map.put("300514004", new Jclqmix14004());
		map.put("300514005", new Jclqmix14005());
		map.put("300514006", new Jclqmix14006());
		map.put("300514011", new Jclqmix14011());
		
		map.put("300515001", new Jclqmix15001());
		map.put("300515005", new Jclqmix15005());
		map.put("300515006", new Jclqmix15006());
		map.put("300515010", new Jclqmix15010());
		map.put("300515016", new Jclqmix15016());
		map.put("300515020", new Jclqmix15020());
		map.put("300515026", new Jclqmix15026());
		
		map.put("300516001", new Jclqmix16001());
		map.put("300516006", new Jclqmix16006());
		map.put("300516007", new Jclqmix16007());
		map.put("300516015", new Jclqmix16015());
		map.put("300516020", new Jclqmix16020());
		map.put("300516022", new Jclqmix16022());
		map.put("300516035", new Jclqmix16035());
		map.put("300516042", new Jclqmix16042());
		map.put("300516050", new Jclqmix16050());
		map.put("300516057", new Jclqmix16057());
		
		map.put("300517001", new Jclqmix17001());
		map.put("300517007", new Jclqmix17007());
		map.put("300517008", new Jclqmix17008());
		map.put("300517021", new Jclqmix17021());
		map.put("300517035", new Jclqmix17035());
		map.put("300517120", new Jclqmix17120());
		
		map.put("300518001", new Jclqmix18001());
		map.put("300518008", new Jclqmix18008());
		map.put("300518009", new Jclqmix18009());
		map.put("300518028", new Jclqmix18028());
		map.put("300518056", new Jclqmix18056());
		map.put("300518070", new Jclqmix18070());
		map.put("300518247", new Jclqmix18247());
		
		
		
		map.put("300522001", new Jclqmix22001());
		map.put("300523001", new Jclqmix23001());
		map.put("300523003", new Jclqmix23003());
		map.put("300523004", new Jclqmix23004());
		map.put("300524001", new Jclqmix24001());
		map.put("300524004", new Jclqmix24004());
		map.put("300524005", new Jclqmix24005());
		map.put("300524006", new Jclqmix24006());
		map.put("300524011", new Jclqmix24011());
		
		map.put("300525001", new Jclqmix25001());
		map.put("300525005", new Jclqmix25005());
		map.put("300525006", new Jclqmix25006());
		map.put("300525010", new Jclqmix25010());
		map.put("300525016", new Jclqmix25016());
		map.put("300525020", new Jclqmix25020());
		map.put("300525026", new Jclqmix25026());
		
		map.put("300526001", new Jclqmix26001());
		map.put("300526006", new Jclqmix26006());
		map.put("300526007", new Jclqmix26007());
		map.put("300526015", new Jclqmix26015());
		map.put("300526020", new Jclqmix26020());
		map.put("300526022", new Jclqmix26022());
		map.put("300526035", new Jclqmix26035());
		map.put("300526042", new Jclqmix26042());
		map.put("300526050", new Jclqmix26050());
		map.put("300526057", new Jclqmix26057());
		
		map.put("300527001", new Jclqmix27001());
		map.put("300527007", new Jclqmix27007());
		map.put("300527008", new Jclqmix27008());
		map.put("300527021", new Jclqmix27021());
		map.put("300527035", new Jclqmix27035());
		map.put("300527120", new Jclqmix27120());
		
		map.put("300528001", new Jclqmix28001());
		map.put("300528008", new Jclqmix28008());
		map.put("300528009", new Jclqmix28009());
		map.put("300528028", new Jclqmix28028());
		map.put("300528056", new Jclqmix28056());
		map.put("300528070", new Jclqmix28070());
		map.put("300528247", new Jclqmix28247());
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
		return LotteryType.JCLQ_HHGG;
	}

	@Override
	public List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,
			String betcode, String odds) {
		JingcaiCanCal cancal = jclqCanCalMix(betcode, odds);
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
	protected JingcaiResult getJingcaiResult(JczqRace race, JingcaiCodeItem item) {
		return null;
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		AbstractJingcaiLot jingcai = jingcaimap.get(String.valueOf(item.getLotteryType().getValue()));
		
		return jingcai.getJingcaiResult(race, item);
	}

}
