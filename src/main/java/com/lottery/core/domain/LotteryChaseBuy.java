package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
@Table(name="lottery_chase_buy")
@Entity
public class LotteryChaseBuy implements Serializable{
 /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/**
  * 追号执行表
  * */
    @Column(name="id")
    @Id
	private String id;//主键
    @Column(name="chaseid")//追号id
	private String chaseId;
    @Column(name="orderid")
	private String orderId;//订单号
    @Column(name="lottery_type")
	private Integer lotteryType;//彩种
    @Column(name="phase")
	private String phase;//期号
	@Column(name="status")
	private Integer status; //状态，ChaseBuyStatus
	@Column(name="chase_index")
	private Long chaseIndex;//第几次追号
	@Column(name="phase_start_time")
	private Date phaseStartTime;//期开始时间
	@Column(name="phase_end_time")
	private Date phaseEndTime;//期结束时间
	@Column(name="prize")
	private BigDecimal prize;//中奖金额
    @Column(name="amount")
	private BigDecimal amount;//购买金额
    @Column(name="multiple")
    private Integer multiple;//倍数
    @Column(name="remain_num")
    private Integer remainNum;//剩余期数
    @Column(name="remain_amount")
    private BigDecimal remainAmount;//剩余金额
    @Column(name="addition")
	private Integer addition;//YesNoStatus 是否追加
    @Column(name="memo")
    private String memo;//描述
    @Column(name="finish_time")
    private Date finishTime;//完成时间
    @Column(name="userno")
    private String userno;
	@Column(name="user_name")
	private String userName;
    @Column(name="chase_type")
    private Integer chaseType;
    @Column(name="create_time")
    private Date createTime;
    @Column(name="bet_code")
    private String betCode;//投注号码
    @Column(name="order_result_status")
    private Integer orderResultStatus;//中奖状态
    @Column(name="order_status")
    private Integer orderStatus;//出票状态
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getChaseId() {
		return chaseId;
	}

	public void setChaseId(String chaseId) {
		this.chaseId = chaseId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	

	public Long getChaseIndex() {
		return chaseIndex;
	}

	public void setChaseIndex(Long chaseIndex) {
		this.chaseIndex = chaseIndex;
	}

	public Date getPhaseStartTime() {
		return phaseStartTime;
	}

	public void setPhaseStartTime(Date phaseStartTime) {
		this.phaseStartTime = phaseStartTime;
	}

	public Date getPhaseEndTime() {
		return phaseEndTime;
	}

	public void setPhaseEndTime(Date phaseEndTime) {
		this.phaseEndTime = phaseEndTime;
	}

	public BigDecimal getPrize() {
		return prize;
	}

	public void setPrize(BigDecimal prize) {
		this.prize = prize;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Integer getMultiple() {
		return multiple;
	}

	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getAddition() {
		return addition;
	}

	public void setAddition(Integer addition) {
		this.addition = addition;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


    public Integer getChaseType() {
        return chaseType;
    }

    public void setChaseType(Integer chaseType) {
        this.chaseType = chaseType;
    }

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public String getBetCode() {
		return betCode;
	}

	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}

	public Integer getOrderResultStatus() {
		return orderResultStatus;
	}

	public void setOrderResultStatus(Integer orderResultStatus) {
		this.orderResultStatus = orderResultStatus;
	}

	public Integer getOrderStatus() {
		return orderStatus;
	}

	public void setOrderStatus(Integer orderStatus) {
		this.orderStatus = orderStatus;
	}
	

}
