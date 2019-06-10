package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import com.lottery.core.service.bet.BettingLimitNumberService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.lottery.LotteryAddPrizeStrategyStatus;
import com.lottery.common.contains.lottery.PlayType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.LotteryAddPrizeStrategyDao;
import com.lottery.core.dao.LotteryOrderDAO;
import com.lottery.core.dao.impl.AwardLevelDaoImpl;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.core.domain.LotteryAddPrizeStrategy;
import com.lottery.core.service.LotteryPhaseService;

@Component
public abstract class AbstractLot implements Lot {
	
	protected Logger logger = LoggerFactory.getLogger(getClass());
	
	@Override
	public boolean isBetcodeLimited(String betcode,List<BettingLimitNumber> limitedCodes) {
		if(limitedCodes==null||limitedCodes.isEmpty()) {
			return false;
		}
		LotPlayType type = getLotPlayType(betcode.split("\\-")[0]);
		for(BettingLimitNumber limitedCode:limitedCodes) {
			if(type.isBetcodeLimited(betcode, limitedCode)) {
				return true;
			}
		}
		return false;
	}
	
	
	private Set<String> getUniqueCode(String betcode, int lotmulti, long amt, int oneAmount) {
		List<SplitedLot> splits = split(betcode, lotmulti, amt, oneAmount);
		Set<String> set = new HashSet<String>();
		for(SplitedLot lot:splits) {
			set.add(lot.getBetcode());
		}
		return set;
	}
	
	private boolean isBetcodesLimited(Set<String> betcodes,List<BettingLimitNumber> limitedCodes) {
		if(limitedCodes==null||limitedCodes.isEmpty()) {
			return false;
		}
		Map<String, List<BettingLimitNumber>> classifyLimitedCodes = classifyBettingLimitNumber(limitedCodes);
		for(String betcode:betcodes) {
			if(!classifyLimitedCodes.containsKey(betcode.split("\\-")[0])) {
				continue;
			}
			if(isBetcodeLimited(betcode, classifyLimitedCodes.get(betcode.split("\\-")[0]))) {
				return true;
			}
		}
		return false;
	}
	
	
	@Override
	public List<String> canUnionTypes() {
		return null;
	}
	
	protected Map<String,List<BettingLimitNumber>> classifyBettingLimitNumber(List<BettingLimitNumber> limitedCodes) {
		if(limitedCodes==null||limitedCodes.isEmpty()) {
			return null;
		}
		Map<String,List<BettingLimitNumber>> map  = new HashMap<String, List<BettingLimitNumber>>();
		for(BettingLimitNumber code:limitedCodes) {
			if(map.containsKey(String.valueOf(code.getPlayType()))) {
				map.get(String.valueOf(code.getPlayType())).add(code);
			}else {
				List<BettingLimitNumber> list = new ArrayList<BettingLimitNumber>();
				list.add(code);
				map.put(String.valueOf(code.getPlayType()), list);
			}
		}
		return map;
	}
	

    @Autowired
	protected BettingLimitNumberService bettingLimitNumberService;

