package com.lottery.core.domain.terminal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.cache.IMemcachedObject;

/**
 * 终端和出票彩种配置的匹配记录
 * 
 * 
 */
@Entity
@Table(name="terminal_config")
public class TerminalConfig implements IMemcachedObject{

	private static final long serialVersionUID = -2088499951914710730L;
	@Id
    @Column(name="id")
	private Long id;	//记录编号
	@Column(name="lottery_type",nullable=false)
	private Integer lotteryType;	//彩种编号
	@Column(name="terminal_id",nullable=false)
	private Long terminalId;			//终端编号
	@Column(name="terminal_type")
	private Integer terminalType;	//终端类型 TerminalType
	@Column(name="play_type")
	private Integer playType;          //玩法编号 PlayType
	@Column(name="terminal_name")
	private String terminalName;//终端名字
	@Column(name="weight",nullable=false)
	private Integer weight;				//优先级，权重
	@Column(name="is_enabled",nullable=false)
	private Integer isEnabled;				//是否可用 EnabledStatus
    @Column(name="is_paused",nullable=false)
	private Integer isPaused; 		//是否暂停送票 YesNoStatus 1暂停
    @Column(name="allotforbidperiod")
	private String allotForbidPeriod;	// 禁止分票时段
	@Column(name="sendforbidperiod")
    private String sendForbidPeriod;	// 禁止送票时段
	@Column(name="terminateForward")
	private Long terminateForward;	//打票提前截止时间，单位：毫秒
    @Column(name="checkForbidPeriod")
	private String checkForbidPeriod;	// 禁止检票时段
    @Column(name="is_check_enabled")
    private Integer isCheckEnabled;//是否暂停检票
    @Column(name="terminate_allot_forward")
	private Long terminateAllotForward;		// 打票截止前分票提前时间，单位：毫秒
   
    @Column(name="amount_enabled",nullable=false)
	private Integer amountEnabled;//金额是否可用 1可用 0停用
    @Column(name = "prefer_amount_region")
	private String preferAmountRegion;   // 偏好金额范围(金额格式：0|5000,10000|0,1000|2000，[左边界，右边界)，0表示不判断此边界)
	@Column(name="playtype_not_contain")
	private String playTypeNotContain;//不包含玩法
	@Column(name="playtype_not_contain_enabled")
	private Integer playTypeNotContainEnabled;//不包含玩法是否可用

	@Column(name="mix_contain")
	private String mixContain;//混合包括(格式:3001&3002,两个必须包含;3001|3002 两个包含一个就行,中间用逗号分隔.比如:3006|3007,3008&3009)
	@Column(name="check_wait")
	private Long checkWait=10000l;//查票等待,单位:毫秒
	@Column(name="send_wait")
	private Long sendWait=10000l;//送票等待,单位:毫秒


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Integer getLotteryType() {
		return lotteryType;
	}

	public void setLotteryType(Integer lotteryType) {
		this.lotteryType = lotteryType;
	}

	public Long getTerminalId() {
		return terminalId;
	}

	public void setTerminalId(Long terminalId) {
		this.terminalId = terminalId;
	}

	public Integer getTerminalType() {
		return terminalType;
	}

	public void setTerminalType(Integer terminalType) {
		this.terminalType = terminalType;
	}

	public Integer getPlayType() {
		return playType;
	}

	public void setPlayType(Integer playType) {
		this.playType = playType;
	}

	public Integer getWeight() {
		return weight;
	}

	public void setWeight(Integer weight) {
		this.weight = weight;
	}

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getIsPaused() {
		return isPaused;
	}

	public void setIsPaused(Integer isPaused) {
		this.isPaused = isPaused;
	}

	public String getAllotForbidPeriod() {
		return allotForbidPeriod;
	}

	public void setAllotForbidPeriod(String allotForbidPeriod) {
		this.allotForbidPeriod = allotForbidPeriod;
	}

	public String getSendForbidPeriod() {
		return sendForbidPeriod;
	}

	public void setSendForbidPeriod(String sendForbidPeriod) {
		this.sendForbidPeriod = sendForbidPeriod;
	}

	public Long getTerminateForward() {
		return terminateForward;
	}

	public void setTerminateForward(Long terminateForward) {
		this.terminateForward = terminateForward;
	}

	public String getCheckForbidPeriod() {
		return checkForbidPeriod;
	}

	public void setCheckForbidPeriod(String checkForbidPeriod) {
		this.checkForbidPeriod = checkForbidPeriod;
	}

	public String getTerminalName() {
		return terminalName;
	}

	public void setTerminalName(String terminalName) {
		this.terminalName = terminalName;
	}

	
	
	public Integer getIsCheckEnabled() {
		return isCheckEnabled;
	}

	public void setIsCheckEnabled(Integer isCheckEnabled) {
		this.isCheckEnabled = isCheckEnabled;
	}

	public Long getTerminateAllotForward() {
		return terminateAllotForward;
	}

	public void setTerminateAllotForward(Long terminateAllotForward) {
		this.terminateAllotForward = terminateAllotForward;
	}

	public String getPreferAmountRegion() {
		return preferAmountRegion;
	}

	public void setPreferAmountRegion(String preferAmountRegion) {
		this.preferAmountRegion = preferAmountRegion;
	}

	public String getPlayTypeNotContain() {
		return playTypeNotContain;
	}

	public void setPlayTypeNotContain(String playTypeNotContain) {
		this.playTypeNotContain = playTypeNotContain;
	}

	public Integer getPlayTypeNotContainEnabled() {
		return playTypeNotContainEnabled;
	}

	public void setPlayTypeNotContainEnabled(Integer playTypeNotContainEnabled) {
		this.playTypeNotContainEnabled = playTypeNotContainEnabled;
	}
	public Integer getAmountEnabled() {
		return amountEnabled;
	}
	public void setAmountEnabled(Integer amountEnabled) {
		this.amountEnabled = amountEnabled;
	}

	public String getMixContain() {
		return mixContain;
	}

	public void setMixContain(String mixContain) {
		this.mixContain = mixContain;
	}

	public Long getCheckWait() {
		return checkWait;
	}

	public void setCheckWait(Long checkWait) {
		this.checkWait = checkWait;
	}

	public Long getSendWait() {
		return sendWait;
	}

	public void setSendWait(Long sendWait) {
		this.sendWait = sendWait;
	}

	@Override
	public String toString() {
		return "TerminalConfig{" +
				"id=" + id +
				", lotteryType=" + lotteryType +
				", terminalId=" + terminalId +
				", terminalType=" + terminalType +
				", playType=" + playType +
				", terminalName='" + terminalName + '\'' +
				", weight=" + weight +
				", isEnabled=" + isEnabled +
				", isPaused=" + isPaused +
				", allotForbidPeriod='" + allotForbidPeriod + '\'' +
				", sendForbidPeriod='" + sendForbidPeriod + '\'' +
				", terminateForward=" + terminateForward +
				", checkForbidPeriod='" + checkForbidPeriod + '\'' +
				", isCheckEnabled=" + isCheckEnabled +
				", terminateAllotForward=" + terminateAllotForward +
				", amountEnabled=" + amountEnabled +
				", preferAmountRegion='" + preferAmountRegion + '\'' +
				", playTypeNotContain='" + playTypeNotContain + '\'' +
				", playTypeNotContainEnabled=" + playTypeNotContainEnabled +
				", mixContain='" + mixContain + '\'' +
				'}';
	}


}
