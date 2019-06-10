package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.JcGuanyajunRaceDao;
import com.lottery.core.domain.JcGuanYaJunRace;
import com.lottery.lottype.jc.JingcaiGuanyajunCanCal;


public abstract class AbstractGuanyajunLot extends AbstractLot{
	
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected JcGuanyajunRaceDao jcGuanyajunRaceDao;

	private String oddRegex = "\\d+[_]\\d+(\\.\\d+)?";
	
	public boolean validateJcGuanyajunOdd(String betcode,String odds) {
		if(StringUtil.isEmpty(betcode)||StringUtil.isEmpty(odds)) {
			return false;
		}
		
		if(!isOddMatch(odds)) {
			return false;
		}
		
		if(!isSetEqual(getBetcodeMatch(betcode), getOddMatch(odds))) {
			return false;
		}
		
		return true;
	}
	
	
	private Set<String> getBetcodeMatch(String betcode) {
		return new HashSet<String>(Arrays.asList(betcode.split("\\-")[1].replace("^", "").split(",")));
	}
	
	
	private Set<String> getOddMatch(String odds) {
		Set<String> match = new HashSet<String>();
		for(String odd:odds.split(",")) {
			match.add(odd.substring(0, 2));
		}
		return match;
	}
	
	
	private boolean isOddMatch(String odds) {
		for(String odd:odds.split(",")) {
			if(!odd.matches(oddRegex)) {
				return false;
			}
		}
		return true;
	}
	
	private Map<String,String> getMatchOdd(String odds) {
		Map<String,String> matchOdd = new HashMap<String, String>();
		for(String odd:odds.split(",")) {
			matchOdd.put(odd.split("\\_")[0], odd.split("\\_")[1]);
		}
		return matchOdd;
	}
	
	private boolean isSetEqual(Set<String> a,Set<String> b) {
		if(a.size()!=b.size()) {
			return false;
		}
		
		for(String value:a) {
			if(!b.contains(value)) {
				return false;
			}
		}
		return true;
	}
	
	
	
	protected JingcaiGuanyajunCanCal canCal(String betcode,int lotteryType,String phase) {
		Set<String> matches = getBetcodeMatch(betcode);
		JingcaiGuanyajunCanCal cancal = new JingcaiGuanyajunCanCal();
		cancal.setCanCal(true);
		for(String match:matches) {
			JcGuanYaJunRace race = jcGuanyajunRaceDao.getGuanyajunResult(lotteryType, phase, match);
			if(race==null) {
				cancal.setCanCal(false);
				cancal.setNotOpenMatchid(match);
				return cancal;
			}
			cancal.addResult(race);
		}
		return cancal;
	}
	
	
	
	private List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,
			String betcode, String odds,int lotteryType,String phase) {
		
		if(!validateJcGuanyajunOdd(betcode, odds)) {
			logger.error("票{}赔率格式错误",orderid);
			return null;
		}
		
		
		JingcaiGuanyajunCanCal canCal = canCal(betcode, lotteryType, phase);
		if(canCal.isCanCal()==false) {
			//lotteryOrderDao.updateOrderLastMatchNum(orderid, canCal.getNotOpenMatchid());
			logger.error("票{}未全部开奖,退出算奖,本次未开奖的场次为{}",new Object[]{orderid,canCal.getNotOpenMatchid()});
			return null;
		}
		
		
		Map<String, String> matchOdd = getMatchOdd(odds);
		List<JingcaiPrizeResult> results = new ArrayList<JingcaiPrizeResult>();
		
		
		for(String match:matchOdd.keySet()) {
			if(canCal.getResult().get(match).getIsWin()==YesNoStatus.yes.value) {
				JingcaiPrizeResult result = new JingcaiPrizeResult();
				result.setType(1);
				result.setBig(false);
				result.setPrize(new BigDecimal(matchOdd.get(match)).multiply(new BigDecimal(200)));
				results.add(result);
			}
		}
		return results;
	}
	
	
	
	public JingcaiPrizeResult calculateGuanyajunPrizeResult(String orderid,String betcode,String odds,BigDecimal lotmulti,int lotteryType,String phase) {
		List<JingcaiPrizeResult> results = getJingcaiPrizeResults(orderid, betcode, odds,lotteryType,phase);
		
		
		if(results==null) {
			return null;
		}
		BigDecimal prize = BigDecimal.ZERO;
		BigDecimal afterTaxPrize = BigDecimal.ZERO;
		
		for(JingcaiPrizeResult result:results) {
			result.setAfterTaxPrize(result.getPrize().compareTo(new BigDecimal(1000000))==1?result.getPrize().multiply(new BigDecimal("0.8")):result.getPrize());
			BigDecimal afterPrize = new BigDecimal(MathUtils.getFoueSixInfive(String.valueOf(result.getAfterTaxPrize().divide(new BigDecimal(100)).doubleValue())));
			result.setAfterTaxPrize(afterPrize);
			prize = prize.add(result.getPrize());
			afterTaxPrize = afterTaxPrize.add(result.getAfterTaxPrize());
		}
		
		JingcaiPrizeResult pr = new JingcaiPrizeResult();
		pr.setPrize(prize.multiply(lotmulti));
		pr.setAfterTaxPrize(afterTaxPrize.multiply(lotmulti));
		pr.setPrizedetail(getJingcaiPrizeDetail(results,lotmulti));
		pr.setBig(isBigPrize("", pr.getPrize(), pr.getAfterTaxPrize()));
		return pr;
	}
	
	
	private String getJingcaiPrizeDetail(List<JingcaiPrizeResult> results,BigDecimal lotmulti) {
		Map<String,Integer> detailMap = new HashMap<String,Integer>();
		
		for(JingcaiPrizeResult result:results) {
			if(detailMap.containsKey(String.valueOf(result.getType()))) {
				detailMap.put(String.valueOf(result.getType()), detailMap.get(String.valueOf(result.getType()))+1);
			}else {
				detailMap.put(String.valueOf(result.getType()), 1);
			}
		}
		StringBuilder detail = new StringBuilder();
		for(String key:detailMap.keySet()) {
			detail.append(key).append("_").append(detailMap.get(key)*lotmulti.intValue()).append(",");
		}
		
		if(detail.toString().endsWith(",")) {
			detail.deleteCharAt(detail.length()-1);
		}
		return detail.toString();
	} 
	
	
	
	@Override
	public String getAllMatches(String betcode,int prizeOptimize) {
		return betcode.split("\\-")[1].replace("^", "");
	}
	
	public static void main(String[] args) {
		String oddRegex = "\\d+[_]\\d+(\\.\\d+)?";
		
		System.out.println("02_32.23".matches(oddRegex));
	}

}
