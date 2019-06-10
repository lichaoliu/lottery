package com.lottery.controller.admin.dto;

public class KcMonitor {
	private Integer lotteryType;
	private String lotname;
	private String phase;
	private String deadLine;
	Object[] unallottedTicket;
	Object[] unsentTicket;
	Object[] printTicket;
	
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getLotname() {
		return lotname;
	}
	public void setLotname(String lotname) {
		this.lotname = lotname;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getDeadLine() {
		return deadLine;
	}
	public void setDeadLine(String deadLine) {
		this.deadLine = deadLine;
	}
	public Object[] getUnallottedTicket() {
		return unallottedTicket;
	}
	public void setUnallottedTicket(Object[] unallottedTicket) {
		this.unallottedTicket = unallottedTicket;
	}
	public Object[] getUnsentTicket() {
		return unsentTicket;
	}
	public void setUnsentTicket(Object[] unsentTicket) {
		this.unsentTicket = unsentTicket;
	}
	public Object[] getPrintTicket() {
		return printTicket;
	}
	public void setPrintTicket(Object[] printTicket) {
		this.printTicket = printTicket;
	}
}
