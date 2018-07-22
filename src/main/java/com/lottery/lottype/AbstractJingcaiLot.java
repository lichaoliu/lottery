package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.Constants;
import com.lottery.common.contains.ErrorCode;
import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.LotteryOrderLineContains;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.core.dao.JclqRaceDAO;
import com.lottery.core.dao.JczqRaceDao;
import com.lottery.core.domain.JclqRace;
import com.lottery.core.domain.JczqRace;
import com.lottery.lottype.jc.JingcaiBetcodeUtil;
import com.lottery.lottype.jc.JingcaiCanCal;
import com.lottery.lottype.jc.JingcaiCodeItem;
import com.lottery.lottype.jc.JingcaiResult;

@Component
public abstract class AbstractJingcaiLot extends AbstractLot{
	
	
	@Autowired
	private JczqRaceDao jczqRaceDao;
	
	@Autowired
	private JclqRaceDAO jclqRaceDao;
	


	
	protected String danguanType = Constants.JINGCAI_DANGUAN_TYPE;
	/**
	 * 获取竞彩最大场次
	 * @param betcode
	 * @return
	 */
	protected String getMaxTeamid(String betcode) {
		if(betcode.contains("#")) {
			return getDantuoMaxTeamid(betcode);
		}
		return getNormalMaxteamid(betcode);
	}
	
	
	
	/**
	 * 校验竞彩
	 * @param type
	 * @param betcode
	 * @param odd
	 * @return
	 */
	public boolean validateJingcaiOdd(LotteryType type,String betcode,String odd) {
		if(type.getValue()==LotteryType.JCZQ_HHGG.getValue()||type.getValue()==LotteryType.JCLQ_HHGG.getValue()) {
			return checkBetcodeAndOddMix(betcode, odd);
		}
		return checkBetcodeAndOddNormal(betcode, odd);
	}
	
	/**
	 * 判读注码中所有场次是否开奖，不开奖返回不能算奖，返回不能算奖的场次
	 * 开奖返回各个比赛结果，单关过关赔率，是否取消
	 * @param betcode
	 * @param peilu
	 * @return
	 */
	protected JingcaiCanCal jczqCanCal(String betcode,String peilu) {
		if(!checkBetcodeAndOddNormal(betcode,peilu)) {
			throw new LotteryException(ErrorCode.betcode_jingcai_odds_error, ErrorCode.betcode_jingcai_odds_error.memo);
		}
		
		JingcaiCanCal cancal = new JingcaiCanCal();
		cancal.setCanCal(true);
		List<JingcaiCodeItem> JingcaiItems = convertBetItemToJingcaiItem(peilu, betcode);
		
		//转换为JingcaiItem
		//循环JingcaiItem看是否有没开奖的比赛，有就是不能计算，设置为false，保存不能计算的场次
		//如能计算，每个JingcaiItem获取一个JingcaiResult
		for(JingcaiCodeItem item:JingcaiItems) {
			JczqRace jczqRace = jczqRaceDao.getJczqRaceResult(item.getMatchid());
			if(jczqRace==null) {
				cancal.setCanCal(false);
				cancal.setNotOpenMatchid(item.getMatchid());
				break;
			}
			cancal.addCodeItems(item);
			cancal.addResults(getJingcaiResult(jczqRace, item));
		}
		return cancal;
	}
	
	
	/**
	 * 判读注码中所有场次是否开奖，不开奖返回不能算奖，返回不能算奖的场次
	 * 开奖返回各个比赛结果，单关过关赔率，是否取消
	 * @param betcode
	 * @param peilu
	 * @return
	 */
	protected JingcaiCanCal jclqCanCal(String betcode,String peilu) {
		if(!checkBetcodeAndOddNormal(betcode,peilu)) {
			throw new LotteryException(ErrorCode.betcode_jingcai_odds_error, ErrorCode.betcode_jingcai_odds_error.memo);
		}
		
		JingcaiCanCal cancal = new JingcaiCanCal();
		cancal.setCanCal(true);
		List<JingcaiCodeItem> JingcaiItems = convertBetItemToJingcaiItem(peilu, betcode);
		
		//转换为JingcaiItem
		//循环JingcaiItem看是否有没开奖的比赛，有就是不能计算，设置为false，保存不能计算的场次
		//如能计算，每个JingcaiItem获取一个JingcaiResult
		for(JingcaiCodeItem item:JingcaiItems) {
			JclqRace jclqRace = jclqRaceDao.getJclqRaceResult(item.getMatchid());
			if(jclqRace==null) {
				cancal.setCanCal(false);
				cancal.setNotOpenMatchid(item.getMatchid());
				break;
			}
			cancal.addCodeItems(item);
			cancal.addResults(getJingcaiResult(jclqRace, item));
		}
		return cancal;
	}
	
	
	
	
	
