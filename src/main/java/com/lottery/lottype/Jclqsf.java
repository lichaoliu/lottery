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
import com.lottery.lottype.jc.jclq.sf.Jclqsf11001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf12001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf13001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf13003;
import com.lottery.lottype.jc.jclq.sf.Jclqsf13004;
import com.lottery.lottype.jc.jclq.sf.Jclqsf14001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf14004;
import com.lottery.lottype.jc.jclq.sf.Jclqsf14005;
import com.lottery.lottype.jc.jclq.sf.Jclqsf14006;
import com.lottery.lottype.jc.jclq.sf.Jclqsf14011;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15005;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15006;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15010;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15016;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15020;
import com.lottery.lottype.jc.jclq.sf.Jclqsf15026;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16006;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16007;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16015;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16020;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16022;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16035;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16042;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16050;
import com.lottery.lottype.jc.jclq.sf.Jclqsf16057;
import com.lottery.lottype.jc.jclq.sf.Jclqsf17001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf17007;
import com.lottery.lottype.jc.jclq.sf.Jclqsf17008;
import com.lottery.lottype.jc.jclq.sf.Jclqsf17021;
import com.lottery.lottype.jc.jclq.sf.Jclqsf17035;
import com.lottery.lottype.jc.jclq.sf.Jclqsf17120;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18008;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18009;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18028;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18056;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18070;
import com.lottery.lottype.jc.jclq.sf.Jclqsf18247;
import com.lottery.lottype.jc.jclq.sf.Jclqsf22001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf23001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf23003;
import com.lottery.lottype.jc.jclq.sf.Jclqsf23004;
import com.lottery.lottype.jc.jclq.sf.Jclqsf24001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf24004;
import com.lottery.lottype.jc.jclq.sf.Jclqsf24005;
import com.lottery.lottype.jc.jclq.sf.Jclqsf24006;
import com.lottery.lottype.jc.jclq.sf.Jclqsf24011;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25005;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25006;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25010;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25016;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25020;
import com.lottery.lottype.jc.jclq.sf.Jclqsf25026;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26006;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26007;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26015;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26020;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26022;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26035;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26042;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26050;
import com.lottery.lottype.jc.jclq.sf.Jclqsf26057;
import com.lottery.lottype.jc.jclq.sf.Jclqsf27001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf27007;
import com.lottery.lottype.jc.jclq.sf.Jclqsf27008;
import com.lottery.lottype.jc.jclq.sf.Jclqsf27021;
import com.lottery.lottype.jc.jclq.sf.Jclqsf27035;
import com.lottery.lottype.jc.jclq.sf.Jclqsf27120;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28001;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28008;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28009;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28028;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28056;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28070;
import com.lottery.lottype.jc.jclq.sf.Jclqsf28247;

@Component("3001")
public class Jclqsf extends AbstractJingcaiLot{

	private Logger logger = LoggerFactory.getLogger(Jclqsf.class);
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300111001", new Jclqsf11001());
		map.put("300112001", new Jclqsf12001());
		map.put("300113001", new Jclqsf13001());
		map.put("300113003", new Jclqsf13003());
		map.put("300113004", new Jclqsf13004());
		map.put("300114001", new Jclqsf14001());
		map.put("300114004", new Jclqsf14004());
		map.put("300114005", new Jclqsf14005());
		map.put("300114006", new Jclqsf14006());
		map.put("300114011", new Jclqsf14011());
		
		map.put("300115001", new Jclqsf15001());
		map.put("300115005", new Jclqsf15005());
		map.put("300115006", new Jclqsf15006());
		map.put("300115010", new Jclqsf15010());
		map.put("300115016", new Jclqsf15016());
		map.put("300115020", new Jclqsf15020());
		map.put("300115026", new Jclqsf15026());
		
		map.put("300116001", new Jclqsf16001());
		map.put("300116006", new Jclqsf16006());
		map.put("300116007", new Jclqsf16007());
		map.put("300116015", new Jclqsf16015());
		map.put("300116020", new Jclqsf16020());
		map.put("300116022", new Jclqsf16022());
		map.put("300116035", new Jclqsf16035());
		map.put("300116042", new Jclqsf16042());
		map.put("300116050", new Jclqsf16050());
		map.put("300116057", new Jclqsf16057());
		
		map.put("300117001", new Jclqsf17001());
		map.put("300117007", new Jclqsf17007());
		map.put("300117008", new Jclqsf17008());
		map.put("300117021", new Jclqsf17021());
		map.put("300117035", new Jclqsf17035());
		map.put("300117120", new Jclqsf17120());
		
		map.put("300118001", new Jclqsf18001());
		map.put("300118008", new Jclqsf18008());
		map.put("300118009", new Jclqsf18009());
		map.put("300118028", new Jclqsf18028());
		map.put("300118056", new Jclqsf18056());
		map.put("300118070", new Jclqsf18070());
		map.put("300118247", new Jclqsf18247());
		
		
		
		map.put("300122001", new Jclqsf22001());
		map.put("300123001", new Jclqsf23001());
		map.put("300123003", new Jclqsf23003());
		map.put("300123004", new Jclqsf23004());
		map.put("300124001", new Jclqsf24001());
		map.put("300124004", new Jclqsf24004());
		map.put("300124005", new Jclqsf24005());
		map.put("300124006", new Jclqsf24006());
		map.put("300124011", new Jclqsf24011());
		
		map.put("300125001", new Jclqsf25001());
		map.put("300125005", new Jclqsf25005());
		map.put("300125006", new Jclqsf25006());
		map.put("300125010", new Jclqsf25010());
		map.put("300125016", new Jclqsf25016());
		map.put("300125020", new Jclqsf25020());
		map.put("300125026", new Jclqsf25026());
		
		map.put("300126001", new Jclqsf26001());
		map.put("300126006", new Jclqsf26006());
		map.put("300126007", new Jclqsf26007());
		map.put("300126015", new Jclqsf26015());
		map.put("300126020", new Jclqsf26020());
		map.put("300126022", new Jclqsf26022());
		map.put("300126035", new Jclqsf26035());
		map.put("300126042", new Jclqsf26042());
		map.put("300126050", new Jclqsf26050());
		map.put("300126057", new Jclqsf26057());
		
		map.put("300127001", new Jclqsf27001());
		map.put("300127007", new Jclqsf27007());
		map.put("300127008", new Jclqsf27008());
		map.put("300127021", new Jclqsf27021());
		map.put("300127035", new Jclqsf27035());
		map.put("300127120", new Jclqsf27120());
		
		map.put("300128001", new Jclqsf28001());
		map.put("300128008", new Jclqsf28008());
		map.put("300128009", new Jclqsf28009());
		map.put("300128028", new Jclqsf28028());
		map.put("300128056", new Jclqsf28056());
		map.put("300128070", new Jclqsf28070());
		map.put("300128247", new Jclqsf28247());
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
		return LotteryType.JCLQ_SF;
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
		
		int zhu = Integer.parseInt(finalScore.split(":")[0]);
		int ke = Integer.parseInt(finalScore.split(":")[1]);
		
		
		String result_str = "";
		
		if(zhu>ke) {
			result_str = "3";
		}else {
			result_str = "0";
		}
		
		result.setResult_dynamic(result_str);
		result.setResult_static(result_str);
		
//		result.setPrize_dynamic(race.getPrizeSf());
		
		return result;
	}

}
