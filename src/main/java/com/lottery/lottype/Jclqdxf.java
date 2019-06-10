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
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf11001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf12001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf13001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf13003;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf13004;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf14001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf14004;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf14005;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf14006;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf14011;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15005;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15006;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15010;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15016;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15020;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf15026;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16006;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16007;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16015;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16020;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16022;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16035;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16042;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16050;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf16057;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf17001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf17007;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf17008;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf17021;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf17035;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf17120;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18008;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18009;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18028;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18056;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18070;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf18247;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf22001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf23001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf23003;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf23004;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf24001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf24004;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf24005;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf24006;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf24011;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25005;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25006;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25010;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25016;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25020;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf25026;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26006;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26007;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26015;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26020;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26022;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26035;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26042;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26050;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf26057;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf27001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf27007;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf27008;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf27021;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf27035;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf27120;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28001;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28008;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28009;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28028;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28056;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28070;
import com.lottery.lottype.jc.jclq.dxf.Jclqdxf28247;

@Component("3004")
public class Jclqdxf extends AbstractJingcaiLot{

	private Logger logger = LoggerFactory.getLogger(Jclqdxf.class);
	
	
	public LotPlayType getLotPlayType(String playType) {
		return map.get(playType);
	}
	
	Map<String, LotJingCaiPlayType> map = new HashMap<String, LotJingCaiPlayType>();
	{
		map.put("300411001", new Jclqdxf11001());
		map.put("300412001", new Jclqdxf12001());
		map.put("300413001", new Jclqdxf13001());
		map.put("300413003", new Jclqdxf13003());
		map.put("300413004", new Jclqdxf13004());
		map.put("300414001", new Jclqdxf14001());
		map.put("300414004", new Jclqdxf14004());
		map.put("300414005", new Jclqdxf14005());
		map.put("300414006", new Jclqdxf14006());
		map.put("300414011", new Jclqdxf14011());
		
		map.put("300415001", new Jclqdxf15001());
		map.put("300415005", new Jclqdxf15005());
		map.put("300415006", new Jclqdxf15006());
		map.put("300415010", new Jclqdxf15010());
		map.put("300415016", new Jclqdxf15016());
		map.put("300415020", new Jclqdxf15020());
		map.put("300415026", new Jclqdxf15026());
		
		map.put("300416001", new Jclqdxf16001());
		map.put("300416006", new Jclqdxf16006());
		map.put("300416007", new Jclqdxf16007());
		map.put("300416015", new Jclqdxf16015());
		map.put("300416020", new Jclqdxf16020());
		map.put("300416022", new Jclqdxf16022());
		map.put("300416035", new Jclqdxf16035());
		map.put("300416042", new Jclqdxf16042());
		map.put("300416050", new Jclqdxf16050());
		map.put("300416057", new Jclqdxf16057());
		
		map.put("300417001", new Jclqdxf17001());
		map.put("300417007", new Jclqdxf17007());
		map.put("300417008", new Jclqdxf17008());
		map.put("300417021", new Jclqdxf17021());
		map.put("300417035", new Jclqdxf17035());
		map.put("300417120", new Jclqdxf17120());
		
		map.put("300418001", new Jclqdxf18001());
		map.put("300418008", new Jclqdxf18008());
		map.put("300418009", new Jclqdxf18009());
		map.put("300418028", new Jclqdxf18028());
		map.put("300418056", new Jclqdxf18056());
		map.put("300418070", new Jclqdxf18070());
		map.put("300418247", new Jclqdxf18247());
		
		
		
		map.put("300422001", new Jclqdxf22001());
		map.put("300423001", new Jclqdxf23001());
		map.put("300423003", new Jclqdxf23003());
		map.put("300423004", new Jclqdxf23004());
		map.put("300424001", new Jclqdxf24001());
		map.put("300424004", new Jclqdxf24004());
		map.put("300424005", new Jclqdxf24005());
		map.put("300424006", new Jclqdxf24006());
		map.put("300424011", new Jclqdxf24011());
		
		map.put("300425001", new Jclqdxf25001());
		map.put("300425005", new Jclqdxf25005());
		map.put("300425006", new Jclqdxf25006());
		map.put("300425010", new Jclqdxf25010());
		map.put("300425016", new Jclqdxf25016());
		map.put("300425020", new Jclqdxf25020());
		map.put("300425026", new Jclqdxf25026());
		
		map.put("300426001", new Jclqdxf26001());
		map.put("300426006", new Jclqdxf26006());
		map.put("300426007", new Jclqdxf26007());
		map.put("300426015", new Jclqdxf26015());
		map.put("300426020", new Jclqdxf26020());
		map.put("300426022", new Jclqdxf26022());
		map.put("300426035", new Jclqdxf26035());
		map.put("300426042", new Jclqdxf26042());
		map.put("300426050", new Jclqdxf26050());
		map.put("300426057", new Jclqdxf26057());
		
		map.put("300427001", new Jclqdxf27001());
		map.put("300427007", new Jclqdxf27007());
		map.put("300427008", new Jclqdxf27008());
		map.put("300427021", new Jclqdxf27021());
		map.put("300427035", new Jclqdxf27035());
		map.put("300427120", new Jclqdxf27120());
		
		map.put("300428001", new Jclqdxf28001());
		map.put("300428008", new Jclqdxf28008());
		map.put("300428009", new Jclqdxf28009());
		map.put("300428028", new Jclqdxf28028());
		map.put("300428056", new Jclqdxf28056());
		map.put("300428070", new Jclqdxf28070());
		map.put("300428247", new Jclqdxf28247());
		
		
		
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
		return LotteryType.JCLQ_DXF;
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
		boolean cancel = race.getStatus().equals(RaceStatus.CANCEL.getValue())?true:false;
		
		JingcaiResult result = new JingcaiResult(race.getMatchNum(),cancel);
		
		if(result.isCancel()) {
			return result;
		}
		
		String finalScore = race.getFinalScore();
		
		double zhu = Double.parseDouble(finalScore.split(":")[0]);
		double ke = Double.parseDouble(finalScore.split(":")[1]);
		
		double sum = zhu + ke;
		if(sum>Double.parseDouble(item.getSpecialItem().replace("+", ""))) {
			result.setResult_static("1");
		}else {
			result.setResult_static("2");
		}
		return result;
	}

}
