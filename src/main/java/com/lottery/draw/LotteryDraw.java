package com.lottery.draw;

import com.lottery.common.contains.YesNoStatus;

public class LotteryDraw {
	public static String LEVEV_SPILTSTRI=",";//奖级之间的分割符号
	/**
	 * 开奖结果
	 * */
	private Integer lotteryType;//彩种
	private String phase;//期号
	private String volumeOfSales;	//销售量
	private Integer status;//状态，PhaseStatus
	private String jackpot;			//奖池
	private String timeDraw;		//开奖时间
	private String addJackpot;//加奖奖池
	private String resultDetail;//开奖详情，奖金，奖级等,格式为：奖级_注数_每注金额,大乐投追加用z1_注数_每注金额,中间用逗号分割
	private String windCode;//开奖号码，号码用,号分割；特殊符号，或篮球用|分割,格式为：01,02,03,04,05,06|07
	private String drawDataFrom;//来自哪个终端
	private Integer isDraw=YesNoStatus.no.getValue();//是否直接开奖
	private String responsMessage;//返回信息
	public String toString(){
		StringBuffer sb = new StringBuffer("开奖详情：");
		sb.append("[彩种："+lotteryType);
		sb.append(",期号:"+phase);
		sb.append(",销售量:"+volumeOfSales);
		sb.append(",奖池:"+jackpot);
		sb.append(",加奖奖池:"+addJackpot);
		sb.append(",状态:"+status);
		sb.append(",开奖详情:"+resultDetail);
		sb.append(",开奖号码:("+windCode);
		sb.append("),来自终端:"+drawDataFrom+"]");
		return sb.toString();
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getPhase() {
		return phase;
	}
	
	public Integer getStatus() {
		return status;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getVolumeOfSales() {
		return volumeOfSales;
	}
	public void setVolumeOfSales(String volumeOfSales) {
		this.volumeOfSales = volumeOfSales;
	}
	public String getJackpot() {
		return jackpot;
	}
	public void setJackpot(String jackpot) {
		this.jackpot = jackpot;
	}
	public String getTimeDraw() {
		return timeDraw;
	}
	public void setTimeDraw(String timeDraw) {
		this.timeDraw = timeDraw;
	}
	public String getResultDetail() {
		return resultDetail;
	}
	public void setResultDetail(String resultDetail) {
		this.resultDetail = resultDetail;
	}
	public String getWindCode() {
		return windCode;
	}
	public void setWindCode(String windCode) {
		this.windCode = windCode;
	}
	public String getAddJackpot() {
		return addJackpot;
	}
	public void setAddJackpot(String addJackpot) {
		this.addJackpot = addJackpot;
	}

	public String getDrawFrom() {
		return drawDataFrom;
	}
	public void setDrawFrom(String drawFrom) {
		this.drawDataFrom = drawFrom;
	}

	public String getResponsMessage() {
		return responsMessage;
	}

	public void setResponsMessage(String responsMessage) {
		this.responsMessage = responsMessage;
	}

	public String getDrawDataFrom() {
		return drawDataFrom;
	}

	public void setDrawDataFrom(String drawDataFrom) {
		this.drawDataFrom = drawDataFrom;
	}

	public Integer getIsDraw() {
		return isDraw;
	}

	public void setIsDraw(Integer isDraw) {
		this.isDraw = isDraw;
	}
}
