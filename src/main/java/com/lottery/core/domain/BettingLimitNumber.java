package com.lottery.core.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by fengqinyun on 16/8/25.
 * 投注限号
 */
@Entity
@Table(name="betting_limit_number")
public class BettingLimitNumber implements Serializable{
    @Id
    @Column(name="id")
    private Long id;
    @Column(name="limit_type")
    private Integer limitType;//限号类型 LimitNumberType
    @Column(name="lottery_type")
    private Integer lotteryType;
    @Column(name="play_type")
    private Integer playType;
    @Column(name = "status")
    private Integer status;//YesNoStatus
    @Column(name = "create_time")
    private Date createTime;
    @Column(name="update_time")
    private Date updateTime;
    @Column(name="content")
    private String content;//限号内容


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getLimitType() {
        return limitType;
    }

    public void setLimitType(Integer limitType) {
        this.limitType = limitType;
    }

    public Integer getLotteryType() {
        return lotteryType;
    }

    public void setLotteryType(Integer lotteryType) {
        this.lotteryType = lotteryType;
    }

    public Integer getPlayType() {
        return playType;
    }

    public void setPlayType(Integer playType) {
        this.playType = playType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
