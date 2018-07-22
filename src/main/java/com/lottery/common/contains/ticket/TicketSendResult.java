package com.lottery.common.contains.ticket;

import com.lottery.common.contains.lottery.LotteryType;
import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketFailureType;

import java.io.Serializable;
import java.util.Date;

/**
 * 解析好的出票商送票结果
 */
public class TicketSendResult implements Serializable {

	private static final long serialVersionUID = 935157685645826787L;

	private String id; // 我方票号

	private String requestId; // 送票票号，可能是我方票号，也可能是其他编码

	private LotteryType lotteryType; // 如果能获取到彩种则此处有值

	private String phase; // 彩期

	private String serialId; // 出票序列号，针对部分同步出票的情况在此记录

	private String externalId; // 外部票号

	private TicketSendResultStatus status; // 送票结果状态(我方状态)

	private String statusCode; // 送票结果状态码(对方原始状态)

	private TicketFailureType failureType; // 失败类型，送票时发生的限号等错误

	private String failureTypeCode; // 失败类型码(对方原始状态)

	private TerminalType terminalType; // 传入终端类型以支持不同终端进行不同的错误定制处理

	private Long terminalId; // 终端号

	private String message; // 额外消息

	private Date sendTime; // 送票时间，

	private String sendMessage;// 发送消息体

	private String responseMessage;// 出票方返回消息体

	private String peiLv;// 赔率(只用户虚拟账户投注)
	
	private String passwd;//票面密码

	private String machineCode;//逻辑机号
	
	private String sellRunCode;//逻辑机销售流水号
	
	private String md5Code;//md5加密
	
	private Date printTime;
	
	private boolean clearFailure = false;// 是否明确失败

	private boolean syncPrinted=false;//是否通过出票

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getRequestId() {
		return requestId;
	}

	public void setRequestId(String requestId) {
		this.requestId = requestId;
	}

	public TicketFailureType getFailureType() {
		return failureType;
	}

	public void setFailureType(TicketFailureType failureType) {
		this.failureType = failureType;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public LotteryType getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(LotteryType lotteryType) {
		this.lotteryType = lotteryType;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public TicketSendResultStatus getStatus() {
		return status;
	}

	public void setStatus(TicketSendResultStatus status) {
		this.status = status;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public String getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}

	public String getFailureTypeCode() {
		return failureTypeCode;
	}

	public void setFailureTypeCode(String failureTypeCode) {
		this.failureTypeCode = failureTypeCode;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getSendMessage() {
		return sendMessage;
	}

	public void setSendMessage(String sendMessage) {
		this.sendMessage = sendMessage;
	}

	public String getResponseMessage() {
		return responseMessage;
	}

	public void setResponseMessage(String responseMessage) {
		this.responseMessage = responseMessage;
	}

	public String getPeiLv() {
		return peiLv;
	}

	public void setPeiLv(String peiLv) {
		this.peiLv = peiLv;
	}

	public boolean isClearFailure() {
		return clearFailure;
	}

	public void setClearFailure(boolean clearFailure) {
		this.clearFailure = clearFailure;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	

	public String getMd5Code() {
		return md5Code;
	}

	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public boolean isSyncPrinted() {
		return syncPrinted;
	}

	public void setSyncPrinted(boolean syncPrinted) {
		this.syncPrinted = syncPrinted;
	}

	public String toString(){
		return "id="+id+",status="+status;
	}

	public String getMachineCode() {
		return machineCode;
	}

	public void setMachineCode(String machineCode) {
		this.machineCode = machineCode;
	}

	public String getSellRunCode() {
		return sellRunCode;
	}

	public void setSellRunCode(String sellRunCode) {
		this.sellRunCode = sellRunCode;
	}
	
}
