package com.lottery.core.domain.ticket;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@Entity
@Table(name="lottery_order")
public class LotteryOrder implements Serializable{

	private static final long serialVersionUID = -2088636558466819988L;
	@Id
	@Column(name="id")
	private String id;							// 订单流水号
	@Column(name="userno")
	@NotNull
	private String userno;						// 接入商编号
	@Column(name="byuserno",nullable=false)
	private String buyUserno;                          //合买账号  
  	@Column(name="agencyno")
	private String agencyNo;                           //用户渠道
	@Column(name="pretax_prize")
  	private BigDecimal pretaxPrize;						//税前奖金
	@Column(name="small_posttaxprize")
	private BigDecimal smallPosttaxPrize;				//税后奖金
	@Column(name="big_posttaxprize")
	private BigDecimal bigPosttaxPrize;					//大奖税后奖金
	@Column(name="prize_detail")
	private String prizeDetail;                     //中奖详情
	@Column(name="order_result_status")
	private Integer orderResultStatus;    // 订单开奖结果状态 //OrderResultStatus
    @Column(name="amount",nullable=false)
	private BigDecimal amount;					// 金额
    @Column(name="amt")
    @NotNull
    private BigDecimal amt;                    //单倍金额
	@Column(name="receive_time",nullable=false)

    private Date receiveTime;				// 接票时间
	@Column(name="process_time")
	
	private Date processTime;				// 处理时间
	@Column(name="print_time")

	private Date printTime;					// 出票时间
	@Column(name="draw_time")

	private Date drawTime;					// 开奖时间
	@Column(name="reward_time")
	
	private Date rewardTime;				// 派奖时间
	@Column(name="dead_line",nullable=false)

	private Date deadline;					//出票截止时间
	@Column(name="order_status",nullable=false)
	private Integer orderStatus;        // 订单状态  //OrderStatus
	@Column(name="pay_status",nullable=false)
	private Integer payStatus;        	// 支付状态 PayStatus
	@Column(name="lottery_type",nullable=false)
	
	private Integer lotteryType;		// 彩种
	@Column(name="phase")
	private String phase;					// 彩期 
	@Column(name="content",nullable=false)
	private String content;					// 投注内容
	@Column(name="multiple",nullable=false)
	private Integer multiple;				// 倍数
	@Column(name="adddition")
	private Integer addition; 			// 订单追加字段(目前可能存储大乐透追加或者快乐8飞盘) //YesNoStatus
	@Column(name="last_matchnum")
	private Long lastMatchNum;	            //订单中最后一个场次的索引顺序
	@Column(name="first_matchnum")
	private Long firstMatchNum;				//订单中第一个场次的索引顺序
	@Column(name="hemaiid")
	private String hemaiId;                 //合买id
	@Column(name="chase_id")
	private String chaseId;                 //追号或优惠券id
	@Column(name="terminalid")
	private String terminalId;//送票终端，可用为空
	
	@Column(name="is_exchanged")
	private Integer isExchanged;		//派奖状态  YesNoStatus
	@Column(name="wincode")
	private String wincode;//中奖号码
	@Column(name="terminal_type_id")
	private Integer terminalTypeId;//送票终端类型，可为空
	
	@Column(name="account_type")
	private Integer accountType;//支付类型
	@Column(name="bet_type")
	private Integer betType;
	@Column(name="match_nums")
	private String matchNums;//竞彩的场次集合，中间用逗号隔开,比如201401001,201401002
	@Column(name="play_type_str")
	private String playTypeStr;//玩法字符串，如果是多玩法 ，存为玩法,玩法
	@Column(name="total_prize")
	private BigDecimal totalPrize;//中奖总金额
	@Column(name="add_prize")
	private BigDecimal addPrize;//加奖奖金
	@Column(name="user_name")
	 private String  userName;//登陆名
	 @Column(name="real_name")
	 private String realName;//用户真实姓名
	
	@Column(name="buy_agencyno")
	 private String buyAgencyno;//购买渠道,从哪个渠道进行购买的
	@Column(name="agent_id")
	private String agentId;// 出票id
    @Column(name="ticket_count")
    private  Integer ticketCount;//票总数
    @Column(name="fail_count")
    private Integer failCount;//失败票数
    @Column(name="prize_optimize")//奖金优化
    private Integer prizeOptimize;
    @Column(name="code_filter")//注码 过滤标记
    private Integer codeFilter;
    @Column(name="preferential_amount")
	private BigDecimal preferentialAmount;//优惠金额
    @Column(name="memo")
	private String memo;//描述,备注

