package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="user_transaction")
public class UserTransaction implements Serializable {

	private static final long serialVersionUID = -768816152885637647L;

	/**
	 * 用户交易表  user_transaction ,用于银行充值，支付充值信息的记录，和账户表相关联
	 */
	@Id
	@Column(name = "id")
	private String id;

	@Column(name = "userno")
	private String userno;//用户id
	
	@Column(name = "trade_no")
	private String tradeNo;//外部交易号
	
    @Column(name="create_time")
    private Date createTime;//创建时间
    
    @Column(name="finish_time")
    private Date finishTime;//充值完成时间
   
	@Column(name = "fee")
	private BigDecimal fee;//费用
	
	@Column(name = "amount")
	private BigDecimal amount;//充值金额

	@Column(name = "status",nullable=false)
	private Integer status;//状态 PayStatus
	
	@Column(name = "channel")
	private String channel; //渠道
	
	@Column(name="pay_type")
	private String payType;//充值方式
	
	@Column(name="description")//描述
	private String description;
	@Column(name="real_amount")
	private BigDecimal realAmount;//扣除费率后的直接金额
	@Column(name="give_amount")
	private BigDecimal giveAmount;//赠送进额
	@Column(name="give_id")
	private String giveId;//赠送活动id
	@Column(name="card_id")
	private String cardId;//卡号
	@Column(name="passwd")
	private String passwd;//密码
	
	@Column(name="not_draw_perset")
	private BigDecimal notDrawPerset;//不能提现比例
	
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

	public String getTradeNo() {
		return tradeNo;
	}

	public void setTradeNo(String tradeNo) {
		this.tradeNo = tradeNo;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public BigDecimal getFee() {
		return fee;
	}

	public void setFee(BigDecimal fee) {
		this.fee = fee;
	}
	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}
	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public Date getFinishTime() {
		return finishTime;
	}

	public void setFinishTime(Date finishTime) {
		this.finishTime = finishTime;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public BigDecimal getRealAmount() {
		return realAmount;
	}

	public void setRealAmount(BigDecimal realAmount) {
		this.realAmount = realAmount;
	}

	public BigDecimal getGiveAmount() {
		return giveAmount;
	}

	public void setGiveAmount(BigDecimal giveAmount) {
		this.giveAmount = giveAmount;
	}

	public String getGiveId() {
		return giveId;
	}

	public void setGiveId(String giveId) {
		this.giveId = giveId;
	}

	
	public String getCardId() {
		return cardId;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public String getPasswd() {
		return passwd;
	}

	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}

	public String toString(){
		StringBuffer sb=new StringBuffer();
		sb.append("[id:"+id);
		sb.append("create_time:"+createTime);
		sb.append("]");
		return sb.toString();
	}

	public BigDecimal getNotDrawPerset() {
		return notDrawPerset;
	}

	public void setNotDrawPerset(BigDecimal notDrawPerset) {
		this.notDrawPerset = notDrawPerset;
	}

    
}
