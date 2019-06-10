package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "lottery_caselot_buy")
public class LotteryCaseLotBuy implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -5655797627892359540L;

	/** ID */
	@Id
	@Column(name = "id")
	private String id;

	/** 合买ID */
	@Column(name = "caselot_id")
	private String caselotId;

	/** 用户编号 */
	@Column(name = "userno")
	private String userno;

	/** 购买金额 */
	@Column(name = "num")
	private BigDecimal num;

	/** 购买时间 */
	@Column(name = "buy_time")
	private Date buyTime;

	/** 状态  CaseLotBuyState*/
	@Column(name = "flag")
	private Integer flag;

	/** 购买时的保底金额 */
	@Column(name = "safe_amt")
	private BigDecimal safeAmt;

	/** 冻结的保底金额，也就是实际可保底的金额 */
	@Column(name = "freeze_safe_amt")
	private BigDecimal freezeSafeAmt;

	/** 购买类型。1:发起者，0:参与者 */
	@Column(name = "buy_type")
	private Integer buyType;

	/** 中奖金额 */
	@Column(name = "prize_amt")
	private BigDecimal prizeAmt;

	/** 佣金金额 */
	@Column(name = "commision_prize_amt")
	private BigDecimal commisionPrizeAmt;

	/** 派奖状态 */
	@Column(name = "is_exchanged")
	private Integer isExchanged;
	
	/** 彩种 */
	@Column(name = "lottery_type")
	private Integer lotteryType;

	/** 期号 */
	@Column(name = "phase")
	private String phase;
	
	@Column(name="user_name")
	private String  userName;//登陆名
	
	@Column(name="real_name")
	private String realName;//用户真实姓名
	
	//保底可提现余额变动金额
	@Column(name = "freez_draw_amt")
	private BigDecimal freezDrawAmt;

	//购买可提现余额变动金额
	@Column(name = "buy_draw_amt")
	private BigDecimal buyDrawAmt;
    @Column(name="total_amount")
	private BigDecimal totalAmount;//总金额

	/** 方案类型 */
	@Column(name = "lots_type")
	private Integer lotsType;


	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCaselotId() {
		return caselotId;
	}

	public void setCaselotId(String caselotId) {
		this.caselotId = caselotId;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}

	public BigDecimal getNum() {
		return num;
	}

	public void setNum(BigDecimal num) {
		this.num = num;
	}

	public Date getBuyTime() {
		return buyTime;
	}

	public void setBuyTime(Date buyTime) {
		this.buyTime = buyTime;
	}

	public Integer getFlag() {
		return flag;
	}

	public void setFlag(Integer flag) {
		this.flag = flag;
	}

	public BigDecimal getSafeAmt() {
		return safeAmt;
	}

	public void setSafeAmt(BigDecimal safeAmt) {
		this.safeAmt = safeAmt;
	}

	public BigDecimal getFreezeSafeAmt() {
		return freezeSafeAmt;
	}

	public void setFreezeSafeAmt(BigDecimal freezeSafeAmt) {
		this.freezeSafeAmt = freezeSafeAmt;
	}

	public Integer getBuyType() {
		return buyType;
	}

	public void setBuyType(Integer buyType) {
		this.buyType = buyType;
	}

	public BigDecimal getPrizeAmt() {
		return prizeAmt;
	}

	public void setPrizeAmt(BigDecimal prizeAmt) {
		this.prizeAmt = prizeAmt;
	}

	public BigDecimal getCommisionPrizeAmt() {
		return commisionPrizeAmt;
	}

	public void setCommisionPrizeAmt(BigDecimal commisionPrizeAmt) {
		this.commisionPrizeAmt = commisionPrizeAmt;
	}

	public Integer getIsExchanged() {
		return isExchanged;
	}

	public void setIsExchanged(Integer isExchanged) {
		this.isExchanged = isExchanged;
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

	public BigDecimal getFreezDrawAmt() {
		return freezDrawAmt;
	}

	public void setFreezDrawAmt(BigDecimal freezDrawAmt) {
		this.freezDrawAmt = freezDrawAmt;
	}

	public BigDecimal getBuyDrawAmt() {
		return buyDrawAmt;
	}

	public void setBuyDrawAmt(BigDecimal buyDrawAmt) {
		this.buyDrawAmt = buyDrawAmt;
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

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public Integer getLotsType() {
		return lotsType;
	}

	public void setLotsType(Integer lotsType) {
		this.lotsType = lotsType;
	}
}
