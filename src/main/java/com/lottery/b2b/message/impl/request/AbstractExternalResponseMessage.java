package com.lottery.b2b.message.impl.request;

import java.math.BigDecimal;

import com.lottery.b2b.message.IExternalResponseMessage;
import com.lottery.b2b.message.impl.AbstractExternalMessage;
import com.lottery.common.contains.ErrorCode;

public abstract class AbstractExternalResponseMessage extends AbstractExternalMessage
		implements IExternalResponseMessage {

	private ErrorCode errorCode;
	private String timestamp;
	private BigDecimal creditBalance;
	private String key;
	public ErrorCode getErrorCode() {
		return errorCode;
	}
	private Long terminalId;

	public void setErrorCode(ErrorCode errorCode) {
		this.errorCode = errorCode;
	}
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	 
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public BigDecimal getCreditBalance() {
		return creditBalance;
	}
	public void setCreditBalance(BigDecimal creditBalance) {
		this.creditBalance = creditBalance;
	}

	@Override
	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}
}
