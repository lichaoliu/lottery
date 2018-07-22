package com.lottery.common.contains.ticket;


import com.lottery.common.contains.lottery.TerminalType;
import com.lottery.common.contains.lottery.TicketFailureType;
import com.lottery.common.contains.lottery.TicketVenderStatus;

import java.io.Serializable;
import java.util.Date;

/**
 * 解析好的出票商票实体
 */
public class TicketVender implements Serializable {

	private static final long serialVersionUID = 5562166867709951963L;

	private String id; // 出票商返回的票号，可能是我方票号，也可能是其他编码

	private Integer lotteryType; // 如果能获取到彩种则此处有值

	private String externalId; // 出票商票号

	private TicketVenderStatus status; // 出票商系统中的出票状态(我方定义的状态)

	private String statusCode; // 出票商系统中的出票状态码(对方原始状态)

	private String ext; // 出票扩展信息

	private TicketFailureType failureType; // 失败类型

	private String failureTypeCode; // 失败类型码(对方原始码)

	private TerminalType terminalType; // 传入终端类型以支持不同终端进行不同的错误定制处理

	private Long terminalId; // 终端号

	private String message; // 额外消息

	private Date printTime; // 出票时间，仅针对出票成功的票有效

	private boolean ticketNotify=false; // 是否是出票通知(默认查票)

	private String peiLv;// 赔率

	private String sendMessage;//发送消息

	private String responseMessage;//相应消息
	private String serialId;//出票商落地票号

	private String otherPeilv;//出票方赔率

	private boolean terminalIdJudge=true;//终端号判断

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public TicketVenderStatus getStatus() {
		return status;
	}

	public void setStatus(TicketVenderStatus status) {
		this.status = status;
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

	public String getExt() {
		return ext;
	}

	public void setExt(String ext) {
		this.ext = ext;
	}

	public TerminalType getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(TerminalType terminalType) {
		this.terminalType = terminalType;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	public boolean isTicketNotify() {
		return ticketNotify;
	}

	public void setTicketNotify(boolean ticketNotify) {
		this.ticketNotify = ticketNotify;
	}

	

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
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

	public String getPeiLv() {
		return peiLv;
	}

	public void setPeiLv(String peiLv) {
		this.peiLv = peiLv;
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

	public String getOtherPeilv() {
		return otherPeilv;
	}

	public void setOtherPeilv(String otherPeilv) {
		this.otherPeilv = otherPeilv;
	}

	public boolean isTerminalIdJudge() {
		return terminalIdJudge;
	}

	public void setTerminalIdJudge(boolean terminalIdJudge) {
		this.terminalIdJudge = terminalIdJudge;
	}

	@Override
	public String toString() {
		return "TicketVender{" +
				"id='" + id + '\'' +
				", lotteryType=" + lotteryType +
				", externalId='" + externalId + '\'' +
				", status=" + status +
				", statusCode='" + statusCode + '\'' +
				", ext='" + ext + '\'' +
				", failureType=" + failureType +
				", failureTypeCode='" + failureTypeCode + '\'' +
				", terminalType=" + terminalType +
				", terminalId=" + terminalId +
				", message='" + message + '\'' +
				", printTime=" + printTime +
				", ticketNotify=" + ticketNotify +
				", peiLv='" + peiLv + '\'' +
				", sendMessage='" + sendMessage + '\'' +
				", responseMessage='" + responseMessage + '\'' +
				", serialId='" + serialId + '\'' +
				", otherPeilv='" + otherPeilv + '\'' +
				", terminalIdJudge=" + terminalIdJudge +
				'}';
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TicketVender that = (TicketVender) o;

		if (ticketNotify != that.ticketNotify) return false;
		if (terminalIdJudge != that.terminalIdJudge) return false;
		if (id != null ? !id.equals(that.id) : that.id != null) return false;
		if (lotteryType != that.lotteryType) return false;
		if (externalId != null ? !externalId.equals(that.externalId) : that.externalId != null) return false;
		if (status != that.status) return false;
		if (statusCode != null ? !statusCode.equals(that.statusCode) : that.statusCode != null) return false;
		if (ext != null ? !ext.equals(that.ext) : that.ext != null) return false;
		if (failureType != that.failureType) return false;
		if (failureTypeCode != null ? !failureTypeCode.equals(that.failureTypeCode) : that.failureTypeCode != null)
			return false;
		if (terminalType != that.terminalType) return false;
		if (terminalId != null ? !terminalId.equals(that.terminalId) : that.terminalId != null) return false;
		if (message != null ? !message.equals(that.message) : that.message != null) return false;
		if (printTime != null ? !printTime.equals(that.printTime) : that.printTime != null) return false;
		if (peiLv != null ? !peiLv.equals(that.peiLv) : that.peiLv != null) return false;
		if (sendMessage != null ? !sendMessage.equals(that.sendMessage) : that.sendMessage != null) return false;
		if (responseMessage != null ? !responseMessage.equals(that.responseMessage) : that.responseMessage != null)
			return false;
		if (serialId != null ? !serialId.equals(that.serialId) : that.serialId != null) return false;
		return otherPeilv != null ? otherPeilv.equals(that.otherPeilv) : that.otherPeilv == null;

	}

	@Override
	public int hashCode() {
		int result = id != null ? id.hashCode() : 0;
		result = 31 * result + (lotteryType != null ? lotteryType.hashCode() : 0);
		result = 31 * result + (externalId != null ? externalId.hashCode() : 0);
		result = 31 * result + (status != null ? status.hashCode() : 0);
		result = 31 * result + (statusCode != null ? statusCode.hashCode() : 0);
		result = 31 * result + (ext != null ? ext.hashCode() : 0);
		result = 31 * result + (failureType != null ? failureType.hashCode() : 0);
		result = 31 * result + (failureTypeCode != null ? failureTypeCode.hashCode() : 0);
		result = 31 * result + (terminalType != null ? terminalType.hashCode() : 0);
		result = 31 * result + (terminalId != null ? terminalId.hashCode() : 0);
		result = 31 * result + (message != null ? message.hashCode() : 0);
		result = 31 * result + (printTime != null ? printTime.hashCode() : 0);
		result = 31 * result + (ticketNotify ? 1 : 0);
		result = 31 * result + (peiLv != null ? peiLv.hashCode() : 0);
		result = 31 * result + (sendMessage != null ? sendMessage.hashCode() : 0);
		result = 31 * result + (responseMessage != null ? responseMessage.hashCode() : 0);
		result = 31 * result + (serialId != null ? serialId.hashCode() : 0);
		result = 31 * result + (otherPeilv != null ? otherPeilv.hashCode() : 0);
		result = 31 * result + (terminalIdJudge ? 1 : 0);
		return result;
	}
}
