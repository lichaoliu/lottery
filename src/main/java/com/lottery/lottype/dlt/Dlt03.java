package com.lottery.lottype.dlt;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.lottery.common.contains.ErrorCode;
import com.lottery.common.exception.LotteryException;
import com.lottery.common.util.MathUtils;
import com.lottery.common.util.StringUtil;
import com.lottery.lottype.SplitedLot;

public class Dlt03 extends DltX{

	Dlt02 dlt02 = new Dlt02();

	@Override
	public long getSingleBetAmount(String betcode, BigDecimal beishu,
			int oneAmount) {
		//玩法为200103
		if(!betcode.split("\\-")[0].equals("200103")) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		//注码以^结尾
		if(!betcode.endsWith("^")) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		//注码包含#
		if(!betcode.contains("#")) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		betcode = betcode.replace("^", "");
		String qian = betcode.split("-")[1].split("\\|")[0];
		String hou = betcode.split("-")[1].split("\\|")[1];
		
		//前为胆拖的话，匹配前胆拖正则
		if(qian.contains("#")&&(!qian.matches(dlt_dt_qian))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		//前不为胆拖的话，匹配前非胆拖正则
		if((!qian.contains("#"))&&(!qian.matches(dlt_qian))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		//后为胆拖的话，匹配后胆拖正则
		if(hou.contains("#")&&(!hou.matches(dlt_dt_hou))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		//后不为胆拖的话，匹配后非胆拖正则
		if((!hou.contains("#"))&&(!hou.matches(dlt_hou))) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		//检查前驱注码是否重复
		if(!checkDuplicate(qian.replace("#", ","))){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		//检查后驱注码是否重复
		if(!checkDuplicate(hou.replace("#", ","))){
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		//计算前区注数
		long zhushuQian = 0;
		if(qian.contains("#")) {
			int qiandan = qian.split("#")[0].split(",").length;
			int qiantuo = qian.split("#")[1].split(",").length;
			zhushuQian = MathUtils.combine(qiantuo, 5-qiandan);
		}else {
			zhushuQian = MathUtils.combine(qian.split(",").length, 5);
		}
		
		//前区注数大于10000，校验失败
		if(zhushuQian>10000) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		//计算后区注数
		long zhushuHou = 0;
		if(hou.contains("#")) {
			zhushuHou = hou.split("#")[1].split(",").length;
		}else {
			zhushuHou = MathUtils.combine(hou.split(",").length, 2);
		}
		
		//总注数=1 校验失败
		if(zhushuQian*zhushuHou==1) {
			throw new LotteryException(ErrorCode.betamount_error, ErrorCode.betamount_error.memo);
		}
		
		return zhushuQian*zhushuHou*beishu.longValue()*oneAmount;
	}

	@Override
	public List<SplitedLot> splitByType(String betcode, int lotmulti,
			int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		List<SplitedLot> zhumaList = transform(betcode,lotmulti,oneAmount);
		
		for(SplitedLot splitedLot:zhumaList) {
			if(!SplitedLot.isToBeSplit99(splitedLot.getLotMulti(),splitedLot.getAmt()/oneAmount*200)) {
				list.add(splitedLot);
			}else {
				int amtSingle = (int) (splitedLot.getAmt()/oneAmount*200 / splitedLot.getLotMulti());
				int permissionLotmulti = 2000000 / amtSingle;
				if(permissionLotmulti>99) {
					permissionLotmulti = 99;
				}
				list.addAll(SplitedLot.splitToPermissionMulti(splitedLot.getBetcode(), splitedLot.getLotMulti(), permissionLotmulti,lotterytype));
			}
		}
		for(SplitedLot s:list) {
			if(s.getBetcode().startsWith("200102")) {
				s.setAmt(dlt02.getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), oneAmount));
			}else {
				s.setAmt(getSingleBetAmount(s.getBetcode(), new BigDecimal(s.getLotMulti()), oneAmount));
			}
			
		}

		return list;
	}

	
	private List<SplitedLot> transform(String betcode,int lotmulti,int oneAmount) {
		List<SplitedLot> list = new ArrayList<SplitedLot>();
		betcode = betcode.replace("^", "");
		String qian = betcode.split("-")[1].split("\\|")[0];
		String hou = betcode.split("-")[1].split("\\|")[1];
		//计算前区注数
		long zhushuQian = 0;
		if(qian.contains("#")) {
			int qiandan = qian.split("#")[0].split(",").length;
			int qiantuo = qian.split("#")[1].split(",").length;
			zhushuQian = MathUtils.combine(qiantuo, 5-qiandan);
		}else {
			zhushuQian = MathUtils.combine(qian.split(",").length, 5);
		}
		
		//计算后区注数
		long zhushuHou = 0;
		if(hou.contains("#")) {
			zhushuHou = hou.split("#")[1].split(",").length;
		}else {
			zhushuHou = MathUtils.combine(hou.split(",").length, 2);
		}
		
		if(qian.contains("#")) {
			if(zhushuQian==1) {
				qian = qian.replace("#", ",");
				String type = hou.contains("#")?"200103":"200102";
				String code = type+"-"+qian+"|"+hou+"^";
				if("200103".equals(type)) {
					SplitedLot lot = new SplitedLot(code, lotmulti, getSingleBetAmount(code, new BigDecimal(lotmulti), oneAmount),lotterytype);
					list.add(lot);
				}else {
					SplitedLot lot = new SplitedLot(code, lotmulti, dlt02.getSingleBetAmount(code, new BigDecimal(lotmulti), oneAmount),lotterytype);
					list.add(lot);
				}
				
			}else {
				if(zhushuQian*zhushuHou<=10000) {
					SplitedLot lot = new SplitedLot(betcode+"^", lotmulti, getSingleBetAmount(betcode+"^", new BigDecimal(lotmulti), oneAmount),lotterytype);
					list.add(lot);
				}else {
					if(hou.contains("#")) {
						String houdan = hou.split("#")[0];
						for(String code:hou.split("#")[1].split(",")) {
							String realcode = "200103-"+qian+"|"+houdan+","+code+"^";
							SplitedLot lot = new SplitedLot(realcode, lotmulti, getSingleBetAmount(realcode, new BigDecimal(lotmulti), oneAmount),lotterytype);
							list.add(lot);
						}
					}else {
						List<List<String>> selectCodes = MathUtils.getCodeCollection(Arrays.asList(hou.split(",")), 2);
						for(List<String> codes:selectCodes) {
							String code = "200103-"+qian+"|"+codes.get(0)+","+codes.get(1)+"^";
							SplitedLot lot = new SplitedLot(code, lotmulti, getSingleBetAmount(code, new BigDecimal(lotmulti), oneAmount),lotterytype);
							list.add(lot);
						}
					}
				}
			}
		}else {
			if(zhushuHou==1) {
				String realcode = "200102-"+qian+"|"+hou.replace("#", ",")+"^";
				SplitedLot lot = new SplitedLot(realcode, lotmulti, dlt02.getSingleBetAmount(realcode, new BigDecimal(lotmulti), oneAmount),lotterytype);
				list.add(lot);
			}else {
				if(zhushuQian*zhushuHou<10000) {
					SplitedLot lot = new SplitedLot(betcode+"^", lotmulti, getSingleBetAmount(betcode+"^", new BigDecimal(lotmulti), oneAmount),lotterytype);
					list.add(lot);
				}else {
					String houdan = hou.split("#")[0];
					for(String code:hou.split("#")[1].split(",")) {
						String realcode = "200102-"+qian+"|"+houdan+","+code+"^";
						SplitedLot lot = new SplitedLot(realcode, lotmulti, dlt02.getSingleBetAmount(realcode, new BigDecimal(lotmulti), oneAmount),lotterytype);
						list.add(lot);
					}
				}
			}
			
		}
		return list;
	}
	@Override
	public String caculatePrizeLevel(String betcode, String wincode,
			int oneAmount) {
		betcode = betcode.replace("^", "");
		boolean addition = oneAmount==300?true:false;
		StringBuilder sb = new StringBuilder();
		String betcodeQ = betcode.split("-")[1].split("\\|")[0];
		String betcodeH = betcode.split("-")[1].split("\\|")[1];
		
		List<List<String>> qians = new ArrayList<List<String>>();
		List<List<String>> hous = new ArrayList<List<String>>();
		if(betcodeQ.contains("#")) {
			int dan = betcodeQ.split("\\#")[0].split(",").length;
			qians = MathUtils.getCodeCollection(Arrays.asList(betcodeQ.split("\\#")[1].split(",")), 5-dan);
			
			for(List<String> qian:qians) {
				qian.addAll(Arrays.asList(betcodeQ.split("\\#")[0].split(",")));
			}
		}else {
			qians = MathUtils.getCodeCollection(Arrays.asList(betcodeQ.split(",")), 5);
		}
		
		if(betcodeH.contains("#")) {
			String dan = betcodeH.split("\\#")[0];
			String[] tuos = betcodeH.split("\\#")[1].split(",");
			
			for(String tuo:tuos) {
				List<String> list = new ArrayList<String>();
				list.add(dan);
				list.add(tuo);
				hous.add(list);
			}
		}else {
			hous = MathUtils.getCodeCollection(Arrays.asList(betcodeH.split(",")), 2);
		}
		for(List<String> qian:qians) {
			for(List<String> hou:hous) {
				String prize = getPrize(qian, hou, wincode.split("\\|")[0].split(","), wincode.split("\\|")[1].split(","), addition);
				if(!StringUtil.isEmpty(prize)) {
					sb.append(prize).append(",");
				}
			}
		}
		check2delete(sb);
		return sb.toString();
	}

}
