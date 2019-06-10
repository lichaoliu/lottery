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

import com.lottery.common.contains.YesNoStatus;
import com.lottery.common.contains.lottery.DcType;
import com.lottery.common.contains.lottery.LotteryOrderLineContains;
import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.RaceStatus;
import com.lottery.common.util.MathUtils;
import com.lottery.core.dao.DcRaceDAO;
import com.lottery.core.domain.DcRace;
import com.lottery.lottype.dc.DanChangCancal;
import com.lottery.lottype.dc.DanChangResult;
import com.lottery.lottype.dc.DcBetcodeUtil;
import com.lottery.lottype.dc.DcResultUtil;


public abstract class AbstractDanchangLot extends AbstractLot{
	
	
	@Autowired
	private DcRaceDAO dcRaceDao;

	
	
	
	
	
	/**
	 * 计算注码拆分为N串1的奖级信息，不带倍数不乘以200
	 * @param orderid
	 * @param betcode
	 * @param odds
	 * @return
	 */
	public List<DanChangPrizeResult> getDanChangPrizeResults(String orderid,String betcode,String phase,DcType dctype) {
		List<String> matches = getMatches(betcode);
		DanChangCancal cancal = cancal(matches, phase,dctype);
		
		if(cancal.isCancal()==false) {
			//lotteryOrderDao.updateOrderLastMatchNum(orderid, cancal.getNotcalMatchno());
			logger.error("票{}未全部开奖,退出算奖,本次未开奖的场次为{}",new Object[]{orderid,cancal.getNotcalMatchno()});
			return null;
		}
		
		List<DanChangPrizeResult> results = new ArrayList<DanChangPrizeResult>();
		String type = betcode.split("\\-")[0].substring(5);
		
		String realbetcode = betcode.split("\\-")[1].replace("^", "");
		
		List<List<String>> codesN1 = splitBetcodeToN1(Arrays.asList(realbetcode.split("\\|")), Integer.parseInt(type));
		
		
		for(List<String> codeN1:codesN1) {
			int cancelcount = 0;
			BigDecimal cancelzhushu = BigDecimal.ONE;
			BigDecimal amt = BigDecimal.ONE;
			
			for(String code:codeN1) {
				String matchid = code.substring(0, 3);
				List<String> zhumas = Arrays.asList(code.substring(3).replace("(", "").replace(")", "").split(","));
				
				DanChangResult dcResult = cancal.getResult(matchid);
				if(dcResult.isCancel()==true) {
					cancelzhushu = cancelzhushu.multiply(new BigDecimal(zhumas.size()));
					cancelcount = cancelcount + 1;
				}else {
					if(zhumas.contains(dcResult.getResult())) {
						amt = amt.multiply(new BigDecimal(dcResult.getOdd()));
					}else {
						amt = BigDecimal.ZERO;
						break;
					}
				}
			}
			
			if(amt.compareTo(BigDecimal.ZERO)==1) {
				if(cancelcount>0) {
					for(int i=0;i<cancelzhushu.intValue();i++) {
						DanChangPrizeResult prizeresult = new DanChangPrizeResult();
						int typecount = codeN1.size()-cancelcount;
						if(typecount==0) {
							typecount = 1;
						}
						prizeresult.setType(typecount*100+1);
						prizeresult.setPrize(amt);
						results.add(prizeresult);
					}
				}else {
					DanChangPrizeResult prizeresult = new DanChangPrizeResult();
					prizeresult.setType(codeN1.size()*100+1);
					prizeresult.setPrize(amt);
					results.add(prizeresult);
				}
			}
			
		}
		return 	results;
	}
	
	
	public DanChangPrizeResult calculateDanChangPrizeResult(String orderid,String betcode,String phase,BigDecimal lotmulti,DcType type) {
		List<DanChangPrizeResult> results = getDanChangPrizeResults(orderid, betcode, phase,type);
		
		if(results==null) {
			return null;
		}
		
		BigDecimal prize = BigDecimal.ZERO;
		BigDecimal afterTaxPrize = BigDecimal.ZERO;
		
		for(DanChangPrizeResult result:results) {
			BigDecimal percent65 = result.getPrize().multiply(new BigDecimal("0.65"));
			if(result.getPrize().compareTo(BigDecimal.ZERO)==1&&result.getPrize().multiply(new BigDecimal("0.65")).compareTo(BigDecimal.ONE)==-1) {
				percent65 = BigDecimal.ONE;
			}
			result.setPrize(getLimitDanchangPrize(String.valueOf(result.getType()),percent65.multiply(new BigDecimal(200))));
			result.setAfterTaxPrize(result.getPrize().compareTo(new BigDecimal(1000000))==1?result.getPrize().multiply(new BigDecimal("0.8")):result.getPrize());
			prize = prize.add(result.getPrize());
			afterTaxPrize = afterTaxPrize.add(result.getAfterTaxPrize());
		}
		
		DanChangPrizeResult pr = new DanChangPrizeResult();
		pr.setPrize(prize.multiply(lotmulti));
		pr.setAfterTaxPrize(afterTaxPrize.multiply(lotmulti));
		pr.setPrizedetail(getDanchangPrizeDetail(results,lotmulti));
		if(pr.getPrize().intValue()>1000000) {
			pr.setBig(true);
		}
		return pr;
	}
	
	
	protected BigDecimal getLimitDanchangPrize(String type, BigDecimal prizePre) {
		
		String prizeStr = prizePre.toString();
		if(prizeStr.contains(".")) {
			prizeStr = prizeStr.substring(0, prizeStr.indexOf("."));
		}
		BigDecimal prize = new BigDecimal(prizeStr);
		return prize.compareTo(new BigDecimal(500000000)) == 1 ? new BigDecimal(
					500000000) : prize;
	}
	
	
	private String getDanchangPrizeDetail(List<DanChangPrizeResult> results,BigDecimal lotmulti) {
		Map<String,Integer> detailMap = new HashMap<String,Integer>();
		
		for(DanChangPrizeResult result:results) {
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
	
	
	protected DanChangCancal cancal(List<String> matches,String phase,DcType dctype) {
		DanChangCancal cancal = new DanChangCancal();
		cancal.setCancal(true);
		for(String match:matches) {
			DcRace dcRaceResult = dcRaceDao.getDcRaceResult(phase, Integer.parseInt(match),dctype);
			if(dcRaceResult==null) {
				cancal.setCancal(false);
				cancal.setNotcalMatchno(match);
				break;
			}
			
			DanChangResult result = new DanChangResult(match);
			if(dcRaceResult.getStatus()==RaceStatus.CANCEL.value) {
				result.setCancel(true);
			}else {
				result.setCancel(false);
				if(getLotteryType()==LotteryType.DC_BF) {
					result.setResult(DcResultUtil.getCalcuteResult(getLotteryType(), dcRaceResult.getBfResult()));
					result.setOdd(dcRaceResult.getSpBf());
				}else if(getLotteryType()==LotteryType.DC_BQC) {
					result.setResult(DcResultUtil.getCalcuteResult(getLotteryType(), dcRaceResult.getBcsfpResult()));
					result.setOdd(dcRaceResult.getSpBcsfp());
				}else if(getLotteryType()==LotteryType.DC_SPF) {
					result.setResult(DcResultUtil.getCalcuteResult(getLotteryType(), dcRaceResult.getSfpResult()));
					result.setOdd(dcRaceResult.getSpSfp());
				}else if(getLotteryType()==LotteryType.DC_SXDS) {
					result.setResult(DcResultUtil.getCalcuteResult(getLotteryType(), dcRaceResult.getSxdsResult()));
					result.setOdd(dcRaceResult.getSpSxds());
				}else if(getLotteryType()==LotteryType.DC_ZJQ) {
					result.setResult(DcResultUtil.getCalcuteResult(getLotteryType(), dcRaceResult.getJqsResult()));
					result.setOdd(dcRaceResult.getSpJqs());
				}else if(getLotteryType()==LotteryType.DC_SFGG) {
					result.setResult(DcResultUtil.getCalcuteResult(getLotteryType(), dcRaceResult.getSfResult()));
					result.setOdd(dcRaceResult.getSpSf());
				}
			}
			cancal.addResult(match, result);
		}
		return cancal;
	}
	
	
	
	/**
	 * 获取注码中的所有场次
	 * @param betcode
	 * @return
	 */
	private List<String> getMatches(String betcode) {
		betcode = betcode.split("-")[1].replace("#", "").replace("^", "");
		
		List<String> list = new ArrayList<String>();
		
		for(String bet:betcode.split("\\|")) {
			list.add(bet.substring(0, 3));
		}
		return list;
	}
	
	
	
	/**
	 * 讲标准注码转换为串1形式来计算注数
	 * @param codeCollection
	 * @param type
	 * @return
	 */
	protected List<List<String>> splitBetcodeToN1(List<String> betcodes,int type) {
		List<List<String>> allcodes = new ArrayList<List<String>>();
		List<Integer> combinations = DcBetcodeUtil.getCombinationsByType(type);
		for(int i:combinations) {
			allcodes.addAll(combinationItemList(betcodes, i));
		}
		return allcodes;
	}
	
	
	/**
	 * 选出串1
	 * @param 
	 * @param betcodes
	 * @param c
	 * @return
	 */
	private List<List<String>> combinationItemList(List<String> betcodes,int c) {
		List<List<String>> list = new ArrayList<List<String>>();
		list.addAll(MathUtils.getCodeCollection(betcodes, c));
		return list;
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
				matches.add(onebet.substring(0, 3));
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
