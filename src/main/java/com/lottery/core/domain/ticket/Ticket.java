package com.lottery.core.domain.ticket;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "ticket")
public class Ticket implements Serializable {

	private static final long serialVersionUID = -5205622398106067051L;
	@Id
	@Column(name = "id")
	private String id;
	@Column(name = "orderid", nullable = false)
	private String orderId; // 订单编号
	@Column(name = "content", nullable = false)
	private String content; // 拆票内容
	@Column(name = "passwd")
	private String passwd; // 票密码
	@Column(name = "phase")
	private String phase; // 期数
	@Column(name = "batch_id")
	private String batchId; // 批次编号
	@Column(name = "batch_index")
	private Long batchIndex; // 批次编号
	@Column(name = "lottery_type", nullable = false)
	private Integer lotteryType;
	@Column(name = "amount", nullable = false)
	private BigDecimal amount; // 票金额
	@Column(name = "terminal_id")
	private Long terminalId; // 实际票被送到的终端号
	@Column(name = "create_time")
	private Date createTime; // 拆票生成时间

	@Column(name = "send_time")
	private Date sendTime; // 送票时间

	@Column(name = "print_time")
	private Date printTime; // 出票时间

	@Column(name = "dead_line", nullable = false)
	private Date deadline; // 出票截止时间
	@Column(name = "draw_time")
	private Date drawTime; // 系统开奖时间，这里应该不是彩期开奖的时间，而是系统自动或手动对这张票进行开奖操作的时间
	@Column(name = "multiple", nullable = false)
	private Integer multiple; // 倍数
	@Column(name = "play_type", nullable = false)
	private Integer playType; // 玩法 PlayType
	@Column(name = "status", nullable = false)
	private Integer status; // 票状态 TicketStatus
	@Column(name = "ticket_result_status")
	private Integer ticketResultStatus; // 票中奖状态 TicketResultStatus
	@Column(name = "pretax_prize")
	private BigDecimal pretaxPrize; // 税前中奖奖金
	@Column(name = "posttax_prize")
	private BigDecimal posttaxPrize; // 税后中奖奖金
	@Column(name = "prize_detail")
	private String prizeDetail; // 中奖详情，与详情对应：奖级^金额|奖级^金额

	@Column(name = "is_exchanged")
	private Integer isExchanged; // 是否已兑 YesNoStatus
	@Column(name = "exchange_time")
	private Date exchangeTime; // 兑奖日期
	@Column(name = "external_id")
	private String externalId; // 外部票号，对应某些出票商使用 (乐世胜豪北单出票用来存票密码)
	@Column(name = "addition")
	private Integer addition; // 扩展字段：是否追加投注 YesNoStatus
	@Column(name = "peilv")
	private String peilv; // 竞彩赔率
	@Column(name = "failure_type")
	private Integer failureType; // 出票失败类型 ,TicketFailureType
	@Column(name = "failure_message")
	private String failureMessage; // 出票失败消息
	@Column(name = "terminate_time")
	private Date terminateTime;
	@Column(name = "userno")
	@NotNull
	private String userno;
	@Column(name = "terminal_type")
	private Integer terminalType;// 终端类型id,TeminalType 的value值
	@Column(name = "total_prize")
	private BigDecimal totalPrize;// 中奖总金额
	@Column(name = "add_prize")
	private BigDecimal addPrize;// 加奖奖金
	@Column(name = "user_name")
	private String userName;// 登陆名
	@Column(name = "real_name")
	private String realName;// 用户真实姓名

	@Column(name = "match_nums")
	private String matchNums;
	@Column(name = "first_matchnum")
	private Long firstMatchnum;
	@Column(name = "last_matchnum")
	private Long lastMatchnum;
	@Column(name = "agency_prize")
	private BigDecimal agencyPrize;// 出票中心奖金
	@Column(name = "agency_exchanged")
	private Integer agencyExchanged;// 中心是否兑奖
    @Column(name="machine_code")
	private String machineCode;//逻辑机号
    @Column(name="sell_run_code")
    private String sellRunCode;//销售流水号
    @Column(name="md5_code")
    private String md5Code;//md5加密
    
    @Column(name="agent_id")
	private String agentId;
	@Column(name = "order_amount")
	private BigDecimal orderAmount;//订单金额
	
	@Column(name = "ticket_end_time")
	private Date ticketEndTime;//票截止时间
    @Column(name="serial_id")
	private String serialId;//真实票号(落地票号)


    
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String getPhase() {
		return phase;
	}

	public void setPhase(String phase) {
		this.phase = phase;
	}

	public Long getBatchIndex() {
		return batchIndex;
	}

