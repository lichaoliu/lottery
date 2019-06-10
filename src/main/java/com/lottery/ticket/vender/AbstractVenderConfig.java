package com.lottery.ticket.vender;

import com.lottery.ticket.IVenderConfig;


public abstract class AbstractVenderConfig implements IVenderConfig {

	private String requestUrl;
	private String checkUrl;
	private  Integer port;
	private String key;
	private String publicKey;
	private String privateKey;
	private Integer sendCount=50;
	private Integer checkCount=15;
	private String agentCode;
	private String passwd;
	private Integer timeOutSecondForCheck=30;
	private Integer timeOutSecondForSend=30;
	private Long terminalId;
	private Boolean syncTicketCheck=true;
	public String getRequestUrl() {
		return requestUrl;
	}
	public void setRequestUrl(String requestUrl) {
		this.requestUrl = requestUrl;
	}
	public String getCheckUrl() {
		return checkUrl;
	}
	public void setCheckUrl(String checkUrl) {
		this.checkUrl = checkUrl;
	}

	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getPublicKey() {
		return publicKey;
	}
	public void setPublicKey(String publicKey) {
		this.publicKey = publicKey;
	}
	public String getPrivateKey() {
		return privateKey;
	}
	public void setPrivateKey(String privateKey) {
		this.privateKey = privateKey;
	}
	public Integer getSendCount() {
		return sendCount;
	}
	public void setSendCount(Integer sendCount) {
		this.sendCount = sendCount;
	}
	public Integer getCheckCount() {
		return checkCount;
	}
	public void setCheckCount(Integer checkCount) {
		this.checkCount = checkCount;
	}
	public String getAgentCode() {
		return agentCode;
	}
	public void setAgentCode(String agentCode) {
		this.agentCode = agentCode;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public Integer getTimeOutSecondForCheck() {
		return timeOutSecondForCheck;
	}
	public void setTimeOutSecondForCheck(Integer timeOutSecondForCheck) {
		this.timeOutSecondForCheck = timeOutSecondForCheck;
	}
	public Integer getTimeOutSecondForSend() {
		return timeOutSecondForSend;
	}
	public void setTimeOutSecondForSend(Integer timeOutSecondForSend) {
		this.timeOutSecondForSend = timeOutSecondForSend;
	}
	public Long getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	@Override
	public Integer getPort() {
		return port;
	}

	public void setPort(Integer port) {
		this.port = port;
	}

	@Override
	public Boolean getSyncTicketCheck() {
		return syncTicketCheck;
	}

	public void setSyncTicketCheck(Boolean syncTicketCheck) {
		this.syncTicketCheck = syncTicketCheck;
	}
}
