package com.lottery.lottype.dc;

public class DanChangResult {

	private String matchid;
	
	private boolean cancel;
	
	private String result;
	
	private String odd;

	public String getMatchid() {
		return matchid;
	}

	public void setMatchid(String matchid) {
		this.matchid = matchid;
	}

	public boolean isCancel() {
		return cancel;
	}

	public void setCancel(boolean cancel) {
		this.cancel = cancel;
	}

	public String getResult() {
		return result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getOdd() {
		return odd;
	}

	public void setOdd(String odd) {
		this.odd = odd;
	}

	public DanChangResult(String matchid) {
		super();
		this.matchid = matchid;
	}
	
	
	
}
