package com.lottery.lottype.jc;


//竞彩赛事计算结果
public class JingcaiResult {

	
	private String matchid;
	
	//是否取消
	private boolean cancel;
	
	//固定奖金结果(带赔率的)
	private String result_static;
	
	//浮动奖金结果(不带赔率的单关的最终结果)
	private String result_dynamic;
	
	//浮动奖金
	private String prize_dynamic;

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

	public String getResult_static() {
		return result_static;
	}

	public void setResult_static(String result_static) {
		this.result_static = result_static;
	}

	public String getResult_dynamic() {
		return result_dynamic;
	}

	public void setResult_dynamic(String result_dynamic) {
		this.result_dynamic = result_dynamic;
	}

	public String getPrize_dynamic() {
		return prize_dynamic;
	}

	public void setPrize_dynamic(String prize_dynamic) {
		this.prize_dynamic = prize_dynamic;
	}

	public JingcaiResult(String matchid, boolean cancel) {
		super();
		this.matchid = matchid;
		this.cancel = cancel;
	}
	
	

	
}
