package com.lottery.core.domain.account;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
@Table(name="user_rebate_statistic")
@Entity
public class UserRebateStatistic implements Serializable{
	@EmbeddedId
	private UserRebateStatisticPK id;
	@Column(name="amount")
	private BigDecimal amount;//投注总金额
	@Column(name="point_location")
	private Double pointLocation;//点位
	@Column(name="rebate_amount")
	private BigDecimal rebateAmount;//返点金额
	@Column(name="create_time")
	private Date createTime;//
	@Column(name="belong_to")
	private String belongTo;//隶属于
	@Column(name="is_agent")
	private Integer isAgent;//是否渠道
	@Column(name="user_name")
	private String userName;//
	@Column(name="statistic_lottery")
	private Integer statisticLottery;//彩种编号
	@Column(name="statistic_Type")
	private Integer statisticType;//统计类型
	@Column(name="month_num")
	private Long monthNum;
	public UserRebateStatisticPK getId() {
		return id;
	}
	public void setId(UserRebateStatisticPK id) {
		this.id = id;
	}
	public BigDecimal getAmount() {
		return amount;
	}
	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}
	public Double getPointLocation() {
		return pointLocation;
	}
	public void setPointLocation(Double pointLocation) {
		this.pointLocation = pointLocation;
	}
	public BigDecimal getRebateAmount() {
		return rebateAmount;
	}
	public void setRebateAmount(BigDecimal rebateAmount) {
		this.rebateAmount = rebateAmount;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public String getBelongTo() {
		return belongTo;
	}
	public void setBelongTo(String belongTo) {
		this.belongTo = belongTo;
	}
	public Integer getIsAgent() {
		return isAgent;
	}
	public void setIsAgent(Integer isAgent) {
		this.isAgent = isAgent;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Integer getStatisticLottery() {
		return statisticLottery;
	}
	public void setStatisticLottery(Integer statisticLottery) {
		this.statisticLottery = statisticLottery;
	}
	public Integer getStatisticType() {
		return statisticType;
	}
	public void setStatisticType(Integer statisticType) {
		this.statisticType = statisticType;
	}
	public Long getMonthNum() {
		return monthNum;
	}
	public void setMonthNum(Long monthNum) {
		this.monthNum = monthNum;
	}
	
	
}