	/**
	 * 混合过关篮球
	 * 判读注码中所有场次是否开奖，不开奖返回不能算奖，返回不能算奖的场次
	 * 开奖返回各个比赛结果，单关过关赔率，是否取消
	 * @param betcode
	 * @param peilu
	 * @return
	 */
	protected JingcaiCanCal jclqCanCalMix(String betcode,String peilu) {
		if(!checkBetcodeAndOddMix(betcode,peilu)) {
			throw new LotteryException(ErrorCode.betcode_jingcai_odds_error, ErrorCode.betcode_jingcai_odds_error.memo);
		}
		
		JingcaiCanCal cancal = new JingcaiCanCal();
		cancal.setCanCal(true);
		List<JingcaiCodeItem> JingcaiItems = convertBetItemToJingcaiItemMix(peilu, betcode);
		
		//转换为JingcaiItem
		//循环JingcaiItem看是否有没开奖的比赛，有就是不能计算，设置为false，保存不能计算的场次
		//如能计算，每个JingcaiItem获取一个JingcaiResult
		for(JingcaiCodeItem item:JingcaiItems) {
			JclqRace jclqRace = jclqRaceDao.getJclqRaceResult(item.getMatchid());
			if(jclqRace==null) {
				cancal.setCanCal(false);
				cancal.setNotOpenMatchid(item.getMatchid());
				break;
			}
			cancal.addCodeItems(item);
			cancal.addResults(getJingcaiResult(jclqRace, item));
		}
		return cancal;
	}
	
	
	
	/**
	 * 混合过关足球
	 * 判读注码中所有场次是否开奖，不开奖返回不能算奖，返回不能算奖的场次
	 * 开奖返回各个比赛结果，单关过关赔率，是否取消
	 * @param betcode
	 * @param peilu
	 * @return
	 */
	protected JingcaiCanCal jczqCanCalMix(String betcode,String peilu) {
		if(!checkBetcodeAndOddMix(betcode,peilu)) {
			throw new LotteryException(ErrorCode.betcode_jingcai_odds_error, ErrorCode.betcode_jingcai_odds_error.memo);
		}
		
		JingcaiCanCal cancal = new JingcaiCanCal();
		cancal.setCanCal(true);
		List<JingcaiCodeItem> JingcaiItems = convertBetItemToJingcaiItemMix(peilu, betcode);
		
		//转换为JingcaiItem
		//循环JingcaiItem看是否有没开奖的比赛，有就是不能计算，设置为false，保存不能计算的场次
		//如能计算，每个JingcaiItem获取一个JingcaiResult
		for(JingcaiCodeItem item:JingcaiItems) {
			JczqRace jczqRace = jczqRaceDao.getJczqRaceResult(item.getMatchid());
			if(jczqRace==null) {
				cancal.setCanCal(false);
				cancal.setNotOpenMatchid(item.getMatchid());
				break;
			}
			cancal.addCodeItems(item);
			cancal.addResults(getJingcaiResult(jczqRace, item));
		}
		return cancal;
	}
	
	
	/**
	 * 获取普通过关注码的最大场次
	 * @param betcode
	 * @return
	 */
	private String getNormalMaxteamid(String betcode) {
		betcode = betcode.split("-")[1];
		String[] bets = betcode.split("\\|");
		Long[] matches = new Long[bets.length];
		
		for(int i=0;i<bets.length;i++) {
			matches[i] = Long.parseLong(bets[i].substring(0, 11));
		}
		Arrays.sort(matches);
		
		return String.valueOf(matches[matches.length-1]);
	}

