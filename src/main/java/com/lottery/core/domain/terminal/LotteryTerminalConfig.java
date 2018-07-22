package com.lottery.core.domain.terminal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.lottery.common.cache.IMemcachedObject;

/**
 * 彩种的终端相关的配置记录
 * 
 * 
 */
@Table(name="lottery_terminal_config")
@Entity
public class LotteryTerminalConfig implements IMemcachedObject {

    private static final long serialVersionUID = 445605308149040545L;
    @Id
    @Column(name="id")
    private Long id;    //记录编号
    @Column(name="lottery_type")
    private Integer lotteryType;
    @Column(name="is_enabled")
    private Integer isEnabled;                //是否可用 EnabledStatus
    @Column(name="is_check_enabled")
    private Integer isCheckEnabled;            //是否可用异步检票
    @Column(name="is_paused")
    private Integer isPaused;        //是否暂停送票
   @Column(name="allot_forbid_period")
    private String allotForbidPeriod;    // 禁止分票时段
    @Column(name="send_forbid_period") 
    private String sendForbidPeriod;    // 禁止送票时段
    @Column(name="check_forbid_period")
    private String checkForbidPeriod;    // 禁止检票时段

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

	public Integer getIsEnabled() {
		return isEnabled;
	}

	public void setIsEnabled(Integer isEnabled) {
		this.isEnabled = isEnabled;
	}

	public Integer getIsCheckEnabled() {
		return isCheckEnabled;
	}

	public void setIsCheckEnabled(Integer isCheckEnabled) {
		this.isCheckEnabled = isCheckEnabled;
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

	public String getCheckForbidPeriod() {
		return checkForbidPeriod;
	}

	public void setCheckForbidPeriod(String checkForbidPeriod) {
		this.checkForbidPeriod = checkForbidPeriod;
	}

}