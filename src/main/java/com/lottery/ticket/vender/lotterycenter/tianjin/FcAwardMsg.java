package com.lottery.ticket.vender.lotterycenter.tianjin;

import java.io.Serializable;

public class FcAwardMsg implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6645841620813729081L;
	/**
	 * 玩法名称
	 */
	private String gameName;
	/**
	 * 逻辑机号
	 */
	private String logicCode;
	/**
	 * 期号
	 */
	private String gameIssue;
	/**
	 * 中奖金额
	 */
	private String winMoney;
	/**
	 * 票面密码
	 */
	private String ticketCode;
	/**
	 * 投注时间
	 */
	private String pTime;
	public String getGameName() {
		return gameName;
	}
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	public String getLogicCode() {
		return logicCode;
	}
	public void setLogicCode(String logicCode) {
		this.logicCode = logicCode;
	}
	public String getGameIssue() {
		return gameIssue;
	}
	public void setGameIssue(String gameIssue) {
		this.gameIssue = gameIssue;
	}
	public String getWinMoney() {
		return winMoney;
	}
	public void setWinMoney(String winMoney) {
		this.winMoney = winMoney;
	}
	public String getTicketCode() {
		return ticketCode;
	}
	public void setTicketCode(String ticketCode) {
		this.ticketCode = ticketCode;
	}
	public String getpTime() {
		return pTime;
	}
	public void setpTime(String pTime) {
		this.pTime = pTime;
	}
	
	

}
