package com.lottery.b2b.message.impl;

import com.lottery.b2b.message.IExternalMessage;

public abstract class AbstractExternalMessage implements IExternalMessage{

	private String version;
	private String merchant;
	private String messageId;
	private String command;
	private String timestamp;
	private String messageBody;
	private String signature;
	public String getVersion() {
		return version;
	}
	public void setVersion(String version) {
		this.version = version;
	}
	public String getMerchant() {
		return merchant;
	}
	public void setMerchant(String merchant) {
		this.merchant = merchant;
	}
	public String getMessageId() {
		return messageId;
	}
	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}
	public String getCommand() {
		return command;
	}
	public void setCommand(String command) {
		this.command = command;
	}
	
	public String getTimestamp() {
		return timestamp;
	}
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	public String getMessageBody() {
		return messageBody;
	}
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	public String getSignature() {
		return signature;
	}
	public void setSignature(String signature) {
		this.signature = signature;
	}
	
}
