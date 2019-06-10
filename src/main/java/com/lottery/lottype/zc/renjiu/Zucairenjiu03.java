package com.lottery.lottype.zc.renjiu;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.lottype.SplitedLot;

public class Zucairenjiu03 extends ZucairenjiuX{
	
	private Zucairenjiu01 rj01 = new Zucairenjiu01();
	private Zucairenjiu02 rj02 = new Zucairenjiu02();

	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		if(!betcode.matches(regex_rjc03)) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		String[] dans = betcode.split("-")[1].replace("^", "").split("\\#")[0].split(",");
		String[] tuos = betcode.split("-")[1].replace("^", "").split("\\#")[1].split(",");
		
		//判断是不是有重复 胆码
		for(String bet:dans) {
			if(isRepeated(bet.toCharArray())) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		//判断是不是有重复 托码
		for(String bet:tuos) {
			if(isRepeated(bet.toCharArray())) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		for(int i=0;i<14;i++) {
			if((!dans[i].equals("~"))&&(!tuos[i].equals("~"))) {
				throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
			}
		}
		
		int selectdan = 0;
		int selecttuo = 0;
		
		for(String dan:dans) {
			if(!dan.equals("~")) {
				selectdan = selectdan + 1;
			}
		}
		for(String tuo:tuos) {
			if(!tuo.equals("~")) {
				selecttuo = selecttuo + 1;
			}
		}
		if(selectdan>8||selectdan+selecttuo< 9) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		
		return dantuoZhushu(betcode)*200*beishu.longValue();
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt())) {
				list.add(splitedLot);
			}else {
				int amtSingle = (int) (splitedLot.getAmt() / splitedLot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			if(s.getBetcode().startsWith("400201")) {
				s.setAmt(rj01.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else if(s.getBetcode().startsWith("400202")) {
				s.setAmt(rj02.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}else if(s.getBetcode().startsWith("400203")) {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), 200));
			}
			
		}

		return list;
	}
	
	
	
	private List<SplitedLot> transform(String oribetcode, int lotmulti) {
		String[] dans = oribetcode.split("-")[1].replace("^", "").split("\\#")[0].split(",");
		String[] tuos = oribetcode.split("-")[1].replace("^", "").split("\\#")[1].split(",");

		// 计算胆码托码不是未选的个数
		int selectdan = 0;
		int selecttuo = 0;

		for (String dan : dans) {
			if (!dan.equals("~")) {
				selectdan = selectdan + 1;
			}
		}
		for (String tuo : tuos) {
			if (!tuo.equals("~")) {
				selecttuo = selecttuo + 1;
			}
		}

		// 找出托码不是未选的位置
		List<String> tuoselect = new ArrayList<String>();
		for (int i = 0; i < tuos.length; i++) {
			if (!tuos[i].equals("~")) {
				tuoselect.add(String.valueOf(i));
			}
		}

		List<List<String>> tuoCollections = MathUtils.getCodeCollection(
				tuoselect, 9 - selectdan);

		List<String> betcodes = new ArrayList<String>();

		for (List<String> tuoCollection : tuoCollections) {
			String[] realcode = dans.clone();
			for (String position : tuoCollection) {
				realcode[Integer.parseInt(position)] = tuos[Integer
						.parseInt(position)];
			}
			betcodes.add(appendBetcode(realcode));
		}
		List<SplitedLot> list = new ArrayList<SplitedLot>();

		for (String betcode : betcodes) {
			long amt = 0;
			if(betcode.startsWith("400201")) {
				amt = rj01.getSingleBetAmount(betcode, BigDecimal.ONE, 200);
			}else if(betcode.startsWith("400202")) {
				amt = rj02.getSingleBetAmount(betcode, BigDecimal.ONE, 200);
			}else if(betcode.startsWith("400203")) {
				amt = getSingleBetAmount(betcode, BigDecimal.ONE, 200);
			}

			if (amt <= 2000000) {
				SplitedLot lot = new SplitedLot(lotterytype);
				lot.setBetcode(betcode);
				lot.setLotMulti(lotmulti);
				lot.setAmt(amt*lotmulti);
				list.add(lot);
			} else {
				String[] betlengths = new String[14];

				String[] bets2 = betcode.split("-")[1].replace("^", "").split(",");

				for (int i = 0; i < bets2.length; i++) {
					betlengths[i] = bets2[i].length() + "-" + i;
				}

				Arrays.sort(betlengths);

				int zhushu = 1;
				List<Integer> notSplit = new ArrayList<Integer>();
				List<Integer> beSplit = new ArrayList<Integer>();

				for (int i = betlengths.length - 1; i > 0; i--) {
					zhushu = zhushu * Integer.parseInt(betlengths[i].split("-")[0]);
					notSplit.add(Integer.parseInt(betlengths[i].split("-")[1]));
					if (zhushu * Integer.parseInt(betlengths[i - 1].split("-")[0]) >= 10000) {
						for (int j = i - 1; j >= 0; j--) {
							beSplit.add(Integer.parseInt(betlengths[j].split("-")[1]));
						}
						break;
					}
				}

				String[] beSplitCode = new String[beSplit.size()];
				for (int i = 0; i < beSplit.size(); i++) {
					beSplitCode[i] = bets2[beSplit.get(i)];
				}

				List<String> combinations = new ArrayList<String>();
				List<String> changed = new ArrayList<String>();
				for (String splitCode : beSplitCode) {
					changed = combinations;
					combinations = new ArrayList<String>();
					for (char c : splitCode.toCharArray()) {
						if (changed.size() == 0) {
							combinations.add(String.valueOf(c));
						} else {
							for (String change : changed) {
								combinations.add(change + "," + String.valueOf(c));
							}
						}
					}
				}

				for (String combination : combinations) {
					String[] realcode = new String[14];
					for (int not : notSplit) {
						realcode[not] = bets2[not];
					}

					for (int i = 0; i < beSplit.size(); i++) {
						realcode[beSplit.get(i)] = combination.split(",")[i];
					}

					SplitedLot lot = new SplitedLot(lotterytype);
					lot.setBetcode(appendBetcode(realcode));
					lot.setLotMulti(lotmulti);
					long newamt = 0;
					if(lot.getBetcode().startsWith("400201")) {
						newamt = rj01.getSingleBetAmount(lot.getBetcode(),new BigDecimal(lotmulti), 200);
					}else if(lot.getBetcode().startsWith("400202")) {
						newamt = rj02.getSingleBetAmount(lot.getBetcode(),new BigDecimal(lotmulti), 200);
					}else {
						newamt = getSingleBetAmount(lot.getBetcode(),new BigDecimal(lotmulti), 200);
					}
					lot.setAmt(newamt);
					list.add(lot);
				}
			}
		}
		return list;
	}
	
	
	
	private String appendBetcode(String[] strs) {
		StringBuilder betcode = new StringBuilder(String.valueOf(lotterytype));
		int numselect = 0;
		for(String str:strs) {
			if(!str.equals("~")) {
				numselect = numselect + str.length();
			}
		}
		
		if(numselect==9) {
			betcode.append("01-");
		}else {
			betcode.append("02-");
		}
		
		for(String str:strs) {
			betcode.append(str).append(",");
		}
		if(betcode.toString().endsWith(",")) {
			betcode.deleteCharAt(betcode.length()-1);
		}
		betcode.append("^");
		return betcode.toString();
	}
	

}