	public void setBatchIndex(Long batchIndex) {
		this.batchIndex = batchIndex;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getSendTime() {
		return sendTime;
	}

	public void setSendTime(Date sendTime) {
		this.sendTime = sendTime;
	}

	public Date getPrintTime() {
		return printTime;
	}

	public void setPrintTime(Date printTime) {
		this.printTime = printTime;
	}

	public Date getDrawTime() {
		return drawTime;
	}

	public void setDrawTime(Date drawTime) {
		this.drawTime = drawTime;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public Integer getTicketResultStatus() {
		return ticketResultStatus;
	}

	public void setTicketResultStatus(Integer ticketResultStatus) {
		this.ticketResultStatus = ticketResultStatus;
	}

	public BigDecimal getPretaxPrize() {
		return pretaxPrize;
	}

	public void setPretaxPrize(BigDecimal pretaxPrize) {
		this.pretaxPrize = pretaxPrize;
	}

	public BigDecimal getPosttaxPrize() {
		return posttaxPrize;
	}

	public void setPosttaxPrize(BigDecimal posttaxPrize) {
		this.posttaxPrize = posttaxPrize;
	}

	public String getPrizeDetail() {
		return prizeDetail;
	}

	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}

	public Integer getIsExchanged() {
		return isExchanged;
	}

	public void setIsExchanged(Integer isExchanged) {
		this.isExchanged = isExchanged;
	}

	public Date getExchangeTime() {
		return exchangeTime;
	}

	public void setExchangeTime(Date exchangeTime) {
		this.exchangeTime = exchangeTime;
	}

	public String getExternalId() {
		return externalId;
	}

	public void setExternalId(String externalId) {
		this.externalId = externalId;
	}

	public Integer getAddition() {
		return addition;
	}

	public void setAddition(Integer addition) {
		this.addition = addition;
	}

	public String getPeilv() {
		return peilv;
	}

	public void setPeilv(String peilv) {
		this.peilv = peilv;
	}

	public Integer getFailureType() {
		return failureType;
	}

	public void setFailureType(Integer failureType) {
		this.failureType = failureType;
	}

	public String getFailureMessage() {
		return failureMessage;
	}

	public void setFailureMessage(String failureMessage) {
		this.failureMessage = failureMessage;
	}

	public Date getTerminateTime() {
		return terminateTime;
	}

	public void setTerminateTime(Date terminateTime) {
		this.terminateTime = terminateTime;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getBatchId() {
		return batchId;
	}

	public void setBatchId(String batchId) {
		this.batchId = batchId;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public BigDecimal getTotalPrize() {
		return totalPrize;
	}

	public void setTotalPrize(BigDecimal totalPrize) {
		this.totalPrize = totalPrize;
	}

	public BigDecimal getAddPrize() {
		return addPrize;
	}

	public void setAddPrize(BigDecimal addPrize) {
		this.addPrize = addPrize;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getRealName() {
		return realName;
	}

	public void setRealName(String realName) {
		this.realName = realName;
	}

	

	public String getMatchNums() {
		return matchNums;
	}

	public void setMatchNums(String matchNums) {
		this.matchNums = matchNums;
	}

	public Long getFirstMatchnum() {
		return firstMatchnum;
	}

	public void setFirstMatchnum(Long firstMatchnum) {
		this.firstMatchnum = firstMatchnum;
	}

	public Long getLastMatchnum() {
		return lastMatchnum;
	}

	public void setLastMatchnum(Long lastMatchnum) {
		this.lastMatchnum = lastMatchnum;
	}

	public BigDecimal getAgencyPrize() {
		return agencyPrize;
	}

	public void setAgencyPrize(BigDecimal agencyPrize) {
		this.agencyPrize = agencyPrize;
	}

	public Integer getAgencyExchanged() {
		return agencyExchanged;
	}

	public void setAgencyExchanged(Integer agencyExchanged) {
		this.agencyExchanged = agencyExchanged;
	}

	


	public String getMd5Code() {
		return md5Code;
	}

	public void setMd5Code(String md5Code) {
		this.md5Code = md5Code;
	}

	
	
	public String getAgentId() {
		return agentId;
	}

	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

	public String getSerialId() {
		return serialId;
	}

	public void setSerialId(String serialId) {
		this.serialId = serialId;
	}

	@Override
	public String toString() {
		return "Ticket{" +
				"id='" + id + '\'' +
				", orderId='" + orderId + '\'' +
				", content='" + content + '\'' +
				", passwd='" + passwd + '\'' +
				", phase='" + phase + '\'' +
				", batchId='" + batchId + '\'' +
				", batchIndex=" + batchIndex +
				", lotteryType=" + lotteryType +
				", amount=" + amount +
				", terminalId=" + terminalId +
				", createTime=" + createTime +
				", sendTime=" + sendTime +
				", printTime=" + printTime +
				", deadline=" + deadline +
				", drawTime=" + drawTime +
				", multiple=" + multiple +
				", playType=" + playType +
				", status=" + status +
				", ticketResultStatus=" + ticketResultStatus +
				", pretaxPrize=" + pretaxPrize +
				", posttaxPrize=" + posttaxPrize +
				", prizeDetail='" + prizeDetail + '\'' +
				", isExchanged=" + isExchanged +
				", exchangeTime=" + exchangeTime +
				", externalId='" + externalId + '\'' +
				", addition=" + addition +
				", peilv='" + peilv + '\'' +
				", failureType=" + failureType +
				", failureMessage='" + failureMessage + '\'' +
				", terminateTime=" + terminateTime +
				", userno='" + userno + '\'' +
				", terminalType=" + terminalType +
				", totalPrize=" + totalPrize +
				", addPrize=" + addPrize +
				", userName='" + userName + '\'' +
				", realName='" + realName + '\'' +
				", matchNums='" + matchNums + '\'' +
				", firstMatchnum=" + firstMatchnum +
				", lastMatchnum=" + lastMatchnum +
				", agencyPrize=" + agencyPrize +
				", agencyExchanged=" + agencyExchanged +
				", machineCode='" + machineCode + '\'' +
				", sellRunCode='" + sellRunCode + '\'' +
				", md5Code='" + md5Code + '\'' +
				", agentId='" + agentId + '\'' +
				", orderAmount=" + orderAmount +
				", ticketEndTime=" + ticketEndTime +
				", serialId='" + serialId + '\'' +
				'}';
	}

	public BigDecimal getOrderAmount() {
		return orderAmount;
	}

	public void setOrderAmount(BigDecimal orderAmount) {
		this.orderAmount = orderAmount;
	}

	public Date getTicketEndTime() {
		return ticketEndTime;
	}

	public void setTicketEndTime(Date ticketEndTime) {
		this.ticketEndTime = ticketEndTime;
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
