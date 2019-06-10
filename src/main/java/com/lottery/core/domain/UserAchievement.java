package com.lottery.core.domain;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.AchievementType;

/**
 * 用户战绩信息
 */
@Entity
@Table(name = "user_achievement")
public class UserAchievement implements Serializable {

	@Id
    @Column(name = "id")
    private Long id;

	/** 用户编号 */
	@Column(name = "userno")
	private String userno;

	/** 彩种 */
	@Column(name = "lottery_type")
	private Integer lotteryType;

	/** 有效战绩 */
	@Column(name = "effective_achievement")
	private BigDecimal effectiveAchievement;

	/** 灰色战绩 */
	@Column(name = "ineffective_achievement")
	private BigDecimal ineffectiveAchievement;

	/** 最近修改时间 */
	@Column(name = "update_time")
	private Date updateTime;

	
	/**
	 * 计算显示的图标
	 * @param achievement 战绩值
	 * @return Map<String, Integer>
	 */
	public Map<String, Integer> getDisplayIcon() {
		// 有效战绩图标
		int effectiveValue = this.getEffectiveAchievement().intValue();
		int crownNum = effectiveValue / AchievementType.crown.intValue();
		int temp = effectiveValue % AchievementType.crown.intValue();
		int cupNum = temp / AchievementType.cup.intValue();
		temp = temp % AchievementType.cup.intValue();
		int diamondNum = temp / AchievementType.diamond.intValue();
		temp = temp % AchievementType.diamond.intValue();
		int goldStarNum = temp / AchievementType.goldStar.intValue();
		Map<String, Integer> result = new HashMap<String, Integer>();
		if (crownNum != 0) {
			result.put("crown", crownNum);
		}
		if (cupNum != 0) {
			result.put("cup", cupNum);
		}
		if (diamondNum != 0) {
			result.put("diamond", diamondNum);
		}
		if (goldStarNum != 0) {
			result.put("goldStar", goldStarNum);
		}
		// 无效战绩图标
		int ineffectiveValue = this.getIneffectiveAchievement().intValue();
		int graycrownNum = ineffectiveValue / AchievementType.crown.intValue();
		temp = ineffectiveValue % AchievementType.crown.intValue();
		int graycupNum = temp / AchievementType.cup.intValue();
		temp = temp % AchievementType.cup.intValue();
		int graydiamondNum = temp / AchievementType.diamond.intValue();
		temp = temp % AchievementType.diamond.intValue();
		int graygoldStarNum = temp / AchievementType.goldStar.intValue();
		if (graycrownNum != 0) {
			result.put("graycrown", graycrownNum);
		}
		if (graycupNum != 0) {
			result.put("graycup", graycupNum);
		}
		if (graydiamondNum != 0) {
			result.put("graydiamond", graydiamondNum);
		}
		if (graygoldStarNum != 0) {
			result.put("graygoldStar", graygoldStarNum);
		}

		return result;
	}
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
	public BigDecimal getEffectiveAchievement() {
		return effectiveAchievement;
	}
	public void setEffectiveAchievement(BigDecimal effectiveAchievement) {
		this.effectiveAchievement = effectiveAchievement;
	}
	public BigDecimal getIneffectiveAchievement() {
		return ineffectiveAchievement;
	}
	public void setIneffectiveAchievement(BigDecimal ineffectiveAchievement) {
		this.ineffectiveAchievement = ineffectiveAchievement;
	}
	public Integer getLotteryType() {
		return lotteryType;
	}
	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	
}