	@Override 
	public String toString() {
		StringBuffer sb = new StringBuffer("订单详情：");
		sb.append("[订单流水号：" + id + "]");
		sb.append("[彩种：" + lotteryType + "]");
		sb.append("[彩期：" + phase + "]");
		sb.append("[投注类型：" + betType + "]");
		sb.append("[订单状态：" + orderStatus + "]");
		sb.append("[订单开奖结果状态：" + orderResultStatus + "]");
		sb.append("[金额：" + amount + "]");
		sb.append("[倍数：" + multiple + "]");
		sb.append("[订单追加：" + addition + "]");
		sb.append("[税前奖金：" + pretaxPrize + "]");
		sb.append("[小奖税后奖金：" + smallPosttaxPrize + "]");
		sb.append("[大奖税后奖金：" + bigPosttaxPrize + "]");
		sb.append("[中奖详情：" + prizeDetail + "]");
		sb.append("[玩法详情：" + playTypeStr + "]");
		sb.append("[中奖总金额：" + totalPrize + "]");
		sb.append("[奖金优化：" + prizeOptimize + "]");
		return sb.toString();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserno() {
		return userno;
	}
	public void setUserno(String userno) {
		this.userno = userno;
	}
	public String getBuyUserno() {
		return buyUserno;
	}
	public void setBuyUserno(String buyUserno) {
		this.buyUserno = buyUserno;
	}

	
	public BigDecimal getPretaxPrize() {
		return pretaxPrize;
	}
	public void setPretaxPrize(BigDecimal pretaxPrize) {
		this.pretaxPrize = pretaxPrize;
	}
	public BigDecimal getSmallPosttaxPrize() {
		return smallPosttaxPrize;
	}
	public void setSmallPosttaxPrize(BigDecimal smallPosttaxPrize) {
		this.smallPosttaxPrize = smallPosttaxPrize;
	}
	public BigDecimal getBigPosttaxPrize() {
		return bigPosttaxPrize;
	}
	public void setBigPosttaxPrize(BigDecimal bigPosttaxPrize) {
		this.bigPosttaxPrize = bigPosttaxPrize;
	}
	public String getPrizeDetail() {
		return prizeDetail;
	}
	public void setPrizeDetail(String prizeDetail) {
		this.prizeDetail = prizeDetail;
	}
	public Integer getOrderResultStatus() {
		return orderResultStatus;
	}
	public void setOrderResultStatus(Integer orderResultStatus) {
		this.orderResultStatus = orderResultStatus;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public BigDecimal getAmt() {
		return amt;
	}
	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}
	public Date getReceiveTime() {
		return receiveTime;
	}
	public void setReceiveTime(Date receiveTime) {
		this.receiveTime = receiveTime;
	}
	public Date getProcessTime() {
		return processTime;
	}
	public void setProcessTime(Date processTime) {
		this.processTime = processTime;
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
	public Date getRewardTime() {
		return rewardTime;
	}
	public void setRewardTime(Date rewardTime) {
		this.rewardTime = rewardTime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public Integer getOrderStatus() {
		return orderStatus;
	}
	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	public Integer getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Integer payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public String getPhase() {
		return phase;
	}
	public void setPhase(String phase) {
		this.phase = phase;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public Integer getAddition() {
		return addition;
	}
	public void setAddition(Integer addition) {
		this.addition = addition;
	}
	public Long getLastMatchNum() {
		return lastMatchNum;
	}
	public void setLastMatchNum(Long lastMatchNum) {
		this.lastMatchNum = lastMatchNum;
	}
	public Long getFirstMatchNum() {
		return firstMatchNum;
	}
	public void setFirstMatchNum(Long firstMatchNum) {
		this.firstMatchNum = firstMatchNum;
	}
	public String getHemaiId() {
		return hemaiId;
	}
	public void setHemaiId(String hemaiId) {
		this.hemaiId = hemaiId;
	}
	public String getTerminalId() {
		return terminalId;
	}
	public void setTerminalId(String terminalId) {
		this.terminalId = terminalId;
	}
	public Integer getIsExchanged() {
		return isExchanged;
	}
	public void setIsExchanged(Integer isExchanged) {
		this.isExchanged = isExchanged;
	}
	public String getWincode() {
		return wincode;
	}
	public void setWincode(String wincode) {
		this.wincode = wincode;
	}
	public Integer getTerminalTypeId() {
		return terminalTypeId;
	}
	public void setTerminalTypeId(Integer terminalTypeId) {
		this.terminalTypeId = terminalTypeId;
	}
	public Integer getAccountType() {
		return accountType;
	}
	public void setAccountType(Integer accountType) {
		this.accountType = accountType;
	}
	public String getChaseId() {
		return chaseId;
	}
	public void setChaseId(String chaseId) {
		this.chaseId = chaseId;
	}
	public Integer getBetType() {
		return betType;
	}
	public void setBetType(Integer betType) {
		this.betType = betType;
	}
	public String getMatchNums() {
		return matchNums;
	}
	public void setMatchNums(String matchNums) {
		this.matchNums = matchNums;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPlayTypeStr() {
		return playTypeStr;
	}
	public void setPlayTypeStr(String playTypeStr) {
		this.playTypeStr = playTypeStr;
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
	public String getRealName() {
		return realName;
	}
	public void setRealName(String realName) {
		this.realName = realName;
	}
	public String getAgencyNo() {
		return agencyNo;
	}
	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}
	public String getBuyAgencyno() {
		return buyAgencyno;
	}
	public void setBuyAgencyno(String buyAgencyno) {
		this.buyAgencyno = buyAgencyno;
	}
	public String getAgentId() {
		return agentId;
	}
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}

    public Integer getTicketCount() {
        return ticketCount;
    }

    public void setTicketCount(Integer ticketCount) {
        this.ticketCount = ticketCount;
    }

    public Integer getFailCount() {
        return failCount;
    }

    public void setFailCount(Integer failCount) {
        this.failCount = failCount;
    }
	public Integer getPrizeOptimize() {
		return prizeOptimize;
	}
	public void setPrizeOptimize(Integer prizeOptimize) {
		this.prizeOptimize = prizeOptimize;
	}
	public Integer getCodeFilter() {
		return codeFilter;
	}
	public void setCodeFilter(Integer codeFilter) {
		this.codeFilter = codeFilter;
	}


	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public BigDecimal getPreferentialAmount() {
		return preferentialAmount;
	}

	public void setPreferentialAmount(BigDecimal preferentialAmount) {
		this.preferentialAmount = preferentialAmount;
	}
}
