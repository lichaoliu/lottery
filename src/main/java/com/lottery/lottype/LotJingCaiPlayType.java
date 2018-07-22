package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.core.domain.BettingLimitNumber;
import com.lottery.lottype.jc.JingcaiBetcodeUtil;

@Component
public abstract class LotJingCaiPlayType implements LotPlayType{

	@Autowired
	private Map<String,AbstractJingcaiLot> map;
	
	//key playType  value amt
	private Map<String,String> splitConfigMap;
	
	
	public List<SplitedLot> splitJingcaiLot(List<SplitedLot> splits,int oneAmount) {
		
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		
		for(SplitedLot lot:splits) {
			String limit = splitConfigMap.get(lot.getBetcode().substring(0, 9));
//			long amount = getSingleBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount);
			if(StringUtil.isNotEmpt(limit)&&lot.getAmt()>=Long.parseLong(limit)&&Long.parseLong(limit)<=2000000) {
				realsplits.addAll(splitAmtLimit(lot, Long.parseLong(limit)));
			}else {
				realsplits.addAll(splitIgnoreAmtLimit(lot));
			}
		}
		for(SplitedLot lot:realsplits) {
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount));
		}
		return realsplits;
	}
	
	
	private List<SplitedLot> splitIgnoreAmtLimit(SplitedLot lot) {
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		if(!SplitedLot.isToBeSplit99(lot.getLotMulti(), lot.getAmt())) {
			realsplits.add(lot);
		}else {
			int amtSingle = (int)(lot.getAmt()/lot.getLotMulti());
			int permissionLotmulti = 2000000 / amtSingle;
			if(permissionLotmulti>99) {
				permissionLotmulti = 99;
			}
			realsplits.addAll(SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype()));
		}
		return realsplits;
	}
	
	
	private List<SplitedLot> splitAmtLimit(SplitedLot lot,long amt) {
		long amtSingle = lot.getAmt()/lot.getLotMulti();
		long beishuLong = amt/amtSingle;
		
		//达到金额的倍数大于99 按照原来规则
		if(beishuLong>99) {
			return splitIgnoreAmtLimit(lot);
		}
		
		double beishuDouble = (double)amt/amtSingle;
		
		//达到金额的倍数小于等于1 按照1倍拆
		if(beishuDouble<=1.0d) {
			int permissionLotmulti = 1;
			return SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype());
		}
		
		//倍数刚好是整数 按倍数拆
		if(beishuDouble==(double)beishuLong) {
			int permissionLotmulti = (int)beishuLong;
			return SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype());
		}
		
		//倍数刚好是小数 且票总倍数除以(倍数+1) 大于等于2  按照倍数+1拆
		//大于等于2  意味着能拆出多个符合的票  如果不小于2 没必要拆
		if(beishuDouble>(double)beishuLong&&lot.getLotMulti()/(beishuLong+1)>=2) {
			int permissionLotmulti = (int)beishuLong + 1;
			return SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype());
		}
		return splitIgnoreAmtLimit(lot);
	}
	
	
	protected long mulSelectCode(List<String> selects) {
		long zhushu = 1;
		for(String select:selects) {
			int start = select.indexOf("(");
			int end = select.indexOf(")");
			select = select.substring(start+1, end);
			zhushu = zhushu * select.split(",").length;
		}
		return zhushu;
	}
	
	protected abstract int getLotterytype();
	
	protected abstract int getPlayType();
	
	protected abstract long getNormalBetAmount(String betcode, BigDecimal beishu,int oneAmount);
	
	/**
	 * 根据玩法获取标准场次数
	 * @param type
	 * @return
	 */
	protected int getNeedMatchNumByType() {
		int type = getPlayType();
		return JingcaiBetcodeUtil.getNeedMatchNumByType(type);
	}
	
	
	/**
	 * 获取注码中的总的场次数量，过滤重复
	 * @param betcode
	 * @return
	 */
	protected int getTotalMatchNum(String betcode) {
		Set<String> set = new HashSet<String>();
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			set.add(code.substring(0, 11));
		}
		return set.size();
	}
	
	/**
	 * 判断注码场次是否重复
	 * @param betcode
	 * @return
	 */
	protected boolean isMatchDuplication(String betcode) {
		return isDuplication(getMatchs(betcode));
	}
	
	
	/**
	 * 判断注码场次是否重复,混合过关，需要场次和玩法一起判断
	 * @param betcode
	 * @return
	 */
	protected boolean isMatchDuplicationMix(String betcode) {
		return isDuplication(getMixMatchs(betcode));
	}
	
	
	
	/**
	 * 判断注码中实际投注内容是否有重复
	 * @param betcode 实际投注内容
	 * @param codeLength 每位注码长度
	 * @return
	 */
	protected boolean isBetcodeDuplication(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			String bet = code.substring(11).replace("(", "").replace(")", "");
			if(isDuplication(Arrays.asList(bet.split(",")))) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * 判断注码中实际投注内容是否有重复
	 * @param betcode 实际投注内容
	 * @param codeLength 每位注码长度
	 * @return
	 */
	protected boolean isBetcodeDuplicationMix(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			String bet = code.substring(16).replace("(", "").replace(")", "");
			if(isDuplication(Arrays.asList(bet.split(",")))) {
				return true;
			}
		}
		return false;
	}
	
	
	
	/**
	 * 获取注码中所有场次，不忽略重复
	 * @param betcode
	 * @return
	 */
	private List<String> getMatchs(String betcode) {
		List<String> matches = new ArrayList<String>();
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			matches.add(code.substring(0, 11));
		}
		return matches;
	} 
	
	
	/**
	 * 获取注码中所有场次，不忽略重复
	 * @param betcode
	 * @return
	 */
	private List<String> getMixMatchs(String betcode) {
		List<String> matches = new ArrayList<String>();
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			matches.add(code.substring(0, 16));
		}
		return matches;
	}
	
	
	protected boolean isMatchDuplicationDT(String betcode) {
		
		Set<String> danmatches = getDistinctMatches(betcode.split("\\-")[1].split("\\#")[0].replace("^", ""));
		Set<String> tuomatches = getDistinctMatches(betcode.split("\\-")[1].split("\\#")[1].replace("^", ""));
		
		int total = danmatches.size()+tuomatches.size();
		
		danmatches.addAll(tuomatches);
		
		if(danmatches.size()==total) {
			return false;
		}
		return true;
	}
	
	
	private Set<String> getDistinctMatches(String codes) {
		Set<String> matches = new HashSet<String>();
		
		for(String code:codes.split("\\|")) {
			matches.add(code.substring(0, 11));
		}
		return matches;
	}
	
	
	/**
	 * 判断list是否有重复项
	 * @param list
	 * @return
	 */
	private boolean isDuplication(List<String> list) {
		for(int i=0;i<list.size()-1;i++) {
			for(int j=i+1;j<list.size();j++) {
				if(list.get(i).equals(list.get(j))) {
					return true;
				}
			}
		}
		return false;
	}
	
	
	/**
	 * 计算注数(普通投注,非胆拖)
	 * @param betcode
	 * @param type
	 * @return
	 */
	protected long caculateZhushu(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		int num = getNeedMatchNumByType();
		List<List<String>> codeCollection = MathUtils.getCodeCollection(Arrays.asList(betcode.split("\\|")), num);
		List<List<String>> n1Codes = splitToN1(codeCollection, getPlayType());
		long zhushu = 0;
		for(List<String> n1code:n1Codes) {
			zhushu = zhushu + mulSelectCode(n1code);
		}
		return zhushu;
	}
	
	
	
	/**
	 * 计算注数(普通投注,非胆拖)
	 * @param betcode
	 * @param type
	 * @return
	 */
	protected long caculateZhushuMix(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		int num = getNeedMatchNumByType();
		List<List<String>> convert = convert(betcode, num);
		
		List<List<String>> n1Codes = splitToN1(convert, getPlayType());
		
		long zhushu = 0;
		for(List<String> n1code:n1Codes) {
			zhushu = zhushu + mulSelectCode(n1code);
		}
		return zhushu;
	}
	
	/**
	 * 将混合投注的注码转换为标准串关形式的注码(玩法不限制)
	 * @param betcode 注码
	 * @param num 串关数
	 * @return
	 */
	protected List<List<String>> convert(String betcode,int num) {
		Map<String, List<String>> splitByMatch = splitByMatch(betcode);
		List<Map<String, List<String>>> splitMatchByNum = splitMatchByNum(splitByMatch,num);
		List<List<String>> convertToStandardMatch = convertToStandardMatch(splitMatchByNum);
		return convertToStandardMatch;
	}
	
	
	/**
	 * 将混合投注的注码转换为标准串关形式的注码(玩法不限制)胆拖
	 * @param betcode 注码
	 * @param num 串关数
	 * @return
	 */
	protected List<List<String>> convertDT(String betcode,int num) {
		int danlength = getDistinctMatches(betcode.split("\\#")[0]).size();
		Map<String, List<String>> splitByMatchdan = splitByMatch(betcode.split("\\#")[0]);
		Map<String, List<String>> splitByMatchtuo = splitByMatch(betcode.split("\\#")[1]);
		List<Map<String, List<String>>> splitMatchByNum = splitMatchByNum(splitByMatchtuo,num-danlength);
		
		for(Map<String, List<String>> map:splitMatchByNum) {
			map.putAll(splitByMatchdan);
		}
		List<List<String>> convertToStandardMatch = convertToStandardMatch(splitMatchByNum);
		return convertToStandardMatch;
	}
	
	
	/**
	 * 根据map的大小将注码转换为以map.size为长度的标砖串关注码(玩法不限制)
	 * @param matchs
	 * @return
	 */
	private List<List<String>> convertToStandardMatch(List<Map<String,List<String>>> matchs) {
		List<List<String>> listMatchs = new ArrayList<List<String>>();
		for(Map<String,List<String>> map:matchs) {
			List<List<String>> listlist = new ArrayList<List<String>>();
			for (Iterator<String> iterator = map.keySet().iterator(); iterator.hasNext();) {
				String changci = iterator.next();
				List<String> changcicode = map.get(changci);
				listlist = rezuheList(listlist,changcicode);
			}
			listMatchs.addAll(listlist);
		}
		
		return listMatchs;
	}
	
	
	/**
	 * 自上而下的组合方式如a,bc,d，组合为abd，acd
	 * @param listlistOri 原始组合列
	 * @param as 新添加组合列
	 * @return
	 */
	private List<List<String>> rezuheList(List<List<String>> listlistOri,List<String> as) {
		List<List<String>> listlist = new ArrayList<List<String>>();
		if(listlistOri.size()==0) {
			for(String a:as) {
				List<String> list = new ArrayList<String>();
				list.add(a);
				listlist.add(list);
			}
			return listlist;
		}
		
		for(List<String> list:listlistOri) {
			for(String a:as) {
				ArrayList<String> newlist = new ArrayList<String>();
				newlist.addAll(list);
				newlist.add(a);
				listlist.add(newlist);
			}
		}
		return listlist;
	}
	
	
	/**
	 * 按照num数组合场次
	 * @param matchs key为场次 value为内容
	 * @param num
	 * @return
	 */
	private List<Map<String,List<String>>> splitMatchByNum(Map<String,List<String>> matchs,int num) {
		Set<String> keyset = matchs.keySet();
		List<String> list = new ArrayList<String>();
		
		for (Iterator<String> iterator = keyset.iterator(); iterator.hasNext();) {
			String string = iterator.next();
			list.add(string);
		}
		
		List<List<String>> split = MathUtils.getCodeCollection(list, num);
		
		List<Map<String,List<String>>> listmap = new ArrayList<Map<String,List<String>>>();
		for(List<String> strings:split) {
			Map<String,List<String>> map = new HashMap<String,List<String>>();
			for(String match:strings) {
				map.put(match, matchs.get(match));
			}
			listmap.add(map);
		}
		
		return listmap;
	}
	
	
	/**
	 * 讲注码拆分为场次和所选内容的组合，map的key为场次，value为投注内容
	 * @param betcode
	 * @return
	 */
	private Map<String,List<String>> splitByMatch(String betcode) {
		Map<String,List<String>> map = new HashMap<String,List<String>>();
		for(String code:betcode.split("\\|")) {
			if(map.get(code.substring(0, 11))==null) {
				List<String> list = new ArrayList<String>();
				list.add(code);
				map.put(code.substring(0, 11), list);
			}else {
				List<String> list = map.get(code.substring(0, 11));
				list.add(code);
				map.put(code.substring(0, 11), list);
			}
		}
		return map;
	}
	
	
	/**
	 * 计算注数(普通投注,非胆拖)
	 * @param betcode
	 * @param type
	 * @return
	 */
	protected long caculateZhushuDT(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		List<String> dans = Arrays.asList(betcode.split("\\#")[0].split("\\|"));
		List<String> tuos = Arrays.asList(betcode.split("\\#")[1].split("\\|"));
		
		int num = getNeedMatchNumByType();
		List<List<String>> codeCollection = MathUtils.getCodeCollection(tuos, num-dans.size());
		
		for(List<String> list:codeCollection) {
			list.addAll(dans);
		}
		
		List<List<String>> n1Codes = splitToN1(codeCollection, getPlayType());
		long zhushu = 0;
		for(List<String> n1code:n1Codes) {
			zhushu = zhushu + mulSelectCode(n1code);
		}
		return zhushu;
	}
	
	
	
	/**
	 * 计算注数(胆拖混合)
	 * @param betcode
	 * @param type
	 * @return
	 */
	protected long caculateZhushuDTMix(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		int num = getNeedMatchNumByType();
		List<List<String>> convert = convertDT(betcode, num);
		
		List<List<String>> n1Codes = splitToN1(convert, getPlayType());
		
		long zhushu = 0;
		for(List<String> n1code:n1Codes) {
			zhushu = zhushu + mulSelectCode(n1code);
		}
		return zhushu;
	}
	
	
	/**
	 * 竞彩普通投注拆票
	 * @param betcode
	 * @param lotmulti
	 * @param type
	 * @param oneAmount
	 * @return
	 */
	protected List<SplitedLot> splitByBetType(String betcode,int lotmulti,int oneAmount) {
		int type = getPlayType();
		int num = getNeedMatchNumByType();
		betcode = betcode.split("-")[1].replace("^", "");
		List<SplitedLot> splits = new ArrayList<SplitedLot>();
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		List<List<String>> codeCollection = MathUtils.getCodeCollection(Arrays.asList(betcode.split("\\|")), num);
		
		for(List<String> codes:codeCollection) {
			StringBuilder finalcode = new StringBuilder();
			Collections.sort(codes);
			for(String code:codes) {
				finalcode.append(code).append("|");
			}
			if(finalcode.toString().endsWith("|")) {
				finalcode.deleteCharAt(finalcode.length()-1);
			}
			finalcode.append("^");
			SplitedLot lot = new SplitedLot(getLotterytype());
			lot.setBetcode(getLotterytype()+"1"+String.valueOf(type)+"-"+finalcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
			splits.add(lot);
		}
		
		for(SplitedLot lot:splits) {
			if(!SplitedLot.isToBeSplit99(lot.getLotMulti(), lot.getAmt())) {
				realsplits.add(lot);
			}else {
				int amtSingle = (int)(lot.getAmt()/lot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				realsplits.addAll(SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype()));
			}
		}
		
		for(SplitedLot lot:realsplits) {
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount));
		}
		return realsplits;
	}
	
	
	
	/**
	 * 竞彩混合普通投注拆票
	 * @param betcode
	 * @param lotmulti
	 * @param type
	 * @param oneAmount
	 * @return
	 */
	protected List<SplitedLot> splitByBetTypeMix(String betcode,int lotmulti,int oneAmount) {
		betcode = betcode.split("-")[1].replace("^", "");
		int num = getNeedMatchNumByType();
		List<List<String>> convert = convert(betcode, num);
		
		
		int type = getPlayType();
		List<SplitedLot> splits = new ArrayList<SplitedLot>();
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		
		for(List<String> codes:convert) {
			StringBuilder finalcode = new StringBuilder();
			Collections.sort(codes);
			for(String code:codes) {
				finalcode.append(code).append("|");
			}
			if(finalcode.toString().endsWith("|")) {
				finalcode.deleteCharAt(finalcode.length()-1);
			}
			finalcode.append("^");
			
			SplitedLot lot = new SplitedLot(getLotterytype());
			lot.setBetcode(getLotterytype()+"1"+String.valueOf(type)+"-"+finalcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
			splits.add(lot);
			
		}
		
		for(SplitedLot lot:splits) {
			if(!SplitedLot.isToBeSplit99(lot.getLotMulti(), lot.getAmt())) {
				realsplits.add(lot);
			}else {
				int amtSingle = (int)(lot.getAmt()/lot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				realsplits.addAll(SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype()));
			}
		}
		
		for(SplitedLot lot:realsplits) {
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount));
			lot.setBetcode(getMixTrueBetcode(lot.getBetcode()));
			lot.setLotterytype(Integer.parseInt(lot.getBetcode().substring(0, 4)));
			
		}
		return realsplits;
	}
	
	
	
	
	/**
	 * 竞彩混合胆拖投注拆票
	 * @param betcode
	 * @param lotmulti
	 * @param type
	 * @param oneAmount
	 * @return
	 */
	protected List<SplitedLot> splitByBetTypeDTMix(String betcode,int lotmulti,int oneAmount) {
		betcode = betcode.split("-")[1].replace("^", "");
		int num = getNeedMatchNumByType();
		List<List<String>> convert = convertDT(betcode, num);
		
		
		int type = getPlayType();
		List<SplitedLot> splits = new ArrayList<SplitedLot>();
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		
		for(List<String> codes:convert) {
			StringBuilder finalcode = new StringBuilder();
			Collections.sort(codes);
			for(String code:codes) {
				finalcode.append(code).append("|");
			}
			if(finalcode.toString().endsWith("|")) {
				finalcode.deleteCharAt(finalcode.length()-1);
			}
			finalcode.append("^");
			
			SplitedLot lot = new SplitedLot(getLotterytype());
			lot.setBetcode(getLotterytype()+"1"+String.valueOf(type)+"-"+finalcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getNormalBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
			splits.add(lot);
			
		}
		
		for(SplitedLot lot:splits) {
			if(!SplitedLot.isToBeSplit99(lot.getLotMulti(), lot.getAmt())) {
				realsplits.add(lot);
			}else {
				int amtSingle = (int)(lot.getAmt()/lot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				realsplits.addAll(SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype()));
			}
		}
		
		for(SplitedLot lot:realsplits) {
			lot.setAmt(getNormalBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount));
			lot.setBetcode(getMixTrueBetcode(lot.getBetcode()));
			lot.setLotterytype(Integer.parseInt(lot.getBetcode().substring(0, 4)));
			
		}
		return realsplits;
	}
	
	
	/**
	 * 竞彩混合过关获取真实注码
	 * @param betcode
	 * @return
	 */
	private String getMixTrueBetcode(String betcode) {
		
		String[] bets = betcode.split("\\-")[1].replace("^", "").split("\\|");
		String playtype = bets[0].split("\\*")[1].substring(0, 4);
		for(String bet:bets) {
			if(!bet.split("\\*")[1].substring(0, 4).equals(playtype)) {
				return betcode;
			}
		}
		return playtype+betcode.substring(4).replace("*"+playtype, "");
	}
	
	
	
	protected List<SplitedLot> splitByBetTypeDT(String betcode,int lotmulti,int oneAmount) {
		int type = getPlayType();
		int num = getNeedMatchNumByType();
		betcode = betcode.split("-")[1].replace("^", "");
		
		List<SplitedLot> splits = new ArrayList<SplitedLot>();
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		
		List<String> dans = Arrays.asList(betcode.split("\\#")[0].split("\\|"));
		List<String> tuos = Arrays.asList(betcode.split("\\#")[1].split("\\|"));
		List<List<String>> codeCollection = MathUtils.getCodeCollection(tuos, num-dans.size());
		
		for(List<String> codes:codeCollection) {
			StringBuilder finalcode = new StringBuilder();
			codes.addAll(dans);
			Collections.sort(codes);
			for(String code:codes) {
				finalcode.append(code).append("|");
			}
			if(finalcode.toString().endsWith("|")) {
				finalcode.deleteCharAt(finalcode.length()-1);
			}
			finalcode.append("^");
			SplitedLot lot = new SplitedLot(getLotterytype());
			lot.setBetcode(getLotterytype()+"1"+String.valueOf(type)+"-"+finalcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(getNormalBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
			splits.add(lot);
		}
		
		for(SplitedLot lot:splits) {
			if(!SplitedLot.isToBeSplit99(lot.getLotMulti(), lot.getAmt())) {
				realsplits.add(lot);
			}else {
				int amtSingle = (int)(lot.getAmt()/lot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				realsplits.addAll(SplitedLot.splitToPermissionMulti(lot.getBetcode(), lot.getLotMulti(), permissionLotmulti, getLotterytype()));
			}
		}
		
		for(SplitedLot lot:realsplits) {
			lot.setAmt(getNormalBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount));
		}
		return realsplits;
		
		
	}
	
	
	/**
	 * 讲标准注码转换为串1形式来计算注数
	 * @param codeCollection
	 * @param type
	 * @return
	 */
	private List<List<String>> splitToN1(List<List<String>> codeCollection,int type) {
		List<List<String>> allcodes = new ArrayList<List<String>>();
		List<Integer> combinations = JingcaiBetcodeUtil.getCombinationsByType(type);
		for(int i:combinations) {
			allcodes.addAll(combinationList(codeCollection, i));
		}
		return allcodes;
	}
	
	
	/**
	 * 循环计算每个串1的注数
	 * @param betcodes
	 * @param c
	 * @return
	 */
	private List<List<String>> combinationList(List<List<String>> betcodes,int c) {
		List<List<String>> list = new ArrayList<List<String>>();
		for(List<String> betcode:betcodes) {
			list.addAll(MathUtils.getCodeCollection(betcode, c));
		}
		return list;
	}
	
	
	
	protected boolean checkCodeLimit(String code,BettingLimitNumber number) {
		if(number==null||code.substring(0, 9).equals(number.getPlayType())) {
			return false;
		}
		
		if(sortCode(code).equals(sortCode(number.getContent()))) {
			return true;
		}
		return false;
	}
	
	
	//300612001-20140101001(0,1,3)|20140101002(0,1,3)|20140101003(0,1,3)^
	//300512001-20140330302*3004(2)|20140330302*3001(1,3)|20140330307*3001(0)^
	private String sortCode(String betcode) {
		Map<String,List<String>> codes = new HashMap<String, List<String>>();
		List<String> keys = new ArrayList<String>();
		for(String code:betcode.split("\\-")[1].replace("^", "").split("\\|")) {
			int start = code.indexOf("(");
			keys.add(code.substring(0, start));
			codes.put(code.substring(0, start), Arrays.asList(code.substring(start).replace("(", "").replace(")", "").split(",")));
		}
		
		StringBuilder codestr = new StringBuilder(betcode.split("\\-")[0]+"-");
		
		Collections.sort(keys);
		
		for(String key:keys) {
			codestr.append(key).append("(");
			List<String> codesSort = codes.get(key);
			Collections.sort(codesSort);
			for(String onecode:codesSort) {
				codestr.append(onecode).append(",");
			}
			codestr.deleteCharAt(codestr.length()-1);
			codestr.append(")").append("|");
		}
		
		codestr.deleteCharAt(codestr.length()-1);
		codestr.append("^");
		
		return codestr.toString();
	}
	
	
	@Override
	public boolean isBetcodeLimited(String betcode,BettingLimitNumber limitedCodes) {
		return checkCodeLimit(betcode, limitedCodes);
	}
	
}
