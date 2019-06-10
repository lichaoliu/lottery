package com.lottery.core.domain.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.contains.CommonStatus;
import com.lottery.common.contains.SignStatus;
import com.lottery.common.contains.lottery.AccountDetailType;
import com.lottery.common.contains.lottery.AccountType;
@Entity
@Table(name="user_account_detail")
public class UserAccountDetail implements Serializable{

	/**
	 * 账号变动记录表
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@Column(name="id")
    private String id;//主键
	@Column(name="userno")
	private  String userno;
	@Column(name="user_name")
	private String userName;//用户名
	@Column(name="payid")
	private String payId; //支付编号 ，包括订单号(orderId)，交易号(usertransactionId)
	@Column(name="create_time")
	private Date createtime;//交易时间
	@Column(name="amt")
	private BigDecimal amt;//交易金额
	@Column(name="pay_type")
	private Integer payType;//支付类型 //AccountType
	@Column(name="status")
    private Integer commonStatus; //状态() //CommonStatus
	@Column(name="balance")
	private BigDecimal balance;//用户余额
	@Column(name="draw_balance")
	private BigDecimal drawbalance;//可用余额
    @Column(name="freeze")
	private BigDecimal freeze;//冻结金额
    @Column(name="memo")//描述
    private String memo="";
	@Column(name="account_detail_type")
    private Integer detailType; //AccountDetailType
	@Column(name="otherid")
	private String otherid; //合买id
	@Column(name="sign_status")
	private Integer signStatus;//账务标示 SignStatus
	@Column(name="finish_time")
	private Date finishTime;//完成时间
	@Column(name="lottery_type")
	private Integer lotteryType;//彩种
	@Column(name="phase")
	private String phase;//期号
	@Column(name="draw_amount")
	private BigDecimal drawAmount=BigDecimal.ZERO;//使用可提现金额
	@Column(name="not_draw_amount")
	private BigDecimal notDrawAmount=BigDecimal.ZERO;//不可提现金额
	@Column(name="give_amount")
	private BigDecimal giveAmount=BigDecimal.ZERO;//赠送金额
	@Column(name="agency_no")
	private String agencyNo;//渠道号
	@Column(name="orderid")
	private String orderId;//订单号
	@Column(name="order_prize_amount")
	private BigDecimal orderPrizeAmount=BigDecimal.ZERO;//订单中奖金额
    @Column(name = "order_prize_type")
	private Integer orderPrizeType;//算奖方式
	
	public String getId() {
		return id;
	}
	
	public UserAccountDetail(String id, String userno, String userName, String payId,
			Date createtime, BigDecimal amt, AccountType payType,
			CommonStatus commonStatus, BigDecimal balance,AccountDetailType detailType,
			BigDecimal drawbalance, BigDecimal freeze,String memo,SignStatus signStatus) {
		super();
		this.id = id;
		this.userno = userno;
		this.userName=userName;
		this.payId = payId;
		this.createtime = createtime;
		this.amt = amt;
		this.payType = payType.getValue();
		this.commonStatus = commonStatus.getValue();
		this.balance = balance;
		this.drawbalance = drawbalance;
		this.freeze = freeze;
		this.detailType=detailType.getValue();
		this.memo=memo;
		this.signStatus=signStatus.value;
		this.createtime=new Date();
	}

	public UserAccountDetail(){}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public BigDecimal getAmt() {
		return amt;
	}

	public void setAmt(BigDecimal amt) {
		this.amt = amt;
	}

	public Integer getPayType() {
		return payType;
	}

	public void setPayType(Integer payType) {
		this.payType = payType;
	}

	public Integer getCommonStatus() {
		return commonStatus;
	}

	public void setCommonStatus(Integer commonStatus) {
		this.commonStatus = commonStatus;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public BigDecimal getDrawbalance() {
		return drawbalance;
	}

	public void setDrawbalance(BigDecimal drawbalance) {
		this.drawbalance = drawbalance;
	}

	public BigDecimal getFreeze() {
		return freeze;
	}

	public void setFreeze(BigDecimal freeze) {
		this.freeze = freeze;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public Integer getDetailType() {
		return detailType;
	}

	public void setDetailType(Integer detailType) {
		this.detailType = detailType;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getOtherid() {
		return otherid;
	}

	public void setOtherid(String otherid) {
		this.otherid = otherid;
	}

	
	public Integer getSignStatus() {
		return signStatus;
	}

	public void setSignStatus(Integer signStatus) {
		this.signStatus = signStatus;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
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

	public BigDecimal getDrawAmount() {
		return drawAmount;
	}

	public void setDrawAmount(BigDecimal drawAmount) {
		this.drawAmount = drawAmount;
	}

	public BigDecimal getNotDrawAmount() {
		return notDrawAmount;
	}

	public void setNotDrawAmount(BigDecimal notDrawAmount) {
		this.notDrawAmount = notDrawAmount;
	}

	public BigDecimal getGiveAmount() {
		return giveAmount;
	}

	public void setGiveAmount(BigDecimal giveAmount) {
		this.giveAmount = giveAmount;
	}

	public String getAgencyNo() {
		return agencyNo;
	}

	public void setAgencyNo(String agencyNo) {
		this.agencyNo = agencyNo;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

	public BigDecimal getOrderPrizeAmount() {
		return orderPrizeAmount;
	}

	public void setOrderPrizeAmount(BigDecimal orderPrizeAmount) {
		this.orderPrizeAmount = orderPrizeAmount;
	}

	public Integer getOrderPrizeType() {
		return orderPrizeType;
	}

	public void setOrderPrizeType(Integer orderPrizeType) {
		this.orderPrizeType = orderPrizeType;
	}
}
