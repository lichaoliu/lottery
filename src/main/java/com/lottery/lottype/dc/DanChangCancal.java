package com.lottery.lottype.dc;

import java.util.HashMap;
import java.util.Map;

public class DanChangCancal {

	private boolean cancal;
	
	private Map<String,DanChangResult> results;
	
	private String notcalMatchno;
	

	public boolean isCancal() {
		return cancal;
	}

	public void setCancal(boolean cancal) {
		this.cancal = cancal;
	}
	
	public void addResult(String matchid,DanChangResult result) {
		results.put(matchid, result);
	}
	
	public DanChangResult getResult(String matchesid) {
		return results.get(matchesid);
	}
	

	public DanChangCancal() {
		super();
		this.results = new HashMap<String, DanChangResult>();
	}

	public String getNotcalMatchno() {
		return notcalMatchno;
	}

	public void setNotcalMatchno(String notcalMatchno) {
		this.notcalMatchno = notcalMatchno;
	}
	
	
	
	
}