	protected boolean validateBasicBetcode(String betcode) {
		if(StringUtil.isEmpty(betcode)||betcode.endsWith("!")||(!betcode.endsWith("^"))) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean validateWithLimited(String betcode,BigDecimal amount,BigDecimal beishu,int oneAmount) {
		boolean validate = validate(betcode, amount, beishu, oneAmount);
		if(validate) {
			boolean limited = false;
			try {
				List<BettingLimitNumber> limitedCodes = bettingLimitNumberService.getByLotteryType(getLotteryType().value);
				if(limitedCodes==null||limitedCodes.isEmpty()) {
					return validate;
				}
				Set<String> uniqueCodes = getUniqueCode(betcode, beishu.intValue(), amount.longValue(), oneAmount);
				limited = isBetcodesLimited(uniqueCodes, limitedCodes);
			} catch(Exception e) {
				
			}
			if(limited) {
				throw new LotteryException(ErrorCode.bet_limit, ErrorCode.bet_limit.memo);
			}
		}
		return validate;
	}
	
	@Override
	public boolean validatePrizeOptimize(String betcode,BigDecimal amount,BigDecimal beishu,int oneAmount) {
		if(StringUtil.isEmpty(betcode)) {
			return false;
		}
		
		long totalAmt = 0;
		for(String code:betcode.split("!")) {
			if(Integer.parseInt(code.split(":")[1])<1) {
				return false;
			}
			boolean validate = validate(code.split(":")[0], new BigDecimal(code.split(":")[2]), new BigDecimal(code.split(":")[1]), oneAmount);
			if(!validate) {
				logger.error("奖金优化注码验证出错code={} betcode={} amount={} beishu={}",new Object[]{code,betcode,amount,beishu});
				return false;
			}
			
			totalAmt = totalAmt + Long.parseLong(code.split(":")[2]);
		}
		return amount.longValue()==totalAmt*beishu.longValue();
		
	}
	
	@Override
	public List<SplitedLot> splitPrizeOptimize(String betcode,int lotmulti,long amt,int oneAmount) {
		List<SplitedLot> splitedLots = new ArrayList<SplitedLot>();
		for(String code:betcode.split("!")) {
			splitedLots.addAll(split(code.split(":")[0], Integer.parseInt(code.split(":")[1])*lotmulti, Integer.parseInt(code.split(":")[2])*lotmulti, oneAmount));
		}
		return splitedLots;
	}


	@Override
	public PrizeResult calcuteOrderpirzeamt(String betcode,int lotmulti,long amt,int oneAmount,String wincode,String phase) {
		List<SplitedLot> codes = split(betcode, lotmulti, amt, oneAmount);
		
		List<String> prizeinfos = new ArrayList<String>();
		
		for(SplitedLot lot:codes) {
			String prize = getPrizeLevelInfo(lot.getBetcode(), wincode, new BigDecimal(lot.getLotMulti()), oneAmount);
			if(!StringUtil.isEmpty(prize)) {
				prizeinfos.add(prize);
			}
		}
		
		String prizeInfo = getPrizeInfo(prizeinfos);
		
		if(!StringUtil.isEmpty(prizeInfo)) {
			return caculatePrizeAmt(prizeInfo, getAwardLevels(getLotteryType().getValue(), phase), new BigDecimal(oneAmount));
		}
		return null;
	}


	@Override
	public boolean validatePrizeDetail(String prizedetail) {
		return true;
	}


	@Autowired
	private AwardLevelDaoImpl awardleveldao;
	
	@Autowired
	private LotteryAddPrizeStrategyDao lotteryAddPrizeStrategyDao;
	


	@Autowired
	protected LotteryOrderDAO lotteryOrderDao;

	@Autowired
	protected LotteryPhaseService phaseDao;
	
	@Override
	public String getPrizeLevelInfo(String betcode, String wincode,
			BigDecimal lotmulti,int oneAmount) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public boolean validatePhase(String phase) {
		return false;
	}

	
	
	protected String combinePrizeInfo(String prizeInfo, BigDecimal lotmulti) {
		if (StringUtil.isEmpty(prizeInfo)) {
			return "";
		}
		int total = 0;
		Map<String, Integer> map = new HashMap<String, Integer>();
		for (String prize : prizeInfo.split(",")) {
			if (map.get(prize) == null) {
				map.put(prize, 1 * lotmulti.intValue());
			} else {
				total = map.get(prize) + 1 * lotmulti.intValue();
				map.put(prize, total);
			}
		}

		StringBuilder sb = new StringBuilder();
		for (String key : map.keySet()) {
			sb.append(key).append("_").append(map.get(key)).append(",");
		}
		if (!StringUtil.isEmpty(sb.toString())) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	@Override
	public Map<String,Long> getAwardLevels(int lotterytype, String phase) {
		List<LotteryAddPrizeStrategy> strategys = lotteryAddPrizeStrategyDao.findAddPrizeStrategys(lotterytype, phase, LotteryAddPrizeStrategyStatus.OPEN);
		Map<String, Long> levels = awardleveldao.getPrizeLevel(lotterytype);
		for(LotteryAddPrizeStrategy strategy:strategys) {
			if(levels.containsKey(strategy.getId().getPrizeLevel())) {
				levels.put(strategy.getId().getPrizeLevel(), levels.get(strategy.getId().getPrizeLevel())+strategy.getAddamt());
			}
		}
		return levels;
	}
	
	@Override
	public Map<String,Long> getAwardLevelsByPrizeInfo(int lotterytype,String phase,String prizeInfo) {
		List<LotteryAddPrizeStrategy> strategys = lotteryAddPrizeStrategyDao.findAddPrizeStrategys(lotterytype, phase, LotteryAddPrizeStrategyStatus.OPEN);
		Map<String, Long> levels = convertPrizeInfo(prizeInfo);
		for(LotteryAddPrizeStrategy strategy:strategys) {
			if(levels.containsKey(strategy.getId().getPrizeLevel())) {
				levels.put(strategy.getId().getPrizeLevel(), levels.get(strategy.getId().getPrizeLevel())+strategy.getAddamt());
			}
		}
		return levels;
	}
	
	
	private Map<String,Long> convertPrizeInfo(String prizeInfo) {
		Map<String, String> mapper = getPrizeLevelMapper();
		Map<String, Long> levels = new HashMap<String, Long>();
		for(String info:prizeInfo.split(",")) {
			if(mapper!=null) {
				levels.put(mapper.containsKey(info.split("\\_")[0])?mapper.get(info.split("\\_")[0]):info.split("\\_")[0], 
						Long.parseLong(info.split("\\_")[2])*100);
			}else {
				levels.put(info.split("\\_")[0], Long.parseLong(info.split("\\_")[2])*100);
			}
			
		}
		return levels;
	}
	
	@Override
	public Map<String,String> getPrizeLevelMapper() {
		return new HashMap<String,String>();
	}
	
	
	@Override
	public PrizeResult caculatePrizeAmt(String prizeinfo,
			Map<String, Long> prizeLevelInfo, BigDecimal oneMoney) {
		BigDecimal preTaxAmount = BigDecimal.ZERO;
		BigDecimal afterTaxAmount = BigDecimal.ZERO;
		for (String prize : prizeinfo.split(",")) {
			if (StringUtil.isEmpty(prize)) {
				continue;
			}

			String[] values = prize.split("_");
			Long amt = prizeLevelInfo.get(values[0]);
			if (amt == null) {
				PrizeResult result = new PrizeResult(BigDecimal.ZERO, BigDecimal.ZERO, prizeinfo);
				result.setBig(isBigPrize(prizeinfo, BigDecimal.ZERO, BigDecimal.ZERO));
				return result;
			}
			// 中得改奖级的个数
			int prizeNumber = Integer.parseInt(values[1]);

			preTaxAmount = preTaxAmount.add(new BigDecimal(amt * prizeNumber));
			if (amt > 1000000) {
				afterTaxAmount = afterTaxAmount.add(new BigDecimal(amt * 4 / 5 * prizeNumber)) ;
			} else {
				afterTaxAmount = afterTaxAmount.add(new BigDecimal(amt * prizeNumber)) ;
			}

		}

		PrizeResult result = new PrizeResult(preTaxAmount, afterTaxAmount,prizeinfo);
		result.setBig(isBigPrize(prizeinfo, result.getPreTaxAmt(), result.getAfterTaxAmt()));
		return result;
	}
	
	
	@Override
	public String getPrizeInfo(List<String> prizeinfos) {
		if (null == prizeinfos || prizeinfos.isEmpty()) {
			return "";
		}
		try {
			Map<String, Integer> map = new HashMap<String, Integer>();
			for (String prizeinfo : prizeinfos) {
				if (!StringUtil.isEmpty(prizeinfo.trim())) {
					String[] infos = prizeinfo.split(",");
					for (String info : infos) {
						String[] values = info.split("_");
						Integer count = map.get(values[0]);
						count = null == count ? 0 : count;
						map.put(values[0], count + Integer.parseInt(values[1]));
					}
				}
			}
			List<String> list = new ArrayList<String>();
			for (Entry<String, Integer> entry : map.entrySet()) {
				list.add(entry.getKey() + "_" + entry.getValue());
			}
			StringBuilder builder = new StringBuilder();
			for (String s : list) {
				builder.append(s).append(",");
			}
			if (builder.length() == 0) {
				return builder.toString();
			}
			builder.deleteCharAt(builder.length() - 1);
			return builder.toString();
		} catch (Exception e) {
			return null;
		}
	}
	
	
	@Override
	public PlayType getBetcodePlayType(String betcode) {
		String playtype = betcode.split("!")[0].split("-")[0];
		
		for(String bet:betcode.split("!")) {
			if(!bet.split("-")[0].equals(playtype)) {
				return PlayType.mix;
			}
		}
		return PlayType.get(Integer.parseInt(playtype));
	}


	@Override
	public String getAllPlayType(String betcode) {
		Set<String> playtypes = new HashSet<String>();
		
		for(String bet:betcode.split("!")) {
			playtypes.add(bet.split("\\-")[0]);
		}
		
		StringBuilder types = new StringBuilder();
		for(String type:playtypes) {
			types.append(type).append(",");
		}
		
		if(types.toString().endsWith(",")) {
			types = types.deleteCharAt(types.length()-1);
		}
		
		return types.toString();
	}

	

	@Override
	public BigDecimal computeAchievement(BigDecimal amt, BigDecimal preTaxAmount, BigDecimal afterTaxAmount) {
		BigDecimal result = BigDecimal.ZERO;
		if (amt == null || preTaxAmount == null || afterTaxAmount == null) {
			return result;
		}
		// 盈利=税后奖金-方案金额
		BigDecimal afterAmt = afterTaxAmount.add(amt.negate());
		// 盈利回报 = 税后奖金/方案金额
		BigDecimal afterrate = BigDecimal.ZERO;
		if (amt.compareTo(BigDecimal.ZERO) > 0) {
			afterrate = afterTaxAmount.divide(amt, 0, BigDecimal.ROUND_HALF_UP);
			if (afterAmt.compareTo(new BigDecimal(10000000)) >= 0 || afterrate.compareTo(new BigDecimal(500)) >= 0) {// 税后盈利达到10万或回报达到500倍，可获得两颗金星
				result = result.add(new BigDecimal(20));
			} else if (preTaxAmount.compareTo(new BigDecimal(100000)) >= 0
					|| afterrate.compareTo(new BigDecimal(10)) >= 0) {// 税前奖金达到1000，或回报大于10倍
				result = result.add(new BigDecimal(10));
			}
			if (preTaxAmount.compareTo(new BigDecimal(100000000)) >= 0) {
				result = result.add(new BigDecimal(10));
			}
		}
		return result;
	}
	
	@Override
	public String getAllMatches(String betcode, int prizeOptimize) {
		return null;
	}
	
	protected boolean isZcSingleUpload(String betcode,String playtype) {
		for(String bet:betcode.split("\\!")) {
			if(!playtype.equals(bet.split("\\-")[0])) {
				return false;
			}
		}
		return true;
	}
	
	
	protected String unionBetcode(String betcode,List<String> canUnion) {
		if(canUnion==null||canUnion.isEmpty()) {
			return betcode;
		}
		Map<String,StringBuilder> map = new HashMap<String, StringBuilder>();
		StringBuilder unionCode = new StringBuilder();
		for(String code:betcode.split("!")) {
			String type = code.substring(0,6);
			if(canUnion.contains(type)) {
				if(map.containsKey(type)) {
					map.get(type).append(code.split("\\-")[1]);
				}else {
					StringBuilder builder = new StringBuilder();
					builder.append(code.split("\\-")[1]);
					map.put(type, builder);
				}
			}else {
				unionCode.append(code).append("!");
			}
		}
		for(String type:map.keySet()) {
			unionCode.append(type).append("-").append(map.get(type).toString()).append("!");
		}
		
		if(unionCode.toString().endsWith("!")) {
			unionCode = unionCode.deleteCharAt(unionCode.length()-1);
		}
		return unionCode.toString();
	}


	protected boolean isLimitting(int lotteryType,String betcode){
		return false;
	}
}
