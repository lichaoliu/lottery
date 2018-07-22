package com.lottery.controller.admin.dto;

import java.util.List;

public class TicketMonitor {
	private String terminalName;
	private List<Object[]> ids;
	private Long size;
	public String getTerminalName() {
		return terminalName;
	}
	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}
	public List<Object[]> getIds() {
		return ids;
	}
	public void setIds(List<Object[]> ids) {
		this.ids = ids;
	}
	public Long getSize() {
		return size;
	}
	public void setSize(Long size) {
		this.size = size;
	}
}
