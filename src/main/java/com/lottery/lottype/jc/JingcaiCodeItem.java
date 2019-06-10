package com.lottery.lottype.jc;

import java.util.HashMap;
import java.util.Map;

import com.lottery.common.contains.lottery.LotteryType;

/**
 * 竞彩投注注码解析类
 * @author liu
 *
 */
public class JingcaiCodeItem {

	//赛事id
	private String matchid;
	
	//投注玩法
	private String type;
	
	//投注彩种
	private LotteryType lotteryType;
	
	
	//注码和对应的赔率
	private Map<String,String> codeItem;
	
	//特殊数字如让分、让球、预设总分
	private String specialItem;

	public JingcaiCodeItem(String matchid,String type) {
		super();
		this.matchid = matchid;
		this.type = type;
		this.codeItem = new HashMap<String, String>();
	}
	
	

	public JingcaiCodeItem(String matchid, String type, LotteryType lotteryType) {
		super();
		this.matchid = matchid;
		this.type = type;
		this.lotteryType = lotteryType;
		this.codeItem = new HashMap<String, String>();
	}



	public String getSpecialItem() {
		return specialItem;
	}

	public void setSpecialItem(String specialItem) {
		this.specialItem = specialItem;
	}

	public String getMatchid() {
		return matchid;
	}

	public Map<String, String> getCodeItem() {
		return codeItem;
	}
	
	public void addCodeItem(String code,String odd) {
		codeItem.put(code, odd);
	}

	public String getType() {
		return type;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}
	
	
	

}
