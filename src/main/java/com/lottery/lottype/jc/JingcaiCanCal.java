package com.lottery.lottype.jc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class JingcaiCanCal {

	//是否全部开奖
	private boolean canCal;
	
	//第一个没开奖的场次
	private String notOpenMatchid;
	
	//根据赛果和赔率计算的竞彩开奖结果
	private Map<String,JingcaiResult> results;
	
	private List<JingcaiCodeItem> codeItems;

	public boolean isCanCal() {
		return canCal;
	}

	public void setCanCal(boolean canCal) {
		this.canCal = canCal;
	}

	public String getNotOpenMatchid() {
		return notOpenMatchid;
	}

	public void setNotOpenMatchid(String notOpenMatchid) {
		this.notOpenMatchid = notOpenMatchid;
	}


	public Map<String, JingcaiResult> getResults() {
		return results;
	}

	public void addResults(JingcaiResult result) {
		this.results.put(result.getMatchid(), result);
	}

	public List<JingcaiCodeItem> getCodeItems() {
		return codeItems;
	}
	
	public void addCodeItems(JingcaiCodeItem item) {
		codeItems.add(item);
	}

	public JingcaiCanCal() {
		super();
		this.results = new HashMap<String,JingcaiResult>();
		this.codeItems = new ArrayList<JingcaiCodeItem>();
	}
}
