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
import com.lottery.lottype.jc.jczq.mix.Jczqmix11001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix12001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix13001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix13003;
import com.lottery.lottype.jc.jczq.mix.Jczqmix13004;
import com.lottery.lottype.jc.jczq.mix.Jczqmix14001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix14004;
import com.lottery.lottype.jc.jczq.mix.Jczqmix14005;
import com.lottery.lottype.jc.jczq.mix.Jczqmix14006;
import com.lottery.lottype.jc.jczq.mix.Jczqmix14011;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15005;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15006;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15010;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15016;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15020;
import com.lottery.lottype.jc.jczq.mix.Jczqmix15026;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16006;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16007;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16015;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16020;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16022;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16035;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16042;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16050;
import com.lottery.lottype.jc.jczq.mix.Jczqmix16057;
import com.lottery.lottype.jc.jczq.mix.Jczqmix17001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix17007;
import com.lottery.lottype.jc.jczq.mix.Jczqmix17008;
import com.lottery.lottype.jc.jczq.mix.Jczqmix17021;
import com.lottery.lottype.jc.jczq.mix.Jczqmix17035;
import com.lottery.lottype.jc.jczq.mix.Jczqmix17120;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18008;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18009;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18028;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18056;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18070;
import com.lottery.lottype.jc.jczq.mix.Jczqmix18247;
import com.lottery.lottype.jc.jczq.mix.Jczqmix22001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix23001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix23003;
import com.lottery.lottype.jc.jczq.mix.Jczqmix23004;
import com.lottery.lottype.jc.jczq.mix.Jczqmix24001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix24004;
import com.lottery.lottype.jc.jczq.mix.Jczqmix24005;
import com.lottery.lottype.jc.jczq.mix.Jczqmix24006;
import com.lottery.lottype.jc.jczq.mix.Jczqmix24011;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25005;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25006;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25010;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25016;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25020;
import com.lottery.lottype.jc.jczq.mix.Jczqmix25026;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26006;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26007;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26015;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26020;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26022;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26035;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26042;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26050;
import com.lottery.lottype.jc.jczq.mix.Jczqmix26057;
import com.lottery.lottype.jc.jczq.mix.Jczqmix27001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix27007;
import com.lottery.lottype.jc.jczq.mix.Jczqmix27008;
import com.lottery.lottype.jc.jczq.mix.Jczqmix27021;
import com.lottery.lottype.jc.jczq.mix.Jczqmix27035;
import com.lottery.lottype.jc.jczq.mix.Jczqmix27120;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28001;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28008;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28009;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28028;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28056;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28070;
import com.lottery.lottype.jc.jczq.mix.Jczqmix28247;

@Component("3011")
public class JczqMix extends AbstractJingcaiLot{
	
	private Logger logger = LoggerFactory.getLogger(JczqMix.class);
	
	@Autowired
	private Map<String,AbstractJingcaiLot> jingcaimap;
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		
		map.put("301111001", new Jczqmix11001());
		map.put("301112001", new Jczqmix12001());
		map.put("301113001", new Jczqmix13001());
		map.put("301113003", new Jczqmix13003());
		map.put("301113004", new Jczqmix13004());
		map.put("301114001", new Jczqmix14001());
		map.put("301114004", new Jczqmix14004());
		map.put("301114005", new Jczqmix14005());
		map.put("301114006", new Jczqmix14006());
		map.put("301114011", new Jczqmix14011());
		
		map.put("301115001", new Jczqmix15001());
		map.put("301115005", new Jczqmix15005());
		map.put("301115006", new Jczqmix15006());
		map.put("301115010", new Jczqmix15010());
		map.put("301115016", new Jczqmix15016());
		map.put("301115020", new Jczqmix15020());
		map.put("301115026", new Jczqmix15026());
		
		map.put("301116001", new Jczqmix16001());
		map.put("301116006", new Jczqmix16006());
		map.put("301116007", new Jczqmix16007());
		map.put("301116015", new Jczqmix16015());
		map.put("301116020", new Jczqmix16020());
		map.put("301116022", new Jczqmix16022());
		map.put("301116035", new Jczqmix16035());
		map.put("301116042", new Jczqmix16042());
		map.put("301116050", new Jczqmix16050());
		map.put("301116057", new Jczqmix16057());
		
		map.put("301117001", new Jczqmix17001());
		map.put("301117007", new Jczqmix17007());
		map.put("301117008", new Jczqmix17008());
		map.put("301117021", new Jczqmix17021());
		map.put("301117035", new Jczqmix17035());
		map.put("301117120", new Jczqmix17120());
		
		map.put("301118001", new Jczqmix18001());
		map.put("301118008", new Jczqmix18008());
		map.put("301118009", new Jczqmix18009());
		map.put("301118028", new Jczqmix18028());
		map.put("301118056", new Jczqmix18056());
		map.put("301118070", new Jczqmix18070());
		map.put("301118247", new Jczqmix18247());
		
		
		
		map.put("301122001", new Jczqmix22001());
		map.put("301123001", new Jczqmix23001());
		map.put("301123003", new Jczqmix23003());
		map.put("301123004", new Jczqmix23004());
		map.put("301124001", new Jczqmix24001());
		map.put("301124004", new Jczqmix24004());
		map.put("301124005", new Jczqmix24005());
		map.put("301124006", new Jczqmix24006());
		map.put("301124011", new Jczqmix24011());
		
		map.put("301125001", new Jczqmix25001());
		map.put("301125005", new Jczqmix25005());
		map.put("301125006", new Jczqmix25006());
		map.put("301125010", new Jczqmix25010());
		map.put("301125016", new Jczqmix25016());
		map.put("301125020", new Jczqmix25020());
		map.put("301125026", new Jczqmix25026());
		
		map.put("301126001", new Jczqmix26001());
		map.put("301126006", new Jczqmix26006());
		map.put("301126007", new Jczqmix26007());
		map.put("301126015", new Jczqmix26015());
		map.put("301126020", new Jczqmix26020());
		map.put("301126022", new Jczqmix26022());
		map.put("301126035", new Jczqmix26035());
		map.put("301126042", new Jczqmix26042());
		map.put("301126050", new Jczqmix26050());
		map.put("301126057", new Jczqmix26057());
		
		map.put("301127001", new Jczqmix27001());
		map.put("301127007", new Jczqmix27007());
		map.put("301127008", new Jczqmix27008());
		map.put("301127021", new Jczqmix27021());
		map.put("301127035", new Jczqmix27035());
		map.put("301127120", new Jczqmix27120());
		
		map.put("301128001", new Jczqmix28001());
		map.put("301128008", new Jczqmix28008());
		map.put("301128009", new Jczqmix28009());
		map.put("301128028", new Jczqmix28028());
		map.put("301128056", new Jczqmix28056());
		map.put("301128070", new Jczqmix28070());
		map.put("301128247", new Jczqmix28247());
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
		return LotteryType.JCZQ_HHGG;
	}

	@Override
	public List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,
			String betcode, String odds) {
		JingcaiCanCal cancal = jczqCanCalMix(betcode, odds);
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
		AbstractJingcaiLot jingcai = jingcaimap.get(String.valueOf(item.getLotteryType().getValue()));
		return jingcai.getJingcaiResult(race, item);
	}

	@Override
	protected JingcaiResult getJingcaiResult(JclqRace race, JingcaiCodeItem item) {
		// TODO Auto-generated method stub
		return null;
	}

}
