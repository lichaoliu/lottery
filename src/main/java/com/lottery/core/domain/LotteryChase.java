package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 追号总表
 */
@Entity
@Table(name="lottery_chase")
public class LotteryChase implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4939691313935767881L;

	@Id
	@Column(name = "id")
	private String id;//主键

	/** 用户编号 */
	@Column(name = "userno")
	private String userno;

	/** 彩种编号 */
	@Column(name = "lottery_type")
	private Integer lotteryType;

	/** 彩票购买期数 */
	@Column(name = "batch_num")
	private Integer batchNum;

	/** 剩余购买期数 */
	@Column(name = "remain_num")
	private Integer remainNum;

	/** 开始投注的期号 */
	@Column(name = "begin_phase", length = 60)
	private String beginPhase;

	/** 上次投注的期号 */
	@Column(name = "last_phase", length = 60)
	private String lastPhase;
	
	/** 已投注期数 */
	@Column(name = "bet_num")
	private Integer betNum;
	
	/** 彩票注码 */
	@Column(name = "bet_code")
	private String betCode;


	/** 单次投注金额 */
	@Column(name = "amount")
	private BigDecimal amount;
	
	/**投注总金额 */
	@Column(name = "total_amount")
	private BigDecimal totalAmount;
	/**剩余总金额*/
    @Column(name="remain_amount")
	private BigDecimal remainAmount;
	/** 1 正常  2 取消 3 结束 */
	@Column(name = "state")
	private Integer state;

	/** 结束时间 */
	@Column(name = "end_time")
	private Date endTime;


	@Column(name = "chase_type")
	private Integer chaseType; //追号类型ChaseType
    @Column(name="prize_total")
    private BigDecimal prizeTotal;//中奖金额达到多少停止追号
	/** 最近修改时间 */
	@Column(name = "change_time")
	private Date changeTime;

	/** 由谁撤销 0:系统，1：用户 */
	@Column(name = "cancel_by")
	private Integer cancelBy;
	
	@Column(name="create_time")
	private Date createTime;//创建时间
	@Column(name="chase_detail")
	private String chaseDetail;//追号详情
	@Column(name="already_prize")
	private BigDecimal alreadyPrize=BigDecimal.ZERO;//已中奖金额
	@Column(name="multiple")
	private Integer multiple; //倍数
	@Column(name="addition")
	private Integer addition;//YesNoStatus 是否追加
	@Column(name="end_phase")
	private String endPhase;//结束期
	@Column(name="user_name")
	private String userName;
	@Column(name="memo")
	private String memo;
	@Column(name="buy_agencyno")
	private String buyAgencyno;
	public LotteryChase() {
		super();
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

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Integer getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(Integer batchNum) {
		this.batchNum = batchNum;
	}

	public Integer getRemainNum() {
		return remainNum;
	}

	public void setRemainNum(Integer remainNum) {
		this.remainNum = remainNum;
	}

	public String getBeginPhase() {
		return beginPhase;
	}

	public void setBeginPhase(String beginPhase) {
		this.beginPhase = beginPhase;
	}

	public String getLastPhase() {
		return lastPhase;
	}

	public void setLastPhase(String lastPhase) {
		this.lastPhase = lastPhase;
	}

	public Integer getBetNum() {
		return betNum;
	}

	public void setBetNum(Integer betNum) {
		this.betNum = betNum;
	}

	public String getBetCode() {
		return betCode;
	}

	public void setBetCode(String betCode) {
		this.betCode = betCode;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}



	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public Date getEndTime() {
		return endTime;
	}

	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

    public Integer getChaseType() {
        return chaseType;
    }

    public void setChaseType(Integer chaseType) {
        this.chaseType = chaseType;
    }

    public Date getChangeTime() {
		return changeTime;
	}

	public void setChangeTime(Date changeTime) {
		this.changeTime = changeTime;
	}

	public Integer getCancelBy() {
		return cancelBy;
	}

	public void setCancelBy(Integer cancelBy) {
		this.cancelBy = cancelBy;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public BigDecimal getRemainAmount() {
		return remainAmount;
	}

	public void setRemainAmount(BigDecimal remainAmount) {
		this.remainAmount = remainAmount;
	}

    public BigDecimal getPrizeTotal() {
        return prizeTotal;
    }

    public void setPrizeTotal(BigDecimal prizeTotal) {
        this.prizeTotal = prizeTotal;
    }

    public String getChaseDetail() {
		return chaseDetail;
	}

	public void setChaseDetail(String chaseDetail) {
		this.chaseDetail = chaseDetail;
	}

	
	public BigDecimal getAlreadyPrize() {
		return alreadyPrize;
	}
	public void setAlreadyPrize(BigDecimal alreadyPrize) {
		this.alreadyPrize = alreadyPrize;
	}
	
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	public LotteryChase(String id, String userno, Integer lotteryType,
			Integer batchNum, Integer remainNum, String beginPhase,
			String lastPhase, Integer betNum, String betCode,
			BigDecimal amount, BigDecimal totalAmount, BigDecimal remainAmount,
			Integer state, Date endTime, Integer chaseType, Date changeTime,
			Integer cancelBy, Date createTime, String chaseDetail,BigDecimal prizeTotal) {
		super();
		this.id = id;
		this.userno = userno;
		this.lotteryType = lotteryType;
		this.batchNum = batchNum;
		this.remainNum = remainNum;
		this.beginPhase = beginPhase;
		this.lastPhase = lastPhase;
		this.betNum = betNum;
		this.betCode = betCode;
		this.amount = amount;
		this.totalAmount = totalAmount;
		this.remainAmount = remainAmount;
		this.state = state;
		this.endTime = endTime;
		this.chaseType = chaseType;
		this.changeTime = changeTime;
		this.cancelBy = cancelBy;
		this.createTime = createTime;
		this.chaseDetail = chaseDetail;
        this.prizeTotal=prizeTotal;
	}
	public Integer getAddition() {
		return addition;
	}
	public void setAddition(Integer addition) {
		this.addition = addition;
	}
	public String getEndPhase() {
		return endPhase;
	}
	public void setEndPhase(String endPhase) {
		this.endPhase = endPhase;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getBuyAgencyno() {
		return buyAgencyno;
	}
	public void setBuyAgencyno(String buyAgencyno) {
		this.buyAgencyno = buyAgencyno;
	}
	
	


	
	
}
