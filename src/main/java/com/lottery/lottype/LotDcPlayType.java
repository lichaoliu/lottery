package com.lottery.lottype;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.lottery.common.util.MathUtils;
import com.lottery.lottype.dc.DcBetcodeUtil;


public abstract class LotDcPlayType implements LotPlayType {

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		return null;
	}
	
	public abstract int getPlayType();
	
	protected abstract int getLotterytype();
	
	protected abstract long getNormalBetAmount(String betcode, BigDecimal beishu,int oneAmount);

	/**
	 * 根据玩法获取标准场次数
	 * @param type
	 * @return
	 */
	protected int getNeedMatchNumByType() {
		int type = getPlayType();
		return DcBetcodeUtil.getNeedMatchNumByType(type);
	}
	
	
	/**
	 * 获取注码中的总的场次数量，不过滤重复
	 * @param betcode
	 * @return
	 */
	protected int getTotalMatchNum(String betcode) {
		Set<String> set = new HashSet<String>();
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			set.add(code.substring(0, 3));
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
	 * 获取注码中所有场次，不忽略重复
	 * @param betcode
	 * @return
	 */
	private List<String> getMatchs(String betcode) {
		List<String> matches = new ArrayList<String>();
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			matches.add(code.substring(0, 3));
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
	 * 判断注码中实际投注内容是否有重复
	 * @param betcode 实际投注内容
	 * @param codeLength 每位注码长度
	 * @return
	 */
	protected boolean isBetcodeDuplication(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		for(String code:betcode.split("\\|")) {
			String bet = code.substring(3).replace("(", "").replace(")", "");
			if(isDuplication(Arrays.asList(bet.split(",")))) {
				return true;
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
	 * 计算注数(普通投注,非胆拖) 按照过滤重复的方式拆分计算金额
	 * @param betcode
	 * @param type
	 * @return
	 */
	protected long caculateZhushuN1(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		List<List<String>> codeCollection = new ArrayList<List<String>>();
		codeCollection.add(Arrays.asList(betcode.split("\\|")));
		List<List<String>> n1Codes = splitToN1(codeCollection, getPlayType());
		long zhushu = 0;
		for(List<String> n1code:n1Codes) {
			zhushu = zhushu + mulSelectCode(n1code);
		}
		return zhushu;
	}
	
	
	/**
	 * 讲标准注码转换为串1形式来计算注数
	 * @param codeCollection
	 * @param type
	 * @return
	 */
	private List<List<String>> splitToN1(List<List<String>> codeCollection,int type) {
		List<List<String>> allcodes = new ArrayList<List<String>>();
		List<Integer> combinations = DcBetcodeUtil.getCombinationsByType(type);
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
	
	
	protected long mulSelectCode(List<String> selects) {
		long zhushu = 1;
		for(String select:selects) {
			select = select.replace("(", "").replace(")", "").substring(3);
			zhushu = zhushu * select.split(",").length;
		}
		return zhushu;
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
	 * 计算注数(普通投注,非胆拖) 按照过滤重复的方式计算
	 * @param betcode
	 * @param type
	 * @return
	 */
	protected long caculateZhushuDTN1(String betcode) {
		betcode = betcode.split("-")[1].replace("^", "");
		List<String> dans = Arrays.asList(betcode.split("\\#")[0].split("\\|"));
		List<String> tuos = Arrays.asList(betcode.split("\\#")[1].split("\\|"));
		
		List<Integer> combinationsByType = DcBetcodeUtil.getCombinationsByType(getPlayType());
		
		List<List<String>> n1Codes = new ArrayList<List<String>>();
		for(int select:combinationsByType) {
			if(dans.size()>=select) {
				n1Codes.addAll(MathUtils.getCodeCollection(dans, select));
			}else {
				List<List<String>> tuoCombinations = MathUtils.getCodeCollection(tuos, select-dans.size());
				for(List<String> tuoCombination:tuoCombinations) {
					tuoCombination.addAll(dans);
					n1Codes.add(tuoCombination);
				}
			}
		}
		
		long zhushu = 0;
		for(List<String> n1code:n1Codes) {
			zhushu = zhushu + mulSelectCode(n1code);
		}
		return zhushu;
	}
	
	
	
	/**
	 * 北单单复式投注拆票
	 * @param betcode
	 * @param lotmulti
	 * @param type
	 * @param oneAmount
	 * @return
	 */
	protected List<SplitedLot> splitByBetTypeDF(String betcode,int lotmulti,int oneAmount) {
		int typeint = getPlayType();
		
		String type = String.valueOf(typeint).length()==3?"0"+typeint:String.valueOf(typeint);
		
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
	 * 北单单复式投注拆票 按照过滤的方式
	 * 如果场次个数等于串关个数，不拆分成串1的形式，计算金额直接调用原来方法进行金额计算，只对倍数进行拆分
	 * 如果场次个数大于串关个数，为了防止重复，则采用拆分为串1的形式，计算金额通通为串1的计算金额方式，之后对倍数进行拆分
	 * @param betcode
	 * @param lotmulti
	 * @param type
	 * @param oneAmount
	 * @return
	 */
	protected List<SplitedLot> splitByBetTypeDFN1(String betcode,int lotmulti,int oneAmount) {
		int num = getNeedMatchNumByType();
		String playtype = betcode.split("-")[0];
		betcode = betcode.split("-")[1].replace("^", "");
		List<SplitedLot> splits = new ArrayList<SplitedLot>();
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		
		if(betcode.split("\\|").length==num) {
			SplitedLot lot = new SplitedLot(getLotterytype());
			lot.setBetcode(playtype+"-"+betcode+"^");
			lot.setLotMulti(lotmulti);
			lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lotmulti), oneAmount));
			splits.add(lot);
		}else {
			List<List<String>> codeCollection = new ArrayList<List<String>>();
			codeCollection.add(Arrays.asList(betcode.split("\\|")));
			List<List<String>> n1Codes = splitToN1(codeCollection, getPlayType());
			
			for(List<String> codes:n1Codes) {
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
				
				String splitcodeType = codes.size()>=10?codes.size()+"01":"0"+codes.size()+"01";
				lot.setBetcode(getLotterytype()+"1"+splitcodeType+"-"+finalcode);
				lot.setLotMulti(lotmulti);
				lot.setAmt(mulSelectCode(codes)*lotmulti*oneAmount);
				splits.add(lot);
			}
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
			if(lot.getBetcode().split("-")[0].endsWith("01")) {
				lot.setAmt(mulSelectCode(Arrays.asList(lot.getBetcode().replace("^", "").split("-")[1].split("\\|")))*lot.getLotMulti()*oneAmount);
			}else {
				lot.setAmt(getSingleBetAmount(lot.getBetcode(), new BigDecimal(lot.getLotMulti()), oneAmount));
			}
			
		}
		return realsplits;
	}
	
	
	
	protected List<SplitedLot> splitByBetTypeDT(String betcode,int lotmulti,int oneAmount) {
		int typeint = getPlayType();
		String type = String.valueOf(typeint).length()==3?"0"+typeint:String.valueOf(typeint);
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
	 * 北单胆拖拆票 按照过滤方式
	 * 因为胆拖全为复式，所以不会出现场次个数等于串关个数的情况，所有玩法都按照串1方式进行拆分
	 * @param betcode
	 * @param lotmulti
	 * @param oneAmount
	 * @return
	 */
	protected List<SplitedLot> splitByBetTypeDTN1(String betcode,int lotmulti,int oneAmount) {
		betcode = betcode.split("-")[1].replace("^", "");
		
		List<SplitedLot> splits = new ArrayList<SplitedLot>();
		List<SplitedLot> realsplits = new ArrayList<SplitedLot>();
		
		List<String> dans = Arrays.asList(betcode.split("\\#")[0].split("\\|"));
		List<String> tuos = Arrays.asList(betcode.split("\\#")[1].split("\\|"));
		
		List<Integer> combinationsByType = DcBetcodeUtil.getCombinationsByType(getPlayType());
		
		List<List<String>> n1Codes = new ArrayList<List<String>>();
		for(int select:combinationsByType) {
			if(dans.size()>=select) {
				n1Codes.addAll(MathUtils.getCodeCollection(dans, select));
			}else {
				List<List<String>> tuoCombinations = MathUtils.getCodeCollection(tuos, select-dans.size());
				for(List<String> tuoCombination:tuoCombinations) {
					tuoCombination.addAll(dans);
					n1Codes.add(tuoCombination);
				}
			}
		}
		
		for(List<String> codes:n1Codes) {
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
			String splitcodeType = codes.size()>=10?codes.size()+"01":"0"+codes.size()+"01";
			lot.setBetcode(getLotterytype()+"1"+splitcodeType+"-"+finalcode);
			lot.setLotMulti(lotmulti);
			lot.setAmt(mulSelectCode(codes)*lotmulti*oneAmount);
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
			lot.setAmt(mulSelectCode(Arrays.asList(lot.getBetcode().replace("^", "").split("-")[1].split("\\|")))*lot.getLotMulti()*oneAmount);
		}
		return realsplits;
	}

}