	/**
	 * 获取胆拖过关注码的最大场次
	 * @param betcode
	 * @return
	 */
	private String getDantuoMaxTeamid(String betcode) {
		betcode = betcode.replace("#", "|");
		return getNormalMaxteamid(betcode);
	}
	
	
	/**
	 * 计算注码拆分为N串1的奖级信息，不带倍数不乘以200
	 * @param ticketid
	 * @param betcode
	 * @param odds
	 * @return
	 */
	public abstract List<JingcaiPrizeResult> getJingcaiPrizeResults(String orderid,String betcode,String odds);
	
	
	/**
	 * 计算竞彩奖金
	 * @param orderid
	 * @param betcode
	 * @param odds
	 * @param lotmulti
	 * @return
	 */
	public JingcaiPrizeResult calculateJingcaiPrizeResult(String orderid,String betcode,String odds,BigDecimal lotmulti) {
		List<JingcaiPrizeResult> results = getJingcaiPrizeResults(orderid, betcode, odds);
		
		
		if(results==null) {
			return null;
		}
		BigDecimal prize = BigDecimal.ZERO;
		BigDecimal afterTaxPrize = BigDecimal.ZERO;
		
		for(JingcaiPrizeResult result:results) {
			result.setPrize(getLimitJingCaiPrize(String.valueOf(result.getType()),result.getPrize()));
			result.setAfterTaxPrize(result.getPrize().compareTo(new BigDecimal(1000000))==1?result.getPrize().multiply(new BigDecimal("0.8")):result.getPrize());
			BigDecimal afterPrize = new BigDecimal(getFoueSixInfive(String.valueOf(result.getAfterTaxPrize().divide(new BigDecimal(100)).doubleValue())));
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

	
	
	
	protected JingcaiCanCal getJingcaiZQCanCalNorMal(List<JingcaiCodeItem> items) {
		JingcaiCanCal cancal = new JingcaiCanCal();
		cancal.setCanCal(true);
		
		for(JingcaiCodeItem item:items) {
			JczqRace jczqRace = jczqRaceDao.find(item.getMatchid());
			if(jczqRace==null) {
				cancal.setCanCal(false);
				cancal.setNotOpenMatchid(item.getMatchid());
				break;
			}
			
			JingcaiResult jingcaiResult = getJingcaiResult(jczqRace, item);
			cancal.addResults(jingcaiResult);
		}
		
		return cancal;
	}
	
	protected abstract JingcaiResult getJingcaiResult(JczqRace race,JingcaiCodeItem item);
	
	protected abstract JingcaiResult getJingcaiResult(JclqRace race,JingcaiCodeItem item);
	
	
	
	
	
	
	
	
	
	/**
	 * 将注码赔率转换为JingcaiCodeItem类
	 * 注意没有赔率的类
	 * 同时支持竞彩足球和竞彩篮球普通玩法，混合不支持
	 * 没赔率的，应该以注码为转换基础，赔率选项null
	 * @param odd
	 * @param betcode
	 * @return
	 */
	protected List<JingcaiCodeItem> convertBetItemToJingcaiItem(String odd,String betcode) {
		
		List<JingcaiCodeItem> items = new ArrayList<JingcaiCodeItem>();
		String type = betcode.split("\\-")[0].substring(5);
		LotteryType lotteryType = LotteryType.getLotteryType(Integer.parseInt(betcode.split("\\-")[0].substring(0,4)));
		
		
		/**
		//单关无赔率
		if("1001".equals(type)&&(!lotteryType.equals(LotteryType.JCZQ_BF))&&(!lotteryType.equals(LotteryType.JCLQ_SFC))) {
			betcode = betcode.split("\\-")[1].replace("^", "");
			String[] betcodes = betcode.split("\\|");
			for(String bet:betcodes) {
				JingcaiCodeItem item = new JingcaiCodeItem(bet.substring(0, 11), type);
				String[] splitCodes = bet.replace("(", "").replace(")", "").substring(11).split(",");
				for(String splitcode:splitCodes) {
					item.addCodeItem(splitcode, null);
				}
				items.add(item);
			}
		}else {
			String[] odds = odd.split("\\|");
			for(String oneOdd:odds) {
				JingcaiCodeItem item = new JingcaiCodeItem(oneOdd.substring(0, 11), type);
				oneOdd = oneOdd.substring(11).replace("(", "").replace(")", "");
				if(lotteryType.equals(LotteryType.JCLQ_RFSF)||lotteryType.equals(LotteryType.JCLQ_DXF)) {
					item.setSpecialItem(oneOdd.split(":")[0]);
					for(String codes:oneOdd.split(":")[1].split(",")) {
						item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
					}
				}else {
					for(String codes:oneOdd.split(",")) {
						item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
					}
				}
				items.add(item);
			}
		}*/
		
		String[] odds = odd.split("\\|");
		for(String oneOdd:odds) {
			JingcaiCodeItem item = new JingcaiCodeItem(oneOdd.substring(0, 11), type);
			oneOdd = oneOdd.substring(11).replace("(", "").replace(")", "");
			if(lotteryType.equals(LotteryType.JCLQ_RFSF)||lotteryType.equals(LotteryType.JCLQ_DXF)) {
				item.setSpecialItem(oneOdd.split(":")[0]);
				for(String codes:oneOdd.split(":")[1].split(",")) {
					item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
				}
			}else {
				for(String codes:oneOdd.split(",")) {
					item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
				}
			}
			items.add(item);
		}
		
		return items;
	}
	
	
	
	/**
	 * 混合过关
	 * 将注码赔率转换为JingcaiCodeItem类
	 * 注意没有赔率的类
	 * 同时支持竞彩足球和竞彩篮球普通玩法，混合不支持
	 * 没赔率的，应该以注码为转换基础，赔率选项null
	 * @param odd
	 * @param betcode
	 * @return
	 */
	protected List<JingcaiCodeItem> convertBetItemToJingcaiItemMix(String odd,String betcode) {
		
		List<JingcaiCodeItem> items = new ArrayList<JingcaiCodeItem>();
		String type = betcode.split("\\-")[0].substring(5);
		
		
		String[] odds = odd.split("\\|");
		for(String oneOdd:odds) {
			JingcaiCodeItem item = new JingcaiCodeItem(oneOdd.substring(0, 11), type,LotteryType.getLotteryType(Integer.parseInt(oneOdd.substring(12, 16))));
			oneOdd = oneOdd.substring(16).replace("(", "").replace(")", "");
			if(item.getLotteryType().equals(LotteryType.JCLQ_RFSF)||item.getLotteryType().equals(LotteryType.JCLQ_DXF)) {
				item.setSpecialItem(oneOdd.split(":")[0]);
				for(String codes:oneOdd.split(":")[1].split(",")) {
					item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
				}
			}else {
				for(String codes:oneOdd.split(",")) {
					item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
				}
			}
			items.add(item);
		}
		
		return items;
	}
	
	
	/**
	 * 检查注码和金额，非胆拖
	 * @param betcode
	 * @param odd
	 * @return
	 */
	private boolean checkBetcodeAndOddNormal(String betcode,String odd) {
		try {
			//注码为空不允许
			if(StringUtil.isEmpty(betcode)) {
				return false;
			}
			LotteryType lotteryType = LotteryType.getLotteryType(Integer.parseInt(betcode.substring(0, 4)));
			String type = betcode.split("\\-")[0].substring(5);
			
			//单关玩法的时候，足球比分和篮球胜分差允许赔率为空
//			if("1001".equals(type)) {
//				if((!lotteryType.equals(LotteryType.JCZQ_BF))&&(!lotteryType.equals(LotteryType.JCLQ_SFC))&&StringUtil.isEmpty(odd)) {
//					return true;
//				}
//			}
			Map<String,JingcaiCodeItem> betcodeItems = new HashMap<String,JingcaiCodeItem>();
			Map<String,JingcaiCodeItem> oddItems = new HashMap<String,JingcaiCodeItem>();
			
			//获取注码的Item
			betcode = betcode.split("\\-")[1].replace("^", "");
			String[] betcodes = betcode.split("\\|");
			for(String bet:betcodes) {
				JingcaiCodeItem item = new JingcaiCodeItem(bet.substring(0, 11), type);
				String[] splitCodes = bet.replace("(", "").replace(")", "").substring(11).split(",");
				for(String splitcode:splitCodes) {
					item.addCodeItem(splitcode, null);
				}
				betcodeItems.put(item.getMatchid(), item);
			}
			
			
			//获取赔率的Item
			String[] odds = odd.split("\\|");
			for(String oneOdd:odds) {
				JingcaiCodeItem item = new JingcaiCodeItem(oneOdd.substring(0, 11), type);
				oneOdd = oneOdd.substring(11).replace("(", "").replace(")", "");
				if(lotteryType.equals(LotteryType.JCLQ_RFSF)||lotteryType.equals(LotteryType.JCLQ_DXF)) {
					item.setSpecialItem(oneOdd.split(":")[0]);
					for(String codes:oneOdd.split(":")[1].split(",")) {
						item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
					}
				}else {
					for(String codes:oneOdd.split(",")) {
						item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
					}
				}
				oddItems.put(item.getMatchid(), item);
			}
			
			//注码和赔率的数量不相等，不允许
			if(betcodeItems.size()!=oddItems.size()) {
				return false;
			}
			
			
			//比较过程
			//注码包含的场次，赔率不包含，不允许
			//赔率里篮球让分胜负和大小分如果没有让分数和预设总分，不允许
			//注码的Item投注选项和赔率Item投注选项不同，不允许
			//赔率Item的对应赔率为空，不允许
			for(String key:betcodeItems.keySet()) {
				JingcaiCodeItem oddItem = oddItems.get(key);
				if(oddItem==null) {
					return false;
				}
				
				if(lotteryType.equals(LotteryType.JCLQ_RFSF)||lotteryType.equals(LotteryType.JCLQ_DXF)) {
					if(StringUtil.isEmpty(oddItem.getSpecialItem())) {
						return false;
					}
				}
				JingcaiCodeItem betcodeItem = betcodeItems.get(key);
				
				if(!checkBetPeiluMap(betcodeItem.getCodeItem(), oddItem.getCodeItem())) {
					return false;
				}
				
			}
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	
	
	
	/**
	 * 检查注码和金额，混合
	 * @param betcode
	 * @param odd
	 * @return
	 */
	private boolean checkBetcodeAndOddMix(String betcode,String odd) {
		try {
			//注码为空不允许
			if(StringUtil.isEmpty(betcode)) {
				return false;
			}
			String type = betcode.split("\\-")[0].substring(5);
			
			
			Map<String,JingcaiCodeItem> betcodeItems = new HashMap<String,JingcaiCodeItem>();
			Map<String,JingcaiCodeItem> oddItems = new HashMap<String,JingcaiCodeItem>();
			
			//获取注码的Item
			betcode = betcode.split("\\-")[1].replace("^", "");
			String[] betcodes = betcode.split("\\|");
			for(String bet:betcodes) {
				JingcaiCodeItem item = new JingcaiCodeItem(bet.substring(0, 11), type,LotteryType.getLotteryType(Integer.parseInt(bet.substring(12, 16))));
				String[] splitCodes = bet.replace("(", "").replace(")", "").substring(16).split(",");
				for(String splitcode:splitCodes) {
					item.addCodeItem(splitcode, null);
				}
				betcodeItems.put(item.getMatchid(), item);
			}
			
			
			//获取赔率的Item
			String[] odds = odd.split("\\|");
			for(String oneOdd:odds) {
				JingcaiCodeItem item = new JingcaiCodeItem(oneOdd.substring(0, 11), type,LotteryType.getLotteryType(Integer.parseInt(oneOdd.substring(12, 16))));
				oneOdd = oneOdd.substring(16).replace("(", "").replace(")", "");
				if(item.getLotteryType().equals(LotteryType.JCLQ_RFSF)||item.getLotteryType().equals(LotteryType.JCLQ_DXF)) {
					item.setSpecialItem(oneOdd.split(":")[0]);
					for(String codes:oneOdd.split(":")[1].split(",")) {
						item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
					}
				}else {
					for(String codes:oneOdd.split(",")) {
						item.addCodeItem(codes.split("_")[0], codes.split("_")[1]);
					}
				}
				oddItems.put(item.getMatchid(), item);
			}
			
			//注码和赔率的数量不相等，不允许
			if(betcodeItems.size()!=oddItems.size()) {
				return false;
			}
			
			
			//比较过程
			//注码包含的场次，赔率不包含，不允许
			//赔率里篮球让分胜负和大小分如果没有让分数和预设总分，不允许
			//注码的Item投注选项和赔率Item投注选项不同，不允许
			//赔率Item的对应赔率为空，不允许
			for(String key:betcodeItems.keySet()) {
				JingcaiCodeItem oddItem = oddItems.get(key);
				if(oddItem==null) {
					return false;
				}
				
				if(oddItem.getLotteryType().equals(LotteryType.JCLQ_RFSF)||oddItem.getLotteryType().equals(LotteryType.JCLQ_DXF)) {
					if(StringUtil.isEmpty(oddItem.getSpecialItem())) {
						return false;
					}
				}
				JingcaiCodeItem betcodeItem = betcodeItems.get(key);
				
				if(!checkBetPeiluMap(betcodeItem.getCodeItem(), oddItem.getCodeItem())) {
					return false;
				}
				
			}
			return true;
		}catch(Exception e) {
			return false;
		}
		
	}
	
	
	/**
	 * 比较注码Item和赔率Item
	 * 两者大小要相同，key值要相同，赔率Item里赔率不能为空
	 * @param betmap
	 * @param peilumap
	 * @return
	 */
	private boolean checkBetPeiluMap(Map<String,String> betmap,Map<String,String> peilumap) {
		if(betmap.size()!=peilumap.size()) {
			return false;
		}
		for(String key:betmap.keySet()) {
			if(!peilumap.containsKey(key)) {
				return false;
			}
			if(StringUtil.isEmpty(peilumap.get(key))) {
				return false;
			}
		}
		return true;
	}
	
	
	
	/**
	 * 讲标准注码转换为串1形式来计算注数
	 * @param codeCollection
	 * @param type
	 * @return
	 */
	protected List<List<JingcaiCodeItem>> splitJingcaiItemToN1(List<JingcaiCodeItem> items,int type) {
		List<List<JingcaiCodeItem>> allcodes = new ArrayList<List<JingcaiCodeItem>>();
		List<Integer> combinations = JingcaiBetcodeUtil.getCombinationsByType(type);
		for(int i:combinations) {
			allcodes.addAll(combinationItemList(items, i));
		}
		return allcodes;
	}
	
	
	/**
	 * 从JingcaiItem选出串1
	 * @param <T>
	 * @param betcodes
	 * @param c
	 * @return
	 */
	private <T> List<List<T>> combinationItemList(List<T> betcodes,int c) {
		List<List<T>> list = new ArrayList<List<T>>();
		list.addAll(MathUtils.getCodeCollectionE(betcodes, c));
		return list;
	}
	
	
	/**
	 * 竞彩获取奖金限制
	 * @param type
	 * @param prizePre
	 * @return
	 */
	private static BigDecimal getLimitJingCaiPrize(String type, BigDecimal prizePre) {
		
		long prizef = getFoueSixInfive(prizePre.divide(new BigDecimal(100)).toPlainString());
		
		BigDecimal prize = new BigDecimal(prizef);
		
		if("1001".equals(type)) {
			return prize.compareTo(new BigDecimal(500000000)) == 1 ? new BigDecimal(
					500000000) : prize;
		}else if ("2001".equals(type) || "3001".equals(type) || "3003".equals(type)
				|| "3004".equals(type)) {

			return prize.compareTo(new BigDecimal(20000000)) == 1 ? new BigDecimal(
					20000000) : prize;
		} else if ("4001".equals(type) || "4004".equals(type)
				|| "4005".equals(type) || "4006".equals(type)
				|| "4011".equals(type) || "5001".equals(type)
				|| "5005".equals(type) || "5006".equals(type)
				|| "5010".equals(type) || "5016".equals(type)
				|| "5020".equals(type) || "5026".equals(type)) {
			return prize.compareTo(new BigDecimal(50000000)) == 1 ? new BigDecimal(
					50000000) : prize;
		} else if ("6001".equals(type) || "6006".equals(type)
				|| "6007".equals(type) || "6015".equals(type)
				|| "6020".equals(type) || "6022".equals(type)
				|| "6035".equals(type) || "6042".equals(type)
				|| "6050".equals(type) || "6057".equals(type)
				|| "7001".equals(type) || "7007".equals(type)
				|| "7008".equals(type) || "7021".equals(type)
				|| "7035".equals(type) || "70120".equals(type)
				|| "8001".equals(type) || "8008".equals(type)
				|| "8009".equals(type) || "8028".equals(type)
				|| "8056".equals(type) || "8070".equals(type)
				|| "8247".equals(type)) {
			return prize.compareTo(new BigDecimal(100000000)) == 1 ? new BigDecimal(
					100000000) : prize;
		} else {
			return prize;
		}
	}
	
	
	/**
	 * 四舍六入五成双
	 * @param valueOf
	 * @return
	 */
	public static long getFoueSixInfive(String valueOf) {
		if (valueOf.indexOf(".") == -1) {
			return Long.valueOf(valueOf) * 100;
		} else {
			String xiaoshu = valueOf.substring(valueOf.indexOf(".") + 1);
			if (xiaoshu.length() >= 3) {
				valueOf = valueOf.substring(0, valueOf.indexOf(".") + 4);
				xiaoshu = valueOf.substring(valueOf.indexOf(".") + 1);
				int c = Integer.parseInt(xiaoshu.substring(2, 3));
				if (c < 5) {
					return new BigDecimal(valueOf.substring(0,
							valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).longValue();
				} else if (c > 5) {
					return new BigDecimal(valueOf.substring(0,
							valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).add(BigDecimal.ONE).longValue();
				} else {
					if (Integer.parseInt(xiaoshu.substring(1, 2)) % 2 == 0) {
						return new BigDecimal(valueOf.substring(0,
								valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).longValue();
					} else {
						return new BigDecimal(valueOf.substring(0,
								valueOf.indexOf(".") + 3)).multiply(new BigDecimal(100)).add(BigDecimal.ONE).longValue();
					}
				}
			} else {
				return new BigDecimal(valueOf).multiply(new BigDecimal(100)).longValue();
			}

		}
	}
	
	
	
	@Override
	public String getAllMatches(String betcode,int prizeOptimize) {
		betcode = betcode.replace("#", "|");
		
		Set<String> matches = new HashSet<String>();
		
		for(String bet:betcode.split("!")) {
			bet = bet.split("\\-")[1].replace("^", "");
			if(prizeOptimize==YesNoStatus.yes.value) {
				bet = bet.split(LotteryOrderLineContains.PRIZE_OPTIMIZE_SPLITLINE)[0];
			}
			
			for(String onebet:bet.split("\\|")) {
				matches.add(onebet.substring(0, 11));
			}
		}
		
		List<String> matchlist = new ArrayList<String>();
		matchlist.addAll(matches);
		
		Collections.sort(matchlist);
		
		StringBuilder matchstr = new StringBuilder();
		for(String match:matchlist) {
			matchstr.append(match).append(",");
		}
		
		if(matchstr.toString().endsWith(",")) {
			matchstr.deleteCharAt(matchstr.length()-1);
		}
		return matchstr.toString();
	}
	
	
	
}
