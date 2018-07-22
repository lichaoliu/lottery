package com.lottery.core.domain.print;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Created by fengqinyun on 16/11/9.
 */
@Table(name="print_ticket")
@Entity
public class PrintTicket implements Serializable {

    @Id
    @Column(name = "id")
    private String id;

    @Column(name="serial_id")
    private String serialId;//真实票号(落地票号)
    @Column(name = "content")
    private String content; // 拆票内容
    @Column(name = "passwd")
    private String passwd; // 票密码
    @Column(name = "phase")
    private String phase; // 期数


    @Column(name = "lottery_type")
    private Integer lotteryType;

    @Column(name="other_lottery_type")
    private String otherLotteryType;//对方彩种gameId
    @Column(name="other_phase")
    private String otherPhase;//对方期号
    @Column(name="other_content")
    private String otherContent;//对方出票内容number
    
    @Column(name="other_bet_type")
    private String otherBetType;//对方投注方式
    @Column(name="other_play_type")
    private String otherPlayType;//对方玩法
    @Column(name="icount")
  	private Integer icount; //总注数

  	@Column(name="fail_msg")
  	private String failMsg;//错误信息
  	
    @Column(name="print_server")
    private String printServer;//打票机器
    @Column(name="prize_server")
    private String prizeServer;//兑奖机器  默认同打票机器
	@Column(name="print_count")
    private Integer printCount;//打印次数
	@Column(name = "exchange_count")
	private Integer exchangeCount;//兑奖次数
    @Column(name="is_print")
    private Integer isPrint;//是否打印

    @Column(name = "amount", nullable = false)
    private BigDecimal amount; // 票金额

    @Column(name = "create_time")
    private Date createTime; // 拆票生成时间

    @Column(name = "send_time")
    private Date sendTime; // 送票时间

    @Column(name = "print_time")
    private Date printTime; // 出票时间

    @Column(name = "end_time", nullable = false)
    private Date endTime; // 出票截止时间

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


    @Column(name = "is_exchanged")
    private Integer isExchanged; // 是否已兑 YesNoStatus
    @Column(name = "exchange_time")
    private Date exchangeTime; // 兑奖日期

    @Column(name = "addition")
    private Integer addition; // 扩展字段：是否追加投注 YesNoStatus
    @Column(name = "peilv")
    private String peilv; // 竞彩赔率


    @Column(name = "agency_prize")
    private BigDecimal agencyPrize;// 出票中心奖金

    @Column(name = "is_priority")
	private Integer isPriority;//是否优先 YesNoStatus
	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getSerialId() {
		return serialId;
	}


	public void setSerialId(String serialId) {
		this.serialId = serialId;
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


	public Integer getLotteryType() {
		return lotteryType;
	}


	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}


	public String getOtherLotteryType() {
		return otherLotteryType;
	}


	public void setOtherLotteryType(String otherLotteryType) {
		this.otherLotteryType = otherLotteryType;
	}


	public String getOtherPhase() {
		return otherPhase;
	}


	public void setOtherPhase(String otherPhase) {
		this.otherPhase = otherPhase;
	}


	public String getOtherContent() {
		return otherContent;
	}


	public void setOtherContent(String otherContent) {
		this.otherContent = otherContent;
	}


	public String getOtherBetType() {
		return otherBetType;
	}


	public void setOtherBetType(String otherBetType) {
		this.otherBetType = otherBetType;
	}


	public String getOtherPlayType() {
		return otherPlayType;
	}


	public void setOtherPlayType(String otherPlayType) {
		this.otherPlayType = otherPlayType;
	}


	public Integer getIcount() {
		return icount;
	}


	public void setIcount(Integer icount) {
		this.icount = icount;
	}




	public String getFailMsg() {
		return failMsg;
	}


	public void setFailMsg(String failMsg) {
		this.failMsg = failMsg;
	}


	public String getPrintServer() {
		return printServer;
	}


	public void setPrintServer(String printServer) {
		this.printServer = printServer;
	}


	public String getPrizeServer() {
		return prizeServer;
	}


	public void setPrizeServer(String prizeServer) {
		this.prizeServer = prizeServer;
	}


	public Integer getPrintCount() {
		return printCount;
	}


	public void setPrintCount(Integer printCount) {
		this.printCount = printCount;
	}


	public Integer getIsPrint() {
		return isPrint;
	}


	public void setIsPrint(Integer isPrint) {
		this.isPrint = isPrint;
	}





	public BigDecimal getAmount() {
		return amount;
	}


	public void setAmount(BigDecimal amount) {
		this.amount = amount;
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


	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
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


	public BigDecimal getAgencyPrize() {
		return agencyPrize;
	}


	public void setAgencyPrize(BigDecimal agencyPrize) {
		this.agencyPrize = agencyPrize;
	}


	public Integer getExchangeCount() {
		return exchangeCount;
	}


	public void setExchangeCount(Integer exchangeCount) {
		this.exchangeCount = exchangeCount;
	}

	public Integer getIsPriority() {
		return isPriority;
	}

	public void setIsPriority(Integer isPriority) {
		this.isPriority = isPriority;
	}


	@Override
	public String toString() {
		return "PrintTicket{" +
				"id='" + id + '\'' +
				", serialId='" + serialId + '\'' +
				", content='" + content + '\'' +
				", passwd='" + passwd + '\'' +
				", phase='" + phase + '\'' +
				", lotteryType=" + lotteryType +
				", otherLotteryType='" + otherLotteryType + '\'' +
				", otherPhase='" + otherPhase + '\'' +
				", otherContent='" + otherContent + '\'' +
				", otherBetType='" + otherBetType + '\'' +
				", otherPlayType='" + otherPlayType + '\'' +
				", icount=" + icount +
				", failMsg='" + failMsg + '\'' +
				", printServer='" + printServer + '\'' +
				", prizeServer='" + prizeServer + '\'' +
				", printCount=" + printCount +
				", exchangeCount=" + exchangeCount +
				", isPrint=" + isPrint +
				", amount=" + amount +
				", createTime=" + createTime +
				", sendTime=" + sendTime +
				", printTime=" + printTime +
				", endTime=" + endTime +
				", multiple=" + multiple +
				", playType=" + playType +
				", status=" + status +
				", ticketResultStatus=" + ticketResultStatus +
				", pretaxPrize=" + pretaxPrize +
				", posttaxPrize=" + posttaxPrize +
				", isExchanged=" + isExchanged +
				", exchangeTime=" + exchangeTime +
				", addition=" + addition +
				", peilv='" + peilv + '\'' +
				", agencyPrize=" + agencyPrize +
				", isPriority=" + isPriority +
				'}';
	}
}
