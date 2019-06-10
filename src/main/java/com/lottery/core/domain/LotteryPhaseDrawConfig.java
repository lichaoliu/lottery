package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * 彩种开奖配置
 * Created by fengqinyun on 15/4/22.
 */
@Entity
@Table(name="lottery_phase_draw_config")
public class LotteryPhaseDrawConfig implements Serializable {
    @Id
    @Column(name = "id")
    private  Long id;
    @Column(name = "lottery_type")
    private  Integer lotteryType;
    @Column(name="terminal_id")
    private  Long terminalId;
    @Column(name="is_enabled")
    private  Integer isEnabled;    //是否启用 EnableStatus
    @Column(name="sync_time")
    private  Integer syncTime;       //是否同步彩期时间 YesNoStatus
    @Column(name="terminal_name")
    private String terminalName;//终端名字
    @Column(name="is_draw")
    private Integer isDraw;//是否可以直接算奖

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

    public Integer getIsEnabled() {
        return isEnabled;
    }

    public void setIsEnabled(Integer isEnabled) {
        this.isEnabled = isEnabled;
    }

    public Integer getSyncTime() {
        return syncTime;
    }

    public void setSyncTime(Integer syncTime) {
        this.syncTime = syncTime;
    }

    public String getTerminalName() {
        return terminalName;
    }

    public void setTerminalName(String terminalName) {
        this.terminalName = terminalName;
    }

    public Integer getIsDraw() {
        return isDraw;
    }

    public void setIsDraw(Integer isDraw) {
        this.isDraw = isDraw;
    }
}
