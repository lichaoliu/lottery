package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * 战绩创建记录
 */
@Entity
@Table(name = "user_achievement_detail")
public class UserAchievementDetail implements Serializable{

	@Id
	@Column(name = "id")
	private Long id;

	/** 用户编号 */
	@Column(name = "userno")
	private String userno;

	/** 彩种 */
	@Column(name = "lottery_type")
	private Integer lotteryType;

	/** 战绩 */
	@Column(name = "achievement")
	private BigDecimal achievement;

	/** 战绩类型  0无效  1有效*/
	@Column(name = "achievement_type")
	private Integer achievementType;

	/** 订单ID */
	@Column(name = "caselot_id")
	private String caselotId;

	/**	创建时间 */
	@Column(name = "create_time")
	private Date createTime;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUserno() {
		return userno;
	}

	public void setUserno(String userno) {
		this.userno = userno;
	}


	public BigDecimal getAchievement() {
		return achievement;
	}

	public void setAchievement(BigDecimal achievement) {
		this.achievement = achievement;
	}

	public Integer getAchievementType() {
		return achievementType;
	}

	public void setAchievementType(Integer achievementType) {
		this.achievementType = achievementType;
	}

	public String getCaselotId() {
		return caselotId;
	}

	public void setCaselotId(String caselotId) {
		this.caselotId = caselotId;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

}
